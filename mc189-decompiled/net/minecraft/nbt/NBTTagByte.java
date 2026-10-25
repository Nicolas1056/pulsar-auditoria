package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagByte extends NBTBase.NBTPrimitive {
   private byte b;

   NBTTagByte() {
   }

   public NBTTagByte(byte var1) {
      this.b = var1;
   }

   @Override
   void a(DataOutput var1) throws IOException {
      var1.writeByte(this.b);
   }

   @Override
   void a(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      var3.a(72L);
      this.b = var1.readByte();
   }

   @Override
   public byte a() {
      return 1;
   }

   @Override
   public String toString() {
      return "" + this.b + "b";
   }

   @Override
   public NBTBase b() {
      return new NBTTagByte(this.b);
   }

   @Override
   public boolean equals(Object var1) {
      if (super.equals(var1)) {
         NBTTagByte var2 = (NBTTagByte)var1;
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
      return this.b;
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
