package net.minecraft.client.gui;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IntHashMap;

public class GuiPageButtonList extends GuiListExtended {
   private final List<GuiPageButtonList.GuiEntry> u = Lists.newArrayList();
   private final IntHashMap<Gui> v = new IntHashMap<>();
   private final List<GuiTextField> w = Lists.newArrayList();
   private final GuiPageButtonList.GuiListEntry[][] x;
   private int y;
   private GuiPageButtonList.GuiResponder z;
   private Gui A;

   public GuiPageButtonList(
      Minecraft var1, int var2, int var3, int var4, int var5, int var6, GuiPageButtonList.GuiResponder var7, GuiPageButtonList.GuiListEntry[]... var8
   ) {
      super(var1, var2, var3, var4, var5, var6);
      this.z = var7;
      this.x = var8;
      this.k = false;
      this.s();
      this.t();
   }

   private void s() {
      for (GuiPageButtonList.GuiListEntry[] var4 : this.x) {
         for (byte var5 = 0; var5 < var4.length; var5 += 2) {
            GuiPageButtonList.GuiListEntry var6 = var4[var5];
            GuiPageButtonList.GuiListEntry var7 = var5 < var4.length - 1 ? var4[var5 + 1] : null;
            Gui var8 = this.a(var6, 0, var7 == null);
            Gui var9 = this.a(var7, 160, var6 == null);
            GuiPageButtonList.GuiEntry var10 = new GuiPageButtonList.GuiEntry(var8, var9);
            this.u.add(var10);
            if (var6 != null && var8 != null) {
               this.v.a(var6.b(), var8);
               if (var8 instanceof GuiTextField) {
                  this.w.add((GuiTextField)var8);
               }
            }

            if (var7 != null && var9 != null) {
               this.v.a(var7.b(), var9);
               if (var9 instanceof GuiTextField) {
                  this.w.add((GuiTextField)var9);
               }
            }
         }
      }
   }

   private void t() {
      this.u.clear();

      for (byte var1 = 0; var1 < this.x[this.y].length; var1 += 2) {
         GuiPageButtonList.GuiListEntry var2 = this.x[this.y][var1];
         GuiPageButtonList.GuiListEntry var3 = var1 < this.x[this.y].length - 1 ? this.x[this.y][var1 + 1] : null;
         Gui var4 = this.v.a(var2.b());
         Gui var5 = var3 != null ? this.v.a(var3.b()) : null;
         GuiPageButtonList.GuiEntry var6 = new GuiPageButtonList.GuiEntry(var4, var5);
         this.u.add(var6);
      }
   }

   public void c(int var1) {
      if (var1 != this.y) {
         int var2 = this.y;
         this.y = var1;
         this.t();
         this.e(var2, var1);
         this.n = 0.0F;
      }
   }

   public int e() {
      return this.y;
   }

   public int f() {
      return this.x.length;
   }

   public Gui g() {
      return this.A;
   }

   public void h() {
      if (this.y > 0) {
         this.c(this.y - 1);
      }
   }

   public void i() {
      if (this.y < this.x.length - 1) {
         this.c(this.y + 1);
      }
   }

   public Gui d(int var1) {
      return this.v.a(var1);
   }

   private void e(int var1, int var2) {
      for (GuiPageButtonList.GuiListEntry var6 : this.x[var1]) {
         if (var6 != null) {
            this.a(this.v.a(var6.b()), false);
         }
      }

      for (GuiPageButtonList.GuiListEntry var10 : this.x[var2]) {
         if (var10 != null) {
            this.a(this.v.a(var10.b()), true);
         }
      }
   }

   private void a(Gui var1, boolean var2) {
      if (var1 instanceof GuiButton) {
         ((GuiButton)var1).m = var2;
      } else if (var1 instanceof GuiTextField) {
         ((GuiTextField)var1).e(var2);
      } else if (var1 instanceof GuiLabel) {
         ((GuiLabel)var1).j = var2;
      }
   }

   private Gui a(GuiPageButtonList.GuiListEntry var1, int var2, boolean var3) {
      if (var1 instanceof GuiPageButtonList.GuiSlideEntry) {
         return this.a(this.b / 2 - 155 + var2, 0, (GuiPageButtonList.GuiSlideEntry)var1);
      } else if (var1 instanceof GuiPageButtonList.GuiButtonEntry) {
         return this.a(this.b / 2 - 155 + var2, 0, (GuiPageButtonList.GuiButtonEntry)var1);
      } else if (var1 instanceof GuiPageButtonList.EditBoxEntry) {
         return this.a(this.b / 2 - 155 + var2, 0, (GuiPageButtonList.EditBoxEntry)var1);
      } else {
         return var1 instanceof GuiPageButtonList.GuiLabelEntry ? this.a(this.b / 2 - 155 + var2, 0, (GuiPageButtonList.GuiLabelEntry)var1, var3) : null;
      }
   }

   public void a(boolean var1) {
      for (GuiPageButtonList.GuiEntry var3 : this.u) {
         if (var3.b instanceof GuiButton) {
            ((GuiButton)var3.b).l = var1;
         }

         if (var3.c instanceof GuiButton) {
            ((GuiButton)var3.c).l = var1;
         }
      }
   }

   @Override
   public boolean b(int var1, int var2, int var3) {
      boolean var4 = super.b(var1, var2, var3);
      int var5 = this.c(var1, var2);
      if (var5 >= 0) {
         GuiPageButtonList.GuiEntry var6 = this.e(var5);
         if (this.A != var6.d && this.A != null && this.A instanceof GuiTextField) {
            ((GuiTextField)this.A).b(false);
         }

         this.A = var6.d;
      }

      return var4;
   }

   private GuiSlider a(int var1, int var2, GuiPageButtonList.GuiSlideEntry var3) {
      GuiSlider var4 = new GuiSlider(this.z, var3.b(), var1, var2, var3.c(), var3.e(), var3.f(), var3.g(), var3.a());
      var4.m = var3.d();
      return var4;
   }

   private GuiListButton a(int var1, int var2, GuiPageButtonList.GuiButtonEntry var3) {
      GuiListButton var4 = new GuiListButton(this.z, var3.b(), var1, var2, var3.c(), var3.a());
      var4.m = var3.d();
      return var4;
   }

   private GuiTextField a(int var1, int var2, GuiPageButtonList.EditBoxEntry var3) {
      GuiTextField var4 = new GuiTextField(var3.b(), this.a.k, var1, var2, 150, 20);
      var4.a(var3.c());
      var4.a(this.z);
      var4.e(var3.d());
      var4.a(var3.a());
      return var4;
   }

   private GuiLabel a(int var1, int var2, GuiPageButtonList.GuiLabelEntry var3, boolean var4) {
      GuiLabel var5;
      if (var4) {
         var5 = new GuiLabel(this.a.k, var3.b(), var1, var2, this.b - var1 * 2, 20, -1);
      } else {
         var5 = new GuiLabel(this.a.k, var3.b(), var1, var2, 150, 20, -1);
      }

      var5.j = var3.d();
      var5.a(var3.c());
      var5.a();
      return var5;
   }

   public void a(char var1, int var2) {
      if (this.A instanceof GuiTextField) {
         GuiTextField var3 = (GuiTextField)this.A;
         if (!GuiScreen.e(var2)) {
            if (var2 == 15) {
               var3.b(false);
               int var13 = this.w.indexOf(this.A);
               if (GuiScreen.r()) {
                  if (var13 == 0) {
                     var13 = this.w.size() - 1;
                  } else {
                     var13--;
                  }
               } else if (var13 == this.w.size() - 1) {
                  var13 = 0;
               } else {
                  var13++;
               }

               this.A = this.w.get(var13);
               var3 = (GuiTextField)this.A;
               var3.b(true);
               int var15 = var3.f + this.h;
               int var16 = var3.f;
               if (var15 > this.e) {
                  this.n = this.n + (var15 - this.e);
               } else if (var16 < this.d) {
                  this.n = var16;
               }
            } else {
               var3.a(var1, var2);
            }
         } else {
            String var4 = GuiScreen.o();
            String[] var5 = var4.split(";");
            int var6 = this.w.indexOf(this.A);
            int var7 = var6;

            for (String var11 : var5) {
               this.w.get(var7).a(var11);
               if (var7 == this.w.size() - 1) {
                  var7 = 0;
               } else {
                  var7++;
               }

               if (var7 == var6) {
                  break;
               }
            }
         }
      }
   }

   public GuiPageButtonList.GuiEntry e(int var1) {
      return this.u.get(var1);
   }

   @Override
   public int b() {
      return this.u.size();
   }

   @Override
   public int c() {
      return 400;
   }

   @Override
   protected int d() {
      return super.d() + 32;
   }

   public static class EditBoxEntry extends GuiPageButtonList.GuiListEntry {
      private final Predicate<String> a;

      public EditBoxEntry(int var1, String var2, boolean var3, Predicate<String> var4) {
         super(var1, var2, var3);
         this.a = (Predicate<String>)Objects.firstNonNull(var4, Predicates.alwaysTrue());
      }

      public Predicate<String> a() {
         return this.a;
      }
   }

   public static class GuiButtonEntry extends GuiPageButtonList.GuiListEntry {
      private final boolean a;

      public GuiButtonEntry(int var1, String var2, boolean var3, boolean var4) {
         super(var1, var2, var3);
         this.a = var4;
      }

      public boolean a() {
         return this.a;
      }
   }

   public static class GuiEntry implements GuiListExtended.IGuiListEntry {
      private final Minecraft a = Minecraft.A();
      private final Gui b;
      private final Gui c;
      private Gui d;

      public GuiEntry(Gui var1, Gui var2) {
         this.b = var1;
         this.c = var2;
      }

      public Gui a() {
         return this.b;
      }

      public Gui b() {
         return this.c;
      }

      @Override
      public void a(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
         this.a(this.b, var3, var6, var7, false);
         this.a(this.c, var3, var6, var7, false);
      }

      private void a(Gui var1, int var2, int var3, int var4, boolean var5) {
         if (var1 != null) {
            if (var1 instanceof GuiButton) {
               this.a((GuiButton)var1, var2, var3, var4, var5);
            } else if (var1 instanceof GuiTextField) {
               this.a((GuiTextField)var1, var2, var5);
            } else if (var1 instanceof GuiLabel) {
               this.a((GuiLabel)var1, var2, var3, var4, var5);
            }
         }
      }

      private void a(GuiButton var1, int var2, int var3, int var4, boolean var5) {
         var1.i = var2;
         if (!var5) {
            var1.a(this.a, var3, var4);
         }
      }

      private void a(GuiTextField var1, int var2, boolean var3) {
         var1.f = var2;
         if (!var3) {
            var1.g();
         }
      }

      private void a(GuiLabel var1, int var2, int var3, int var4, boolean var5) {
         var1.h = var2;
         if (!var5) {
            var1.a(this.a, var3, var4);
         }
      }

      @Override
      public void a(int var1, int var2, int var3) {
         this.a(this.b, var3, 0, 0, true);
         this.a(this.c, var3, 0, 0, true);
      }

      @Override
      public boolean a(int var1, int var2, int var3, int var4, int var5, int var6) {
         boolean var7 = this.a(this.b, var2, var3, var4);
         boolean var8 = this.a(this.c, var2, var3, var4);
         return var7 || var8;
      }

      private boolean a(Gui var1, int var2, int var3, int var4) {
         if (var1 == null) {
            return false;
         } else if (var1 instanceof GuiButton) {
            return this.a((GuiButton)var1, var2, var3, var4);
         } else {
            if (var1 instanceof GuiTextField) {
               this.a((GuiTextField)var1, var2, var3, var4);
            }

            return false;
         }
      }

      private boolean a(GuiButton var1, int var2, int var3, int var4) {
         boolean var5 = var1.c(this.a, var2, var3);
         if (var5) {
            this.d = var1;
         }

         return var5;
      }

      private void a(GuiTextField var1, int var2, int var3, int var4) {
         var1.a(var2, var3, var4);
         if (var1.m()) {
            this.d = var1;
         }
      }

      @Override
      public void b(int var1, int var2, int var3, int var4, int var5, int var6) {
         this.b(this.b, var2, var3, var4);
         this.b(this.c, var2, var3, var4);
      }

      private void b(Gui var1, int var2, int var3, int var4) {
         if (var1 != null) {
            if (var1 instanceof GuiButton) {
               this.b((GuiButton)var1, var2, var3, var4);
            }
         }
      }

      private void b(GuiButton var1, int var2, int var3, int var4) {
         var1.a(var2, var3);
      }
   }

   public static class GuiLabelEntry extends GuiPageButtonList.GuiListEntry {
      public GuiLabelEntry(int var1, String var2, boolean var3) {
         super(var1, var2, var3);
      }
   }

   public static class GuiListEntry {
      private final int a;
      private final String b;
      private final boolean c;

      public GuiListEntry(int var1, String var2, boolean var3) {
         this.a = var1;
         this.b = var2;
         this.c = var3;
      }

      public int b() {
         return this.a;
      }

      public String c() {
         return this.b;
      }

      public boolean d() {
         return this.c;
      }
   }

   public interface GuiResponder {
      void a(int var1, boolean var2);

      void a(int var1, float var2);

      void a(int var1, String var2);
   }

   public static class GuiSlideEntry extends GuiPageButtonList.GuiListEntry {
      private final GuiSlider.FormatHelper a;
      private final float b;
      private final float c;
      private final float d;

      public GuiSlideEntry(int var1, String var2, boolean var3, GuiSlider.FormatHelper var4, float var5, float var6, float var7) {
         super(var1, var2, var3);
         this.a = var4;
         this.b = var5;
         this.c = var6;
         this.d = var7;
      }

      public GuiSlider.FormatHelper a() {
         return this.a;
      }

      public float e() {
         return this.b;
      }

      public float f() {
         return this.c;
      }

      public float g() {
         return this.d;
      }
   }
}
