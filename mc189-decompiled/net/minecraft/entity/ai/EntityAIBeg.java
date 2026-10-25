package net.minecraft.entity.ai;

import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityAIBeg extends EntityAIBase {
   private EntityWolf a;
   private EntityPlayer b;
   private World c;
   private float d;
   private int e;

   public EntityAIBeg(EntityWolf var1, float var2) {
      this.a = var1;
      this.c = var1.o;
      this.d = var2;
      this.a(2);
   }

   @Override
   public boolean a() {
      this.b = this.c.a(this.a, (double)this.d);
      return this.b == null ? false : this.a(this.b);
   }

   @Override
   public boolean b() {
      if (!this.b.ai()) {
         return false;
      } else {
         return this.a.h(this.b) > this.d * this.d ? false : this.e > 0 && this.a(this.b);
      }
   }

   @Override
   public void c() {
      this.a.p(true);
      this.e = 40 + this.a.bc().nextInt(40);
   }

   @Override
   public void d() {
      this.a.p(false);
      this.b = null;
   }

   @Override
   public void e() {
      this.a.p().a(this.b.s, this.b.t + this.b.aS(), this.b.u, 10.0F, this.a.bQ());
      this.e--;
   }

   private boolean a(EntityPlayer var1) {
      ItemStack var2 = var1.bi.h();
      if (var2 == null) {
         return false;
      } else {
         return !this.a.cl() && var2.b() == Items.aX ? true : this.a.d(var2);
      }
   }
}
