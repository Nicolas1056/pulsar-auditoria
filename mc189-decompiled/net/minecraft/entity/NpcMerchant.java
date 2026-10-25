package net.minecraft.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryMerchant;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class NpcMerchant implements IMerchant {
   private InventoryMerchant a;
   private EntityPlayer b;
   private MerchantRecipeList c;
   private IChatComponent d;

   public NpcMerchant(EntityPlayer var1, IChatComponent var2) {
      this.b = var1;
      this.d = var2;
      this.a = new InventoryMerchant(var1, this);
   }

   @Override
   public EntityPlayer v_() {
      return this.b;
   }

   @Override
   public void a_(EntityPlayer var1) {
   }

   @Override
   public MerchantRecipeList b_(EntityPlayer var1) {
      return this.c;
   }

   @Override
   public void a(MerchantRecipeList var1) {
      this.c = var1;
   }

   @Override
   public void a(MerchantRecipe var1) {
      var1.g();
   }

   @Override
   public void a_(ItemStack var1) {
   }

   @Override
   public IChatComponent f_() {
      return (IChatComponent)(this.d != null ? this.d : new ChatComponentTranslation("entity.Villager.name"));
   }
}
