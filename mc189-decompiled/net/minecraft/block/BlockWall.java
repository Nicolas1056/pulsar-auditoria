package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWall extends Block {
   public static final PropertyBool a = PropertyBool.a("up");
   public static final PropertyBool b = PropertyBool.a("north");
   public static final PropertyBool N = PropertyBool.a("east");
   public static final PropertyBool O = PropertyBool.a("south");
   public static final PropertyBool P = PropertyBool.a("west");
   public static final PropertyEnum<BlockWall.EnumType> Q = PropertyEnum.a("variant", BlockWall.EnumType.class);

   public BlockWall(Block var1) {
      super(var1.J);
      this.j(this.M.b().a(a, false).a(b, false).a(N, false).a(O, false).a(P, false).a(Q, BlockWall.EnumType.a));
      this.c(var1.w);
      this.b(var1.x / 3.0F);
      this.a(var1.H);
      this.a(CreativeTabs.b);
   }

   @Override
   public String f() {
      return StatCollector.a(this.a() + "." + BlockWall.EnumType.a.c() + ".name");
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public boolean b(IBlockAccess var1, BlockPos var2) {
      return false;
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      boolean var3 = this.e(var1, var2.c());
      boolean var4 = this.e(var1, var2.d());
      boolean var5 = this.e(var1, var2.e());
      boolean var6 = this.e(var1, var2.f());
      float var7 = 0.25F;
      float var8 = 0.75F;
      float var9 = 0.25F;
      float var10 = 0.75F;
      float var11 = 1.0F;
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

      if (var3 && var4 && !var5 && !var6) {
         var11 = 0.8125F;
         var7 = 0.3125F;
         var8 = 0.6875F;
      } else if (!var3 && !var4 && var5 && var6) {
         var11 = 0.8125F;
         var9 = 0.3125F;
         var10 = 0.6875F;
      }

      this.a(var7, 0.0F, var9, var8, var11, var10);
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      this.a(var1, var2);
      this.F = 1.5;
      return super.a(var1, var2, var3);
   }

   public boolean e(IBlockAccess var1, BlockPos var2) {
      Block var3 = var1.p(var2).c();
      if (var3 == Blocks.cv) {
         return false;
      } else if (var3 == this || var3 instanceof BlockFenceGate) {
         return true;
      } else {
         return var3.J.k() && var3.d() ? var3.J != Material.C : false;
      }
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      for (BlockWall.EnumType var7 : BlockWall.EnumType.values()) {
         var3.add(new ItemStack(var1, 1, var7.a()));
      }
   }

   @Override
   public int a(IBlockState var1) {
      return var1.b(Q).a();
   }

   @Override
   public boolean a(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return var3 == EnumFacing.a ? super.a(var1, var2, var3) : true;
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(Q, BlockWall.EnumType.a(var1));
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(Q).a();
   }

   @Override
   public IBlockState a(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      return var1.a(a, !var2.d(var3.a())).a(b, this.e(var2, var3.c())).a(N, this.e(var2, var3.f())).a(O, this.e(var2, var3.d())).a(P, this.e(var2, var3.e()));
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a, b, N, P, O, Q);
   }

   public static enum EnumType implements IStringSerializable {
      a(0, "cobblestone", "normal"),
      b(1, "mossy_cobblestone", "mossy");

      private static final BlockWall.EnumType[] c = new BlockWall.EnumType[values().length];
      private final int d;
      private final String e;
      private String f;

      private EnumType(int var3, String var4, String var5) {
         this.d = var3;
         this.e = var4;
         this.f = var5;
      }

      public int a() {
         return this.d;
      }

      @Override
      public String toString() {
         return this.e;
      }

      public static BlockWall.EnumType a(int var0) {
         if (var0 < 0 || var0 >= c.length) {
            var0 = 0;
         }

         return c[var0];
      }

      @Override
      public String l() {
         return this.e;
      }

      public String c() {
         return this.f;
      }

      static {
         for (BlockWall.EnumType var3 : values()) {
            c[var3.a()] = var3;
         }
      }
   }
}
