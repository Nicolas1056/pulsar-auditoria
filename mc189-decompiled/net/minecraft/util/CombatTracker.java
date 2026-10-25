package net.minecraft.util;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class CombatTracker {
   private final List<CombatEntry> a = Lists.newArrayList();
   private final EntityLivingBase b;
   private int c;
   private int d;
   private int e;
   private boolean f;
   private boolean g;
   private String h;

   public CombatTracker(EntityLivingBase var1) {
      this.b = var1;
   }

   public void a() {
      this.j();
      if (this.b.k_()) {
         Block var1 = this.b.o.p(new BlockPos(this.b.s, this.b.aR().b, this.b.u)).c();
         if (var1 == Blocks.au) {
            this.h = "ladder";
         } else if (var1 == Blocks.bn) {
            this.h = "vines";
         }
      } else if (this.b.V()) {
         this.h = "water";
      }
   }

   public void a(DamageSource var1, float var2, float var3) {
      this.g();
      this.a();
      CombatEntry var4 = new CombatEntry(var1, this.b.W, var2, var3, this.h, this.b.O);
      this.a.add(var4);
      this.c = this.b.W;
      this.g = true;
      if (var4.f() && !this.f && this.b.ai()) {
         this.f = true;
         this.d = this.b.W;
         this.e = this.d;
         this.b.h_();
      }
   }

   public IChatComponent b() {
      if (this.a.size() == 0) {
         return new ChatComponentTranslation("death.attack.generic", this.b.f_());
      } else {
         CombatEntry var1 = this.i();
         CombatEntry var2 = this.a.get(this.a.size() - 1);
         IChatComponent var4 = var2.h();
         Entity var5 = var2.a().j();
         Object var3;
         if (var1 != null && var2.a() == DamageSource.i) {
            IChatComponent var6 = var1.h();
            if (var1.a() == DamageSource.i || var1.a() == DamageSource.j) {
               var3 = new ChatComponentTranslation("death.fell.accident." + this.a(var1), this.b.f_());
            } else if (var6 != null && (var4 == null || !var6.equals(var4))) {
               Entity var9 = var1.a().j();
               ItemStack var8 = var9 instanceof EntityLivingBase ? ((EntityLivingBase)var9).bA() : null;
               if (var8 != null && var8.s()) {
                  var3 = new ChatComponentTranslation("death.fell.assist.item", this.b.f_(), var6, var8.C());
               } else {
                  var3 = new ChatComponentTranslation("death.fell.assist", this.b.f_(), var6);
               }
            } else if (var4 != null) {
               ItemStack var7 = var5 instanceof EntityLivingBase ? ((EntityLivingBase)var5).bA() : null;
               if (var7 != null && var7.s()) {
                  var3 = new ChatComponentTranslation("death.fell.finish.item", this.b.f_(), var4, var7.C());
               } else {
                  var3 = new ChatComponentTranslation("death.fell.finish", this.b.f_(), var4);
               }
            } else {
               var3 = new ChatComponentTranslation("death.fell.killer", this.b.f_());
            }
         } else {
            var3 = var2.a().b(this.b);
         }

         return (IChatComponent)var3;
      }
   }

   public EntityLivingBase c() {
      EntityLivingBase var1 = null;
      EntityPlayer var2 = null;
      float var3 = 0.0F;
      float var4 = 0.0F;

      for (CombatEntry var6 : this.a) {
         if (var6.a().j() instanceof EntityPlayer && (var2 == null || var6.c() > var4)) {
            var4 = var6.c();
            var2 = (EntityPlayer)var6.a().j();
         }

         if (var6.a().j() instanceof EntityLivingBase && (var1 == null || var6.c() > var3)) {
            var3 = var6.c();
            var1 = (EntityLivingBase)var6.a().j();
         }
      }

      return (EntityLivingBase)(var2 != null && var4 >= var3 / 3.0F ? var2 : var1);
   }

   private CombatEntry i() {
      CombatEntry var1 = null;
      CombatEntry var2 = null;
      byte var3 = 0;
      float var4 = 0.0F;

      for (int var5 = 0; var5 < this.a.size(); var5++) {
         CombatEntry var6 = this.a.get(var5);
         CombatEntry var7 = var5 > 0 ? this.a.get(var5 - 1) : null;
         if ((var6.a() == DamageSource.i || var6.a() == DamageSource.j) && var6.i() > 0.0F && (var1 == null || var6.i() > var4)) {
            if (var5 > 0) {
               var1 = var7;
            } else {
               var1 = var6;
            }

            var4 = var6.i();
         }

         if (var6.g() != null && (var2 == null || var6.c() > var3)) {
            var2 = var6;
         }
      }

      if (var4 > 5.0F && var1 != null) {
         return var1;
      } else {
         return var3 > 5 && var2 != null ? var2 : null;
      }
   }

   private String a(CombatEntry var1) {
      return var1.g() == null ? "generic" : var1.g();
   }

   public int f() {
      return this.f ? this.b.W - this.d : this.e - this.d;
   }

   private void j() {
      this.h = null;
   }

   public void g() {
      int var1 = this.f ? 300 : 100;
      if (this.g && (!this.b.ai() || this.b.W - this.c > var1)) {
         boolean var2 = this.f;
         this.g = false;
         this.f = false;
         this.e = this.b.W;
         if (var2) {
            this.b.j();
         }

         this.a.clear();
      }
   }

   public EntityLivingBase h() {
      return this.b;
   }
}
