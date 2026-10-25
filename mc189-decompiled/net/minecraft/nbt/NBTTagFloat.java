package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.util.MathHelper;

public class NBTTagFloat extends NBTBase.NBTPrimitive {
   private float b;

   NBTTagFloat() {
   }

   public NBTTagFloat(float var1) {
      this.b = var1;
   }

   @Override
   void a(DataOutput var1) throws IOException {
      var1.writeFloat(this.b);
   }

   @Override
   void a(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      var3.a(96L);
      this.b = var1.readFloat();
   }

   @Override
   public byte a() {
      return 5;
   }

   @Override
   public String toString() {
      return "" + this.b + "f";
   }

   @Override
   public NBTBase b() {
      return new NBTTagFloat(this.b);
   }

   @Override
   public boolean equals(Object var1) {
      if (super.equals(var1)) {
         NBTTagFloat var2 = (NBTTagFloat)var1;
         return this.b == var2.b;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return super.hashCode() ^ Float.floatToIntBits(this.b);
   }

   @Override
   public long c() {
      return (long)this.b;
   }

   @Override
   public int d() {
      return MathHelper.d(this.b);
   }

   @Override
   public short e() {
      return (short)(MathHelper.d(this.b) & 65535);
   }

   @Override
   public byte f() {
      return (byte)(MathHelper.d(this.b) & 0xFF);
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
