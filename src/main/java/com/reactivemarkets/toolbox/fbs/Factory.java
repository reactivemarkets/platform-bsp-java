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

package com.reactivemarkets.toolbox.fbs;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public final class Factory {
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private Factory() {
    }

    public static ChannelInboundHandler newFbsDecoder() {
        return new MessageToMessageDecoder<ByteBuf>() {
            @Override
            protected void decode(final ChannelHandlerContext ctx, final ByteBuf msg,
                                  final List<Object> out) {
                out.add(msg.toString(CHARSET));
            }
        };
    }

    public static ChannelOutboundHandler newFbsEncoder() {
        return new MessageToMessageEncoder<CharSequence>() {
            @Override
            protected void encode(final ChannelHandlerContext ctx, final CharSequence msg, final List<Object> out) {
                out.add(ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(msg), CHARSET));
            }
        };
    }
}
