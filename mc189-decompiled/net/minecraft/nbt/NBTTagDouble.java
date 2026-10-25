package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.util.MathHelper;

public class NBTTagDouble extends NBTBase.NBTPrimitive {
   private double b;

   NBTTagDouble() {
   }

   public NBTTagDouble(double var1) {
      this.b = var1;
   }

   @Override
   void a(DataOutput var1) throws IOException {
      var1.writeDouble(this.b);
   }

   @Override
   void a(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      var3.a(128L);
      this.b = var1.readDouble();
   }

   @Override
   public byte a() {
      return 6;
   }

   @Override
   public String toString() {
      return "" + this.b + "d";
   }

   @Override
   public NBTBase b() {
      return new NBTTagDouble(this.b);
   }

   @Override
   public boolean equals(Object var1) {
      if (super.equals(var1)) {
         NBTTagDouble var2 = (NBTTagDouble)var1;
         return this.b == var2.b;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      long var1 = Double.doubleToLongBits(this.b);
      return super.hashCode() ^ (int)(var1 ^ var1 >>> 32);
   }

   @Override
   public long c() {
      return (long)Math.floor(this.b);
   }

   @Override
   public int d() {
      return MathHelper.c(this.b);
   }

   @Override
   public short e() {
      return (short)(MathHelper.c(this.b) & 65535);
   }

   @Override
   public byte f() {
      return (byte)(MathHelper.c(this.b) & 0xFF);
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
