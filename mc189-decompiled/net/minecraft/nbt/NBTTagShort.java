package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagShort extends NBTBase.NBTPrimitive {
   private short b;

   public NBTTagShort() {
   }

   public NBTTagShort(short var1) {
      this.b = var1;
   }

   @Override
   void a(DataOutput var1) throws IOException {
      var1.writeShort(this.b);
   }

   @Override
   void a(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      var3.a(80L);
      this.b = var1.readShort();
   }

   @Override
   public byte a() {
      return 2;
   }

   @Override
   public String toString() {
      return "" + this.b + "s";
   }

   @Override
   public NBTBase b() {
      return new NBTTagShort(this.b);
   }

   @Override
   public boolean equals(Object var1) {
      if (super.equals(var1)) {
         NBTTagShort var2 = (NBTTagShort)var1;
         return this.b == var2.b;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return super.hashCode() ^ this.b;
   }

   @Override
   public long c() {
      return this.b;
   }

   @Override
   public int d() {
      return this.b;
   }

   @Override
   public short e() {
      return this.b;
   }

   @Override
   public byte f() {
      return (byte)(this.b & 255);
   }

   @Override
   public double g() {
      return this.b;
   }

   @Override
   public float h() {
      return this.b;
   }
}
