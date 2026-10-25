package net.minecraft.entity.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityMagmaCube extends EntitySlime {
   public EntityMagmaCube(World var1) {
      super(var1);
      this.ab = true;
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.d).a(0.2F);
   }

   @Override
   public boolean bR() {
      return this.o.aa() != EnumDifficulty.a;
   }

   @Override
   public boolean bS() {
      return this.o.a(this.aR(), this) && this.o.a(this, this.aR()).isEmpty() && !this.o.d(this.aR());
   }

   @Override
   public int br() {
      return this.cm() * 3;
   }

   @Override
   public int b(float var1) {
      return 15728880;
   }

   @Override
   public float c(float var1) {
      return 1.0F;
   }

   @Override
   protected EnumParticleTypes n() {
      return EnumParticleTypes.A;
   }

   @Override
   protected EntitySlime cf() {
      return new EntityMagmaCube(this.o);
   }

   @Override
   protected Item A() {
      return Items.bE;
   }

   @Override
   protected void b(boolean var1, int var2) {
      Item var3 = this.A();
      if (var3 != null && this.cm() > 1) {
         int var4 = this.V.nextInt(4) - 2;
         if (var2 > 0) {
            var4 += this.V.nextInt(var2 + 1);
         }

         for (int var5 = 0; var5 < var4; var5++) {
            this.a(var3, 1);
         }
      }
   }

   @Override
   public boolean at() {
      return false;
   }

   @Override
   protected int cg() {
      return super.cg() * 4;
   }

   @Override
   protected void ch() {
      this.a *= 0.9F;
   }

   @Override
   protected void bF() {
      this.w = 0.42F + this.cm() * 0.1F;
      this.ai = true;
   }

   @Override
   protected void bH() {
      this.w = 0.22F + this.cm() * 0.05F;
      this.ai = true;
   }

   @Override
   public void e(float var1, float var2) {
   }

   @Override
   protected boolean ci() {
      return true;
   }

   @Override
   protected int cj() {
      return super.cj() + 2;
   }

   @Override
   protected String ck() {
      return this.cm() > 1 ? "mob.magmacube.big" : "mob.magmacube.small";
   }

   @Override
   protected boolean cl() {
      return true;
   }
}
