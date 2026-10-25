package net.minecraft.realms;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;

public class RealmsSliderButton extends RealmsButton {
   public float value = 1.0F;
   public boolean sliding;
   private final float minValue;
   private final float maxValue;
   private int steps;

   public RealmsSliderButton(int var1, int var2, int var3, int var4, int var5, int var6) {
      this(var1, var2, var3, var4, var6, 0, 1.0F, var5);
   }

   public RealmsSliderButton(int var1, int var2, int var3, int var4, int var5, int var6, float var7, float var8) {
      super(var1, var2, var3, var4, 20, "");
      this.minValue = var7;
      this.maxValue = var8;
      this.value = this.toPct(var6);
      this.getProxy().j = this.getMessage();
   }

   public String getMessage() {
      return "";
   }

   public float toPct(float var1) {
      return MathHelper.a((this.clamp(var1) - this.minValue) / (this.maxValue - this.minValue), 0.0F, 1.0F);
   }

   public float toValue(float var1) {
      return this.clamp(this.minValue + (this.maxValue - this.minValue) * MathHelper.a(var1, 0.0F, 1.0F));
   }

   public float clamp(float var1) {
      var1 = this.clampSteps(var1);
      return MathHelper.a(var1, this.minValue, this.maxValue);
   }

   protected float clampSteps(float var1) {
      if (this.steps > 0) {
         var1 = this.steps * Math.round(var1 / this.steps);
      }

      return var1;
   }

   @Override
   public int getYImage(boolean var1) {
      return 0;
   }

   @Override
   public void renderBg(int var1, int var2) {
      if (this.getProxy().m) {
         if (this.sliding) {
            this.value = (float)(var1 - (this.getProxy().h + 4)) / (this.getProxy().b() - 8);
            this.value = MathHelper.a(this.value, 0.0F, 1.0F);
            float var3 = this.toValue(this.value);
            this.clicked(var3);
            this.value = this.toPct(var3);
            this.getProxy().j = this.getMessage();
         }

         Minecraft.A().P().a(WIDGETS_LOCATION);
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         this.blit(this.getProxy().h + (int)(this.value * (this.getProxy().b() - 8)), this.getProxy().i, 0, 66, 4, 20);
         this.blit(this.getProxy().h + (int)(this.value * (this.getProxy().b() - 8)) + 4, this.getProxy().i, 196, 66, 4, 20);
      }
   }

   @Override
   public void clicked(int var1, int var2) {
      this.value = (float)(var1 - (this.getProxy().h + 4)) / (this.getProxy().b() - 8);
      this.value = MathHelper.a(this.value, 0.0F, 1.0F);
      this.clicked(this.toValue(this.value));
      this.getProxy().j = this.getMessage();
      this.sliding = true;
   }

   public void clicked(float var1) {
   }

   @Override
   public void released(int var1, int var2) {
      this.sliding = false;
   }
}
