package com.metrofico.pulsar.engine.v26x.perf;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.bootstrap.BootstrapLog;
import com.metrofico.pulsar.engine.v26x.mixin.NameTagFeatureRendererAccessor;
import com.metrofico.pulsar.engine.v26x.mixin.PreparedGroupAccessor;
import com.mojang.blaze3d.ProjectionType;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Font.PreparedText;
import net.minecraft.client.renderer.StagedVertexBuffer;
import net.minecraft.client.renderer.feature.CustomFeatureRenderer;
import net.minecraft.client.renderer.feature.FeatureFrameContext;
import net.minecraft.client.renderer.feature.FeatureRenderer;
import net.minecraft.client.renderer.feature.FeatureRendererMap;
import net.minecraft.client.renderer.feature.FlameFeatureRenderer;
import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import net.minecraft.client.renderer.feature.LeashFeatureRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.feature.NameTagFeatureRenderer;
import net.minecraft.client.renderer.feature.RenderTypeFeatureRenderer;
import net.minecraft.client.renderer.feature.ShadowFeatureRenderer;
import net.minecraft.client.renderer.feature.NameTagFeatureRenderer.Submit;
import net.minecraft.client.renderer.feature.submit.SubmitNode;
import net.minecraft.client.renderer.rendertype.PreparedRenderType;
import net.minecraft.client.renderer.rendertype.RenderType;

public final class ParallelFeatures {
   private static volatile boolean broken;
   private static boolean frameActive;
   private static ProjectionType projection;
   private static final Map<Object, StagedVertexBuffer> BUFFERS = new IdentityHashMap<>();
   private static final ConcurrentHashMap<Object, StagedVertexBuffer> FRAME_BUFFERS = new ConcurrentHashMap<>();
   private static final ConcurrentHashMap<RenderType, PreparedRenderType> PREPARED = new ConcurrentHashMap<>();
   private static final ConcurrentHashMap<Object, PreparedText> TEXTS = new ConcurrentHashMap<>();
   private static final List<Runnable> MAIN = new ArrayList<>();
   private static final Set<StagedVertexBuffer> USED = Collections.newSetFromMap(new IdentityHashMap<>());
   private static final List<Runnable> MAIN_AFTER_MODELS = new ArrayList<>();
   private static final ParallelFeatures.Worker MODELS = new ParallelFeatures.Worker("pulsar-rasgos-modelos");
   private static final ParallelFeatures.Worker TAGS = new ParallelFeatures.Worker("pulsar-rasgos-letreros");
   private static final ParallelFeatures.Worker ITEMS = new ParallelFeatures.Worker("pulsar-rasgos-items");
   private static final Set<RenderType> WARM = Collections.newSetFromMap(new ConcurrentHashMap<>());
   private static final LinkedBlockingQueue<Object> INBOX = new LinkedBlockingQueue<>();
   static long diagMain;
   static long diagWait;
   static long diagUpload;
   static long diagPrep;

   private ParallelFeatures() {
   }

   public static boolean enabled() {
      ClientOptions o = ClientOptions.get();
      return !broken && o.enabled("perf") && o.bool("perf.parallelFeatures", true);
   }

   public static void beginFrame() {
      frameActive = enabled();
      PREPARED.clear();
      TEXTS.clear();
      MAIN.clear();
      MAIN_AFTER_MODELS.clear();
      if (frameActive) {
         projection = RenderSystem.getProjectionType();
         if (WARM.size() > 512) {
            WARM.clear();
         }

         for (RenderType type : WARM) {
            PREPARED.put(type, type.prepare());
         }
      }
   }

   public static void prepare(Object group, FeatureFrameContext context, FeatureRendererMap renderers, List<SubmitNode> all) {
      long p0 = System.nanoTime();

      try {
         prepare0(group, context, renderers, all);
      } finally {
         diagPrep = diagPrep + (System.nanoTime() - p0);
      }
   }

   private static void prepare0(Object group, FeatureFrameContext context, FeatureRendererMap renderers, List<SubmitNode> all) {
      PreparedGroupAccessor g = (PreparedGroupAccessor)group;
      FeatureRenderer<?> renderer = renderers.get(g.pulsar$featureType());
      if (!frameActive) {
         FRAME_BUFFERS.remove(renderer);
         g.pulsar$prepare(context, renderers, all);
      } else {
         Runnable task = () -> g.pulsar$prepare(context, renderers, all);
         if (renderer instanceof ModelFeatureRenderer) {
            USED.add(use(renderer));
            MODELS.post(renderer, context, task);
         } else if (renderer instanceof NameTagFeatureRenderer) {
            Font font = context.font();

            for (Object s : g.pulsar$slice(all)) {
               Submit submit = (Submit)s;
               TEXTS.put(submit, NameTagFeatureRendererAccessor.pulsar$prepareText(font, submit));
            }

            USED.add(use(renderer));
            TAGS.post(renderer, context, task);
         } else if (renderer instanceof ItemFeatureRenderer
            || renderer instanceof ShadowFeatureRenderer
            || renderer instanceof FlameFeatureRenderer
            || renderer instanceof LeashFeatureRenderer) {
            USED.add(use(renderer));
            ITEMS.post(renderer, context, task);
         } else if (renderer instanceof CustomFeatureRenderer) {
            FRAME_BUFFERS.remove(renderer);
            MAIN_AFTER_MODELS.add(task);
         } else {
            FRAME_BUFFERS.remove(renderer);
            MAIN.add(task);
         }
      }
   }

   public static void join() {
      if (frameActive) {
         long t0 = System.nanoTime();

         for (Runnable r : MAIN) {
            r.run();
            serve();
         }

         long t1 = System.nanoTime();
         MODELS.await();
         ITEMS.await();
         long t2 = System.nanoTime();

         for (Runnable r : MAIN_AFTER_MODELS) {
            r.run();
            serve();
         }

         long t3 = System.nanoTime();
         TAGS.await();
         long t4 = System.nanoTime();
         diagMain += t1 - t0 + (t3 - t2);
         diagWait += t2 - t1 + (t4 - t3);
         Throwable failure = MODELS.takeFailure();
         if (failure == null) {
            failure = TAGS.takeFailure();
         }

         if (failure == null) {
            failure = ITEMS.takeFailure();
         }

         if (failure != null) {
            broken = true;
            BootstrapLog.info("perf: rasgos en paralelo desactivados: " + failure);
         }

         long u0 = System.nanoTime();

         for (StagedVertexBuffer b : USED) {
            b.upload();
         }

         diagUpload = diagUpload + (System.nanoTime() - u0);
         USED.clear();
         MAIN.clear();
         MAIN_AFTER_MODELS.clear();
      }
   }

   public static StagedVertexBuffer buffer(FeatureFrameContext context, RenderTypeFeatureRenderer<?> renderer) {
      StagedVertexBuffer own = FRAME_BUFFERS.get(renderer);
      return own != null ? own : context.stagedVertexBuffer();
   }

   private static StagedVertexBuffer use(Object renderer) {
      StagedVertexBuffer b = BUFFERS.computeIfAbsent(renderer, r -> new StagedVertexBuffer(() -> "Pulsar rasgos " + r.getClass().getSimpleName(), 4194304));
      FRAME_BUFFERS.put(renderer, b);
      return b;
   }

   public static PreparedRenderType prepared(RenderType type) {
      if (!frameActive) {
         return type.prepare();
      } else if (RenderSystem.isOnRenderThread()) {
         PreparedRenderType p = PREPARED.get(type);
         if (p == null) {
            p = type.prepare();
            PREPARED.put(type, p);
         }

         return p;
      } else {
         PreparedRenderType p = PREPARED.get(type);
         if (p == null) {
            ParallelFeatures.PrepareRequest req = new ParallelFeatures.PrepareRequest(type);
            INBOX.add(req);

            try {
               req.done.await();
            } catch (InterruptedException var4) {
               Thread.currentThread().interrupt();
            }

            p = req.result;
            if (p == null) {
               throw new IllegalStateException("tipo de render sin resolver en un carril: " + type);
            }
         }

         return p;
      }
   }

   private static void serve(ParallelFeatures.PrepareRequest req) {
      try {
         PreparedRenderType p = PREPARED.get(req.type);
         if (p == null) {
            p = req.type.prepare();
            PREPARED.put(req.type, p);
         }

         WARM.add(req.type);
         req.result = p;
      } catch (Throwable var5) {
         BootstrapLog.info("perf: no se pudo resolver " + req.type + ": " + var5);
      } finally {
         req.done.countDown();
      }
   }

   private static void serve() {
      Object e;
      while ((e = INBOX.poll()) != null) {
         if (e instanceof ParallelFeatures.PrepareRequest req) {
            serve(req);
         } else if (e instanceof ParallelFeatures.Worker w) {
            w.finished = true;
         }
      }
   }

   public static String lanes(int frames) {
      if (frames <= 0) {
         return "";
      } else {
         String s = String.format(
            Locale.ROOT,
            " · carriles %.2f/%.2f/%.2f ms · join principal %.2f espera %.2f subida %.2f · prep %.2f",
            MODELS.busyNanos / 1000000.0 / frames,
            ITEMS.busyNanos / 1000000.0 / frames,
            TAGS.busyNanos / 1000000.0 / frames,
            diagMain / 1000000.0 / frames,
            diagWait / 1000000.0 / frames,
            diagUpload / 1000000.0 / frames,
            diagPrep / 1000000.0 / frames
         );
         diagPrep = 0L;
         diagUpload = 0L;
         diagWait = 0L;
         diagMain = 0L;
         MODELS.busyNanos = ITEMS.busyNanos = TAGS.busyNanos = 0L;
         return s;
      }
   }

   public static ProjectionType projectionType() {
      return frameActive && !RenderSystem.isOnRenderThread() ? projection : RenderSystem.getProjectionType();
   }

   public static PreparedText preparedText(Object submit) {
      return TEXTS.isEmpty() ? null : TEXTS.remove(submit);
   }

   public static void endDraw(Iterable<FeatureRenderer<?>> renderers) {
      for (FeatureRenderer<?> r : renderers) {
         StagedVertexBuffer b = BUFFERS.get(r);
         if (b != null) {
            b.endDraw();
         }
      }
   }

   public static void endFrame() {
      for (StagedVertexBuffer b : BUFFERS.values()) {
         b.endFrame();
      }
   }

   public static void close() {
      for (StagedVertexBuffer b : BUFFERS.values()) {
         b.close();
      }

      BUFFERS.clear();
   }

   private static final class PrepareRequest {
      final RenderType type;
      final CountDownLatch done = new CountDownLatch(1);
      volatile PreparedRenderType result;

      PrepareRequest(RenderType type) {
         this.type = type;
      }
   }

   private static final class Worker implements Runnable {
      private final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
      private final Thread thread;
      private volatile Throwable failure;
      volatile long busyNanos;
      private boolean posted;
      boolean finished;

      Worker(String name) {
         this.thread = new Thread(this, name);
         this.thread.setDaemon(true);
         this.thread.start();
      }

      void post(FeatureRenderer<?> renderer, FeatureFrameContext context, Runnable task) {
         this.posted = true;
         this.queue.add(() -> {
            long t0 = System.nanoTime();

            try {
               task.run();
               this.busyNanos = this.busyNanos + (System.nanoTime() - t0);
            } catch (Throwable var7) {
               this.failure = var7;
               pad(renderer, context);
            }
         });
      }

      private static void pad(FeatureRenderer<?> renderer, FeatureFrameContext context) {
         try {
            renderer.prepareGroup(context, List.of(), false);
         } catch (Throwable var3) {
         }
      }

      void await() {
         if (this.posted) {
            this.posted = false;
            this.finished = false;
            this.queue.add(() -> ParallelFeatures.INBOX.add(this));

            try {
               while (!this.finished) {
                  Object e = ParallelFeatures.INBOX.take();
                  if (e instanceof ParallelFeatures.PrepareRequest req) {
                     ParallelFeatures.serve(req);
                  } else if (e instanceof ParallelFeatures.Worker w) {
                     w.finished = true;
                  }
               }
            } catch (InterruptedException var4) {
               Thread.currentThread().interrupt();
               this.failure = var4;
            }
         }
      }

      Throwable takeFailure() {
         Throwable t = this.failure;
         this.failure = null;
         return t;
      }

      @Override
      public void run() {
         while (true) {
            try {
               this.queue.take().run();
            } catch (InterruptedException var2) {
               return;
            } catch (Throwable var3) {
               this.failure = var3;
            }
         }
      }
   }
}
