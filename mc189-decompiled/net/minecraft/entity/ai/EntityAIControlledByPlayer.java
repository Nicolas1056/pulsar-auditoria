package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.pathfinder.WalkNodeProcessor;

public class EntityAIControlledByPlayer extends EntityAIBase {
   private final EntityLiving a;
   private final float b;
   private float c;
   private boolean d;
   private int e;
   private int f;

   public EntityAIControlledByPlayer(EntityLiving var1, float var2) {
      this.a = var1;
      this.b = var2;
      this.a(7);
   }

   @Override
   public void c() {
      this.c = 0.0F;
   }

   @Override
   public void d() {
      this.d = false;
      this.c = 0.0F;
   }

   @Override
   public boolean a() {
      return this.a.ai() && this.a.l != null && this.a.l instanceof EntityPlayer && (this.d || this.a.bW());
   }

   @Override
   public void e() {
      EntityPlayer var1 = (EntityPlayer)this.a.l;
      EntityCreature var2 = (EntityCreature)this.a;
      float var3 = MathHelper.g(var1.y - this.a.y) * 0.5F;
      if (var3 > 5.0F) {
         var3 = 5.0F;
      }

      if (var3 < -5.0F) {
         var3 = -5.0F;
      }

      this.a.y = MathHelper.g(this.a.y + var3);
      if (this.c < this.b) {
         this.c = this.c + (this.b - this.c) * 0.01F;
      }

      if (this.c > this.b) {
         this.c = this.b;
      }

      int var4 = MathHelper.c(this.a.s);
      int var5 = MathHelper.c(this.a.t);
      int var6 = MathHelper.c(this.a.u);
      float var7 = this.c;
      if (this.d) {
         if (this.e++ > this.f) {
            this.d = false;
         }

         var7 += var7 * 1.15F * MathHelper.a((float)this.e / this.f * (float) Math.PI);
      }

      float var8 = 0.91F;
      if (this.a.C) {
         var8 = this.a.o.p(new BlockPos(MathHelper.d((float)var4), MathHelper.d((float)var5) - 1, MathHelper.d((float)var6))).c().L * 0.91F;
      }

      float var9 = 0.16277136F / (var8 * var8 * var8);
      float var10 = MathHelper.a(var2.y * (float) Math.PI / 180.0F);
      float var11 = MathHelper.b(var2.y * (float) Math.PI / 180.0F);
      float var12 = var2.bI() * var9;
      float var13 = Math.max(var7, 1.0F);
      var13 = var12 / var13;
      float var14 = var7 * var13;
      float var15 = -(var14 * var10);
      float var16 = var14 * var11;
      if (MathHelper.e(var15) > MathHelper.e(var16)) {
         if (var15 < 0.0F) {
            var15 -= this.a.J / 2.0F;
         }

         if (var15 > 0.0F) {
            var15 += this.a.J / 2.0F;
         }

         var16 = 0.0F;
      } else {
         var15 = 0.0F;
         if (var16 < 0.0F) {
            var16 -= this.a.J / 2.0F;
         }

         if (var16 > 0.0F) {
            var16 += this.a.J / 2.0F;
         }
      }

      int var17 = MathHelper.c(this.a.s + var15);
      int var18 = MathHelper.c(this.a.u + var16);
      int var19 = MathHelper.d(this.a.J + 1.0F);
      int var20 = MathHelper.d(this.a.K + var1.K + 1.0F);
      int var21 = MathHelper.d(this.a.J + 1.0F);
      if (var4 != var17 || var6 != var18) {
         Block var22 = this.a.o.p(new BlockPos(var4, var5, var6)).c();
         boolean var23 = !this.a(var22) && (var22.t() != Material.a || !this.a(this.a.o.p(new BlockPos(var4, var5 - 1, var6)).c()));
         if (var23
            && 0 == WalkNodeProcessor.a(this.a.o, this.a, var17, var5, var18, var19, var20, var21, false, false, true)
            && 1 == WalkNodeProcessor.a(this.a.o, this.a, var4, var5 + 1, var6, var19, var20, var21, false, false, true)
            && 1 == WalkNodeProcessor.a(this.a.o, this.a, var17, var5 + 1, var18, var19, var20, var21, false, false, true)) {
            var2.r().a();
         }
      }

      if (!var1.bA.d && this.c >= this.b * 0.5F && this.a.bc().nextFloat() < 0.006F && !this.d) {
         ItemStack var25 = var1.bA();
         if (var25 != null && var25.b() == Items.bY) {
            var25.a(1, var1);
            if (var25.b == 0) {
               ItemStack var26 = new ItemStack(Items.aR);
               var26.d(var25.o());
               var1.bi.a[var1.bi.c] = var26;
            }
         }
      }

      this.a.g(0.0F, var7);
   }

   private boolean a(Block var1) {
      return var1 instanceof BlockStairs || var1 instanceof BlockSlab;
   }

   public boolean f() {
      return this.d;
   }

   public void g() {
      this.d = true;
      this.e = 0;
      this.f = this.a.bc().nextInt(841) + 140;
   }

   public boolean h() {
      return !this.f() && this.c > this.b * 0.3F;
   }
}
