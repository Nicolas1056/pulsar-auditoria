package net.minecraft.client.stream;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.properties.Property;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.stream.GuiTwitchUserMode;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.lwjgl.opengl.GL11;
import tv.twitch.AuthToken;
import tv.twitch.ErrorCode;
import tv.twitch.broadcast.EncodingCpuUsage;
import tv.twitch.broadcast.FrameBuffer;
import tv.twitch.broadcast.GameInfo;
import tv.twitch.broadcast.IngestList;
import tv.twitch.broadcast.IngestServer;
import tv.twitch.broadcast.StreamInfo;
import tv.twitch.broadcast.VideoParams;
import tv.twitch.chat.ChatRawMessage;
import tv.twitch.chat.ChatTokenizedMessage;
import tv.twitch.chat.ChatUserInfo;
import tv.twitch.chat.ChatUserMode;
import tv.twitch.chat.ChatUserSubscription;

public class TwitchStream implements BroadcastController.BroadcastListener, ChatController.ChatListener, IngestServerTester.IngestTestListener, IStream {
   private static final Logger b = LogManager.getLogger();
   public static final Marker a = MarkerManager.getMarker("STREAM");
   private final BroadcastController c;
   private final ChatController d;
   private String e;
   private final Minecraft f;
   private final IChatComponent g = new ChatComponentText("Twitch");
   private final Map<String, ChatUserInfo> h = Maps.newHashMap();
   private Framebuffer i;
   private boolean j;
   private int k = 30;
   private long l = 0L;
   private boolean m = false;
   private boolean n;
   private boolean o;
   private boolean p;
   private IStream.AuthFailureReason q = IStream.AuthFailureReason.a;
   private static boolean r;

   public TwitchStream(Minecraft var1, final Property var2) {
      this.f = var1;
      this.c = new BroadcastController();
      this.d = new ChatController();
      this.c.a(this);
      this.d.a(this);
      this.c.a("nmt37qblda36pvonovdkbopzfzw3wlq");
      this.d.a("nmt37qblda36pvonovdkbopzfzw3wlq");
      this.g.b().a(EnumChatFormatting.f);
      if (var2 != null && !Strings.isNullOrEmpty(var2.getValue()) && OpenGlHelper.l) {
         Thread var3 = new Thread("Twitch authenticator") {
            @Override
            public void run() {
               try {
                  URL var1x = new URL("https://api.twitch.tv/kraken?oauth_token=" + URLEncoder.encode(var2.getValue(), "UTF-8"));
                  String var2x = HttpUtil.a(var1x);
                  JsonObject var3x = JsonUtils.l(new JsonParser().parse(var2x), "Response");
                  JsonObject var4 = JsonUtils.s(var3x, "token");
                  if (JsonUtils.i(var4, "valid")) {
                     String var5 = JsonUtils.h(var4, "user_name");
                     TwitchStream.b.debug(TwitchStream.a, "Authenticated with twitch; username is {}", new Object[]{var5});
                     AuthToken var6 = new AuthToken();
                     var6.data = var2.getValue();
                     TwitchStream.this.c.a(var5, var6);
                     TwitchStream.this.d.c(var5);
                     TwitchStream.this.d.a(var6);
                     Runtime.getRuntime().addShutdownHook(new Thread("Twitch shutdown hook") {
                        @Override
                        public void run() {
                           TwitchStream.this.f();
                        }
                     });
                     TwitchStream.this.c.C();
                     TwitchStream.this.d.n();
                  } else {
                     TwitchStream.this.q = IStream.AuthFailureReason.b;
                     TwitchStream.b.error(TwitchStream.a, "Given twitch access token is invalid");
                  }
               } catch (IOException var7) {
                  TwitchStream.this.q = IStream.AuthFailureReason.a;
                  TwitchStream.b.error(TwitchStream.a, "Could not authenticate with twitch", var7);
               }
            }
         };
         var3.setDaemon(true);
         var3.start();
      }
   }

   @Override
   public void f() {
      b.debug(a, "Shutdown streaming");
      this.c.E();
      this.d.p();
   }

   @Override
   public void g() {
      int var1 = this.f.t.S;
      boolean var2 = this.e != null && this.d.d(this.e);
      boolean var3 = this.d.h() == ChatController.ChatState.c && (this.e == null || this.d.e(this.e) == ChatController.EnumChannelState.e);
      if (var1 == 2) {
         if (var2) {
            b.debug(a, "Disconnecting from twitch chat per user options");
            this.d.l(this.e);
         }
      } else if (var1 == 1) {
         if (var3 && this.c.q()) {
            b.debug(a, "Connecting to twitch chat per user options");
            this.F();
         }
      } else if (var1 == 0) {
         if (var2 && !this.k()) {
            b.debug(a, "Disconnecting from twitch chat as user is no longer streaming");
            this.d.l(this.e);
         } else if (var3 && this.k()) {
            b.debug(a, "Connecting to twitch chat as user is streaming");
            this.F();
         }
      }

      this.c.K();
      this.d.q();
   }

   protected void F() {
      ChatController.ChatState var1 = this.d.h();
      String var2 = this.c.l().name;
      this.e = var2;
      if (var1 != ChatController.ChatState.c) {
         b.warn("Invalid twitch chat state {}", new Object[]{var1});
      } else if (this.d.e(this.e) == ChatController.EnumChannelState.e) {
         this.d.j(var2);
      } else {
         b.warn("Invalid twitch chat state {}", new Object[]{var1});
      }
   }

   @Override
   public void h() {
      if (this.c.m() && !this.c.p()) {
         long var1 = System.nanoTime();
         long var3 = 1000000000 / this.k;
         long var5 = var1 - this.l;
         boolean var7 = var5 >= var3;
         if (var7) {
            FrameBuffer var8 = this.c.Q();
            Framebuffer var9 = this.f.b();
            this.i.a(true);
            GlStateManager.n(5889);
            GlStateManager.E();
            GlStateManager.D();
            GlStateManager.a(0.0, this.i.c, this.i.d, 0.0, 1000.0, 3000.0);
            GlStateManager.n(5888);
            GlStateManager.E();
            GlStateManager.D();
            GlStateManager.b(0.0F, 0.0F, -2000.0F);
            GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.b(0, 0, this.i.c, this.i.d);
            GlStateManager.w();
            GlStateManager.c();
            GlStateManager.k();
            float var10 = this.i.c;
            float var11 = this.i.d;
            float var12 = (float)var9.c / var9.a;
            float var13 = (float)var9.d / var9.b;
            var9.c();
            GL11.glTexParameterf(3553, 10241, 9729.0F);
            GL11.glTexParameterf(3553, 10240, 9729.0F);
            Tessellator var14 = Tessellator.a();
            WorldRenderer var15 = var14.c();
            var15.a(7, DefaultVertexFormats.g);
            var15.b(0.0, (double)var11, 0.0).a(0.0, (double)var13).d();
            var15.b((double)var10, (double)var11, 0.0).a((double)var12, (double)var13).d();
            var15.b((double)var10, 0.0, 0.0).a((double)var12, 0.0).d();
            var15.b(0.0, 0.0, 0.0).a(0.0, 0.0).d();
            var14.b();
            var9.d();
            GlStateManager.F();
            GlStateManager.n(5889);
            GlStateManager.F();
            GlStateManager.n(5888);
            this.c.a(var8);
            this.i.e();
            this.c.b(var8);
            this.l = var1;
         }
      }
   }

   @Override
   public boolean i() {
      return this.c.q();
   }

   @Override
   public boolean j() {
      return this.c.n();
   }

   @Override
   public boolean k() {
      return this.c.m();
   }

   @Override
   public void a(Metadata var1, long var2) {
      if (this.k() && this.j) {
         long var4 = this.c.x();
         if (!this.c.a(var1.c(), var4 + var2, var1.a(), var1.b())) {
            b.warn(a, "Couldn't send stream metadata action at {}: {}", new Object[]{var4 + var2, var1});
         } else {
            b.debug(a, "Sent stream metadata action at {}: {}", new Object[]{var4 + var2, var1});
         }
      }
   }

   @Override
   public void a(Metadata var1, long var2, long var4) {
      if (this.k() && this.j) {
         long var6 = this.c.x();
         String var8 = var1.a();
         String var9 = var1.b();
         long var10 = this.c.b(var1.c(), var6 + var2, var8, var9);
         if (var10 < 0L) {
            b.warn(a, "Could not send stream metadata sequence from {} to {}: {}", new Object[]{var6 + var2, var6 + var4, var1});
         } else if (this.c.a(var1.c(), var6 + var4, var10, var8, var9)) {
            b.debug(a, "Sent stream metadata sequence from {} to {}: {}", new Object[]{var6 + var2, var6 + var4, var1});
         } else {
            b.warn(a, "Half-sent stream metadata sequence from {} to {}: {}", new Object[]{var6 + var2, var6 + var4, var1});
         }
      }
   }

   @Override
   public boolean l() {
      return this.c.p();
   }

   @Override
   public void m() {
      if (this.c.G()) {
         b.debug(a, "Requested commercial from Twitch");
      } else {
         b.warn(a, "Could not request commercial from Twitch");
      }
   }

   @Override
   public void n() {
      this.c.I();
      this.o = true;
      this.p();
   }

   @Override
   public void o() {
      this.c.J();
      this.o = false;
      this.p();
   }

   @Override
   public void p() {
      if (this.k()) {
         float var1 = this.f.t.M;
         boolean var2 = this.o || var1 <= 0.0F;
         this.c.b(var2 ? 0.0F : var1);
         this.c.a(this.D() ? 0.0F : this.f.t.L);
      }
   }

   @Override
   public void q() {
      GameSettings var1 = this.f.t;
      VideoParams var2 = this.c.a(b(var1.N), a(var1.O), c(var1.K), (float)this.f.d / this.f.e);
      switch (var1.P) {
         case 0:
            var2.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_LOW;
            break;
         case 1:
            var2.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_MEDIUM;
            break;
         case 2:
            var2.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
      }

      if (this.i == null) {
         this.i = new Framebuffer(var2.outputWidth, var2.outputHeight, false);
      } else {
         this.i.a(var2.outputWidth, var2.outputHeight);
      }

      if (var1.R != null && var1.R.length() > 0) {
         for (IngestServer var6 : this.s()) {
            if (var6.serverUrl.equals(var1.R)) {
               this.c.a(var6);
               break;
            }
         }
      }

      this.k = var2.targetFps;
      this.j = var1.Q;
      this.c.a(var2);
      b.info(a, "Streaming at {}/{} at {} kbps to {}", new Object[]{var2.outputWidth, var2.outputHeight, var2.maxKbps, this.c.s().serverUrl});
      this.c.a(null, "Minecraft", null);
   }

   @Override
   public void r() {
      if (this.c.H()) {
         b.info(a, "Stopped streaming to Twitch");
      } else {
         b.warn(a, "Could not stop streaming to Twitch");
      }
   }

   @Override
   public void a(ErrorCode var1, AuthToken var2) {
   }

   @Override
   public void a(ErrorCode var1) {
      if (ErrorCode.succeeded(var1)) {
         b.debug(a, "Login attempt successful");
         this.n = true;
      } else {
         b.warn(a, "Login attempt unsuccessful: {} (error code {})", new Object[]{ErrorCode.getString(var1), var1.getValue()});
         this.n = false;
      }
   }

   @Override
   public void a(ErrorCode var1, GameInfo[] var2) {
   }

   @Override
   public void a(BroadcastController.BroadcastState var1) {
      b.debug(a, "Broadcast state changed to {}", new Object[]{var1});
      if (var1 == BroadcastController.BroadcastState.b) {
         this.c.a(BroadcastController.BroadcastState.d);
      }
   }

   @Override
   public void a() {
      b.info(a, "Logged out of twitch");
   }

   @Override
   public void a(StreamInfo var1) {
      b.debug(a, "Stream info updated; {} viewers on stream ID {}", new Object[]{var1.viewers, var1.streamId});
   }

   @Override
   public void a(IngestList var1) {
   }

   @Override
   public void b(ErrorCode var1) {
      b.warn(a, "Issue submitting frame: {} (Error code {})", new Object[]{ErrorCode.getString(var1), var1.getValue()});
      this.f.q.d().a(new ChatComponentText("Issue streaming frame: " + var1 + " (" + ErrorCode.getString(var1) + ")"), 2);
   }

   @Override
   public void b() {
      this.p();
      b.info(a, "Broadcast to Twitch has started");
   }

   @Override
   public void c() {
      b.info(a, "Broadcast to Twitch has stopped");
   }

   @Override
   public void c(ErrorCode var1) {
      if (var1 == ErrorCode.TTV_EC_SOUNDFLOWER_NOT_INSTALLED) {
         ChatComponentTranslation var2 = new ChatComponentTranslation("stream.unavailable.soundflower.chat.link");
         var2.b()
            .a(
               new ClickEvent(
                  ClickEvent.Action.a, "https://help.mojang.com/customer/portal/articles/1374877-configuring-soundflower-for-streaming-on-apple-computers"
               )
            );
         var2.b().d(true);
         ChatComponentTranslation var3 = new ChatComponentTranslation("stream.unavailable.soundflower.chat", var2);
         var3.b().a(EnumChatFormatting.e);
         this.f.q.d().a(var3);
      } else {
         ChatComponentTranslation var4 = new ChatComponentTranslation("stream.unavailable.unknown.chat", ErrorCode.getString(var1));
         var4.b().a(EnumChatFormatting.e);
         this.f.q.d().a(var4);
      }
   }

   @Override
   public void a(IngestServerTester var1, IngestServerTester.IngestTestState var2) {
      b.debug(a, "Ingest test state changed to {}", new Object[]{var2});
      if (var2 == IngestServerTester.IngestTestState.f) {
         this.m = true;
      }
   }

   public static int a(float var0) {
      return MathHelper.d(10.0F + var0 * 50.0F);
   }

   public static int b(float var0) {
      return MathHelper.d(230.0F + var0 * 3270.0F);
   }

   public static float c(float var0) {
      return 0.1F + var0 * 0.1F;
   }

   @Override
   public IngestServer[] s() {
      return this.c.t().getServers();
   }

   @Override
   public void u() {
      IngestServerTester var1 = this.c.M();
      if (var1 != null) {
         var1.a(this);
      }
   }

   @Override
   public IngestServerTester v() {
      return this.c.w();
   }

   @Override
   public boolean w() {
      return this.c.o();
   }

   @Override
   public int x() {
      return this.k() ? this.c.j().viewers : 0;
   }

   @Override
   public void d(ErrorCode var1) {
      if (ErrorCode.failed(var1)) {
         b.error(a, "Chat failed to initialize");
      }
   }

   @Override
   public void e(ErrorCode var1) {
      if (ErrorCode.failed(var1)) {
         b.error(a, "Chat failed to shutdown");
      }
   }

   @Override
   public void a(ChatController.ChatState var1) {
   }

   @Override
   public void a(String var1, ChatRawMessage[] var2) {
      for (ChatRawMessage var6 : var2) {
         this.a(var6.userName, var6);
         if (this.a(var6.modes, var6.subscriptions, this.f.t.T)) {
            ChatComponentText var7 = new ChatComponentText(var6.userName);
            ChatComponentTranslation var8 = new ChatComponentTranslation(
               "chat.stream." + (var6.action ? "emote" : "text"), this.g, var7, EnumChatFormatting.a(var6.message)
            );
            if (var6.action) {
               var8.b().b(true);
            }

            ChatComponentText var9 = new ChatComponentText("");
            var9.a(new ChatComponentTranslation("stream.userinfo.chatTooltip"));

            for (IChatComponent var11 : GuiTwitchUserMode.a(var6.modes, var6.subscriptions, null)) {
               var9.a("\n");
               var9.a(var11);
            }

            var7.b().a(new HoverEvent(HoverEvent.Action.a, var9));
            var7.b().a(new ClickEvent(ClickEvent.Action.d, var6.userName));
            this.f.q.d().a(var8);
         }
      }
   }

   @Override
   public void a(String var1, ChatTokenizedMessage[] var2) {
   }

   private void a(String var1, ChatRawMessage var2) {
      ChatUserInfo var3 = this.h.get(var1);
      if (var3 == null) {
         var3 = new ChatUserInfo();
         var3.displayName = var1;
         this.h.put(var1, var3);
      }

      var3.subscriptions = var2.subscriptions;
      var3.modes = var2.modes;
      var3.nameColorARGB = var2.nameColorARGB;
   }

   private boolean a(Set<ChatUserMode> var1, Set<ChatUserSubscription> var2, int var3) {
      if (var1.contains(ChatUserMode.TTV_CHAT_USERMODE_BANNED)) {
         return false;
      } else if (var1.contains(ChatUserMode.TTV_CHAT_USERMODE_ADMINSTRATOR)) {
         return true;
      } else if (var1.contains(ChatUserMode.TTV_CHAT_USERMODE_MODERATOR)) {
         return true;
      } else if (var1.contains(ChatUserMode.TTV_CHAT_USERMODE_STAFF)) {
         return true;
      } else if (var3 == 0) {
         return true;
      } else {
         return var3 == 1 ? var2.contains(ChatUserSubscription.TTV_CHAT_USERSUB_SUBSCRIBER) : false;
      }
   }

   @Override
   public void a(String var1, ChatUserInfo[] var2, ChatUserInfo[] var3, ChatUserInfo[] var4) {
      for (ChatUserInfo var8 : var3) {
         this.h.remove(var8.displayName);
      }

      for (ChatUserInfo var15 : var4) {
         this.h.put(var15.displayName, var15);
      }

      for (ChatUserInfo var16 : var2) {
         this.h.put(var16.displayName, var16);
      }
   }

   @Override
   public void a(String var1) {
      b.debug(a, "Chat connected");
   }

   @Override
   public void b(String var1) {
      b.debug(a, "Chat disconnected");
      this.h.clear();
   }

   @Override
   public void a(String var1, String var2) {
   }

   @Override
   public void d() {
   }

   @Override
   public void e() {
   }

   @Override
   public void c(String var1) {
   }

   @Override
   public void d(String var1) {
   }

   @Override
   public boolean y() {
      return this.e != null && this.e.equals(this.c.l().name);
   }

   @Override
   public String z() {
      return this.e;
   }

   @Override
   public ChatUserInfo e(String var1) {
      return this.h.get(var1);
   }

   @Override
   public void f(String var1) {
      this.d.a(this.e, var1);
   }

   @Override
   public boolean A() {
      return r && this.c.b();
   }

   @Override
   public ErrorCode B() {
      return !r ? ErrorCode.TTV_EC_OS_TOO_OLD : this.c.A();
   }

   @Override
   public boolean C() {
      return this.n;
   }

   @Override
   public void a(boolean var1) {
      this.p = var1;
      this.p();
   }

   @Override
   public boolean D() {
      boolean var1 = this.f.t.U == 1;
      return this.o || this.f.t.L <= 0.0F || var1 != this.p;
   }

   @Override
   public IStream.AuthFailureReason E() {
      return this.q;
   }

   static {
      try {
         if (Util.a() == Util.EnumOS.c) {
            System.loadLibrary("avutil-ttv-51");
            System.loadLibrary("swresample-ttv-0");
            System.loadLibrary("libmp3lame-ttv");
            if (System.getProperty("os.arch").contains("64")) {
               System.loadLibrary("libmfxsw64");
            } else {
               System.loadLibrary("libmfxsw32");
            }
         }

         r = true;
      } catch (Throwable var1) {
         r = false;
      }
   }
}
