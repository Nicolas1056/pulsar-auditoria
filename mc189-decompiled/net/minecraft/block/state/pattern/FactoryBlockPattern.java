package net.minecraft.block.state.pattern;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.state.BlockWorldState;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class FactoryBlockPattern {
   private static final Joiner a = Joiner.on(",");
   private final List<String[]> b = Lists.newArrayList();
   private final Map<Character, Predicate<BlockWorldState>> c = Maps.newHashMap();
   private int d;
   private int e;

   private FactoryBlockPattern() {
      this.c.put(' ', Predicates.alwaysTrue());
   }

   public FactoryBlockPattern a(String... var1) {
      if (!ArrayUtils.isEmpty(var1) && !StringUtils.isEmpty(var1[0])) {
         if (this.b.isEmpty()) {
            this.d = var1.length;
            this.e = var1[0].length();
         }

         if (var1.length != this.d) {
            throw new IllegalArgumentException("Expected aisle with height of " + this.d + ", but was given one with a height of " + var1.length + ")");
         } else {
            for (String var5 : var1) {
               if (var5.length() != this.e) {
                  throw new IllegalArgumentException(
                     "Not all rows in the given aisle are the correct width (expected " + this.e + ", found one with " + var5.length() + ")"
                  );
               }

               for (char var9 : var5.toCharArray()) {
                  if (!this.c.containsKey(var9)) {
                     this.c.put(var9, null);
                  }
               }
            }

            this.b.add(var1);
            return this;
         }
      } else {
         throw new IllegalArgumentException("Empty pattern for aisle");
      }
   }

   public static FactoryBlockPattern a() {
      return new FactoryBlockPattern();
   }

   public FactoryBlockPattern a(char var1, Predicate<BlockWorldState> var2) {
      this.c.put(var1, var2);
      return this;
   }

   public BlockPattern b() {
      return new BlockPattern(this.c());
   }

   private Predicate<BlockWorldState>[][][] c() {
      this.d();
      Predicate[][][] var1 = (Predicate[][][])Array.newInstance(Predicate.class, this.b.size(), this.d, this.e);

      for (int var2 = 0; var2 < this.b.size(); var2++) {
         for (int var3 = 0; var3 < this.d; var3++) {
            for (int var4 = 0; var4 < this.e; var4++) {
               var1[var2][var3][var4] = this.c.get(this.b.get(var2)[var3].charAt(var4));
            }
         }
      }

      return var1;
   }

   private void d() {
      ArrayList var1 = Lists.newArrayList();

      for (Entry var3 : this.c.entrySet()) {
         if (var3.getValue() == null) {
            var1.add(var3.getKey());
         }
      }

      if (!var1.isEmpty()) {
         throw new IllegalStateException("Predicates for character(s) " + a.join(var1) + " are missing");
      }
   }
}
