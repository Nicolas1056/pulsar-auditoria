package net.minecraft.client.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.util.EnumChatFormatting;

public abstract class GuiResourcePackList extends GuiListExtended {
   protected final Minecraft u;
   protected final List<ResourcePackListEntry> v;

   public GuiResourcePackList(Minecraft var1, int var2, int var3, List<ResourcePackListEntry> var4) {
      super(var1, var2, var3, 32, var3 - 55 + 4, 36);
      this.u = var1;
      this.v = var4;
      this.k = false;
      this.a(true, (int)(var1.k.a * 1.5F));
   }

   @Override
   protected void a(int var1, int var2, Tessellator var3) {
      String var4 = EnumChatFormatting.t + "" + EnumChatFormatting.r + this.e();
      this.u.k.a(var4, var1 + this.b / 2 - this.u.k.a(var4) / 2, Math.min(this.d + 3, var2), 16777215);
   }

   protected abstract String e();

   public List<ResourcePackListEntry> f() {
      return this.v;
   }

   @Override
   protected int b() {
      return this.f().size();
   }

   public ResourcePackListEntry c(int var1) {
      return this.f().get(var1);
   }

   @Override
   public int c() {
      return this.b;
   }

   @Override
   protected int d() {
      return this.f - 6;
   }
}
