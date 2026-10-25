package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.client.renderer.VertexBufferUploader;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.EnumWorldBlockLayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

public class ChunkRenderDispatcher {
   private static final Logger a = LogManager.getLogger();
   private static final ThreadFactory b = new ThreadFactoryBuilder().setNameFormat("Chunk Batcher %d").setDaemon(true).build();
   private final List<ChunkRenderWorker> c = Lists.newArrayList();
   private final BlockingQueue<ChunkCompileTaskGenerator> d = Queues.newArrayBlockingQueue(100);
   private final BlockingQueue<RegionRenderCacheBuilder> e = Queues.newArrayBlockingQueue(5);
   private final WorldVertexBufferUploader f = new WorldVertexBufferUploader();
   private final VertexBufferUploader g = new VertexBufferUploader();
   private final Queue<ListenableFutureTask<?>> h = Queues.newArrayDeque();
   private final ChunkRenderWorker i;

   public ChunkRenderDispatcher() {
      for (int var1 = 0; var1 < 2; var1++) {
         ChunkRenderWorker var2 = new ChunkRenderWorker(this);
         Thread var3 = b.newThread(var2);
         var3.start();
         this.c.add(var2);
      }

      for (int var4 = 0; var4 < 5; var4++) {
         this.e.add(new RegionRenderCacheBuilder());
      }

      this.i = new ChunkRenderWorker(this, new RegionRenderCacheBuilder());
   }

   public String a() {
      return String.format("pC: %03d, pU: %1d, aB: %1d", this.d.size(), this.h.size(), this.e.size());
   }

   public boolean a(long var1) {
      boolean var3 = false;

      long var8;
      do {
         boolean var4 = false;
         synchronized (this.h) {
            if (!this.h.isEmpty()) {
               this.h.poll().run();
               var4 = true;
               var3 = true;
            }
         }

         if (var1 == 0L || !var4) {
            break;
         }

         var8 = var1 - System.nanoTime();
      } while (var8 >= 0L);

      return var3;
   }

   public boolean a(RenderChunk var1) {
      var1.c().lock();

      boolean var4;
      try {
         final ChunkCompileTaskGenerator var2 = var1.d();
         var2.a(new Runnable() {
            @Override
            public void run() {
               ChunkRenderDispatcher.this.d.remove(var2);
            }
         });
         boolean var3 = this.d.offer(var2);
         if (!var3) {
            var2.e();
         }

         var4 = var3;
      } finally {
         var1.c().unlock();
      }

      return var4;
   }

   public boolean b(RenderChunk var1) {
      var1.c().lock();

      boolean var3;
      try {
         ChunkCompileTaskGenerator var2 = var1.d();

         try {
            this.i.a(var2);
         } catch (InterruptedException var7) {
         }

         var3 = true;
      } finally {
         var1.c().unlock();
      }

      return var3;
   }

   public void b() {
      this.e();

      while (this.a(0L)) {
      }

      ArrayList var1 = Lists.newArrayList();

      while (var1.size() != 5) {
         try {
            var1.add(this.c());
         } catch (InterruptedException var3) {
         }
      }

      this.e.addAll(var1);
   }

   public void a(RegionRenderCacheBuilder var1) {
      this.e.add(var1);
   }

   public RegionRenderCacheBuilder c() throws InterruptedException {
      return this.e.take();
   }

   public ChunkCompileTaskGenerator d() throws InterruptedException {
      return this.d.take();
   }

   public boolean c(RenderChunk var1) {
      var1.c().lock();

      boolean var3;
      try {
         final ChunkCompileTaskGenerator var2 = var1.e();
         if (var2 == null) {
            return true;
         }

         var2.a(new Runnable() {
            @Override
            public void run() {
               ChunkRenderDispatcher.this.d.remove(var2);
            }
         });
         var3 = this.d.offer(var2);
      } finally {
         var1.c().unlock();
      }

      return var3;
   }

   public ListenableFuture<Object> a(final EnumWorldBlockLayer var1, final WorldRenderer var2, final RenderChunk var3, final CompiledChunk var4) {
      if (Minecraft.A().aJ()) {
         if (OpenGlHelper.f()) {
            this.a(var2, var3.b(var1.ordinal()));
         } else {
            this.a(var2, ((ListedRenderChunk)var3).a(var1, var4), var3);
         }

         var2.c(0.0, 0.0, 0.0);
         return Futures.immediateFuture(null);
      } else {
         ListenableFutureTask var5 = ListenableFutureTask.create(new Runnable() {
            @Override
            public void run() {
               ChunkRenderDispatcher.this.a(var1, var2, var3, var4);
            }
         }, null);
         synchronized (this.h) {
            this.h.add(var5);
            return var5;
         }
      }
   }

   private void a(WorldRenderer var1, int var2, RenderChunk var3) {
      GL11.glNewList(var2, 4864);
      GlStateManager.E();
      var3.f();
      this.f.a(var1);
      GlStateManager.F();
      GL11.glEndList();
   }

   private void a(WorldRenderer var1, VertexBuffer var2) {
      this.g.a(var2);
      this.g.a(var1);
   }

   public void e() {
      while (!this.d.isEmpty()) {
         ChunkCompileTaskGenerator var1 = this.d.poll();
         if (var1 != null) {
            var1.e();
         }
      }
   }
}
