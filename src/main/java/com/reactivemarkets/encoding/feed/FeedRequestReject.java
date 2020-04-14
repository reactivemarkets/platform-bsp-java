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
public final class FeedRequestReject extends Table {
  public static FeedRequestReject getRootAsFeedRequestReject(ByteBuffer _bb) { return getRootAsFeedRequestReject(_bb, new FeedRequestReject()); }
  public static FeedRequestReject getRootAsFeedRequestReject(ByteBuffer _bb, FeedRequestReject obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; vtable_start = bb_pos - bb.getInt(bb_pos); vtable_size = bb.getShort(vtable_start); }
  public FeedRequestReject __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public String reqId() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer reqIdAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public ByteBuffer reqIdInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 4, 1); }
  public int errorCode() { int o = __offset(6); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public String errorMessage() { int o = __offset(8); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer errorMessageAsByteBuffer() { return __vector_as_bytebuffer(8, 1); }
  public ByteBuffer errorMessageInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 8, 1); }

  public static int createFeedRequestReject(FlatBufferBuilder builder,
      int req_idOffset,
      int error_code,
      int error_messageOffset) {
    builder.startObject(3);
    FeedRequestReject.addErrorMessage(builder, error_messageOffset);
    FeedRequestReject.addErrorCode(builder, error_code);
    FeedRequestReject.addReqId(builder, req_idOffset);
    return FeedRequestReject.endFeedRequestReject(builder);
  }

  public static void startFeedRequestReject(FlatBufferBuilder builder) { builder.startObject(3); }
  public static void addReqId(FlatBufferBuilder builder, int reqIdOffset) { builder.addOffset(0, reqIdOffset, 0); }
  public static void addErrorCode(FlatBufferBuilder builder, int errorCode) { builder.addInt(1, errorCode, 0); }
  public static void addErrorMessage(FlatBufferBuilder builder, int errorMessageOffset) { builder.addOffset(2, errorMessageOffset, 0); }
  public static int endFeedRequestReject(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
}
