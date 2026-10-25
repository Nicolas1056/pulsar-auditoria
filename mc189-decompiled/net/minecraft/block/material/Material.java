package net.minecraft.block.material;

public class Material {
   public static final Material a = new MaterialTransparent(MapColor.b);
   public static final Material b = new Material(MapColor.c);
   public static final Material c = new Material(MapColor.l);
   public static final Material d = new Material(MapColor.o).g();
   public static final Material e = new Material(MapColor.m).f();
   public static final Material f = new Material(MapColor.h).f();
   public static final Material g = new Material(MapColor.h).f().o();
   public static final Material h = new MaterialLiquid(MapColor.n).n();
   public static final Material i = new MaterialLiquid(MapColor.f).n();
   public static final Material j = new Material(MapColor.i).g().s().n();
   public static final Material k = new MaterialLogic(MapColor.i).n();
   public static final Material l = new MaterialLogic(MapColor.i).g().n().i();
   public static final Material m = new Material(MapColor.t);
   public static final Material n = new Material(MapColor.e).g();
   public static final Material o = new MaterialTransparent(MapColor.b).n();
   public static final Material p = new Material(MapColor.d);
   public static final Material q = new MaterialLogic(MapColor.b).n();
   public static final Material r = new MaterialLogic(MapColor.e).g();
   public static final Material s = new Material(MapColor.b).s().p();
   public static final Material t = new Material(MapColor.b).p();
   public static final Material u = new Material(MapColor.f).g().s();
   public static final Material v = new Material(MapColor.i).n();
   public static final Material w = new Material(MapColor.g).s().p();
   public static final Material x = new Material(MapColor.g).p();
   public static final Material y = new MaterialLogic(MapColor.j).i().s().f().n();
   public static final Material z = new Material(MapColor.j).f();
   public static final Material A = new Material(MapColor.i).s().n();
   public static final Material B = new Material(MapColor.k);
   public static final Material C = new Material(MapColor.i).n();
   public static final Material D = new Material(MapColor.i).n();
   public static final Material E = new MaterialPortal(MapColor.b).o();
   public static final Material F = new Material(MapColor.b).n();
   public static final Material G = (new Material(MapColor.e) {
      @Override
      public boolean c() {
         return false;
      }
   }).f().n();
   public static final Material H = new Material(MapColor.m).o();
   public static final Material I = new Material(MapColor.b).f().o();
   private boolean J;
   private boolean K;
   private boolean L;
   private final MapColor M;
   private boolean N = true;
   private int O;
   private boolean P;

   public Material(MapColor var1) {
      this.M = var1;
   }

   public boolean d() {
      return false;
   }

   public boolean a() {
      return true;
   }

   public boolean b() {
      return true;
   }

   public boolean c() {
      return true;
   }

   private Material s() {
      this.L = true;
      return this;
   }

   protected Material f() {
      this.N = false;
      return this;
   }

   protected Material g() {
      this.J = true;
      return this;
   }

   public boolean h() {
      return this.J;
   }

   public Material i() {
      this.K = true;
      return this;
   }

   public boolean j() {
      return this.K;
   }

   public boolean k() {
      return this.L ? false : this.c();
   }

   public boolean l() {
      return this.N;
   }

   public int m() {
      return this.O;
   }

   protected Material n() {
      this.O = 1;
      return this;
   }

   protected Material o() {
      this.O = 2;
      return this;
   }

   protected Material p() {
      this.P = true;
      return this;
   }

   public MapColor r() {
      return this.M;
   }
}
