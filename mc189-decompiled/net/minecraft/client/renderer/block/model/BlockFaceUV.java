package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.util.JsonUtils;

public class BlockFaceUV {
   public float[] a;
   public final int b;

   public BlockFaceUV(float[] var1, int var2) {
      this.a = var1;
      this.b = var2;
   }

   public float a(int var1) {
      if (this.a == null) {
         throw new NullPointerException("uvs");
      } else {
         int var2 = this.d(var1);
         return var2 != 0 && var2 != 1 ? this.a[2] : this.a[0];
      }
   }

   public float b(int var1) {
      if (this.a == null) {
         throw new NullPointerException("uvs");
      } else {
         int var2 = this.d(var1);
         return var2 != 0 && var2 != 3 ? this.a[3] : this.a[1];
      }
   }

   private int d(int var1) {
      return (var1 + this.b / 90) % 4;
   }

   public int c(int var1) {
      return (var1 + (4 - this.b / 90)) % 4;
   }

   public void a(float[] var1) {
      if (this.a == null) {
         this.a = var1;
      }
   }

   static class Deserializer implements JsonDeserializer<BlockFaceUV> {
      public BlockFaceUV a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject var4 = var1.getAsJsonObject();
         float[] var5 = this.b(var4);
         int var6 = this.a(var4);
         return new BlockFaceUV(var5, var6);
      }

      protected int a(JsonObject var1) {
         int var2 = JsonUtils.a(var1, "rotation", 0);
         if (var2 >= 0 && var2 % 90 == 0 && var2 / 90 <= 3) {
            return var2;
         } else {
            throw new JsonParseException("Invalid rotation " + var2 + " found, only 0/90/180/270 allowed");
         }
      }

      private float[] b(JsonObject var1) {
         if (!var1.has("uv")) {
            return null;
         } else {
            JsonArray var2 = JsonUtils.t(var1, "uv");
            if (var2.size() != 4) {
               throw new JsonParseException("Expected 4 uv values, found: " + var2.size());
            } else {
               float[] var3 = new float[4];

               for (int var4 = 0; var4 < var3.length; var4++) {
                  var3[var4] = JsonUtils.d(var2.get(var4), "uv[" + var4 + "]");
               }

               return var3;
            }
         }
      }
   }
}
