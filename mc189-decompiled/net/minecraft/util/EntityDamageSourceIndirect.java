package net.minecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class EntityDamageSourceIndirect extends EntityDamageSource {
   private Entity r;

   public EntityDamageSourceIndirect(String var1, Entity var2, Entity var3) {
      super(var1, var2);
      this.r = var3;
   }

   @Override
   public Entity i() {
      return this.q;
   }

   @Override
   public Entity j() {
      return this.r;
   }

   @Override
   public IChatComponent b(EntityLivingBase var1) {
      IChatComponent var2 = this.r == null ? this.q.f_() : this.r.f_();
      ItemStack var3 = this.r instanceof EntityLivingBase ? ((EntityLivingBase)this.r).bA() : null;
      String var4 = "death.attack." + this.p;
      String var5 = var4 + ".item";
      return var3 != null && var3.s() && StatCollector.c(var5)
         ? new ChatComponentTranslation(var5, var1.f_(), var2, var3.C())
         : new ChatComponentTranslation(var4, var1.f_(), var2);
   }
}
