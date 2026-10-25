package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemLead;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFence extends Block {
   public static final PropertyBool a = PropertyBool.a("north");
   public static final PropertyBool b = PropertyBool.a("east");
   public static final PropertyBool N = PropertyBool.a("south");
   public static final PropertyBool O = PropertyBool.a("west");

   public BlockFence(Material var1) {
      this(var1, var1.r());
   }

   public BlockFence(Material var1, MapColor var2) {
      super(var1, var2);
      this.j(this.M.b().a(a, false).a(b, false).a(N, false).a(O, false));
      this.a(CreativeTabs.c);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List<AxisAlignedBB> var5, Entity var6) {
      boolean var7 = this.e(var1, var2.c());
      boolean var8 = this.e(var1, var2.d());
      boolean var9 = this.e(var1, var2.e());
      boolean var10 = this.e(var1, var2.f());
      float var11 = 0.375F;
      float var12 = 0.625F;
      float var13 = 0.375F;
      float var14 = 0.625F;
      if (var7) {
         var13 = 0.0F;
      }

      if (var8) {
         var14 = 1.0F;
      }

      if (var7 || var8) {
         this.a(var11, 0.0F, var13, var12, 1.5F, var14);
         super.a(var1, var2, var3, var4, var5, var6);
      }

      var13 = 0.375F;
      var14 = 0.625F;
      if (var9) {
         var11 = 0.0F;
      }

      if (var10) {
         var12 = 1.0F;
      }

      if (var9 || var10 || !var7 && !var8) {
         this.a(var11, 0.0F, var13, var12, 1.5F, var14);
         super.a(var1, var2, var3, var4, var5, var6);
      }

      if (var7) {
         var13 = 0.0F;
      }

      if (var8) {
         var14 = 1.0F;
      }

      this.a(var11, 0.0F, var13, var12, 1.0F, var14);
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      boolean var3 = this.e(var1, var2.c());
      boolean var4 = this.e(var1, var2.d());
      boolean var5 = this.e(var1, var2.e());
      boolean var6 = this.e(var1, var2.f());
      float var7 = 0.375F;
      float var8 = 0.625F;
      float var9 = 0.375F;
      float var10 = 0.625F;
      if (var3) {
         var9 = 0.0F;
      }

      if (var4) {
         var10 = 1.0F;
      }

      if (var5) {
         var7 = 0.0F;
      }

      if (var6) {
         var8 = 1.0F;
      }

      this.a(var7, 0.0F, var9, var8, 1.0F, var10);
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
   public boolean b(IBlockAccess var1, BlockPos var2) {
      return false;
   }

   public boolean e(IBlockAccess var1, BlockPos var2) {
      Block var3 = var1.p(var2).c();
      if (var3 == Blocks.cv) {
         return false;
      } else if ((!(var3 instanceof BlockFence) || var3.J != this.J) && !(var3 instanceof BlockFenceGate)) {
         return var3.J.k() && var3.d() ? var3.J != Material.C : false;
      } else {
         return true;
      }
   }

   @Override
   public boolean a(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return true;
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      return var1.D ? true : ItemLead.a(var4, var1, var2);
   }

   @Override
   public int c(IBlockState var1) {
      return 0;
   }

   @Override
   public IBlockState a(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      return var1.a(a, this.e(var2, var3.c())).a(b, this.e(var2, var3.f())).a(N, this.e(var2, var3.d())).a(O, this.e(var2, var3.e()));
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a, b, O, N);
   }
}
