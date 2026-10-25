package net.minecraft.client.gui.spectator;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.gui.spectator.categories.TeleportToPlayer;
import net.minecraft.client.gui.spectator.categories.TeleportToTeam;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class BaseSpectatorGroup implements ISpectatorMenuView {
   private final List<ISpectatorMenuObject> a = Lists.newArrayList();

   public BaseSpectatorGroup() {
      this.a.add(new TeleportToPlayer());
      this.a.add(new TeleportToTeam());
   }

   @Override
   public List<ISpectatorMenuObject> a() {
      return this.a;
   }

   @Override
   public IChatComponent b() {
      return new ChatComponentText("Press a key to select a command, and again to use it.");
   }
}
