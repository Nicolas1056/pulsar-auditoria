package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityAIFleeSun extends EntityAIBase {
   private EntityCreature a;
   private double b;
   private double c;
   private double d;
   private double e;
   private World f;

   public EntityAIFleeSun(EntityCreature var1, double var2) {
      this.a = var1;
      this.e = var2;
      this.f = var1.o;
      this.a(1);
   }

   @Override
   public boolean a() {
      if (!this.f.w()) {
         return false;
      } else if (!this.a.at()) {
         return false;
      } else if (!this.f.i(new BlockPos(this.a.s, this.a.aR().b, this.a.u))) {
         return false;
      } else {
         Vec3 var1 = this.f();
         if (var1 == null) {
            return false;
         } else {
            this.b = var1.a;
            this.c = var1.b;
            this.d = var1.c;
            return true;
         }
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

   private Vec3 f() {
      Random var1 = this.a.bc();
      BlockPos var2 = new BlockPos(this.a.s, this.a.aR().b, this.a.u);

      for (int var3 = 0; var3 < 10; var3++) {
         BlockPos var4 = var2.a(var1.nextInt(20) - 10, var1.nextInt(6) - 3, var1.nextInt(20) - 10);
         if (!this.f.i(var4) && this.a.a(var4) < 0.0F) {
            return new Vec3(var4.n(), var4.o(), var4.p());
         }
      }

      return null;
   }
}
