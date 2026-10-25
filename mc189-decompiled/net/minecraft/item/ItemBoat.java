package net.minecraft.item;

import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemBoat extends Item {
   public ItemBoat() {
      this.h = 1;
      this.a(CreativeTabs.e);
   }

   @Override
   public ItemStack a(ItemStack var1, World var2, EntityPlayer var3) {
      float var4 = 1.0F;
      float var5 = var3.B + (var3.z - var3.B) * var4;
      float var6 = var3.A + (var3.y - var3.A) * var4;
      double var7 = var3.p + (var3.s - var3.p) * var4;
      double var9 = var3.q + (var3.t - var3.q) * var4 + var3.aS();
      double var11 = var3.r + (var3.u - var3.r) * var4;
      Vec3 var13 = new Vec3(var7, var9, var11);
      float var14 = MathHelper.b(-var6 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var15 = MathHelper.a(-var6 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var16 = -MathHelper.b(-var5 * (float) (Math.PI / 180.0));
      float var17 = MathHelper.a(-var5 * (float) (Math.PI / 180.0));
      float var18 = var15 * var16;
      float var20 = var14 * var16;
      double var21 = 5.0;
      Vec3 var23 = var13.b(var18 * var21, var17 * var21, var20 * var21);
      MovingObjectPosition var24 = var2.a(var13, var23, true);
      if (var24 == null) {
         return var1;
      } else {
         Vec3 var25 = var3.d(var4);
         boolean var26 = false;
         float var27 = 1.0F;
         List var28 = var2.b(var3, var3.aR().a(var25.a * var21, var25.b * var21, var25.c * var21).b(var27, var27, var27));

         for (int var29 = 0; var29 < var28.size(); var29++) {
            Entity var30 = (Entity)var28.get(var29);
            if (var30.ad()) {
               float var31 = var30.ao();
               AxisAlignedBB var32 = var30.aR().b(var31, var31, var31);
               if (var32.a(var13)) {
                  var26 = true;
               }
            }
         }

         if (var26) {
            return var1;
         } else {
            if (var24.a == MovingObjectPosition.MovingObjectType.b) {
               BlockPos var33 = var24.a();
               if (var2.p(var33).c() == Blocks.aH) {
                  var33 = var33.b();
               }

               EntityBoat var34 = new EntityBoat(var2, var33.n() + 0.5F, var33.o() + 1.0F, var33.p() + 0.5F);
               var34.y = ((MathHelper.c(var3.y * 4.0F / 360.0F + 0.5) & 3) - 1) * 90;
               if (!var2.a(var34, var34.aR().b(-0.1, -0.1, -0.1)).isEmpty()) {
                  return var1;
               }

               if (!var2.D) {
                  var2.d(var34);
               }

               if (!var3.bA.d) {
                  var1.b--;
               }

               var3.b(StatList.ad[Item.b(this)]);
            }

            return var1;
         }
      }
   }
}
