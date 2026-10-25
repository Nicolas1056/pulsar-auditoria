package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;

public class LayerWolfCollar implements LayerRenderer<EntityWolf> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/wolf/wolf_collar.png");
   private final RenderWolf b;

   public LayerWolfCollar(RenderWolf var1) {
      this.b = var1;
   }

   public void a(EntityWolf var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (var1.cl() && !var1.ax()) {
         this.b.a(a);
         EnumDyeColor var9 = EnumDyeColor.b(var1.cw().a());
         float[] var10 = EntitySheep.a(var9);
         GlStateManager.c(var10[0], var10[1], var10[2]);
         this.b.b().a(var1, var2, var3, var5, var6, var7, var8);
      }
   }

   @Override
   public boolean b() {
      return true;
   }
}
