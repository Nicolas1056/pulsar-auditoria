package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.StatCollector;

public class BlockStone extends Block {
   public static final PropertyEnum<BlockStone.EnumType> a = PropertyEnum.a("variant", BlockStone.EnumType.class);

   public BlockStone() {
      super(Material.e);
      this.j(this.M.b().a(a, BlockStone.EnumType.a));
      this.a(CreativeTabs.b);
   }

   @Override
   public String f() {
      return StatCollector.a(this.a() + "." + BlockStone.EnumType.a.d() + ".name");
   }

   @Override
   public MapColor g(IBlockState var1) {
      return var1.b(a).c();
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return var1.b(a) == BlockStone.EnumType.a ? Item.a(Blocks.e) : Item.a(Blocks.b);
   }

   @Override
   public int a(IBlockState var1) {
      return var1.b(a).a();
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      for (BlockStone.EnumType var7 : BlockStone.EnumType.values()) {
         var3.add(new ItemStack(var1, 1, var7.a()));
      }
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, BlockStone.EnumType.a(var1));
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
      a(0, MapColor.m, "stone"),
      b(1, MapColor.l, "granite"),
      c(2, MapColor.l, "smooth_granite", "graniteSmooth"),
      d(3, MapColor.p, "diorite"),
      e(4, MapColor.p, "smooth_diorite", "dioriteSmooth"),
      f(5, MapColor.m, "andesite"),
      g(6, MapColor.m, "smooth_andesite", "andesiteSmooth");

      private static final BlockStone.EnumType[] h = new BlockStone.EnumType[values().length];
      private final int i;
      private final String j;
      private final String k;
      private final MapColor l;

      private EnumType(int var3, MapColor var4, String var5) {
         this(var3, var4, var5, var5);
      }

      private EnumType(int var3, MapColor var4, String var5, String var6) {
         this.i = var3;
         this.j = var5;
         this.k = var6;
         this.l = var4;
      }

      public int a() {
         return this.i;
      }

      public MapColor c() {
         return this.l;
      }

      @Override
      public String toString() {
         return this.j;
      }

      public static BlockStone.EnumType a(int var0) {
         if (var0 < 0 || var0 >= h.length) {
            var0 = 0;
         }

         return h[var0];
      }

      @Override
      public String l() {
         return this.j;
      }

      public String d() {
         return this.k;
      }

      static {
         for (BlockStone.EnumType var3 : values()) {
            h[var3.a()] = var3;
         }
      }
   }
}
