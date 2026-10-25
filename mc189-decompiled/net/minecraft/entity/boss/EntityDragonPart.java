package net.minecraft.entity.boss;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

public class EntityDragonPart extends Entity {
   public final IEntityMultiPart a;
   public final String b;

   public EntityDragonPart(IEntityMultiPart var1, String var2, float var3, float var4) {
      super(var1.a());
      this.a(var3, var4);
      this.a = var1;
      this.b = var2;
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

   @Override
   public boolean ad() {
      return true;
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      return this.b(var1) ? false : this.a.a(this, var1, var2);
   }

   @Override
   public boolean k(Entity var1) {
      return this == var1 || this.a == var1;
   }
}
