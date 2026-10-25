package net.minecraft.client.gui;

import net.minecraft.util.IProgressUpdate;

public class GuiScreenWorking extends GuiScreen implements IProgressUpdate {
   private String a = "";
   private String f = "";
   private int g;
   private boolean h;

   @Override
   public void a(String var1) {
      this.b(var1);
   }

   @Override
   public void b(String var1) {
      this.a = var1;
      this.c("Working...");
   }

   @Override
   public void c(String var1) {
      this.f = var1;
      this.a(0);
   }

   @Override
   public void a(int var1) {
      this.g = var1;
   }

   @Override
   public void a() {
      this.h = true;
   }

   @Override
   public void a(int var1, int var2, float var3) {
      if (this.h) {
         if (!this.j.al()) {
            this.j.a(null);
         }
      } else {
         this.c();
         this.a(this.q, this.a, this.l / 2, 70, 16777215);
         this.a(this.q, this.f + " " + this.g + "%", this.l / 2, 90, 16777215);
         super.a(var1, var2, var3);
      }
   }
}
