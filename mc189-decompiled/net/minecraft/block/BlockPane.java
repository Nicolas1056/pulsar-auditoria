package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPane extends Block {
   public static final PropertyBool b = PropertyBool.a("north");
   public static final PropertyBool N = PropertyBool.a("east");
   public static final PropertyBool O = PropertyBool.a("south");
   public static final PropertyBool P = PropertyBool.a("west");
   private final boolean a;

   protected BlockPane(Material var1, boolean var2) {
      super(var1);
      this.j(this.M.b().a(b, false).a(N, false).a(O, false).a(P, false));
      this.a = var2;
      this.a(CreativeTabs.c);
   }

   @Override
   public IBlockState a(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      return var1.a(b, this.c(var2.p(var3.c()).c())).a(O, this.c(var2.p(var3.d()).c())).a(P, this.c(var2.p(var3.e()).c())).a(N, this.c(var2.p(var3.f()).c()));
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return !this.a ? null : super.a(var1, var2, var3);
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
      return var1.p(var2).c() == this ? false : super.a(var1, var2, var3);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List<AxisAlignedBB> var5, Entity var6) {
      boolean var7 = this.c(var1.p(var2.c()).c());
      boolean var8 = this.c(var1.p(var2.d()).c());
      boolean var9 = this.c(var1.p(var2.e()).c());
      boolean var10 = this.c(var1.p(var2.f()).c());
      if ((!var9 || !var10) && (var9 || var10 || var7 || var8)) {
         if (var9) {
            this.a(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
            super.a(var1, var2, var3, var4, var5, var6);
         } else if (var10) {
            this.a(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
            super.a(var1, var2, var3, var4, var5, var6);
         }
      } else {
         this.a(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
         super.a(var1, var2, var3, var4, var5, var6);
      }

      if ((!var7 || !var8) && (var9 || var10 || var7 || var8)) {
         if (var7) {
            this.a(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
            super.a(var1, var2, var3, var4, var5, var6);
         } else if (var8) {
            this.a(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
            super.a(var1, var2, var3, var4, var5, var6);
         }
      } else {
         this.a(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
         super.a(var1, var2, var3, var4, var5, var6);
      }
   }

   @Override
   public void j() {
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      float var3 = 0.4375F;
      float var4 = 0.5625F;
      float var5 = 0.4375F;
      float var6 = 0.5625F;
      boolean var7 = this.c(var1.p(var2.c()).c());
      boolean var8 = this.c(var1.p(var2.d()).c());
      boolean var9 = this.c(var1.p(var2.e()).c());
      boolean var10 = this.c(var1.p(var2.f()).c());
      if ((!var9 || !var10) && (var9 || var10 || var7 || var8)) {
         if (var9) {
            var3 = 0.0F;
         } else if (var10) {
            var4 = 1.0F;
         }
      } else {
         var3 = 0.0F;
         var4 = 1.0F;
      }

      if ((!var7 || !var8) && (var9 || var10 || var7 || var8)) {
         if (var7) {
            var5 = 0.0F;
         } else if (var8) {
            var6 = 1.0F;
         }
      } else {
         var5 = 0.0F;
         var6 = 1.0F;
      }

      this.a(var3, 0.0F, var5, var4, 1.0F, var6);
   }

   public final boolean c(Block var1) {
      return var1.o() || var1 == this || var1 == Blocks.w || var1 == Blocks.cG || var1 == Blocks.cH || var1 instanceof BlockPane;
   }

   @Override
   protected boolean I() {
      return true;
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.b;
   }

   @Override
   public int c(IBlockState var1) {
      return 0;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, b, N, P, O);
   }
}
