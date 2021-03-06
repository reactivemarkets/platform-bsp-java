// automatically generated by the FlatBuffers compiler, do not modify

package com.reactivemarkets.encoding.fbs;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class Posn extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_1_12_0(); }
  public static Posn getRootAsPosn(ByteBuffer _bb) { return getRootAsPosn(_bb, new Posn()); }
  public static Posn getRootAsPosn(ByteBuffer _bb, Posn obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public Posn __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public String accnt() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer accntAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public ByteBuffer accntInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 4, 1); }
  public String market() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer marketAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public ByteBuffer marketInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 6, 1); }
  public int settlDate() { int o = __offset(8); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public double buyQty() { int o = __offset(10); return o != 0 ? bb.getDouble(o + bb_pos) : 0.0; }
  public double buyCost() { int o = __offset(12); return o != 0 ? bb.getDouble(o + bb_pos) : 0.0; }
  public double sellQty() { int o = __offset(14); return o != 0 ? bb.getDouble(o + bb_pos) : 0.0; }
  public double sellCost() { int o = __offset(16); return o != 0 ? bb.getDouble(o + bb_pos) : 0.0; }

  public static int createPosn(FlatBufferBuilder builder,
      int accntOffset,
      int marketOffset,
      int settl_date,
      double buy_qty,
      double buy_cost,
      double sell_qty,
      double sell_cost) {
    builder.startTable(7);
    Posn.addSellCost(builder, sell_cost);
    Posn.addSellQty(builder, sell_qty);
    Posn.addBuyCost(builder, buy_cost);
    Posn.addBuyQty(builder, buy_qty);
    Posn.addSettlDate(builder, settl_date);
    Posn.addMarket(builder, marketOffset);
    Posn.addAccnt(builder, accntOffset);
    return Posn.endPosn(builder);
  }

  public static void startPosn(FlatBufferBuilder builder) { builder.startTable(7); }
  public static void addAccnt(FlatBufferBuilder builder, int accntOffset) { builder.addOffset(0, accntOffset, 0); }
  public static void addMarket(FlatBufferBuilder builder, int marketOffset) { builder.addOffset(1, marketOffset, 0); }
  public static void addSettlDate(FlatBufferBuilder builder, int settlDate) { builder.addInt(2, settlDate, 0); }
  public static void addBuyQty(FlatBufferBuilder builder, double buyQty) { builder.addDouble(3, buyQty, 0.0); }
  public static void addBuyCost(FlatBufferBuilder builder, double buyCost) { builder.addDouble(4, buyCost, 0.0); }
  public static void addSellQty(FlatBufferBuilder builder, double sellQty) { builder.addDouble(5, sellQty, 0.0); }
  public static void addSellCost(FlatBufferBuilder builder, double sellCost) { builder.addDouble(6, sellCost, 0.0); }
  public static int endPosn(FlatBufferBuilder builder) {
    int o = builder.endTable();
    builder.required(o, 4);  // accnt
    builder.required(o, 6);  // market
    return o;
  }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public Posn get(int j) { return get(new Posn(), j); }
    public Posn get(Posn obj, int j) {  return obj.__assign(__indirect(__element(j), bb), bb); }
  }
}

