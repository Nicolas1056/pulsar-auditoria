package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.StringUtils;

public abstract class EntityAITarget extends EntityAIBase {
   protected final EntityCreature e;
   protected boolean f;
   private boolean a;
   private int b;
   private int c;
   private int d;

   public EntityAITarget(EntityCreature var1, boolean var2) {
      this(var1, var2, false);
   }

   public EntityAITarget(EntityCreature var1, boolean var2, boolean var3) {
      this.e = var1;
      this.f = var2;
      this.a = var3;
   }

   @Override
   public boolean b() {
      EntityLivingBase var1 = this.e.u();
      if (var1 == null) {
         return false;
      } else if (!var1.ai()) {
         return false;
      } else {
         Team var2 = this.e.bO();
         Team var3 = var1.bO();
         if (var2 != null && var3 == var2) {
            return false;
         } else {
            double var4 = this.f();
            if (this.e.h(var1) > var4 * var4) {
               return false;
            } else {
               if (this.f) {
                  if (this.e.t().a(var1)) {
                     this.d = 0;
                  } else if (++this.d > 60) {
                     return false;
                  }
               }

               return !(var1 instanceof EntityPlayer) || !((EntityPlayer)var1).bA.a;
            }
         }
      }
   }

   protected double f() {
      IAttributeInstance var1 = this.e.a(SharedMonsterAttributes.b);
      return var1 == null ? 16.0 : var1.e();
   }

   @Override
   public void c() {
      this.b = 0;
      this.c = 0;
      this.d = 0;
   }

   @Override
   public void d() {
      this.e.d(null);
   }

   public static boolean a(EntityLiving var0, EntityLivingBase var1, boolean var2, boolean var3) {
      if (var1 == null) {
         return false;
      } else if (var1 == var0) {
         return false;
      } else if (!var1.ai()) {
         return false;
      } else if (!var0.a((Class<? extends EntityLivingBase>)var1.getClass())) {
         return false;
      } else {
         Team var4 = var0.bO();
         Team var5 = var1.bO();
         if (var4 != null && var5 == var4) {
            return false;
         } else {
            if (var0 instanceof IEntityOwnable && StringUtils.isNotEmpty(((IEntityOwnable)var0).b())) {
               if (var1 instanceof IEntityOwnable && ((IEntityOwnable)var0).b().equals(((IEntityOwnable)var1).b())) {
                  return false;
               }

               if (var1 == ((IEntityOwnable)var0).m_()) {
                  return false;
               }
            } else if (var1 instanceof EntityPlayer && !var2 && ((EntityPlayer)var1).bA.a) {
               return false;
            }

            return !var3 || var0.t().a(var1);
         }
      }
   }

   protected boolean a(EntityLivingBase var1, boolean var2) {
      if (!a(this.e, var1, var2, this.f)) {
         return false;
      } else if (!this.e.e(new BlockPos(var1))) {
         return false;
      } else {
         if (this.a) {
            if (--this.c <= 0) {
               this.b = 0;
            }

            if (this.b == 0) {
               this.b = this.a(var1) ? 1 : 2;
            }

            if (this.b == 2) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean a(EntityLivingBase var1) {
      this.c = 10 + this.e.bc().nextInt(5);
      PathEntity var2 = this.e.s().a(var1);
      if (var2 == null) {
         return false;
      } else {
         PathPoint var3 = var2.c();
         if (var3 == null) {
            return false;
         } else {
            int var4 = var3.a - MathHelper.c(var1.s);
            int var5 = var3.c - MathHelper.c(var1.u);
            return var4 * var4 + var5 * var5 <= 2.25;
         }
      }
   }
}
