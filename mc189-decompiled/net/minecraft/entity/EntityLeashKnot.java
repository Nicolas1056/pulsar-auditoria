package net.minecraft.entity;

import net.minecraft.block.BlockFence;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class EntityLeashKnot extends EntityHanging {
   public EntityLeashKnot(World var1) {
      super(var1);
   }

   public EntityLeashKnot(World var1, BlockPos var2) {
      super(var1, var2);
      this.b(var2.n() + 0.5, var2.o() + 0.5, var2.p() + 0.5);
      float var3 = 0.125F;
      float var4 = 0.1875F;
      float var5 = 0.25F;
      this.a(new AxisAlignedBB(this.s - 0.1875, this.t - 0.25 + 0.125, this.u - 0.1875, this.s + 0.1875, this.t + 0.25 + 0.125, this.u + 0.1875));
   }

   @Override
   protected void h() {
      super.h();
   }

   @Override
   public void a(EnumFacing var1) {
   }

   @Override
   public int l() {
      return 9;
   }

   @Override
   public int m() {
      return 9;
   }

   @Override
   public float aS() {
      return -0.0625F;
   }

   @Override
   public boolean a(double var1) {
      return var1 < 1024.0;
   }

   @Override
   public void b(Entity var1) {
   }

   @Override
   public boolean d(NBTTagCompound var1) {
      return false;
   }

   @Override
   public void b(NBTTagCompound var1) {
   }

   @Override
   public void a(NBTTagCompound var1) {
   }

   @Override
   public boolean e(EntityPlayer var1) {
      ItemStack var2 = var1.bA();
      boolean var3 = false;
      if (var2 != null && var2.b() == Items.cn && !this.o.D) {
         double var4 = 7.0;

         for (EntityLiving var8 : this.o
            .a(EntityLiving.class, new AxisAlignedBB(this.s - var4, this.t - var4, this.u - var4, this.s + var4, this.t + var4, this.u + var4))) {
            if (var8.cc() && var8.cd() == var1) {
               var8.a(this, true);
               var3 = true;
            }
         }
      }

      if (!this.o.D && !var3) {
         this.J();
         if (var1.bA.d) {
            double var9 = 7.0;

            for (EntityLiving var12 : this.o
               .a(EntityLiving.class, new AxisAlignedBB(this.s - var9, this.t - var9, this.u - var9, this.s + var9, this.t + var9, this.u + var9))) {
               if (var12.cc() && var12.cd() == this) {
                  var12.a(true, false);
               }
            }
         }
      }

      return true;
   }

   @Override
   public boolean j() {
      return this.o.p(this.a).c() instanceof BlockFence;
   }

   public static EntityLeashKnot a(World var0, BlockPos var1) {
      EntityLeashKnot var2 = new EntityLeashKnot(var0, var1);
      var2.n = true;
      var0.d(var2);
      return var2;
   }

   public static EntityLeashKnot b(World var0, BlockPos var1) {
      int var2 = var1.n();
      int var3 = var1.o();
      int var4 = var1.p();

      for (EntityLeashKnot var7 : var0.a(EntityLeashKnot.class, new AxisAlignedBB(var2 - 1.0, var3 - 1.0, var4 - 1.0, var2 + 1.0, var3 + 1.0, var4 + 1.0))) {
         if (var7.n().equals(var1)) {
            return var7;
         }
      }

      return null;
   }
}
