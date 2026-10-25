package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.LanServerDetector;
import net.minecraft.client.resources.I18n;

public class ServerListEntryLanDetected implements GuiListExtended.IGuiListEntry {
   private final GuiMultiplayer c;
   protected final Minecraft a;
   protected final LanServerDetector.LanServer b;
   private long d = 0L;

   protected ServerListEntryLanDetected(GuiMultiplayer var1, LanServerDetector.LanServer var2) {
      this.c = var1;
      this.b = var2;
      this.a = Minecraft.A();
   }

   @Override
   public void a(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      this.a.k.a(I18n.a("lanServer.title"), var2 + 32 + 3, var3 + 1, 16777215);
      this.a.k.a(this.b.a(), var2 + 32 + 3, var3 + 12, 8421504);
      if (this.a.t.x) {
         this.a.k.a(I18n.a("selectServer.hiddenAddress"), var2 + 32 + 3, var3 + 12 + 11, 3158064);
      } else {
         this.a.k.a(this.b.b(), var2 + 32 + 3, var3 + 12 + 11, 3158064);
      }
   }

   @Override
   public boolean a(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.c.b(var1);
      if (Minecraft.J() - this.d < 250L) {
         this.c.f();
      }

      this.d = Minecraft.J();
      return false;
   }

   @Override
   public void a(int var1, int var2, int var3) {
   }

   @Override
   public void b(int var1, int var2, int var3, int var4, int var5, int var6) {
   }

   public LanServerDetector.LanServer a() {
      return this.b;
   }
}
