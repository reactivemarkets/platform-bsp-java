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

package com.reactivemarkets.toolbox.bsp.example;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

import static com.reactivemarkets.toolbox.bsp.Factory.newBspDecoder;
import static com.reactivemarkets.toolbox.bsp.Factory.newBspEncoder;
import static com.reactivemarkets.toolbox.fbs.Factory.newFbsDecoder;
import static com.reactivemarkets.toolbox.fbs.Factory.newFbsEncoder;

public final class Ping {

    private static final String HOST = "localhost";
    private static final int PORT = 8280;

    private Ping() {
    }

    private static Bootstrap newClientBootstrap(final EventLoopGroup group,
                                                final ChannelInboundHandler handler) {
        final Bootstrap b = new Bootstrap();
        b.group(group);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.TCP_NODELAY, true);
        b.handler(new LoggingHandler(LogLevel.INFO));
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(final SocketChannel ch) throws Exception {
                final ChannelPipeline p = ch.pipeline();
                p.addLast("ping", new IdleStateHandler(6, 5, 0, TimeUnit.SECONDS));
                p.addLast("bspDecoder", newBspDecoder());
                p.addLast("bspEncoder", newBspEncoder());
                p.addLast("fbsDecoder", newFbsDecoder());
                p.addLast("fbsEncoder", newFbsEncoder());
                p.addLast("handler", handler);
            }
        });
        return b;
    }

    public static void main(final String[] args) throws Exception {
        final EventLoopGroup group = new NioEventLoopGroup();
        try {
            final Bootstrap b = newClientBootstrap(group, new PingHandler());
            final Channel channel = b.connect(HOST, PORT).sync().channel();
            try {
                for (int i = 0; i < 10; ++i) {
                    channel.writeAndFlush("hello");
                }
                while (channel.isActive()) {
                    Thread.sleep(1000);
                }
            } finally {
                channel.closeFuture().sync();
            }
        } finally {
            group.shutdownGracefully();
        }
    }
}
