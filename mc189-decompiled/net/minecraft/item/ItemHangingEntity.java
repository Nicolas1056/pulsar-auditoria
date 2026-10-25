package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemHangingEntity extends Item {
   private final Class<? extends EntityHanging> a;

   public ItemHangingEntity(Class<? extends EntityHanging> var1) {
      this.a = var1;
      this.a(CreativeTabs.c);
   }

   @Override
   public boolean a(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var5 == EnumFacing.a) {
         return false;
      } else if (var5 == EnumFacing.b) {
         return false;
      } else {
         BlockPos var9 = var4.a(var5);
         if (!var2.a(var9, var5, var1)) {
            return false;
         } else {
            EntityHanging var10 = this.a(var3, var9, var5);
            if (var10 != null && var10.j()) {
               if (!var3.D) {
                  var3.d(var10);
               }

               var1.b--;
            }

            return true;
         }
      }
   }

   private EntityHanging a(World var1, BlockPos var2, EnumFacing var3) {
      if (this.a == EntityPainting.class) {
         return new EntityPainting(var1, var2, var3);
      } else {
         return this.a == EntityItemFrame.class ? new EntityItemFrame(var1, var2, var3) : null;
      }
   }
}
