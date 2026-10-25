package net.minecraft.world;

import net.minecraft.nbt.NBTTagCompound;

public class LockCode {
   public static final LockCode a = new LockCode("");
   private final String b;

   public LockCode(String var1) {
      this.b = var1;
   }

   public boolean a() {
      return this.b == null || this.b.isEmpty();
   }

   public String b() {
      return this.b;
   }

   public void a(NBTTagCompound var1) {
      var1.a("Lock", this.b);
   }

   public static LockCode b(NBTTagCompound var0) {
      if (var0.b("Lock", 8)) {
         String var1 = var0.j("Lock");
         return new LockCode(var1);
      } else {
         return a;
      }
   }
}
