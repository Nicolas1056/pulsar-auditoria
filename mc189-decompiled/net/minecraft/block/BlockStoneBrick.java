package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public class BlockStoneBrick extends Block {
   public static final PropertyEnum<BlockStoneBrick.EnumType> a = PropertyEnum.a("variant", BlockStoneBrick.EnumType.class);
   public static final int b = BlockStoneBrick.EnumType.a.a();
   public static final int N = BlockStoneBrick.EnumType.b.a();
   public static final int O = BlockStoneBrick.EnumType.c.a();
   public static final int P = BlockStoneBrick.EnumType.d.a();

   public BlockStoneBrick() {
      super(Material.e);
      this.j(this.M.b().a(a, BlockStoneBrick.EnumType.a));
      this.a(CreativeTabs.b);
   }

   @Override
   public int a(IBlockState var1) {
      return var1.b(a).a();
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      for (BlockStoneBrick.EnumType var7 : BlockStoneBrick.EnumType.values()) {
         var3.add(new ItemStack(var1, 1, var7.a()));
      }
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, BlockStoneBrick.EnumType.a(var1));
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
      a(0, "stonebrick", "default"),
      b(1, "mossy_stonebrick", "mossy"),
      c(2, "cracked_stonebrick", "cracked"),
      d(3, "chiseled_stonebrick", "chiseled");

      private static final BlockStoneBrick.EnumType[] e = new BlockStoneBrick.EnumType[values().length];
      private final int f;
      private final String g;
      private final String h;

      private EnumType(int var3, String var4, String var5) {
         this.f = var3;
         this.g = var4;
         this.h = var5;
      }

      public int a() {
         return this.f;
      }

      @Override
      public String toString() {
         return this.g;
      }

      public static BlockStoneBrick.EnumType a(int var0) {
         if (var0 < 0 || var0 >= e.length) {
            var0 = 0;
         }

         return e[var0];
      }

      @Override
      public String l() {
         return this.g;
      }

      public String c() {
         return this.h;
      }

      static {
         for (BlockStoneBrick.EnumType var3 : values()) {
            e[var3.a()] = var3;
         }
      }
   }
}
