package com.metrofico.pulsar.engine.v26x.emote;

import com.metrofico.pulsar.api.emote.Emote;
import com.metrofico.pulsar.api.emote.EmoteEvaluator;
import com.metrofico.pulsar.api.emote.EmotePlayer;
import com.metrofico.pulsar.api.emote.Emote.Prop;
import com.metrofico.pulsar.api.emote.EmotePlayer.Active;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import org.joml.Quaternionf;

public final class EmotePropLayer extends RenderLayer<AvatarRenderState, PlayerModel> {
   private static final float DEG = (float) (Math.PI / 180.0);
   private static final float[] BUF = new float[9];
   private static final Quaternionf Q = new Quaternionf();

   public EmotePropLayer(RenderLayerParent<AvatarRenderState, PlayerModel> parent) {
      super(parent);
   }

   public void submit(PoseStack pose, SubmitNodeCollector collector, int light, AvatarRenderState state, float yRot, float xRot) {
      if (EmotePlayer.anyActive()) {
         Active a = EmotePlayer.active(state.id);
         if (a != null) {
            List<Prop> props = a.emote.props();
            if (!props.isEmpty()) {
               double elapsed = a.elapsed();
               float w = EmoteEvaluator.weight(elapsed, a.endAt());
               if (!(w <= 0.0F)) {
                  double t = EmoteEvaluator.time(a.emote, elapsed);

                  for (Prop prop : props) {
                     EmoteProps.Baked baked = EmoteProps.baked(prop);
                     if (baked != null) {
                        pose.pushPose();
                        this.attach(pose, prop.bone());
                        pose.translate(prop.offset()[0] / 16.0F, prop.offset()[1] / 16.0F, prop.offset()[2] / 16.0F);
                        if (prop.rotation()[0] != 0.0F || prop.rotation()[1] != 0.0F || prop.rotation()[2] != 0.0F) {
                           pose.mulPose(
                              Q.rotationZYX(
                                 prop.rotation()[2] * (float) (Math.PI / 180.0),
                                 prop.rotation()[1] * (float) (Math.PI / 180.0),
                                 prop.rotation()[0] * (float) (Math.PI / 180.0)
                              )
                           );
                        }

                        float scale = (float)prop.scale();
                        if (prop.anim() != null) {
                           int mask = EmoteEvaluator.sample(prop.anim(), t, BUF);
                           if ((mask & 2) != 0) {
                              pose.translate(BUF[3] / 16.0F * w, BUF[4] / 16.0F * w, BUF[5] / 16.0F * w);
                           }

                           if ((mask & 1) != 0) {
                              pose.mulPose(
                                 Q.rotationZYX(
                                    BUF[2] * (float) (Math.PI / 180.0) * w, BUF[1] * (float) (Math.PI / 180.0) * w, BUF[0] * (float) (Math.PI / 180.0) * w
                                 )
                              );
                           }

                           if ((mask & 4) != 0) {
                              scale *= 1.0F + (BUF[6] - 1.0F) * w;
                           }
                        }

                        scale *= w;
                        pose.scale(scale, scale, scale);
                        if (baked.mesh() != null) {
                           submitMesh(baked.mesh(), baked.texture(), pose, collector, light, state);
                        } else {
                           renderColoredCutoutModel(baked.model(), baked.texture(), pose, collector, light, state, -1, 20);
                        }

                        pose.popPose();
                     }
                  }
               }
            }
         }
      }
   }

   private static void submitMesh(
      EmoteProps.BakedMesh mesh, Identifier texture, PoseStack pose, SubmitNodeCollector collector, int light, AvatarRenderState state
   ) {
      int overlay = LivingEntityRenderer.getOverlayCoords(state, 0.0F);
      collector.order(20)
         .submitCustomGeometry(
            pose,
            RenderTypes.entityCutout(texture),
            (p, consumer) -> {
               float[] pos = mesh.pos();
               float[] uv = mesh.uv();
               float[] nrm = mesh.normal();

               for (int t = 0; t < mesh.triangles(); t++) {
                  int o = t * 9;
                  int q = t * 6;
                  int n = t * 3;

                  for (int k = 0; k < 4; k++) {
                     int v = Math.min(k, 2);
                     consumer.addVertex(p, pos[o + v * 3], pos[o + v * 3 + 1], pos[o + v * 3 + 2])
                        .setColor(-1)
                        .setUv(uv[q + v * 2], uv[q + v * 2 + 1])
                        .setOverlay(overlay)
                        .setLight(light)
                        .setNormal(p, nrm[n], nrm[n + 1], nrm[n + 2]);
                  }
               }
            }
         );
   }

   private void attach(PoseStack pose, String bone) {
      PlayerModel model = (PlayerModel)this.getParentModel();

      ModelPart base = switch (bone) {
         case "head" -> model.head;
         case "body", "body_lower" -> model.body;
         case "right_arm", "right_forearm" -> model.rightArm;
         case "left_arm", "left_forearm" -> model.leftArm;
         case "right_leg", "right_shin" -> model.rightLeg;
         case "left_leg", "left_shin" -> model.leftLeg;
         default -> null;
      };
      if (base != null) {
         base.translateAndRotate(pose);
         if (Emote.PARENT.containsKey(bone) && base.hasChild(bone)) {
            base.getChild(bone).translateAndRotate(pose);
         }
      }
   }
}
