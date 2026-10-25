package net.minecraft.client.audio;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import io.netty.util.internal.ThreadLocalRandom;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.SoundSystemLogger;
import paulscode.sound.Source;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

public class SoundManager {
   private static final Marker a = MarkerManager.getMarker("SOUNDS");
   private static final Logger b = LogManager.getLogger();
   private final SoundHandler c;
   private final GameSettings d;
   private SoundManager.SoundSystemStarterThread e;
   private boolean f;
   private int g = 0;
   private final Map<String, ISound> h = HashBiMap.create();
   private final Map<ISound, String> i = ((BiMap)this.h).inverse();
   private Map<ISound, SoundPoolEntry> j = Maps.newHashMap();
   private final Multimap<SoundCategory, String> k = HashMultimap.create();
   private final List<ITickableSound> l = Lists.newArrayList();
   private final Map<ISound, Integer> m = Maps.newHashMap();
   private final Map<String, Integer> n = Maps.newHashMap();

   public SoundManager(SoundHandler var1, GameSettings var2) {
      this.c = var1;
      this.d = var2;

      try {
         SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
         SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
      } catch (SoundSystemException var4) {
         b.error(a, "Error linking with the LibraryJavaSound plug-in", var4);
      }
   }

   public void a() {
      this.b();
      this.i();
   }

   private synchronized void i() {
      if (!this.f) {
         try {
            new Thread(new Runnable() {
               @Override
               public void run() {
                  SoundSystemConfig.setLogger(new SoundSystemLogger() {
                     public void message(String var1, int var2x) {
                        if (!var1.isEmpty()) {
                           SoundManager.b.info(var1);
                        }
                     }

                     public void importantMessage(String var1, int var2x) {
                        if (!var1.isEmpty()) {
                           SoundManager.b.warn(var1);
                        }
                     }

                     public void errorMessage(String var1, String var2x, int var3) {
                        if (!var2x.isEmpty()) {
                           SoundManager.b.error("Error in class '" + var1 + "'");
                           SoundManager.b.error(var2x);
                        }
                     }
                  });
                  SoundManager.this.e = SoundManager.this.new SoundSystemStarterThread();
                  SoundManager.this.f = true;
                  SoundManager.this.e.setMasterVolume(SoundManager.this.d.a(SoundCategory.a));
                  SoundManager.b.info(SoundManager.a, "Sound engine started");
               }
            }, "Sound Library Loader").start();
         } catch (RuntimeException var2) {
            b.error(a, "Error starting SoundSystem. Turning off sounds & music", var2);
            this.d.a(SoundCategory.a, 0.0F);
            this.d.b();
         }
      }
   }

   private float a(SoundCategory var1) {
      return var1 != null && var1 != SoundCategory.a ? this.d.a(var1) : 1.0F;
   }

   public void a(SoundCategory var1, float var2) {
      if (this.f) {
         if (var1 == SoundCategory.a) {
            this.e.setMasterVolume(var2);
         } else {
            for (String var4 : this.k.get(var1)) {
               ISound var5 = this.h.get(var4);
               float var6 = this.a(var5, this.j.get(var5), var1);
               if (var6 <= 0.0F) {
                  this.b(var5);
               } else {
                  this.e.setVolume(var4, var6);
               }
            }
         }
      }
   }

   public void b() {
      if (this.f) {
         this.c();
         this.e.cleanup();
         this.f = false;
      }
   }

   public void c() {
      if (this.f) {
         for (String var2 : this.h.keySet()) {
            this.e.stop(var2);
         }

         this.h.clear();
         this.m.clear();
         this.l.clear();
         this.k.clear();
         this.j.clear();
         this.n.clear();
      }
   }

   public void d() {
      this.g++;

      for (ITickableSound var2 : this.l) {
         var2.c();
         if (var2.k()) {
            this.b(var2);
         } else {
            String var3 = this.i.get(var2);
            this.e.setVolume(var3, this.a(var2, this.j.get(var2), this.c.a(var2.a()).d()));
            this.e.setPitch(var3, this.a(var2, this.j.get(var2)));
            this.e.setPosition(var3, var2.g(), var2.h(), var2.i());
         }
      }

      Iterator var9 = this.h.entrySet().iterator();

      while (var9.hasNext()) {
         Entry var10 = (Entry)var9.next();
         String var12 = (String)var10.getKey();
         ISound var4 = (ISound)var10.getValue();
         if (!this.e.playing(var12)) {
            int var5 = this.n.get(var12);
            if (var5 <= this.g) {
               int var6 = var4.d();
               if (var4.b() && var6 > 0) {
                  this.m.put(var4, this.g + var6);
               }

               var9.remove();
               b.debug(a, "Removed channel {} because it's not playing anymore", new Object[]{var12});
               this.e.removeSource(var12);
               this.n.remove(var12);
               this.j.remove(var4);

               try {
                  this.k.remove(this.c.a(var4.a()).d(), var12);
               } catch (RuntimeException var8) {
               }

               if (var4 instanceof ITickableSound) {
                  this.l.remove(var4);
               }
            }
         }
      }

      Iterator var11 = this.m.entrySet().iterator();

      while (var11.hasNext()) {
         Entry var13 = (Entry)var11.next();
         if (this.g >= (Integer)var13.getValue()) {
            ISound var14 = (ISound)var13.getKey();
            if (var14 instanceof ITickableSound) {
               ((ITickableSound)var14).c();
            }

            this.c(var14);
            var11.remove();
         }
      }
   }

   public boolean a(ISound var1) {
      if (!this.f) {
         return false;
      } else {
         String var2 = this.i.get(var1);
         return var2 == null ? false : this.e.playing(var2) || this.n.containsKey(var2) && this.n.get(var2) <= this.g;
      }
   }

   public void b(ISound var1) {
      if (this.f) {
         String var2 = this.i.get(var1);
         if (var2 != null) {
            this.e.stop(var2);
         }
      }
   }

   public void c(ISound var1) {
      if (this.f) {
         if (this.e.getMasterVolume() <= 0.0F) {
            b.debug(a, "Skipped playing soundEvent: {}, master volume was zero", new Object[]{var1.a()});
         } else {
            SoundEventAccessorComposite var2 = this.c.a(var1.a());
            if (var2 == null) {
               b.warn(a, "Unable to play unknown soundEvent: {}", new Object[]{var1.a()});
            } else {
               SoundPoolEntry var3 = var2.b();
               if (var3 == SoundHandler.a) {
                  b.warn(a, "Unable to play empty soundEvent: {}", new Object[]{var2.c()});
               } else {
                  float var4 = var1.e();
                  float var5 = 16.0F;
                  if (var4 > 1.0F) {
                     var5 *= var4;
                  }

                  SoundCategory var6 = var2.d();
                  float var7 = this.a(var1, var3, var6);
                  double var8 = this.a(var1, var3);
                  ResourceLocation var10 = var3.a();
                  if (var7 == 0.0F) {
                     b.debug(a, "Skipped playing sound {}, volume was zero.", new Object[]{var10});
                  } else {
                     boolean var11 = var1.b() && var1.d() == 0;
                     String var12 = MathHelper.a(ThreadLocalRandom.current()).toString();
                     if (var3.d()) {
                        this.e.newStreamingSource(false, var12, a(var10), var10.toString(), var11, var1.g(), var1.h(), var1.i(), var1.j().a(), var5);
                     } else {
                        this.e.newSource(false, var12, a(var10), var10.toString(), var11, var1.g(), var1.h(), var1.i(), var1.j().a(), var5);
                     }

                     b.debug(a, "Playing sound {} for event {} as channel {}", new Object[]{var3.a(), var2.c(), var12});
                     this.e.setPitch(var12, (float)var8);
                     this.e.setVolume(var12, var7);
                     this.e.play(var12);
                     this.n.put(var12, this.g + 20);
                     this.h.put(var12, var1);
                     this.j.put(var1, var3);
                     if (var6 != SoundCategory.a) {
                        this.k.put(var6, var12);
                     }

                     if (var1 instanceof ITickableSound) {
                        this.l.add((ITickableSound)var1);
                     }
                  }
               }
            }
         }
      }
   }

   private float a(ISound var1, SoundPoolEntry var2) {
      return (float)MathHelper.a(var1.f() * var2.b(), 0.5, 2.0);
   }

   private float a(ISound var1, SoundPoolEntry var2, SoundCategory var3) {
      return (float)MathHelper.a(var1.e() * var2.c(), 0.0, 1.0) * this.a(var3);
   }

   public void e() {
      for (String var2 : this.h.keySet()) {
         b.debug(a, "Pausing channel {}", new Object[]{var2});
         this.e.pause(var2);
      }
   }

   public void f() {
      for (String var2 : this.h.keySet()) {
         b.debug(a, "Resuming channel {}", new Object[]{var2});
         this.e.play(var2);
      }
   }

   public void a(ISound var1, int var2) {
      this.m.put(var1, this.g + var2);
   }

   private static URL a(final ResourceLocation var0) {
      String var1 = String.format("%s:%s:%s", "mcsounddomain", var0.b(), var0.a());
      URLStreamHandler var2 = new URLStreamHandler() {
         @Override
         protected URLConnection openConnection(URL var1) {
            return new URLConnection(var1) {
               @Override
               public void connect() throws IOException {
               }

               @Override
               public InputStream getInputStream() throws IOException {
                  return Minecraft.A().Q().a(var0).b();
               }
            };
         }
      };

      try {
         return new URL(null, var1, var2);
      } catch (MalformedURLException var4) {
         throw new Error("TODO: Sanely handle url exception! :D");
      }
   }

   public void a(EntityPlayer var1, float var2) {
      if (this.f && var1 != null) {
         float var3 = var1.B + (var1.z - var1.B) * var2;
         float var4 = var1.A + (var1.y - var1.A) * var2;
         double var5 = var1.p + (var1.s - var1.p) * var2;
         double var7 = var1.q + (var1.t - var1.q) * var2 + var1.aS();
         double var9 = var1.r + (var1.u - var1.r) * var2;
         float var11 = MathHelper.b((var4 + 90.0F) * (float) (Math.PI / 180.0));
         float var12 = MathHelper.a((var4 + 90.0F) * (float) (Math.PI / 180.0));
         float var13 = MathHelper.b(-var3 * (float) (Math.PI / 180.0));
         float var14 = MathHelper.a(-var3 * (float) (Math.PI / 180.0));
         float var15 = MathHelper.b((-var3 + 90.0F) * (float) (Math.PI / 180.0));
         float var16 = MathHelper.a((-var3 + 90.0F) * (float) (Math.PI / 180.0));
         float var17 = var11 * var13;
         float var19 = var12 * var13;
         float var20 = var11 * var15;
         float var22 = var12 * var15;
         this.e.setListenerPosition((float)var5, (float)var7, (float)var9);
         this.e.setListenerOrientation(var17, var14, var19, var20, var16, var22);
      }
   }

   class SoundSystemStarterThread extends SoundSystem {
      private SoundSystemStarterThread() {
      }

      public boolean playing(String var1) {
         synchronized (SoundSystemConfig.THREAD_SYNC) {
            if (this.soundLibrary == null) {
               return false;
            } else {
               Source var3 = (Source)this.soundLibrary.getSources().get(var1);
               return var3 == null ? false : var3.playing() || var3.paused() || var3.preLoad;
            }
         }
      }
   }
}
