package net.minecraft.client.gui;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerDetector;
import net.minecraft.client.network.OldServerPinger;
import net.minecraft.client.resources.I18n;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiMultiplayer extends GuiScreen implements GuiYesNoCallback {
   private static final Logger a = LogManager.getLogger();
   private final OldServerPinger f = new OldServerPinger();
   private GuiScreen g;
   private ServerSelectionList h;
   private ServerList i;
   private GuiButton r;
   private GuiButton s;
   private GuiButton t;
   private boolean u;
   private boolean v;
   private boolean w;
   private boolean x;
   private String y;
   private ServerData z;
   private LanServerDetector.LanServerList A;
   private LanServerDetector.ThreadLanServerFind B;
   private boolean C;

   public GuiMultiplayer(GuiScreen var1) {
      this.g = var1;
   }

   @Override
   public void b() {
      Keyboard.enableRepeatEvents(true);
      this.n.clear();
      if (!this.C) {
         this.C = true;
         this.i = new ServerList(this.j);
         this.i.a();
         this.A = new LanServerDetector.LanServerList();

         try {
            this.B = new LanServerDetector.ThreadLanServerFind(this.A);
            this.B.start();
         } catch (Exception var2) {
            a.warn("Unable to start LAN server detection: " + var2.getMessage());
         }

         this.h = new ServerSelectionList(this, this.j, this.l, this.m, 32, this.m - 64, 36);
         this.h.a(this.i);
      } else {
         this.h.a(this.l, this.m, 32, this.m - 64);
      }

      this.a();
   }

   @Override
   public void k() {
      super.k();
      this.h.p();
   }

   public void a() {
      this.n.add(this.r = new GuiButton(7, this.l / 2 - 154, this.m - 28, 70, 20, I18n.a("selectServer.edit")));
      this.n.add(this.t = new GuiButton(2, this.l / 2 - 74, this.m - 28, 70, 20, I18n.a("selectServer.delete")));
      this.n.add(this.s = new GuiButton(1, this.l / 2 - 154, this.m - 52, 100, 20, I18n.a("selectServer.select")));
      this.n.add(new GuiButton(4, this.l / 2 - 50, this.m - 52, 100, 20, I18n.a("selectServer.direct")));
      this.n.add(new GuiButton(3, this.l / 2 + 4 + 50, this.m - 52, 100, 20, I18n.a("selectServer.add")));
      this.n.add(new GuiButton(8, this.l / 2 + 4, this.m - 28, 70, 20, I18n.a("selectServer.refresh")));
      this.n.add(new GuiButton(0, this.l / 2 + 4 + 76, this.m - 28, 75, 20, I18n.a("gui.cancel")));
      this.b(this.h.e());
   }

   @Override
   public void e() {
      super.e();
      if (this.A.a()) {
         List var1 = this.A.c();
         this.A.b();
         this.h.a(var1);
      }

      this.f.a();
   }

   @Override
   public void m() {
      Keyboard.enableRepeatEvents(false);
      if (this.B != null) {
         this.B.interrupt();
         this.B = null;
      }

      this.f.b();
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.l) {
         GuiListExtended.IGuiListEntry var2 = this.h.e() < 0 ? null : this.h.b(this.h.e());
         if (var1.k == 2 && var2 instanceof ServerListEntryNormal) {
            String var9 = ((ServerListEntryNormal)var2).a().a;
            if (var9 != null) {
               this.u = true;
               String var4 = I18n.a("selectServer.deleteQuestion");
               String var5 = "'" + var9 + "' " + I18n.a("selectServer.deleteWarning");
               String var6 = I18n.a("selectServer.deleteButton");
               String var7 = I18n.a("gui.cancel");
               GuiYesNo var8 = new GuiYesNo(this, var4, var5, var6, var7, this.h.e());
               this.j.a(var8);
            }
         } else if (var1.k == 1) {
            this.f();
         } else if (var1.k == 4) {
            this.x = true;
            this.j.a(new GuiScreenServerList(this, this.z = new ServerData(I18n.a("selectServer.defaultName"), "", false)));
         } else if (var1.k == 3) {
            this.v = true;
            this.j.a(new GuiScreenAddServer(this, this.z = new ServerData(I18n.a("selectServer.defaultName"), "", false)));
         } else if (var1.k == 7 && var2 instanceof ServerListEntryNormal) {
            this.w = true;
            ServerData var3 = ((ServerListEntryNormal)var2).a();
            this.z = new ServerData(var3.a, var3.b, false);
            this.z.a(var3);
            this.j.a(new GuiScreenAddServer(this, this.z));
         } else if (var1.k == 0) {
            this.j.a(this.g);
         } else if (var1.k == 8) {
            this.i();
         }
      }
   }

   private void i() {
      this.j.a(new GuiMultiplayer(this.g));
   }

   @Override
   public void a(boolean var1, int var2) {
      GuiListExtended.IGuiListEntry var3 = this.h.e() < 0 ? null : this.h.b(this.h.e());
      if (this.u) {
         this.u = false;
         if (var1 && var3 instanceof ServerListEntryNormal) {
            this.i.b(this.h.e());
            this.i.b();
            this.h.c(-1);
            this.h.a(this.i);
         }

         this.j.a(this);
      } else if (this.x) {
         this.x = false;
         if (var1) {
            this.a(this.z);
         } else {
            this.j.a(this);
         }
      } else if (this.v) {
         this.v = false;
         if (var1) {
            this.i.a(this.z);
            this.i.b();
            this.h.c(-1);
            this.h.a(this.i);
         }

         this.j.a(this);
      } else if (this.w) {
         this.w = false;
         if (var1 && var3 instanceof ServerListEntryNormal) {
            ServerData var4 = ((ServerListEntryNormal)var3).a();
            var4.a = this.z.a;
            var4.b = this.z.b;
            var4.a(this.z);
            this.i.b();
            this.h.a(this.i);
         }

         this.j.a(this);
      }
   }

   @Override
   protected void a(char var1, int var2) {
      int var3 = this.h.e();
      GuiListExtended.IGuiListEntry var4 = var3 < 0 ? null : this.h.b(var3);
      if (var2 == 63) {
         this.i();
      } else {
         if (var3 >= 0) {
            if (var2 == 200) {
               if (r()) {
                  if (var3 > 0 && var4 instanceof ServerListEntryNormal) {
                     this.i.a(var3, var3 - 1);
                     this.b(this.h.e() - 1);
                     this.h.h(-this.h.r());
                     this.h.a(this.i);
                  }
               } else if (var3 > 0) {
                  this.b(this.h.e() - 1);
                  this.h.h(-this.h.r());
                  if (this.h.b(this.h.e()) instanceof ServerListEntryLanScan) {
                     if (this.h.e() > 0) {
                        this.b(this.h.b() - 1);
                        this.h.h(-this.h.r());
                     } else {
                        this.b(-1);
                     }
                  }
               } else {
                  this.b(-1);
               }
            } else if (var2 == 208) {
               if (r()) {
                  if (var3 < this.i.c() - 1) {
                     this.i.a(var3, var3 + 1);
                     this.b(var3 + 1);
                     this.h.h(this.h.r());
                     this.h.a(this.i);
                  }
               } else if (var3 < this.h.b()) {
                  this.b(this.h.e() + 1);
                  this.h.h(this.h.r());
                  if (this.h.b(this.h.e()) instanceof ServerListEntryLanScan) {
                     if (this.h.e() < this.h.b() - 1) {
                        this.b(this.h.b() + 1);
                        this.h.h(this.h.r());
                     } else {
                        this.b(-1);
                     }
                  }
               } else {
                  this.b(-1);
               }
            } else if (var2 != 28 && var2 != 156) {
               super.a(var1, var2);
            } else {
               this.a(this.n.get(2));
            }
         } else {
            super.a(var1, var2);
         }
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.y = null;
      this.c();
      this.h.a(var1, var2, var3);
      this.a(this.q, I18n.a("multiplayer.title"), this.l / 2, 20, 16777215);
      super.a(var1, var2, var3);
      if (this.y != null) {
         this.a(Lists.newArrayList(Splitter.on("\n").split(this.y)), var1, var2);
      }
   }

   public void f() {
      GuiListExtended.IGuiListEntry var1 = this.h.e() < 0 ? null : this.h.b(this.h.e());
      if (var1 instanceof ServerListEntryNormal) {
         this.a(((ServerListEntryNormal)var1).a());
      } else if (var1 instanceof ServerListEntryLanDetected) {
         LanServerDetector.LanServer var2 = ((ServerListEntryLanDetected)var1).a();
         this.a(new ServerData(var2.a(), var2.b(), true));
      }
   }

   private void a(ServerData var1) {
      this.j.a(new GuiConnecting(this, this.j, var1));
   }

   public void b(int var1) {
      this.h.c(var1);
      GuiListExtended.IGuiListEntry var2 = var1 < 0 ? null : this.h.b(var1);
      this.s.l = false;
      this.r.l = false;
      this.t.l = false;
      if (var2 != null && !(var2 instanceof ServerListEntryLanScan)) {
         this.s.l = true;
         if (var2 instanceof ServerListEntryNormal) {
            this.r.l = true;
            this.t.l = true;
         }
      }
   }

   public OldServerPinger g() {
      return this.f;
   }

   public void a(String var1) {
      this.y = var1;
   }

   @Override
   protected void a(int var1, int var2, int var3) {
      super.a(var1, var2, var3);
      this.h.b(var1, var2, var3);
   }

   @Override
   protected void b(int var1, int var2, int var3) {
      super.b(var1, var2, var3);
      this.h.c(var1, var2, var3);
   }

   public ServerList h() {
      return this.i;
   }

   public boolean a(ServerListEntryNormal var1, int var2) {
      return var2 > 0;
   }

   public boolean b(ServerListEntryNormal var1, int var2) {
      return var2 < this.i.c() - 1;
   }

   public void a(ServerListEntryNormal var1, int var2, boolean var3) {
      int var4 = var3 ? 0 : var2 - 1;
      this.i.a(var2, var4);
      if (this.h.e() == var2) {
         this.b(var4);
      }

      this.h.a(this.i);
   }

   public void b(ServerListEntryNormal var1, int var2, boolean var3) {
      int var4 = var3 ? this.i.c() - 1 : var2 + 1;
      this.i.a(var2, var4);
      if (this.h.e() == var2) {
         this.b(var4);
      }

      this.h.a(this.i);
   }
}
