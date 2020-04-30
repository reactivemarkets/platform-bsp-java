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
public final class FeedRequest extends Table {
  public static FeedRequest getRootAsFeedRequest(ByteBuffer _bb) { return getRootAsFeedRequest(_bb, new FeedRequest()); }
  public static FeedRequest getRootAsFeedRequest(ByteBuffer _bb, FeedRequest obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; vtable_start = bb_pos - bb.getInt(bb_pos); vtable_size = bb.getShort(vtable_start); }
  public FeedRequest __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public String reqId() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer reqIdAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public ByteBuffer reqIdInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 4, 1); }
  public short subReqType() { int o = __offset(6); return o != 0 ? bb.getShort(o + bb_pos) : 1; }
  public short feedType() { int o = __offset(8); return o != 0 ? bb.getShort(o + bb_pos) : 0; }
  public int depth() { int o = __offset(10); return o != 0 ? bb.get(o + bb_pos) & 0xFF : 0; }
  public int grouping() { int o = __offset(12); return o != 0 ? bb.getShort(o + bb_pos) & 0xFFFF : 0; }
  public int frequency() { int o = __offset(14); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public String markets(int j) { int o = __offset(16); return o != 0 ? __string(__vector(o) + j * 4) : null; }
  public int marketsLength() { int o = __offset(16); return o != 0 ? __vector_len(o) : 0; }

  public static int createFeedRequest(FlatBufferBuilder builder,
      int req_idOffset,
      short sub_req_type,
      short feed_type,
      int depth,
      int grouping,
      int frequency,
      int marketsOffset) {
    builder.startObject(7);
    FeedRequest.addMarkets(builder, marketsOffset);
    FeedRequest.addFrequency(builder, frequency);
    FeedRequest.addReqId(builder, req_idOffset);
    FeedRequest.addGrouping(builder, grouping);
    FeedRequest.addFeedType(builder, feed_type);
    FeedRequest.addSubReqType(builder, sub_req_type);
    FeedRequest.addDepth(builder, depth);
    return FeedRequest.endFeedRequest(builder);
  }

  public static void startFeedRequest(FlatBufferBuilder builder) { builder.startObject(7); }
  public static void addReqId(FlatBufferBuilder builder, int reqIdOffset) { builder.addOffset(0, reqIdOffset, 0); }
  public static void addSubReqType(FlatBufferBuilder builder, short subReqType) { builder.addShort(1, subReqType, 1); }
  public static void addFeedType(FlatBufferBuilder builder, short feedType) { builder.addShort(2, feedType, 0); }
  public static void addDepth(FlatBufferBuilder builder, int depth) { builder.addByte(3, (byte)depth, (byte)0); }
  public static void addGrouping(FlatBufferBuilder builder, int grouping) { builder.addShort(4, (short)grouping, (short)0); }
  public static void addFrequency(FlatBufferBuilder builder, int frequency) { builder.addInt(5, frequency, 0); }
  public static void addMarkets(FlatBufferBuilder builder, int marketsOffset) { builder.addOffset(6, marketsOffset, 0); }
  public static int createMarketsVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startMarketsVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endFeedRequest(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
}
