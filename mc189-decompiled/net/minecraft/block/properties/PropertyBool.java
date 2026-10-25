package net.minecraft.block.properties;

import com.google.common.collect.ImmutableSet;
import java.util.Collection;

public class PropertyBool extends PropertyHelper<Boolean> {
   private final ImmutableSet<Boolean> a = ImmutableSet.of(true, false);

   protected PropertyBool(String var1) {
      super(var1, Boolean.class);
   }

   @Override
   public Collection<Boolean> c() {
      return this.a;
   }

   public static PropertyBool a(String var0) {
      return new PropertyBool(var0);
   }

   public String a(Boolean var1) {
      return var1.toString();
   }
}
