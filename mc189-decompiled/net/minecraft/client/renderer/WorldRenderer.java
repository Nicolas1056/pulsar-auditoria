package net.minecraft.client.renderer;

import com.google.common.primitives.Floats;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.MathHelper;
import org.apache.logging.log4j.LogManager;

public class WorldRenderer {
   private ByteBuffer a;
   private IntBuffer b;
   private ShortBuffer c;
   private FloatBuffer d;
   private int e;
   private VertexFormatElement f;
   private int g;
   private boolean h;
   private int i;
   private double j;
   private double k;
   private double l;
   private VertexFormat m;
   private boolean n;

   public WorldRenderer(int var1) {
      this.a = GLAllocation.c(var1 * 4);
      this.b = this.a.asIntBuffer();
      this.c = this.a.asShortBuffer();
      this.d = this.a.asFloatBuffer();
   }

   private void b(int var1) {
      if (var1 > this.b.remaining()) {
         int var2 = this.a.capacity();
         int var3 = var2 % 2097152;
         int var4 = var3 + (((this.b.position() + var1) * 4 - var3) / 2097152 + 1) * 2097152;
         LogManager.getLogger().warn("Needed to grow BufferBuilder buffer: Old size " + var2 + " bytes, new size " + var4 + " bytes.");
         int var5 = this.b.position();
         ByteBuffer var6 = GLAllocation.c(var4);
         ((Buffer)this.a).position(0);
         var6.put(this.a);
         ((Buffer)var6).rewind();
         this.a = var6;
         this.d = this.a.asFloatBuffer().asReadOnlyBuffer();
         this.b = this.a.asIntBuffer();
         ((Buffer)this.b).position(var5);
         this.c = this.a.asShortBuffer();
         ((Buffer)this.c).position(var5 << 1);
      }
   }

   public void a(float var1, float var2, float var3) {
      int var4 = this.e / 4;
      final float[] var5 = new float[var4];

      for (int var6 = 0; var6 < var4; var6++) {
         var5[var6] = a(this.d, (float)(var1 + this.j), (float)(var2 + this.k), (float)(var3 + this.l), this.m.f(), var6 * this.m.g());
      }

      Integer[] var15 = new Integer[var4];

      for (int var7 = 0; var7 < var15.length; var7++) {
         var15[var7] = var7;
      }

      Arrays.sort(var15, new Comparator<Integer>() {
         public int a(Integer var1, Integer var2x) {
            return Floats.compare(var5[var2x], var5[var1]);
         }
      });
      BitSet var16 = new BitSet();
      int var8 = this.m.g();
      int[] var9 = new int[var8];

      for (int var17 = 0; (var17 = var16.nextClearBit(var17)) < var15.length; var17++) {
         int var11 = var15[var17];
         if (var11 != var17) {
            ((Buffer)this.b).limit(var11 * var8 + var8);
            ((Buffer)this.b).position(var11 * var8);
            this.b.get(var9);
            int var12 = var11;

            for (int var13 = var15[var11]; var12 != var17; var13 = var15[var13]) {
               ((Buffer)this.b).limit(var13 * var8 + var8);
               ((Buffer)this.b).position(var13 * var8);
               IntBuffer var14 = this.b.slice();
               ((Buffer)this.b).limit(var12 * var8 + var8);
               ((Buffer)this.b).position(var12 * var8);
               this.b.put(var14);
               var16.set(var12);
               var12 = var13;
            }

            ((Buffer)this.b).limit(var17 * var8 + var8);
            ((Buffer)this.b).position(var17 * var8);
            this.b.put(var9);
         }

         var16.set(var17);
      }
   }

   public WorldRenderer.State a() {
      ((Buffer)this.b).rewind();
      int var1 = this.j();
      ((Buffer)this.b).limit(var1);
      int[] var2 = new int[var1];
      this.b.get(var2);
      ((Buffer)this.b).limit(this.b.capacity());
      ((Buffer)this.b).position(var1);
      return new WorldRenderer.State(var2, new VertexFormat(this.m));
   }

   private int j() {
      return this.e * this.m.f();
   }

   private static float a(FloatBuffer var0, float var1, float var2, float var3, int var4, int var5) {
      float var6 = var0.get(var5 + var4 * 0 + 0);
      float var7 = var0.get(var5 + var4 * 0 + 1);
      float var8 = var0.get(var5 + var4 * 0 + 2);
      float var9 = var0.get(var5 + var4 * 1 + 0);
      float var10 = var0.get(var5 + var4 * 1 + 1);
      float var11 = var0.get(var5 + var4 * 1 + 2);
      float var12 = var0.get(var5 + var4 * 2 + 0);
      float var13 = var0.get(var5 + var4 * 2 + 1);
      float var14 = var0.get(var5 + var4 * 2 + 2);
      float var15 = var0.get(var5 + var4 * 3 + 0);
      float var16 = var0.get(var5 + var4 * 3 + 1);
      float var17 = var0.get(var5 + var4 * 3 + 2);
      float var18 = (var6 + var9 + var12 + var15) * 0.25F - var1;
      float var19 = (var7 + var10 + var13 + var16) * 0.25F - var2;
      float var20 = (var8 + var11 + var14 + var17) * 0.25F - var3;
      return var18 * var18 + var19 * var19 + var20 * var20;
   }

   public void a(WorldRenderer.State var1) {
      ((Buffer)this.b).clear();
      this.b(var1.a().length);
      this.b.put(var1.a());
      this.e = var1.b();
      this.m = new VertexFormat(var1.c());
   }

   public void b() {
      this.e = 0;
      this.f = null;
      this.g = 0;
   }

   public void a(int var1, VertexFormat var2) {
      if (this.n) {
         throw new IllegalStateException("Already building!");
      } else {
         this.n = true;
         this.b();
         this.i = var1;
         this.m = var2;
         this.f = var2.c(this.g);
         this.h = false;
         ((Buffer)this.a).limit(this.a.capacity());
      }
   }

   public WorldRenderer a(double var1, double var3) {
      int var5 = this.e * this.m.g() + this.m.d(this.g);
      switch (this.f.a()) {
         case a:
            this.a.putFloat(var5, (float)var1);
            this.a.putFloat(var5 + 4, (float)var3);
            break;
         case f:
         case g:
            this.a.putInt(var5, (int)var1);
            this.a.putInt(var5 + 4, (int)var3);
            break;
         case d:
         case e:
            this.a.putShort(var5, (short)var3);
            this.a.putShort(var5 + 2, (short)var1);
            break;
         case b:
         case c:
            this.a.put(var5, (byte)var3);
            this.a.put(var5 + 1, (byte)var1);
      }

      this.k();
      return this;
   }

   public WorldRenderer a(int var1, int var2) {
      int var3 = this.e * this.m.g() + this.m.d(this.g);
      switch (this.f.a()) {
         case a:
            this.a.putFloat(var3, var1);
            this.a.putFloat(var3 + 4, var2);
            break;
         case f:
         case g:
            this.a.putInt(var3, var1);
            this.a.putInt(var3 + 4, var2);
            break;
         case d:
         case e:
            this.a.putShort(var3, (short)var2);
            this.a.putShort(var3 + 2, (short)var1);
            break;
         case b:
         case c:
            this.a.put(var3, (byte)var2);
            this.a.put(var3 + 1, (byte)var1);
      }

      this.k();
      return this;
   }

   public void a(int var1, int var2, int var3, int var4) {
      int var5 = (this.e - 4) * this.m.f() + this.m.b(1) / 4;
      int var6 = this.m.g() >> 2;
      this.b.put(var5, var1);
      this.b.put(var5 + var6, var2);
      this.b.put(var5 + var6 * 2, var3);
      this.b.put(var5 + var6 * 3, var4);
   }

   public void a(double var1, double var3, double var5) {
      int var7 = this.m.f();
      int var8 = (this.e - 4) * var7;

      for (int var9 = 0; var9 < 4; var9++) {
         int var10 = var8 + var9 * var7;
         int var11 = var10 + 1;
         int var12 = var11 + 1;
         this.b.put(var10, Float.floatToRawIntBits((float)(var1 + this.j) + Float.intBitsToFloat(this.b.get(var10))));
         this.b.put(var11, Float.floatToRawIntBits((float)(var3 + this.k) + Float.intBitsToFloat(this.b.get(var11))));
         this.b.put(var12, Float.floatToRawIntBits((float)(var5 + this.l) + Float.intBitsToFloat(this.b.get(var12))));
      }
   }

   private int c(int var1) {
      return ((this.e - var1) * this.m.g() + this.m.e()) / 4;
   }

   public void a(float var1, float var2, float var3, int var4) {
      int var5 = this.c(var4);
      int var6 = -1;
      if (!this.h) {
         var6 = this.b.get(var5);
         if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            int var7 = (int)((var6 & 0xFF) * var1);
            int var8 = (int)((var6 >> 8 & 0xFF) * var2);
            int var9 = (int)((var6 >> 16 & 0xFF) * var3);
            var6 &= -16777216;
            var6 |= var9 << 16 | var8 << 8 | var7;
         } else {
            int var13 = (int)((var6 >> 24 & 0xFF) * var1);
            int var14 = (int)((var6 >> 16 & 0xFF) * var2);
            int var15 = (int)((var6 >> 8 & 0xFF) * var3);
            var6 &= 255;
            var6 |= var13 << 24 | var14 << 16 | var15 << 8;
         }
      }

      this.b.put(var5, var6);
   }

   private void b(int var1, int var2) {
      int var3 = this.c(var2);
      int var4 = var1 >> 16 & 0xFF;
      int var5 = var1 >> 8 & 0xFF;
      int var6 = var1 & 0xFF;
      int var7 = var1 >> 24 & 0xFF;
      this.a(var3, var4, var5, var6, var7);
   }

   public void b(float var1, float var2, float var3, int var4) {
      int var5 = this.c(var4);
      int var6 = MathHelper.a((int)(var1 * 255.0F), 0, 255);
      int var7 = MathHelper.a((int)(var2 * 255.0F), 0, 255);
      int var8 = MathHelper.a((int)(var3 * 255.0F), 0, 255);
      this.a(var5, var6, var7, var8, 255);
   }

   private void a(int var1, int var2, int var3, int var4, int var5) {
      if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
         this.b.put(var1, var5 << 24 | var4 << 16 | var3 << 8 | var2);
      } else {
         this.b.put(var1, var2 << 24 | var3 << 16 | var4 << 8 | var5);
      }
   }

   public void c() {
      this.h = true;
   }

   public WorldRenderer a(float var1, float var2, float var3, float var4) {
      return this.b((int)(var1 * 255.0F), (int)(var2 * 255.0F), (int)(var3 * 255.0F), (int)(var4 * 255.0F));
   }

   public WorldRenderer b(int var1, int var2, int var3, int var4) {
      if (this.h) {
         return this;
      } else {
         int var5 = this.e * this.m.g() + this.m.d(this.g);
         switch (this.f.a()) {
            case a:
               this.a.putFloat(var5, var1 / 255.0F);
               this.a.putFloat(var5 + 4, var2 / 255.0F);
               this.a.putFloat(var5 + 8, var3 / 255.0F);
               this.a.putFloat(var5 + 12, var4 / 255.0F);
               break;
            case f:
            case g:
               this.a.putFloat(var5, var1);
               this.a.putFloat(var5 + 4, var2);
               this.a.putFloat(var5 + 8, var3);
               this.a.putFloat(var5 + 12, var4);
               break;
            case d:
            case e:
               this.a.putShort(var5, (short)var1);
               this.a.putShort(var5 + 2, (short)var2);
               this.a.putShort(var5 + 4, (short)var3);
               this.a.putShort(var5 + 6, (short)var4);
               break;
            case b:
            case c:
               if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
                  this.a.put(var5, (byte)var1);
                  this.a.put(var5 + 1, (byte)var2);
                  this.a.put(var5 + 2, (byte)var3);
                  this.a.put(var5 + 3, (byte)var4);
               } else {
                  this.a.put(var5, (byte)var4);
                  this.a.put(var5 + 1, (byte)var3);
                  this.a.put(var5 + 2, (byte)var2);
                  this.a.put(var5 + 3, (byte)var1);
               }
         }

         this.k();
         return this;
      }
   }

   public void a(int[] var1) {
      this.b(var1.length);
      ((Buffer)this.b).position(this.j());
      this.b.put(var1);
      this.e = this.e + var1.length / this.m.f();
   }

   public void d() {
      this.e++;
      this.b(this.m.f());
   }

   public WorldRenderer b(double var1, double var3, double var5) {
      int var7 = this.e * this.m.g() + this.m.d(this.g);
      switch (this.f.a()) {
         case a:
            this.a.putFloat(var7, (float)(var1 + this.j));
            this.a.putFloat(var7 + 4, (float)(var3 + this.k));
            this.a.putFloat(var7 + 8, (float)(var5 + this.l));
            break;
         case f:
         case g:
            this.a.putInt(var7, Float.floatToRawIntBits((float)(var1 + this.j)));
            this.a.putInt(var7 + 4, Float.floatToRawIntBits((float)(var3 + this.k)));
            this.a.putInt(var7 + 8, Float.floatToRawIntBits((float)(var5 + this.l)));
            break;
         case d:
         case e:
            this.a.putShort(var7, (short)(var1 + this.j));
            this.a.putShort(var7 + 2, (short)(var3 + this.k));
            this.a.putShort(var7 + 4, (short)(var5 + this.l));
            break;
         case b:
         case c:
            this.a.put(var7, (byte)(var1 + this.j));
            this.a.put(var7 + 1, (byte)(var3 + this.k));
            this.a.put(var7 + 2, (byte)(var5 + this.l));
      }

      this.k();
      return this;
   }

   public void b(float var1, float var2, float var3) {
      int var4 = (byte)(var1 * 127.0F) & 255;
      int var5 = (byte)(var2 * 127.0F) & 255;
      int var6 = (byte)(var3 * 127.0F) & 255;
      int var7 = var4 | var5 << 8 | var6 << 16;
      int var8 = this.m.g() >> 2;
      int var9 = (this.e - 4) * var8 + this.m.c() / 4;
      this.b.put(var9, var7);
      this.b.put(var9 + var8, var7);
      this.b.put(var9 + var8 * 2, var7);
      this.b.put(var9 + var8 * 3, var7);
   }

   private void k() {
      this.g++;
      this.g = this.g % this.m.i();
      this.f = this.m.c(this.g);
      if (this.f.b() == VertexFormatElement.EnumUsage.g) {
         this.k();
      }
   }

   public WorldRenderer c(float var1, float var2, float var3) {
      int var4 = this.e * this.m.g() + this.m.d(this.g);
      switch (this.f.a()) {
         case a:
            this.a.putFloat(var4, var1);
            this.a.putFloat(var4 + 4, var2);
            this.a.putFloat(var4 + 8, var3);
            break;
         case f:
         case g:
            this.a.putInt(var4, (int)var1);
            this.a.putInt(var4 + 4, (int)var2);
            this.a.putInt(var4 + 8, (int)var3);
            break;
         case d:
         case e:
            this.a.putShort(var4, (short)((int)var1 * 32767 & 65535));
            this.a.putShort(var4 + 2, (short)((int)var2 * 32767 & 65535));
            this.a.putShort(var4 + 4, (short)((int)var3 * 32767 & 65535));
            break;
         case b:
         case c:
            this.a.put(var4, (byte)((int)var1 * 127 & 0xFF));
            this.a.put(var4 + 1, (byte)((int)var2 * 127 & 0xFF));
            this.a.put(var4 + 2, (byte)((int)var3 * 127 & 0xFF));
      }

      this.k();
      return this;
   }

   public void c(double var1, double var3, double var5) {
      this.j = var1;
      this.k = var3;
      this.l = var5;
   }

   public void e() {
      if (!this.n) {
         throw new IllegalStateException("Not building!");
      } else {
         this.n = false;
         ((Buffer)this.a).position(0);
         ((Buffer)this.a).limit(this.j() * 4);
      }
   }

   public ByteBuffer f() {
      return this.a;
   }

   public VertexFormat g() {
      return this.m;
   }

   public int h() {
      return this.e;
   }

   public int i() {
      return this.i;
   }

   public void a(int var1) {
      for (int var2 = 0; var2 < 4; var2++) {
         this.b(var1, var2 + 1);
      }
   }

   public void d(float var1, float var2, float var3) {
      for (int var4 = 0; var4 < 4; var4++) {
         this.b(var1, var2, var3, var4 + 1);
      }
   }

   public class State {
      private final int[] b;
      private final VertexFormat c;

      public State(int[] var2, VertexFormat var3) {
         this.b = var2;
         this.c = var3;
      }

      public int[] a() {
         return this.b;
      }

      public int b() {
         return this.b.length / this.c.f();
      }

      public VertexFormat c() {
         return this.c;
      }
   }
}
