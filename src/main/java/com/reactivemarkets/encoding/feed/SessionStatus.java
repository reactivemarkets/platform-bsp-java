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
public final class SessionStatus extends Table {
  public static SessionStatus getRootAsSessionStatus(ByteBuffer _bb) { return getRootAsSessionStatus(_bb, new SessionStatus()); }
  public static SessionStatus getRootAsSessionStatus(ByteBuffer _bb, SessionStatus obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; vtable_start = bb_pos - bb.getInt(bb_pos); vtable_size = bb.getShort(vtable_start); }
  public SessionStatus __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public long sourceTs() { int o = __offset(4); return o != 0 ? bb.getLong(o + bb_pos) : 0L; }
  public String source() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer sourceAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public ByteBuffer sourceInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 6, 1); }
  public int code() { int o = __offset(8); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public String message() { int o = __offset(10); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer messageAsByteBuffer() { return __vector_as_bytebuffer(10, 1); }
  public ByteBuffer messageInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 10, 1); }

  public static int createSessionStatus(FlatBufferBuilder builder,
      long source_ts,
      int sourceOffset,
      int code,
      int messageOffset) {
    builder.startObject(4);
    SessionStatus.addSourceTs(builder, source_ts);
    SessionStatus.addMessage(builder, messageOffset);
    SessionStatus.addCode(builder, code);
    SessionStatus.addSource(builder, sourceOffset);
    return SessionStatus.endSessionStatus(builder);
  }

  public static void startSessionStatus(FlatBufferBuilder builder) { builder.startObject(4); }
  public static void addSourceTs(FlatBufferBuilder builder, long sourceTs) { builder.addLong(0, sourceTs, 0L); }
  public static void addSource(FlatBufferBuilder builder, int sourceOffset) { builder.addOffset(1, sourceOffset, 0); }
  public static void addCode(FlatBufferBuilder builder, int code) { builder.addInt(2, code, 0); }
  public static void addMessage(FlatBufferBuilder builder, int messageOffset) { builder.addOffset(3, messageOffset, 0); }
  public static int endSessionStatus(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
}
