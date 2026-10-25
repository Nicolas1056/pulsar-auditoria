package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagInt extends NBTBase.NBTPrimitive {
   private int b;

   NBTTagInt() {
   }

   public NBTTagInt(int var1) {
      this.b = var1;
   }

   @Override
   void a(DataOutput var1) throws IOException {
      var1.writeInt(this.b);
   }

   @Override
   void a(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      var3.a(96L);
      this.b = var1.readInt();
   }

   @Override
   public byte a() {
      return 3;
   }

   @Override
   public String toString() {
      return "" + this.b;
   }

   @Override
   public NBTBase b() {
      return new NBTTagInt(this.b);
   }

   @Override
   public boolean equals(Object var1) {
      if (super.equals(var1)) {
         NBTTagInt var2 = (NBTTagInt)var1;
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
      return (short)(this.b & 65535);
   }

   @Override
   public byte f() {
      return (byte)(this.b & 0xFF);
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
