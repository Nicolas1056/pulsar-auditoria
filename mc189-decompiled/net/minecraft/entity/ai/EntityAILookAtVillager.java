package net.minecraft.entity.ai;

import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;

public class EntityAILookAtVillager extends EntityAIBase {
   private EntityIronGolem a;
   private EntityVillager b;
   private int c;

   public EntityAILookAtVillager(EntityIronGolem var1) {
      this.a = var1;
      this.a(3);
   }

   @Override
   public boolean a() {
      if (!this.a.o.w()) {
         return false;
      } else if (this.a.bc().nextInt(8000) != 0) {
         return false;
      } else {
         this.b = this.a.o.a(EntityVillager.class, this.a.aR().b(6.0, 2.0, 6.0), this.a);
         return this.b != null;
      }
   }

   @Override
   public boolean b() {
      return this.c > 0;
   }

   @Override
   public void c() {
      this.c = 400;
      this.a.a(true);
   }

   @Override
   public void d() {
      this.a.a(false);
      this.b = null;
   }

   @Override
   public void e() {
      this.a.p().a(this.b, 30.0F, 30.0F);
      this.c--;
   }
}
