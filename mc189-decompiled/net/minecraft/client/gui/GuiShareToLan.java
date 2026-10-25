package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldSettings;

public class GuiShareToLan extends GuiScreen {
   private final GuiScreen a;
   private GuiButton f;
   private GuiButton g;
   private String h = "survival";
   private boolean i;

   public GuiShareToLan(GuiScreen var1) {
      this.a = var1;
   }

   @Override
   public void b() {
      this.n.clear();
      this.n.add(new GuiButton(101, this.l / 2 - 155, this.m - 28, 150, 20, I18n.a("lanServer.start")));
      this.n.add(new GuiButton(102, this.l / 2 + 5, this.m - 28, 150, 20, I18n.a("gui.cancel")));
      this.n.add(this.g = new GuiButton(104, this.l / 2 - 155, 100, 150, 20, I18n.a("selectWorld.gameMode")));
      this.n.add(this.f = new GuiButton(103, this.l / 2 + 5, 100, 150, 20, I18n.a("selectWorld.allowCommands")));
      this.a();
   }

   private void a() {
      this.g.j = I18n.a("selectWorld.gameMode") + " " + I18n.a("selectWorld.gameMode." + this.h);
      this.f.j = I18n.a("selectWorld.allowCommands") + " ";
      if (this.i) {
         this.f.j = this.f.j + I18n.a("options.on");
      } else {
         this.f.j = this.f.j + I18n.a("options.off");
      }
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.k == 102) {
         this.j.a(this.a);
      } else if (var1.k == 104) {
         if (this.h.equals("spectator")) {
            this.h = "creative";
         } else if (this.h.equals("creative")) {
            this.h = "adventure";
         } else if (this.h.equals("adventure")) {
            this.h = "survival";
         } else {
            this.h = "spectator";
         }

         this.a();
      } else if (var1.k == 103) {
         this.i = !this.i;
         this.a();
      } else if (var1.k == 101) {
         this.j.a(null);
         String var2 = this.j.G().a(WorldSettings.GameType.a(this.h), this.i);
         Object var3;
         if (var2 != null) {
            var3 = new ChatComponentTranslation("commands.publish.started", var2);
         } else {
            var3 = new ChatComponentText("commands.publish.failed");
         }

         this.j.q.d().a((IChatComponent)var3);
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      this.a(this.q, I18n.a("lanServer.title"), this.l / 2, 50, 16777215);
      this.a(this.q, I18n.a("lanServer.otherPlayers"), this.l / 2, 82, 16777215);
      super.a(var1, var2, var3);
   }
}
