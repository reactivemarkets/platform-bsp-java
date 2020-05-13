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

package com.reactivemarkets.papi;

import java.nio.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class FeedRequestAccept extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_1_12_0(); }
  public static FeedRequestAccept getRootAsFeedRequestAccept(ByteBuffer _bb) { return getRootAsFeedRequestAccept(_bb, new FeedRequestAccept()); }
  public static FeedRequestAccept getRootAsFeedRequestAccept(ByteBuffer _bb, FeedRequestAccept obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public FeedRequestAccept __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public String reqId() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer reqIdAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public ByteBuffer reqIdInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 4, 1); }
  public int feedId() { int o = __offset(6); return o != 0 ? bb.getInt(o + bb_pos) : 0; }

  public static int createFeedRequestAccept(FlatBufferBuilder builder,
      int req_idOffset,
      int feed_id) {
    builder.startTable(2);
    FeedRequestAccept.addFeedId(builder, feed_id);
    FeedRequestAccept.addReqId(builder, req_idOffset);
    return FeedRequestAccept.endFeedRequestAccept(builder);
  }

  public static void startFeedRequestAccept(FlatBufferBuilder builder) { builder.startTable(2); }
  public static void addReqId(FlatBufferBuilder builder, int reqIdOffset) { builder.addOffset(0, reqIdOffset, 0); }
  public static void addFeedId(FlatBufferBuilder builder, int feedId) { builder.addInt(1, feedId, 0); }
  public static int endFeedRequestAccept(FlatBufferBuilder builder) {
    int o = builder.endTable();
    return o;
  }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public FeedRequestAccept get(int j) { return get(new FeedRequestAccept(), j); }
    public FeedRequestAccept get(FeedRequestAccept obj, int j) {  return obj.__assign(__indirect(__element(j), bb), bb); }
  }
}
