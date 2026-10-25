package net.minecraft.client.shader;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

public class ShaderGroup {
   private Framebuffer a;
   private IResourceManager b;
   private String c;
   private final List<Shader> d = Lists.newArrayList();
   private final Map<String, Framebuffer> e = Maps.newHashMap();
   private final List<Framebuffer> f = Lists.newArrayList();
   private Matrix4f g;
   private int h;
   private int i;
   private float j;
   private float k;

   public ShaderGroup(TextureManager var1, IResourceManager var2, Framebuffer var3, ResourceLocation var4) throws IOException, JsonSyntaxException {
      this.b = var2;
      this.a = var3;
      this.j = 0.0F;
      this.k = 0.0F;
      this.h = var3.c;
      this.i = var3.d;
      this.c = var4.toString();
      this.c();
      this.a(var1, var4);
   }

   public void a(TextureManager var1, ResourceLocation var2) throws IOException, JsonSyntaxException {
      JsonParser var3 = new JsonParser();
      InputStream var4 = null;

      try {
         IResource var5 = this.b.a(var2);
         var4 = var5.b();
         JsonObject var22 = var3.parse(IOUtils.toString(var4, Charsets.UTF_8)).getAsJsonObject();
         if (JsonUtils.d(var22, "targets")) {
            JsonArray var7 = var22.getAsJsonArray("targets");
            int var8 = 0;

            for (JsonElement var10 : var7) {
               try {
                  this.a(var10);
               } catch (Exception var19) {
                  JsonException var12 = JsonException.a(var19);
                  var12.a("targets[" + var8 + "]");
                  throw var12;
               }

               var8++;
            }
         }

         if (JsonUtils.d(var22, "passes")) {
            JsonArray var23 = var22.getAsJsonArray("passes");
            int var24 = 0;

            for (JsonElement var26 : var23) {
               try {
                  this.a(var1, var26);
               } catch (Exception var18) {
                  JsonException var27 = JsonException.a(var18);
                  var27.a("passes[" + var24 + "]");
                  throw var27;
               }

               var24++;
            }
         }
      } catch (Exception var20) {
         JsonException var6 = JsonException.a(var20);
         var6.b(var2.a());
         throw var6;
      } finally {
         IOUtils.closeQuietly(var4);
      }
   }

   private void a(JsonElement var1) throws JsonException {
      if (JsonUtils.a(var1)) {
         this.a(var1.getAsString(), this.h, this.i);
      } else {
         JsonObject var2 = JsonUtils.l(var1, "target");
         String var3 = JsonUtils.h(var2, "name");
         int var4 = JsonUtils.a(var2, "width", this.h);
         int var5 = JsonUtils.a(var2, "height", this.i);
         if (this.e.containsKey(var3)) {
            throw new JsonException(var3 + " is already defined");
         }

         this.a(var3, var4, var5);
      }
   }

   private void a(TextureManager var1, JsonElement var2) throws IOException {
      JsonObject var3 = JsonUtils.l(var2, "pass");
      String var4 = JsonUtils.h(var3, "name");
      String var5 = JsonUtils.h(var3, "intarget");
      String var6 = JsonUtils.h(var3, "outtarget");
      Framebuffer var7 = this.b(var5);
      Framebuffer var8 = this.b(var6);
      if (var7 == null) {
         throw new JsonException("Input target '" + var5 + "' does not exist");
      } else if (var8 == null) {
         throw new JsonException("Output target '" + var6 + "' does not exist");
      } else {
         Shader var9 = this.a(var4, var7, var8);
         JsonArray var10 = JsonUtils.a(var3, "auxtargets", null);
         if (var10 != null) {
            int var11 = 0;

            for (JsonElement var13 : var10) {
               try {
                  JsonObject var14 = JsonUtils.l(var13, "auxtarget");
                  String var30 = JsonUtils.h(var14, "name");
                  String var16 = JsonUtils.h(var14, "id");
                  Framebuffer var17 = this.b(var16);
                  if (var17 == null) {
                     ResourceLocation var18 = new ResourceLocation("textures/effect/" + var16 + ".png");

                     try {
                        this.b.a(var18);
                     } catch (FileNotFoundException var24) {
                        throw new JsonException("Render target or texture '" + var16 + "' does not exist");
                     }

                     var1.a(var18);
                     ITextureObject var19 = var1.b(var18);
                     int var20 = JsonUtils.m(var14, "width");
                     int var21 = JsonUtils.m(var14, "height");
                     boolean var22 = JsonUtils.i(var14, "bilinear");
                     if (var22) {
                        GL11.glTexParameteri(3553, 10241, 9729);
                        GL11.glTexParameteri(3553, 10240, 9729);
                     } else {
                        GL11.glTexParameteri(3553, 10241, 9728);
                        GL11.glTexParameteri(3553, 10240, 9728);
                     }

                     var9.a(var30, var19.b(), var20, var21);
                  } else {
                     var9.a(var30, var17, var17.a, var17.b);
                  }
               } catch (Exception var25) {
                  JsonException var15 = JsonException.a(var25);
                  var15.a("auxtargets[" + var11 + "]");
                  throw var15;
               }

               var11++;
            }
         }

         JsonArray var26 = JsonUtils.a(var3, "uniforms", null);
         if (var26 != null) {
            int var27 = 0;

            for (JsonElement var29 : var26) {
               try {
                  this.b(var29);
               } catch (Exception var23) {
                  JsonException var31 = JsonException.a(var23);
                  var31.a("uniforms[" + var27 + "]");
                  throw var31;
               }

               var27++;
            }
         }
      }
   }

   private void b(JsonElement var1) throws JsonException {
      JsonObject var2 = JsonUtils.l(var1, "uniform");
      String var3 = JsonUtils.h(var2, "name");
      ShaderUniform var4 = this.d.get(this.d.size() - 1).c().a(var3);
      if (var4 == null) {
         throw new JsonException("Uniform '" + var3 + "' does not exist");
      } else {
         float[] var5 = new float[4];
         int var6 = 0;

         for (JsonElement var9 : JsonUtils.t(var2, "values")) {
            try {
               var5[var6] = JsonUtils.d(var9, "value");
            } catch (Exception var12) {
               JsonException var11 = JsonException.a(var12);
               var11.a("values[" + var6 + "]");
               throw var11;
            }

            var6++;
         }

         switch (var6) {
            case 0:
            default:
               break;
            case 1:
               var4.a(var5[0]);
               break;
            case 2:
               var4.a(var5[0], var5[1]);
               break;
            case 3:
               var4.a(var5[0], var5[1], var5[2]);
               break;
            case 4:
               var4.a(var5[0], var5[1], var5[2], var5[3]);
         }
      }
   }

   public Framebuffer a(String var1) {
      return this.e.get(var1);
   }

   public void a(String var1, int var2, int var3) {
      Framebuffer var4 = new Framebuffer(var2, var3, true);
      var4.a(0.0F, 0.0F, 0.0F, 0.0F);
      this.e.put(var1, var4);
      if (var2 == this.h && var3 == this.i) {
         this.f.add(var4);
      }
   }

   public void a() {
      for (Framebuffer var2 : this.e.values()) {
         var2.a();
      }

      for (Shader var4 : this.d) {
         var4.b();
      }

      this.d.clear();
   }

   public Shader a(String var1, Framebuffer var2, Framebuffer var3) throws IOException {
      Shader var4 = new Shader(this.b, var1, var2, var3);
      this.d.add(this.d.size(), var4);
      return var4;
   }

   private void c() {
      this.g = new Matrix4f();
      this.g.setIdentity();
      this.g.m00 = 2.0F / this.a.a;
      this.g.m11 = 2.0F / -this.a.b;
      this.g.m22 = -0.0020001999F;
      this.g.m33 = 1.0F;
      this.g.m03 = -1.0F;
      this.g.m13 = 1.0F;
      this.g.m23 = -1.0001999F;
   }

   public void a(int var1, int var2) {
      this.h = this.a.a;
      this.i = this.a.b;
      this.c();

      for (Shader var4 : this.d) {
         var4.a(this.g);
      }

      for (Framebuffer var6 : this.f) {
         var6.a(var1, var2);
      }
   }

   public void a(float var1) {
      if (var1 < this.k) {
         this.j = this.j + (1.0F - this.k);
         this.j += var1;
      } else {
         this.j = this.j + (var1 - this.k);
      }

      this.k = var1;

      while (this.j > 20.0F) {
         this.j -= 20.0F;
      }

      for (Shader var3 : this.d) {
         var3.a(this.j / 20.0F);
      }
   }

   public final String b() {
      return this.c;
   }

   private Framebuffer b(String var1) {
      if (var1 == null) {
         return null;
      } else {
         return var1.equals("minecraft:main") ? this.a : this.e.get(var1);
      }
   }
}
