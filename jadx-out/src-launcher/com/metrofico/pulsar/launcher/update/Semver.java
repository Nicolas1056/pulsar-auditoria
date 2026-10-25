package com.metrofico.pulsar.launcher.update;

import java.util.Arrays;

public record Semver(int major, int minor, int patch, String pre) implements Comparable<Semver> {
   public static Semver parse(String s) {
      String v = s.trim();
      String pre = null;
      int dash = v.indexOf(45);
      if (dash > 0) {
         pre = v.substring(dash + 1);
         v = v.substring(0, dash);
      }

      String[] parts = v.split("\\.");
      if (parts.length != 3) {
         throw new IllegalArgumentException("not a version: " + s);
      } else {
         return new Semver(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), pre);
      }
   }

   public int compareTo(Semver o) {
      int c = Integer.compare(this.major, o.major);
      if (c == 0) {
         c = Integer.compare(this.minor, o.minor);
      }

      if (c == 0) {
         c = Integer.compare(this.patch, o.patch);
      }

      if (c == 0) {
         if (this.pre == null && o.pre == null) {
            return 0;
         }

         if (this.pre == null) {
            return 1;
         }

         if (o.pre == null) {
            return -1;
         }

         String[] a = this.pre.split("\\.");
         String[] b = o.pre.split("\\.");

         for (int i = 0; i < Math.max(a.length, b.length); i++) {
            if (i >= a.length) {
               return -1;
            }

            if (i >= b.length) {
               return 1;
            }

            boolean na = a[i].chars().allMatch(Character::isDigit);
            boolean nb = b[i].chars().allMatch(Character::isDigit);
            int d = na && nb ? Integer.compare(Integer.parseInt(a[i]), Integer.parseInt(b[i])) : a[i].compareTo(b[i]);
            if (d != 0) {
               return d;
            }
         }
      }

      return c;
   }

   public boolean newerThan(Semver o) {
      return this.compareTo(o) > 0;
   }

   @Override
   public String toString() {
      return this.major + "." + this.minor + "." + this.patch + (this.pre == null ? "" : "-" + this.pre);
   }

   public static boolean isValidPublic(String s) {
      return isValid(s);
   }

   static boolean isValid(String s) {
      try {
         parse(s);
         return true;
      } catch (RuntimeException var2) {
         return false;
      }
   }

   static Semver max(Semver... v) {
      return Arrays.stream(v).max(Semver::compareTo).orElseThrow();
   }
}
