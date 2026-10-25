package com.metrofico.pulsar.bootstrap.mixin;

import com.metrofico.pulsar.bootstrap.loader.GameClassLoader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Type;

public final class MixinTargetValidator {
   private static final String MIXIN_ANNOTATION = "Lorg/spongepowered/asm/mixin/Mixin;";
   private final GameClassLoader loader;

   public MixinTargetValidator(GameClassLoader loader) {
      this.loader = loader;
   }

   public void validate(List<String> configNames) {
      List<MixinTargetValidator.MissingTarget> missing = new ArrayList<>();

      for (String configName : configNames) {
         MixinConfigFile config = this.readConfig(configName);

         for (String mixinClass : config.mixinClasses()) {
            for (String target : this.declaredTargets(configName, mixinClass)) {
               if (!this.loader.hasClass(target) && this.loader.getResource(target.replace('.', '/') + ".class") == null) {
                  missing.add(new MixinTargetValidator.MissingTarget(configName, mixinClass, target));
               }
            }
         }
      }

      if (!missing.isEmpty()) {
         throw new IllegalStateException("Refusing to start: " + missing.size() + " @Mixin target(s) do not exist in this game version: " + missing);
      }
   }

   private MixinConfigFile readConfig(String name) {
      try {
         MixinConfigFile var3;
         try (InputStream in = this.loader.getResourceAsStream(name)) {
            if (in == null) {
               throw new IllegalStateException("Mixin config not found on the classpath: " + name);
            }

            var3 = MixinConfigFile.read(name, in);
         }

         return var3;
      } catch (IOException var7) {
         throw new IllegalStateException("Cannot read mixin config " + name, var7);
      }
   }

   List<String> declaredTargets(String configName, String mixinClass) {
      byte[] bytes;
      try {
         bytes = this.loader.rawClassBytes(mixinClass);
      } catch (ClassNotFoundException var5) {
         throw new IllegalStateException("Mixin config " + configName + " lists " + mixinClass + " but the class does not exist", var5);
      }

      final List<String> targets = new ArrayList<>();
      new ClassReader(bytes).accept(new ClassVisitor(589824) {
         {
            Objects.requireNonNull(MixinTargetValidator.this);
         }

         public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
            return !"Lorg/spongepowered/asm/mixin/Mixin;".equals(descriptor) ? null : new AnnotationVisitor(589824) {
               {
                  Objects.requireNonNull(<VAR_NAMELESS_ENCLOSURE>);
               }

               public AnnotationVisitor visitArray(String name) {
                  return !"value".equals(name) && !"targets".equals(name) ? null : new AnnotationVisitor(589824) {
                     {
                        Objects.requireNonNull(<VAR_NAMELESS_ENCLOSURE>);
                     }

                     public void visit(String n, Object value) {
                        targets.add(value instanceof Type type ? type.getClassName() : value.toString().replace('/', '.'));
                     }
                  };
               }
            };
         }
      }, 7);
      if (targets.isEmpty()) {
         throw new IllegalStateException("Mixin config " + configName + " lists " + mixinClass + " but it has no @Mixin targets");
      } else {
         return targets;
      }
   }

   public record MissingTarget(String config, String mixinClass, String target) {
      @Override
      public String toString() {
         return this.config + " → " + this.mixinClass + " → " + this.target;
      }
   }
}
