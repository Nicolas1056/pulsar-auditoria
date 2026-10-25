package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagIntArray extends NBTBase {
   private int[] b;

   NBTTagIntArray() {
   }

   public NBTTagIntArray(int[] var1) {
      this.b = var1;
   }

   @Override
   void a(DataOutput var1) throws IOException {
      var1.writeInt(this.b.length);

      for (int var2 = 0; var2 < this.b.length; var2++) {
         var1.writeInt(this.b[var2]);
      }
   }

   @Override
   void a(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      var3.a(192L);
      int var4 = var1.readInt();
      var3.a(32 * var4);
      this.b = new int[var4];

      for (int var5 = 0; var5 < var4; var5++) {
         this.b[var5] = var1.readInt();
      }
   }

   @Override
   public byte a() {
      return 11;
   }

   @Override
   public String toString() {
      String var1 = "[";

      for (int var5 : this.b) {
         var1 = var1 + var5 + ",";
      }

      return var1 + "]";
   }

   @Override
   public NBTBase b() {
      int[] var1 = new int[this.b.length];
      System.arraycopy(this.b, 0, var1, 0, this.b.length);
      return new NBTTagIntArray(var1);
   }

   @Override
   public boolean equals(Object var1) {
      return super.equals(var1) ? Arrays.equals(this.b, ((NBTTagIntArray)var1).b) : false;
   }

   @Override
   public int hashCode() {
      return super.hashCode() ^ Arrays.hashCode(this.b);
   }

   public int[] c() {
      return this.b;
   }
}
