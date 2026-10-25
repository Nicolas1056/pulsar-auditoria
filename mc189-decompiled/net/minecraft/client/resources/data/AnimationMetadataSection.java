package net.minecraft.client.resources.data;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnimationMetadataSection implements IMetadataSection {
   private final List<AnimationFrame> a;
   private final int b;
   private final int c;
   private final int d;
   private final boolean e;

   public AnimationMetadataSection(List<AnimationFrame> var1, int var2, int var3, int var4, boolean var5) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
      this.e = var5;
   }

   public int a() {
      return this.c;
   }

   public int b() {
      return this.b;
   }

   public int c() {
      return this.a.size();
   }

   public int d() {
      return this.d;
   }

   public boolean e() {
      return this.e;
   }

   private AnimationFrame d(int var1) {
      return this.a.get(var1);
   }

   public int a(int var1) {
      AnimationFrame var2 = this.d(var1);
      return var2.a() ? this.d : var2.b();
   }

   public boolean b(int var1) {
      return !this.a.get(var1).a();
   }

   public int c(int var1) {
      return this.a.get(var1).c();
   }

   public Set<Integer> f() {
      HashSet var1 = Sets.newHashSet();

      for (AnimationFrame var3 : this.a) {
         var1.add(var3.c());
      }

      return var1;
   }
}
