package net.minecraft.client.audio;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.util.JsonUtils;
import org.apache.commons.lang3.Validate;

public class SoundListSerializer implements JsonDeserializer<SoundList> {
   public SoundList a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
      JsonObject var4 = JsonUtils.l(var1, "entry");
      SoundList var5 = new SoundList();
      var5.a(JsonUtils.a(var4, "replace", false));
      SoundCategory var6 = SoundCategory.a(JsonUtils.a(var4, "category", SoundCategory.a.a()));
      var5.a(var6);
      Validate.notNull(var6, "Invalid category", new Object[0]);
      if (var4.has("sounds")) {
         JsonArray var7 = JsonUtils.t(var4, "sounds");

         for (int var8 = 0; var8 < var7.size(); var8++) {
            JsonElement var9 = var7.get(var8);
            SoundList.SoundEntry var10 = new SoundList.SoundEntry();
            if (JsonUtils.a(var9)) {
               var10.a(JsonUtils.a(var9, "sound"));
            } else {
               JsonObject var11 = JsonUtils.l(var9, "sound");
               var10.a(JsonUtils.h(var11, "name"));
               if (var11.has("type")) {
                  SoundList.SoundEntry.Type var12 = SoundList.SoundEntry.Type.a(JsonUtils.h(var11, "type"));
                  Validate.notNull(var12, "Invalid type", new Object[0]);
                  var10.a(var12);
               }

               if (var11.has("volume")) {
                  float var13 = JsonUtils.k(var11, "volume");
                  Validate.isTrue(var13 > 0.0F, "Invalid volume", new Object[0]);
                  var10.a(var13);
               }

               if (var11.has("pitch")) {
                  float var14 = JsonUtils.k(var11, "pitch");
                  Validate.isTrue(var14 > 0.0F, "Invalid pitch", new Object[0]);
                  var10.b(var14);
               }

               if (var11.has("weight")) {
                  int var15 = JsonUtils.m(var11, "weight");
                  Validate.isTrue(var15 > 0, "Invalid weight", new Object[0]);
                  var10.a(var15);
               }

               if (var11.has("stream")) {
                  var10.a(JsonUtils.i(var11, "stream"));
               }
            }

            var5.a().add(var10);
         }
      }

      return var5;
   }
}
