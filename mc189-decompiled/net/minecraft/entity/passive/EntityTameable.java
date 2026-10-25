package net.minecraft.entity.passive;

import java.util.UUID;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public abstract class EntityTameable extends EntityAnimal implements IEntityOwnable {
   protected EntityAISit bm = new EntityAISit(this);

   public EntityTameable(World var1) {
      super(var1);
      this.cm();
   }

   @Override
   protected void h() {
      super.h();
      this.ac.a(16, Byte.valueOf((byte)0));
      this.ac.a(17, "");
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      if (this.b() == null) {
         var1.a("OwnerUUID", "");
      } else {
         var1.a("OwnerUUID", this.b());
      }

      var1.a("Sitting", this.cn());
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      String var2 = "";
      if (var1.b("OwnerUUID", 8)) {
         var2 = var1.j("OwnerUUID");
      } else {
         String var3 = var1.j("Owner");
         var2 = PreYggdrasilConverter.a(var3);
      }

      if (var2.length() > 0) {
         this.b(var2);
         this.m(true);
      }

      this.bm.a(var1.n("Sitting"));
      this.n(var1.n("Sitting"));
   }

   protected void l(boolean var1) {
      EnumParticleTypes var2 = EnumParticleTypes.I;
      if (!var1) {
         var2 = EnumParticleTypes.l;
      }

      for (int var3 = 0; var3 < 7; var3++) {
         double var4 = this.V.nextGaussian() * 0.02;
         double var6 = this.V.nextGaussian() * 0.02;
         double var8 = this.V.nextGaussian() * 0.02;
         this.o
            .a(
               var2,
               this.s + this.V.nextFloat() * this.J * 2.0F - this.J,
               this.t + 0.5 + this.V.nextFloat() * this.K,
               this.u + this.V.nextFloat() * this.J * 2.0F - this.J,
               var4,
               var6,
               var8
            );
      }
   }

   @Override
   public void a(byte var1) {
      if (var1 == 7) {
         this.l(true);
      } else if (var1 == 6) {
         this.l(false);
      } else {
         super.a(var1);
      }
   }

   public boolean cl() {
      return (this.ac.a(16) & 4) != 0;
   }

   public void m(boolean var1) {
      byte var2 = this.ac.a(16);
      if (var1) {
         this.ac.b(16, (byte)(var2 | 4));
      } else {
         this.ac.b(16, (byte)(var2 & -5));
      }

      this.cm();
   }

   protected void cm() {
   }

   public boolean cn() {
      return (this.ac.a(16) & 1) != 0;
   }

   public void n(boolean var1) {
      byte var2 = this.ac.a(16);
      if (var1) {
         this.ac.b(16, (byte)(var2 | 1));
      } else {
         this.ac.b(16, (byte)(var2 & -2));
      }
   }

   @Override
   public String b() {
      return this.ac.e(17);
   }

   public void b(String var1) {
      this.ac.b(17, var1);
   }

   public EntityLivingBase co() {
      try {
         UUID var1 = UUID.fromString(this.b());
         return var1 == null ? null : this.o.b(var1);
      } catch (IllegalArgumentException var2) {
         return null;
      }
   }

   public boolean e(EntityLivingBase var1) {
      return var1 == this.co();
   }

   public EntityAISit cp() {
      return this.bm;
   }

   public boolean a(EntityLivingBase var1, EntityLivingBase var2) {
      return true;
   }

   @Override
   public Team bO() {
      if (this.cl()) {
         EntityLivingBase var1 = this.co();
         if (var1 != null) {
            return var1.bO();
         }
      }

      return super.bO();
   }

   @Override
   public boolean c(EntityLivingBase var1) {
      if (this.cl()) {
         EntityLivingBase var2 = this.co();
         if (var1 == var2) {
            return true;
         }

         if (var2 != null) {
            return var2.c(var1);
         }
      }

      return super.c(var1);
   }

   @Override
   public void a(DamageSource var1) {
      if (!this.o.D && this.o.Q().b("showDeathMessages") && this.l_() && this.co() instanceof EntityPlayerMP) {
         ((EntityPlayerMP)this.co()).a(this.bs().b());
      }

      super.a(var1);
   }
}
