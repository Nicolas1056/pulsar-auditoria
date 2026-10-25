package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;

public class ChunkCompileTaskGenerator {
   private final RenderChunk a;
   private final ReentrantLock b = new ReentrantLock();
   private final List<Runnable> c = Lists.newArrayList();
   private final ChunkCompileTaskGenerator.Type d;
   private RegionRenderCacheBuilder e;
   private CompiledChunk f;
   private ChunkCompileTaskGenerator.Status g = ChunkCompileTaskGenerator.Status.a;
   private boolean h;

   public ChunkCompileTaskGenerator(RenderChunk var1, ChunkCompileTaskGenerator.Type var2) {
      this.a = var1;
      this.d = var2;
   }

   public ChunkCompileTaskGenerator.Status a() {
      return this.g;
   }

   public RenderChunk b() {
      return this.a;
   }

   public CompiledChunk c() {
      return this.f;
   }

   public void a(CompiledChunk var1) {
      this.f = var1;
   }

   public RegionRenderCacheBuilder d() {
      return this.e;
   }

   public void a(RegionRenderCacheBuilder var1) {
      this.e = var1;
   }

   public void a(ChunkCompileTaskGenerator.Status var1) {
      this.b.lock();

      try {
         this.g = var1;
      } finally {
         this.b.unlock();
      }
   }

   public void e() {
      this.b.lock();

      try {
         if (this.d == ChunkCompileTaskGenerator.Type.a && this.g != ChunkCompileTaskGenerator.Status.d) {
            this.a.a(true);
         }

         this.h = true;
         this.g = ChunkCompileTaskGenerator.Status.d;

         for (Runnable var2 : this.c) {
            var2.run();
         }
      } finally {
         this.b.unlock();
      }
   }

   public void a(Runnable var1) {
      this.b.lock();

      try {
         this.c.add(var1);
         if (this.h) {
            var1.run();
         }
      } finally {
         this.b.unlock();
      }
   }

   public ReentrantLock f() {
      return this.b;
   }

   public ChunkCompileTaskGenerator.Type g() {
      return this.d;
   }

   public boolean h() {
      return this.h;
   }

   public static enum Status {
      a,
      b,
      c,
      d;
   }

   public static enum Type {
      a,
      b;
   }
}
