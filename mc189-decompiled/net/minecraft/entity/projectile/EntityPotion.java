package net.minecraft.entity.projectile;

import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityPotion extends EntityThrowable {
   private ItemStack c;

   public EntityPotion(World var1) {
      super(var1);
   }

   public EntityPotion(World var1, EntityLivingBase var2, int var3) {
      this(var1, var2, new ItemStack(Items.bz, 1, var3));
   }

   public EntityPotion(World var1, EntityLivingBase var2, ItemStack var3) {
      super(var1, var2);
      this.c = var3;
   }

   public EntityPotion(World var1, double var2, double var4, double var6, int var8) {
      this(var1, var2, var4, var6, new ItemStack(Items.bz, 1, var8));
   }

   public EntityPotion(World var1, double var2, double var4, double var6, ItemStack var8) {
      super(var1, var2, var4, var6);
      this.c = var8;
   }

   @Override
   protected float m() {
      return 0.05F;
   }

   @Override
   protected float j() {
      return 0.5F;
   }

   @Override
   protected float l() {
      return -20.0F;
   }

   public void a(int var1) {
      if (this.c == null) {
         this.c = new ItemStack(Items.bz, 1, 0);
      }

      this.c.b(var1);
   }

   public int o() {
      if (this.c == null) {
         this.c = new ItemStack(Items.bz, 1, 0);
      }

      return this.c.i();
   }

   @Override
   protected void a(MovingObjectPosition var1) {
      if (!this.o.D) {
         List var2 = Items.bz.h(this.c);
         if (var2 != null && !var2.isEmpty()) {
            AxisAlignedBB var3 = this.aR().b(4.0, 2.0, 4.0);
            List var4 = this.o.a(EntityLivingBase.class, var3);
            if (!var4.isEmpty()) {
               for (EntityLivingBase var6 : var4) {
                  double var7 = this.h(var6);
                  if (var7 < 16.0) {
                     double var9 = 1.0 - Math.sqrt(var7) / 4.0;
                     if (var6 == var1.d) {
                        var9 = 1.0;
                     }

                     for (PotionEffect var12 : var2) {
                        int var13 = var12.a();
                        if (Potion.a[var13].b()) {
                           Potion.a[var13].a(this, this.n(), var6, var12.c(), var9);
                        } else {
                           int var14 = (int)(var9 * var12.b() + 0.5);
                           if (var14 > 20) {
                              var6.c(new PotionEffect(var13, var14, var12.c()));
                           }
                        }
                     }
                  }
               }
            }
         }

         this.o.b(2002, new BlockPos(this), this.o());
         this.J();
      }
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      if (var1.b("Potion", 10)) {
         this.c = ItemStack.a(var1.m("Potion"));
      } else {
         this.a(var1.f("potionValue"));
      }

      if (this.c == null) {
         this.J();
      }
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      if (this.c != null) {
         var1.a("Potion", this.c.b(new NBTTagCompound()));
      }
   }
}
