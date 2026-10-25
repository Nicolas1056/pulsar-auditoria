package net.minecraft.entity.passive;

import java.util.Calendar;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBat extends EntityAmbientCreature {
   private BlockPos a;

   public EntityBat(World var1) {
      super(var1);
      this.a(0.5F, 0.9F);
      this.a(true);
   }

   @Override
   protected void h() {
      super.h();
      this.ac.a(16, new Byte((byte)0));
   }

   @Override
   protected float bB() {
      return 0.1F;
   }

   @Override
   protected float bC() {
      return super.bC() * 0.95F;
   }

   @Override
   protected String z() {
      return this.n() && this.V.nextInt(4) != 0 ? null : "mob.bat.idle";
   }

   @Override
   protected String bo() {
      return "mob.bat.hurt";
   }

   @Override
   protected String bp() {
      return "mob.bat.death";
   }

   @Override
   public boolean ae() {
      return false;
   }

   @Override
   protected void s(Entity var1) {
   }

   @Override
   protected void bL() {
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.a).a(6.0);
   }

   public boolean n() {
      return (this.ac.a(16) & 1) != 0;
   }

   public void a(boolean var1) {
      byte var2 = this.ac.a(16);
      if (var1) {
         this.ac.b(16, (byte)(var2 | 1));
      } else {
         this.ac.b(16, (byte)(var2 & -2));
      }
   }

   @Override
   public void t_() {
      super.t_();
      if (this.n()) {
         this.v = this.w = this.x = 0.0;
         this.t = MathHelper.c(this.t) + 1.0 - this.K;
      } else {
         this.w *= 0.6F;
      }
   }

   @Override
   protected void E() {
      super.E();
      BlockPos var1 = new BlockPos(this);
      BlockPos var2 = var1.a();
      if (this.n()) {
         if (!this.o.p(var2).c().v()) {
            this.a(false);
            this.o.a(null, 1015, var1, 0);
         } else {
            if (this.V.nextInt(200) == 0) {
               this.aK = this.V.nextInt(360);
            }

            if (this.o.a(this, 4.0) != null) {
               this.a(false);
               this.o.a(null, 1015, var1, 0);
            }
         }
      } else {
         if (this.a != null && (!this.o.d(this.a) || this.a.o() < 1)) {
            this.a = null;
         }

         if (this.a == null || this.V.nextInt(30) == 0 || this.a.c((int)this.s, (int)this.t, (int)this.u) < 4.0) {
            this.a = new BlockPos(
               (int)this.s + this.V.nextInt(7) - this.V.nextInt(7), (int)this.t + this.V.nextInt(6) - 2, (int)this.u + this.V.nextInt(7) - this.V.nextInt(7)
            );
         }

         double var3 = this.a.n() + 0.5 - this.s;
         double var5 = this.a.o() + 0.1 - this.t;
         double var7 = this.a.p() + 0.5 - this.u;
         this.v = this.v + (Math.signum(var3) * 0.5 - this.v) * 0.1F;
         this.w = this.w + (Math.signum(var5) * 0.7F - this.w) * 0.1F;
         this.x = this.x + (Math.signum(var7) * 0.5 - this.x) * 0.1F;
         float var9 = (float)(MathHelper.b(this.x, this.v) * 180.0 / (float) Math.PI) - 90.0F;
         float var10 = MathHelper.g(var9 - this.y);
         this.ba = 0.5F;
         this.y += var10;
         if (this.V.nextInt(100) == 0 && this.o.p(var2).c().v()) {
            this.a(true);
         }
      }
   }

   @Override
   protected boolean s_() {
      return false;
   }

   @Override
   public void e(float var1, float var2) {
   }

   @Override
   protected void a(double var1, boolean var3, Block var4, BlockPos var5) {
   }

   @Override
   public boolean aI() {
      return true;
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      if (this.b(var1)) {
         return false;
      } else {
         if (!this.o.D && this.n()) {
            this.a(false);
         }

         return super.a(var1, var2);
      }
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.ac.b(16, var1.d("BatFlags"));
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("BatFlags", this.ac.a(16));
   }

   @Override
   public boolean bR() {
      BlockPos var1 = new BlockPos(this.s, this.aR().b, this.u);
      if (var1.o() >= this.o.F()) {
         return false;
      } else {
         int var2 = this.o.l(var1);
         byte var3 = 4;
         if (this.a(this.o.Y())) {
            var3 = 7;
         } else if (this.V.nextBoolean()) {
            return false;
         }

         return var2 > this.V.nextInt(var3) ? false : super.bR();
      }
   }

   private boolean a(Calendar var1) {
      return var1.get(2) + 1 == 10 && var1.get(5) >= 20 || var1.get(2) + 1 == 11 && var1.get(5) <= 3;
   }

   @Override
   public float aS() {
      return this.K / 2.0F;
   }
}
