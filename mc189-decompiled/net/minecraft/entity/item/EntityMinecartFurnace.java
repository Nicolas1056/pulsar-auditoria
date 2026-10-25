package net.minecraft.entity.item;

import net.minecraft.block.BlockFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityMinecartFurnace extends EntityMinecart {
   private int c;
   public double a;
   public double b;

   public EntityMinecartFurnace(World var1) {
      super(var1);
   }

   public EntityMinecartFurnace(World var1, double var2, double var4, double var6) {
      super(var1, var2, var4, var6);
   }

   @Override
   public EntityMinecart.EnumMinecartType s() {
      return EntityMinecart.EnumMinecartType.c;
   }

   @Override
   protected void h() {
      super.h();
      this.ac.a(16, new Byte((byte)0));
   }

   @Override
   public void t_() {
      super.t_();
      if (this.c > 0) {
         this.c--;
      }

      if (this.c <= 0) {
         this.a = this.b = 0.0;
      }

      this.i(this.c > 0);
      if (this.j() && this.V.nextInt(4) == 0) {
         this.o.a(EnumParticleTypes.m, this.s, this.t + 0.8, this.u, 0.0, 0.0, 0.0);
      }
   }

   @Override
   protected double m() {
      return 0.2;
   }

   @Override
   public void a(DamageSource var1) {
      super.a(var1);
      if (!var1.c() && this.o.Q().b("doEntityDrops")) {
         this.a(new ItemStack(Blocks.al, 1), 0.0F);
      }
   }

   @Override
   protected void a(BlockPos var1, IBlockState var2) {
      super.a(var1, var2);
      double var3 = this.a * this.a + this.b * this.b;
      if (var3 > 1.0E-4 && this.v * this.v + this.x * this.x > 0.001) {
         var3 = MathHelper.a(var3);
         this.a /= var3;
         this.b /= var3;
         if (this.a * this.v + this.b * this.x < 0.0) {
            this.a = 0.0;
            this.b = 0.0;
         } else {
            double var5 = var3 / this.m();
            this.a *= var5;
            this.b *= var5;
         }
      }
   }

   @Override
   protected void o() {
      double var1 = this.a * this.a + this.b * this.b;
      if (var1 > 1.0E-4) {
         var1 = MathHelper.a(var1);
         this.a /= var1;
         this.b /= var1;
         double var3 = 1.0;
         this.v *= 0.8F;
         this.w *= 0.0;
         this.x *= 0.8F;
         this.v = this.v + this.a * var3;
         this.x = this.x + this.b * var3;
      } else {
         this.v *= 0.98F;
         this.w *= 0.0;
         this.x *= 0.98F;
      }

      super.o();
   }

   @Override
   public boolean e(EntityPlayer var1) {
      ItemStack var2 = var1.bi.h();
      if (var2 != null && var2.b() == Items.h) {
         if (!var1.bA.d && --var2.b == 0) {
            var1.bi.a(var1.bi.c, null);
         }

         this.c += 3600;
      }

      this.a = this.s - var1.s;
      this.b = this.u - var1.u;
      return true;
   }

   @Override
   protected void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("PushX", this.a);
      var1.a("PushZ", this.b);
      var1.a("Fuel", (short)this.c);
   }

   @Override
   protected void a(NBTTagCompound var1) {
      super.a(var1);
      this.a = var1.i("PushX");
      this.b = var1.i("PushZ");
      this.c = var1.e("Fuel");
   }

   protected boolean j() {
      return (this.ac.a(16) & 1) != 0;
   }

   protected void i(boolean var1) {
      if (var1) {
         this.ac.b(16, (byte)(this.ac.a(16) | 1));
      } else {
         this.ac.b(16, (byte)(this.ac.a(16) & -2));
      }
   }

   @Override
   public IBlockState u() {
      return (this.j() ? Blocks.am : Blocks.al).Q().a(BlockFurnace.a, EnumFacing.c);
   }
}
