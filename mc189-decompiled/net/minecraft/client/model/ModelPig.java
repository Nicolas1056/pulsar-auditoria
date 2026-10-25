package net.minecraft.client.model;

public class ModelPig extends ModelQuadruped {
   public ModelPig() {
      this(0.0F);
   }

   public ModelPig(float var1) {
      super(6, var1);
      this.a.a(16, 16).a(-2.0F, 0.0F, -9.0F, 4, 3, 1, var1);
      this.g = 4.0F;
   }
}
