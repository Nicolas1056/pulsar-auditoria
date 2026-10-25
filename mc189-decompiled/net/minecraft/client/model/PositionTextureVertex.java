package net.minecraft.client.model;

import net.minecraft.util.Vec3;

public class PositionTextureVertex {
   public Vec3 a;
   public float b;
   public float c;

   public PositionTextureVertex(float var1, float var2, float var3, float var4, float var5) {
      this(new Vec3(var1, var2, var3), var4, var5);
   }

   public PositionTextureVertex a(float var1, float var2) {
      return new PositionTextureVertex(this, var1, var2);
   }

   public PositionTextureVertex(PositionTextureVertex var1, float var2, float var3) {
      this.a = var1.a;
      this.b = var2;
      this.c = var3;
   }

   public PositionTextureVertex(Vec3 var1, float var2, float var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }
}
