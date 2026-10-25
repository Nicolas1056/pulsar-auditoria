package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.perf.FrameFlags;
import com.metrofico.pulsar.engine.v189.perf.GpuOcclusion189;
import com.metrofico.pulsar.engine.v189.perf.SectionBuffer;
import com.metrofico.pulsar.engine.v189.perf.SectionData189;
import com.metrofico.pulsar.engine.v189.perf.SectionDirt189;
import com.metrofico.pulsar.engine.v189.perf.SectionMatrix189;
import com.metrofico.pulsar.engine.v189.perf.SpriteCulling189;
import com.metrofico.pulsar.engine.v189.perf.TerrainRegions189;
import java.nio.FloatBuffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bht.class})
public abstract class BuiltChunkMixin implements SectionData189 {
   @Unique
   private final TerrainRegions189.Slot[] pulsar$slots = new TerrainRegions189.Slot[4];
   @Unique
   private int pulsar$layers;
   @Unique
   private long pulsar$visibility;
   @Unique
   private int pulsar$index = -1;
   @Unique
   private int pulsar$entityStamp;
   @Unique
   private Object pulsar$info;
   @Unique
   private boolean pulsar$pending;
   @Shadow
   private bmt[] m;
   @Shadow
   @Final
   private int k;
   @Shadow
   @Final
   private FloatBuffer l;

   @Override
   public boolean pulsar$pending() {
      return this.pulsar$pending;
   }

   @Override
   public void pulsar$pending(boolean pending) {
      this.pulsar$pending = pending;
   }

   @Override
   public int pulsar$index() {
      return this.pulsar$index;
   }

   @Override
   public void pulsar$index(int index) {
      this.pulsar$index = index;
   }

   @Override
   public Object pulsar$info() {
      return this.pulsar$info;
   }

   @Override
   public void pulsar$info(Object info) {
      this.pulsar$info = info;
   }

   @Override
   public int pulsar$entityStamp() {
      return this.pulsar$entityStamp;
   }

   @Override
   public void pulsar$entityStamp(int stamp) {
      this.pulsar$entityStamp = stamp;
   }

   @Override
   public TerrainRegions189.Slot[] pulsar$slots() {
      return this.pulsar$slots;
   }

   @Override
   public int pulsar$layers() {
      return this.pulsar$layers;
   }

   @Override
   public long pulsar$visibility() {
      return this.pulsar$visibility;
   }

   @Inject(
      method = {"a(Lbhq;)V"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$compiled(bhq helper, CallbackInfo ci) {
      int layers = 0;
      long vis = 0L;
      if (helper != bhq.a) {
         adf[] all = adf.values();

         for (int l = 0; l < all.length && l < 4; l++) {
            if (!helper.b(all[l])) {
               layers |= 1 << l;
            }

            if (helper.d(all[l])) {
               layers |= 16 << l;
            }
         }

         if (!helper.b().isEmpty()) {
            layers |= 256;
         }

         cq[] dirs = cq.values();

         for (int a = 0; a < 6; a++) {
            for (int b = 0; b < 6; b++) {
               if (helper.a(dirs[a], dirs[b])) {
                  vis |= 1L << a * 6 + b;
               }
            }
         }
      }

      this.pulsar$layers = layers;
      this.pulsar$visibility = vis;
   }

   @Inject(
      method = {"h"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$reset(CallbackInfo ci) {
      this.pulsar$layers = 0;
      this.pulsar$visibility = 0L;
   }

   @Override
   public int hashCode() {
      return this.k * -1640531527;
   }

   @Inject(
      method = {"<init>"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$tag(adm world, bfr renderer, cj pos, int index, CallbackInfo ci) {
      if (this.m != null) {
         for (int i = 0; i < this.m.length; i++) {
            if (this.m[i] != null) {
               ((SectionBuffer)this.m[i]).pulsar$owner((bht)this, i);
            }
         }
      }
   }

   @Inject(
      method = {"a(Lcj;)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$moved(cj pos, CallbackInfo ci) {
      TerrainRegions189.free((bht)this);
      GpuOcclusion189.forget((bht)this);
   }

   @Inject(
      method = {"a(Ladf;FFFLbfd;Lbhq;)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$layerCompiled(adf layer, float x, float y, float z, bfd buffer, bhq helper, CallbackInfo ci) {
      if (SpriteCulling189.enabled()) {
         SpriteCulling189.sectionCompiled((SpriteCulling189.SectionExt)helper);
      }
   }

   @Inject(
      method = {"m"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$matrixKnown(CallbackInfo ci) {
      if (FrameFlags.sectionMatrix && SectionMatrix189.MATRIX.fill(this.l)) {
         ci.cancel();
      }
   }

   @Redirect(
      method = {"<init>"},
      at = @At(
         value = "INVOKE",
         target = "Lavd;h(I)Ljava/nio/FloatBuffer;"
      ),
      require = 1
   )
   private FloatBuffer pulsar$matrixBuffer(int size) {
      return SectionMatrix189.buffer(size);
   }

   @Inject(
      method = {"m"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$matrixLearned(CallbackInfo ci) {
      SectionMatrix189.MATRIX.learn(this.l);
   }

   @Inject(
      method = {"b(FFFLbhn;)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$built(float x, float y, float z, bhn task, CallbackInfo ci) {
      SectionDirt189.built();
   }

   @Inject(
      method = {"a()V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$deleted(CallbackInfo ci) {
      TerrainRegions189.free((bht)this);
      GpuOcclusion189.forget((bht)this);
   }
}
