package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCarrot;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityRabbit extends EntityAnimal {
   private EntityRabbit.AIAvoidEntity<EntityWolf> bm;
   private int bo = 0;
   private int bp = 0;
   private boolean bq = false;
   private boolean br = false;
   private int bs = 0;
   private EntityRabbit.EnumMoveType bt = EntityRabbit.EnumMoveType.b;
   private int bu = 0;
   private EntityPlayer bv = null;

   public EntityRabbit(World var1) {
      super(var1);
      this.a(0.6F, 0.7F);
      this.g = new EntityRabbit.RabbitJumpHelper(this);
      this.f = new EntityRabbit.RabbitMoveHelper(this);
      ((PathNavigateGround)this.s()).a(true);
      this.h.a(2.5F);
      this.i.a(1, new EntityAISwimming(this));
      this.i.a(1, new EntityRabbit.AIPanic(this, 1.33));
      this.i.a(2, new EntityAITempt(this, 1.0, Items.bR, false));
      this.i.a(2, new EntityAITempt(this, 1.0, Items.bW, false));
      this.i.a(2, new EntityAITempt(this, 1.0, Item.a(Blocks.N), false));
      this.i.a(3, new EntityAIMate(this, 0.8));
      this.i.a(5, new EntityRabbit.AIRaidFarm(this));
      this.i.a(5, new EntityAIWander(this, 0.6));
      this.i.a(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
      this.bm = new EntityRabbit.AIAvoidEntity<>(this, EntityWolf.class, 16.0F, 1.33, 1.33);
      this.i.a(4, this.bm);
      this.b(0.0);
   }

   @Override
   protected float bE() {
      return this.f.a() && this.f.e() > this.t + 0.5 ? 0.5F : this.bt.b();
   }

   public void a(EntityRabbit.EnumMoveType var1) {
      this.bt = var1;
   }

   public float p(float var1) {
      return this.bp == 0 ? 0.0F : (this.bo + var1) / this.bp;
   }

   public void b(double var1) {
      this.s().a(var1);
      this.f.a(this.f.d(), this.f.e(), this.f.f(), var1);
   }

   public void a(boolean var1, EntityRabbit.EnumMoveType var2) {
      super.i(var1);
      if (!var1) {
         if (this.bt == EntityRabbit.EnumMoveType.e) {
            this.bt = EntityRabbit.EnumMoveType.b;
         }
      } else {
         this.b(1.5 * var2.a());
         this.a(this.cm(), this.bB(), ((this.V.nextFloat() - this.V.nextFloat()) * 0.2F + 1.0F) * 0.8F);
      }

      this.bq = var1;
   }

   public void b(EntityRabbit.EnumMoveType var1) {
      this.a(true, var1);
      this.bp = var1.d();
      this.bo = 0;
   }

   public boolean cl() {
      return this.bq;
   }

   @Override
   protected void h() {
      super.h();
      this.ac.a(18, Byte.valueOf((byte)0));
   }

   @Override
   public void E() {
      if (this.f.b() > 0.8) {
         this.a(EntityRabbit.EnumMoveType.d);
      } else if (this.bt != EntityRabbit.EnumMoveType.e) {
         this.a(EntityRabbit.EnumMoveType.b);
      }

      if (this.bs > 0) {
         this.bs--;
      }

      if (this.bu > 0) {
         this.bu = this.bu - this.V.nextInt(3);
         if (this.bu < 0) {
            this.bu = 0;
         }
      }

      if (this.C) {
         if (!this.br) {
            this.a(false, EntityRabbit.EnumMoveType.a);
            this.cw();
         }

         if (this.cn() == 99 && this.bs == 0) {
            EntityLivingBase var1 = this.u();
            if (var1 != null && this.h(var1) < 16.0) {
               this.a(var1.s, var1.u);
               this.f.a(var1.s, var1.t, var1.u, this.f.b());
               this.b(EntityRabbit.EnumMoveType.e);
               this.br = true;
            }
         }

         EntityRabbit.RabbitJumpHelper var4 = (EntityRabbit.RabbitJumpHelper)this.g;
         if (!var4.c()) {
            if (this.f.a() && this.bs == 0) {
               PathEntity var2 = this.h.j();
               Vec3 var3 = new Vec3(this.f.d(), this.f.e(), this.f.f());
               if (var2 != null && var2.e() < var2.d()) {
                  var3 = var2.a(this);
               }

               this.a(var3.a, var3.c);
               this.b(this.bt);
            }
         } else if (!var4.d()) {
            this.ct();
         }
      }

      this.br = this.C;
   }

   @Override
   public void Y() {
   }

   private void a(double var1, double var3) {
      this.y = (float)(MathHelper.b(var3 - this.u, var1 - this.s) * 180.0 / (float) Math.PI) - 90.0F;
   }

   private void ct() {
      ((EntityRabbit.RabbitJumpHelper)this.g).a(true);
   }

   private void cu() {
      ((EntityRabbit.RabbitJumpHelper)this.g).a(false);
   }

   private void cv() {
      this.bs = this.co();
   }

   private void cw() {
      this.cv();
      this.cu();
   }

   @Override
   public void m() {
      super.m();
      if (this.bo != this.bp) {
         if (this.bo == 0 && !this.o.D) {
            this.o.a(this, (byte)1);
         }

         this.bo++;
      } else if (this.bp != 0) {
         this.bo = 0;
         this.bp = 0;
      }
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.a).a(10.0);
      this.a(SharedMonsterAttributes.d).a(0.3F);
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("RabbitType", this.cn());
      var1.a("MoreCarrotTicks", this.bu);
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.r(var1.f("RabbitType"));
      this.bu = var1.f("MoreCarrotTicks");
   }

   protected String cm() {
      return "mob.rabbit.hop";
   }

   @Override
   protected String z() {
      return "mob.rabbit.idle";
   }

   @Override
   protected String bo() {
      return "mob.rabbit.hurt";
   }

   @Override
   protected String bp() {
      return "mob.rabbit.death";
   }

   @Override
   public boolean r(Entity var1) {
      if (this.cn() == 99) {
         this.a("mob.attack", 1.0F, (this.V.nextFloat() - this.V.nextFloat()) * 0.2F + 1.0F);
         return var1.a(DamageSource.a((EntityLivingBase)this), 8.0F);
      } else {
         return var1.a(DamageSource.a((EntityLivingBase)this), 3.0F);
      }
   }

   @Override
   public int br() {
      return this.cn() == 99 ? 8 : super.br();
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      return this.b(var1) ? false : super.a(var1, var2);
   }

   @Override
   protected void bq() {
      this.a(new ItemStack(Items.br, 1), 0.0F);
   }

   @Override
   protected void b(boolean var1, int var2) {
      int var3 = this.V.nextInt(2) + this.V.nextInt(1 + var2);

      for (int var4 = 0; var4 < var3; var4++) {
         this.a(Items.bs, 1);
      }

      var3 = this.V.nextInt(2);

      for (int var6 = 0; var6 < var3; var6++) {
         if (this.at()) {
            this.a(Items.bp, 1);
         } else {
            this.a(Items.bo, 1);
         }
      }
   }

   private boolean a(Item var1) {
      return var1 == Items.bR || var1 == Items.bW || var1 == Item.a(Blocks.N);
   }

   public EntityRabbit b(EntityAgeable var1) {
      EntityRabbit var2 = new EntityRabbit(this.o);
      if (var1 instanceof EntityRabbit) {
         var2.r(this.V.nextBoolean() ? this.cn() : ((EntityRabbit)var1).cn());
      }

      return var2;
   }

   @Override
   public boolean d(ItemStack var1) {
      return var1 != null && this.a(var1.b());
   }

   public int cn() {
      return this.ac.a(18);
   }

   public void r(int var1) {
      if (var1 == 99) {
         this.i.a(this.bm);
         this.i.a(4, new EntityRabbit.AIEvilAttack(this));
         this.bi.a(1, new EntityAIHurtByTarget(this, false));
         this.bi.a(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
         this.bi.a(2, new EntityAINearestAttackableTarget<>(this, EntityWolf.class, true));
         if (!this.l_()) {
            this.a(StatCollector.a("entity.KillerBunny.name"));
         }
      }

      this.ac.b(18, (byte)var1);
   }

   @Override
   public IEntityLivingData a(DifficultyInstance var1, IEntityLivingData var2) {
      var2 = super.a(var1, var2);
      int var3 = this.V.nextInt(6);
      boolean var4 = false;
      if (var2 instanceof EntityRabbit.RabbitTypeData) {
         var3 = ((EntityRabbit.RabbitTypeData)var2).a;
         var4 = true;
      } else {
         var2 = new EntityRabbit.RabbitTypeData(var3);
      }

      this.r(var3);
      if (var4) {
         this.b(-24000);
      }

      return var2;
   }

   private boolean cx() {
      return this.bu == 0;
   }

   protected int co() {
      return this.bt.c();
   }

   protected void cp() {
      this.o
         .a(
            EnumParticleTypes.M,
            this.s + this.V.nextFloat() * this.J * 2.0F - this.J,
            this.t + 0.5 + this.V.nextFloat() * this.K,
            this.u + this.V.nextFloat() * this.J * 2.0F - this.J,
            0.0,
            0.0,
            0.0,
            Block.f(Blocks.cb.a(7))
         );
      this.bu = 100;
   }

   @Override
   public void a(byte var1) {
      if (var1 == 1) {
         this.Z();
         this.bp = 10;
         this.bo = 0;
      } else {
         super.a(var1);
      }
   }

   static class AIAvoidEntity<T extends Entity> extends EntityAIAvoidEntity<T> {
      private EntityRabbit c;

      public AIAvoidEntity(EntityRabbit var1, Class<T> var2, float var3, double var4, double var6) {
         super(var1, var2, var3, var4, var6);
         this.c = var1;
      }

      @Override
      public void e() {
         super.e();
      }
   }

   static class AIEvilAttack extends EntityAIAttackOnCollide {
      public AIEvilAttack(EntityRabbit var1) {
         super(var1, EntityLivingBase.class, 1.4, true);
      }

      @Override
      protected double a(EntityLivingBase var1) {
         return 4.0F + var1.J;
      }
   }

   static class AIPanic extends EntityAIPanic {
      private EntityRabbit b;

      public AIPanic(EntityRabbit var1, double var2) {
         super(var1, var2);
         this.b = var1;
      }

      @Override
      public void e() {
         super.e();
         this.b.b(this.a);
      }
   }

   static class AIRaidFarm extends EntityAIMoveToBlock {
      private final EntityRabbit c;
      private boolean d;
      private boolean e = false;

      public AIRaidFarm(EntityRabbit var1) {
         super(var1, 0.7F, 16);
         this.c = var1;
      }

      @Override
      public boolean a() {
         if (this.a <= 0) {
            if (!this.c.o.Q().b("mobGriefing")) {
               return false;
            }

            this.e = false;
            this.d = this.c.cx();
         }

         return super.a();
      }

      @Override
      public boolean b() {
         return this.e && super.b();
      }

      @Override
      public void c() {
         super.c();
      }

      @Override
      public void d() {
         super.d();
      }

      @Override
      public void e() {
         super.e();
         this.c.p().a(this.b.n() + 0.5, this.b.o() + 1, this.b.p() + 0.5, 10.0F, this.c.bQ());
         if (this.f()) {
            World var1 = this.c.o;
            BlockPos var2 = this.b.a();
            IBlockState var3 = var1.p(var2);
            Block var4 = var3.c();
            if (this.e && var4 instanceof BlockCarrot && var3.b(BlockCarrot.a) == 7) {
               var1.a(var2, Blocks.a.Q(), 2);
               var1.b(var2, true);
               this.c.cp();
            }

            this.e = false;
            this.a = 10;
         }
      }

      @Override
      protected boolean a(World var1, BlockPos var2) {
         Block var3 = var1.p(var2).c();
         if (var3 == Blocks.ak) {
            var2 = var2.a();
            IBlockState var4 = var1.p(var2);
            var3 = var4.c();
            if (var3 instanceof BlockCarrot && var4.b(BlockCarrot.a) == 7 && this.d && !this.e) {
               this.e = true;
               return true;
            }
         }

         return false;
      }
   }

   static enum EnumMoveType {
      a(0.0F, 0.0F, 30, 1),
      b(0.8F, 0.2F, 20, 10),
      c(1.0F, 0.45F, 14, 14),
      d(1.75F, 0.4F, 1, 8),
      e(2.0F, 0.7F, 7, 8);

      private final float f;
      private final float g;
      private final int h;
      private final int i;

      private EnumMoveType(float var3, float var4, int var5, int var6) {
         this.f = var3;
         this.g = var4;
         this.h = var5;
         this.i = var6;
      }

      public float a() {
         return this.f;
      }

      public float b() {
         return this.g;
      }

      public int c() {
         return this.h;
      }

      public int d() {
         return this.i;
      }
   }

   public class RabbitJumpHelper extends EntityJumpHelper {
      private EntityRabbit c;
      private boolean d = false;

      public RabbitJumpHelper(EntityRabbit var2) {
         super(var2);
         this.c = var2;
      }

      public boolean c() {
         return this.a;
      }

      public boolean d() {
         return this.d;
      }

      public void a(boolean var1) {
         this.d = var1;
      }

      @Override
      public void b() {
         if (this.a) {
            this.c.b(EntityRabbit.EnumMoveType.c);
            this.a = false;
         }
      }
   }

   static class RabbitMoveHelper extends EntityMoveHelper {
      private EntityRabbit g;

      public RabbitMoveHelper(EntityRabbit var1) {
         super(var1);
         this.g = var1;
      }

      @Override
      public void c() {
         if (this.g.C && !this.g.cl()) {
            this.g.b(0.0);
         }

         super.c();
      }
   }

   public static class RabbitTypeData implements IEntityLivingData {
      public int a;

      public RabbitTypeData(int var1) {
         this.a = var1;
      }
   }
}
