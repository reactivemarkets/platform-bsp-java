// automatically generated by the FlatBuffers compiler, do not modify

package com.reactivemarkets.encoding.fbs;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class FullAmount extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_1_12_0(); }
  public static FullAmount getRootAsFullAmount(ByteBuffer _bb) { return getRootAsFullAmount(_bb, new FullAmount()); }
  public static FullAmount getRootAsFullAmount(ByteBuffer _bb, FullAmount obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public FullAmount __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public String market() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer marketAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public ByteBuffer marketInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 4, 1); }
  public String id() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer idAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public ByteBuffer idInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 6, 1); }
  public String requestId() { int o = __offset(8); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer requestIdAsByteBuffer() { return __vector_as_bytebuffer(8, 1); }
  public ByteBuffer requestIdInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 8, 1); }
  public com.reactivemarkets.encoding.fbs.FAEntry entries(int j) { return entries(new com.reactivemarkets.encoding.fbs.FAEntry(), j); }
  public com.reactivemarkets.encoding.fbs.FAEntry entries(com.reactivemarkets.encoding.fbs.FAEntry obj, int j) { int o = __offset(10); return o != 0 ? obj.__assign(__indirect(__vector(o) + j * 4), bb) : null; }
  public int entriesLength() { int o = __offset(10); return o != 0 ? __vector_len(o) : 0; }
  public com.reactivemarkets.encoding.fbs.FAEntry.Vector entriesVector() { return entriesVector(new com.reactivemarkets.encoding.fbs.FAEntry.Vector()); }
  public com.reactivemarkets.encoding.fbs.FAEntry.Vector entriesVector(com.reactivemarkets.encoding.fbs.FAEntry.Vector obj) { int o = __offset(10); return o != 0 ? obj.__assign(__vector(o), 4, bb) : null; }

  public static int createFullAmount(FlatBufferBuilder builder,
      int marketOffset,
      int idOffset,
      int request_idOffset,
      int entriesOffset) {
    builder.startTable(4);
    FullAmount.addEntries(builder, entriesOffset);
    FullAmount.addRequestId(builder, request_idOffset);
    FullAmount.addId(builder, idOffset);
    FullAmount.addMarket(builder, marketOffset);
    return FullAmount.endFullAmount(builder);
  }

  public static void startFullAmount(FlatBufferBuilder builder) { builder.startTable(4); }
  public static void addMarket(FlatBufferBuilder builder, int marketOffset) { builder.addOffset(0, marketOffset, 0); }
  public static void addId(FlatBufferBuilder builder, int idOffset) { builder.addOffset(1, idOffset, 0); }
  public static void addRequestId(FlatBufferBuilder builder, int requestIdOffset) { builder.addOffset(2, requestIdOffset, 0); }
  public static void addEntries(FlatBufferBuilder builder, int entriesOffset) { builder.addOffset(3, entriesOffset, 0); }
  public static int createEntriesVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startEntriesVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endFullAmount(FlatBufferBuilder builder) {
    int o = builder.endTable();
    builder.required(o, 4);  // market
    return o;
  }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public FullAmount get(int j) { return get(new FullAmount(), j); }
    public FullAmount get(FullAmount obj, int j) {  return obj.__assign(__indirect(__element(j), bb), bb); }
  }
}

