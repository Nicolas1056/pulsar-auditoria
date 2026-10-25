package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
import net.minecraft.client.renderer.chunk.ListChunkFactory;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VboChunkFactory;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemRecord;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Matrix4f;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vector3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class RenderGlobal implements IWorldAccess, IResourceManagerReloadListener {
   private static final Logger b = LogManager.getLogger();
   private static final ResourceLocation c = new ResourceLocation("textures/environment/moon_phases.png");
   private static final ResourceLocation d = new ResourceLocation("textures/environment/sun.png");
   private static final ResourceLocation e = new ResourceLocation("textures/environment/clouds.png");
   private static final ResourceLocation f = new ResourceLocation("textures/environment/end_sky.png");
   private static final ResourceLocation g = new ResourceLocation("textures/misc/forcefield.png");
   private final Minecraft h;
   private final TextureManager i;
   private final RenderManager j;
   private WorldClient k;
   private Set<RenderChunk> l = Sets.newLinkedHashSet();
   private List<RenderGlobal.ContainerLocalRenderInformation> m = Lists.newArrayListWithCapacity(69696);
   private final Set<TileEntity> n = Sets.newHashSet();
   private ViewFrustum o;
   private int p = -1;
   private int q = -1;
   private int r = -1;
   private VertexFormat s;
   private VertexBuffer t;
   private VertexBuffer u;
   private VertexBuffer v;
   private int w;
   private final Map<Integer, DestroyBlockProgress> x = Maps.newHashMap();
   private final Map<BlockPos, ISound> y = Maps.newHashMap();
   private final TextureAtlasSprite[] z = new TextureAtlasSprite[10];
   private Framebuffer A;
   private ShaderGroup B;
   private double C = Double.MIN_VALUE;
   private double D = Double.MIN_VALUE;
   private double E = Double.MIN_VALUE;
   private int F = Integer.MIN_VALUE;
   private int G = Integer.MIN_VALUE;
   private int H = Integer.MIN_VALUE;
   private double I = Double.MIN_VALUE;
   private double J = Double.MIN_VALUE;
   private double K = Double.MIN_VALUE;
   private double L = Double.MIN_VALUE;
   private double M = Double.MIN_VALUE;
   private final ChunkRenderDispatcher N = new ChunkRenderDispatcher();
   private ChunkRenderContainer O;
   private int P = -1;
   private int Q = 2;
   private int R;
   private int S;
   private int T;
   private boolean U = false;
   private ClippingHelper V;
   private final Vector4f[] W = new Vector4f[8];
   private final Vector3d X = new Vector3d();
   private boolean Y = false;
   IRenderChunkFactory a;
   private double Z;
   private double aa;
   private double ab;
   private boolean ac = true;

   public RenderGlobal(Minecraft var1) {
      this.h = var1;
      this.j = var1.af();
      this.i = var1.P();
      this.i.a(g);
      GL11.glTexParameteri(3553, 10242, 10497);
      GL11.glTexParameteri(3553, 10243, 10497);
      GlStateManager.i(0);
      this.n();
      this.Y = OpenGlHelper.f();
      if (this.Y) {
         this.O = new VboRenderList();
         this.a = new VboChunkFactory();
      } else {
         this.O = new RenderList();
         this.a = new ListChunkFactory();
      }

      this.s = new VertexFormat();
      this.s.a(new VertexFormatElement(0, VertexFormatElement.EnumType.a, VertexFormatElement.EnumUsage.a, 3));
      this.q();
      this.p();
      this.o();
   }

   @Override
   public void a(IResourceManager var1) {
      this.n();
   }

   private void n() {
      TextureMap var1 = this.h.T();

      for (int var2 = 0; var2 < this.z.length; var2++) {
         this.z[var2] = var1.a("minecraft:blocks/destroy_stage_" + var2);
      }
   }

   public void b() {
      if (OpenGlHelper.O) {
         if (ShaderLinkHelper.b() == null) {
            ShaderLinkHelper.a();
         }

         ResourceLocation var1 = new ResourceLocation("shaders/post/entity_outline.json");

         try {
            this.B = new ShaderGroup(this.h.P(), this.h.Q(), this.h.b(), var1);
            this.B.a(this.h.d, this.h.e);
            this.A = this.B.a("final");
         } catch (IOException var3) {
            b.warn("Failed to load shader: " + var1, var3);
            this.B = null;
            this.A = null;
         } catch (JsonSyntaxException var4) {
            b.warn("Failed to load shader: " + var1, var4);
            this.B = null;
            this.A = null;
         }
      } else {
         this.B = null;
         this.A = null;
      }
   }

   public void c() {
      if (this.d()) {
         GlStateManager.l();
         GlStateManager.a(770, 771, 0, 1);
         this.A.a(this.h.d, this.h.e, false);
         GlStateManager.k();
      }
   }

   protected boolean d() {
      return this.A != null && this.B != null && this.h.h != null && this.h.h.v() && this.h.t.ar.d();
   }

   private void o() {
      Tessellator var1 = Tessellator.a();
      WorldRenderer var2 = var1.c();
      if (this.v != null) {
         this.v.c();
      }

      if (this.r >= 0) {
         GLAllocation.b(this.r);
         this.r = -1;
      }

      if (this.Y) {
         this.v = new VertexBuffer(this.s);
         this.a(var2, -16.0F, true);
         var2.e();
         var2.b();
         this.v.a(var2.f());
      } else {
         this.r = GLAllocation.a(1);
         GL11.glNewList(this.r, 4864);
         this.a(var2, -16.0F, true);
         var1.b();
         GL11.glEndList();
      }
   }

   private void p() {
      Tessellator var1 = Tessellator.a();
      WorldRenderer var2 = var1.c();
      if (this.u != null) {
         this.u.c();
      }

      if (this.q >= 0) {
         GLAllocation.b(this.q);
         this.q = -1;
      }

      if (this.Y) {
         this.u = new VertexBuffer(this.s);
         this.a(var2, 16.0F, false);
         var2.e();
         var2.b();
         this.u.a(var2.f());
      } else {
         this.q = GLAllocation.a(1);
         GL11.glNewList(this.q, 4864);
         this.a(var2, 16.0F, false);
         var1.b();
         GL11.glEndList();
      }
   }

   private void a(WorldRenderer var1, float var2, boolean var3) {
      byte var4 = 64;
      byte var5 = 6;
      var1.a(7, DefaultVertexFormats.e);

      for (short var6 = -384; var6 <= 384; var6 += 64) {
         for (short var7 = -384; var7 <= 384; var7 += 64) {
            float var8 = var6;
            float var9 = var6 + 64;
            if (var3) {
               var9 = var6;
               var8 = var6 + 64;
            }

            var1.b((double)var8, (double)var2, (double)var7).d();
            var1.b((double)var9, (double)var2, (double)var7).d();
            var1.b((double)var9, (double)var2, (double)(var7 + 64)).d();
            var1.b((double)var8, (double)var2, (double)(var7 + 64)).d();
         }
      }
   }

   private void q() {
      Tessellator var1 = Tessellator.a();
      WorldRenderer var2 = var1.c();
      if (this.t != null) {
         this.t.c();
      }

      if (this.p >= 0) {
         GLAllocation.b(this.p);
         this.p = -1;
      }

      if (this.Y) {
         this.t = new VertexBuffer(this.s);
         this.a(var2);
         var2.e();
         var2.b();
         this.t.a(var2.f());
      } else {
         this.p = GLAllocation.a(1);
         GlStateManager.E();
         GL11.glNewList(this.p, 4864);
         this.a(var2);
         var1.b();
         GL11.glEndList();
         GlStateManager.F();
      }
   }

   private void a(WorldRenderer var1) {
      Random var2 = new Random(10842L);
      var1.a(7, DefaultVertexFormats.e);

      for (int var3 = 0; var3 < 1500; var3++) {
         double var4 = var2.nextFloat() * 2.0F - 1.0F;
         double var6 = var2.nextFloat() * 2.0F - 1.0F;
         double var8 = var2.nextFloat() * 2.0F - 1.0F;
         double var10 = 0.15F + var2.nextFloat() * 0.1F;
         double var12 = var4 * var4 + var6 * var6 + var8 * var8;
         if (var12 < 1.0 && var12 > 0.01) {
            var12 = 1.0 / Math.sqrt(var12);
            var4 *= var12;
            var6 *= var12;
            var8 *= var12;
            double var14 = var4 * 100.0;
            double var16 = var6 * 100.0;
            double var18 = var8 * 100.0;
            double var20 = Math.atan2(var4, var8);
            double var22 = Math.sin(var20);
            double var24 = Math.cos(var20);
            double var26 = Math.atan2(Math.sqrt(var4 * var4 + var8 * var8), var6);
            double var28 = Math.sin(var26);
            double var30 = Math.cos(var26);
            double var32 = var2.nextDouble() * Math.PI * 2.0;
            double var34 = Math.sin(var32);
            double var36 = Math.cos(var32);

            for (int var38 = 0; var38 < 4; var38++) {
               double var39 = 0.0;
               double var41 = ((var38 & 2) - 1) * var10;
               double var43 = ((var38 + 1 & 2) - 1) * var10;
               double var45 = 0.0;
               double var47 = var41 * var36 - var43 * var34;
               double var49 = var43 * var36 + var41 * var34;
               double var53 = var47 * var28 + 0.0 * var30;
               double var55 = 0.0 * var28 - var47 * var30;
               double var57 = var55 * var22 - var49 * var24;
               double var61 = var49 * var22 + var55 * var24;
               var1.b(var14 + var57, var16 + var53, var18 + var61).d();
            }
         }
      }
   }

   public void a(WorldClient var1) {
      if (this.k != null) {
         this.k.b(this);
      }

      this.C = Double.MIN_VALUE;
      this.D = Double.MIN_VALUE;
      this.E = Double.MIN_VALUE;
      this.F = Integer.MIN_VALUE;
      this.G = Integer.MIN_VALUE;
      this.H = Integer.MIN_VALUE;
      this.j.a(var1);
      this.k = var1;
      if (var1 != null) {
         var1.a(this);
         this.a();
      }
   }

   public void a() {
      if (this.k != null) {
         this.ac = true;
         Blocks.t.b(this.h.t.i);
         Blocks.u.b(this.h.t.i);
         this.P = this.h.t.c;
         boolean var1 = this.Y;
         this.Y = OpenGlHelper.f();
         if (var1 && !this.Y) {
            this.O = new RenderList();
            this.a = new ListChunkFactory();
         } else if (!var1 && this.Y) {
            this.O = new VboRenderList();
            this.a = new VboChunkFactory();
         }

         if (var1 != this.Y) {
            this.q();
            this.p();
            this.o();
         }

         if (this.o != null) {
            this.o.a();
         }

         this.e();
         synchronized (this.n) {
            this.n.clear();
         }

         this.o = new ViewFrustum(this.k, this.h.t.c, this, this.a);
         if (this.k != null) {
            Entity var5 = this.h.ac();
            if (var5 != null) {
               this.o.a(var5.s, var5.u);
            }
         }

         this.Q = 2;
      }
   }

   protected void e() {
      this.l.clear();
      this.N.b();
   }

   public void a(int var1, int var2) {
      if (OpenGlHelper.O) {
         if (this.B != null) {
            this.B.a(var1, var2);
         }
      }
   }

   public void a(Entity var1, ICamera var2, float var3) {
      if (this.Q > 0) {
         this.Q--;
      } else {
         double var4 = var1.p + (var1.s - var1.p) * var3;
         double var6 = var1.q + (var1.t - var1.q) * var3;
         double var8 = var1.r + (var1.u - var1.r) * var3;
         this.k.B.a("prepare");
         TileEntityRendererDispatcher.a.a(this.k, this.h.P(), this.h.k, this.h.ac(), var3);
         this.j.a(this.k, this.h.k, this.h.ac(), this.h.i, this.h.t, var3);
         this.R = 0;
         this.S = 0;
         this.T = 0;
         Entity var10 = this.h.ac();
         double var11 = var10.P + (var10.s - var10.P) * var3;
         double var13 = var10.Q + (var10.t - var10.Q) * var3;
         double var15 = var10.R + (var10.u - var10.R) * var3;
         TileEntityRendererDispatcher.b = var11;
         TileEntityRendererDispatcher.c = var13;
         TileEntityRendererDispatcher.d = var15;
         this.j.a(var11, var13, var15);
         this.h.o.i();
         this.k.B.c("global");
         List var17 = this.k.E();
         this.R = var17.size();

         for (int var18 = 0; var18 < this.k.k.size(); var18++) {
            Entity var19 = this.k.k.get(var18);
            this.S++;
            if (var19.h(var4, var6, var8)) {
               this.j.a(var19, var3);
            }
         }

         if (this.d()) {
            GlStateManager.c(519);
            GlStateManager.n();
            this.A.f();
            this.A.a(false);
            this.k.B.c("entityOutlines");
            RenderHelper.a();
            this.j.c(true);

            for (int var28 = 0; var28 < var17.size(); var28++) {
               Entity var33 = (Entity)var17.get(var28);
               boolean var20 = this.h.ac() instanceof EntityLivingBase && ((EntityLivingBase)this.h.ac()).bJ();
               boolean var21 = var33.h(var4, var6, var8) && (var33.ah || var2.a(var33.aR()) || var33.l == this.h.h) && var33 instanceof EntityPlayer;
               if ((var33 != this.h.ac() || this.h.t.aB != 0 || var20) && var21) {
                  this.j.a(var33, var3);
               }
            }

            this.j.c(false);
            RenderHelper.b();
            GlStateManager.a(false);
            this.B.a(var3);
            GlStateManager.e();
            GlStateManager.a(true);
            this.h.b().a(false);
            GlStateManager.m();
            GlStateManager.l();
            GlStateManager.g();
            GlStateManager.c(515);
            GlStateManager.j();
            GlStateManager.d();
         }

         this.k.B.c("entities");

         for (RenderGlobal.ContainerLocalRenderInformation var34 : this.m) {
            Chunk var38 = this.k.f(var34.a.j());
            ClassInheritanceMultiMap var42 = var38.s()[var34.a.j().o() / 16];
            if (!var42.isEmpty()) {
               for (Entity var23 : var42) {
                  boolean var24 = this.j.a(var23, var2, var4, var6, var8) || var23.l == this.h.h;
                  if (var24) {
                     boolean var25 = this.h.ac() instanceof EntityLivingBase ? ((EntityLivingBase)this.h.ac()).bJ() : false;
                     if (var23 == this.h.ac() && this.h.t.aB == 0 && !var25 || var23.t >= 0.0 && var23.t < 256.0 && !this.k.e(new BlockPos(var23))) {
                        continue;
                     }

                     this.S++;
                     this.j.a(var23, var3);
                  }

                  if (!var24 && var23 instanceof EntityWitherSkull) {
                     this.h.af().b(var23, var3);
                  }
               }
            }
         }

         this.k.B.c("blockentities");
         RenderHelper.b();

         for (RenderGlobal.ContainerLocalRenderInformation var35 : this.m) {
            List var39 = var35.a.g().b();
            if (!var39.isEmpty()) {
               for (TileEntity var45 : var39) {
                  TileEntityRendererDispatcher.a.a(var45, var3, -1);
               }
            }
         }

         synchronized (this.n) {
            for (TileEntity var40 : this.n) {
               TileEntityRendererDispatcher.a.a(var40, var3, -1);
            }
         }

         this.s();

         for (DestroyBlockProgress var37 : this.x.values()) {
            BlockPos var41 = var37.b();
            TileEntity var44 = this.k.s(var41);
            if (var44 instanceof TileEntityChest) {
               TileEntityChest var46 = (TileEntityChest)var44;
               if (var46.h != null) {
                  var41 = var41.a(EnumFacing.e);
                  var44 = this.k.s(var41);
               } else if (var46.f != null) {
                  var41 = var41.a(EnumFacing.c);
                  var44 = this.k.s(var41);
               }
            }

            Block var47 = this.k.p(var41).c();
            if (var44 != null && (var47 instanceof BlockChest || var47 instanceof BlockEnderChest || var47 instanceof BlockSign || var47 instanceof BlockSkull)
               )
             {
               TileEntityRendererDispatcher.a.a(var44, var3, var37.c());
            }
         }

         this.t();
         this.h.o.h();
         this.h.A.b();
      }
   }

   public String f() {
      int var1 = this.o.f.length;
      int var2 = 0;

      for (RenderGlobal.ContainerLocalRenderInformation var4 : this.m) {
         CompiledChunk var5 = var4.a.b;
         if (var5 != CompiledChunk.a && !var5.a()) {
            var2++;
         }
      }

      return String.format("C: %d/%d %sD: %d, %s", var2, var1, this.h.G ? "(s) " : "", this.P, this.N.a());
   }

   public String g() {
      return "E: " + this.S + "/" + this.R + ", B: " + this.T + ", I: " + (this.R - this.T - this.S);
   }

   public void a(Entity var1, double var2, ICamera var4, int var5, boolean var6) {
      if (this.h.t.c != this.P) {
         this.a();
      }

      this.k.B.a("camera");
      double var7 = var1.s - this.C;
      double var9 = var1.t - this.D;
      double var11 = var1.u - this.E;
      if (this.F != var1.ae || this.G != var1.af || this.H != var1.ag || var7 * var7 + var9 * var9 + var11 * var11 > 16.0) {
         this.C = var1.s;
         this.D = var1.t;
         this.E = var1.u;
         this.F = var1.ae;
         this.G = var1.af;
         this.H = var1.ag;
         this.o.a(var1.s, var1.u);
      }

      this.k.B.c("renderlistcamera");
      double var13 = var1.P + (var1.s - var1.P) * var2;
      double var15 = var1.Q + (var1.t - var1.Q) * var2;
      double var17 = var1.R + (var1.u - var1.R) * var2;
      this.O.a(var13, var15, var17);
      this.k.B.c("cull");
      if (this.V != null) {
         Frustum var19 = new Frustum(this.V);
         var19.a(this.X.a, this.X.b, this.X.c);
         var4 = var19;
      }

      this.h.A.c("culling");
      BlockPos var35 = new BlockPos(var13, var15 + var1.aS(), var17);
      RenderChunk var20 = this.o.a(var35);
      BlockPos var21 = new BlockPos(MathHelper.c(var13 / 16.0) * 16, MathHelper.c(var15 / 16.0) * 16, MathHelper.c(var17 / 16.0) * 16);
      this.ac = this.ac || !this.l.isEmpty() || var1.s != this.I || var1.t != this.J || var1.u != this.K || var1.z != this.L || var1.y != this.M;
      this.I = var1.s;
      this.J = var1.t;
      this.K = var1.u;
      this.L = var1.z;
      this.M = var1.y;
      boolean var22 = this.V != null;
      if (!var22 && this.ac) {
         this.ac = false;
         this.m = Lists.newArrayList();
         LinkedList var23 = Lists.newLinkedList();
         boolean var24 = this.h.G;
         if (var20 != null) {
            boolean var38 = false;
            RenderGlobal.ContainerLocalRenderInformation var41 = new RenderGlobal.ContainerLocalRenderInformation(var20, null, 0);
            Set var44 = this.c(var35);
            if (var44.size() == 1) {
               Vector3f var46 = this.a(var1, var2);
               EnumFacing var29 = EnumFacing.a(var46.x, var46.y, var46.z).d();
               var44.remove(var29);
            }

            if (var44.isEmpty()) {
               var38 = true;
            }

            if (var38 && !var6) {
               this.m.add(var41);
            } else {
               if (var6 && this.k.p(var35).c().c()) {
                  var24 = false;
               }

               var20.a(var5);
               var23.add(var41);
            }
         } else {
            int var25 = var35.o() > 0 ? 248 : 8;

            for (int var26 = -this.P; var26 <= this.P; var26++) {
               for (int var27 = -this.P; var27 <= this.P; var27++) {
                  RenderChunk var28 = this.o.a(new BlockPos((var26 << 4) + 8, var25, (var27 << 4) + 8));
                  if (var28 != null && ((ICamera)var4).a(var28.c)) {
                     var28.a(var5);
                     var23.add(new RenderGlobal.ContainerLocalRenderInformation(var28, null, 0));
                  }
               }
            }
         }

         while (!var23.isEmpty()) {
            RenderGlobal.ContainerLocalRenderInformation var39 = (RenderGlobal.ContainerLocalRenderInformation)var23.poll();
            RenderChunk var42 = var39.a;
            EnumFacing var45 = var39.b;
            BlockPos var47 = var42.j();
            this.m.add(var39);

            for (EnumFacing var32 : EnumFacing.values()) {
               RenderChunk var33 = this.a(var21, var42, var32);
               if ((!var24 || !var39.c.contains(var32.d()))
                  && (!var24 || var45 == null || var42.g().a(var45.d(), var32))
                  && var33 != null
                  && var33.a(var5)
                  && ((ICamera)var4).a(var33.c)) {
                  RenderGlobal.ContainerLocalRenderInformation var34 = new RenderGlobal.ContainerLocalRenderInformation(var33, var32, var39.d + 1);
                  var34.c.addAll(var39.c);
                  var34.c.add(var32);
                  var23.add(var34);
               }
            }
         }
      }

      if (this.U) {
         this.a(var13, var15, var17);
         this.U = false;
      }

      this.N.e();
      Set var36 = this.l;
      this.l = Sets.newLinkedHashSet();

      for (RenderGlobal.ContainerLocalRenderInformation var40 : this.m) {
         RenderChunk var43 = var40.a;
         if (var43.l() || var36.contains(var43)) {
            this.ac = true;
            if (this.a(var21, var40.a)) {
               this.h.A.a("build near");
               this.N.b(var43);
               var43.a(false);
               this.h.A.b();
            } else {
               this.l.add(var43);
            }
         }
      }

      this.l.addAll(var36);
      this.h.A.b();
   }

   private boolean a(BlockPos var1, RenderChunk var2) {
      BlockPos var3 = var2.j();
      if (MathHelper.a(var1.n() - var3.n()) > 16) {
         return false;
      } else {
         return MathHelper.a(var1.o() - var3.o()) > 16 ? false : MathHelper.a(var1.p() - var3.p()) <= 16;
      }
   }

   private Set<EnumFacing> c(BlockPos var1) {
      VisGraph var2 = new VisGraph();
      BlockPos var3 = new BlockPos(var1.n() >> 4 << 4, var1.o() >> 4 << 4, var1.p() >> 4 << 4);
      Chunk var4 = this.k.f(var3);

      for (BlockPos.MutableBlockPos var6 : BlockPos.b(var3, var3.a(15, 15, 15))) {
         if (var4.a(var6).c()) {
            var2.a(var6);
         }
      }

      return var2.b(var1);
   }

   private RenderChunk a(BlockPos var1, RenderChunk var2, EnumFacing var3) {
      BlockPos var4 = var2.a(var3);
      if (MathHelper.a(var1.n() - var4.n()) > this.P * 16) {
         return null;
      } else if (var4.o() < 0 || var4.o() >= 256) {
         return null;
      } else {
         return MathHelper.a(var1.p() - var4.p()) > this.P * 16 ? null : this.o.a(var4);
      }
   }

   private void a(double var1, double var3, double var5) {
      this.V = new ClippingHelperImpl();
      ((ClippingHelperImpl)this.V).b();
      Matrix4f var7 = new Matrix4f(this.V.c);
      var7.transpose();
      Matrix4f var8 = new Matrix4f(this.V.b);
      var8.transpose();
      Matrix4f var9 = new Matrix4f();
      Matrix4f.mul(var8, var7, var9);
      var9.invert();
      this.X.a = var1;
      this.X.b = var3;
      this.X.c = var5;
      this.W[0] = new Vector4f(-1.0F, -1.0F, -1.0F, 1.0F);
      this.W[1] = new Vector4f(1.0F, -1.0F, -1.0F, 1.0F);
      this.W[2] = new Vector4f(1.0F, 1.0F, -1.0F, 1.0F);
      this.W[3] = new Vector4f(-1.0F, 1.0F, -1.0F, 1.0F);
      this.W[4] = new Vector4f(-1.0F, -1.0F, 1.0F, 1.0F);
      this.W[5] = new Vector4f(1.0F, -1.0F, 1.0F, 1.0F);
      this.W[6] = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.W[7] = new Vector4f(-1.0F, 1.0F, 1.0F, 1.0F);

      for (int var10 = 0; var10 < 8; var10++) {
         Matrix4f.transform(var9, this.W[var10], this.W[var10]);
         this.W[var10].x = this.W[var10].x / this.W[var10].w;
         this.W[var10].y = this.W[var10].y / this.W[var10].w;
         this.W[var10].z = this.W[var10].z / this.W[var10].w;
         this.W[var10].w = 1.0F;
      }
   }

   protected Vector3f a(Entity var1, double var2) {
      float var4 = (float)(var1.B + (var1.z - var1.B) * var2);
      float var5 = (float)(var1.A + (var1.y - var1.A) * var2);
      if (Minecraft.A().t.aB == 2) {
         var4 += 180.0F;
      }

      float var6 = MathHelper.b(-var5 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var7 = MathHelper.a(-var5 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var8 = -MathHelper.b(-var4 * (float) (Math.PI / 180.0));
      float var9 = MathHelper.a(-var4 * (float) (Math.PI / 180.0));
      return new Vector3f(var7 * var8, var9, var6 * var8);
   }

   public int a(EnumWorldBlockLayer var1, double var2, int var4, Entity var5) {
      RenderHelper.a();
      if (var1 == EnumWorldBlockLayer.d) {
         this.h.A.a("translucent_sort");
         double var6 = var5.s - this.Z;
         double var8 = var5.t - this.aa;
         double var10 = var5.u - this.ab;
         if (var6 * var6 + var8 * var8 + var10 * var10 > 1.0) {
            this.Z = var5.s;
            this.aa = var5.t;
            this.ab = var5.u;
            int var12 = 0;

            for (RenderGlobal.ContainerLocalRenderInformation var14 : this.m) {
               if (var14.a.b.d(var1) && var12++ < 15) {
                  this.N.c(var14.a);
               }
            }
         }

         this.h.A.b();
      }

      this.h.A.a("filterempty");
      int var15 = 0;
      boolean var7 = var1 == EnumWorldBlockLayer.d;
      int var16 = var7 ? this.m.size() - 1 : 0;
      int var9 = var7 ? -1 : this.m.size();
      int var17 = var7 ? -1 : 1;

      for (int var11 = var16; var11 != var9; var11 += var17) {
         RenderChunk var18 = this.m.get(var11).a;
         if (!var18.g().b(var1)) {
            var15++;
            this.O.a(var18, var1);
         }
      }

      this.h.A.c("render_" + var1);
      this.a(var1);
      this.h.A.b();
      return var15;
   }

   private void a(EnumWorldBlockLayer var1) {
      this.h.o.i();
      if (OpenGlHelper.f()) {
         GL11.glEnableClientState(32884);
         OpenGlHelper.l(OpenGlHelper.q);
         GL11.glEnableClientState(32888);
         OpenGlHelper.l(OpenGlHelper.r);
         GL11.glEnableClientState(32888);
         OpenGlHelper.l(OpenGlHelper.q);
         GL11.glEnableClientState(32886);
      }

      this.O.a(var1);
      if (OpenGlHelper.f()) {
         for (VertexFormatElement var4 : DefaultVertexFormats.a.h()) {
            VertexFormatElement.EnumUsage var5 = var4.b();
            int var6 = var4.d();
            switch (var5) {
               case a:
                  GL11.glDisableClientState(32884);
                  break;
               case d:
                  OpenGlHelper.l(OpenGlHelper.q + var6);
                  GL11.glDisableClientState(32888);
                  OpenGlHelper.l(OpenGlHelper.q);
                  break;
               case c:
                  GL11.glDisableClientState(32886);
                  GlStateManager.G();
            }
         }
      }

      this.h.o.h();
   }

   private void a(Iterator<DestroyBlockProgress> var1) {
      while (var1.hasNext()) {
         DestroyBlockProgress var2 = (DestroyBlockProgress)var1.next();
         int var3 = var2.d();
         if (this.w - var3 > 400) {
            var1.remove();
         }
      }
   }

   public void j() {
      this.w++;
      if (this.w % 20 == 0) {
         this.a(this.x.values().iterator());
      }
   }

   private void r() {
      GlStateManager.n();
      GlStateManager.c();
      GlStateManager.l();
      GlStateManager.a(770, 771, 1, 0);
      RenderHelper.a();
      GlStateManager.a(false);
      this.i.a(f);
      Tessellator var1 = Tessellator.a();
      WorldRenderer var2 = var1.c();

      for (int var3 = 0; var3 < 6; var3++) {
         GlStateManager.E();
         if (var3 == 1) {
            GlStateManager.b(90.0F, 1.0F, 0.0F, 0.0F);
         }

         if (var3 == 2) {
            GlStateManager.b(-90.0F, 1.0F, 0.0F, 0.0F);
         }

         if (var3 == 3) {
            GlStateManager.b(180.0F, 1.0F, 0.0F, 0.0F);
         }

         if (var3 == 4) {
            GlStateManager.b(90.0F, 0.0F, 0.0F, 1.0F);
         }

         if (var3 == 5) {
            GlStateManager.b(-90.0F, 0.0F, 0.0F, 1.0F);
         }

         var2.a(7, DefaultVertexFormats.i);
         var2.b(-100.0, -100.0, -100.0).a(0.0, 0.0).b(40, 40, 40, 255).d();
         var2.b(-100.0, -100.0, 100.0).a(0.0, 16.0).b(40, 40, 40, 255).d();
         var2.b(100.0, -100.0, 100.0).a(16.0, 16.0).b(40, 40, 40, 255).d();
         var2.b(100.0, -100.0, -100.0).a(16.0, 0.0).b(40, 40, 40, 255).d();
         var1.b();
         GlStateManager.F();
      }

      GlStateManager.a(true);
      GlStateManager.w();
      GlStateManager.d();
   }

   public void a(float var1, int var2) {
      if (this.h.f.t.q() == 1) {
         this.r();
      } else if (this.h.f.t.d()) {
         GlStateManager.x();
         Vec3 var3 = this.k.a(this.h.ac(), var1);
         float var4 = (float)var3.a;
         float var5 = (float)var3.b;
         float var6 = (float)var3.c;
         if (var2 != 2) {
            float var7 = (var4 * 30.0F + var5 * 59.0F + var6 * 11.0F) / 100.0F;
            float var8 = (var4 * 30.0F + var5 * 70.0F) / 100.0F;
            float var9 = (var4 * 30.0F + var6 * 70.0F) / 100.0F;
            var4 = var7;
            var5 = var8;
            var6 = var9;
         }

         GlStateManager.c(var4, var5, var6);
         Tessellator var20 = Tessellator.a();
         WorldRenderer var21 = var20.c();
         GlStateManager.a(false);
         GlStateManager.m();
         GlStateManager.c(var4, var5, var6);
         if (this.Y) {
            this.u.a();
            GL11.glEnableClientState(32884);
            GL11.glVertexPointer(3, 5126, 12, 0L);
            this.u.a(7);
            this.u.b();
            GL11.glDisableClientState(32884);
         } else {
            GlStateManager.o(this.q);
         }

         GlStateManager.n();
         GlStateManager.c();
         GlStateManager.l();
         GlStateManager.a(770, 771, 1, 0);
         RenderHelper.a();
         float[] var22 = this.k.t.a(this.k.c(var1), var1);
         if (var22 != null) {
            GlStateManager.x();
            GlStateManager.j(7425);
            GlStateManager.E();
            GlStateManager.b(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.b(MathHelper.a(this.k.d(var1)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.b(90.0F, 0.0F, 0.0F, 1.0F);
            float var10 = var22[0];
            float var11 = var22[1];
            float var12 = var22[2];
            if (var2 != 2) {
               float var13 = (var10 * 30.0F + var11 * 59.0F + var12 * 11.0F) / 100.0F;
               float var14 = (var10 * 30.0F + var11 * 70.0F) / 100.0F;
               float var15 = (var10 * 30.0F + var12 * 70.0F) / 100.0F;
               var10 = var13;
               var11 = var14;
               var12 = var15;
            }

            var21.a(6, DefaultVertexFormats.f);
            var21.b(0.0, 100.0, 0.0).a(var10, var11, var12, var22[3]).d();
            byte var29 = 16;

            for (int var32 = 0; var32 <= 16; var32++) {
               float var35 = var32 * (float) Math.PI * 2.0F / 16.0F;
               float var16 = MathHelper.a(var35);
               float var17 = MathHelper.b(var35);
               var21.b((double)(var16 * 120.0F), (double)(var17 * 120.0F), (double)(-var17 * 40.0F * var22[3])).a(var22[0], var22[1], var22[2], 0.0F).d();
            }

            var20.b();
            GlStateManager.F();
            GlStateManager.j(7424);
         }

         GlStateManager.w();
         GlStateManager.a(770, 1, 1, 0);
         GlStateManager.E();
         float var23 = 1.0F - this.k.j(var1);
         GlStateManager.c(1.0F, 1.0F, 1.0F, var23);
         GlStateManager.b(-90.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.b(this.k.c(var1) * 360.0F, 1.0F, 0.0F, 0.0F);
         float var25 = 30.0F;
         this.i.a(d);
         var21.a(7, DefaultVertexFormats.g);
         var21.b((double)(-var25), 100.0, (double)(-var25)).a(0.0, 0.0).d();
         var21.b((double)var25, 100.0, (double)(-var25)).a(1.0, 0.0).d();
         var21.b((double)var25, 100.0, (double)var25).a(1.0, 1.0).d();
         var21.b((double)(-var25), 100.0, (double)var25).a(0.0, 1.0).d();
         var20.b();
         var25 = 20.0F;
         this.i.a(c);
         int var27 = this.k.x();
         int var30 = var27 % 4;
         int var33 = var27 / 4 % 2;
         float var36 = (var30 + 0) / 4.0F;
         float var37 = (var33 + 0) / 2.0F;
         float var38 = (var30 + 1) / 4.0F;
         float var18 = (var33 + 1) / 2.0F;
         var21.a(7, DefaultVertexFormats.g);
         var21.b((double)(-var25), -100.0, (double)var25).a((double)var38, (double)var18).d();
         var21.b((double)var25, -100.0, (double)var25).a((double)var36, (double)var18).d();
         var21.b((double)var25, -100.0, (double)(-var25)).a((double)var36, (double)var37).d();
         var21.b((double)(-var25), -100.0, (double)(-var25)).a((double)var38, (double)var37).d();
         var20.b();
         GlStateManager.x();
         float var19 = this.k.g(var1) * var23;
         if (var19 > 0.0F) {
            GlStateManager.c(var19, var19, var19, var19);
            if (this.Y) {
               this.t.a();
               GL11.glEnableClientState(32884);
               GL11.glVertexPointer(3, 5126, 12, 0L);
               this.t.a(7);
               this.t.b();
               GL11.glDisableClientState(32884);
            } else {
               GlStateManager.o(this.p);
            }
         }

         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.k();
         GlStateManager.d();
         GlStateManager.m();
         GlStateManager.F();
         GlStateManager.x();
         GlStateManager.c(0.0F, 0.0F, 0.0F);
         double var24 = this.h.h.e(var1).b - this.k.X();
         if (var24 < 0.0) {
            GlStateManager.E();
            GlStateManager.b(0.0F, 12.0F, 0.0F);
            if (this.Y) {
               this.v.a();
               GL11.glEnableClientState(32884);
               GL11.glVertexPointer(3, 5126, 12, 0L);
               this.v.a(7);
               this.v.b();
               GL11.glDisableClientState(32884);
            } else {
               GlStateManager.o(this.r);
            }

            GlStateManager.F();
            float var28 = 1.0F;
            float var31 = -((float)(var24 + 65.0));
            float var34 = -1.0F;
            var21.a(7, DefaultVertexFormats.f);
            var21.b(-1.0, (double)var31, 1.0).b(0, 0, 0, 255).d();
            var21.b(1.0, (double)var31, 1.0).b(0, 0, 0, 255).d();
            var21.b(1.0, -1.0, 1.0).b(0, 0, 0, 255).d();
            var21.b(-1.0, -1.0, 1.0).b(0, 0, 0, 255).d();
            var21.b(-1.0, -1.0, -1.0).b(0, 0, 0, 255).d();
            var21.b(1.0, -1.0, -1.0).b(0, 0, 0, 255).d();
            var21.b(1.0, (double)var31, -1.0).b(0, 0, 0, 255).d();
            var21.b(-1.0, (double)var31, -1.0).b(0, 0, 0, 255).d();
            var21.b(1.0, -1.0, -1.0).b(0, 0, 0, 255).d();
            var21.b(1.0, -1.0, 1.0).b(0, 0, 0, 255).d();
            var21.b(1.0, (double)var31, 1.0).b(0, 0, 0, 255).d();
            var21.b(1.0, (double)var31, -1.0).b(0, 0, 0, 255).d();
            var21.b(-1.0, (double)var31, -1.0).b(0, 0, 0, 255).d();
            var21.b(-1.0, (double)var31, 1.0).b(0, 0, 0, 255).d();
            var21.b(-1.0, -1.0, 1.0).b(0, 0, 0, 255).d();
            var21.b(-1.0, -1.0, -1.0).b(0, 0, 0, 255).d();
            var21.b(-1.0, -1.0, -1.0).b(0, 0, 0, 255).d();
            var21.b(-1.0, -1.0, 1.0).b(0, 0, 0, 255).d();
            var21.b(1.0, -1.0, 1.0).b(0, 0, 0, 255).d();
            var21.b(1.0, -1.0, -1.0).b(0, 0, 0, 255).d();
            var20.b();
         }

         if (this.k.t.g()) {
            GlStateManager.c(var4 * 0.2F + 0.04F, var5 * 0.2F + 0.04F, var6 * 0.6F + 0.1F);
         } else {
            GlStateManager.c(var4, var5, var6);
         }

         GlStateManager.E();
         GlStateManager.b(0.0F, -((float)(var24 - 16.0)), 0.0F);
         GlStateManager.o(this.r);
         GlStateManager.F();
         GlStateManager.w();
         GlStateManager.a(true);
      }
   }

   public void b(float var1, int var2) {
      if (this.h.f.t.d()) {
         if (this.h.t.e() == 2) {
            this.c(var1, var2);
         } else {
            GlStateManager.p();
            float var3 = (float)(this.h.ac().Q + (this.h.ac().t - this.h.ac().Q) * var1);
            byte var4 = 32;
            byte var5 = 8;
            Tessellator var6 = Tessellator.a();
            WorldRenderer var7 = var6.c();
            this.i.a(e);
            GlStateManager.l();
            GlStateManager.a(770, 771, 1, 0);
            Vec3 var8 = this.k.e(var1);
            float var9 = (float)var8.a;
            float var10 = (float)var8.b;
            float var11 = (float)var8.c;
            if (var2 != 2) {
               float var12 = (var9 * 30.0F + var10 * 59.0F + var11 * 11.0F) / 100.0F;
               float var13 = (var9 * 30.0F + var10 * 70.0F) / 100.0F;
               float var14 = (var9 * 30.0F + var11 * 70.0F) / 100.0F;
               var9 = var12;
               var10 = var13;
               var11 = var14;
            }

            float var26 = 4.8828125E-4F;
            double var27 = this.w + var1;
            double var15 = this.h.ac().p + (this.h.ac().s - this.h.ac().p) * var1 + var27 * 0.03F;
            double var17 = this.h.ac().r + (this.h.ac().u - this.h.ac().r) * var1;
            int var19 = MathHelper.c(var15 / 2048.0);
            int var20 = MathHelper.c(var17 / 2048.0);
            var15 -= var19 * 2048;
            var17 -= var20 * 2048;
            float var21 = this.k.t.f() - var3 + 0.33F;
            float var22 = (float)(var15 * 4.8828125E-4);
            float var23 = (float)(var17 * 4.8828125E-4);
            var7.a(7, DefaultVertexFormats.i);

            for (short var24 = -256; var24 < 256; var24 += 32) {
               for (short var25 = -256; var25 < 256; var25 += 32) {
                  var7.b((double)(var24 + 0), (double)var21, (double)(var25 + 32))
                     .a((double)((var24 + 0) * 4.8828125E-4F + var22), (double)((var25 + 32) * 4.8828125E-4F + var23))
                     .a(var9, var10, var11, 0.8F)
                     .d();
                  var7.b((double)(var24 + 32), (double)var21, (double)(var25 + 32))
                     .a((double)((var24 + 32) * 4.8828125E-4F + var22), (double)((var25 + 32) * 4.8828125E-4F + var23))
                     .a(var9, var10, var11, 0.8F)
                     .d();
                  var7.b((double)(var24 + 32), (double)var21, (double)(var25 + 0))
                     .a((double)((var24 + 32) * 4.8828125E-4F + var22), (double)((var25 + 0) * 4.8828125E-4F + var23))
                     .a(var9, var10, var11, 0.8F)
                     .d();
                  var7.b((double)(var24 + 0), (double)var21, (double)(var25 + 0))
                     .a((double)((var24 + 0) * 4.8828125E-4F + var22), (double)((var25 + 0) * 4.8828125E-4F + var23))
                     .a(var9, var10, var11, 0.8F)
                     .d();
               }
            }

            var6.b();
            GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.k();
            GlStateManager.o();
         }
      }
   }

   public boolean a(double var1, double var3, double var5, float var7) {
      return false;
   }

   private void c(float var1, int var2) {
      GlStateManager.p();
      float var3 = (float)(this.h.ac().Q + (this.h.ac().t - this.h.ac().Q) * var1);
      Tessellator var4 = Tessellator.a();
      WorldRenderer var5 = var4.c();
      float var6 = 12.0F;
      float var7 = 4.0F;
      double var8 = this.w + var1;
      double var10 = (this.h.ac().p + (this.h.ac().s - this.h.ac().p) * var1 + var8 * 0.03F) / 12.0;
      double var12 = (this.h.ac().r + (this.h.ac().u - this.h.ac().r) * var1) / 12.0 + 0.33F;
      float var14 = this.k.t.f() - var3 + 0.33F;
      int var15 = MathHelper.c(var10 / 2048.0);
      int var16 = MathHelper.c(var12 / 2048.0);
      var10 -= var15 * 2048;
      var12 -= var16 * 2048;
      this.i.a(e);
      GlStateManager.l();
      GlStateManager.a(770, 771, 1, 0);
      Vec3 var17 = this.k.e(var1);
      float var18 = (float)var17.a;
      float var19 = (float)var17.b;
      float var20 = (float)var17.c;
      if (var2 != 2) {
         float var21 = (var18 * 30.0F + var19 * 59.0F + var20 * 11.0F) / 100.0F;
         float var22 = (var18 * 30.0F + var19 * 70.0F) / 100.0F;
         float var23 = (var18 * 30.0F + var20 * 70.0F) / 100.0F;
         var18 = var21;
         var19 = var22;
         var20 = var23;
      }

      float var48 = var18 * 0.9F;
      float var49 = var19 * 0.9F;
      float var50 = var20 * 0.9F;
      float var24 = var18 * 0.7F;
      float var25 = var19 * 0.7F;
      float var26 = var20 * 0.7F;
      float var27 = var18 * 0.8F;
      float var28 = var19 * 0.8F;
      float var29 = var20 * 0.8F;
      float var30 = 0.00390625F;
      float var31 = MathHelper.c(var10) * 0.00390625F;
      float var32 = MathHelper.c(var12) * 0.00390625F;
      float var33 = (float)(var10 - MathHelper.c(var10));
      float var34 = (float)(var12 - MathHelper.c(var12));
      byte var35 = 8;
      byte var36 = 4;
      float var37 = 9.765625E-4F;
      GlStateManager.a(12.0F, 1.0F, 12.0F);

      for (int var38 = 0; var38 < 2; var38++) {
         if (var38 == 0) {
            GlStateManager.a(false, false, false, false);
         } else {
            switch (var2) {
               case 0:
                  GlStateManager.a(false, true, true, true);
                  break;
               case 1:
                  GlStateManager.a(true, false, false, true);
                  break;
               case 2:
                  GlStateManager.a(true, true, true, true);
            }
         }

         for (int var39 = -3; var39 <= 4; var39++) {
            for (int var40 = -3; var40 <= 4; var40++) {
               var5.a(7, DefaultVertexFormats.l);
               float var41 = var39 * 8;
               float var42 = var40 * 8;
               float var43 = var41 - var33;
               float var44 = var42 - var34;
               if (var14 > -5.0F) {
                  var5.b((double)(var43 + 0.0F), (double)(var14 + 0.0F), (double)(var44 + 8.0F))
                     .a((double)((var41 + 0.0F) * 0.00390625F + var31), (double)((var42 + 8.0F) * 0.00390625F + var32))
                     .a(var24, var25, var26, 0.8F)
                     .c(0.0F, -1.0F, 0.0F)
                     .d();
                  var5.b((double)(var43 + 8.0F), (double)(var14 + 0.0F), (double)(var44 + 8.0F))
                     .a((double)((var41 + 8.0F) * 0.00390625F + var31), (double)((var42 + 8.0F) * 0.00390625F + var32))
                     .a(var24, var25, var26, 0.8F)
                     .c(0.0F, -1.0F, 0.0F)
                     .d();
                  var5.b((double)(var43 + 8.0F), (double)(var14 + 0.0F), (double)(var44 + 0.0F))
                     .a((double)((var41 + 8.0F) * 0.00390625F + var31), (double)((var42 + 0.0F) * 0.00390625F + var32))
                     .a(var24, var25, var26, 0.8F)
                     .c(0.0F, -1.0F, 0.0F)
                     .d();
                  var5.b((double)(var43 + 0.0F), (double)(var14 + 0.0F), (double)(var44 + 0.0F))
                     .a((double)((var41 + 0.0F) * 0.00390625F + var31), (double)((var42 + 0.0F) * 0.00390625F + var32))
                     .a(var24, var25, var26, 0.8F)
                     .c(0.0F, -1.0F, 0.0F)
                     .d();
               }

               if (var14 <= 5.0F) {
                  var5.b((double)(var43 + 0.0F), (double)(var14 + 4.0F - 9.765625E-4F), (double)(var44 + 8.0F))
                     .a((double)((var41 + 0.0F) * 0.00390625F + var31), (double)((var42 + 8.0F) * 0.00390625F + var32))
                     .a(var18, var19, var20, 0.8F)
                     .c(0.0F, 1.0F, 0.0F)
                     .d();
                  var5.b((double)(var43 + 8.0F), (double)(var14 + 4.0F - 9.765625E-4F), (double)(var44 + 8.0F))
                     .a((double)((var41 + 8.0F) * 0.00390625F + var31), (double)((var42 + 8.0F) * 0.00390625F + var32))
                     .a(var18, var19, var20, 0.8F)
                     .c(0.0F, 1.0F, 0.0F)
                     .d();
                  var5.b((double)(var43 + 8.0F), (double)(var14 + 4.0F - 9.765625E-4F), (double)(var44 + 0.0F))
                     .a((double)((var41 + 8.0F) * 0.00390625F + var31), (double)((var42 + 0.0F) * 0.00390625F + var32))
                     .a(var18, var19, var20, 0.8F)
                     .c(0.0F, 1.0F, 0.0F)
                     .d();
                  var5.b((double)(var43 + 0.0F), (double)(var14 + 4.0F - 9.765625E-4F), (double)(var44 + 0.0F))
                     .a((double)((var41 + 0.0F) * 0.00390625F + var31), (double)((var42 + 0.0F) * 0.00390625F + var32))
                     .a(var18, var19, var20, 0.8F)
                     .c(0.0F, 1.0F, 0.0F)
                     .d();
               }

               if (var39 > -1) {
                  for (int var45 = 0; var45 < 8; var45++) {
                     var5.b((double)(var43 + var45 + 0.0F), (double)(var14 + 0.0F), (double)(var44 + 8.0F))
                        .a((double)((var41 + var45 + 0.5F) * 0.00390625F + var31), (double)((var42 + 8.0F) * 0.00390625F + var32))
                        .a(var48, var49, var50, 0.8F)
                        .c(-1.0F, 0.0F, 0.0F)
                        .d();
                     var5.b((double)(var43 + var45 + 0.0F), (double)(var14 + 4.0F), (double)(var44 + 8.0F))
                        .a((double)((var41 + var45 + 0.5F) * 0.00390625F + var31), (double)((var42 + 8.0F) * 0.00390625F + var32))
                        .a(var48, var49, var50, 0.8F)
                        .c(-1.0F, 0.0F, 0.0F)
                        .d();
                     var5.b((double)(var43 + var45 + 0.0F), (double)(var14 + 4.0F), (double)(var44 + 0.0F))
                        .a((double)((var41 + var45 + 0.5F) * 0.00390625F + var31), (double)((var42 + 0.0F) * 0.00390625F + var32))
                        .a(var48, var49, var50, 0.8F)
                        .c(-1.0F, 0.0F, 0.0F)
                        .d();
                     var5.b((double)(var43 + var45 + 0.0F), (double)(var14 + 0.0F), (double)(var44 + 0.0F))
                        .a((double)((var41 + var45 + 0.5F) * 0.00390625F + var31), (double)((var42 + 0.0F) * 0.00390625F + var32))
                        .a(var48, var49, var50, 0.8F)
                        .c(-1.0F, 0.0F, 0.0F)
                        .d();
                  }
               }

               if (var39 <= 1) {
                  for (int var51 = 0; var51 < 8; var51++) {
                     var5.b((double)(var43 + var51 + 1.0F - 9.765625E-4F), (double)(var14 + 0.0F), (double)(var44 + 8.0F))
                        .a((double)((var41 + var51 + 0.5F) * 0.00390625F + var31), (double)((var42 + 8.0F) * 0.00390625F + var32))
                        .a(var48, var49, var50, 0.8F)
                        .c(1.0F, 0.0F, 0.0F)
                        .d();
                     var5.b((double)(var43 + var51 + 1.0F - 9.765625E-4F), (double)(var14 + 4.0F), (double)(var44 + 8.0F))
                        .a((double)((var41 + var51 + 0.5F) * 0.00390625F + var31), (double)((var42 + 8.0F) * 0.00390625F + var32))
                        .a(var48, var49, var50, 0.8F)
                        .c(1.0F, 0.0F, 0.0F)
                        .d();
                     var5.b((double)(var43 + var51 + 1.0F - 9.765625E-4F), (double)(var14 + 4.0F), (double)(var44 + 0.0F))
                        .a((double)((var41 + var51 + 0.5F) * 0.00390625F + var31), (double)((var42 + 0.0F) * 0.00390625F + var32))
                        .a(var48, var49, var50, 0.8F)
                        .c(1.0F, 0.0F, 0.0F)
                        .d();
                     var5.b((double)(var43 + var51 + 1.0F - 9.765625E-4F), (double)(var14 + 0.0F), (double)(var44 + 0.0F))
                        .a((double)((var41 + var51 + 0.5F) * 0.00390625F + var31), (double)((var42 + 0.0F) * 0.00390625F + var32))
                        .a(var48, var49, var50, 0.8F)
                        .c(1.0F, 0.0F, 0.0F)
                        .d();
                  }
               }

               if (var40 > -1) {
                  for (int var52 = 0; var52 < 8; var52++) {
                     var5.b((double)(var43 + 0.0F), (double)(var14 + 4.0F), (double)(var44 + var52 + 0.0F))
                        .a((double)((var41 + 0.0F) * 0.00390625F + var31), (double)((var42 + var52 + 0.5F) * 0.00390625F + var32))
                        .a(var27, var28, var29, 0.8F)
                        .c(0.0F, 0.0F, -1.0F)
                        .d();
                     var5.b((double)(var43 + 8.0F), (double)(var14 + 4.0F), (double)(var44 + var52 + 0.0F))
                        .a((double)((var41 + 8.0F) * 0.00390625F + var31), (double)((var42 + var52 + 0.5F) * 0.00390625F + var32))
                        .a(var27, var28, var29, 0.8F)
                        .c(0.0F, 0.0F, -1.0F)
                        .d();
                     var5.b((double)(var43 + 8.0F), (double)(var14 + 0.0F), (double)(var44 + var52 + 0.0F))
                        .a((double)((var41 + 8.0F) * 0.00390625F + var31), (double)((var42 + var52 + 0.5F) * 0.00390625F + var32))
                        .a(var27, var28, var29, 0.8F)
                        .c(0.0F, 0.0F, -1.0F)
                        .d();
                     var5.b((double)(var43 + 0.0F), (double)(var14 + 0.0F), (double)(var44 + var52 + 0.0F))
                        .a((double)((var41 + 0.0F) * 0.00390625F + var31), (double)((var42 + var52 + 0.5F) * 0.00390625F + var32))
                        .a(var27, var28, var29, 0.8F)
                        .c(0.0F, 0.0F, -1.0F)
                        .d();
                  }
               }

               if (var40 <= 1) {
                  for (int var53 = 0; var53 < 8; var53++) {
                     var5.b((double)(var43 + 0.0F), (double)(var14 + 4.0F), (double)(var44 + var53 + 1.0F - 9.765625E-4F))
                        .a((double)((var41 + 0.0F) * 0.00390625F + var31), (double)((var42 + var53 + 0.5F) * 0.00390625F + var32))
                        .a(var27, var28, var29, 0.8F)
                        .c(0.0F, 0.0F, 1.0F)
                        .d();
                     var5.b((double)(var43 + 8.0F), (double)(var14 + 4.0F), (double)(var44 + var53 + 1.0F - 9.765625E-4F))
                        .a((double)((var41 + 8.0F) * 0.00390625F + var31), (double)((var42 + var53 + 0.5F) * 0.00390625F + var32))
                        .a(var27, var28, var29, 0.8F)
                        .c(0.0F, 0.0F, 1.0F)
                        .d();
                     var5.b((double)(var43 + 8.0F), (double)(var14 + 0.0F), (double)(var44 + var53 + 1.0F - 9.765625E-4F))
                        .a((double)((var41 + 8.0F) * 0.00390625F + var31), (double)((var42 + var53 + 0.5F) * 0.00390625F + var32))
                        .a(var27, var28, var29, 0.8F)
                        .c(0.0F, 0.0F, 1.0F)
                        .d();
                     var5.b((double)(var43 + 0.0F), (double)(var14 + 0.0F), (double)(var44 + var53 + 1.0F - 9.765625E-4F))
                        .a((double)((var41 + 0.0F) * 0.00390625F + var31), (double)((var42 + var53 + 0.5F) * 0.00390625F + var32))
                        .a(var27, var28, var29, 0.8F)
                        .c(0.0F, 0.0F, 1.0F)
                        .d();
                  }
               }

               var4.b();
            }
         }
      }

      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.k();
      GlStateManager.o();
   }

   public void a(long var1) {
      this.ac = this.ac | this.N.a(var1);
      if (!this.l.isEmpty()) {
         Iterator var3 = this.l.iterator();

         while (var3.hasNext()) {
            RenderChunk var4 = (RenderChunk)var3.next();
            if (!this.N.a(var4)) {
               break;
            }

            var4.a(false);
            var3.remove();
            long var5 = var1 - System.nanoTime();
            if (var5 < 0L) {
               break;
            }
         }
      }
   }

   public void a(Entity var1, float var2) {
      Tessellator var3 = Tessellator.a();
      WorldRenderer var4 = var3.c();
      WorldBorder var5 = this.k.af();
      double var6 = this.h.t.c * 16;
      if (!(var1.s < var5.d() - var6) || !(var1.s > var5.b() + var6) || !(var1.u < var5.e() - var6) || !(var1.u > var5.c() + var6)) {
         double var8 = 1.0 - var5.a(var1) / var6;
         var8 = Math.pow(var8, 4.0);
         double var10 = var1.P + (var1.s - var1.P) * var2;
         double var12 = var1.Q + (var1.t - var1.Q) * var2;
         double var14 = var1.R + (var1.u - var1.R) * var2;
         GlStateManager.l();
         GlStateManager.a(770, 1, 1, 0);
         this.i.a(g);
         GlStateManager.a(false);
         GlStateManager.E();
         int var16 = var5.a().a();
         float var17 = (var16 >> 16 & 0xFF) / 255.0F;
         float var18 = (var16 >> 8 & 0xFF) / 255.0F;
         float var19 = (var16 & 0xFF) / 255.0F;
         GlStateManager.c(var17, var18, var19, (float)var8);
         GlStateManager.a(-3.0F, -3.0F);
         GlStateManager.q();
         GlStateManager.a(516, 0.1F);
         GlStateManager.d();
         GlStateManager.p();
         float var20 = (float)(Minecraft.J() % 3000L) / 3000.0F;
         float var21 = 0.0F;
         float var22 = 0.0F;
         float var23 = 128.0F;
         var4.a(7, DefaultVertexFormats.g);
         var4.c(-var10, -var12, -var14);
         double var24 = Math.max((double)MathHelper.c(var14 - var6), var5.c());
         double var26 = Math.min((double)MathHelper.f(var14 + var6), var5.e());
         if (var10 > var5.d() - var6) {
            float var28 = 0.0F;

            for (double var29 = var24; var29 < var26; var28 += 0.5F) {
               double var31 = Math.min(1.0, var26 - var29);
               float var33 = (float)var31 * 0.5F;
               var4.b(var5.d(), 256.0, var29).a((double)(var20 + var28), (double)(var20 + 0.0F)).d();
               var4.b(var5.d(), 256.0, var29 + var31).a((double)(var20 + var33 + var28), (double)(var20 + 0.0F)).d();
               var4.b(var5.d(), 0.0, var29 + var31).a((double)(var20 + var33 + var28), (double)(var20 + 128.0F)).d();
               var4.b(var5.d(), 0.0, var29).a((double)(var20 + var28), (double)(var20 + 128.0F)).d();
               var29++;
            }
         }

         if (var10 < var5.b() + var6) {
            float var37 = 0.0F;

            for (double var40 = var24; var40 < var26; var37 += 0.5F) {
               double var43 = Math.min(1.0, var26 - var40);
               float var46 = (float)var43 * 0.5F;
               var4.b(var5.b(), 256.0, var40).a((double)(var20 + var37), (double)(var20 + 0.0F)).d();
               var4.b(var5.b(), 256.0, var40 + var43).a((double)(var20 + var46 + var37), (double)(var20 + 0.0F)).d();
               var4.b(var5.b(), 0.0, var40 + var43).a((double)(var20 + var46 + var37), (double)(var20 + 128.0F)).d();
               var4.b(var5.b(), 0.0, var40).a((double)(var20 + var37), (double)(var20 + 128.0F)).d();
               var40++;
            }
         }

         var24 = Math.max((double)MathHelper.c(var10 - var6), var5.b());
         var26 = Math.min((double)MathHelper.f(var10 + var6), var5.d());
         if (var14 > var5.e() - var6) {
            float var38 = 0.0F;

            for (double var41 = var24; var41 < var26; var38 += 0.5F) {
               double var44 = Math.min(1.0, var26 - var41);
               float var47 = (float)var44 * 0.5F;
               var4.b(var41, 256.0, var5.e()).a((double)(var20 + var38), (double)(var20 + 0.0F)).d();
               var4.b(var41 + var44, 256.0, var5.e()).a((double)(var20 + var47 + var38), (double)(var20 + 0.0F)).d();
               var4.b(var41 + var44, 0.0, var5.e()).a((double)(var20 + var47 + var38), (double)(var20 + 128.0F)).d();
               var4.b(var41, 0.0, var5.e()).a((double)(var20 + var38), (double)(var20 + 128.0F)).d();
               var41++;
            }
         }

         if (var14 < var5.c() + var6) {
            float var39 = 0.0F;

            for (double var42 = var24; var42 < var26; var39 += 0.5F) {
               double var45 = Math.min(1.0, var26 - var42);
               float var48 = (float)var45 * 0.5F;
               var4.b(var42, 256.0, var5.c()).a((double)(var20 + var39), (double)(var20 + 0.0F)).d();
               var4.b(var42 + var45, 256.0, var5.c()).a((double)(var20 + var48 + var39), (double)(var20 + 0.0F)).d();
               var4.b(var42 + var45, 0.0, var5.c()).a((double)(var20 + var48 + var39), (double)(var20 + 128.0F)).d();
               var4.b(var42, 0.0, var5.c()).a((double)(var20 + var39), (double)(var20 + 128.0F)).d();
               var42++;
            }
         }

         var3.b();
         var4.c(0.0, 0.0, 0.0);
         GlStateManager.o();
         GlStateManager.c();
         GlStateManager.a(0.0F, 0.0F);
         GlStateManager.r();
         GlStateManager.d();
         GlStateManager.k();
         GlStateManager.F();
         GlStateManager.a(true);
      }
   }

   private void s() {
      GlStateManager.a(774, 768, 1, 0);
      GlStateManager.l();
      GlStateManager.c(1.0F, 1.0F, 1.0F, 0.5F);
      GlStateManager.a(-3.0F, -3.0F);
      GlStateManager.q();
      GlStateManager.a(516, 0.1F);
      GlStateManager.d();
      GlStateManager.E();
   }

   private void t() {
      GlStateManager.c();
      GlStateManager.a(0.0F, 0.0F);
      GlStateManager.r();
      GlStateManager.d();
      GlStateManager.a(true);
      GlStateManager.F();
   }

   public void a(Tessellator var1, WorldRenderer var2, Entity var3, float var4) {
      double var5 = var3.P + (var3.s - var3.P) * var4;
      double var7 = var3.Q + (var3.t - var3.Q) * var4;
      double var9 = var3.R + (var3.u - var3.R) * var4;
      if (!this.x.isEmpty()) {
         this.i.a(TextureMap.g);
         this.s();
         var2.a(7, DefaultVertexFormats.a);
         var2.c(-var5, -var7, -var9);
         var2.c();
         Iterator var11 = this.x.values().iterator();

         while (var11.hasNext()) {
            DestroyBlockProgress var12 = (DestroyBlockProgress)var11.next();
            BlockPos var13 = var12.b();
            double var14 = var13.n() - var5;
            double var16 = var13.o() - var7;
            double var18 = var13.p() - var9;
            Block var20 = this.k.p(var13).c();
            if (!(var20 instanceof BlockChest) && !(var20 instanceof BlockEnderChest) && !(var20 instanceof BlockSign) && !(var20 instanceof BlockSkull)) {
               if (var14 * var14 + var16 * var16 + var18 * var18 > 1024.0) {
                  var11.remove();
               } else {
                  IBlockState var21 = this.k.p(var13);
                  if (var21.c().t() != Material.a) {
                     int var22 = var12.c();
                     TextureAtlasSprite var23 = this.z[var22];
                     BlockRendererDispatcher var24 = this.h.ae();
                     var24.a(var21, var13, var23, this.k);
                  }
               }
            }
         }

         var1.b();
         var2.c(0.0, 0.0, 0.0);
         this.t();
      }
   }

   public void a(EntityPlayer var1, MovingObjectPosition var2, int var3, float var4) {
      if (var3 == 0 && var2.a == MovingObjectPosition.MovingObjectType.b) {
         GlStateManager.l();
         GlStateManager.a(770, 771, 1, 0);
         GlStateManager.c(0.0F, 0.0F, 0.0F, 0.4F);
         GL11.glLineWidth(2.0F);
         GlStateManager.x();
         GlStateManager.a(false);
         float var5 = 0.002F;
         BlockPos var6 = var2.a();
         Block var7 = this.k.p(var6).c();
         if (var7.t() != Material.a && this.k.af().a(var6)) {
            var7.a((IBlockAccess)this.k, var6);
            double var8 = var1.P + (var1.s - var1.P) * var4;
            double var10 = var1.Q + (var1.t - var1.Q) * var4;
            double var12 = var1.R + (var1.u - var1.R) * var4;
            a(var7.b((World)this.k, var6).b(0.002F, 0.002F, 0.002F).c(-var8, -var10, -var12));
         }

         GlStateManager.a(true);
         GlStateManager.w();
         GlStateManager.k();
      }
   }

   public static void a(AxisAlignedBB var0) {
      Tessellator var1 = Tessellator.a();
      WorldRenderer var2 = var1.c();
      var2.a(3, DefaultVertexFormats.e);
      var2.b(var0.a, var0.b, var0.c).d();
      var2.b(var0.d, var0.b, var0.c).d();
      var2.b(var0.d, var0.b, var0.f).d();
      var2.b(var0.a, var0.b, var0.f).d();
      var2.b(var0.a, var0.b, var0.c).d();
      var1.b();
      var2.a(3, DefaultVertexFormats.e);
      var2.b(var0.a, var0.e, var0.c).d();
      var2.b(var0.d, var0.e, var0.c).d();
      var2.b(var0.d, var0.e, var0.f).d();
      var2.b(var0.a, var0.e, var0.f).d();
      var2.b(var0.a, var0.e, var0.c).d();
      var1.b();
      var2.a(1, DefaultVertexFormats.e);
      var2.b(var0.a, var0.b, var0.c).d();
      var2.b(var0.a, var0.e, var0.c).d();
      var2.b(var0.d, var0.b, var0.c).d();
      var2.b(var0.d, var0.e, var0.c).d();
      var2.b(var0.d, var0.b, var0.f).d();
      var2.b(var0.d, var0.e, var0.f).d();
      var2.b(var0.a, var0.b, var0.f).d();
      var2.b(var0.a, var0.e, var0.f).d();
      var1.b();
   }

   public static void a(AxisAlignedBB var0, int var1, int var2, int var3, int var4) {
      Tessellator var5 = Tessellator.a();
      WorldRenderer var6 = var5.c();
      var6.a(3, DefaultVertexFormats.f);
      var6.b(var0.a, var0.b, var0.c).b(var1, var2, var3, var4).d();
      var6.b(var0.d, var0.b, var0.c).b(var1, var2, var3, var4).d();
      var6.b(var0.d, var0.b, var0.f).b(var1, var2, var3, var4).d();
      var6.b(var0.a, var0.b, var0.f).b(var1, var2, var3, var4).d();
      var6.b(var0.a, var0.b, var0.c).b(var1, var2, var3, var4).d();
      var5.b();
      var6.a(3, DefaultVertexFormats.f);
      var6.b(var0.a, var0.e, var0.c).b(var1, var2, var3, var4).d();
      var6.b(var0.d, var0.e, var0.c).b(var1, var2, var3, var4).d();
      var6.b(var0.d, var0.e, var0.f).b(var1, var2, var3, var4).d();
      var6.b(var0.a, var0.e, var0.f).b(var1, var2, var3, var4).d();
      var6.b(var0.a, var0.e, var0.c).b(var1, var2, var3, var4).d();
      var5.b();
      var6.a(1, DefaultVertexFormats.f);
      var6.b(var0.a, var0.b, var0.c).b(var1, var2, var3, var4).d();
      var6.b(var0.a, var0.e, var0.c).b(var1, var2, var3, var4).d();
      var6.b(var0.d, var0.b, var0.c).b(var1, var2, var3, var4).d();
      var6.b(var0.d, var0.e, var0.c).b(var1, var2, var3, var4).d();
      var6.b(var0.d, var0.b, var0.f).b(var1, var2, var3, var4).d();
      var6.b(var0.d, var0.e, var0.f).b(var1, var2, var3, var4).d();
      var6.b(var0.a, var0.b, var0.f).b(var1, var2, var3, var4).d();
      var6.b(var0.a, var0.e, var0.f).b(var1, var2, var3, var4).d();
      var5.b();
   }

   private void b(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.o.a(var1, var2, var3, var4, var5, var6);
   }

   @Override
   public void a(BlockPos var1) {
      int var2 = var1.n();
      int var3 = var1.o();
      int var4 = var1.p();
      this.b(var2 - 1, var3 - 1, var4 - 1, var2 + 1, var3 + 1, var4 + 1);
   }

   @Override
   public void b(BlockPos var1) {
      int var2 = var1.n();
      int var3 = var1.o();
      int var4 = var1.p();
      this.b(var2 - 1, var3 - 1, var4 - 1, var2 + 1, var3 + 1, var4 + 1);
   }

   @Override
   public void a(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.b(var1 - 1, var2 - 1, var3 - 1, var4 + 1, var5 + 1, var6 + 1);
   }

   @Override
   public void a(String var1, BlockPos var2) {
      ISound var3 = this.y.get(var2);
      if (var3 != null) {
         this.h.W().b(var3);
         this.y.remove(var2);
      }

      if (var1 != null) {
         ItemRecord var4 = ItemRecord.b(var1);
         if (var4 != null) {
            this.h.q.a(var4.g());
         }

         PositionedSoundRecord var5 = PositionedSoundRecord.a(new ResourceLocation(var1), var2.n(), var2.o(), var2.p());
         this.y.put(var2, var5);
         this.h.W().a(var5);
      }
   }

   @Override
   public void a(String var1, double var2, double var4, double var6, float var8, float var9) {
   }

   @Override
   public void a(EntityPlayer var1, String var2, double var3, double var5, double var7, float var9, float var10) {
   }

   @Override
   public void a(int var1, boolean var2, final double var3, final double var5, final double var7, double var9, double var11, double var13, int... var15) {
      try {
         this.b(var1, var2, var3, var5, var7, var9, var11, var13, var15);
      } catch (Throwable var19) {
         CrashReport var17 = CrashReport.a(var19, "Exception while adding particle");
         CrashReportCategory var18 = var17.a("Particle being added");
         var18.a("ID", var1);
         if (var15 != null) {
            var18.a("Parameters", var15);
         }

         var18.a("Position", new Callable<String>() {
            public String a() throws Exception {
               return CrashReportCategory.a(var3, var5, var7);
            }
         });
         throw new ReportedException(var17);
      }
   }

   private void a(EnumParticleTypes var1, double var2, double var4, double var6, double var8, double var10, double var12, int... var14) {
      this.a(var1.c(), var1.e(), var2, var4, var6, var8, var10, var12, var14);
   }

   private EntityFX b(int var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
      if (this.h != null && this.h.ac() != null && this.h.j != null) {
         int var16 = this.h.t.aM;
         if (var16 == 1 && this.k.s.nextInt(3) == 0) {
            var16 = 2;
         }

         double var17 = this.h.ac().s - var3;
         double var19 = this.h.ac().t - var5;
         double var21 = this.h.ac().u - var7;
         if (var2) {
            return this.h.j.a(var1, var3, var5, var7, var9, var11, var13, var15);
         } else {
            double var23 = 16.0;
            if (var17 * var17 + var19 * var19 + var21 * var21 > 256.0) {
               return null;
            } else {
               return var16 > 1 ? null : this.h.j.a(var1, var3, var5, var7, var9, var11, var13, var15);
            }
         }
      } else {
         return null;
      }
   }

   @Override
   public void a(Entity var1) {
   }

   @Override
   public void b(Entity var1) {
   }

   public void k() {
   }

   @Override
   public void a(int var1, BlockPos var2, int var3) {
      switch (var1) {
         case 1013:
         case 1018:
            if (this.h.ac() != null) {
               double var4 = var2.n() - this.h.ac().s;
               double var6 = var2.o() - this.h.ac().t;
               double var8 = var2.p() - this.h.ac().u;
               double var10 = Math.sqrt(var4 * var4 + var6 * var6 + var8 * var8);
               double var12 = this.h.ac().s;
               double var14 = this.h.ac().t;
               double var16 = this.h.ac().u;
               if (var10 > 0.0) {
                  var12 += var4 / var10 * 2.0;
                  var14 += var6 / var10 * 2.0;
                  var16 += var8 / var10 * 2.0;
               }

               if (var1 == 1013) {
                  this.k.a(var12, var14, var16, "mob.wither.spawn", 1.0F, 1.0F, false);
               } else {
                  this.k.a(var12, var14, var16, "mob.enderdragon.end", 5.0F, 1.0F, false);
               }
            }
      }
   }

   @Override
   public void a(EntityPlayer var1, int var2, BlockPos var3, int var4) {
      Random var5 = this.k.s;
      switch (var2) {
         case 1000:
            this.k.a(var3, "random.click", 1.0F, 1.0F, false);
            break;
         case 1001:
            this.k.a(var3, "random.click", 1.0F, 1.2F, false);
            break;
         case 1002:
            this.k.a(var3, "random.bow", 1.0F, 1.2F, false);
            break;
         case 1003:
            this.k.a(var3, "random.door_open", 1.0F, this.k.s.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1004:
            this.k.a(var3, "random.fizz", 0.5F, 2.6F + (var5.nextFloat() - var5.nextFloat()) * 0.8F, false);
            break;
         case 1005:
            if (Item.b(var4) instanceof ItemRecord) {
               this.k.a(var3, "records." + ((ItemRecord)Item.b(var4)).a);
            } else {
               this.k.a(var3, null);
            }
            break;
         case 1006:
            this.k.a(var3, "random.door_close", 1.0F, this.k.s.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1007:
            this.k.a(var3, "mob.ghast.charge", 10.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1008:
            this.k.a(var3, "mob.ghast.fireball", 10.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1009:
            this.k.a(var3, "mob.ghast.fireball", 2.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1010:
            this.k.a(var3, "mob.zombie.wood", 2.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1011:
            this.k.a(var3, "mob.zombie.metal", 2.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1012:
            this.k.a(var3, "mob.zombie.woodbreak", 2.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1014:
            this.k.a(var3, "mob.wither.shoot", 2.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1015:
            this.k.a(var3, "mob.bat.takeoff", 0.05F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1016:
            this.k.a(var3, "mob.zombie.infect", 2.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1017:
            this.k.a(var3, "mob.zombie.unfect", 2.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1020:
            this.k.a(var3, "random.anvil_break", 1.0F, this.k.s.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1021:
            this.k.a(var3, "random.anvil_use", 1.0F, this.k.s.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1022:
            this.k.a(var3, "random.anvil_land", 0.3F, this.k.s.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 2000:
            int var32 = var4 % 3 - 1;
            int var8 = var4 / 3 % 3 - 1;
            double var34 = var3.n() + var32 * 0.6 + 0.5;
            double var36 = var3.o() + 0.5;
            double var40 = var3.p() + var8 * 0.6 + 0.5;

            for (int var41 = 0; var41 < 10; var41++) {
               double var42 = var5.nextDouble() * 0.2 + 0.01;
               double var44 = var34 + var32 * 0.01 + (var5.nextDouble() - 0.5) * var8 * 0.5;
               double var20 = var36 + (var5.nextDouble() - 0.5) * 0.5;
               double var22 = var40 + var8 * 0.01 + (var5.nextDouble() - 0.5) * var32 * 0.5;
               double var24 = var32 * var42 + var5.nextGaussian() * 0.01;
               double var26 = -0.03 + var5.nextGaussian() * 0.01;
               double var28 = var8 * var42 + var5.nextGaussian() * 0.01;
               this.a(EnumParticleTypes.l, var44, var20, var22, var24, var26, var28);
            }
            break;
         case 2001:
            Block var6 = Block.c(var4 & 4095);
            if (var6.t() != Material.a) {
               this.h
                  .W()
                  .a(
                     new PositionedSoundRecord(
                        new ResourceLocation(var6.H.a()), (var6.H.d() + 1.0F) / 2.0F, var6.H.e() * 0.8F, var3.n() + 0.5F, var3.o() + 0.5F, var3.p() + 0.5F
                     )
                  );
            }

            this.h.j.a(var3, var6.a(var4 >> 12 & 0xFF));
            break;
         case 2002:
            double var31 = var3.n();
            double var33 = var3.o();
            double var35 = var3.p();

            for (int var38 = 0; var38 < 8; var38++) {
               this.a(
                  EnumParticleTypes.K,
                  var31,
                  var33,
                  var35,
                  var5.nextGaussian() * 0.15,
                  var5.nextDouble() * 0.2,
                  var5.nextGaussian() * 0.15,
                  Item.b(Items.bz),
                  var4
               );
            }

            int var39 = Items.bz.g(var4);
            float var14 = (var39 >> 16 & 0xFF) / 255.0F;
            float var15 = (var39 >> 8 & 0xFF) / 255.0F;
            float var16 = (var39 >> 0 & 0xFF) / 255.0F;
            EnumParticleTypes var17 = EnumParticleTypes.n;
            if (Items.bz.h(var4)) {
               var17 = EnumParticleTypes.o;
            }

            for (int var43 = 0; var43 < 100; var43++) {
               double var45 = var5.nextDouble() * 4.0;
               double var46 = var5.nextDouble() * Math.PI * 2.0;
               double var47 = Math.cos(var46) * var45;
               double var25 = 0.01 + var5.nextDouble() * 0.5;
               double var27 = Math.sin(var46) * var45;
               EntityFX var29 = this.b(var17.c(), var17.e(), var31 + var47 * 0.1, var33 + 0.3, var35 + var27 * 0.1, var47, var25, var27);
               if (var29 != null) {
                  float var30 = 0.75F + var5.nextFloat() * 0.25F;
                  var29.b(var14 * var30, var15 * var30, var16 * var30);
                  var29.a((float)var45);
               }
            }

            this.k.a(var3, "game.potion.smash", 1.0F, this.k.s.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 2003:
            double var7 = var3.n() + 0.5;
            double var9 = var3.o();
            double var11 = var3.p() + 0.5;

            for (int var13 = 0; var13 < 8; var13++) {
               this.a(EnumParticleTypes.K, var7, var9, var11, var5.nextGaussian() * 0.15, var5.nextDouble() * 0.2, var5.nextGaussian() * 0.15, Item.b(Items.bH));
            }

            for (double var37 = 0.0; var37 < Math.PI * 2; var37 += Math.PI / 20) {
               this.a(
                  EnumParticleTypes.y,
                  var7 + Math.cos(var37) * 5.0,
                  var9 - 0.4,
                  var11 + Math.sin(var37) * 5.0,
                  Math.cos(var37) * -5.0,
                  0.0,
                  Math.sin(var37) * -5.0
               );
               this.a(
                  EnumParticleTypes.y,
                  var7 + Math.cos(var37) * 5.0,
                  var9 - 0.4,
                  var11 + Math.sin(var37) * 5.0,
                  Math.cos(var37) * -7.0,
                  0.0,
                  Math.sin(var37) * -7.0
               );
            }
            break;
         case 2004:
            for (int var18 = 0; var18 < 20; var18++) {
               double var19 = var3.n() + 0.5 + (this.k.s.nextFloat() - 0.5) * 2.0;
               double var21 = var3.o() + 0.5 + (this.k.s.nextFloat() - 0.5) * 2.0;
               double var23 = var3.p() + 0.5 + (this.k.s.nextFloat() - 0.5) * 2.0;
               this.k.a(EnumParticleTypes.l, var19, var21, var23, 0.0, 0.0, 0.0, new int[0]);
               this.k.a(EnumParticleTypes.A, var19, var21, var23, 0.0, 0.0, 0.0, new int[0]);
            }
            break;
         case 2005:
            ItemDye.a(this.k, var3, var4);
      }
   }

   @Override
   public void b(int var1, BlockPos var2, int var3) {
      if (var3 >= 0 && var3 < 10) {
         DestroyBlockProgress var4 = this.x.get(var1);
         if (var4 == null || var4.b().n() != var2.n() || var4.b().o() != var2.o() || var4.b().p() != var2.p()) {
            var4 = new DestroyBlockProgress(var1, var2);
            this.x.put(var1, var4);
         }

         var4.a(var3);
         var4.b(this.w);
      } else {
         this.x.remove(var1);
      }
   }

   public void m() {
      this.ac = true;
   }

   public void a(Collection<TileEntity> var1, Collection<TileEntity> var2) {
      synchronized (this.n) {
         this.n.removeAll(var1);
         this.n.addAll(var2);
      }
   }

   class ContainerLocalRenderInformation {
      final RenderChunk a;
      final EnumFacing b;
      final Set<EnumFacing> c = EnumSet.noneOf(EnumFacing.class);
      final int d;

      private ContainerLocalRenderInformation(RenderChunk var2, EnumFacing var3, int var4) {
         this.a = var2;
         this.b = var3;
         this.d = var4;
      }
   }
}
