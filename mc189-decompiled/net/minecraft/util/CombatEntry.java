package net.minecraft.util;

import net.minecraft.entity.EntityLivingBase;

public class CombatEntry {
   private final DamageSource a;
   private final int b;
   private final float c;
   private final float d;
   private final String e;
   private final float f;

   public CombatEntry(DamageSource var1, int var2, float var3, float var4, String var5, float var6) {
      this.a = var1;
      this.b = var2;
      this.c = var4;
      this.d = var3;
      this.e = var5;
      this.f = var6;
   }

   public DamageSource a() {
      return this.a;
   }

   public float c() {
      return this.c;
   }

   public boolean f() {
      return this.a.j() instanceof EntityLivingBase;
   }

   public String g() {
      return this.e;
   }

   public IChatComponent h() {
      return this.a().j() == null ? null : this.a().j().f_();
   }

   public float i() {
      return this.a == DamageSource.j ? Float.MAX_VALUE : this.f;
   }
}
