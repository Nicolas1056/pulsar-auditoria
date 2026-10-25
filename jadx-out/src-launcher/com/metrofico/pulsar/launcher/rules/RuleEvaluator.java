package com.metrofico.pulsar.launcher.rules;

import com.metrofico.pulsar.launcher.meta.VersionDetail;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public final class RuleEvaluator {
   public static boolean allows(List<VersionDetail.Rule> rules, Platform platform, Set<String> enabledFeatures) {
      if (rules != null && !rules.isEmpty()) {
         boolean allowed = false;

         for (VersionDetail.Rule rule : rules) {
            if (matches(rule, platform, enabledFeatures)) {
               allowed = rule.allows();
            }
         }

         return allowed;
      } else {
         return true;
      }
   }

   static boolean matches(VersionDetail.Rule rule, Platform platform, Set<String> enabledFeatures) {
      if (rule.os() != null) {
         if (rule.os().name() != null && !rule.os().name().equals(platform.os())) {
            return false;
         }

         if (rule.os().arch() != null && !rule.os().arch().equals(platform.arch())) {
            return false;
         }

         if (rule.os().version() != null && (platform.version() == null || !Pattern.compile(rule.os().version()).matcher(platform.version()).find())) {
            return false;
         }
      }

      if (rule.features() != null) {
         for (Entry<String, Boolean> feature : rule.features().entrySet()) {
            if (enabledFeatures.contains(feature.getKey()) != feature.getValue()) {
               return false;
            }
         }
      }

      return true;
   }

   private RuleEvaluator() {
   }
}
