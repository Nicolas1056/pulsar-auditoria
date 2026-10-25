package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

public class BlockQuartz extends Block {
   public static final PropertyEnum<BlockQuartz.EnumType> a = PropertyEnum.a("variant", BlockQuartz.EnumType.class);

   public BlockQuartz() {
      super(Material.e);
      this.j(this.M.b().a(a, BlockQuartz.EnumType.a));
      this.a(CreativeTabs.b);
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      if (var7 == BlockQuartz.EnumType.c.a()) {
         switch (var3.k()) {
            case c:
               return this.Q().a(a, BlockQuartz.EnumType.e);
            case a:
               return this.Q().a(a, BlockQuartz.EnumType.d);
            case b:
            default:
               return this.Q().a(a, BlockQuartz.EnumType.c);
         }
      } else {
         return var7 == BlockQuartz.EnumType.b.a() ? this.Q().a(a, BlockQuartz.EnumType.b) : this.Q().a(a, BlockQuartz.EnumType.a);
      }
   }

   @Override
   public int a(IBlockState var1) {
      BlockQuartz.EnumType var2 = var1.b(a);
      return var2 != BlockQuartz.EnumType.d && var2 != BlockQuartz.EnumType.e ? var2.a() : BlockQuartz.EnumType.c.a();
   }

   @Override
   protected ItemStack i(IBlockState var1) {
      BlockQuartz.EnumType var2 = var1.b(a);
      return var2 != BlockQuartz.EnumType.d && var2 != BlockQuartz.EnumType.e ? super.i(var1) : new ItemStack(Item.a(this), 1, BlockQuartz.EnumType.c.a());
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      var3.add(new ItemStack(var1, 1, BlockQuartz.EnumType.a.a()));
      var3.add(new ItemStack(var1, 1, BlockQuartz.EnumType.b.a()));
      var3.add(new ItemStack(var1, 1, BlockQuartz.EnumType.c.a()));
   }

   @Override
   public MapColor g(IBlockState var1) {
      return MapColor.p;
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, BlockQuartz.EnumType.a(var1));
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(a).a();
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a);
   }

   public static enum EnumType implements IStringSerializable {
      a(0, "default", "default"),
      b(1, "chiseled", "chiseled"),
      c(2, "lines_y", "lines"),
      d(3, "lines_x", "lines"),
      e(4, "lines_z", "lines");

      private static final BlockQuartz.EnumType[] f = new BlockQuartz.EnumType[values().length];
      private final int g;
      private final String h;
      private final String i;

      private EnumType(int var3, String var4, String var5) {
         this.g = var3;
         this.h = var4;
         this.i = var5;
      }

      public int a() {
         return this.g;
      }

      @Override
      public String toString() {
         return this.i;
      }

      public static BlockQuartz.EnumType a(int var0) {
         if (var0 < 0 || var0 >= f.length) {
            var0 = 0;
         }

         return f[var0];
      }

      @Override
      public String l() {
         return this.h;
      }

      static {
         for (BlockQuartz.EnumType var3 : values()) {
            f[var3.a()] = var3;
         }
      }
   }
}
