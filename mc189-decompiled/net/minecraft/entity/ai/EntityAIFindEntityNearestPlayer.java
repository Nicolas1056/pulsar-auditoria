package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import java.util.Collections;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.Team;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityAIFindEntityNearestPlayer extends EntityAIBase {
   private static final Logger a = LogManager.getLogger();
   private EntityLiving b;
   private final Predicate<Entity> c;
   private final EntityAINearestAttackableTarget.Sorter d;
   private EntityLivingBase e;

   public EntityAIFindEntityNearestPlayer(EntityLiving var1) {
      this.b = var1;
      if (var1 instanceof EntityCreature) {
         a.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
      }

      this.c = new Predicate<Entity>() {
         public boolean a(Entity var1) {
            if (!(var1 instanceof EntityPlayer)) {
               return false;
            } else if (((EntityPlayer)var1).bA.a) {
               return false;
            } else {
               double var2 = EntityAIFindEntityNearestPlayer.this.f();
               if (var1.av()) {
                  var2 *= 0.8F;
               }

               if (var1.ax()) {
                  float var4 = ((EntityPlayer)var1).bY();
                  if (var4 < 0.1F) {
                     var4 = 0.1F;
                  }

                  var2 *= 0.7F * var4;
               }

               return var1.g(EntityAIFindEntityNearestPlayer.this.b) > var2
                  ? false
                  : EntityAITarget.a(EntityAIFindEntityNearestPlayer.this.b, (EntityLivingBase)var1, false, true);
            }
         }
      };
      this.d = new EntityAINearestAttackableTarget.Sorter(var1);
   }

   @Override
   public boolean a() {
      double var1 = this.f();
      List var3 = this.b.o.a(EntityPlayer.class, this.b.aR().b(var1, 4.0, var1), this.c);
      Collections.sort(var3, this.d);
      if (var3.isEmpty()) {
         return false;
      } else {
         this.e = (EntityLivingBase)var3.get(0);
         return true;
      }
   }

   @Override
   public boolean b() {
      EntityLivingBase var1 = this.b.u();
      if (var1 == null) {
         return false;
      } else if (!var1.ai()) {
         return false;
      } else if (var1 instanceof EntityPlayer && ((EntityPlayer)var1).bA.a) {
         return false;
      } else {
         Team var2 = this.b.bO();
         Team var3 = var1.bO();
         if (var2 != null && var3 == var2) {
            return false;
         } else {
            double var4 = this.f();
            return this.b.h(var1) > var4 * var4 ? false : !(var1 instanceof EntityPlayerMP) || !((EntityPlayerMP)var1).c.d();
         }
      }
   }

   @Override
   public void c() {
      this.b.d(this.e);
      super.c();
   }

   @Override
   public void d() {
      this.b.d(null);
      super.c();
   }

   protected double f() {
      IAttributeInstance var1 = this.b.a(SharedMonsterAttributes.b);
      return var1 == null ? 16.0 : var1.e();
   }
}
