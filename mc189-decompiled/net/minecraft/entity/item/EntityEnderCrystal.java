package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;

public class EntityEnderCrystal extends Entity {
   public int a;
   public int b;

   public EntityEnderCrystal(World var1) {
      super(var1);
      this.k = true;
      this.a(2.0F, 2.0F);
      this.b = 5;
      this.a = this.V.nextInt(100000);
   }

   public EntityEnderCrystal(World var1, double var2, double var4, double var6) {
      this(var1);
      this.b(var2, var4, var6);
   }

   @Override
   protected boolean s_() {
      return false;
   }

   @Override
   protected void h() {
      this.ac.a(8, Integer.valueOf(this.b));
   }

   @Override
   public void t_() {
      this.p = this.s;
      this.q = this.t;
      this.r = this.u;
      this.a++;
      this.ac.b(8, this.b);
      int var1 = MathHelper.c(this.s);
      int var2 = MathHelper.c(this.t);
      int var3 = MathHelper.c(this.u);
      if (this.o.t instanceof WorldProviderEnd && this.o.p(new BlockPos(var1, var2, var3)).c() != Blocks.ab) {
         this.o.a(new BlockPos(var1, var2, var3), Blocks.ab.Q());
      }
   }

   @Override
   protected void b(NBTTagCompound var1) {
   }

   @Override
   protected void a(NBTTagCompound var1) {
   }

   @Override
   public boolean ad() {
      return true;
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      if (this.b(var1)) {
         return false;
      } else {
         if (!this.I && !this.o.D) {
            this.b = 0;
            if (this.b <= 0) {
               this.J();
               if (!this.o.D) {
                  this.o.a(null, this.s, this.t, this.u, 6.0F, true);
               }
            }
         }

         return true;
      }
   }
}
