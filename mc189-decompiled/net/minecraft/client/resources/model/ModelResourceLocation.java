package net.minecraft.client.resources.model;

import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.StringUtils;

public class ModelResourceLocation extends ResourceLocation {
   private final String c;

   protected ModelResourceLocation(int var1, String... var2) {
      super(0, var2[0], var2[1]);
      this.c = StringUtils.isEmpty(var2[2]) ? "normal" : var2[2].toLowerCase();
   }

   public ModelResourceLocation(String var1) {
      this(0, b(var1));
   }

   public ModelResourceLocation(ResourceLocation var1, String var2) {
      this(var1.toString(), var2);
   }

   public ModelResourceLocation(String var1, String var2) {
      this(0, b(var1 + '#' + (var2 == null ? "normal" : var2)));
   }

   protected static String[] b(String var0) {
      String[] var1 = new String[]{null, var0, null};
      int var2 = var0.indexOf(35);
      String var3 = var0;
      if (var2 >= 0) {
         var1[2] = var0.substring(var2 + 1, var0.length());
         if (var2 > 1) {
            var3 = var0.substring(0, var2);
         }
      }

      System.arraycopy(ResourceLocation.a(var3), 0, var1, 0, 2);
      return var1;
   }

   public String c() {
      return this.c;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 instanceof ModelResourceLocation && super.equals(var1)) {
         ModelResourceLocation var2 = (ModelResourceLocation)var1;
         return this.c.equals(var2.c);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return 31 * super.hashCode() + this.c.hashCode();
   }

   @Override
   public String toString() {
      return super.toString() + '#' + this.c;
   }
}
