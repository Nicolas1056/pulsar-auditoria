package net.minecraft.client.renderer;

import com.google.common.collect.Maps;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockDropper;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStem;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlabNew;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockWall;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.BlockStateMapper;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;

public class BlockModelShapes {
   private final Map<IBlockState, IBakedModel> a = Maps.newIdentityHashMap();
   private final BlockStateMapper b = new BlockStateMapper();
   private final ModelManager c;

   public BlockModelShapes(ModelManager var1) {
      this.c = var1;
      this.d();
   }

   public BlockStateMapper a() {
      return this.b;
   }

   public TextureAtlasSprite a(IBlockState var1) {
      Block var2 = var1.c();
      IBakedModel var3 = this.b(var1);
      if (var3 == null || var3 == this.c.a()) {
         if (var2 == Blocks.ax || var2 == Blocks.an || var2 == Blocks.ae || var2 == Blocks.cg || var2 == Blocks.cK || var2 == Blocks.cL) {
            return this.c.b().a("minecraft:blocks/planks_oak");
         }

         if (var2 == Blocks.bQ) {
            return this.c.b().a("minecraft:blocks/obsidian");
         }

         if (var2 == Blocks.k || var2 == Blocks.l) {
            return this.c.b().a("minecraft:blocks/lava_still");
         }

         if (var2 == Blocks.i || var2 == Blocks.j) {
            return this.c.b().a("minecraft:blocks/water_still");
         }

         if (var2 == Blocks.ce) {
            return this.c.b().a("minecraft:blocks/soul_sand");
         }

         if (var2 == Blocks.cv) {
            return this.c.b().a("minecraft:items/barrier");
         }
      }

      if (var3 == null) {
         var3 = this.c.a();
      }

      return var3.e();
   }

   public IBakedModel b(IBlockState var1) {
      IBakedModel var2 = this.a.get(var1);
      if (var2 == null) {
         var2 = this.c.a();
      }

      return var2;
   }

   public ModelManager b() {
      return this.c;
   }

   public void c() {
      this.a.clear();

      for (Entry var2 : this.b.a().entrySet()) {
         this.a.put((IBlockState)var2.getKey(), this.c.a((ModelResourceLocation)var2.getValue()));
      }
   }

   public void a(Block var1, IStateMapper var2) {
      this.b.a(var1, var2);
   }

   public void a(Block... var1) {
      this.b.a(var1);
   }

   private void d() {
      this.a(
         Blocks.a,
         Blocks.i,
         Blocks.j,
         Blocks.k,
         Blocks.l,
         Blocks.M,
         Blocks.ae,
         Blocks.bQ,
         Blocks.cg,
         Blocks.an,
         Blocks.ce,
         Blocks.bF,
         Blocks.cv,
         Blocks.ax,
         Blocks.cL,
         Blocks.cK
      );
      this.a(Blocks.b, new StateMap.Builder().a(BlockStone.a).a());
      this.a(Blocks.cI, new StateMap.Builder().a(BlockPrismarine.a).a());
      this.a(Blocks.t, new StateMap.Builder().a(BlockOldLeaf.Q).a("_leaves").a(BlockLeaves.b, BlockLeaves.a).a());
      this.a(Blocks.u, new StateMap.Builder().a(BlockNewLeaf.Q).a("_leaves").a(BlockLeaves.b, BlockLeaves.a).a());
      this.a(Blocks.aK, new StateMap.Builder().a(BlockCactus.a).a());
      this.a(Blocks.aM, new StateMap.Builder().a(BlockReed.a).a());
      this.a(Blocks.aN, new StateMap.Builder().a(BlockJukebox.a).a());
      this.a(Blocks.bX, new StateMap.Builder().a(BlockCommandBlock.a).a());
      this.a(Blocks.bZ, new StateMap.Builder().a(BlockWall.Q).a("_wall").a());
      this.a(Blocks.cF, new StateMap.Builder().a(BlockDoublePlant.a).a(BlockDoublePlant.N).a());
      this.a(Blocks.bo, new StateMap.Builder().a(BlockFenceGate.b).a());
      this.a(Blocks.bp, new StateMap.Builder().a(BlockFenceGate.b).a());
      this.a(Blocks.bq, new StateMap.Builder().a(BlockFenceGate.b).a());
      this.a(Blocks.br, new StateMap.Builder().a(BlockFenceGate.b).a());
      this.a(Blocks.bs, new StateMap.Builder().a(BlockFenceGate.b).a());
      this.a(Blocks.bt, new StateMap.Builder().a(BlockFenceGate.b).a());
      this.a(Blocks.bS, new StateMap.Builder().a(BlockTripWire.O, BlockTripWire.a).a());
      this.a(Blocks.bL, new StateMap.Builder().a(BlockPlanks.a).a("_double_slab").a());
      this.a(Blocks.bM, new StateMap.Builder().a(BlockPlanks.a).a("_slab").a());
      this.a(Blocks.W, new StateMap.Builder().a(BlockTNT.a).a());
      this.a(Blocks.ab, new StateMap.Builder().a(BlockFire.a).a());
      this.a(Blocks.af, new StateMap.Builder().a(BlockRedstoneWire.P).a());
      this.a(Blocks.ao, new StateMap.Builder().a(BlockDoor.O).a());
      this.a(Blocks.ap, new StateMap.Builder().a(BlockDoor.O).a());
      this.a(Blocks.aq, new StateMap.Builder().a(BlockDoor.O).a());
      this.a(Blocks.ar, new StateMap.Builder().a(BlockDoor.O).a());
      this.a(Blocks.as, new StateMap.Builder().a(BlockDoor.O).a());
      this.a(Blocks.at, new StateMap.Builder().a(BlockDoor.O).a());
      this.a(Blocks.aA, new StateMap.Builder().a(BlockDoor.O).a());
      this.a(Blocks.L, new StateMap.Builder().a(BlockColored.a).a("_wool").a());
      this.a(Blocks.cy, new StateMap.Builder().a(BlockColored.a).a("_carpet").a());
      this.a(Blocks.cu, new StateMap.Builder().a(BlockColored.a).a("_stained_hardened_clay").a());
      this.a(Blocks.cH, new StateMap.Builder().a(BlockColored.a).a("_stained_glass_pane").a());
      this.a(Blocks.cG, new StateMap.Builder().a(BlockColored.a).a("_stained_glass").a());
      this.a(Blocks.A, new StateMap.Builder().a(BlockSandStone.a).a());
      this.a(Blocks.cM, new StateMap.Builder().a(BlockRedSandstone.a).a());
      this.a(Blocks.H, new StateMap.Builder().a(BlockTallGrass.a).a());
      this.a(Blocks.C, new StateMap.Builder().a(BlockBed.b).a());
      this.a(Blocks.N, new StateMap.Builder().a(Blocks.N.n()).a());
      this.a(Blocks.O, new StateMap.Builder().a(Blocks.O.n()).a());
      this.a(Blocks.U, new StateMap.Builder().a(BlockStoneSlab.N).a("_slab").a());
      this.a(Blocks.cP, new StateMap.Builder().a(BlockStoneSlabNew.N).a("_slab").a());
      this.a(Blocks.be, new StateMap.Builder().a(BlockSilverfish.a).a("_monster_egg").a());
      this.a(Blocks.bf, new StateMap.Builder().a(BlockStoneBrick.a).a());
      this.a(Blocks.z, new StateMap.Builder().a(BlockDispenser.b).a());
      this.a(Blocks.ct, new StateMap.Builder().a(BlockDropper.b).a());
      this.a(Blocks.r, new StateMap.Builder().a(BlockOldLog.b).a("_log").a());
      this.a(Blocks.s, new StateMap.Builder().a(BlockNewLog.b).a("_log").a());
      this.a(Blocks.f, new StateMap.Builder().a(BlockPlanks.a).a("_planks").a());
      this.a(Blocks.g, new StateMap.Builder().a(BlockSapling.a).a("_sapling").a());
      this.a(Blocks.m, new StateMap.Builder().a(BlockSand.a).a());
      this.a(Blocks.cp, new StateMap.Builder().a(BlockHopper.b).a());
      this.a(Blocks.ca, new StateMap.Builder().a(BlockFlowerPot.a).a());
      this.a(Blocks.cq, new StateMapperBase() {
         @Override
         protected ModelResourceLocation a(IBlockState var1) {
            BlockQuartz.EnumType var2 = var1.b(BlockQuartz.a);
            switch (var2) {
               case a:
               default:
                  return new ModelResourceLocation("quartz_block", "normal");
               case b:
                  return new ModelResourceLocation("chiseled_quartz_block", "normal");
               case c:
                  return new ModelResourceLocation("quartz_column", "axis=y");
               case d:
                  return new ModelResourceLocation("quartz_column", "axis=x");
               case e:
                  return new ModelResourceLocation("quartz_column", "axis=z");
            }
         }
      });
      this.a(Blocks.I, new StateMapperBase() {
         @Override
         protected ModelResourceLocation a(IBlockState var1) {
            return new ModelResourceLocation("dead_bush", "normal");
         }
      });
      this.a(Blocks.bl, new StateMapperBase() {
         @Override
         protected ModelResourceLocation a(IBlockState var1) {
            LinkedHashMap var2 = Maps.newLinkedHashMap(var1.b());
            if (var1.b(BlockStem.b) != EnumFacing.b) {
               var2.remove(BlockStem.a);
            }

            return new ModelResourceLocation(Block.c.c(var1.c()), this.a(var2));
         }
      });
      this.a(Blocks.bm, new StateMapperBase() {
         @Override
         protected ModelResourceLocation a(IBlockState var1) {
            LinkedHashMap var2 = Maps.newLinkedHashMap(var1.b());
            if (var1.b(BlockStem.b) != EnumFacing.b) {
               var2.remove(BlockStem.a);
            }

            return new ModelResourceLocation(Block.c.c(var1.c()), this.a(var2));
         }
      });
      this.a(Blocks.d, new StateMapperBase() {
         @Override
         protected ModelResourceLocation a(IBlockState var1) {
            LinkedHashMap var2 = Maps.newLinkedHashMap(var1.b());
            String var3 = BlockDirt.a.a((Comparable)var2.remove(BlockDirt.a));
            if (BlockDirt.DirtType.c != var1.b(BlockDirt.a)) {
               var2.remove(BlockDirt.b);
            }

            return new ModelResourceLocation(var3, this.a(var2));
         }
      });
      this.a(Blocks.T, new StateMapperBase() {
         @Override
         protected ModelResourceLocation a(IBlockState var1) {
            LinkedHashMap var2 = Maps.newLinkedHashMap(var1.b());
            String var3 = BlockStoneSlab.N.a((Comparable)var2.remove(BlockStoneSlab.N));
            var2.remove(BlockStoneSlab.b);
            String var4 = var1.b(BlockStoneSlab.b) ? "all" : "normal";
            return new ModelResourceLocation(var3 + "_double_slab", var4);
         }
      });
      this.a(Blocks.cO, new StateMapperBase() {
         @Override
         protected ModelResourceLocation a(IBlockState var1) {
            LinkedHashMap var2 = Maps.newLinkedHashMap(var1.b());
            String var3 = BlockStoneSlabNew.N.a((Comparable)var2.remove(BlockStoneSlabNew.N));
            var2.remove(BlockStoneSlab.b);
            String var4 = var1.b(BlockStoneSlabNew.b) ? "all" : "normal";
            return new ModelResourceLocation(var3 + "_double_slab", var4);
         }
      });
   }
}
