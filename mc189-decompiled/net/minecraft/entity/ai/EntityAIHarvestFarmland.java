package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityAIHarvestFarmland extends EntityAIMoveToBlock {
   private final EntityVillager c;
   private boolean d;
   private boolean e;
   private int f;

   public EntityAIHarvestFarmland(EntityVillager var1, double var2) {
      super(var1, var2, 16);
      this.c = var1;
   }

   @Override
   public boolean a() {
      if (this.a <= 0) {
         if (!this.c.o.Q().b("mobGriefing")) {
            return false;
         }

         this.f = -1;
         this.d = this.c.cu();
         this.e = this.c.ct();
      }

      return super.a();
   }

   @Override
   public boolean b() {
      return this.f >= 0 && super.b();
   }

   @Override
   public void c() {
      super.c();
   }

   @Override
   public void d() {
      super.d();
   }

   @Override
   public void e() {
      super.e();
      this.c.p().a(this.b.n() + 0.5, this.b.o() + 1, this.b.p() + 0.5, 10.0F, this.c.bQ());
      if (this.f()) {
         World var1 = this.c.o;
         BlockPos var2 = this.b.a();
         IBlockState var3 = var1.p(var2);
         Block var4 = var3.c();
         if (this.f == 0 && var4 instanceof BlockCrops && var3.b(BlockCrops.a) == 7) {
            var1.b(var2, true);
         } else if (this.f == 1 && var4 == Blocks.a) {
            InventoryBasic var5 = this.c.cq();

            for (int var6 = 0; var6 < var5.o_(); var6++) {
               ItemStack var7 = var5.a(var6);
               boolean var8 = false;
               if (var7 != null) {
                  if (var7.b() == Items.N) {
                     var1.a(var2, Blocks.aj.Q(), 3);
                     var8 = true;
                  } else if (var7.b() == Items.bS) {
                     var1.a(var2, Blocks.cc.Q(), 3);
                     var8 = true;
                  } else if (var7.b() == Items.bR) {
                     var1.a(var2, Blocks.cb.Q(), 3);
                     var8 = true;
                  }
               }

               if (var8) {
                  var7.b--;
                  if (var7.b <= 0) {
                     var5.a(var6, null);
                  }
                  break;
               }
            }
         }

         this.f = -1;
         this.a = 10;
      }
   }

   @Override
   protected boolean a(World var1, BlockPos var2) {
      Block var3 = var1.p(var2).c();
      if (var3 == Blocks.ak) {
         var2 = var2.a();
         IBlockState var4 = var1.p(var2);
         var3 = var4.c();
         if (var3 instanceof BlockCrops && var4.b(BlockCrops.a) == 7 && this.e && (this.f == 0 || this.f < 0)) {
            this.f = 0;
            return true;
         }

         if (var3 == Blocks.a && this.d && (this.f == 1 || this.f < 0)) {
            this.f = 1;
            return true;
         }
      }

      return false;
   }
}
