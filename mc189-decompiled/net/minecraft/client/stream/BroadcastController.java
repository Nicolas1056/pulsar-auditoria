package net.minecraft.client.stream;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ThreadSafeBoundList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tv.twitch.AuthToken;
import tv.twitch.Core;
import tv.twitch.ErrorCode;
import tv.twitch.MessageLevel;
import tv.twitch.StandardCoreAPI;
import tv.twitch.broadcast.ArchivingState;
import tv.twitch.broadcast.AudioDeviceType;
import tv.twitch.broadcast.AudioParams;
import tv.twitch.broadcast.ChannelInfo;
import tv.twitch.broadcast.DesktopStreamAPI;
import tv.twitch.broadcast.EncodingCpuUsage;
import tv.twitch.broadcast.FrameBuffer;
import tv.twitch.broadcast.GameInfo;
import tv.twitch.broadcast.GameInfoList;
import tv.twitch.broadcast.IStatCallbacks;
import tv.twitch.broadcast.IStreamCallbacks;
import tv.twitch.broadcast.IngestList;
import tv.twitch.broadcast.IngestServer;
import tv.twitch.broadcast.PixelFormat;
import tv.twitch.broadcast.StartFlags;
import tv.twitch.broadcast.StatType;
import tv.twitch.broadcast.Stream;
import tv.twitch.broadcast.StreamInfo;
import tv.twitch.broadcast.StreamInfoForSetting;
import tv.twitch.broadcast.UserInfo;
import tv.twitch.broadcast.VideoParams;

public class BroadcastController {
   private static final Logger D = LogManager.getLogger();
   protected final int a = 30;
   protected final int b = 3;
   private static final ThreadSafeBoundList<String> E = new ThreadSafeBoundList<>(String.class, 50);
   private String F = null;
   protected BroadcastController.BroadcastListener c = null;
   protected String d = "";
   protected String e = "";
   protected String f = "";
   protected boolean g = true;
   protected Core h = null;
   protected Stream i = null;
   protected List<FrameBuffer> j = Lists.newArrayList();
   protected List<FrameBuffer> k = Lists.newArrayList();
   protected boolean l = false;
   protected boolean m = false;
   protected boolean n = false;
   protected BroadcastController.BroadcastState o = BroadcastController.BroadcastState.a;
   protected String p = null;
   protected VideoParams q = null;
   protected AudioParams r = null;
   protected IngestList s = new IngestList(new IngestServer[0]);
   protected IngestServer t = null;
   protected AuthToken u = new AuthToken();
   protected ChannelInfo v = new ChannelInfo();
   protected UserInfo w = new UserInfo();
   protected StreamInfo x = new StreamInfo();
   protected ArchivingState y = new ArchivingState();
   protected long z = 0L;
   protected IngestServerTester A = null;
   private ErrorCode G;
   protected IStreamCallbacks B = new IStreamCallbacks() {
      public void requestAuthTokenCallback(ErrorCode var1, AuthToken var2) {
         if (ErrorCode.succeeded(var1)) {
            BroadcastController.this.u = var2;
            BroadcastController.this.a(BroadcastController.BroadcastState.d);
         } else {
            BroadcastController.this.u.data = "";
            BroadcastController.this.a(BroadcastController.BroadcastState.b);
            String var3 = ErrorCode.getString(var1);
            BroadcastController.this.d(String.format("RequestAuthTokenDoneCallback got failure: %s", var3));
         }

         try {
            if (BroadcastController.this.c != null) {
               BroadcastController.this.c.a(var1, var2);
            }
         } catch (Exception var4) {
            BroadcastController.this.d(var4.toString());
         }
      }

      public void loginCallback(ErrorCode var1, ChannelInfo var2) {
         if (ErrorCode.succeeded(var1)) {
            BroadcastController.this.v = var2;
            BroadcastController.this.a(BroadcastController.BroadcastState.f);
            BroadcastController.this.m = true;
         } else {
            BroadcastController.this.a(BroadcastController.BroadcastState.b);
            BroadcastController.this.m = false;
            String var3 = ErrorCode.getString(var1);
            BroadcastController.this.d(String.format("LoginCallback got failure: %s", var3));
         }

         try {
            if (BroadcastController.this.c != null) {
               BroadcastController.this.c.a(var1);
            }
         } catch (Exception var4) {
            BroadcastController.this.d(var4.toString());
         }
      }

      public void getIngestServersCallback(ErrorCode var1, IngestList var2) {
         if (ErrorCode.succeeded(var1)) {
            BroadcastController.this.s = var2;
            BroadcastController.this.t = BroadcastController.this.s.getDefaultServer();
            BroadcastController.this.a(BroadcastController.BroadcastState.h);

            try {
               if (BroadcastController.this.c != null) {
                  BroadcastController.this.c.a(var2);
               }
            } catch (Exception var4) {
               BroadcastController.this.d(var4.toString());
            }
         } else {
            String var3 = ErrorCode.getString(var1);
            BroadcastController.this.d(String.format("IngestListCallback got failure: %s", var3));
            BroadcastController.this.a(BroadcastController.BroadcastState.e);
         }
      }

      public void getUserInfoCallback(ErrorCode var1, UserInfo var2) {
         BroadcastController.this.w = var2;
         if (ErrorCode.failed(var1)) {
            String var3 = ErrorCode.getString(var1);
            BroadcastController.this.d(String.format("UserInfoDoneCallback got failure: %s", var3));
         }
      }

      public void getStreamInfoCallback(ErrorCode var1, StreamInfo var2) {
         if (ErrorCode.succeeded(var1)) {
            BroadcastController.this.x = var2;

            try {
               if (BroadcastController.this.c != null) {
                  BroadcastController.this.c.a(var2);
               }
            } catch (Exception var4) {
               BroadcastController.this.d(var4.toString());
            }
         } else {
            String var3 = ErrorCode.getString(var1);
            BroadcastController.this.e(String.format("StreamInfoDoneCallback got failure: %s", var3));
         }
      }

      public void getArchivingStateCallback(ErrorCode var1, ArchivingState var2) {
         BroadcastController.this.y = var2;
         if (ErrorCode.failed(var1)) {
         }
      }

      public void runCommercialCallback(ErrorCode var1) {
         if (ErrorCode.failed(var1)) {
            String var2 = ErrorCode.getString(var1);
            BroadcastController.this.e(String.format("RunCommercialCallback got failure: %s", var2));
         }
      }

      public void setStreamInfoCallback(ErrorCode var1) {
         if (ErrorCode.failed(var1)) {
            String var2 = ErrorCode.getString(var1);
            BroadcastController.this.e(String.format("SetStreamInfoCallback got failure: %s", var2));
         }
      }

      public void getGameNameListCallback(ErrorCode var1, GameInfoList var2) {
         if (ErrorCode.failed(var1)) {
            String var3 = ErrorCode.getString(var1);
            BroadcastController.this.d(String.format("GameNameListCallback got failure: %s", var3));
         }

         try {
            if (BroadcastController.this.c != null) {
               BroadcastController.this.c.a(var1, var2 == null ? new GameInfo[0] : var2.list);
            }
         } catch (Exception var4) {
            BroadcastController.this.d(var4.toString());
         }
      }

      public void bufferUnlockCallback(long var1) {
         FrameBuffer var3 = FrameBuffer.lookupBuffer(var1);
         BroadcastController.this.k.add(var3);
      }

      public void startCallback(ErrorCode var1) {
         if (ErrorCode.succeeded(var1)) {
            try {
               if (BroadcastController.this.c != null) {
                  BroadcastController.this.c.b();
               }
            } catch (Exception var4) {
               BroadcastController.this.d(var4.toString());
            }

            BroadcastController.this.a(BroadcastController.BroadcastState.k);
         } else {
            BroadcastController.this.q = null;
            BroadcastController.this.r = null;
            BroadcastController.this.a(BroadcastController.BroadcastState.i);

            try {
               if (BroadcastController.this.c != null) {
                  BroadcastController.this.c.c(var1);
               }
            } catch (Exception var3) {
               BroadcastController.this.d(var3.toString());
            }

            String var2 = ErrorCode.getString(var1);
            BroadcastController.this.d(String.format("startCallback got failure: %s", var2));
         }
      }

      public void stopCallback(ErrorCode var1) {
         if (ErrorCode.succeeded(var1)) {
            BroadcastController.this.q = null;
            BroadcastController.this.r = null;
            BroadcastController.this.P();

            try {
               if (BroadcastController.this.c != null) {
                  BroadcastController.this.c.c();
               }
            } catch (Exception var3) {
               BroadcastController.this.d(var3.toString());
            }

            if (BroadcastController.this.m) {
               BroadcastController.this.a(BroadcastController.BroadcastState.i);
            } else {
               BroadcastController.this.a(BroadcastController.BroadcastState.b);
            }
         } else {
            BroadcastController.this.a(BroadcastController.BroadcastState.i);
            String var2 = ErrorCode.getString(var1);
            BroadcastController.this.d(String.format("stopCallback got failure: %s", var2));
         }
      }

      public void sendActionMetaDataCallback(ErrorCode var1) {
         if (ErrorCode.failed(var1)) {
            String var2 = ErrorCode.getString(var1);
            BroadcastController.this.d(String.format("sendActionMetaDataCallback got failure: %s", var2));
         }
      }

      public void sendStartSpanMetaDataCallback(ErrorCode var1) {
         if (ErrorCode.failed(var1)) {
            String var2 = ErrorCode.getString(var1);
            BroadcastController.this.d(String.format("sendStartSpanMetaDataCallback got failure: %s", var2));
         }
      }

      public void sendEndSpanMetaDataCallback(ErrorCode var1) {
         if (ErrorCode.failed(var1)) {
            String var2 = ErrorCode.getString(var1);
            BroadcastController.this.d(String.format("sendEndSpanMetaDataCallback got failure: %s", var2));
         }
      }
   };
   protected IStatCallbacks C = new IStatCallbacks() {
      public void statCallback(StatType var1, long var2) {
      }
   };

   public void a(BroadcastController.BroadcastListener var1) {
      this.c = var1;
   }

   public boolean b() {
      return this.l;
   }

   public void a(String var1) {
      this.d = var1;
   }

   public StreamInfo j() {
      return this.x;
   }

   public ChannelInfo l() {
      return this.v;
   }

   public boolean m() {
      return this.o == BroadcastController.BroadcastState.k || this.o == BroadcastController.BroadcastState.m;
   }

   public boolean n() {
      return this.o == BroadcastController.BroadcastState.i;
   }

   public boolean o() {
      return this.o == BroadcastController.BroadcastState.n;
   }

   public boolean p() {
      return this.o == BroadcastController.BroadcastState.m;
   }

   public boolean q() {
      return this.m;
   }

   public IngestServer s() {
      return this.t;
   }

   public void a(IngestServer var1) {
      this.t = var1;
   }

   public IngestList t() {
      return this.s;
   }

   public void a(float var1) {
      this.i.setVolume(AudioDeviceType.TTV_RECORDER_DEVICE, var1);
   }

   public void b(float var1) {
      this.i.setVolume(AudioDeviceType.TTV_PLAYBACK_DEVICE, var1);
   }

   public IngestServerTester w() {
      return this.A;
   }

   public long x() {
      return this.i.getStreamTime();
   }

   protected boolean y() {
      return true;
   }

   public ErrorCode A() {
      return this.G;
   }

   public BroadcastController() {
      this.h = Core.getInstance();
      if (Core.getInstance() == null) {
         this.h = new Core(new StandardCoreAPI());
      }

      this.i = new Stream(new DesktopStreamAPI());
   }

   protected PixelFormat B() {
      return PixelFormat.TTV_PF_RGBA;
   }

   public boolean C() {
      if (this.l) {
         return false;
      } else {
         this.i.setStreamCallbacks(this.B);
         ErrorCode var1 = this.h.initialize(this.d, System.getProperty("java.library.path"));
         if (!this.a(var1)) {
            this.i.setStreamCallbacks(null);
            this.G = var1;
            return false;
         } else {
            var1 = this.h.setTraceLevel(MessageLevel.TTV_ML_ERROR);
            if (!this.a(var1)) {
               this.i.setStreamCallbacks(null);
               this.h.shutdown();
               this.G = var1;
               return false;
            } else if (ErrorCode.succeeded(var1)) {
               this.l = true;
               this.a(BroadcastController.BroadcastState.b);
               return true;
            } else {
               this.G = var1;
               this.h.shutdown();
               return false;
            }
         }
      }
   }

   public boolean D() {
      if (!this.l) {
         return true;
      } else if (this.o()) {
         return false;
      } else {
         this.n = true;
         this.F();
         this.i.setStreamCallbacks(null);
         this.i.setStatCallbacks(null);
         ErrorCode var1 = this.h.shutdown();
         this.a(var1);
         this.l = false;
         this.n = false;
         this.a(BroadcastController.BroadcastState.a);
         return true;
      }
   }

   public void E() {
      if (this.o != BroadcastController.BroadcastState.a) {
         if (this.A != null) {
            this.A.m();
         }

         for (; this.A != null; this.K()) {
            try {
               Thread.sleep(200L);
            } catch (Exception var2) {
               this.d(var2.toString());
            }
         }

         this.D();
      }
   }

   public boolean a(String var1, AuthToken var2) {
      if (this.o()) {
         return false;
      } else {
         this.F();
         if (var1 == null || var1.isEmpty()) {
            this.d("Username must be valid");
            return false;
         } else if (var2 != null && var2.data != null && !var2.data.isEmpty()) {
            this.p = var1;
            this.u = var2;
            if (this.b()) {
               this.a(BroadcastController.BroadcastState.d);
            }

            return true;
         } else {
            this.d("Auth token must be valid");
            return false;
         }
      }
   }

   public boolean F() {
      if (this.o()) {
         return false;
      } else {
         if (this.m()) {
            this.i.stop(false);
         }

         this.p = "";
         this.u = new AuthToken();
         if (!this.m) {
            return false;
         } else {
            this.m = false;
            if (!this.n) {
               try {
                  if (this.c != null) {
                     this.c.a();
                  }
               } catch (Exception var2) {
                  this.d(var2.toString());
               }
            }

            this.a(BroadcastController.BroadcastState.b);
            return true;
         }
      }
   }

   public boolean a(String var1, String var2, String var3) {
      if (!this.m) {
         return false;
      } else {
         if (var1 == null || var1.equals("")) {
            var1 = this.p;
         }

         if (var2 == null) {
            var2 = "";
         }

         if (var3 == null) {
            var3 = "";
         }

         StreamInfoForSetting var4 = new StreamInfoForSetting();
         var4.streamTitle = var3;
         var4.gameName = var2;
         ErrorCode var5 = this.i.setStreamInfo(this.u, var1, var4);
         this.a(var5);
         return ErrorCode.succeeded(var5);
      }
   }

   public boolean G() {
      if (!this.m()) {
         return false;
      } else {
         ErrorCode var1 = this.i.runCommercial(this.u);
         this.a(var1);
         return ErrorCode.succeeded(var1);
      }
   }

   public VideoParams a(int var1, int var2, float var3, float var4) {
      int[] var5 = this.i.getMaxResolution(var1, var2, var3, var4);
      VideoParams var6 = new VideoParams();
      var6.maxKbps = var1;
      var6.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
      var6.pixelFormat = this.B();
      var6.targetFps = var2;
      var6.outputWidth = var5[0];
      var6.outputHeight = var5[1];
      var6.disableAdaptiveBitrate = false;
      var6.verticalFlip = false;
      return var6;
   }

   public boolean a(VideoParams var1) {
      if (var1 != null && this.n()) {
         this.q = var1.clone();
         this.r = new AudioParams();
         this.r.audioEnabled = this.g && this.y();
         this.r.enableMicCapture = this.r.audioEnabled;
         this.r.enablePlaybackCapture = this.r.audioEnabled;
         this.r.enablePassthroughAudio = false;
         if (!this.O()) {
            this.q = null;
            this.r = null;
            return false;
         } else {
            ErrorCode var2 = this.i.start(var1, this.r, this.t, StartFlags.None, true);
            if (ErrorCode.failed(var2)) {
               this.P();
               String var3 = ErrorCode.getString(var2);
               this.d(String.format("Error while starting to broadcast: %s", var3));
               this.q = null;
               this.r = null;
               return false;
            } else {
               this.a(BroadcastController.BroadcastState.j);
               return true;
            }
         }
      } else {
         return false;
      }
   }

   public boolean H() {
      if (!this.m()) {
         return false;
      } else {
         ErrorCode var1 = this.i.stop(true);
         if (ErrorCode.failed(var1)) {
            String var2 = ErrorCode.getString(var1);
            this.d(String.format("Error while stopping the broadcast: %s", var2));
            return false;
         } else {
            this.a(BroadcastController.BroadcastState.l);
            return ErrorCode.succeeded(var1);
         }
      }
   }

   public boolean I() {
      if (!this.m()) {
         return false;
      } else {
         ErrorCode var1 = this.i.pauseVideo();
         if (ErrorCode.failed(var1)) {
            this.H();
            String var2 = ErrorCode.getString(var1);
            this.d(String.format("Error pausing stream: %s\n", var2));
         } else {
            this.a(BroadcastController.BroadcastState.m);
         }

         return ErrorCode.succeeded(var1);
      }
   }

   public boolean J() {
      if (!this.p()) {
         return false;
      } else {
         this.a(BroadcastController.BroadcastState.k);
         return true;
      }
   }

   public boolean a(String var1, long var2, String var4, String var5) {
      ErrorCode var6 = this.i.sendActionMetaData(this.u, var1, var2, var4, var5);
      if (ErrorCode.failed(var6)) {
         String var7 = ErrorCode.getString(var6);
         this.d(String.format("Error while sending meta data: %s\n", var7));
         return false;
      } else {
         return true;
      }
   }

   public long b(String var1, long var2, String var4, String var5) {
      long var6 = this.i.sendStartSpanMetaData(this.u, var1, var2, var4, var5);
      if (var6 == -1L) {
         this.d(String.format("Error in SendStartSpanMetaData\n"));
      }

      return var6;
   }

   public boolean a(String var1, long var2, long var4, String var6, String var7) {
      if (var4 == -1L) {
         this.d(String.format("Invalid sequence id: %d\n", var4));
         return false;
      } else {
         ErrorCode var8 = this.i.sendEndSpanMetaData(this.u, var1, var2, var4, var6, var7);
         if (ErrorCode.failed(var8)) {
            String var9 = ErrorCode.getString(var8);
            this.d(String.format("Error in SendStopSpanMetaData: %s\n", var9));
            return false;
         } else {
            return true;
         }
      }
   }

   protected void a(BroadcastController.BroadcastState var1) {
      if (var1 != this.o) {
         this.o = var1;

         try {
            if (this.c != null) {
               this.c.a(var1);
            }
         } catch (Exception var3) {
            this.d(var3.toString());
         }
      }
   }

   public void K() {
      if (this.i != null && this.l) {
         ErrorCode var1 = this.i.pollTasks();
         this.a(var1);
         if (this.o()) {
            this.A.k();
            if (this.A.f()) {
               this.A = null;
               this.a(BroadcastController.BroadcastState.i);
            }
         }

         switch (this.o) {
            case d:
               this.a(BroadcastController.BroadcastState.e);
               var1 = this.i.login(this.u);
               if (ErrorCode.failed(var1)) {
                  String var9 = ErrorCode.getString(var1);
                  this.d(String.format("Error in TTV_Login: %s\n", var9));
               }
               break;
            case f:
               this.a(BroadcastController.BroadcastState.g);
               var1 = this.i.getIngestServers(this.u);
               if (ErrorCode.failed(var1)) {
                  this.a(BroadcastController.BroadcastState.f);
                  String var8 = ErrorCode.getString(var1);
                  this.d(String.format("Error in TTV_GetIngestServers: %s\n", var8));
               }
               break;
            case h:
               this.a(BroadcastController.BroadcastState.i);
               var1 = this.i.getUserInfo(this.u);
               if (ErrorCode.failed(var1)) {
                  String var2 = ErrorCode.getString(var1);
                  this.d(String.format("Error in TTV_GetUserInfo: %s\n", var2));
               }

               this.L();
               var1 = this.i.getArchivingState(this.u);
               if (ErrorCode.failed(var1)) {
                  String var7 = ErrorCode.getString(var1);
                  this.d(String.format("Error in TTV_GetArchivingState: %s\n", var7));
               }
            case j:
            case l:
            case g:
            case c:
            case b:
            case a:
            case n:
            default:
               break;
            case m:
            case k:
               this.L();
         }
      }
   }

   protected void L() {
      long var1 = System.nanoTime();
      long var3 = (var1 - this.z) / 1000000000L;
      if (var3 >= 30L) {
         this.z = var1;
         ErrorCode var5 = this.i.getStreamInfo(this.u, this.p);
         if (ErrorCode.failed(var5)) {
            String var6 = ErrorCode.getString(var5);
            this.d(String.format("Error in TTV_GetStreamInfo: %s", var6));
         }
      }
   }

   public IngestServerTester M() {
      if (!this.n() || this.s == null) {
         return null;
      } else if (this.o()) {
         return null;
      } else {
         this.A = new IngestServerTester(this.i, this.s);
         this.A.j();
         this.a(BroadcastController.BroadcastState.n);
         return this.A;
      }
   }

   protected boolean O() {
      for (int var1 = 0; var1 < 3; var1++) {
         FrameBuffer var2 = this.i.allocateFrameBuffer(this.q.outputWidth * this.q.outputHeight * 4);
         if (!var2.getIsValid()) {
            this.d(String.format("Error while allocating frame buffer"));
            return false;
         }

         this.j.add(var2);
         this.k.add(var2);
      }

      return true;
   }

   protected void P() {
      for (int var1 = 0; var1 < this.j.size(); var1++) {
         FrameBuffer var2 = this.j.get(var1);
         var2.free();
      }

      this.k.clear();
      this.j.clear();
   }

   public FrameBuffer Q() {
      if (this.k.size() == 0) {
         this.d(String.format("Out of free buffers, this should never happen"));
         return null;
      } else {
         FrameBuffer var1 = this.k.get(this.k.size() - 1);
         this.k.remove(this.k.size() - 1);
         return var1;
      }
   }

   public void a(FrameBuffer var1) {
      try {
         this.i.captureFrameBuffer_ReadPixels(var1);
      } catch (Throwable var5) {
         CrashReport var3 = CrashReport.a(var5, "Trying to submit a frame to Twitch");
         CrashReportCategory var4 = var3.a("Broadcast State");
         var4.a("Last reported errors", Arrays.toString(E.c()));
         var4.a("Buffer", var1);
         var4.a("Free buffer count", this.k.size());
         var4.a("Capture buffer count", this.j.size());
         throw new ReportedException(var3);
      }
   }

   public ErrorCode b(FrameBuffer var1) {
      if (this.p()) {
         this.J();
      } else if (!this.m()) {
         return ErrorCode.TTV_EC_STREAM_NOT_STARTED;
      }

      ErrorCode var2 = this.i.submitVideoFrame(var1);
      if (var2 != ErrorCode.TTV_EC_SUCCESS) {
         String var3 = ErrorCode.getString(var2);
         if (ErrorCode.succeeded(var2)) {
            this.e(String.format("Warning in SubmitTexturePointer: %s\n", var3));
         } else {
            this.d(String.format("Error in SubmitTexturePointer: %s\n", var3));
            this.H();
         }

         if (this.c != null) {
            this.c.b(var2);
         }
      }

      return var2;
   }

   protected boolean a(ErrorCode var1) {
      if (ErrorCode.failed(var1)) {
         this.d(ErrorCode.getString(var1));
         return false;
      } else {
         return true;
      }
   }

   protected void d(String var1) {
      this.F = var1;
      E.a("<Error> " + var1);
      D.error(TwitchStream.a, "[Broadcast controller] {}", new Object[]{var1});
   }

   protected void e(String var1) {
      E.a("<Warning> " + var1);
      D.warn(TwitchStream.a, "[Broadcast controller] {}", new Object[]{var1});
   }

   public interface BroadcastListener {
      void a(ErrorCode var1, AuthToken var2);

      void a(ErrorCode var1);

      void a(ErrorCode var1, GameInfo[] var2);

      void a(BroadcastController.BroadcastState var1);

      void a();

      void a(StreamInfo var1);

      void a(IngestList var1);

      void b(ErrorCode var1);

      void b();

      void c();

      void c(ErrorCode var1);
   }

   public static enum BroadcastState {
      a,
      b,
      c,
      d,
      e,
      f,
      g,
      h,
      i,
      j,
      k,
      l,
      m,
      n;
   }
}
