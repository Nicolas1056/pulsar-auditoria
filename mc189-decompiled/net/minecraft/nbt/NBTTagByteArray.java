package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagByteArray extends NBTBase {
   private byte[] b;

   NBTTagByteArray() {
   }

   public NBTTagByteArray(byte[] var1) {
      this.b = var1;
   }

   @Override
   void a(DataOutput var1) throws IOException {
      var1.writeInt(this.b.length);
      var1.write(this.b);
   }

   @Override
   void a(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      var3.a(192L);
      int var4 = var1.readInt();
      var3.a(8 * var4);
      this.b = new byte[var4];
      var1.readFully(this.b);
   }

   @Override
   public byte a() {
      return 7;
   }

   @Override
   public String toString() {
      return "[" + this.b.length + " bytes]";
   }

   @Override
   public NBTBase b() {
      byte[] var1 = new byte[this.b.length];
      System.arraycopy(this.b, 0, var1, 0, this.b.length);
      return new NBTTagByteArray(var1);
   }

   @Override
   public boolean equals(Object var1) {
      return super.equals(var1) ? Arrays.equals(this.b, ((NBTTagByteArray)var1).b) : false;
   }

   @Override
   public int hashCode() {
      return super.hashCode() ^ Arrays.hashCode(this.b);
   }

   public byte[] c() {
      return this.b;
   }
}
