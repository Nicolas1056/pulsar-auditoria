package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDragonEgg extends Block {
   public BlockDragonEgg() {
      super(Material.D, MapColor.E);
      this.a(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3) {
      var1.a(var2, this, this.a(var1));
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      var1.a(var2, this, this.a(var1));
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      this.e(var1, var2);
   }

   private void e(World var1, BlockPos var2) {
      if (BlockFalling.e(var1, var2.b()) && var2.o() >= 0) {
         byte var3 = 32;
         if (!BlockFalling.N && var1.a(var2.a(-var3, -var3, -var3), var2.a((int)var3, (int)var3, (int)var3))) {
            var1.d(new EntityFallingBlock(var1, var2.n() + 0.5F, var2.o(), var2.p() + 0.5F, this.Q()));
         } else {
            var1.g(var2);
            BlockPos var4 = var2;

            while (BlockFalling.e(var1, var4) && var4.o() > 0) {
               var4 = var4.b();
            }

            if (var4.o() > 0) {
               var1.a(var4, this.Q(), 2);
            }
         }
      }
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      this.f(var1, var2);
      return true;
   }

   @Override
   public void a(World var1, BlockPos var2, EntityPlayer var3) {
      this.f(var1, var2);
   }

   private void f(World var1, BlockPos var2) {
      IBlockState var3 = var1.p(var2);
      if (var3.c() == this) {
         for (int var4 = 0; var4 < 1000; var4++) {
            BlockPos var5 = var2.a(var1.s.nextInt(16) - var1.s.nextInt(16), var1.s.nextInt(8) - var1.s.nextInt(8), var1.s.nextInt(16) - var1.s.nextInt(16));
            if (var1.p(var5).c().J == Material.a) {
               if (var1.D) {
                  for (int var6 = 0; var6 < 128; var6++) {
                     double var7 = var1.s.nextDouble();
                     float var9 = (var1.s.nextFloat() - 0.5F) * 0.2F;
                     float var10 = (var1.s.nextFloat() - 0.5F) * 0.2F;
                     float var11 = (var1.s.nextFloat() - 0.5F) * 0.2F;
                     double var12 = var5.n() + (var2.n() - var5.n()) * var7 + (var1.s.nextDouble() - 0.5) * 1.0 + 0.5;
                     double var14 = var5.o() + (var2.o() - var5.o()) * var7 + var1.s.nextDouble() * 1.0 - 0.5;
                     double var16 = var5.p() + (var2.p() - var5.p()) * var7 + (var1.s.nextDouble() - 0.5) * 1.0 + 0.5;
                     var1.a(EnumParticleTypes.y, var12, var14, var16, var9, var10, var11);
                  }
               } else {
                  var1.a(var5, var3, 2);
                  var1.g(var2);
               }

               return;
            }
         }
      }
   }

   @Override
   public int a(World var1) {
      return 5;
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public boolean a(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return true;
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return null;
   }
}
