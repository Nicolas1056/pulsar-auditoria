package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.nio.FloatBuffer;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RegionRenderCache;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class RenderChunk {
   private World d;
   private final RenderGlobal e;
   public static int a;
   private BlockPos f;
   public CompiledChunk b = CompiledChunk.a;
   private final ReentrantLock g = new ReentrantLock();
   private final ReentrantLock h = new ReentrantLock();
   private ChunkCompileTaskGenerator i = null;
   private final Set<TileEntity> j = Sets.newHashSet();
   private final int k;
   private final FloatBuffer l = GLAllocation.h(16);
   private final VertexBuffer[] m = new VertexBuffer[EnumWorldBlockLayer.values().length];
   public AxisAlignedBB c;
   private int n = -1;
   private boolean o = true;
   private EnumMap<EnumFacing, BlockPos> p = Maps.newEnumMap(EnumFacing.class);

   public RenderChunk(World var1, RenderGlobal var2, BlockPos var3, int var4) {
      this.d = var1;
      this.e = var2;
      this.k = var4;
      if (!var3.equals(this.j())) {
         this.a(var3);
      }

      if (OpenGlHelper.f()) {
         for (int var5 = 0; var5 < EnumWorldBlockLayer.values().length; var5++) {
            this.m[var5] = new VertexBuffer(DefaultVertexFormats.a);
         }
      }
   }

   public boolean a(int var1) {
      if (this.n == var1) {
         return false;
      } else {
         this.n = var1;
         return true;
      }
   }

   public VertexBuffer b(int var1) {
      return this.m[var1];
   }

   public void a(BlockPos var1) {
      this.h();
      this.f = var1;
      this.c = new AxisAlignedBB(var1, var1.a(16, 16, 16));

      for (EnumFacing var5 : EnumFacing.values()) {
         this.p.put(var5, var1.a(var5, 16));
      }

      this.m();
   }

   public void a(float var1, float var2, float var3, ChunkCompileTaskGenerator var4) {
      CompiledChunk var5 = var4.c();
      if (var5.c() != null && !var5.b(EnumWorldBlockLayer.d)) {
         this.a(var4.d().a(EnumWorldBlockLayer.d), this.f);
         var4.d().a(EnumWorldBlockLayer.d).a(var5.c());
         this.a(EnumWorldBlockLayer.d, var1, var2, var3, var4.d().a(EnumWorldBlockLayer.d), var5);
      }
   }

   public void b(float var1, float var2, float var3, ChunkCompileTaskGenerator var4) {
      CompiledChunk var5 = new CompiledChunk();
      boolean var6 = true;
      BlockPos var7 = this.f;
      BlockPos var8 = var7.a(15, 15, 15);
      var4.f().lock();

      RegionRenderCache var9;
      try {
         if (var4.a() != ChunkCompileTaskGenerator.Status.b) {
            return;
         }

         var9 = new RegionRenderCache(this.d, var7.a(-1, -1, -1), var8.a(1, 1, 1), 1);
         var4.a(var5);
      } finally {
         var4.f().unlock();
      }

      VisGraph var10 = new VisGraph();
      HashSet var11 = Sets.newHashSet();
      if (!var9.W()) {
         a++;
         boolean[] var12 = new boolean[EnumWorldBlockLayer.values().length];
         BlockRendererDispatcher var13 = Minecraft.A().ae();

         for (BlockPos.MutableBlockPos var15 : BlockPos.b(var7, var8)) {
            IBlockState var16 = var9.p(var15);
            Block var17 = var16.c();
            if (var17.c()) {
               var10.a(var15);
            }

            if (var17.z()) {
               TileEntity var18 = var9.s(new BlockPos(var15));
               TileEntitySpecialRenderer var19 = TileEntityRendererDispatcher.a.b(var18);
               if (var18 != null && var19 != null) {
                  var5.a(var18);
                  if (var19.a()) {
                     var11.add(var18);
                  }
               }
            }

            EnumWorldBlockLayer var34 = var17.m();
            int var35 = var34.ordinal();
            if (var17.b() != -1) {
               WorldRenderer var20 = var4.d().a(var35);
               if (!var5.d(var34)) {
                  var5.c(var34);
                  this.a(var20, var7);
               }

               var12[var35] |= var13.a(var16, var15, var9, var20);
            }
         }

         for (EnumWorldBlockLayer var33 : EnumWorldBlockLayer.values()) {
            if (var12[var33.ordinal()]) {
               var5.a(var33);
            }

            if (var5.d(var33)) {
               this.a(var33, var1, var2, var3, var4.d().a(var33), var5);
            }
         }
      }

      var5.a(var10.a());
      this.g.lock();

      try {
         HashSet var28 = Sets.newHashSet(var11);
         HashSet var29 = Sets.newHashSet(this.j);
         var28.removeAll(this.j);
         var29.removeAll(var11);
         this.j.clear();
         this.j.addAll(var11);
         this.e.a(var29, var28);
      } finally {
         this.g.unlock();
      }
   }

   protected void b() {
      this.g.lock();

      try {
         if (this.i != null && this.i.a() != ChunkCompileTaskGenerator.Status.d) {
            this.i.e();
            this.i = null;
         }
      } finally {
         this.g.unlock();
      }
   }

   public ReentrantLock c() {
      return this.g;
   }

   public ChunkCompileTaskGenerator d() {
      this.g.lock();

      ChunkCompileTaskGenerator var1;
      try {
         this.b();
         this.i = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Type.a);
         var1 = this.i;
      } finally {
         this.g.unlock();
      }

      return var1;
   }

   public ChunkCompileTaskGenerator e() {
      this.g.lock();

      Object var1;
      try {
         if (this.i == null || this.i.a() != ChunkCompileTaskGenerator.Status.a) {
            if (this.i != null && this.i.a() != ChunkCompileTaskGenerator.Status.d) {
               this.i.e();
               this.i = null;
            }

            this.i = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Type.b);
            this.i.a(this.b);
            return this.i;
         }

         var1 = null;
      } finally {
         this.g.unlock();
      }

      return (ChunkCompileTaskGenerator)var1;
   }

   private void a(WorldRenderer var1, BlockPos var2) {
      var1.a(7, DefaultVertexFormats.a);
      var1.c((double)(-var2.n()), (double)(-var2.o()), (double)(-var2.p()));
   }

   private void a(EnumWorldBlockLayer var1, float var2, float var3, float var4, WorldRenderer var5, CompiledChunk var6) {
      if (var1 == EnumWorldBlockLayer.d && !var6.b(var1)) {
         var5.a(var2, var3, var4);
         var6.a(var5.a());
      }

      var5.e();
   }

   private void m() {
      GlStateManager.E();
      GlStateManager.D();
      float var1 = 1.000001F;
      GlStateManager.b(-8.0F, -8.0F, -8.0F);
      GlStateManager.a(var1, var1, var1);
      GlStateManager.b(8.0F, 8.0F, 8.0F);
      GlStateManager.a(2982, this.l);
      GlStateManager.F();
   }

   public void f() {
      GlStateManager.a(this.l);
   }

   public CompiledChunk g() {
      return this.b;
   }

   public void a(CompiledChunk var1) {
      this.h.lock();

      try {
         this.b = var1;
      } finally {
         this.h.unlock();
      }
   }

   public void h() {
      this.b();
      this.b = CompiledChunk.a;
   }

   public void a() {
      this.h();
      this.d = null;

      for (int var1 = 0; var1 < EnumWorldBlockLayer.values().length; var1++) {
         if (this.m[var1] != null) {
            this.m[var1].c();
         }
      }
   }

   public BlockPos j() {
      return this.f;
   }

   public void a(boolean var1) {
      this.o = var1;
   }

   public boolean l() {
      return this.o;
   }

   public BlockPos a(EnumFacing var1) {
      return this.p.get(var1);
   }
}
