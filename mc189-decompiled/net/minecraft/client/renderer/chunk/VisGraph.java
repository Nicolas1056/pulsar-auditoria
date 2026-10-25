package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.Set;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IntegerCache;

public class VisGraph {
   private static final int a = (int)Math.pow(16.0, 0.0);
   private static final int b = (int)Math.pow(16.0, 1.0);
   private static final int c = (int)Math.pow(16.0, 2.0);
   private final BitSet d = new BitSet(4096);
   private static final int[] e = new int[1352];
   private int f = 4096;

   public void a(BlockPos var1) {
      this.d.set(c(var1), true);
      this.f--;
   }

   private static int c(BlockPos var0) {
      return a(var0.n() & 15, var0.o() & 15, var0.p() & 15);
   }

   private static int a(int var0, int var1, int var2) {
      return var0 << 0 | var1 << 8 | var2 << 4;
   }

   public SetVisibility a() {
      SetVisibility var1 = new SetVisibility();
      if (4096 - this.f < 256) {
         var1.a(true);
      } else if (this.f == 0) {
         var1.a(false);
      } else {
         for (int var5 : e) {
            if (!this.d.get(var5)) {
               var1.a(this.a(var5));
            }
         }
      }

      return var1;
   }

   public Set<EnumFacing> b(BlockPos var1) {
      return this.a(c(var1));
   }

   private Set<EnumFacing> a(int var1) {
      EnumSet var2 = EnumSet.noneOf(EnumFacing.class);
      LinkedList var3 = Lists.newLinkedList();
      var3.add(IntegerCache.a(var1));
      this.d.set(var1, true);

      while (!var3.isEmpty()) {
         int var4 = (Integer)var3.poll();
         this.a(var4, var2);

         for (EnumFacing var8 : EnumFacing.values()) {
            int var9 = this.a(var4, var8);
            if (var9 >= 0 && !this.d.get(var9)) {
               this.d.set(var9, true);
               var3.add(IntegerCache.a(var9));
            }
         }
      }

      return var2;
   }

   private void a(int var1, Set<EnumFacing> var2) {
      int var3 = var1 >> 0 & 15;
      if (var3 == 0) {
         var2.add(EnumFacing.e);
      } else if (var3 == 15) {
         var2.add(EnumFacing.f);
      }

      int var4 = var1 >> 8 & 15;
      if (var4 == 0) {
         var2.add(EnumFacing.a);
      } else if (var4 == 15) {
         var2.add(EnumFacing.b);
      }

      int var5 = var1 >> 4 & 15;
      if (var5 == 0) {
         var2.add(EnumFacing.c);
      } else if (var5 == 15) {
         var2.add(EnumFacing.d);
      }
   }

   private int a(int var1, EnumFacing var2) {
      switch (var2) {
         case a:
            if ((var1 >> 8 & 15) == 0) {
               return -1;
            }

            return var1 - c;
         case b:
            if ((var1 >> 8 & 15) == 15) {
               return -1;
            }

            return var1 + c;
         case c:
            if ((var1 >> 4 & 15) == 0) {
               return -1;
            }

            return var1 - b;
         case d:
            if ((var1 >> 4 & 15) == 15) {
               return -1;
            }

            return var1 + b;
         case e:
            if ((var1 >> 0 & 15) == 0) {
               return -1;
            }

            return var1 - a;
         case f:
            if ((var1 >> 0 & 15) == 15) {
               return -1;
            }

            return var1 + a;
         default:
            return -1;
      }
   }

   static {
      boolean var0 = false;
      byte var1 = 15;
      int var2 = 0;

      for (int var3 = 0; var3 < 16; var3++) {
         for (int var4 = 0; var4 < 16; var4++) {
            for (int var5 = 0; var5 < 16; var5++) {
               if (var3 == 0 || var3 == 15 || var4 == 0 || var4 == 15 || var5 == 0 || var5 == 15) {
                  e[var2++] = a(var3, var4, var5);
               }
            }
         }
      }
   }
}
