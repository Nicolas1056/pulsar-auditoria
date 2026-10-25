package com.metrofico.pulsar.engine.v189.emote;

import com.metrofico.pulsar.api.emote.Emote;
import com.metrofico.pulsar.api.emote.EmoteEvaluator;
import com.metrofico.pulsar.api.emote.EmotePlayer;
import com.metrofico.pulsar.api.emote.Emote.Prop;
import com.metrofico.pulsar.api.emote.EmotePlayer.Active;
import java.util.List;

public final class EmotePropLayer189 implements blb<bet> {
   private static final float[] BUF = new float[9];
   private final bln renderer;

   public EmotePropLayer189(bln renderer) {
      this.renderer = renderer;
   }

   public void render(bet entity, float limbAngle, float limbDistance, float tickDelta, float age, float headYaw, float headPitch, float scale) {
      if (EmotePlayer.anyActive()) {
         Active a = EmotePlayer.active(entity.F());
         if (a != null) {
            List<Prop> props = a.emote.props();
            if (!props.isEmpty()) {
               double elapsed = a.elapsed();
               float w = EmoteEvaluator.weight(elapsed, a.endAt());
               if (!(w <= 0.0F)) {
                  double t = EmoteEvaluator.time(a.emote, elapsed);

                  for (Prop prop : props) {
                     EmoteProps189.Baked baked = EmoteProps189.baked(prop);
                     if (baked != null) {
                        bfl.E();
                        this.attach(prop.bone(), scale);
                        bfl.b(prop.offset()[0] / 16.0F, prop.offset()[1] / 16.0F, prop.offset()[2] / 16.0F);
                        rotateZYX(prop.rotation()[2], prop.rotation()[1], prop.rotation()[0]);
                        float s = (float)prop.scale();
                        if (prop.anim() != null) {
                           int mask = EmoteEvaluator.sample(prop.anim(), t, BUF);
                           if ((mask & 2) != 0) {
                              bfl.b(BUF[3] / 16.0F * w, BUF[4] / 16.0F * w, BUF[5] / 16.0F * w);
                           }

                           if ((mask & 1) != 0) {
                              rotateZYX(BUF[2] * w, BUF[1] * w, BUF[0] * w);
                           }

                           if ((mask & 4) != 0) {
                              s *= 1.0F + (BUF[6] - 1.0F) * w;
                           }
                        }

                        s *= w;
                        bfl.a(s, s, s);
                        bfl.i(baked.texture());
                        bfl.c(1.0F, 1.0F, 1.0F, 1.0F);
                        if (baked.model() != null) {
                           baked.model().a(scale);
                        } else {
                           drawMesh(baked);
                        }

                        bfl.F();
                     }
                  }
               }
            }
         }
      }
   }

   private static void rotateZYX(float z, float y, float x) {
      if (z != 0.0F) {
         bfl.b(z, 0.0F, 0.0F, 1.0F);
      }

      if (y != 0.0F) {
         bfl.b(y, 0.0F, 1.0F, 0.0F);
      }

      if (x != 0.0F) {
         bfl.b(x, 1.0F, 0.0F, 0.0F);
      }
   }

   private static void drawMesh(EmoteProps189.Baked mesh) {
      bfl.p();
      bfx tess = bfx.a();
      bfd b = tess.c();
      b.a(4, bms.j);
      float[] pos = mesh.pos();
      float[] uv = mesh.uv();
      float[] nrm = mesh.normal();

      for (int tri = 0; tri < mesh.triangles(); tri++) {
         int o = tri * 9;
         int q = tri * 6;
         int n = tri * 3;

         for (int v = 0; v < 3; v++) {
            b.b(pos[o + v * 3], pos[o + v * 3 + 1], pos[o + v * 3 + 2]).a(uv[q + v * 2], uv[q + v * 2 + 1]).c(nrm[n], nrm[n + 1], nrm[n + 2]).d();
         }
      }

      tess.b();
      bfl.o();
   }

   private void attach(String bone, float scale) {
      bbr model = this.renderer.g();

      bct base = switch (bone) {
         case "head" -> model.e;
         case "body", "body_lower" -> model.g;
         case "right_arm", "right_forearm" -> model.h;
         case "left_arm", "left_forearm" -> model.i;
         case "right_leg", "right_shin" -> model.j;
         case "left_leg", "left_shin" -> model.k;
         default -> null;
      };
      if (base != null) {
         base.c(scale);
         if (Emote.PARENT.containsKey(bone)) {
            MeshSubdivider189.Joints j = MeshSubdivider189.joints(model);
            if (j != null) {
               for (int i = 0; i < MeshSubdivider189.NAMES.length; i++) {
                  if (MeshSubdivider189.NAMES[i].equals(bone) && j.parts[i] != null) {
                     j.parts[i].c(scale);
                  }
               }
            }
         }
      }
   }

   public boolean b() {
      return false;
   }
}
