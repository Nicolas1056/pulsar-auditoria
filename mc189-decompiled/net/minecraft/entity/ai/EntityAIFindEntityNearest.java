package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import java.util.Collections;
import java.util.List;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityAIFindEntityNearest extends EntityAIBase {
   private static final Logger a = LogManager.getLogger();
   private EntityLiving b;
   private final Predicate<EntityLivingBase> c;
   private final EntityAINearestAttackableTarget.Sorter d;
   private EntityLivingBase e;
   private Class<? extends EntityLivingBase> f;

   public EntityAIFindEntityNearest(EntityLiving var1, Class<? extends EntityLivingBase> var2) {
      this.b = var1;
      this.f = var2;
      if (var1 instanceof EntityCreature) {
         a.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
      }

      this.c = new Predicate<EntityLivingBase>() {
         public boolean a(EntityLivingBase var1) {
            double var2x = EntityAIFindEntityNearest.this.f();
            if (var1.av()) {
               var2x *= 0.8F;
            }

            if (var1.ax()) {
               return false;
            } else {
               return var1.g(EntityAIFindEntityNearest.this.b) > var2x ? false : EntityAITarget.a(EntityAIFindEntityNearest.this.b, var1, false, true);
            }
         }
      };
      this.d = new EntityAINearestAttackableTarget.Sorter(var1);
   }

   @Override
   public boolean a() {
      double var1 = this.f();
      List var3 = this.b.o.a(this.f, this.b.aR().b(var1, 4.0, var1), this.c);
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
      } else {
         double var2 = this.f();
         return this.b.h(var1) > var2 * var2 ? false : !(var1 instanceof EntityPlayerMP) || !((EntityPlayerMP)var1).c.d();
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
