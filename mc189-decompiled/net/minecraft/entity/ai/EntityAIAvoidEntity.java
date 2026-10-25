package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.Vec3;

public class EntityAIAvoidEntity<T extends Entity> extends EntityAIBase {
   private final Predicate<Entity> c = new Predicate<Entity>() {
      public boolean a(Entity var1) {
         return var1.ai() && EntityAIAvoidEntity.this.a.t().a(var1);
      }
   };
   protected EntityCreature a;
   private double d;
   private double e;
   protected T b;
   private float f;
   private PathEntity g;
   private PathNavigate h;
   private Class<T> i;
   private Predicate<? super T> j;

   public EntityAIAvoidEntity(EntityCreature var1, Class<T> var2, float var3, double var4, double var6) {
      this(var1, var2, Predicates.alwaysTrue(), var3, var4, var6);
   }

   public EntityAIAvoidEntity(EntityCreature var1, Class<T> var2, Predicate<? super T> var3, float var4, double var5, double var7) {
      this.a = var1;
      this.i = var2;
      this.j = var3;
      this.f = var4;
      this.d = var5;
      this.e = var7;
      this.h = var1.s();
      this.a(1);
   }

   @Override
   public boolean a() {
      List var1 = this.a.o.a(this.i, this.a.aR().b(this.f, 3.0, this.f), Predicates.and(new Predicate[]{EntitySelectors.d, this.c, this.j}));
      if (var1.isEmpty()) {
         return false;
      } else {
         this.b = (T)var1.get(0);
         Vec3 var2 = RandomPositionGenerator.b(this.a, 16, 7, new Vec3(this.b.s, this.b.t, this.b.u));
         if (var2 == null) {
            return false;
         } else if (this.b.e(var2.a, var2.b, var2.c) < this.b.h(this.a)) {
            return false;
         } else {
            this.g = this.h.a(var2.a, var2.b, var2.c);
            return this.g == null ? false : this.g.b(var2);
         }
      }
   }

   @Override
   public boolean b() {
      return !this.h.m();
   }

   @Override
   public void c() {
      this.h.a(this.g, this.d);
   }

   @Override
   public void d() {
      this.b = null;
   }

   @Override
   public void e() {
      if (this.a.h(this.b) < 49.0) {
         this.a.s().a(this.e);
      } else {
         this.a.s().a(this.d);
      }
   }
}
