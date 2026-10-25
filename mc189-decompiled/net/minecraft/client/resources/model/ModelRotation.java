package net.minecraft.client.resources.model;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public enum ModelRotation {
   a(0, 0),
   b(0, 90),
   c(0, 180),
   d(0, 270),
   e(90, 0),
   f(90, 90),
   g(90, 180),
   h(90, 270),
   i(180, 0),
   j(180, 90),
   k(180, 180),
   l(180, 270),
   m(270, 0),
   n(270, 90),
   o(270, 180),
   p(270, 270);

   private static final Map<Integer, ModelRotation> q = Maps.newHashMap();
   private final int r;
   private final Matrix4f s;
   private final int t;
   private final int u;

   private static int b(int var0, int var1) {
      return var0 * 360 + var1;
   }

   private ModelRotation(int var3, int var4) {
      this.r = b(var3, var4);
      this.s = new Matrix4f();
      Matrix4f var5 = new Matrix4f();
      var5.setIdentity();
      Matrix4f.rotate(-var3 * (float) (Math.PI / 180.0), new Vector3f(1.0F, 0.0F, 0.0F), var5, var5);
      this.t = MathHelper.a(var3 / 90);
      Matrix4f var6 = new Matrix4f();
      var6.setIdentity();
      Matrix4f.rotate(-var4 * (float) (Math.PI / 180.0), new Vector3f(0.0F, 1.0F, 0.0F), var6, var6);
      this.u = MathHelper.a(var4 / 90);
      Matrix4f.mul(var6, var5, this.s);
   }

   public Matrix4f a() {
      return this.s;
   }

   public EnumFacing a(EnumFacing var1) {
      EnumFacing var2 = var1;

      for (int var3 = 0; var3 < this.t; var3++) {
         var2 = var2.a(EnumFacing.Axis.a);
      }

      if (var2.k() != EnumFacing.Axis.b) {
         for (int var4 = 0; var4 < this.u; var4++) {
            var2 = var2.a(EnumFacing.Axis.b);
         }
      }

      return var2;
   }

   public int a(EnumFacing var1, int var2) {
      int var3 = var2;
      if (var1.k() == EnumFacing.Axis.a) {
         var3 = (var2 + this.t) % 4;
      }

      EnumFacing var4 = var1;

      for (int var5 = 0; var5 < this.t; var5++) {
         var4 = var4.a(EnumFacing.Axis.a);
      }

      if (var4.k() == EnumFacing.Axis.b) {
         var3 = (var3 + this.u) % 4;
      }

      return var3;
   }

   public static ModelRotation a(int var0, int var1) {
      return q.get(b(MathHelper.b(var0, 360), MathHelper.b(var1, 360)));
   }

   static {
      for (ModelRotation var3 : values()) {
         q.put(var3.r, var3);
      }
   }
}
