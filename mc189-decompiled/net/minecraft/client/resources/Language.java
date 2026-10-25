package net.minecraft.client.resources;

public class Language implements Comparable<Language> {
   private final String a;
   private final String b;
   private final String c;
   private final boolean d;

   public Language(String var1, String var2, String var3, boolean var4) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
   }

   public String a() {
      return this.a;
   }

   public boolean b() {
      return this.d;
   }

   @Override
   public String toString() {
      return String.format("%s (%s)", this.c, this.b);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else {
         return !(var1 instanceof Language) ? false : this.a.equals(((Language)var1).a);
      }
   }

   @Override
   public int hashCode() {
      return this.a.hashCode();
   }

   public int a(Language var1) {
      return this.a.compareTo(var1.a);
   }
}
