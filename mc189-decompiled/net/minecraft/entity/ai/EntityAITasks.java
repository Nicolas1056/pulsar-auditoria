package net.minecraft.entity.ai;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.minecraft.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityAITasks {
   private static final Logger a = LogManager.getLogger();
   private List<EntityAITasks.EntityAITaskEntry> b = Lists.newArrayList();
   private List<EntityAITasks.EntityAITaskEntry> c = Lists.newArrayList();
   private final Profiler d;
   private int e;
   private int f = 3;

   public EntityAITasks(Profiler var1) {
      this.d = var1;
   }

   public void a(int var1, EntityAIBase var2) {
      this.b.add(new EntityAITasks.EntityAITaskEntry(var1, var2));
   }

   public void a(EntityAIBase var1) {
      Iterator var2 = this.b.iterator();

      while (var2.hasNext()) {
         EntityAITasks.EntityAITaskEntry var3 = (EntityAITasks.EntityAITaskEntry)var2.next();
         EntityAIBase var4 = var3.a;
         if (var4 == var1) {
            if (this.c.contains(var3)) {
               var4.d();
               this.c.remove(var3);
            }

            var2.remove();
         }
      }
   }

   public void a() {
      this.d.a("goalSetup");
      if (this.e++ % this.f == 0) {
         for (EntityAITasks.EntityAITaskEntry var2 : this.b) {
            boolean var3 = this.c.contains(var2);
            if (var3) {
               if (this.b(var2) && this.a(var2)) {
                  continue;
               }

               var2.a.d();
               this.c.remove(var2);
            }

            if (this.b(var2) && var2.a.a()) {
               var2.a.c();
               this.c.add(var2);
            }
         }
      } else {
         Iterator var4 = this.c.iterator();

         while (var4.hasNext()) {
            EntityAITasks.EntityAITaskEntry var6 = (EntityAITasks.EntityAITaskEntry)var4.next();
            if (!this.a(var6)) {
               var6.a.d();
               var4.remove();
            }
         }
      }

      this.d.b();
      this.d.a("goalTick");

      for (EntityAITasks.EntityAITaskEntry var7 : this.c) {
         var7.a.e();
      }

      this.d.b();
   }

   private boolean a(EntityAITasks.EntityAITaskEntry var1) {
      return var1.a.b();
   }

   private boolean b(EntityAITasks.EntityAITaskEntry var1) {
      for (EntityAITasks.EntityAITaskEntry var3 : this.b) {
         if (var3 != var1) {
            if (var1.b >= var3.b) {
               if (!this.a(var1, var3) && this.c.contains(var3)) {
                  return false;
               }
            } else if (!var3.a.i() && this.c.contains(var3)) {
               return false;
            }
         }
      }

      return true;
   }

   private boolean a(EntityAITasks.EntityAITaskEntry var1, EntityAITasks.EntityAITaskEntry var2) {
      return (var1.a.j() & var2.a.j()) == 0;
   }

   class EntityAITaskEntry {
      public EntityAIBase a;
      public int b;

      public EntityAITaskEntry(int var2, EntityAIBase var3) {
         this.b = var2;
         this.a = var3;
      }
   }
}
