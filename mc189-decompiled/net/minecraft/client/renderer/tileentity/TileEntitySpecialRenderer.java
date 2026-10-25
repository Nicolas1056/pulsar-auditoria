package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class TileEntitySpecialRenderer<T extends TileEntity> {
   protected static final ResourceLocation[] a = new ResourceLocation[]{
      new ResourceLocation("textures/blocks/destroy_stage_0.png"),
      new ResourceLocation("textures/blocks/destroy_stage_1.png"),
      new ResourceLocation("textures/blocks/destroy_stage_2.png"),
      new ResourceLocation("textures/blocks/destroy_stage_3.png"),
      new ResourceLocation("textures/blocks/destroy_stage_4.png"),
      new ResourceLocation("textures/blocks/destroy_stage_5.png"),
      new ResourceLocation("textures/blocks/destroy_stage_6.png"),
      new ResourceLocation("textures/blocks/destroy_stage_7.png"),
      new ResourceLocation("textures/blocks/destroy_stage_8.png"),
      new ResourceLocation("textures/blocks/destroy_stage_9.png")
   };
   protected TileEntityRendererDispatcher b;

   public abstract void a(T var1, double var2, double var4, double var6, float var8, int var9);

   protected void a(ResourceLocation var1) {
      TextureManager var2 = this.b.e;
      if (var2 != null) {
         var2.a(var1);
      }
   }

   protected World b() {
      return this.b.f;
   }

   public void a(TileEntityRendererDispatcher var1) {
      this.b = var1;
   }

   public FontRenderer c() {
      return this.b.a();
   }

   public boolean a() {
      return false;
   }
}
