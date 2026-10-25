package net.minecraft.entity.ai;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;

public class EntityAIMoveThroughVillage extends EntityAIBase {
   private EntityCreature a;
   private double b;
   private PathEntity c;
   private VillageDoorInfo d;
   private boolean e;
   private List<VillageDoorInfo> f = Lists.newArrayList();

   public EntityAIMoveThroughVillage(EntityCreature var1, double var2, boolean var4) {
      this.a = var1;
      this.b = var2;
      this.e = var4;
      this.a(1);
      if (!(var1.s() instanceof PathNavigateGround)) {
         throw new IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal");
      }
   }

   @Override
   public boolean a() {
      this.f();
      if (this.e && this.a.o.w()) {
         return false;
      } else {
         Village var1 = this.a.o.ae().a(new BlockPos(this.a), 0);
         if (var1 == null) {
            return false;
         } else {
            this.d = this.a(var1);
            if (this.d == null) {
               return false;
            } else {
               PathNavigateGround var2 = (PathNavigateGround)this.a.s();
               boolean var3 = var2.g();
               var2.b(false);
               this.c = var2.a(this.d.d());
               var2.b(var3);
               if (this.c != null) {
                  return true;
               } else {
                  Vec3 var4 = RandomPositionGenerator.a(this.a, 10, 7, new Vec3(this.d.d().n(), this.d.d().o(), this.d.d().p()));
                  if (var4 == null) {
                     return false;
                  } else {
                     var2.b(false);
                     this.c = this.a.s().a(var4.a, var4.b, var4.c);
                     var2.b(var3);
                     return this.c != null;
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean b() {
      if (this.a.s().m()) {
         return false;
      } else {
         float var1 = this.a.J + 4.0F;
         return this.a.b(this.d.d()) > var1 * var1;
      }
   }

   @Override
   public void c() {
      this.a.s().a(this.c, this.b);
   }

   @Override
   public void d() {
      if (this.a.s().m() || this.a.b(this.d.d()) < 16.0) {
         this.f.add(this.d);
      }
   }

   private VillageDoorInfo a(Village var1) {
      VillageDoorInfo var2 = null;
      int var3 = Integer.MAX_VALUE;

      for (VillageDoorInfo var6 : var1.f()) {
         int var7 = var6.b(MathHelper.c(this.a.s), MathHelper.c(this.a.t), MathHelper.c(this.a.u));
         if (var7 < var3 && !this.a(var6)) {
            var2 = var6;
            var3 = var7;
         }
      }

      return var2;
   }

   private boolean a(VillageDoorInfo var1) {
      for (VillageDoorInfo var3 : this.f) {
         if (var1.d().equals(var3.d())) {
            return true;
         }
      }

      return false;
   }

   private void f() {
      if (this.f.size() > 15) {
         this.f.remove(0);
      }
   }
}
