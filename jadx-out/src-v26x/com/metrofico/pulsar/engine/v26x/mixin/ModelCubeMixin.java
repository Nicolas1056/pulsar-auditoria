package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.emote.JointBend;
import com.metrofico.pulsar.engine.v26x.emote.JointLink;
import com.metrofico.pulsar.engine.v26x.perf.EntityBake;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import net.minecraft.client.model.geom.ModelPart.Cube;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Cube.class})
abstract class ModelCubeMixin implements JointLink.Host {
   @Unique
   private volatile EntityBake.Tables pulsar$tables;
   @Unique
   private JointLink pulsar$joint;

   @Inject(
      method = {"compile"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$fastCompile(Pose pose, VertexConsumer consumer, int light, int overlay, int color, CallbackInfo ci) {
      JointLink link = this.pulsar$joint;
      boolean bent = link != null && JointBend.bent(link.joint.xRot, link.joint.yRot, link.joint.zRot);
      if (bent || EntityBake.enabled() && consumer instanceof BufferBuilderAccessor) {
         EntityBake.Tables t = this.pulsar$tables;
         if (t == null) {
            t = new EntityBake.Tables((Cube)this);
            this.pulsar$tables = t;
         }

         if (bent) {
            EntityBake.compileBent(t, link, pose, consumer, light, overlay, color);
            ci.cancel();
         } else if (EntityBake.compile(t, pose, (BufferBuilderAccessor)consumer, light, overlay, color)) {
            ci.cancel();
         }
      }
   }

   @Override
   public JointLink pulsar$joint() {
      return this.pulsar$joint;
   }

   @Override
   public void pulsar$joint(JointLink link) {
      this.pulsar$joint = link;
   }
}
