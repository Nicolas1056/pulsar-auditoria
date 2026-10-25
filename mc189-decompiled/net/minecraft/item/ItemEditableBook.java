package net.minecraft.item;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponentProcessor;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public class ItemEditableBook extends Item {
   public ItemEditableBook() {
      this.c(1);
   }

   public static boolean b(NBTTagCompound var0) {
      if (!ItemWritableBook.b(var0)) {
         return false;
      } else if (!var0.b("title", 8)) {
         return false;
      } else {
         String var1 = var0.j("title");
         return var1 == null || var1.length() > 32 ? false : var0.b("author", 8);
      }
   }

   public static int h(ItemStack var0) {
      return var0.o().f("generation");
   }

   @Override
   public String a(ItemStack var1) {
      if (var1.n()) {
         NBTTagCompound var2 = var1.o();
         String var3 = var2.j("title");
         if (!StringUtils.b(var3)) {
            return var3;
         }
      }

      return super.a(var1);
   }

   @Override
   public void a(ItemStack var1, EntityPlayer var2, List<String> var3, boolean var4) {
      if (var1.n()) {
         NBTTagCompound var5 = var1.o();
         String var6 = var5.j("author");
         if (!StringUtils.b(var6)) {
            var3.add(EnumChatFormatting.h + StatCollector.a("book.byAuthor", var6));
         }

         var3.add(EnumChatFormatting.h + StatCollector.a("book.generation." + var5.f("generation")));
      }
   }

   @Override
   public ItemStack a(ItemStack var1, World var2, EntityPlayer var3) {
      if (!var2.D) {
         this.a(var1, var3);
      }

      var3.a(var1);
      var3.b(StatList.ad[Item.b(this)]);
      return var1;
   }

   private void a(ItemStack var1, EntityPlayer var2) {
      if (var1 != null && var1.o() != null) {
         NBTTagCompound var3 = var1.o();
         if (!var3.n("resolved")) {
            var3.a("resolved", true);
            if (b(var3)) {
               NBTTagList var4 = var3.c("pages", 8);

               for (int var5 = 0; var5 < var4.c(); var5++) {
                  String var6 = var4.f(var5);

                  Object var7;
                  try {
                     var7 = IChatComponent.Serializer.a(var6);
                     var7 = ChatComponentProcessor.a(var2, (IChatComponent)var7, var2);
                  } catch (Exception var9) {
                     var7 = new ChatComponentText(var6);
                  }

                  var4.a(var5, new NBTTagString(IChatComponent.Serializer.a((IChatComponent)var7)));
               }

               var3.a("pages", var4);
               if (var2 instanceof EntityPlayerMP && var2.bZ() == var1) {
                  Slot var10 = var2.bk.a(var2.bi, var2.bi.c);
                  ((EntityPlayerMP)var2).a.a(new S2FPacketSetSlot(0, var10.e, var1));
               }
            }
         }
      }
   }

   @Override
   public boolean f(ItemStack var1) {
      return true;
   }
}
