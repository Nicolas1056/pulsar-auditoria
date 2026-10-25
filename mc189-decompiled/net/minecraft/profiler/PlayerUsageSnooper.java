package net.minecraft.profiler;

import com.google.common.collect.Maps;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.util.HttpUtil;

public class PlayerUsageSnooper {
   private final Map<String, Object> a = Maps.newHashMap();
   private final Map<String, Object> b = Maps.newHashMap();
   private final String c = UUID.randomUUID().toString();
   private final URL d;
   private final IPlayerUsage e;
   private final Timer f = new Timer("Snooper Timer", true);
   private final Object g = new Object();
   private final long h;
   private boolean i;
   private int j;

   public PlayerUsageSnooper(String var1, IPlayerUsage var2, long var3) {
      try {
         this.d = new URL("http://snoop.minecraft.net/" + var1 + "?version=" + 2);
      } catch (MalformedURLException var6) {
         throw new IllegalArgumentException();
      }

      this.e = var2;
      this.h = var3;
   }

   public void a() {
      if (!this.i) {
         this.i = true;
         this.h();
         this.f.schedule(new TimerTask() {
            @Override
            public void run() {
               if (PlayerUsageSnooper.this.e.ad()) {
                  HashMap var1;
                  synchronized (PlayerUsageSnooper.this.g) {
                     var1 = Maps.newHashMap(PlayerUsageSnooper.this.b);
                     if (PlayerUsageSnooper.this.j == 0) {
                        var1.putAll(PlayerUsageSnooper.this.a);
                     }

                     var1.put("snooper_count", PlayerUsageSnooper.this.j++);
                     var1.put("snooper_token", PlayerUsageSnooper.this.c);
                  }

                  HttpUtil.a(PlayerUsageSnooper.this.d, var1, true);
               }
            }
         }, 0L, 900000L);
      }
   }

   private void h() {
      this.i();
      this.a("snooper_token", this.c);
      this.b("snooper_token", this.c);
      this.b("os_name", System.getProperty("os.name"));
      this.b("os_version", System.getProperty("os.version"));
      this.b("os_architecture", System.getProperty("os.arch"));
      this.b("java_version", System.getProperty("java.version"));
      this.a("version", "1.8.9");
      this.e.b(this);
   }

   private void i() {
      RuntimeMXBean var1 = ManagementFactory.getRuntimeMXBean();
      List var2 = var1.getInputArguments();
      int var3 = 0;

      for (String var5 : var2) {
         if (var5.startsWith("-X")) {
            this.a("jvm_arg[" + var3++ + "]", var5);
         }
      }

      this.a("jvm_args", var3);
   }

   public void b() {
      this.b("memory_total", Runtime.getRuntime().totalMemory());
      this.b("memory_max", Runtime.getRuntime().maxMemory());
      this.b("memory_free", Runtime.getRuntime().freeMemory());
      this.b("cpu_cores", Runtime.getRuntime().availableProcessors());
      this.e.a(this);
   }

   public void a(String var1, Object var2) {
      synchronized (this.g) {
         this.b.put(var1, var2);
      }
   }

   public void b(String var1, Object var2) {
      synchronized (this.g) {
         this.a.put(var1, var2);
      }
   }

   public Map<String, String> c() {
      LinkedHashMap var1 = Maps.newLinkedHashMap();
      synchronized (this.g) {
         this.b();

         for (Entry var4 : this.a.entrySet()) {
            var1.put(var4.getKey(), var4.getValue().toString());
         }

         for (Entry var8 : this.b.entrySet()) {
            var1.put(var8.getKey(), var8.getValue().toString());
         }

         return var1;
      }
   }

   public boolean d() {
      return this.i;
   }

   public void e() {
      this.f.cancel();
   }

   public String f() {
      return this.c;
   }

   public long g() {
      return this.h;
   }
}
