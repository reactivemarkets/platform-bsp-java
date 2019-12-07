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

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.nio.ByteOrder;
import java.util.List;

import static com.reactivemarkets.toolbox.bsp.BspConstants.INITIAL_BYTES_TO_STRIP;
import static com.reactivemarkets.toolbox.bsp.BspConstants.LENGTH_ADJUSTMENT;
import static com.reactivemarkets.toolbox.bsp.BspConstants.LENGTH_FIELD_LENGTH;
import static com.reactivemarkets.toolbox.bsp.BspConstants.LENGTH_FIELD_OFFSET;
import static com.reactivemarkets.toolbox.bsp.BspConstants.MAX_FRAME_LENGTH;

public final class BspFactory {
    private BspFactory() {
    }

    public static EventLoopGroup newBspEventLoopGroup() {
        // Single-threaded event group.
        return new NioEventLoopGroup(1);
    }

    public static BspClient newBspClient(final EventLoopGroup group, final BspHandler handler,
                                         final BspConfig config) {
        return new BspClientImpl(group, handler, config);
    }

    public static ChannelInboundHandler newBspDecoder() {
        return new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN, MAX_FRAME_LENGTH,
                LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH, LENGTH_ADJUSTMENT,
                INITIAL_BYTES_TO_STRIP, true);
    }

    public static ChannelOutboundHandler newBspEncoder() {
        return new MessageToMessageEncoder<ByteBuf>() {
            @Override
            protected void encode(final ChannelHandlerContext ctx, final ByteBuf msg, final List<Object> out) {
                final int length = msg.readableBytes();
                out.add(ctx.alloc().buffer(4).writeIntLE(length));
                out.add(msg.retain());
            }
        };
    }
}
