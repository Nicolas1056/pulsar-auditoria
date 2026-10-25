package net.minecraft.client.renderer;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class RenderHelper {
   private static FloatBuffer a = GLAllocation.h(16);
   private static final Vec3 b = new Vec3(0.2F, 1.0, -0.7F).a();
   private static final Vec3 c = new Vec3(-0.2F, 1.0, 0.7F).a();

   public static void a() {
      GlStateManager.f();
      GlStateManager.b(0);
      GlStateManager.b(1);
      GlStateManager.h();
   }

   public static void b() {
      GlStateManager.e();
      GlStateManager.a(0);
      GlStateManager.a(1);
      GlStateManager.g();
      GlStateManager.a(1032, 5634);
      float var0 = 0.4F;
      float var1 = 0.6F;
      float var2 = 0.0F;
      GL11.glLight(16384, 4611, a(b.a, b.b, b.c, 0.0));
      GL11.glLight(16384, 4609, a(var1, var1, var1, 1.0F));
      GL11.glLight(16384, 4608, a(0.0F, 0.0F, 0.0F, 1.0F));
      GL11.glLight(16384, 4610, a(var2, var2, var2, 1.0F));
      GL11.glLight(16385, 4611, a(c.a, c.b, c.c, 0.0));
      GL11.glLight(16385, 4609, a(var1, var1, var1, 1.0F));
      GL11.glLight(16385, 4608, a(0.0F, 0.0F, 0.0F, 1.0F));
      GL11.glLight(16385, 4610, a(var2, var2, var2, 1.0F));
      GlStateManager.j(7424);
      GL11.glLightModel(2899, a(var0, var0, var0, 1.0F));
   }

   private static FloatBuffer a(double var0, double var2, double var4, double var6) {
      return a((float)var0, (float)var2, (float)var4, (float)var6);
   }

   private static FloatBuffer a(float var0, float var1, float var2, float var3) {
      ((Buffer)a).clear();
      a.put(var0).put(var1).put(var2).put(var3);
      ((Buffer)a).flip();
      return a;
   }

   public static void c() {
      GlStateManager.E();
      GlStateManager.b(-30.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(165.0F, 1.0F, 0.0F, 0.0F);
      b();
      GlStateManager.F();
   }
}
