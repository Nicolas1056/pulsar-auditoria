package net.minecraft.world.biome;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenSwamp;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BiomeGenBase {
   private static final Logger aD = LogManager.getLogger();
   protected static final BiomeGenBase.Height a = new BiomeGenBase.Height(0.1F, 0.2F);
   protected static final BiomeGenBase.Height b = new BiomeGenBase.Height(-0.5F, 0.0F);
   protected static final BiomeGenBase.Height c = new BiomeGenBase.Height(-1.0F, 0.1F);
   protected static final BiomeGenBase.Height d = new BiomeGenBase.Height(-1.8F, 0.1F);
   protected static final BiomeGenBase.Height e = new BiomeGenBase.Height(0.125F, 0.05F);
   protected static final BiomeGenBase.Height f = new BiomeGenBase.Height(0.2F, 0.2F);
   protected static final BiomeGenBase.Height g = new BiomeGenBase.Height(0.45F, 0.3F);
   protected static final BiomeGenBase.Height h = new BiomeGenBase.Height(1.5F, 0.025F);
   protected static final BiomeGenBase.Height i = new BiomeGenBase.Height(1.0F, 0.5F);
   protected static final BiomeGenBase.Height j = new BiomeGenBase.Height(0.0F, 0.025F);
   protected static final BiomeGenBase.Height k = new BiomeGenBase.Height(0.1F, 0.8F);
   protected static final BiomeGenBase.Height l = new BiomeGenBase.Height(0.2F, 0.3F);
   protected static final BiomeGenBase.Height m = new BiomeGenBase.Height(-0.2F, 0.1F);
   private static final BiomeGenBase[] aE = new BiomeGenBase[256];
   public static final Set<BiomeGenBase> n = Sets.newHashSet();
   public static final Map<String, BiomeGenBase> o = Maps.newHashMap();
   public static final BiomeGenBase p = new BiomeGenOcean(0).b(112).a("Ocean").a(c);
   public static final BiomeGenBase q = new BiomeGenPlains(1).b(9286496).a("Plains");
   public static final BiomeGenBase r = new BiomeGenDesert(2).b(16421912).a("Desert").b().a(2.0F, 0.0F).a(e);
   public static final BiomeGenBase s = new BiomeGenHills(3, false).b(6316128).a("Extreme Hills").a(i).a(0.2F, 0.3F);
   public static final BiomeGenBase t = new BiomeGenForest(4, 0).b(353825).a("Forest");
   public static final BiomeGenBase u = new BiomeGenTaiga(5, 0).b(747097).a("Taiga").a(5159473).a(0.25F, 0.8F).a(f);
   public static final BiomeGenBase v = new BiomeGenSwamp(6).b(522674).a("Swampland").a(9154376).a(m).a(0.8F, 0.9F);
   public static final BiomeGenBase w = new BiomeGenRiver(7).b(255).a("River").a(b);
   public static final BiomeGenBase x = new BiomeGenHell(8).b(16711680).a("Hell").b().a(2.0F, 0.0F);
   public static final BiomeGenBase y = new BiomeGenEnd(9).b(8421631).a("The End").b();
   public static final BiomeGenBase z = new BiomeGenOcean(10).b(9474208).a("FrozenOcean").c().a(c).a(0.0F, 0.5F);
   public static final BiomeGenBase A = new BiomeGenRiver(11).b(10526975).a("FrozenRiver").c().a(b).a(0.0F, 0.5F);
   public static final BiomeGenBase B = new BiomeGenSnow(12, false).b(16777215).a("Ice Plains").c().a(0.0F, 0.5F).a(e);
   public static final BiomeGenBase C = new BiomeGenSnow(13, false).b(10526880).a("Ice Mountains").c().a(g).a(0.0F, 0.5F);
   public static final BiomeGenBase D = new BiomeGenMushroomIsland(14).b(16711935).a("MushroomIsland").a(0.9F, 1.0F).a(l);
   public static final BiomeGenBase E = new BiomeGenMushroomIsland(15).b(10486015).a("MushroomIslandShore").a(0.9F, 1.0F).a(j);
   public static final BiomeGenBase F = new BiomeGenBeach(16).b(16440917).a("Beach").a(0.8F, 0.4F).a(j);
   public static final BiomeGenBase G = new BiomeGenDesert(17).b(13786898).a("DesertHills").b().a(2.0F, 0.0F).a(g);
   public static final BiomeGenBase H = new BiomeGenForest(18, 0).b(2250012).a("ForestHills").a(g);
   public static final BiomeGenBase I = new BiomeGenTaiga(19, 0).b(1456435).a("TaigaHills").a(5159473).a(0.25F, 0.8F).a(g);
   public static final BiomeGenBase J = new BiomeGenHills(20, true).b(7501978).a("Extreme Hills Edge").a(i.a()).a(0.2F, 0.3F);
   public static final BiomeGenBase K = new BiomeGenJungle(21, false).b(5470985).a("Jungle").a(5470985).a(0.95F, 0.9F);
   public static final BiomeGenBase L = new BiomeGenJungle(22, false).b(2900485).a("JungleHills").a(5470985).a(0.95F, 0.9F).a(g);
   public static final BiomeGenBase M = new BiomeGenJungle(23, true).b(6458135).a("JungleEdge").a(5470985).a(0.95F, 0.8F);
   public static final BiomeGenBase N = new BiomeGenOcean(24).b(48).a("Deep Ocean").a(d);
   public static final BiomeGenBase O = new BiomeGenStoneBeach(25).b(10658436).a("Stone Beach").a(0.2F, 0.3F).a(k);
   public static final BiomeGenBase P = new BiomeGenBeach(26).b(16445632).a("Cold Beach").a(0.05F, 0.3F).a(j).c();
   public static final BiomeGenBase Q = new BiomeGenForest(27, 2).a("Birch Forest").b(3175492);
   public static final BiomeGenBase R = new BiomeGenForest(28, 2).a("Birch Forest Hills").b(2055986).a(g);
   public static final BiomeGenBase S = new BiomeGenForest(29, 3).b(4215066).a("Roofed Forest");
   public static final BiomeGenBase T = new BiomeGenTaiga(30, 0).b(3233098).a("Cold Taiga").a(5159473).c().a(-0.5F, 0.4F).a(f).c(16777215);
   public static final BiomeGenBase U = new BiomeGenTaiga(31, 0).b(2375478).a("Cold Taiga Hills").a(5159473).c().a(-0.5F, 0.4F).a(g).c(16777215);
   public static final BiomeGenBase V = new BiomeGenTaiga(32, 1).b(5858897).a("Mega Taiga").a(5159473).a(0.3F, 0.8F).a(f);
   public static final BiomeGenBase W = new BiomeGenTaiga(33, 1).b(4542270).a("Mega Taiga Hills").a(5159473).a(0.3F, 0.8F).a(g);
   public static final BiomeGenBase X = new BiomeGenHills(34, true).b(5271632).a("Extreme Hills+").a(i).a(0.2F, 0.3F);
   public static final BiomeGenBase Y = new BiomeGenSavanna(35).b(12431967).a("Savanna").a(1.2F, 0.0F).b().a(e);
   public static final BiomeGenBase Z = new BiomeGenSavanna(36).b(10984804).a("Savanna Plateau").a(1.0F, 0.0F).b().a(h);
   public static final BiomeGenBase aa = new BiomeGenMesa(37, false, false).b(14238997).a("Mesa");
   public static final BiomeGenBase ab = new BiomeGenMesa(38, false, true).b(11573093).a("Mesa Plateau F").a(h);
   public static final BiomeGenBase ac = new BiomeGenMesa(39, false, false).b(13274213).a("Mesa Plateau").a(h);
   public static final BiomeGenBase ad = p;
   protected static final NoiseGeneratorPerlin ae;
   protected static final NoiseGeneratorPerlin af;
   protected static final WorldGenDoublePlant ag;
   public String ah;
   public int ai;
   public int aj;
   public IBlockState ak = Blocks.c.Q();
   public IBlockState al = Blocks.d.Q();
   public int am = 5169201;
   public float an = a.a;
   public float ao = a.b;
   public float ap = 0.5F;
   public float aq = 0.5F;
   public int ar = 16777215;
   public BiomeDecorator as;
   protected List<BiomeGenBase.SpawnListEntry> at = Lists.newArrayList();
   protected List<BiomeGenBase.SpawnListEntry> au = Lists.newArrayList();
   protected List<BiomeGenBase.SpawnListEntry> av = Lists.newArrayList();
   protected List<BiomeGenBase.SpawnListEntry> aw = Lists.newArrayList();
   protected boolean ax;
   protected boolean ay = true;
   public final int az;
   protected WorldGenTrees aA = new WorldGenTrees(false);
   protected WorldGenBigTree aB = new WorldGenBigTree(false);
   protected WorldGenSwamp aC = new WorldGenSwamp();

   protected BiomeGenBase(int var1) {
      this.az = var1;
      aE[var1] = this;
      this.as = this.a();
      this.au.add(new BiomeGenBase.SpawnListEntry(EntitySheep.class, 12, 4, 4));
      this.au.add(new BiomeGenBase.SpawnListEntry(EntityRabbit.class, 10, 3, 3));
      this.au.add(new BiomeGenBase.SpawnListEntry(EntityPig.class, 10, 4, 4));
      this.au.add(new BiomeGenBase.SpawnListEntry(EntityChicken.class, 10, 4, 4));
      this.au.add(new BiomeGenBase.SpawnListEntry(EntityCow.class, 8, 4, 4));
      this.at.add(new BiomeGenBase.SpawnListEntry(EntitySpider.class, 100, 4, 4));
      this.at.add(new BiomeGenBase.SpawnListEntry(EntityZombie.class, 100, 4, 4));
      this.at.add(new BiomeGenBase.SpawnListEntry(EntitySkeleton.class, 100, 4, 4));
      this.at.add(new BiomeGenBase.SpawnListEntry(EntityCreeper.class, 100, 4, 4));
      this.at.add(new BiomeGenBase.SpawnListEntry(EntitySlime.class, 100, 4, 4));
      this.at.add(new BiomeGenBase.SpawnListEntry(EntityEnderman.class, 10, 1, 4));
      this.at.add(new BiomeGenBase.SpawnListEntry(EntityWitch.class, 5, 1, 1));
      this.av.add(new BiomeGenBase.SpawnListEntry(EntitySquid.class, 10, 4, 4));
      this.aw.add(new BiomeGenBase.SpawnListEntry(EntityBat.class, 10, 8, 8));
   }

   protected BiomeDecorator a() {
      return new BiomeDecorator();
   }

   protected BiomeGenBase a(float var1, float var2) {
      if (var1 > 0.1F && var1 < 0.2F) {
         throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
      } else {
         this.ap = var1;
         this.aq = var2;
         return this;
      }
   }

   protected final BiomeGenBase a(BiomeGenBase.Height var1) {
      this.an = var1.a;
      this.ao = var1.b;
      return this;
   }

   protected BiomeGenBase b() {
      this.ay = false;
      return this;
   }

   public WorldGenAbstractTree a(Random var1) {
      return (WorldGenAbstractTree)(var1.nextInt(10) == 0 ? this.aB : this.aA);
   }

   public WorldGenerator b(Random var1) {
      return new WorldGenTallGrass(BlockTallGrass.EnumType.b);
   }

   public BlockFlower.EnumFlowerType a(Random var1, BlockPos var2) {
      return var1.nextInt(3) > 0 ? BlockFlower.EnumFlowerType.a : BlockFlower.EnumFlowerType.b;
   }

   protected BiomeGenBase c() {
      this.ax = true;
      return this;
   }

   protected BiomeGenBase a(String var1) {
      this.ah = var1;
      return this;
   }

   protected BiomeGenBase a(int var1) {
      this.am = var1;
      return this;
   }

   protected BiomeGenBase b(int var1) {
      this.a(var1, false);
      return this;
   }

   protected BiomeGenBase c(int var1) {
      this.aj = var1;
      return this;
   }

   protected BiomeGenBase a(int var1, boolean var2) {
      this.ai = var1;
      if (var2) {
         this.aj = (var1 & 16711422) >> 1;
      } else {
         this.aj = var1;
      }

      return this;
   }

   public int a(float var1) {
      var1 /= 3.0F;
      var1 = MathHelper.a(var1, -1.0F, 1.0F);
      return MathHelper.c(0.62222224F - var1 * 0.05F, 0.5F + var1 * 0.1F, 1.0F);
   }

   public List<BiomeGenBase.SpawnListEntry> a(EnumCreatureType var1) {
      switch (var1) {
         case a:
            return this.at;
         case b:
            return this.au;
         case d:
            return this.av;
         case c:
            return this.aw;
         default:
            return Collections.emptyList();
      }
   }

   public boolean d() {
      return this.j();
   }

   public boolean e() {
      return this.j() ? false : this.ay;
   }

   public boolean f() {
      return this.aq > 0.85F;
   }

   public float g() {
      return 0.1F;
   }

   public final int h() {
      return (int)(this.aq * 65536.0F);
   }

   public final float i() {
      return this.aq;
   }

   public final float a(BlockPos var1) {
      if (var1.o() > 64) {
         float var2 = (float)(ae.a(var1.n() * 1.0 / 8.0, var1.p() * 1.0 / 8.0) * 4.0);
         return this.ap - (var2 + var1.o() - 64.0F) * 0.05F / 30.0F;
      } else {
         return this.ap;
      }
   }

   public void a(World var1, Random var2, BlockPos var3) {
      this.as.a(var1, var2, this, var3);
   }

   public int b(BlockPos var1) {
      double var2 = MathHelper.a(this.a(var1), 0.0F, 1.0F);
      double var4 = MathHelper.a(this.i(), 0.0F, 1.0F);
      return ColorizerGrass.a(var2, var4);
   }

   public int c(BlockPos var1) {
      double var2 = MathHelper.a(this.a(var1), 0.0F, 1.0F);
      double var4 = MathHelper.a(this.i(), 0.0F, 1.0F);
      return ColorizerFoliage.a(var2, var4);
   }

   public boolean j() {
      return this.ax;
   }

   public void a(World var1, Random var2, ChunkPrimer var3, int var4, int var5, double var6) {
      this.b(var1, var2, var3, var4, var5, var6);
   }

   public final void b(World var1, Random var2, ChunkPrimer var3, int var4, int var5, double var6) {
      int var8 = var1.F();
      IBlockState var9 = this.ak;
      IBlockState var10 = this.al;
      int var11 = -1;
      int var12 = (int)(var6 / 3.0 + 3.0 + var2.nextDouble() * 0.25);
      int var13 = var4 & 15;
      int var14 = var5 & 15;
      BlockPos.MutableBlockPos var15 = new BlockPos.MutableBlockPos();

      for (int var16 = 255; var16 >= 0; var16--) {
         if (var16 <= var2.nextInt(5)) {
            var3.a(var14, var16, var13, Blocks.h.Q());
         } else {
            IBlockState var17 = var3.a(var14, var16, var13);
            if (var17.c().t() == Material.a) {
               var11 = -1;
            } else if (var17.c() == Blocks.b) {
               if (var11 == -1) {
                  if (var12 <= 0) {
                     var9 = null;
                     var10 = Blocks.b.Q();
                  } else if (var16 >= var8 - 4 && var16 <= var8 + 1) {
                     var9 = this.ak;
                     var10 = this.al;
                  }

                  if (var16 < var8 && (var9 == null || var9.c().t() == Material.a)) {
                     if (this.a(var15.c(var4, var16, var5)) < 0.15F) {
                        var9 = Blocks.aI.Q();
                     } else {
                        var9 = Blocks.j.Q();
                     }
                  }

                  var11 = var12;
                  if (var16 >= var8 - 1) {
                     var3.a(var14, var16, var13, var9);
                  } else if (var16 < var8 - 7 - var12) {
                     var9 = null;
                     var10 = Blocks.b.Q();
                     var3.a(var14, var16, var13, Blocks.n.Q());
                  } else {
                     var3.a(var14, var16, var13, var10);
                  }
               } else if (var11 > 0) {
                  var11--;
                  var3.a(var14, var16, var13, var10);
                  if (var11 == 0 && var10.c() == Blocks.m) {
                     var11 = var2.nextInt(4) + Math.max(0, var16 - 63);
                     var10 = var10.b(BlockSand.a) == BlockSand.EnumType.b ? Blocks.cM.Q() : Blocks.A.Q();
                  }
               }
            }
         }
      }
   }

   protected BiomeGenBase k() {
      return this.d(this.az + 128);
   }

   protected BiomeGenBase d(int var1) {
      return new BiomeGenMutated(var1, this);
   }

   public Class<? extends BiomeGenBase> l() {
      return (Class<? extends BiomeGenBase>)this.getClass();
   }

   public boolean a(BiomeGenBase var1) {
      if (var1 == this) {
         return true;
      } else {
         return var1 == null ? false : this.l() == var1.l();
      }
   }

   public BiomeGenBase.TempCategory m() {
      if (this.ap < 0.2) {
         return BiomeGenBase.TempCategory.b;
      } else {
         return this.ap < 1.0 ? BiomeGenBase.TempCategory.c : BiomeGenBase.TempCategory.d;
      }
   }

   public static BiomeGenBase[] n() {
      return aE;
   }

   public static BiomeGenBase e(int var0) {
      return a(var0, null);
   }

   public static BiomeGenBase a(int var0, BiomeGenBase var1) {
      if (var0 >= 0 && var0 <= aE.length) {
         BiomeGenBase var2 = aE[var0];
         return var2 == null ? var1 : var2;
      } else {
         aD.warn("Biome ID is out of bounds: " + var0 + ", defaulting to 0 (Ocean)");
         return p;
      }
   }

   static {
      q.k();
      r.k();
      t.k();
      u.k();
      v.k();
      B.k();
      K.k();
      M.k();
      T.k();
      Y.k();
      Z.k();
      aa.k();
      ab.k();
      ac.k();
      Q.k();
      R.k();
      S.k();
      V.k();
      s.k();
      X.k();
      V.d(W.az + 128).a("Redwood Taiga Hills M");

      for (BiomeGenBase var3 : aE) {
         if (var3 != null) {
            if (o.containsKey(var3.ah)) {
               throw new Error("Biome \"" + var3.ah + "\" is defined as both ID " + o.get(var3.ah).az + " and " + var3.az);
            }

            o.put(var3.ah, var3);
            if (var3.az < 128) {
               n.add(var3);
            }
         }
      }

      n.remove(x);
      n.remove(y);
      n.remove(z);
      n.remove(J);
      ae = new NoiseGeneratorPerlin(new Random(1234L), 1);
      af = new NoiseGeneratorPerlin(new Random(2345L), 1);
      ag = new WorldGenDoublePlant();
   }

   public static class Height {
      public float a;
      public float b;

      public Height(float var1, float var2) {
         this.a = var1;
         this.b = var2;
      }

      public BiomeGenBase.Height a() {
         return new BiomeGenBase.Height(this.a * 0.8F, this.b * 0.6F);
      }
   }

   public static class SpawnListEntry extends WeightedRandom.Item {
      public Class<? extends EntityLiving> b;
      public int c;
      public int d;

      public SpawnListEntry(Class<? extends EntityLiving> var1, int var2, int var3, int var4) {
         super(var2);
         this.b = var1;
         this.c = var3;
         this.d = var4;
      }

      @Override
      public String toString() {
         return this.b.getSimpleName() + "*(" + this.c + "-" + this.d + "):" + this.a;
      }
   }

   public static enum TempCategory {
      a,
      b,
      c,
      d;
   }
}
