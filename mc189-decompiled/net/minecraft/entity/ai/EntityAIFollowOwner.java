package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAIFollowOwner extends EntityAIBase {
   private EntityTameable d;
   private EntityLivingBase e;
   World a;
   private double f;
   private PathNavigate g;
   private int h;
   float b;
   float c;
   private boolean i;

   public EntityAIFollowOwner(EntityTameable var1, double var2, float var4, float var5) {
      this.d = var1;
      this.a = var1.o;
      this.f = var2;
      this.g = var1.s();
      this.c = var4;
      this.b = var5;
      this.a(3);
      if (!(var1.s() instanceof PathNavigateGround)) {
         throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
      }
   }

   @Override
   public boolean a() {
      EntityLivingBase var1 = this.d.co();
      if (var1 == null) {
         return false;
      } else if (var1 instanceof EntityPlayer && ((EntityPlayer)var1).v()) {
         return false;
      } else if (this.d.cn()) {
         return false;
      } else if (this.d.h(var1) < this.c * this.c) {
         return false;
      } else {
         this.e = var1;
         return true;
      }
   }

   @Override
   public boolean b() {
      return !this.g.m() && this.d.h(this.e) > this.b * this.b && !this.d.cn();
   }

   @Override
   public void c() {
      this.h = 0;
      this.i = ((PathNavigateGround)this.d.s()).e();
      ((PathNavigateGround)this.d.s()).a(false);
   }

   @Override
   public void d() {
      this.e = null;
      this.g.n();
      ((PathNavigateGround)this.d.s()).a(true);
   }

   private boolean a(BlockPos var1) {
      IBlockState var2 = this.a.p(var1);
      Block var3 = var2.c();
      return var3 == Blocks.a ? true : !var3.d();
   }

   @Override
   public void e() {
      this.d.p().a(this.e, 10.0F, this.d.bQ());
      if (!this.d.cn()) {
         if (--this.h <= 0) {
            this.h = 10;
            if (!this.g.a(this.e, this.f)) {
               if (!this.d.cc()) {
                  if (!(this.d.h(this.e) < 144.0)) {
                     int var1 = MathHelper.c(this.e.s) - 2;
                     int var2 = MathHelper.c(this.e.u) - 2;
                     int var3 = MathHelper.c(this.e.aR().b);

                     for (int var4 = 0; var4 <= 4; var4++) {
                        for (int var5 = 0; var5 <= 4; var5++) {
                           if ((var4 < 1 || var5 < 1 || var4 > 3 || var5 > 3)
                              && World.a(this.a, new BlockPos(var1 + var4, var3 - 1, var2 + var5))
                              && this.a(new BlockPos(var1 + var4, var3, var2 + var5))
                              && this.a(new BlockPos(var1 + var4, var3 + 1, var2 + var5))) {
                              this.d.b(var1 + var4 + 0.5F, var3, var2 + var5 + 0.5F, this.d.y, this.d.z);
                              this.g.n();
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
}
