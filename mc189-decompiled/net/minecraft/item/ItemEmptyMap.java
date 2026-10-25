package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;

public class ItemEmptyMap extends ItemMapBase {
   protected ItemEmptyMap() {
      this.a(CreativeTabs.f);
   }

   @Override
   public ItemStack a(ItemStack var1, World var2, EntityPlayer var3) {
      ItemStack var4 = new ItemStack(Items.bd, 1, var2.b("map"));
      String var5 = "map_" + var4.i();
      MapData var6 = new MapData(var5);
      var2.a(var5, var6);
      var6.e = 0;
      var6.a(var3.s, var3.u, var6.e);
      var6.d = (byte)var2.t.q();
      var6.c();
      var1.b--;
      if (var1.b <= 0) {
         return var4;
      } else {
         if (!var3.bi.a(var4.k())) {
            var3.a(var4, false);
         }

         var3.b(StatList.ad[Item.b(this)]);
         return var1;
      }
   }
}
