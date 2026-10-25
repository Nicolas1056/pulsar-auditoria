package com.metrofico.pulsar.engine.v26x.emote;

import com.metrofico.pulsar.api.emote.Emote.Prop;
import com.metrofico.pulsar.api.emote.Emote.Prop.Cube;
import com.metrofico.pulsar.api.emote.Emote.Prop.Mesh;
import com.metrofico.pulsar.bootstrap.BootstrapLog;
import com.mojang.blaze3d.platform.NativeImage;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.Identifier;

public final class EmoteProps {
   private static final Map<Prop, EmoteProps.Baked> BAKED = new IdentityHashMap<>();
   private static final Map<Integer, Identifier> TEXTURES = new HashMap<>();

   private EmoteProps() {
   }

   public static synchronized EmoteProps.Baked baked(Prop prop) {
      if (BAKED.containsKey(prop)) {
         return BAKED.get(prop);
      } else {
         EmoteProps.Baked baked = null;

         try {
            if (prop.mesh() != null) {
               baked = new EmoteProps.Baked(null, bakeMesh(prop.mesh()), texture(prop.texture()));
               BAKED.put(prop, baked);
               return baked;
            }

            MeshDefinition mesh = new MeshDefinition();
            CubeListBuilder cubes = CubeListBuilder.create();

            for (Cube c : prop.cubes()) {
               cubes.texOffs(Math.round(c.uv()[0]), Math.round(c.uv()[1]))
                  .addBox(c.origin()[0], c.origin()[1], c.origin()[2], c.size()[0], c.size()[1], c.size()[2], new CubeDeformation(c.inflate()));
            }

            mesh.getRoot().addOrReplaceChild("prop", cubes, PartPose.ZERO);
            baked = new EmoteProps.Baked(
               new Model<AvatarRenderState>(LayerDefinition.create(mesh, prop.texWidth(), prop.texHeight()).bakeRoot(), RenderTypes::entityCutout) {},
               null,
               texture(prop.texture())
            );
         } catch (Exception var6) {
            BootstrapLog.info("emote prop " + prop.id() + " inválido: " + var6.getMessage());
         }

         BAKED.put(prop, baked);
         return baked;
      }
   }

   static EmoteProps.BakedMesh bakeMesh(Mesh m) {
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

      return new EmoteProps.BakedMesh(pos, uv, normal, out);
   }

   private static Identifier texture(byte[] png) throws Exception {
      int hash = Arrays.hashCode(png);
      Identifier cached = TEXTURES.get(hash);
      if (cached != null) {
         return cached;
      } else {
         NativeImage image = NativeImage.read(new ByteArrayInputStream(png));
         Identifier id = Identifier.fromNamespaceAndPath("pulsar", "emote/prop/" + Integer.toHexString(hash) + "-" + TEXTURES.size());
         Minecraft.getInstance().getTextureManager().register(id, new DynamicTexture(() -> "pulsar emote prop", image));
         TEXTURES.put(hash, id);
         return id;
      }
   }

   public record Baked(Model<AvatarRenderState> model, EmoteProps.BakedMesh mesh, Identifier texture) {
   }

   public record BakedMesh(float[] pos, float[] uv, float[] normal, int triangles) {
   }
}
