package net.minecraft.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemMinecart extends Item {
   private static final IBehaviorDispenseItem a = new BehaviorDefaultDispenseItem() {
      private final BehaviorDefaultDispenseItem b = new BehaviorDefaultDispenseItem();

      @Override
      public ItemStack b(IBlockSource var1, ItemStack var2) {
         EnumFacing var3 = BlockDispenser.b(var1.f());
         World var4 = var1.i();
         double var5 = var1.a() + var3.g() * 1.125;
         double var7 = Math.floor(var1.b()) + var3.h();
         double var9 = var1.c() + var3.i() * 1.125;
         BlockPos var11 = var1.d().a(var3);
         IBlockState var12 = var4.p(var11);
         BlockRailBase.EnumRailDirection var13 = var12.c() instanceof BlockRailBase
            ? var12.b(((BlockRailBase)var12.c()).n())
            : BlockRailBase.EnumRailDirection.a;
         double var14;
         if (BlockRailBase.d(var12)) {
            if (var13.c()) {
               var14 = 0.6;
            } else {
               var14 = 0.1;
            }
         } else {
            if (var12.c().t() != Material.a || !BlockRailBase.d(var4.p(var11.b()))) {
               return this.b.a(var1, var2);
            }

            IBlockState var16 = var4.p(var11.b());
            BlockRailBase.EnumRailDirection var17 = var16.c() instanceof BlockRailBase
               ? var16.b(((BlockRailBase)var16.c()).n())
               : BlockRailBase.EnumRailDirection.a;
            if (var3 != EnumFacing.a && var17.c()) {
               var14 = -0.4;
            } else {
               var14 = -0.9;
            }
         }

         EntityMinecart var18 = EntityMinecart.a(var4, var5, var7 + var14, var9, ((ItemMinecart)var2.b()).b);
         if (var2.s()) {
            var18.a(var2.q());
         }

         var4.d(var18);
         var2.a(1);
         return var2;
      }

      @Override
      protected void a(IBlockSource var1) {
         var1.i().b(1000, var1.d(), 0);
      }
   };
   private final EntityMinecart.EnumMinecartType b;

   public ItemMinecart(EntityMinecart.EnumMinecartType var1) {
      this.h = 1;
      this.b = var1;
      this.a(CreativeTabs.e);
      BlockDispenser.N.a(this, a);
   }

   @Override
   public boolean a(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      IBlockState var9 = var3.p(var4);
      if (BlockRailBase.d(var9)) {
         if (!var3.D) {
            BlockRailBase.EnumRailDirection var10 = var9.c() instanceof BlockRailBase
               ? var9.b(((BlockRailBase)var9.c()).n())
               : BlockRailBase.EnumRailDirection.a;
            double var11 = 0.0;
            if (var10.c()) {
               var11 = 0.5;
            }

            EntityMinecart var13 = EntityMinecart.a(var3, var4.n() + 0.5, var4.o() + 0.0625 + var11, var4.p() + 0.5, this.b);
            if (var1.s()) {
               var13.a(var1.q());
            }

            var3.d(var13);
         }

         var1.b--;
         return true;
      } else {
         return false;
      }
   }
}
