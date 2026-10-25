package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public class BlockSand extends BlockFalling {
   public static final PropertyEnum<BlockSand.EnumType> a = PropertyEnum.a("variant", BlockSand.EnumType.class);

   public BlockSand() {
      this.j(this.M.b().a(a, BlockSand.EnumType.a));
   }

   @Override
   public int a(IBlockState var1) {
      return var1.b(a).a();
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      for (BlockSand.EnumType var7 : BlockSand.EnumType.values()) {
         var3.add(new ItemStack(var1, 1, var7.a()));
      }
   }

   @Override
   public MapColor g(IBlockState var1) {
      return var1.b(a).c();
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, BlockSand.EnumType.a(var1));
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
      a(0, "sand", "default", MapColor.d),
      b(1, "red_sand", "red", MapColor.q);

      private static final BlockSand.EnumType[] c = new BlockSand.EnumType[values().length];
      private final int d;
      private final String e;
      private final MapColor f;
      private final String g;

      private EnumType(int var3, String var4, String var5, MapColor var6) {
         this.d = var3;
         this.e = var4;
         this.f = var6;
         this.g = var5;
      }

      public int a() {
         return this.d;
      }

      @Override
      public String toString() {
         return this.e;
      }

      public MapColor c() {
         return this.f;
      }

      public static BlockSand.EnumType a(int var0) {
         if (var0 < 0 || var0 >= c.length) {
            var0 = 0;
         }

         return c[var0];
      }

      @Override
      public String l() {
         return this.e;
      }

      public String d() {
         return this.g;
      }

      static {
         for (BlockSand.EnumType var3 : values()) {
            c[var3.a()] = var3;
         }
      }
   }
}
