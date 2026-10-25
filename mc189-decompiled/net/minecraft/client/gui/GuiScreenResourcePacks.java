package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.client.resources.ResourcePackListEntryDefault;
import net.minecraft.client.resources.ResourcePackListEntryFound;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Sys;

public class GuiScreenResourcePacks extends GuiScreen {
   private static final Logger a = LogManager.getLogger();
   private final GuiScreen f;
   private List<ResourcePackListEntry> g;
   private List<ResourcePackListEntry> h;
   private GuiResourcePackAvailable i;
   private GuiResourcePackSelected r;
   private boolean s = false;

   public GuiScreenResourcePacks(GuiScreen var1) {
      this.f = var1;
   }

   @Override
   public void b() {
      this.n.add(new GuiOptionButton(2, this.l / 2 - 154, this.m - 48, I18n.a("resourcePack.openFolder")));
      this.n.add(new GuiOptionButton(1, this.l / 2 + 4, this.m - 48, I18n.a("gui.done")));
      if (!this.s) {
         this.g = Lists.newArrayList();
         this.h = Lists.newArrayList();
         ResourcePackRepository var1 = this.j.R();
         var1.a();
         ArrayList var2 = Lists.newArrayList(var1.b());
         var2.removeAll(var1.c());

         for (ResourcePackRepository.Entry var4 : var2) {
            this.g.add(new ResourcePackListEntryFound(this, var4));
         }

         for (ResourcePackRepository.Entry var6 : Lists.reverse(var1.c())) {
            this.h.add(new ResourcePackListEntryFound(this, var6));
         }

         this.h.add(new ResourcePackListEntryDefault(this));
      }

      this.i = new GuiResourcePackAvailable(this.j, 200, this.m, this.g);
      this.i.i(this.l / 2 - 4 - 200);
      this.i.d(7, 8);
      this.r = new GuiResourcePackSelected(this.j, 200, this.m, this.h);
      this.r.i(this.l / 2 + 4);
      this.r.d(7, 8);
   }

   @Override
   public void k() {
      super.k();
      this.r.p();
      this.i.p();
   }

   public boolean a(ResourcePackListEntry var1) {
      return this.h.contains(var1);
   }

   public List<ResourcePackListEntry> b(ResourcePackListEntry var1) {
      return this.a(var1) ? this.h : this.g;
   }

   public List<ResourcePackListEntry> a() {
      return this.g;
   }

   public List<ResourcePackListEntry> f() {
      return this.h;
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.l) {
         if (var1.k == 2) {
            File var2 = this.j.R().d();
            String var3 = var2.getAbsolutePath();
            if (Util.a() == Util.EnumOS.d) {
               try {
                  a.info(var3);
                  Runtime.getRuntime().exec(new String[]{"/usr/bin/open", var3});
                  return;
               } catch (IOException var9) {
                  a.error("Couldn't open file", var9);
               }
            } else if (Util.a() == Util.EnumOS.c) {
               String var4 = String.format("cmd.exe /C start \"Open file\" \"%s\"", var3);

               try {
                  Runtime.getRuntime().exec(var4);
                  return;
               } catch (IOException var8) {
                  a.error("Couldn't open file", var8);
               }
            }

            boolean var13 = false;

            try {
               Class var5 = Class.forName("java.awt.Desktop");
               Object var6 = var5.getMethod("getDesktop").invoke(null);
               var5.getMethod("browse", URI.class).invoke(var6, var2.toURI());
            } catch (Throwable var7) {
               a.error("Couldn't open link", var7);
               var13 = true;
            }

            if (var13) {
               a.info("Opening via system class!");
               Sys.openURL("file://" + var3);
            }
         } else if (var1.k == 1) {
            if (this.s) {
               ArrayList var10 = Lists.newArrayList();

               for (ResourcePackListEntry var14 : this.h) {
                  if (var14 instanceof ResourcePackListEntryFound) {
                     var10.add(((ResourcePackListEntryFound)var14).j());
                  }
               }

               Collections.reverse(var10);
               this.j.R().a(var10);
               this.j.t.k.clear();
               this.j.t.l.clear();

               for (ResourcePackRepository.Entry var15 : var10) {
                  this.j.t.k.add(var15.d());
                  if (var15.f() != 1) {
                     this.j.t.l.add(var15.d());
                  }
               }

               this.j.t.b();
               this.j.e();
            }

            this.j.a(this.f);
         }
      }
   }

   @Override
   protected void a(int var1, int var2, int var3) {
      super.a(var1, var2, var3);
      this.i.b(var1, var2, var3);
      this.r.b(var1, var2, var3);
   }

   @Override
   protected void b(int var1, int var2, int var3) {
      super.b(var1, var2, var3);
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c(0);
      this.i.a(var1, var2, var3);
      this.r.a(var1, var2, var3);
      this.a(this.q, I18n.a("resourcePack.title"), this.l / 2, 16, 16777215);
      this.a(this.q, I18n.a("resourcePack.folderInfo"), this.l / 2 - 77, this.m - 26, 8421504);
      super.a(var1, var2, var3);
   }

   public void g() {
      this.s = true;
   }
}
