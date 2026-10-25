package net.minecraft.entity.ai;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class EntityAIVillagerInteract extends EntityAIWatchClosest2 {
   private int e;
   private EntityVillager f;

   public EntityAIVillagerInteract(EntityVillager var1) {
      super(var1, EntityVillager.class, 3.0F, 0.02F);
      this.f = var1;
   }

   @Override
   public void c() {
      super.c();
      if (this.f.cs() && this.b instanceof EntityVillager && ((EntityVillager)this.b).ct()) {
         this.e = 10;
      } else {
         this.e = 0;
      }
   }

   @Override
   public void e() {
      super.e();
      if (this.e > 0) {
         this.e--;
         if (this.e == 0) {
            InventoryBasic var1 = this.f.cq();

            for (int var2 = 0; var2 < var1.o_(); var2++) {
               ItemStack var3 = var1.a(var2);
               ItemStack var4 = null;
               if (var3 != null) {
                  Item var5 = var3.b();
                  if ((var5 == Items.P || var5 == Items.bS || var5 == Items.bR) && var3.b > 3) {
                     int var12 = var3.b / 2;
                     var3.b -= var12;
                     var4 = new ItemStack(var5, var12, var3.i());
                  } else if (var5 == Items.O && var3.b > 5) {
                     int var6 = var3.b / 2 / 3 * 3;
                     int var7 = var6 / 3;
                     var3.b -= var6;
                     var4 = new ItemStack(Items.P, var7, 0);
                  }

                  if (var3.b <= 0) {
                     var1.a(var2, null);
                  }
               }

               if (var4 != null) {
                  double var11 = this.f.t - 0.3F + this.f.aS();
                  EntityItem var13 = new EntityItem(this.f.o, this.f.s, var11, this.f.u, var4);
                  float var8 = 0.3F;
                  float var9 = this.f.aK;
                  float var10 = this.f.z;
                  var13.v = -MathHelper.a(var9 / 180.0F * (float) Math.PI) * MathHelper.b(var10 / 180.0F * (float) Math.PI) * var8;
                  var13.x = MathHelper.b(var9 / 180.0F * (float) Math.PI) * MathHelper.b(var10 / 180.0F * (float) Math.PI) * var8;
                  var13.w = -MathHelper.a(var10 / 180.0F * (float) Math.PI) * var8 + 0.1F;
                  var13.p();
                  this.f.o.d(var13);
                  break;
               }
            }
         }
      }
   }
}
