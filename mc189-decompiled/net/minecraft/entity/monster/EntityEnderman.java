package net.minecraft.entity.monster;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityEnderman extends EntityMob {
   private static final UUID a = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
   private static final AttributeModifier b = new AttributeModifier(a, "Attacking speed boost", 0.15F, 0).a(false);
   private static final Set<Block> c = Sets.newIdentityHashSet();
   private boolean bm;

   public EntityEnderman(World var1) {
      super(var1);
      this.a(0.6F, 2.9F);
      this.S = 1.0F;
      this.i.a(0, new EntityAISwimming(this));
      this.i.a(2, new EntityAIAttackOnCollide(this, 1.0, false));
      this.i.a(7, new EntityAIWander(this, 1.0));
      this.i.a(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.i.a(8, new EntityAILookIdle(this));
      this.i.a(10, new EntityEnderman.AIPlaceBlock(this));
      this.i.a(11, new EntityEnderman.AITakeBlock(this));
      this.bi.a(1, new EntityAIHurtByTarget(this, false));
      this.bi.a(2, new EntityEnderman.AIFindPlayer(this));
      this.bi.a(3, new EntityAINearestAttackableTarget<>(this, EntityEndermite.class, 10, true, false, new Predicate<EntityEndermite>() {
         public boolean a(EntityEndermite var1) {
            return var1.n();
         }
      }));
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.a).a(40.0);
      this.a(SharedMonsterAttributes.d).a(0.3F);
      this.a(SharedMonsterAttributes.e).a(7.0);
      this.a(SharedMonsterAttributes.b).a(64.0);
   }

   @Override
   protected void h() {
      super.h();
      this.ac.a(16, new Short((short)0));
      this.ac.a(17, new Byte((byte)0));
      this.ac.a(18, new Byte((byte)0));
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      IBlockState var2 = this.cm();
      var1.a("carried", (short)Block.a(var2.c()));
      var1.a("carriedData", (short)var2.c().c(var2));
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      IBlockState var2;
      if (var1.b("carried", 8)) {
         var2 = Block.b(var1.j("carried")).a(var1.e("carriedData") & '\uffff');
      } else {
         var2 = Block.c(var1.e("carried")).a(var1.e("carriedData") & '\uffff');
      }

      this.a(var2);
   }

   private boolean c(EntityPlayer var1) {
      ItemStack var2 = var1.bi.b[3];
      if (var2 != null && var2.b() == Item.a(Blocks.aU)) {
         return false;
      } else {
         Vec3 var3 = var1.d(1.0F).a();
         Vec3 var4 = new Vec3(this.s - var1.s, this.aR().b + this.K / 2.0F - (var1.t + var1.aS()), this.u - var1.u);
         double var5 = var4.b();
         var4 = var4.a();
         double var7 = var3.b(var4);
         return var7 > 1.0 - 0.025 / var5 ? var1.t(this) : false;
      }
   }

   @Override
   public float aS() {
      return 2.55F;
   }

   @Override
   public void m() {
      if (this.o.D) {
         for (int var1 = 0; var1 < 2; var1++) {
            this.o
               .a(
                  EnumParticleTypes.y,
                  this.s + (this.V.nextDouble() - 0.5) * this.J,
                  this.t + this.V.nextDouble() * this.K - 0.25,
                  this.u + (this.V.nextDouble() - 0.5) * this.J,
                  (this.V.nextDouble() - 0.5) * 2.0,
                  -this.V.nextDouble(),
                  (this.V.nextDouble() - 0.5) * 2.0
               );
         }
      }

      this.aY = false;
      super.m();
   }

   @Override
   protected void E() {
      if (this.U()) {
         this.a(DamageSource.f, 1.0F);
      }

      if (this.co() && !this.bm && this.V.nextInt(100) == 0) {
         this.a(false);
      }

      if (this.o.w()) {
         float var1 = this.c(1.0F);
         if (var1 > 0.5F && this.o.i(new BlockPos(this)) && this.V.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F) {
            this.d(null);
            this.a(false);
            this.bm = false;
            this.n();
         }
      }

      super.E();
   }

   protected boolean n() {
      double var1 = this.s + (this.V.nextDouble() - 0.5) * 64.0;
      double var3 = this.t + (this.V.nextInt(64) - 32);
      double var5 = this.u + (this.V.nextDouble() - 0.5) * 64.0;
      return this.k(var1, var3, var5);
   }

   protected boolean b(Entity var1) {
      Vec3 var2 = new Vec3(this.s - var1.s, this.aR().b + this.K / 2.0F - var1.t + var1.aS(), this.u - var1.u);
      var2 = var2.a();
      double var3 = 16.0;
      double var5 = this.s + (this.V.nextDouble() - 0.5) * 8.0 - var2.a * var3;
      double var7 = this.t + (this.V.nextInt(16) - 8) - var2.b * var3;
      double var9 = this.u + (this.V.nextDouble() - 0.5) * 8.0 - var2.c * var3;
      return this.k(var5, var7, var9);
   }

   protected boolean k(double var1, double var3, double var5) {
      double var7 = this.s;
      double var9 = this.t;
      double var11 = this.u;
      this.s = var1;
      this.t = var3;
      this.u = var5;
      boolean var13 = false;
      BlockPos var14 = new BlockPos(this.s, this.t, this.u);
      if (this.o.e(var14)) {
         boolean var15 = false;

         while (!var15 && var14.o() > 0) {
            BlockPos var16 = var14.b();
            Block var17 = this.o.p(var16).c();
            if (var17.t().c()) {
               var15 = true;
            } else {
               this.t--;
               var14 = var16;
            }
         }

         if (var15) {
            super.a(this.s, this.t, this.u);
            if (this.o.a(this, this.aR()).isEmpty() && !this.o.d(this.aR())) {
               var13 = true;
            }
         }
      }

      if (!var13) {
         this.b(var7, var9, var11);
         return false;
      } else {
         short var28 = 128;

         for (int var29 = 0; var29 < var28; var29++) {
            double var30 = var29 / (var28 - 1.0);
            float var19 = (this.V.nextFloat() - 0.5F) * 0.2F;
            float var20 = (this.V.nextFloat() - 0.5F) * 0.2F;
            float var21 = (this.V.nextFloat() - 0.5F) * 0.2F;
            double var22 = var7 + (this.s - var7) * var30 + (this.V.nextDouble() - 0.5) * this.J * 2.0;
            double var24 = var9 + (this.t - var9) * var30 + this.V.nextDouble() * this.K;
            double var26 = var11 + (this.u - var11) * var30 + (this.V.nextDouble() - 0.5) * this.J * 2.0;
            this.o.a(EnumParticleTypes.y, var22, var24, var26, var19, var20, var21);
         }

         this.o.a(var7, var9, var11, "mob.endermen.portal", 1.0F, 1.0F);
         this.a("mob.endermen.portal", 1.0F, 1.0F);
         return true;
      }
   }

   @Override
   protected String z() {
      return this.co() ? "mob.endermen.scream" : "mob.endermen.idle";
   }

   @Override
   protected String bo() {
      return "mob.endermen.hit";
   }

   @Override
   protected String bp() {
      return "mob.endermen.death";
   }

   @Override
   protected Item A() {
      return Items.bu;
   }

   @Override
   protected void b(boolean var1, int var2) {
      Item var3 = this.A();
      if (var3 != null) {
         int var4 = this.V.nextInt(2 + var2);

         for (int var5 = 0; var5 < var4; var5++) {
            this.a(var3, 1);
         }
      }
   }

   public void a(IBlockState var1) {
      this.ac.b(16, (short)(Block.f(var1) & 65535));
   }

   public IBlockState cm() {
      return Block.d(this.ac.b(16) & '\uffff');
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      if (this.b(var1)) {
         return false;
      } else {
         if (var1.j() == null || !(var1.j() instanceof EntityEndermite)) {
            if (!this.o.D) {
               this.a(true);
            }

            if (var1 instanceof EntityDamageSource && var1.j() instanceof EntityPlayer) {
               if (var1.j() instanceof EntityPlayerMP && ((EntityPlayerMP)var1.j()).c.d()) {
                  this.a(false);
               } else {
                  this.bm = true;
               }
            }

            if (var1 instanceof EntityDamageSourceIndirect) {
               this.bm = false;

               for (int var4 = 0; var4 < 64; var4++) {
                  if (this.n()) {
                     return true;
                  }
               }

               return false;
            }
         }

         boolean var3 = super.a(var1, var2);
         if (var1.e() && this.V.nextInt(10) != 0) {
            this.n();
         }

         return var3;
      }
   }

   public boolean co() {
      return this.ac.a(18) > 0;
   }

   public void a(boolean var1) {
      this.ac.b(18, (byte)(var1 ? 1 : 0));
   }

   static {
      c.add(Blocks.c);
      c.add(Blocks.d);
      c.add(Blocks.m);
      c.add(Blocks.n);
      c.add(Blocks.N);
      c.add(Blocks.O);
      c.add(Blocks.P);
      c.add(Blocks.Q);
      c.add(Blocks.W);
      c.add(Blocks.aK);
      c.add(Blocks.aL);
      c.add(Blocks.aU);
      c.add(Blocks.bk);
      c.add(Blocks.bw);
   }

   static class AIFindPlayer extends EntityAINearestAttackableTarget {
      private EntityPlayer g;
      private int h;
      private int i;
      private EntityEnderman j;

      public AIFindPlayer(EntityEnderman var1) {
         super(var1, EntityPlayer.class, true);
         this.j = var1;
      }

      @Override
      public boolean a() {
         double var1 = this.f();
         List var3 = this.e.o.a(EntityPlayer.class, this.e.aR().b(var1, 4.0, var1), this.c);
         Collections.sort(var3, this.b);
         if (var3.isEmpty()) {
            return false;
         } else {
            this.g = (EntityPlayer)var3.get(0);
            return true;
         }
      }

      @Override
      public void c() {
         this.h = 5;
         this.i = 0;
      }

      @Override
      public void d() {
         this.g = null;
         this.j.a(false);
         IAttributeInstance var1 = this.j.a(SharedMonsterAttributes.d);
         var1.c(EntityEnderman.b);
         super.d();
      }

      @Override
      public boolean b() {
         if (this.g != null) {
            if (!this.j.c(this.g)) {
               return false;
            } else {
               this.j.bm = true;
               this.j.a(this.g, 10.0F, 10.0F);
               return true;
            }
         } else {
            return super.b();
         }
      }

      @Override
      public void e() {
         if (this.g != null) {
            if (--this.h <= 0) {
               this.d = this.g;
               this.g = null;
               super.c();
               this.j.a("mob.endermen.stare", 1.0F, 1.0F);
               this.j.a(true);
               IAttributeInstance var1 = this.j.a(SharedMonsterAttributes.d);
               var1.b(EntityEnderman.b);
            }
         } else {
            if (this.d != null) {
               if (this.d instanceof EntityPlayer && this.j.c((EntityPlayer)this.d)) {
                  if (this.d.h(this.j) < 16.0) {
                     this.j.n();
                  }

                  this.i = 0;
               } else if (this.d.h(this.j) > 256.0 && this.i++ >= 30 && this.j.b(this.d)) {
                  this.i = 0;
               }
            }

            super.e();
         }
      }
   }

   static class AIPlaceBlock extends EntityAIBase {
      private EntityEnderman a;

      public AIPlaceBlock(EntityEnderman var1) {
         this.a = var1;
      }

      @Override
      public boolean a() {
         if (!this.a.o.Q().b("mobGriefing")) {
            return false;
         } else {
            return this.a.cm().c().t() == Material.a ? false : this.a.bc().nextInt(2000) == 0;
         }
      }

      @Override
      public void e() {
         Random var1 = this.a.bc();
         World var2 = this.a.o;
         int var3 = MathHelper.c(this.a.s - 1.0 + var1.nextDouble() * 2.0);
         int var4 = MathHelper.c(this.a.t + var1.nextDouble() * 2.0);
         int var5 = MathHelper.c(this.a.u - 1.0 + var1.nextDouble() * 2.0);
         BlockPos var6 = new BlockPos(var3, var4, var5);
         Block var7 = var2.p(var6).c();
         Block var8 = var2.p(var6.b()).c();
         if (this.a(var2, var6, this.a.cm().c(), var7, var8)) {
            var2.a(var6, this.a.cm(), 3);
            this.a.a(Blocks.a.Q());
         }
      }

      private boolean a(World var1, BlockPos var2, Block var3, Block var4, Block var5) {
         if (!var3.d(var1, var2)) {
            return false;
         } else if (var4.t() != Material.a) {
            return false;
         } else {
            return var5.t() == Material.a ? false : var5.d();
         }
      }
   }

   static class AITakeBlock extends EntityAIBase {
      private EntityEnderman a;

      public AITakeBlock(EntityEnderman var1) {
         this.a = var1;
      }

      @Override
      public boolean a() {
         if (!this.a.o.Q().b("mobGriefing")) {
            return false;
         } else {
            return this.a.cm().c().t() != Material.a ? false : this.a.bc().nextInt(20) == 0;
         }
      }

      @Override
      public void e() {
         Random var1 = this.a.bc();
         World var2 = this.a.o;
         int var3 = MathHelper.c(this.a.s - 2.0 + var1.nextDouble() * 4.0);
         int var4 = MathHelper.c(this.a.t + var1.nextDouble() * 3.0);
         int var5 = MathHelper.c(this.a.u - 2.0 + var1.nextDouble() * 4.0);
         BlockPos var6 = new BlockPos(var3, var4, var5);
         IBlockState var7 = var2.p(var6);
         Block var8 = var7.c();
         if (EntityEnderman.c.contains(var8)) {
            this.a.a(var7);
            var2.a(var6, Blocks.a.Q());
         }
      }
   }
}
