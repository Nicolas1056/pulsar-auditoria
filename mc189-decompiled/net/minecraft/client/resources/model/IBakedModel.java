package net.minecraft.client.resources.model;

import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public interface IBakedModel {
   List<BakedQuad> a(EnumFacing var1);

   List<BakedQuad> a();

   boolean b();

   boolean c();

   boolean d();

   TextureAtlasSprite e();

   ItemCameraTransforms f();
}
