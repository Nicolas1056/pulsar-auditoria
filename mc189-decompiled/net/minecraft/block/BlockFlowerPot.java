package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFlowerPot extends BlockContainer {
   public static final PropertyInteger a = PropertyInteger.a("legacy_data", 0, 15);
   public static final PropertyEnum<BlockFlowerPot.EnumFlowerType> b = PropertyEnum.a("contents", BlockFlowerPot.EnumFlowerType.class);

   public BlockFlowerPot() {
      super(Material.q);
      this.j(this.M.b().a(b, BlockFlowerPot.EnumFlowerType.a).a(a, 0));
      this.j();
   }

   @Override
   public String f() {
      return StatCollector.a("item.flowerPot.name");
   }

   @Override
   public void j() {
      float var1 = 0.375F;
      float var2 = var1 / 2.0F;
      this.a(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, var1, 0.5F + var2);
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public int b() {
      return 3;
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public int a(IBlockAccess var1, BlockPos var2, int var3) {
      TileEntity var4 = var1.s(var2);
      if (var4 instanceof TileEntityFlowerPot) {
         Item var5 = ((TileEntityFlowerPot)var4).b();
         if (var5 instanceof ItemBlock) {
            return Block.a(var5).a(var1, var2, var3);
         }
      }

      return 16777215;
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      ItemStack var9 = var4.bi.h();
      if (var9 != null && var9.b() instanceof ItemBlock) {
         TileEntityFlowerPot var10 = this.f(var1, var2);
         if (var10 == null) {
            return false;
         } else if (var10.b() != null) {
            return false;
         } else {
            Block var11 = Block.a(var9.b());
            if (!this.a(var11, var9.i())) {
               return false;
            } else {
               var10.a(var9.b(), var9.i());
               var10.p_();
               var1.h(var2);
               var4.b(StatList.T);
               if (!var4.bA.d && --var9.b <= 0) {
                  var4.bi.a(var4.bi.c, null);
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   private boolean a(Block var1, int var2) {
      return var1 == Blocks.N || var1 == Blocks.O || var1 == Blocks.aK || var1 == Blocks.P || var1 == Blocks.Q || var1 == Blocks.g || var1 == Blocks.I
         ? true
         : var1 == Blocks.H && var2 == BlockTallGrass.EnumType.c.a();
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      TileEntityFlowerPot var3 = this.f(var1, var2);
      return var3 != null && var3.b() != null ? var3.b() : Items.bQ;
   }

   @Override
   public int j(World var1, BlockPos var2) {
      TileEntityFlowerPot var3 = this.f(var1, var2);
      return var3 != null && var3.b() != null ? var3.c() : 0;
   }

   @Override
   public boolean M() {
      return true;
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      return super.d(var1, var2) && World.a(var1, var2.b());
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!World.a(var1, var2.b())) {
         this.b(var1, var2, var3, 0);
         var1.g(var2);
      }
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3) {
      TileEntityFlowerPot var4 = this.f(var1, var2);
      if (var4 != null && var4.b() != null) {
         a(var1, var2, new ItemStack(var4.b(), 1, var4.c()));
      }

      super.b(var1, var2, var3);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      super.a(var1, var2, var3, var4);
      if (var4.bA.d) {
         TileEntityFlowerPot var5 = this.f(var1, var2);
         if (var5 != null) {
            var5.a(null, 0);
         }
      }
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Items.bQ;
   }

   private TileEntityFlowerPot f(World var1, BlockPos var2) {
      TileEntity var3 = var1.s(var2);
      return var3 instanceof TileEntityFlowerPot ? (TileEntityFlowerPot)var3 : null;
   }

   @Override
   public TileEntity a(World var1, int var2) {
      Object var3 = null;
      int var4 = 0;
      switch (var2) {
         case 1:
            var3 = Blocks.O;
            var4 = BlockFlower.EnumFlowerType.b.b();
            break;
         case 2:
            var3 = Blocks.N;
            break;
         case 3:
            var3 = Blocks.g;
            var4 = BlockPlanks.EnumType.a.a();
            break;
         case 4:
            var3 = Blocks.g;
            var4 = BlockPlanks.EnumType.b.a();
            break;
         case 5:
            var3 = Blocks.g;
            var4 = BlockPlanks.EnumType.c.a();
            break;
         case 6:
            var3 = Blocks.g;
            var4 = BlockPlanks.EnumType.d.a();
            break;
         case 7:
            var3 = Blocks.Q;
            break;
         case 8:
            var3 = Blocks.P;
            break;
         case 9:
            var3 = Blocks.aK;
            break;
         case 10:
            var3 = Blocks.I;
            break;
         case 11:
            var3 = Blocks.H;
            var4 = BlockTallGrass.EnumType.c.a();
            break;
         case 12:
            var3 = Blocks.g;
            var4 = BlockPlanks.EnumType.e.a();
            break;
         case 13:
            var3 = Blocks.g;
            var4 = BlockPlanks.EnumType.f.a();
      }

      return new TileEntityFlowerPot(Item.a((Block)var3), var4);
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, b, a);
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(a);
   }

   @Override
   public IBlockState a(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      BlockFlowerPot.EnumFlowerType var4 = BlockFlowerPot.EnumFlowerType.a;
      TileEntity var5 = var2.s(var3);
      if (var5 instanceof TileEntityFlowerPot) {
         TileEntityFlowerPot var6 = (TileEntityFlowerPot)var5;
         Item var7 = var6.b();
         if (var7 instanceof ItemBlock) {
            int var8 = var6.c();
            Block var9 = Block.a(var7);
            if (var9 == Blocks.g) {
               switch (BlockPlanks.EnumType.a(var8)) {
                  case a:
                     var4 = BlockFlowerPot.EnumFlowerType.l;
                     break;
                  case b:
                     var4 = BlockFlowerPot.EnumFlowerType.m;
                     break;
                  case c:
                     var4 = BlockFlowerPot.EnumFlowerType.n;
                     break;
                  case d:
                     var4 = BlockFlowerPot.EnumFlowerType.o;
                     break;
                  case e:
                     var4 = BlockFlowerPot.EnumFlowerType.p;
                     break;
                  case f:
                     var4 = BlockFlowerPot.EnumFlowerType.q;
                     break;
                  default:
                     var4 = BlockFlowerPot.EnumFlowerType.a;
               }
            } else if (var9 == Blocks.H) {
               switch (var8) {
                  case 0:
                     var4 = BlockFlowerPot.EnumFlowerType.t;
                     break;
                  case 2:
                     var4 = BlockFlowerPot.EnumFlowerType.u;
                     break;
                  default:
                     var4 = BlockFlowerPot.EnumFlowerType.a;
               }
            } else if (var9 == Blocks.N) {
               var4 = BlockFlowerPot.EnumFlowerType.k;
            } else if (var9 == Blocks.O) {
               switch (BlockFlower.EnumFlowerType.a(BlockFlower.EnumFlowerColor.b, var8)) {
                  case b:
                     var4 = BlockFlowerPot.EnumFlowerType.b;
                     break;
                  case c:
                     var4 = BlockFlowerPot.EnumFlowerType.c;
                     break;
                  case d:
                     var4 = BlockFlowerPot.EnumFlowerType.d;
                     break;
                  case e:
                     var4 = BlockFlowerPot.EnumFlowerType.e;
                     break;
                  case f:
                     var4 = BlockFlowerPot.EnumFlowerType.f;
                     break;
                  case g:
                     var4 = BlockFlowerPot.EnumFlowerType.g;
                     break;
                  case h:
                     var4 = BlockFlowerPot.EnumFlowerType.h;
                     break;
                  case i:
                     var4 = BlockFlowerPot.EnumFlowerType.i;
                     break;
                  case j:
                     var4 = BlockFlowerPot.EnumFlowerType.j;
                     break;
                  default:
                     var4 = BlockFlowerPot.EnumFlowerType.a;
               }
            } else if (var9 == Blocks.Q) {
               var4 = BlockFlowerPot.EnumFlowerType.r;
            } else if (var9 == Blocks.P) {
               var4 = BlockFlowerPot.EnumFlowerType.s;
            } else if (var9 == Blocks.I) {
               var4 = BlockFlowerPot.EnumFlowerType.t;
            } else if (var9 == Blocks.aK) {
               var4 = BlockFlowerPot.EnumFlowerType.v;
            }
         }
      }

      return var1.a(b, var4);
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.c;
   }

   public static enum EnumFlowerType implements IStringSerializable {
      a("empty"),
      b("rose"),
      c("blue_orchid"),
      d("allium"),
      e("houstonia"),
      f("red_tulip"),
      g("orange_tulip"),
      h("white_tulip"),
      i("pink_tulip"),
      j("oxeye_daisy"),
      k("dandelion"),
      l("oak_sapling"),
      m("spruce_sapling"),
      n("birch_sapling"),
      o("jungle_sapling"),
      p("acacia_sapling"),
      q("dark_oak_sapling"),
      r("mushroom_red"),
      s("mushroom_brown"),
      t("dead_bush"),
      u("fern"),
      v("cactus");

      private final String w;

      private EnumFlowerType(String var3) {
         this.w = var3;
      }

      @Override
      public String toString() {
         return this.w;
      }

      @Override
      public String l() {
         return this.w;
      }
   }
}
