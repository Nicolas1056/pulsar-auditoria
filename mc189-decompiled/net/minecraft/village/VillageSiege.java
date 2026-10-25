package net.minecraft.village;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;

public class VillageSiege {
   private World a;
   private boolean b;
   private int c = -1;
   private int d;
   private int e;
   private Village f;
   private int g;
   private int h;
   private int i;

   public VillageSiege(World var1) {
      this.a = var1;
   }

   public void a() {
      if (this.a.w()) {
         this.c = 0;
      } else if (this.c != 2) {
         if (this.c == 0) {
            float var1 = this.a.c(0.0F);
            if (var1 < 0.5 || var1 > 0.501) {
               return;
            }

            this.c = this.a.s.nextInt(10) == 0 ? 1 : 2;
            this.b = false;
            if (this.c == 2) {
               return;
            }
         }

         if (this.c != -1) {
            if (!this.b) {
               if (!this.b()) {
                  return;
               }

               this.b = true;
            }

            if (this.e > 0) {
               this.e--;
            } else {
               this.e = 2;
               if (this.d > 0) {
                  this.c();
                  this.d--;
               } else {
                  this.c = 2;
               }
            }
         }
      }
   }

   private boolean b() {
      for (EntityPlayer var3 : this.a.j) {
         if (!var3.v()) {
            this.f = this.a.ae().a(new BlockPos(var3), 1);
            if (this.f != null && this.f.c() >= 10 && this.f.d() >= 20 && this.f.e() >= 20) {
               BlockPos var4 = this.f.a();
               float var5 = this.f.b();
               boolean var6 = false;

               for (int var7 = 0; var7 < 10; var7++) {
                  float var8 = this.a.s.nextFloat() * (float) Math.PI * 2.0F;
                  this.g = var4.n() + (int)(MathHelper.b(var8) * var5 * 0.9);
                  this.h = var4.o();
                  this.i = var4.p() + (int)(MathHelper.a(var8) * var5 * 0.9);
                  var6 = false;

                  for (Village var10 : this.a.ae().b()) {
                     if (var10 != this.f && var10.a(new BlockPos(this.g, this.h, this.i))) {
                        var6 = true;
                        break;
                     }
                  }

                  if (!var6) {
                     break;
                  }
               }

               if (var6) {
                  return false;
               }

               Vec3 var11 = this.a(new BlockPos(this.g, this.h, this.i));
               if (var11 != null) {
                  this.e = 0;
                  this.d = 20;
                  return true;
               }
            }
         }
      }

      return false;
   }

   private boolean c() {
      Vec3 var1 = this.a(new BlockPos(this.g, this.h, this.i));
      if (var1 == null) {
         return false;
      } else {
         EntityZombie var2;
         try {
            var2 = new EntityZombie(this.a);
            var2.a(this.a.E(new BlockPos(var2)), null);
            var2.m(false);
         } catch (Exception var4) {
            var4.printStackTrace();
            return false;
         }

         var2.b(var1.a, var1.b, var1.c, this.a.s.nextFloat() * 360.0F, 0.0F);
         this.a.d(var2);
         BlockPos var3 = this.f.a();
         var2.a(var3, this.f.b());
         return true;
      }
   }

   private Vec3 a(BlockPos var1) {
      for (int var2 = 0; var2 < 10; var2++) {
         BlockPos var3 = var1.a(this.a.s.nextInt(16) - 8, this.a.s.nextInt(6) - 3, this.a.s.nextInt(16) - 8);
         if (this.f.a(var3) && SpawnerAnimals.a(EntityLiving.SpawnPlacementType.a, this.a, var3)) {
            return new Vec3(var3.n(), var3.o(), var3.p());
         }
      }

      return null;
   }
}
