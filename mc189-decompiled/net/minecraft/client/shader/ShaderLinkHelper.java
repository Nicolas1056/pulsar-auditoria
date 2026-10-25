package net.minecraft.client.shader;

import java.io.IOException;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.util.JsonException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShaderLinkHelper {
   private static final Logger a = LogManager.getLogger();
   private static ShaderLinkHelper b;

   public static void a() {
      b = new ShaderLinkHelper();
   }

   public static ShaderLinkHelper b() {
      return b;
   }

   private ShaderLinkHelper() {
   }

   public void a(ShaderManager var1) {
      var1.f().b(var1);
      var1.e().b(var1);
      OpenGlHelper.e(var1.h());
   }

   public int c() throws JsonException {
      int var1 = OpenGlHelper.d();
      if (var1 <= 0) {
         throw new JsonException("Could not create shader program (returned program ID " + var1 + ")");
      } else {
         return var1;
      }
   }

   public void b(ShaderManager var1) throws IOException {
      var1.f().a(var1);
      var1.e().a(var1);
      OpenGlHelper.f(var1.h());
      int var2 = OpenGlHelper.a(var1.h(), OpenGlHelper.m);
      if (var2 == 0) {
         a.warn("Error encountered when linking program containing VS " + var1.e().a() + " and FS " + var1.f().a() + ". Log output:");
         a.warn(OpenGlHelper.e(var1.h(), 32768));
      }
   }
}
