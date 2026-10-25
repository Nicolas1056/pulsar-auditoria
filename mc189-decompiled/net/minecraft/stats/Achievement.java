package net.minecraft.stats;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IJsonSerializable;
import net.minecraft.util.StatCollector;

public class Achievement extends StatBase {
   public final int a;
   public final int b;
   public final Achievement c;
   private final String k;
   private IStatStringFormat l;
   public final ItemStack d;
   private boolean m;

   public Achievement(String var1, String var2, int var3, int var4, Item var5, Achievement var6) {
      this(var1, var2, var3, var4, new ItemStack(var5), var6);
   }

   public Achievement(String var1, String var2, int var3, int var4, Block var5, Achievement var6) {
      this(var1, var2, var3, var4, new ItemStack(var5), var6);
   }

   public Achievement(String var1, String var2, int var3, int var4, ItemStack var5, Achievement var6) {
      super(var1, new ChatComponentTranslation("achievement." + var2));
      this.d = var5;
      this.k = "achievement." + var2 + ".desc";
      this.a = var3;
      this.b = var4;
      if (var3 < AchievementList.a) {
         a = var3;
      }

      if (var4 < AchievementList.b) {
         b = var4;
      }

      if (var3 > AchievementList.c) {
         AchievementList.c = var3;
      }

      if (var4 > AchievementList.d) {
         AchievementList.d = var4;
      }

      this.c = var6;
   }

   public Achievement a() {
      this.f = true;
      return this;
   }

   public Achievement b() {
      this.m = true;
      return this;
   }

   public Achievement c() {
      super.h();
      AchievementList.e.add(this);
      return this;
   }

   @Override
   public boolean d() {
      return true;
   }

   @Override
   public IChatComponent e() {
      IChatComponent var1 = super.e();
      var1.b().a(this.g() ? EnumChatFormatting.f : EnumChatFormatting.k);
      return var1;
   }

   public Achievement a(Class<? extends IJsonSerializable> var1) {
      return (Achievement)super.b(var1);
   }

   public String f() {
      return this.l != null ? this.l.a(StatCollector.a(this.k)) : StatCollector.a(this.k);
   }

   public Achievement a(IStatStringFormat var1) {
      this.l = var1;
      return this;
   }

   public boolean g() {
      return this.m;
   }
}
