package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class ModelBlockDefinition {
   static final Gson a = new GsonBuilder()
      .registerTypeAdapter(ModelBlockDefinition.class, new ModelBlockDefinition.Deserializer())
      .registerTypeAdapter(ModelBlockDefinition.Variant.class, new ModelBlockDefinition.Variant.Deserializer())
      .create();
   private final Map<String, ModelBlockDefinition.Variants> b = Maps.newHashMap();

   public static ModelBlockDefinition a(Reader var0) {
      return (ModelBlockDefinition)a.fromJson(var0, ModelBlockDefinition.class);
   }

   public ModelBlockDefinition(Collection<ModelBlockDefinition.Variants> var1) {
      for (ModelBlockDefinition.Variants var3 : var1) {
         this.b.put(var3.a, var3);
      }
   }

   public ModelBlockDefinition(List<ModelBlockDefinition> var1) {
      for (ModelBlockDefinition var3 : var1) {
         this.b.putAll(var3.b);
      }
   }

   public ModelBlockDefinition.Variants b(String var1) {
      ModelBlockDefinition.Variants var2 = this.b.get(var1);
      if (var2 == null) {
         throw new ModelBlockDefinition.MissingVariantException();
      } else {
         return var2;
      }
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 instanceof ModelBlockDefinition) {
         ModelBlockDefinition var2 = (ModelBlockDefinition)var1;
         return this.b.equals(var2.b);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.b.hashCode();
   }

   public static class Deserializer implements JsonDeserializer<ModelBlockDefinition> {
      public ModelBlockDefinition a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject var4 = var1.getAsJsonObject();
         List var5 = this.a(var3, var4);
         return new ModelBlockDefinition(var5);
      }

      protected List<ModelBlockDefinition.Variants> a(JsonDeserializationContext var1, JsonObject var2) {
         JsonObject var3 = JsonUtils.s(var2, "variants");
         ArrayList var4 = Lists.newArrayList();

         for (Entry var6 : var3.entrySet()) {
            var4.add(this.a(var1, var6));
         }

         return var4;
      }

      protected ModelBlockDefinition.Variants a(JsonDeserializationContext var1, Entry<String, JsonElement> var2) {
         String var3 = (String)var2.getKey();
         ArrayList var4 = Lists.newArrayList();
         JsonElement var5 = (JsonElement)var2.getValue();
         if (var5.isJsonArray()) {
            for (JsonElement var7 : var5.getAsJsonArray()) {
               var4.add((ModelBlockDefinition.Variant)var1.deserialize(var7, ModelBlockDefinition.Variant.class));
            }
         } else {
            var4.add((ModelBlockDefinition.Variant)var1.deserialize(var5, ModelBlockDefinition.Variant.class));
         }

         return new ModelBlockDefinition.Variants(var3, var4);
      }
   }

   public class MissingVariantException extends RuntimeException {
      protected MissingVariantException() {
      }
   }

   public static class Variant {
      private final ResourceLocation a;
      private final ModelRotation b;
      private final boolean c;
      private final int d;

      public Variant(ResourceLocation var1, ModelRotation var2, boolean var3, int var4) {
         this.a = var1;
         this.b = var2;
         this.c = var3;
         this.d = var4;
      }

      public ResourceLocation a() {
         return this.a;
      }

      public ModelRotation b() {
         return this.b;
      }

      public boolean c() {
         return this.c;
      }

      public int d() {
         return this.d;
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof ModelBlockDefinition.Variant)) {
            return false;
         } else {
            ModelBlockDefinition.Variant var2 = (ModelBlockDefinition.Variant)var1;
            return this.a.equals(var2.a) && this.b == var2.b && this.c == var2.c;
         }
      }

      @Override
      public int hashCode() {
         int var1 = this.a.hashCode();
         var1 = 31 * var1 + (this.b != null ? this.b.hashCode() : 0);
         return 31 * var1 + (this.c ? 1 : 0);
      }

      public static class Deserializer implements JsonDeserializer<ModelBlockDefinition.Variant> {
         public ModelBlockDefinition.Variant a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
            JsonObject var4 = var1.getAsJsonObject();
            String var5 = this.b(var4);
            ModelRotation var6 = this.a(var4);
            boolean var7 = this.d(var4);
            int var8 = this.c(var4);
            return new ModelBlockDefinition.Variant(this.a(var5), var6, var7, var8);
         }

         private ResourceLocation a(String var1) {
            ResourceLocation var2 = new ResourceLocation(var1);
            return new ResourceLocation(var2.b(), "block/" + var2.a());
         }

         private boolean d(JsonObject var1) {
            return JsonUtils.a(var1, "uvlock", false);
         }

         protected ModelRotation a(JsonObject var1) {
            int var2 = JsonUtils.a(var1, "x", 0);
            int var3 = JsonUtils.a(var1, "y", 0);
            ModelRotation var4 = ModelRotation.a(var2, var3);
            if (var4 == null) {
               throw new JsonParseException("Invalid BlockModelRotation x: " + var2 + ", y: " + var3);
            } else {
               return var4;
            }
         }

         protected String b(JsonObject var1) {
            return JsonUtils.h(var1, "model");
         }

         protected int c(JsonObject var1) {
            return JsonUtils.a(var1, "weight", 1);
         }
      }
   }

   public static class Variants {
      private final String a;
      private final List<ModelBlockDefinition.Variant> b;

      public Variants(String var1, List<ModelBlockDefinition.Variant> var2) {
         this.a = var1;
         this.b = var2;
      }

      public List<ModelBlockDefinition.Variant> b() {
         return this.b;
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof ModelBlockDefinition.Variants)) {
            return false;
         } else {
            ModelBlockDefinition.Variants var2 = (ModelBlockDefinition.Variants)var1;
            return !this.a.equals(var2.a) ? false : this.b.equals(var2.b);
         }
      }

      @Override
      public int hashCode() {
         int var1 = this.a.hashCode();
         return 31 * var1 + this.b.hashCode();
      }
   }
}
