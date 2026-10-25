package net.minecraft.item;

import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemAppleGold extends ItemFood {
   public ItemAppleGold(int var1, float var2, boolean var3) {
      super(var1, var2, var3);
      this.a(true);
   }

   @Override
   public boolean f(ItemStack var1) {
      return var1.i() > 0;
   }

   @Override
   public EnumRarity g(ItemStack var1) {
      return var1.i() == 0 ? EnumRarity.c : EnumRarity.d;
   }

   @Override
   protected void c(ItemStack var1, World var2, EntityPlayer var3) {
      if (!var2.D) {
         var3.c(new PotionEffect(Potion.x.H, 2400, 0));
      }

      if (var1.i() > 0) {
         if (!var2.D) {
            var3.c(new PotionEffect(Potion.l.H, 600, 4));
            var3.c(new PotionEffect(Potion.m.H, 6000, 0));
            var3.c(new PotionEffect(Potion.n.H, 6000, 0));
         }
      } else {
         super.c(var1, var2, var3);
      }
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      var3.add(new ItemStack(var1, 1, 0));
      var3.add(new ItemStack(var1, 1, 1));
   }
}
