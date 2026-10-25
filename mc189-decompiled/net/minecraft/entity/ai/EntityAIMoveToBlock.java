package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class EntityAIMoveToBlock extends EntityAIBase {
   private final EntityCreature c;
   private final double d;
   protected int a;
   private int e;
   private int f;
   protected BlockPos b = BlockPos.a;
   private boolean g;
   private int h;

   public EntityAIMoveToBlock(EntityCreature var1, double var2, int var4) {
      this.c = var1;
      this.d = var2;
      this.h = var4;
      this.a(5);
   }

   @Override
   public boolean a() {
      if (this.a > 0) {
         this.a--;
         return false;
      } else {
         this.a = 200 + this.c.bc().nextInt(200);
         return this.g();
      }
   }

   @Override
   public boolean b() {
      return this.e >= -this.f && this.e <= 1200 && this.a(this.c.o, this.b);
   }

   @Override
   public void c() {
      this.c.s().a(this.b.n() + 0.5, this.b.o() + 1, this.b.p() + 0.5, this.d);
      this.e = 0;
      this.f = this.c.bc().nextInt(this.c.bc().nextInt(1200) + 1200) + 1200;
   }

   @Override
   public void d() {
   }

   @Override
   public void e() {
      if (this.c.c(this.b.a()) > 1.0) {
         this.g = false;
         this.e++;
         if (this.e % 40 == 0) {
            this.c.s().a(this.b.n() + 0.5, this.b.o() + 1, this.b.p() + 0.5, this.d);
         }
      } else {
         this.g = true;
         this.e--;
      }
   }

   protected boolean f() {
      return this.g;
   }

   private boolean g() {
      int var1 = this.h;
      boolean var2 = true;
      BlockPos var3 = new BlockPos(this.c);

      for (int var4 = 0; var4 <= 1; var4 = var4 > 0 ? -var4 : 1 - var4) {
         for (int var5 = 0; var5 < var1; var5++) {
            for (int var6 = 0; var6 <= var5; var6 = var6 > 0 ? -var6 : 1 - var6) {
               for (int var7 = var6 < var5 && var6 > -var5 ? var5 : 0; var7 <= var5; var7 = var7 > 0 ? -var7 : 1 - var7) {
                  BlockPos var8 = var3.a(var6, var4 - 1, var7);
                  if (this.c.e(var8) && this.a(this.c.o, var8)) {
                     this.b = var8;
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   protected abstract boolean a(World var1, BlockPos var2);
}
