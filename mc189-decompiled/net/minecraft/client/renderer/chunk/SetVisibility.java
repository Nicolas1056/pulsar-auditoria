package net.minecraft.client.renderer.chunk;

import java.util.BitSet;
import java.util.Set;
import net.minecraft.util.EnumFacing;

public class SetVisibility {
   private static final int a = EnumFacing.values().length;
   private final BitSet b = new BitSet(a * a);

   public void a(Set<EnumFacing> var1) {
      for (EnumFacing var3 : var1) {
         for (EnumFacing var5 : var1) {
            this.a(var3, var5, true);
         }
      }
   }

   public void a(EnumFacing var1, EnumFacing var2, boolean var3) {
      this.b.set(var1.ordinal() + var2.ordinal() * a, var3);
      this.b.set(var2.ordinal() + var1.ordinal() * a, var3);
   }

   public void a(boolean var1) {
      this.b.set(0, this.b.size(), var1);
   }

   public boolean a(EnumFacing var1, EnumFacing var2) {
      return this.b.get(var1.ordinal() + var2.ordinal() * a);
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(' ');

      for (EnumFacing var5 : EnumFacing.values()) {
         var1.append(' ').append(var5.toString().toUpperCase().charAt(0));
      }

      var1.append('\n');

      for (EnumFacing var14 : EnumFacing.values()) {
         var1.append(var14.toString().toUpperCase().charAt(0));

         for (EnumFacing var9 : EnumFacing.values()) {
            if (var14 == var9) {
               var1.append("  ");
            } else {
               boolean var10 = this.a(var14, var9);
               var1.append(' ').append((char)(var10 ? 'Y' : 'n'));
            }
         }

         var1.append('\n');
      }

      return var1.toString();
   }
}
