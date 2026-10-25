package net.minecraft.tileentity;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TileEntityNote extends TileEntity {
   public byte a;
   public boolean f;

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("note", this.a);
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.a = var1.d("note");
      this.a = (byte)MathHelper.a(this.a, 0, 24);
   }

   public void b() {
      this.a = (byte)((this.a + 1) % 25);
      this.p_();
   }

   public void a(World var1, BlockPos var2) {
      if (var1.p(var2.a()).c().t() == Material.a) {
         Material var3 = var1.p(var2.b()).c().t();
         byte var4 = 0;
         if (var3 == Material.e) {
            var4 = 1;
         }

         if (var3 == Material.p) {
            var4 = 2;
         }

         if (var3 == Material.s) {
            var4 = 3;
         }

         if (var3 == Material.d) {
            var4 = 4;
         }

         var1.c(var2, Blocks.B, var4, this.a);
      }
   }
}
