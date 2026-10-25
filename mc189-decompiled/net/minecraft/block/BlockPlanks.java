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

public class BlockPlanks extends Block {
   public static final PropertyEnum<BlockPlanks.EnumType> a = PropertyEnum.a("variant", BlockPlanks.EnumType.class);

   public BlockPlanks() {
      super(Material.d);
      this.j(this.M.b().a(a, BlockPlanks.EnumType.a));
      this.a(CreativeTabs.b);
   }

   @Override
   public int a(IBlockState var1) {
      return var1.b(a).a();
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      for (BlockPlanks.EnumType var7 : BlockPlanks.EnumType.values()) {
         var3.add(new ItemStack(var1, 1, var7.a()));
      }
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, BlockPlanks.EnumType.a(var1));
   }

   @Override
   public MapColor g(IBlockState var1) {
      return var1.b(a).c();
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
      a(0, "oak", MapColor.o),
      b(1, "spruce", MapColor.J),
      c(2, "birch", MapColor.d),
      d(3, "jungle", MapColor.l),
      e(4, "acacia", MapColor.q),
      f(5, "dark_oak", "big_oak", MapColor.B);

      private static final BlockPlanks.EnumType[] g = new BlockPlanks.EnumType[values().length];
      private final int h;
      private final String i;
      private final String j;
      private final MapColor k;

      private EnumType(int var3, String var4, MapColor var5) {
         this(var3, var4, var4, var5);
      }

      private EnumType(int var3, String var4, String var5, MapColor var6) {
         this.h = var3;
         this.i = var4;
         this.j = var5;
         this.k = var6;
      }

      public int a() {
         return this.h;
      }

      public MapColor c() {
         return this.k;
      }

      @Override
      public String toString() {
         return this.i;
      }

      public static BlockPlanks.EnumType a(int var0) {
         if (var0 < 0 || var0 >= g.length) {
            var0 = 0;
         }

         return g[var0];
      }

      @Override
      public String l() {
         return this.i;
      }

      public String d() {
         return this.j;
      }

      static {
         for (BlockPlanks.EnumType var3 : values()) {
            g[var3.a()] = var3;
         }
      }
   }
}
