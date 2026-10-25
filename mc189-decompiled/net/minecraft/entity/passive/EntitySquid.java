package net.minecraft.entity.passive;

import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySquid extends EntityWaterMob {
   public float a;
   public float b;
   public float c;
   public float bk;
   public float bl;
   public float bm;
   public float bn;
   public float bo;
   private float bp;
   private float bq;
   private float br;
   private float bs;
   private float bt;
   private float bu;

   public EntitySquid(World var1) {
      super(var1);
      this.a(0.95F, 0.95F);
      this.V.setSeed(1 + this.F());
      this.bq = 1.0F / (this.V.nextFloat() + 1.0F) * 0.2F;
      this.i.a(0, new EntitySquid.AIMoveRandom(this));
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.a).a(10.0);
   }

   @Override
   public float aS() {
      return this.K * 0.5F;
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

   @Override
   protected float bB() {
      return 0.4F;
   }

   @Override
   protected Item A() {
      return null;
   }

   @Override
   protected boolean s_() {
      return false;
   }

   @Override
   protected void b(boolean var1, int var2) {
      int var3 = this.V.nextInt(3 + var2) + 1;

      for (int var4 = 0; var4 < var3; var4++) {
         this.a(new ItemStack(Items.aW, 1, EnumDyeColor.p.b()), 0.0F);
      }
   }

   @Override
   public boolean V() {
      return this.o.a(this.aR().b(0.0, -0.6F, 0.0), Material.h, this);
   }

   @Override
   public void m() {
      super.m();
      this.b = this.a;
      this.bk = this.c;
      this.bm = this.bl;
      this.bo = this.bn;
      this.bl = this.bl + this.bq;
      if (this.bl > Math.PI * 2) {
         if (this.o.D) {
            this.bl = (float) (Math.PI * 2);
         } else {
            this.bl = (float)(this.bl - (Math.PI * 2));
            if (this.V.nextInt(10) == 0) {
               this.bq = 1.0F / (this.V.nextFloat() + 1.0F) * 0.2F;
            }

            this.o.a(this, (byte)19);
         }
      }

      if (this.Y) {
         if (this.bl < (float) Math.PI) {
            float var1 = this.bl / (float) Math.PI;
            this.bn = MathHelper.a(var1 * var1 * (float) Math.PI) * (float) Math.PI * 0.25F;
            if (var1 > 0.75) {
               this.bp = 1.0F;
               this.br = 1.0F;
            } else {
               this.br *= 0.8F;
            }
         } else {
            this.bn = 0.0F;
            this.bp *= 0.9F;
            this.br *= 0.99F;
         }

         if (!this.o.D) {
            this.v = this.bs * this.bp;
            this.w = this.bt * this.bp;
            this.x = this.bu * this.bp;
         }

         float var2 = MathHelper.a(this.v * this.v + this.x * this.x);
         this.aI = this.aI + (-((float)MathHelper.b(this.v, this.x)) * 180.0F / (float) Math.PI - this.aI) * 0.1F;
         this.y = this.aI;
         this.c = (float)(this.c + Math.PI * this.br * 1.5);
         this.a = this.a + (-((float)MathHelper.b((double)var2, this.w)) * 180.0F / (float) Math.PI - this.a) * 0.1F;
      } else {
         this.bn = MathHelper.e(MathHelper.a(this.bl)) * (float) Math.PI * 0.25F;
         if (!this.o.D) {
            this.v = 0.0;
            this.w -= 0.08;
            this.w *= 0.98F;
            this.x = 0.0;
         }

         this.a = (float)(this.a + (-90.0F - this.a) * 0.02);
      }
   }

   @Override
   public void g(float var1, float var2) {
      this.d(this.v, this.w, this.x);
   }

   @Override
   public boolean bR() {
      return this.t > 45.0 && this.t < this.o.F() && super.bR();
   }

   @Override
   public void a(byte var1) {
      if (var1 == 19) {
         this.bl = 0.0F;
      } else {
         super.a(var1);
      }
   }

   public void b(float var1, float var2, float var3) {
      this.bs = var1;
      this.bt = var2;
      this.bu = var3;
   }

   public boolean n() {
      return this.bs != 0.0F || this.bt != 0.0F || this.bu != 0.0F;
   }

   static class AIMoveRandom extends EntityAIBase {
      private EntitySquid a;

      public AIMoveRandom(EntitySquid var1) {
         this.a = var1;
      }

      @Override
      public boolean a() {
         return true;
      }

      @Override
      public void e() {
         int var1 = this.a.bh();
         if (var1 > 100) {
            this.a.b(0.0F, 0.0F, 0.0F);
         } else if (this.a.bc().nextInt(50) == 0 || !this.a.Y || !this.a.n()) {
            float var2 = this.a.bc().nextFloat() * (float) Math.PI * 2.0F;
            float var3 = MathHelper.b(var2) * 0.2F;
            float var4 = -0.1F + this.a.bc().nextFloat() * 0.2F;
            float var5 = MathHelper.a(var2) * 0.2F;
            this.a.b(var3, var4, var5);
         }
      }
   }
}
