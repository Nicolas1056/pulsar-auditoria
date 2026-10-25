package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.entity.monster.EntitySlime;

public class LayerSlimeGel implements LayerRenderer<EntitySlime> {
   private final RenderSlime a;
   private final ModelBase b = new ModelSlime(0);

   public LayerSlimeGel(RenderSlime var1) {
      this.a = var1;
   }

   public void a(EntitySlime var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (!var1.ax()) {
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.z();
         GlStateManager.l();
         GlStateManager.b(770, 771);
         this.b.a(this.a.b());
         this.b.a(var1, var2, var3, var5, var6, var7, var8);
         GlStateManager.k();
         GlStateManager.A();
      }
   }

   @Override
   public boolean b() {
      return true;
   }
}
