package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;

public class EntityAIRestrictOpenDoor extends EntityAIBase {
   private EntityCreature a;
   private VillageDoorInfo b;

   public EntityAIRestrictOpenDoor(EntityCreature var1) {
      this.a = var1;
      if (!(var1.s() instanceof PathNavigateGround)) {
         throw new IllegalArgumentException("Unsupported mob type for RestrictOpenDoorGoal");
      }
   }

   @Override
   public boolean a() {
      if (this.a.o.w()) {
         return false;
      } else {
         BlockPos var1 = new BlockPos(this.a);
         Village var2 = this.a.o.ae().a(var1, 16);
         if (var2 == null) {
            return false;
         } else {
            this.b = var2.b(var1);
            return this.b == null ? false : this.b.b(var1) < 2.25;
         }
      }
   }

   @Override
   public boolean b() {
      return this.a.o.w() ? false : !this.b.i() && this.b.c(new BlockPos(this.a));
   }

   @Override
   public void c() {
      ((PathNavigateGround)this.a.s()).b(false);
      ((PathNavigateGround)this.a.s()).c(false);
   }

   @Override
   public void d() {
      ((PathNavigateGround)this.a.s()).b(true);
      ((PathNavigateGround)this.a.s()).c(true);
      this.b = null;
   }

   @Override
   public void e() {
      this.b.b();
   }
}
