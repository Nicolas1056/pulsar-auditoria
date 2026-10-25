package net.minecraft.client.settings;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.stream.TwitchStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class GameSettings {
   private static final Logger aP = LogManager.getLogger();
   private static final Gson aQ = new Gson();
   private static final ParameterizedType aR = new ParameterizedType() {
      @Override
      public Type[] getActualTypeArguments() {
         return new Type[]{String.class};
      }

      @Override
      public Type getRawType() {
         return List.class;
      }

      @Override
      public Type getOwnerType() {
         return null;
      }
   };
   private static final String[] aS = new String[]{"options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large"};
   private static final String[] aT = new String[]{"options.particles.all", "options.particles.decreased", "options.particles.minimal"};
   private static final String[] aU = new String[]{"options.ao.off", "options.ao.min", "options.ao.max"};
   private static final String[] aV = new String[]{"options.stream.compression.low", "options.stream.compression.medium", "options.stream.compression.high"};
   private static final String[] aW = new String[]{
      "options.stream.chat.enabled.streaming", "options.stream.chat.enabled.always", "options.stream.chat.enabled.never"
   };
   private static final String[] aX = new String[]{
      "options.stream.chat.userFilter.all", "options.stream.chat.userFilter.subs", "options.stream.chat.userFilter.mods"
   };
   private static final String[] aY = new String[]{"options.stream.mic_toggle.mute", "options.stream.mic_toggle.talk"};
   private static final String[] aZ = new String[]{"options.off", "options.graphics.fast", "options.graphics.fancy"};
   public float a = 0.5F;
   public boolean b;
   public int c = -1;
   public boolean d = true;
   public boolean e;
   public boolean f = true;
   public int g = 120;
   public int h = 2;
   public boolean i = true;
   public int j = 2;
   public List<String> k = Lists.newArrayList();
   public List<String> l = Lists.newArrayList();
   public EntityPlayer.EnumChatVisibility m = EntityPlayer.EnumChatVisibility.a;
   public boolean n = true;
   public boolean o = true;
   public boolean p = true;
   public float q = 1.0F;
   public boolean r = true;
   public boolean s;
   public boolean t = true;
   public boolean u = false;
   public boolean v = true;
   public boolean w = false;
   public boolean x;
   public boolean y;
   public boolean z = true;
   private final Set<EnumPlayerModelParts> ba = Sets.newHashSet(EnumPlayerModelParts.values());
   public boolean A;
   public int B;
   public int C;
   public boolean D = true;
   public float E = 1.0F;
   public float F = 1.0F;
   public float G = 0.44366196F;
   public float H = 1.0F;
   public boolean I = true;
   public int J = 4;
   private Map<SoundCategory, Float> bb = Maps.newEnumMap(SoundCategory.class);
   public float K = 0.5F;
   public float L = 1.0F;
   public float M = 1.0F;
   public float N = 0.5412844F;
   public float O = 0.31690142F;
   public int P = 1;
   public boolean Q = true;
   public String R = "";
   public int S = 0;
   public int T = 0;
   public int U = 0;
   public boolean V = true;
   public boolean W = true;
   public boolean X = true;
   public KeyBinding Y = new KeyBinding("key.forward", 17, "key.categories.movement");
   public KeyBinding Z = new KeyBinding("key.left", 30, "key.categories.movement");
   public KeyBinding aa = new KeyBinding("key.back", 31, "key.categories.movement");
   public KeyBinding ab = new KeyBinding("key.right", 32, "key.categories.movement");
   public KeyBinding ac = new KeyBinding("key.jump", 57, "key.categories.movement");
   public KeyBinding ad = new KeyBinding("key.sneak", 42, "key.categories.movement");
   public KeyBinding ae = new KeyBinding("key.sprint", 29, "key.categories.movement");
   public KeyBinding af = new KeyBinding("key.inventory", 18, "key.categories.inventory");
   public KeyBinding ag = new KeyBinding("key.use", -99, "key.categories.gameplay");
   public KeyBinding ah = new KeyBinding("key.drop", 16, "key.categories.gameplay");
   public KeyBinding ai = new KeyBinding("key.attack", -100, "key.categories.gameplay");
   public KeyBinding aj = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
   public KeyBinding ak = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
   public KeyBinding al = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
   public KeyBinding am = new KeyBinding("key.command", 53, "key.categories.multiplayer");
   public KeyBinding an = new KeyBinding("key.screenshot", 60, "key.categories.misc");
   public KeyBinding ao = new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
   public KeyBinding ap = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
   public KeyBinding aq = new KeyBinding("key.fullscreen", 87, "key.categories.misc");
   public KeyBinding ar = new KeyBinding("key.spectatorOutlines", 0, "key.categories.misc");
   public KeyBinding as = new KeyBinding("key.streamStartStop", 64, "key.categories.stream");
   public KeyBinding at = new KeyBinding("key.streamPauseUnpause", 65, "key.categories.stream");
   public KeyBinding au = new KeyBinding("key.streamCommercial", 0, "key.categories.stream");
   public KeyBinding av = new KeyBinding("key.streamToggleMic", 0, "key.categories.stream");
   public KeyBinding[] aw = new KeyBinding[]{
      new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"),
      new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"),
      new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"),
      new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"),
      new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"),
      new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"),
      new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"),
      new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"),
      new KeyBinding("key.hotbar.9", 10, "key.categories.inventory")
   };
   public KeyBinding[] ax = (KeyBinding[])ArrayUtils.addAll(
      new KeyBinding[]{
         this.ai,
         this.ag,
         this.Y,
         this.Z,
         this.aa,
         this.ab,
         this.ac,
         this.ad,
         this.ae,
         this.ah,
         this.af,
         this.ak,
         this.al,
         this.aj,
         this.am,
         this.an,
         this.ao,
         this.ap,
         this.as,
         this.at,
         this.au,
         this.av,
         this.aq,
         this.ar
      },
      this.aw
   );
   protected Minecraft ay;
   private File bc;
   public EnumDifficulty az = EnumDifficulty.c;
   public boolean aA;
   public int aB;
   public boolean aC;
   public boolean aD;
   public boolean aE;
   public String aF = "";
   public boolean aG;
   public boolean aH;
   public float aI = 70.0F;
   public float aJ;
   public float aK;
   public int aL;
   public int aM;
   public String aN = "en_US";
   public boolean aO = false;

   public GameSettings(Minecraft var1, File var2) {
      this.ay = var1;
      this.bc = new File(var2, "options.txt");
      if (var1.U() && Runtime.getRuntime().maxMemory() >= 1000000000L) {
         GameSettings.Options.f.a(32.0F);
      } else {
         GameSettings.Options.f.a(16.0F);
      }

      this.c = var1.U() ? 12 : 8;
      this.a();
   }

   public GameSettings() {
   }

   public static String c(int var0) {
      if (var0 < 0) {
         return I18n.a("key.mouseButton", var0 + 101);
      } else {
         return var0 < 256 ? Keyboard.getKeyName(var0) : String.format("%c", (char)(var0 - 256)).toUpperCase();
      }
   }

   public static boolean a(KeyBinding var0) {
      if (var0.i() == 0) {
         return false;
      } else {
         return var0.i() < 0 ? Mouse.isButtonDown(var0.i() + 100) : Keyboard.isKeyDown(var0.i());
      }
   }

   public void a(KeyBinding var1, int var2) {
      var1.b(var2);
      this.b();
   }

   public void a(GameSettings.Options var1, float var2) {
      if (var1 == GameSettings.Options.b) {
         this.a = var2;
      }

      if (var1 == GameSettings.Options.c) {
         this.aI = var2;
      }

      if (var1 == GameSettings.Options.d) {
         this.aJ = var2;
      }

      if (var1 == GameSettings.Options.i) {
         this.g = (int)var2;
      }

      if (var1 == GameSettings.Options.s) {
         this.q = var2;
         this.ay.q.d().b();
      }

      if (var1 == GameSettings.Options.B) {
         this.H = var2;
         this.ay.q.d().b();
      }

      if (var1 == GameSettings.Options.C) {
         this.G = var2;
         this.ay.q.d().b();
      }

      if (var1 == GameSettings.Options.A) {
         this.F = var2;
         this.ay.q.d().b();
      }

      if (var1 == GameSettings.Options.z) {
         this.E = var2;
         this.ay.q.d().b();
      }

      if (var1 == GameSettings.Options.D) {
         int var3 = this.J;
         this.J = (int)var2;
         if (var3 != var2) {
            this.ay.T().a(this.J);
            this.ay.P().a(TextureMap.g);
            this.ay.T().a(false, this.J > 0);
            this.ay.B();
         }
      }

      if (var1 == GameSettings.Options.P) {
         this.v = !this.v;
         this.ay.g.a();
      }

      if (var1 == GameSettings.Options.f) {
         this.c = (int)var2;
         this.ay.g.m();
      }

      if (var1 == GameSettings.Options.F) {
         this.K = var2;
      }

      if (var1 == GameSettings.Options.G) {
         this.L = var2;
         this.ay.Y().p();
      }

      if (var1 == GameSettings.Options.H) {
         this.M = var2;
         this.ay.Y().p();
      }

      if (var1 == GameSettings.Options.I) {
         this.N = var2;
      }

      if (var1 == GameSettings.Options.J) {
         this.O = var2;
      }
   }

   public void a(GameSettings.Options var1, int var2) {
      if (var1 == GameSettings.Options.a) {
         this.b = !this.b;
      }

      if (var1 == GameSettings.Options.n) {
         this.aL = this.aL + var2 & 3;
      }

      if (var1 == GameSettings.Options.o) {
         this.aM = (this.aM + var2) % 3;
      }

      if (var1 == GameSettings.Options.g) {
         this.d = !this.d;
      }

      if (var1 == GameSettings.Options.k) {
         this.h = (this.h + var2) % 3;
      }

      if (var1 == GameSettings.Options.E) {
         this.aO = !this.aO;
         this.ay.k.a(this.ay.S().a() || this.aO);
      }

      if (var1 == GameSettings.Options.j) {
         this.f = !this.f;
      }

      if (var1 == GameSettings.Options.h) {
         this.e = !this.e;
         this.ay.e();
      }

      if (var1 == GameSettings.Options.l) {
         this.i = !this.i;
         this.ay.g.a();
      }

      if (var1 == GameSettings.Options.m) {
         this.j = (this.j + var2) % 3;
         this.ay.g.a();
      }

      if (var1 == GameSettings.Options.p) {
         this.m = EntityPlayer.EnumChatVisibility.a((this.m.a() + var2) % 3);
      }

      if (var1 == GameSettings.Options.K) {
         this.P = (this.P + var2) % 3;
      }

      if (var1 == GameSettings.Options.L) {
         this.Q = !this.Q;
      }

      if (var1 == GameSettings.Options.M) {
         this.S = (this.S + var2) % 3;
      }

      if (var1 == GameSettings.Options.N) {
         this.T = (this.T + var2) % 3;
      }

      if (var1 == GameSettings.Options.O) {
         this.U = (this.U + var2) % 2;
      }

      if (var1 == GameSettings.Options.q) {
         this.n = !this.n;
      }

      if (var1 == GameSettings.Options.r) {
         this.o = !this.o;
      }

      if (var1 == GameSettings.Options.t) {
         this.p = !this.p;
      }

      if (var1 == GameSettings.Options.u) {
         this.r = !this.r;
      }

      if (var1 == GameSettings.Options.y) {
         this.A = !this.A;
      }

      if (var1 == GameSettings.Options.v) {
         this.s = !this.s;
         if (this.ay.K() != this.s) {
            this.ay.q();
         }
      }

      if (var1 == GameSettings.Options.w) {
         this.t = !this.t;
         Display.setVSyncEnabled(this.t);
      }

      if (var1 == GameSettings.Options.x) {
         this.u = !this.u;
         this.ay.g.a();
      }

      if (var1 == GameSettings.Options.P) {
         this.v = !this.v;
         this.ay.g.a();
      }

      if (var1 == GameSettings.Options.Q) {
         this.w = !this.w;
      }

      if (var1 == GameSettings.Options.R) {
         this.W = !this.W;
      }

      if (var1 == GameSettings.Options.S) {
         this.X = !this.X;
      }

      this.b();
   }

   public float a(GameSettings.Options var1) {
      if (var1 == GameSettings.Options.c) {
         return this.aI;
      } else if (var1 == GameSettings.Options.d) {
         return this.aJ;
      } else if (var1 == GameSettings.Options.e) {
         return this.aK;
      } else if (var1 == GameSettings.Options.b) {
         return this.a;
      } else if (var1 == GameSettings.Options.s) {
         return this.q;
      } else if (var1 == GameSettings.Options.B) {
         return this.H;
      } else if (var1 == GameSettings.Options.C) {
         return this.G;
      } else if (var1 == GameSettings.Options.z) {
         return this.E;
      } else if (var1 == GameSettings.Options.A) {
         return this.F;
      } else if (var1 == GameSettings.Options.i) {
         return this.g;
      } else if (var1 == GameSettings.Options.D) {
         return this.J;
      } else if (var1 == GameSettings.Options.f) {
         return this.c;
      } else if (var1 == GameSettings.Options.F) {
         return this.K;
      } else if (var1 == GameSettings.Options.G) {
         return this.L;
      } else if (var1 == GameSettings.Options.H) {
         return this.M;
      } else if (var1 == GameSettings.Options.I) {
         return this.N;
      } else {
         return var1 == GameSettings.Options.J ? this.O : 0.0F;
      }
   }

   public boolean b(GameSettings.Options var1) {
      switch (var1) {
         case a:
            return this.b;
         case g:
            return this.d;
         case h:
            return this.e;
         case j:
            return this.f;
         case q:
            return this.n;
         case r:
            return this.o;
         case t:
            return this.p;
         case u:
            return this.r;
         case v:
            return this.s;
         case w:
            return this.t;
         case x:
            return this.u;
         case y:
            return this.A;
         case L:
            return this.Q;
         case E:
            return this.aO;
         case P:
            return this.v;
         case Q:
            return this.w;
         case R:
            return this.W;
         case S:
            return this.X;
         default:
            return false;
      }
   }

   private static String a(String[] var0, int var1) {
      if (var1 < 0 || var1 >= var0.length) {
         var1 = 0;
      }

      return I18n.a(var0[var1]);
   }

   public String c(GameSettings.Options var1) {
      String var2 = I18n.a(var1.d()) + ": ";
      if (var1.a()) {
         float var6 = this.a(var1);
         float var4 = var1.c(var6);
         if (var1 == GameSettings.Options.b) {
            if (var4 == 0.0F) {
               return var2 + I18n.a("options.sensitivity.min");
            } else {
               return var4 == 1.0F ? var2 + I18n.a("options.sensitivity.max") : var2 + (int)(var4 * 200.0F) + "%";
            }
         } else if (var1 == GameSettings.Options.c) {
            if (var6 == 70.0F) {
               return var2 + I18n.a("options.fov.min");
            } else {
               return var6 == 110.0F ? var2 + I18n.a("options.fov.max") : var2 + (int)var6;
            }
         } else if (var1 == GameSettings.Options.i) {
            return var6 == var1.Y ? var2 + I18n.a("options.framerateLimit.max") : var2 + (int)var6 + " fps";
         } else if (var1 == GameSettings.Options.k) {
            return var6 == var1.X ? var2 + I18n.a("options.cloudHeight.min") : var2 + ((int)var6 + 128);
         } else if (var1 == GameSettings.Options.d) {
            if (var4 == 0.0F) {
               return var2 + I18n.a("options.gamma.min");
            } else {
               return var4 == 1.0F ? var2 + I18n.a("options.gamma.max") : var2 + "+" + (int)(var4 * 100.0F) + "%";
            }
         } else if (var1 == GameSettings.Options.e) {
            return var2 + (int)(var4 * 400.0F) + "%";
         } else if (var1 == GameSettings.Options.s) {
            return var2 + (int)(var4 * 90.0F + 10.0F) + "%";
         } else if (var1 == GameSettings.Options.C) {
            return var2 + GuiNewChat.b(var4) + "px";
         } else if (var1 == GameSettings.Options.B) {
            return var2 + GuiNewChat.b(var4) + "px";
         } else if (var1 == GameSettings.Options.A) {
            return var2 + GuiNewChat.a(var4) + "px";
         } else if (var1 == GameSettings.Options.f) {
            return var2 + (int)var6 + " chunks";
         } else if (var1 == GameSettings.Options.D) {
            return var6 == 0.0F ? var2 + I18n.a("options.off") : var2 + (int)var6;
         } else if (var1 == GameSettings.Options.J) {
            return var2 + TwitchStream.a(var4) + " fps";
         } else if (var1 == GameSettings.Options.I) {
            return var2 + TwitchStream.b(var4) + " Kbps";
         } else if (var1 == GameSettings.Options.F) {
            return var2 + String.format("%.3f bpp", TwitchStream.c(var4));
         } else {
            return var4 == 0.0F ? var2 + I18n.a("options.off") : var2 + (int)(var4 * 100.0F) + "%";
         }
      } else if (var1.b()) {
         boolean var5 = this.b(var1);
         return var5 ? var2 + I18n.a("options.on") : var2 + I18n.a("options.off");
      } else if (var1 == GameSettings.Options.n) {
         return var2 + a(aS, this.aL);
      } else if (var1 == GameSettings.Options.p) {
         return var2 + I18n.a(this.m.b());
      } else if (var1 == GameSettings.Options.o) {
         return var2 + a(aT, this.aM);
      } else if (var1 == GameSettings.Options.m) {
         return var2 + a(aU, this.j);
      } else if (var1 == GameSettings.Options.K) {
         return var2 + a(aV, this.P);
      } else if (var1 == GameSettings.Options.M) {
         return var2 + a(aW, this.S);
      } else if (var1 == GameSettings.Options.N) {
         return var2 + a(aX, this.T);
      } else if (var1 == GameSettings.Options.O) {
         return var2 + a(aY, this.U);
      } else if (var1 == GameSettings.Options.k) {
         return var2 + a(aZ, this.h);
      } else if (var1 == GameSettings.Options.l) {
         if (this.i) {
            return var2 + I18n.a("options.graphics.fancy");
         } else {
            String var3 = "options.graphics.fast";
            return var2 + I18n.a("options.graphics.fast");
         }
      } else {
         return var2;
      }
   }

   public void a() {
      try {
         if (!this.bc.exists()) {
            return;
         }

         BufferedReader var1 = new BufferedReader(new FileReader(this.bc));
         String var2 = "";
         this.bb.clear();

         while ((var2 = var1.readLine()) != null) {
            try {
               String[] var3 = var2.split(":");
               if (var3[0].equals("mouseSensitivity")) {
                  this.a = this.a(var3[1]);
               }

               if (var3[0].equals("fov")) {
                  this.aI = this.a(var3[1]) * 40.0F + 70.0F;
               }

               if (var3[0].equals("gamma")) {
                  this.aJ = this.a(var3[1]);
               }

               if (var3[0].equals("saturation")) {
                  this.aK = this.a(var3[1]);
               }

               if (var3[0].equals("invertYMouse")) {
                  this.b = var3[1].equals("true");
               }

               if (var3[0].equals("renderDistance")) {
                  this.c = Integer.parseInt(var3[1]);
               }

               if (var3[0].equals("guiScale")) {
                  this.aL = Integer.parseInt(var3[1]);
               }

               if (var3[0].equals("particles")) {
                  this.aM = Integer.parseInt(var3[1]);
               }

               if (var3[0].equals("bobView")) {
                  this.d = var3[1].equals("true");
               }

               if (var3[0].equals("anaglyph3d")) {
                  this.e = var3[1].equals("true");
               }

               if (var3[0].equals("maxFps")) {
                  this.g = Integer.parseInt(var3[1]);
               }

               if (var3[0].equals("fboEnable")) {
                  this.f = var3[1].equals("true");
               }

               if (var3[0].equals("difficulty")) {
                  this.az = EnumDifficulty.a(Integer.parseInt(var3[1]));
               }

               if (var3[0].equals("fancyGraphics")) {
                  this.i = var3[1].equals("true");
               }

               if (var3[0].equals("ao")) {
                  if (var3[1].equals("true")) {
                     this.j = 2;
                  } else if (var3[1].equals("false")) {
                     this.j = 0;
                  } else {
                     this.j = Integer.parseInt(var3[1]);
                  }
               }

               if (var3[0].equals("renderClouds")) {
                  if (var3[1].equals("true")) {
                     this.h = 2;
                  } else if (var3[1].equals("false")) {
                     this.h = 0;
                  } else if (var3[1].equals("fast")) {
                     this.h = 1;
                  }
               }

               if (var3[0].equals("resourcePacks")) {
                  this.k = (List<String>)aQ.fromJson(var2.substring(var2.indexOf(58) + 1), aR);
                  if (this.k == null) {
                     this.k = Lists.newArrayList();
                  }
               }

               if (var3[0].equals("incompatibleResourcePacks")) {
                  this.l = (List<String>)aQ.fromJson(var2.substring(var2.indexOf(58) + 1), aR);
                  if (this.l == null) {
                     this.l = Lists.newArrayList();
                  }
               }

               if (var3[0].equals("lastServer") && var3.length >= 2) {
                  this.aF = var2.substring(var2.indexOf(58) + 1);
               }

               if (var3[0].equals("lang") && var3.length >= 2) {
                  this.aN = var3[1];
               }

               if (var3[0].equals("chatVisibility")) {
                  this.m = EntityPlayer.EnumChatVisibility.a(Integer.parseInt(var3[1]));
               }

               if (var3[0].equals("chatColors")) {
                  this.n = var3[1].equals("true");
               }

               if (var3[0].equals("chatLinks")) {
                  this.o = var3[1].equals("true");
               }

               if (var3[0].equals("chatLinksPrompt")) {
                  this.p = var3[1].equals("true");
               }

               if (var3[0].equals("chatOpacity")) {
                  this.q = this.a(var3[1]);
               }

               if (var3[0].equals("snooperEnabled")) {
                  this.r = var3[1].equals("true");
               }

               if (var3[0].equals("fullscreen")) {
                  this.s = var3[1].equals("true");
               }

               if (var3[0].equals("enableVsync")) {
                  this.t = var3[1].equals("true");
               }

               if (var3[0].equals("useVbo")) {
                  this.u = var3[1].equals("true");
               }

               if (var3[0].equals("hideServerAddress")) {
                  this.x = var3[1].equals("true");
               }

               if (var3[0].equals("advancedItemTooltips")) {
                  this.y = var3[1].equals("true");
               }

               if (var3[0].equals("pauseOnLostFocus")) {
                  this.z = var3[1].equals("true");
               }

               if (var3[0].equals("touchscreen")) {
                  this.A = var3[1].equals("true");
               }

               if (var3[0].equals("overrideHeight")) {
                  this.C = Integer.parseInt(var3[1]);
               }

               if (var3[0].equals("overrideWidth")) {
                  this.B = Integer.parseInt(var3[1]);
               }

               if (var3[0].equals("heldItemTooltips")) {
                  this.D = var3[1].equals("true");
               }

               if (var3[0].equals("chatHeightFocused")) {
                  this.H = this.a(var3[1]);
               }

               if (var3[0].equals("chatHeightUnfocused")) {
                  this.G = this.a(var3[1]);
               }

               if (var3[0].equals("chatScale")) {
                  this.E = this.a(var3[1]);
               }

               if (var3[0].equals("chatWidth")) {
                  this.F = this.a(var3[1]);
               }

               if (var3[0].equals("showInventoryAchievementHint")) {
                  this.I = var3[1].equals("true");
               }

               if (var3[0].equals("mipmapLevels")) {
                  this.J = Integer.parseInt(var3[1]);
               }

               if (var3[0].equals("streamBytesPerPixel")) {
                  this.K = this.a(var3[1]);
               }

               if (var3[0].equals("streamMicVolume")) {
                  this.L = this.a(var3[1]);
               }

               if (var3[0].equals("streamSystemVolume")) {
                  this.M = this.a(var3[1]);
               }

               if (var3[0].equals("streamKbps")) {
                  this.N = this.a(var3[1]);
               }

               if (var3[0].equals("streamFps")) {
                  this.O = this.a(var3[1]);
               }

               if (var3[0].equals("streamCompression")) {
                  this.P = Integer.parseInt(var3[1]);
               }

               if (var3[0].equals("streamSendMetadata")) {
                  this.Q = var3[1].equals("true");
               }

               if (var3[0].equals("streamPreferredServer") && var3.length >= 2) {
                  this.R = var2.substring(var2.indexOf(58) + 1);
               }

               if (var3[0].equals("streamChatEnabled")) {
                  this.S = Integer.parseInt(var3[1]);
               }

               if (var3[0].equals("streamChatUserFilter")) {
                  this.T = Integer.parseInt(var3[1]);
               }

               if (var3[0].equals("streamMicToggleBehavior")) {
                  this.U = Integer.parseInt(var3[1]);
               }

               if (var3[0].equals("forceUnicodeFont")) {
                  this.aO = var3[1].equals("true");
               }

               if (var3[0].equals("allowBlockAlternatives")) {
                  this.v = var3[1].equals("true");
               }

               if (var3[0].equals("reducedDebugInfo")) {
                  this.w = var3[1].equals("true");
               }

               if (var3[0].equals("useNativeTransport")) {
                  this.V = var3[1].equals("true");
               }

               if (var3[0].equals("entityShadows")) {
                  this.W = var3[1].equals("true");
               }

               if (var3[0].equals("realmsNotifications")) {
                  this.X = var3[1].equals("true");
               }

               for (KeyBinding var7 : this.ax) {
                  if (var3[0].equals("key_" + var7.g())) {
                     var7.b(Integer.parseInt(var3[1]));
                  }
               }

               for (SoundCategory var17 : SoundCategory.values()) {
                  if (var3[0].equals("soundCategory_" + var17.a())) {
                     this.bb.put(var17, this.a(var3[1]));
                  }
               }

               for (EnumPlayerModelParts var18 : EnumPlayerModelParts.values()) {
                  if (var3[0].equals("modelPart_" + var18.c())) {
                     this.a(var18, var3[1].equals("true"));
                  }
               }
            } catch (Exception var8) {
               aP.warn("Skipping bad option: " + var2);
            }
         }

         KeyBinding.b();
         var1.close();
      } catch (Exception var9) {
         aP.error("Failed to load options", var9);
      }
   }

   private float a(String var1) {
      if (var1.equals("true")) {
         return 1.0F;
      } else {
         return var1.equals("false") ? 0.0F : Float.parseFloat(var1);
      }
   }

   public void b() {
      try {
         PrintWriter var1 = new PrintWriter(new FileWriter(this.bc));
         var1.println("invertYMouse:" + this.b);
         var1.println("mouseSensitivity:" + this.a);
         var1.println("fov:" + (this.aI - 70.0F) / 40.0F);
         var1.println("gamma:" + this.aJ);
         var1.println("saturation:" + this.aK);
         var1.println("renderDistance:" + this.c);
         var1.println("guiScale:" + this.aL);
         var1.println("particles:" + this.aM);
         var1.println("bobView:" + this.d);
         var1.println("anaglyph3d:" + this.e);
         var1.println("maxFps:" + this.g);
         var1.println("fboEnable:" + this.f);
         var1.println("difficulty:" + this.az.a());
         var1.println("fancyGraphics:" + this.i);
         var1.println("ao:" + this.j);
         switch (this.h) {
            case 0:
               var1.println("renderClouds:false");
               break;
            case 1:
               var1.println("renderClouds:fast");
               break;
            case 2:
               var1.println("renderClouds:true");
         }

         var1.println("resourcePacks:" + aQ.toJson(this.k));
         var1.println("incompatibleResourcePacks:" + aQ.toJson(this.l));
         var1.println("lastServer:" + this.aF);
         var1.println("lang:" + this.aN);
         var1.println("chatVisibility:" + this.m.a());
         var1.println("chatColors:" + this.n);
         var1.println("chatLinks:" + this.o);
         var1.println("chatLinksPrompt:" + this.p);
         var1.println("chatOpacity:" + this.q);
         var1.println("snooperEnabled:" + this.r);
         var1.println("fullscreen:" + this.s);
         var1.println("enableVsync:" + this.t);
         var1.println("useVbo:" + this.u);
         var1.println("hideServerAddress:" + this.x);
         var1.println("advancedItemTooltips:" + this.y);
         var1.println("pauseOnLostFocus:" + this.z);
         var1.println("touchscreen:" + this.A);
         var1.println("overrideWidth:" + this.B);
         var1.println("overrideHeight:" + this.C);
         var1.println("heldItemTooltips:" + this.D);
         var1.println("chatHeightFocused:" + this.H);
         var1.println("chatHeightUnfocused:" + this.G);
         var1.println("chatScale:" + this.E);
         var1.println("chatWidth:" + this.F);
         var1.println("showInventoryAchievementHint:" + this.I);
         var1.println("mipmapLevels:" + this.J);
         var1.println("streamBytesPerPixel:" + this.K);
         var1.println("streamMicVolume:" + this.L);
         var1.println("streamSystemVolume:" + this.M);
         var1.println("streamKbps:" + this.N);
         var1.println("streamFps:" + this.O);
         var1.println("streamCompression:" + this.P);
         var1.println("streamSendMetadata:" + this.Q);
         var1.println("streamPreferredServer:" + this.R);
         var1.println("streamChatEnabled:" + this.S);
         var1.println("streamChatUserFilter:" + this.T);
         var1.println("streamMicToggleBehavior:" + this.U);
         var1.println("forceUnicodeFont:" + this.aO);
         var1.println("allowBlockAlternatives:" + this.v);
         var1.println("reducedDebugInfo:" + this.w);
         var1.println("useNativeTransport:" + this.V);
         var1.println("entityShadows:" + this.W);
         var1.println("realmsNotifications:" + this.X);

         for (KeyBinding var5 : this.ax) {
            var1.println("key_" + var5.g() + ":" + var5.i());
         }

         for (SoundCategory var13 : SoundCategory.values()) {
            var1.println("soundCategory_" + var13.a() + ":" + this.a(var13));
         }

         for (EnumPlayerModelParts var14 : EnumPlayerModelParts.values()) {
            var1.println("modelPart_" + var14.c() + ":" + this.ba.contains(var14));
         }

         var1.close();
      } catch (Exception var6) {
         aP.error("Failed to save options", var6);
      }

      this.c();
   }

   public float a(SoundCategory var1) {
      return this.bb.containsKey(var1) ? this.bb.get(var1) : 1.0F;
   }

   public void a(SoundCategory var1, float var2) {
      this.ay.W().a(var1, var2);
      this.bb.put(var1, var2);
   }

   public void c() {
      if (this.ay.h != null) {
         int var1 = 0;

         for (EnumPlayerModelParts var3 : this.ba) {
            var1 |= var3.a();
         }

         this.ay.h.a.a(new C15PacketClientSettings(this.aN, this.c, this.m, this.n, var1));
      }
   }

   public Set<EnumPlayerModelParts> d() {
      return ImmutableSet.copyOf(this.ba);
   }

   public void a(EnumPlayerModelParts var1, boolean var2) {
      if (var2) {
         this.ba.add(var1);
      } else {
         this.ba.remove(var1);
      }

      this.c();
   }

   public void a(EnumPlayerModelParts var1) {
      if (!this.d().contains(var1)) {
         this.ba.add(var1);
      } else {
         this.ba.remove(var1);
      }

      this.c();
   }

   public int e() {
      return this.c >= 4 ? this.h : 0;
   }

   public boolean f() {
      return this.V;
   }

   public static enum Options {
      a("options.invertMouse", false, true),
      b("options.sensitivity", true, false),
      c("options.fov", true, false, 30.0F, 110.0F, 1.0F),
      d("options.gamma", true, false),
      e("options.saturation", true, false),
      f("options.renderDistance", true, false, 2.0F, 16.0F, 1.0F),
      g("options.viewBobbing", false, true),
      h("options.anaglyph", false, true),
      i("options.framerateLimit", true, false, 10.0F, 260.0F, 10.0F),
      j("options.fboEnable", false, true),
      k("options.renderClouds", false, false),
      l("options.graphics", false, false),
      m("options.ao", false, false),
      n("options.guiScale", false, false),
      o("options.particles", false, false),
      p("options.chat.visibility", false, false),
      q("options.chat.color", false, true),
      r("options.chat.links", false, true),
      s("options.chat.opacity", true, false),
      t("options.chat.links.prompt", false, true),
      u("options.snooper", false, true),
      v("options.fullscreen", false, true),
      w("options.vsync", false, true),
      x("options.vbo", false, true),
      y("options.touchscreen", false, true),
      z("options.chat.scale", true, false),
      A("options.chat.width", true, false),
      B("options.chat.height.focused", true, false),
      C("options.chat.height.unfocused", true, false),
      D("options.mipmapLevels", true, false, 0.0F, 4.0F, 1.0F),
      E("options.forceUnicodeFont", false, true),
      F("options.stream.bytesPerPixel", true, false),
      G("options.stream.micVolumne", true, false),
      H("options.stream.systemVolume", true, false),
      I("options.stream.kbps", true, false),
      J("options.stream.fps", true, false),
      K("options.stream.compression", false, false),
      L("options.stream.sendMetadata", false, true),
      M("options.stream.chat.enabled", false, false),
      N("options.stream.chat.userFilter", false, false),
      O("options.stream.micToggleBehavior", false, false),
      P("options.blockAlternatives", false, true),
      Q("options.reducedDebugInfo", false, true),
      R("options.entityShadows", false, true),
      S("options.realmsNotifications", false, true);

      private final boolean T;
      private final boolean U;
      private final String V;
      private final float W;
      private float X;
      private float Y;

      public static GameSettings.Options a(int var0) {
         for (GameSettings.Options var4 : values()) {
            if (var4.c() == var0) {
               return var4;
            }
         }

         return null;
      }

      private Options(String var3, boolean var4, boolean var5) {
         this(var3, var4, var5, 0.0F, 1.0F, 0.0F);
      }

      private Options(String var3, boolean var4, boolean var5, float var6, float var7, float var8) {
         this.V = var3;
         this.T = var4;
         this.U = var5;
         this.X = var6;
         this.Y = var7;
         this.W = var8;
      }

      public boolean a() {
         return this.T;
      }

      public boolean b() {
         return this.U;
      }

      public int c() {
         return this.ordinal();
      }

      public String d() {
         return this.V;
      }

      public float f() {
         return this.Y;
      }

      public void a(float var1) {
         this.Y = var1;
      }

      public float c(float var1) {
         return MathHelper.a((this.e(var1) - this.X) / (this.Y - this.X), 0.0F, 1.0F);
      }

      public float d(float var1) {
         return this.e(this.X + (this.Y - this.X) * MathHelper.a(var1, 0.0F, 1.0F));
      }

      public float e(float var1) {
         var1 = this.f(var1);
         return MathHelper.a(var1, this.X, this.Y);
      }

      protected float f(float var1) {
         if (this.W > 0.0F) {
            var1 = this.W * Math.round(var1 / this.W);
         }

         return var1;
      }
   }
}
