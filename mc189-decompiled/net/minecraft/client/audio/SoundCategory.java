package net.minecraft.client.audio;

import com.google.common.collect.Maps;
import java.util.Map;

public enum SoundCategory {
   a("master", 0),
   b("music", 1),
   c("record", 2),
   d("weather", 3),
   e("block", 4),
   f("hostile", 5),
   g("neutral", 6),
   h("player", 7),
   i("ambient", 8);

   private static final Map<String, SoundCategory> j = Maps.newHashMap();
   private static final Map<Integer, SoundCategory> k = Maps.newHashMap();
   private final String l;
   private final int m;

   private SoundCategory(String var3, int var4) {
      this.l = var3;
      this.m = var4;
   }

   public String a() {
      return this.l;
   }

   public int b() {
      return this.m;
   }

   public static SoundCategory a(String var0) {
      return j.get(var0);
   }

   static {
      for (SoundCategory var3 : values()) {
         if (j.containsKey(var3.a()) || k.containsKey(var3.b())) {
            throw new Error("Clash in Sound Category ID & Name pools! Cannot insert " + var3);
         }

         j.put(var3.a(), var3);
         k.put(var3.b(), var3);
      }
   }
}
