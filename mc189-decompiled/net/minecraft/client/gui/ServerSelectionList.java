package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerDetector;

public class ServerSelectionList extends GuiListExtended {
   private final GuiMultiplayer u;
   private final List<ServerListEntryNormal> v = Lists.newArrayList();
   private final List<ServerListEntryLanDetected> w = Lists.newArrayList();
   private final GuiListExtended.IGuiListEntry x = new ServerListEntryLanScan();
   private int y = -1;

   public ServerSelectionList(GuiMultiplayer var1, Minecraft var2, int var3, int var4, int var5, int var6, int var7) {
      super(var2, var3, var4, var5, var6, var7);
      this.u = var1;
   }

   @Override
   public GuiListExtended.IGuiListEntry b(int var1) {
      if (var1 < this.v.size()) {
         return this.v.get(var1);
      } else {
         var1 -= this.v.size();
         return var1 == 0 ? this.x : this.w.get(--var1);
      }
   }

   @Override
   protected int b() {
      return this.v.size() + 1 + this.w.size();
   }

   public void c(int var1) {
      this.y = var1;
   }

   @Override
   protected boolean a(int var1) {
      return var1 == this.y;
   }

   public int e() {
      return this.y;
   }

   public void a(ServerList var1) {
      this.v.clear();

      for (int var2 = 0; var2 < var1.c(); var2++) {
         this.v.add(new ServerListEntryNormal(this.u, var1.a(var2)));
      }
   }

   public void a(List<LanServerDetector.LanServer> var1) {
      this.w.clear();

      for (LanServerDetector.LanServer var3 : var1) {
         this.w.add(new ServerListEntryLanDetected(this.u, var3));
      }
   }

   @Override
   protected int d() {
      return super.d() + 30;
   }

   @Override
   public int c() {
      return super.c() + 85;
   }
}
