package net.minecraft.util;

public enum EnumWorldBlockLayer {
   a("Solid"),
   b("Mipped Cutout"),
   c("Cutout"),
   d("Translucent");

   private final String e;

   private EnumWorldBlockLayer(String var3) {
      this.e = var3;
   }

   @Override
   public String toString() {
      return this.e;
   }
}
