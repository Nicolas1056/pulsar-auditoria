package net.minecraft.entity;

import java.util.UUID;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class EntityCreature extends EntityLiving {
   public static final UUID bk = UUID.fromString("E199AD21-BA8A-4C53-8D13-6182D5C69D3A");
   public static final AttributeModifier bl = new AttributeModifier(bk, "Fleeing speed bonus", 2.0, 2).a(false);
   private BlockPos a;
   private float b;
   private EntityAIBase c;
   private boolean bm;

   public EntityCreature(World var1) {
      super(var1);
      this.a = BlockPos.a;
      this.b = -1.0F;
      this.c = new EntityAIMoveTowardsRestriction(this, 1.0);
   }

   public float a(BlockPos var1) {
      return 0.0F;
   }

   @Override
   public boolean bR() {
      return super.bR() && this.a(new BlockPos(this.s, this.aR().b, this.u)) >= 0.0F;
   }

   public boolean cf() {
      return !this.h.m();
   }

   public boolean cg() {
      return this.e(new BlockPos(this));
   }

   public boolean e(BlockPos var1) {
      return this.b == -1.0F ? true : this.a.i(var1) < this.b * this.b;
   }

   public void a(BlockPos var1, int var2) {
      this.a = var1;
      this.b = var2;
   }

   public BlockPos ch() {
      return this.a;
   }

   public float ci() {
      return this.b;
   }

   public void cj() {
      this.b = -1.0F;
   }

   public boolean ck() {
      return this.b != -1.0F;
   }

   @Override
   protected void ca() {
      super.ca();
      if (this.cc() && this.cd() != null && this.cd().o == this.o) {
         Entity var1 = this.cd();
         this.a(new BlockPos((int)var1.s, (int)var1.t, (int)var1.u), 5);
         float var2 = this.g(var1);
         if (this instanceof EntityTameable && ((EntityTameable)this).cn()) {
            if (var2 > 10.0F) {
               this.a(true, true);
            }

            return;
         }

         if (!this.bm) {
            this.i.a(2, this.c);
            if (this.s() instanceof PathNavigateGround) {
               ((PathNavigateGround)this.s()).a(false);
            }

            this.bm = true;
         }

         this.o(var2);
         if (var2 > 4.0F) {
            this.s().a(var1, 1.0);
         }

         if (var2 > 6.0F) {
            double var3 = (var1.s - this.s) / var2;
            double var5 = (var1.t - this.t) / var2;
            double var7 = (var1.u - this.u) / var2;
            this.v = this.v + var3 * Math.abs(var3) * 0.4;
            this.w = this.w + var5 * Math.abs(var5) * 0.4;
            this.x = this.x + var7 * Math.abs(var7) * 0.4;
         }

         if (var2 > 10.0F) {
            this.a(true, true);
         }
      } else if (!this.cc() && this.bm) {
         this.bm = false;
         this.i.a(this.c);
         if (this.s() instanceof PathNavigateGround) {
            ((PathNavigateGround)this.s()).a(true);
         }

         this.cj();
      }
   }

   protected void o(float var1) {
   }
}
