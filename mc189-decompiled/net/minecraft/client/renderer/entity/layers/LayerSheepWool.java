package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;

public class LayerSheepWool implements LayerRenderer<EntitySheep> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
   private final RenderSheep b;
   private final ModelSheep1 c = new ModelSheep1();

   public LayerSheepWool(RenderSheep var1) {
      this.b = var1;
   }

   public void a(EntitySheep var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (!var1.cm() && !var1.ax()) {
         this.b.a(a);
         if (var1.l_() && "jeb_".equals(var1.aM())) {
            byte var17 = 25;
            int var10 = var1.W / 25 + var1.F();
            int var11 = EnumDyeColor.values().length;
            int var12 = var10 % var11;
            int var13 = (var10 + 1) % var11;
            float var14 = (var1.W % 25 + var4) / 25.0F;
            float[] var15 = EntitySheep.a(EnumDyeColor.b(var12));
            float[] var16 = EntitySheep.a(EnumDyeColor.b(var13));
            GlStateManager.c(
               var15[0] * (1.0F - var14) + var16[0] * var14, var15[1] * (1.0F - var14) + var16[1] * var14, var15[2] * (1.0F - var14) + var16[2] * var14
            );
         } else {
            float[] var9 = EntitySheep.a(var1.cl());
            GlStateManager.c(var9[0], var9[1], var9[2]);
         }

         this.c.a(this.b.b());
         this.c.a(var1, var2, var3, var4);
         this.c.a(var1, var2, var3, var5, var6, var7, var8);
      }
   }

   @Override
   public boolean b() {
      return true;
   }
}
