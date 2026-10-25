package net.minecraft.client.gui;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.C00PacketKeepAlive;

public class GuiDownloadTerrain extends GuiScreen {
   private NetHandlerPlayClient a;
   private int f;

   public GuiDownloadTerrain(NetHandlerPlayClient var1) {
      this.a = var1;
   }

   @Override
   protected void a(char var1, int var2) {
   }

   @Override
   public void b() {
      this.n.clear();
   }

   @Override
   public void e() {
      this.f++;
      if (this.f % 20 == 0) {
         this.a.a(new C00PacketKeepAlive());
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c(0);
      this.a(this.q, I18n.a("multiplayer.downloadingTerrain"), this.l / 2, this.m / 2 - 50, 16777215);
      super.a(var1, var2, var3);
   }

   @Override
   public boolean d() {
      return false;
   }
}
