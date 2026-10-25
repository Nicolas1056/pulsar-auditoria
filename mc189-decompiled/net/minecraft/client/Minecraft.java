package net.minecraft.client;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import javax.imageio.ImageIO;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMemoryErrorScreen;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.gui.stream.GuiStreamUnavailable;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITickableTextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.FoliageColorReloadListener;
import net.minecraft.client.resources.GrassColorReloadListener;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.resources.ResourceIndex;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.AnimationMetadataSectionSerializer;
import net.minecraft.client.resources.data.FontMetadataSection;
import net.minecraft.client.resources.data.FontMetadataSectionSerializer;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.data.LanguageMetadataSection;
import net.minecraft.client.resources.data.LanguageMetadataSectionSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.client.resources.data.PackMetadataSectionSerializer;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSectionSerializer;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.stream.IStream;
import net.minecraft.client.stream.NullStream;
import net.minecraft.client.stream.TwitchStream;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.IStatStringFormat;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MinecraftError;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import net.minecraft.util.Util;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.OpenGLException;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

public class Minecraft implements IThreadListener, IPlayerUsage {
   private static final Logger K = LogManager.getLogger();
   private static final ResourceLocation L = new ResourceLocation("textures/gui/title/mojang.png");
   public static final boolean a = Util.a() == Util.EnumOS.d;
   public static byte[] b = new byte[10485760];
   private static final List<DisplayMode> M = Lists.newArrayList(new DisplayMode[]{new DisplayMode(2560, 1600), new DisplayMode(2880, 1800)});
   private final File N;
   private final PropertyMap O;
   private final PropertyMap P;
   private ServerData Q;
   private TextureManager R;
   private static Minecraft S;
   public PlayerControllerMP c;
   private boolean T;
   private boolean U = true;
   private boolean V;
   private CrashReport W;
   public int d;
   public int e;
   private boolean X = false;
   private Timer Y = new Timer(20.0F);
   private PlayerUsageSnooper Z = new PlayerUsageSnooper("client", this, MinecraftServer.az());
   public WorldClient f;
   public RenderGlobal g;
   private RenderManager aa;
   private RenderItem ab;
   private ItemRenderer ac;
   public EntityPlayerSP h;
   private Entity ad;
   public Entity i;
   public EffectRenderer j;
   private final Session ae;
   private boolean af;
   public FontRenderer k;
   public FontRenderer l;
   public GuiScreen m;
   public LoadingScreenRenderer n;
   public EntityRenderer o;
   private int ag;
   private int ah;
   private int ai;
   private IntegratedServer aj;
   public GuiAchievement p;
   public GuiIngame q;
   public boolean r;
   public MovingObjectPosition s;
   public GameSettings t;
   public MouseHelper u;
   public final File v;
   private final File ak;
   private final String al;
   private final Proxy am;
   private ISaveFormat an;
   private static int ao;
   private int ap;
   private String aq;
   private int ar;
   public boolean w;
   long x = J();
   private int as;
   public final FrameTimer y = new FrameTimer();
   long z = System.nanoTime();
   private final boolean at;
   private final boolean au;
   private NetworkManager av;
   private boolean aw;
   public final Profiler A = new Profiler();
   private long ax = -1L;
   private IReloadableResourceManager ay;
   private final IMetadataSerializer az = new IMetadataSerializer();
   private final List<IResourcePack> aA = Lists.newArrayList();
   private final DefaultResourcePack aB;
   private ResourcePackRepository aC;
   private LanguageManager aD;
   private IStream aE;
   private Framebuffer aF;
   private TextureMap aG;
   private SoundHandler aH;
   private MusicTicker aI;
   private ResourceLocation aJ;
   private final MinecraftSessionService aK;
   private SkinManager aL;
   private final Queue<FutureTask<?>> aM = Queues.newArrayDeque();
   private long aN = 0L;
   private final Thread aO = Thread.currentThread();
   private ModelManager aP;
   private BlockRendererDispatcher aQ;
   volatile boolean B = true;
   public String C = "";
   public boolean D = false;
   public boolean E = false;
   public boolean F = false;
   public boolean G = true;
   long H = J();
   int I;
   long J = -1L;
   private String aR = "root";

   public Minecraft(GameConfiguration var1) {
      S = this;
      this.v = var1.c.a;
      this.ak = var1.c.c;
      this.N = var1.c.b;
      this.al = var1.d.b;
      this.O = var1.a.b;
      this.P = var1.a.c;
      this.aB = new DefaultResourcePack(new ResourceIndex(var1.c.c, var1.c.d).a());
      this.am = var1.a.d == null ? Proxy.NO_PROXY : var1.a.d;
      this.aK = new YggdrasilAuthenticationService(var1.a.d, UUID.randomUUID().toString()).createMinecraftSessionService();
      this.ae = var1.a.a;
      K.info("Setting user: " + this.ae.c());
      K.info("(Session ID is " + this.ae.a() + ")");
      this.au = var1.d.a;
      this.d = var1.b.a > 0 ? var1.b.a : 1;
      this.e = var1.b.b > 0 ? var1.b.b : 1;
      this.ah = var1.b.a;
      this.ai = var1.b.b;
      this.T = var1.b.c;
      this.at = as();
      this.aj = new IntegratedServer(this);
      if (var1.e.a != null) {
         this.aq = var1.e.a;
         this.ar = var1.e.b;
      }

      ImageIO.setUseCache(false);
      Bootstrap.c();
   }

   public void a() {
      this.B = true;

      try {
         this.am();
      } catch (Throwable var11) {
         CrashReport var2 = CrashReport.a(var11, "Initializing game");
         var2.a("Initialization");
         this.c(this.b(var2));
         return;
      }

      try {
         try {
            while (this.B) {
               if (this.V && this.W != null) {
                  this.c(this.W);
                  return;
               } else {
                  try {
                     this.av();
                  } catch (OutOfMemoryError var10) {
                     this.l();
                     this.a(new GuiMemoryErrorScreen());
                     System.gc();
                  }
               }
            }

            return;
         } catch (MinecraftError var12) {
         } catch (ReportedException var13) {
            this.b(var13.a());
            this.l();
            K.fatal("Reported exception thrown!", var13);
            this.c(var13.a());
         } catch (Throwable var14) {
            CrashReport var16 = this.b(new CrashReport("Unexpected error", var14));
            this.l();
            K.fatal("Unreported exception thrown!", var14);
            this.c(var16);
         }
      } finally {
         this.g();
      }
   }

   private void am() throws LWJGLException, IOException {
      this.t = new GameSettings(this, this.v);
      this.aA.add(this.aB);
      this.at();
      if (this.t.C > 0 && this.t.B > 0) {
         this.d = this.t.B;
         this.e = this.t.C;
      }

      K.info("LWJGL Version: " + Sys.getVersion());
      this.ar();
      this.aq();
      this.ap();
      OpenGlHelper.a();
      this.aF = new Framebuffer(this.d, this.e, true);
      this.aF.a(0.0F, 0.0F, 0.0F, 0.0F);
      this.an();
      this.aC = new ResourcePackRepository(this.N, new File(this.v, "server-resource-packs"), this.aB, this.az, this.t);
      this.ay = new SimpleReloadableResourceManager(this.az);
      this.aD = new LanguageManager(this.az, this.t.aN);
      this.ay.a(this.aD);
      this.e();
      this.R = new TextureManager(this.ay);
      this.ay.a(this.R);
      this.a(this.R);
      this.ao();
      this.aL = new SkinManager(this.R, new File(this.ak, "skins"), this.aK);
      this.an = new AnvilSaveConverter(new File(this.v, "saves"));
      this.aH = new SoundHandler(this.ay, this.t);
      this.ay.a(this.aH);
      this.aI = new MusicTicker(this);
      this.k = new FontRenderer(this.t, new ResourceLocation("textures/font/ascii.png"), this.R, false);
      if (this.t.aN != null) {
         this.k.a(this.d());
         this.k.b(this.aD.b());
      }

      this.l = new FontRenderer(this.t, new ResourceLocation("textures/font/ascii_sga.png"), this.R, false);
      this.ay.a(this.k);
      this.ay.a(this.l);
      this.ay.a(new GrassColorReloadListener());
      this.ay.a(new FoliageColorReloadListener());
      AchievementList.f.a(new IStatStringFormat() {
         @Override
         public String a(String var1) {
            try {
               return String.format(var1, GameSettings.c(Minecraft.this.t.af.i()));
            } catch (Exception var3) {
               return "Error: " + var3.getLocalizedMessage();
            }
         }
      });
      this.u = new MouseHelper();
      this.b("Pre startup");
      GlStateManager.w();
      GlStateManager.j(7425);
      GlStateManager.a(1.0);
      GlStateManager.j();
      GlStateManager.c(515);
      GlStateManager.d();
      GlStateManager.a(516, 0.1F);
      GlStateManager.e(1029);
      GlStateManager.n(5889);
      GlStateManager.D();
      GlStateManager.n(5888);
      this.b("Startup");
      this.aG = new TextureMap("textures");
      this.aG.a(this.t.J);
      this.R.a(TextureMap.g, (ITickableTextureObject)this.aG);
      this.R.a(TextureMap.g);
      this.aG.a(false, this.t.J > 0);
      this.aP = new ModelManager(this.aG);
      this.ay.a(this.aP);
      this.ab = new RenderItem(this.R, this.aP);
      this.aa = new RenderManager(this.R, this.ab);
      this.ac = new ItemRenderer(this);
      this.ay.a(this.ab);
      this.o = new EntityRenderer(this, this.ay);
      this.ay.a(this.o);
      this.aQ = new BlockRendererDispatcher(this.aP.c(), this.t);
      this.ay.a(this.aQ);
      this.g = new RenderGlobal(this);
      this.ay.a(this.g);
      this.p = new GuiAchievement(this);
      GlStateManager.b(0, 0, this.d, this.e);
      this.j = new EffectRenderer(this.f, this.R);
      this.b("Post startup");
      this.q = new GuiIngame(this);
      if (this.aq != null) {
         this.a(new GuiConnecting(new GuiMainMenu(), this, this.aq, this.ar));
      } else {
         this.a(new GuiMainMenu());
      }

      this.R.c(this.aJ);
      this.aJ = null;
      this.n = new LoadingScreenRenderer(this);
      if (this.t.s && !this.T) {
         this.q();
      }

      try {
         Display.setVSyncEnabled(this.t.t);
      } catch (OpenGLException var2) {
         this.t.t = false;
         this.t.b();
      }

      this.g.b();
   }

   private void an() {
      this.az.a(new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
      this.az.a(new FontMetadataSectionSerializer(), FontMetadataSection.class);
      this.az.a(new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
      this.az.a(new PackMetadataSectionSerializer(), PackMetadataSection.class);
      this.az.a(new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
   }

   private void ao() {
      try {
         this.aE = new TwitchStream(this, (Property)Iterables.getFirst(this.O.get("twitch_access_token"), null));
      } catch (Throwable var2) {
         this.aE = new NullStream(var2);
         K.error("Couldn't initialize twitch stream");
      }
   }

   private void ap() throws LWJGLException {
      Display.setResizable(true);
      Display.setTitle("Minecraft 1.8.9");

      try {
         Display.create(new PixelFormat().withDepthBits(24));
      } catch (LWJGLException var4) {
         K.error("Couldn't set pixel format", var4);

         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var3) {
         }

         if (this.T) {
            this.au();
         }

         Display.create();
      }
   }

   private void aq() throws LWJGLException {
      if (this.T) {
         Display.setFullscreen(true);
         DisplayMode var1 = Display.getDisplayMode();
         this.d = Math.max(1, var1.getWidth());
         this.e = Math.max(1, var1.getHeight());
      } else {
         Display.setDisplayMode(new DisplayMode(this.d, this.e));
      }
   }

   private void ar() {
      Util.EnumOS var1 = Util.a();
      if (var1 != Util.EnumOS.d) {
         InputStream var2 = null;
         InputStream var3 = null;

         try {
            var2 = this.aB.c(new ResourceLocation("icons/icon_16x16.png"));
            var3 = this.aB.c(new ResourceLocation("icons/icon_32x32.png"));
            if (var2 != null && var3 != null) {
               Display.setIcon(new ByteBuffer[]{this.a(var2), this.a(var3)});
            }
         } catch (IOException var8) {
            K.error("Couldn't set icon", var8);
         } finally {
            IOUtils.closeQuietly(var2);
            IOUtils.closeQuietly(var3);
         }
      }
   }

   private static boolean as() {
      String[] var0 = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};

      for (String var4 : var0) {
         String var5 = System.getProperty(var4);
         if (var5 != null && var5.contains("64")) {
            return true;
         }
      }

      return false;
   }

   public Framebuffer b() {
      return this.aF;
   }

   public String c() {
      return this.al;
   }

   private void at() {
      Thread var1 = new Thread("Timer hack thread") {
         @Override
         public void run() {
            while (Minecraft.this.B) {
               try {
                  Thread.sleep(2147483647L);
               } catch (InterruptedException var2) {
               }
            }
         }
      };
      var1.setDaemon(true);
      var1.start();
   }

   public void a(CrashReport var1) {
      this.V = true;
      this.W = var1;
   }

   public void c(CrashReport var1) {
      File var2 = new File(A().v, "crash-reports");
      File var3 = new File(var2, "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-client.txt");
      Bootstrap.a(var1.e());
      if (var1.f() != null) {
         Bootstrap.a("#@!@# Game crashed! Crash report saved to: #@!@# " + var1.f());
         System.exit(-1);
      } else if (var1.a(var3)) {
         Bootstrap.a("#@!@# Game crashed! Crash report saved to: #@!@# " + var3.getAbsolutePath());
         System.exit(-1);
      } else {
         Bootstrap.a("#@?@# Game crashed! Crash report could not be saved. #@?@#");
         System.exit(-2);
      }
   }

   public boolean d() {
      return this.aD.a() || this.t.aO;
   }

   public void e() {
      ArrayList var1 = Lists.newArrayList(this.aA);

      for (ResourcePackRepository.Entry var3 : this.aC.c()) {
         var1.add(var3.c());
      }

      if (this.aC.e() != null) {
         var1.add(this.aC.e());
      }

      try {
         this.ay.a(var1);
      } catch (RuntimeException var4) {
         K.info("Caught error stitching, removing all assigned resourcepacks", var4);
         var1.clear();
         var1.addAll(this.aA);
         this.aC.a(Collections.emptyList());
         this.ay.a(var1);
         this.t.k.clear();
         this.t.l.clear();
         this.t.b();
      }

      this.aD.a(var1);
      if (this.g != null) {
         this.g.a();
      }
   }

   private ByteBuffer a(InputStream var1) throws IOException {
      BufferedImage var2 = ImageIO.read(var1);
      int[] var3 = var2.getRGB(0, 0, var2.getWidth(), var2.getHeight(), null, 0, var2.getWidth());
      ByteBuffer var4 = ByteBuffer.allocate(4 * var3.length);

      for (int var8 : var3) {
         var4.putInt(var8 << 8 | var8 >> 24 & 0xFF);
      }

      ((Buffer)var4).flip();
      return var4;
   }

   private void au() throws LWJGLException {
      HashSet var1 = Sets.newHashSet();
      Collections.addAll(var1, Display.getAvailableDisplayModes());
      DisplayMode var2 = Display.getDesktopDisplayMode();
      if (!var1.contains(var2) && Util.a() == Util.EnumOS.d) {
         for (DisplayMode var4 : M) {
            boolean var5 = true;

            for (DisplayMode var7 : var1) {
               if (var7.getBitsPerPixel() == 32 && var7.getWidth() == var4.getWidth() && var7.getHeight() == var4.getHeight()) {
                  var5 = false;
                  break;
               }
            }

            if (!var5) {
               for (DisplayMode var9 : var1) {
                  if (var9.getBitsPerPixel() == 32 && var9.getWidth() == var4.getWidth() / 2 && var9.getHeight() == var4.getHeight() / 2) {
                     var2 = var9;
                     break;
                  }
               }
            }
         }
      }

      Display.setDisplayMode(var2);
      this.d = var2.getWidth();
      this.e = var2.getHeight();
   }

   private void a(TextureManager var1) throws LWJGLException {
      ScaledResolution var2 = new ScaledResolution(this);
      int var3 = var2.e();
      Framebuffer var4 = new Framebuffer(var2.a() * var3, var2.b() * var3, true);
      var4.a(false);
      GlStateManager.n(5889);
      GlStateManager.D();
      GlStateManager.a(0.0, var2.a(), var2.b(), 0.0, 1000.0, 3000.0);
      GlStateManager.n(5888);
      GlStateManager.D();
      GlStateManager.b(0.0F, 0.0F, -2000.0F);
      GlStateManager.f();
      GlStateManager.n();
      GlStateManager.i();
      GlStateManager.w();
      InputStream var5 = null;

      try {
         var5 = this.aB.a(L);
         this.aJ = var1.a("logo", new DynamicTexture(ImageIO.read(var5)));
         var1.a(this.aJ);
      } catch (IOException var12) {
         K.error("Unable to load logo: " + L, var12);
      } finally {
         IOUtils.closeQuietly(var5);
      }

      Tessellator var6 = Tessellator.a();
      WorldRenderer var7 = var6.c();
      var7.a(7, DefaultVertexFormats.i);
      var7.b(0.0, (double)this.e, 0.0).a(0.0, 0.0).b(255, 255, 255, 255).d();
      var7.b((double)this.d, (double)this.e, 0.0).a(0.0, 0.0).b(255, 255, 255, 255).d();
      var7.b((double)this.d, 0.0, 0.0).a(0.0, 0.0).b(255, 255, 255, 255).d();
      var7.b(0.0, 0.0, 0.0).a(0.0, 0.0).b(255, 255, 255, 255).d();
      var6.b();
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      short var8 = 256;
      short var9 = 256;
      this.a((var2.a() - var8) / 2, (var2.b() - var9) / 2, 0, 0, var8, var9, 255, 255, 255, 255);
      GlStateManager.f();
      GlStateManager.n();
      var4.e();
      var4.c(var2.a() * var3, var2.b() * var3);
      GlStateManager.d();
      GlStateManager.a(516, 0.1F);
      this.h();
   }

   public void a(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10) {
      float var11 = 0.00390625F;
      float var12 = 0.00390625F;
      WorldRenderer var13 = Tessellator.a().c();
      var13.a(7, DefaultVertexFormats.i);
      var13.b((double)var1, (double)(var2 + var6), 0.0).a((double)(var3 * var11), (double)((var4 + var6) * var12)).b(var7, var8, var9, var10).d();
      var13.b((double)(var1 + var5), (double)(var2 + var6), 0.0)
         .a((double)((var3 + var5) * var11), (double)((var4 + var6) * var12))
         .b(var7, var8, var9, var10)
         .d();
      var13.b((double)(var1 + var5), (double)var2, 0.0).a((double)((var3 + var5) * var11), (double)(var4 * var12)).b(var7, var8, var9, var10).d();
      var13.b((double)var1, (double)var2, 0.0).a((double)(var3 * var11), (double)(var4 * var12)).b(var7, var8, var9, var10).d();
      Tessellator.a().b();
   }

   public ISaveFormat f() {
      return this.an;
   }

   public void a(GuiScreen var1) {
      if (this.m != null) {
         this.m.m();
      }

      if (var1 == null && this.f == null) {
         var1 = new GuiMainMenu();
      } else if (var1 == null && this.h.bn() <= 0.0F) {
         var1 = new GuiGameOver();
      }

      if (var1 instanceof GuiMainMenu) {
         this.t.aC = false;
         this.q.d().a();
      }

      this.m = (GuiScreen)var1;
      if (var1 != null) {
         this.o();
         ScaledResolution var2 = new ScaledResolution(this);
         int var3 = var2.a();
         int var4 = var2.b();
         ((GuiScreen)var1).a(this, var3, var4);
         this.r = false;
      } else {
         this.aH.e();
         this.n();
      }
   }

   private void b(String var1) {
      if (this.U) {
         int var2 = GL11.glGetError();
         if (var2 != 0) {
            String var3 = GLU.gluErrorString(var2);
            K.error("########## GL ERROR ##########");
            K.error("@ " + var1);
            K.error(var2 + ": " + var3);
         }
      }
   }

   public void g() {
      try {
         this.aE.f();
         K.info("Stopping!");

         try {
            this.a(null);
         } catch (Throwable var5) {
         }

         this.aH.d();
      } finally {
         Display.destroy();
         if (!this.V) {
            System.exit(0);
         }
      }

      System.gc();
   }

   private void av() {
      long var1 = System.nanoTime();
      this.A.a("root");
      if (Display.isCreated() && Display.isCloseRequested()) {
         this.m();
      }

      if (this.af && this.f != null) {
         float var3 = this.Y.c;
         this.Y.a();
         this.Y.c = var3;
      } else {
         this.Y.a();
      }

      this.A.a("scheduledExecutables");
      synchronized (this.aM) {
         while (!this.aM.isEmpty()) {
            Util.a(this.aM.poll(), K);
         }
      }

      this.A.b();
      long var11 = System.nanoTime();
      this.A.a("tick");

      for (int var5 = 0; var5 < this.Y.b; var5++) {
         this.s();
      }

      this.A.c("preRenderErrors");
      long var12 = System.nanoTime() - var11;
      this.b("Pre render");
      this.A.c("sound");
      this.aH.a(this.h, this.Y.c);
      this.A.b();
      this.A.a("render");
      GlStateManager.E();
      GlStateManager.m(16640);
      this.aF.a(true);
      this.A.a("display");
      GlStateManager.w();
      if (this.h != null && this.h.aj()) {
         this.t.aB = 0;
      }

      this.A.b();
      if (!this.r) {
         this.A.c("gameRenderer");
         this.o.a(this.Y.c, var1);
         this.A.b();
      }

      this.A.b();
      if (this.t.aC && this.t.aD && !this.t.aA) {
         if (!this.A.a) {
            this.A.a();
         }

         this.A.a = true;
         this.a(var12);
      } else {
         this.A.a = false;
         this.J = System.nanoTime();
      }

      this.p.a();
      this.aF.e();
      GlStateManager.F();
      GlStateManager.E();
      this.aF.c(this.d, this.e);
      GlStateManager.F();
      GlStateManager.E();
      this.o.b(this.Y.c);
      GlStateManager.F();
      this.A.a("root");
      this.h();
      Thread.yield();
      this.A.a("stream");
      this.A.a("update");
      this.aE.g();
      this.A.c("submit");
      this.aE.h();
      this.A.b();
      this.A.b();
      this.b("Post render");
      this.I++;
      this.af = this.F() && this.m != null && this.m.d() && !this.aj.b();
      long var7 = System.nanoTime();
      this.y.a(var7 - this.z);
      this.z = var7;

      while (J() >= this.H + 1000L) {
         ao = this.I;
         this.C = String.format(
            "%d fps (%d chunk update%s) T: %s%s%s%s%s",
            ao,
            RenderChunk.a,
            RenderChunk.a != 1 ? "s" : "",
            this.t.g == GameSettings.Options.i.f() ? "inf" : this.t.g,
            this.t.t ? " vsync" : "",
            this.t.i ? "" : " fast",
            this.t.h == 0 ? "" : (this.t.h == 1 ? " fast-clouds" : " fancy-clouds"),
            OpenGlHelper.f() ? " vbo" : ""
         );
         RenderChunk.a = 0;
         this.H += 1000L;
         this.I = 0;
         this.Z.b();
         if (!this.Z.d()) {
            this.Z.a();
         }
      }

      if (this.k()) {
         this.A.a("fpslimit_wait");
         Display.sync(this.j());
         this.A.b();
      }

      this.A.b();
   }

   public void h() {
      this.A.a("display_update");
      Display.update();
      this.A.b();
      this.i();
   }

   protected void i() {
      if (!this.T && Display.wasResized()) {
         int var1 = this.d;
         int var2 = this.e;
         this.d = Display.getWidth();
         this.e = Display.getHeight();
         if (this.d != var1 || this.e != var2) {
            if (this.d <= 0) {
               this.d = 1;
            }

            if (this.e <= 0) {
               this.e = 1;
            }

            this.a(this.d, this.e);
         }
      }
   }

   public int j() {
      return this.f == null && this.m != null ? 30 : this.t.g;
   }

   public boolean k() {
      return this.j() < GameSettings.Options.i.f();
   }

   public void l() {
      try {
         b = new byte[0];
         this.g.k();
      } catch (Throwable var3) {
      }

      try {
         System.gc();
         this.a(null);
      } catch (Throwable var2) {
      }

      System.gc();
   }

   private void b(int var1) {
      List var2 = this.A.b(this.aR);
      if (var2 != null && !var2.isEmpty()) {
         Profiler.Result var3 = (Profiler.Result)var2.remove(0);
         if (var1 == 0) {
            if (var3.c.length() > 0) {
               int var4 = this.aR.lastIndexOf(".");
               if (var4 >= 0) {
                  this.aR = this.aR.substring(0, var4);
               }
            }
         } else {
            var1--;
            if (var1 < var2.size() && !((Profiler.Result)var2.get(var1)).c.equals("unspecified")) {
               if (this.aR.length() > 0) {
                  this.aR = this.aR + ".";
               }

               this.aR = this.aR + ((Profiler.Result)var2.get(var1)).c;
            }
         }
      }
   }

   private void a(long var1) {
      if (this.A.a) {
         List var3 = this.A.b(this.aR);
         Profiler.Result var4 = (Profiler.Result)var3.remove(0);
         GlStateManager.m(256);
         GlStateManager.n(5889);
         GlStateManager.g();
         GlStateManager.D();
         GlStateManager.a(0.0, this.d, this.e, 0.0, 1000.0, 3000.0);
         GlStateManager.n(5888);
         GlStateManager.D();
         GlStateManager.b(0.0F, 0.0F, -2000.0F);
         GL11.glLineWidth(1.0F);
         GlStateManager.x();
         Tessellator var5 = Tessellator.a();
         WorldRenderer var6 = var5.c();
         short var7 = 160;
         int var8 = this.d - var7 - 10;
         int var9 = this.e - var7 * 2;
         GlStateManager.l();
         var6.a(7, DefaultVertexFormats.f);
         var6.b((double)(var8 - var7 * 1.1F), (double)(var9 - var7 * 0.6F - 16.0F), 0.0).b(200, 0, 0, 0).d();
         var6.b((double)(var8 - var7 * 1.1F), (double)(var9 + var7 * 2), 0.0).b(200, 0, 0, 0).d();
         var6.b((double)(var8 + var7 * 1.1F), (double)(var9 + var7 * 2), 0.0).b(200, 0, 0, 0).d();
         var6.b((double)(var8 + var7 * 1.1F), (double)(var9 - var7 * 0.6F - 16.0F), 0.0).b(200, 0, 0, 0).d();
         var5.b();
         GlStateManager.k();
         double var10 = 0.0;

         for (int var12 = 0; var12 < var3.size(); var12++) {
            Profiler.Result var13 = (Profiler.Result)var3.get(var12);
            int var14 = MathHelper.c(var13.a / 4.0) + 1;
            var6.a(6, DefaultVertexFormats.f);
            int var15 = var13.a();
            int var16 = var15 >> 16 & 0xFF;
            int var17 = var15 >> 8 & 0xFF;
            int var18 = var15 & 0xFF;
            var6.b((double)var8, (double)var9, 0.0).b(var16, var17, var18, 255).d();

            for (int var19 = var14; var19 >= 0; var19--) {
               float var20 = (float)((var10 + var13.a * var19 / var14) * (float) Math.PI * 2.0 / 100.0);
               float var21 = MathHelper.a(var20) * var7;
               float var22 = MathHelper.b(var20) * var7 * 0.5F;
               var6.b((double)(var8 + var21), (double)(var9 - var22), 0.0).b(var16, var17, var18, 255).d();
            }

            var5.b();
            var6.a(5, DefaultVertexFormats.f);

            for (int var35 = var14; var35 >= 0; var35--) {
               float var36 = (float)((var10 + var13.a * var35 / var14) * (float) Math.PI * 2.0 / 100.0);
               float var37 = MathHelper.a(var36) * var7;
               float var38 = MathHelper.b(var36) * var7 * 0.5F;
               var6.b((double)(var8 + var37), (double)(var9 - var38), 0.0).b(var16 >> 1, var17 >> 1, var18 >> 1, 255).d();
               var6.b((double)(var8 + var37), (double)(var9 - var38 + 10.0F), 0.0).b(var16 >> 1, var17 >> 1, var18 >> 1, 255).d();
            }

            var5.b();
            var10 += var13.a;
         }

         DecimalFormat var23 = new DecimalFormat("##0.00");
         GlStateManager.w();
         String var24 = "";
         if (!var4.c.equals("unspecified")) {
            var24 = var24 + "[0] ";
         }

         if (var4.c.length() == 0) {
            var24 = var24 + "ROOT ";
         } else {
            var24 = var24 + var4.c + " ";
         }

         int var28 = 16777215;
         this.k.a(var24, (float)(var8 - var7), (float)(var9 - var7 / 2 - 16), var28);
         this.k.a(var24 = var23.format(var4.b) + "%", (float)(var8 + var7 - this.k.a(var24)), (float)(var9 - var7 / 2 - 16), var28);

         for (int var27 = 0; var27 < var3.size(); var27++) {
            Profiler.Result var29 = (Profiler.Result)var3.get(var27);
            String var30 = "";
            if (var29.c.equals("unspecified")) {
               var30 = var30 + "[?] ";
            } else {
               var30 = var30 + "[" + (var27 + 1) + "] ";
            }

            var30 = var30 + var29.c;
            this.k.a(var30, (float)(var8 - var7), (float)(var9 + var7 / 2 + var27 * 8 + 20), var29.a());
            this.k.a(var30 = var23.format(var29.a) + "%", (float)(var8 + var7 - 50 - this.k.a(var30)), (float)(var9 + var7 / 2 + var27 * 8 + 20), var29.a());
            this.k.a(var30 = var23.format(var29.b) + "%", (float)(var8 + var7 - this.k.a(var30)), (float)(var9 + var7 / 2 + var27 * 8 + 20), var29.a());
         }
      }
   }

   public void m() {
      this.B = false;
   }

   public void n() {
      if (Display.isActive()) {
         if (!this.w) {
            this.w = true;
            this.u.a();
            this.a(null);
            this.ag = 10000;
         }
      }
   }

   public void o() {
      if (this.w) {
         KeyBinding.a();
         this.w = false;
         this.u.b();
      }
   }

   public void p() {
      if (this.m == null) {
         this.a(new GuiIngameMenu());
         if (this.F() && !this.aj.b()) {
            this.aH.a();
         }
      }
   }

   private void b(boolean var1) {
      if (!var1) {
         this.ag = 0;
      }

      if (this.ag <= 0 && !this.h.bS()) {
         if (var1 && this.s != null && this.s.a == MovingObjectPosition.MovingObjectType.b) {
            BlockPos var2 = this.s.a();
            if (this.f.p(var2).c().t() != Material.a && this.c.c(var2, this.s.b)) {
               this.j.a(var2, this.s.b);
               this.h.bw();
            }
         } else {
            this.c.c();
         }
      }
   }

   private void aw() {
      if (this.ag <= 0) {
         this.h.bw();
         if (this.s == null) {
            K.error("Null returned as 'hitResult', this shouldn't happen!");
            if (this.c.g()) {
               this.ag = 10;
            }
         } else {
            switch (this.s.a) {
               case c:
                  this.c.a(this.h, this.s.d);
                  break;
               case b:
                  BlockPos var1 = this.s.a();
                  if (this.f.p(var1).c().t() != Material.a) {
                     this.c.b(var1, this.s.b);
                     break;
                  }
               case a:
               default:
                  if (this.c.g()) {
                     this.ag = 10;
                  }
            }
         }
      }
   }

   private void ax() {
      if (!this.c.m()) {
         this.ap = 4;
         boolean var1 = true;
         ItemStack var2 = this.h.bi.h();
         if (this.s == null) {
            K.warn("Null returned as 'hitResult', this shouldn't happen!");
         } else {
            switch (this.s.a) {
               case c:
                  if (this.c.a(this.h, this.s.d, this.s)) {
                     var1 = false;
                  } else if (this.c.b(this.h, this.s.d)) {
                     var1 = false;
                  }
                  break;
               case b:
                  BlockPos var3 = this.s.a();
                  if (this.f.p(var3).c().t() != Material.a) {
                     int var4 = var2 != null ? var2.b : 0;
                     if (this.c.a(this.h, this.f, var2, var3, this.s.b, this.s.c)) {
                        var1 = false;
                        this.h.bw();
                     }

                     if (var2 == null) {
                        return;
                     }

                     if (var2.b == 0) {
                        this.h.bi.a[this.h.bi.c] = null;
                     } else if (var2.b != var4 || this.c.h()) {
                        this.o.c.b();
                     }
                  }
            }
         }

         if (var1) {
            ItemStack var5 = this.h.bi.h();
            if (var5 != null && this.c.a(this.h, this.f, var5)) {
               this.o.c.c();
            }
         }
      }
   }

   public void q() {
      try {
         this.T = !this.T;
         this.t.s = this.T;
         if (this.T) {
            this.au();
            this.d = Display.getDisplayMode().getWidth();
            this.e = Display.getDisplayMode().getHeight();
            if (this.d <= 0) {
               this.d = 1;
            }

            if (this.e <= 0) {
               this.e = 1;
            }
         } else {
            Display.setDisplayMode(new DisplayMode(this.ah, this.ai));
            this.d = this.ah;
            this.e = this.ai;
            if (this.d <= 0) {
               this.d = 1;
            }

            if (this.e <= 0) {
               this.e = 1;
            }
         }

         if (this.m != null) {
            this.a(this.d, this.e);
         } else {
            this.ay();
         }

         Display.setFullscreen(this.T);
         Display.setVSyncEnabled(this.t.t);
         this.h();
      } catch (Exception var2) {
         K.error("Couldn't toggle fullscreen", var2);
      }
   }

   private void a(int var1, int var2) {
      this.d = Math.max(1, var1);
      this.e = Math.max(1, var2);
      if (this.m != null) {
         ScaledResolution var3 = new ScaledResolution(this);
         this.m.b(this, var3.a(), var3.b());
      }

      this.n = new LoadingScreenRenderer(this);
      this.ay();
   }

   private void ay() {
      this.aF.a(this.d, this.e);
      if (this.o != null) {
         this.o.a(this.d, this.e);
      }
   }

   public MusicTicker r() {
      return this.aI;
   }

   public void s() {
      if (this.ap > 0) {
         this.ap--;
      }

      this.A.a("gui");
      if (!this.af) {
         this.q.c();
      }

      this.A.b();
      this.o.a(1.0F);
      this.A.a("gameMode");
      if (!this.af && this.f != null) {
         this.c.e();
      }

      this.A.c("textures");
      if (!this.af) {
         this.R.e();
      }

      if (this.m == null && this.h != null) {
         if (this.h.bn() <= 0.0F) {
            this.a(null);
         } else if (this.h.bJ() && this.f != null) {
            this.a(new GuiSleepMP());
         }
      } else if (this.m != null && this.m instanceof GuiSleepMP && !this.h.bJ()) {
         this.a(null);
      }

      if (this.m != null) {
         this.ag = 10000;
      }

      if (this.m != null) {
         try {
            this.m.p();
         } catch (Throwable var7) {
            CrashReport var2 = CrashReport.a(var7, "Updating screen events");
            CrashReportCategory var3 = var2.a("Affected screen");
            var3.a("Screen name", new Callable<String>() {
               public String a() throws Exception {
                  return Minecraft.this.m.getClass().getCanonicalName();
               }
            });
            throw new ReportedException(var2);
         }

         if (this.m != null) {
            try {
               this.m.e();
            } catch (Throwable var6) {
               CrashReport var12 = CrashReport.a(var6, "Ticking screen");
               CrashReportCategory var16 = var12.a("Affected screen");
               var16.a("Screen name", new Callable<String>() {
                  public String a() throws Exception {
                     return Minecraft.this.m.getClass().getCanonicalName();
                  }
               });
               throw new ReportedException(var12);
            }
         }
      }

      if (this.m == null || this.m.p) {
         this.A.c("mouse");

         while (Mouse.next()) {
            int var1 = Mouse.getEventButton();
            KeyBinding.a(var1 - 100, Mouse.getEventButtonState());
            if (Mouse.getEventButtonState()) {
               if (this.h.v() && var1 == 2) {
                  this.q.g().b();
               } else {
                  KeyBinding.a(var1 - 100);
               }
            }

            long var13 = J() - this.x;
            if (var13 <= 200L) {
               int var4 = Mouse.getEventDWheel();
               if (var4 != 0) {
                  if (this.h.v()) {
                     var4 = var4 < 0 ? -1 : 1;
                     if (this.q.g().a()) {
                        this.q.g().b(-var4);
                     } else {
                        float var5 = MathHelper.a(this.h.bA.a() + var4 * 0.005F, 0.0F, 0.2F);
                        this.h.bA.a(var5);
                     }
                  } else {
                     this.h.bi.d(var4);
                  }
               }

               if (this.m == null) {
                  if (!this.w && Mouse.getEventButtonState()) {
                     this.n();
                  }
               } else if (this.m != null) {
                  this.m.k();
               }
            }
         }

         if (this.ag > 0) {
            this.ag--;
         }

         this.A.c("keyboard");

         while (Keyboard.next()) {
            int var9 = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();
            KeyBinding.a(var9, Keyboard.getEventKeyState());
            if (Keyboard.getEventKeyState()) {
               KeyBinding.a(var9);
            }

            if (this.ax > 0L) {
               if (J() - this.ax >= 6000L) {
                  throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
               }

               if (!Keyboard.isKeyDown(46) || !Keyboard.isKeyDown(61)) {
                  this.ax = -1L;
               }
            } else if (Keyboard.isKeyDown(46) && Keyboard.isKeyDown(61)) {
               this.ax = J();
            }

            this.Z();
            if (Keyboard.getEventKeyState()) {
               if (var9 == 62 && this.o != null) {
                  this.o.c();
               }

               if (this.m != null) {
                  this.m.l();
               } else {
                  if (var9 == 1) {
                     this.p();
                  }

                  if (var9 == 32 && Keyboard.isKeyDown(61) && this.q != null) {
                     this.q.d().a();
                  }

                  if (var9 == 31 && Keyboard.isKeyDown(61)) {
                     this.e();
                  }

                  if (var9 == 17 && Keyboard.isKeyDown(61)) {
                  }

                  if (var9 == 18 && Keyboard.isKeyDown(61)) {
                  }

                  if (var9 == 47 && Keyboard.isKeyDown(61)) {
                  }

                  if (var9 == 38 && Keyboard.isKeyDown(61)) {
                  }

                  if (var9 == 22 && Keyboard.isKeyDown(61)) {
                  }

                  if (var9 == 20 && Keyboard.isKeyDown(61)) {
                     this.e();
                  }

                  if (var9 == 33 && Keyboard.isKeyDown(61)) {
                     this.t.a(GameSettings.Options.f, GuiScreen.r() ? -1 : 1);
                  }

                  if (var9 == 30 && Keyboard.isKeyDown(61)) {
                     this.g.a();
                  }

                  if (var9 == 35 && Keyboard.isKeyDown(61)) {
                     this.t.y = !this.t.y;
                     this.t.b();
                  }

                  if (var9 == 48 && Keyboard.isKeyDown(61)) {
                     this.aa.b(!this.aa.b());
                  }

                  if (var9 == 25 && Keyboard.isKeyDown(61)) {
                     this.t.z = !this.t.z;
                     this.t.b();
                  }

                  if (var9 == 59) {
                     this.t.aA = !this.t.aA;
                  }

                  if (var9 == 61) {
                     this.t.aC = !this.t.aC;
                     this.t.aD = GuiScreen.r();
                     this.t.aE = GuiScreen.s();
                  }

                  if (this.t.ao.f()) {
                     this.t.aB++;
                     if (this.t.aB > 2) {
                        this.t.aB = 0;
                     }

                     if (this.t.aB == 0) {
                        this.o.a(this.ac());
                     } else if (this.t.aB == 1) {
                        this.o.a(null);
                     }

                     this.g.m();
                  }

                  if (this.t.ap.f()) {
                     this.t.aG = !this.t.aG;
                  }
               }

               if (this.t.aC && this.t.aD) {
                  if (var9 == 11) {
                     this.b(0);
                  }

                  for (int var14 = 0; var14 < 9; var14++) {
                     if (var9 == 2 + var14) {
                        this.b(var14 + 1);
                     }
                  }
               }
            }
         }

         for (int var10 = 0; var10 < 9; var10++) {
            if (this.t.aw[var10].f()) {
               if (this.h.v()) {
                  this.q.g().a(var10);
               } else {
                  this.h.bi.c = var10;
               }
            }
         }

         boolean var11 = this.t.m != EntityPlayer.EnumChatVisibility.c;

         while (this.t.af.f()) {
            if (this.c.j()) {
               this.h.u();
            } else {
               this.u().a(new C16PacketClientStatus(C16PacketClientStatus.EnumState.c));
               this.a(new GuiInventory(this.h));
            }
         }

         while (this.t.ah.f()) {
            if (!this.h.v()) {
               this.h.a(GuiScreen.q());
            }
         }

         while (this.t.ak.f() && var11) {
            this.a(new GuiChat());
         }

         if (this.m == null && this.t.am.f() && var11) {
            this.a(new GuiChat("/"));
         }

         if (this.h.bS()) {
            if (!this.t.ag.d()) {
               this.c.c(this.h);
            }

            while (this.t.ai.f()) {
            }

            while (this.t.ag.f()) {
            }

            while (this.t.aj.f()) {
            }
         } else {
            while (this.t.ai.f()) {
               this.aw();
            }

            while (this.t.ag.f()) {
               this.ax();
            }

            while (this.t.aj.f()) {
               this.az();
            }
         }

         if (this.t.ag.d() && this.ap == 0 && !this.h.bS()) {
            this.ax();
         }

         this.b(this.m == null && this.t.ai.d() && this.w);
      }

      if (this.f != null) {
         if (this.h != null) {
            this.as++;
            if (this.as == 30) {
               this.as = 0;
               this.f.h(this.h);
            }
         }

         this.A.c("gameRenderer");
         if (!this.af) {
            this.o.e();
         }

         this.A.c("levelRenderer");
         if (!this.af) {
            this.g.j();
         }

         this.A.c("level");
         if (!this.af) {
            if (this.f.ac() > 0) {
               this.f.d(this.f.ac() - 1);
            }

            this.f.i();
         }
      } else if (this.o.a()) {
         this.o.b();
      }

      if (!this.af) {
         this.aI.c();
         this.aH.c();
      }

      if (this.f != null) {
         if (!this.af) {
            this.f.a(this.f.aa() != EnumDifficulty.a, true);

            try {
               this.f.c();
            } catch (Throwable var8) {
               CrashReport var15 = CrashReport.a(var8, "Exception in world tick");
               if (this.f == null) {
                  CrashReportCategory var17 = var15.a("Affected level");
                  var17.a("Problem", "Level is null!");
               } else {
                  this.f.a(var15);
               }

               throw new ReportedException(var15);
            }
         }

         this.A.c("animateTick");
         if (!this.af && this.f != null) {
            this.f.b(MathHelper.c(this.h.s), MathHelper.c(this.h.t), MathHelper.c(this.h.u));
         }

         this.A.c("particles");
         if (!this.af) {
            this.j.a();
         }
      } else if (this.av != null) {
         this.A.c("pendingConnection");
         this.av.a();
      }

      this.A.b();
      this.x = J();
   }

   public void a(String var1, String var2, WorldSettings var3) {
      this.a(null);
      System.gc();
      ISaveHandler var4 = this.an.a(var1, false);
      WorldInfo var5 = var4.d();
      if (var5 == null && var3 != null) {
         var5 = new WorldInfo(var3, var1);
         var4.a(var5);
      }

      if (var3 == null) {
         var3 = new WorldSettings(var5);
      }

      try {
         this.aj = new IntegratedServer(this, var1, var2, var3);
         this.aj.D();
         this.aw = true;
      } catch (Throwable var10) {
         CrashReport var7 = CrashReport.a(var10, "Starting integrated server");
         CrashReportCategory var8 = var7.a("Starting integrated server");
         var8.a("Level ID", var1);
         var8.a("Level Name", var2);
         throw new ReportedException(var7);
      }

      this.n.a(I18n.a("menu.loadingLevel"));

      while (!this.aj.ar()) {
         String var6 = this.aj.j();
         if (var6 != null) {
            this.n.c(I18n.a(var6));
         } else {
            this.n.c("");
         }

         try {
            Thread.sleep(200L);
         } catch (InterruptedException var9) {
         }
      }

      this.a(null);
      SocketAddress var11 = this.aj.aq().a();
      NetworkManager var12 = NetworkManager.a(var11);
      var12.a(new NetHandlerLoginClient(var12, this, null));
      var12.a(new C00Handshake(47, var11.toString(), 0, EnumConnectionState.d));
      var12.a(new C00PacketLoginStart(this.L().e()));
      this.av = var12;
   }

   public void a(WorldClient var1) {
      this.a(var1, "");
   }

   public void a(WorldClient var1, String var2) {
      if (var1 == null) {
         NetHandlerPlayClient var3 = this.u();
         if (var3 != null) {
            var3.b();
         }

         if (this.aj != null && this.aj.O()) {
            this.aj.w();
            this.aj.a();
         }

         this.aj = null;
         this.p.b();
         this.o.k().a();
      }

      this.ad = null;
      this.av = null;
      if (this.n != null) {
         this.n.b(var2);
         this.n.c("");
      }

      if (var1 == null && this.f != null) {
         this.aC.f();
         this.q.i();
         this.a(null);
         this.aw = false;
      }

      this.aH.b();
      this.f = var1;
      if (var1 != null) {
         if (this.g != null) {
            this.g.a(var1);
         }

         if (this.j != null) {
            this.j.a(var1);
         }

         if (this.h == null) {
            this.h = this.c.a(var1, new StatFileWriter());
            this.c.b(this.h);
         }

         this.h.I();
         var1.d(this.h);
         this.h.b = new MovementInputFromOptions(this.t);
         this.c.a(this.h);
         this.ad = this.h;
      } else {
         this.an.d();
         this.h = null;
      }

      System.gc();
      this.x = 0L;
   }

   public void a(int var1) {
      this.f.g();
      this.f.a();
      int var2 = 0;
      String var3 = null;
      if (this.h != null) {
         var2 = this.h.F();
         this.f.e(this.h);
         var3 = this.h.w();
      }

      this.ad = null;
      EntityPlayerSP var4 = this.h;
      this.h = this.c.a(this.f, this.h == null ? new StatFileWriter() : this.h.x());
      this.h.H().a(var4.H().c());
      this.h.am = var1;
      this.ad = this.h;
      this.h.I();
      this.h.f(var3);
      this.f.d(this.h);
      this.c.b(this.h);
      this.h.b = new MovementInputFromOptions(this.t);
      this.h.d(var2);
      this.c.a(this.h);
      this.h.k(var4.cq());
      if (this.m instanceof GuiGameOver) {
         this.a(null);
      }
   }

   public final boolean t() {
      return this.au;
   }

   public NetHandlerPlayClient u() {
      return this.h != null ? this.h.a : null;
   }

   public static boolean v() {
      return S == null || !S.t.aA;
   }

   public static boolean w() {
      return S != null && S.t.i;
   }

   public static boolean x() {
      return S != null && S.t.j != 0;
   }

   private void az() {
      if (this.s != null) {
         boolean var1 = this.h.bA.d;
         int var3 = 0;
         boolean var4 = false;
         TileEntity var5 = null;
         Object var2;
         if (this.s.a == MovingObjectPosition.MovingObjectType.b) {
            BlockPos var6 = this.s.a();
            Block var7 = this.f.p(var6).c();
            if (var7.t() == Material.a) {
               return;
            }

            var2 = var7.c((World)this.f, var6);
            if (var2 == null) {
               return;
            }

            if (var1 && GuiScreen.q()) {
               var5 = this.f.s(var6);
            }

            Block var8 = var2 instanceof ItemBlock && !var7.M() ? Block.a((Item)var2) : var7;
            var3 = var8.j(this.f, var6);
            var4 = ((Item)var2).k();
         } else {
            if (this.s.a != MovingObjectPosition.MovingObjectType.c || this.s.d == null || !var1) {
               return;
            }

            if (this.s.d instanceof EntityPainting) {
               var2 = Items.an;
            } else if (this.s.d instanceof EntityLeashKnot) {
               var2 = Items.cn;
            } else if (this.s.d instanceof EntityItemFrame) {
               EntityItemFrame var9 = (EntityItemFrame)this.s.d;
               ItemStack var12 = var9.o();
               if (var12 == null) {
                  var2 = Items.bP;
               } else {
                  var2 = var12.b();
                  var3 = var12.i();
                  var4 = true;
               }
            } else if (this.s.d instanceof EntityMinecart) {
               EntityMinecart var10 = (EntityMinecart)this.s.d;
               switch (var10.s()) {
                  case c:
                     var2 = Items.aO;
                     break;
                  case b:
                     var2 = Items.aN;
                     break;
                  case d:
                     var2 = Items.ch;
                     break;
                  case f:
                     var2 = Items.ci;
                     break;
                  case g:
                     var2 = Items.cp;
                     break;
                  default:
                     var2 = Items.az;
               }
            } else if (this.s.d instanceof EntityBoat) {
               var2 = Items.aE;
            } else if (this.s.d instanceof EntityArmorStand) {
               var2 = Items.cj;
            } else {
               var2 = Items.bJ;
               var3 = EntityList.a(this.s.d);
               var4 = true;
               if (!EntityList.a.containsKey(var3)) {
                  return;
               }
            }
         }

         InventoryPlayer var11 = this.h.bi;
         if (var5 == null) {
            var11.a((Item)var2, var3, var4, var1);
         } else {
            ItemStack var13 = this.a((Item)var2, var3, var5);
            var11.a(var11.c, var13);
         }

         if (var1) {
            int var14 = this.h.bj.c.size() - 9 + var11.c;
            this.c.a(var11.a(var11.c), var14);
         }
      }
   }

   private ItemStack a(Item var1, int var2, TileEntity var3) {
      ItemStack var4 = new ItemStack(var1, 1, var2);
      NBTTagCompound var5 = new NBTTagCompound();
      var3.b(var5);
      if (var1 == Items.bX && var5.c("Owner")) {
         NBTTagCompound var8 = var5.m("Owner");
         NBTTagCompound var9 = new NBTTagCompound();
         var9.a("SkullOwner", var8);
         var4.d(var9);
         return var4;
      } else {
         var4.a("BlockEntityTag", var5);
         NBTTagCompound var6 = new NBTTagCompound();
         NBTTagList var7 = new NBTTagList();
         var7.a(new NBTTagString("(+NBT)"));
         var6.a("Lore", var7);
         var4.a("display", var6);
         return var4;
      }
   }

   public CrashReport b(CrashReport var1) {
      var1.g().a("Launched Version", new Callable<String>() {
         public String a() {
            return Minecraft.this.al;
         }
      });
      var1.g().a("LWJGL", new Callable<String>() {
         public String a() {
            return Sys.getVersion();
         }
      });
      var1.g().a("OpenGL", new Callable<String>() {
         public String a() {
            return GL11.glGetString(7937) + " GL version " + GL11.glGetString(7938) + ", " + GL11.glGetString(7936);
         }
      });
      var1.g().a("GL Caps", new Callable<String>() {
         public String a() {
            return OpenGlHelper.c();
         }
      });
      var1.g().a("Using VBOs", new Callable<String>() {
         public String a() {
            return Minecraft.this.t.u ? "Yes" : "No";
         }
      });
      var1.g()
         .a(
            "Is Modded",
            new Callable<String>() {
               public String a() throws Exception {
                  String var1 = ClientBrandRetriever.getClientModName();
                  if (!var1.equals("vanilla")) {
                     return "Definitely; Client brand changed to '" + var1 + "'";
                  } else {
                     return Minecraft.class.getSigners() == null
                        ? "Very likely; Jar signature invalidated"
                        : "Probably not. Jar signature remains and client brand is untouched.";
                  }
               }
            }
         );
      var1.g().a("Type", new Callable<String>() {
         public String a() throws Exception {
            return "Client (map_client.txt)";
         }
      });
      var1.g().a("Resource Packs", new Callable<String>() {
         public String a() throws Exception {
            StringBuilder var1 = new StringBuilder();

            for (String var3 : Minecraft.this.t.k) {
               if (var1.length() > 0) {
                  var1.append(", ");
               }

               var1.append(var3);
               if (Minecraft.this.t.l.contains(var3)) {
                  var1.append(" (incompatible)");
               }
            }

            return var1.toString();
         }
      });
      var1.g().a("Current Language", new Callable<String>() {
         public String a() throws Exception {
            return Minecraft.this.aD.c().toString();
         }
      });
      var1.g().a("Profiler Position", new Callable<String>() {
         public String a() throws Exception {
            return Minecraft.this.A.a ? Minecraft.this.A.c() : "N/A (disabled)";
         }
      });
      var1.g().a("CPU", new Callable<String>() {
         public String a() {
            return OpenGlHelper.j();
         }
      });
      if (this.f != null) {
         this.f.a(var1);
      }

      return var1;
   }

   public static Minecraft A() {
      return S;
   }

   public ListenableFuture<Object> B() {
      return this.a(new Runnable() {
         @Override
         public void run() {
            Minecraft.this.e();
         }
      });
   }

   @Override
   public void a(PlayerUsageSnooper var1) {
      var1.a("fps", ao);
      var1.a("vsync_enabled", this.t.t);
      var1.a("display_frequency", Display.getDisplayMode().getFrequency());
      var1.a("display_type", this.T ? "fullscreen" : "windowed");
      var1.a("run_time", (MinecraftServer.az() - var1.g()) / 60L * 1000L);
      var1.a("current_action", this.aA());
      String var2 = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN ? "little" : "big";
      var1.a("endianness", var2);
      var1.a("resource_packs", this.aC.c().size());
      int var3 = 0;

      for (ResourcePackRepository.Entry var5 : this.aC.c()) {
         var1.a("resource_pack[" + var3++ + "]", var5.d());
      }

      if (this.aj != null && this.aj.av() != null) {
         var1.a("snooper_partner", this.aj.av().f());
      }
   }

   private String aA() {
      if (this.aj != null) {
         return this.aj.b() ? "hosting_lan" : "singleplayer";
      } else if (this.Q != null) {
         return this.Q.d() ? "playing_lan" : "multiplayer";
      } else {
         return "out_of_game";
      }
   }

   @Override
   public void b(PlayerUsageSnooper var1) {
      var1.b("opengl_version", GL11.glGetString(7938));
      var1.b("opengl_vendor", GL11.glGetString(7936));
      var1.b("client_brand", ClientBrandRetriever.getClientModName());
      var1.b("launched_version", this.al);
      ContextCapabilities var2 = GLContext.getCapabilities();
      var1.b("gl_caps[ARB_arrays_of_arrays]", var2.GL_ARB_arrays_of_arrays);
      var1.b("gl_caps[ARB_base_instance]", var2.GL_ARB_base_instance);
      var1.b("gl_caps[ARB_blend_func_extended]", var2.GL_ARB_blend_func_extended);
      var1.b("gl_caps[ARB_clear_buffer_object]", var2.GL_ARB_clear_buffer_object);
      var1.b("gl_caps[ARB_color_buffer_float]", var2.GL_ARB_color_buffer_float);
      var1.b("gl_caps[ARB_compatibility]", var2.GL_ARB_compatibility);
      var1.b("gl_caps[ARB_compressed_texture_pixel_storage]", var2.GL_ARB_compressed_texture_pixel_storage);
      var1.b("gl_caps[ARB_compute_shader]", var2.GL_ARB_compute_shader);
      var1.b("gl_caps[ARB_copy_buffer]", var2.GL_ARB_copy_buffer);
      var1.b("gl_caps[ARB_copy_image]", var2.GL_ARB_copy_image);
      var1.b("gl_caps[ARB_depth_buffer_float]", var2.GL_ARB_depth_buffer_float);
      var1.b("gl_caps[ARB_compute_shader]", var2.GL_ARB_compute_shader);
      var1.b("gl_caps[ARB_copy_buffer]", var2.GL_ARB_copy_buffer);
      var1.b("gl_caps[ARB_copy_image]", var2.GL_ARB_copy_image);
      var1.b("gl_caps[ARB_depth_buffer_float]", var2.GL_ARB_depth_buffer_float);
      var1.b("gl_caps[ARB_depth_clamp]", var2.GL_ARB_depth_clamp);
      var1.b("gl_caps[ARB_depth_texture]", var2.GL_ARB_depth_texture);
      var1.b("gl_caps[ARB_draw_buffers]", var2.GL_ARB_draw_buffers);
      var1.b("gl_caps[ARB_draw_buffers_blend]", var2.GL_ARB_draw_buffers_blend);
      var1.b("gl_caps[ARB_draw_elements_base_vertex]", var2.GL_ARB_draw_elements_base_vertex);
      var1.b("gl_caps[ARB_draw_indirect]", var2.GL_ARB_draw_indirect);
      var1.b("gl_caps[ARB_draw_instanced]", var2.GL_ARB_draw_instanced);
      var1.b("gl_caps[ARB_explicit_attrib_location]", var2.GL_ARB_explicit_attrib_location);
      var1.b("gl_caps[ARB_explicit_uniform_location]", var2.GL_ARB_explicit_uniform_location);
      var1.b("gl_caps[ARB_fragment_layer_viewport]", var2.GL_ARB_fragment_layer_viewport);
      var1.b("gl_caps[ARB_fragment_program]", var2.GL_ARB_fragment_program);
      var1.b("gl_caps[ARB_fragment_shader]", var2.GL_ARB_fragment_shader);
      var1.b("gl_caps[ARB_fragment_program_shadow]", var2.GL_ARB_fragment_program_shadow);
      var1.b("gl_caps[ARB_framebuffer_object]", var2.GL_ARB_framebuffer_object);
      var1.b("gl_caps[ARB_framebuffer_sRGB]", var2.GL_ARB_framebuffer_sRGB);
      var1.b("gl_caps[ARB_geometry_shader4]", var2.GL_ARB_geometry_shader4);
      var1.b("gl_caps[ARB_gpu_shader5]", var2.GL_ARB_gpu_shader5);
      var1.b("gl_caps[ARB_half_float_pixel]", var2.GL_ARB_half_float_pixel);
      var1.b("gl_caps[ARB_half_float_vertex]", var2.GL_ARB_half_float_vertex);
      var1.b("gl_caps[ARB_instanced_arrays]", var2.GL_ARB_instanced_arrays);
      var1.b("gl_caps[ARB_map_buffer_alignment]", var2.GL_ARB_map_buffer_alignment);
      var1.b("gl_caps[ARB_map_buffer_range]", var2.GL_ARB_map_buffer_range);
      var1.b("gl_caps[ARB_multisample]", var2.GL_ARB_multisample);
      var1.b("gl_caps[ARB_multitexture]", var2.GL_ARB_multitexture);
      var1.b("gl_caps[ARB_occlusion_query2]", var2.GL_ARB_occlusion_query2);
      var1.b("gl_caps[ARB_pixel_buffer_object]", var2.GL_ARB_pixel_buffer_object);
      var1.b("gl_caps[ARB_seamless_cube_map]", var2.GL_ARB_seamless_cube_map);
      var1.b("gl_caps[ARB_shader_objects]", var2.GL_ARB_shader_objects);
      var1.b("gl_caps[ARB_shader_stencil_export]", var2.GL_ARB_shader_stencil_export);
      var1.b("gl_caps[ARB_shader_texture_lod]", var2.GL_ARB_shader_texture_lod);
      var1.b("gl_caps[ARB_shadow]", var2.GL_ARB_shadow);
      var1.b("gl_caps[ARB_shadow_ambient]", var2.GL_ARB_shadow_ambient);
      var1.b("gl_caps[ARB_stencil_texturing]", var2.GL_ARB_stencil_texturing);
      var1.b("gl_caps[ARB_sync]", var2.GL_ARB_sync);
      var1.b("gl_caps[ARB_tessellation_shader]", var2.GL_ARB_tessellation_shader);
      var1.b("gl_caps[ARB_texture_border_clamp]", var2.GL_ARB_texture_border_clamp);
      var1.b("gl_caps[ARB_texture_buffer_object]", var2.GL_ARB_texture_buffer_object);
      var1.b("gl_caps[ARB_texture_cube_map]", var2.GL_ARB_texture_cube_map);
      var1.b("gl_caps[ARB_texture_cube_map_array]", var2.GL_ARB_texture_cube_map_array);
      var1.b("gl_caps[ARB_texture_non_power_of_two]", var2.GL_ARB_texture_non_power_of_two);
      var1.b("gl_caps[ARB_uniform_buffer_object]", var2.GL_ARB_uniform_buffer_object);
      var1.b("gl_caps[ARB_vertex_blend]", var2.GL_ARB_vertex_blend);
      var1.b("gl_caps[ARB_vertex_buffer_object]", var2.GL_ARB_vertex_buffer_object);
      var1.b("gl_caps[ARB_vertex_program]", var2.GL_ARB_vertex_program);
      var1.b("gl_caps[ARB_vertex_shader]", var2.GL_ARB_vertex_shader);
      var1.b("gl_caps[EXT_bindable_uniform]", var2.GL_EXT_bindable_uniform);
      var1.b("gl_caps[EXT_blend_equation_separate]", var2.GL_EXT_blend_equation_separate);
      var1.b("gl_caps[EXT_blend_func_separate]", var2.GL_EXT_blend_func_separate);
      var1.b("gl_caps[EXT_blend_minmax]", var2.GL_EXT_blend_minmax);
      var1.b("gl_caps[EXT_blend_subtract]", var2.GL_EXT_blend_subtract);
      var1.b("gl_caps[EXT_draw_instanced]", var2.GL_EXT_draw_instanced);
      var1.b("gl_caps[EXT_framebuffer_multisample]", var2.GL_EXT_framebuffer_multisample);
      var1.b("gl_caps[EXT_framebuffer_object]", var2.GL_EXT_framebuffer_object);
      var1.b("gl_caps[EXT_framebuffer_sRGB]", var2.GL_EXT_framebuffer_sRGB);
      var1.b("gl_caps[EXT_geometry_shader4]", var2.GL_EXT_geometry_shader4);
      var1.b("gl_caps[EXT_gpu_program_parameters]", var2.GL_EXT_gpu_program_parameters);
      var1.b("gl_caps[EXT_gpu_shader4]", var2.GL_EXT_gpu_shader4);
      var1.b("gl_caps[EXT_multi_draw_arrays]", var2.GL_EXT_multi_draw_arrays);
      var1.b("gl_caps[EXT_packed_depth_stencil]", var2.GL_EXT_packed_depth_stencil);
      var1.b("gl_caps[EXT_paletted_texture]", var2.GL_EXT_paletted_texture);
      var1.b("gl_caps[EXT_rescale_normal]", var2.GL_EXT_rescale_normal);
      var1.b("gl_caps[EXT_separate_shader_objects]", var2.GL_EXT_separate_shader_objects);
      var1.b("gl_caps[EXT_shader_image_load_store]", var2.GL_EXT_shader_image_load_store);
      var1.b("gl_caps[EXT_shadow_funcs]", var2.GL_EXT_shadow_funcs);
      var1.b("gl_caps[EXT_shared_texture_palette]", var2.GL_EXT_shared_texture_palette);
      var1.b("gl_caps[EXT_stencil_clear_tag]", var2.GL_EXT_stencil_clear_tag);
      var1.b("gl_caps[EXT_stencil_two_side]", var2.GL_EXT_stencil_two_side);
      var1.b("gl_caps[EXT_stencil_wrap]", var2.GL_EXT_stencil_wrap);
      var1.b("gl_caps[EXT_texture_3d]", var2.GL_EXT_texture_3d);
      var1.b("gl_caps[EXT_texture_array]", var2.GL_EXT_texture_array);
      var1.b("gl_caps[EXT_texture_buffer_object]", var2.GL_EXT_texture_buffer_object);
      var1.b("gl_caps[EXT_texture_integer]", var2.GL_EXT_texture_integer);
      var1.b("gl_caps[EXT_texture_lod_bias]", var2.GL_EXT_texture_lod_bias);
      var1.b("gl_caps[EXT_texture_sRGB]", var2.GL_EXT_texture_sRGB);
      var1.b("gl_caps[EXT_vertex_shader]", var2.GL_EXT_vertex_shader);
      var1.b("gl_caps[EXT_vertex_weighting]", var2.GL_EXT_vertex_weighting);
      var1.b("gl_caps[gl_max_vertex_uniforms]", GL11.glGetInteger(35658));
      GL11.glGetError();
      var1.b("gl_caps[gl_max_fragment_uniforms]", GL11.glGetInteger(35657));
      GL11.glGetError();
      var1.b("gl_caps[gl_max_vertex_attribs]", GL11.glGetInteger(34921));
      GL11.glGetError();
      var1.b("gl_caps[gl_max_vertex_texture_image_units]", GL11.glGetInteger(35660));
      GL11.glGetError();
      var1.b("gl_caps[gl_max_texture_image_units]", GL11.glGetInteger(34930));
      GL11.glGetError();
      var1.b("gl_caps[gl_max_texture_image_units]", GL11.glGetInteger(35071));
      GL11.glGetError();
      var1.b("gl_max_texture_size", C());
   }

   public static int C() {
      for (short var0 = 16384; var0 > 0; var0 >>= 1) {
         GL11.glTexImage2D(32868, 0, 6408, var0, var0, 0, 6408, 5121, (ByteBuffer)null);
         int var1 = GL11.glGetTexLevelParameteri(32868, 0, 4096);
         if (var1 != 0) {
            return var0;
         }
      }

      return -1;
   }

   @Override
   public boolean ad() {
      return this.t.r;
   }

   public void a(ServerData var1) {
      this.Q = var1;
   }

   public ServerData D() {
      return this.Q;
   }

   public boolean E() {
      return this.aw;
   }

   public boolean F() {
      return this.aw && this.aj != null;
   }

   public IntegratedServer G() {
      return this.aj;
   }

   public static void H() {
      if (S != null) {
         IntegratedServer var0 = S.G();
         if (var0 != null) {
            var0.t();
         }
      }
   }

   public PlayerUsageSnooper I() {
      return this.Z;
   }

   public static long J() {
      return Sys.getTime() * 1000L / Sys.getTimerResolution();
   }

   public boolean K() {
      return this.T;
   }

   public Session L() {
      return this.ae;
   }

   public PropertyMap M() {
      return this.O;
   }

   public PropertyMap N() {
      if (this.P.isEmpty()) {
         GameProfile var1 = this.aa().fillProfileProperties(this.ae.e(), false);
         this.P.putAll(var1.getProperties());
      }

      return this.P;
   }

   public Proxy O() {
      return this.am;
   }

   public TextureManager P() {
      return this.R;
   }

   public IResourceManager Q() {
      return this.ay;
   }

   public ResourcePackRepository R() {
      return this.aC;
   }

   public LanguageManager S() {
      return this.aD;
   }

   public TextureMap T() {
      return this.aG;
   }

   public boolean U() {
      return this.at;
   }

   public boolean V() {
      return this.af;
   }

   public SoundHandler W() {
      return this.aH;
   }

   public MusicTicker.MusicType X() {
      if (this.h != null) {
         if (this.h.o.t instanceof WorldProviderHell) {
            return MusicTicker.MusicType.e;
         } else if (this.h.o.t instanceof WorldProviderEnd) {
            return BossStatus.c != null && BossStatus.b > 0 ? MusicTicker.MusicType.f : MusicTicker.MusicType.g;
         } else {
            return this.h.bA.d && this.h.bA.c ? MusicTicker.MusicType.c : MusicTicker.MusicType.b;
         }
      } else {
         return MusicTicker.MusicType.a;
      }
   }

   public IStream Y() {
      return this.aE;
   }

   public void Z() {
      int var1 = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() : Keyboard.getEventKey();
      if (var1 != 0 && !Keyboard.isRepeatEvent()) {
         if (!(this.m instanceof GuiControls) || ((GuiControls)this.m).g <= J() - 20L) {
            if (Keyboard.getEventKeyState()) {
               if (var1 == this.t.as.i()) {
                  if (this.Y().k()) {
                     this.Y().r();
                  } else if (this.Y().j()) {
                     this.a(new GuiYesNo(new GuiYesNoCallback() {
                        @Override
                        public void a(boolean var1, int var2) {
                           if (var1) {
                              Minecraft.this.Y().q();
                           }

                           Minecraft.this.a(null);
                        }
                     }, I18n.a("stream.confirm_start"), "", 0));
                  } else if (!this.Y().A() || !this.Y().i()) {
                     GuiStreamUnavailable.a(this.m);
                  } else if (this.f != null) {
                     this.q.d().a(new ChatComponentText("Not ready to start streaming yet!"));
                  }
               } else if (var1 == this.t.at.i()) {
                  if (this.Y().k()) {
                     if (this.Y().l()) {
                        this.Y().o();
                     } else {
                        this.Y().n();
                     }
                  }
               } else if (var1 == this.t.au.i()) {
                  if (this.Y().k()) {
                     this.Y().m();
                  }
               } else if (var1 == this.t.av.i()) {
                  this.aE.a(true);
               } else if (var1 == this.t.aq.i()) {
                  this.q();
               } else if (var1 == this.t.an.i()) {
                  this.q.d().a(ScreenShotHelper.a(this.v, this.d, this.e, this.aF));
               }
            } else if (var1 == this.t.av.i()) {
               this.aE.a(false);
            }
         }
      }
   }

   public MinecraftSessionService aa() {
      return this.aK;
   }

   public SkinManager ab() {
      return this.aL;
   }

   public Entity ac() {
      return this.ad;
   }

   public void a(Entity var1) {
      this.ad = var1;
      this.o.a(var1);
   }

   public <V> ListenableFuture<V> a(Callable<V> var1) {
      Validate.notNull(var1);
      if (!this.aJ()) {
         ListenableFutureTask var2 = ListenableFutureTask.create(var1);
         synchronized (this.aM) {
            this.aM.add(var2);
            return var2;
         }
      } else {
         try {
            return Futures.immediateFuture(var1.call());
         } catch (Exception var6) {
            return Futures.immediateFailedCheckedFuture(var6);
         }
      }
   }

   @Override
   public ListenableFuture<Object> a(Runnable var1) {
      Validate.notNull(var1);
      return this.a(Executors.callable(var1));
   }

   @Override
   public boolean aJ() {
      return Thread.currentThread() == this.aO;
   }

   public BlockRendererDispatcher ae() {
      return this.aQ;
   }

   public RenderManager af() {
      return this.aa;
   }

   public RenderItem ag() {
      return this.ab;
   }

   public ItemRenderer ah() {
      return this.ac;
   }

   public static int ai() {
      return ao;
   }

   public FrameTimer aj() {
      return this.y;
   }

   public static Map<String, String> ak() {
      HashMap var0 = Maps.newHashMap();
      var0.put("X-Minecraft-Username", A().L().c());
      var0.put("X-Minecraft-UUID", A().L().b());
      var0.put("X-Minecraft-Version", "1.8.9");
      return var0;
   }

   public boolean al() {
      return this.X;
   }

   public void a(boolean var1) {
      this.X = var1;
   }
}
