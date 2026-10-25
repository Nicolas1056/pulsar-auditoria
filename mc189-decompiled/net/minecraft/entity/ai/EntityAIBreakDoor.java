package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.EnumDifficulty;

public class EntityAIBreakDoor extends EntityAIDoorInteract {
   private int g;
   private int h = -1;

   public EntityAIBreakDoor(EntityLiving var1) {
      super(var1);
   }

   @Override
   public boolean a() {
      if (!super.a()) {
         return false;
      } else {
         return !this.a.o.Q().b("mobGriefing") ? false : !BlockDoor.f(this.a.o, this.b);
      }
   }

   @Override
   public void c() {
      super.c();
      this.g = 0;
   }

   @Override
   public boolean b() {
      double var1 = this.a.b(this.b);
      return this.g <= 240 && !BlockDoor.f(this.a.o, this.b) && var1 < 4.0;
   }

   @Override
   public void d() {
      super.d();
      this.a.o.c(this.a.F(), this.b, -1);
   }

   @Override
   public void e() {
      super.e();
      if (this.a.bc().nextInt(20) == 0) {
         this.a.o.b(1010, this.b, 0);
      }

      this.g++;
      int var1 = (int)(this.g / 240.0F * 10.0F);
      if (var1 != this.h) {
         this.a.o.c(this.a.F(), this.b, var1);
         this.h = var1;
      }

      if (this.g == 240 && this.a.o.aa() == EnumDifficulty.d) {
         this.a.o.g(this.b);
         this.a.o.b(1012, this.b, 0);
         this.a.o.b(2001, this.b, Block.a(this.c));
      }
   }
}
