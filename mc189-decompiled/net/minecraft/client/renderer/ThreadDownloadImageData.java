package net.minecraft.client.renderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadDownloadImageData extends SimpleTexture {
   private static final Logger g = LogManager.getLogger();
   private static final AtomicInteger h = new AtomicInteger(0);
   private final File i;
   private final String j;
   private final IImageBuffer k;
   private BufferedImage l;
   private Thread m;
   private boolean n;

   public ThreadDownloadImageData(File var1, String var2, ResourceLocation var3, IImageBuffer var4) {
      super(var3);
      this.i = var1;
      this.j = var2;
      this.k = var4;
   }

   private void g() {
      if (!this.n) {
         if (this.l != null) {
            if (this.f != null) {
               this.c();
            }

            TextureUtil.a(super.b(), this.l);
            this.n = true;
         }
      }
   }

   @Override
   public int b() {
      this.g();
      return super.b();
   }

   public void a(BufferedImage var1) {
      this.l = var1;
      if (this.k != null) {
         this.k.a();
      }
   }

   @Override
   public void a(IResourceManager var1) throws IOException {
      if (this.l == null && this.f != null) {
         super.a(var1);
      }

      if (this.m == null) {
         if (this.i != null && this.i.isFile()) {
            g.debug("Loading http texture from local cache ({})", new Object[]{this.i});

            try {
               this.l = ImageIO.read(this.i);
               if (this.k != null) {
                  this.a(this.k.a(this.l));
               }
            } catch (IOException var3) {
               g.error("Couldn't load skin " + this.i, var3);
               this.d();
            }
         } else {
            this.d();
         }
      }
   }

   protected void d() {
      this.m = new Thread("Texture Downloader #" + h.incrementAndGet()) {
         @Override
         public void run() {
            HttpURLConnection var1 = null;
            ThreadDownloadImageData.g
               .debug("Downloading http texture from {} to {}", new Object[]{ThreadDownloadImageData.this.j, ThreadDownloadImageData.this.i});

            try {
               var1 = (HttpURLConnection)new URL(ThreadDownloadImageData.this.j).openConnection(Minecraft.A().O());
               var1.setDoInput(true);
               var1.setDoOutput(false);
               var1.connect();
               if (var1.getResponseCode() / 100 == 2) {
                  BufferedImage var2;
                  if (ThreadDownloadImageData.this.i != null) {
                     FileUtils.copyInputStreamToFile(var1.getInputStream(), ThreadDownloadImageData.this.i);
                     var2 = ImageIO.read(ThreadDownloadImageData.this.i);
                  } else {
                     var2 = TextureUtil.a(var1.getInputStream());
                  }

                  if (ThreadDownloadImageData.this.k != null) {
                     var2 = ThreadDownloadImageData.this.k.a(var2);
                  }

                  ThreadDownloadImageData.this.a(var2);
                  return;
               }
            } catch (Exception var6) {
               ThreadDownloadImageData.g.error("Couldn't download http texture", var6);
               return;
            } finally {
               if (var1 != null) {
                  var1.disconnect();
               }
            }
         }
      };
      this.m.setDaemon(true);
      this.m.start();
   }
}
