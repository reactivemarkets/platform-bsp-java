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
public final class MDSnapshotL2 extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_1_12_0(); }
  public static MDSnapshotL2 getRootAsMDSnapshotL2(ByteBuffer _bb) { return getRootAsMDSnapshotL2(_bb, new MDSnapshotL2()); }
  public static MDSnapshotL2 getRootAsMDSnapshotL2(ByteBuffer _bb, MDSnapshotL2 obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public MDSnapshotL2 __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public long sourceTs() { int o = __offset(4); return o != 0 ? bb.getLong(o + bb_pos) : 0L; }
  public String source() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer sourceAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public ByteBuffer sourceInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 6, 1); }
  public String market() { int o = __offset(8); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer marketAsByteBuffer() { return __vector_as_bytebuffer(8, 1); }
  public ByteBuffer marketInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 8, 1); }
  public int feedId() { int o = __offset(10); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public long id() { int o = __offset(12); return o != 0 ? bb.getLong(o + bb_pos) : 0L; }
  public short depth() { int o = __offset(14); return o != 0 ? bb.getShort(o + bb_pos) : 0; }
  public int flags() { int o = __offset(16); return o != 0 ? bb.getShort(o + bb_pos) & 0xFFFF : 0; }
  public com.reactivemarkets.papi.MDLevel2 bidSide(int j) { return bidSide(new com.reactivemarkets.papi.MDLevel2(), j); }
  public com.reactivemarkets.papi.MDLevel2 bidSide(com.reactivemarkets.papi.MDLevel2 obj, int j) { int o = __offset(18); return o != 0 ? obj.__assign(__vector(o) + j * 16, bb) : null; }
  public int bidSideLength() { int o = __offset(18); return o != 0 ? __vector_len(o) : 0; }
  public com.reactivemarkets.papi.MDLevel2.Vector bidSideVector() { return bidSideVector(new com.reactivemarkets.papi.MDLevel2.Vector()); }
  public com.reactivemarkets.papi.MDLevel2.Vector bidSideVector(com.reactivemarkets.papi.MDLevel2.Vector obj) { int o = __offset(18); return o != 0 ? obj.__assign(__vector(o), 16, bb) : null; }
  public com.reactivemarkets.papi.MDLevel2 offerSide(int j) { return offerSide(new com.reactivemarkets.papi.MDLevel2(), j); }
  public com.reactivemarkets.papi.MDLevel2 offerSide(com.reactivemarkets.papi.MDLevel2 obj, int j) { int o = __offset(20); return o != 0 ? obj.__assign(__vector(o) + j * 16, bb) : null; }
  public int offerSideLength() { int o = __offset(20); return o != 0 ? __vector_len(o) : 0; }
  public com.reactivemarkets.papi.MDLevel2.Vector offerSideVector() { return offerSideVector(new com.reactivemarkets.papi.MDLevel2.Vector()); }
  public com.reactivemarkets.papi.MDLevel2.Vector offerSideVector(com.reactivemarkets.papi.MDLevel2.Vector obj) { int o = __offset(20); return o != 0 ? obj.__assign(__vector(o), 16, bb) : null; }

  public static int createMDSnapshotL2(FlatBufferBuilder builder,
      long source_ts,
      int sourceOffset,
      int marketOffset,
      int feed_id,
      long id,
      short depth,
      int flags,
      int bid_sideOffset,
      int offer_sideOffset) {
    builder.startTable(9);
    MDSnapshotL2.addId(builder, id);
    MDSnapshotL2.addSourceTs(builder, source_ts);
    MDSnapshotL2.addOfferSide(builder, offer_sideOffset);
    MDSnapshotL2.addBidSide(builder, bid_sideOffset);
    MDSnapshotL2.addFeedId(builder, feed_id);
    MDSnapshotL2.addMarket(builder, marketOffset);
    MDSnapshotL2.addSource(builder, sourceOffset);
    MDSnapshotL2.addFlags(builder, flags);
    MDSnapshotL2.addDepth(builder, depth);
    return MDSnapshotL2.endMDSnapshotL2(builder);
  }

  public static void startMDSnapshotL2(FlatBufferBuilder builder) { builder.startTable(9); }
  public static void addSourceTs(FlatBufferBuilder builder, long sourceTs) { builder.addLong(0, sourceTs, 0L); }
  public static void addSource(FlatBufferBuilder builder, int sourceOffset) { builder.addOffset(1, sourceOffset, 0); }
  public static void addMarket(FlatBufferBuilder builder, int marketOffset) { builder.addOffset(2, marketOffset, 0); }
  public static void addFeedId(FlatBufferBuilder builder, int feedId) { builder.addInt(3, feedId, 0); }
  public static void addId(FlatBufferBuilder builder, long id) { builder.addLong(4, id, 0L); }
  public static void addDepth(FlatBufferBuilder builder, short depth) { builder.addShort(5, depth, 0); }
  public static void addFlags(FlatBufferBuilder builder, int flags) { builder.addShort(6, (short)flags, (short)0); }
  public static void addBidSide(FlatBufferBuilder builder, int bidSideOffset) { builder.addOffset(7, bidSideOffset, 0); }
  public static void startBidSideVector(FlatBufferBuilder builder, int numElems) { builder.startVector(16, numElems, 8); }
  public static void addOfferSide(FlatBufferBuilder builder, int offerSideOffset) { builder.addOffset(8, offerSideOffset, 0); }
  public static void startOfferSideVector(FlatBufferBuilder builder, int numElems) { builder.startVector(16, numElems, 8); }
  public static int endMDSnapshotL2(FlatBufferBuilder builder) {
    int o = builder.endTable();
    builder.required(o, 8);  // market
    return o;
  }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public MDSnapshotL2 get(int j) { return get(new MDSnapshotL2(), j); }
    public MDSnapshotL2 get(MDSnapshotL2 obj, int j) {  return obj.__assign(__indirect(__element(j), bb), bb); }
  }
}