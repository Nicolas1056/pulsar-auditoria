package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.Vec3;

public class EntityAIWander extends EntityAIBase {
   private EntityCreature a;
   private double b;
   private double c;
   private double d;
   private double e;
   private int f;
   private boolean g;

   public EntityAIWander(EntityCreature var1, double var2) {
      this(var1, var2, 120);
   }

   public EntityAIWander(EntityCreature var1, double var2, int var4) {
      this.a = var1;
      this.e = var2;
      this.f = var4;
      this.a(1);
   }

   @Override
   public boolean a() {
      if (!this.g) {
         if (this.a.bh() >= 100) {
            return false;
         }

         if (this.a.bc().nextInt(this.f) != 0) {
            return false;
         }
      }

      Vec3 var1 = RandomPositionGenerator.a(this.a, 10, 7);
      if (var1 == null) {
         return false;
      } else {
         this.b = var1.a;
         this.c = var1.b;
         this.d = var1.c;
         this.g = false;
         return true;
      }
   }

   @Override
   public boolean b() {
      return !this.a.s().m();
   }

   @Override
   public void c() {
      this.a.s().a(this.b, this.c, this.d, this.e);
   }

   public void f() {
      this.g = true;
   }

   public void b(int var1) {
      this.f = var1;
   }
}
