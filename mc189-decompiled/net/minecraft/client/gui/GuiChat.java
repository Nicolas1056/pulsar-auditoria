package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GuiChat extends GuiScreen {
   private static final Logger f = LogManager.getLogger();
   private String g = "";
   private int h = -1;
   private boolean i;
   private boolean r;
   private int s;
   private List<String> t = Lists.newArrayList();
   protected GuiTextField a;
   private String u = "";

   public GuiChat() {
   }

   public GuiChat(String var1) {
      this.u = var1;
   }

   @Override
   public void b() {
      Keyboard.enableRepeatEvents(true);
      this.h = this.j.q.d().c().size();
      this.a = new GuiTextField(0, this.q, 4, this.m - 12, this.l - 4, 12);
      this.a.f(100);
      this.a.a(false);
      this.a.b(true);
      this.a.a(this.u);
      this.a.d(false);
   }

   @Override
   public void m() {
      Keyboard.enableRepeatEvents(false);
      this.j.q.d().d();
   }

   @Override
   public void e() {
      this.a.a();
   }

   @Override
   protected void a(char var1, int var2) {
      this.r = false;
      if (var2 == 15) {
         this.a();
      } else {
         this.i = false;
      }

      if (var2 == 1) {
         this.j.a(null);
      } else if (var2 == 28 || var2 == 156) {
         String var3 = this.a.b().trim();
         if (var3.length() > 0) {
            this.f(var3);
         }

         this.j.a(null);
      } else if (var2 == 200) {
         this.b(-1);
      } else if (var2 == 208) {
         this.b(1);
      } else if (var2 == 201) {
         this.j.q.d().b(this.j.q.d().i() - 1);
      } else if (var2 == 209) {
         this.j.q.d().b(-this.j.q.d().i() + 1);
      } else {
         this.a.a(var1, var2);
      }
   }

   @Override
   public void k() {
      super.k();
      int var1 = Mouse.getEventDWheel();
      if (var1 != 0) {
         if (var1 > 1) {
            var1 = 1;
         }

         if (var1 < -1) {
            var1 = -1;
         }

         if (!r()) {
            var1 *= 7;
         }

         this.j.q.d().b(var1);
      }
   }

   @Override
   protected void a(int var1, int var2, int var3) {
      if (var3 == 0) {
         IChatComponent var4 = this.j.q.d().a(Mouse.getX(), Mouse.getY());
         if (this.a(var4)) {
            return;
         }
      }

      this.a.a(var1, var2, var3);
      super.a(var1, var2, var3);
   }

   @Override
   protected void a(String var1, boolean var2) {
      if (var2) {
         this.a.a(var1);
      } else {
         this.a.b(var1);
      }
   }

   public void a() {
      if (this.i) {
         this.a.b(this.a.a(-1, this.a.i(), false) - this.a.i());
         if (this.s >= this.t.size()) {
            this.s = 0;
         }
      } else {
         int var1 = this.a.a(-1, this.a.i(), false);
         this.t.clear();
         this.s = 0;
         String var2 = this.a.b().substring(var1).toLowerCase();
         String var3 = this.a.b().substring(0, this.a.i());
         this.a(var3, var2);
         if (this.t.isEmpty()) {
            return;
         }

         this.i = true;
         this.a.b(var1 - this.a.i());
      }

      if (this.t.size() > 1) {
         StringBuilder var4 = new StringBuilder();

         for (String var6 : this.t) {
            if (var4.length() > 0) {
               var4.append(", ");
            }

            var4.append(var6);
         }

         this.j.q.d().a(new ChatComponentText(var4.toString()), 1);
      }

      this.a.b(this.t.get(this.s++));
   }

   private void a(String var1, String var2) {
      if (var1.length() >= 1) {
         BlockPos var3 = null;
         if (this.j.s != null && this.j.s.a == MovingObjectPosition.MovingObjectType.b) {
            var3 = this.j.s.a();
         }

         this.j.h.a.a(new C14PacketTabComplete(var1, var3));
         this.r = true;
      }
   }

   public void b(int var1) {
      int var2 = this.h + var1;
      int var3 = this.j.q.d().c().size();
      var2 = MathHelper.a(var2, 0, var3);
      if (var2 != this.h) {
         if (var2 == var3) {
            this.h = var3;
            this.a.a(this.g);
         } else {
            if (this.h == var3) {
               this.g = this.a.b();
            }

            this.a.a(this.j.q.d().c().get(var2));
            this.h = var2;
         }
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      a(2, this.m - 14, this.l - 2, this.m - 2, Integer.MIN_VALUE);
      this.a.g();
      IChatComponent var4 = this.j.q.d().a(Mouse.getX(), Mouse.getY());
      if (var4 != null && var4.b().i() != null) {
         this.a(var4, var1, var2);
      }

      super.a(var1, var2, var3);
   }

   public void a(String[] var1) {
      if (this.r) {
         this.i = false;
         this.t.clear();

         for (String var5 : var1) {
            if (var5.length() > 0) {
               this.t.add(var5);
            }
         }

         String var6 = this.a.b().substring(this.a.a(-1, this.a.i(), false));
         String var7 = StringUtils.getCommonPrefix(var1);
         if (var7.length() > 0 && !var6.equalsIgnoreCase(var7)) {
            this.a.b(this.a.a(-1, this.a.i(), false) - this.a.i());
            this.a.b(var7);
         } else if (this.t.size() > 0) {
            this.i = true;
            this.a();
         }
      }
   }

   @Override
   public boolean d() {
      return false;
   }
}
