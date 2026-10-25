package net.minecraft.item;

import net.minecraft.util.EnumChatFormatting;

public enum EnumRarity {
   a(EnumChatFormatting.p, "Common"),
   b(EnumChatFormatting.o, "Uncommon"),
   c(EnumChatFormatting.l, "Rare"),
   d(EnumChatFormatting.n, "Epic");

   public final EnumChatFormatting e;
   public final String f;

   private EnumRarity(EnumChatFormatting var3, String var4) {
      this.e = var3;
      this.f = var4;
   }
}
