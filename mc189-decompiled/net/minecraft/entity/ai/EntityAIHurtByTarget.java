package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;

public class EntityAIHurtByTarget extends EntityAITarget {
   private boolean a;
   private int b;
   private final Class[] c;

   public EntityAIHurtByTarget(EntityCreature var1, boolean var2, Class... var3) {
      super(var1, false);
      this.a = var2;
      this.c = var3;
      this.a(1);
   }

   @Override
   public boolean a() {
      int var1 = this.e.be();
      return var1 != this.b && this.a(this.e.bd(), false);
   }

   @Override
   public void c() {
      this.e.d(this.e.bd());
      this.b = this.e.be();
      if (this.a) {
         double var1 = this.f();

         for (EntityCreature var5 : this.e
            .o
            .a(this.e.getClass(), new AxisAlignedBB(this.e.s, this.e.t, this.e.u, this.e.s + 1.0, this.e.t + 1.0, this.e.u + 1.0).b(var1, 10.0, var1))) {
            if (this.e != var5 && var5.u() == null && !var5.c(this.e.bd())) {
               boolean var6 = false;

               for (Class var10 : this.c) {
                  if (var5.getClass() == var10) {
                     var6 = true;
                     break;
                  }
               }

               if (!var6) {
                  this.a(var5, this.e.bd());
               }
            }
         }
      }

      super.c();
   }

   protected void a(EntityCreature var1, EntityLivingBase var2) {
      var1.d(var2);
   }
}
