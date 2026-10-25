package net.minecraft.entity.item;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityMinecartTNT extends EntityMinecart {
   private int a = -1;

   public EntityMinecartTNT(World var1) {
      super(var1);
   }

   public EntityMinecartTNT(World var1, double var2, double var4, double var6) {
      super(var1, var2, var4, var6);
   }

   @Override
   public EntityMinecart.EnumMinecartType s() {
      return EntityMinecart.EnumMinecartType.d;
   }

   @Override
   public IBlockState u() {
      return Blocks.W.Q();
   }

   @Override
   public void t_() {
      super.t_();
      if (this.a > 0) {
         this.a--;
         this.o.a(EnumParticleTypes.l, this.s, this.t + 0.5, this.u, 0.0, 0.0, 0.0);
      } else if (this.a == 0) {
         this.b(this.v * this.v + this.x * this.x);
      }

      if (this.D) {
         double var1 = this.v * this.v + this.x * this.x;
         if (var1 >= 0.01F) {
            this.b(var1);
         }
      }
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      Entity var3 = var1.i();
      if (var3 instanceof EntityArrow) {
         EntityArrow var4 = (EntityArrow)var3;
         if (var4.at()) {
            this.b(var4.v * var4.v + var4.w * var4.w + var4.x * var4.x);
         }
      }

      return super.a(var1, var2);
   }

   @Override
   public void a(DamageSource var1) {
      super.a(var1);
      double var2 = this.v * this.v + this.x * this.x;
      if (!var1.c() && this.o.Q().b("doEntityDrops")) {
         this.a(new ItemStack(Blocks.W, 1), 0.0F);
      }

      if (var1.o() || var1.c() || var2 >= 0.01F) {
         this.b(var2);
      }
   }

   protected void b(double var1) {
      if (!this.o.D) {
         double var3 = Math.sqrt(var1);
         if (var3 > 5.0) {
            var3 = 5.0;
         }

         this.o.a(this, this.s, this.t, this.u, (float)(4.0 + this.V.nextDouble() * 1.5 * var3), true);
         this.J();
      }
   }

   @Override
   public void e(float var1, float var2) {
      if (var1 >= 3.0F) {
         float var3 = var1 / 10.0F;
         this.b(var3 * var3);
      }

      super.e(var1, var2);
   }

   @Override
   public void a(int var1, int var2, int var3, boolean var4) {
      if (var4 && this.a < 0) {
         this.j();
      }
   }

   @Override
   public void a(byte var1) {
      if (var1 == 10) {
         this.j();
      } else {
         super.a(var1);
      }
   }

   public void j() {
      this.a = 80;
      if (!this.o.D) {
         this.o.a(this, (byte)10);
         if (!this.R()) {
            this.o.a(this, "game.tnt.primed", 1.0F, 1.0F);
         }
      }
   }

   public int l() {
      return this.a;
   }

   public boolean y() {
      return this.a > -1;
   }

   @Override
   public float a(Explosion var1, World var2, BlockPos var3, IBlockState var4) {
      return !this.y() || !BlockRailBase.d(var4) && !BlockRailBase.e(var2, var3.a()) ? super.a(var1, var2, var3, var4) : 0.0F;
   }

   @Override
   public boolean a(Explosion var1, World var2, BlockPos var3, IBlockState var4, float var5) {
      return !this.y() || !BlockRailBase.d(var4) && !BlockRailBase.e(var2, var3.a()) ? super.a(var1, var2, var3, var4, var5) : false;
   }

   @Override
   protected void a(NBTTagCompound var1) {
      super.a(var1);
      if (var1.b("TNTFuse", 99)) {
         this.a = var1.f("TNTFuse");
      }
   }

   @Override
   protected void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("TNTFuse", this.a);
   }
}
