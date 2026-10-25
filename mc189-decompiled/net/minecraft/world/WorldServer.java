package net.minecraft.world;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEventData;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.INpc;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S24PacketBlockAction;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.ScoreboardSaveData;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ReportedException;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.village.VillageCollection;
import net.minecraft.village.VillageSiege;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.gen.feature.WorldGeneratorBonusChest;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldServer extends World implements IThreadListener {
   private static final Logger a = LogManager.getLogger();
   private final MinecraftServer I;
   private final EntityTracker J;
   private final PlayerManager K;
   private final Set<NextTickListEntry> L = Sets.newHashSet();
   private final TreeSet<NextTickListEntry> M = new TreeSet<>();
   private final Map<UUID, Entity> N = Maps.newHashMap();
   public ChunkProviderServer b;
   public boolean c;
   private boolean O;
   private int P;
   private final Teleporter Q;
   private final SpawnerAnimals R = new SpawnerAnimals();
   protected final VillageSiege d = new VillageSiege(this);
   private WorldServer.ServerBlockEventList[] S = new WorldServer.ServerBlockEventList[]{
      new WorldServer.ServerBlockEventList(), new WorldServer.ServerBlockEventList()
   };
   private int T;
   private static final List<WeightedRandomChestContent> U = Lists.newArrayList(
      new WeightedRandomChestContent[]{
         new WeightedRandomChestContent(Items.y, 0, 1, 3, 10),
         new WeightedRandomChestContent(Item.a(Blocks.f), 0, 1, 3, 10),
         new WeightedRandomChestContent(Item.a(Blocks.r), 0, 1, 3, 10),
         new WeightedRandomChestContent(Items.t, 0, 1, 1, 3),
         new WeightedRandomChestContent(Items.p, 0, 1, 1, 5),
         new WeightedRandomChestContent(Items.s, 0, 1, 1, 3),
         new WeightedRandomChestContent(Items.o, 0, 1, 1, 5),
         new WeightedRandomChestContent(Items.e, 0, 2, 3, 5),
         new WeightedRandomChestContent(Items.P, 0, 2, 3, 3),
         new WeightedRandomChestContent(Item.a(Blocks.s), 0, 1, 3, 10)
      }
   );
   private List<NextTickListEntry> V = Lists.newArrayList();

   public WorldServer(MinecraftServer var1, ISaveHandler var2, WorldInfo var3, int var4, Profiler var5) {
      super(var2, var3, WorldProvider.a(var4), var5, false);
      this.I = var1;
      this.J = new EntityTracker(this);
      this.K = new PlayerManager(this);
      this.t.a(this);
      this.v = this.k();
      this.Q = new Teleporter(this);
      this.B();
      this.C();
      this.af().a(var1.aI());
   }

   @Override
   public World b() {
      this.z = new MapStorage(this.w);
      String var1 = VillageCollection.a(this.t);
      VillageCollection var2 = (VillageCollection)this.z.a(VillageCollection.class, var1);
      if (var2 == null) {
         this.A = new VillageCollection(this);
         this.z.a(var1, this.A);
      } else {
         this.A = var2;
         this.A.a(this);
      }

      this.C = new ServerScoreboard(this.I);
      ScoreboardSaveData var3 = (ScoreboardSaveData)this.z.a(ScoreboardSaveData.class, "scoreboard");
      if (var3 == null) {
         var3 = new ScoreboardSaveData();
         this.z.a("scoreboard", var3);
      }

      var3.a(this.C);
      ((ServerScoreboard)this.C).a(var3);
      this.af().c(this.x.C(), this.x.D());
      this.af().c(this.x.I());
      this.af().b(this.x.H());
      this.af().c(this.x.J());
      this.af().b(this.x.K());
      if (this.x.F() > 0L) {
         this.af().a(this.x.E(), this.x.G(), this.x.F());
      } else {
         this.af().a(this.x.E());
      }

      return this;
   }

   @Override
   public void c() {
      super.c();
      if (this.P().t() && this.aa() != EnumDifficulty.d) {
         this.P().a(EnumDifficulty.d);
      }

      this.t.m().b();
      if (this.f()) {
         if (this.Q().b("doDaylightCycle")) {
            long var1 = this.x.g() + 24000L;
            this.x.c(var1 - var1 % 24000L);
         }

         this.e();
      }

      this.B.a("mobSpawner");
      if (this.Q().b("doMobSpawning") && this.x.u() != WorldType.g) {
         this.R.a(this, this.F, this.G, this.x.f() % 400L == 0L);
      }

      this.B.c("chunkSource");
      this.v.d();
      int var3 = this.a(1.0F);
      if (var3 != this.ab()) {
         this.c(var3);
      }

      this.x.b(this.x.f() + 1L);
      if (this.Q().b("doDaylightCycle")) {
         this.x.c(this.x.g() + 1L);
      }

      this.B.c("tickPending");
      this.a(false);
      this.B.c("tickBlocks");
      this.h();
      this.B.c("chunkMap");
      this.K.b();
      this.B.c("village");
      this.A.a();
      this.d.a();
      this.B.c("portalForcer");
      this.Q.a(this.K());
      this.B.b();
      this.ak();
   }

   public BiomeGenBase.SpawnListEntry a(EnumCreatureType var1, BlockPos var2) {
      List var3 = this.N().a(var1, var2);
      return var3 != null && !var3.isEmpty() ? WeightedRandom.a(this.s, var3) : null;
   }

   public boolean a(EnumCreatureType var1, BiomeGenBase.SpawnListEntry var2, BlockPos var3) {
      List var4 = this.N().a(var1, var3);
      return var4 != null && !var4.isEmpty() ? var4.contains(var2) : false;
   }

   @Override
   public void d() {
      this.O = false;
      if (!this.j.isEmpty()) {
         int var1 = 0;
         int var2 = 0;

         for (EntityPlayer var4 : this.j) {
            if (var4.v()) {
               var1++;
            } else if (var4.bJ()) {
               var2++;
            }
         }

         this.O = var2 > 0 && var2 >= this.j.size() - var1;
      }
   }

   protected void e() {
      this.O = false;

      for (EntityPlayer var2 : this.j) {
         if (var2.bJ()) {
            var2.a(false, false, true);
         }
      }

      this.ag();
   }

   private void ag() {
      this.x.g(0);
      this.x.b(false);
      this.x.f(0);
      this.x.a(false);
   }

   public boolean f() {
      if (this.O && !this.D) {
         for (EntityPlayer var2 : this.j) {
            if (var2.v() || !var2.cf()) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public void g() {
      if (this.x.d() <= 0) {
         this.x.b(this.F() + 1);
      }

      int var1 = this.x.c();
      int var2 = this.x.e();
      int var3 = 0;

      while (this.c(new BlockPos(var1, 0, var2)).t() == Material.a) {
         var1 += this.s.nextInt(8) - this.s.nextInt(8);
         var2 += this.s.nextInt(8) - this.s.nextInt(8);
         if (++var3 == 10000) {
            break;
         }
      }

      this.x.a(var1);
      this.x.c(var2);
   }

   @Override
   protected void h() {
      super.h();
      if (this.x.u() == WorldType.g) {
         for (ChunkCoordIntPair var21 : this.E) {
            this.a(var21.a, var21.b).b(false);
         }
      } else {
         int var1 = 0;
         int var2 = 0;

         for (ChunkCoordIntPair var4 : this.E) {
            int var5 = var4.a * 16;
            int var6 = var4.b * 16;
            this.B.a("getChunk");
            Chunk var7 = this.a(var4.a, var4.b);
            this.a(var5, var6, var7);
            this.B.c("tickChunk");
            var7.b(false);
            this.B.c("thunder");
            if (this.s.nextInt(100000) == 0 && this.S() && this.R()) {
               this.m = this.m * 3 + 1013904223;
               int var8 = this.m >> 2;
               BlockPos var9 = this.a(new BlockPos(var5 + (var8 & 15), 0, var6 + (var8 >> 8 & 15)));
               if (this.C(var9)) {
                  this.c(new EntityLightningBolt(this, var9.n(), var9.o(), var9.p()));
               }
            }

            this.B.c("iceandsnow");
            if (this.s.nextInt(16) == 0) {
               this.m = this.m * 3 + 1013904223;
               int var22 = this.m >> 2;
               BlockPos var24 = this.q(new BlockPos(var5 + (var22 & 15), 0, var6 + (var22 >> 8 & 15)));
               BlockPos var10 = var24.b();
               if (this.w(var10)) {
                  this.a(var10, Blocks.aI.Q());
               }

               if (this.S() && this.f(var24, true)) {
                  this.a(var24, Blocks.aH.Q());
               }

               if (this.S() && this.b(var10).e()) {
                  this.p(var10).c().k(this, var10);
               }
            }

            this.B.c("tickBlocks");
            int var23 = this.Q().c("randomTickSpeed");
            if (var23 > 0) {
               for (ExtendedBlockStorage var12 : var7.h()) {
                  if (var12 != null && var12.b()) {
                     for (int var13 = 0; var13 < var23; var13++) {
                        this.m = this.m * 3 + 1013904223;
                        int var14 = this.m >> 2;
                        int var15 = var14 & 15;
                        int var16 = var14 >> 8 & 15;
                        int var17 = var14 >> 16 & 15;
                        var2++;
                        IBlockState var18 = var12.a(var15, var17, var16);
                        Block var19 = var18.c();
                        if (var19.y()) {
                           var1++;
                           var19.a(this, new BlockPos(var15 + var5, var17 + var12.d(), var16 + var6), var18, this.s);
                        }
                     }
                  }
               }
            }

            this.B.b();
         }
      }
   }

   protected BlockPos a(BlockPos var1) {
      BlockPos var2 = this.q(var1);
      AxisAlignedBB var3 = new AxisAlignedBB(var2, new BlockPos(var2.n(), this.U(), var2.p())).b(3.0, 3.0, 3.0);
      List var4 = this.a(EntityLivingBase.class, var3, new Predicate<EntityLivingBase>() {
         public boolean a(EntityLivingBase var1) {
            return var1 != null && var1.ai() && WorldServer.this.i(var1.c());
         }
      });
      return !var4.isEmpty() ? ((EntityLivingBase)var4.get(this.s.nextInt(var4.size()))).c() : var2;
   }

   @Override
   public boolean a(BlockPos var1, Block var2) {
      NextTickListEntry var3 = new NextTickListEntry(var1, var2);
      return this.V.contains(var3);
   }

   @Override
   public void a(BlockPos var1, Block var2, int var3) {
      this.a(var1, var2, var3, 0);
   }

   @Override
   public void a(BlockPos var1, Block var2, int var3, int var4) {
      NextTickListEntry var5 = new NextTickListEntry(var1, var2);
      byte var6 = 0;
      if (this.e && var2.t() != Material.a) {
         if (var2.N()) {
            var6 = 8;
            if (this.a(var5.a.a(-var6, -var6, -var6), var5.a.a((int)var6, (int)var6, (int)var6))) {
               IBlockState var7 = this.p(var5.a);
               if (var7.c().t() != Material.a && var7.c() == var5.a()) {
                  var7.c().b(this, var5.a, var7, this.s);
               }
            }

            return;
         }

         var3 = 1;
      }

      if (this.a(var1.a(-var6, -var6, -var6), var1.a((int)var6, (int)var6, (int)var6))) {
         if (var2.t() != Material.a) {
            var5.a(var3 + this.x.f());
            var5.a(var4);
         }

         if (!this.L.contains(var5)) {
            this.L.add(var5);
            this.M.add(var5);
         }
      }
   }

   @Override
   public void b(BlockPos var1, Block var2, int var3, int var4) {
      NextTickListEntry var5 = new NextTickListEntry(var1, var2);
      var5.a(var4);
      if (var2.t() != Material.a) {
         var5.a(var3 + this.x.f());
      }

      if (!this.L.contains(var5)) {
         this.L.add(var5);
         this.M.add(var5);
      }
   }

   @Override
   public void i() {
      if (this.j.isEmpty()) {
         if (this.P++ >= 1200) {
            return;
         }
      } else {
         this.j();
      }

      super.i();
   }

   public void j() {
      this.P = 0;
   }

   @Override
   public boolean a(boolean var1) {
      if (this.x.u() == WorldType.g) {
         return false;
      } else {
         int var2 = this.M.size();
         if (var2 != this.L.size()) {
            throw new IllegalStateException("TickNextTick list out of synch");
         } else {
            if (var2 > 1000) {
               var2 = 1000;
            }

            this.B.a("cleaning");

            for (int var3 = 0; var3 < var2; var3++) {
               NextTickListEntry var4 = this.M.first();
               if (!var1 && var4.b > this.x.f()) {
                  break;
               }

               this.M.remove(var4);
               this.L.remove(var4);
               this.V.add(var4);
            }

            this.B.b();
            this.B.a("ticking");
            Iterator var11 = this.V.iterator();

            while (var11.hasNext()) {
               NextTickListEntry var12 = (NextTickListEntry)var11.next();
               var11.remove();
               byte var5 = 0;
               if (this.a(var12.a.a(-var5, -var5, -var5), var12.a.a((int)var5, (int)var5, (int)var5))) {
                  IBlockState var6 = this.p(var12.a);
                  if (var6.c().t() != Material.a && Block.a(var6.c(), var12.a())) {
                     try {
                        var6.c().b(this, var12.a, var6, this.s);
                     } catch (Throwable var10) {
                        CrashReport var8 = CrashReport.a(var10, "Exception while ticking a block");
                        CrashReportCategory var9 = var8.a("Block being ticked");
                        CrashReportCategory.a(var9, var12.a, var6);
                        throw new ReportedException(var8);
                     }
                  }
               } else {
                  this.a(var12.a, var12.a(), 0);
               }
            }

            this.B.b();
            this.V.clear();
            return !this.M.isEmpty();
         }
      }
   }

   @Override
   public List<NextTickListEntry> a(Chunk var1, boolean var2) {
      ChunkCoordIntPair var3 = var1.j();
      int var4 = (var3.a << 4) - 2;
      int var5 = var4 + 16 + 2;
      int var6 = (var3.b << 4) - 2;
      int var7 = var6 + 16 + 2;
      return this.a(new StructureBoundingBox(var4, 0, var6, var5, 256, var7), var2);
   }

   @Override
   public List<NextTickListEntry> a(StructureBoundingBox var1, boolean var2) {
      ArrayList var3 = null;

      for (int var4 = 0; var4 < 2; var4++) {
         Iterator var5;
         if (var4 == 0) {
            var5 = this.M.iterator();
         } else {
            var5 = this.V.iterator();
         }

         while (var5.hasNext()) {
            NextTickListEntry var6 = (NextTickListEntry)var5.next();
            BlockPos var7 = var6.a;
            if (var7.n() >= var1.a && var7.n() < var1.d && var7.p() >= var1.c && var7.p() < var1.f) {
               if (var2) {
                  this.L.remove(var6);
                  var5.remove();
               }

               if (var3 == null) {
                  var3 = Lists.newArrayList();
               }

               var3.add(var6);
            }
         }
      }

      return var3;
   }

   @Override
   public void a(Entity var1, boolean var2) {
      if (!this.ai() && (var1 instanceof EntityAnimal || var1 instanceof EntityWaterMob)) {
         var1.J();
      }

      if (!this.ah() && var1 instanceof INpc) {
         var1.J();
      }

      super.a(var1, var2);
   }

   private boolean ah() {
      return this.I.ah();
   }

   private boolean ai() {
      return this.I.ag();
   }

   @Override
   protected IChunkProvider k() {
      IChunkLoader var1 = this.w.a(this.t);
      this.b = new ChunkProviderServer(this, var1, this.t.c());
      return this.b;
   }

   public List<TileEntity> a(int var1, int var2, int var3, int var4, int var5, int var6) {
      ArrayList var7 = Lists.newArrayList();

      for (int var8 = 0; var8 < this.h.size(); var8++) {
         TileEntity var9 = this.h.get(var8);
         BlockPos var10 = var9.v();
         if (var10.n() >= var1 && var10.o() >= var2 && var10.p() >= var3 && var10.n() < var4 && var10.o() < var5 && var10.p() < var6) {
            var7.add(var9);
         }
      }

      return var7;
   }

   @Override
   public boolean a(EntityPlayer var1, BlockPos var2) {
      return !this.I.a(this, var2, var1) && this.af().a(var2);
   }

   @Override
   public void a(WorldSettings var1) {
      if (!this.x.w()) {
         try {
            this.b(var1);
            if (this.x.u() == WorldType.g) {
               this.aj();
            }

            super.a(var1);
         } catch (Throwable var6) {
            CrashReport var3 = CrashReport.a(var6, "Exception initializing level");

            try {
               this.a(var3);
            } catch (Throwable var5) {
            }

            throw new ReportedException(var3);
         }

         this.x.d(true);
      }
   }

   private void aj() {
      this.x.f(false);
      this.x.c(true);
      this.x.b(false);
      this.x.a(false);
      this.x.i(1000000000);
      this.x.c(6000L);
      this.x.a(WorldSettings.GameType.e);
      this.x.g(false);
      this.x.a(EnumDifficulty.a);
      this.x.e(true);
      this.Q().a("doDaylightCycle", "false");
   }

   private void b(WorldSettings var1) {
      if (!this.t.e()) {
         this.x.a(BlockPos.a.b(this.t.i()));
      } else if (this.x.u() == WorldType.g) {
         this.x.a(BlockPos.a.a());
      } else {
         this.y = true;
         WorldChunkManager var2 = this.t.m();
         List var3 = var2.a();
         Random var4 = new Random(this.J());
         BlockPos var5 = var2.a(0, 0, 256, var3, var4);
         int var6 = 0;
         int var7 = this.t.i();
         int var8 = 0;
         if (var5 != null) {
            var6 = var5.n();
            var8 = var5.p();
         } else {
            a.warn("Unable to find spawn biome");
         }

         int var9 = 0;

         while (!this.t.a(var6, var8)) {
            var6 += var4.nextInt(64) - var4.nextInt(64);
            var8 += var4.nextInt(64) - var4.nextInt(64);
            if (++var9 == 1000) {
               break;
            }
         }

         this.x.a(new BlockPos(var6, var7, var8));
         this.y = false;
         if (var1.c()) {
            this.l();
         }
      }
   }

   protected void l() {
      WorldGeneratorBonusChest var1 = new WorldGeneratorBonusChest(U, 10);

      for (int var2 = 0; var2 < 10; var2++) {
         int var3 = this.x.c() + this.s.nextInt(6) - this.s.nextInt(6);
         int var4 = this.x.e() + this.s.nextInt(6) - this.s.nextInt(6);
         BlockPos var5 = this.r(new BlockPos(var3, 0, var4)).a();
         if (var1.b(this, this.s, var5)) {
            break;
         }
      }
   }

   public BlockPos m() {
      return this.t.h();
   }

   public void a(boolean var1, IProgressUpdate var2) throws MinecraftException {
      if (this.v.e()) {
         if (var2 != null) {
            var2.a("Saving level");
         }

         this.a();
         if (var2 != null) {
            var2.c("Saving chunks");
         }

         this.v.a(var1, var2);

         for (Chunk var5 : Lists.newArrayList(this.b.a())) {
            if (var5 != null && !this.K.a(var5.a, var5.b)) {
               this.b.b(var5.a, var5.b);
            }
         }
      }
   }

   public void n() {
      if (this.v.e()) {
         this.v.c();
      }
   }

   protected void a() throws MinecraftException {
      this.I();
      this.x.a(this.af().h());
      this.x.d(this.af().f());
      this.x.c(this.af().g());
      this.x.e(this.af().m());
      this.x.f(this.af().n());
      this.x.j(this.af().q());
      this.x.k(this.af().p());
      this.x.b(this.af().j());
      this.x.e(this.af().i());
      this.w.a(this.x, this.I.ap().t());
      this.z.a();
   }

   @Override
   protected void a(Entity var1) {
      super.a(var1);
      this.l.a(var1.F(), var1);
      this.N.put(var1.aK(), var1);
      Entity[] var2 = var1.aB();
      if (var2 != null) {
         for (int var3 = 0; var3 < var2.length; var3++) {
            this.l.a(var2[var3].F(), var2[var3]);
         }
      }
   }

   @Override
   protected void b(Entity var1) {
      super.b(var1);
      this.l.d(var1.F());
      this.N.remove(var1.aK());
      Entity[] var2 = var1.aB();
      if (var2 != null) {
         for (int var3 = 0; var3 < var2.length; var3++) {
            this.l.d(var2[var3].F());
         }
      }
   }

   @Override
   public boolean c(Entity var1) {
      if (super.c(var1)) {
         this.I.ap().a(var1.s, var1.t, var1.u, 512.0, this.t.q(), new S2CPacketSpawnGlobalEntity(var1));
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void a(Entity var1, byte var2) {
      this.s().b(var1, new S19PacketEntityStatus(var1, var2));
   }

   @Override
   public Explosion a(Entity var1, double var2, double var4, double var6, float var8, boolean var9, boolean var10) {
      Explosion var11 = new Explosion(this, var1, var2, var4, var6, var8, var9, var10);
      var11.a();
      var11.a(false);
      if (!var10) {
         var11.d();
      }

      for (EntityPlayer var13 : this.j) {
         if (var13.e(var2, var4, var6) < 4096.0) {
            ((EntityPlayerMP)var13).a.a(new S27PacketExplosion(var2, var4, var6, var8, var11.e(), var11.b().get(var13)));
         }
      }

      return var11;
   }

   @Override
   public void c(BlockPos var1, Block var2, int var3, int var4) {
      BlockEventData var5 = new BlockEventData(var1, var2, var3, var4);

      for (BlockEventData var7 : this.S[this.T]) {
         if (var7.equals(var5)) {
            return;
         }
      }

      this.S[this.T].add(var5);
   }

   private void ak() {
      while (!this.S[this.T].isEmpty()) {
         int var1 = this.T;
         this.T ^= 1;

         for (BlockEventData var3 : this.S[var1]) {
            if (this.a(var3)) {
               this.I.ap().a(var3.a().n(), var3.a().o(), var3.a().p(), 64.0, this.t.q(), new S24PacketBlockAction(var3.a(), var3.d(), var3.b(), var3.c()));
            }
         }

         this.S[var1].clear();
      }
   }

   private boolean a(BlockEventData var1) {
      IBlockState var2 = this.p(var1.a());
      return var2.c() == var1.d() ? var2.c().a(this, var1.a(), var2, var1.b(), var1.c()) : false;
   }

   public void o() {
      this.w.a();
   }

   @Override
   protected void p() {
      boolean var1 = this.S();
      super.p();
      if (this.o != this.p) {
         this.I.ap().a(new S2BPacketChangeGameState(7, this.p), this.t.q());
      }

      if (this.q != this.r) {
         this.I.ap().a(new S2BPacketChangeGameState(8, this.r), this.t.q());
      }

      if (var1 != this.S()) {
         if (var1) {
            this.I.ap().a(new S2BPacketChangeGameState(2, 0.0F));
         } else {
            this.I.ap().a(new S2BPacketChangeGameState(1, 0.0F));
         }

         this.I.ap().a(new S2BPacketChangeGameState(7, this.p));
         this.I.ap().a(new S2BPacketChangeGameState(8, this.r));
      }
   }

   @Override
   protected int q() {
      return this.I.ap().s();
   }

   public MinecraftServer r() {
      return this.I;
   }

   public EntityTracker s() {
      return this.J;
   }

   public PlayerManager t() {
      return this.K;
   }

   public Teleporter u() {
      return this.Q;
   }

   public void a(EnumParticleTypes var1, double var2, double var4, double var6, int var8, double var9, double var11, double var13, double var15, int... var17) {
      this.a(var1, false, var2, var4, var6, var8, var9, var11, var13, var15, var17);
   }

   public void a(
      EnumParticleTypes var1,
      boolean var2,
      double var3,
      double var5,
      double var7,
      int var9,
      double var10,
      double var12,
      double var14,
      double var16,
      int... var18
   ) {
      S2APacketParticles var19 = new S2APacketParticles(
         var1, var2, (float)var3, (float)var5, (float)var7, (float)var10, (float)var12, (float)var14, (float)var16, var9, var18
      );

      for (int var20 = 0; var20 < this.j.size(); var20++) {
         EntityPlayerMP var21 = (EntityPlayerMP)this.j.get(var20);
         BlockPos var22 = var21.c();
         double var23 = var22.c(var3, var5, var7);
         if (var23 <= 256.0 || var2 && var23 <= 65536.0) {
            var21.a.a(var19);
         }
      }
   }

   public Entity a(UUID var1) {
      return this.N.get(var1);
   }

   @Override
   public ListenableFuture<Object> a(Runnable var1) {
      return this.I.a(var1);
   }

   @Override
   public boolean aJ() {
      return this.I.aJ();
   }

   static class ServerBlockEventList extends ArrayList<BlockEventData> {
      private ServerBlockEventList() {
      }
   }
}
