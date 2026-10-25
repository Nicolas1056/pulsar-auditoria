package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.JsonUtils;

public class BlockPartFace {
   public static final EnumFacing a = null;
   public final EnumFacing b;
   public final int c;
   public final String d;
   public final BlockFaceUV e;

   public BlockPartFace(EnumFacing var1, int var2, String var3, BlockFaceUV var4) {
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
   }

   static class Deserializer implements JsonDeserializer<BlockPartFace> {
      public BlockPartFace a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject var4 = var1.getAsJsonObject();
         EnumFacing var5 = this.c(var4);
         int var6 = this.a(var4);
         String var7 = this.b(var4);
         BlockFaceUV var8 = (BlockFaceUV)var3.deserialize(var4, BlockFaceUV.class);
         return new BlockPartFace(var5, var6, var7, var8);
      }

      protected int a(JsonObject var1) {
         return JsonUtils.a(var1, "tintindex", -1);
      }

      private String b(JsonObject var1) {
         return JsonUtils.h(var1, "texture");
      }

      private EnumFacing c(JsonObject var1) {
         String var2 = JsonUtils.a(var1, "cullface", "");
         return EnumFacing.a(var2);
      }
   }
}
