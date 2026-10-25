package net.minecraft.client.resources.model;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandom;

public class WeightedBakedModel implements IBakedModel {
   private final int a;
   private final List<WeightedBakedModel.MyWeighedRandomItem> b;
   private final IBakedModel c;

   public WeightedBakedModel(List<WeightedBakedModel.MyWeighedRandomItem> var1) {
      this.b = var1;
      this.a = WeightedRandom.a(var1);
      this.c = ((WeightedBakedModel.MyWeighedRandomItem)var1.get(0)).b;
   }

   @Override
   public List<BakedQuad> a(EnumFacing var1) {
      return this.c.a(var1);
   }

   @Override
   public List<BakedQuad> a() {
      return this.c.a();
   }

   @Override
   public boolean b() {
      return this.c.b();
   }

   @Override
   public boolean c() {
      return this.c.c();
   }

   @Override
   public boolean d() {
      return this.c.d();
   }

   @Override
   public TextureAtlasSprite e() {
      return this.c.e();
   }

   @Override
   public ItemCameraTransforms f() {
      return this.c.f();
   }

   public IBakedModel a(long var1) {
      return WeightedRandom.a(this.b, Math.abs((int)var1 >> 16) % this.a).b;
   }

   public static class Builder {
      private List<WeightedBakedModel.MyWeighedRandomItem> a = Lists.newArrayList();

      public WeightedBakedModel.Builder a(IBakedModel var1, int var2) {
         this.a.add(new WeightedBakedModel.MyWeighedRandomItem(var1, var2));
         return this;
      }

      public WeightedBakedModel a() {
         Collections.sort(this.a);
         return new WeightedBakedModel(this.a);
      }

      public IBakedModel b() {
         return this.a.get(0).b;
      }
   }

   static class MyWeighedRandomItem extends WeightedRandom.Item implements Comparable<WeightedBakedModel.MyWeighedRandomItem> {
      protected final IBakedModel b;

      public MyWeighedRandomItem(IBakedModel var1, int var2) {
         super(var2);
         this.b = var1;
      }

      public int a(WeightedBakedModel.MyWeighedRandomItem var1) {
         return ComparisonChain.start().compare(var1.a, this.a).compare(this.a(), var1.a()).result();
      }

      protected int a() {
         int var1 = this.b.a().size();

         for (EnumFacing var5 : EnumFacing.values()) {
            var1 += this.b.a(var5).size();
         }

         return var1;
      }

      @Override
      public String toString() {
         return "MyWeighedRandomItem{weight=" + this.a + ", model=" + this.b + '}';
      }
   }
}
