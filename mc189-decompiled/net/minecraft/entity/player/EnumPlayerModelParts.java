package net.minecraft.entity.player;

import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public enum EnumPlayerModelParts {
   a(0, "cape"),
   b(1, "jacket"),
   c(2, "left_sleeve"),
   d(3, "right_sleeve"),
   e(4, "left_pants_leg"),
   f(5, "right_pants_leg"),
   g(6, "hat");

   private final int h;
   private final int i;
   private final String j;
   private final IChatComponent k;

   private EnumPlayerModelParts(int var3, String var4) {
      this.h = var3;
      this.i = 1 << var3;
      this.j = var4;
      this.k = new ChatComponentTranslation("options.modelPart." + var4);
   }

   public int a() {
      return this.i;
   }

   public int b() {
      return this.h;
   }

   public String c() {
      return this.j;
   }

   public IChatComponent d() {
      return this.k;
   }
}
