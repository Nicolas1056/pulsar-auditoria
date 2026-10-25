package com.metrofico.pulsar.launcher.launch;

import com.metrofico.pulsar.launcher.meta.VersionDetail;
import com.metrofico.pulsar.launcher.rules.Platform;
import com.metrofico.pulsar.launcher.rules.RuleEvaluator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class ArgumentBuilder {
   private static final Pattern PLACEHOLDER = Pattern.compile("\\$\\{([a-zA-Z_]+)}");
   static final List<String> LEGACY_JVM_ARGS = List.of("-Djava.library.path=${natives_directory}", "-cp", "${classpath}");
   private final Platform platform;
   private final Set<String> features;
   private final Map<String, String> values;

   ArgumentBuilder(Platform platform, Set<String> features, Map<String, String> values) {
      this.platform = platform;
      this.features = features;
      this.values = values;
   }

   List<String> jvm(VersionDetail version) {
      return version.usesLegacyArguments() ? this.substituteAll(LEGACY_JVM_ARGS) : this.resolve(version.arguments().jvm());
   }

   List<String> game(VersionDetail version) {
      return version.usesLegacyArguments()
         ? this.substituteAll(List.of(version.minecraftArguments().trim().split("\\s+")))
         : this.resolve(version.arguments().game());
   }

   private List<String> resolve(List<VersionDetail.Argument> arguments) {
      List<String> out = new ArrayList<>();

      for (VersionDetail.Argument argument : arguments) {
         if (RuleEvaluator.allows(argument.rules(), this.platform, this.features)) {
            out.addAll(this.substituteAll(argument.values()));
         }
      }

      return out;
   }

   private List<String> substituteAll(List<String> raw) {
      List<String> out = new ArrayList<>(raw.size());

      for (String s : raw) {
         out.add(this.substitute(s));
      }

      return out;
   }

   String substitute(String template) {
      Matcher m = PLACEHOLDER.matcher(template);
      StringBuilder sb = new StringBuilder();

      while (m.find()) {
         String key = m.group(1);
         String value = this.values.get(key);
         if (value == null) {
            throw new IllegalArgumentException("Unknown launch placeholder ${" + key + "} in: " + template);
         }

         m.appendReplacement(sb, Matcher.quoteReplacement(value));
      }

      m.appendTail(sb);
      return sb.toString();
   }
}
