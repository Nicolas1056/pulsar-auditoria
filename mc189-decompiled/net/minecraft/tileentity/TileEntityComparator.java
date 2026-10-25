package net.minecraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityComparator extends TileEntity {
   private int a;

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("OutputSignal", this.a);
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.a = var1.f("OutputSignal");
   }

   public int b() {
      return this.a;
   }

   public void a(int var1) {
      this.a = var1;
   }
}
