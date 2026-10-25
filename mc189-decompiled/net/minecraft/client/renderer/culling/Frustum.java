package net.minecraft.client.renderer.culling;

import net.minecraft.util.AxisAlignedBB;

public class Frustum implements ICamera {
   private ClippingHelper a;
   private double b;
   private double c;
   private double d;

   public Frustum() {
      this(ClippingHelperImpl.a());
   }

   public Frustum(ClippingHelper var1) {
      this.a = var1;
   }

   @Override
   public void a(double var1, double var3, double var5) {
      this.b = var1;
      this.c = var3;
      this.d = var5;
   }

   public boolean b(double var1, double var3, double var5, double var7, double var9, double var11) {
      return this.a.b(var1 - this.b, var3 - this.c, var5 - this.d, var7 - this.b, var9 - this.c, var11 - this.d);
   }

   @Override
   public boolean a(AxisAlignedBB var1) {
      return this.b(var1.a, var1.b, var1.c, var1.d, var1.e, var1.f);
   }
}
