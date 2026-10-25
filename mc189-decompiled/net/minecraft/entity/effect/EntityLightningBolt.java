package net.minecraft.entity.effect;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityLightningBolt extends EntityWeatherEffect {
   private int b;
   public long a;
   private int c;

   public EntityLightningBolt(World var1, double var2, double var4, double var6) {
      super(var1);
      this.b(var2, var4, var6, 0.0F, 0.0F);
      this.b = 2;
      this.a = this.V.nextLong();
      this.c = this.V.nextInt(3) + 1;
      BlockPos var8 = new BlockPos(this);
      if (!var1.D && var1.Q().b("doFireTick") && (var1.aa() == EnumDifficulty.c || var1.aa() == EnumDifficulty.d) && var1.a(var8, 10)) {
         if (var1.p(var8).c().t() == Material.a && Blocks.ab.d(var1, var8)) {
            var1.a(var8, Blocks.ab.Q());
         }

         for (int var9 = 0; var9 < 4; var9++) {
            BlockPos var10 = var8.a(this.V.nextInt(3) - 1, this.V.nextInt(3) - 1, this.V.nextInt(3) - 1);
            if (var1.p(var10).c().t() == Material.a && Blocks.ab.d(var1, var10)) {
               var1.a(var10, Blocks.ab.Q());
            }
         }
      }
   }

   @Override
   public void t_() {
      super.t_();
      if (this.b == 2) {
         this.o.a(this.s, this.t, this.u, "ambient.weather.thunder", 10000.0F, 0.8F + this.V.nextFloat() * 0.2F);
         this.o.a(this.s, this.t, this.u, "random.explode", 2.0F, 0.5F + this.V.nextFloat() * 0.2F);
      }

      this.b--;
      if (this.b < 0) {
         if (this.c == 0) {
            this.J();
         } else if (this.b < -this.V.nextInt(10)) {
            this.c--;
            this.b = 1;
            this.a = this.V.nextLong();
            BlockPos var1 = new BlockPos(this);
            if (!this.o.D && this.o.Q().b("doFireTick") && this.o.a(var1, 10) && this.o.p(var1).c().t() == Material.a && Blocks.ab.d(this.o, var1)) {
               this.o.a(var1, Blocks.ab.Q());
            }
         }
      }

      if (this.b >= 0) {
         if (this.o.D) {
            this.o.d(2);
         } else {
            double var6 = 3.0;
            List var3 = this.o.b(this, new AxisAlignedBB(this.s - var6, this.t - var6, this.u - var6, this.s + var6, this.t + 6.0 + var6, this.u + var6));

            for (int var4 = 0; var4 < var3.size(); var4++) {
               Entity var5 = (Entity)var3.get(var4);
               var5.a(this);
            }
         }
      }
   }

   @Override
   protected void h() {
   }

   @Override
   protected void a(NBTTagCompound var1) {
   }

   @Override
   protected void b(NBTTagCompound var1) {
   }
}
