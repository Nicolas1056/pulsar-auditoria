package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTallGrass extends BlockBush implements IGrowable {
   public static final PropertyEnum<BlockTallGrass.EnumType> a = PropertyEnum.a("type", BlockTallGrass.EnumType.class);

   protected BlockTallGrass() {
      super(Material.l);
      this.j(this.M.b().a(a, BlockTallGrass.EnumType.a));
      float var1 = 0.4F;
      this.a(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, 0.8F, 0.5F + var1);
   }

   @Override
   public int H() {
      return ColorizerGrass.a(0.5, 1.0);
   }

   @Override
   public boolean f(World var1, BlockPos var2, IBlockState var3) {
      return this.c(var1.p(var2.b()).c());
   }

   @Override
   public boolean a(World var1, BlockPos var2) {
      return true;
   }

   @Override
   public int h(IBlockState var1) {
      if (var1.c() != this) {
         return super.h(var1);
      } else {
         BlockTallGrass.EnumType var2 = var1.b(a);
         return var2 == BlockTallGrass.EnumType.a ? 16777215 : ColorizerGrass.a(0.5, 1.0);
      }
   }

   @Override
   public int a(IBlockAccess var1, BlockPos var2, int var3) {
      return var1.b(var2).b(var2);
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return var2.nextInt(8) == 0 ? Items.N : null;
   }

   @Override
   public int a(int var1, Random var2) {
      return 1 + var2.nextInt(var1 * 2 + 1);
   }

   @Override
   public void a(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, TileEntity var5) {
      if (!var1.D && var2.bZ() != null && var2.bZ().b() == Items.be) {
         var2.b(StatList.ab[Block.a(this)]);
         a(var1, var3, new ItemStack(Blocks.H, 1, var4.b(a).a()));
      } else {
         super.a(var1, var2, var3, var4, var5);
      }
   }

   @Override
   public int j(World var1, BlockPos var2) {
      IBlockState var3 = var1.p(var2);
      return var3.c().c(var3);
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      for (int var4 = 1; var4 < 3; var4++) {
         var3.add(new ItemStack(var1, 1, var4));
      }
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, boolean var4) {
      return var3.b(a) != BlockTallGrass.EnumType.a;
   }

   @Override
   public boolean a(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return true;
   }

   @Override
   public void b(World var1, Random var2, BlockPos var3, IBlockState var4) {
      BlockDoublePlant.EnumPlantType var5 = BlockDoublePlant.EnumPlantType.c;
      if (var4.b(a) == BlockTallGrass.EnumType.c) {
         var5 = BlockDoublePlant.EnumPlantType.d;
      }

      if (Blocks.cF.d(var1, var3)) {
         Blocks.cF.a(var1, var3, var5, 2);
      }
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, BlockTallGrass.EnumType.a(var1));
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
   public Block.EnumOffsetType R() {
      return Block.EnumOffsetType.c;
   }

   public static enum EnumType implements IStringSerializable {
      a(0, "dead_bush"),
      b(1, "tall_grass"),
      c(2, "fern");

      private static final BlockTallGrass.EnumType[] d = new BlockTallGrass.EnumType[values().length];
      private final int e;
      private final String f;

      private EnumType(int var3, String var4) {
         this.e = var3;
         this.f = var4;
      }

      public int a() {
         return this.e;
      }

      @Override
      public String toString() {
         return this.f;
      }

      public static BlockTallGrass.EnumType a(int var0) {
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
         for (BlockTallGrass.EnumType var3 : values()) {
            d[var3.a()] = var3;
         }
      }
   }
}
