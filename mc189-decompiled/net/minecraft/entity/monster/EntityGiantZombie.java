package net.minecraft.entity.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityGiantZombie extends EntityMob {
   public EntityGiantZombie(World var1) {
      super(var1);
      this.a(this.J * 6.0F, this.K * 6.0F);
   }

   @Override
   public float aS() {
      return 10.440001F;
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.a).a(100.0);
      this.a(SharedMonsterAttributes.d).a(0.5);
      this.a(SharedMonsterAttributes.e).a(50.0);
   }

   @Override
   public float a(BlockPos var1) {
      return this.o.o(var1) - 0.5F;
   }
}
