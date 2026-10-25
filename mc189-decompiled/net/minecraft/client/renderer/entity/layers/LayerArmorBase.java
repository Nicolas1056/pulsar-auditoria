package net.minecraft.client.renderer.entity.layers;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class LayerArmorBase<T extends ModelBase> implements LayerRenderer<EntityLivingBase> {
   protected static final ResourceLocation b = new ResourceLocation("textures/misc/enchanted_item_glint.png");
   protected T c;
   protected T d;
   private final RendererLivingEntity<?> a;
   private float e = 1.0F;
   private float f = 1.0F;
   private float g = 1.0F;
   private float h = 1.0F;
   private boolean i;
   private static final Map<String, ResourceLocation> j = Maps.newHashMap();

   public LayerArmorBase(RendererLivingEntity<?> var1) {
      this.a = var1;
      this.a();
   }

   @Override
   public void a(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      this.a(var1, var2, var3, var4, var5, var6, var7, var8, 4);
      this.a(var1, var2, var3, var4, var5, var6, var7, var8, 3);
      this.a(var1, var2, var3, var4, var5, var6, var7, var8, 2);
      this.a(var1, var2, var3, var4, var5, var6, var7, var8, 1);
   }

   @Override
   public boolean b() {
      return false;
   }

   private void a(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9) {
      ItemStack var10 = this.a(var1, var9);
      if (var10 != null && var10.b() instanceof ItemArmor) {
         ItemArmor var11 = (ItemArmor)var10.b();
         ModelBase var12 = this.a(var9);
         var12.a(this.a.b());
         var12.a(var1, var2, var3, var4);
         this.a((T)var12, var9);
         boolean var13 = this.b(var9);
         this.a.a(this.a(var11, var13));
         switch (var11.x_()) {
            case a:
               int var14 = var11.b(var10);
               float var15 = (var14 >> 16 & 0xFF) / 255.0F;
               float var16 = (var14 >> 8 & 0xFF) / 255.0F;
               float var17 = (var14 & 0xFF) / 255.0F;
               GlStateManager.c(this.f * var15, this.g * var16, this.h * var17, this.e);
               var12.a(var1, var2, var3, var5, var6, var7, var8);
               this.a.a(this.a(var11, var13, "overlay"));
            case b:
            case c:
            case d:
            case e:
               GlStateManager.c(this.f, this.g, this.h, this.e);
               var12.a(var1, var2, var3, var5, var6, var7, var8);
            default:
               if (!this.i && var10.w()) {
                  this.a(var1, (T)var12, var2, var3, var4, var5, var6, var7, var8);
               }
         }
      }
   }

   public ItemStack a(EntityLivingBase var1, int var2) {
      return var1.q(var2 - 1);
   }

   public T a(int var1) {
      return this.b(var1) ? this.c : this.d;
   }

   private boolean b(int var1) {
      return var1 == 2;
   }

   private void a(EntityLivingBase var1, T var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      float var10 = var1.W + var5;
      this.a.a(b);
      GlStateManager.l();
      GlStateManager.c(514);
      GlStateManager.a(false);
      float var11 = 0.5F;
      GlStateManager.c(var11, var11, var11, 1.0F);

      for (int var12 = 0; var12 < 2; var12++) {
         GlStateManager.f();
         GlStateManager.b(768, 1);
         float var13 = 0.76F;
         GlStateManager.c(0.5F * var13, 0.25F * var13, 0.8F * var13, 1.0F);
         GlStateManager.n(5890);
         GlStateManager.D();
         float var14 = 0.33333334F;
         GlStateManager.a(var14, var14, var14);
         GlStateManager.b(30.0F - var12 * 60.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.b(0.0F, var10 * (0.001F + var12 * 0.003F) * 20.0F, 0.0F);
         GlStateManager.n(5888);
         var2.a(var1, var3, var4, var6, var7, var8, var9);
      }

      GlStateManager.n(5890);
      GlStateManager.D();
      GlStateManager.n(5888);
      GlStateManager.e();
      GlStateManager.a(true);
      GlStateManager.c(515);
      GlStateManager.k();
   }

   private ResourceLocation a(ItemArmor var1, boolean var2) {
      return this.a(var1, var2, null);
   }

   private ResourceLocation a(ItemArmor var1, boolean var2, String var3) {
      String var4 = String.format("textures/models/armor/%s_layer_%d%s.png", var1.x_().c(), var2 ? 2 : 1, var3 == null ? "" : String.format("_%s", var3));
      ResourceLocation var5 = j.get(var4);
      if (var5 == null) {
         var5 = new ResourceLocation(var4);
         j.put(var4, var5);
      }

      return var5;
   }

   protected abstract void a();

   protected abstract void a(T var1, int var2);
}
