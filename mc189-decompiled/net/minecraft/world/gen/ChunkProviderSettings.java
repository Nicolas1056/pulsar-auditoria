package net.minecraft.world.gen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import net.minecraft.util.JsonUtils;
import net.minecraft.world.biome.BiomeGenBase;

public class ChunkProviderSettings {
   public final float a;
   public final float b;
   public final float c;
   public final float d;
   public final float e;
   public final float f;
   public final float g;
   public final float h;
   public final float i;
   public final float j;
   public final float k;
   public final float l;
   public final float m;
   public final float n;
   public final float o;
   public final float p;
   public final int q;
   public final boolean r;
   public final boolean s;
   public final int t;
   public final boolean u;
   public final boolean v;
   public final boolean w;
   public final boolean x;
   public final boolean y;
   public final boolean z;
   public final boolean A;
   public final int B;
   public final boolean C;
   public final int D;
   public final boolean E;
   public final int F;
   public final int G;
   public final int H;
   public final int I;
   public final int J;
   public final int K;
   public final int L;
   public final int M;
   public final int N;
   public final int O;
   public final int P;
   public final int Q;
   public final int R;
   public final int S;
   public final int T;
   public final int U;
   public final int V;
   public final int W;
   public final int X;
   public final int Y;
   public final int Z;
   public final int aa;
   public final int ab;
   public final int ac;
   public final int ad;
   public final int ae;
   public final int af;
   public final int ag;
   public final int ah;
   public final int ai;
   public final int aj;
   public final int ak;
   public final int al;
   public final int am;
   public final int an;
   public final int ao;
   public final int ap;
   public final int aq;
   public final int ar;
   public final int as;
   public final int at;
   public final int au;
   public final int av;
   public final int aw;
   public final int ax;
   public final int ay;
   public final int az;

   private ChunkProviderSettings(ChunkProviderSettings.Factory var1) {
      this.a = var1.b;
      this.b = var1.c;
      this.c = var1.d;
      this.d = var1.e;
      this.e = var1.f;
      this.f = var1.g;
      this.g = var1.h;
      this.h = var1.i;
      this.i = var1.j;
      this.j = var1.k;
      this.k = var1.l;
      this.l = var1.m;
      this.m = var1.n;
      this.n = var1.o;
      this.o = var1.p;
      this.p = var1.q;
      this.q = var1.r;
      this.r = var1.s;
      this.s = var1.t;
      this.t = var1.u;
      this.u = var1.v;
      this.v = var1.w;
      this.w = var1.x;
      this.x = var1.y;
      this.y = var1.z;
      this.z = var1.A;
      this.A = var1.B;
      this.B = var1.C;
      this.C = var1.D;
      this.D = var1.E;
      this.E = var1.F;
      this.F = var1.G;
      this.G = var1.H;
      this.H = var1.I;
      this.I = var1.J;
      this.J = var1.K;
      this.K = var1.L;
      this.L = var1.M;
      this.M = var1.N;
      this.N = var1.O;
      this.O = var1.P;
      this.P = var1.Q;
      this.Q = var1.R;
      this.R = var1.S;
      this.S = var1.T;
      this.T = var1.U;
      this.U = var1.V;
      this.V = var1.W;
      this.W = var1.X;
      this.X = var1.Y;
      this.Y = var1.Z;
      this.Z = var1.aa;
      this.aa = var1.ab;
      this.ab = var1.ac;
      this.ac = var1.ad;
      this.ad = var1.ae;
      this.ae = var1.af;
      this.af = var1.ag;
      this.ag = var1.ah;
      this.ah = var1.ai;
      this.ai = var1.aj;
      this.aj = var1.ak;
      this.ak = var1.al;
      this.al = var1.am;
      this.am = var1.an;
      this.an = var1.ao;
      this.ao = var1.ap;
      this.ap = var1.aq;
      this.aq = var1.ar;
      this.ar = var1.as;
      this.as = var1.at;
      this.at = var1.au;
      this.au = var1.av;
      this.av = var1.aw;
      this.aw = var1.ax;
      this.ax = var1.ay;
      this.ay = var1.az;
      this.az = var1.aA;
   }

   public static class Factory {
      static final Gson a = new GsonBuilder().registerTypeAdapter(ChunkProviderSettings.Factory.class, new ChunkProviderSettings.Serializer()).create();
      public float b = 684.412F;
      public float c = 684.412F;
      public float d = 512.0F;
      public float e = 512.0F;
      public float f = 200.0F;
      public float g = 200.0F;
      public float h = 0.5F;
      public float i = 80.0F;
      public float j = 160.0F;
      public float k = 80.0F;
      public float l = 8.5F;
      public float m = 12.0F;
      public float n = 1.0F;
      public float o = 0.0F;
      public float p = 1.0F;
      public float q = 0.0F;
      public int r = 63;
      public boolean s = true;
      public boolean t = true;
      public int u = 8;
      public boolean v = true;
      public boolean w = true;
      public boolean x = true;
      public boolean y = true;
      public boolean z = true;
      public boolean A = true;
      public boolean B = true;
      public int C = 4;
      public boolean D = true;
      public int E = 80;
      public boolean F = false;
      public int G = -1;
      public int H = 4;
      public int I = 4;
      public int J = 33;
      public int K = 10;
      public int L = 0;
      public int M = 256;
      public int N = 33;
      public int O = 8;
      public int P = 0;
      public int Q = 256;
      public int R = 33;
      public int S = 10;
      public int T = 0;
      public int U = 80;
      public int V = 33;
      public int W = 10;
      public int X = 0;
      public int Y = 80;
      public int Z = 33;
      public int aa = 10;
      public int ab = 0;
      public int ac = 80;
      public int ad = 17;
      public int ae = 20;
      public int af = 0;
      public int ag = 128;
      public int ah = 9;
      public int ai = 20;
      public int aj = 0;
      public int ak = 64;
      public int al = 9;
      public int am = 2;
      public int an = 0;
      public int ao = 32;
      public int ap = 8;
      public int aq = 8;
      public int ar = 0;
      public int as = 16;
      public int at = 8;
      public int au = 1;
      public int av = 0;
      public int aw = 16;
      public int ax = 7;
      public int ay = 1;
      public int az = 16;
      public int aA = 16;

      public static ChunkProviderSettings.Factory a(String var0) {
         if (var0.length() == 0) {
            return new ChunkProviderSettings.Factory();
         } else {
            try {
               return (ChunkProviderSettings.Factory)a.fromJson(var0, ChunkProviderSettings.Factory.class);
            } catch (Exception var2) {
               return new ChunkProviderSettings.Factory();
            }
         }
      }

      @Override
      public String toString() {
         return a.toJson(this);
      }

      public Factory() {
         this.a();
      }

      public void a() {
         this.b = 684.412F;
         this.c = 684.412F;
         this.d = 512.0F;
         this.e = 512.0F;
         this.f = 200.0F;
         this.g = 200.0F;
         this.h = 0.5F;
         this.i = 80.0F;
         this.j = 160.0F;
         this.k = 80.0F;
         this.l = 8.5F;
         this.m = 12.0F;
         this.n = 1.0F;
         this.o = 0.0F;
         this.p = 1.0F;
         this.q = 0.0F;
         this.r = 63;
         this.s = true;
         this.t = true;
         this.u = 8;
         this.v = true;
         this.w = true;
         this.x = true;
         this.y = true;
         this.z = true;
         this.A = true;
         this.B = true;
         this.C = 4;
         this.D = true;
         this.E = 80;
         this.F = false;
         this.G = -1;
         this.H = 4;
         this.I = 4;
         this.J = 33;
         this.K = 10;
         this.L = 0;
         this.M = 256;
         this.N = 33;
         this.O = 8;
         this.P = 0;
         this.Q = 256;
         this.R = 33;
         this.S = 10;
         this.T = 0;
         this.U = 80;
         this.V = 33;
         this.W = 10;
         this.X = 0;
         this.Y = 80;
         this.Z = 33;
         this.aa = 10;
         this.ab = 0;
         this.ac = 80;
         this.ad = 17;
         this.ae = 20;
         this.af = 0;
         this.ag = 128;
         this.ah = 9;
         this.ai = 20;
         this.aj = 0;
         this.ak = 64;
         this.al = 9;
         this.am = 2;
         this.an = 0;
         this.ao = 32;
         this.ap = 8;
         this.aq = 8;
         this.ar = 0;
         this.as = 16;
         this.at = 8;
         this.au = 1;
         this.av = 0;
         this.aw = 16;
         this.ax = 7;
         this.ay = 1;
         this.az = 16;
         this.aA = 16;
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (var1 != null && this.getClass() == var1.getClass()) {
            ChunkProviderSettings.Factory var2 = (ChunkProviderSettings.Factory)var1;
            if (this.aa != var2.aa) {
               return false;
            } else if (this.ac != var2.ac) {
               return false;
            } else if (this.ab != var2.ab) {
               return false;
            } else if (this.Z != var2.Z) {
               return false;
            } else if (Float.compare(var2.l, this.l) != 0) {
               return false;
            } else if (Float.compare(var2.o, this.o) != 0) {
               return false;
            } else if (Float.compare(var2.n, this.n) != 0) {
               return false;
            } else if (Float.compare(var2.q, this.q) != 0) {
               return false;
            } else if (Float.compare(var2.p, this.p) != 0) {
               return false;
            } else if (this.H != var2.H) {
               return false;
            } else if (this.ae != var2.ae) {
               return false;
            } else if (this.ag != var2.ag) {
               return false;
            } else if (this.af != var2.af) {
               return false;
            } else if (this.ad != var2.ad) {
               return false;
            } else if (Float.compare(var2.b, this.b) != 0) {
               return false;
            } else if (Float.compare(var2.h, this.h) != 0) {
               return false;
            } else if (Float.compare(var2.f, this.f) != 0) {
               return false;
            } else if (Float.compare(var2.g, this.g) != 0) {
               return false;
            } else if (this.au != var2.au) {
               return false;
            } else if (this.aw != var2.aw) {
               return false;
            } else if (this.av != var2.av) {
               return false;
            } else if (this.at != var2.at) {
               return false;
            } else if (this.W != var2.W) {
               return false;
            } else if (this.Y != var2.Y) {
               return false;
            } else if (this.X != var2.X) {
               return false;
            } else if (this.V != var2.V) {
               return false;
            } else if (this.K != var2.K) {
               return false;
            } else if (this.M != var2.M) {
               return false;
            } else if (this.L != var2.L) {
               return false;
            } else if (this.J != var2.J) {
               return false;
            } else if (this.u != var2.u) {
               return false;
            } else if (this.G != var2.G) {
               return false;
            } else if (this.am != var2.am) {
               return false;
            } else if (this.ao != var2.ao) {
               return false;
            } else if (this.an != var2.an) {
               return false;
            } else if (this.al != var2.al) {
               return false;
            } else if (this.S != var2.S) {
               return false;
            } else if (this.U != var2.U) {
               return false;
            } else if (this.T != var2.T) {
               return false;
            } else if (this.R != var2.R) {
               return false;
            } else if (this.O != var2.O) {
               return false;
            } else if (this.Q != var2.Q) {
               return false;
            } else if (this.P != var2.P) {
               return false;
            } else if (this.N != var2.N) {
               return false;
            } else if (Float.compare(var2.c, this.c) != 0) {
               return false;
            } else if (this.ai != var2.ai) {
               return false;
            } else if (this.ak != var2.ak) {
               return false;
            } else if (this.aj != var2.aj) {
               return false;
            } else if (this.ah != var2.ah) {
               return false;
            } else if (this.az != var2.az) {
               return false;
            } else if (this.ay != var2.ay) {
               return false;
            } else if (this.ax != var2.ax) {
               return false;
            } else if (this.aA != var2.aA) {
               return false;
            } else if (this.E != var2.E) {
               return false;
            } else if (Float.compare(var2.e, this.e) != 0) {
               return false;
            } else if (Float.compare(var2.i, this.i) != 0) {
               return false;
            } else if (Float.compare(var2.j, this.j) != 0) {
               return false;
            } else if (Float.compare(var2.k, this.k) != 0) {
               return false;
            } else if (this.aq != var2.aq) {
               return false;
            } else if (this.as != var2.as) {
               return false;
            } else if (this.ar != var2.ar) {
               return false;
            } else if (this.ap != var2.ap) {
               return false;
            } else if (this.I != var2.I) {
               return false;
            } else if (this.r != var2.r) {
               return false;
            } else if (Float.compare(var2.m, this.m) != 0) {
               return false;
            } else if (Float.compare(var2.d, this.d) != 0) {
               return false;
            } else if (this.s != var2.s) {
               return false;
            } else if (this.t != var2.t) {
               return false;
            } else if (this.D != var2.D) {
               return false;
            } else if (this.F != var2.F) {
               return false;
            } else if (this.x != var2.x) {
               return false;
            } else if (this.A != var2.A) {
               return false;
            } else if (this.v != var2.v) {
               return false;
            } else if (this.y != var2.y) {
               return false;
            } else if (this.z != var2.z) {
               return false;
            } else if (this.w != var2.w) {
               return false;
            } else {
               return this.B != var2.B ? false : this.C == var2.C;
            }
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         int var1 = this.b != 0.0F ? Float.floatToIntBits(this.b) : 0;
         var1 = 31 * var1 + (this.c != 0.0F ? Float.floatToIntBits(this.c) : 0);
         var1 = 31 * var1 + (this.d != 0.0F ? Float.floatToIntBits(this.d) : 0);
         var1 = 31 * var1 + (this.e != 0.0F ? Float.floatToIntBits(this.e) : 0);
         var1 = 31 * var1 + (this.f != 0.0F ? Float.floatToIntBits(this.f) : 0);
         var1 = 31 * var1 + (this.g != 0.0F ? Float.floatToIntBits(this.g) : 0);
         var1 = 31 * var1 + (this.h != 0.0F ? Float.floatToIntBits(this.h) : 0);
         var1 = 31 * var1 + (this.i != 0.0F ? Float.floatToIntBits(this.i) : 0);
         var1 = 31 * var1 + (this.j != 0.0F ? Float.floatToIntBits(this.j) : 0);
         var1 = 31 * var1 + (this.k != 0.0F ? Float.floatToIntBits(this.k) : 0);
         var1 = 31 * var1 + (this.l != 0.0F ? Float.floatToIntBits(this.l) : 0);
         var1 = 31 * var1 + (this.m != 0.0F ? Float.floatToIntBits(this.m) : 0);
         var1 = 31 * var1 + (this.n != 0.0F ? Float.floatToIntBits(this.n) : 0);
         var1 = 31 * var1 + (this.o != 0.0F ? Float.floatToIntBits(this.o) : 0);
         var1 = 31 * var1 + (this.p != 0.0F ? Float.floatToIntBits(this.p) : 0);
         var1 = 31 * var1 + (this.q != 0.0F ? Float.floatToIntBits(this.q) : 0);
         var1 = 31 * var1 + this.r;
         var1 = 31 * var1 + (this.s ? 1 : 0);
         var1 = 31 * var1 + (this.t ? 1 : 0);
         var1 = 31 * var1 + this.u;
         var1 = 31 * var1 + (this.v ? 1 : 0);
         var1 = 31 * var1 + (this.w ? 1 : 0);
         var1 = 31 * var1 + (this.x ? 1 : 0);
         var1 = 31 * var1 + (this.y ? 1 : 0);
         var1 = 31 * var1 + (this.z ? 1 : 0);
         var1 = 31 * var1 + (this.A ? 1 : 0);
         var1 = 31 * var1 + (this.B ? 1 : 0);
         var1 = 31 * var1 + this.C;
         var1 = 31 * var1 + (this.D ? 1 : 0);
         var1 = 31 * var1 + this.E;
         var1 = 31 * var1 + (this.F ? 1 : 0);
         var1 = 31 * var1 + this.G;
         var1 = 31 * var1 + this.H;
         var1 = 31 * var1 + this.I;
         var1 = 31 * var1 + this.J;
         var1 = 31 * var1 + this.K;
         var1 = 31 * var1 + this.L;
         var1 = 31 * var1 + this.M;
         var1 = 31 * var1 + this.N;
         var1 = 31 * var1 + this.O;
         var1 = 31 * var1 + this.P;
         var1 = 31 * var1 + this.Q;
         var1 = 31 * var1 + this.R;
         var1 = 31 * var1 + this.S;
         var1 = 31 * var1 + this.T;
         var1 = 31 * var1 + this.U;
         var1 = 31 * var1 + this.V;
         var1 = 31 * var1 + this.W;
         var1 = 31 * var1 + this.X;
         var1 = 31 * var1 + this.Y;
         var1 = 31 * var1 + this.Z;
         var1 = 31 * var1 + this.aa;
         var1 = 31 * var1 + this.ab;
         var1 = 31 * var1 + this.ac;
         var1 = 31 * var1 + this.ad;
         var1 = 31 * var1 + this.ae;
         var1 = 31 * var1 + this.af;
         var1 = 31 * var1 + this.ag;
         var1 = 31 * var1 + this.ah;
         var1 = 31 * var1 + this.ai;
         var1 = 31 * var1 + this.aj;
         var1 = 31 * var1 + this.ak;
         var1 = 31 * var1 + this.al;
         var1 = 31 * var1 + this.am;
         var1 = 31 * var1 + this.an;
         var1 = 31 * var1 + this.ao;
         var1 = 31 * var1 + this.ap;
         var1 = 31 * var1 + this.aq;
         var1 = 31 * var1 + this.ar;
         var1 = 31 * var1 + this.as;
         var1 = 31 * var1 + this.at;
         var1 = 31 * var1 + this.au;
         var1 = 31 * var1 + this.av;
         var1 = 31 * var1 + this.aw;
         var1 = 31 * var1 + this.ax;
         var1 = 31 * var1 + this.ay;
         var1 = 31 * var1 + this.az;
         return 31 * var1 + this.aA;
      }

      public ChunkProviderSettings b() {
         return new ChunkProviderSettings(this);
      }
   }

   public static class Serializer implements JsonDeserializer<ChunkProviderSettings.Factory>, JsonSerializer<ChunkProviderSettings.Factory> {
      public ChunkProviderSettings.Factory a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject var4 = var1.getAsJsonObject();
         ChunkProviderSettings.Factory var5 = new ChunkProviderSettings.Factory();

         try {
            var5.b = JsonUtils.a(var4, "coordinateScale", var5.b);
            var5.c = JsonUtils.a(var4, "heightScale", var5.c);
            var5.e = JsonUtils.a(var4, "lowerLimitScale", var5.e);
            var5.d = JsonUtils.a(var4, "upperLimitScale", var5.d);
            var5.f = JsonUtils.a(var4, "depthNoiseScaleX", var5.f);
            var5.g = JsonUtils.a(var4, "depthNoiseScaleZ", var5.g);
            var5.h = JsonUtils.a(var4, "depthNoiseScaleExponent", var5.h);
            var5.i = JsonUtils.a(var4, "mainNoiseScaleX", var5.i);
            var5.j = JsonUtils.a(var4, "mainNoiseScaleY", var5.j);
            var5.k = JsonUtils.a(var4, "mainNoiseScaleZ", var5.k);
            var5.l = JsonUtils.a(var4, "baseSize", var5.l);
            var5.m = JsonUtils.a(var4, "stretchY", var5.m);
            var5.n = JsonUtils.a(var4, "biomeDepthWeight", var5.n);
            var5.o = JsonUtils.a(var4, "biomeDepthOffset", var5.o);
            var5.p = JsonUtils.a(var4, "biomeScaleWeight", var5.p);
            var5.q = JsonUtils.a(var4, "biomeScaleOffset", var5.q);
            var5.r = JsonUtils.a(var4, "seaLevel", var5.r);
            var5.s = JsonUtils.a(var4, "useCaves", var5.s);
            var5.t = JsonUtils.a(var4, "useDungeons", var5.t);
            var5.u = JsonUtils.a(var4, "dungeonChance", var5.u);
            var5.v = JsonUtils.a(var4, "useStrongholds", var5.v);
            var5.w = JsonUtils.a(var4, "useVillages", var5.w);
            var5.x = JsonUtils.a(var4, "useMineShafts", var5.x);
            var5.y = JsonUtils.a(var4, "useTemples", var5.y);
            var5.z = JsonUtils.a(var4, "useMonuments", var5.z);
            var5.A = JsonUtils.a(var4, "useRavines", var5.A);
            var5.B = JsonUtils.a(var4, "useWaterLakes", var5.B);
            var5.C = JsonUtils.a(var4, "waterLakeChance", var5.C);
            var5.D = JsonUtils.a(var4, "useLavaLakes", var5.D);
            var5.E = JsonUtils.a(var4, "lavaLakeChance", var5.E);
            var5.F = JsonUtils.a(var4, "useLavaOceans", var5.F);
            var5.G = JsonUtils.a(var4, "fixedBiome", var5.G);
            if (var5.G >= 38 || var5.G < -1) {
               var5.G = -1;
            } else if (var5.G >= BiomeGenBase.x.az) {
               var5.G += 2;
            }

            var5.H = JsonUtils.a(var4, "biomeSize", var5.H);
            var5.I = JsonUtils.a(var4, "riverSize", var5.I);
            var5.J = JsonUtils.a(var4, "dirtSize", var5.J);
            var5.K = JsonUtils.a(var4, "dirtCount", var5.K);
            var5.L = JsonUtils.a(var4, "dirtMinHeight", var5.L);
            var5.M = JsonUtils.a(var4, "dirtMaxHeight", var5.M);
            var5.N = JsonUtils.a(var4, "gravelSize", var5.N);
            var5.O = JsonUtils.a(var4, "gravelCount", var5.O);
            var5.P = JsonUtils.a(var4, "gravelMinHeight", var5.P);
            var5.Q = JsonUtils.a(var4, "gravelMaxHeight", var5.Q);
            var5.R = JsonUtils.a(var4, "graniteSize", var5.R);
            var5.S = JsonUtils.a(var4, "graniteCount", var5.S);
            var5.T = JsonUtils.a(var4, "graniteMinHeight", var5.T);
            var5.U = JsonUtils.a(var4, "graniteMaxHeight", var5.U);
            var5.V = JsonUtils.a(var4, "dioriteSize", var5.V);
            var5.W = JsonUtils.a(var4, "dioriteCount", var5.W);
            var5.X = JsonUtils.a(var4, "dioriteMinHeight", var5.X);
            var5.Y = JsonUtils.a(var4, "dioriteMaxHeight", var5.Y);
            var5.Z = JsonUtils.a(var4, "andesiteSize", var5.Z);
            var5.aa = JsonUtils.a(var4, "andesiteCount", var5.aa);
            var5.ab = JsonUtils.a(var4, "andesiteMinHeight", var5.ab);
            var5.ac = JsonUtils.a(var4, "andesiteMaxHeight", var5.ac);
            var5.ad = JsonUtils.a(var4, "coalSize", var5.ad);
            var5.ae = JsonUtils.a(var4, "coalCount", var5.ae);
            var5.af = JsonUtils.a(var4, "coalMinHeight", var5.af);
            var5.ag = JsonUtils.a(var4, "coalMaxHeight", var5.ag);
            var5.ah = JsonUtils.a(var4, "ironSize", var5.ah);
            var5.ai = JsonUtils.a(var4, "ironCount", var5.ai);
            var5.aj = JsonUtils.a(var4, "ironMinHeight", var5.aj);
            var5.ak = JsonUtils.a(var4, "ironMaxHeight", var5.ak);
            var5.al = JsonUtils.a(var4, "goldSize", var5.al);
            var5.am = JsonUtils.a(var4, "goldCount", var5.am);
            var5.an = JsonUtils.a(var4, "goldMinHeight", var5.an);
            var5.ao = JsonUtils.a(var4, "goldMaxHeight", var5.ao);
            var5.ap = JsonUtils.a(var4, "redstoneSize", var5.ap);
            var5.aq = JsonUtils.a(var4, "redstoneCount", var5.aq);
            var5.ar = JsonUtils.a(var4, "redstoneMinHeight", var5.ar);
            var5.as = JsonUtils.a(var4, "redstoneMaxHeight", var5.as);
            var5.at = JsonUtils.a(var4, "diamondSize", var5.at);
            var5.au = JsonUtils.a(var4, "diamondCount", var5.au);
            var5.av = JsonUtils.a(var4, "diamondMinHeight", var5.av);
            var5.aw = JsonUtils.a(var4, "diamondMaxHeight", var5.aw);
            var5.ax = JsonUtils.a(var4, "lapisSize", var5.ax);
            var5.ay = JsonUtils.a(var4, "lapisCount", var5.ay);
            var5.az = JsonUtils.a(var4, "lapisCenterHeight", var5.az);
            var5.aA = JsonUtils.a(var4, "lapisSpread", var5.aA);
         } catch (Exception var7) {
         }

         return var5;
      }

      public JsonElement a(ChunkProviderSettings.Factory var1, Type var2, JsonSerializationContext var3) {
         JsonObject var4 = new JsonObject();
         var4.addProperty("coordinateScale", var1.b);
         var4.addProperty("heightScale", var1.c);
         var4.addProperty("lowerLimitScale", var1.e);
         var4.addProperty("upperLimitScale", var1.d);
         var4.addProperty("depthNoiseScaleX", var1.f);
         var4.addProperty("depthNoiseScaleZ", var1.g);
         var4.addProperty("depthNoiseScaleExponent", var1.h);
         var4.addProperty("mainNoiseScaleX", var1.i);
         var4.addProperty("mainNoiseScaleY", var1.j);
         var4.addProperty("mainNoiseScaleZ", var1.k);
         var4.addProperty("baseSize", var1.l);
         var4.addProperty("stretchY", var1.m);
         var4.addProperty("biomeDepthWeight", var1.n);
         var4.addProperty("biomeDepthOffset", var1.o);
         var4.addProperty("biomeScaleWeight", var1.p);
         var4.addProperty("biomeScaleOffset", var1.q);
         var4.addProperty("seaLevel", var1.r);
         var4.addProperty("useCaves", var1.s);
         var4.addProperty("useDungeons", var1.t);
         var4.addProperty("dungeonChance", var1.u);
         var4.addProperty("useStrongholds", var1.v);
         var4.addProperty("useVillages", var1.w);
         var4.addProperty("useMineShafts", var1.x);
         var4.addProperty("useTemples", var1.y);
         var4.addProperty("useMonuments", var1.z);
         var4.addProperty("useRavines", var1.A);
         var4.addProperty("useWaterLakes", var1.B);
         var4.addProperty("waterLakeChance", var1.C);
         var4.addProperty("useLavaLakes", var1.D);
         var4.addProperty("lavaLakeChance", var1.E);
         var4.addProperty("useLavaOceans", var1.F);
         var4.addProperty("fixedBiome", var1.G);
         var4.addProperty("biomeSize", var1.H);
         var4.addProperty("riverSize", var1.I);
         var4.addProperty("dirtSize", var1.J);
         var4.addProperty("dirtCount", var1.K);
         var4.addProperty("dirtMinHeight", var1.L);
         var4.addProperty("dirtMaxHeight", var1.M);
         var4.addProperty("gravelSize", var1.N);
         var4.addProperty("gravelCount", var1.O);
         var4.addProperty("gravelMinHeight", var1.P);
         var4.addProperty("gravelMaxHeight", var1.Q);
         var4.addProperty("graniteSize", var1.R);
         var4.addProperty("graniteCount", var1.S);
         var4.addProperty("graniteMinHeight", var1.T);
         var4.addProperty("graniteMaxHeight", var1.U);
         var4.addProperty("dioriteSize", var1.V);
         var4.addProperty("dioriteCount", var1.W);
         var4.addProperty("dioriteMinHeight", var1.X);
         var4.addProperty("dioriteMaxHeight", var1.Y);
         var4.addProperty("andesiteSize", var1.Z);
         var4.addProperty("andesiteCount", var1.aa);
         var4.addProperty("andesiteMinHeight", var1.ab);
         var4.addProperty("andesiteMaxHeight", var1.ac);
         var4.addProperty("coalSize", var1.ad);
         var4.addProperty("coalCount", var1.ae);
         var4.addProperty("coalMinHeight", var1.af);
         var4.addProperty("coalMaxHeight", var1.ag);
         var4.addProperty("ironSize", var1.ah);
         var4.addProperty("ironCount", var1.ai);
         var4.addProperty("ironMinHeight", var1.aj);
         var4.addProperty("ironMaxHeight", var1.ak);
         var4.addProperty("goldSize", var1.al);
         var4.addProperty("goldCount", var1.am);
         var4.addProperty("goldMinHeight", var1.an);
         var4.addProperty("goldMaxHeight", var1.ao);
         var4.addProperty("redstoneSize", var1.ap);
         var4.addProperty("redstoneCount", var1.aq);
         var4.addProperty("redstoneMinHeight", var1.ar);
         var4.addProperty("redstoneMaxHeight", var1.as);
         var4.addProperty("diamondSize", var1.at);
         var4.addProperty("diamondCount", var1.au);
         var4.addProperty("diamondMinHeight", var1.av);
         var4.addProperty("diamondMaxHeight", var1.aw);
         var4.addProperty("lapisSize", var1.ax);
         var4.addProperty("lapisCount", var1.ay);
         var4.addProperty("lapisCenterHeight", var1.az);
         var4.addProperty("lapisSpread", var1.aA);
         return var4;
      }
   }
}
