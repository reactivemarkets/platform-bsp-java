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
  public short feed() { int o = __offset(8); return o != 0 ? bb.getShort(o + bb_pos) : 0; }
  public int grouping() { int o = __offset(10); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public int conflation() { int o = __offset(12); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public short depth() { int o = __offset(14); return o != 0 ? bb.getShort(o + bb_pos) : 0; }
  public String markets(int j) { int o = __offset(16); return o != 0 ? __string(__vector(o) + j * 4) : null; }
  public int marketsLength() { int o = __offset(16); return o != 0 ? __vector_len(o) : 0; }

  public static int createFeedRequest(FlatBufferBuilder builder,
      int req_idOffset,
      short sub_req_type,
      short feed,
      int grouping,
      int conflation,
      short depth,
      int marketsOffset) {
    builder.startObject(7);
    FeedRequest.addMarkets(builder, marketsOffset);
    FeedRequest.addConflation(builder, conflation);
    FeedRequest.addGrouping(builder, grouping);
    FeedRequest.addReqId(builder, req_idOffset);
    FeedRequest.addDepth(builder, depth);
    FeedRequest.addFeed(builder, feed);
    FeedRequest.addSubReqType(builder, sub_req_type);
    return FeedRequest.endFeedRequest(builder);
  }

  public static void startFeedRequest(FlatBufferBuilder builder) { builder.startObject(7); }
  public static void addReqId(FlatBufferBuilder builder, int reqIdOffset) { builder.addOffset(0, reqIdOffset, 0); }
  public static void addSubReqType(FlatBufferBuilder builder, short subReqType) { builder.addShort(1, subReqType, 1); }
  public static void addFeed(FlatBufferBuilder builder, short feed) { builder.addShort(2, feed, 0); }
  public static void addGrouping(FlatBufferBuilder builder, int grouping) { builder.addInt(3, grouping, 0); }
  public static void addConflation(FlatBufferBuilder builder, int conflation) { builder.addInt(4, conflation, 0); }
  public static void addDepth(FlatBufferBuilder builder, short depth) { builder.addShort(5, depth, 0); }
  public static void addMarkets(FlatBufferBuilder builder, int marketsOffset) { builder.addOffset(6, marketsOffset, 0); }
  public static int createMarketsVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startMarketsVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endFeedRequest(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
}
