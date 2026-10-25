package net.minecraft.client.renderer.entity.layers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelWitch;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderWitch;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class LayerHeldItemWitch implements LayerRenderer<EntityWitch> {
   private final RenderWitch a;

   public LayerHeldItemWitch(RenderWitch var1) {
      this.a = var1;
   }

   public void a(EntityWitch var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      ItemStack var9 = var1.bA();
      if (var9 != null) {
         GlStateManager.c(1.0F, 1.0F, 1.0F);
         GlStateManager.E();
         if (this.a.b().r) {
            GlStateManager.b(0.0F, 0.625F, 0.0F);
            GlStateManager.b(-20.0F, -1.0F, 0.0F, 0.0F);
            float var10 = 0.5F;
            GlStateManager.a(var10, var10, var10);
         }

         ((ModelWitch)this.a.b()).f.c(0.0625F);
         GlStateManager.b(-0.0625F, 0.53125F, 0.21875F);
         Item var13 = var9.b();
         Minecraft var11 = Minecraft.A();
         if (var13 instanceof ItemBlock && var11.ae().a(Block.a(var13), var9.i())) {
            GlStateManager.b(0.0F, 0.0625F, -0.25F);
            GlStateManager.b(30.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.b(-5.0F, 0.0F, 1.0F, 0.0F);
            float var16 = 0.375F;
            GlStateManager.a(var16, -var16, var16);
         } else if (var13 == Items.f) {
            GlStateManager.b(0.0F, 0.125F, -0.125F);
            GlStateManager.b(-45.0F, 0.0F, 1.0F, 0.0F);
            float var12 = 0.625F;
            GlStateManager.a(var12, -var12, var12);
            GlStateManager.b(-100.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.b(-20.0F, 0.0F, 1.0F, 0.0F);
         } else if (var13.w_()) {
            if (var13.e()) {
               GlStateManager.b(180.0F, 0.0F, 0.0F, 1.0F);
               GlStateManager.b(0.0F, -0.0625F, 0.0F);
            }

            this.a.C_();
            GlStateManager.b(0.0625F, -0.125F, 0.0F);
            float var14 = 0.625F;
            GlStateManager.a(var14, -var14, var14);
            GlStateManager.b(0.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.b(0.0F, 0.0F, 1.0F, 0.0F);
         } else {
            GlStateManager.b(0.1875F, 0.1875F, 0.0F);
            float var15 = 0.875F;
            GlStateManager.a(var15, var15, var15);
            GlStateManager.b(-20.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.b(-60.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.b(-30.0F, 0.0F, 0.0F, 1.0F);
         }

         GlStateManager.b(-15.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.b(40.0F, 0.0F, 0.0F, 1.0F);
         var11.ah().a(var1, var9, ItemCameraTransforms.TransformType.b);
         GlStateManager.F();
      }
   }

   @Override
   public boolean b() {
      return false;
   }
}
