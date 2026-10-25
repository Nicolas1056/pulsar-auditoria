package net.minecraft.client.particle;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EffectRenderer {
   private static final ResourceLocation b = new ResourceLocation("textures/particle/particles.png");
   protected World a;
   private List<EntityFX>[][] c = new List[4][];
   private List<EntityParticleEmitter> d = Lists.newArrayList();
   private TextureManager e;
   private Random f = new Random();
   private Map<Integer, IParticleFactory> g = Maps.newHashMap();

   public EffectRenderer(World var1, TextureManager var2) {
      this.a = var1;
      this.e = var2;

      for (int var3 = 0; var3 < 4; var3++) {
         this.c[var3] = new List[2];

         for (int var4 = 0; var4 < 2; var4++) {
            this.c[var3][var4] = Lists.newArrayList();
         }
      }

      this.c();
   }

   private void c() {
      this.a(EnumParticleTypes.a.c(), new EntityExplodeFX.Factory());
      this.a(EnumParticleTypes.e.c(), new EntityBubbleFX.Factory());
      this.a(EnumParticleTypes.f.c(), new EntitySplashFX.Factory());
      this.a(EnumParticleTypes.g.c(), new EntityFishWakeFX.Factory());
      this.a(EnumParticleTypes.N.c(), new EntityRainFX.Factory());
      this.a(EnumParticleTypes.h.c(), new EntitySuspendFX.Factory());
      this.a(EnumParticleTypes.i.c(), new EntityAuraFX.Factory());
      this.a(EnumParticleTypes.j.c(), new EntityCrit2FX.Factory());
      this.a(EnumParticleTypes.k.c(), new EntityCrit2FX.MagicFactory());
      this.a(EnumParticleTypes.l.c(), new EntitySmokeFX.Factory());
      this.a(EnumParticleTypes.m.c(), new EntityCritFX.Factory());
      this.a(EnumParticleTypes.n.c(), new EntitySpellParticleFX.Factory());
      this.a(EnumParticleTypes.o.c(), new EntitySpellParticleFX.InstantFactory());
      this.a(EnumParticleTypes.p.c(), new EntitySpellParticleFX.MobFactory());
      this.a(EnumParticleTypes.q.c(), new EntitySpellParticleFX.AmbientMobFactory());
      this.a(EnumParticleTypes.r.c(), new EntitySpellParticleFX.WitchFactory());
      this.a(EnumParticleTypes.s.c(), new EntityDropParticleFX.WaterFactory());
      this.a(EnumParticleTypes.t.c(), new EntityDropParticleFX.LavaFactory());
      this.a(EnumParticleTypes.u.c(), new EntityHeartFX.AngryVillagerFactory());
      this.a(EnumParticleTypes.v.c(), new EntityAuraFX.HappyVillagerFactory());
      this.a(EnumParticleTypes.w.c(), new EntityAuraFX.Factory());
      this.a(EnumParticleTypes.x.c(), new EntityNoteFX.Factory());
      this.a(EnumParticleTypes.y.c(), new EntityPortalFX.Factory());
      this.a(EnumParticleTypes.z.c(), new EntityEnchantmentTableParticleFX.EnchantmentTable());
      this.a(EnumParticleTypes.A.c(), new EntityFlameFX.Factory());
      this.a(EnumParticleTypes.B.c(), new EntityLavaFX.Factory());
      this.a(EnumParticleTypes.C.c(), new EntityFootStepFX.Factory());
      this.a(EnumParticleTypes.D.c(), new EntityCloudFX.Factory());
      this.a(EnumParticleTypes.E.c(), new EntityReddustFX.Factory());
      this.a(EnumParticleTypes.F.c(), new EntityBreakingFX.SnowballFactory());
      this.a(EnumParticleTypes.G.c(), new EntitySnowShovelFX.Factory());
      this.a(EnumParticleTypes.H.c(), new EntityBreakingFX.SlimeFactory());
      this.a(EnumParticleTypes.I.c(), new EntityHeartFX.Factory());
      this.a(EnumParticleTypes.J.c(), new Barrier.Factory());
      this.a(EnumParticleTypes.K.c(), new EntityBreakingFX.Factory());
      this.a(EnumParticleTypes.L.c(), new EntityDiggingFX.Factory());
      this.a(EnumParticleTypes.M.c(), new EntityBlockDustFX.Factory());
      this.a(EnumParticleTypes.c.c(), new EntityHugeExplodeFX.Factory());
      this.a(EnumParticleTypes.b.c(), new EntityLargeExplodeFX.Factory());
      this.a(EnumParticleTypes.d.c(), new EntityFirework.Factory());
      this.a(EnumParticleTypes.P.c(), new MobAppearance.Factory());
   }

   public void a(int var1, IParticleFactory var2) {
      this.g.put(var1, var2);
   }

   public void a(Entity var1, EnumParticleTypes var2) {
      this.d.add(new EntityParticleEmitter(this.a, var1, var2));
   }

   public EntityFX a(int var1, double var2, double var4, double var6, double var8, double var10, double var12, int... var14) {
      IParticleFactory var15 = this.g.get(var1);
      if (var15 != null) {
         EntityFX var16 = var15.a(var1, this.a, var2, var4, var6, var8, var10, var12, var14);
         if (var16 != null) {
            this.a(var16);
            return var16;
         }
      }

      return null;
   }

   public void a(EntityFX var1) {
      int var2 = var1.a();
      int var3 = var1.j() != 1.0F ? 0 : 1;
      if (this.c[var2][var3].size() >= 4000) {
         this.c[var2][var3].remove(0);
      }

      this.c[var2][var3].add(var1);
   }

   public void a() {
      for (int var1 = 0; var1 < 4; var1++) {
         this.a(var1);
      }

      ArrayList var4 = Lists.newArrayList();

      for (EntityParticleEmitter var3 : this.d) {
         var3.t_();
         if (var3.I) {
            var4.add(var3);
         }
      }

      this.d.removeAll(var4);
   }

   private void a(int var1) {
      for (int var2 = 0; var2 < 2; var2++) {
         this.a(this.c[var1][var2]);
      }
   }

   private void a(List<EntityFX> var1) {
      ArrayList var2 = Lists.newArrayList();

      for (int var3 = 0; var3 < var1.size(); var3++) {
         EntityFX var4 = (EntityFX)var1.get(var3);
         this.d(var4);
         if (var4.I) {
            var2.add(var4);
         }
      }

      var1.removeAll(var2);
   }

   private void d(final EntityFX var1) {
      try {
         var1.t_();
      } catch (Throwable var6) {
         CrashReport var3 = CrashReport.a(var6, "Ticking Particle");
         CrashReportCategory var4 = var3.a("Particle being ticked");
         final int var5 = var1.a();
         var4.a("Particle", new Callable<String>() {
            public String a() throws Exception {
               return var1.toString();
            }
         });
         var4.a("Particle Type", new Callable<String>() {
            public String a() throws Exception {
               if (var5 == 0) {
                  return "MISC_TEXTURE";
               } else if (var5 == 1) {
                  return "TERRAIN_TEXTURE";
               } else {
                  return var5 == 3 ? "ENTITY_PARTICLE_TEXTURE" : "Unknown - " + var5;
               }
            }
         });
         throw new ReportedException(var3);
      }
   }

   public void a(Entity var1, float var2) {
      float var3 = ActiveRenderInfo.b();
      float var4 = ActiveRenderInfo.d();
      float var5 = ActiveRenderInfo.e();
      float var6 = ActiveRenderInfo.f();
      float var7 = ActiveRenderInfo.c();
      EntityFX.aw = var1.P + (var1.s - var1.P) * var2;
      EntityFX.ax = var1.Q + (var1.t - var1.Q) * var2;
      EntityFX.ay = var1.R + (var1.u - var1.R) * var2;
      GlStateManager.l();
      GlStateManager.b(770, 771);
      GlStateManager.a(516, 0.003921569F);

      for (final int var8 = 0; var8 < 3; var8++) {
         for (int var9 = 0; var9 < 2; var9++) {
            if (!this.c[var8][var9].isEmpty()) {
               switch (var9) {
                  case 0:
                     GlStateManager.a(false);
                     break;
                  case 1:
                     GlStateManager.a(true);
               }

               switch (var8) {
                  case 0:
                  default:
                     this.e.a(b);
                     break;
                  case 1:
                     this.e.a(TextureMap.g);
               }

               GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
               Tessellator var10 = Tessellator.a();
               WorldRenderer var11 = var10.c();
               var11.a(7, DefaultVertexFormats.d);

               for (int var12 = 0; var12 < this.c[var8][var9].size(); var12++) {
                  final EntityFX var13 = this.c[var8][var9].get(var12);

                  try {
                     var13.a(var11, var1, var2, var3, var7, var4, var5, var6);
                  } catch (Throwable var18) {
                     CrashReport var15 = CrashReport.a(var18, "Rendering Particle");
                     CrashReportCategory var16 = var15.a("Particle being rendered");
                     var16.a("Particle", new Callable<String>() {
                        public String a() throws Exception {
                           return var13.toString();
                        }
                     });
                     var16.a("Particle Type", new Callable<String>() {
                        public String a() throws Exception {
                           if (var8 == 0) {
                              return "MISC_TEXTURE";
                           } else if (var8 == 1) {
                              return "TERRAIN_TEXTURE";
                           } else {
                              return var8 == 3 ? "ENTITY_PARTICLE_TEXTURE" : "Unknown - " + var8;
                           }
                        }
                     });
                     throw new ReportedException(var15);
                  }
               }

               var10.b();
            }
         }
      }

      GlStateManager.a(true);
      GlStateManager.k();
      GlStateManager.a(516, 0.1F);
   }

   public void b(Entity var1, float var2) {
      float var3 = (float) (Math.PI / 180.0);
      float var4 = MathHelper.b(var1.y * (float) (Math.PI / 180.0));
      float var5 = MathHelper.a(var1.y * (float) (Math.PI / 180.0));
      float var6 = -var5 * MathHelper.a(var1.z * (float) (Math.PI / 180.0));
      float var7 = var4 * MathHelper.a(var1.z * (float) (Math.PI / 180.0));
      float var8 = MathHelper.b(var1.z * (float) (Math.PI / 180.0));

      for (int var9 = 0; var9 < 2; var9++) {
         List var10 = this.c[3][var9];
         if (!var10.isEmpty()) {
            Tessellator var11 = Tessellator.a();
            WorldRenderer var12 = var11.c();

            for (int var13 = 0; var13 < var10.size(); var13++) {
               EntityFX var14 = (EntityFX)var10.get(var13);
               var14.a(var12, var1, var2, var4, var8, var5, var6, var7);
            }
         }
      }
   }

   public void a(World var1) {
      this.a = var1;

      for (int var2 = 0; var2 < 4; var2++) {
         for (int var3 = 0; var3 < 2; var3++) {
            this.c[var2][var3].clear();
         }
      }

      this.d.clear();
   }

   public void a(BlockPos var1, IBlockState var2) {
      if (var2.c().t() != Material.a) {
         var2 = var2.c().a(var2, this.a, var1);
         byte var3 = 4;

         for (int var4 = 0; var4 < var3; var4++) {
            for (int var5 = 0; var5 < var3; var5++) {
               for (int var6 = 0; var6 < var3; var6++) {
                  double var7 = var1.n() + (var4 + 0.5) / var3;
                  double var9 = var1.o() + (var5 + 0.5) / var3;
                  double var11 = var1.p() + (var6 + 0.5) / var3;
                  this.a(new EntityDiggingFX(this.a, var7, var9, var11, var7 - var1.n() - 0.5, var9 - var1.o() - 0.5, var11 - var1.p() - 0.5, var2).a(var1));
               }
            }
         }
      }
   }

   public void a(BlockPos var1, EnumFacing var2) {
      IBlockState var3 = this.a.p(var1);
      Block var4 = var3.c();
      if (var4.b() != -1) {
         int var5 = var1.n();
         int var6 = var1.o();
         int var7 = var1.p();
         float var8 = 0.1F;
         double var9 = var5 + this.f.nextDouble() * (var4.C() - var4.B() - var8 * 2.0F) + var8 + var4.B();
         double var11 = var6 + this.f.nextDouble() * (var4.E() - var4.D() - var8 * 2.0F) + var8 + var4.D();
         double var13 = var7 + this.f.nextDouble() * (var4.G() - var4.F() - var8 * 2.0F) + var8 + var4.F();
         if (var2 == EnumFacing.a) {
            var11 = var6 + var4.D() - var8;
         }

         if (var2 == EnumFacing.b) {
            var11 = var6 + var4.E() + var8;
         }

         if (var2 == EnumFacing.c) {
            var13 = var7 + var4.F() - var8;
         }

         if (var2 == EnumFacing.d) {
            var13 = var7 + var4.G() + var8;
         }

         if (var2 == EnumFacing.e) {
            var9 = var5 + var4.B() - var8;
         }

         if (var2 == EnumFacing.f) {
            var9 = var5 + var4.C() + var8;
         }

         this.a(new EntityDiggingFX(this.a, var9, var11, var13, 0.0, 0.0, 0.0, var3).a(var1).a(0.2F).h(0.6F));
      }
   }

   public void b(EntityFX var1) {
      this.a(var1, 1, 0);
   }

   public void c(EntityFX var1) {
      this.a(var1, 0, 1);
   }

   private void a(EntityFX var1, int var2, int var3) {
      for (int var4 = 0; var4 < 4; var4++) {
         if (this.c[var4][var2].contains(var1)) {
            this.c[var4][var2].remove(var1);
            this.c[var4][var3].add(var1);
         }
      }
   }

   public String b() {
      int var1 = 0;

      for (int var2 = 0; var2 < 4; var2++) {
         for (int var3 = 0; var3 < 2; var3++) {
            var1 += this.c[var2][var3].size();
         }
      }

      return "" + var1;
   }
}
