package net.minecraft.client.multiplayer;

import com.google.common.collect.Sets;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSoundMinecart;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.EntityFirework;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.SaveDataMemoryStorage;
import net.minecraft.world.storage.SaveHandlerMP;
import net.minecraft.world.storage.WorldInfo;

public class WorldClient extends World {
   private NetHandlerPlayClient a;
   private ChunkProviderClient b;
   private final Set<Entity> c = Sets.newHashSet();
   private final Set<Entity> d = Sets.newHashSet();
   private final Minecraft I = Minecraft.A();
   private final Set<ChunkCoordIntPair> J = Sets.newHashSet();

   public WorldClient(NetHandlerPlayClient var1, WorldSettings var2, int var3, EnumDifficulty var4, Profiler var5) {
      super(new SaveHandlerMP(), new WorldInfo(var2, "MpServer"), WorldProvider.a(var3), var5, true);
      this.a = var1;
      this.P().a(var4);
      this.B(new BlockPos(8, 64, 8));
      this.t.a(this);
      this.v = this.k();
      this.z = new SaveDataMemoryStorage();
      this.B();
      this.C();
   }

   @Override
   public void c() {
      super.c();
      this.a(this.K() + 1L);
      if (this.Q().b("doDaylightCycle")) {
         this.b(this.L() + 1L);
      }

      this.B.a("reEntryProcessing");

      for (int var1 = 0; var1 < 10 && !this.d.isEmpty(); var1++) {
         Entity var2 = this.d.iterator().next();
         this.d.remove(var2);
         if (!this.f.contains(var2)) {
            this.d(var2);
         }
      }

      this.B.c("chunkCache");
      this.b.d();
      this.B.c("blocks");
      this.h();
      this.B.b();
   }

   public void a(int var1, int var2, int var3, int var4, int var5, int var6) {
   }

   @Override
   protected IChunkProvider k() {
      this.b = new ChunkProviderClient(this);
      return this.b;
   }

   @Override
   protected void h() {
      super.h();
      this.J.retainAll(this.E);
      if (this.J.size() == this.E.size()) {
         this.J.clear();
      }

      int var1 = 0;

      for (ChunkCoordIntPair var3 : this.E) {
         if (!this.J.contains(var3)) {
            int var4 = var3.a * 16;
            int var5 = var3.b * 16;
            this.B.a("getChunk");
            Chunk var6 = this.a(var3.a, var3.b);
            this.a(var4, var5, var6);
            this.B.b();
            this.J.add(var3);
            if (++var1 >= 10) {
               return;
            }
         }
      }
   }

   public void b(int var1, int var2, boolean var3) {
      if (var3) {
         this.b.c(var1, var2);
      } else {
         this.b.b(var1, var2);
      }

      if (!var3) {
         this.b(var1 * 16, 0, var2 * 16, var1 * 16 + 15, 256, var2 * 16 + 15);
      }
   }

   @Override
   public boolean d(Entity var1) {
      boolean var2 = super.d(var1);
      this.c.add(var1);
      if (!var2) {
         this.d.add(var1);
      } else if (var1 instanceof EntityMinecart) {
         this.I.W().a(new MovingSoundMinecart((EntityMinecart)var1));
      }

      return var2;
   }

   @Override
   public void e(Entity var1) {
      super.e(var1);
      this.c.remove(var1);
   }

   @Override
   protected void a(Entity var1) {
      super.a(var1);
      if (this.d.contains(var1)) {
         this.d.remove(var1);
      }
   }

   @Override
   protected void b(Entity var1) {
      super.b(var1);
      boolean var2 = false;
      if (this.c.contains(var1)) {
         if (var1.ai()) {
            this.d.add(var1);
            var2 = true;
         } else {
            this.c.remove(var1);
         }
      }
   }

   public void a(int var1, Entity var2) {
      Entity var3 = this.a(var1);
      if (var3 != null) {
         this.e(var3);
      }

      this.c.add(var2);
      var2.d(var1);
      if (!this.d(var2)) {
         this.d.add(var2);
      }

      this.l.a(var1, var2);
   }

   @Override
   public Entity a(int var1) {
      return (Entity)(var1 == this.I.h.F() ? this.I.h : super.a(var1));
   }

   public Entity e(int var1) {
      Entity var2 = this.l.d(var1);
      if (var2 != null) {
         this.c.remove(var2);
         this.e(var2);
      }

      return var2;
   }

   public boolean b(BlockPos var1, IBlockState var2) {
      int var3 = var1.n();
      int var4 = var1.o();
      int var5 = var1.p();
      this.a(var3, var4, var5, var3, var4, var5);
      return super.a(var1, var2, 3);
   }

   @Override
   public void H() {
      this.a.a().a(new ChatComponentText("Quitting"));
   }

   @Override
   protected void p() {
   }

   @Override
   protected int q() {
      return this.I.t.c;
   }

   public void b(int var1, int var2, int var3) {
      byte var4 = 16;
      Random var5 = new Random();
      ItemStack var6 = this.I.h.bA();
      boolean var7 = this.I.c.l() == WorldSettings.GameType.c && var6 != null && Block.a(var6.b()) == Blocks.cv;
      BlockPos.MutableBlockPos var8 = new BlockPos.MutableBlockPos();

      for (int var9 = 0; var9 < 1000; var9++) {
         int var10 = var1 + this.s.nextInt(var4) - this.s.nextInt(var4);
         int var11 = var2 + this.s.nextInt(var4) - this.s.nextInt(var4);
         int var12 = var3 + this.s.nextInt(var4) - this.s.nextInt(var4);
         var8.c(var10, var11, var12);
         IBlockState var13 = this.p(var8);
         var13.c().c(this, var8, var13, var5);
         if (var7 && var13.c() == Blocks.cv) {
            this.a(EnumParticleTypes.J, var10 + 0.5F, var11 + 0.5F, var12 + 0.5F, 0.0, 0.0, 0.0, new int[0]);
         }
      }
   }

   public void a() {
      this.f.removeAll(this.g);

      for (int var1 = 0; var1 < this.g.size(); var1++) {
         Entity var2 = this.g.get(var1);
         int var3 = var2.ae;
         int var4 = var2.ag;
         if (var2.ad && this.a(var3, var4, true)) {
            this.a(var3, var4).b(var2);
         }
      }

      for (int var5 = 0; var5 < this.g.size(); var5++) {
         this.b(this.g.get(var5));
      }

      this.g.clear();

      for (int var6 = 0; var6 < this.f.size(); var6++) {
         Entity var7 = this.f.get(var6);
         if (var7.m != null) {
            if (!var7.m.I && var7.m.l == var7) {
               continue;
            }

            var7.m.l = null;
            var7.m = null;
         }

         if (var7.I) {
            int var8 = var7.ae;
            int var9 = var7.ag;
            if (var7.ad && this.a(var8, var9, true)) {
               this.a(var8, var9).b(var7);
            }

            this.f.remove(var6--);
            this.b(var7);
         }
      }
   }

   @Override
   public CrashReportCategory a(CrashReport var1) {
      CrashReportCategory var2 = super.a(var1);
      var2.a("Forced entities", new Callable<String>() {
         public String a() {
            return WorldClient.this.c.size() + " total; " + WorldClient.this.c.toString();
         }
      });
      var2.a("Retry entities", new Callable<String>() {
         public String a() {
            return WorldClient.this.d.size() + " total; " + WorldClient.this.d.toString();
         }
      });
      var2.a("Server brand", new Callable<String>() {
         public String a() throws Exception {
            return WorldClient.this.I.h.w();
         }
      });
      var2.a("Server type", new Callable<String>() {
         public String a() throws Exception {
            return WorldClient.this.I.G() == null ? "Non-integrated multiplayer server" : "Integrated singleplayer server";
         }
      });
      return var2;
   }

   public void a(BlockPos var1, String var2, float var3, float var4, boolean var5) {
      this.a(var1.n() + 0.5, var1.o() + 0.5, var1.p() + 0.5, var2, var3, var4, var5);
   }

   @Override
   public void a(double var1, double var3, double var5, String var7, float var8, float var9, boolean var10) {
      double var11 = this.I.ac().e(var1, var3, var5);
      PositionedSoundRecord var13 = new PositionedSoundRecord(new ResourceLocation(var7), var8, var9, (float)var1, (float)var3, (float)var5);
      if (var10 && var11 > 100.0) {
         double var14 = Math.sqrt(var11) / 40.0;
         this.I.W().a(var13, (int)(var14 * 20.0));
      } else {
         this.I.W().a(var13);
      }
   }

   @Override
   public void a(double var1, double var3, double var5, double var7, double var9, double var11, NBTTagCompound var13) {
      this.I.j.a(new EntityFirework.StarterFX(this, var1, var3, var5, var7, var9, var11, this.I.j, var13));
   }

   public void a(Scoreboard var1) {
      this.C = var1;
   }

   @Override
   public void b(long var1) {
      if (var1 < 0L) {
         var1 = -var1;
         this.Q().a("doDaylightCycle", "false");
      } else {
         this.Q().a("doDaylightCycle", "true");
      }

      super.b(var1);
   }
}
