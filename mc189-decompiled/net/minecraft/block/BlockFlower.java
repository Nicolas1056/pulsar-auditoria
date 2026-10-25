package net.minecraft.block;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public abstract class BlockFlower extends BlockBush {
   protected PropertyEnum<BlockFlower.EnumFlowerType> a;

   protected BlockFlower() {
      this.j(this.M.b().a(this.n(), this.l() == BlockFlower.EnumFlowerColor.b ? BlockFlower.EnumFlowerType.b : BlockFlower.EnumFlowerType.a));
   }

   @Override
   public int a(IBlockState var1) {
      return var1.b(this.n()).b();
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      for (BlockFlower.EnumFlowerType var7 : BlockFlower.EnumFlowerType.a(this.l())) {
         var3.add(new ItemStack(var1, 1, var7.b()));
      }
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(this.n(), BlockFlower.EnumFlowerType.a(this.l(), var1));
   }

   public abstract BlockFlower.EnumFlowerColor l();

   public IProperty<BlockFlower.EnumFlowerType> n() {
      if (this.a == null) {
         this.a = PropertyEnum.a("type", BlockFlower.EnumFlowerType.class, new Predicate<BlockFlower.EnumFlowerType>() {
            public boolean a(BlockFlower.EnumFlowerType var1) {
               return var1.a() == BlockFlower.this.l();
            }
         });
      }

      return this.a;
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(this.n()).b();
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, this.n());
   }

   @Override
   public Block.EnumOffsetType R() {
      return Block.EnumOffsetType.b;
   }

   public static enum EnumFlowerColor {
      a,
      b;

      public BlockFlower a() {
         return this == a ? Blocks.N : Blocks.O;
      }
   }

   public static enum EnumFlowerType implements IStringSerializable {
      a(BlockFlower.EnumFlowerColor.a, 0, "dandelion"),
      b(BlockFlower.EnumFlowerColor.b, 0, "poppy"),
      c(BlockFlower.EnumFlowerColor.b, 1, "blue_orchid", "blueOrchid"),
      d(BlockFlower.EnumFlowerColor.b, 2, "allium"),
      e(BlockFlower.EnumFlowerColor.b, 3, "houstonia"),
      f(BlockFlower.EnumFlowerColor.b, 4, "red_tulip", "tulipRed"),
      g(BlockFlower.EnumFlowerColor.b, 5, "orange_tulip", "tulipOrange"),
      h(BlockFlower.EnumFlowerColor.b, 6, "white_tulip", "tulipWhite"),
      i(BlockFlower.EnumFlowerColor.b, 7, "pink_tulip", "tulipPink"),
      j(BlockFlower.EnumFlowerColor.b, 8, "oxeye_daisy", "oxeyeDaisy");

      private static final BlockFlower.EnumFlowerType[][] k = new BlockFlower.EnumFlowerType[BlockFlower.EnumFlowerColor.values().length][];
      private final BlockFlower.EnumFlowerColor l;
      private final int m;
      private final String n;
      private final String o;

      private EnumFlowerType(BlockFlower.EnumFlowerColor var3, int var4, String var5) {
         this(var3, var4, var5, var5);
      }

      private EnumFlowerType(BlockFlower.EnumFlowerColor var3, int var4, String var5, String var6) {
         this.l = var3;
         this.m = var4;
         this.n = var5;
         this.o = var6;
      }

      public BlockFlower.EnumFlowerColor a() {
         return this.l;
      }

      public int b() {
         return this.m;
      }

      public static BlockFlower.EnumFlowerType a(BlockFlower.EnumFlowerColor var0, int var1) {
         BlockFlower.EnumFlowerType[] var2 = k[var0.ordinal()];
         if (var1 < 0 || var1 >= var2.length) {
            var1 = 0;
         }

         return var2[var1];
      }

      public static BlockFlower.EnumFlowerType[] a(BlockFlower.EnumFlowerColor var0) {
         return k[var0.ordinal()];
      }

      @Override
      public String toString() {
         return this.n;
      }

      @Override
      public String l() {
         return this.n;
      }

      public String d() {
         return this.o;
      }

      static {
         for (final BlockFlower.EnumFlowerColor var3 : BlockFlower.EnumFlowerColor.values()) {
            Collection var4 = Collections2.filter(Lists.newArrayList(values()), new Predicate<BlockFlower.EnumFlowerType>() {
               public boolean a(BlockFlower.EnumFlowerType var1) {
                  return var1.a() == var3;
               }
            });
            k[var3.ordinal()] = var4.toArray(new BlockFlower.EnumFlowerType[var4.size()]);
         }
      }
   }
}
