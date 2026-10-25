package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityAIOpenDoor extends EntityAIDoorInteract {
   boolean g;
   int h;

   public EntityAIOpenDoor(EntityLiving var1, boolean var2) {
      super(var1);
      this.a = var1;
      this.g = var2;
   }

   @Override
   public boolean b() {
      return this.g && this.h > 0 && super.b();
   }

   @Override
   public void c() {
      this.h = 20;
      this.c.a(this.a.o, this.b, true);
   }

   @Override
   public void d() {
      if (this.g) {
         this.c.a(this.a.o, this.b, false);
      }
   }

   @Override
   public void e() {
      this.h--;
      super.e();
   }
}
