package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.util.vector.Vector3f;

public class ItemModelGenerator {
   public static final List<String> a = Lists.newArrayList(new String[]{"layer0", "layer1", "layer2", "layer3", "layer4"});

   public ModelBlock a(TextureMap var1, ModelBlock var2) {
      HashMap var3 = Maps.newHashMap();
      ArrayList var4 = Lists.newArrayList();

      for (int var5 = 0; var5 < a.size(); var5++) {
         String var6 = a.get(var5);
         if (!var2.b(var6)) {
            break;
         }

         String var7 = var2.c(var6);
         var3.put(var6, var7);
         TextureAtlasSprite var8 = var1.a(new ResourceLocation(var7).toString());
         var4.addAll(this.a(var5, var6, var8));
      }

      if (var4.isEmpty()) {
         return null;
      } else {
         var3.put("particle", var2.b("particle") ? var2.c("particle") : (String)var3.get("layer0"));
         return new ModelBlock(var4, var3, false, false, var2.g());
      }
   }

   private List<BlockPart> a(int var1, String var2, TextureAtlasSprite var3) {
      HashMap var4 = Maps.newHashMap();
      var4.put(EnumFacing.d, new BlockPartFace(null, var1, var2, new BlockFaceUV(new float[]{0.0F, 0.0F, 16.0F, 16.0F}, 0)));
      var4.put(EnumFacing.c, new BlockPartFace(null, var1, var2, new BlockFaceUV(new float[]{16.0F, 0.0F, 0.0F, 16.0F}, 0)));
      ArrayList var5 = Lists.newArrayList();
      var5.add(new BlockPart(new Vector3f(0.0F, 0.0F, 7.5F), new Vector3f(16.0F, 16.0F, 8.5F), var4, null, true));
      var5.addAll(this.a(var3, var2, var1));
      return var5;
   }

   private List<BlockPart> a(TextureAtlasSprite var1, String var2, int var3) {
      float var4 = var1.c();
      float var5 = var1.d();
      ArrayList var6 = Lists.newArrayList();

      for (ItemModelGenerator.Span var8 : this.a(var1)) {
         float var9 = 0.0F;
         float var10 = 0.0F;
         float var11 = 0.0F;
         float var12 = 0.0F;
         float var13 = 0.0F;
         float var14 = 0.0F;
         float var15 = 0.0F;
         float var16 = 0.0F;
         float var17 = 0.0F;
         float var18 = 0.0F;
         float var19 = var8.b();
         float var20 = var8.c();
         float var21 = var8.d();
         ItemModelGenerator.SpanFacing var22 = var8.a();
         switch (var22) {
            case a:
               var13 = var19;
               var9 = var19;
               var11 = var14 = var20 + 1.0F;
               var15 = var21;
               var10 = var21;
               var16 = var21;
               var12 = var21;
               var17 = 16.0F / var4;
               var18 = 16.0F / (var5 - 1.0F);
               break;
            case b:
               var16 = var21;
               var15 = var21;
               var13 = var19;
               var9 = var19;
               var11 = var14 = var20 + 1.0F;
               var10 = var21 + 1.0F;
               var12 = var21 + 1.0F;
               var17 = 16.0F / var4;
               var18 = 16.0F / (var5 - 1.0F);
               break;
            case c:
               var13 = var21;
               var9 = var21;
               var14 = var21;
               var11 = var21;
               var16 = var19;
               var10 = var19;
               var12 = var15 = var20 + 1.0F;
               var17 = 16.0F / (var4 - 1.0F);
               var18 = 16.0F / var5;
               break;
            case d:
               var14 = var21;
               var13 = var21;
               var9 = var21 + 1.0F;
               var11 = var21 + 1.0F;
               var16 = var19;
               var10 = var19;
               var12 = var15 = var20 + 1.0F;
               var17 = 16.0F / (var4 - 1.0F);
               var18 = 16.0F / var5;
         }

         float var23 = 16.0F / var4;
         float var24 = 16.0F / var5;
         var9 *= var23;
         var11 *= var23;
         var10 *= var24;
         var12 *= var24;
         var10 = 16.0F - var10;
         var12 = 16.0F - var12;
         var13 *= var17;
         var14 *= var17;
         var15 *= var18;
         var16 *= var18;
         HashMap var25 = Maps.newHashMap();
         var25.put(var22.a(), new BlockPartFace(null, var3, var2, new BlockFaceUV(new float[]{var13, var15, var14, var16}, 0)));
         switch (var22) {
            case a:
               var6.add(new BlockPart(new Vector3f(var9, var10, 7.5F), new Vector3f(var11, var10, 8.5F), var25, null, true));
               break;
            case b:
               var6.add(new BlockPart(new Vector3f(var9, var12, 7.5F), new Vector3f(var11, var12, 8.5F), var25, null, true));
               break;
            case c:
               var6.add(new BlockPart(new Vector3f(var9, var10, 7.5F), new Vector3f(var9, var12, 8.5F), var25, null, true));
               break;
            case d:
               var6.add(new BlockPart(new Vector3f(var11, var10, 7.5F), new Vector3f(var11, var12, 8.5F), var25, null, true));
         }
      }

      return var6;
   }

   private List<ItemModelGenerator.Span> a(TextureAtlasSprite var1) {
      int var2 = var1.c();
      int var3 = var1.d();
      ArrayList var4 = Lists.newArrayList();

      for (int var5 = 0; var5 < var1.k(); var5++) {
         int[] var6 = var1.a(var5)[0];

         for (int var7 = 0; var7 < var3; var7++) {
            for (int var8 = 0; var8 < var2; var8++) {
               boolean var9 = !this.a(var6, var8, var7, var2, var3);
               this.a(ItemModelGenerator.SpanFacing.a, var4, var6, var8, var7, var2, var3, var9);
               this.a(ItemModelGenerator.SpanFacing.b, var4, var6, var8, var7, var2, var3, var9);
               this.a(ItemModelGenerator.SpanFacing.c, var4, var6, var8, var7, var2, var3, var9);
               this.a(ItemModelGenerator.SpanFacing.d, var4, var6, var8, var7, var2, var3, var9);
            }
         }
      }

      return var4;
   }

   private void a(ItemModelGenerator.SpanFacing var1, List<ItemModelGenerator.Span> var2, int[] var3, int var4, int var5, int var6, int var7, boolean var8) {
      boolean var9 = this.a(var3, var4 + var1.b(), var5 + var1.c(), var6, var7) && var8;
      if (var9) {
         this.a(var2, var1, var4, var5);
      }
   }

   private void a(List<ItemModelGenerator.Span> var1, ItemModelGenerator.SpanFacing var2, int var3, int var4) {
      ItemModelGenerator.Span var5 = null;

      for (ItemModelGenerator.Span var7 : var1) {
         if (var7.a() == var2) {
            int var8 = var2.d() ? var4 : var3;
            if (var7.d() == var8) {
               var5 = var7;
               break;
            }
         }
      }

      int var9 = var2.d() ? var4 : var3;
      int var10 = var2.d() ? var3 : var4;
      if (var5 == null) {
         var1.add(new ItemModelGenerator.Span(var2, var10, var9));
      } else {
         var5.a(var10);
      }
   }

   private boolean a(int[] var1, int var2, int var3, int var4, int var5) {
      return var2 >= 0 && var3 >= 0 && var2 < var4 && var3 < var5 ? (var1[var3 * var4 + var2] >> 24 & 0xFF) == 0 : true;
   }

   static class Span {
      private final ItemModelGenerator.SpanFacing a;
      private int b;
      private int c;
      private final int d;

      public Span(ItemModelGenerator.SpanFacing var1, int var2, int var3) {
         this.a = var1;
         this.b = var2;
         this.c = var2;
         this.d = var3;
      }

      public void a(int var1) {
         if (var1 < this.b) {
            this.b = var1;
         } else if (var1 > this.c) {
            this.c = var1;
         }
      }

      public ItemModelGenerator.SpanFacing a() {
         return this.a;
      }

      public int b() {
         return this.b;
      }

      public int c() {
         return this.c;
      }

      public int d() {
         return this.d;
      }
   }

   static enum SpanFacing {
      a(EnumFacing.b, 0, -1),
      b(EnumFacing.a, 0, 1),
      c(EnumFacing.f, -1, 0),
      d(EnumFacing.e, 1, 0);

      private final EnumFacing e;
      private final int f;
      private final int g;

      private SpanFacing(EnumFacing var3, int var4, int var5) {
         this.e = var3;
         this.f = var4;
         this.g = var5;
      }

      public EnumFacing a() {
         return this.e;
      }

      public int b() {
         return this.f;
      }

      public int c() {
         return this.g;
      }

      private boolean d() {
         return this == b || this == a;
      }
   }
}
