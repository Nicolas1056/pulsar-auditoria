package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIOcelotAttack;
import net.minecraft.entity.ai.EntityAIOcelotSit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityOcelot extends EntityTameable {
   private EntityAIAvoidEntity<EntityPlayer> bo;
   private EntityAITempt bp;

   public EntityOcelot(World var1) {
      super(var1);
      this.a(0.6F, 0.7F);
      ((PathNavigateGround)this.s()).a(true);
      this.i.a(1, new EntityAISwimming(this));
      this.i.a(2, this.bm);
      this.i.a(3, this.bp = new EntityAITempt(this, 0.6, Items.aU, true));
      this.i.a(5, new EntityAIFollowOwner(this, 1.0, 10.0F, 5.0F));
      this.i.a(6, new EntityAIOcelotSit(this, 0.8));
      this.i.a(7, new EntityAILeapAtTarget(this, 0.3F));
      this.i.a(8, new EntityAIOcelotAttack(this));
      this.i.a(9, new EntityAIMate(this, 0.8));
      this.i.a(10, new EntityAIWander(this, 0.8));
      this.i.a(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
      this.bi.a(1, new EntityAITargetNonTamed<>(this, EntityChicken.class, false, null));
   }

   @Override
   protected void h() {
      super.h();
      this.ac.a(18, Byte.valueOf((byte)0));
   }

   @Override
   public void E() {
      if (this.q().a()) {
         double var1 = this.q().b();
         if (var1 == 0.6) {
            this.c(true);
            this.d(false);
         } else if (var1 == 1.33) {
            this.c(false);
            this.d(true);
         } else {
            this.c(false);
            this.d(false);
         }
      } else {
         this.c(false);
         this.d(false);
      }
   }

   @Override
   protected boolean C() {
      return !this.cl() && this.W > 2400;
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.a).a(10.0);
      this.a(SharedMonsterAttributes.d).a(0.3F);
   }

   @Override
   public void e(float var1, float var2) {
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("CatType", this.ct());
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.r(var1.f("CatType"));
   }

   @Override
   protected String z() {
      if (this.cl()) {
         if (this.cr()) {
            return "mob.cat.purr";
         } else {
            return this.V.nextInt(4) == 0 ? "mob.cat.purreow" : "mob.cat.meow";
         }
      } else {
         return "";
      }
   }

   @Override
   protected String bo() {
      return "mob.cat.hitt";
   }

   @Override
   protected String bp() {
      return "mob.cat.hitt";
   }

   @Override
   protected float bB() {
      return 0.4F;
   }

   @Override
   protected Item A() {
      return Items.aF;
   }

   @Override
   public boolean r(Entity var1) {
      return var1.a(DamageSource.a((EntityLivingBase)this), 3.0F);
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      if (this.b(var1)) {
         return false;
      } else {
         this.bm.a(false);
         return super.a(var1, var2);
      }
   }

   @Override
   protected void b(boolean var1, int var2) {
   }

   @Override
   public boolean a(EntityPlayer var1) {
      ItemStack var2 = var1.bi.h();
      if (this.cl()) {
         if (this.e((EntityLivingBase)var1) && !this.o.D && !this.d(var2)) {
            this.bm.a(!this.cn());
         }
      } else if (this.bp.f() && var2 != null && var2.b() == Items.aU && var1.h(this) < 9.0) {
         if (!var1.bA.d) {
            var2.b--;
         }

         if (var2.b <= 0) {
            var1.bi.a(var1.bi.c, null);
         }

         if (!this.o.D) {
            if (this.V.nextInt(3) == 0) {
               this.m(true);
               this.r(1 + this.o.s.nextInt(3));
               this.b(var1.aK().toString());
               this.l(true);
               this.bm.a(true);
               this.o.a(this, (byte)7);
            } else {
               this.l(false);
               this.o.a(this, (byte)6);
            }
         }

         return true;
      }

      return super.a(var1);
   }

   public EntityOcelot b(EntityAgeable var1) {
      EntityOcelot var2 = new EntityOcelot(this.o);
      if (this.cl()) {
         var2.b(this.b());
         var2.m(true);
         var2.r(this.ct());
      }

      return var2;
   }

   @Override
   public boolean d(ItemStack var1) {
      return var1 != null && var1.b() == Items.aU;
   }

   @Override
   public boolean a(EntityAnimal var1) {
      if (var1 == this) {
         return false;
      } else if (!this.cl()) {
         return false;
      } else if (!(var1 instanceof EntityOcelot)) {
         return false;
      } else {
         EntityOcelot var2 = (EntityOcelot)var1;
         return !var2.cl() ? false : this.cr() && var2.cr();
      }
   }

   public int ct() {
      return this.ac.a(18);
   }

   public void r(int var1) {
      this.ac.b(18, (byte)var1);
   }

   @Override
   public boolean bR() {
      return this.o.s.nextInt(3) != 0;
   }

   @Override
   public boolean bS() {
      if (this.o.a(this.aR(), this) && this.o.a(this, this.aR()).isEmpty() && !this.o.d(this.aR())) {
         BlockPos var1 = new BlockPos(this.s, this.aR().b, this.u);
         if (var1.o() < this.o.F()) {
            return false;
         }

         Block var2 = this.o.p(var1.b()).c();
         if (var2 == Blocks.c || var2.t() == Material.j) {
            return true;
         }
      }

      return false;
   }

   @Override
   public String e_() {
      if (this.l_()) {
         return this.aM();
      } else {
         return this.cl() ? StatCollector.a("entity.Cat.name") : super.e_();
      }
   }

   @Override
   public void m(boolean var1) {
      super.m(var1);
   }

   @Override
   protected void cm() {
      if (this.bo == null) {
         this.bo = new EntityAIAvoidEntity<>(this, EntityPlayer.class, 16.0F, 0.8, 1.33);
      }

      this.i.a(this.bo);
      if (!this.cl()) {
         this.i.a(4, this.bo);
      }
   }

   @Override
   public IEntityLivingData a(DifficultyInstance var1, IEntityLivingData var2) {
      var2 = super.a(var1, var2);
      if (this.o.s.nextInt(7) == 0) {
         for (int var3 = 0; var3 < 2; var3++) {
            EntityOcelot var4 = new EntityOcelot(this.o);
            var4.b(this.s, this.t, this.u, this.y, 0.0F);
            var4.b(-24000);
            this.o.d(var4);
         }
      }

      return var2;
   }
}
