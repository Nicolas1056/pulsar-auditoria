package com.metrofico.pulsar.engine.v189.mixin;

import com.google.common.collect.Sets;
import com.metrofico.pulsar.api.camera.CameraDirector;
import com.metrofico.pulsar.api.hud.modules.Freecam;
import com.metrofico.pulsar.api.hud.modules.Zoom;
import com.metrofico.pulsar.api.perf.FrameFlags;
import com.metrofico.pulsar.api.perf.FramePacer;
import com.metrofico.pulsar.engine.v189.gfx.PlayerSkins189;
import com.metrofico.pulsar.engine.v189.perf.ChunkUpdates189;
import com.metrofico.pulsar.engine.v189.perf.Clouds189;
import com.metrofico.pulsar.engine.v189.perf.EntityCache189;
import com.metrofico.pulsar.engine.v189.perf.FireBatch189;
import com.metrofico.pulsar.engine.v189.perf.FovWatch189;
import com.metrofico.pulsar.engine.v189.perf.GpuOcclusion189;
import com.metrofico.pulsar.engine.v189.perf.ItemLod189;
import com.metrofico.pulsar.engine.v189.perf.LabelBatch189;
import com.metrofico.pulsar.engine.v189.perf.NearBuilds189;
import com.metrofico.pulsar.engine.v189.perf.OcclusionCuller189;
import com.metrofico.pulsar.engine.v189.perf.PerfStats189;
import com.metrofico.pulsar.engine.v189.perf.RenderDistanceAccess;
import com.metrofico.pulsar.engine.v189.perf.SectionData189;
import com.metrofico.pulsar.engine.v189.perf.SectionDirt189;
import com.metrofico.pulsar.engine.v189.perf.SignBoards189;
import com.metrofico.pulsar.engine.v189.perf.SignText189;
import com.metrofico.pulsar.engine.v189.perf.SpriteCulling189;
import com.metrofico.pulsar.engine.v189.perf.TerrainRegions189;
import com.metrofico.pulsar.engine.v189.perf.Visibility189;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({bfr.class})
public abstract class WorldRendererMixin implements RenderDistanceAccess {
   @Shadow
   private int P;
   @Shadow
   @Final
   private ave h;
   @Shadow
   @Final
   private bmj i;
   @Shadow
   private bdb k;
   @Shadow
   private int w;
   @Shadow
   private boolean ac;
   @Shadow
   @Final
   private static jy e;
   @Shadow
   private Set<bht> l;
   @Unique
   private boolean pulsar$sameQueue;
   @Unique
   private long pulsar$lastReorder;
   @Shadow
   private double Z;
   @Shadow
   private double aa;
   @Shadow
   private double ab;
   @Shadow
   private bho N;
   @Shadow
   private bfh O;
   @Shadow
   private List<?> m;
   private static final List<bht> pulsar$occlusionBatch = new ArrayList<>();

   @Override
   public int pulsar$renderDistance() {
      return this.P;
   }

   @Shadow
   private boolean a(cj pos, bht chunk) {
      throw new AssertionError();
   }

   @Inject(
      method = {"c(FI)V"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$cachedClouds(float tickDelta, int pass, CallbackInfo ci) {
      if (pass == 2 && Clouds189.enabled()) {
         pk cam = this.h.ac();
         float camY = (float)(cam.Q + (cam.t - cam.Q) * tickDelta);
         double camX = cam.p + (cam.s - cam.p) * tickDelta;
         double camZ = cam.r + (cam.u - cam.r) * tickDelta;
         this.i.a(e);
         aui c = this.k.e(tickDelta);
         Clouds189.render(tickDelta, camX, camY, camZ, this.w, this.k.t.f(), (float)c.a, (float)c.b, (float)c.c);
         ci.cancel();
      }
   }

   @Inject(
      method = {"b(FI)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$cloudsBegin(float tickDelta, int pass, CallbackInfo ci) {
      PerfStats189.begin(9);
   }

   @Inject(
      method = {"b(FI)V"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$cloudsEnd(float tickDelta, int pass, CallbackInfo ci) {
      PerfStats189.end(9);
   }

   @Inject(
      method = {"a(Lpk;Lbia;F)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$frame(pk camera, bia view, float tickDelta, CallbackInfo ci) {
      PerfStats189.begin(1);
      bcy network = ave.A().u();
      if (this.k != null && network != null) {
         PlayerSkins189.retainPresentPlayers(this.k, network);
      }

      EntityCache189.worldPass(true);
      FrameFlags.refresh();
      SignBoards189.frame();
      ave mc = ave.A();
      if (FrameFlags.perf && mc.f != null && camera != null) {
         aui off = auz.a();
         double ex = camera.p + (camera.s - camera.p) * tickDelta + (off != null ? off.a : 0.0);
         double ey = camera.q + (camera.t - camera.q) * tickDelta + (off != null ? off.b : 0.0);
         double ez = camera.r + (camera.u - camera.r) * tickDelta + (off != null ? off.c : 0.0);
         OcclusionCuller189.frame(ex, ey, ez, mc.f);
         GpuOcclusion189.frame(ex, ey, ez);
         ItemLod189.beginFrame(mc.f, ex, ey, ez);
      } else {
         ItemLod189.beginFrame(null, 0.0, 0.0, 0.0);
      }
   }

   @Inject(
      method = {"a(Lpk;Lbia;F)V"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$entitiesEnd(pk camera, bia view, float tickDelta, CallbackInfo ci) {
      EntityCache189.flushStart();
      FireBatch189.flush();
      EntityCache189.flushMid();
      EntityCache189.worldPass(false);
      LabelBatch189.flush();
      EntityCache189.flushEnd();
      PerfStats189.end(1);
   }

   @Redirect(
      method = {"a(Lpk;DLbia;IZ)V"},
      at = @At(
         value = "INVOKE",
         target = "Ljava/util/Set;contains(Ljava/lang/Object;)Z"
      ),
      require = 1
   )
   private boolean pulsar$pendingRebuild(Set<?> pending, Object chunk) {
      return this.pulsar$sameQueue && pending == this.l && chunk instanceof SectionData189 sd
         ? sd.pulsar$pending()
         : !pending.isEmpty() && pending.contains(chunk);
   }

   @Redirect(
      method = {"a(Lpk;DLbia;IZ)V"},
      at = @At(
         value = "INVOKE",
         target = "Lcom/google/common/collect/Sets;newLinkedHashSet()Ljava/util/LinkedHashSet;"
      ),
      require = 1
   )
   private LinkedHashSet<?> pulsar$queue() {
      long now = System.nanoTime();
      this.pulsar$sameQueue = FrameFlags.asyncNearChunks
         && this.l instanceof LinkedHashSet
         && this.l.size() >= 256
         && now - this.pulsar$lastReorder < 50000000L;
      if (this.pulsar$sameQueue) {
         return (LinkedHashSet<?>)this.l;
      } else {
         this.pulsar$lastReorder = now;
         return Sets.newLinkedHashSet();
      }
   }

   @Redirect(
      method = {"a(Lpk;DLbia;IZ)V"},
      at = @At(
         value = "INVOKE",
         target = "Ljava/util/Set;add(Ljava/lang/Object;)Z"
      ),
      require = 1
   )
   private boolean pulsar$queueAdd(Set<Object> set, Object o) {
      if (set == this.l && o instanceof SectionData189 sd) {
         if (this.pulsar$sameQueue && sd.pulsar$pending()) {
            return false;
         }

         sd.pulsar$pending(true);
      }

      return set.add(o);
   }

   @Redirect(
      method = {"a(Lpk;DLbia;IZ)V"},
      at = @At(
         value = "INVOKE",
         target = "Ljava/util/Set;addAll(Ljava/util/Collection;)Z"
      ),
      require = 1
   )
   private boolean pulsar$queueAddAll(Set<Object> set, Collection<?> old) {
      return set != old && set.addAll((Collection<? extends Object>)old);
   }

   @Redirect(
      method = {"a(J)V"},
      at = @At(
         value = "INVOKE",
         target = "Lbht;a(Z)V"
      ),
      require = 1
   )
   private void pulsar$dequeued(bht chunk, boolean needsRebuild) {
      ((SectionData189)chunk).pulsar$pending(false);
      chunk.a(needsRebuild);
   }

   @Redirect(
      method = {"a(Lpk;Lbia;F)V"},
      at = @At(
         value = "FIELD",
         target = "Lbfr;m:Ljava/util/List;",
         opcode = 180,
         ordinal = 0
      ),
      require = 1
   )
   private List<?> pulsar$sectionsWithEntities(bfr self) {
      return Visibility189.owns(this.m) ? Visibility189.withEntities(this.k, self) : this.m;
   }

   @Redirect(
      method = {"a(Lpk;Lbia;F)V"},
      at = @At(
         value = "FIELD",
         target = "Lbfr;m:Ljava/util/List;",
         opcode = 180,
         ordinal = 1
      ),
      require = 1
   )
   private List<?> pulsar$sectionsWithBlockEntities(bfr self) {
      return Visibility189.owns(this.m) ? Visibility189.withBlockEntities() : this.m;
   }

   @Inject(
      method = {"a(Ladf;DILpk;)I"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$ownLayer(adf layer, double tickDelta, int pass, pk entity, CallbackInfoReturnable<Integer> cir) {
      if (TerrainRegions189.enabled() && bqs.f() && Visibility189.owns(this.m) && this.O instanceof bfy) {
         PerfStats189.begin(0);
         bht[] sections = Visibility189.sections();
         int count = Visibility189.sectionCount();
         avc.a();
         boolean translucent = layer == adf.d;
         int ordinal = layer.ordinal();
         if (ordinal == 0 && SpriteCulling189.enabled()) {
            SpriteCulling189.sectionsVisible(sections, count);
         }

         if (translucent) {
            double dx = entity.s - this.Z;
            double dy = entity.t - this.aa;
            double dz = entity.u - this.ab;
            if (dx * dx + dy * dy + dz * dz > 1.0) {
               this.Z = entity.s;
               this.aa = entity.t;
               this.ab = entity.u;
               int started = 16 << ordinal;
               int j = 0;

               for (int k = 0; k < count && j < 15; k++) {
                  if ((((SectionData189)sections[k]).pulsar$layers() & started) != 0) {
                     j++;
                     this.N.c(sections[k]);
                  }
               }
            }
         }

         AbstractChunkRenderManagerAccessor view = (AbstractChunkRenderManagerAccessor)this.O;
         this.h.o.i();
         GL11.glEnableClientState(32884);
         bqs.l(bqs.q);
         GL11.glEnableClientState(32888);
         bqs.l(bqs.r);
         GL11.glEnableClientState(32888);
         bqs.l(bqs.q);
         GL11.glEnableClientState(32886);
         int drawn = TerrainRegions189.drawLayer(ordinal, sections, count, translucent, view.pulsar$viewX(), view.pulsar$viewY(), view.pulsar$viewZ());
         bqs.g(bqs.R, 0);
         bfl.G();
         GL11.glDisableClientState(32884);
         GL11.glDisableClientState(32886);
         bfl.G();
         bqs.l(bqs.q);
         GL11.glDisableClientState(32888);
         bqs.l(bqs.r);
         GL11.glDisableClientState(32888);
         bqs.l(bqs.q);
         this.h.o.h();
         PerfStats189.end(0);
         if (layer == adf.c && pass == 0 && entity != null && GpuOcclusion189.enabled()) {
            pulsar$occlusionBatch.clear();

            for (int kx = 0; kx < count; kx++) {
               pulsar$occlusionBatch.add(sections[kx]);
            }

            double vx = entity.p + (entity.s - entity.p) * tickDelta;
            double vy = entity.q + (entity.t - entity.q) * tickDelta;
            double vz = entity.r + (entity.u - entity.r) * tickDelta;
            GpuOcclusion189.issue(pulsar$occlusionBatch, vx, vy, vz);
         }

         cir.setReturnValue(drawn);
      }
   }

   @Inject(
      method = {"a(Ladf;DILpk;)I"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$terrainBegin(adf layer, double tickDelta, int pass, pk entity, CallbackInfoReturnable<Integer> cir) {
      PerfStats189.begin(0);
   }

   @Inject(
      method = {"a(Ladf;DILpk;)I"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$terrainEnd(adf layer, double tickDelta, int pass, pk entity, CallbackInfoReturnable<Integer> cir) {
      PerfStats189.end(0);
      if (layer == adf.c && pass == 0 && entity != null && GpuOcclusion189.enabled()) {
         pulsar$occlusionBatch.clear();

         for (Object info : this.m) {
            pulsar$occlusionBatch.add(((ChunkInfoAccessor)info).pulsar$chunk());
         }

         aui off = auz.a();
         double vx = entity.p + (entity.s - entity.p) * tickDelta;
         double vy = entity.q + (entity.t - entity.q) * tickDelta;
         double vz = entity.r + (entity.u - entity.r) * tickDelta;
         GpuOcclusion189.issue(pulsar$occlusionBatch, vx, vy, vz);
      }
   }

   @Inject(
      method = {"a(FI)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$skyBegin(float tickDelta, int pass, CallbackInfo ci) {
      PerfStats189.begin(3);
   }

   @Inject(
      method = {"a(FI)V"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$skyEnd(float tickDelta, int pass, CallbackInfo ci) {
      PerfStats189.end(3);
   }

   @Inject(
      method = {"a(J)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$chunksBegin(long deadline, CallbackInfo ci) {
      PerfStats189.begin(4);
      ChunkUpdates189.frame();
   }

   @Inject(
      method = {"a(J)V"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$chunksEnd(long deadline, CallbackInfo ci) {
      PerfStats189.end(4);
   }

   @Redirect(
      method = {"a(Lpk;Lbia;F)V"},
      at = @At(
         value = "INVOKE",
         target = "Lbhc;a(Lakw;FI)V"
      ),
      require = 3
   )
   private void pulsar$blockEntity(bhc dispatcher, akw be, float tickDelta, int destroyStage, pk viewer, bia camera, float partial) {
      if (destroyStage < 0 && be instanceof aln && SignBoards189.enabled() && SignBoards189.board((aln)be)) {
         cj pos = be.v();
         if (!((SignBoards189.SignSection)be).pulsar$textBaked()
            && !SignText189.blank((SignText189.SignExt)be, ((aln)be).a)
            && !SignText189.tooFar(pos.n() + 0.5 - bhc.b, pos.o() + 0.5 - bhc.c, pos.p() + 0.5 - bhc.d)) {
            SignBoards189.textOnly(true);

            try {
               this.pulsar$cullAndRender(dispatcher, be, tickDelta, destroyStage, camera);
            } finally {
               SignBoards189.textOnly(false);
            }
         }
      } else {
         this.pulsar$cullAndRender(dispatcher, be, tickDelta, destroyStage, camera);
      }
   }

   private void pulsar$cullAndRender(bhc dispatcher, akw be, float tickDelta, int destroyStage, bia camera) {
      if (FrameFlags.cullBlockEntities && be.s() <= 4096.0) {
         PerfStats189.BLOCK_ENTITIES_SEEN.incrementAndGet();
         cj pos = be.v();
         if (camera != null && !camera.a(new aug(pos.n() - 1, pos.o() - 1, pos.p() - 1, pos.n() + 2, pos.o() + 2, pos.p() + 2))) {
            PerfStats189.BLOCK_ENTITIES_CULLED.incrementAndGet();
            return;
         }

         double ex = OcclusionCuller189.eyeX();
         double ey = OcclusionCuller189.eyeY();
         double ez = OcclusionCuller189.eyeZ();
         double dx = pos.n() + 0.5 - ex;
         double dy = pos.o() + 0.5 - ey;
         double dz = pos.p() + 0.5 - ez;
         if (!Zoom.active() && dx * dx + dy * dy + dz * dz > FrameFlags.beDistanceSq * FramePacer.blockEntityScaleSq()) {
            PerfStats189.BLOCK_ENTITIES_CULLED.incrementAndGet();
            return;
         }

         aug box = new aug(pos.n(), pos.o(), pos.p(), pos.n() + 1, pos.o() + 1, pos.p() + 1);
         if (OcclusionCuller189.candidate(box) && OcclusionCuller189.hidden(OcclusionCuller189.blockKey(pos.n(), pos.o(), pos.p()), box)) {
            PerfStats189.BLOCK_ENTITIES_CULLED.incrementAndGet();
            return;
         }
      }

      dispatcher.a(be, tickDelta, destroyStage);
   }

   @Inject(
      method = {"a(Lcj;)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$blockChanged(cj pos, CallbackInfo ci) {
      NearBuilds189.blockChanged(pos);
      SectionDirt189.block();
   }

   @Inject(
      method = {"b(Lcj;)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$lightChanged(cj pos, CallbackInfo ci) {
      SectionDirt189.light();
   }

   @Inject(
      method = {"a(IIIIII)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$regionChanged(int x0, int y0, int z0, int x1, int y1, int z1, CallbackInfo ci) {
      SectionDirt189.region();
   }

   @Redirect(
      method = {"a(Lpk;DLbia;IZ)V"},
      at = @At(
         value = "INVOKE",
         target = "Lbfr;a(Lcj;Lbht;)Z"
      ),
      require = 1
   )
   private boolean pulsar$nearBuild(bfr self, cj pos, bht chunk) {
      return !this.a(pos, chunk) ? false : !FrameFlags.asyncNearChunks || NearBuilds189.urgent(chunk);
   }

   @Inject(
      method = {"b(IIIIII)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$dirty(int x0, int y0, int z0, int x1, int y1, int z1, CallbackInfo ci) {
      SignBoards189.invalidate(x0, y0, z0, x1, y1, z1);
      Visibility189.blockChanged();
      OcclusionCuller189.invalidateBlocks(x0, y0, z0, x1, y1, z1);
   }

   @Inject(
      method = {"a()V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$reload(CallbackInfo ci) {
      Clouds189.clear();
      NearBuilds189.clear();
      Visibility189.clear();
      LabelBatch189.clear();
      EntityCache189.clear();
      SignBoards189.clear();
      TerrainRegions189.clear();
      GpuOcclusion189.clear();
   }

   @Inject(
      method = {"a(Lbdb;)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$world(bdb world, CallbackInfo ci) {
      OcclusionCuller189.worldChanged(world);
   }

   @Inject(
      method = {"a(Lpk;DLbia;IZ)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$visibilityBegin(pk entity, double tickDelta, bia camera, int frame, boolean spectator, CallbackInfo ci) {
      PerfStats189.begin(8);
      if (FovWatch189.changedSinceLastFrame()) {
         this.ac = true;
         if (FovWatch189.openedWide()) {
            GpuOcclusion189.revealAll();
         }
      }
   }

   @Inject(
      method = {"a(Lpk;DLbia;IZ)V"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$visibilityEnd(pk entity, double tickDelta, bia camera, int frame, boolean spectator, CallbackInfo ci) {
      PerfStats189.end(8);
   }

   @Redirect(
      method = {"a(Lpk;DLbia;IZ)V"},
      at = @At(
         value = "FIELD",
         target = "Lpk;z:F",
         opcode = 180
      ),
      require = 2
   )
   private float pulsar$freecamPitch(pk e) {
      return Freecam.active() ? Freecam.pitch() : e.z;
   }

   @Redirect(
      method = {"a(Lpk;DLbia;IZ)V"},
      at = @At(
         value = "FIELD",
         target = "Lpk;y:F",
         opcode = 180
      ),
      require = 2
   )
   private float pulsar$freecamYaw(pk e) {
      return Freecam.active() ? Freecam.yaw() : e.y;
   }

   @Redirect(
      method = {"a(Lpk;DLbia;IZ)V"},
      at = @At(
         value = "FIELD",
         target = "Lbfr;ac:Z",
         opcode = 180,
         ordinal = 1
      ),
      require = 1
   )
   private boolean pulsar$visibility(bfr self, pk entity, double tickDelta, bia camera, int frame, boolean spectator) {
      if (this.ac && Visibility189.enabled()) {
         boolean culling = this.h.G && !CameraDirector.get().active();

         try {
            if (Visibility189.compute(self, entity, tickDelta, camera, frame, spectator, culling)) {
               this.ac = false;
               return false;
            }
         } catch (RuntimeException var10) {
            Visibility189.disable(var10);
         }

         return true;
      } else {
         return this.ac;
      }
   }

   @Redirect(
      method = {"a(Lpk;DLbia;IZ)V"},
      at = @At(
         value = "FIELD",
         target = "Lave;G:Z",
         opcode = 180
      ),
      require = 1
   )
   private boolean pulsar$culling(ave mc) {
      return mc.G && !CameraDirector.get().active();
   }

   @Redirect(
      method = {"a(Lpk;Lbia;F)V"},
      at = @At(
         value = "FIELD",
         target = "Lavh;aB:I",
         opcode = 180
      ),
      require = 2
   )
   private int pulsar$perspective(avh options) {
      CameraDirector d = CameraDirector.get();
      return d.active() && !d.hideSelf() ? 1 : options.aB;
   }
}
