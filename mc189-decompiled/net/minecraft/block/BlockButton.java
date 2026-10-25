package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockButton extends Block {
   public static final PropertyDirection a = PropertyDirection.a("facing");
   public static final PropertyBool b = PropertyBool.a("powered");
   private final boolean N;

   protected BlockButton(boolean var1) {
      super(Material.q);
      this.j(this.M.b().a(a, EnumFacing.c).a(b, false));
      this.a(true);
      this.a(CreativeTabs.d);
      this.N = var1;
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   @Override
   public int a(World var1) {
      return this.N ? 30 : 20;
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
   public boolean b(World var1, BlockPos var2, EnumFacing var3) {
      return a(var1, var2, var3.d());
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      for (EnumFacing var6 : EnumFacing.values()) {
         if (a(var1, var2, var6)) {
            return true;
         }
      }

      return false;
   }

   protected static boolean a(World var0, BlockPos var1, EnumFacing var2) {
      BlockPos var3 = var1.a(var2);
      return var2 == EnumFacing.a ? World.a(var0, var3) : var0.p(var3).c().v();
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return a(var1, var2, var3.d()) ? this.Q().a(a, var3).a(b, false) : this.Q().a(a, EnumFacing.a).a(b, false);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (this.e(var1, var2, var3) && !a(var1, var2, var3.b(a).d())) {
         this.b(var1, var2, var3, 0);
         var1.g(var2);
      }
   }

   private boolean e(World var1, BlockPos var2, IBlockState var3) {
      if (this.d(var1, var2)) {
         return true;
      } else {
         this.b(var1, var2, var3, 0);
         var1.g(var2);
         return false;
      }
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      this.d(var1.p(var2));
   }

   private void d(IBlockState var1) {
      EnumFacing var2 = var1.b(a);
      boolean var3 = var1.b(b);
      float var4 = 0.25F;
      float var5 = 0.375F;
      float var6 = (var3 ? 1 : 2) / 16.0F;
      float var7 = 0.125F;
      float var8 = 0.1875F;
      switch (var2) {
         case f:
            this.a(0.0F, 0.375F, 0.3125F, var6, 0.625F, 0.6875F);
            break;
         case e:
            this.a(1.0F - var6, 0.375F, 0.3125F, 1.0F, 0.625F, 0.6875F);
            break;
         case d:
            this.a(0.3125F, 0.375F, 0.0F, 0.6875F, 0.625F, var6);
            break;
         case c:
            this.a(0.3125F, 0.375F, 1.0F - var6, 0.6875F, 0.625F, 1.0F);
            break;
         case b:
            this.a(0.3125F, 0.0F, 0.375F, 0.6875F, 0.0F + var6, 0.625F);
            break;
         case a:
            this.a(0.3125F, 1.0F - var6, 0.375F, 0.6875F, 1.0F, 0.625F);
      }
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var3.b(b)) {
         return true;
      } else {
         var1.a(var2, var3.a(b, true), 3);
         var1.b(var2, var2);
         var1.a(var2.n() + 0.5, var2.o() + 0.5, var2.p() + 0.5, "random.click", 0.3F, 0.6F);
         this.c(var1, var2, var3.b(a));
         var1.a(var2, this, this.a(var1));
         return true;
      }
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3) {
      if (var3.b(b)) {
         this.c(var1, var2, var3.b(a));
      }

      super.b(var1, var2, var3);
   }

   @Override
   public int a(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return var3.b(b) ? 15 : 0;
   }

   @Override
   public int b(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      if (!var3.b(b)) {
         return 0;
      } else {
         return var3.b(a) == var4 ? 15 : 0;
      }
   }

   @Override
   public boolean i() {
      return true;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Random var4) {
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (!var1.D) {
         if (var3.b(b)) {
            if (this.N) {
               this.f(var1, var2, var3);
            } else {
               var1.a(var2, var3.a(b, false));
               this.c(var1, var2, var3.b(a));
               var1.a(var2.n() + 0.5, var2.o() + 0.5, var2.p() + 0.5, "random.click", 0.3F, 0.5F);
               var1.b(var2, var2);
            }
         }
      }
   }

   @Override
   public void j() {
      float var1 = 0.1875F;
      float var2 = 0.125F;
      float var3 = 0.125F;
      this.a(0.5F - var1, 0.5F - var2, 0.5F - var3, 0.5F + var1, 0.5F + var2, 0.5F + var3);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Entity var4) {
      if (!var1.D) {
         if (this.N) {
            if (!var3.b(b)) {
               this.f(var1, var2, var3);
            }
         }
      }
   }

   private void f(World var1, BlockPos var2, IBlockState var3) {
      this.d(var3);
      List var4 = var1.a(
         EntityArrow.class, new AxisAlignedBB(var2.n() + this.B, var2.o() + this.C, var2.p() + this.D, var2.n() + this.E, var2.o() + this.F, var2.p() + this.G)
      );
      boolean var5 = !var4.isEmpty();
      boolean var6 = var3.b(b);
      if (var5 && !var6) {
         var1.a(var2, var3.a(b, true));
         this.c(var1, var2, var3.b(a));
         var1.b(var2, var2);
         var1.a(var2.n() + 0.5, var2.o() + 0.5, var2.p() + 0.5, "random.click", 0.3F, 0.6F);
      }

      if (!var5 && var6) {
         var1.a(var2, var3.a(b, false));
         this.c(var1, var2, var3.b(a));
         var1.b(var2, var2);
         var1.a(var2.n() + 0.5, var2.o() + 0.5, var2.p() + 0.5, "random.click", 0.3F, 0.5F);
      }

      if (var5) {
         var1.a(var2, this, this.a(var1));
      }
   }

   private void c(World var1, BlockPos var2, EnumFacing var3) {
      var1.c(var2, this);
      var1.c(var2.a(var3.d()), this);
   }

   @Override
   public IBlockState a(int var1) {
      EnumFacing var2;
      switch (var1 & 7) {
         case 0:
            var2 = EnumFacing.a;
            break;
         case 1:
            var2 = EnumFacing.f;
            break;
         case 2:
            var2 = EnumFacing.e;
            break;
         case 3:
            var2 = EnumFacing.d;
            break;
         case 4:
            var2 = EnumFacing.c;
            break;
         case 5:
         default:
            var2 = EnumFacing.b;
      }

      return this.Q().a(a, var2).a(b, (var1 & 8) > 0);
   }

   @Override
   public int c(IBlockState var1) {
      byte var2;
      switch ((EnumFacing)var1.b(a)) {
         case f:
            var2 = 1;
            break;
         case e:
            var2 = 2;
            break;
         case d:
            var2 = 3;
            break;
         case c:
            var2 = 4;
            break;
         case b:
         default:
            var2 = 5;
            break;
         case a:
            var2 = 0;
      }

      if (var1.b(b)) {
         var2 |= 8;
      }

      return var2;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a, b);
   }
}
