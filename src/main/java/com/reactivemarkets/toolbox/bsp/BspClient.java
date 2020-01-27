/*
 * Copyright (C) 2019 Reactive Markets Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.reactivemarkets.toolbox.bsp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.nio.channels.ClosedChannelException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public interface BspClient {

    /**
     * Execute an asynchronous close or shutdown.
     *
     * @return a future for the asynchronous close operation.
     */
    Future<Void> close();

    /**
     * Flush all pending data to the underlying channel.
     *
     * @throws ClosedChannelException if the channel is closed or otherwise unavailable.
     */
    void flush() throws ClosedChannelException;

    /**
     * Execute an asynchronous reconnect. This operation will have no effect if a connection attempt
     * is already in progress.
     */
    void reconnect();

    /**
     * Schedule an asynchronous reconnect. This operation will have no effect if a connection
     * attempt is already in progress.
     *
     * @param delay the delay before reconnecting.
     * @param unit  the time unit for the delay.
     */
    void reconnect(long delay, TimeUnit unit);

    /**
     * Write the message to the output buffer. This method will not flush the output buffer
     * automatically. The {@link #flush()} method must be called to flush all pending data to the
     * underlying channel.
     *
     * @param msg the message to be written to the output buffer.
     * @throws ClosedChannelException if the channel is closed or otherwise unavailable.
     */
    void write(ByteBuf msg) throws ClosedChannelException;

    /**
     * Write the message to the output buffer and then immediately flush all pending data to the
     * underlying channel.
     *
     * @param msg the message to be written to the output buffer.
     * @throws ClosedChannelException if the channel is closed or otherwise unavailable.
     */
    void writeAndFlush(ByteBuf msg) throws ClosedChannelException;
}

@ChannelHandler.Sharable
final class BspClientImpl extends ChannelInboundHandlerAdapter implements BspClient {

    private final EventLoopGroup group;
    private final BspHandler handler;
    private final BspConfig config;
    private final Bootstrap bootstrap;
    private final CompletableFuture<Void> closeFuture = new CompletableFuture<Void>();
    private final Runnable reconnectCommand;
    private final AtomicBoolean connectPending = new AtomicBoolean(true);
    private ChannelFuture connectFuture;
    private volatile Channel channel;
    /**
     * Set to true by the {@link #close()} method.
     * Multiple readers.
     * Single writer: event loop thread.
     */
    private volatile boolean closed = false;

    BspClientImpl(final EventLoopGroup group, final BspHandler handler, final BspConfig config) {
        this.group = group;
        this.handler = handler;
        this.config = config;
        bootstrap = newClientBootstrap(group, this, config);
        reconnectCommand = newReconnectCommand();
        connect();
    }

    private static Bootstrap newClientBootstrap(final EventLoopGroup group,
                                                final ChannelInboundHandler handler,
                                                final BspConfig config) {
        final Bootstrap bs = new Bootstrap();
        bs.group(group);
        bs.channel(NioSocketChannel.class);
        bs.option(ChannelOption.TCP_NODELAY, true);
        bs.handler(new LoggingHandler(LogLevel.INFO));
        bs.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(final SocketChannel sc) throws Exception {
                final ChannelPipeline p = sc.pipeline();
                p.addLast("keepalive", new IdleStateHandler(config.hbInt + 1, config.hbInt, 0,
                    TimeUnit.SECONDS));
                p.addLast("bspDecoder", BspFactory.newBspDecoder());
                p.addLast("bspEncoder", BspFactory.newBspEncoder());
                p.addLast("handler", handler);
            }
        });
        return bs;
    }

    @Override
    public Future<Void> close() {
        // Execute on the event loop thread.
        group.execute(() -> {
            // Event loop thread.
            if (closed) {
                // Close already initiated.
                return;
            }
            // Single writer: event loop thread.
            closed = true;
            if (channel != null) {
                // The channel member is only set while the channel is active, so future completion
                // can be deferred until the #channelInactive() method is called.
                channel.close();
            } else {
                // Cancel the connect future if still pending.
                if (!connectFuture.isDone()) {
                    connectFuture.cancel(true);
                }
                closeFuture.complete(null);
            }
        });
        return closeFuture;
    }

    @Override
    public void flush() throws ClosedChannelException {
        final Channel chan = channel();
        chan.flush();
    }

    @Override
    public void reconnect() {
        // Execute on the event loop thread.
        if (connectPending.compareAndSet(false, true)) {
            group.execute(reconnectCommand);
        }
    }

    @Override
    public void reconnect(final long delay, final TimeUnit unit) {
        // Execute on the event loop thread.
        if (connectPending.compareAndSet(false, true)) {
            group.schedule(reconnectCommand, delay, unit);
        }
    }

    @Override
    public void write(final ByteBuf msg) throws ClosedChannelException {
        final Channel chan = channel();
        chan.write(msg, chan.voidPromise());
    }

    @Override
    public void writeAndFlush(final ByteBuf msg) throws ClosedChannelException {
        final Channel chan = channel();
        chan.writeAndFlush(msg, chan.voidPromise());
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        // Event loop thread.
        if (closed) {
            // This might conceivably happen if a connection was pending when the client was closed.
            ctx.close();
            return;
        }
        assert channel == null;
        channel = ctx.channel();
        handler.onBspConnnect(this);
    }

    @Override
    public void channelInactive(final ChannelHandlerContext ctx) {
        // Event loop thread.

        // The channel will be null if the #channelActive() method was called when the client was
        // already closed. In which case, #onDisconnect() should not be called, because
        // #onConnnect() was also not called.
        if (channel != null) {
            channel = null;
            handler.onBspDisconnect(this);
        }
        if (closed) {
            closeFuture.complete(null);
        }
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object obj) {
        // Event loop thread.
        final ByteBuf msg = (ByteBuf) obj;
        if (msg.readableBytes() == 0) {
            // Ignore keep-alives.
            msg.release();
            return;
        }
        handler.onBspMessage(this, msg);
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
        // Event loop thread.
        handler.onBspError(this, cause);
    }

    @Override
    public void userEventTriggered(final ChannelHandlerContext ctx, final Object evt) {
        // Event loop thread.
        if (evt instanceof IdleStateEvent) {
            final IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                readerIdle(ctx);
            } else if (event.state() == IdleState.WRITER_IDLE) {
                writerIdle(ctx);
            }
        }
    }

    private Runnable newReconnectCommand() {
        // Execute on the event loop thread.
        return () -> {
            // Event loop thread.
            if (closed) {
                // Prevent reconnect after close.
                return;
            }
            if (channel != null) {
                // Close the current channel before initiating a new connection.
                channel.close().addListener(future -> {
                    try {
                        connect();
                    } catch (final Throwable cause) {
                        // Ensure that errors on the synchronous part of the operation are handled.
                        connectPending.set(false);
                        handler.onBspError(this, cause);
                    }
                });
            } else {
                try {
                    connect();
                } catch (final Throwable cause) {
                    // Ensure that errors on the synchronous part of the operation are handled.
                    connectPending.set(false);
                    handler.onBspError(this, cause);
                }
            }
        };
    }

    private Channel channel() throws ClosedChannelException {
        final Channel chan = channel;
        if (chan == null || closed) {
            throw new ClosedChannelException();
        }
        return chan;
    }

    private void connect() {
        assert connectPending.get();
        connectFuture = bootstrap.connect(config.host, config.port);
        connectFuture.addListener(future -> {
            // Reset pending flag when the operation completes.
            connectPending.set(false);
            try {
                future.get();
            } catch (final ExecutionException e) {
                handler.onBspError(this, e.getCause());
            }
        });
    }

    private void readerIdle(final ChannelHandlerContext ctx) {
        handler.onBspTimeout(this);
    }

    private void writerIdle(final ChannelHandlerContext ctx) {
        // Send keepalive.
        final ByteBuf msg = new EmptyByteBuf(ctx.alloc());
        ctx.writeAndFlush(msg);
    }
}
