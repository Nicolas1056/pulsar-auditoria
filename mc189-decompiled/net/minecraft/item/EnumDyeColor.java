package net.minecraft.item;

import net.minecraft.block.material.MapColor;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IStringSerializable;

public enum EnumDyeColor implements IStringSerializable {
   a(0, 15, "white", "white", MapColor.j, EnumChatFormatting.p),
   b(1, 14, "orange", "orange", MapColor.q, EnumChatFormatting.g),
   c(2, 13, "magenta", "magenta", MapColor.r, EnumChatFormatting.l),
   d(3, 12, "light_blue", "lightBlue", MapColor.s, EnumChatFormatting.j),
   e(4, 11, "yellow", "yellow", MapColor.t, EnumChatFormatting.o),
   f(5, 10, "lime", "lime", MapColor.u, EnumChatFormatting.k),
   g(6, 9, "pink", "pink", MapColor.v, EnumChatFormatting.n),
   h(7, 8, "gray", "gray", MapColor.w, EnumChatFormatting.i),
   i(8, 7, "silver", "silver", MapColor.x, EnumChatFormatting.h),
   j(9, 6, "cyan", "cyan", MapColor.y, EnumChatFormatting.d),
   k(10, 5, "purple", "purple", MapColor.z, EnumChatFormatting.f),
   l(11, 4, "blue", "blue", MapColor.A, EnumChatFormatting.b),
   m(12, 3, "brown", "brown", MapColor.B, EnumChatFormatting.g),
   n(13, 2, "green", "green", MapColor.C, EnumChatFormatting.c),
   o(14, 1, "red", "red", MapColor.D, EnumChatFormatting.e),
   p(15, 0, "black", "black", MapColor.E, EnumChatFormatting.a);

   private static final EnumDyeColor[] q = new EnumDyeColor[values().length];
   private static final EnumDyeColor[] r = new EnumDyeColor[values().length];
   private final int s;
   private final int t;
   private final String u;
   private final String v;
   private final MapColor w;
   private final EnumChatFormatting x;

   private EnumDyeColor(int var3, int var4, String var5, String var6, MapColor var7, EnumChatFormatting var8) {
      this.s = var3;
      this.t = var4;
      this.u = var5;
      this.v = var6;
      this.w = var7;
      this.x = var8;
   }

   public int a() {
      return this.s;
   }

   public int b() {
      return this.t;
   }

   public String d() {
      return this.v;
   }

   public MapColor e() {
      return this.w;
   }

   public static EnumDyeColor a(int var0) {
      if (var0 < 0 || var0 >= r.length) {
         var0 = 0;
      }

      return r[var0];
   }

   public static EnumDyeColor b(int var0) {
      if (var0 < 0 || var0 >= q.length) {
         var0 = 0;
      }

      return q[var0];
   }

   @Override
   public String toString() {
      return this.v;
   }

   @Override
   public String l() {
      return this.u;
   }

   static {
      for (EnumDyeColor var3 : values()) {
         q[var3.a()] = var3;
         r[var3.b()] = var3;
      }
   }
}
