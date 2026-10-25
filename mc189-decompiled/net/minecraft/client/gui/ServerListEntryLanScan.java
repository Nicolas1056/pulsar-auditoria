package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class ServerListEntryLanScan implements GuiListExtended.IGuiListEntry {
   private final Minecraft a = Minecraft.A();

   @Override
   public void a(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      int var9 = var3 + var5 / 2 - this.a.k.a / 2;
      this.a.k.a(I18n.a("lanServer.scanning"), this.a.m.l / 2 - this.a.k.a(I18n.a("lanServer.scanning")) / 2, var9, 16777215);
      String var10;
      switch ((int)(Minecraft.J() / 300L % 4L)) {
         case 0:
         default:
            var10 = "O o o";
            break;
         case 1:
         case 3:
            var10 = "o O o";
            break;
         case 2:
            var10 = "o o O";
      }

      this.a.k.a(var10, this.a.m.l / 2 - this.a.k.a(var10) / 2, var9 + this.a.k.a, 8421504);
   }

   @Override
   public void a(int var1, int var2, int var3) {
   }

   @Override
   public boolean a(int var1, int var2, int var3, int var4, int var5, int var6) {
      return false;
   }

   @Override
   public void b(int var1, int var2, int var3, int var4, int var5, int var6) {
   }
}
