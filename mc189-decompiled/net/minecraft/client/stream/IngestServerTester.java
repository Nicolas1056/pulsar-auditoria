package net.minecraft.client.stream;

import com.google.common.collect.Lists;
import java.util.List;
import tv.twitch.AuthToken;
import tv.twitch.ErrorCode;
import tv.twitch.broadcast.ArchivingState;
import tv.twitch.broadcast.AudioParams;
import tv.twitch.broadcast.ChannelInfo;
import tv.twitch.broadcast.EncodingCpuUsage;
import tv.twitch.broadcast.FrameBuffer;
import tv.twitch.broadcast.GameInfoList;
import tv.twitch.broadcast.IStatCallbacks;
import tv.twitch.broadcast.IStreamCallbacks;
import tv.twitch.broadcast.IngestList;
import tv.twitch.broadcast.IngestServer;
import tv.twitch.broadcast.PixelFormat;
import tv.twitch.broadcast.RTMPState;
import tv.twitch.broadcast.StartFlags;
import tv.twitch.broadcast.StatType;
import tv.twitch.broadcast.Stream;
import tv.twitch.broadcast.StreamInfo;
import tv.twitch.broadcast.UserInfo;
import tv.twitch.broadcast.VideoParams;

public class IngestServerTester {
   protected IngestServerTester.IngestTestListener a = null;
   protected Stream b = null;
   protected IngestList c = null;
   protected IngestServerTester.IngestTestState d = IngestServerTester.IngestTestState.a;
   protected long e = 8000L;
   protected long f = 2000L;
   protected long g = 0L;
   protected RTMPState h = RTMPState.Invalid;
   protected VideoParams i = null;
   protected AudioParams j = null;
   protected long k = 0L;
   protected List<FrameBuffer> l = null;
   protected boolean m = false;
   protected IStreamCallbacks n = null;
   protected IStatCallbacks o = null;
   protected IngestServer p = null;
   protected boolean q = false;
   protected boolean r = false;
   protected int s = -1;
   protected int t = 0;
   protected long u = 0L;
   protected float v = 0.0F;
   protected float w = 0.0F;
   protected boolean x = false;
   protected boolean y = false;
   protected boolean z = false;
   protected IStreamCallbacks A = new IStreamCallbacks() {
      public void requestAuthTokenCallback(ErrorCode var1, AuthToken var2) {
      }

      public void loginCallback(ErrorCode var1, ChannelInfo var2) {
      }

      public void getIngestServersCallback(ErrorCode var1, IngestList var2) {
      }

      public void getUserInfoCallback(ErrorCode var1, UserInfo var2) {
      }

      public void getStreamInfoCallback(ErrorCode var1, StreamInfo var2) {
      }

      public void getArchivingStateCallback(ErrorCode var1, ArchivingState var2) {
      }

      public void runCommercialCallback(ErrorCode var1) {
      }

      public void setStreamInfoCallback(ErrorCode var1) {
      }

      public void getGameNameListCallback(ErrorCode var1, GameInfoList var2) {
      }

      public void bufferUnlockCallback(long var1) {
      }

      public void startCallback(ErrorCode var1) {
         IngestServerTester.this.y = false;
         if (ErrorCode.succeeded(var1)) {
            IngestServerTester.this.x = true;
            IngestServerTester.this.k = System.currentTimeMillis();
            IngestServerTester.this.a(IngestServerTester.IngestTestState.c);
         } else {
            IngestServerTester.this.m = false;
            IngestServerTester.this.a(IngestServerTester.IngestTestState.e);
         }
      }

      public void stopCallback(ErrorCode var1) {
         if (ErrorCode.failed(var1)) {
            System.out.println("IngestTester.stopCallback failed to stop - " + IngestServerTester.this.p.serverName + ": " + var1.toString());
         }

         IngestServerTester.this.z = false;
         IngestServerTester.this.x = false;
         IngestServerTester.this.a(IngestServerTester.IngestTestState.e);
         IngestServerTester.this.p = null;
         if (IngestServerTester.this.q) {
            IngestServerTester.this.a(IngestServerTester.IngestTestState.g);
         }
      }

      public void sendActionMetaDataCallback(ErrorCode var1) {
      }

      public void sendStartSpanMetaDataCallback(ErrorCode var1) {
      }

      public void sendEndSpanMetaDataCallback(ErrorCode var1) {
      }
   };
   protected IStatCallbacks B = new IStatCallbacks() {
      public void statCallback(StatType var1, long var2) {
         switch (var1) {
            case TTV_ST_RTMPSTATE:
               IngestServerTester.this.h = RTMPState.lookupValue((int)var2);
               break;
            case TTV_ST_RTMPDATASENT:
               IngestServerTester.this.g = var2;
         }
      }
   };

   public void a(IngestServerTester.IngestTestListener var1) {
      this.a = var1;
   }

   public IngestServer c() {
      return this.p;
   }

   public int d() {
      return this.s;
   }

   public boolean f() {
      return this.d == IngestServerTester.IngestTestState.f || this.d == IngestServerTester.IngestTestState.h || this.d == IngestServerTester.IngestTestState.i;
   }

   public float i() {
      return this.w;
   }

   public IngestServerTester(Stream var1, IngestList var2) {
      this.b = var1;
      this.c = var2;
   }

   public void j() {
      if (this.d == IngestServerTester.IngestTestState.a) {
         this.s = 0;
         this.q = false;
         this.r = false;
         this.x = false;
         this.y = false;
         this.z = false;
         this.o = this.b.getStatCallbacks();
         this.b.setStatCallbacks(this.B);
         this.n = this.b.getStreamCallbacks();
         this.b.setStreamCallbacks(this.A);
         this.i = new VideoParams();
         this.i.targetFps = 60;
         this.i.maxKbps = 3500;
         this.i.outputWidth = 1280;
         this.i.outputHeight = 720;
         this.i.pixelFormat = PixelFormat.TTV_PF_BGRA;
         this.i.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
         this.i.disableAdaptiveBitrate = true;
         this.i.verticalFlip = false;
         this.b.getDefaultParams(this.i);
         this.j = new AudioParams();
         this.j.audioEnabled = false;
         this.j.enableMicCapture = false;
         this.j.enablePlaybackCapture = false;
         this.j.enablePassthroughAudio = false;
         this.l = Lists.newArrayList();
         byte var1 = 3;

         for (int var2 = 0; var2 < var1; var2++) {
            FrameBuffer var3 = this.b.allocateFrameBuffer(this.i.outputWidth * this.i.outputHeight * 4);
            if (!var3.getIsValid()) {
               this.p();
               this.a(IngestServerTester.IngestTestState.i);
               return;
            }

            this.l.add(var3);
            this.b.randomizeFrameBuffer(var3);
         }

         this.a(IngestServerTester.IngestTestState.b);
         this.k = System.currentTimeMillis();
      }
   }

   public void k() {
      if (!this.f() && this.d != IngestServerTester.IngestTestState.a) {
         if (!this.y && !this.z) {
            switch (this.d) {
               case b:
               case e:
                  if (this.p != null) {
                     if (this.r || !this.m) {
                        this.p.bitrateKbps = 0.0F;
                     }

                     this.b(this.p);
                  } else {
                     this.k = 0L;
                     this.r = false;
                     this.m = true;
                     if (this.d != IngestServerTester.IngestTestState.b) {
                        this.s++;
                     }

                     if (this.s < this.c.getServers().length) {
                        this.p = this.c.getServers()[this.s];
                        this.a(this.p);
                     } else {
                        this.a(IngestServerTester.IngestTestState.f);
                     }
                  }
                  break;
               case c:
               case d:
                  this.c(this.p);
                  break;
               case g:
                  this.a(IngestServerTester.IngestTestState.h);
            }

            this.o();
            if (this.d == IngestServerTester.IngestTestState.h || this.d == IngestServerTester.IngestTestState.f) {
               this.p();
            }
         }
      }
   }

   public void m() {
      if (!this.f() && !this.q) {
         this.q = true;
         if (this.p != null) {
            this.p.bitrateKbps = 0.0F;
         }
      }
   }

   protected boolean a(IngestServer var1) {
      this.m = true;
      this.g = 0L;
      this.h = RTMPState.Idle;
      this.p = var1;
      this.y = true;
      this.a(IngestServerTester.IngestTestState.c);
      ErrorCode var2 = this.b.start(this.i, this.j, var1, StartFlags.TTV_Start_BandwidthTest, true);
      if (ErrorCode.failed(var2)) {
         this.y = false;
         this.m = false;
         this.a(IngestServerTester.IngestTestState.e);
         return false;
      } else {
         this.u = this.g;
         var1.bitrateKbps = 0.0F;
         this.t = 0;
         return true;
      }
   }

   protected void b(IngestServer var1) {
      if (this.y) {
         this.r = true;
      } else if (this.x) {
         this.z = true;
         ErrorCode var2 = this.b.stop(true);
         if (ErrorCode.failed(var2)) {
            this.A.stopCallback(ErrorCode.TTV_EC_SUCCESS);
            System.out.println("Stop failed: " + var2.toString());
         }

         this.b.pollStats();
      } else {
         this.A.stopCallback(ErrorCode.TTV_EC_SUCCESS);
      }
   }

   protected long n() {
      return System.currentTimeMillis() - this.k;
   }

   protected void o() {
      float var1 = (float)this.n();
      switch (this.d) {
         case b:
         case c:
         case a:
         case f:
         case h:
         case i:
            this.w = 0.0F;
            break;
         case e:
            this.w = 1.0F;
            break;
         case d:
         case g:
         default:
            this.w = var1 / (float)this.e;
      }

      switch (this.d) {
         case f:
         case h:
         case i:
            this.v = 1.0F;
            break;
         default:
            this.v = (float)this.s / this.c.getServers().length;
            this.v = this.v + this.w / this.c.getServers().length;
      }
   }

   protected boolean c(IngestServer var1) {
      if (this.r || this.q || this.n() >= this.e) {
         this.a(IngestServerTester.IngestTestState.e);
         return true;
      } else if (!this.y && !this.z) {
         ErrorCode var2 = this.b.submitVideoFrame(this.l.get(this.t));
         if (ErrorCode.failed(var2)) {
            this.m = false;
            this.a(IngestServerTester.IngestTestState.e);
            return false;
         } else {
            this.t = (this.t + 1) % this.l.size();
            this.b.pollStats();
            if (this.h == RTMPState.SendVideo) {
               this.a(IngestServerTester.IngestTestState.d);
               long var3 = this.n();
               if (var3 > 0L && this.g > this.u) {
                  var1.bitrateKbps = (float)(this.g * 8L) / (float)this.n();
                  this.u = this.g;
               }
            }

            return true;
         }
      } else {
         return true;
      }
   }

   protected void p() {
      this.p = null;
      if (this.l != null) {
         for (int var1 = 0; var1 < this.l.size(); var1++) {
            this.l.get(var1).free();
         }

         this.l = null;
      }

      if (this.b.getStatCallbacks() == this.B) {
         this.b.setStatCallbacks(this.o);
         this.o = null;
      }

      if (this.b.getStreamCallbacks() == this.A) {
         this.b.setStreamCallbacks(this.n);
         this.n = null;
      }
   }

   protected void a(IngestServerTester.IngestTestState var1) {
      if (var1 != this.d) {
         this.d = var1;
         if (this.a != null) {
            this.a.a(this, var1);
         }
      }
   }

   public interface IngestTestListener {
      void a(IngestServerTester var1, IngestServerTester.IngestTestState var2);
   }

   public static enum IngestTestState {
      a,
      b,
      c,
      d,
      e,
      f,
      g,
      h,
      i;
   }
}
