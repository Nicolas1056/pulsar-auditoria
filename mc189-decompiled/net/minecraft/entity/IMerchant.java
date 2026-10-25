package net.minecraft.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IChatComponent;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public interface IMerchant {
   void a_(EntityPlayer var1);

   EntityPlayer v_();

   MerchantRecipeList b_(EntityPlayer var1);

   void a(MerchantRecipeList var1);

   void a(MerchantRecipe var1);

   void a_(ItemStack var1);

   IChatComponent f_();
}
