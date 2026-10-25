package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class RenderRabbit extends RenderLiving<EntityRabbit> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/rabbit/brown.png");
   private static final ResourceLocation e = new ResourceLocation("textures/entity/rabbit/white.png");
   private static final ResourceLocation j = new ResourceLocation("textures/entity/rabbit/black.png");
   private static final ResourceLocation k = new ResourceLocation("textures/entity/rabbit/gold.png");
   private static final ResourceLocation l = new ResourceLocation("textures/entity/rabbit/salt.png");
   private static final ResourceLocation m = new ResourceLocation("textures/entity/rabbit/white_splotched.png");
   private static final ResourceLocation n = new ResourceLocation("textures/entity/rabbit/toast.png");
   private static final ResourceLocation o = new ResourceLocation("textures/entity/rabbit/caerbannog.png");

   public RenderRabbit(RenderManager var1, ModelBase var2, float var3) {
      super(var1, var2, var3);
   }

   protected ResourceLocation a(EntityRabbit var1) {
      String var2 = EnumChatFormatting.a(var1.e_());
      if (var2 != null && var2.equals("Toast")) {
         return n;
      } else {
         switch (var1.cn()) {
            case 0:
            default:
               return a;
            case 1:
               return e;
            case 2:
               return j;
            case 3:
               return m;
            case 4:
               return k;
            case 5:
               return l;
            case 99:
               return o;
         }
      }
   }
}
