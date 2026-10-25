package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EntitySelectors;

public class EntityAINearestAttackableTarget<T extends EntityLivingBase> extends EntityAITarget {
   protected final Class<T> a;
   private final int g;
   protected final EntityAINearestAttackableTarget.Sorter b;
   protected Predicate<? super T> c;
   protected EntityLivingBase d;

   public EntityAINearestAttackableTarget(EntityCreature var1, Class<T> var2, boolean var3) {
      this(var1, var2, var3, false);
   }

   public EntityAINearestAttackableTarget(EntityCreature var1, Class<T> var2, boolean var3, boolean var4) {
      this(var1, var2, 10, var3, var4, null);
   }

   public EntityAINearestAttackableTarget(EntityCreature var1, Class<T> var2, int var3, boolean var4, boolean var5, final Predicate<? super T> var6) {
      super(var1, var4, var5);
      this.a = var2;
      this.g = var3;
      this.b = new EntityAINearestAttackableTarget.Sorter(var1);
      this.a(1);
      this.c = new Predicate<T>() {
         public boolean a(T var1) {
            if (var6 != null && !var6.apply(var1)) {
               return false;
            } else {
               if (var1 instanceof EntityPlayer) {
                  double var2x = EntityAINearestAttackableTarget.this.f();
                  if (var1.av()) {
                     var2x *= 0.8F;
                  }

                  if (var1.ax()) {
                     float var4x = ((EntityPlayer)var1).bY();
                     if (var4x < 0.1F) {
                        var4x = 0.1F;
                     }

                     var2x *= 0.7F * var4x;
                  }

                  if (var1.g(EntityAINearestAttackableTarget.this.e) > var2x) {
                     return false;
                  }
               }

               return EntityAINearestAttackableTarget.this.a(var1, false);
            }
         }
      };
   }

   @Override
   public boolean a() {
      if (this.g > 0 && this.e.bc().nextInt(this.g) != 0) {
         return false;
      } else {
         double var1 = this.f();
         List var3 = this.e.o.a(this.a, this.e.aR().b(var1, 4.0, var1), Predicates.and(this.c, EntitySelectors.d));
         Collections.sort(var3, this.b);
         if (var3.isEmpty()) {
            return false;
         } else {
            this.d = (EntityLivingBase)var3.get(0);
            return true;
         }
      }
   }

   @Override
   public void c() {
      this.e.d(this.d);
      super.c();
   }

   public static class Sorter implements Comparator<Entity> {
      private final Entity a;

      public Sorter(Entity var1) {
         this.a = var1;
      }

      public int a(Entity var1, Entity var2) {
         double var3 = this.a.h(var1);
         double var5 = this.a.h(var2);
         if (var3 < var5) {
            return -1;
         } else {
            return var3 > var5 ? 1 : 0;
         }
      }
   }
}
