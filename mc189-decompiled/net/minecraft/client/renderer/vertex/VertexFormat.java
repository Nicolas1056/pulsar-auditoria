package net.minecraft.client.renderer.vertex;

import com.google.common.collect.Lists;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VertexFormat {
   private static final Logger a = LogManager.getLogger();
   private final List<VertexFormatElement> b = Lists.newArrayList();
   private final List<Integer> c = Lists.newArrayList();
   private int d = 0;
   private int e = -1;
   private List<Integer> f = Lists.newArrayList();
   private int g = -1;

   public VertexFormat(VertexFormat var1) {
      this();

      for (int var2 = 0; var2 < var1.i(); var2++) {
         this.a(var1.c(var2));
      }

      this.d = var1.g();
   }

   public VertexFormat() {
   }

   public void a() {
      this.b.clear();
      this.c.clear();
      this.e = -1;
      this.f.clear();
      this.g = -1;
      this.d = 0;
   }

   public VertexFormat a(VertexFormatElement var1) {
      if (var1.f() && this.j()) {
         a.warn("VertexFormat error: Trying to add a position VertexFormatElement when one already exists, ignoring.");
         return this;
      } else {
         this.b.add(var1);
         this.c.add(this.d);
         switch (var1.b()) {
            case b:
               this.g = this.d;
               break;
            case c:
               this.e = this.d;
               break;
            case d:
               this.f.add(var1.d(), this.d);
         }

         this.d = this.d + var1.e();
         return this;
      }
   }

   public boolean b() {
      return this.g >= 0;
   }

   public int c() {
      return this.g;
   }

   public boolean d() {
      return this.e >= 0;
   }

   public int e() {
      return this.e;
   }

   public boolean a(int var1) {
      return this.f.size() - 1 >= var1;
   }

   public int b(int var1) {
      return this.f.get(var1);
   }

   @Override
   public String toString() {
      String var1 = "format: " + this.b.size() + " elements: ";

      for (int var2 = 0; var2 < this.b.size(); var2++) {
         var1 = var1 + this.b.get(var2).toString();
         if (var2 != this.b.size() - 1) {
            var1 = var1 + " ";
         }
      }

      return var1;
   }

   private boolean j() {
      int var1 = 0;

      for (int var2 = this.b.size(); var1 < var2; var1++) {
         VertexFormatElement var3 = this.b.get(var1);
         if (var3.f()) {
            return true;
         }
      }

      return false;
   }

   public int f() {
      return this.g() / 4;
   }

   public int g() {
      return this.d;
   }

   public List<VertexFormatElement> h() {
      return this.b;
   }

   public int i() {
      return this.b.size();
   }

   public VertexFormatElement c(int var1) {
      return this.b.get(var1);
   }

   public int d(int var1) {
      return this.c.get(var1);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         VertexFormat var2 = (VertexFormat)var1;
         if (this.d != var2.d) {
            return false;
         } else {
            return !this.b.equals(var2.b) ? false : this.c.equals(var2.c);
         }
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int var1 = this.b.hashCode();
      var1 = 31 * var1 + this.c.hashCode();
      return 31 * var1 + this.d;
   }
}
