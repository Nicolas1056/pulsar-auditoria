package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class GuiUtilRenderComponents {
   public static String a(String var0, boolean var1) {
      return !var1 && !Minecraft.A().t.n ? EnumChatFormatting.a(var0) : var0;
   }

   public static List<IChatComponent> a(IChatComponent var0, int var1, FontRenderer var2, boolean var3, boolean var4) {
      int var5 = 0;
      ChatComponentText var6 = new ChatComponentText("");
      ArrayList var7 = Lists.newArrayList();
      ArrayList var8 = Lists.newArrayList(var0);

      for (int var9 = 0; var9 < var8.size(); var9++) {
         IChatComponent var10 = (IChatComponent)var8.get(var9);
         String var11 = var10.e();
         boolean var12 = false;
         if (var11.contains("\n")) {
            int var13 = var11.indexOf(10);
            String var14 = var11.substring(var13 + 1);
            var11 = var11.substring(0, var13 + 1);
            ChatComponentText var15 = new ChatComponentText(var14);
            var15.a(var10.b().m());
            var8.add(var9 + 1, var15);
            var12 = true;
         }

         String var21 = a(var10.b().k() + var11, var4);
         String var22 = var21.endsWith("\n") ? var21.substring(0, var21.length() - 1) : var21;
         int var23 = var2.a(var22);
         ChatComponentText var16 = new ChatComponentText(var22);
         var16.a(var10.b().m());
         if (var5 + var23 > var1) {
            String var17 = var2.a(var21, var1 - var5, false);
            String var18 = var17.length() < var21.length() ? var21.substring(var17.length()) : null;
            if (var18 != null && var18.length() > 0) {
               int var19 = var17.lastIndexOf(" ");
               if (var19 >= 0 && var2.a(var21.substring(0, var19)) > 0) {
                  var17 = var21.substring(0, var19);
                  if (var3) {
                     var19++;
                  }

                  var18 = var21.substring(var19);
               } else if (var5 > 0 && !var21.contains(" ")) {
                  var17 = "";
                  var18 = var21;
               }

               ChatComponentText var20 = new ChatComponentText(var18);
               var20.a(var10.b().m());
               var8.add(var9 + 1, var20);
            }

            var23 = var2.a(var17);
            var16 = new ChatComponentText(var17);
            var16.a(var10.b().m());
            var12 = true;
         }

         if (var5 + var23 <= var1) {
            var5 += var23;
            var6.a(var16);
         } else {
            var12 = true;
         }

         if (var12) {
            var7.add(var6);
            var5 = 0;
            var6 = new ChatComponentText("");
         }
      }

      var7.add(var6);
      return var7;
   }
}
