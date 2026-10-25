package net.minecraft.client.gui.spectator.categories;

import com.google.common.base.Objects;
import java.util.List;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.SpectatorMenu;

public class SpectatorDetails {
   private final ISpectatorMenuView a;
   private final List<ISpectatorMenuObject> b;
   private final int c;

   public SpectatorDetails(ISpectatorMenuView var1, List<ISpectatorMenuObject> var2, int var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   public ISpectatorMenuObject a(int var1) {
      return var1 >= 0 && var1 < this.b.size() ? (ISpectatorMenuObject)Objects.firstNonNull(this.b.get(var1), SpectatorMenu.a) : SpectatorMenu.a;
   }

   public int b() {
      return this.c;
   }
}
