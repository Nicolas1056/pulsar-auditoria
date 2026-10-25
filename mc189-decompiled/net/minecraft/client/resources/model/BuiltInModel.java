package net.minecraft.client.resources.model;

import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class BuiltInModel implements IBakedModel {
   private ItemCameraTransforms a;

   public BuiltInModel(ItemCameraTransforms var1) {
      this.a = var1;
   }

   @Override
   public List<BakedQuad> a(EnumFacing var1) {
      return null;
   }

   @Override
   public List<BakedQuad> a() {
      return null;
   }

   @Override
   public boolean b() {
      return false;
   }

   @Override
   public boolean c() {
      return true;
   }

   @Override
   public boolean d() {
      return true;
   }

   @Override
   public TextureAtlasSprite e() {
      return null;
   }

   @Override
   public ItemCameraTransforms f() {
      return this.a;
   }
}
