package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagLong extends NBTBase.NBTPrimitive {
   private long b;

   NBTTagLong() {
   }

   public NBTTagLong(long var1) {
      this.b = var1;
   }

   @Override
   void a(DataOutput var1) throws IOException {
      var1.writeLong(this.b);
   }

   @Override
   void a(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      var3.a(128L);
      this.b = var1.readLong();
   }

   @Override
   public byte a() {
      return 4;
   }

   @Override
   public String toString() {
      return "" + this.b + "L";
   }

   @Override
   public NBTBase b() {
      return new NBTTagLong(this.b);
   }

   @Override
   public boolean equals(Object var1) {
      if (super.equals(var1)) {
         NBTTagLong var2 = (NBTTagLong)var1;
         return this.b == var2.b;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return super.hashCode() ^ (int)(this.b ^ this.b >>> 32);
   }

   @Override
   public long c() {
      return this.b;
   }

   @Override
   public int d() {
      return (int)(this.b & -1L);
   }

   @Override
   public short e() {
      return (short)(this.b & 65535L);
   }

   @Override
   public byte f() {
      return (byte)(this.b & 255L);
   }

   @Override
   public double g() {
      return this.b;
   }

   @Override
   public float h() {
      return (float)this.b;
   }
}
