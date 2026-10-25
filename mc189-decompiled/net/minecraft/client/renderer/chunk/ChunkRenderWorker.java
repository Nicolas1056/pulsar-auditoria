package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumWorldBlockLayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkRenderWorker implements Runnable {
   private static final Logger a = LogManager.getLogger();
   private final ChunkRenderDispatcher b;
   private final RegionRenderCacheBuilder c;

   public ChunkRenderWorker(ChunkRenderDispatcher var1) {
      this(var1, null);
   }

   public ChunkRenderWorker(ChunkRenderDispatcher var1, RegionRenderCacheBuilder var2) {
      this.b = var1;
      this.c = var2;
   }

   @Override
   public void run() {
      while (true) {
         try {
            this.a(this.b.d());
         } catch (InterruptedException var3) {
            a.debug("Stopping due to interrupt");
            return;
         } catch (Throwable var4) {
            CrashReport var2 = CrashReport.a(var4, "Batching chunks");
            Minecraft.A().a(Minecraft.A().b(var2));
            return;
         }
      }
   }

   protected void a(final ChunkCompileTaskGenerator var1) throws InterruptedException {
      var1.f().lock();

      try {
         if (var1.a() != ChunkCompileTaskGenerator.Status.a) {
            if (!var1.h()) {
               a.warn("Chunk render task was " + var1.a() + " when I expected it to be pending; ignoring task");
            }

            return;
         }

         var1.a(ChunkCompileTaskGenerator.Status.b);
      } finally {
         var1.f().unlock();
      }

      Entity var2 = Minecraft.A().ac();
      if (var2 == null) {
         var1.e();
      } else {
         var1.a(this.b());
         float var3 = (float)var2.s;
         float var4 = (float)var2.t + var2.aS();
         float var5 = (float)var2.u;
         ChunkCompileTaskGenerator.Type var6 = var1.g();
         if (var6 == ChunkCompileTaskGenerator.Type.a) {
            var1.b().b(var3, var4, var5, var1);
         } else if (var6 == ChunkCompileTaskGenerator.Type.b) {
            var1.b().a(var3, var4, var5, var1);
         }

         var1.f().lock();

         try {
            if (var1.a() != ChunkCompileTaskGenerator.Status.b) {
               if (!var1.h()) {
                  a.warn("Chunk render task was " + var1.a() + " when I expected it to be compiling; aborting task");
               }

               this.b(var1);
               return;
            }

            var1.a(ChunkCompileTaskGenerator.Status.c);
         } finally {
            var1.f().unlock();
         }

         final CompiledChunk var7 = var1.c();
         ArrayList var8 = Lists.newArrayList();
         if (var6 == ChunkCompileTaskGenerator.Type.a) {
            for (EnumWorldBlockLayer var12 : EnumWorldBlockLayer.values()) {
               if (var7.d(var12)) {
                  var8.add(this.b.a(var12, var1.d().a(var12), var1.b(), var7));
               }
            }
         } else if (var6 == ChunkCompileTaskGenerator.Type.b) {
            var8.add(this.b.a(EnumWorldBlockLayer.d, var1.d().a(EnumWorldBlockLayer.d), var1.b(), var7));
         }

         final ListenableFuture var19 = Futures.allAsList(var8);
         var1.a(new Runnable() {
            @Override
            public void run() {
               var19.cancel(false);
            }
         });
         Futures.addCallback(var19, new FutureCallback<List<Object>>() {
            public void a(List<Object> var1x) {
               ChunkRenderWorker.this.b(var1);
               var1.f().lock();

               label44: {
                  try {
                     if (var1.a() == ChunkCompileTaskGenerator.Status.c) {
                        var1.a(ChunkCompileTaskGenerator.Status.d);
                        break label44;
                     }

                     if (!var1.h()) {
                        ChunkRenderWorker.a.warn("Chunk render task was " + var1.a() + " when I expected it to be uploading; aborting task");
                     }
                  } finally {
                     var1.f().unlock();
                  }

                  return;
               }

               var1.b().a(var7);
            }

            public void onFailure(Throwable var1x) {
               ChunkRenderWorker.this.b(var1);
               if (!(var1x instanceof CancellationException) && !(var1x instanceof InterruptedException)) {
                  Minecraft.A().a(CrashReport.a(var1x, "Rendering chunk"));
               }
            }
         });
      }
   }

   private RegionRenderCacheBuilder b() throws InterruptedException {
      return this.c != null ? this.c : this.b.c();
   }

   private void b(ChunkCompileTaskGenerator var1) {
      if (this.c == null) {
         this.b.a(var1.d());
      }
   }
}
