package net.minecraft.entity.passive;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityMooshroom extends EntityCow {
   public EntityMooshroom(World var1) {
      super(var1);
      this.a(0.9F, 1.3F);
      this.bn = Blocks.bw;
   }

   @Override
   public boolean a(EntityPlayer var1) {
      ItemStack var2 = var1.bi.h();
      if (var2 != null && var2.b() == Items.z && this.l() >= 0) {
         if (var2.b == 1) {
            var1.bi.a(var1.bi.c, new ItemStack(Items.A));
            return true;
         }

         if (var1.bi.a(new ItemStack(Items.A)) && !var1.bA.d) {
            var1.bi.a(var1.bi.c, 1);
            return true;
         }
      }

      if (var2 != null && var2.b() == Items.be && this.l() >= 0) {
         this.J();
         this.o.a(EnumParticleTypes.b, this.s, this.t + this.K / 2.0F, this.u, 0.0, 0.0, 0.0);
         if (!this.o.D) {
            EntityCow var3 = new EntityCow(this.o);
            var3.b(this.s, this.t, this.u, this.y, this.z);
            var3.i(this.bn());
            var3.aI = this.aI;
            if (this.l_()) {
               var3.a(this.aM());
            }

            this.o.d(var3);

            for (int var4 = 0; var4 < 5; var4++) {
               this.o.d(new EntityItem(this.o, this.s, this.t + this.K, this.u, new ItemStack(Blocks.Q)));
            }

            var2.a(1, var1);
            this.a("mob.sheep.shear", 1.0F, 1.0F);
         }

         return true;
      } else {
         return super.a(var1);
      }
   }

   public EntityMooshroom c(EntityAgeable var1) {
      return new EntityMooshroom(this.o);
   }
}
