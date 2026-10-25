package net.minecraft.item;

import java.util.List;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemMonsterPlacer extends Item {
   public ItemMonsterPlacer() {
      this.a(true);
      this.a(CreativeTabs.f);
   }

   @Override
   public String a(ItemStack var1) {
      String var2 = ("" + StatCollector.a(this.a() + ".name")).trim();
      String var3 = EntityList.b(var1.i());
      if (var3 != null) {
         var2 = var2 + " " + StatCollector.a("entity." + var3 + ".name");
      }

      return var2;
   }

   @Override
   public int a(ItemStack var1, int var2) {
      EntityList.EntityEggInfo var3 = EntityList.a.get(var1.i());
      if (var3 != null) {
         return var2 == 0 ? var3.b : var3.c;
      } else {
         return 16777215;
      }
   }

   @Override
   public boolean a(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var3.D) {
         return true;
      } else if (!var2.a(var4.a(var5), var5, var1)) {
         return false;
      } else {
         IBlockState var9 = var3.p(var4);
         if (var9.c() == Blocks.ac) {
            TileEntity var10 = var3.s(var4);
            if (var10 instanceof TileEntityMobSpawner) {
               MobSpawnerBaseLogic var11 = ((TileEntityMobSpawner)var10).b();
               var11.a(EntityList.b(var1.i()));
               var10.p_();
               var3.h(var4);
               if (!var2.bA.d) {
                  var1.b--;
               }

               return true;
            }
         }

         var4 = var4.a(var5);
         double var14 = 0.0;
         if (var5 == EnumFacing.b && var9 instanceof BlockFence) {
            var14 = 0.5;
         }

         Entity var12 = a(var3, var1.i(), var4.n() + 0.5, var4.o() + var14, var4.p() + 0.5);
         if (var12 != null) {
            if (var12 instanceof EntityLivingBase && var1.s()) {
               var12.a(var1.q());
            }

            if (!var2.bA.d) {
               var1.b--;
            }
         }

         return true;
      }
   }

   @Override
   public ItemStack a(ItemStack var1, World var2, EntityPlayer var3) {
      if (var2.D) {
         return var1;
      } else {
         MovingObjectPosition var4 = this.a(var2, var3, true);
         if (var4 == null) {
            return var1;
         } else {
            if (var4.a == MovingObjectPosition.MovingObjectType.b) {
               BlockPos var5 = var4.a();
               if (!var2.a(var3, var5)) {
                  return var1;
               }

               if (!var3.a(var5, var4.b, var1)) {
                  return var1;
               }

               if (var2.p(var5).c() instanceof BlockLiquid) {
                  Entity var6 = a(var2, var1.i(), var5.n() + 0.5, var5.o() + 0.5, var5.p() + 0.5);
                  if (var6 != null) {
                     if (var6 instanceof EntityLivingBase && var1.s()) {
                        ((EntityLiving)var6).a(var1.q());
                     }

                     if (!var3.bA.d) {
                        var1.b--;
                     }

                     var3.b(StatList.ad[Item.b(this)]);
                  }
               }
            }

            return var1;
         }
      }
   }

   public static Entity a(World var0, int var1, double var2, double var4, double var6) {
      if (!EntityList.a.containsKey(var1)) {
         return null;
      } else {
         Entity var8 = null;

         for (int var9 = 0; var9 < 1; var9++) {
            var8 = EntityList.a(var1, var0);
            if (var8 instanceof EntityLivingBase) {
               EntityLiving var10 = (EntityLiving)var8;
               var8.b(var2, var4, var6, MathHelper.g(var0.s.nextFloat() * 360.0F), 0.0F);
               var10.aK = var10.y;
               var10.aI = var10.y;
               var10.a(var0.E(new BlockPos(var10)), null);
               var0.d(var8);
               var10.x();
            }
         }

         return var8;
      }
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      for (EntityList.EntityEggInfo var5 : EntityList.a.values()) {
         var3.add(new ItemStack(var1, 1, var5.a));
      }
   }
}
