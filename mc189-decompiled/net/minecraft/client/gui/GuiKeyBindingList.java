package net.minecraft.client.gui;

import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.lang3.ArrayUtils;

public class GuiKeyBindingList extends GuiListExtended {
   private final GuiControls u;
   private final Minecraft v;
   private final GuiListExtended.IGuiListEntry[] w;
   private int x = 0;

   public GuiKeyBindingList(GuiControls var1, Minecraft var2) {
      super(var2, var1.l, var1.m, 63, var1.m - 32, 20);
      this.u = var1;
      this.v = var2;
      KeyBinding[] var3 = (KeyBinding[])ArrayUtils.clone(var2.t.ax);
      this.w = new GuiListExtended.IGuiListEntry[var3.length + KeyBinding.c().size()];
      Arrays.sort((Object[])var3);
      int var4 = 0;
      String var5 = null;

      for (KeyBinding var9 : var3) {
         String var10 = var9.e();
         if (!var10.equals(var5)) {
            var5 = var10;
            this.w[var4++] = new GuiKeyBindingList.CategoryEntry(var10);
         }

         int var11 = var2.k.a(I18n.a(var9.g()));
         if (var11 > this.x) {
            this.x = var11;
         }

         this.w[var4++] = new GuiKeyBindingList.KeyEntry(var9);
      }
   }

   @Override
   protected int b() {
      return this.w.length;
   }

   @Override
   public GuiListExtended.IGuiListEntry b(int var1) {
      return this.w[var1];
   }

   @Override
   protected int d() {
      return super.d() + 15;
   }

   @Override
   public int c() {
      return super.c() + 32;
   }

   public class CategoryEntry implements GuiListExtended.IGuiListEntry {
      private final String b;
      private final int c;

      public CategoryEntry(String var2) {
         this.b = I18n.a(var2);
         this.c = GuiKeyBindingList.this.v.k.a(this.b);
      }

      @Override
      public void a(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
         GuiKeyBindingList.this.v.k.a(this.b, GuiKeyBindingList.this.v.m.l / 2 - this.c / 2, var3 + var5 - GuiKeyBindingList.this.v.k.a - 1, 16777215);
      }

      @Override
      public boolean a(int var1, int var2, int var3, int var4, int var5, int var6) {
         return false;
      }

      @Override
      public void b(int var1, int var2, int var3, int var4, int var5, int var6) {
      }

      @Override
      public void a(int var1, int var2, int var3) {
      }
   }

   public class KeyEntry implements GuiListExtended.IGuiListEntry {
      private final KeyBinding b;
      private final String c;
      private final GuiButton d;
      private final GuiButton e;

      private KeyEntry(KeyBinding var2) {
         this.b = var2;
         this.c = I18n.a(var2.g());
         this.d = new GuiButton(0, 0, 0, 75, 20, I18n.a(var2.g()));
         this.e = new GuiButton(0, 0, 0, 50, 20, I18n.a("controls.reset"));
      }

      @Override
      public void a(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
         boolean var9 = GuiKeyBindingList.this.u.f == this.b;
         GuiKeyBindingList.this.v.k.a(this.c, var2 + 90 - GuiKeyBindingList.this.x, var3 + var5 / 2 - GuiKeyBindingList.this.v.k.a / 2, 16777215);
         this.e.h = var2 + 190;
         this.e.i = var3;
         this.e.l = this.b.i() != this.b.h();
         this.e.a(GuiKeyBindingList.this.v, var6, var7);
         this.d.h = var2 + 105;
         this.d.i = var3;
         this.d.j = GameSettings.c(this.b.i());
         boolean var10 = false;
         if (this.b.i() != 0) {
            for (KeyBinding var14 : GuiKeyBindingList.this.v.t.ax) {
               if (var14 != this.b && var14.i() == this.b.i()) {
                  var10 = true;
                  break;
               }
            }
         }

         if (var9) {
            this.d.j = EnumChatFormatting.p + "> " + EnumChatFormatting.o + this.d.j + EnumChatFormatting.p + " <";
         } else if (var10) {
            this.d.j = EnumChatFormatting.m + this.d.j;
         }

         this.d.a(GuiKeyBindingList.this.v, var6, var7);
      }

      @Override
      public boolean a(int var1, int var2, int var3, int var4, int var5, int var6) {
         if (this.d.c(GuiKeyBindingList.this.v, var2, var3)) {
            GuiKeyBindingList.this.u.f = this.b;
            return true;
         } else if (this.e.c(GuiKeyBindingList.this.v, var2, var3)) {
            GuiKeyBindingList.this.v.t.a(this.b, this.b.h());
            KeyBinding.b();
            return true;
         } else {
            return false;
         }
      }

      @Override
      public void b(int var1, int var2, int var3, int var4, int var5, int var6) {
         this.d.a(var2, var3);
         this.e.a(var2, var3);
      }

      @Override
      public void a(int var1, int var2, int var3) {
      }
   }
}
