package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.client.renderer.GlStateManager;

public class ItemCameraTransforms {
   public static final ItemCameraTransforms a = new ItemCameraTransforms();
   public static float b = 0.0F;
   public static float c = 0.0F;
   public static float d = 0.0F;
   public static float e = 0.0F;
   public static float f = 0.0F;
   public static float g = 0.0F;
   public static float h = 0.0F;
   public static float i = 0.0F;
   public static float j = 0.0F;
   public final ItemTransformVec3f k;
   public final ItemTransformVec3f l;
   public final ItemTransformVec3f m;
   public final ItemTransformVec3f n;
   public final ItemTransformVec3f o;
   public final ItemTransformVec3f p;

   private ItemCameraTransforms() {
      this(ItemTransformVec3f.a, ItemTransformVec3f.a, ItemTransformVec3f.a, ItemTransformVec3f.a, ItemTransformVec3f.a, ItemTransformVec3f.a);
   }

   public ItemCameraTransforms(ItemCameraTransforms var1) {
      this.k = var1.k;
      this.l = var1.l;
      this.m = var1.m;
      this.n = var1.n;
      this.o = var1.o;
      this.p = var1.p;
   }

   public ItemCameraTransforms(
      ItemTransformVec3f var1, ItemTransformVec3f var2, ItemTransformVec3f var3, ItemTransformVec3f var4, ItemTransformVec3f var5, ItemTransformVec3f var6
   ) {
      this.k = var1;
      this.l = var2;
      this.m = var3;
      this.n = var4;
      this.o = var5;
      this.p = var6;
   }

   public void a(ItemCameraTransforms.TransformType var1) {
      ItemTransformVec3f var2 = this.b(var1);
      if (var2 != ItemTransformVec3f.a) {
         GlStateManager.b(var2.c.x + b, var2.c.y + c, var2.c.z + d);
         GlStateManager.b(var2.b.y + f, 0.0F, 1.0F, 0.0F);
         GlStateManager.b(var2.b.x + e, 1.0F, 0.0F, 0.0F);
         GlStateManager.b(var2.b.z + g, 0.0F, 0.0F, 1.0F);
         GlStateManager.a(var2.d.x + h, var2.d.y + i, var2.d.z + j);
      }
   }

   public ItemTransformVec3f b(ItemCameraTransforms.TransformType var1) {
      switch (var1) {
         case b:
            return this.k;
         case c:
            return this.l;
         case d:
            return this.m;
         case e:
            return this.n;
         case f:
            return this.o;
         case g:
            return this.p;
         default:
            return ItemTransformVec3f.a;
      }
   }

   public boolean c(ItemCameraTransforms.TransformType var1) {
      return !this.b(var1).equals(ItemTransformVec3f.a);
   }

   static class Deserializer implements JsonDeserializer<ItemCameraTransforms> {
      public ItemCameraTransforms a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject var4 = var1.getAsJsonObject();
         ItemTransformVec3f var5 = this.a(var3, var4, "thirdperson");
         ItemTransformVec3f var6 = this.a(var3, var4, "firstperson");
         ItemTransformVec3f var7 = this.a(var3, var4, "head");
         ItemTransformVec3f var8 = this.a(var3, var4, "gui");
         ItemTransformVec3f var9 = this.a(var3, var4, "ground");
         ItemTransformVec3f var10 = this.a(var3, var4, "fixed");
         return new ItemCameraTransforms(var5, var6, var7, var8, var9, var10);
      }

      private ItemTransformVec3f a(JsonDeserializationContext var1, JsonObject var2, String var3) {
         return var2.has(var3) ? (ItemTransformVec3f)var1.deserialize(var2.get(var3), ItemTransformVec3f.class) : ItemTransformVec3f.a;
      }
   }

   public static enum TransformType {
      a,
      b,
      c,
      d,
      e,
      f,
      g;
   }
}
