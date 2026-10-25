package net.minecraft.entity.player;

import net.minecraft.nbt.NBTTagCompound;

public class PlayerCapabilities {
   public boolean a;
   public boolean b;
   public boolean c;
   public boolean d;
   public boolean e = true;
   private float f = 0.05F;
   private float g = 0.1F;

   public void a(NBTTagCompound var1) {
      NBTTagCompound var2 = new NBTTagCompound();
      var2.a("invulnerable", this.a);
      var2.a("flying", this.b);
      var2.a("mayfly", this.c);
      var2.a("instabuild", this.d);
      var2.a("mayBuild", this.e);
      var2.a("flySpeed", this.f);
      var2.a("walkSpeed", this.g);
      var1.a("abilities", var2);
   }

   public void b(NBTTagCompound var1) {
      if (var1.b("abilities", 10)) {
         NBTTagCompound var2 = var1.m("abilities");
         this.a = var2.n("invulnerable");
         this.b = var2.n("flying");
         this.c = var2.n("mayfly");
         this.d = var2.n("instabuild");
         if (var2.b("flySpeed", 99)) {
            this.f = var2.h("flySpeed");
            this.g = var2.h("walkSpeed");
         }

         if (var2.b("mayBuild", 1)) {
            this.e = var2.n("mayBuild");
         }
      }
   }

   public float a() {
      return this.f;
   }

   public void a(float var1) {
      this.f = var1;
   }

   public float b() {
      return this.g;
   }

   public void b(float var1) {
      this.g = var1;
   }
}
