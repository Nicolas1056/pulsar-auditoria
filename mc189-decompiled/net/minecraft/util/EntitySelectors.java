package net.minecraft.util;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public final class EntitySelectors {
   public static final Predicate<Entity> a = new Predicate<Entity>() {
      public boolean a(Entity var1) {
         return var1.ai();
      }
   };
   public static final Predicate<Entity> b = new Predicate<Entity>() {
      public boolean a(Entity var1) {
         return var1.ai() && var1.l == null && var1.m == null;
      }
   };
   public static final Predicate<Entity> c = new Predicate<Entity>() {
      public boolean a(Entity var1) {
         return var1 instanceof IInventory && var1.ai();
      }
   };
   public static final Predicate<Entity> d = new Predicate<Entity>() {
      public boolean a(Entity var1) {
         return !(var1 instanceof EntityPlayer) || !((EntityPlayer)var1).v();
      }
   };

   public static class ArmoredMob implements Predicate<Entity> {
      private final ItemStack a;

      public ArmoredMob(ItemStack var1) {
         this.a = var1;
      }

      public boolean a(Entity var1) {
         if (!var1.ai()) {
            return false;
         } else if (!(var1 instanceof EntityLivingBase)) {
            return false;
         } else {
            EntityLivingBase var2 = (EntityLivingBase)var1;
            if (var2.p(EntityLiving.c(this.a)) != null) {
               return false;
            } else if (var2 instanceof EntityLiving) {
               return ((EntityLiving)var2).bY();
            } else {
               return var2 instanceof EntityArmorStand ? true : var2 instanceof EntityPlayer;
            }
         }
      }
   }
}
