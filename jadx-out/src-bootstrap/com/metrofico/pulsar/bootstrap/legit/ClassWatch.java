package com.metrofico.pulsar.bootstrap.legit;

import com.metrofico.pulsar.bootstrap.BootstrapLog;
import com.metrofico.pulsar.bootstrap.loader.GameClassLoader;
import java.io.File;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Proxy;
import java.net.URISyntaxException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Path;
import java.security.CodeSource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public final class ClassWatch implements ClassFileTransformer {
   static final int REPORT_LIMIT = 16;
   private static final int PENDING_LIMIT = 256;
   private static volatile ClassWatch current;
   private final ClassLoader app = ClassLoader.getSystemClassLoader();
   private final ClassLoader platform = ClassLoader.getPlatformClassLoader();
   private final Set<Path> classpath;
   private final Map<CodeSource, Boolean> knownSources = new ConcurrentHashMap<>();
   private final ConcurrentLinkedQueue<ClassWatch.Candidate> pendingClasses = new ConcurrentLinkedQueue<>();
   private final ConcurrentLinkedQueue<ClassWatch.Redefinition> pendingRedefinitions = new ConcurrentLinkedQueue<>();
   private final AtomicInteger pending = new AtomicInteger();
   private final List<String> unknown = new ArrayList<>();
   private final List<ClassWatch.Redefinition> redefined = new ArrayList<>();
   private volatile GameClassLoader game;

   ClassWatch(Set<Path> classpath) {
      this.classpath = Set.copyOf(classpath);
   }

   public static void premain(String args, Instrumentation inst) {
      try {
         ClassWatch watch = new ClassWatch(classpathEntries());
         new ClassWatch.Candidate("", null);
         ClassWatch.Redefinition.of("", new byte[0]);
         inst.addTransformer(watch, true);
         current = watch;
      } catch (NoSuchAlgorithmException | RuntimeException var3) {
         BootstrapLog.error("guard: class watch could not start", var3);
      }
   }

   public static ClassWatch current() {
      return current;
   }

   public static void attach(GameClassLoader loader) {
      ClassWatch watch = current;
      if (watch != null) {
         watch.game = loader;
      }
   }

   @Override
   public byte[] transform(Module module, ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain domain, byte[] classfileBuffer) {
      try {
         if (className == null || loader == null || loader == this.platform || isJdkLoader(loader)) {
            return null;
         }

         if (classBeingRedefined != null) {
            this.queue(this.pendingRedefinitions, ClassWatch.Redefinition.of(className.replace('/', '.'), classfileBuffer));
            GameClassLoader g = this.game;
            if (g != null) {
               g.recordRedefinition(className.replace('/', '.'), classfileBuffer);
            }
         } else if (this.isForeign(loader, className, domain)) {
            this.queue(this.pendingClasses, new ClassWatch.Candidate(className.replace('/', '.'), loader));
         }
      } catch (Throwable var8) {
      }

      return null;
   }

   synchronized Map<String, Object> report() {
      ClassWatch.Candidate c;
      while ((c = this.pendingClasses.poll()) != null) {
         this.pending.decrementAndGet();
         if (this.unknown.size() < 16 && !this.unknown.contains(c.name) && !isProxy(c)) {
            this.unknown.add(c.name);
            BootstrapLog.info("guard: foreign class " + c.name + " (" + c.loaderName + ")");
         }
      }

      while ((r = this.pendingRedefinitions.poll()) != null) {
         this.pending.decrementAndGet();
         if (this.redefined.size() < 16 && !this.redefined.contains(r)) {
            this.redefined.add(r);
            BootstrapLog.info("guard: class redefined in memory " + r.name + " sha256=" + r.sha256);
         }
      }

      List<Map<String, Object>> changes = new ArrayList<>(this.redefined.size());

      for (ClassWatch.Redefinition r : this.redefined) {
         Map<String, Object> o = new LinkedHashMap<>();
         o.put("name", r.name);
         o.put("sha256", r.sha256);
         changes.add(o);
      }

      Map<String, Object> out = new LinkedHashMap<>();
      out.put("unknownClasses", List.copyOf(this.unknown));
      out.put("redefinedClasses", changes);
      return out;
   }

   private boolean isForeign(ClassLoader loader, String className, ProtectionDomain domain) {
      GameClassLoader g = this.game;
      if (g != null && loader == g) {
         return !g.isDefining(className.replace('/', '.'));
      } else if (loader == this.app) {
         CodeSource source = domain == null ? null : domain.getCodeSource();
         return source == null || !this.knownSources.computeIfAbsent(source, this::onClasspath);
      } else {
         return true;
      }
   }

   private boolean onClasspath(CodeSource source) {
      try {
         return source.getLocation() != null && this.classpath.contains(Path.of(source.getLocation().toURI()).toAbsolutePath().normalize());
      } catch (IllegalArgumentException | FileSystemNotFoundException | URISyntaxException var3) {
         return false;
      }
   }

   private static boolean isJdkLoader(ClassLoader loader) {
      String n = loader.getClass().getName();
      return n.startsWith("jdk.internal.") || n.startsWith("sun.");
   }

   private static boolean isProxy(ClassWatch.Candidate c) {
      ClassLoader loader = c.loader.get();
      if (loader == null) {
         return false;
      } else {
         try {
            return Proxy.isProxyClass(Class.forName(c.name, false, loader));
         } catch (Throwable var3) {
            return false;
         }
      }
   }

   private <T> void queue(ConcurrentLinkedQueue<T> queue, T item) {
      if (this.pending.incrementAndGet() > 256) {
         this.pending.decrementAndGet();
      } else {
         queue.add(item);
      }
   }

   static Set<Path> classpathEntries() {
      Set<Path> out = new HashSet<>();

      for (String entry : System.getProperty("java.class.path", "").split(File.pathSeparator)) {
         if (!entry.isBlank()) {
            out.add(Path.of(entry).toAbsolutePath().normalize());
         }
      }

      return out;
   }

   private static final class Candidate {
      final String name;
      final WeakReference<ClassLoader> loader;
      final String loaderName;

      Candidate(String name, ClassLoader loader) {
         this.name = name;
         this.loader = new WeakReference<>(loader);
         this.loaderName = loader == null ? "" : loader.getClass().getName();
      }
   }

   private record Redefinition(String name, String sha256) {
      static ClassWatch.Redefinition of(String name, byte[] bytes) throws NoSuchAlgorithmException {
         return new ClassWatch.Redefinition(name, HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes)));
      }
   }
}
