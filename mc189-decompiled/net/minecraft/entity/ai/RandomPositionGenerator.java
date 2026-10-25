package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RandomPositionGenerator {
   private static Vec3 a = new Vec3(0.0, 0.0, 0.0);

   public static Vec3 a(EntityCreature var0, int var1, int var2) {
      return c(var0, var1, var2, null);
   }

   public static Vec3 a(EntityCreature var0, int var1, int var2, Vec3 var3) {
      a = var3.a(var0.s, var0.t, var0.u);
      return c(var0, var1, var2, a);
   }

   public static Vec3 b(EntityCreature var0, int var1, int var2, Vec3 var3) {
      a = new Vec3(var0.s, var0.t, var0.u).d(var3);
      return c(var0, var1, var2, a);
   }

   private static Vec3 c(EntityCreature var0, int var1, int var2, Vec3 var3) {
      Random var4 = var0.bc();
      boolean var5 = false;
      int var6 = 0;
      int var7 = 0;
      int var8 = 0;
      float var9 = -99999.0F;
      boolean var10;
      if (var0.ck()) {
         double var11 = var0.ch().c(MathHelper.c(var0.s), MathHelper.c(var0.t), MathHelper.c(var0.u)) + 4.0;
         double var13 = var0.ci() + var1;
         var10 = var11 < var13 * var13;
      } else {
         var10 = false;
      }

      for (int var17 = 0; var17 < 10; var17++) {
         int var12 = var4.nextInt(2 * var1 + 1) - var1;
         int var19 = var4.nextInt(2 * var2 + 1) - var2;
         int var14 = var4.nextInt(2 * var1 + 1) - var1;
         if (var3 == null || !(var12 * var3.a + var14 * var3.c < 0.0)) {
            if (var0.ck() && var1 > 1) {
               BlockPos var15 = var0.ch();
               if (var0.s > var15.n()) {
                  var12 -= var4.nextInt(var1 / 2);
               } else {
                  var12 += var4.nextInt(var1 / 2);
               }

               if (var0.u > var15.p()) {
                  var14 -= var4.nextInt(var1 / 2);
               } else {
                  var14 += var4.nextInt(var1 / 2);
               }
            }

            var12 += MathHelper.c(var0.s);
            var19 += MathHelper.c(var0.t);
            var14 += MathHelper.c(var0.u);
            BlockPos var22 = new BlockPos(var12, var19, var14);
            if (!var10 || var0.e(var22)) {
               float var16 = var0.a(var22);
               if (var16 > var9) {
                  var9 = var16;
                  var6 = var12;
                  var7 = var19;
                  var8 = var14;
                  var5 = true;
               }
            }
         }
      }

      return var5 ? new Vec3(var6, var7, var8) : null;
   }
}
