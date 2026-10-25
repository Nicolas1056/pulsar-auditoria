package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderSnowball<T extends Entity> extends Render<T> {
   protected final Item a;
   private final RenderItem e;

   public RenderSnowball(RenderManager var1, Item var2, RenderItem var3) {
      super(var1);
      this.a = var2;
      this.e = var3;
   }

   @Override
   public void a(T var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.E();
      GlStateManager.b((float)var2, (float)var4, (float)var6);
      GlStateManager.B();
      GlStateManager.a(0.5F, 0.5F, 0.5F);
      GlStateManager.b(-this.b.e, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(this.b.f, 1.0F, 0.0F, 0.0F);
      this.a(TextureMap.g);
      this.e.a(this.d((T)var1), ItemCameraTransforms.TransformType.f);
      GlStateManager.C();
      GlStateManager.F();
      super.a((T)var1, var2, var4, var6, var8, var9);
   }

   public ItemStack d(T var1) {
      return new ItemStack(this.a, 1, 0);
   }

   @Override
   protected ResourceLocation a(Entity var1) {
      return TextureMap.g;
   }
}
