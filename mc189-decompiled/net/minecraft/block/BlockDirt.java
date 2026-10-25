package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDirt extends Block {
   public static final PropertyEnum<BlockDirt.DirtType> a = PropertyEnum.a("variant", BlockDirt.DirtType.class);
   public static final PropertyBool b = PropertyBool.a("snowy");

   protected BlockDirt() {
      super(Material.c);
      this.j(this.M.b().a(a, BlockDirt.DirtType.a).a(b, false));
      this.a(CreativeTabs.b);
   }

   @Override
   public MapColor g(IBlockState var1) {
      return var1.b(a).d();
   }

   @Override
   public IBlockState a(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      if (var1.b(a) == BlockDirt.DirtType.c) {
         Block var4 = var2.p(var3.a()).c();
         var1 = var1.a(b, var4 == Blocks.aJ || var4 == Blocks.aH);
      }

      return var1;
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      var3.add(new ItemStack(this, 1, BlockDirt.DirtType.a.a()));
      var3.add(new ItemStack(this, 1, BlockDirt.DirtType.b.a()));
      var3.add(new ItemStack(this, 1, BlockDirt.DirtType.c.a()));
   }

   @Override
   public int j(World var1, BlockPos var2) {
      IBlockState var3 = var1.p(var2);
      return var3.c() != this ? 0 : var3.b(a).a();
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, BlockDirt.DirtType.a(var1));
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(a).a();
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a, b);
   }

   @Override
   public int a(IBlockState var1) {
      BlockDirt.DirtType var2 = var1.b(a);
      if (var2 == BlockDirt.DirtType.c) {
         var2 = BlockDirt.DirtType.a;
      }

      return var2.a();
   }

   public static enum DirtType implements IStringSerializable {
      a(0, "dirt", "default", MapColor.l),
      b(1, "coarse_dirt", "coarse", MapColor.l),
      c(2, "podzol", MapColor.J);

      private static final BlockDirt.DirtType[] d = new BlockDirt.DirtType[values().length];
      private final int e;
      private final String f;
      private final String g;
      private final MapColor h;

      private DirtType(int var3, String var4, MapColor var5) {
         this(var3, var4, var4, var5);
      }

      private DirtType(int var3, String var4, String var5, MapColor var6) {
         this.e = var3;
         this.f = var4;
         this.g = var5;
         this.h = var6;
      }

      public int a() {
         return this.e;
      }

      public String c() {
         return this.g;
      }

      public MapColor d() {
         return this.h;
      }

      @Override
      public String toString() {
         return this.f;
      }

      public static BlockDirt.DirtType a(int var0) {
         if (var0 < 0 || var0 >= d.length) {
            var0 = 0;
         }

         return d[var0];
      }

      @Override
      public String l() {
         return this.f;
      }

      static {
         for (BlockDirt.DirtType var3 : values()) {
            d[var3.a()] = var3;
         }
      }
   }
}
