package net.minecraft.realms;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;

public class RealmsEditBox {
   private final GuiTextField editBox;

   public RealmsEditBox(int var1, int var2, int var3, int var4, int var5) {
      this.editBox = new GuiTextField(var1, Minecraft.A().k, var2, var3, var4, var5);
   }

   public String getValue() {
      return this.editBox.b();
   }

   public void tick() {
      this.editBox.a();
   }

   public void setFocus(boolean var1) {
      this.editBox.b(var1);
   }

   public void setValue(String var1) {
      this.editBox.a(var1);
   }

   public void keyPressed(char var1, int var2) {
      this.editBox.a(var1, var2);
   }

   public boolean isFocused() {
      return this.editBox.m();
   }

   public void mouseClicked(int var1, int var2, int var3) {
      this.editBox.a(var1, var2, var3);
   }

   public void render() {
      this.editBox.g();
   }

   public void setMaxLength(int var1) {
      this.editBox.f(var1);
   }

   public void setIsEditable(boolean var1) {
      this.editBox.c(var1);
   }
}
