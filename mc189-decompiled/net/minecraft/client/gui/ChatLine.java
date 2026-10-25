package net.minecraft.client.gui;

import net.minecraft.util.IChatComponent;

public class ChatLine {
   private final int a;
   private final IChatComponent b;
   private final int c;

   public ChatLine(int var1, IChatComponent var2, int var3) {
      this.b = var2;
      this.a = var1;
      this.c = var3;
   }

   public IChatComponent a() {
      return this.b;
   }

   public int b() {
      return this.a;
   }

   public int c() {
      return this.c;
   }
}
