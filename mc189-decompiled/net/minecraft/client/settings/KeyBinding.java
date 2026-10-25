package net.minecraft.client.settings;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IntHashMap;

public class KeyBinding implements Comparable<KeyBinding> {
   private static final List<KeyBinding> a = Lists.newArrayList();
   private static final IntHashMap<KeyBinding> b = new IntHashMap<>();
   private static final Set<String> c = Sets.newHashSet();
   private final String d;
   private final int e;
   private final String f;
   private int g;
   private boolean h;
   private int i;

   public static void a(int var0) {
      if (var0 != 0) {
         KeyBinding var1 = b.a(var0);
         if (var1 != null) {
            var1.i++;
         }
      }
   }

   public static void a(int var0, boolean var1) {
      if (var0 != 0) {
         KeyBinding var2 = b.a(var0);
         if (var2 != null) {
            var2.h = var1;
         }
      }
   }

   public static void a() {
      for (KeyBinding var1 : a) {
         var1.j();
      }
   }

   public static void b() {
      b.c();

      for (KeyBinding var1 : a) {
         b.a(var1.g, var1);
      }
   }

   public static Set<String> c() {
      return c;
   }

   public KeyBinding(String var1, int var2, String var3) {
      this.d = var1;
      this.g = var2;
      this.e = var2;
      this.f = var3;
      a.add(this);
      b.a(var2, this);
      c.add(var3);
   }

   public boolean d() {
      return this.h;
   }

   public String e() {
      return this.f;
   }

   public boolean f() {
      if (this.i == 0) {
         return false;
      } else {
         this.i--;
         return true;
      }
   }

   private void j() {
      this.i = 0;
      this.h = false;
   }

   public String g() {
      return this.d;
   }

   public int h() {
      return this.e;
   }

   public int i() {
      return this.g;
   }

   public void b(int var1) {
      this.g = var1;
   }

   public int a(KeyBinding var1) {
      int var2 = I18n.a(this.f).compareTo(I18n.a(var1.f));
      if (var2 == 0) {
         var2 = I18n.a(this.d).compareTo(I18n.a(var1.d));
      }

      return var2;
   }
}
