package net.minecraft.util;

import java.util.List;
import net.minecraft.command.CommandException;
import net.minecraft.command.EntityNotFoundException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerSelector;
import net.minecraft.entity.Entity;

public class ChatComponentProcessor {
   public static IChatComponent a(ICommandSender var0, IChatComponent var1, Entity var2) throws CommandException {
      Object var3 = null;
      if (var1 instanceof ChatComponentScore) {
         ChatComponentScore var4 = (ChatComponentScore)var1;
         String var5 = var4.g();
         if (PlayerSelector.b(var5)) {
            List var6 = PlayerSelector.b(var0, var5, Entity.class);
            if (var6.size() != 1) {
               throw new EntityNotFoundException();
            }

            var5 = ((Entity)var6.get(0)).e_();
         }

         var3 = var2 != null && var5.equals("*") ? new ChatComponentScore(var2.e_(), var4.h()) : new ChatComponentScore(var5, var4.h());
         ((ChatComponentScore)var3).b(var4.e());
      } else if (var1 instanceof ChatComponentSelector) {
         String var8 = ((ChatComponentSelector)var1).g();
         var3 = PlayerSelector.b(var0, var8);
         if (var3 == null) {
            var3 = new ChatComponentText("");
         }
      } else if (var1 instanceof ChatComponentText) {
         var3 = new ChatComponentText(((ChatComponentText)var1).g());
      } else {
         if (!(var1 instanceof ChatComponentTranslation)) {
            return var1;
         }

         Object[] var9 = ((ChatComponentTranslation)var1).j();

         for (int var11 = 0; var11 < var9.length; var11++) {
            Object var13 = var9[var11];
            if (var13 instanceof IChatComponent) {
               var9[var11] = a(var0, (IChatComponent)var13, var2);
            }
         }

         var3 = new ChatComponentTranslation(((ChatComponentTranslation)var1).i(), var9);
      }

      ChatStyle var10 = var1.b();
      if (var10 != null) {
         ((IChatComponent)var3).a(var10.m());
      }

      for (IChatComponent var14 : var1.a()) {
         ((IChatComponent)var3).a(a(var0, var14, var2));
      }

      return (IChatComponent)var3;
   }
}
