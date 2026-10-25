package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.IRegistry;

public class ModelManager implements IResourceManagerReloadListener {
   private IRegistry<ModelResourceLocation, IBakedModel> a;
   private final TextureMap b;
   private final BlockModelShapes c;
   private IBakedModel d;

   public ModelManager(TextureMap var1) {
      this.b = var1;
      this.c = new BlockModelShapes(this);
   }

   @Override
   public void a(IResourceManager var1) {
      ModelBakery var2 = new ModelBakery(var1, this.b, this.c);
      this.a = var2.a();
      this.d = this.a.a(ModelBakery.a);
      this.c.c();
   }

   public IBakedModel a(ModelResourceLocation var1) {
      if (var1 == null) {
         return this.d;
      } else {
         IBakedModel var2 = this.a.a(var1);
         return var2 == null ? this.d : var2;
      }
   }

   public IBakedModel a() {
      return this.d;
   }

   public TextureMap b() {
      return this.b;
   }

   public BlockModelShapes c() {
      return this.c;
   }
}
