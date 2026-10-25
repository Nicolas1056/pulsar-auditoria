package net.minecraft.client.gui;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.stream.GuiTwitchUserMode;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityList;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import tv.twitch.chat.ChatUserInfo;

public abstract class GuiScreen extends Gui implements GuiYesNoCallback {
   private static final Logger a = LogManager.getLogger();
   private static final Set<String> f = Sets.newHashSet(new String[]{"http", "https"});
   private static final Splitter g = Splitter.on('\n');
   protected Minecraft j;
   protected RenderItem k;
   public int l;
   public int m;
   protected List<GuiButton> n = Lists.newArrayList();
   protected List<GuiLabel> o = Lists.newArrayList();
   public boolean p;
   protected FontRenderer q;
   private GuiButton h;
   private int i;
   private long r;
   private int s;
   private URI t;

   public void a(int var1, int var2, float var3) {
      for (int var4 = 0; var4 < this.n.size(); var4++) {
         this.n.get(var4).a(this.j, var1, var2);
      }

      for (int var5 = 0; var5 < this.o.size(); var5++) {
         this.o.get(var5).a(this.j, var1, var2);
      }
   }

   protected void a(char var1, int var2) {
      if (var2 == 1) {
         this.j.a(null);
         if (this.j.m == null) {
            this.j.n();
         }
      }
   }

   public static String o() {
      try {
         Transferable var0 = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
         if (var0 != null && var0.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return (String)var0.getTransferData(DataFlavor.stringFlavor);
         }
      } catch (Exception var1) {
      }

      return "";
   }

   public static void e(String var0) {
      if (!StringUtils.isEmpty(var0)) {
         try {
            StringSelection var1 = new StringSelection(var0);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(var1, null);
         } catch (Exception var2) {
         }
      }
   }

   protected void a(ItemStack var1, int var2, int var3) {
      List var4 = var1.a(this.j.h, this.j.t.y);

      for (int var5 = 0; var5 < var4.size(); var5++) {
         if (var5 == 0) {
            var4.set(var5, var1.u().e + (String)var4.get(var5));
         } else {
            var4.set(var5, EnumChatFormatting.h + (String)var4.get(var5));
         }
      }

      this.a(var4, var2, var3);
   }

   protected void a(String var1, int var2, int var3) {
      this.a(Arrays.asList(var1), var2, var3);
   }

   protected void a(List<String> var1, int var2, int var3) {
      if (!var1.isEmpty()) {
         GlStateManager.C();
         RenderHelper.a();
         GlStateManager.f();
         GlStateManager.i();
         int var4 = 0;

         for (String var6 : var1) {
            int var7 = this.q.a(var6);
            if (var7 > var4) {
               var4 = var7;
            }
         }

         int var14 = var2 + 12;
         int var15 = var3 - 12;
         int var8 = 8;
         if (var1.size() > 1) {
            var8 += 2 + (var1.size() - 1) * 10;
         }

         if (var14 + var4 > this.l) {
            var14 -= 28 + var4;
         }

         if (var15 + var8 + 6 > this.m) {
            var15 = this.m - var8 - 6;
         }

         this.e = 300.0F;
         this.k.a = 300.0F;
         int var9 = -267386864;
         this.a(var14 - 3, var15 - 4, var14 + var4 + 3, var15 - 3, var9, var9);
         this.a(var14 - 3, var15 + var8 + 3, var14 + var4 + 3, var15 + var8 + 4, var9, var9);
         this.a(var14 - 3, var15 - 3, var14 + var4 + 3, var15 + var8 + 3, var9, var9);
         this.a(var14 - 4, var15 - 3, var14 - 3, var15 + var8 + 3, var9, var9);
         this.a(var14 + var4 + 3, var15 - 3, var14 + var4 + 4, var15 + var8 + 3, var9, var9);
         int var10 = 1347420415;
         int var11 = (var10 & 16711422) >> 1 | var10 & 0xFF000000;
         this.a(var14 - 3, var15 - 3 + 1, var14 - 3 + 1, var15 + var8 + 3 - 1, var10, var11);
         this.a(var14 + var4 + 2, var15 - 3 + 1, var14 + var4 + 3, var15 + var8 + 3 - 1, var10, var11);
         this.a(var14 - 3, var15 - 3, var14 + var4 + 3, var15 - 3 + 1, var10, var10);
         this.a(var14 - 3, var15 + var8 + 2, var14 + var4 + 3, var15 + var8 + 3, var11, var11);

         for (int var12 = 0; var12 < var1.size(); var12++) {
            String var13 = (String)var1.get(var12);
            this.q.a(var13, (float)var14, (float)var15, -1);
            if (var12 == 0) {
               var15 += 2;
            }

            var15 += 10;
         }

         this.e = 0.0F;
         this.k.a = 0.0F;
         GlStateManager.e();
         GlStateManager.j();
         RenderHelper.b();
         GlStateManager.B();
      }
   }

   protected void a(IChatComponent var1, int var2, int var3) {
      if (var1 != null && var1.b().i() != null) {
         HoverEvent var4 = var1.b().i();
         if (var4.a() == HoverEvent.Action.c) {
            ItemStack var5 = null;

            try {
               NBTTagCompound var6 = JsonToNBT.a(var4.b().c());
               if (var6 instanceof NBTTagCompound) {
                  var5 = ItemStack.a(var6);
               }
            } catch (NBTException var11) {
            }

            if (var5 != null) {
               this.a(var5, var2, var3);
            } else {
               this.a(EnumChatFormatting.m + "Invalid Item!", var2, var3);
            }
         } else if (var4.a() == HoverEvent.Action.d) {
            if (this.j.t.y) {
               try {
                  NBTTagCompound var12 = JsonToNBT.a(var4.b().c());
                  if (var12 instanceof NBTTagCompound) {
                     ArrayList var14 = Lists.newArrayList();
                     NBTTagCompound var7 = var12;
                     var14.add(var7.j("name"));
                     if (var7.b("type", 8)) {
                        String var8 = var7.j("type");
                        var14.add("Type: " + var8 + " (" + EntityList.a(var8) + ")");
                     }

                     var14.add(var7.j("id"));
                     this.a(var14, var2, var3);
                  } else {
                     this.a(EnumChatFormatting.m + "Invalid Entity!", var2, var3);
                  }
               } catch (NBTException var10) {
                  this.a(EnumChatFormatting.m + "Invalid Entity!", var2, var3);
               }
            }
         } else if (var4.a() == HoverEvent.Action.a) {
            this.a(g.splitToList(var4.b().d()), var2, var3);
         } else if (var4.a() == HoverEvent.Action.b) {
            StatBase var13 = StatList.a(var4.b().c());
            if (var13 != null) {
               IChatComponent var15 = var13.e();
               ChatComponentTranslation var16 = new ChatComponentTranslation("stats.tooltip.type." + (var13.d() ? "achievement" : "statistic"));
               var16.b().b(true);
               String var17 = var13 instanceof Achievement ? ((Achievement)var13).f() : null;
               ArrayList var9 = Lists.newArrayList(new String[]{var15.d(), var16.d()});
               if (var17 != null) {
                  var9.addAll(this.q.c(var17, 150));
               }

               this.a(var9, var2, var3);
            } else {
               this.a(EnumChatFormatting.m + "Invalid statistic/achievement!", var2, var3);
            }
         }

         GlStateManager.f();
      }
   }

   protected void a(String var1, boolean var2) {
   }

   protected boolean a(IChatComponent var1) {
      if (var1 == null) {
         return false;
      } else {
         ClickEvent var2 = var1.b().h();
         if (r()) {
            if (var1.b().j() != null) {
               this.a(var1.b().j(), false);
            }
         } else if (var2 != null) {
            if (var2.a() == ClickEvent.Action.a) {
               if (!this.j.t.o) {
                  return false;
               }

               try {
                  URI var3 = new URI(var2.b());
                  String var4 = var3.getScheme();
                  if (var4 == null) {
                     throw new URISyntaxException(var2.b(), "Missing protocol");
                  }

                  if (!f.contains(var4.toLowerCase())) {
                     throw new URISyntaxException(var2.b(), "Unsupported protocol: " + var4.toLowerCase());
                  }

                  if (this.j.t.p) {
                     this.t = var3;
                     this.j.a(new GuiConfirmOpenLink(this, var2.b(), 31102009, false));
                  } else {
                     this.a(var3);
                  }
               } catch (URISyntaxException var5) {
                  a.error("Can't open url for " + var2, var5);
               }
            } else if (var2.a() == ClickEvent.Action.b) {
               URI var6 = new File(var2.b()).toURI();
               this.a(var6);
            } else if (var2.a() == ClickEvent.Action.e) {
               this.a(var2.b(), true);
            } else if (var2.a() == ClickEvent.Action.c) {
               this.b(var2.b(), false);
            } else if (var2.a() == ClickEvent.Action.d) {
               ChatUserInfo var7 = this.j.Y().e(var2.b());
               if (var7 != null) {
                  this.j.a(new GuiTwitchUserMode(this.j.Y(), var7));
               } else {
                  a.error("Tried to handle twitch user but couldn't find them!");
               }
            } else {
               a.error("Don't know how to handle " + var2);
            }

            return true;
         }

         return false;
      }
   }

   public void f(String var1) {
      this.b(var1, true);
   }

   public void b(String var1, boolean var2) {
      if (var2) {
         this.j.q.d().a(var1);
      }

      this.j.h.e(var1);
   }

   protected void a(int var1, int var2, int var3) {
      if (var3 == 0) {
         for (int var4 = 0; var4 < this.n.size(); var4++) {
            GuiButton var5 = this.n.get(var4);
            if (var5.c(this.j, var1, var2)) {
               this.h = var5;
               var5.a(this.j.W());
               this.a(var5);
            }
         }
      }
   }

   protected void b(int var1, int var2, int var3) {
      if (this.h != null && var3 == 0) {
         this.h.a(var1, var2);
         this.h = null;
      }
   }

   protected void a(int var1, int var2, int var3, long var4) {
   }

   protected void a(GuiButton var1) {
   }

   public void a(Minecraft var1, int var2, int var3) {
      this.j = var1;
      this.k = var1.ag();
      this.q = var1.k;
      this.l = var2;
      this.m = var3;
      this.n.clear();
      this.b();
   }

   public void a(int var1, int var2) {
      this.l = var1;
      this.m = var2;
   }

   public void b() {
   }

   public void p() {
      if (Mouse.isCreated()) {
         while (Mouse.next()) {
            this.k();
         }
      }

      if (Keyboard.isCreated()) {
         while (Keyboard.next()) {
            this.l();
         }
      }
   }

   public void k() {
      int var1 = Mouse.getEventX() * this.l / this.j.d;
      int var2 = this.m - Mouse.getEventY() * this.m / this.j.e - 1;
      int var3 = Mouse.getEventButton();
      if (Mouse.getEventButtonState()) {
         if (this.j.t.A && this.s++ > 0) {
            return;
         }

         this.i = var3;
         this.r = Minecraft.J();
         this.a(var1, var2, this.i);
      } else if (var3 != -1) {
         if (this.j.t.A && --this.s > 0) {
            return;
         }

         this.i = -1;
         this.b(var1, var2, var3);
      } else if (this.i != -1 && this.r > 0L) {
         long var4 = Minecraft.J() - this.r;
         this.a(var1, var2, this.i, var4);
      }
   }

   public void l() {
      if (Keyboard.getEventKeyState()) {
         this.a(Keyboard.getEventCharacter(), Keyboard.getEventKey());
      }

      this.j.Z();
   }

   public void e() {
   }

   public void m() {
   }

   public void c() {
      this.b_(0);
   }

   public void b_(int var1) {
      if (this.j.f != null) {
         this.a(0, 0, this.l, this.m, -1072689136, -804253680);
      } else {
         this.c(var1);
      }
   }

   public void c(int var1) {
      GlStateManager.f();
      GlStateManager.n();
      Tessellator var2 = Tessellator.a();
      WorldRenderer var3 = var2.c();
      this.j.P().a(b);
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      float var4 = 32.0F;
      var3.a(7, DefaultVertexFormats.i);
      var3.b(0.0, (double)this.m, 0.0).a(0.0, (double)(this.m / 32.0F + var1)).b(64, 64, 64, 255).d();
      var3.b((double)this.l, (double)this.m, 0.0).a((double)(this.l / 32.0F), (double)(this.m / 32.0F + var1)).b(64, 64, 64, 255).d();
      var3.b((double)this.l, 0.0, 0.0).a((double)(this.l / 32.0F), (double)var1).b(64, 64, 64, 255).d();
      var3.b(0.0, 0.0, 0.0).a(0.0, (double)var1).b(64, 64, 64, 255).d();
      var2.b();
   }

   public boolean d() {
      return true;
   }

   @Override
   public void a(boolean var1, int var2) {
      if (var2 == 31102009) {
         if (var1) {
            this.a(this.t);
         }

         this.t = null;
         this.j.a(this);
      }
   }

   private void a(URI var1) {
      try {
         Class var2 = Class.forName("java.awt.Desktop");
         Object var3 = var2.getMethod("getDesktop").invoke(null);
         var2.getMethod("browse", URI.class).invoke(var3, var1);
      } catch (Throwable var4) {
         a.error("Couldn't open link", var4);
      }
   }

   public static boolean q() {
      return Minecraft.a ? Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220) : Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
   }

   public static boolean r() {
      return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
   }

   public static boolean s() {
      return Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184);
   }

   public static boolean d(int var0) {
      return var0 == 45 && q() && !r() && !s();
   }

   public static boolean e(int var0) {
      return var0 == 47 && q() && !r() && !s();
   }

   public static boolean f(int var0) {
      return var0 == 46 && q() && !r() && !s();
   }

   public static boolean g(int var0) {
      return var0 == 30 && q() && !r() && !s();
   }

   public void b(Minecraft var1, int var2, int var3) {
      this.a(var1, var2, var3);
   }
}
