package net.minecraft.client.gui.spectator.categories;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class TeleportToTeam implements ISpectatorMenuView, ISpectatorMenuObject {
   private final List<ISpectatorMenuObject> a = Lists.newArrayList();

   public TeleportToTeam() {
      Minecraft var1 = Minecraft.A();

      for (ScorePlayerTeam var3 : var1.f.Z().g()) {
         this.a.add(new TeleportToTeam.TeamSelectionObject(var3));
      }
   }

   @Override
   public List<ISpectatorMenuObject> a() {
      return this.a;
   }

   @Override
   public IChatComponent b() {
      return new ChatComponentText("Select a team to teleport to");
   }

   @Override
   public void a(SpectatorMenu var1) {
      var1.a(this);
   }

   @Override
   public IChatComponent A_() {
      return new ChatComponentText("Teleport to team member");
   }

   @Override
   public void a(float var1, int var2) {
      Minecraft.A().P().a(GuiSpectator.a);
      Gui.a(0, 0, 16.0F, 0.0F, 16, 16, 256.0F, 256.0F);
   }

   @Override
   public boolean B_() {
      for (ISpectatorMenuObject var2 : this.a) {
         if (var2.B_()) {
            return true;
         }
      }

      return false;
   }

   class TeamSelectionObject implements ISpectatorMenuObject {
      private final ScorePlayerTeam b;
      private final ResourceLocation c;
      private final List<NetworkPlayerInfo> d;

      public TeamSelectionObject(ScorePlayerTeam var2) {
         this.b = var2;
         this.d = Lists.newArrayList();

         for (String var4 : var2.d()) {
            NetworkPlayerInfo var5 = Minecraft.A().u().a(var4);
            if (var5 != null) {
               this.d.add(var5);
            }
         }

         if (!this.d.isEmpty()) {
            String var6 = this.d.get(new Random().nextInt(this.d.size())).a().getName();
            this.c = AbstractClientPlayer.c(var6);
            AbstractClientPlayer.a(this.c, var6);
         } else {
            this.c = DefaultPlayerSkin.a();
         }
      }

      @Override
      public void a(SpectatorMenu var1) {
         var1.a(new TeleportToPlayer(this.d));
      }

      @Override
      public IChatComponent A_() {
         return new ChatComponentText(this.b.c());
      }

      @Override
      public void a(float var1, int var2) {
         int var3 = -1;
         String var4 = FontRenderer.b(this.b.e());
         if (var4.length() >= 2) {
            var3 = Minecraft.A().k.b(var4.charAt(1));
         }

         if (var3 >= 0) {
            float var5 = (var3 >> 16 & 0xFF) / 255.0F;
            float var6 = (var3 >> 8 & 0xFF) / 255.0F;
            float var7 = (var3 & 0xFF) / 255.0F;
            Gui.a(1, 1, 15, 15, MathHelper.b(var5 * var1, var6 * var1, var7 * var1) | var2 << 24);
         }

         Minecraft.A().P().a(this.c);
         GlStateManager.c(var1, var1, var1, var2 / 255.0F);
         Gui.a(2, 2, 8.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
         Gui.a(2, 2, 40.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
      }

      @Override
      public boolean B_() {
         return !this.d.isEmpty();
      }
   }
}
