package net.minecraft.entity.ai.attributes;

public abstract class BaseAttribute implements IAttribute {
   private final IAttribute a;
   private final String b;
   private final double c;
   private boolean d;

   protected BaseAttribute(IAttribute var1, String var2, double var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      if (var2 == null) {
         throw new IllegalArgumentException("Name cannot be null!");
      }
   }

   @Override
   public String a() {
      return this.b;
   }

   @Override
   public double b() {
      return this.c;
   }

   @Override
   public boolean c() {
      return this.d;
   }

   public BaseAttribute a(boolean var1) {
      this.d = var1;
      return this;
   }

   @Override
   public IAttribute d() {
      return this.a;
   }

   @Override
   public int hashCode() {
      return this.b.hashCode();
   }

   @Override
   public boolean equals(Object var1) {
      return var1 instanceof IAttribute && this.b.equals(((IAttribute)var1).a());
   }
}
