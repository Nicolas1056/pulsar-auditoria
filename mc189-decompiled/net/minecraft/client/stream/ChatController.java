package net.minecraft.client.stream;

import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tv.twitch.AuthToken;
import tv.twitch.Core;
import tv.twitch.ErrorCode;
import tv.twitch.StandardCoreAPI;
import tv.twitch.chat.Chat;
import tv.twitch.chat.ChatBadgeData;
import tv.twitch.chat.ChatChannelInfo;
import tv.twitch.chat.ChatEmoticonData;
import tv.twitch.chat.ChatEvent;
import tv.twitch.chat.ChatRawMessage;
import tv.twitch.chat.ChatTokenizationOption;
import tv.twitch.chat.ChatTokenizedMessage;
import tv.twitch.chat.ChatUserInfo;
import tv.twitch.chat.IChatAPIListener;
import tv.twitch.chat.IChatChannelListener;
import tv.twitch.chat.StandardChatAPI;

public class ChatController {
   private static final Logger q = LogManager.getLogger();
   protected ChatController.ChatListener a = null;
   protected String b = "";
   protected String c = "";
   protected String d = "";
   protected Core e = null;
   protected Chat f = null;
   protected ChatController.ChatState g = ChatController.ChatState.a;
   protected AuthToken h = new AuthToken();
   protected HashMap<String, ChatController.ChatChannelListener> i = new HashMap<>();
   protected int j = 128;
   protected ChatController.EnumEmoticonMode k = ChatController.EnumEmoticonMode.a;
   protected ChatController.EnumEmoticonMode l = ChatController.EnumEmoticonMode.a;
   protected ChatEmoticonData m = null;
   protected int n = 500;
   protected int o = 2000;
   protected IChatAPIListener p = new IChatAPIListener() {
      public void chatInitializationCallback(ErrorCode var1) {
         if (ErrorCode.succeeded(var1)) {
            ChatController.this.f.setMessageFlushInterval(ChatController.this.n);
            ChatController.this.f.setUserChangeEventInterval(ChatController.this.o);
            ChatController.this.r();
            ChatController.this.a(ChatController.ChatState.c);
         } else {
            ChatController.this.a(ChatController.ChatState.a);
         }

         try {
            if (ChatController.this.a != null) {
               ChatController.this.a.d(var1);
            }
         } catch (Exception var3) {
            ChatController.this.n(var3.toString());
         }
      }

      public void chatShutdownCallback(ErrorCode var1) {
         if (ErrorCode.succeeded(var1)) {
            ErrorCode var2 = ChatController.this.e.shutdown();
            if (ErrorCode.failed(var2)) {
               String var3 = ErrorCode.getString(var2);
               ChatController.this.n(String.format("Error shutting down the Twitch sdk: %s", var3));
            }

            ChatController.this.a(ChatController.ChatState.a);
         } else {
            ChatController.this.a(ChatController.ChatState.c);
            ChatController.this.n(String.format("Error shutting down Twith chat: %s", var1));
         }

         try {
            if (ChatController.this.a != null) {
               ChatController.this.a.e(var1);
            }
         } catch (Exception var4) {
            ChatController.this.n(var4.toString());
         }
      }

      public void chatEmoticonDataDownloadCallback(ErrorCode var1) {
         if (ErrorCode.succeeded(var1)) {
            ChatController.this.s();
         }
      }
   };

   public void a(ChatController.ChatListener var1) {
      this.a = var1;
   }

   public void a(AuthToken var1) {
      this.h = var1;
   }

   public void a(String var1) {
      this.c = var1;
   }

   public void c(String var1) {
      this.b = var1;
   }

   public ChatController.ChatState h() {
      return this.g;
   }

   public boolean d(String var1) {
      if (!this.i.containsKey(var1)) {
         return false;
      } else {
         ChatController.ChatChannelListener var2 = this.i.get(var1);
         return var2.a() == ChatController.EnumChannelState.c;
      }
   }

   public ChatController.EnumChannelState e(String var1) {
      if (!this.i.containsKey(var1)) {
         return ChatController.EnumChannelState.e;
      } else {
         ChatController.ChatChannelListener var2 = this.i.get(var1);
         return var2.a();
      }
   }

   public ChatController() {
      this.e = Core.getInstance();
      if (this.e == null) {
         this.e = new Core(new StandardCoreAPI());
      }

      this.f = new Chat(new StandardChatAPI());
   }

   public boolean n() {
      if (this.g != ChatController.ChatState.a) {
         return false;
      } else {
         this.a(ChatController.ChatState.b);
         ErrorCode var1 = this.e.initialize(this.c, null);
         if (ErrorCode.failed(var1)) {
            this.a(ChatController.ChatState.a);
            String var5 = ErrorCode.getString(var1);
            this.n(String.format("Error initializing Twitch sdk: %s", var5));
            return false;
         } else {
            this.l = this.k;
            HashSet var2 = new HashSet();
            switch (this.k) {
               case a:
                  var2.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_NONE);
                  break;
               case b:
                  var2.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_EMOTICON_URLS);
                  break;
               case c:
                  var2.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_EMOTICON_TEXTURES);
            }

            var1 = this.f.initialize(var2, this.p);
            if (ErrorCode.failed(var1)) {
               this.e.shutdown();
               this.a(ChatController.ChatState.a);
               String var3 = ErrorCode.getString(var1);
               this.n(String.format("Error initializing Twitch chat: %s", var3));
               return false;
            } else {
               this.a(ChatController.ChatState.c);
               return true;
            }
         }
      }
   }

   public boolean j(String var1) {
      return this.a(var1, false);
   }

   protected boolean a(String var1, boolean var2) {
      if (this.g != ChatController.ChatState.c) {
         return false;
      } else if (this.i.containsKey(var1)) {
         this.n("Already in channel: " + var1);
         return false;
      } else if (var1 != null && !var1.equals("")) {
         ChatController.ChatChannelListener var3 = new ChatController.ChatChannelListener(var1);
         this.i.put(var1, var3);
         boolean var4 = var3.a(var2);
         if (!var4) {
            this.i.remove(var1);
         }

         return var4;
      } else {
         return false;
      }
   }

   public boolean l(String var1) {
      if (this.g != ChatController.ChatState.c) {
         return false;
      } else if (!this.i.containsKey(var1)) {
         this.n("Not in channel: " + var1);
         return false;
      } else {
         ChatController.ChatChannelListener var2 = this.i.get(var1);
         return var2.g();
      }
   }

   public boolean o() {
      if (this.g != ChatController.ChatState.c) {
         return false;
      } else {
         ErrorCode var1 = this.f.shutdown();
         if (ErrorCode.failed(var1)) {
            String var2 = ErrorCode.getString(var1);
            this.n(String.format("Error shutting down chat: %s", var2));
            return false;
         } else {
            this.t();
            this.a(ChatController.ChatState.d);
            return true;
         }
      }
   }

   public void p() {
      if (this.h() != ChatController.ChatState.a) {
         this.o();
         if (this.h() == ChatController.ChatState.d) {
            while (this.h() != ChatController.ChatState.a) {
               try {
                  Thread.sleep(200L);
                  this.q();
               } catch (InterruptedException var2) {
               }
            }
         }
      }
   }

   public void q() {
      if (this.g != ChatController.ChatState.a) {
         ErrorCode var1 = this.f.flushEvents();
         if (ErrorCode.failed(var1)) {
            String var2 = ErrorCode.getString(var1);
            this.n(String.format("Error flushing chat events: %s", var2));
         }
      }
   }

   public boolean a(String var1, String var2) {
      if (this.g != ChatController.ChatState.c) {
         return false;
      } else if (!this.i.containsKey(var1)) {
         this.n("Not in channel: " + var1);
         return false;
      } else {
         ChatController.ChatChannelListener var3 = this.i.get(var1);
         return var3.b(var2);
      }
   }

   protected void a(ChatController.ChatState var1) {
      if (var1 != this.g) {
         this.g = var1;

         try {
            if (this.a != null) {
               this.a.a(var1);
            }
         } catch (Exception var3) {
            this.n(var3.toString());
         }
      }
   }

   protected void r() {
      if (this.l != ChatController.EnumEmoticonMode.a) {
         if (this.m == null) {
            ErrorCode var1 = this.f.downloadEmoticonData();
            if (ErrorCode.failed(var1)) {
               String var2 = ErrorCode.getString(var1);
               this.n(String.format("Error trying to download emoticon data: %s", var2));
            }
         }
      }
   }

   protected void s() {
      if (this.m == null) {
         this.m = new ChatEmoticonData();
         ErrorCode var1 = this.f.getEmoticonData(this.m);
         if (ErrorCode.succeeded(var1)) {
            try {
               if (this.a != null) {
                  this.a.d();
               }
            } catch (Exception var3) {
               this.n(var3.toString());
            }
         } else {
            this.n("Error preparing emoticon data: " + ErrorCode.getString(var1));
         }
      }
   }

   protected void t() {
      if (this.m != null) {
         ErrorCode var1 = this.f.clearEmoticonData();
         if (ErrorCode.succeeded(var1)) {
            this.m = null;

            try {
               if (this.a != null) {
                  this.a.e();
               }
            } catch (Exception var3) {
               this.n(var3.toString());
            }
         } else {
            this.n("Error clearing emoticon data: " + ErrorCode.getString(var1));
         }
      }
   }

   protected void n(String var1) {
      q.error(TwitchStream.a, "[Chat controller] {}", new Object[]{var1});
   }

   public class ChatChannelListener implements IChatChannelListener {
      protected String a = null;
      protected boolean b = false;
      protected ChatController.EnumChannelState c = ChatController.EnumChannelState.a;
      protected List<ChatUserInfo> d = Lists.newArrayList();
      protected LinkedList<ChatRawMessage> e = new LinkedList<>();
      protected LinkedList<ChatTokenizedMessage> f = new LinkedList<>();
      protected ChatBadgeData g = null;

      public ChatChannelListener(String var2) {
         this.a = var2;
      }

      public ChatController.EnumChannelState a() {
         return this.c;
      }

      public boolean a(boolean var1) {
         this.b = var1;
         ErrorCode var2 = ErrorCode.TTV_EC_SUCCESS;
         if (var1) {
            var2 = ChatController.this.f.connectAnonymous(this.a, this);
         } else {
            var2 = ChatController.this.f.connect(this.a, ChatController.this.b, ChatController.this.h.data, this);
         }

         if (ErrorCode.failed(var2)) {
            String var3 = ErrorCode.getString(var2);
            ChatController.this.n(String.format("Error connecting: %s", var3));
            this.d(this.a);
            return false;
         } else {
            this.a(ChatController.EnumChannelState.b);
            this.h();
            return true;
         }
      }

      public boolean g() {
         switch (this.c) {
            case c:
            case b:
               ErrorCode var1 = ChatController.this.f.disconnect(this.a);
               if (ErrorCode.failed(var1)) {
                  String var2 = ErrorCode.getString(var1);
                  ChatController.this.n(String.format("Error disconnecting: %s", var2));
                  return false;
               }

               this.a(ChatController.EnumChannelState.d);
               return true;
            case a:
            case e:
            case d:
            default:
               return false;
         }
      }

      protected void a(ChatController.EnumChannelState var1) {
         if (var1 != this.c) {
            this.c = var1;
         }
      }

      public void a(String var1) {
         if (ChatController.this.l == ChatController.EnumEmoticonMode.a) {
            this.e.clear();
            this.f.clear();
         } else {
            if (this.e.size() > 0) {
               ListIterator var2 = this.e.listIterator();

               while (var2.hasNext()) {
                  ChatRawMessage var3 = (ChatRawMessage)var2.next();
                  if (var3.userName.equals(var1)) {
                     var2.remove();
                  }
               }
            }

            if (this.f.size() > 0) {
               ListIterator var5 = this.f.listIterator();

               while (var5.hasNext()) {
                  ChatTokenizedMessage var6 = (ChatTokenizedMessage)var5.next();
                  if (var6.displayName.equals(var1)) {
                     var5.remove();
                  }
               }
            }
         }

         try {
            if (ChatController.this.a != null) {
               ChatController.this.a.a(this.a, var1);
            }
         } catch (Exception var4) {
            ChatController.this.n(var4.toString());
         }
      }

      public boolean b(String var1) {
         if (this.c != ChatController.EnumChannelState.c) {
            return false;
         } else {
            ErrorCode var2 = ChatController.this.f.sendMessage(this.a, var1);
            if (ErrorCode.failed(var2)) {
               String var3 = ErrorCode.getString(var2);
               ChatController.this.n(String.format("Error sending chat message: %s", var3));
               return false;
            } else {
               return true;
            }
         }
      }

      protected void h() {
         if (ChatController.this.l != ChatController.EnumEmoticonMode.a) {
            if (this.g == null) {
               ErrorCode var1 = ChatController.this.f.downloadBadgeData(this.a);
               if (ErrorCode.failed(var1)) {
                  String var2 = ErrorCode.getString(var1);
                  ChatController.this.n(String.format("Error trying to download badge data: %s", var2));
               }
            }
         }
      }

      protected void i() {
         if (this.g == null) {
            this.g = new ChatBadgeData();
            ErrorCode var1 = ChatController.this.f.getBadgeData(this.a, this.g);
            if (ErrorCode.succeeded(var1)) {
               try {
                  if (ChatController.this.a != null) {
                     ChatController.this.a.c(this.a);
                  }
               } catch (Exception var3) {
                  ChatController.this.n(var3.toString());
               }
            } else {
               ChatController.this.n("Error preparing badge data: " + ErrorCode.getString(var1));
            }
         }
      }

      protected void j() {
         if (this.g != null) {
            ErrorCode var1 = ChatController.this.f.clearBadgeData(this.a);
            if (ErrorCode.succeeded(var1)) {
               this.g = null;

               try {
                  if (ChatController.this.a != null) {
                     ChatController.this.a.d(this.a);
                  }
               } catch (Exception var3) {
                  ChatController.this.n(var3.toString());
               }
            } else {
               ChatController.this.n("Error releasing badge data: " + ErrorCode.getString(var1));
            }
         }
      }

      protected void c(String var1) {
         try {
            if (ChatController.this.a != null) {
               ChatController.this.a.a(var1);
            }
         } catch (Exception var3) {
            ChatController.this.n(var3.toString());
         }
      }

      protected void d(String var1) {
         try {
            if (ChatController.this.a != null) {
               ChatController.this.a.b(var1);
            }
         } catch (Exception var3) {
            ChatController.this.n(var3.toString());
         }
      }

      private void k() {
         if (this.c != ChatController.EnumChannelState.e) {
            this.a(ChatController.EnumChannelState.e);
            this.d(this.a);
            this.j();
         }
      }

      public void chatStatusCallback(String var1, ErrorCode var2) {
         if (!ErrorCode.succeeded(var2)) {
            ChatController.this.i.remove(var1);
            this.k();
         }
      }

      public void chatChannelMembershipCallback(String var1, ChatEvent var2, ChatChannelInfo var3) {
         switch (var2) {
            case TTV_CHAT_JOINED_CHANNEL:
               this.a(ChatController.EnumChannelState.c);
               this.c(var1);
               break;
            case TTV_CHAT_LEFT_CHANNEL:
               this.k();
         }
      }

      public void chatChannelUserChangeCallback(String var1, ChatUserInfo[] var2, ChatUserInfo[] var3, ChatUserInfo[] var4) {
         for (int var5 = 0; var5 < var3.length; var5++) {
            int var6 = this.d.indexOf(var3[var5]);
            if (var6 >= 0) {
               this.d.remove(var6);
            }
         }

         for (int var8 = 0; var8 < var4.length; var8++) {
            int var10 = this.d.indexOf(var4[var8]);
            if (var10 >= 0) {
               this.d.remove(var10);
            }

            this.d.add(var4[var8]);
         }

         for (int var9 = 0; var9 < var2.length; var9++) {
            this.d.add(var2[var9]);
         }

         try {
            if (ChatController.this.a != null) {
               ChatController.this.a.a(this.a, var2, var3, var4);
            }
         } catch (Exception var7) {
            ChatController.this.n(var7.toString());
         }
      }

      public void chatChannelRawMessageCallback(String var1, ChatRawMessage[] var2) {
         for (int var3 = 0; var3 < var2.length; var3++) {
            this.e.addLast(var2[var3]);
         }

         try {
            if (ChatController.this.a != null) {
               ChatController.this.a.a(this.a, var2);
            }
         } catch (Exception var4) {
            ChatController.this.n(var4.toString());
         }

         while (this.e.size() > ChatController.this.j) {
            this.e.removeFirst();
         }
      }

      public void chatChannelTokenizedMessageCallback(String var1, ChatTokenizedMessage[] var2) {
         for (int var3 = 0; var3 < var2.length; var3++) {
            this.f.addLast(var2[var3]);
         }

         try {
            if (ChatController.this.a != null) {
               ChatController.this.a.a(this.a, var2);
            }
         } catch (Exception var4) {
            ChatController.this.n(var4.toString());
         }

         while (this.f.size() > ChatController.this.j) {
            this.f.removeFirst();
         }
      }

      public void chatClearCallback(String var1, String var2) {
         this.a(var2);
      }

      public void chatBadgeDataDownloadCallback(String var1, ErrorCode var2) {
         if (ErrorCode.succeeded(var2)) {
            this.i();
         }
      }
   }

   public interface ChatListener {
      void d(ErrorCode var1);

      void e(ErrorCode var1);

      void d();

      void e();

      void a(ChatController.ChatState var1);

      void a(String var1, ChatTokenizedMessage[] var2);

      void a(String var1, ChatRawMessage[] var2);

      void a(String var1, ChatUserInfo[] var2, ChatUserInfo[] var3, ChatUserInfo[] var4);

      void a(String var1);

      void b(String var1);

      void a(String var1, String var2);

      void c(String var1);

      void d(String var1);
   }

   public static enum ChatState {
      a,
      b,
      c,
      d;
   }

   public static enum EnumChannelState {
      a,
      b,
      c,
      d,
      e;
   }

   public static enum EnumEmoticonMode {
      a,
      b,
      c;
   }
}
