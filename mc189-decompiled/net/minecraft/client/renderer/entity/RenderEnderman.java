package net.minecraft.client.renderer.entity;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.renderer.entity.layers.LayerEndermanEyes;
import net.minecraft.client.renderer.entity.layers.LayerHeldBlock;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.ResourceLocation;

public class RenderEnderman extends RenderLiving<EntityEnderman> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/enderman/enderman.png");
   private ModelEnderman e;
   private Random j = new Random();

   public RenderEnderman(RenderManager var1) {
      super(var1, new ModelEnderman(0.0F), 0.5F);
      this.e = (ModelEnderman)super.f;
      this.a(new LayerEndermanEyes(this));
      this.a(new LayerHeldBlock(this));
   }

   public void a(EntityEnderman var1, double var2, double var4, double var6, float var8, float var9) {
      this.e.a = var1.cm().c().t() != Material.a;
      this.e.b = var1.co();
      if (var1.co()) {
         double var10 = 0.02;
         var2 += this.j.nextGaussian() * var10;
         var6 += this.j.nextGaussian() * var10;
      }

      super.a(var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation a(EntityEnderman var1) {
      return a;
   }
}
