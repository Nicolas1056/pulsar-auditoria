package net.minecraft.entity.monster;

import java.util.List;
import java.util.UUID;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityWitch extends EntityMob implements IRangedAttackMob {
   private static final UUID a = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
   private static final AttributeModifier b = new AttributeModifier(a, "Drinking speed penalty", -0.25, 0).a(false);
   private static final Item[] c = new Item[]{Items.aT, Items.aY, Items.aC, Items.bB, Items.bA, Items.H, Items.y, Items.y};
   private int bm;

   public EntityWitch(World var1) {
      super(var1);
      this.a(0.6F, 1.95F);
      this.i.a(1, new EntityAISwimming(this));
      this.i.a(2, new EntityAIArrowAttack(this, 1.0, 60, 10.0F));
      this.i.a(2, new EntityAIWander(this, 1.0));
      this.i.a(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.i.a(3, new EntityAILookIdle(this));
      this.bi.a(1, new EntityAIHurtByTarget(this, false));
      this.bi.a(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
   }

   @Override
   protected void h() {
      super.h();
      this.H().a(21, Byte.valueOf((byte)0));
   }

   @Override
   protected String z() {
      return null;
   }

   @Override
   protected String bo() {
      return null;
   }

   @Override
   protected String bp() {
      return null;
   }

   public void a(boolean var1) {
      this.H().b(21, Byte.valueOf((byte)(var1 ? 1 : 0)));
   }

   public boolean n() {
      return this.H().a(21) == 1;
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.a).a(26.0);
      this.a(SharedMonsterAttributes.d).a(0.25);
   }

   @Override
   public void m() {
      if (!this.o.D) {
         if (this.n()) {
            if (this.bm-- <= 0) {
               this.a(false);
               ItemStack var5 = this.bA();
               this.c(0, null);
               if (var5 != null && var5.b() == Items.bz) {
                  List var6 = Items.bz.h(var5);
                  if (var6 != null) {
                     for (PotionEffect var4 : var6) {
                        this.c(new PotionEffect(var4));
                     }
                  }
               }

               this.a(SharedMonsterAttributes.d).c(b);
            }
         } else {
            short var1 = -1;
            if (this.V.nextFloat() < 0.15F && this.a(Material.h) && !this.a(Potion.o)) {
               var1 = 8237;
            } else if (this.V.nextFloat() < 0.15F && this.at() && !this.a(Potion.n)) {
               var1 = 16307;
            } else if (this.V.nextFloat() < 0.05F && this.bn() < this.bu()) {
               var1 = 16341;
            } else if (this.V.nextFloat() < 0.25F && this.u() != null && !this.a(Potion.c) && this.u().h(this) > 121.0) {
               var1 = 16274;
            } else if (this.V.nextFloat() < 0.25F && this.u() != null && !this.a(Potion.c) && this.u().h(this) > 121.0) {
               var1 = 16274;
            }

            if (var1 > -1) {
               this.c(0, new ItemStack(Items.bz, 1, var1));
               this.bm = this.bA().l();
               this.a(true);
               IAttributeInstance var2 = this.a(SharedMonsterAttributes.d);
               var2.c(b);
               var2.b(b);
            }
         }

         if (this.V.nextFloat() < 7.5E-4F) {
            this.o.a(this, (byte)15);
         }
      }

      super.m();
   }

   @Override
   public void a(byte var1) {
      if (var1 == 15) {
         for (int var2 = 0; var2 < this.V.nextInt(35) + 10; var2++) {
            this.o
               .a(
                  EnumParticleTypes.r,
                  this.s + this.V.nextGaussian() * 0.13F,
                  this.aR().e + 0.5 + this.V.nextGaussian() * 0.13F,
                  this.u + this.V.nextGaussian() * 0.13F,
                  0.0,
                  0.0,
                  0.0
               );
         }
      } else {
         super.a(var1);
      }
   }

   @Override
   protected float c(DamageSource var1, float var2) {
      var2 = super.c(var1, var2);
      if (var1.j() == this) {
         var2 = 0.0F;
      }

      if (var1.s()) {
         var2 = (float)(var2 * 0.15);
      }

      return var2;
   }

   @Override
   protected void b(boolean var1, int var2) {
      int var3 = this.V.nextInt(3) + 1;

      for (int var4 = 0; var4 < var3; var4++) {
         int var5 = this.V.nextInt(3);
         Item var6 = c[this.V.nextInt(c.length)];
         if (var2 > 0) {
            var5 += this.V.nextInt(var2 + 1);
         }

         for (int var7 = 0; var7 < var5; var7++) {
            this.a(var6, 1);
         }
      }
   }

   @Override
   public void a(EntityLivingBase var1, float var2) {
      if (!this.n()) {
         EntityPotion var3 = new EntityPotion(this.o, this, 32732);
         double var4 = var1.t + var1.aS() - 1.1F;
         var3.z -= -20.0F;
         double var6 = var1.s + var1.v - this.s;
         double var8 = var4 - this.t;
         double var10 = var1.u + var1.x - this.u;
         float var12 = MathHelper.a(var6 * var6 + var10 * var10);
         if (var12 >= 8.0F && !var1.a(Potion.d)) {
            var3.a(32698);
         } else if (var1.bn() >= 8.0F && !var1.a(Potion.u)) {
            var3.a(32660);
         } else if (var12 <= 3.0F && !var1.a(Potion.t) && this.V.nextFloat() < 0.25F) {
            var3.a(32696);
         }

         var3.c(var6, var8 + var12 * 0.2F, var10, 0.75F, 8.0F);
         this.o.d(var3);
      }
   }

   @Override
   public float aS() {
      return 1.62F;
   }
}
