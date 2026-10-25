package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;

public class EntityAIMoveIndoors extends EntityAIBase {
   private EntityCreature a;
   private VillageDoorInfo b;
   private int c = -1;
   private int d = -1;

   public EntityAIMoveIndoors(EntityCreature var1) {
      this.a = var1;
      this.a(1);
   }

   @Override
   public boolean a() {
      BlockPos var1 = new BlockPos(this.a);
      if ((!this.a.o.w() || this.a.o.S() && !this.a.o.b(var1).e()) && !this.a.o.t.o()) {
         if (this.a.bc().nextInt(50) != 0) {
            return false;
         } else if (this.c != -1 && this.a.e(this.c, this.a.t, this.d) < 4.0) {
            return false;
         } else {
            Village var2 = this.a.o.ae().a(var1, 14);
            if (var2 == null) {
               return false;
            } else {
               this.b = var2.c(var1);
               return this.b != null;
            }
         }
      } else {
         return false;
      }
   }

   @Override
   public boolean b() {
      return !this.a.s().m();
   }

   @Override
   public void c() {
      this.c = -1;
      BlockPos var1 = this.b.e();
      int var2 = var1.n();
      int var3 = var1.o();
      int var4 = var1.p();
      if (this.a.b(var1) > 256.0) {
         Vec3 var5 = RandomPositionGenerator.a(this.a, 14, 3, new Vec3(var2 + 0.5, var3, var4 + 0.5));
         if (var5 != null) {
            this.a.s().a(var5.a, var5.b, var5.c, 1.0);
         }
      } else {
         this.a.s().a(var2 + 0.5, var3, var4 + 0.5, 1.0);
      }
   }

   @Override
   public void d() {
      this.c = this.b.e().n();
      this.d = this.b.e().p();
      this.b = null;
   }
}
