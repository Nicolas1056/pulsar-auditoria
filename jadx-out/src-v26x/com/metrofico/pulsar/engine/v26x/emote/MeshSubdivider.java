package com.metrofico.pulsar.engine.v26x.emote;

import com.metrofico.pulsar.bootstrap.BootstrapLog;
import com.metrofico.pulsar.engine.v26x.mixin.CubeDefinitionAccessor;
import com.metrofico.pulsar.engine.v26x.mixin.CubeListBuilderAccessor;
import com.metrofico.pulsar.engine.v26x.mixin.LayerDefinitionAccessor;
import com.metrofico.pulsar.engine.v26x.mixin.PartDefinitionAccessor;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.core.Direction;

public final class MeshSubdivider {
   private static final Map<String, String> JOINTS = Map.of(
      "body", "body_lower", "right_arm", "right_forearm", "left_arm", "left_forearm", "right_leg", "right_shin", "left_leg", "left_shin"
   );
   private static final float HALF = 6.0F;

   private MeshSubdivider() {
   }

   public static Map<ModelLayerLocation, LayerDefinition> apply(Map<ModelLayerLocation, LayerDefinition> roots) {
      Map<ModelLayerLocation, LayerDefinition> out = new LinkedHashMap<>(roots.size());
      int replaced = 0;

      for (Entry<ModelLayerLocation, LayerDefinition> e : roots.entrySet()) {
         ModelLayerLocation loc = e.getKey();
         String model = loc.model().getPath();
         boolean player = model.equals("player") || model.equals("player_slim");
         String var9 = loc.layer();

         boolean armor = switch (var9) {
            case "helmet", "chestplate", "leggings", "boots" -> true;
            default -> false;
         };
         if (player && (loc.layer().equals("main") || armor)) {
            out.put(loc, subdivide(e.getValue(), armor ? 32 : 64));
            replaced++;
         } else {
            out.put(loc, e.getValue());
         }
      }

      BootstrapLog.info("emotes v2: malla del jugador subdividida en " + replaced + " capas");
      return out;
   }

   public static LayerDefinition subdivide(LayerDefinition source, int texHeight) {
      MeshDefinition mesh = new MeshDefinition();
      copyChildren(((LayerDefinitionAccessor)source).pulsar$mesh().getRoot(), mesh.getRoot(), true);
      return LayerDefinition.create(mesh, 64, texHeight);
   }

   private static void copyChildren(PartDefinition src, PartDefinition dst, boolean root) {
      for (Entry<String, PartDefinition> child : src.getChildren()) {
         String name = child.getKey();
         PartDefinition part = child.getValue();
         if (root && JOINTS.containsKey(name)) {
            split(name, part, dst);
         } else {
            PartDefinition copied = dst.addOrReplaceChild(
               name, builderWith(((PartDefinitionAccessor)part).pulsar$cubes()), ((PartDefinitionAccessor)part).pulsar$pose()
            );
            copyChildren(part, copied, false);
         }
      }
   }

   private static void split(String name, PartDefinition part, PartDefinition dstRoot) {
      List<CubeDefinition> cubes = ((PartDefinitionAccessor)part).pulsar$cubes();
      CubeDefinition base = null;

      for (CubeDefinition c : cubes) {
         if (((CubeDefinitionAccessor)c).pulsar$dimensions().y() == 12.0F) {
            base = c;
            break;
         }
      }

      if (base == null) {
         PartDefinition copied = dstRoot.addOrReplaceChild(name, builderWith(cubes), ((PartDefinitionAccessor)part).pulsar$pose());
         copyChildren(part, copied, false);
      } else {
         CubeDefinitionAccessor b = (CubeDefinitionAccessor)base;
         float jointY = b.pulsar$origin().y() + 6.0F;
         float cx = b.pulsar$origin().x() + b.pulsar$dimensions().x() / 2.0F;
         CubeListBuilder upper = CubeListBuilder.create();
         CubeListBuilder lower = CubeListBuilder.create();
         splitCubes(cubes, jointY, cx, upper, lower);
         PartDefinition newPart = dstRoot.addOrReplaceChild(name, upper, ((PartDefinitionAccessor)part).pulsar$pose());
         PartDefinition joint = newPart.addOrReplaceChild(JOINTS.get(name), lower, PartPose.offset(cx, jointY, 0.0F));

         for (Entry<String, PartDefinition> child : part.getChildren()) {
            PartDefinition overlay = child.getValue();
            List<CubeDefinition> oCubes = ((PartDefinitionAccessor)overlay).pulsar$cubes();
            boolean spans = oCubes.stream().anyMatch(cxx -> ((CubeDefinitionAccessor)cxx).pulsar$dimensions().y() == 12.0F);
            if (!spans) {
               PartDefinition copied = newPart.addOrReplaceChild(child.getKey(), builderWith(oCubes), ((PartDefinitionAccessor)overlay).pulsar$pose());
               copyChildren(overlay, copied, false);
            } else {
               CubeListBuilder oUpper = CubeListBuilder.create();
               CubeListBuilder oLower = CubeListBuilder.create();
               splitCubes(oCubes, jointY, cx, oUpper, oLower);
               newPart.addOrReplaceChild(child.getKey(), oUpper, ((PartDefinitionAccessor)overlay).pulsar$pose());
               joint.addOrReplaceChild(child.getKey() + "_lower", oLower, PartPose.ZERO);
            }
         }
      }
   }

   private static void splitCubes(List<CubeDefinition> cubes, float jointY, float cx, CubeListBuilder upper, CubeListBuilder lower) {
      for (CubeDefinition c : cubes) {
         CubeDefinitionAccessor a = (CubeDefinitionAccessor)c;
         if (a.pulsar$dimensions().y() != 12.0F) {
            ((CubeListBuilderAccessor)upper).pulsar$cubes().add(c);
         } else {
            float u = a.pulsar$texCoord().u();
            float v = a.pulsar$texCoord().v();
            float ox = a.pulsar$origin().x();
            float oy = a.pulsar$origin().y();
            float oz = a.pulsar$origin().z();
            float dx = a.pulsar$dimensions().x();
            float dz = a.pulsar$dimensions().z();
            Set<Direction> faces = a.pulsar$visibleFaces();
            add(upper, c, u, v, ox, oy, oz, dx, 6.0F, dz, without(faces, Direction.UP));
            add(lower, c, u, v + 6.0F, ox - cx, 0.0F, oz, dx, 6.0F, dz, without(faces, Direction.UP, Direction.DOWN));
            if (faces.contains(Direction.UP)) {
               add(lower, c, u, v, ox - cx, 6.0F, oz, dx, 0.0F, dz, EnumSet.of(Direction.UP));
            }
         }
      }
   }

   private static void add(
      CubeListBuilder builder, CubeDefinition like, float u, float v, float x, float y, float z, float dx, float dy, float dz, Set<Direction> faces
   ) {
      if (!faces.isEmpty()) {
         CubeDefinitionAccessor a = (CubeDefinitionAccessor)like;
         ((CubeListBuilderAccessor)builder)
            .pulsar$cubes()
            .add(
               new CubeDefinition(
                  a.pulsar$comment(), u, v, x, y, z, dx, dy, dz, a.pulsar$grow(), a.pulsar$mirror(), a.pulsar$texScale().u(), a.pulsar$texScale().v(), faces
               )
            );
      }
   }

   private static CubeListBuilder builderWith(List<CubeDefinition> cubes) {
      CubeListBuilder builder = CubeListBuilder.create();
      ((CubeListBuilderAccessor)builder).pulsar$cubes().addAll(cubes);
      return builder;
   }

   private static Set<Direction> without(Set<Direction> faces, Direction... remove) {
      EnumSet<Direction> out = EnumSet.noneOf(Direction.class);
      out.addAll(faces);

      for (Direction d : remove) {
         out.remove(d);
      }

      return out;
   }
}
