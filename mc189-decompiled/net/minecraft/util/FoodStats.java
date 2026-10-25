package net.minecraft.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.EnumDifficulty;

public class FoodStats {
   private int a = 20;
   private float b;
   private float c;
   private int d;
   private int e = 20;

   public FoodStats() {
      this.b = 5.0F;
   }

   public void a(int var1, float var2) {
      this.a = Math.min(var1 + this.a, 20);
      this.b = Math.min(this.b + var1 * var2 * 2.0F, (float)this.a);
   }

   public void a(ItemFood var1, ItemStack var2) {
      this.a(var1.h(var2), var1.i(var2));
   }

   public void a(EntityPlayer var1) {
      EnumDifficulty var2 = var1.o.aa();
      this.e = this.a;
      if (this.c > 4.0F) {
         this.c -= 4.0F;
         if (this.b > 0.0F) {
            this.b = Math.max(this.b - 1.0F, 0.0F);
         } else if (var2 != EnumDifficulty.a) {
            this.a = Math.max(this.a - 1, 0);
         }
      }

      if (var1.o.Q().b("naturalRegeneration") && this.a >= 18 && var1.cm()) {
         this.d++;
         if (this.d >= 80) {
            var1.h(1.0F);
            this.a(3.0F);
            this.d = 0;
         }
      } else if (this.a <= 0) {
         this.d++;
         if (this.d >= 80) {
            if (var1.bn() > 10.0F || var2 == EnumDifficulty.d || var1.bn() > 1.0F && var2 == EnumDifficulty.c) {
               var1.a(DamageSource.g, 1.0F);
            }

            this.d = 0;
         }
      } else {
         this.d = 0;
      }
   }

   public void a(NBTTagCompound var1) {
      if (var1.b("foodLevel", 99)) {
         this.a = var1.f("foodLevel");
         this.d = var1.f("foodTickTimer");
         this.b = var1.h("foodSaturationLevel");
         this.c = var1.h("foodExhaustionLevel");
      }
   }

   public void b(NBTTagCompound var1) {
      var1.a("foodLevel", this.a);
      var1.a("foodTickTimer", this.d);
      var1.a("foodSaturationLevel", this.b);
      var1.a("foodExhaustionLevel", this.c);
   }

   public int a() {
      return this.a;
   }

   public int b() {
      return this.e;
   }

   public boolean c() {
      return this.a < 20;
   }

   public void a(float var1) {
      this.c = Math.min(this.c + var1, 40.0F);
   }

   public float e() {
      return this.b;
   }

   public void a(int var1) {
      this.a = var1;
   }

   public void b(float var1) {
      this.b = var1;
   }
}
