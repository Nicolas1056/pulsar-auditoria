package net.minecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EntityDamageSource extends DamageSource {
   protected Entity q;
   private boolean r = false;

   public EntityDamageSource(String var1, Entity var2) {
      super(var1);
      this.q = var2;
   }

   public EntityDamageSource v() {
      this.r = true;
      return this;
   }

   public boolean w() {
      return this.r;
   }

   @Override
   public Entity j() {
      return this.q;
   }

   @Override
   public IChatComponent b(EntityLivingBase var1) {
      ItemStack var2 = this.q instanceof EntityLivingBase ? ((EntityLivingBase)this.q).bA() : null;
      String var3 = "death.attack." + this.p;
      String var4 = var3 + ".item";
      return var2 != null && var2.s() && StatCollector.c(var4)
         ? new ChatComponentTranslation(var4, var1.f_(), this.q.f_(), var2.C())
         : new ChatComponentTranslation(var3, var1.f_(), this.q.f_());
   }

   @Override
   public boolean r() {
      return this.q != null && this.q instanceof EntityLivingBase && !(this.q instanceof EntityPlayer);
   }
}
