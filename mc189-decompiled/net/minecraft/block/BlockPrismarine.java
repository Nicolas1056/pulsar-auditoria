package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.StatCollector;

public class BlockPrismarine extends Block {
   public static final PropertyEnum<BlockPrismarine.EnumType> a = PropertyEnum.a("variant", BlockPrismarine.EnumType.class);
   public static final int b = BlockPrismarine.EnumType.a.a();
   public static final int N = BlockPrismarine.EnumType.b.a();
   public static final int O = BlockPrismarine.EnumType.c.a();

   public BlockPrismarine() {
      super(Material.e);
      this.j(this.M.b().a(a, BlockPrismarine.EnumType.a));
      this.a(CreativeTabs.b);
   }

   @Override
   public String f() {
      return StatCollector.a(this.a() + "." + BlockPrismarine.EnumType.a.c() + ".name");
   }

   @Override
   public MapColor g(IBlockState var1) {
      return var1.b(a) == BlockPrismarine.EnumType.a ? MapColor.y : MapColor.G;
   }

   @Override
   public int a(IBlockState var1) {
      return var1.b(a).a();
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(a).a();
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a);
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, BlockPrismarine.EnumType.a(var1));
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      var3.add(new ItemStack(var1, 1, b));
      var3.add(new ItemStack(var1, 1, N));
      var3.add(new ItemStack(var1, 1, O));
   }

   public static enum EnumType implements IStringSerializable {
      a(0, "prismarine", "rough"),
      b(1, "prismarine_bricks", "bricks"),
      c(2, "dark_prismarine", "dark");

      private static final BlockPrismarine.EnumType[] d = new BlockPrismarine.EnumType[values().length];
      private final int e;
      private final String f;
      private final String g;

      private EnumType(int var3, String var4, String var5) {
         this.e = var3;
         this.f = var4;
         this.g = var5;
      }

      public int a() {
         return this.e;
      }

      @Override
      public String toString() {
         return this.f;
      }

      public static BlockPrismarine.EnumType a(int var0) {
         if (var0 < 0 || var0 >= d.length) {
            var0 = 0;
         }

         return d[var0];
      }

      @Override
      public String l() {
         return this.f;
      }

      public String c() {
         return this.g;
      }

      static {
         for (BlockPrismarine.EnumType var3 : values()) {
            d[var3.a()] = var3;
         }
      }
   }
}
