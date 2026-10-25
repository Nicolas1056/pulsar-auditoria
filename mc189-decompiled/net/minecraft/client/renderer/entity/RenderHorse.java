package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.ResourceLocation;

public class RenderHorse extends RenderLiving<EntityHorse> {
   private static final Map<String, ResourceLocation> a = Maps.newHashMap();
   private static final ResourceLocation e = new ResourceLocation("textures/entity/horse/horse_white.png");
   private static final ResourceLocation j = new ResourceLocation("textures/entity/horse/mule.png");
   private static final ResourceLocation k = new ResourceLocation("textures/entity/horse/donkey.png");
   private static final ResourceLocation l = new ResourceLocation("textures/entity/horse/horse_zombie.png");
   private static final ResourceLocation m = new ResourceLocation("textures/entity/horse/horse_skeleton.png");

   public RenderHorse(RenderManager var1, ModelHorse var2, float var3) {
      super(var1, var2, var3);
   }

   protected void a(EntityHorse var1, float var2) {
      float var3 = 1.0F;
      int var4 = var1.cl();
      if (var4 == 1) {
         var3 *= 0.87F;
      } else if (var4 == 2) {
         var3 *= 0.92F;
      }

      GlStateManager.a(var3, var3, var3);
      super.a(var1, var2);
   }

   protected ResourceLocation a(EntityHorse var1) {
      if (!var1.cJ()) {
         switch (var1.cl()) {
            case 0:
            default:
               return e;
            case 1:
               return k;
            case 2:
               return j;
            case 3:
               return l;
            case 4:
               return m;
         }
      } else {
         return this.b(var1);
      }
   }

   private ResourceLocation b(EntityHorse var1) {
      String var2 = var1.cL();
      if (!var1.cK()) {
         return null;
      } else {
         ResourceLocation var3 = a.get(var2);
         if (var3 == null) {
            var3 = new ResourceLocation(var2);
            Minecraft.A().P().a(var3, new LayeredTexture(var1.cM()));
            a.put(var2, var3);
         }

         return var3;
      }
   }
}
