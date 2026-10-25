package net.minecraft.world;

import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.border.IBorderListener;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.ISaveHandler;

public class WorldServerMulti extends WorldServer {
   private WorldServer a;

   public WorldServerMulti(MinecraftServer var1, ISaveHandler var2, int var3, WorldServer var4, Profiler var5) {
      super(var1, var2, new DerivedWorldInfo(var4.P()), var3, var5);
      this.a = var4;
      var4.af().a(new IBorderListener() {
         @Override
         public void a(WorldBorder var1, double var2x) {
            WorldServerMulti.this.af().a(var2x);
         }

         @Override
         public void a(WorldBorder var1, double var2x, double var4x, long var6) {
            WorldServerMulti.this.af().a(var2x, var4x, var6);
         }

         @Override
         public void a(WorldBorder var1, double var2x, double var4x) {
            WorldServerMulti.this.af().c(var2x, var4x);
         }

         @Override
         public void a(WorldBorder var1, int var2x) {
            WorldServerMulti.this.af().b(var2x);
         }

         @Override
         public void b(WorldBorder var1, int var2x) {
            WorldServerMulti.this.af().c(var2x);
         }

         @Override
         public void b(WorldBorder var1, double var2x) {
            WorldServerMulti.this.af().c(var2x);
         }

         @Override
         public void c(WorldBorder var1, double var2x) {
            WorldServerMulti.this.af().b(var2x);
         }
      });
   }

   @Override
   protected void a() {
   }

   @Override
   public World b() {
      this.z = this.a.T();
      this.C = this.a.Z();
      String var1 = VillageCollection.a(this.t);
      VillageCollection var2 = (VillageCollection)this.z.a(VillageCollection.class, var1);
      if (var2 == null) {
         this.A = new VillageCollection(this);
         this.z.a(var1, this.A);
      } else {
         this.A = var2;
         this.A.a(this);
      }

      return this;
   }
}
