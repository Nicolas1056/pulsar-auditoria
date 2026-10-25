package net.minecraft.client.resources;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

public abstract class ResourcePackListEntry implements GuiListExtended.IGuiListEntry {
   private static final ResourceLocation c = new ResourceLocation("textures/gui/resource_packs.png");
   private static final IChatComponent d = new ChatComponentTranslation("resourcePack.incompatible");
   private static final IChatComponent e = new ChatComponentTranslation("resourcePack.incompatible.old");
   private static final IChatComponent f = new ChatComponentTranslation("resourcePack.incompatible.new");
   protected final Minecraft a;
   protected final GuiScreenResourcePacks b;

   public ResourcePackListEntry(GuiScreenResourcePacks var1) {
      this.b = var1;
      this.a = Minecraft.A();
   }

   @Override
   public void a(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      int var9 = this.a();
      if (var9 != 1) {
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         Gui.a(var2 - 1, var3 - 1, var2 + var4 - 9, var3 + var5 + 1, -8978432);
      }

      this.d();
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      Gui.a(var2, var3, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
      String var10 = this.c();
      String var11 = this.b();
      if ((this.a.t.A || var8) && this.e()) {
         this.a.P().a(c);
         Gui.a(var2, var3, var2 + 32, var3 + 32, -1601138544);
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         int var12 = var6 - var2;
         int var13 = var7 - var3;
         if (var9 < 1) {
            var10 = d.d();
            var11 = e.d();
         } else if (var9 > 1) {
            var10 = d.d();
            var11 = f.d();
         }

         if (this.f()) {
            if (var12 < 32) {
               Gui.a(var2, var3, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
            } else {
               Gui.a(var2, var3, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
         } else {
            if (this.g()) {
               if (var12 < 16) {
                  Gui.a(var2, var3, 32.0F, 32.0F, 32, 32, 256.0F, 256.0F);
               } else {
                  Gui.a(var2, var3, 32.0F, 0.0F, 32, 32, 256.0F, 256.0F);
               }
            }

            if (this.h()) {
               if (var12 < 32 && var12 > 16 && var13 < 16) {
                  Gui.a(var2, var3, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
               } else {
                  Gui.a(var2, var3, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
               }
            }

            if (this.i()) {
               if (var12 < 32 && var12 > 16 && var13 > 16) {
                  Gui.a(var2, var3, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
               } else {
                  Gui.a(var2, var3, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
               }
            }
         }
      }

      int var15 = this.a.k.a(var10);
      if (var15 > 157) {
         var10 = this.a.k.a(var10, 157 - this.a.k.a("...")) + "...";
      }

      this.a.k.a(var10, (float)(var2 + 32 + 2), (float)(var3 + 1), 16777215);
      List var16 = this.a.k.c(var11, 157);

      for (int var14 = 0; var14 < 2 && var14 < var16.size(); var14++) {
         this.a.k.a((String)var16.get(var14), (float)(var2 + 32 + 2), (float)(var3 + 12 + 10 * var14), 8421504);
      }
   }

   protected abstract int a();

   protected abstract String b();

   protected abstract String c();

   protected abstract void d();

   protected boolean e() {
      return true;
   }

   protected boolean f() {
      return !this.b.a(this);
   }

   protected boolean g() {
      return this.b.a(this);
   }

   protected boolean h() {
      List var1 = this.b.b(this);
      int var2 = var1.indexOf(this);
      return var2 > 0 && ((ResourcePackListEntry)var1.get(var2 - 1)).e();
   }

   protected boolean i() {
      List var1 = this.b.b(this);
      int var2 = var1.indexOf(this);
      return var2 >= 0 && var2 < var1.size() - 1 && ((ResourcePackListEntry)var1.get(var2 + 1)).e();
   }

   @Override
   public boolean a(int var1, int var2, int var3, int var4, int var5, int var6) {
      if (this.e() && var5 <= 32) {
         if (this.f()) {
            this.b.g();
            int var11 = this.a();
            if (var11 != 1) {
               String var13 = I18n.a("resourcePack.incompatible.confirm.title");
               String var9 = I18n.a("resourcePack.incompatible.confirm." + (var11 > 1 ? "new" : "old"));
               this.a.a(new GuiYesNo(new GuiYesNoCallback() {
                  @Override
                  public void a(boolean var1, int var2x) {
                     List var3x = ResourcePackListEntry.this.b.b(ResourcePackListEntry.this);
                     ResourcePackListEntry.this.a.a(ResourcePackListEntry.this.b);
                     if (var1) {
                        var3x.remove(ResourcePackListEntry.this);
                        ResourcePackListEntry.this.b.f().add(0, ResourcePackListEntry.this);
                     }
                  }
               }, var13, var9, 0));
            } else {
               this.b.b(this).remove(this);
               this.b.f().add(0, this);
            }

            return true;
         }

         if (var5 < 16 && this.g()) {
            this.b.b(this).remove(this);
            this.b.a().add(0, this);
            this.b.g();
            return true;
         }

         if (var5 > 16 && var6 < 16 && this.h()) {
            List var10 = this.b.b(this);
            int var12 = var10.indexOf(this);
            var10.remove(this);
            var10.add(var12 - 1, this);
            this.b.g();
            return true;
         }

         if (var5 > 16 && var6 > 16 && this.i()) {
            List var7 = this.b.b(this);
            int var8 = var7.indexOf(this);
            var7.remove(this);
            var7.add(var8 + 1, this);
            this.b.g();
            return true;
         }
      }

      return false;
   }

   @Override
   public void a(int var1, int var2, int var3) {
   }

   @Override
   public void b(int var1, int var2, int var3, int var4, int var5, int var6) {
   }
}
