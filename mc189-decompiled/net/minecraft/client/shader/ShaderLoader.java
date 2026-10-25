package net.minecraft.client.shader;

import com.google.common.collect.Maps;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Map;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.BufferUtils;

public class ShaderLoader {
   private final ShaderLoader.ShaderType a;
   private final String b;
   private int c;
   private int d = 0;

   private ShaderLoader(ShaderLoader.ShaderType var1, int var2, String var3) {
      this.a = var1;
      this.c = var2;
      this.b = var3;
   }

   public void a(ShaderManager var1) {
      this.d++;
      OpenGlHelper.b(var1.h(), this.c);
   }

   public void b(ShaderManager var1) {
      this.d--;
      if (this.d <= 0) {
         OpenGlHelper.a(this.c);
         this.a.d().remove(this.b);
      }
   }

   public String a() {
      return this.b;
   }

   public static ShaderLoader a(IResourceManager var0, ShaderLoader.ShaderType var1, String var2) throws IOException {
      ShaderLoader var3 = var1.d().get(var2);
      if (var3 == null) {
         ResourceLocation var4 = new ResourceLocation("shaders/program/" + var2 + var1.b());
         BufferedInputStream var5 = new BufferedInputStream(var0.a(var4).b());
         byte[] var6 = a(var5);
         ByteBuffer var7 = BufferUtils.createByteBuffer(var6.length);
         var7.put(var6);
         ((Buffer)var7).position(0);
         int var8 = OpenGlHelper.b(var1.c());
         OpenGlHelper.a(var8, var7);
         OpenGlHelper.c(var8);
         if (OpenGlHelper.c(var8, OpenGlHelper.n) == 0) {
            String var9 = StringUtils.trim(OpenGlHelper.d(var8, 32768));
            JsonException var10 = new JsonException("Couldn't compile " + var1.a() + " program: " + var9);
            var10.b(var4.a());
            throw var10;
         }

         var3 = new ShaderLoader(var1, var8, var2);
         var1.d().put(var2, var3);
      }

      return var3;
   }

   protected static byte[] a(BufferedInputStream var0) throws IOException {
      byte[] var1;
      try {
         var1 = IOUtils.toByteArray(var0);
      } finally {
         var0.close();
      }

      return var1;
   }

   public static enum ShaderType {
      a("vertex", ".vsh", OpenGlHelper.o),
      b("fragment", ".fsh", OpenGlHelper.p);

      private final String c;
      private final String d;
      private final int e;
      private final Map<String, ShaderLoader> f = Maps.newHashMap();

      private ShaderType(String var3, String var4, int var5) {
         this.c = var3;
         this.d = var4;
         this.e = var5;
      }

      public String a() {
         return this.c;
      }

      protected String b() {
         return this.d;
      }

      protected int c() {
         return this.e;
      }

      protected Map<String, ShaderLoader> d() {
         return this.f;
      }
   }
}
