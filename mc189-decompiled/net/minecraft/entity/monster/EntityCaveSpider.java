package net.minecraft.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityCaveSpider extends EntitySpider {
   public EntityCaveSpider(World var1) {
      super(var1);
      this.a(0.7F, 0.5F);
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.a).a(12.0);
   }

   @Override
   public boolean r(Entity var1) {
      if (super.r(var1)) {
         if (var1 instanceof EntityLivingBase) {
            byte var2 = 0;
            if (this.o.aa() == EnumDifficulty.c) {
               var2 = 7;
            } else if (this.o.aa() == EnumDifficulty.d) {
               var2 = 15;
            }

            if (var2 > 0) {
               ((EntityLivingBase)var1).c(new PotionEffect(Potion.u.H, var2 * 20, 0));
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public IEntityLivingData a(DifficultyInstance var1, IEntityLivingData var2) {
      return var2;
   }

   @Override
   public float aS() {
      return 0.45F;
   }
}
