package net.minecraft.entity;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.attributes.ServersideAttributeMap;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S0APacketUseBed;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S0EPacketSpawnObject;
import net.minecraft.network.play.server.S0FPacketSpawnMob;
import net.minecraft.network.play.server.S10PacketSpawnPainting;
import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S19PacketEntityHeadLook;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.network.play.server.S1CPacketEntityMetadata;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S20PacketEntityProperties;
import net.minecraft.network.play.server.S49PacketUpdateEntityNBT;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.storage.MapData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityTrackerEntry {
   private static final Logger p = LogManager.getLogger();
   public Entity a;
   public int b;
   public int c;
   public int d;
   public int e;
   public int f;
   public int g;
   public int h;
   public int i;
   public double j;
   public double k;
   public double l;
   public int m;
   private double q;
   private double r;
   private double s;
   private boolean t;
   private boolean u;
   private int v;
   private Entity w;
   private boolean x;
   private boolean y;
   public boolean n;
   public Set<EntityPlayerMP> o = Sets.newHashSet();

   public EntityTrackerEntry(Entity var1, int var2, int var3, boolean var4) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.u = var4;
      this.d = MathHelper.c(var1.s * 32.0);
      this.e = MathHelper.c(var1.t * 32.0);
      this.f = MathHelper.c(var1.u * 32.0);
      this.g = MathHelper.d(var1.y * 256.0F / 360.0F);
      this.h = MathHelper.d(var1.z * 256.0F / 360.0F);
      this.i = MathHelper.d(var1.aC() * 256.0F / 360.0F);
      this.y = var1.C;
   }

   @Override
   public boolean equals(Object var1) {
      return var1 instanceof EntityTrackerEntry ? ((EntityTrackerEntry)var1).a.F() == this.a.F() : false;
   }

   @Override
   public int hashCode() {
      return this.a.F();
   }

   public void a(List<EntityPlayer> var1) {
      this.n = false;
      if (!this.t || this.a.e(this.q, this.r, this.s) > 16.0) {
         this.q = this.a.s;
         this.r = this.a.t;
         this.s = this.a.u;
         this.t = true;
         this.n = true;
         this.b(var1);
      }

      if (this.w != this.a.m || this.a.m != null && this.m % 60 == 0) {
         this.w = this.a.m;
         this.a(new S1BPacketEntityAttach(0, this.a, this.a.m));
      }

      if (this.a instanceof EntityItemFrame && this.m % 10 == 0) {
         EntityItemFrame var2 = (EntityItemFrame)this.a;
         ItemStack var3 = var2.o();
         if (var3 != null && var3.b() instanceof ItemMap) {
            MapData var4 = Items.bd.a(var3, this.a.o);

            for (EntityPlayer var6 : var1) {
               EntityPlayerMP var7 = (EntityPlayerMP)var6;
               var4.a(var7, var3);
               Packet var8 = Items.bd.c(var3, this.a.o, var7);
               if (var8 != null) {
                  var7.a.a(var8);
               }
            }
         }

         this.b();
      }

      if (this.m % this.c == 0 || this.a.ai || this.a.H().a()) {
         if (this.a.m == null) {
            this.v++;
            int var24 = MathHelper.c(this.a.s * 32.0);
            int var27 = MathHelper.c(this.a.t * 32.0);
            int var29 = MathHelper.c(this.a.u * 32.0);
            int var30 = MathHelper.d(this.a.y * 256.0F / 360.0F);
            int var31 = MathHelper.d(this.a.z * 256.0F / 360.0F);
            int var32 = var24 - this.d;
            int var33 = var27 - this.e;
            int var9 = var29 - this.f;
            Object var10 = null;
            boolean var11 = Math.abs(var32) >= 4 || Math.abs(var33) >= 4 || Math.abs(var9) >= 4 || this.m % 60 == 0;
            boolean var12 = Math.abs(var30 - this.g) >= 4 || Math.abs(var31 - this.h) >= 4;
            if (this.m > 0 || this.a instanceof EntityArrow) {
               if (var32 >= -128 && var32 < 128 && var33 >= -128 && var33 < 128 && var9 >= -128 && var9 < 128 && this.v <= 400 && !this.x && this.y == this.a.C
                  )
                {
                  if ((!var11 || !var12) && !(this.a instanceof EntityArrow)) {
                     if (var11) {
                        var10 = new S14PacketEntity.S15PacketEntityRelMove(this.a.F(), (byte)var32, (byte)var33, (byte)var9, this.a.C);
                     } else if (var12) {
                        var10 = new S14PacketEntity.S16PacketEntityLook(this.a.F(), (byte)var30, (byte)var31, this.a.C);
                     }
                  } else {
                     var10 = new S14PacketEntity.S17PacketEntityLookMove(this.a.F(), (byte)var32, (byte)var33, (byte)var9, (byte)var30, (byte)var31, this.a.C);
                  }
               } else {
                  this.y = this.a.C;
                  this.v = 0;
                  var10 = new S18PacketEntityTeleport(this.a.F(), var24, var27, var29, (byte)var30, (byte)var31, this.a.C);
               }
            }

            if (this.u) {
               double var13 = this.a.v - this.j;
               double var15 = this.a.w - this.k;
               double var17 = this.a.x - this.l;
               double var19 = 0.02;
               double var21 = var13 * var13 + var15 * var15 + var17 * var17;
               if (var21 > var19 * var19 || var21 > 0.0 && this.a.v == 0.0 && this.a.w == 0.0 && this.a.x == 0.0) {
                  this.j = this.a.v;
                  this.k = this.a.w;
                  this.l = this.a.x;
                  this.a(new S12PacketEntityVelocity(this.a.F(), this.j, this.k, this.l));
               }
            }

            if (var10 != null) {
               this.a((Packet)var10);
            }

            this.b();
            if (var11) {
               this.d = var24;
               this.e = var27;
               this.f = var29;
            }

            if (var12) {
               this.g = var30;
               this.h = var31;
            }

            this.x = false;
         } else {
            int var23 = MathHelper.d(this.a.y * 256.0F / 360.0F);
            int var26 = MathHelper.d(this.a.z * 256.0F / 360.0F);
            boolean var28 = Math.abs(var23 - this.g) >= 4 || Math.abs(var26 - this.h) >= 4;
            if (var28) {
               this.a(new S14PacketEntity.S16PacketEntityLook(this.a.F(), (byte)var23, (byte)var26, this.a.C));
               this.g = var23;
               this.h = var26;
            }

            this.d = MathHelper.c(this.a.s * 32.0);
            this.e = MathHelper.c(this.a.t * 32.0);
            this.f = MathHelper.c(this.a.u * 32.0);
            this.b();
            this.x = true;
         }

         int var25 = MathHelper.d(this.a.aC() * 256.0F / 360.0F);
         if (Math.abs(var25 - this.i) >= 4) {
            this.a(new S19PacketEntityHeadLook(this.a, (byte)var25));
            this.i = var25;
         }

         this.a.ai = false;
      }

      this.m++;
      if (this.a.G) {
         this.b(new S12PacketEntityVelocity(this.a));
         this.a.G = false;
      }
   }

   private void b() {
      DataWatcher var1 = this.a.H();
      if (var1.a()) {
         this.b(new S1CPacketEntityMetadata(this.a.F(), var1, false));
      }

      if (this.a instanceof EntityLivingBase) {
         ServersideAttributeMap var2 = (ServersideAttributeMap)((EntityLivingBase)this.a).by();
         Set var3 = var2.b();
         if (!var3.isEmpty()) {
            this.b(new S20PacketEntityProperties(this.a.F(), var3));
         }

         var3.clear();
      }
   }

   public void a(Packet var1) {
      for (EntityPlayerMP var3 : this.o) {
         var3.a.a(var1);
      }
   }

   public void b(Packet var1) {
      this.a(var1);
      if (this.a instanceof EntityPlayerMP) {
         ((EntityPlayerMP)this.a).a.a(var1);
      }
   }

   public void a() {
      for (EntityPlayerMP var2 : this.o) {
         var2.d(this.a);
      }
   }

   public void a(EntityPlayerMP var1) {
      if (this.o.contains(var1)) {
         var1.d(this.a);
         this.o.remove(var1);
      }
   }

   public void b(EntityPlayerMP var1) {
      if (var1 != this.a) {
         if (this.c(var1)) {
            if (!this.o.contains(var1) && (this.e(var1) || this.a.n)) {
               this.o.add(var1);
               Packet var2 = this.c();
               var1.a.a(var2);
               if (!this.a.H().d()) {
                  var1.a.a(new S1CPacketEntityMetadata(this.a.F(), this.a.H(), true));
               }

               NBTTagCompound var3 = this.a.aV();
               if (var3 != null) {
                  var1.a.a(new S49PacketUpdateEntityNBT(this.a.F(), var3));
               }

               if (this.a instanceof EntityLivingBase) {
                  ServersideAttributeMap var4 = (ServersideAttributeMap)((EntityLivingBase)this.a).by();
                  Collection var5 = var4.c();
                  if (!var5.isEmpty()) {
                     var1.a.a(new S20PacketEntityProperties(this.a.F(), var5));
                  }
               }

               this.j = this.a.v;
               this.k = this.a.w;
               this.l = this.a.x;
               if (this.u && !(var2 instanceof S0FPacketSpawnMob)) {
                  var1.a.a(new S12PacketEntityVelocity(this.a.F(), this.a.v, this.a.w, this.a.x));
               }

               if (this.a.m != null) {
                  var1.a.a(new S1BPacketEntityAttach(0, this.a, this.a.m));
               }

               if (this.a instanceof EntityLiving && ((EntityLiving)this.a).cd() != null) {
                  var1.a.a(new S1BPacketEntityAttach(1, this.a, ((EntityLiving)this.a).cd()));
               }

               if (this.a instanceof EntityLivingBase) {
                  for (int var7 = 0; var7 < 5; var7++) {
                     ItemStack var10 = ((EntityLivingBase)this.a).p(var7);
                     if (var10 != null) {
                        var1.a.a(new S04PacketEntityEquipment(this.a.F(), var7, var10));
                     }
                  }
               }

               if (this.a instanceof EntityPlayer) {
                  EntityPlayer var8 = (EntityPlayer)this.a;
                  if (var8.bJ()) {
                     var1.a.a(new S0APacketUseBed(var8, new BlockPos(this.a)));
                  }
               }

               if (this.a instanceof EntityLivingBase) {
                  EntityLivingBase var9 = (EntityLivingBase)this.a;

                  for (PotionEffect var6 : var9.bl()) {
                     var1.a.a(new S1DPacketEntityEffect(this.a.F(), var6));
                  }
               }
            }
         } else if (this.o.contains(var1)) {
            this.o.remove(var1);
            var1.d(this.a);
         }
      }
   }

   public boolean c(EntityPlayerMP var1) {
      double var2 = var1.s - this.d / 32;
      double var4 = var1.u - this.f / 32;
      return var2 >= -this.b && var2 <= this.b && var4 >= -this.b && var4 <= this.b && this.a.a(var1);
   }

   private boolean e(EntityPlayerMP var1) {
      return var1.u().t().a(var1, this.a.ae, this.a.ag);
   }

   public void b(List<EntityPlayer> var1) {
      for (int var2 = 0; var2 < var1.size(); var2++) {
         this.b((EntityPlayerMP)var1.get(var2));
      }
   }

   private Packet c() {
      if (this.a.I) {
         p.warn("Fetching addPacket for removed entity");
      }

      if (this.a instanceof EntityItem) {
         return new S0EPacketSpawnObject(this.a, 2, 1);
      } else if (this.a instanceof EntityPlayerMP) {
         return new S0CPacketSpawnPlayer((EntityPlayer)this.a);
      } else if (this.a instanceof EntityMinecart) {
         EntityMinecart var9 = (EntityMinecart)this.a;
         return new S0EPacketSpawnObject(this.a, 10, var9.s().a());
      } else if (this.a instanceof EntityBoat) {
         return new S0EPacketSpawnObject(this.a, 1);
      } else if (this.a instanceof IAnimals) {
         this.i = MathHelper.d(this.a.aC() * 256.0F / 360.0F);
         return new S0FPacketSpawnMob((EntityLivingBase)this.a);
      } else if (this.a instanceof EntityFishHook) {
         EntityPlayer var8 = ((EntityFishHook)this.a).b;
         return new S0EPacketSpawnObject(this.a, 90, var8 != null ? var8.F() : this.a.F());
      } else if (this.a instanceof EntityArrow) {
         Entity var7 = ((EntityArrow)this.a).c;
         return new S0EPacketSpawnObject(this.a, 60, var7 != null ? var7.F() : this.a.F());
      } else if (this.a instanceof EntitySnowball) {
         return new S0EPacketSpawnObject(this.a, 61);
      } else if (this.a instanceof EntityPotion) {
         return new S0EPacketSpawnObject(this.a, 73, ((EntityPotion)this.a).o());
      } else if (this.a instanceof EntityExpBottle) {
         return new S0EPacketSpawnObject(this.a, 75);
      } else if (this.a instanceof EntityEnderPearl) {
         return new S0EPacketSpawnObject(this.a, 65);
      } else if (this.a instanceof EntityEnderEye) {
         return new S0EPacketSpawnObject(this.a, 72);
      } else if (this.a instanceof EntityFireworkRocket) {
         return new S0EPacketSpawnObject(this.a, 76);
      } else if (this.a instanceof EntityFireball) {
         EntityFireball var6 = (EntityFireball)this.a;
         S0EPacketSpawnObject var11 = null;
         byte var14 = 63;
         if (this.a instanceof EntitySmallFireball) {
            var14 = 64;
         } else if (this.a instanceof EntityWitherSkull) {
            var14 = 66;
         }

         if (var6.a != null) {
            var11 = new S0EPacketSpawnObject(this.a, var14, ((EntityFireball)this.a).a.F());
         } else {
            var11 = new S0EPacketSpawnObject(this.a, var14, 0);
         }

         var11.d((int)(var6.b * 8000.0));
         var11.e((int)(var6.c * 8000.0));
         var11.f((int)(var6.d * 8000.0));
         return var11;
      } else if (this.a instanceof EntityEgg) {
         return new S0EPacketSpawnObject(this.a, 62);
      } else if (this.a instanceof EntityTNTPrimed) {
         return new S0EPacketSpawnObject(this.a, 50);
      } else if (this.a instanceof EntityEnderCrystal) {
         return new S0EPacketSpawnObject(this.a, 51);
      } else if (this.a instanceof EntityFallingBlock) {
         EntityFallingBlock var5 = (EntityFallingBlock)this.a;
         return new S0EPacketSpawnObject(this.a, 70, Block.f(var5.l()));
      } else if (this.a instanceof EntityArmorStand) {
         return new S0EPacketSpawnObject(this.a, 78);
      } else if (this.a instanceof EntityPainting) {
         return new S10PacketSpawnPainting((EntityPainting)this.a);
      } else if (this.a instanceof EntityItemFrame) {
         EntityItemFrame var4 = (EntityItemFrame)this.a;
         S0EPacketSpawnObject var10 = new S0EPacketSpawnObject(this.a, 71, var4.b.b());
         BlockPos var13 = var4.n();
         var10.a(MathHelper.d((float)(var13.n() * 32)));
         var10.b(MathHelper.d((float)(var13.o() * 32)));
         var10.c(MathHelper.d((float)(var13.p() * 32)));
         return var10;
      } else if (this.a instanceof EntityLeashKnot) {
         EntityLeashKnot var1 = (EntityLeashKnot)this.a;
         S0EPacketSpawnObject var2 = new S0EPacketSpawnObject(this.a, 77);
         BlockPos var3 = var1.n();
         var2.a(MathHelper.d((float)(var3.n() * 32)));
         var2.b(MathHelper.d((float)(var3.o() * 32)));
         var2.c(MathHelper.d((float)(var3.p() * 32)));
         return var2;
      } else if (this.a instanceof EntityXPOrb) {
         return new S11PacketSpawnExperienceOrb((EntityXPOrb)this.a);
      } else {
         throw new IllegalArgumentException("Don't know how to add " + this.a.getClass() + "!");
      }
   }

   public void d(EntityPlayerMP var1) {
      if (this.o.contains(var1)) {
         this.o.remove(var1);
         var1.d(this.a);
      }
   }
}
