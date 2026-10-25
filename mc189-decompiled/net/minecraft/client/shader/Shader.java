package net.minecraft.client.shader;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResourceManager;
import org.lwjgl.util.vector.Matrix4f;

public class Shader {
   private final ShaderManager c;
   public final Framebuffer a;
   public final Framebuffer b;
   private final List<Object> d = Lists.newArrayList();
   private final List<String> e = Lists.newArrayList();
   private final List<Integer> f = Lists.newArrayList();
   private final List<Integer> g = Lists.newArrayList();
   private Matrix4f h;

   public Shader(IResourceManager var1, String var2, Framebuffer var3, Framebuffer var4) throws IOException {
      this.c = new ShaderManager(var1, var2);
      this.a = var3;
      this.b = var4;
   }

   public void b() {
      this.c.a();
   }

   public void a(String var1, Object var2, int var3, int var4) {
      this.e.add(this.e.size(), var1);
      this.d.add(this.d.size(), var2);
      this.f.add(this.f.size(), var3);
      this.g.add(this.g.size(), var4);
   }

   private void d() {
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.k();
      GlStateManager.i();
      GlStateManager.c();
      GlStateManager.n();
      GlStateManager.f();
      GlStateManager.h();
      GlStateManager.w();
      GlStateManager.i(0);
   }

   public void a(Matrix4f var1) {
      this.h = var1;
   }

   public void a(float var1) {
      this.d();
      this.a.e();
      float var2 = this.b.a;
      float var3 = this.b.b;
      GlStateManager.b(0, 0, (int)var2, (int)var3);
      this.c.a("DiffuseSampler", this.a);

      for (int var4 = 0; var4 < this.d.size(); var4++) {
         this.c.a(this.e.get(var4), this.d.get(var4));
         this.c.b("AuxSize" + var4).a(this.f.get(var4).intValue(), this.g.get(var4).intValue());
      }

      this.c.b("ProjMat").a(this.h);
      this.c.b("InSize").a(this.a.a, this.a.b);
      this.c.b("OutSize").a(var2, var3);
      this.c.b("Time").a(var1);
      Minecraft var9 = Minecraft.A();
      this.c.b("ScreenSize").a(var9.d, var9.e);
      this.c.c();
      this.b.f();
      this.b.a(false);
      GlStateManager.a(false);
      GlStateManager.a(true, true, true, true);
      Tessellator var5 = Tessellator.a();
      WorldRenderer var6 = var5.c();
      var6.a(7, DefaultVertexFormats.f);
      var6.b(0.0, (double)var3, 500.0).b(255, 255, 255, 255).d();
      var6.b((double)var2, (double)var3, 500.0).b(255, 255, 255, 255).d();
      var6.b((double)var2, 0.0, 500.0).b(255, 255, 255, 255).d();
      var6.b(0.0, 0.0, 500.0).b(255, 255, 255, 255).d();
      var5.b();
      GlStateManager.a(true);
      GlStateManager.a(true, true, true, true);
      this.c.b();
      this.b.e();
      this.a.d();

      for (Object var8 : this.d) {
         if (var8 instanceof Framebuffer) {
            ((Framebuffer)var8).d();
         }
      }
   }

   public ShaderManager c() {
      return this.c;
   }
}
