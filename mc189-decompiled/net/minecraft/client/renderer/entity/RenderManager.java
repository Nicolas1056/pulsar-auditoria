package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.model.ModelOcelot;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.model.ModelRabbit;
import net.minecraft.client.model.ModelSheep2;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.model.ModelSquid;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.RenderEnderCrystal;
import net.minecraft.client.renderer.tileentity.RenderItemFrame;
import net.minecraft.client.renderer.tileentity.RenderWitherSkull;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class RenderManager {
   private Map<Class<? extends Entity>, Render<? extends Entity>> k = Maps.newHashMap();
   private Map<String, RenderPlayer> l = Maps.newHashMap();
   private RenderPlayer m;
   private FontRenderer n;
   private double o;
   private double p;
   private double q;
   public TextureManager a;
   public World b;
   public Entity c;
   public Entity d;
   public float e;
   public float f;
   public GameSettings g;
   public double h;
   public double i;
   public double j;
   private boolean r = false;
   private boolean s = true;
   private boolean t = false;

   public RenderManager(TextureManager var1, RenderItem var2) {
      this.a = var1;
      this.k.put(EntityCaveSpider.class, new RenderCaveSpider(this));
      this.k.put(EntitySpider.class, new RenderSpider(this));
      this.k.put(EntityPig.class, new RenderPig(this, new ModelPig(), 0.7F));
      this.k.put(EntitySheep.class, new RenderSheep(this, new ModelSheep2(), 0.7F));
      this.k.put(EntityCow.class, new RenderCow(this, new ModelCow(), 0.7F));
      this.k.put(EntityMooshroom.class, new RenderMooshroom(this, new ModelCow(), 0.7F));
      this.k.put(EntityWolf.class, new RenderWolf(this, new ModelWolf(), 0.5F));
      this.k.put(EntityChicken.class, new RenderChicken(this, new ModelChicken(), 0.3F));
      this.k.put(EntityOcelot.class, new RenderOcelot(this, new ModelOcelot(), 0.4F));
      this.k.put(EntityRabbit.class, new RenderRabbit(this, new ModelRabbit(), 0.3F));
      this.k.put(EntitySilverfish.class, new RenderSilverfish(this));
      this.k.put(EntityEndermite.class, new RenderEndermite(this));
      this.k.put(EntityCreeper.class, new RenderCreeper(this));
      this.k.put(EntityEnderman.class, new RenderEnderman(this));
      this.k.put(EntitySnowman.class, new RenderSnowMan(this));
      this.k.put(EntitySkeleton.class, new RenderSkeleton(this));
      this.k.put(EntityWitch.class, new RenderWitch(this));
      this.k.put(EntityBlaze.class, new RenderBlaze(this));
      this.k.put(EntityPigZombie.class, new RenderPigZombie(this));
      this.k.put(EntityZombie.class, new RenderZombie(this));
      this.k.put(EntitySlime.class, new RenderSlime(this, new ModelSlime(16), 0.25F));
      this.k.put(EntityMagmaCube.class, new RenderMagmaCube(this));
      this.k.put(EntityGiantZombie.class, new RenderGiantZombie(this, new ModelZombie(), 0.5F, 6.0F));
      this.k.put(EntityGhast.class, new RenderGhast(this));
      this.k.put(EntitySquid.class, new RenderSquid(this, new ModelSquid(), 0.7F));
      this.k.put(EntityVillager.class, new RenderVillager(this));
      this.k.put(EntityIronGolem.class, new RenderIronGolem(this));
      this.k.put(EntityBat.class, new RenderBat(this));
      this.k.put(EntityGuardian.class, new RenderGuardian(this));
      this.k.put(EntityDragon.class, new RenderDragon(this));
      this.k.put(EntityEnderCrystal.class, new RenderEnderCrystal(this));
      this.k.put(EntityWither.class, new RenderWither(this));
      this.k.put(Entity.class, new RenderEntity(this));
      this.k.put(EntityPainting.class, new RenderPainting(this));
      this.k.put(EntityItemFrame.class, new RenderItemFrame(this, var2));
      this.k.put(EntityLeashKnot.class, new RenderLeashKnot(this));
      this.k.put(EntityArrow.class, new RenderArrow(this));
      this.k.put(EntitySnowball.class, new RenderSnowball<>(this, Items.aD, var2));
      this.k.put(EntityEnderPearl.class, new RenderSnowball<>(this, Items.bu, var2));
      this.k.put(EntityEnderEye.class, new RenderSnowball<>(this, Items.bH, var2));
      this.k.put(EntityEgg.class, new RenderSnowball<>(this, Items.aP, var2));
      this.k.put(EntityPotion.class, new RenderPotion(this, var2));
      this.k.put(EntityExpBottle.class, new RenderSnowball<>(this, Items.bK, var2));
      this.k.put(EntityFireworkRocket.class, new RenderSnowball<>(this, Items.cb, var2));
      this.k.put(EntityLargeFireball.class, new RenderFireball(this, 2.0F));
      this.k.put(EntitySmallFireball.class, new RenderFireball(this, 0.5F));
      this.k.put(EntityWitherSkull.class, new RenderWitherSkull(this));
      this.k.put(EntityItem.class, new RenderEntityItem(this, var2));
      this.k.put(EntityXPOrb.class, new RenderXPOrb(this));
      this.k.put(EntityTNTPrimed.class, new RenderTNTPrimed(this));
      this.k.put(EntityFallingBlock.class, new RenderFallingBlock(this));
      this.k.put(EntityArmorStand.class, new ArmorStandRenderer(this));
      this.k.put(EntityMinecartTNT.class, new RenderTntMinecart(this));
      this.k.put(EntityMinecartMobSpawner.class, new RenderMinecartMobSpawner(this));
      this.k.put(EntityMinecart.class, new RenderMinecart(this));
      this.k.put(EntityBoat.class, new RenderBoat(this));
      this.k.put(EntityFishHook.class, new RenderFish(this));
      this.k.put(EntityHorse.class, new RenderHorse(this, new ModelHorse(), 0.75F));
      this.k.put(EntityLightningBolt.class, new RenderLightningBolt(this));
      this.m = new RenderPlayer(this);
      this.l.put("default", this.m);
      this.l.put("slim", new RenderPlayer(this, true));
   }

   public void a(double var1, double var3, double var5) {
      this.o = var1;
      this.p = var3;
      this.q = var5;
   }

   public <T extends Entity> Render<T> a(Class<? extends Entity> var1) {
      Render var2 = this.k.get(var1);
      if (var2 == null && var1 != Entity.class) {
         var2 = this.a(var1.getSuperclass());
         this.k.put(var1, var2);
      }

      return var2;
   }

   public <T extends Entity> Render<T> a(Entity var1) {
      if (var1 instanceof AbstractClientPlayer) {
         String var2 = ((AbstractClientPlayer)var1).l();
         RenderPlayer var3 = this.l.get(var2);
         return var3 != null ? var3 : this.m;
      } else {
         return this.a((Class<? extends Entity>)var1.getClass());
      }
   }

   public void a(World var1, FontRenderer var2, Entity var3, Entity var4, GameSettings var5, float var6) {
      this.b = var1;
      this.g = var5;
      this.c = var3;
      this.d = var4;
      this.n = var2;
      if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).bJ()) {
         IBlockState var7 = var1.p(new BlockPos(var3));
         Block var8 = var7.c();
         if (var8 == Blocks.C) {
            int var9 = var7.b(BlockBed.O).b();
            this.e = var9 * 90 + 180;
            this.f = 0.0F;
         }
      } else {
         this.e = var3.A + (var3.y - var3.A) * var6;
         this.f = var3.B + (var3.z - var3.B) * var6;
      }

      if (var5.aB == 2) {
         this.e += 180.0F;
      }

      this.h = var3.P + (var3.s - var3.P) * var6;
      this.i = var3.Q + (var3.t - var3.Q) * var6;
      this.j = var3.R + (var3.u - var3.R) * var6;
   }

   public void a(float var1) {
      this.e = var1;
   }

   public boolean a() {
      return this.s;
   }

   public void a(boolean var1) {
      this.s = var1;
   }

   public void b(boolean var1) {
      this.t = var1;
   }

   public boolean b() {
      return this.t;
   }

   public boolean a(Entity var1, float var2) {
      return this.a(var1, var2, false);
   }

   public boolean a(Entity var1, ICamera var2, double var3, double var5, double var7) {
      Render var9 = this.a(var1);
      return var9 != null && var9.a(var1, var2, var3, var5, var7);
   }

   public boolean a(Entity var1, float var2, boolean var3) {
      if (var1.W == 0) {
         var1.P = var1.s;
         var1.Q = var1.t;
         var1.R = var1.u;
      }

      double var4 = var1.P + (var1.s - var1.P) * var2;
      double var6 = var1.Q + (var1.t - var1.Q) * var2;
      double var8 = var1.R + (var1.u - var1.R) * var2;
      float var10 = var1.A + (var1.y - var1.A) * var2;
      int var11 = var1.b(var2);
      if (var1.at()) {
         var11 = 15728880;
      }

      int var12 = var11 % 65536;
      int var13 = var11 / 65536;
      OpenGlHelper.a(OpenGlHelper.r, var12 / 1.0F, var13 / 1.0F);
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      return this.a(var1, var4 - this.o, var6 - this.p, var8 - this.q, var10, var2, var3);
   }

   public void b(Entity var1, float var2) {
      double var3 = var1.P + (var1.s - var1.P) * var2;
      double var5 = var1.Q + (var1.t - var1.Q) * var2;
      double var7 = var1.R + (var1.u - var1.R) * var2;
      Render var9 = this.a(var1);
      if (var9 != null && this.a != null) {
         int var10 = var1.b(var2);
         int var11 = var10 % 65536;
         int var12 = var10 / 65536;
         OpenGlHelper.a(OpenGlHelper.r, var11 / 1.0F, var12 / 1.0F);
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         var9.a(var1, var3 - this.o, var5 - this.p, var7 - this.q);
      }
   }

   public boolean a(Entity var1, double var2, double var4, double var6, float var8, float var9) {
      return this.a(var1, var2, var4, var6, var8, var9, false);
   }

   public boolean a(Entity var1, double var2, double var4, double var6, float var8, float var9, boolean var10) {
      Render var11 = null;

      try {
         var11 = this.a(var1);
         if (var11 != null && this.a != null) {
            try {
               if (var11 instanceof RendererLivingEntity) {
                  ((RendererLivingEntity)var11).a(this.r);
               }

               var11.a(var1, var2, var4, var6, var8, var9);
            } catch (Throwable var18) {
               throw new ReportedException(CrashReport.a(var18, "Rendering entity in world"));
            }

            try {
               if (!this.r) {
                  var11.b(var1, var2, var4, var6, var8, var9);
               }
            } catch (Throwable var17) {
               throw new ReportedException(CrashReport.a(var17, "Post-rendering entity in world"));
            }

            if (this.t && !var1.ax() && !var10) {
               try {
                  this.b(var1, var2, var4, var6, var8, var9);
               } catch (Throwable var16) {
                  throw new ReportedException(CrashReport.a(var16, "Rendering entity hitbox in world"));
               }
            }
         } else if (this.a != null) {
            return false;
         }

         return true;
      } catch (Throwable var19) {
         CrashReport var13 = CrashReport.a(var19, "Rendering entity in world");
         CrashReportCategory var14 = var13.a("Entity being rendered");
         var1.a(var14);
         CrashReportCategory var15 = var13.a("Renderer details");
         var15.a("Assigned renderer", var11);
         var15.a("Location", CrashReportCategory.a(var2, var4, var6));
         var15.a("Rotation", var8);
         var15.a("Delta", var9);
         throw new ReportedException(var13);
      }
   }

   private void b(Entity var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.a(false);
      GlStateManager.x();
      GlStateManager.f();
      GlStateManager.p();
      GlStateManager.k();
      float var10 = var1.J / 2.0F;
      AxisAlignedBB var11 = var1.aR();
      AxisAlignedBB var12 = new AxisAlignedBB(
         var11.a - var1.s + var2, var11.b - var1.t + var4, var11.c - var1.u + var6, var11.d - var1.s + var2, var11.e - var1.t + var4, var11.f - var1.u + var6
      );
      RenderGlobal.a(var12, 255, 255, 255, 255);
      if (var1 instanceof EntityLivingBase) {
         float var13 = 0.01F;
         RenderGlobal.a(
            new AxisAlignedBB(var2 - var10, var4 + var1.aS() - 0.01F, var6 - var10, var2 + var10, var4 + var1.aS() + 0.01F, var6 + var10), 255, 0, 0, 255
         );
      }

      Tessellator var16 = Tessellator.a();
      WorldRenderer var14 = var16.c();
      Vec3 var15 = var1.d(var9);
      var14.a(3, DefaultVertexFormats.f);
      var14.b(var2, var4 + var1.aS(), var6).b(0, 0, 255, 255).d();
      var14.b(var2 + var15.a * 2.0, var4 + var1.aS() + var15.b * 2.0, var6 + var15.c * 2.0).b(0, 0, 255, 255).d();
      var16.b();
      GlStateManager.w();
      GlStateManager.e();
      GlStateManager.o();
      GlStateManager.k();
      GlStateManager.a(true);
   }

   public void a(World var1) {
      this.b = var1;
   }

   public double b(double var1, double var3, double var5) {
      double var7 = var1 - this.h;
      double var9 = var3 - this.i;
      double var11 = var5 - this.j;
      return var7 * var7 + var9 * var9 + var11 * var11;
   }

   public FontRenderer c() {
      return this.n;
   }

   public void c(boolean var1) {
      this.r = var1;
   }
}
