package net.minecraft.client.resources.model;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BreakingFour;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class SimpleBakedModel implements IBakedModel {
   protected final List<BakedQuad> a;
   protected final List<List<BakedQuad>> b;
   protected final boolean c;
   protected final boolean d;
   protected final TextureAtlasSprite e;
   protected final ItemCameraTransforms f;

   public SimpleBakedModel(List<BakedQuad> var1, List<List<BakedQuad>> var2, boolean var3, boolean var4, TextureAtlasSprite var5, ItemCameraTransforms var6) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
      this.e = var5;
      this.f = var6;
   }

   @Override
   public List<BakedQuad> a(EnumFacing var1) {
      return this.b.get(var1.ordinal());
   }

   @Override
   public List<BakedQuad> a() {
      return this.a;
   }

   @Override
   public boolean b() {
      return this.c;
   }

   @Override
   public boolean c() {
      return this.d;
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public TextureAtlasSprite e() {
      return this.e;
   }

   @Override
   public ItemCameraTransforms f() {
      return this.f;
   }

   public static class Builder {
      private final List<BakedQuad> a = Lists.newArrayList();
      private final List<List<BakedQuad>> b = Lists.newArrayListWithCapacity(6);
      private final boolean c;
      private TextureAtlasSprite d;
      private boolean e;
      private ItemCameraTransforms f;

      public Builder(ModelBlock var1) {
         this(var1.b(), var1.c(), var1.g());
      }

      public Builder(IBakedModel var1, TextureAtlasSprite var2) {
         this(var1.b(), var1.c(), var1.f());
         this.d = var1.e();

         for (EnumFacing var6 : EnumFacing.values()) {
            this.a(var1, var2, var6);
         }

         this.a(var1, var2);
      }

      private void a(IBakedModel var1, TextureAtlasSprite var2, EnumFacing var3) {
         for (BakedQuad var5 : var1.a(var3)) {
            this.a(var3, new BreakingFour(var5, var2));
         }
      }

      private void a(IBakedModel var1, TextureAtlasSprite var2) {
         for (BakedQuad var4 : var1.a()) {
            this.a(new BreakingFour(var4, var2));
         }
      }

      private Builder(boolean var1, boolean var2, ItemCameraTransforms var3) {
         for (EnumFacing var7 : EnumFacing.values()) {
            this.b.add(Lists.newArrayList());
         }

         this.c = var1;
         this.e = var2;
         this.f = var3;
      }

      public SimpleBakedModel.Builder a(EnumFacing var1, BakedQuad var2) {
         this.b.get(var1.ordinal()).add(var2);
         return this;
      }

      public SimpleBakedModel.Builder a(BakedQuad var1) {
         this.a.add(var1);
         return this;
      }

      public SimpleBakedModel.Builder a(TextureAtlasSprite var1) {
         this.d = var1;
         return this;
      }

      public IBakedModel b() {
         if (this.d == null) {
            throw new RuntimeException("Missing particle!");
         } else {
            return new SimpleBakedModel(this.a, this.b, this.c, this.e, this.d, this.f);
         }
      }
   }
}
