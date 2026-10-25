package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.MathHelper;
import org.lwjgl.util.vector.Vector3f;

public class ItemTransformVec3f {
   public static final ItemTransformVec3f a = new ItemTransformVec3f(new Vector3f(), new Vector3f(), new Vector3f(1.0F, 1.0F, 1.0F));
   public final Vector3f b;
   public final Vector3f c;
   public final Vector3f d;

   public ItemTransformVec3f(Vector3f var1, Vector3f var2, Vector3f var3) {
      this.b = new Vector3f(var1);
      this.c = new Vector3f(var2);
      this.d = new Vector3f(var3);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (this.getClass() != var1.getClass()) {
         return false;
      } else {
         ItemTransformVec3f var2 = (ItemTransformVec3f)var1;
         if (!this.b.equals(var2.b)) {
            return false;
         } else {
            return !this.d.equals(var2.d) ? false : this.c.equals(var2.c);
         }
      }
   }

   @Override
   public int hashCode() {
      int var1 = this.b.hashCode();
      var1 = 31 * var1 + this.c.hashCode();
      return 31 * var1 + this.d.hashCode();
   }

   static class Deserializer implements JsonDeserializer<ItemTransformVec3f> {
      private static final Vector3f a = new Vector3f(0.0F, 0.0F, 0.0F);
      private static final Vector3f b = new Vector3f(0.0F, 0.0F, 0.0F);
      private static final Vector3f c = new Vector3f(1.0F, 1.0F, 1.0F);

      public ItemTransformVec3f a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject var4 = var1.getAsJsonObject();
         Vector3f var5 = this.a(var4, "rotation", a);
         Vector3f var6 = this.a(var4, "translation", b);
         var6.scale(0.0625F);
         var6.x = MathHelper.a(var6.x, -1.5F, 1.5F);
         var6.y = MathHelper.a(var6.y, -1.5F, 1.5F);
         var6.z = MathHelper.a(var6.z, -1.5F, 1.5F);
         Vector3f var7 = this.a(var4, "scale", c);
         var7.x = MathHelper.a(var7.x, -4.0F, 4.0F);
         var7.y = MathHelper.a(var7.y, -4.0F, 4.0F);
         var7.z = MathHelper.a(var7.z, -4.0F, 4.0F);
         return new ItemTransformVec3f(var5, var6, var7);
      }

      private Vector3f a(JsonObject var1, String var2, Vector3f var3) {
         if (!var1.has(var2)) {
            return var3;
         } else {
            JsonArray var4 = JsonUtils.t(var1, var2);
            if (var4.size() != 3) {
               throw new JsonParseException("Expected 3 " + var2 + " values, found: " + var4.size());
            } else {
               float[] var5 = new float[3];

               for (int var6 = 0; var6 < var5.length; var6++) {
                  var5[var6] = JsonUtils.d(var4.get(var6), var2 + "[" + var6 + "]");
               }

               return new Vector3f(var5[0], var5[1], var5[2]);
            }
         }
      }
   }
}
