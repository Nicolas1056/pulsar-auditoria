package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.MathHelper;
import org.lwjgl.util.vector.Vector3f;

public class BlockPart {
   public final Vector3f a;
   public final Vector3f b;
   public final Map<EnumFacing, BlockPartFace> c;
   public final BlockPartRotation d;
   public final boolean e;

   public BlockPart(Vector3f var1, Vector3f var2, Map<EnumFacing, BlockPartFace> var3, BlockPartRotation var4, boolean var5) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
      this.e = var5;
      this.a();
   }

   private void a() {
      for (Entry var2 : this.c.entrySet()) {
         float[] var3 = this.a((EnumFacing)var2.getKey());
         ((BlockPartFace)var2.getValue()).e.a(var3);
      }
   }

   private float[] a(EnumFacing var1) {
      float[] var2;
      switch (var1) {
         case a:
         case b:
            var2 = new float[]{this.a.x, this.a.z, this.b.x, this.b.z};
            break;
         case c:
         case d:
            var2 = new float[]{this.a.x, 16.0F - this.b.y, this.b.x, 16.0F - this.a.y};
            break;
         case e:
         case f:
            var2 = new float[]{this.a.z, 16.0F - this.b.y, this.b.z, 16.0F - this.a.y};
            break;
         default:
            throw new NullPointerException();
      }

      return var2;
   }

   static class Deserializer implements JsonDeserializer<BlockPart> {
      public BlockPart a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject var4 = var1.getAsJsonObject();
         Vector3f var5 = this.e(var4);
         Vector3f var6 = this.d(var4);
         BlockPartRotation var7 = this.a(var4);
         Map var8 = this.a(var3, var4);
         if (var4.has("shade") && !JsonUtils.c(var4, "shade")) {
            throw new JsonParseException("Expected shade to be a Boolean");
         } else {
            boolean var9 = JsonUtils.a(var4, "shade", true);
            return new BlockPart(var5, var6, var8, var7, var9);
         }
      }

      private BlockPartRotation a(JsonObject var1) {
         BlockPartRotation var2 = null;
         if (var1.has("rotation")) {
            JsonObject var3 = JsonUtils.s(var1, "rotation");
            Vector3f var4 = this.a(var3, "origin");
            var4.scale(0.0625F);
            EnumFacing.Axis var5 = this.c(var3);
            float var6 = this.b(var3);
            boolean var7 = JsonUtils.a(var3, "rescale", false);
            var2 = new BlockPartRotation(var4, var5, var6, var7);
         }

         return var2;
      }

      private float b(JsonObject var1) {
         float var2 = JsonUtils.k(var1, "angle");
         if (var2 != 0.0F && MathHelper.e(var2) != 22.5F && MathHelper.e(var2) != 45.0F) {
            throw new JsonParseException("Invalid rotation " + var2 + " found, only -45/-22.5/0/22.5/45 allowed");
         } else {
            return var2;
         }
      }

      private EnumFacing.Axis c(JsonObject var1) {
         String var2 = JsonUtils.h(var1, "axis");
         EnumFacing.Axis var3 = EnumFacing.Axis.a(var2.toLowerCase());
         if (var3 == null) {
            throw new JsonParseException("Invalid rotation axis: " + var2);
         } else {
            return var3;
         }
      }

      private Map<EnumFacing, BlockPartFace> a(JsonDeserializationContext var1, JsonObject var2) {
         Map var3 = this.b(var1, var2);
         if (var3.isEmpty()) {
            throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
         } else {
            return var3;
         }
      }

      private Map<EnumFacing, BlockPartFace> b(JsonDeserializationContext var1, JsonObject var2) {
         EnumMap var3 = Maps.newEnumMap(EnumFacing.class);
         JsonObject var4 = JsonUtils.s(var2, "faces");

         for (Entry var6 : var4.entrySet()) {
            EnumFacing var7 = this.a((String)var6.getKey());
            var3.put(var7, (BlockPartFace)var1.deserialize((JsonElement)var6.getValue(), BlockPartFace.class));
         }

         return var3;
      }

      private EnumFacing a(String var1) {
         EnumFacing var2 = EnumFacing.a(var1);
         if (var2 == null) {
            throw new JsonParseException("Unknown facing: " + var1);
         } else {
            return var2;
         }
      }

      private Vector3f d(JsonObject var1) {
         Vector3f var2 = this.a(var1, "to");
         if (!(var2.x < -16.0F) && !(var2.y < -16.0F) && !(var2.z < -16.0F) && !(var2.x > 32.0F) && !(var2.y > 32.0F) && !(var2.z > 32.0F)) {
            return var2;
         } else {
            throw new JsonParseException("'to' specifier exceeds the allowed boundaries: " + var2);
         }
      }

      private Vector3f e(JsonObject var1) {
         Vector3f var2 = this.a(var1, "from");
         if (!(var2.x < -16.0F) && !(var2.y < -16.0F) && !(var2.z < -16.0F) && !(var2.x > 32.0F) && !(var2.y > 32.0F) && !(var2.z > 32.0F)) {
            return var2;
         } else {
            throw new JsonParseException("'from' specifier exceeds the allowed boundaries: " + var2);
         }
      }

      private Vector3f a(JsonObject var1, String var2) {
         JsonArray var3 = JsonUtils.t(var1, var2);
         if (var3.size() != 3) {
            throw new JsonParseException("Expected 3 " + var2 + " values, found: " + var3.size());
         } else {
            float[] var4 = new float[3];

            for (int var5 = 0; var5 < var4.length; var5++) {
               var4[var5] = JsonUtils.d(var3.get(var5), var2 + "[" + var5 + "]");
            }

            return new Vector3f(var4[0], var4[1], var4[2]);
         }
      }
   }
}
