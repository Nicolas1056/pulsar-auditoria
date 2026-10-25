package net.minecraft.client.gui;

import io.netty.buffer.Unpooled;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiCommandBlock extends GuiScreen {
   private static final Logger a = LogManager.getLogger();
   private GuiTextField f;
   private GuiTextField g;
   private final CommandBlockLogic h;
   private GuiButton i;
   private GuiButton r;
   private GuiButton s;
   private boolean t;

   public GuiCommandBlock(CommandBlockLogic var1) {
      this.h = var1;
   }

   @Override
   public void e() {
      this.f.a();
   }

   @Override
   public void b() {
      Keyboard.enableRepeatEvents(true);
      this.n.clear();
      this.n.add(this.i = new GuiButton(0, this.l / 2 - 4 - 150, this.m / 4 + 120 + 12, 150, 20, I18n.a("gui.done")));
      this.n.add(this.r = new GuiButton(1, this.l / 2 + 4, this.m / 4 + 120 + 12, 150, 20, I18n.a("gui.cancel")));
      this.n.add(this.s = new GuiButton(4, this.l / 2 + 150 - 20, 150, 20, 20, "O"));
      this.f = new GuiTextField(2, this.q, this.l / 2 - 150, 50, 300, 20);
      this.f.f(32767);
      this.f.b(true);
      this.f.a(this.h.l());
      this.g = new GuiTextField(3, this.q, this.l / 2 - 150, 150, 276, 20);
      this.g.f(32767);
      this.g.c(false);
      this.g.a("-");
      this.t = this.h.m();
      this.a();
      this.i.l = this.f.b().trim().length() > 0;
   }

   @Override
   public void m() {
      Keyboard.enableRepeatEvents(false);
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.l) {
         if (var1.k == 1) {
            this.h.a(this.t);
            this.j.a(null);
         } else if (var1.k == 0) {
            PacketBuffer var2 = new PacketBuffer(Unpooled.buffer());
            var2.writeByte(this.h.i());
            this.h.a(var2);
            var2.a(this.f.b());
            var2.writeBoolean(this.h.m());
            this.j.u().a(new C17PacketCustomPayload("MC|AdvCdm", var2));
            if (!this.h.m()) {
               this.h.b(null);
            }

            this.j.a(null);
         } else if (var1.k == 4) {
            this.h.a(!this.h.m());
            this.a();
         }
      }
   }

   @Override
   protected void a(char var1, int var2) {
      this.f.a(var1, var2);
      this.g.a(var1, var2);
      this.i.l = this.f.b().trim().length() > 0;
      if (var2 == 28 || var2 == 156) {
         this.a(this.i);
      } else if (var2 == 1) {
         this.a(this.r);
      }
   }

   @Override
   protected void a(int var1, int var2, int var3) {
      super.a(var1, var2, var3);
      this.f.a(var1, var2, var3);
      this.g.a(var1, var2, var3);
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      this.a(this.q, I18n.a("advMode.setCommand"), this.l / 2, 20, 16777215);
      this.c(this.q, I18n.a("advMode.command"), this.l / 2 - 150, 37, 10526880);
      this.f.g();
      int var4 = 75;
      int var5 = 0;
      this.c(this.q, I18n.a("advMode.nearestPlayer"), this.l / 2 - 150, var4 + var5++ * this.q.a, 10526880);
      this.c(this.q, I18n.a("advMode.randomPlayer"), this.l / 2 - 150, var4 + var5++ * this.q.a, 10526880);
      this.c(this.q, I18n.a("advMode.allPlayers"), this.l / 2 - 150, var4 + var5++ * this.q.a, 10526880);
      this.c(this.q, I18n.a("advMode.allEntities"), this.l / 2 - 150, var4 + var5++ * this.q.a, 10526880);
      this.c(this.q, "", this.l / 2 - 150, var4 + var5++ * this.q.a, 10526880);
      if (this.g.b().length() > 0) {
         var4 += var5 * this.q.a + 16;
         this.c(this.q, I18n.a("advMode.previousOutput"), this.l / 2 - 150, var4, 10526880);
         this.g.g();
      }

      super.a(var1, var2, var3);
   }

   private void a() {
      if (this.h.m()) {
         this.s.j = "O";
         if (this.h.k() != null) {
            this.g.a(this.h.k().c());
         }
      } else {
         this.s.j = "X";
         this.g.a("-");
      }
   }
}
