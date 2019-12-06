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

import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.nio.ByteOrder;

import static com.reactivemarkets.toolbox.bsp.Constants.INITIAL_BYTES_TO_STRIP;
import static com.reactivemarkets.toolbox.bsp.Constants.LENGTH_ADJUSTMENT;
import static com.reactivemarkets.toolbox.bsp.Constants.LENGTH_FIELD_LENGTH;
import static com.reactivemarkets.toolbox.bsp.Constants.LENGTH_FIELD_OFFSET;
import static com.reactivemarkets.toolbox.bsp.Constants.MAX_FRAME_LENGTH;

public final class Factory {
    private Factory() {
    }

    public static ChannelInboundHandler newBspDecoder() {
        return new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN, MAX_FRAME_LENGTH,
                LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH, LENGTH_ADJUSTMENT,
                INITIAL_BYTES_TO_STRIP, true);
    }

    public static ChannelOutboundHandler newBspEncoder() {
        return new LengthFieldPrepender(ByteOrder.LITTLE_ENDIAN, LENGTH_FIELD_LENGTH, 0, false);
    }
}
