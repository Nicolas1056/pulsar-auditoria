package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityAnimal extends EntityAgeable implements IAnimals {
   protected Block bn = Blocks.c;
   private int bm;
   private EntityPlayer bo;

   public EntityAnimal(World var1) {
      super(var1);
   }

   @Override
   protected void E() {
      if (this.l() != 0) {
         this.bm = 0;
      }

      super.E();
   }

   @Override
   public void m() {
      super.m();
      if (this.l() != 0) {
         this.bm = 0;
      }

      if (this.bm > 0) {
         this.bm--;
         if (this.bm % 10 == 0) {
            double var1 = this.V.nextGaussian() * 0.02;
            double var3 = this.V.nextGaussian() * 0.02;
            double var5 = this.V.nextGaussian() * 0.02;
            this.o
               .a(
                  EnumParticleTypes.I,
                  this.s + this.V.nextFloat() * this.J * 2.0F - this.J,
                  this.t + 0.5 + this.V.nextFloat() * this.K,
                  this.u + this.V.nextFloat() * this.J * 2.0F - this.J,
                  var1,
                  var3,
                  var5
               );
         }
      }
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      if (this.b(var1)) {
         return false;
      } else {
         this.bm = 0;
         return super.a(var1, var2);
      }
   }

   @Override
   public float a(BlockPos var1) {
      return this.o.p(var1.b()).c() == Blocks.c ? 10.0F : this.o.o(var1) - 0.5F;
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("InLove", this.bm);
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.bm = var1.f("InLove");
   }

   @Override
   public boolean bR() {
      int var1 = MathHelper.c(this.s);
      int var2 = MathHelper.c(this.aR().b);
      int var3 = MathHelper.c(this.u);
      BlockPos var4 = new BlockPos(var1, var2, var3);
      return this.o.p(var4.b()).c() == this.bn && this.o.k(var4) > 8 && super.bR();
   }

   @Override
   public int w() {
      return 120;
   }

   @Override
   protected boolean C() {
      return false;
   }

   @Override
   protected int b(EntityPlayer var1) {
      return 1 + this.o.s.nextInt(3);
   }

   public boolean d(ItemStack var1) {
      return var1 == null ? false : var1.b() == Items.O;
   }

   @Override
   public boolean a(EntityPlayer var1) {
      ItemStack var2 = var1.bi.h();
      if (var2 != null) {
         if (this.d(var2) && this.l() == 0 && this.bm <= 0) {
            this.a(var1, var2);
            this.c(var1);
            return true;
         }

         if (this.j_() && this.d(var2)) {
            this.a(var1, var2);
            this.a((int)(-this.l() / 20 * 0.1F), true);
            return true;
         }
      }

      return super.a(var1);
   }

   protected void a(EntityPlayer var1, ItemStack var2) {
      if (!var1.bA.d) {
         var2.b--;
         if (var2.b <= 0) {
            var1.bi.a(var1.bi.c, null);
         }
      }
   }

   public void c(EntityPlayer var1) {
      this.bm = 600;
      this.bo = var1;
      this.o.a(this, (byte)18);
   }

   public EntityPlayer cq() {
      return this.bo;
   }

   public boolean cr() {
      return this.bm > 0;
   }

   public void cs() {
      this.bm = 0;
   }

   public boolean a(EntityAnimal var1) {
      if (var1 == this) {
         return false;
      } else {
         return var1.getClass() != this.getClass() ? false : this.cr() && var1.cr();
      }
   }

   @Override
   public void a(byte var1) {
      if (var1 == 18) {
         for (int var2 = 0; var2 < 7; var2++) {
            double var3 = this.V.nextGaussian() * 0.02;
            double var5 = this.V.nextGaussian() * 0.02;
            double var7 = this.V.nextGaussian() * 0.02;
            this.o
               .a(
                  EnumParticleTypes.I,
                  this.s + this.V.nextFloat() * this.J * 2.0F - this.J,
                  this.t + 0.5 + this.V.nextFloat() * this.K,
                  this.u + this.V.nextFloat() * this.J * 2.0F - this.J,
                  var3,
                  var5,
                  var7
               );
         }
      } else {
         super.a(var1);
      }
   }
}
