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
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelBlock {
   private static final Logger f = LogManager.getLogger();
   static final Gson a = new GsonBuilder()
      .registerTypeAdapter(ModelBlock.class, new ModelBlock.Deserializer())
      .registerTypeAdapter(BlockPart.class, new BlockPart.Deserializer())
      .registerTypeAdapter(BlockPartFace.class, new BlockPartFace.Deserializer())
      .registerTypeAdapter(BlockFaceUV.class, new BlockFaceUV.Deserializer())
      .registerTypeAdapter(ItemTransformVec3f.class, new ItemTransformVec3f.Deserializer())
      .registerTypeAdapter(ItemCameraTransforms.class, new ItemCameraTransforms.Deserializer())
      .create();
   private final List<BlockPart> g;
   private final boolean h;
   private final boolean i;
   private ItemCameraTransforms j;
   public String b = "";
   protected final Map<String, String> c;
   protected ModelBlock d;
   protected ResourceLocation e;

   public static ModelBlock a(Reader var0) {
      return (ModelBlock)a.fromJson(var0, ModelBlock.class);
   }

   public static ModelBlock a(String var0) {
      return a(new StringReader(var0));
   }

   protected ModelBlock(List<BlockPart> var1, Map<String, String> var2, boolean var3, boolean var4, ItemCameraTransforms var5) {
      this(null, var1, var2, var3, var4, var5);
   }

   protected ModelBlock(ResourceLocation var1, Map<String, String> var2, boolean var3, boolean var4, ItemCameraTransforms var5) {
      this(var1, Collections.emptyList(), var2, var3, var4, var5);
   }

   private ModelBlock(ResourceLocation var1, List<BlockPart> var2, Map<String, String> var3, boolean var4, boolean var5, ItemCameraTransforms var6) {
      this.g = var2;
      this.i = var4;
      this.h = var5;
      this.c = var3;
      this.e = var1;
      this.j = var6;
   }

   public List<BlockPart> a() {
      return this.h() ? this.d.a() : this.g;
   }

   private boolean h() {
      return this.d != null;
   }

   public boolean b() {
      return this.h() ? this.d.b() : this.i;
   }

   public boolean c() {
      return this.h;
   }

   public boolean d() {
      return this.e == null || this.d != null && this.d.d();
   }

   public void a(Map<ResourceLocation, ModelBlock> var1) {
      if (this.e != null) {
         this.d = (ModelBlock)var1.get(this.e);
      }
   }

   public boolean b(String var1) {
      return !"missingno".equals(this.c(var1));
   }

   public String c(String var1) {
      if (!this.d(var1)) {
         var1 = '#' + var1;
      }

      return this.a(var1, new ModelBlock.Bookkeep(this));
   }

   private String a(String var1, ModelBlock.Bookkeep var2) {
      if (this.d(var1)) {
         if (this == var2.b) {
            f.warn("Unable to resolve texture due to upward reference: " + var1 + " in " + this.b);
            return "missingno";
         } else {
            String var3 = this.c.get(var1.substring(1));
            if (var3 == null && this.h()) {
               var3 = this.d.a(var1, var2);
            }

            var2.b = this;
            if (var3 != null && this.d(var3)) {
               var3 = var2.a.a(var3, var2);
            }

            return var3 != null && !this.d(var3) ? var3 : "missingno";
         }
      } else {
         return var1;
      }
   }

   private boolean d(String var1) {
      return var1.charAt(0) == '#';
   }

   public ResourceLocation e() {
      return this.e;
   }

   public ModelBlock f() {
      return this.h() ? this.d.f() : this;
   }

   public ItemCameraTransforms g() {
      ItemTransformVec3f var1 = this.a(ItemCameraTransforms.TransformType.b);
      ItemTransformVec3f var2 = this.a(ItemCameraTransforms.TransformType.c);
      ItemTransformVec3f var3 = this.a(ItemCameraTransforms.TransformType.d);
      ItemTransformVec3f var4 = this.a(ItemCameraTransforms.TransformType.e);
      ItemTransformVec3f var5 = this.a(ItemCameraTransforms.TransformType.f);
      ItemTransformVec3f var6 = this.a(ItemCameraTransforms.TransformType.g);
      return new ItemCameraTransforms(var1, var2, var3, var4, var5, var6);
   }

   private ItemTransformVec3f a(ItemCameraTransforms.TransformType var1) {
      return this.d != null && !this.j.c(var1) ? this.d.a(var1) : this.j.b(var1);
   }

   public static void b(Map<ResourceLocation, ModelBlock> var0) {
      for (ModelBlock var2 : var0.values()) {
         try {
            ModelBlock var3 = var2.d;

            for (ModelBlock var4 = var3.d; var3 != var4; var4 = var4.d.d) {
               var3 = var3.d;
            }

            throw new ModelBlock.LoopException();
         } catch (NullPointerException var5) {
         }
      }
   }

   static final class Bookkeep {
      public final ModelBlock a;
      public ModelBlock b;

      private Bookkeep(ModelBlock var1) {
         this.a = var1;
      }
   }

   public static class Deserializer implements JsonDeserializer<ModelBlock> {
      public ModelBlock a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject var4 = var1.getAsJsonObject();
         List var5 = this.a(var3, var4);
         String var6 = this.c(var4);
         boolean var7 = StringUtils.isEmpty(var6);
         boolean var8 = var5.isEmpty();
         if (var8 && var7) {
            throw new JsonParseException("BlockModel requires either elements or parent, found neither");
         } else if (!var7 && !var8) {
            throw new JsonParseException("BlockModel requires either elements or parent, found both");
         } else {
            Map var9 = this.b(var4);
            boolean var10 = this.a(var4);
            ItemCameraTransforms var11 = ItemCameraTransforms.a;
            if (var4.has("display")) {
               JsonObject var12 = JsonUtils.s(var4, "display");
               var11 = (ItemCameraTransforms)var3.deserialize(var12, ItemCameraTransforms.class);
            }

            return var8 ? new ModelBlock(new ResourceLocation(var6), var9, var10, true, var11) : new ModelBlock(var5, var9, var10, true, var11);
         }
      }

      private Map<String, String> b(JsonObject var1) {
         HashMap var2 = Maps.newHashMap();
         if (var1.has("textures")) {
            JsonObject var3 = var1.getAsJsonObject("textures");

            for (Entry var5 : var3.entrySet()) {
               var2.put(var5.getKey(), ((JsonElement)var5.getValue()).getAsString());
            }
         }

         return var2;
      }

      private String c(JsonObject var1) {
         return JsonUtils.a(var1, "parent", "");
      }

      protected boolean a(JsonObject var1) {
         return JsonUtils.a(var1, "ambientocclusion", true);
      }

      protected List<BlockPart> a(JsonDeserializationContext var1, JsonObject var2) {
         ArrayList var3 = Lists.newArrayList();
         if (var2.has("elements")) {
            for (JsonElement var5 : JsonUtils.t(var2, "elements")) {
               var3.add((BlockPart)var1.deserialize(var5, BlockPart.class));
            }
         }

         return var3;
      }
   }

   public static class LoopException extends RuntimeException {
   }
}
