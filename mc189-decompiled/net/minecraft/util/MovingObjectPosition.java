package net.minecraft.util;

import net.minecraft.entity.Entity;

public class MovingObjectPosition {
   private BlockPos e;
   public MovingObjectPosition.MovingObjectType a;
   public EnumFacing b;
   public Vec3 c;
   public Entity d;

   public MovingObjectPosition(Vec3 var1, EnumFacing var2, BlockPos var3) {
      this(MovingObjectPosition.MovingObjectType.b, var1, var2, var3);
   }

   public MovingObjectPosition(Vec3 var1, EnumFacing var2) {
      this(MovingObjectPosition.MovingObjectType.b, var1, var2, BlockPos.a);
   }

   public MovingObjectPosition(Entity var1) {
      this(var1, new Vec3(var1.s, var1.t, var1.u));
   }

   public MovingObjectPosition(MovingObjectPosition.MovingObjectType var1, Vec3 var2, EnumFacing var3, BlockPos var4) {
      this.a = var1;
      this.e = var4;
      this.b = var3;
      this.c = new Vec3(var2.a, var2.b, var2.c);
   }

   public MovingObjectPosition(Entity var1, Vec3 var2) {
      this.a = MovingObjectPosition.MovingObjectType.c;
      this.d = var1;
      this.c = var2;
   }

   public BlockPos a() {
      return this.e;
   }

   @Override
   public String toString() {
      return "HitResult{type=" + this.a + ", blockpos=" + this.e + ", f=" + this.b + ", pos=" + this.c + ", entity=" + this.d + '}';
   }

   public static enum MovingObjectType {
      a,
      b,
      c;
   }
}
