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
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public abstract class BlockStoneSlabNew extends BlockSlab {
   public static final PropertyBool b = PropertyBool.a("seamless");
   public static final PropertyEnum<BlockStoneSlabNew.EnumType> N = PropertyEnum.a("variant", BlockStoneSlabNew.EnumType.class);

   public BlockStoneSlabNew() {
      super(Material.e);
      IBlockState var1 = this.M.b();
      if (this.l()) {
         var1 = var1.a(b, false);
      } else {
         var1 = var1.a(a, BlockSlab.EnumBlockHalf.b);
      }

      this.j(var1.a(N, BlockStoneSlabNew.EnumType.a));
      this.a(CreativeTabs.b);
   }

   @Override
   public String f() {
      return StatCollector.a(this.a() + ".red_sandstone.name");
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Item.a(Blocks.cP);
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return Item.a(Blocks.cP);
   }

   @Override
   public String b(int var1) {
      return super.a() + "." + BlockStoneSlabNew.EnumType.a(var1).d();
   }

   @Override
   public IProperty<?> n() {
      return N;
   }

   @Override
   public Object a(ItemStack var1) {
      return BlockStoneSlabNew.EnumType.a(var1.i() & 7);
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      if (var1 != Item.a(Blocks.cO)) {
         for (BlockStoneSlabNew.EnumType var7 : BlockStoneSlabNew.EnumType.values()) {
            var3.add(new ItemStack(var1, 1, var7.a()));
         }
      }
   }

   @Override
   public IBlockState a(int var1) {
      IBlockState var2 = this.Q().a(N, BlockStoneSlabNew.EnumType.a(var1 & 7));
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
   public MapColor g(IBlockState var1) {
      return var1.b(N).c();
   }

   @Override
   public int a(IBlockState var1) {
      return var1.b(N).a();
   }

   public static enum EnumType implements IStringSerializable {
      a(0, "red_sandstone", BlockSand.EnumType.b.c());

      private static final BlockStoneSlabNew.EnumType[] b = new BlockStoneSlabNew.EnumType[values().length];
      private final int c;
      private final String d;
      private final MapColor e;

      private EnumType(int var3, String var4, MapColor var5) {
         this.c = var3;
         this.d = var4;
         this.e = var5;
      }

      public int a() {
         return this.c;
      }

      public MapColor c() {
         return this.e;
      }

      @Override
      public String toString() {
         return this.d;
      }

      public static BlockStoneSlabNew.EnumType a(int var0) {
         if (var0 < 0 || var0 >= b.length) {
            var0 = 0;
         }

         return b[var0];
      }

      @Override
      public String l() {
         return this.d;
      }

      public String d() {
         return this.d;
      }

      static {
         for (BlockStoneSlabNew.EnumType var3 : values()) {
            b[var3.a()] = var3;
         }
      }
   }
}
