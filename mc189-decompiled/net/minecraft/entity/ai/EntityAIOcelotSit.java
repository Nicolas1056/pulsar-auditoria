package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityAIOcelotSit extends EntityAIMoveToBlock {
   private final EntityOcelot c;

   public EntityAIOcelotSit(EntityOcelot var1, double var2) {
      super(var1, var2, 8);
      this.c = var1;
   }

   @Override
   public boolean a() {
      return this.c.cl() && !this.c.cn() && super.a();
   }

   @Override
   public boolean b() {
      return super.b();
   }

   @Override
   public void c() {
      super.c();
      this.c.cp().a(false);
   }

   @Override
   public void d() {
      super.d();
      this.c.n(false);
   }

   @Override
   public void e() {
      super.e();
      this.c.cp().a(false);
      if (!this.f()) {
         this.c.n(false);
      } else if (!this.c.cn()) {
         this.c.n(true);
      }
   }

   @Override
   protected boolean a(World var1, BlockPos var2) {
      if (!var1.d(var2.a())) {
         return false;
      } else {
         IBlockState var3 = var1.p(var2);
         Block var4 = var3.c();
         if (var4 == Blocks.ae) {
            TileEntity var5 = var1.s(var2);
            if (var5 instanceof TileEntityChest && ((TileEntityChest)var5).l < 1) {
               return true;
            }
         } else {
            if (var4 == Blocks.am) {
               return true;
            }

            if (var4 == Blocks.C && var3.b(BlockBed.a) != BlockBed.EnumPartType.a) {
               return true;
            }
         }

         return false;
      }
   }
}
