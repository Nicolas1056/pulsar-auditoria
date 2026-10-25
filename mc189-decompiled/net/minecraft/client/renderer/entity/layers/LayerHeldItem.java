package net.minecraft.client.renderer.entity.layers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class LayerHeldItem implements LayerRenderer<EntityLivingBase> {
   private final RendererLivingEntity<?> a;

   public LayerHeldItem(RendererLivingEntity<?> var1) {
      this.a = var1;
   }

   @Override
   public void a(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      ItemStack var9 = var1.bA();
      if (var9 != null) {
         GlStateManager.E();
         if (this.a.b().r) {
            float var10 = 0.5F;
            GlStateManager.b(0.0F, 0.625F, 0.0F);
            GlStateManager.b(-20.0F, -1.0F, 0.0F, 0.0F);
            GlStateManager.a(var10, var10, var10);
         }

         ((ModelBiped)this.a.b()).a(0.0625F);
         GlStateManager.b(-0.0625F, 0.4375F, 0.0625F);
         if (var1 instanceof EntityPlayer && ((EntityPlayer)var1).bG != null) {
            var9 = new ItemStack(Items.aR, 0);
         }

         Item var13 = var9.b();
         Minecraft var11 = Minecraft.A();
         if (var13 instanceof ItemBlock && Block.a(var13).b() == 2) {
            GlStateManager.b(0.0F, 0.1875F, -0.3125F);
            GlStateManager.b(20.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.b(45.0F, 0.0F, 1.0F, 0.0F);
            float var12 = 0.375F;
            GlStateManager.a(-var12, -var12, var12);
         }

         if (var1.av()) {
            GlStateManager.b(0.0F, 0.203125F, 0.0F);
         }

         var11.ah().a(var1, var9, ItemCameraTransforms.TransformType.b);
         GlStateManager.F();
      }
   }

   @Override
   public boolean b() {
      return false;
   }
}
