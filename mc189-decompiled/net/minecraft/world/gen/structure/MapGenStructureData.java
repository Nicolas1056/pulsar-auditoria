package net.minecraft.world.gen.structure;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

public class MapGenStructureData extends WorldSavedData {
   private NBTTagCompound b = new NBTTagCompound();

   public MapGenStructureData(String var1) {
      super(var1);
   }

   @Override
   public void a(NBTTagCompound var1) {
      this.b = var1.m("Features");
   }

   @Override
   public void b(NBTTagCompound var1) {
      var1.a("Features", this.b);
   }

   public void a(NBTTagCompound var1, int var2, int var3) {
      this.b.a(b(var2, var3), var1);
   }

   public static String b(int var0, int var1) {
      return "[" + var0 + "," + var1 + "]";
   }

   public NBTTagCompound a() {
      return this.b;
   }
}
