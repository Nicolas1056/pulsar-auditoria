package com.metrofico.pulsar.api.emote;

import java.util.List;
import java.util.Map;
import java.util.Set;

public record Emote(
   String id,
   String name,
   String author,
   double duration,
   boolean loop,
   boolean cancelOnMove,
   Emote.Camera camera,
   Map<String, Emote.Bone> bones,
   List<Emote.Prop> props,
   List<Emote.Sound> sounds
) {
   public static final Set<String> BONES = Set.of(
      "head", "body", "right_arm", "left_arm", "right_leg", "left_leg", "body_lower", "right_forearm", "left_forearm", "right_shin", "left_shin", "root"
   );
   public static final Map<String, String> PARENT = Map.of(
      "body_lower", "body", "right_forearm", "right_arm", "left_forearm", "left_arm", "right_shin", "right_leg", "left_shin", "left_leg"
   );
   public static final List<String> BONE_ORDER = List.of(
      "head", "body", "right_arm", "left_arm", "right_leg", "left_leg", "body_lower", "right_forearm", "left_forearm", "right_shin", "left_shin", "root"
   );
   public static final int ROOT_INDEX = BONE_ORDER.indexOf("root");
   public static final double MAX_DURATION = 30.0;
   public static final int MAX_KEYFRAMES = 2000;
   public static final int MAX_BYTES = 262144;
   public static final double MAX_DEGREES = 720.0;
   public static final double MAX_OFFSET = 32.0;
   public static final double MIN_SCALE = 0.05;
   public static final double MAX_SCALE = 4.0;
   public static final double MAX_ROOT_OFFSET = 160.0;
   public static final int MAX_PROPS = 3;
   public static final int MAX_PROP_CUBES = 48;
   public static final int MAX_TEXTURE_BYTES = 32768;
   public static final int MAX_TEXTURE_SIZE = 128;
   public static final int MAX_PROP_TRIANGLES = 1500;
   public static final int MAX_MESH_TEXTURE_BYTES = 65536;
   public static final int MAX_MESH_TEXTURE_SIZE = 256;
   public static final int MAX_SOUNDS = 4;
   public static final int MAX_SOUND_BYTES = 256000;

   public Emote.Bone bone(String name) {
      return this.bones.get(name);
   }

   public boolean animates(String bone) {
      Emote.Bone b = this.bones.get(bone);
      return b != null && !b.isEmpty();
   }

   public int keyCount() {
      int n = 0;

      for (Emote.Bone b : this.bones.values()) {
         n += b.keyCount();
      }

      return n;
   }

   public record Bone(List<Emote.Key> rot, List<Emote.Key> pos, List<Emote.Key> scale) {
      public boolean isEmpty() {
         return this.rot.isEmpty() && this.pos.isEmpty() && this.scale.isEmpty();
      }

      public int keyCount() {
         return this.rot.size() + this.pos.size() + this.scale.size();
      }
   }

   public static enum Camera {
      FRONT,
      BACK,
      NONE;
   }

   public record Key(double t, float x, float y, float z, Easing easing) {
      public Key(double t, float x, float y, float z, Easing easing) {
         if (easing == null) {
            easing = Easing.LINEAR;
         }

         this.t = t;
         this.x = x;
         this.y = y;
         this.z = z;
         this.easing = easing;
      }
   }

   public record Prop(
      String id,
      String bone,
      float[] offset,
      float[] rotation,
      double scale,
      List<Emote.Prop.Cube> cubes,
      byte[] texture,
      int texWidth,
      int texHeight,
      Emote.Bone anim,
      Emote.Prop.Mesh mesh
   ) {
      public Prop(
         String id,
         String bone,
         float[] offset,
         float[] rotation,
         double scale,
         List<Emote.Prop.Cube> cubes,
         byte[] texture,
         int texWidth,
         int texHeight,
         Emote.Bone anim
      ) {
         this(id, bone, offset, rotation, scale, cubes, texture, texWidth, texHeight, anim, null);
      }

      public record Cube(float[] origin, float[] size, float[] uv, float inflate) {
      }

      public record Mesh(float[] pos, float[] uv, int[] idx) {
         public int vertices() {
            return this.pos.length / 3;
         }

         public int triangles() {
            return this.idx.length / 3;
         }
      }
   }

   public record Sound(String sha256, double at, float volume, float pitch) {
   }
}
