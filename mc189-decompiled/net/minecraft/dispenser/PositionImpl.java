package net.minecraft.dispenser;

public class PositionImpl implements IPosition {
   protected final double a;
   protected final double b;
   protected final double c;

   public PositionImpl(double var1, double var3, double var5) {
      this.a = var1;
      this.b = var3;
      this.c = var5;
   }

   @Override
   public double a() {
      return this.a;
   }

   @Override
   public double b() {
      return this.b;
   }

   @Override
   public double c() {
      return this.c;
   }
}
