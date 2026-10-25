package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.BlockPos;

public abstract class EntityAIDoorInteract extends EntityAIBase {
   protected EntityLiving a;
   protected BlockPos b = BlockPos.a;
   protected BlockDoor c;
   boolean d;
   float e;
   float f;

   public EntityAIDoorInteract(EntityLiving var1) {
      this.a = var1;
      if (!(var1.s() instanceof PathNavigateGround)) {
         throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
      }
   }

   @Override
   public boolean a() {
      if (!this.a.D) {
         return false;
      } else {
         PathNavigateGround var1 = (PathNavigateGround)this.a.s();
         PathEntity var2 = var1.j();
         if (var2 != null && !var2.b() && var1.g()) {
            for (int var3 = 0; var3 < Math.min(var2.e() + 2, var2.d()); var3++) {
               PathPoint var4 = var2.a(var3);
               this.b = new BlockPos(var4.a, var4.b + 1, var4.c);
               if (!(this.a.e(this.b.n(), this.a.t, this.b.p()) > 2.25)) {
                  this.c = this.a(this.b);
                  if (this.c != null) {
                     return true;
                  }
               }
            }

            this.b = new BlockPos(this.a).a();
            this.c = this.a(this.b);
            return this.c != null;
         } else {
            return false;
         }
      }
   }

   @Override
   public boolean b() {
      return !this.d;
   }

   @Override
   public void c() {
      this.d = false;
      this.e = (float)(this.b.n() + 0.5F - this.a.s);
      this.f = (float)(this.b.p() + 0.5F - this.a.u);
   }

   @Override
   public void e() {
      float var1 = (float)(this.b.n() + 0.5F - this.a.s);
      float var2 = (float)(this.b.p() + 0.5F - this.a.u);
      float var3 = this.e * var1 + this.f * var2;
      if (var3 < 0.0F) {
         this.d = true;
      }
   }

   private BlockDoor a(BlockPos var1) {
      Block var2 = this.a.o.p(var1).c();
      return var2 instanceof BlockDoor && var2.t() == Material.d ? (BlockDoor)var2 : null;
   }
}
