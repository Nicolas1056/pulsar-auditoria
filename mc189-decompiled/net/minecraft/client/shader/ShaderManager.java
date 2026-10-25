package net.minecraft.client.shader;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.util.JsonBlendingMode;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShaderManager {
   private static final Logger a = LogManager.getLogger();
   private static final ShaderDefault b = new ShaderDefault();
   private static ShaderManager c = null;
   private static int d = -1;
   private static boolean e = true;
   private final Map<String, Object> f = Maps.newHashMap();
   private final List<String> g = Lists.newArrayList();
   private final List<Integer> h = Lists.newArrayList();
   private final List<ShaderUniform> i = Lists.newArrayList();
   private final List<Integer> j = Lists.newArrayList();
   private final Map<String, ShaderUniform> k = Maps.newHashMap();
   private final int l;
   private final String m;
   private final boolean n;
   private boolean o;
   private final JsonBlendingMode p;
   private final List<Integer> q;
   private final List<String> r;
   private final ShaderLoader s;
   private final ShaderLoader t;

   public ShaderManager(IResourceManager var1, String var2) throws IOException {
      JsonParser var3 = new JsonParser();
      ResourceLocation var4 = new ResourceLocation("shaders/program/" + var2 + ".json");
      this.m = var2;
      InputStream var5 = null;

      try {
         var5 = var1.a(var4).b();
         JsonObject var6 = var3.parse(IOUtils.toString(var5, Charsets.UTF_8)).getAsJsonObject();
         String var7 = JsonUtils.h(var6, "vertex");
         String var28 = JsonUtils.h(var6, "fragment");
         JsonArray var9 = JsonUtils.a(var6, "samplers", null);
         if (var9 != null) {
            int var10 = 0;

            for (JsonElement var12 : var9) {
               try {
                  this.a(var12);
               } catch (Exception var25) {
                  JsonException var14 = JsonException.a(var25);
                  var14.a("samplers[" + var10 + "]");
                  throw var14;
               }

               var10++;
            }
         }

         JsonArray var29 = JsonUtils.a(var6, "attributes", null);
         if (var29 != null) {
            int var30 = 0;
            this.q = Lists.newArrayListWithCapacity(var29.size());
            this.r = Lists.newArrayListWithCapacity(var29.size());

            for (JsonElement var13 : var29) {
               try {
                  this.r.add(JsonUtils.a(var13, "attribute"));
               } catch (Exception var24) {
                  JsonException var15 = JsonException.a(var24);
                  var15.a("attributes[" + var30 + "]");
                  throw var15;
               }

               var30++;
            }
         } else {
            this.q = null;
            this.r = null;
         }

         JsonArray var31 = JsonUtils.a(var6, "uniforms", null);
         if (var31 != null) {
            int var33 = 0;

            for (JsonElement var37 : var31) {
               try {
                  this.b(var37);
               } catch (Exception var23) {
                  JsonException var16 = JsonException.a(var23);
                  var16.a("uniforms[" + var33 + "]");
                  throw var16;
               }

               var33++;
            }
         }

         this.p = JsonBlendingMode.a(JsonUtils.a(var6, "blend", null));
         this.n = JsonUtils.a(var6, "cull", true);
         this.s = ShaderLoader.a(var1, ShaderLoader.ShaderType.a, var7);
         this.t = ShaderLoader.a(var1, ShaderLoader.ShaderType.b, var28);
         this.l = ShaderLinkHelper.b().c();
         ShaderLinkHelper.b().b(this);
         this.i();
         if (this.r != null) {
            for (String var36 : this.r) {
               int var38 = OpenGlHelper.b(this.l, var36);
               this.q.add(var38);
            }
         }
      } catch (Exception var26) {
         JsonException var8 = JsonException.a(var26);
         var8.b(var4.a());
         throw var8;
      } finally {
         IOUtils.closeQuietly(var5);
      }

      this.d();
   }

   public void a() {
      ShaderLinkHelper.b().a(this);
   }

   public void b() {
      OpenGlHelper.d(0);
      d = -1;
      c = null;
      e = true;

      for (int var1 = 0; var1 < this.h.size(); var1++) {
         if (this.f.get(this.g.get(var1)) != null) {
            GlStateManager.g(OpenGlHelper.q + var1);
            GlStateManager.i(0);
         }
      }
   }

   public void c() {
      this.o = false;
      c = this;
      this.p.a();
      if (this.l != d) {
         OpenGlHelper.d(this.l);
         d = this.l;
      }

      if (this.n) {
         GlStateManager.o();
      } else {
         GlStateManager.p();
      }

      for (int var1 = 0; var1 < this.h.size(); var1++) {
         if (this.f.get(this.g.get(var1)) != null) {
            GlStateManager.g(OpenGlHelper.q + var1);
            GlStateManager.w();
            Object var2 = this.f.get(this.g.get(var1));
            int var3 = -1;
            if (var2 instanceof Framebuffer) {
               var3 = ((Framebuffer)var2).g;
            } else if (var2 instanceof ITextureObject) {
               var3 = ((ITextureObject)var2).b();
            } else if (var2 instanceof Integer) {
               var3 = (Integer)var2;
            }

            if (var3 != -1) {
               GlStateManager.i(var3);
               OpenGlHelper.f(OpenGlHelper.a(this.l, this.g.get(var1)), var1);
            }
         }
      }

      for (ShaderUniform var5 : this.i) {
         var5.b();
      }
   }

   public void d() {
      this.o = true;
   }

   public ShaderUniform a(String var1) {
      return this.k.containsKey(var1) ? this.k.get(var1) : null;
   }

   public ShaderUniform b(String var1) {
      return (ShaderUniform)(this.k.containsKey(var1) ? this.k.get(var1) : b);
   }

   private void i() {
      int var1 = 0;

      for (int var2 = 0; var1 < this.g.size(); var2++) {
         String var3 = this.g.get(var1);
         int var4 = OpenGlHelper.a(this.l, var3);
         if (var4 == -1) {
            a.warn("Shader " + this.m + "could not find sampler named " + var3 + " in the specified shader program.");
            this.f.remove(var3);
            this.g.remove(var2);
            var2--;
         } else {
            this.h.add(var4);
         }

         var1++;
      }

      for (ShaderUniform var6 : this.i) {
         String var7 = var6.a();
         int var8 = OpenGlHelper.a(this.l, var7);
         if (var8 == -1) {
            a.warn("Could not find uniform named " + var7 + " in the specified" + " shader program.");
         } else {
            this.j.add(var8);
            var6.b(var8);
            this.k.put(var7, var6);
         }
      }
   }

   private void a(JsonElement var1) throws JsonException {
      JsonObject var2 = JsonUtils.l(var1, "sampler");
      String var3 = JsonUtils.h(var2, "name");
      if (!JsonUtils.a(var2, "file")) {
         this.f.put(var3, null);
         this.g.add(var3);
      } else {
         this.g.add(var3);
      }
   }

   public void a(String var1, Object var2) {
      if (this.f.containsKey(var1)) {
         this.f.remove(var1);
      }

      this.f.put(var1, var2);
      this.d();
   }

   private void b(JsonElement var1) throws JsonException {
      JsonObject var2 = JsonUtils.l(var1, "uniform");
      String var3 = JsonUtils.h(var2, "name");
      int var4 = ShaderUniform.a(JsonUtils.h(var2, "type"));
      int var5 = JsonUtils.m(var2, "count");
      float[] var6 = new float[Math.max(var5, 16)];
      JsonArray var7 = JsonUtils.t(var2, "values");
      if (var7.size() != var5 && var7.size() > 1) {
         throw new JsonException("Invalid amount of values specified (expected " + var5 + ", found " + var7.size() + ")");
      } else {
         int var8 = 0;

         for (JsonElement var10 : var7) {
            try {
               var6[var8] = JsonUtils.d(var10, "value");
            } catch (Exception var13) {
               JsonException var12 = JsonException.a(var13);
               var12.a("values[" + var8 + "]");
               throw var12;
            }

            var8++;
         }

         if (var5 > 1 && var7.size() == 1) {
            while (var8 < var5) {
               var6[var8] = var6[0];
               var8++;
            }
         }

         int var14 = var5 > 1 && var5 <= 4 && var4 < 8 ? var5 - 1 : 0;
         ShaderUniform var15 = new ShaderUniform(var3, var4 + var14, var5, this);
         if (var4 <= 3) {
            var15.a((int)var6[0], (int)var6[1], (int)var6[2], (int)var6[3]);
         } else if (var4 <= 7) {
            var15.b(var6[0], var6[1], var6[2], var6[3]);
         } else {
            var15.a(var6);
         }

         this.i.add(var15);
      }
   }

   public ShaderLoader e() {
      return this.s;
   }

   public ShaderLoader f() {
      return this.t;
   }

   public int h() {
      return this.l;
   }
}
