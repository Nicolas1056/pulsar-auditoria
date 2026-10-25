package com.metrofico.pulsar.bootstrap.legit;

import com.metrofico.pulsar.api.guard.Guard;
import com.metrofico.pulsar.api.guard.GuardReport;
import com.metrofico.pulsar.api.input.MouseLock;
import com.metrofico.pulsar.bootstrap.loader.GameClassLoader;
import com.sun.management.HotSpotDiagnosticMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

final class SelfGuard implements Supplier<Map<String, Object>> {
   private final GameClassLoader loader;
   private final List<String> agents;
   private final boolean attachDisabled;
   private final Guard guard;

   SelfGuard(GameClassLoader loader) {
      this.loader = loader;
      this.agents = List.copyOf(BootSeal.injectingArgs());
      this.attachDisabled = attachDisabled();
      this.guard = Guard.current();
   }

   public Map<String, Object> get() {
      List<String> flags = new ArrayList<>();
      if (!this.attachDisabled) {
         flags.add("attach-enabled");
      }

      for (ThreadInfo t : ManagementFactory.getThreadMXBean().dumpAllThreads(false, false, 0)) {
         if (t != null && "Attach Listener".equals(t.getThreadName())) {
            flags.add("attach-listener");
            break;
         }
      }

      ClassWatch watch = ClassWatch.current();
      Map<String, Object> classes = watch == null ? Map.of("unknownClasses", List.of(), "redefinedClasses", List.of()) : watch.report();
      if (watch == null) {
         flags.add("class-watch-off");
      }

      Map<String, Object> d = new LinkedHashMap<>();
      d.put("loadedClasses", this.loader == null ? 0 : this.loader.definedCount());
      d.put("unknownClasses", classes.get("unknownClasses"));
      d.put("redefinedClasses", classes.get("redefinedClasses"));
      d.put("agents", this.agents);
      d.put("attachDisabled", this.attachDisabled);
      d.put("guardFlags", flags);

      try {
         d.put("guard", this.guard.scan().toJson());
      } catch (RuntimeException var6) {
         d.put("guard", GuardReport.EMPTY.toJson());
      }

      MouseLock mice = MouseLock.current();
      if (mice != null) {
         d.put("mice", mice.report());
      }

      return d;
   }

   private static boolean attachDisabled() {
      try {
         HotSpotDiagnosticMXBean hs = ManagementFactory.getPlatformMXBean(HotSpotDiagnosticMXBean.class);
         return hs != null && "true".equals(hs.getVMOption("DisableAttachMechanism").getValue());
      } catch (RuntimeException var1) {
         return false;
      }
   }
}
