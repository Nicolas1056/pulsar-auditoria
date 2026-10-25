package net.minecraft.entity.monster;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class EntitySilverfish extends EntityMob {
   private EntitySilverfish.AISummonSilverfish a;

   public EntitySilverfish(World var1) {
      super(var1);
      this.a(0.4F, 0.3F);
      this.i.a(1, new EntityAISwimming(this));
      this.i.a(3, this.a = new EntitySilverfish.AISummonSilverfish(this));
      this.i.a(4, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0, false));
      this.i.a(5, new EntitySilverfish.AIHideInStone(this));
      this.bi.a(1, new EntityAIHurtByTarget(this, true));
      this.bi.a(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
   }

   @Override
   public double am() {
      return 0.2;
   }

   @Override
   public float aS() {
      return 0.1F;
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.a).a(8.0);
      this.a(SharedMonsterAttributes.d).a(0.25);
      this.a(SharedMonsterAttributes.e).a(1.0);
   }

   @Override
   protected boolean s_() {
      return false;
   }

   @Override
   protected String z() {
      return "mob.silverfish.say";
   }

   @Override
   protected String bo() {
      return "mob.silverfish.hit";
   }

   @Override
   protected String bp() {
      return "mob.silverfish.kill";
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      if (this.b(var1)) {
         return false;
      } else {
         if (var1 instanceof EntityDamageSource || var1 == DamageSource.l) {
            this.a.f();
         }

         return super.a(var1, var2);
      }
   }

   @Override
   protected void a(BlockPos var1, Block var2) {
      this.a("mob.silverfish.step", 0.15F, 1.0F);
   }

   @Override
   protected Item A() {
      return null;
   }

   @Override
   public void t_() {
      this.aI = this.y;
      super.t_();
   }

   @Override
   public float a(BlockPos var1) {
      return this.o.p(var1.b()).c() == Blocks.b ? 10.0F : super.a(var1);
   }

   @Override
   protected boolean n_() {
      return true;
   }

   @Override
   public boolean bR() {
      if (super.bR()) {
         EntityPlayer var1 = this.o.a(this, 5.0);
         return var1 == null;
      } else {
         return false;
      }
   }

   @Override
   public EnumCreatureAttribute bz() {
      return EnumCreatureAttribute.c;
   }

   static class AIHideInStone extends EntityAIWander {
      private final EntitySilverfish a;
      private EnumFacing b;
      private boolean c;

      public AIHideInStone(EntitySilverfish var1) {
         super(var1, 1.0, 10);
         this.a = var1;
         this.a(1);
      }

      @Override
      public boolean a() {
         if (this.a.u() != null) {
            return false;
         } else if (!this.a.s().m()) {
            return false;
         } else {
            Random var1 = this.a.bc();
            if (var1.nextInt(10) == 0) {
               this.b = EnumFacing.a(var1);
               BlockPos var2 = new BlockPos(this.a.s, this.a.t + 0.5, this.a.u).a(this.b);
               IBlockState var3 = this.a.o.p(var2);
               if (BlockSilverfish.d(var3)) {
                  this.c = true;
                  return true;
               }
            }

            this.c = false;
            return super.a();
         }
      }

      @Override
      public boolean b() {
         return this.c ? false : super.b();
      }

      @Override
      public void c() {
         if (!this.c) {
            super.c();
         } else {
            World var1 = this.a.o;
            BlockPos var2 = new BlockPos(this.a.s, this.a.t + 0.5, this.a.u).a(this.b);
            IBlockState var3 = var1.p(var2);
            if (BlockSilverfish.d(var3)) {
               var1.a(var2, Blocks.be.Q().a(BlockSilverfish.a, BlockSilverfish.EnumType.a(var3)), 3);
               this.a.y();
               this.a.J();
            }
         }
      }
   }

   static class AISummonSilverfish extends EntityAIBase {
      private EntitySilverfish a;
      private int b;

      public AISummonSilverfish(EntitySilverfish var1) {
         this.a = var1;
      }

      public void f() {
         if (this.b == 0) {
            this.b = 20;
         }
      }

      @Override
      public boolean a() {
         return this.b > 0;
      }

      @Override
      public void e() {
         this.b--;
         if (this.b <= 0) {
            World var1 = this.a.o;
            Random var2 = this.a.bc();
            BlockPos var3 = new BlockPos(this.a);

            for (int var4 = 0; var4 <= 5 && var4 >= -5; var4 = var4 <= 0 ? 1 - var4 : 0 - var4) {
               for (int var5 = 0; var5 <= 10 && var5 >= -10; var5 = var5 <= 0 ? 1 - var5 : 0 - var5) {
                  for (int var6 = 0; var6 <= 10 && var6 >= -10; var6 = var6 <= 0 ? 1 - var6 : 0 - var6) {
                     BlockPos var7 = var3.a(var5, var4, var6);
                     IBlockState var8 = var1.p(var7);
                     if (var8.c() == Blocks.be) {
                        if (var1.Q().b("mobGriefing")) {
                           var1.b(var7, true);
                        } else {
                           var1.a(var7, var8.b(BlockSilverfish.a).d(), 3);
                        }

                        if (var2.nextBoolean()) {
                           return;
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
