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

public class BlockRedSandstone extends Block {
   public static final PropertyEnum<BlockRedSandstone.EnumType> a = PropertyEnum.a("type", BlockRedSandstone.EnumType.class);

   public BlockRedSandstone() {
      super(Material.e, BlockSand.EnumType.b.c());
      this.j(this.M.b().a(a, BlockRedSandstone.EnumType.a));
      this.a(CreativeTabs.b);
   }

   @Override
   public int a(IBlockState var1) {
      return var1.b(a).a();
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      for (BlockRedSandstone.EnumType var7 : BlockRedSandstone.EnumType.values()) {
         var3.add(new ItemStack(var1, 1, var7.a()));
      }
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, BlockRedSandstone.EnumType.a(var1));
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
      a(0, "red_sandstone", "default"),
      b(1, "chiseled_red_sandstone", "chiseled"),
      c(2, "smooth_red_sandstone", "smooth");

      private static final BlockRedSandstone.EnumType[] d = new BlockRedSandstone.EnumType[values().length];
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

      public static BlockRedSandstone.EnumType a(int var0) {
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
         for (BlockRedSandstone.EnumType var3 : values()) {
            d[var3.a()] = var3;
         }
      }
   }
}
