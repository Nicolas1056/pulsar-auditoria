package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

public class GuiOptionsRowList extends GuiListExtended {
   private final List<GuiOptionsRowList.Row> u = Lists.newArrayList();

   public GuiOptionsRowList(Minecraft var1, int var2, int var3, int var4, int var5, int var6, GameSettings.Options... var7) {
      super(var1, var2, var3, var4, var5, var6);
      this.k = false;

      for (byte var8 = 0; var8 < var7.length; var8 += 2) {
         GameSettings.Options var9 = var7[var8];
         GameSettings.Options var10 = var8 < var7.length - 1 ? var7[var8 + 1] : null;
         GuiButton var11 = this.a(var1, var2 / 2 - 155, 0, var9);
         GuiButton var12 = this.a(var1, var2 / 2 - 155 + 160, 0, var10);
         this.u.add(new GuiOptionsRowList.Row(var11, var12));
      }
   }

   private GuiButton a(Minecraft var1, int var2, int var3, GameSettings.Options var4) {
      if (var4 == null) {
         return null;
      } else {
         int var5 = var4.c();
         return (GuiButton)(var4.a() ? new GuiOptionSlider(var5, var2, var3, var4) : new GuiOptionButton(var5, var2, var3, var4, var1.t.c(var4)));
      }
   }

   public GuiOptionsRowList.Row c(int var1) {
      return this.u.get(var1);
   }

   @Override
   protected int b() {
      return this.u.size();
   }

   @Override
   public int c() {
      return 400;
   }

   @Override
   protected int d() {
      return super.d() + 32;
   }

   public static class Row implements GuiListExtended.IGuiListEntry {
      private final Minecraft a = Minecraft.A();
      private final GuiButton b;
      private final GuiButton c;

      public Row(GuiButton var1, GuiButton var2) {
         this.b = var1;
         this.c = var2;
      }

      @Override
      public void a(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
         if (this.b != null) {
            this.b.i = var3;
            this.b.a(this.a, var6, var7);
         }

         if (this.c != null) {
            this.c.i = var3;
            this.c.a(this.a, var6, var7);
         }
      }

      @Override
      public boolean a(int var1, int var2, int var3, int var4, int var5, int var6) {
         if (this.b.c(this.a, var2, var3)) {
            if (this.b instanceof GuiOptionButton) {
               this.a.t.a(((GuiOptionButton)this.b).c(), 1);
               this.b.j = this.a.t.c(GameSettings.Options.a(this.b.k));
            }

            return true;
         } else if (this.c != null && this.c.c(this.a, var2, var3)) {
            if (this.c instanceof GuiOptionButton) {
               this.a.t.a(((GuiOptionButton)this.c).c(), 1);
               this.c.j = this.a.t.c(GameSettings.Options.a(this.c.k));
            }

            return true;
         } else {
            return false;
         }
      }

      @Override
      public void b(int var1, int var2, int var3, int var4, int var5, int var6) {
         if (this.b != null) {
            this.b.a(var2, var3);
         }

         if (this.c != null) {
            this.c.a(var2, var3);
         }
      }

      @Override
      public void a(int var1, int var2, int var3) {
      }
   }
}
