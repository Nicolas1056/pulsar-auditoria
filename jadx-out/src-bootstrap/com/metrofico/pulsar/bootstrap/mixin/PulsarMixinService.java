package com.metrofico.pulsar.bootstrap.mixin;

import com.metrofico.pulsar.bootstrap.Main;
import com.metrofico.pulsar.bootstrap.loader.GameClassLoader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.launch.platform.container.ContainerHandleVirtual;
import org.spongepowered.asm.launch.platform.container.IContainerHandle;
import org.spongepowered.asm.logging.ILogger;
import org.spongepowered.asm.logging.LoggerAdapterConsole;
import org.spongepowered.asm.mixin.MixinEnvironment.CompatibilityLevel;
import org.spongepowered.asm.mixin.MixinEnvironment.Phase;
import org.spongepowered.asm.mixin.transformer.IMixinTransformer;
import org.spongepowered.asm.mixin.transformer.IMixinTransformerFactory;
import org.spongepowered.asm.service.IAdviceProvider;
import org.spongepowered.asm.service.IClassBytecodeProvider;
import org.spongepowered.asm.service.IClassProvider;
import org.spongepowered.asm.service.IClassTracker;
import org.spongepowered.asm.service.IFeatureValidator;
import org.spongepowered.asm.service.IMixinAuditTrail;
import org.spongepowered.asm.service.IMixinInternal;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.ITransformer;
import org.spongepowered.asm.service.ITransformerProvider;
import org.spongepowered.asm.util.ReEntranceLock;

public final class PulsarMixinService implements IMixinService, IClassProvider, IClassBytecodeProvider, ITransformerProvider, IClassTracker {
   private static volatile GameClassLoader loader;
   private static volatile IMixinTransformer transformer;
   private final ReEntranceLock lock = new ReEntranceLock(1);

   static void attach(GameClassLoader gameLoader) {
      loader = gameLoader;
   }

   static IMixinTransformer transformer() {
      return transformer;
   }

   private static GameClassLoader loader() {
      GameClassLoader l = loader;
      if (l == null) {
         throw new IllegalStateException("PulsarMixinService used before a GameClassLoader was attached");
      } else {
         return l;
      }
   }

   public String getName() {
      return "Pulsar";
   }

   public boolean isValid() {
      return true;
   }

   public void prepare() {
   }

   public Phase getInitialPhase() {
      return Phase.PREINIT;
   }

   public void init() {
   }

   public void beginPhase() {
   }

   public void checkEnv(Object bootSource) {
   }

   public ReEntranceLock getReEntranceLock() {
      return this.lock;
   }

   public IClassProvider getClassProvider() {
      return this;
   }

   public IClassBytecodeProvider getBytecodeProvider() {
      return this;
   }

   public ITransformerProvider getTransformerProvider() {
      return this;
   }

   public IClassTracker getClassTracker() {
      return this;
   }

   public IMixinAuditTrail getAuditTrail() {
      return null;
   }

   public IFeatureValidator getFeatureValidator() {
      return IFeatureValidator.ALLOW_ALL;
   }

   public IAdviceProvider getAdviceProvider() {
      return (required, requiredString) -> "Update Pulsar Client: this mixin needs Mixin compatibility " + requiredString;
   }

   public Collection<String> getPlatformAgents() {
      return List.of("org.spongepowered.asm.launch.platform.MixinPlatformAgentDefault");
   }

   public IContainerHandle getPrimaryContainer() {
      return new ContainerHandleVirtual(this.getName());
   }

   public Collection<IContainerHandle> getMixinContainers() {
      return List.of();
   }

   public String getSideName() {
      return "CLIENT";
   }

   public CompatibilityLevel getMinCompatibilityLevel() {
      return CompatibilityLevel.JAVA_8;
   }

   public CompatibilityLevel getMaxCompatibilityLevel() {
      return CompatibilityLevel.JAVA_25;
   }

   public ILogger getLogger(String name) {
      return new LoggerAdapterConsole(name);
   }

   public void offer(IMixinInternal internal) {
      if (internal instanceof IMixinTransformerFactory factory) {
         transformer = factory.createTransformer();
      }
   }

   public InputStream getResourceAsStream(String name) {
      GameClassLoader l = loader;
      return l != null ? l.getResourceAsStream(name) : Main.class.getClassLoader().getResourceAsStream(name);
   }

   public URL[] getClassPath() {
      return new URL[0];
   }

   public Class<?> findClass(String name) throws ClassNotFoundException {
      return loader().loadClass(name);
   }

   public Class<?> findClass(String name, boolean initialize) throws ClassNotFoundException {
      return Class.forName(name, initialize, loader());
   }

   public Class<?> findAgentClass(String name, boolean initialize) throws ClassNotFoundException {
      return Class.forName(name, initialize, Main.class.getClassLoader());
   }

   public ClassNode getClassNode(String name) throws ClassNotFoundException, IOException {
      return this.getClassNode(name, true);
   }

   public ClassNode getClassNode(String name, boolean runTransformers) throws ClassNotFoundException, IOException {
      return this.getClassNode(name, runTransformers, 0);
   }

   public ClassNode getClassNode(String name, boolean runTransformers, int readerFlags) throws ClassNotFoundException, IOException {
      ClassNode node = new ClassNode();
      new ClassReader(loader().rawClassBytes(name)).accept(node, readerFlags);
      return node;
   }

   public Collection<ITransformer> getTransformers() {
      return List.of();
   }

   public Collection<ITransformer> getDelegatedTransformers() {
      return List.of();
   }

   public void addTransformerExclusion(String name) {
   }

   public void registerInvalidClass(String className) {
   }

   public boolean isClassLoaded(String className) {
      return loader().isLoaded(className);
   }

   public String getClassRestrictions(String className) {
      return "";
   }
}
