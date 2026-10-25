package com.metrofico.pulsar.engine.v189.emote;

import com.metrofico.pulsar.api.emote.Emote.Prop;
import com.metrofico.pulsar.api.emote.Emote.Prop.Cube;
import com.metrofico.pulsar.api.emote.Emote.Prop.Mesh;
import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.engine.v189.gfx.Textures189;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Map;

public final class EmoteProps189 {
   private static final Map<Prop, EmoteProps189.Baked> BAKED = new IdentityHashMap<>();

   private EmoteProps189() {
   }

   public static synchronized EmoteProps189.Baked baked(Prop prop) {
      if (BAKED.containsKey(prop)) {
         return BAKED.get(prop);
      } else {
         EmoteProps189.Baked baked = null;

         try {
            Textures189.Texture tex = Textures189.load("emote-prop:" + Arrays.hashCode(prop.texture()), prop.texture());
            if (tex == null) {
               throw new IllegalStateException("textura ilegible");
            }

            if (prop.mesh() != null) {
               baked = bakeMesh(prop.mesh(), tex.id());
            } else {
               bbo holder = new bbo() {};
               holder.t = prop.texWidth();
               holder.u = prop.texHeight();
               bct part = new bct(holder, "prop");
               part.b(prop.texWidth(), prop.texHeight());

               for (Cube c : prop.cubes()) {
                  part.a(Math.round(c.uv()[0]), Math.round(c.uv()[1]));
                  part.a(c.origin()[0], c.origin()[1], c.origin()[2], Math.round(c.size()[0]), Math.round(c.size()[1]), Math.round(c.size()[2]), c.inflate());
               }

               baked = new EmoteProps189.Baked(part, null, null, null, 0, tex.id());
            }
         } catch (Exception var7) {
            ClientOptions.LOG.accept("emote prop " + prop.id() + " inválido: " + var7.getMessage());
         }

         BAKED.put(prop, baked);
         return baked;
      }
   }

   static EmoteProps189.Baked bakeMesh(Mesh m, int texture) {
      int n = m.triangles();
      float[] pos = new float[n * 9];
      float[] uv = new float[n * 6];
      float[] normal = new float[n * 3];
      int out = 0;

      for (int t = 0; t < n; t++) {
         int a = m.idx()[t * 3];
         int b = m.idx()[t * 3 + 1];
         int c = m.idx()[t * 3 + 2];
         float ax = m.pos()[a * 3] / 16.0F;
         float ay = m.pos()[a * 3 + 1] / 16.0F;
         float az = m.pos()[a * 3 + 2] / 16.0F;
         float bx = m.pos()[b * 3] / 16.0F;
         float by = m.pos()[b * 3 + 1] / 16.0F;
         float bz = m.pos()[b * 3 + 2] / 16.0F;
         float cx = m.pos()[c * 3] / 16.0F;
         float cy = m.pos()[c * 3 + 1] / 16.0F;
         float cz = m.pos()[c * 3 + 2] / 16.0F;
         float ux = bx - ax;
         float uy = by - ay;
         float uz = bz - az;
         float vx = cx - ax;
         float vy = cy - ay;
         float vz = cz - az;
         float nx = uy * vz - uz * vy;
         float ny = uz * vx - ux * vz;
         float nz = ux * vy - uy * vx;
         float len = (float)Math.sqrt(nx * nx + ny * ny + nz * nz);
         if (!(len < 1.0E-9F)) {
            int p = out * 9;
            int q = out * 6;
            pos[p] = ax;
            pos[p + 1] = ay;
            pos[p + 2] = az;
            pos[p + 3] = bx;
            pos[p + 4] = by;
            pos[p + 5] = bz;
            pos[p + 6] = cx;
            pos[p + 7] = cy;
            pos[p + 8] = cz;
            uv[q] = m.uv()[a * 2];
            uv[q + 1] = m.uv()[a * 2 + 1];
            uv[q + 2] = m.uv()[b * 2];
            uv[q + 3] = m.uv()[b * 2 + 1];
            uv[q + 4] = m.uv()[c * 2];
            uv[q + 5] = m.uv()[c * 2 + 1];
            normal[out * 3] = nx / len;
            normal[out * 3 + 1] = ny / len;
            normal[out * 3 + 2] = nz / len;
            out++;
         }
      }

      if (out != n) {
         pos = Arrays.copyOf(pos, out * 9);
         uv = Arrays.copyOf(uv, out * 6);
         normal = Arrays.copyOf(normal, out * 3);
      }

      return new EmoteProps189.Baked(null, pos, uv, normal, out, texture);
   }

   public record Baked(bct model, float[] pos, float[] uv, float[] normal, int triangles, int texture) {
   }
}
