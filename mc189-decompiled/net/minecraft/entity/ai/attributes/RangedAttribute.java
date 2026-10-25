package net.minecraft.entity.ai.attributes;

import net.minecraft.util.MathHelper;

public class RangedAttribute extends BaseAttribute {
   private final double a;
   private final double b;
   private String c;

   public RangedAttribute(IAttribute var1, String var2, double var3, double var5, double var7) {
      super(var1, var2, var3);
      this.a = var5;
      this.b = var7;
      if (var5 > var7) {
         throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
      } else if (var3 < var5) {
         throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
      } else if (var3 > var7) {
         throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
      }
   }

   public RangedAttribute a(String var1) {
      this.c = var1;
      return this;
   }

   public String g() {
      return this.c;
   }

   @Override
   public double a(double var1) {
      return MathHelper.a(var1, this.a, this.b);
   }
}
