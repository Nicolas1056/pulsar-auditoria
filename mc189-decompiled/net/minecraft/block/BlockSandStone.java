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

public class BlockSandStone extends Block {
   public static final PropertyEnum<BlockSandStone.EnumType> a = PropertyEnum.a("type", BlockSandStone.EnumType.class);

   public BlockSandStone() {
      super(Material.e);
      this.j(this.M.b().a(a, BlockSandStone.EnumType.a));
      this.a(CreativeTabs.b);
   }

   @Override
   public int a(IBlockState var1) {
      return var1.b(a).a();
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      for (BlockSandStone.EnumType var7 : BlockSandStone.EnumType.values()) {
         var3.add(new ItemStack(var1, 1, var7.a()));
      }
   }

   @Override
   public MapColor g(IBlockState var1) {
      return MapColor.d;
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, BlockSandStone.EnumType.a(var1));
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
      a(0, "sandstone", "default"),
      b(1, "chiseled_sandstone", "chiseled"),
      c(2, "smooth_sandstone", "smooth");

      private static final BlockSandStone.EnumType[] d = new BlockSandStone.EnumType[values().length];
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

      public static BlockSandStone.EnumType a(int var0) {
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
         for (BlockSandStone.EnumType var3 : values()) {
            d[var3.a()] = var3;
         }
      }
   }
}
