package net.minecraft.client.renderer;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MouseFilter;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.biome.BiomeGenBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;

public class EntityRenderer implements IResourceManagerReloadListener {
   private static final Logger e = LogManager.getLogger();
   private static final ResourceLocation f = new ResourceLocation("textures/environment/rain.png");
   private static final ResourceLocation g = new ResourceLocation("textures/environment/snow.png");
   public static boolean a;
   public static int b;
   private Minecraft h;
   private final IResourceManager i;
   private Random j = new Random();
   private float k;
   public final ItemRenderer c;
   private final MapItemRenderer l;
   private int m;
   private Entity n;
   private MouseFilter o = new MouseFilter();
   private MouseFilter p = new MouseFilter();
   private float q = 4.0F;
   private float r = 4.0F;
   private float s;
   private float t;
   private float u;
   private float v;
   private float w;
   private float x;
   private float y;
   private float z;
   private float A;
   private boolean B;
   private boolean C = true;
   private boolean D = true;
   private long E = Minecraft.J();
   private long F;
   private final DynamicTexture G;
   private final int[] H;
   private final ResourceLocation I;
   private boolean J;
   private float K;
   private float L;
   private int M;
   private float[] N = new float[1024];
   private float[] O = new float[1024];
   private FloatBuffer P = GLAllocation.h(16);
   private float Q;
   private float R;
   private float S;
   private float T;
   private float U;
   private int V = 0;
   private boolean W = false;
   private double X = 1.0;
   private double Y;
   private double Z;
   private ShaderGroup aa;
   private static final ResourceLocation[] ab = new ResourceLocation[]{
      new ResourceLocation("shaders/post/notch.json"),
      new ResourceLocation("shaders/post/fxaa.json"),
      new ResourceLocation("shaders/post/art.json"),
      new ResourceLocation("shaders/post/bumpy.json"),
      new ResourceLocation("shaders/post/blobs2.json"),
      new ResourceLocation("shaders/post/pencil.json"),
      new ResourceLocation("shaders/post/color_convolve.json"),
      new ResourceLocation("shaders/post/deconverge.json"),
      new ResourceLocation("shaders/post/flip.json"),
      new ResourceLocation("shaders/post/invert.json"),
      new ResourceLocation("shaders/post/ntsc.json"),
      new ResourceLocation("shaders/post/outline.json"),
      new ResourceLocation("shaders/post/phosphor.json"),
      new ResourceLocation("shaders/post/scan_pincushion.json"),
      new ResourceLocation("shaders/post/sobel.json"),
      new ResourceLocation("shaders/post/bits.json"),
      new ResourceLocation("shaders/post/desaturate.json"),
      new ResourceLocation("shaders/post/green.json"),
      new ResourceLocation("shaders/post/blur.json"),
      new ResourceLocation("shaders/post/wobble.json"),
      new ResourceLocation("shaders/post/blobs.json"),
      new ResourceLocation("shaders/post/antialias.json"),
      new ResourceLocation("shaders/post/creeper.json"),
      new ResourceLocation("shaders/post/spider.json")
   };
   public static final int d = ab.length;
   private int ac = d;
   private boolean ad = false;
   private int ae = 0;

   public EntityRenderer(Minecraft var1, IResourceManager var2) {
      this.h = var1;
      this.i = var2;
      this.c = var1.ah();
      this.l = new MapItemRenderer(var1.P());
      this.G = new DynamicTexture(16, 16);
      this.I = var1.P().a("lightMap", this.G);
      this.H = this.G.e();
      this.aa = null;

      for (int var3 = 0; var3 < 32; var3++) {
         for (int var4 = 0; var4 < 32; var4++) {
            float var5 = var4 - 16;
            float var6 = var3 - 16;
            float var7 = MathHelper.c(var5 * var5 + var6 * var6);
            this.N[var3 << 5 | var4] = -var6 / var7;
            this.O[var3 << 5 | var4] = var5 / var7;
         }
      }
   }

   public boolean a() {
      return OpenGlHelper.O && this.aa != null;
   }

   public void b() {
      if (this.aa != null) {
         this.aa.a();
      }

      this.aa = null;
      this.ac = d;
   }

   public void c() {
      this.ad = !this.ad;
   }

   public void a(Entity var1) {
      if (OpenGlHelper.O) {
         if (this.aa != null) {
            this.aa.a();
         }

         this.aa = null;
         if (var1 instanceof EntityCreeper) {
            this.a(new ResourceLocation("shaders/post/creeper.json"));
         } else if (var1 instanceof EntitySpider) {
            this.a(new ResourceLocation("shaders/post/spider.json"));
         } else if (var1 instanceof EntityEnderman) {
            this.a(new ResourceLocation("shaders/post/invert.json"));
         }
      }
   }

   public void d() {
      if (OpenGlHelper.O) {
         if (this.h.ac() instanceof EntityPlayer) {
            if (this.aa != null) {
               this.aa.a();
            }

            this.ac = (this.ac + 1) % (ab.length + 1);
            if (this.ac != d) {
               this.a(ab[this.ac]);
            } else {
               this.aa = null;
            }
         }
      }
   }

   private void a(ResourceLocation var1) {
      try {
         this.aa = new ShaderGroup(this.h.P(), this.i, this.h.b(), var1);
         this.aa.a(this.h.d, this.h.e);
         this.ad = true;
      } catch (IOException var3) {
         e.warn("Failed to load shader: " + var1, var3);
         this.ac = d;
         this.ad = false;
      } catch (JsonSyntaxException var4) {
         e.warn("Failed to load shader: " + var1, var4);
         this.ac = d;
         this.ad = false;
      }
   }

   @Override
   public void a(IResourceManager var1) {
      if (this.aa != null) {
         this.aa.a();
      }

      this.aa = null;
      if (this.ac != d) {
         this.a(ab[this.ac]);
      } else {
         this.a(this.h.ac());
      }
   }

   public void e() {
      if (OpenGlHelper.O && ShaderLinkHelper.b() == null) {
         ShaderLinkHelper.a();
      }

      this.l();
      this.m();
      this.T = this.U;
      this.r = this.q;
      if (this.h.t.aG) {
         float var1 = this.h.t.a * 0.6F + 0.2F;
         float var2 = var1 * var1 * var1 * 8.0F;
         this.u = this.o.a(this.s, 0.05F * var2);
         this.v = this.p.a(this.t, 0.05F * var2);
         this.w = 0.0F;
         this.s = 0.0F;
         this.t = 0.0F;
      } else {
         this.u = 0.0F;
         this.v = 0.0F;
         this.o.a();
         this.p.a();
      }

      if (this.h.ac() == null) {
         this.h.a(this.h.h);
      }

      float var4 = this.h.f.o(new BlockPos(this.h.ac()));
      float var5 = this.h.t.c / 32.0F;
      float var3 = var4 * (1.0F - var5) + var5;
      this.U = this.U + (var3 - this.U) * 0.1F;
      this.m++;
      this.c.a();
      this.o();
      this.A = this.z;
      if (BossStatus.d) {
         this.z += 0.05F;
         if (this.z > 1.0F) {
            this.z = 1.0F;
         }

         BossStatus.d = false;
      } else if (this.z > 0.0F) {
         this.z -= 0.0125F;
      }
   }

   public ShaderGroup f() {
      return this.aa;
   }

   public void a(int var1, int var2) {
      if (OpenGlHelper.O) {
         if (this.aa != null) {
            this.aa.a(var1, var2);
         }

         this.h.g.a(var1, var2);
      }
   }

   public void a(float var1) {
      Entity var2 = this.h.ac();
      if (var2 != null) {
         if (this.h.f != null) {
            this.h.A.a("pick");
            this.h.i = null;
            double var3 = this.h.c.d();
            this.h.s = var2.a(var3, var1);
            double var5 = var3;
            Vec3 var7 = var2.e(var1);
            boolean var8 = false;
            byte var9 = 3;
            if (this.h.c.i()) {
               var3 = 6.0;
               var5 = 6.0;
            } else {
               if (var3 > 3.0) {
                  var8 = true;
               }

               var3 = var3;
            }

            if (this.h.s != null) {
               var5 = this.h.s.c.f(var7);
            }

            Vec3 var10 = var2.d(var1);
            Vec3 var11 = var7.b(var10.a * var3, var10.b * var3, var10.c * var3);
            this.n = null;
            Vec3 var12 = null;
            float var13 = 1.0F;
            List var14 = this.h
               .f
               .a(
                  var2,
                  var2.aR().a(var10.a * var3, var10.b * var3, var10.c * var3).b(var13, var13, var13),
                  Predicates.and(EntitySelectors.d, new Predicate<Entity>() {
                     public boolean a(Entity var1) {
                        return var1.ad();
                     }
                  })
               );
            double var15 = var5;

            for (int var17 = 0; var17 < var14.size(); var17++) {
               Entity var18 = (Entity)var14.get(var17);
               float var19 = var18.ao();
               AxisAlignedBB var20 = var18.aR().b(var19, var19, var19);
               MovingObjectPosition var21 = var20.a(var7, var11);
               if (var20.a(var7)) {
                  if (var15 >= 0.0) {
                     this.n = var18;
                     var12 = var21 == null ? var7 : var21.c;
                     var15 = 0.0;
                  }
               } else if (var21 != null) {
                  double var22 = var7.f(var21.c);
                  if (var22 < var15 || var15 == 0.0) {
                     if (var18 == var2.m) {
                        if (var15 == 0.0) {
                           this.n = var18;
                           var12 = var21.c;
                        }
                     } else {
                        this.n = var18;
                        var12 = var21.c;
                        var15 = var22;
                     }
                  }
               }
            }

            if (this.n != null && var8 && var7.f(var12) > 3.0) {
               this.n = null;
               this.h.s = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.a, var12, null, new BlockPos(var12));
            }

            if (this.n != null && (var15 < var5 || this.h.s == null)) {
               this.h.s = new MovingObjectPosition(this.n, var12);
               if (this.n instanceof EntityLivingBase || this.n instanceof EntityItemFrame) {
                  this.h.i = this.n;
               }
            }

            this.h.A.b();
         }
      }
   }

   private void l() {
      float var1 = 1.0F;
      if (this.h.ac() instanceof AbstractClientPlayer) {
         AbstractClientPlayer var2 = (AbstractClientPlayer)this.h.ac();
         var1 = var2.o();
      }

      this.y = this.x;
      this.x = this.x + (var1 - this.x) * 0.5F;
      if (this.x > 1.5F) {
         this.x = 1.5F;
      }

      if (this.x < 0.1F) {
         this.x = 0.1F;
      }
   }

   private float a(float var1, boolean var2) {
      if (this.W) {
         return 90.0F;
      } else {
         Entity var3 = this.h.ac();
         float var4 = 70.0F;
         if (var2) {
            var4 = this.h.t.aI;
            var4 *= this.y + (this.x - this.y) * var1;
         }

         if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).bn() <= 0.0F) {
            float var5 = ((EntityLivingBase)var3).ax + var1;
            var4 /= (1.0F - 500.0F / (var5 + 500.0F)) * 2.0F + 1.0F;
         }

         Block var7 = ActiveRenderInfo.a(this.h.f, var3, var1);
         if (var7.t() == Material.h) {
            var4 = var4 * 60.0F / 70.0F;
         }

         return var4;
      }
   }

   private void d(float var1) {
      if (this.h.ac() instanceof EntityLivingBase) {
         EntityLivingBase var2 = (EntityLivingBase)this.h.ac();
         float var3 = var2.au - var1;
         if (var2.bn() <= 0.0F) {
            float var4 = var2.ax + var1;
            GlStateManager.b(40.0F - 8000.0F / (var4 + 200.0F), 0.0F, 0.0F, 1.0F);
         }

         if (var3 < 0.0F) {
            return;
         }

         var3 /= var2.av;
         var3 = MathHelper.a(var3 * var3 * var3 * var3 * (float) Math.PI);
         float var7 = var2.aw;
         GlStateManager.b(-var7, 0.0F, 1.0F, 0.0F);
         GlStateManager.b(-var3 * 14.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.b(var7, 0.0F, 1.0F, 0.0F);
      }
   }

   private void e(float var1) {
      if (this.h.ac() instanceof EntityPlayer) {
         EntityPlayer var2 = (EntityPlayer)this.h.ac();
         float var3 = var2.M - var2.L;
         float var4 = -(var2.M + var3 * var1);
         float var5 = var2.bn + (var2.bo - var2.bn) * var1;
         float var6 = var2.aE + (var2.aF - var2.aE) * var1;
         GlStateManager.b(MathHelper.a(var4 * (float) Math.PI) * var5 * 0.5F, -Math.abs(MathHelper.b(var4 * (float) Math.PI) * var5), 0.0F);
         GlStateManager.b(MathHelper.a(var4 * (float) Math.PI) * var5 * 3.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.b(Math.abs(MathHelper.b(var4 * (float) Math.PI - 0.2F) * var5) * 5.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.b(var6, 1.0F, 0.0F, 0.0F);
      }
   }

   private void f(float var1) {
      Entity var2 = this.h.ac();
      float var3 = var2.aS();
      double var4 = var2.p + (var2.s - var2.p) * var1;
      double var6 = var2.q + (var2.t - var2.q) * var1 + var3;
      double var8 = var2.r + (var2.u - var2.r) * var1;
      if (var2 instanceof EntityLivingBase && ((EntityLivingBase)var2).bJ()) {
         var3 = (float)(var3 + 1.0);
         GlStateManager.b(0.0F, 0.3F, 0.0F);
         if (!this.h.t.aH) {
            BlockPos var30 = new BlockPos(var2);
            IBlockState var11 = this.h.f.p(var30);
            Block var32 = var11.c();
            if (var32 == Blocks.C) {
               int var33 = var11.b(BlockBed.O).b();
               GlStateManager.b((float)(var33 * 90), 0.0F, 1.0F, 0.0F);
            }

            GlStateManager.b(var2.A + (var2.y - var2.A) * var1 + 180.0F, 0.0F, -1.0F, 0.0F);
            GlStateManager.b(var2.B + (var2.z - var2.B) * var1, -1.0F, 0.0F, 0.0F);
         }
      } else if (this.h.t.aB > 0) {
         double var10 = this.r + (this.q - this.r) * var1;
         if (this.h.t.aH) {
            GlStateManager.b(0.0F, 0.0F, (float)(-var10));
         } else {
            float var12 = var2.y;
            float var13 = var2.z;
            if (this.h.t.aB == 2) {
               var13 += 180.0F;
            }

            double var14 = -MathHelper.a(var12 / 180.0F * (float) Math.PI) * MathHelper.b(var13 / 180.0F * (float) Math.PI) * var10;
            double var16 = MathHelper.b(var12 / 180.0F * (float) Math.PI) * MathHelper.b(var13 / 180.0F * (float) Math.PI) * var10;
            double var18 = -MathHelper.a(var13 / 180.0F * (float) Math.PI) * var10;

            for (int var20 = 0; var20 < 8; var20++) {
               float var21 = (var20 & 1) * 2 - 1;
               float var22 = (var20 >> 1 & 1) * 2 - 1;
               float var23 = (var20 >> 2 & 1) * 2 - 1;
               var21 *= 0.1F;
               var22 *= 0.1F;
               var23 *= 0.1F;
               MovingObjectPosition var24 = this.h
                  .f
                  .a(new Vec3(var4 + var21, var6 + var22, var8 + var23), new Vec3(var4 - var14 + var21 + var23, var6 - var18 + var22, var8 - var16 + var23));
               if (var24 != null) {
                  double var25 = var24.c.f(new Vec3(var4, var6, var8));
                  if (var25 < var10) {
                     var10 = var25;
                  }
               }
            }

            if (this.h.t.aB == 2) {
               GlStateManager.b(180.0F, 0.0F, 1.0F, 0.0F);
            }

            GlStateManager.b(var2.z - var13, 1.0F, 0.0F, 0.0F);
            GlStateManager.b(var2.y - var12, 0.0F, 1.0F, 0.0F);
            GlStateManager.b(0.0F, 0.0F, (float)(-var10));
            GlStateManager.b(var12 - var2.y, 0.0F, 1.0F, 0.0F);
            GlStateManager.b(var13 - var2.z, 1.0F, 0.0F, 0.0F);
         }
      } else {
         GlStateManager.b(0.0F, 0.0F, -0.1F);
      }

      if (!this.h.t.aH) {
         GlStateManager.b(var2.B + (var2.z - var2.B) * var1, 1.0F, 0.0F, 0.0F);
         if (var2 instanceof EntityAnimal) {
            EntityAnimal var31 = (EntityAnimal)var2;
            GlStateManager.b(var31.aL + (var31.aK - var31.aL) * var1 + 180.0F, 0.0F, 1.0F, 0.0F);
         } else {
            GlStateManager.b(var2.A + (var2.y - var2.A) * var1 + 180.0F, 0.0F, 1.0F, 0.0F);
         }
      }

      GlStateManager.b(0.0F, -var3, 0.0F);
      var4 = var2.p + (var2.s - var2.p) * var1;
      var6 = var2.q + (var2.t - var2.q) * var1 + var3;
      var8 = var2.r + (var2.u - var2.r) * var1;
      this.B = this.h.g.a(var4, var6, var8, var1);
   }

   private void a(float var1, int var2) {
      this.k = this.h.t.c * 16;
      GlStateManager.n(5889);
      GlStateManager.D();
      float var3 = 0.07F;
      if (this.h.t.e) {
         GlStateManager.b(-(var2 * 2 - 1) * var3, 0.0F, 0.0F);
      }

      if (this.X != 1.0) {
         GlStateManager.b((float)this.Y, (float)(-this.Z), 0.0F);
         GlStateManager.a(this.X, this.X, 1.0);
      }

      Project.gluPerspective(this.a(var1, true), (float)this.h.d / this.h.e, 0.05F, this.k * MathHelper.a);
      GlStateManager.n(5888);
      GlStateManager.D();
      if (this.h.t.e) {
         GlStateManager.b((var2 * 2 - 1) * 0.1F, 0.0F, 0.0F);
      }

      this.d(var1);
      if (this.h.t.d) {
         this.e(var1);
      }

      float var4 = this.h.h.bI + (this.h.h.bH - this.h.h.bI) * var1;
      if (var4 > 0.0F) {
         byte var5 = 20;
         if (this.h.h.a(Potion.k)) {
            var5 = 7;
         }

         float var6 = 5.0F / (var4 * var4 + 5.0F) - var4 * 0.04F;
         var6 *= var6;
         GlStateManager.b((this.m + var1) * var5, 0.0F, 1.0F, 1.0F);
         GlStateManager.a(1.0F / var6, 1.0F, 1.0F);
         GlStateManager.b(-(this.m + var1) * var5, 0.0F, 1.0F, 1.0F);
      }

      this.f(var1);
      if (this.W) {
         switch (this.V) {
            case 0:
               GlStateManager.b(90.0F, 0.0F, 1.0F, 0.0F);
               break;
            case 1:
               GlStateManager.b(180.0F, 0.0F, 1.0F, 0.0F);
               break;
            case 2:
               GlStateManager.b(-90.0F, 0.0F, 1.0F, 0.0F);
               break;
            case 3:
               GlStateManager.b(90.0F, 1.0F, 0.0F, 0.0F);
               break;
            case 4:
               GlStateManager.b(-90.0F, 1.0F, 0.0F, 0.0F);
         }
      }
   }

   private void b(float var1, int var2) {
      if (!this.W) {
         GlStateManager.n(5889);
         GlStateManager.D();
         float var3 = 0.07F;
         if (this.h.t.e) {
            GlStateManager.b(-(var2 * 2 - 1) * var3, 0.0F, 0.0F);
         }

         Project.gluPerspective(this.a(var1, false), (float)this.h.d / this.h.e, 0.05F, this.k * 2.0F);
         GlStateManager.n(5888);
         GlStateManager.D();
         if (this.h.t.e) {
            GlStateManager.b((var2 * 2 - 1) * 0.1F, 0.0F, 0.0F);
         }

         GlStateManager.E();
         this.d(var1);
         if (this.h.t.d) {
            this.e(var1);
         }

         boolean var4 = this.h.ac() instanceof EntityLivingBase && ((EntityLivingBase)this.h.ac()).bJ();
         if (this.h.t.aB == 0 && !var4 && !this.h.t.aA && !this.h.c.a()) {
            this.i();
            this.c.a(var1);
            this.h();
         }

         GlStateManager.F();
         if (this.h.t.aB == 0 && !var4) {
            this.c.b(var1);
            this.d(var1);
         }

         if (this.h.t.d) {
            this.e(var1);
         }
      }
   }

   public void h() {
      GlStateManager.g(OpenGlHelper.r);
      GlStateManager.x();
      GlStateManager.g(OpenGlHelper.q);
   }

   public void i() {
      GlStateManager.g(OpenGlHelper.r);
      GlStateManager.n(5890);
      GlStateManager.D();
      float var1 = 0.00390625F;
      GlStateManager.a(var1, var1, var1);
      GlStateManager.b(8.0F, 8.0F, 8.0F);
      GlStateManager.n(5888);
      this.h.P().a(this.I);
      GL11.glTexParameteri(3553, 10241, 9729);
      GL11.glTexParameteri(3553, 10240, 9729);
      GL11.glTexParameteri(3553, 10242, 10496);
      GL11.glTexParameteri(3553, 10243, 10496);
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.w();
      GlStateManager.g(OpenGlHelper.q);
   }

   private void m() {
      this.L = (float)(this.L + (Math.random() - Math.random()) * Math.random() * Math.random());
      this.L = (float)(this.L * 0.9);
      this.K = this.K + (this.L - this.K) * 1.0F;
      this.J = true;
   }

   private void g(float var1) {
      if (this.J) {
         this.h.A.a("lightTex");
         WorldClient var2 = this.h.f;
         if (var2 != null) {
            float var3 = var2.b(1.0F);
            float var4 = var3 * 0.95F + 0.05F;

            for (int var5 = 0; var5 < 256; var5++) {
               float var6 = var2.t.p()[var5 / 16] * var4;
               float var7 = var2.t.p()[var5 % 16] * (this.K * 0.1F + 1.5F);
               if (var2.ac() > 0) {
                  var6 = var2.t.p()[var5 / 16];
               }

               float var8 = var6 * (var3 * 0.65F + 0.35F);
               float var9 = var6 * (var3 * 0.65F + 0.35F);
               float var12 = var7 * ((var7 * 0.6F + 0.4F) * 0.6F + 0.4F);
               float var13 = var7 * (var7 * var7 * 0.6F + 0.4F);
               float var14 = var8 + var7;
               float var15 = var9 + var12;
               float var16 = var6 + var13;
               var14 = var14 * 0.96F + 0.03F;
               var15 = var15 * 0.96F + 0.03F;
               var16 = var16 * 0.96F + 0.03F;
               if (this.z > 0.0F) {
                  float var17 = this.A + (this.z - this.A) * var1;
                  var14 = var14 * (1.0F - var17) + var14 * 0.7F * var17;
                  var15 = var15 * (1.0F - var17) + var15 * 0.6F * var17;
                  var16 = var16 * (1.0F - var17) + var16 * 0.6F * var17;
               }

               if (var2.t.q() == 1) {
                  var14 = 0.22F + var7 * 0.75F;
                  var15 = 0.28F + var12 * 0.75F;
                  var16 = 0.25F + var13 * 0.75F;
               }

               if (this.h.h.a(Potion.r)) {
                  float var34 = this.a(this.h.h, var1);
                  float var18 = 1.0F / var14;
                  if (var18 > 1.0F / var15) {
                     var18 = 1.0F / var15;
                  }

                  if (var18 > 1.0F / var16) {
                     var18 = 1.0F / var16;
                  }

                  var14 = var14 * (1.0F - var34) + var14 * var18 * var34;
                  var15 = var15 * (1.0F - var34) + var15 * var18 * var34;
                  var16 = var16 * (1.0F - var34) + var16 * var18 * var34;
               }

               if (var14 > 1.0F) {
                  var14 = 1.0F;
               }

               if (var15 > 1.0F) {
                  var15 = 1.0F;
               }

               if (var16 > 1.0F) {
                  var16 = 1.0F;
               }

               float var35 = this.h.t.aJ;
               float var36 = 1.0F - var14;
               float var19 = 1.0F - var15;
               float var20 = 1.0F - var16;
               var36 = 1.0F - var36 * var36 * var36 * var36;
               var19 = 1.0F - var19 * var19 * var19 * var19;
               var20 = 1.0F - var20 * var20 * var20 * var20;
               var14 = var14 * (1.0F - var35) + var36 * var35;
               var15 = var15 * (1.0F - var35) + var19 * var35;
               var16 = var16 * (1.0F - var35) + var20 * var35;
               var14 = var14 * 0.96F + 0.03F;
               var15 = var15 * 0.96F + 0.03F;
               var16 = var16 * 0.96F + 0.03F;
               if (var14 > 1.0F) {
                  var14 = 1.0F;
               }

               if (var15 > 1.0F) {
                  var15 = 1.0F;
               }

               if (var16 > 1.0F) {
                  var16 = 1.0F;
               }

               if (var14 < 0.0F) {
                  var14 = 0.0F;
               }

               if (var15 < 0.0F) {
                  var15 = 0.0F;
               }

               if (var16 < 0.0F) {
                  var16 = 0.0F;
               }

               short var21 = 255;
               int var22 = (int)(var14 * 255.0F);
               int var23 = (int)(var15 * 255.0F);
               int var24 = (int)(var16 * 255.0F);
               this.H[var5] = var21 << 24 | var22 << 16 | var23 << 8 | var24;
            }

            this.G.d();
            this.J = false;
            this.h.A.b();
         }
      }
   }

   private float a(EntityLivingBase var1, float var2) {
      int var3 = var1.b(Potion.r).b();
      return var3 > 200 ? 1.0F : 0.7F + MathHelper.a((var3 - var2) * (float) Math.PI * 0.2F) * 0.3F;
   }

   public void a(float var1, long var2) {
      boolean var4 = Display.isActive();
      if (!var4 && this.h.t.z && (!this.h.t.A || !Mouse.isButtonDown(1))) {
         if (Minecraft.J() - this.E > 500L) {
            this.h.p();
         }
      } else {
         this.E = Minecraft.J();
      }

      this.h.A.a("mouse");
      if (var4 && Minecraft.a && this.h.w && !Mouse.isInsideWindow()) {
         Mouse.setGrabbed(false);
         Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
         Mouse.setGrabbed(true);
      }

      if (this.h.w && var4) {
         this.h.u.c();
         float var5 = this.h.t.a * 0.6F + 0.2F;
         float var6 = var5 * var5 * var5 * 8.0F;
         float var7 = this.h.u.a * var6;
         float var8 = this.h.u.b * var6;
         byte var9 = 1;
         if (this.h.t.b) {
            var9 = -1;
         }

         if (this.h.t.aG) {
            this.s += var7;
            this.t += var8;
            float var10 = var1 - this.w;
            this.w = var1;
            var7 = this.u * var10;
            var8 = this.v * var10;
            this.h.h.c(var7, var8 * var9);
         } else {
            this.s = 0.0F;
            this.t = 0.0F;
            this.h.h.c(var7, var8 * var9);
         }
      }

      this.h.A.b();
      if (!this.h.r) {
         a = this.h.t.e;
         final ScaledResolution var17 = new ScaledResolution(this.h);
         int var18 = var17.a();
         int var20 = var17.b();
         final int var22 = Mouse.getX() * var18 / this.h.d;
         final int var23 = var20 - Mouse.getY() * var20 / this.h.e - 1;
         int var24 = this.h.t.g;
         if (this.h.f != null) {
            this.h.A.a("level");
            int var11 = Math.min(Minecraft.ai(), var24);
            var11 = Math.max(var11, 60);
            long var12 = System.nanoTime() - var2;
            long var14 = Math.max(1000000000 / var11 / 4 - var12, 0L);
            this.b(var1, System.nanoTime() + var14);
            if (OpenGlHelper.O) {
               this.h.g.c();
               if (this.aa != null && this.ad) {
                  GlStateManager.n(5890);
                  GlStateManager.E();
                  GlStateManager.D();
                  this.aa.a(var1);
                  GlStateManager.F();
               }

               this.h.b().a(true);
            }

            this.F = System.nanoTime();
            this.h.A.c("gui");
            if (!this.h.t.aA || this.h.m != null) {
               GlStateManager.a(516, 0.1F);
               this.h.q.a(var1);
            }

            this.h.A.b();
         } else {
            GlStateManager.b(0, 0, this.h.d, this.h.e);
            GlStateManager.n(5889);
            GlStateManager.D();
            GlStateManager.n(5888);
            GlStateManager.D();
            this.j();
            this.F = System.nanoTime();
         }

         if (this.h.m != null) {
            GlStateManager.m(256);

            try {
               this.h.m.a(var22, var23, var1);
            } catch (Throwable var16) {
               CrashReport var26 = CrashReport.a(var16, "Rendering screen");
               CrashReportCategory var13 = var26.a("Screen render details");
               var13.a("Screen name", new Callable<String>() {
                  public String a() throws Exception {
                     return EntityRenderer.this.h.m.getClass().getCanonicalName();
                  }
               });
               var13.a("Mouse location", new Callable<String>() {
                  public String a() throws Exception {
                     return String.format("Scaled: (%d, %d). Absolute: (%d, %d)", var22, var23, Mouse.getX(), Mouse.getY());
                  }
               });
               var13.a(
                  "Screen size",
                  new Callable<String>() {
                     public String a() throws Exception {
                        return String.format(
                           "Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d",
                           var17.a(),
                           var17.b(),
                           EntityRenderer.this.h.d,
                           EntityRenderer.this.h.e,
                           var17.e()
                        );
                     }
                  }
               );
               throw new ReportedException(var26);
            }
         }
      }
   }

   public void b(float var1) {
      this.j();
      this.h.q.c(new ScaledResolution(this.h));
   }

   private boolean n() {
      if (!this.D) {
         return false;
      } else {
         Entity var1 = this.h.ac();
         boolean var2 = var1 instanceof EntityPlayer && !this.h.t.aA;
         if (var2 && !((EntityPlayer)var1).bA.e) {
            ItemStack var3 = ((EntityPlayer)var1).bZ();
            if (this.h.s != null && this.h.s.a == MovingObjectPosition.MovingObjectType.b) {
               BlockPos var4 = this.h.s.a();
               Block var5 = this.h.f.p(var4).c();
               if (this.h.c.l() == WorldSettings.GameType.e) {
                  var2 = var5.z() && this.h.f.s(var4) instanceof IInventory;
               } else {
                  var2 = var3 != null && (var3.c(var5) || var3.d(var5));
               }
            }
         }

         return var2;
      }
   }

   private void h(float var1) {
      if (this.h.t.aC && !this.h.t.aA && !this.h.h.cq() && !this.h.t.w) {
         Entity var2 = this.h.ac();
         GlStateManager.l();
         GlStateManager.a(770, 771, 1, 0);
         GL11.glLineWidth(1.0F);
         GlStateManager.x();
         GlStateManager.a(false);
         GlStateManager.E();
         GlStateManager.n(5888);
         GlStateManager.D();
         this.f(var1);
         GlStateManager.b(0.0F, var2.aS(), 0.0F);
         RenderGlobal.a(new AxisAlignedBB(0.0, 0.0, 0.0, 0.005, 1.0E-4, 1.0E-4), 255, 0, 0, 255);
         RenderGlobal.a(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0E-4, 1.0E-4, 0.005), 0, 0, 255, 255);
         RenderGlobal.a(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0E-4, 0.0033, 1.0E-4), 0, 255, 0, 255);
         GlStateManager.F();
         GlStateManager.a(true);
         GlStateManager.w();
         GlStateManager.k();
      }
   }

   public void b(float var1, long var2) {
      this.g(var1);
      if (this.h.ac() == null) {
         this.h.a(this.h.h);
      }

      this.a(var1);
      GlStateManager.j();
      GlStateManager.d();
      GlStateManager.a(516, 0.5F);
      this.h.A.a("center");
      if (this.h.t.e) {
         b = 0;
         GlStateManager.a(false, true, true, false);
         this.a(0, var1, var2);
         b = 1;
         GlStateManager.a(true, false, false, false);
         this.a(1, var1, var2);
         GlStateManager.a(true, true, true, false);
      } else {
         this.a(2, var1, var2);
      }

      this.h.A.b();
   }

   private void a(int var1, float var2, long var3) {
      RenderGlobal var5 = this.h.g;
      EffectRenderer var6 = this.h.j;
      boolean var7 = this.n();
      GlStateManager.o();
      this.h.A.c("clear");
      GlStateManager.b(0, 0, this.h.d, this.h.e);
      this.i(var2);
      GlStateManager.m(16640);
      this.h.A.c("camera");
      this.a(var2, var1);
      ActiveRenderInfo.a(this.h.h, this.h.t.aB == 2);
      this.h.A.c("frustum");
      ClippingHelperImpl.a();
      this.h.A.c("culling");
      Frustum var8 = new Frustum();
      Entity var9 = this.h.ac();
      double var10 = var9.P + (var9.s - var9.P) * var2;
      double var12 = var9.Q + (var9.t - var9.Q) * var2;
      double var14 = var9.R + (var9.u - var9.R) * var2;
      var8.a(var10, var12, var14);
      if (this.h.t.c >= 4) {
         this.a(-1, var2);
         this.h.A.c("sky");
         GlStateManager.n(5889);
         GlStateManager.D();
         Project.gluPerspective(this.a(var2, true), (float)this.h.d / this.h.e, 0.05F, this.k * 2.0F);
         GlStateManager.n(5888);
         var5.a(var2, var1);
         GlStateManager.n(5889);
         GlStateManager.D();
         Project.gluPerspective(this.a(var2, true), (float)this.h.d / this.h.e, 0.05F, this.k * MathHelper.a);
         GlStateManager.n(5888);
      }

      this.a(0, var2);
      GlStateManager.j(7425);
      if (var9.t + var9.aS() < 128.0) {
         this.a(var5, var2, var1);
      }

      this.h.A.c("prepareterrain");
      this.a(0, var2);
      this.h.P().a(TextureMap.g);
      RenderHelper.a();
      this.h.A.c("terrain_setup");
      var5.a(var9, var2, var8, this.ae++, this.h.h.v());
      if (var1 == 0 || var1 == 2) {
         this.h.A.c("updatechunks");
         this.h.g.a(var3);
      }

      this.h.A.c("terrain");
      GlStateManager.n(5888);
      GlStateManager.E();
      GlStateManager.c();
      var5.a(EnumWorldBlockLayer.a, var2, var1, var9);
      GlStateManager.d();
      var5.a(EnumWorldBlockLayer.b, var2, var1, var9);
      this.h.P().b(TextureMap.g).b(false, false);
      var5.a(EnumWorldBlockLayer.c, var2, var1, var9);
      this.h.P().b(TextureMap.g).a();
      GlStateManager.j(7424);
      GlStateManager.a(516, 0.1F);
      if (!this.W) {
         GlStateManager.n(5888);
         GlStateManager.F();
         GlStateManager.E();
         RenderHelper.b();
         this.h.A.c("entities");
         var5.a(var9, var8, var2);
         RenderHelper.a();
         this.h();
         GlStateManager.n(5888);
         GlStateManager.F();
         GlStateManager.E();
         if (this.h.s != null && var9.a(Material.h) && var7) {
            EntityPlayer var16 = (EntityPlayer)var9;
            GlStateManager.c();
            this.h.A.c("outline");
            var5.a(var16, this.h.s, 0, var2);
            GlStateManager.d();
         }
      }

      GlStateManager.n(5888);
      GlStateManager.F();
      if (var7 && this.h.s != null && !var9.a(Material.h)) {
         EntityPlayer var17 = (EntityPlayer)var9;
         GlStateManager.c();
         this.h.A.c("outline");
         var5.a(var17, this.h.s, 0, var2);
         GlStateManager.d();
      }

      this.h.A.c("destroyProgress");
      GlStateManager.l();
      GlStateManager.a(770, 1, 1, 0);
      this.h.P().b(TextureMap.g).b(false, false);
      var5.a(Tessellator.a(), Tessellator.a().c(), var9, var2);
      this.h.P().b(TextureMap.g).a();
      GlStateManager.k();
      if (!this.W) {
         this.i();
         this.h.A.c("litParticles");
         var6.b(var9, var2);
         RenderHelper.a();
         this.a(0, var2);
         this.h.A.c("particles");
         var6.a(var9, var2);
         this.h();
      }

      GlStateManager.a(false);
      GlStateManager.o();
      this.h.A.c("weather");
      this.c(var2);
      GlStateManager.a(true);
      var5.a(var9, var2);
      GlStateManager.k();
      GlStateManager.o();
      GlStateManager.a(770, 771, 1, 0);
      GlStateManager.a(516, 0.1F);
      this.a(0, var2);
      GlStateManager.l();
      GlStateManager.a(false);
      this.h.P().a(TextureMap.g);
      GlStateManager.j(7425);
      this.h.A.c("translucent");
      var5.a(EnumWorldBlockLayer.d, var2, var1, var9);
      GlStateManager.j(7424);
      GlStateManager.a(true);
      GlStateManager.o();
      GlStateManager.k();
      GlStateManager.n();
      if (var9.t + var9.aS() >= 128.0) {
         this.h.A.c("aboveClouds");
         this.a(var5, var2, var1);
      }

      this.h.A.c("hand");
      if (this.C) {
         GlStateManager.m(256);
         this.b(var2, var1);
         this.h(var2);
      }
   }

   private void a(RenderGlobal var1, float var2, int var3) {
      if (this.h.t.e() != 0) {
         this.h.A.c("clouds");
         GlStateManager.n(5889);
         GlStateManager.D();
         Project.gluPerspective(this.a(var2, true), (float)this.h.d / this.h.e, 0.05F, this.k * 4.0F);
         GlStateManager.n(5888);
         GlStateManager.E();
         this.a(0, var2);
         var1.b(var2, var3);
         GlStateManager.n();
         GlStateManager.F();
         GlStateManager.n(5889);
         GlStateManager.D();
         Project.gluPerspective(this.a(var2, true), (float)this.h.d / this.h.e, 0.05F, this.k * MathHelper.a);
         GlStateManager.n(5888);
      }
   }

   private void o() {
      float var1 = this.h.f.j(1.0F);
      if (!this.h.t.i) {
         var1 /= 2.0F;
      }

      if (var1 != 0.0F) {
         this.j.setSeed(this.m * 312987231L);
         Entity var2 = this.h.ac();
         WorldClient var3 = this.h.f;
         BlockPos var4 = new BlockPos(var2);
         byte var5 = 10;
         double var6 = 0.0;
         double var8 = 0.0;
         double var10 = 0.0;
         int var12 = 0;
         int var13 = (int)(100.0F * var1 * var1);
         if (this.h.t.aM == 1) {
            var13 >>= 1;
         } else if (this.h.t.aM == 2) {
            var13 = 0;
         }

         for (int var14 = 0; var14 < var13; var14++) {
            BlockPos var15 = var3.q(var4.a(this.j.nextInt(var5) - this.j.nextInt(var5), 0, this.j.nextInt(var5) - this.j.nextInt(var5)));
            BiomeGenBase var16 = var3.b(var15);
            BlockPos var17 = var15.b();
            Block var18 = var3.p(var17).c();
            if (var15.o() <= var4.o() + var5 && var15.o() >= var4.o() - var5 && var16.e() && var16.a(var15) >= 0.15F) {
               double var19 = this.j.nextDouble();
               double var21 = this.j.nextDouble();
               if (var18.t() == Material.i) {
                  this.h.f.a(EnumParticleTypes.l, var15.n() + var19, var15.o() + 0.1F - var18.D(), var15.p() + var21, 0.0, 0.0, 0.0, new int[0]);
               } else if (var18.t() != Material.a) {
                  var18.a((IBlockAccess)var3, var17);
                  if (this.j.nextInt(++var12) == 0) {
                     var6 = var17.n() + var19;
                     var8 = var17.o() + 0.1F + var18.E() - 1.0;
                     var10 = var17.p() + var21;
                  }

                  this.h.f.a(EnumParticleTypes.N, var17.n() + var19, var17.o() + 0.1F + var18.E(), var17.p() + var21, 0.0, 0.0, 0.0, new int[0]);
               }
            }
         }

         if (var12 > 0 && this.j.nextInt(3) < this.M++) {
            this.M = 0;
            if (var8 > var4.o() + 1 && var3.q(var4).o() > MathHelper.d((float)var4.o())) {
               this.h.f.a(var6, var8, var10, "ambient.weather.rain", 0.1F, 0.5F, false);
            } else {
               this.h.f.a(var6, var8, var10, "ambient.weather.rain", 0.2F, 1.0F, false);
            }
         }
      }
   }

   protected void c(float var1) {
      float var2 = this.h.f.j(var1);
      if (!(var2 <= 0.0F)) {
         this.i();
         Entity var3 = this.h.ac();
         WorldClient var4 = this.h.f;
         int var5 = MathHelper.c(var3.s);
         int var6 = MathHelper.c(var3.t);
         int var7 = MathHelper.c(var3.u);
         Tessellator var8 = Tessellator.a();
         WorldRenderer var9 = var8.c();
         GlStateManager.p();
         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
         GlStateManager.l();
         GlStateManager.a(770, 771, 1, 0);
         GlStateManager.a(516, 0.1F);
         double var10 = var3.P + (var3.s - var3.P) * var1;
         double var12 = var3.Q + (var3.t - var3.Q) * var1;
         double var14 = var3.R + (var3.u - var3.R) * var1;
         int var16 = MathHelper.c(var12);
         byte var17 = 5;
         if (this.h.t.i) {
            var17 = 10;
         }

         byte var18 = -1;
         float var19 = this.m + var1;
         var9.c(-var10, -var12, -var14);
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         BlockPos.MutableBlockPos var20 = new BlockPos.MutableBlockPos();

         for (int var21 = var7 - var17; var21 <= var7 + var17; var21++) {
            for (int var22 = var5 - var17; var22 <= var5 + var17; var22++) {
               int var23 = (var21 - var7 + 16) * 32 + var22 - var5 + 16;
               double var24 = this.N[var23] * 0.5;
               double var26 = this.O[var23] * 0.5;
               var20.c(var22, 0, var21);
               BiomeGenBase var28 = var4.b(var20);
               if (var28.e() || var28.d()) {
                  int var29 = var4.q(var20).o();
                  int var30 = var6 - var17;
                  int var31 = var6 + var17;
                  if (var30 < var29) {
                     var30 = var29;
                  }

                  if (var31 < var29) {
                     var31 = var29;
                  }

                  int var32 = var29;
                  if (var29 < var16) {
                     var32 = var16;
                  }

                  if (var30 != var31) {
                     this.j.setSeed(var22 * var22 * 3121 + var22 * 45238971 ^ var21 * var21 * 418711 + var21 * 13761);
                     var20.c(var22, var30, var21);
                     float var33 = var28.a(var20);
                     if (var4.v().a(var33, var29) >= 0.15F) {
                        if (var18 != 0) {
                           if (var18 >= 0) {
                              var8.b();
                           }

                           var18 = 0;
                           this.h.P().a(f);
                           var9.a(7, DefaultVertexFormats.d);
                        }

                        double var34 = ((double)(this.m + var22 * var22 * 3121 + var22 * 45238971 + var21 * var21 * 418711 + var21 * 13761 & 31) + var1)
                           / 32.0
                           * (3.0 + this.j.nextDouble());
                        double var36 = var22 + 0.5F - var3.s;
                        double var38 = var21 + 0.5F - var3.u;
                        float var40 = MathHelper.a(var36 * var36 + var38 * var38) / var17;
                        float var41 = ((1.0F - var40 * var40) * 0.5F + 0.5F) * var2;
                        var20.c(var22, var32, var21);
                        int var42 = var4.b(var20, 0);
                        int var43 = var42 >> 16 & 65535;
                        int var44 = var42 & 65535;
                        var9.b(var22 - var24 + 0.5, (double)var30, var21 - var26 + 0.5)
                           .a(0.0, var30 * 0.25 + var34)
                           .a(1.0F, 1.0F, 1.0F, var41)
                           .a(var43, var44)
                           .d();
                        var9.b(var22 + var24 + 0.5, (double)var30, var21 + var26 + 0.5)
                           .a(1.0, var30 * 0.25 + var34)
                           .a(1.0F, 1.0F, 1.0F, var41)
                           .a(var43, var44)
                           .d();
                        var9.b(var22 + var24 + 0.5, (double)var31, var21 + var26 + 0.5)
                           .a(1.0, var31 * 0.25 + var34)
                           .a(1.0F, 1.0F, 1.0F, var41)
                           .a(var43, var44)
                           .d();
                        var9.b(var22 - var24 + 0.5, (double)var31, var21 - var26 + 0.5)
                           .a(0.0, var31 * 0.25 + var34)
                           .a(1.0F, 1.0F, 1.0F, var41)
                           .a(var43, var44)
                           .d();
                     } else {
                        if (var18 != 1) {
                           if (var18 >= 0) {
                              var8.b();
                           }

                           var18 = 1;
                           this.h.P().a(g);
                           var9.a(7, DefaultVertexFormats.d);
                        }

                        double var49 = ((this.m & 511) + var1) / 512.0F;
                        double var50 = this.j.nextDouble() + var19 * 0.01 * (float)this.j.nextGaussian();
                        double var51 = this.j.nextDouble() + var19 * (float)this.j.nextGaussian() * 0.001;
                        double var52 = var22 + 0.5F - var3.s;
                        double var53 = var21 + 0.5F - var3.u;
                        float var54 = MathHelper.a(var52 * var52 + var53 * var53) / var17;
                        float var45 = ((1.0F - var54 * var54) * 0.3F + 0.5F) * var2;
                        var20.c(var22, var32, var21);
                        int var46 = (var4.b(var20, 0) * 3 + 15728880) / 4;
                        int var47 = var46 >> 16 & 65535;
                        int var48 = var46 & 65535;
                        var9.b(var22 - var24 + 0.5, (double)var30, var21 - var26 + 0.5)
                           .a(0.0 + var50, var30 * 0.25 + var49 + var51)
                           .a(1.0F, 1.0F, 1.0F, var45)
                           .a(var47, var48)
                           .d();
                        var9.b(var22 + var24 + 0.5, (double)var30, var21 + var26 + 0.5)
                           .a(1.0 + var50, var30 * 0.25 + var49 + var51)
                           .a(1.0F, 1.0F, 1.0F, var45)
                           .a(var47, var48)
                           .d();
                        var9.b(var22 + var24 + 0.5, (double)var31, var21 + var26 + 0.5)
                           .a(1.0 + var50, var31 * 0.25 + var49 + var51)
                           .a(1.0F, 1.0F, 1.0F, var45)
                           .a(var47, var48)
                           .d();
                        var9.b(var22 - var24 + 0.5, (double)var31, var21 - var26 + 0.5)
                           .a(0.0 + var50, var31 * 0.25 + var49 + var51)
                           .a(1.0F, 1.0F, 1.0F, var45)
                           .a(var47, var48)
                           .d();
                     }
                  }
               }
            }
         }

         if (var18 >= 0) {
            var8.b();
         }

         var9.c(0.0, 0.0, 0.0);
         GlStateManager.o();
         GlStateManager.k();
         GlStateManager.a(516, 0.1F);
         this.h();
      }
   }

   public void j() {
      ScaledResolution var1 = new ScaledResolution(this.h);
      GlStateManager.m(256);
      GlStateManager.n(5889);
      GlStateManager.D();
      GlStateManager.a(0.0, var1.c(), var1.d(), 0.0, 1000.0, 3000.0);
      GlStateManager.n(5888);
      GlStateManager.D();
      GlStateManager.b(0.0F, 0.0F, -2000.0F);
   }

   private void i(float var1) {
      WorldClient var2 = this.h.f;
      Entity var3 = this.h.ac();
      float var4 = 0.25F + 0.75F * this.h.t.c / 32.0F;
      var4 = 1.0F - (float)Math.pow(var4, 0.25);
      Vec3 var5 = var2.a(this.h.ac(), var1);
      float var6 = (float)var5.a;
      float var7 = (float)var5.b;
      float var8 = (float)var5.c;
      Vec3 var9 = var2.f(var1);
      this.Q = (float)var9.a;
      this.R = (float)var9.b;
      this.S = (float)var9.c;
      if (this.h.t.c >= 4) {
         double var10 = -1.0;
         Vec3 var12 = MathHelper.a(var2.d(var1)) > 0.0F ? new Vec3(var10, 0.0, 0.0) : new Vec3(1.0, 0.0, 0.0);
         float var13 = (float)var3.d(var1).b(var12);
         if (var13 < 0.0F) {
            var13 = 0.0F;
         }

         if (var13 > 0.0F) {
            float[] var14 = var2.t.a(var2.c(var1), var1);
            if (var14 != null) {
               var13 *= var14[3];
               this.Q = this.Q * (1.0F - var13) + var14[0] * var13;
               this.R = this.R * (1.0F - var13) + var14[1] * var13;
               this.S = this.S * (1.0F - var13) + var14[2] * var13;
            }
         }
      }

      this.Q = this.Q + (var6 - this.Q) * var4;
      this.R = this.R + (var7 - this.R) * var4;
      this.S = this.S + (var8 - this.S) * var4;
      float var20 = var2.j(var1);
      if (var20 > 0.0F) {
         float var11 = 1.0F - var20 * 0.5F;
         float var22 = 1.0F - var20 * 0.4F;
         this.Q *= var11;
         this.R *= var11;
         this.S *= var22;
      }

      float var21 = var2.h(var1);
      if (var21 > 0.0F) {
         float var23 = 1.0F - var21 * 0.5F;
         this.Q *= var23;
         this.R *= var23;
         this.S *= var23;
      }

      Block var24 = ActiveRenderInfo.a(this.h.f, var3, var1);
      if (this.B) {
         Vec3 var26 = var2.e(var1);
         this.Q = (float)var26.a;
         this.R = (float)var26.b;
         this.S = (float)var26.c;
      } else if (var24.t() == Material.h) {
         float var27 = EnchantmentHelper.a(var3) * 0.2F;
         if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).a(Potion.o)) {
            var27 = var27 * 0.3F + 0.6F;
         }

         this.Q = 0.02F + var27;
         this.R = 0.02F + var27;
         this.S = 0.2F + var27;
      } else if (var24.t() == Material.i) {
         this.Q = 0.6F;
         this.R = 0.1F;
         this.S = 0.0F;
      }

      float var28 = this.T + (this.U - this.T) * var1;
      this.Q *= var28;
      this.R *= var28;
      this.S *= var28;
      double var29 = (var3.Q + (var3.t - var3.Q) * var1) * var2.t.j();
      if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).a(Potion.q)) {
         int var16 = ((EntityLivingBase)var3).b(Potion.q).b();
         if (var16 < 20) {
            var29 *= 1.0F - var16 / 20.0F;
         } else {
            var29 = 0.0;
         }
      }

      if (var29 < 1.0) {
         if (var29 < 0.0) {
            var29 = 0.0;
         }

         var29 *= var29;
         this.Q = (float)(this.Q * var29);
         this.R = (float)(this.R * var29);
         this.S = (float)(this.S * var29);
      }

      if (this.z > 0.0F) {
         float var31 = this.A + (this.z - this.A) * var1;
         this.Q = this.Q * (1.0F - var31) + this.Q * 0.7F * var31;
         this.R = this.R * (1.0F - var31) + this.R * 0.6F * var31;
         this.S = this.S * (1.0F - var31) + this.S * 0.6F * var31;
      }

      if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).a(Potion.r)) {
         float var32 = this.a((EntityLivingBase)var3, var1);
         float var17 = 1.0F / this.Q;
         if (var17 > 1.0F / this.R) {
            var17 = 1.0F / this.R;
         }

         if (var17 > 1.0F / this.S) {
            var17 = 1.0F / this.S;
         }

         this.Q = this.Q * (1.0F - var32) + this.Q * var17 * var32;
         this.R = this.R * (1.0F - var32) + this.R * var17 * var32;
         this.S = this.S * (1.0F - var32) + this.S * var17 * var32;
      }

      if (this.h.t.e) {
         float var33 = (this.Q * 30.0F + this.R * 59.0F + this.S * 11.0F) / 100.0F;
         float var34 = (this.Q * 30.0F + this.R * 70.0F) / 100.0F;
         float var18 = (this.Q * 30.0F + this.S * 70.0F) / 100.0F;
         this.Q = var33;
         this.R = var34;
         this.S = var18;
      }

      GlStateManager.a(this.Q, this.R, this.S, 0.0F);
   }

   private void a(int var1, float var2) {
      Entity var3 = this.h.ac();
      boolean var4 = false;
      if (var3 instanceof EntityPlayer) {
         var4 = ((EntityPlayer)var3).bA.d;
      }

      GL11.glFog(2918, this.a(this.Q, this.R, this.S, 1.0F));
      GL11.glNormal3f(0.0F, -1.0F, 0.0F);
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      Block var5 = ActiveRenderInfo.a(this.h.f, var3, var2);
      if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).a(Potion.q)) {
         float var9 = 5.0F;
         int var7 = ((EntityLivingBase)var3).b(Potion.q).b();
         if (var7 < 20) {
            var9 = 5.0F + (this.k - 5.0F) * (1.0F - var7 / 20.0F);
         }

         GlStateManager.d(9729);
         if (var1 == -1) {
            GlStateManager.b(0.0F);
            GlStateManager.c(var9 * 0.8F);
         } else {
            GlStateManager.b(var9 * 0.25F);
            GlStateManager.c(var9);
         }

         if (GLContext.getCapabilities().GL_NV_fog_distance) {
            GL11.glFogi(34138, 34139);
         }
      } else if (this.B) {
         GlStateManager.d(2048);
         GlStateManager.a(0.1F);
      } else if (var5.t() == Material.h) {
         GlStateManager.d(2048);
         if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).a(Potion.o)) {
            GlStateManager.a(0.01F);
         } else {
            GlStateManager.a(0.1F - EnchantmentHelper.a(var3) * 0.03F);
         }
      } else if (var5.t() == Material.i) {
         GlStateManager.d(2048);
         GlStateManager.a(2.0F);
      } else {
         float var6 = this.k;
         GlStateManager.d(9729);
         if (var1 == -1) {
            GlStateManager.b(0.0F);
            GlStateManager.c(var6);
         } else {
            GlStateManager.b(var6 * 0.75F);
            GlStateManager.c(var6);
         }

         if (GLContext.getCapabilities().GL_NV_fog_distance) {
            GL11.glFogi(34138, 34139);
         }

         if (this.h.f.t.b((int)var3.s, (int)var3.u)) {
            GlStateManager.b(var6 * 0.05F);
            GlStateManager.c(Math.min(var6, 192.0F) * 0.5F);
         }
      }

      GlStateManager.g();
      GlStateManager.m();
      GlStateManager.a(1028, 4608);
   }

   private FloatBuffer a(float var1, float var2, float var3, float var4) {
      ((Buffer)this.P).clear();
      this.P.put(var1).put(var2).put(var3).put(var4);
      ((Buffer)this.P).flip();
      return this.P;
   }

   public MapItemRenderer k() {
      return this.l;
   }
}
