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

package com.reactivemarkets.encoding.feed;

import java.nio.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class FeedRequestAck extends Table {
  public static FeedRequestAck getRootAsFeedRequestAck(ByteBuffer _bb) { return getRootAsFeedRequestAck(_bb, new FeedRequestAck()); }
  public static FeedRequestAck getRootAsFeedRequestAck(ByteBuffer _bb, FeedRequestAck obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; vtable_start = bb_pos - bb.getInt(bb_pos); vtable_size = bb.getShort(vtable_start); }
  public FeedRequestAck __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public String reqId() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer reqIdAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public ByteBuffer reqIdInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 4, 1); }
  public int feedId() { int o = __offset(6); return o != 0 ? bb.getInt(o + bb_pos) : 0; }

  public static int createFeedRequestAck(FlatBufferBuilder builder,
      int req_idOffset,
      int feed_id) {
    builder.startObject(2);
    FeedRequestAck.addFeedId(builder, feed_id);
    FeedRequestAck.addReqId(builder, req_idOffset);
    return FeedRequestAck.endFeedRequestAck(builder);
  }

  public static void startFeedRequestAck(FlatBufferBuilder builder) { builder.startObject(2); }
  public static void addReqId(FlatBufferBuilder builder, int reqIdOffset) { builder.addOffset(0, reqIdOffset, 0); }
  public static void addFeedId(FlatBufferBuilder builder, int feedId) { builder.addInt(1, feedId, 0); }
  public static int endFeedRequestAck(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
}
