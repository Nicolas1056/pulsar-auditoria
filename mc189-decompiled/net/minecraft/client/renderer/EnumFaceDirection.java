package net.minecraft.client.renderer;

import net.minecraft.util.EnumFacing;

public enum EnumFaceDirection {
   a(
      new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.f, EnumFaceDirection.Constants.e, EnumFaceDirection.Constants.a),
      new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.f, EnumFaceDirection.Constants.e, EnumFaceDirection.Constants.d),
      new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.c, EnumFaceDirection.Constants.e, EnumFaceDirection.Constants.d),
      new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.c, EnumFaceDirection.Constants.e, EnumFaceDirection.Constants.a)
   ),
   b(
      new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.f, EnumFaceDirection.Constants.b, EnumFaceDirection.Constants.d),
      new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.f, EnumFaceDirection.Constants.b, EnumFaceDirection.Constants.a),
      new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.c, EnumFaceDirection.Constants.b, EnumFaceDirection.Constants.a),
      new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.c, EnumFaceDirection.Constants.b, EnumFaceDirection.Constants.d)
   ),
   c(
      new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.c, EnumFaceDirection.Constants.b, EnumFaceDirection.Constants.d),
      new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.c, EnumFaceDirection.Constants.e, EnumFaceDirection.Constants.d),
      new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.f, EnumFaceDirection.Constants.e, EnumFaceDirection.Constants.d),
      new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.f, EnumFaceDirection.Constants.b, EnumFaceDirection.Constants.d)
   ),
   d(
      new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.f, EnumFaceDirection.Constants.b, EnumFaceDirection.Constants.a),
      new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.f, EnumFaceDirection.Constants.e, EnumFaceDirection.Constants.a),
      new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.c, EnumFaceDirection.Constants.e, EnumFaceDirection.Constants.a),
      new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.c, EnumFaceDirection.Constants.b, EnumFaceDirection.Constants.a)
   ),
   e(
      new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.f, EnumFaceDirection.Constants.b, EnumFaceDirection.Constants.d),
      new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.f, EnumFaceDirection.Constants.e, EnumFaceDirection.Constants.d),
      new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.f, EnumFaceDirection.Constants.e, EnumFaceDirection.Constants.a),
      new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.f, EnumFaceDirection.Constants.b, EnumFaceDirection.Constants.a)
   ),
   f(
      new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.c, EnumFaceDirection.Constants.b, EnumFaceDirection.Constants.a),
      new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.c, EnumFaceDirection.Constants.e, EnumFaceDirection.Constants.a),
      new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.c, EnumFaceDirection.Constants.e, EnumFaceDirection.Constants.d),
      new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.c, EnumFaceDirection.Constants.b, EnumFaceDirection.Constants.d)
   );

   private static final EnumFaceDirection[] g = new EnumFaceDirection[6];
   private final EnumFaceDirection.VertexInformation[] h;

   public static EnumFaceDirection a(EnumFacing var0) {
      return g[var0.a()];
   }

   private EnumFaceDirection(EnumFaceDirection.VertexInformation... var3) {
      this.h = var3;
   }

   public EnumFaceDirection.VertexInformation a(int var1) {
      return this.h[var1];
   }

   static {
      g[EnumFaceDirection.Constants.e] = a;
      g[EnumFaceDirection.Constants.b] = b;
      g[EnumFaceDirection.Constants.d] = c;
      g[EnumFaceDirection.Constants.a] = d;
      g[EnumFaceDirection.Constants.f] = e;
      g[EnumFaceDirection.Constants.c] = f;
   }

   public static final class Constants {
      public static final int a = EnumFacing.d.a();
      public static final int b = EnumFacing.b.a();
      public static final int c = EnumFacing.f.a();
      public static final int d = EnumFacing.c.a();
      public static final int e = EnumFacing.a.a();
      public static final int f = EnumFacing.e.a();
   }

   public static class VertexInformation {
      public final int a;
      public final int b;
      public final int c;

      private VertexInformation(int var1, int var2, int var3) {
         this.a = var1;
         this.b = var2;
         this.c = var3;
      }
   }
}
