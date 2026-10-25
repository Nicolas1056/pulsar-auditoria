package net.minecraft.client.renderer.culling;

public class ClippingHelper {
   public float[][] a = new float[6][4];
   public float[] b = new float[16];
   public float[] c = new float[16];
   public float[] d = new float[16];

   private double a(float[] var1, double var2, double var4, double var6) {
      return var1[0] * var2 + var1[1] * var4 + var1[2] * var6 + var1[3];
   }

   public boolean b(double var1, double var3, double var5, double var7, double var9, double var11) {
      for (int var13 = 0; var13 < 6; var13++) {
         float[] var14 = this.a[var13];
         if (!(this.a(var14, var1, var3, var5) > 0.0)
            && !(this.a(var14, var7, var3, var5) > 0.0)
            && !(this.a(var14, var1, var9, var5) > 0.0)
            && !(this.a(var14, var7, var9, var5) > 0.0)
            && !(this.a(var14, var1, var3, var11) > 0.0)
            && !(this.a(var14, var7, var3, var11) > 0.0)
            && !(this.a(var14, var1, var9, var11) > 0.0)
            && !(this.a(var14, var7, var9, var11) > 0.0)) {
            return false;
         }
      }

      return true;
   }
}
