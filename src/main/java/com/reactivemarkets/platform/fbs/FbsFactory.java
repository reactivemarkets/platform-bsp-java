/*
 * Copyright (C) 2020 Reactive Markets Limited
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

package com.reactivemarkets.platform.fbs;

import com.google.flatbuffers.FlatBufferBuilder;
import com.reactivemarkets.encoding.fbs.Asset;
import com.reactivemarkets.encoding.fbs.Body;
import com.reactivemarkets.encoding.fbs.Message;
import com.reactivemarkets.platform.bsp.BspFactory;
import io.netty.buffer.ByteBuf;
import org.agrona.concurrent.HighResolutionClock;

import static com.reactivemarkets.platform.bsp.BspConstants.MAX_MESSAGE_SIZE;

public final class FbsFactory {

    private static final ThreadLocal<FlatBufferBuilder> BUILDER =
        ThreadLocal.withInitial(() -> new FlatBufferBuilder(MAX_MESSAGE_SIZE));

    private FbsFactory() {
    }

    public static FlatBufferBuilder getFbsBuilder() {
        final FlatBufferBuilder builder = BUILDER.get();
        builder.clear();
        return builder;
    }

    public static ByteBuf newFbsAsset(final int id, final String symbol, final String display,
                                      final int assetType) {

        final FlatBufferBuilder builder = getFbsBuilder();

        final int symbolOffset = builder.createString(symbol);
        final int displayOffset = builder.createString(display);
        final int bodyOffset = Asset.createAsset(builder, (short) id, symbolOffset,
            displayOffset, (short) assetType);

        Message.startMessage(builder);
        Message.addTts(builder, HighResolutionClock.epochNanos());
        Message.addBodyType(builder, Body.Asset);
        Message.addBody(builder, bodyOffset);

        final int message = Message.endMessage(builder);
        builder.finish(message);
        return BspFactory.newBspMessage(builder.dataBuffer());
    }
}
