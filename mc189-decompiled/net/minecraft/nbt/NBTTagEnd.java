package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagEnd extends NBTBase {
   NBTTagEnd() {
   }

   @Override
   void a(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      var3.a(64L);
   }

   @Override
   void a(DataOutput var1) throws IOException {
   }

   @Override
   public byte a() {
      return 0;
   }

   @Override
   public String toString() {
      return "END";
   }

   @Override
   public NBTBase b() {
      return new NBTTagEnd();
   }
}
