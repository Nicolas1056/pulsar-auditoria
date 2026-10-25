package com.metrofico.pulsar.engine.v26x.emote;

import com.metrofico.pulsar.engine.v26x.mixin.ModelPartAccessor;
import java.util.List;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart.Cube;

public final class JointLink {
   public final ModelPart joint;
   public final boolean upper;
   public final float px;
   public final float py;
   public final float pz;

   private JointLink(ModelPart joint, boolean upper, float px, float py, float pz) {
      this.joint = joint;
      this.upper = upper;
      this.px = px;
      this.py = py;
      this.pz = pz;
   }

   public static void link(ModelPart parent, String overlay, ModelPart joint) {
      List<Cube> own = ((ModelPartAccessor)joint).pulsar$cubes();

      for (int i = 0; i < own.size(); i++) {
         if (((JointLink.Host)own.get(i)).pulsar$joint() != null) {
            return;
         }
      }

      PartPose pivot = joint.getInitialPose();
      JointLink upper = new JointLink(joint, true, pivot.x() / 16.0F, pivot.y() / 16.0F, pivot.z() / 16.0F);
      JointLink lower = new JointLink(joint, false, 0.0F, 0.0F, 0.0F);
      linkCubes(parent, upper, pivot.y(), true);
      if (parent.hasChild(overlay) && isZero(parent.getChild(overlay).getInitialPose())) {
         linkCubes(parent.getChild(overlay), upper, pivot.y(), true);
      }

      linkCubes(joint, lower, 0.0F, false);
      String lowerOverlay = overlay + "_lower";
      if (joint.hasChild(lowerOverlay) && isZero(joint.getChild(lowerOverlay).getInitialPose())) {
         linkCubes(joint.getChild(lowerOverlay), lower, 0.0F, false);
      }
   }

   private static void linkCubes(ModelPart part, JointLink link, float ringY, boolean upper) {
      for (Cube c : ((ModelPartAccessor)part).pulsar$cubes()) {
         boolean touches = c.maxY > c.minY && Math.abs((upper ? c.maxY : c.minY) - ringY) < 0.001F;
         if (touches) {
            ((JointLink.Host)c).pulsar$joint(link);
         }
      }
   }

   private static boolean isZero(PartPose p) {
      return p.x() == 0.0F && p.y() == 0.0F && p.z() == 0.0F && p.xRot() == 0.0F && p.yRot() == 0.0F && p.zRot() == 0.0F;
   }

   public interface Host {
      JointLink pulsar$joint();

      void pulsar$joint(JointLink var1);
   }
}
