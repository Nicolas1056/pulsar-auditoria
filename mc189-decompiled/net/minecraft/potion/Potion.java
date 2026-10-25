package net.minecraft.potion;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;

public class Potion {
   public static final Potion[] a = new Potion[32];
   private static final Map<ResourceLocation, Potion> I = Maps.newHashMap();
   public static final Potion b = null;
   public static final Potion c = new Potion(1, new ResourceLocation("speed"), false, 8171462)
      .c("potion.moveSpeed")
      .b(0, 0)
      .a(SharedMonsterAttributes.d, "91AEAA56-376B-4498-935B-2F7F68070635", 0.2F, 2);
   public static final Potion d = new Potion(2, new ResourceLocation("slowness"), true, 5926017)
      .c("potion.moveSlowdown")
      .b(1, 0)
      .a(SharedMonsterAttributes.d, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15F, 2);
   public static final Potion e = new Potion(3, new ResourceLocation("haste"), false, 14270531).c("potion.digSpeed").b(2, 0).a(1.5);
   public static final Potion f = new Potion(4, new ResourceLocation("mining_fatigue"), true, 4866583).c("potion.digSlowDown").b(3, 0);
   public static final Potion g = new PotionAttackDamage(5, new ResourceLocation("strength"), false, 9643043)
      .c("potion.damageBoost")
      .b(4, 0)
      .a(SharedMonsterAttributes.e, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 2.5, 2);
   public static final Potion h = new PotionHealth(6, new ResourceLocation("instant_health"), false, 16262179).c("potion.heal");
   public static final Potion i = new PotionHealth(7, new ResourceLocation("instant_damage"), true, 4393481).c("potion.harm");
   public static final Potion j = new Potion(8, new ResourceLocation("jump_boost"), false, 2293580).c("potion.jump").b(2, 1);
   public static final Potion k = new Potion(9, new ResourceLocation("nausea"), true, 5578058).c("potion.confusion").b(3, 1).a(0.25);
   public static final Potion l = new Potion(10, new ResourceLocation("regeneration"), false, 13458603).c("potion.regeneration").b(7, 0).a(0.25);
   public static final Potion m = new Potion(11, new ResourceLocation("resistance"), false, 10044730).c("potion.resistance").b(6, 1);
   public static final Potion n = new Potion(12, new ResourceLocation("fire_resistance"), false, 14981690).c("potion.fireResistance").b(7, 1);
   public static final Potion o = new Potion(13, new ResourceLocation("water_breathing"), false, 3035801).c("potion.waterBreathing").b(0, 2);
   public static final Potion p = new Potion(14, new ResourceLocation("invisibility"), false, 8356754).c("potion.invisibility").b(0, 1);
   public static final Potion q = new Potion(15, new ResourceLocation("blindness"), true, 2039587).c("potion.blindness").b(5, 1).a(0.25);
   public static final Potion r = new Potion(16, new ResourceLocation("night_vision"), false, 2039713).c("potion.nightVision").b(4, 1);
   public static final Potion s = new Potion(17, new ResourceLocation("hunger"), true, 5797459).c("potion.hunger").b(1, 1);
   public static final Potion t = new PotionAttackDamage(18, new ResourceLocation("weakness"), true, 4738376)
      .c("potion.weakness")
      .b(5, 0)
      .a(SharedMonsterAttributes.e, "22653B89-116E-49DC-9B6B-9971489B5BE5", 2.0, 0);
   public static final Potion u = new Potion(19, new ResourceLocation("poison"), true, 5149489).c("potion.poison").b(6, 0).a(0.25);
   public static final Potion v = new Potion(20, new ResourceLocation("wither"), true, 3484199).c("potion.wither").b(1, 2).a(0.25);
   public static final Potion w = new PotionHealthBoost(21, new ResourceLocation("health_boost"), false, 16284963)
      .c("potion.healthBoost")
      .b(2, 2)
      .a(SharedMonsterAttributes.a, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0, 0);
   public static final Potion x = new PotionAbsorption(22, new ResourceLocation("absorption"), false, 2445989).c("potion.absorption").b(2, 2);
   public static final Potion y = new PotionHealth(23, new ResourceLocation("saturation"), false, 16262179).c("potion.saturation");
   public static final Potion z = null;
   public static final Potion A = null;
   public static final Potion B = null;
   public static final Potion C = null;
   public static final Potion D = null;
   public static final Potion E = null;
   public static final Potion F = null;
   public static final Potion G = null;
   public final int H;
   private final Map<IAttribute, AttributeModifier> J = Maps.newHashMap();
   private final boolean K;
   private final int L;
   private String M = "";
   private int N = -1;
   private double O;
   private boolean P;

   protected Potion(int var1, ResourceLocation var2, boolean var3, int var4) {
      this.H = var1;
      a[var1] = this;
      I.put(var2, this);
      this.K = var3;
      if (var3) {
         this.O = 0.5;
      } else {
         this.O = 1.0;
      }

      this.L = var4;
   }

   public static Potion b(String var0) {
      return I.get(new ResourceLocation(var0));
   }

   public static Set<ResourceLocation> c() {
      return I.keySet();
   }

   protected Potion b(int var1, int var2) {
      this.N = var1 + var2 * 8;
      return this;
   }

   public int d() {
      return this.H;
   }

   public void a(EntityLivingBase var1, int var2) {
      if (this.H == l.H) {
         if (var1.bn() < var1.bu()) {
            var1.h(1.0F);
         }
      } else if (this.H == u.H) {
         if (var1.bn() > 1.0F) {
            var1.a(DamageSource.l, 1.0F);
         }
      } else if (this.H == v.H) {
         var1.a(DamageSource.m, 1.0F);
      } else if (this.H == s.H && var1 instanceof EntityPlayer) {
         ((EntityPlayer)var1).a(0.025F * (var2 + 1));
      } else if (this.H == y.H && var1 instanceof EntityPlayer) {
         if (!var1.o.D) {
            ((EntityPlayer)var1).cl().a(var2 + 1, 1.0F);
         }
      } else if ((this.H != h.H || var1.bm()) && (this.H != i.H || !var1.bm())) {
         if (this.H == i.H && !var1.bm() || this.H == h.H && var1.bm()) {
            var1.a(DamageSource.l, (float)(6 << var2));
         }
      } else {
         var1.h(Math.max(4 << var2, 0));
      }
   }

   public void a(Entity var1, Entity var2, EntityLivingBase var3, int var4, double var5) {
      if ((this.H != h.H || var3.bm()) && (this.H != i.H || !var3.bm())) {
         if (this.H == i.H && !var3.bm() || this.H == h.H && var3.bm()) {
            int var8 = (int)(var5 * (6 << var4) + 0.5);
            if (var1 == null) {
               var3.a(DamageSource.l, (float)var8);
            } else {
               var3.a(DamageSource.b(var1, var2), (float)var8);
            }
         }
      } else {
         int var7 = (int)(var5 * (4 << var4) + 0.5);
         var3.h(var7);
      }
   }

   public boolean b() {
      return false;
   }

   public boolean a(int var1, int var2) {
      if (this.H == l.H) {
         int var5 = 50 >> var2;
         return var5 > 0 ? var1 % var5 == 0 : true;
      } else if (this.H == u.H) {
         int var4 = 25 >> var2;
         return var4 > 0 ? var1 % var4 == 0 : true;
      } else if (this.H == v.H) {
         int var3 = 40 >> var2;
         return var3 > 0 ? var1 % var3 == 0 : true;
      } else {
         return this.H == s.H;
      }
   }

   public Potion c(String var1) {
      this.M = var1;
      return this;
   }

   public String a() {
      return this.M;
   }

   public boolean e() {
      return this.N >= 0;
   }

   public int f() {
      return this.N;
   }

   public boolean g() {
      return this.K;
   }

   public static String a(PotionEffect var0) {
      if (var0.h()) {
         return "**:**";
      } else {
         int var1 = var0.b();
         return StringUtils.a(var1);
      }
   }

   protected Potion a(double var1) {
      this.O = var1;
      return this;
   }

   public double h() {
      return this.O;
   }

   public boolean j() {
      return this.P;
   }

   public int k() {
      return this.L;
   }

   public Potion a(IAttribute var1, String var2, double var3, int var5) {
      AttributeModifier var6 = new AttributeModifier(UUID.fromString(var2), this.a(), var3, var5);
      this.J.put(var1, var6);
      return this;
   }

   public Map<IAttribute, AttributeModifier> l() {
      return this.J;
   }

   public void a(EntityLivingBase var1, BaseAttributeMap var2, int var3) {
      for (Entry var5 : this.J.entrySet()) {
         IAttributeInstance var6 = var2.a((IAttribute)var5.getKey());
         if (var6 != null) {
            var6.c((AttributeModifier)var5.getValue());
         }
      }
   }

   public void b(EntityLivingBase var1, BaseAttributeMap var2, int var3) {
      for (Entry var5 : this.J.entrySet()) {
         IAttributeInstance var6 = var2.a((IAttribute)var5.getKey());
         if (var6 != null) {
            AttributeModifier var7 = (AttributeModifier)var5.getValue();
            var6.c(var7);
            var6.b(new AttributeModifier(var7.a(), this.a() + " " + var3, this.a(var3, var7), var7.c()));
         }
      }
   }

   public double a(int var1, AttributeModifier var2) {
      return var2.d() * (var1 + 1);
   }
}
