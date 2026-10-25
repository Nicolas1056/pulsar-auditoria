package net.minecraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemWritableBook extends Item {
   public ItemWritableBook() {
      this.c(1);
   }

   @Override
   public ItemStack a(ItemStack var1, World var2, EntityPlayer var3) {
      var3.a(var1);
      var3.b(StatList.ad[Item.b(this)]);
      return var1;
   }

   public static boolean b(NBTTagCompound var0) {
      if (var0 == null) {
         return false;
      } else if (!var0.b("pages", 9)) {
         return false;
      } else {
         NBTTagList var1 = var0.c("pages", 8);

         for (int var2 = 0; var2 < var1.c(); var2++) {
            String var3 = var1.f(var2);
            if (var3 == null) {
               return false;
            }

            if (var3.length() > 32767) {
               return false;
            }
         }

         return true;
      }
   }
}
