package net.minecraft.client.gui.spectator;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.spectator.categories.SpectatorDetails;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class SpectatorMenu {
   private static final ISpectatorMenuObject b = new SpectatorMenu.EndSpectatorObject();
   private static final ISpectatorMenuObject c = new SpectatorMenu.MoveMenuObject(-1, true);
   private static final ISpectatorMenuObject d = new SpectatorMenu.MoveMenuObject(1, true);
   private static final ISpectatorMenuObject e = new SpectatorMenu.MoveMenuObject(1, false);
   public static final ISpectatorMenuObject a = new ISpectatorMenuObject() {
      @Override
      public void a(SpectatorMenu var1) {
      }

      @Override
      public IChatComponent A_() {
         return new ChatComponentText("");
      }

      @Override
      public void a(float var1, int var2) {
      }

      @Override
      public boolean B_() {
         return false;
      }
   };
   private final ISpectatorMenuRecipient f;
   private final List<SpectatorDetails> g = Lists.newArrayList();
   private ISpectatorMenuView h;
   private int i = -1;
   private int j;

   public SpectatorMenu(ISpectatorMenuRecipient var1) {
      this.h = new BaseSpectatorGroup();
      this.f = var1;
   }

   public ISpectatorMenuObject a(int var1) {
      int var2 = var1 + this.j * 6;
      if (this.j > 0 && var1 == 0) {
         return c;
      } else if (var1 == 7) {
         return var2 < this.h.a().size() ? d : e;
      } else if (var1 == 8) {
         return b;
      } else {
         return var2 >= 0 && var2 < this.h.a().size() ? (ISpectatorMenuObject)Objects.firstNonNull(this.h.a().get(var2), a) : a;
      }
   }

   public List<ISpectatorMenuObject> a() {
      ArrayList var1 = Lists.newArrayList();

      for (int var2 = 0; var2 <= 8; var2++) {
         var1.add(this.a(var2));
      }

      return var1;
   }

   public ISpectatorMenuObject b() {
      return this.a(this.i);
   }

   public ISpectatorMenuView c() {
      return this.h;
   }

   public void b(int var1) {
      ISpectatorMenuObject var2 = this.a(var1);
      if (var2 != a) {
         if (this.i == var1 && var2.B_()) {
            var2.a(this);
         } else {
            this.i = var1;
         }
      }
   }

   public void d() {
      this.f.a(this);
   }

   public int e() {
      return this.i;
   }

   public void a(ISpectatorMenuView var1) {
      this.g.add(this.f());
      this.h = var1;
      this.i = -1;
      this.j = 0;
   }

   public SpectatorDetails f() {
      return new SpectatorDetails(this.h, this.a(), this.i);
   }

   static class EndSpectatorObject implements ISpectatorMenuObject {
      private EndSpectatorObject() {
      }

      @Override
      public void a(SpectatorMenu var1) {
         var1.d();
      }

      @Override
      public IChatComponent A_() {
         return new ChatComponentText("Close menu");
      }

      @Override
      public void a(float var1, int var2) {
         Minecraft.A().P().a(GuiSpectator.a);
         Gui.a(0, 0, 128.0F, 0.0F, 16, 16, 256.0F, 256.0F);
      }

      @Override
      public boolean B_() {
         return true;
      }
   }

   static class MoveMenuObject implements ISpectatorMenuObject {
      private final int a;
      private final boolean b;

      public MoveMenuObject(int var1, boolean var2) {
         this.a = var1;
         this.b = var2;
      }

      @Override
      public void a(SpectatorMenu var1) {
         var1.j += this.a;
      }

      @Override
      public IChatComponent A_() {
         return this.a < 0 ? new ChatComponentText("Previous Page") : new ChatComponentText("Next Page");
      }

      @Override
      public void a(float var1, int var2) {
         Minecraft.A().P().a(GuiSpectator.a);
         if (this.a < 0) {
            Gui.a(0, 0, 144.0F, 0.0F, 16, 16, 256.0F, 256.0F);
         } else {
            Gui.a(0, 0, 160.0F, 0.0F, 16, 16, 256.0F, 256.0F);
         }
      }

      @Override
      public boolean B_() {
         return this.b;
      }
   }
}
