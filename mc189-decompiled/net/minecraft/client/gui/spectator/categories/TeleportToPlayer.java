package net.minecraft.client.gui.spectator.categories;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.PlayerMenuObject;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldSettings;

public class TeleportToPlayer implements ISpectatorMenuView, ISpectatorMenuObject {
   private static final Ordering<NetworkPlayerInfo> a = Ordering.from(new Comparator<NetworkPlayerInfo>() {
      public int a(NetworkPlayerInfo var1, NetworkPlayerInfo var2) {
         return ComparisonChain.start().compare(var1.a().getId(), var2.a().getId()).result();
      }
   });
   private final List<ISpectatorMenuObject> b = Lists.newArrayList();

   public TeleportToPlayer() {
      this(a.sortedCopy(Minecraft.A().u().d()));
   }

   public TeleportToPlayer(Collection<NetworkPlayerInfo> var1) {
      for (NetworkPlayerInfo var3 : a.sortedCopy(var1)) {
         if (var3.b() != WorldSettings.GameType.e) {
            this.b.add(new PlayerMenuObject(var3.a()));
         }
      }
   }

   @Override
   public List<ISpectatorMenuObject> a() {
      return this.b;
   }

   @Override
   public IChatComponent b() {
      return new ChatComponentText("Select a player to teleport to");
   }

   @Override
   public void a(SpectatorMenu var1) {
      var1.a(this);
   }

   @Override
   public IChatComponent A_() {
      return new ChatComponentText("Teleport to player");
   }

   @Override
   public void a(float var1, int var2) {
      Minecraft.A().P().a(GuiSpectator.a);
      Gui.a(0, 0, 0.0F, 0.0F, 16, 16, 256.0F, 256.0F);
   }

   @Override
   public boolean B_() {
      return !this.b.isEmpty();
   }
}
