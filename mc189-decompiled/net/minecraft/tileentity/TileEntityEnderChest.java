package net.minecraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ITickable;

public class TileEntityEnderChest extends TileEntity implements ITickable {
   public float a;
   public float f;
   public int g;
   private int h;

   @Override
   public void c() {
      if (++this.h % 20 * 4 == 0) {
         this.b.c(this.c, Blocks.bQ, 1, this.g);
      }

      this.f = this.a;
      int var1 = this.c.n();
      int var2 = this.c.o();
      int var3 = this.c.p();
      float var4 = 0.1F;
      if (this.g > 0 && this.a == 0.0F) {
         double var5 = var1 + 0.5;
         double var7 = var3 + 0.5;
         this.b.a(var5, var2 + 0.5, var7, "random.chestopen", 0.5F, this.b.s.nextFloat() * 0.1F + 0.9F);
      }

      if (this.g == 0 && this.a > 0.0F || this.g > 0 && this.a < 1.0F) {
         float var11 = this.a;
         if (this.g > 0) {
            this.a += var4;
         } else {
            this.a -= var4;
         }

         if (this.a > 1.0F) {
            this.a = 1.0F;
         }

         float var6 = 0.5F;
         if (this.a < var6 && var11 >= var6) {
            double var12 = var1 + 0.5;
            double var9 = var3 + 0.5;
            this.b.a(var12, var2 + 0.5, var9, "random.chestclosed", 0.5F, this.b.s.nextFloat() * 0.1F + 0.9F);
         }

         if (this.a < 0.0F) {
            this.a = 0.0F;
         }
      }
   }

   @Override
   public boolean c(int var1, int var2) {
      if (var1 == 1) {
         this.g = var2;
         return true;
      } else {
         return super.c(var1, var2);
      }
   }

   @Override
   public void y() {
      this.E();
      super.y();
   }

   public void b() {
      this.g++;
      this.b.c(this.c, Blocks.bQ, 1, this.g);
   }

   public void d() {
      this.g--;
      this.b.c(this.c, Blocks.bQ, 1, this.g);
   }

   public boolean a(EntityPlayer var1) {
      return this.b.s(this.c) != this ? false : !(var1.e(this.c.n() + 0.5, this.c.o() + 0.5, this.c.p() + 0.5) > 64.0);
   }
}
