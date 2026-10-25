package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagString extends NBTBase {
   private String b;

   public NBTTagString() {
      this.b = "";
   }

   public NBTTagString(String var1) {
      this.b = var1;
      if (var1 == null) {
         throw new IllegalArgumentException("Empty string not allowed");
      }
   }

   @Override
   void a(DataOutput var1) throws IOException {
      var1.writeUTF(this.b);
   }

   @Override
   void a(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      var3.a(288L);
      this.b = var1.readUTF();
      var3.a(16 * this.b.length());
   }

   @Override
   public byte a() {
      return 8;
   }

   @Override
   public String toString() {
      return "\"" + this.b.replace("\"", "\\\"") + "\"";
   }

   @Override
   public NBTBase b() {
      return new NBTTagString(this.b);
   }

   @Override
   public boolean c_() {
      return this.b.isEmpty();
   }

   @Override
   public boolean equals(Object var1) {
      if (!super.equals(var1)) {
         return false;
      } else {
         NBTTagString var2 = (NBTTagString)var1;
         return this.b == null && var2.b == null || this.b != null && this.b.equals(var2.b);
      }
   }

   @Override
   public int hashCode() {
      return super.hashCode() ^ this.b.hashCode();
   }

   @Override
   public String a_() {
      return this.b;
   }
}
