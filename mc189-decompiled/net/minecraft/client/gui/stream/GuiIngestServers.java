package net.minecraft.client.gui.stream;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.stream.IngestServerTester;
import net.minecraft.util.EnumChatFormatting;
import tv.twitch.broadcast.IngestServer;

public class GuiIngestServers extends GuiScreen {
   private final GuiScreen a;
   private String f;
   private GuiIngestServers.ServerList g;

   public GuiIngestServers(GuiScreen var1) {
      this.a = var1;
   }

   @Override
   public void b() {
      this.f = I18n.a("options.stream.ingest.title");
      this.g = new GuiIngestServers.ServerList(this.j);
      if (!this.j.Y().w()) {
         this.j.Y().u();
      }

      this.n.add(new GuiButton(1, this.l / 2 - 155, this.m - 24 - 6, 150, 20, I18n.a("gui.done")));
      this.n.add(new GuiButton(2, this.l / 2 + 5, this.m - 24 - 6, 150, 20, I18n.a("options.stream.ingest.reset")));
   }

   @Override
   public void k() {
      super.k();
      this.g.p();
   }

   @Override
   public void m() {
      if (this.j.Y().w()) {
         this.j.Y().v().m();
      }
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.l) {
         if (var1.k == 1) {
            this.j.a(this.a);
         } else {
            this.j.t.R = "";
            this.j.t.b();
         }
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      this.g.a(var1, var2, var3);
      this.a(this.q, this.f, this.l / 2, 20, 16777215);
      super.a(var1, var2, var3);
   }

   class ServerList extends GuiSlot {
      public ServerList(Minecraft var2) {
         super(var2, GuiIngestServers.this.l, GuiIngestServers.this.m, 32, GuiIngestServers.this.m - 35, (int)(var2.k.a * 3.5));
         this.b(false);
      }

      @Override
      protected int b() {
         return this.a.Y().s().length;
      }

      @Override
      protected void a(int var1, boolean var2, int var3, int var4) {
         this.a.t.R = this.a.Y().s()[var1].serverUrl;
         this.a.t.b();
      }

      @Override
      protected boolean a(int var1) {
         return this.a.Y().s()[var1].serverUrl.equals(this.a.t.R);
      }

      @Override
      protected void a() {
      }

      @Override
      protected void a(int var1, int var2, int var3, int var4, int var5, int var6) {
         IngestServer var7 = this.a.Y().s()[var1];
         Object var8 = var7.serverUrl.replaceAll("\\{stream_key\\}", "");
         String var9 = (int)var7.bitrateKbps + " kbps";
         String var10 = null;
         IngestServerTester var11 = this.a.Y().v();
         if (var11 != null) {
            if (var7 == var11.c()) {
               var8 = EnumChatFormatting.k + var8;
               var9 = (int)(var11.i() * 100.0F) + "%";
            } else if (var1 < var11.d()) {
               if (var7.bitrateKbps == 0.0F) {
                  var9 = EnumChatFormatting.m + "Down!";
               }
            } else {
               var9 = EnumChatFormatting.q + "1234" + EnumChatFormatting.v + " kbps";
            }
         } else if (var7.bitrateKbps == 0.0F) {
            var9 = EnumChatFormatting.m + "Down!";
         }

         var2 -= 15;
         if (this.a(var1)) {
            var10 = EnumChatFormatting.j + "(Preferred)";
         } else if (var7.defaultServer) {
            var10 = EnumChatFormatting.k + "(Default)";
         }

         GuiIngestServers.this.c(GuiIngestServers.this.q, var7.serverName, var2 + 2, var3 + 5, 16777215);
         GuiIngestServers.this.c(GuiIngestServers.this.q, (String)var8, var2 + 2, var3 + GuiIngestServers.this.q.a + 5 + 3, 3158064);
         GuiIngestServers.this.c(GuiIngestServers.this.q, var9, this.d() - 5 - GuiIngestServers.this.q.a(var9), var3 + 5, 8421504);
         if (var10 != null) {
            GuiIngestServers.this.c(
               GuiIngestServers.this.q, var10, this.d() - 5 - GuiIngestServers.this.q.a(var10), var3 + 5 + 3 + GuiIngestServers.this.q.a, 8421504
            );
         }
      }

      @Override
      protected int d() {
         return super.d() + 15;
      }
   }
}
