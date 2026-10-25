package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
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
import net.minecraft.world.World;

public abstract class BlockStoneSlab extends BlockSlab {
   public static final PropertyBool b = PropertyBool.a("seamless");
   public static final PropertyEnum<BlockStoneSlab.EnumType> N = PropertyEnum.a("variant", BlockStoneSlab.EnumType.class);

   public BlockStoneSlab() {
      super(Material.e);
      IBlockState var1 = this.M.b();
      if (this.l()) {
         var1 = var1.a(b, false);
      } else {
         var1 = var1.a(a, BlockSlab.EnumBlockHalf.b);
      }

      this.j(var1.a(N, BlockStoneSlab.EnumType.a));
      this.a(CreativeTabs.b);
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Item.a(Blocks.U);
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return Item.a(Blocks.U);
   }

   @Override
   public String b(int var1) {
      return super.a() + "." + BlockStoneSlab.EnumType.a(var1).d();
   }

   @Override
   public IProperty<?> n() {
      return N;
   }

   @Override
   public Object a(ItemStack var1) {
      return BlockStoneSlab.EnumType.a(var1.i() & 7);
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      if (var1 != Item.a(Blocks.T)) {
         for (BlockStoneSlab.EnumType var7 : BlockStoneSlab.EnumType.values()) {
            if (var7 != BlockStoneSlab.EnumType.c) {
               var3.add(new ItemStack(var1, 1, var7.a()));
            }
         }
      }
   }

   @Override
   public IBlockState a(int var1) {
      IBlockState var2 = this.Q().a(N, BlockStoneSlab.EnumType.a(var1 & 7));
      if (this.l()) {
         var2 = var2.a(b, (var1 & 8) != 0);
      } else {
         var2 = var2.a(a, (var1 & 8) == 0 ? BlockSlab.EnumBlockHalf.b : BlockSlab.EnumBlockHalf.a);
      }

      return var2;
   }

   @Override
   public int c(IBlockState var1) {
      int var2 = 0;
      var2 |= var1.b(N).a();
      if (this.l()) {
         if (var1.b(b)) {
            var2 |= 8;
         }
      } else if (var1.b(a) == BlockSlab.EnumBlockHalf.a) {
         var2 |= 8;
      }

      return var2;
   }

   @Override
   protected BlockState e() {
      return this.l() ? new BlockState(this, b, N) : new BlockState(this, a, N);
   }

   @Override
   public int a(IBlockState var1) {
      return var1.b(N).a();
   }

   @Override
   public MapColor g(IBlockState var1) {
      return var1.b(N).c();
   }

   public static enum EnumType implements IStringSerializable {
      a(0, MapColor.m, "stone"),
      b(1, MapColor.d, "sandstone", "sand"),
      c(2, MapColor.o, "wood_old", "wood"),
      d(3, MapColor.m, "cobblestone", "cobble"),
      e(4, MapColor.D, "brick"),
      f(5, MapColor.m, "stone_brick", "smoothStoneBrick"),
      g(6, MapColor.K, "nether_brick", "netherBrick"),
      h(7, MapColor.p, "quartz");

      private static final BlockStoneSlab.EnumType[] i = new BlockStoneSlab.EnumType[values().length];
      private final int j;
      private final MapColor k;
      private final String l;
      private final String m;

      private EnumType(int var3, MapColor var4, String var5) {
         this(var3, var4, var5, var5);
      }

      private EnumType(int var3, MapColor var4, String var5, String var6) {
         this.j = var3;
         this.k = var4;
         this.l = var5;
         this.m = var6;
      }

      public int a() {
         return this.j;
      }

      public MapColor c() {
         return this.k;
      }

      @Override
      public String toString() {
         return this.l;
      }

      public static BlockStoneSlab.EnumType a(int var0) {
         if (var0 < 0 || var0 >= i.length) {
            var0 = 0;
         }

         return i[var0];
      }

      @Override
      public String l() {
         return this.l;
      }

      public String d() {
         return this.m;
      }

      static {
         for (BlockStoneSlab.EnumType var3 : values()) {
            i[var3.a()] = var3;
         }
      }
   }
}
