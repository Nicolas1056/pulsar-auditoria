package net.minecraft.realms;

import java.util.Random;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.StringUtils;

public class RealmsMth {
   public static float sin(float var0) {
      return MathHelper.a(var0);
   }

   public static double nextDouble(Random var0, double var1, double var3) {
      return MathHelper.a(var0, var1, var3);
   }

   public static int ceil(float var0) {
      return MathHelper.f(var0);
   }

   public static int floor(double var0) {
      return MathHelper.c(var0);
   }

   public static int intFloorDiv(int var0, int var1) {
      return MathHelper.a(var0, var1);
   }

   public static float abs(float var0) {
      return MathHelper.e(var0);
   }

   public static int clamp(int var0, int var1, int var2) {
      return MathHelper.a(var0, var1, var2);
   }

   public static double clampedLerp(double var0, double var2, double var4) {
      return MathHelper.b(var0, var2, var4);
   }

   public static int ceil(double var0) {
      return MathHelper.f(var0);
   }

   public static boolean isEmpty(String var0) {
      return StringUtils.isEmpty(var0);
   }

   public static long lfloor(double var0) {
      return MathHelper.d(var0);
   }

   public static float sqrt(double var0) {
      return MathHelper.a(var0);
   }

   public static double clamp(double var0, double var2, double var4) {
      return MathHelper.a(var0, var2, var4);
   }

   public static int getInt(String var0, int var1) {
      return MathHelper.a(var0, var1);
   }

   public static double getDouble(String var0, double var1) {
      return MathHelper.a(var0, var1);
   }

   public static int log2(int var0) {
      return MathHelper.c(var0);
   }

   public static int absFloor(double var0) {
      return MathHelper.e(var0);
   }

   public static int smallestEncompassingPowerOfTwo(int var0) {
      return MathHelper.b(var0);
   }

   public static float sqrt(float var0) {
      return MathHelper.c(var0);
   }

   public static float cos(float var0) {
      return MathHelper.b(var0);
   }

   public static int getInt(String var0, int var1, int var2) {
      return MathHelper.a(var0, var1, var2);
   }

   public static int fastFloor(double var0) {
      return MathHelper.b(var0);
   }

   public static double absMax(double var0, double var2) {
      return MathHelper.a(var0, var2);
   }

   public static float nextFloat(Random var0, float var1, float var2) {
      return MathHelper.a(var0, var1, var2);
   }

   public static double wrapDegrees(double var0) {
      return MathHelper.g(var0);
   }

   public static float wrapDegrees(float var0) {
      return MathHelper.g(var0);
   }

   public static float clamp(float var0, float var1, float var2) {
      return MathHelper.a(var0, var1, var2);
   }

   public static double getDouble(String var0, double var1, double var3) {
      return MathHelper.a(var0, var1, var3);
   }

   public static int roundUp(int var0, int var1) {
      return MathHelper.c(var0, var1);
   }

   public static double average(long[] var0) {
      return MathHelper.a(var0);
   }

   public static int floor(float var0) {
      return MathHelper.d(var0);
   }

   public static int abs(int var0) {
      return MathHelper.a(var0);
   }

   public static int nextInt(Random var0, int var1, int var2) {
      return MathHelper.a(var0, var1, var2);
   }
}
