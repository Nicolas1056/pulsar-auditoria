package net.minecraft.client.renderer.entity;

import java.util.Random;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderEntityItem extends Render<EntityItem> {
   private final RenderItem a;
   private Random e = new Random();

   public RenderEntityItem(RenderManager var1, RenderItem var2) {
      super(var1);
      this.a = var2;
      this.c = 0.15F;
      this.d = 0.75F;
   }

   private int a(EntityItem var1, double var2, double var4, double var6, float var8, IBakedModel var9) {
      ItemStack var10 = var1.l();
      Item var11 = var10.b();
      if (var11 == null) {
         return 0;
      } else {
         boolean var12 = var9.c();
         int var13 = this.a(var10);
         float var14 = 0.25F;
         float var15 = MathHelper.a((var1.o() + var8) / 10.0F + var1.a) * 0.1F + 0.1F;
         float var16 = var9.f().b(ItemCameraTransforms.TransformType.f).d.y;
         GlStateManager.b((float)var2, (float)var4 + var15 + 0.25F * var16, (float)var6);
         if (var12 || this.b.g != null) {
            float var17 = ((var1.o() + var8) / 20.0F + var1.a) * (180.0F / (float)Math.PI);
            GlStateManager.b(var17, 0.0F, 1.0F, 0.0F);
         }

         if (!var12) {
            float var20 = -0.0F * (var13 - 1) * 0.5F;
            float var18 = -0.0F * (var13 - 1) * 0.5F;
            float var19 = -0.046875F * (var13 - 1) * 0.5F;
            GlStateManager.b(var20, var18, var19);
         }

         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         return var13;
      }
   }

   private int a(ItemStack var1) {
      byte var2 = 1;
      if (var1.b > 48) {
         var2 = 5;
      } else if (var1.b > 32) {
         var2 = 4;
      } else if (var1.b > 16) {
         var2 = 3;
      } else if (var1.b > 1) {
         var2 = 2;
      }

      return var2;
   }

   public void a(EntityItem var1, double var2, double var4, double var6, float var8, float var9) {
      ItemStack var10 = var1.l();
      this.e.setSeed(187L);
      boolean var11 = false;
      if (this.c(var1)) {
         this.b.a.b(this.a(var1)).b(false, false);
         var11 = true;
      }

      GlStateManager.B();
      GlStateManager.a(516, 0.1F);
      GlStateManager.l();
      GlStateManager.a(770, 771, 1, 0);
      GlStateManager.E();
      IBakedModel var12 = this.a.a().a(var10);
      int var13 = this.a(var1, var2, var4, var6, var9, var12);

      for (int var14 = 0; var14 < var13; var14++) {
         if (var12.c()) {
            GlStateManager.E();
            if (var14 > 0) {
               float var15 = (this.e.nextFloat() * 2.0F - 1.0F) * 0.15F;
               float var16 = (this.e.nextFloat() * 2.0F - 1.0F) * 0.15F;
               float var17 = (this.e.nextFloat() * 2.0F - 1.0F) * 0.15F;
               GlStateManager.b(var15, var16, var17);
            }

            GlStateManager.a(0.5F, 0.5F, 0.5F);
            var12.f().a(ItemCameraTransforms.TransformType.f);
            this.a.a(var10, var12);
            GlStateManager.F();
         } else {
            GlStateManager.E();
            var12.f().a(ItemCameraTransforms.TransformType.f);
            this.a.a(var10, var12);
            GlStateManager.F();
            float var18 = var12.f().o.d.x;
            float var19 = var12.f().o.d.y;
            float var20 = var12.f().o.d.z;
            GlStateManager.b(0.0F * var18, 0.0F * var19, 0.046875F * var20);
         }
      }

      GlStateManager.F();
      GlStateManager.C();
      GlStateManager.k();
      this.c(var1);
      if (var11) {
         this.b.a.b(this.a(var1)).a();
      }

      super.a(var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation a(EntityItem var1) {
      return TextureMap.g;
   }
}
