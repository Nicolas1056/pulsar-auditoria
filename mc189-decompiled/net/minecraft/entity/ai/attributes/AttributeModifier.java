package net.minecraft.entity.ai.attributes;

import io.netty.util.internal.ThreadLocalRandom;
import java.util.UUID;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.Validate;

public class AttributeModifier {
   private final double a;
   private final int b;
   private final String c;
   private final UUID d;
   private boolean e = true;

   public AttributeModifier(String var1, double var2, int var4) {
      this(MathHelper.a(ThreadLocalRandom.current()), var1, var2, var4);
   }

   public AttributeModifier(UUID var1, String var2, double var3, int var5) {
      this.d = var1;
      this.c = var2;
      this.a = var3;
      this.b = var5;
      Validate.notEmpty(var2, "Modifier name cannot be empty", new Object[0]);
      Validate.inclusiveBetween(0L, 2L, var5, "Invalid operation");
   }

   public UUID a() {
      return this.d;
   }

   public String b() {
      return this.c;
   }

   public int c() {
      return this.b;
   }

   public double d() {
      return this.a;
   }

   public boolean e() {
      return this.e;
   }

   public AttributeModifier a(boolean var1) {
      this.e = var1;
      return this;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         AttributeModifier var2 = (AttributeModifier)var1;
         return this.d != null ? this.d.equals(var2.d) : var2.d == null;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.d != null ? this.d.hashCode() : 0;
   }

   @Override
   public String toString() {
      return "AttributeModifier{amount=" + this.a + ", operation=" + this.b + ", name='" + this.c + '\'' + ", id=" + this.d + ", serialize=" + this.e + '}';
   }
}
