package net.minecraft.client.gui.stream;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.stream.IStream;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import tv.twitch.chat.ChatUserInfo;
import tv.twitch.chat.ChatUserMode;
import tv.twitch.chat.ChatUserSubscription;

public class GuiTwitchUserMode extends GuiScreen {
   private static final EnumChatFormatting a = EnumChatFormatting.c;
   private static final EnumChatFormatting f = EnumChatFormatting.m;
   private static final EnumChatFormatting g = EnumChatFormatting.f;
   private final ChatUserInfo h;
   private final IChatComponent i;
   private final List<IChatComponent> r = Lists.newArrayList();
   private final IStream s;
   private int t;

   public GuiTwitchUserMode(IStream var1, ChatUserInfo var2) {
      this.s = var1;
      this.h = var2;
      this.i = new ChatComponentText(var2.displayName);
      this.r.addAll(a(var2.modes, var2.subscriptions, var1));
   }

   public static List<IChatComponent> a(Set<ChatUserMode> var0, Set<ChatUserSubscription> var1, IStream var2) {
      String var3 = var2 == null ? null : var2.z();
      boolean var4 = var2 != null && var2.y();
      ArrayList var5 = Lists.newArrayList();

      for (ChatUserMode var7 : var0) {
         IChatComponent var8 = a(var7, var3, var4);
         if (var8 != null) {
            ChatComponentText var9 = new ChatComponentText("- ");
            var9.a(var8);
            var5.add(var9);
         }
      }

      for (ChatUserSubscription var11 : var1) {
         IChatComponent var12 = a(var11, var3, var4);
         if (var12 != null) {
            ChatComponentText var13 = new ChatComponentText("- ");
            var13.a(var12);
            var5.add(var13);
         }
      }

      return var5;
   }

   public static IChatComponent a(ChatUserSubscription var0, String var1, boolean var2) {
      ChatComponentTranslation var3 = null;
      if (var0 == ChatUserSubscription.TTV_CHAT_USERSUB_SUBSCRIBER) {
         if (var1 == null) {
            var3 = new ChatComponentTranslation("stream.user.subscription.subscriber");
         } else if (var2) {
            var3 = new ChatComponentTranslation("stream.user.subscription.subscriber.self");
         } else {
            var3 = new ChatComponentTranslation("stream.user.subscription.subscriber.other", var1);
         }

         var3.b().a(a);
      } else if (var0 == ChatUserSubscription.TTV_CHAT_USERSUB_TURBO) {
         var3 = new ChatComponentTranslation("stream.user.subscription.turbo");
         var3.b().a(g);
      }

      return var3;
   }

   public static IChatComponent a(ChatUserMode var0, String var1, boolean var2) {
      ChatComponentTranslation var3 = null;
      if (var0 == ChatUserMode.TTV_CHAT_USERMODE_ADMINSTRATOR) {
         var3 = new ChatComponentTranslation("stream.user.mode.administrator");
         var3.b().a(g);
      } else if (var0 == ChatUserMode.TTV_CHAT_USERMODE_BANNED) {
         if (var1 == null) {
            var3 = new ChatComponentTranslation("stream.user.mode.banned");
         } else if (var2) {
            var3 = new ChatComponentTranslation("stream.user.mode.banned.self");
         } else {
            var3 = new ChatComponentTranslation("stream.user.mode.banned.other", var1);
         }

         var3.b().a(f);
      } else if (var0 == ChatUserMode.TTV_CHAT_USERMODE_BROADCASTER) {
         if (var1 == null) {
            var3 = new ChatComponentTranslation("stream.user.mode.broadcaster");
         } else if (var2) {
            var3 = new ChatComponentTranslation("stream.user.mode.broadcaster.self");
         } else {
            var3 = new ChatComponentTranslation("stream.user.mode.broadcaster.other");
         }

         var3.b().a(a);
      } else if (var0 == ChatUserMode.TTV_CHAT_USERMODE_MODERATOR) {
         if (var1 == null) {
            var3 = new ChatComponentTranslation("stream.user.mode.moderator");
         } else if (var2) {
            var3 = new ChatComponentTranslation("stream.user.mode.moderator.self");
         } else {
            var3 = new ChatComponentTranslation("stream.user.mode.moderator.other", var1);
         }

         var3.b().a(a);
      } else if (var0 == ChatUserMode.TTV_CHAT_USERMODE_STAFF) {
         var3 = new ChatComponentTranslation("stream.user.mode.staff");
         var3.b().a(g);
      }

      return var3;
   }

   @Override
   public void b() {
      int var1 = this.l / 3;
      int var2 = var1 - 130;
      this.n.add(new GuiButton(1, var1 * 0 + var2 / 2, this.m - 70, 130, 20, I18n.a("stream.userinfo.timeout")));
      this.n.add(new GuiButton(0, var1 * 1 + var2 / 2, this.m - 70, 130, 20, I18n.a("stream.userinfo.ban")));
      this.n.add(new GuiButton(2, var1 * 2 + var2 / 2, this.m - 70, 130, 20, I18n.a("stream.userinfo.mod")));
      this.n.add(new GuiButton(5, var1 * 0 + var2 / 2, this.m - 45, 130, 20, I18n.a("gui.cancel")));
      this.n.add(new GuiButton(3, var1 * 1 + var2 / 2, this.m - 45, 130, 20, I18n.a("stream.userinfo.unban")));
      this.n.add(new GuiButton(4, var1 * 2 + var2 / 2, this.m - 45, 130, 20, I18n.a("stream.userinfo.unmod")));
      int var3 = 0;

      for (IChatComponent var5 : this.r) {
         var3 = Math.max(var3, this.q.a(var5.d()));
      }

      this.t = this.l / 2 - var3 / 2;
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.l) {
         if (var1.k == 0) {
            this.s.f("/ban " + this.h.displayName);
         } else if (var1.k == 3) {
            this.s.f("/unban " + this.h.displayName);
         } else if (var1.k == 2) {
            this.s.f("/mod " + this.h.displayName);
         } else if (var1.k == 4) {
            this.s.f("/unmod " + this.h.displayName);
         } else if (var1.k == 1) {
            this.s.f("/timeout " + this.h.displayName);
         }

         this.j.a(null);
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      this.a(this.q, this.i.c(), this.l / 2, 70, 16777215);
      int var4 = 80;

      for (IChatComponent var6 : this.r) {
         this.c(this.q, var6.d(), this.t, var4, 16777215);
         var4 += this.q.a;
      }

      super.a(var1, var2, var3);
   }
}
