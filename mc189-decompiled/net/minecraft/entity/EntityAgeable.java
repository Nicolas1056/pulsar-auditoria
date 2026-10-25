package net.minecraft.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityAgeable extends EntityCreature {
   protected int a;
   protected int b;
   protected int c;
   private float bm = -1.0F;
   private float bn;

   public EntityAgeable(World var1) {
      super(var1);
   }

   public abstract EntityAgeable a(EntityAgeable var1);

   @Override
   public boolean a(EntityPlayer var1) {
      ItemStack var2 = var1.bi.h();
      if (var2 != null && var2.b() == Items.bJ) {
         if (!this.o.D) {
            Class var3 = EntityList.a(var2.i());
            if (var3 != null && this.getClass() == var3) {
               EntityAgeable var4 = this.a(this);
               if (var4 != null) {
                  var4.b(-24000);
                  var4.b(this.s, this.t, this.u, 0.0F, 0.0F);
                  this.o.d(var4);
                  if (var2.s()) {
                     var4.a(var2.q());
                  }

                  if (!var1.bA.d) {
                     var2.b--;
                     if (var2.b <= 0) {
                        var1.bi.a(var1.bi.c, null);
                     }
                  }
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   protected void h() {
      super.h();
      this.ac.a(12, Byte.valueOf((byte)0));
   }

   public int l() {
      return this.o.D ? this.ac.a(12) : this.a;
   }

   public void a(int var1, boolean var2) {
      int var3 = this.l();
      var3 += var1 * 20;
      if (var3 > 0) {
         var3 = 0;
         if (var3 < 0) {
            this.n();
         }
      }

      int var5 = var3 - var3;
      this.b(var3);
      if (var2) {
         this.b += var5;
         if (this.c == 0) {
            this.c = 40;
         }
      }

      if (this.l() == 0) {
         this.b(this.b);
      }
   }

   public void a(int var1) {
      this.a(var1, false);
   }

   public void b(int var1) {
      this.ac.b(12, (byte)MathHelper.a(var1, -1, 1));
      this.a = var1;
      this.a(this.j_());
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("Age", this.l());
      var1.a("ForcedAge", this.b);
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.b(var1.f("Age"));
      this.b = var1.f("ForcedAge");
   }

   @Override
   public void m() {
      super.m();
      if (this.o.D) {
         if (this.c > 0) {
            if (this.c % 4 == 0) {
               this.o
                  .a(
                     EnumParticleTypes.v,
                     this.s + this.V.nextFloat() * this.J * 2.0F - this.J,
                     this.t + 0.5 + this.V.nextFloat() * this.K,
                     this.u + this.V.nextFloat() * this.J * 2.0F - this.J,
                     0.0,
                     0.0,
                     0.0
                  );
            }

            this.c--;
         }

         this.a(this.j_());
      } else {
         int var1 = this.l();
         if (var1 < 0) {
            this.b(++var1);
            if (var1 == 0) {
               this.n();
            }
         } else if (var1 > 0) {
            this.b(--var1);
         }
      }
   }

   @Override
   protected void n() {
   }

   @Override
   public boolean j_() {
      return this.l() < 0;
   }

   public void a(boolean var1) {
      this.a(var1 ? 0.5F : 1.0F);
   }

   @Override
   protected final void a(float var1, float var2) {
      boolean var3 = this.bm > 0.0F;
      this.bm = var1;
      this.bn = var2;
      if (!var3) {
         this.a(1.0F);
      }
   }

   protected final void a(float var1) {
      super.a(this.bm * var1, this.bn * var1);
   }
}
