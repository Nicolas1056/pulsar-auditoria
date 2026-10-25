package net.minecraft.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.network.Packet;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityTracker {
   private static final Logger a = LogManager.getLogger();
   private final WorldServer b;
   private Set<EntityTrackerEntry> c = Sets.newHashSet();
   private IntHashMap<EntityTrackerEntry> d = new IntHashMap<>();
   private int e;

   public EntityTracker(WorldServer var1) {
      this.b = var1;
      this.e = var1.r().ap().d();
   }

   public void a(Entity var1) {
      if (var1 instanceof EntityPlayerMP) {
         this.a(var1, 512, 2);
         EntityPlayerMP var2 = (EntityPlayerMP)var1;

         for (EntityTrackerEntry var4 : this.c) {
            if (var4.a != var2) {
               var4.b(var2);
            }
         }
      } else if (var1 instanceof EntityFishHook) {
         this.a(var1, 64, 5, true);
      } else if (var1 instanceof EntityArrow) {
         this.a(var1, 64, 20, false);
      } else if (var1 instanceof EntitySmallFireball) {
         this.a(var1, 64, 10, false);
      } else if (var1 instanceof EntityFireball) {
         this.a(var1, 64, 10, false);
      } else if (var1 instanceof EntitySnowball) {
         this.a(var1, 64, 10, true);
      } else if (var1 instanceof EntityEnderPearl) {
         this.a(var1, 64, 10, true);
      } else if (var1 instanceof EntityEnderEye) {
         this.a(var1, 64, 4, true);
      } else if (var1 instanceof EntityEgg) {
         this.a(var1, 64, 10, true);
      } else if (var1 instanceof EntityPotion) {
         this.a(var1, 64, 10, true);
      } else if (var1 instanceof EntityExpBottle) {
         this.a(var1, 64, 10, true);
      } else if (var1 instanceof EntityFireworkRocket) {
         this.a(var1, 64, 10, true);
      } else if (var1 instanceof EntityItem) {
         this.a(var1, 64, 20, true);
      } else if (var1 instanceof EntityMinecart) {
         this.a(var1, 80, 3, true);
      } else if (var1 instanceof EntityBoat) {
         this.a(var1, 80, 3, true);
      } else if (var1 instanceof EntitySquid) {
         this.a(var1, 64, 3, true);
      } else if (var1 instanceof EntityWither) {
         this.a(var1, 80, 3, false);
      } else if (var1 instanceof EntityBat) {
         this.a(var1, 80, 3, false);
      } else if (var1 instanceof EntityDragon) {
         this.a(var1, 160, 3, true);
      } else if (var1 instanceof IAnimals) {
         this.a(var1, 80, 3, true);
      } else if (var1 instanceof EntityTNTPrimed) {
         this.a(var1, 160, 10, true);
      } else if (var1 instanceof EntityFallingBlock) {
         this.a(var1, 160, 20, true);
      } else if (var1 instanceof EntityHanging) {
         this.a(var1, 160, Integer.MAX_VALUE, false);
      } else if (var1 instanceof EntityArmorStand) {
         this.a(var1, 160, 3, true);
      } else if (var1 instanceof EntityXPOrb) {
         this.a(var1, 160, 20, true);
      } else if (var1 instanceof EntityEnderCrystal) {
         this.a(var1, 256, Integer.MAX_VALUE, false);
      }
   }

   public void a(Entity var1, int var2, int var3) {
      this.a(var1, var2, var3, false);
   }

   public void a(Entity var1, int var2, final int var3, boolean var4) {
      if (var2 > this.e) {
         var2 = this.e;
      }

      try {
         if (this.d.b(var1.F())) {
            throw new IllegalStateException("Entity is already tracked!");
         }

         EntityTrackerEntry var5 = new EntityTrackerEntry(var1, var2, var3, var4);
         this.c.add(var5);
         this.d.a(var1.F(), var5);
         var5.b(this.b.j);
      } catch (Throwable var11) {
         CrashReport var6 = CrashReport.a(var11, "Adding entity to track");
         CrashReportCategory var7 = var6.a("Entity To Track");
         var7.a("Tracking range", var2 + " blocks");
         var7.a("Update interval", new Callable<String>() {
            public String a() throws Exception {
               String var1x = "Once per " + var3 + " ticks";
               if (var3 == Integer.MAX_VALUE) {
                  var1x = "Maximum (" + var1x + ")";
               }

               return var1x;
            }
         });
         var1.a(var7);
         CrashReportCategory var8 = var6.a("Entity That Is Already Tracked");
         this.d.a(var1.F()).a.a(var8);

         try {
            throw new ReportedException(var6);
         } catch (ReportedException var10) {
            a.error("\"Silently\" catching entity tracking error.", var10);
         }
      }
   }

   public void b(Entity var1) {
      if (var1 instanceof EntityPlayerMP) {
         EntityPlayerMP var2 = (EntityPlayerMP)var1;

         for (EntityTrackerEntry var4 : this.c) {
            var4.a(var2);
         }
      }

      EntityTrackerEntry var5 = this.d.d(var1.F());
      if (var5 != null) {
         this.c.remove(var5);
         var5.a();
      }
   }

   public void a() {
      ArrayList var1 = Lists.newArrayList();

      for (EntityTrackerEntry var3 : this.c) {
         var3.a(this.b.j);
         if (var3.n && var3.a instanceof EntityPlayerMP) {
            var1.add((EntityPlayerMP)var3.a);
         }
      }

      for (int var6 = 0; var6 < var1.size(); var6++) {
         EntityPlayerMP var7 = (EntityPlayerMP)var1.get(var6);

         for (EntityTrackerEntry var5 : this.c) {
            if (var5.a != var7) {
               var5.b(var7);
            }
         }
      }
   }

   public void a(EntityPlayerMP var1) {
      for (EntityTrackerEntry var3 : this.c) {
         if (var3.a == var1) {
            var3.b(this.b.j);
         } else {
            var3.b(var1);
         }
      }
   }

   public void a(Entity var1, Packet var2) {
      EntityTrackerEntry var3 = this.d.a(var1.F());
      if (var3 != null) {
         var3.a(var2);
      }
   }

   public void b(Entity var1, Packet var2) {
      EntityTrackerEntry var3 = this.d.a(var1.F());
      if (var3 != null) {
         var3.b(var2);
      }
   }

   public void b(EntityPlayerMP var1) {
      for (EntityTrackerEntry var3 : this.c) {
         var3.d(var1);
      }
   }

   public void a(EntityPlayerMP var1, Chunk var2) {
      for (EntityTrackerEntry var4 : this.c) {
         if (var4.a != var1 && var4.a.ae == var2.a && var4.a.ag == var2.b) {
            var4.b(var1);
         }
      }
   }
}
