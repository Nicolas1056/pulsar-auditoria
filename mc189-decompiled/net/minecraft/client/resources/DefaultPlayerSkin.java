package net.minecraft.client.resources;

import java.util.UUID;
import net.minecraft.util.ResourceLocation;

public class DefaultPlayerSkin {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/steve.png");
   private static final ResourceLocation b = new ResourceLocation("textures/entity/alex.png");

   public static ResourceLocation a() {
      return a;
   }

   public static ResourceLocation a(UUID var0) {
      return c(var0) ? b : a;
   }

   public static String b(UUID var0) {
      return c(var0) ? "slim" : "default";
   }

   private static boolean c(UUID var0) {
      return (var0.hashCode() & 1) == 1;
   }
}
