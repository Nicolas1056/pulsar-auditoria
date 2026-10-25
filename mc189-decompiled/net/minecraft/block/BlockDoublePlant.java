package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;

public class BlockDoublePlant extends BlockBush implements IGrowable {
   public static final PropertyEnum<BlockDoublePlant.EnumPlantType> a = PropertyEnum.a("variant", BlockDoublePlant.EnumPlantType.class);
   public static final PropertyEnum<BlockDoublePlant.EnumBlockHalf> b = PropertyEnum.a("half", BlockDoublePlant.EnumBlockHalf.class);
   public static final PropertyEnum<EnumFacing> N = BlockDirectional.O;

   public BlockDoublePlant() {
      super(Material.l);
      this.j(this.M.b().a(a, BlockDoublePlant.EnumPlantType.a).a(b, BlockDoublePlant.EnumBlockHalf.b).a(N, EnumFacing.c));
      this.c(0.0F);
      this.a(h);
      this.c("doublePlant");
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public BlockDoublePlant.EnumPlantType e(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.p(var2);
      if (var3.c() == this) {
         var3 = this.a(var3, var1, var2);
         return var3.b(a);
      } else {
         return BlockDoublePlant.EnumPlantType.d;
      }
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      return super.d(var1, var2) && var1.d(var2.a());
   }

   @Override
   public boolean a(World var1, BlockPos var2) {
      IBlockState var3 = var1.p(var2);
      if (var3.c() != this) {
         return true;
      } else {
         BlockDoublePlant.EnumPlantType var4 = this.a(var3, var1, var2).b(a);
         return var4 == BlockDoublePlant.EnumPlantType.d || var4 == BlockDoublePlant.EnumPlantType.c;
      }
   }

   @Override
   protected void e(World var1, BlockPos var2, IBlockState var3) {
      if (!this.f(var1, var2, var3)) {
         boolean var4 = var3.b(b) == BlockDoublePlant.EnumBlockHalf.a;
         BlockPos var5 = var4 ? var2 : var2.a();
         BlockPos var6 = var4 ? var2.b() : var2;
         Object var7 = var4 ? this : var1.p(var5).c();
         Object var8 = var4 ? var1.p(var6).c() : this;
         if (var7 == this) {
            var1.a(var5, Blocks.a.Q(), 2);
         }

         if (var8 == this) {
            var1.a(var6, Blocks.a.Q(), 3);
            if (!var4) {
               this.b(var1, var6, var3, 0);
            }
         }
      }
   }

   @Override
   public boolean f(World var1, BlockPos var2, IBlockState var3) {
      if (var3.b(b) == BlockDoublePlant.EnumBlockHalf.a) {
         return var1.p(var2.b()).c() == this;
      } else {
         IBlockState var4 = var1.p(var2.a());
         return var4.c() == this && super.f(var1, var2, var4);
      }
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      if (var1.b(b) == BlockDoublePlant.EnumBlockHalf.a) {
         return null;
      } else {
         BlockDoublePlant.EnumPlantType var4 = var1.b(a);
         if (var4 == BlockDoublePlant.EnumPlantType.d) {
            return null;
         } else if (var4 == BlockDoublePlant.EnumPlantType.c) {
            return var2.nextInt(8) == 0 ? Items.N : null;
         } else {
            return Item.a(this);
         }
      }
   }

   @Override
   public int a(IBlockState var1) {
      return var1.b(b) != BlockDoublePlant.EnumBlockHalf.a && var1.b(a) != BlockDoublePlant.EnumPlantType.c ? var1.b(a).a() : 0;
   }

   @Override
   public int a(IBlockAccess var1, BlockPos var2, int var3) {
      BlockDoublePlant.EnumPlantType var4 = this.e(var1, var2);
      return var4 != BlockDoublePlant.EnumPlantType.c && var4 != BlockDoublePlant.EnumPlantType.d ? 16777215 : BiomeColorHelper.a(var1, var2);
   }

   public void a(World var1, BlockPos var2, BlockDoublePlant.EnumPlantType var3, int var4) {
      var1.a(var2, this.Q().a(b, BlockDoublePlant.EnumBlockHalf.b).a(a, var3), var4);
      var1.a(var2.a(), this.Q().a(b, BlockDoublePlant.EnumBlockHalf.a), var4);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      var1.a(var2.a(), this.Q().a(b, BlockDoublePlant.EnumBlockHalf.a), 2);
   }

   @Override
   public void a(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, TileEntity var5) {
      if (var1.D || var2.bZ() == null || var2.bZ().b() != Items.be || var4.b(b) != BlockDoublePlant.EnumBlockHalf.b || !this.b(var1, var3, var4, var2)) {
         super.a(var1, var2, var3, var4, var5);
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      if (var3.b(b) == BlockDoublePlant.EnumBlockHalf.a) {
         if (var1.p(var2.b()).c() == this) {
            if (!var4.bA.d) {
               IBlockState var5 = var1.p(var2.b());
               BlockDoublePlant.EnumPlantType var6 = var5.b(a);
               if (var6 != BlockDoublePlant.EnumPlantType.d && var6 != BlockDoublePlant.EnumPlantType.c) {
                  var1.b(var2.b(), true);
               } else if (!var1.D) {
                  if (var4.bZ() != null && var4.bZ().b() == Items.be) {
                     this.b(var1, var2, var5, var4);
                     var1.g(var2.b());
                  } else {
                     var1.b(var2.b(), true);
                  }
               } else {
                  var1.g(var2.b());
               }
            } else {
               var1.g(var2.b());
            }
         }
      } else if (var4.bA.d && var1.p(var2.a()).c() == this) {
         var1.a(var2.a(), Blocks.a.Q(), 2);
      }

      super.a(var1, var2, var3, var4);
   }

   private boolean b(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      BlockDoublePlant.EnumPlantType var5 = var3.b(a);
      if (var5 != BlockDoublePlant.EnumPlantType.d && var5 != BlockDoublePlant.EnumPlantType.c) {
         return false;
      } else {
         var4.b(StatList.ab[Block.a(this)]);
         int var6 = (var5 == BlockDoublePlant.EnumPlantType.c ? BlockTallGrass.EnumType.b : BlockTallGrass.EnumType.c).a();
         a(var1, var2, new ItemStack(Blocks.H, 2, var6));
         return true;
      }
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      for (BlockDoublePlant.EnumPlantType var7 : BlockDoublePlant.EnumPlantType.values()) {
         var3.add(new ItemStack(var1, 1, var7.a()));
      }
   }

   @Override
   public int j(World var1, BlockPos var2) {
      return this.e(var1, var2).a();
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, boolean var4) {
      BlockDoublePlant.EnumPlantType var5 = this.e(var1, var2);
      return var5 != BlockDoublePlant.EnumPlantType.c && var5 != BlockDoublePlant.EnumPlantType.d;
   }

   @Override
   public boolean a(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return true;
   }

   @Override
   public void b(World var1, Random var2, BlockPos var3, IBlockState var4) {
      a(var1, var3, new ItemStack(this, 1, this.e(var1, var3).a()));
   }

   @Override
   public IBlockState a(int var1) {
      return (var1 & 8) > 0
         ? this.Q().a(b, BlockDoublePlant.EnumBlockHalf.a)
         : this.Q().a(b, BlockDoublePlant.EnumBlockHalf.b).a(a, BlockDoublePlant.EnumPlantType.a(var1 & 7));
   }

   @Override
   public IBlockState a(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      if (var1.b(b) == BlockDoublePlant.EnumBlockHalf.a) {
         IBlockState var4 = var2.p(var3.b());
         if (var4.c() == this) {
            var1 = var1.a(a, var4.b(a));
         }
      }

      return var1;
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(b) == BlockDoublePlant.EnumBlockHalf.a ? 8 | var1.b(N).b() : var1.b(a).a();
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, b, a, N);
   }

   @Override
   public Block.EnumOffsetType R() {
      return Block.EnumOffsetType.b;
   }

   public static enum EnumBlockHalf implements IStringSerializable {
      a,
      b;

      @Override
      public String toString() {
         return this.l();
      }

      @Override
      public String l() {
         return this == a ? "upper" : "lower";
      }
   }

   public static enum EnumPlantType implements IStringSerializable {
      a(0, "sunflower"),
      b(1, "syringa"),
      c(2, "double_grass", "grass"),
      d(3, "double_fern", "fern"),
      e(4, "double_rose", "rose"),
      f(5, "paeonia");

      private static final BlockDoublePlant.EnumPlantType[] g = new BlockDoublePlant.EnumPlantType[values().length];
      private final int h;
      private final String i;
      private final String j;

      private EnumPlantType(int var3, String var4) {
         this(var3, var4, var4);
      }

      private EnumPlantType(int var3, String var4, String var5) {
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

      public static BlockDoublePlant.EnumPlantType a(int var0) {
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

      static {
         for (BlockDoublePlant.EnumPlantType var3 : values()) {
            g[var3.a()] = var3;
         }
      }
   }
}
