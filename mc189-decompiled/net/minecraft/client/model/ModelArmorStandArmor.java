package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;

public class ModelArmorStandArmor extends ModelBiped {
   public ModelArmorStandArmor() {
      this(0.0F);
   }

   public ModelArmorStandArmor(float var1) {
      this(var1, 64, 32);
   }

   protected ModelArmorStandArmor(float var1, int var2, int var3) {
      super(var1, 0.0F, var2, var3);
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      if (var7 instanceof EntityArmorStand) {
         EntityArmorStand var8 = (EntityArmorStand)var7;
         this.e.f = (float) (Math.PI / 180.0) * var8.t().b();
         this.e.g = (float) (Math.PI / 180.0) * var8.t().c();
         this.e.h = (float) (Math.PI / 180.0) * var8.t().d();
         this.e.a(0.0F, 1.0F, 0.0F);
         this.g.f = (float) (Math.PI / 180.0) * var8.u().b();
         this.g.g = (float) (Math.PI / 180.0) * var8.u().c();
         this.g.h = (float) (Math.PI / 180.0) * var8.u().d();
         this.i.f = (float) (Math.PI / 180.0) * var8.v().b();
         this.i.g = (float) (Math.PI / 180.0) * var8.v().c();
         this.i.h = (float) (Math.PI / 180.0) * var8.v().d();
         this.h.f = (float) (Math.PI / 180.0) * var8.w().b();
         this.h.g = (float) (Math.PI / 180.0) * var8.w().c();
         this.h.h = (float) (Math.PI / 180.0) * var8.w().d();
         this.k.f = (float) (Math.PI / 180.0) * var8.x().b();
         this.k.g = (float) (Math.PI / 180.0) * var8.x().c();
         this.k.h = (float) (Math.PI / 180.0) * var8.x().d();
         this.k.a(1.9F, 11.0F, 0.0F);
         this.j.f = (float) (Math.PI / 180.0) * var8.y().b();
         this.j.g = (float) (Math.PI / 180.0) * var8.y().c();
         this.j.h = (float) (Math.PI / 180.0) * var8.y().d();
         this.j.a(-1.9F, 11.0F, 0.0F);
         a(this.e, this.f);
      }
   }
}
