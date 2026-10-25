package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

public class BlockSilverfish extends Block {
   public static final PropertyEnum<BlockSilverfish.EnumType> a = PropertyEnum.a("variant", BlockSilverfish.EnumType.class);

   public BlockSilverfish() {
      super(Material.B);
      this.j(this.M.b().a(a, BlockSilverfish.EnumType.a));
      this.c(0.0F);
      this.a(CreativeTabs.c);
   }

   @Override
   public int a(Random var1) {
      return 0;
   }

   public static boolean d(IBlockState var0) {
      Block var1 = var0.c();
      return var0 == Blocks.b.Q().a(BlockStone.a, BlockStone.EnumType.a) || var1 == Blocks.e || var1 == Blocks.bf;
   }

   @Override
   protected ItemStack i(IBlockState var1) {
      switch ((BlockSilverfish.EnumType)var1.b(a)) {
         case b:
            return new ItemStack(Blocks.e);
         case c:
            return new ItemStack(Blocks.bf);
         case d:
            return new ItemStack(Blocks.bf, 1, BlockStoneBrick.EnumType.b.a());
         case e:
            return new ItemStack(Blocks.bf, 1, BlockStoneBrick.EnumType.c.a());
         case f:
            return new ItemStack(Blocks.bf, 1, BlockStoneBrick.EnumType.d.a());
         default:
            return new ItemStack(Blocks.b);
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      if (!var1.D && var1.Q().b("doTileDrops")) {
         EntitySilverfish var6 = new EntitySilverfish(var1);
         var6.b(var2.n() + 0.5, var2.o(), var2.p() + 0.5, 0.0F, 0.0F);
         var1.d(var6);
         var6.y();
      }
   }

   @Override
   public int j(World var1, BlockPos var2) {
      IBlockState var3 = var1.p(var2);
      return var3.c().c(var3);
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      for (BlockSilverfish.EnumType var7 : BlockSilverfish.EnumType.values()) {
         var3.add(new ItemStack(var1, 1, var7.a()));
      }
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, BlockSilverfish.EnumType.a(var1));
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
      a(0, "stone") {
         @Override
         public IBlockState d() {
            return Blocks.b.Q().a(BlockStone.a, BlockStone.EnumType.a);
         }
      },
      b(1, "cobblestone", "cobble") {
         @Override
         public IBlockState d() {
            return Blocks.e.Q();
         }
      },
      c(2, "stone_brick", "brick") {
         @Override
         public IBlockState d() {
            return Blocks.bf.Q().a(BlockStoneBrick.a, BlockStoneBrick.EnumType.a);
         }
      },
      d(3, "mossy_brick", "mossybrick") {
         @Override
         public IBlockState d() {
            return Blocks.bf.Q().a(BlockStoneBrick.a, BlockStoneBrick.EnumType.b);
         }
      },
      e(4, "cracked_brick", "crackedbrick") {
         @Override
         public IBlockState d() {
            return Blocks.bf.Q().a(BlockStoneBrick.a, BlockStoneBrick.EnumType.c);
         }
      },
      f(5, "chiseled_brick", "chiseledbrick") {
         @Override
         public IBlockState d() {
            return Blocks.bf.Q().a(BlockStoneBrick.a, BlockStoneBrick.EnumType.d);
         }
      };

      private static final BlockSilverfish.EnumType[] g = new BlockSilverfish.EnumType[values().length];
      private final int h;
      private final String i;
      private final String j;

      private EnumType(int var3, String var4) {
         this(var3, var4, var4);
      }

      private EnumType(int var3, String var4, String var5) {
         this.h = var3;
         this.i = var4;
         this.j = var5;
      }

      public int a() {
         return this.h;
      }

      @Override
      public String toString() {
         return this.i;
      }

      public static BlockSilverfish.EnumType a(int var0) {
         if (var0 < 0 || var0 >= g.length) {
            var0 = 0;
         }

         return g[var0];
      }

      @Override
      public String l() {
         return this.i;
      }

      public String c() {
         return this.j;
      }

      public abstract IBlockState d();

      public static BlockSilverfish.EnumType a(IBlockState var0) {
         for (BlockSilverfish.EnumType var4 : values()) {
            if (var0 == var4.d()) {
               return var4;
            }
         }

         return a;
      }

      static {
         for (BlockSilverfish.EnumType var3 : values()) {
            g[var3.a()] = var3;
         }
      }
   }
}
