package net.minecraft.world;

import net.minecraft.nbt.NBTTagCompound;

public abstract class WorldSavedData {
   public final String a;
   private boolean b;

   public WorldSavedData(String var1) {
      this.a = var1;
   }

   public abstract void a(NBTTagCompound var1);

   public abstract void b(NBTTagCompound var1);

   public void c() {
      this.a(true);
   }

   public void a(boolean var1) {
      this.b = var1;
   }

   public boolean d() {
      return this.b;
   }
}
