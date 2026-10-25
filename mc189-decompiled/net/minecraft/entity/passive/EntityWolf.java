package net.minecraft.entity.passive;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBeg;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityWolf extends EntityTameable {
   private float bo;
   private float bp;
   private boolean bq;
   private boolean br;
   private float bs;
   private float bt;

   public EntityWolf(World var1) {
      super(var1);
      this.a(0.6F, 0.8F);
      ((PathNavigateGround)this.s()).a(true);
      this.i.a(1, new EntityAISwimming(this));
      this.i.a(2, this.bm);
      this.i.a(3, new EntityAILeapAtTarget(this, 0.4F));
      this.i.a(4, new EntityAIAttackOnCollide(this, 1.0, true));
      this.i.a(5, new EntityAIFollowOwner(this, 1.0, 10.0F, 2.0F));
      this.i.a(6, new EntityAIMate(this, 1.0));
      this.i.a(7, new EntityAIWander(this, 1.0));
      this.i.a(8, new EntityAIBeg(this, 8.0F));
      this.i.a(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.i.a(9, new EntityAILookIdle(this));
      this.bi.a(1, new EntityAIOwnerHurtByTarget(this));
      this.bi.a(2, new EntityAIOwnerHurtTarget(this));
      this.bi.a(3, new EntityAIHurtByTarget(this, true));
      this.bi.a(4, new EntityAITargetNonTamed<>(this, EntityAnimal.class, false, new Predicate<Entity>() {
         public boolean a(Entity var1) {
            return var1 instanceof EntitySheep || var1 instanceof EntityRabbit;
         }
      }));
      this.bi.a(5, new EntityAINearestAttackableTarget<>(this, EntitySkeleton.class, false));
      this.m(false);
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.d).a(0.3F);
      if (this.cl()) {
         this.a(SharedMonsterAttributes.a).a(20.0);
      } else {
         this.a(SharedMonsterAttributes.a).a(8.0);
      }

      this.by().b(SharedMonsterAttributes.e);
      this.a(SharedMonsterAttributes.e).a(2.0);
   }

   @Override
   public void d(EntityLivingBase var1) {
      super.d(var1);
      if (var1 == null) {
         this.o(false);
      } else if (!this.cl()) {
         this.o(true);
      }
   }

   @Override
   protected void E() {
      this.ac.b(18, this.bn());
   }

   @Override
   protected void h() {
      super.h();
      this.ac.a(18, new Float(this.bn()));
      this.ac.a(19, new Byte((byte)0));
      this.ac.a(20, new Byte((byte)EnumDyeColor.o.a()));
   }

   @Override
   protected void a(BlockPos var1, Block var2) {
      this.a("mob.wolf.step", 0.15F, 1.0F);
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("Angry", this.cv());
      var1.a("CollarColor", (byte)this.cw().b());
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.o(var1.n("Angry"));
      if (var1.b("CollarColor", 99)) {
         this.a(EnumDyeColor.a(var1.d("CollarColor")));
      }
   }

   @Override
   protected String z() {
      if (this.cv()) {
         return "mob.wolf.growl";
      } else if (this.V.nextInt(3) == 0) {
         return this.cl() && this.ac.d(18) < 10.0F ? "mob.wolf.whine" : "mob.wolf.panting";
      } else {
         return "mob.wolf.bark";
      }
   }

   @Override
   protected String bo() {
      return "mob.wolf.hurt";
   }

   @Override
   protected String bp() {
      return "mob.wolf.death";
   }

   @Override
   protected float bB() {
      return 0.4F;
   }

   @Override
   protected Item A() {
      return Item.b(-1);
   }

   @Override
   public void m() {
      super.m();
      if (!this.o.D && this.bq && !this.br && !this.cf() && this.C) {
         this.br = true;
         this.bs = 0.0F;
         this.bt = 0.0F;
         this.o.a(this, (byte)8);
      }

      if (!this.o.D && this.u() == null && this.cv()) {
         this.o(false);
      }
   }

   @Override
   public void t_() {
      super.t_();
      this.bp = this.bo;
      if (this.cx()) {
         this.bo = this.bo + (1.0F - this.bo) * 0.4F;
      } else {
         this.bo = this.bo + (0.0F - this.bo) * 0.4F;
      }

      if (this.U()) {
         this.bq = true;
         this.br = false;
         this.bs = 0.0F;
         this.bt = 0.0F;
      } else if ((this.bq || this.br) && this.br) {
         if (this.bs == 0.0F) {
            this.a("mob.wolf.shake", this.bB(), (this.V.nextFloat() - this.V.nextFloat()) * 0.2F + 1.0F);
         }

         this.bt = this.bs;
         this.bs += 0.05F;
         if (this.bt >= 2.0F) {
            this.bq = false;
            this.br = false;
            this.bt = 0.0F;
            this.bs = 0.0F;
         }

         if (this.bs > 0.4F) {
            float var1 = (float)this.aR().b;
            int var2 = (int)(MathHelper.a((this.bs - 0.4F) * (float) Math.PI) * 7.0F);

            for (int var3 = 0; var3 < var2; var3++) {
               float var4 = (this.V.nextFloat() * 2.0F - 1.0F) * this.J * 0.5F;
               float var5 = (this.V.nextFloat() * 2.0F - 1.0F) * this.J * 0.5F;
               this.o.a(EnumParticleTypes.f, this.s + var4, var1 + 0.8F, this.u + var5, this.v, this.w, this.x);
            }
         }
      }
   }

   public boolean ct() {
      return this.bq;
   }

   public float p(float var1) {
      return 0.75F + (this.bt + (this.bs - this.bt) * var1) / 2.0F * 0.25F;
   }

   public float i(float var1, float var2) {
      float var3 = (this.bt + (this.bs - this.bt) * var1 + var2) / 1.8F;
      if (var3 < 0.0F) {
         var3 = 0.0F;
      } else if (var3 > 1.0F) {
         var3 = 1.0F;
      }

      return MathHelper.a(var3 * (float) Math.PI) * MathHelper.a(var3 * (float) Math.PI * 11.0F) * 0.15F * (float) Math.PI;
   }

   public float q(float var1) {
      return (this.bp + (this.bo - this.bp) * var1) * 0.15F * (float) Math.PI;
   }

   @Override
   public float aS() {
      return this.K * 0.8F;
   }

   @Override
   public int bQ() {
      return this.cn() ? 20 : super.bQ();
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      if (this.b(var1)) {
         return false;
      } else {
         Entity var3 = var1.j();
         this.bm.a(false);
         if (var3 != null && !(var3 instanceof EntityPlayer) && !(var3 instanceof EntityArrow)) {
            var2 = (var2 + 1.0F) / 2.0F;
         }

         return super.a(var1, var2);
      }
   }

   @Override
   public boolean r(Entity var1) {
      boolean var2 = var1.a(DamageSource.a((EntityLivingBase)this), (float)((int)this.a(SharedMonsterAttributes.e).e()));
      if (var2) {
         this.a(this, var1);
      }

      return var2;
   }

   @Override
   public void m(boolean var1) {
      super.m(var1);
      if (var1) {
         this.a(SharedMonsterAttributes.a).a(20.0);
      } else {
         this.a(SharedMonsterAttributes.a).a(8.0);
      }

      this.a(SharedMonsterAttributes.e).a(4.0);
   }

   @Override
   public boolean a(EntityPlayer var1) {
      ItemStack var2 = var1.bi.h();
      if (this.cl()) {
         if (var2 != null) {
            if (var2.b() instanceof ItemFood) {
               ItemFood var3 = (ItemFood)var2.b();
               if (var3.g() && this.ac.d(18) < 20.0F) {
                  if (!var1.bA.d) {
                     var2.b--;
                  }

                  this.h(var3.h(var2));
                  if (var2.b <= 0) {
                     var1.bi.a(var1.bi.c, null);
                  }

                  return true;
               }
            } else if (var2.b() == Items.aW) {
               EnumDyeColor var4 = EnumDyeColor.a(var2.i());
               if (var4 != this.cw()) {
                  this.a(var4);
                  if (!var1.bA.d && --var2.b <= 0) {
                     var1.bi.a(var1.bi.c, null);
                  }

                  return true;
               }
            }
         }

         if (this.e((EntityLivingBase)var1) && !this.o.D && !this.d(var2)) {
            this.bm.a(!this.cn());
            this.aY = false;
            this.h.n();
            this.d(null);
         }
      } else if (var2 != null && var2.b() == Items.aX && !this.cv()) {
         if (!var1.bA.d) {
            var2.b--;
         }

         if (var2.b <= 0) {
            var1.bi.a(var1.bi.c, null);
         }

         if (!this.o.D) {
            if (this.V.nextInt(3) == 0) {
               this.m(true);
               this.h.n();
               this.d(null);
               this.bm.a(true);
               this.i(20.0F);
               this.b(var1.aK().toString());
               this.l(true);
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

   @Override
   public void a(byte var1) {
      if (var1 == 8) {
         this.br = true;
         this.bs = 0.0F;
         this.bt = 0.0F;
      } else {
         super.a(var1);
      }
   }

   public float cu() {
      if (this.cv()) {
         return 1.5393804F;
      } else {
         return this.cl() ? (0.55F - (20.0F - this.ac.d(18)) * 0.02F) * (float) Math.PI : (float) (Math.PI / 5);
      }
   }

   @Override
   public boolean d(ItemStack var1) {
      if (var1 == null) {
         return false;
      } else {
         return !(var1.b() instanceof ItemFood) ? false : ((ItemFood)var1.b()).g();
      }
   }

   @Override
   public int bV() {
      return 8;
   }

   public boolean cv() {
      return (this.ac.a(16) & 2) != 0;
   }

   public void o(boolean var1) {
      byte var2 = this.ac.a(16);
      if (var1) {
         this.ac.b(16, (byte)(var2 | 2));
      } else {
         this.ac.b(16, (byte)(var2 & -3));
      }
   }

   public EnumDyeColor cw() {
      return EnumDyeColor.a(this.ac.a(20) & 15);
   }

   public void a(EnumDyeColor var1) {
      this.ac.b(20, (byte)(var1.b() & 15));
   }

   public EntityWolf b(EntityAgeable var1) {
      EntityWolf var2 = new EntityWolf(this.o);
      String var3 = this.b();
      if (var3 != null && var3.trim().length() > 0) {
         var2.b(var3);
         var2.m(true);
      }

      return var2;
   }

   public void p(boolean var1) {
      if (var1) {
         this.ac.b(19, (byte)1);
      } else {
         this.ac.b(19, (byte)0);
      }
   }

   @Override
   public boolean a(EntityAnimal var1) {
      if (var1 == this) {
         return false;
      } else if (!this.cl()) {
         return false;
      } else if (!(var1 instanceof EntityWolf)) {
         return false;
      } else {
         EntityWolf var2 = (EntityWolf)var1;
         if (!var2.cl()) {
            return false;
         } else {
            return var2.cn() ? false : this.cr() && var2.cr();
         }
      }
   }

   public boolean cx() {
      return this.ac.a(19) == 1;
   }

   @Override
   protected boolean C() {
      return !this.cl() && this.W > 2400;
   }

   @Override
   public boolean a(EntityLivingBase var1, EntityLivingBase var2) {
      if (!(var1 instanceof EntityCreeper) && !(var1 instanceof EntityGhast)) {
         if (var1 instanceof EntityWolf) {
            EntityWolf var3 = (EntityWolf)var1;
            if (var3.cl() && var3.co() == var2) {
               return false;
            }
         }

         return var1 instanceof EntityPlayer && var2 instanceof EntityPlayer && !((EntityPlayer)var2).a((EntityPlayer)var1)
            ? false
            : !(var1 instanceof EntityHorse) || !((EntityHorse)var1).co();
      } else {
         return false;
      }
   }

   @Override
   public boolean cb() {
      return !this.cv() && super.cb();
   }
}
