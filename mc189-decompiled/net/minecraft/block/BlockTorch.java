package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BlockTorch extends Block {
   public static final PropertyDirection a = PropertyDirection.a("facing", new Predicate<EnumFacing>() {
      public boolean a(EnumFacing var1) {
         return var1 != EnumFacing.a;
      }
   });

   protected BlockTorch() {
      super(Material.q);
      this.j(this.M.b().a(a, EnumFacing.b));
      this.a(true);
      this.a(CreativeTabs.c);
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public boolean d() {
      return false;
   }

   private boolean e(World var1, BlockPos var2) {
      if (World.a(var1, var2)) {
         return true;
      } else {
         Block var3 = var1.p(var2).c();
         return var3 instanceof BlockFence || var3 == Blocks.w || var3 == Blocks.bZ || var3 == Blocks.cG;
      }
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      for (EnumFacing var4 : a.c()) {
         if (this.a(var1, var2, var4)) {
            return true;
         }
      }

      return false;
   }

   private boolean a(World var1, BlockPos var2, EnumFacing var3) {
      BlockPos var4 = var2.a(var3.d());
      boolean var5 = var3.k().c();
      return var5 && var1.d(var4, true) || var3.equals(EnumFacing.b) && this.e(var1, var4);
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      if (this.a(var1, var2, var3)) {
         return this.Q().a(a, var3);
      } else {
         for (EnumFacing var10 : EnumFacing.Plane.a) {
            if (var1.d(var2.a(var10.d()), true)) {
               return this.Q().a(a, var10);
            }
         }

         return this.Q();
      }
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3) {
      this.f(var1, var2, var3);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      this.e(var1, var2, var3);
   }

   protected boolean e(World var1, BlockPos var2, IBlockState var3) {
      if (!this.f(var1, var2, var3)) {
         return true;
      } else {
         EnumFacing var4 = var3.b(a);
         EnumFacing.Axis var5 = var4.k();
         EnumFacing var6 = var4.d();
         boolean var7 = false;
         if (var5.c() && !var1.d(var2.a(var6), true)) {
            var7 = true;
         } else if (var5.b() && !this.e(var1, var2.a(var6))) {
            var7 = true;
         }

         if (var7) {
            this.b(var1, var2, var3, 0);
            var1.g(var2);
            return true;
         } else {
            return false;
         }
      }
   }

   protected boolean f(World var1, BlockPos var2, IBlockState var3) {
      if (var3.c() == this && this.a(var1, var2, var3.b(a))) {
         return true;
      } else {
         if (var1.p(var2).c() == this) {
            this.b(var1, var2, var3, 0);
            var1.g(var2);
         }

         return false;
      }
   }

   @Override
   public MovingObjectPosition a(World var1, BlockPos var2, Vec3 var3, Vec3 var4) {
      EnumFacing var5 = var1.p(var2).b(a);
      float var6 = 0.15F;
      if (var5 == EnumFacing.f) {
         this.a(0.0F, 0.2F, 0.5F - var6, var6 * 2.0F, 0.8F, 0.5F + var6);
      } else if (var5 == EnumFacing.e) {
         this.a(1.0F - var6 * 2.0F, 0.2F, 0.5F - var6, 1.0F, 0.8F, 0.5F + var6);
      } else if (var5 == EnumFacing.d) {
         this.a(0.5F - var6, 0.2F, 0.0F, 0.5F + var6, 0.8F, var6 * 2.0F);
      } else if (var5 == EnumFacing.c) {
         this.a(0.5F - var6, 0.2F, 1.0F - var6 * 2.0F, 0.5F + var6, 0.8F, 1.0F);
      } else {
         var6 = 0.1F;
         this.a(0.5F - var6, 0.0F, 0.5F - var6, 0.5F + var6, 0.6F, 0.5F + var6);
      }

      return super.a(var1, var2, var3, var4);
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3, Random var4) {
      EnumFacing var5 = var3.b(a);
      double var6 = var2.n() + 0.5;
      double var8 = var2.o() + 0.7;
      double var10 = var2.p() + 0.5;
      double var12 = 0.22;
      double var14 = 0.27;
      if (var5.k().c()) {
         EnumFacing var16 = var5.d();
         var1.a(EnumParticleTypes.l, var6 + var14 * var16.g(), var8 + var12, var10 + var14 * var16.i(), 0.0, 0.0, 0.0);
         var1.a(EnumParticleTypes.A, var6 + var14 * var16.g(), var8 + var12, var10 + var14 * var16.i(), 0.0, 0.0, 0.0);
      } else {
         var1.a(EnumParticleTypes.l, var6, var8, var10, 0.0, 0.0, 0.0);
         var1.a(EnumParticleTypes.A, var6, var8, var10, 0.0, 0.0, 0.0);
      }
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.c;
   }

   @Override
   public IBlockState a(int var1) {
      IBlockState var2 = this.Q();
      switch (var1) {
         case 1:
            var2 = var2.a(a, EnumFacing.f);
            break;
         case 2:
            var2 = var2.a(a, EnumFacing.e);
            break;
         case 3:
            var2 = var2.a(a, EnumFacing.d);
            break;
         case 4:
            var2 = var2.a(a, EnumFacing.c);
            break;
         case 5:
         default:
            var2 = var2.a(a, EnumFacing.b);
      }

      return var2;
   }

   @Override
   public int c(IBlockState var1) {
      int var2 = 0;
      switch ((EnumFacing)var1.b(a)) {
         case f:
            var2 |= 1;
            break;
         case e:
            var2 |= 2;
            break;
         case d:
            var2 |= 3;
            break;
         case c:
            var2 |= 4;
            break;
         case a:
         case b:
         default:
            var2 |= 5;
      }

      return var2;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a);
   }
}
