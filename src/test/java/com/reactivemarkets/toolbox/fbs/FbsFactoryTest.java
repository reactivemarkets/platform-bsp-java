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

import com.reactivemarkets.encoding.fbs.AssetType;
import com.reactivemarkets.encoding.fbs.Message;
import io.netty.buffer.ByteBuf;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public final class FbsFactoryTest {
    private static ByteBuf newFbsAsset() {
        return FbsFactory.newFbsAsset(1, "EUR", "Euro Member Countries, Euro",  AssetType.Ccy);
    }
    @Test
    public void testAsset() {
        final ByteBuf msg = newFbsAsset();
        try {
            assertEquals(1, msg.refCnt());
            assertTrue(msg.readableBytes() > 0);
            final ByteBuffer bb = msg.nioBuffer();
            final Message root = Message.getRootAsMessage(bb);
            final byte type = root.bodyType();
            assertEquals(1, (int) type);
        } finally {
            msg.release();
        }
    }
}
