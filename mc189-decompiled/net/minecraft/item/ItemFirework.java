package net.minecraft.item;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemFirework extends Item {
   @Override
   public boolean a(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      if (!var3.D) {
         EntityFireworkRocket var9 = new EntityFireworkRocket(var3, var4.n() + var6, var4.o() + var7, var4.p() + var8, var1);
         var3.d(var9);
         if (!var2.bA.d) {
            var1.b--;
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public void a(ItemStack var1, EntityPlayer var2, List<String> var3, boolean var4) {
      if (var1.n()) {
         NBTTagCompound var5 = var1.o().m("Fireworks");
         if (var5 != null) {
            if (var5.b("Flight", 99)) {
               var3.add(StatCollector.a("item.fireworks.flight") + " " + var5.d("Flight"));
            }

            NBTTagList var6 = var5.c("Explosions", 10);
            if (var6 != null && var6.c() > 0) {
               for (int var7 = 0; var7 < var6.c(); var7++) {
                  NBTTagCompound var8 = var6.b(var7);
                  ArrayList var9 = Lists.newArrayList();
                  ItemFireworkCharge.a(var8, var9);
                  if (var9.size() > 0) {
                     for (int var10 = 1; var10 < var9.size(); var10++) {
                        var9.set(var10, "  " + (String)var9.get(var10));
                     }

                     var3.addAll(var9);
                  }
               }
            }
         }
      }
   }
}
