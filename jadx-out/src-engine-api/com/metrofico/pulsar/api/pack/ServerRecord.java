package com.metrofico.pulsar.api.pack;

import java.util.List;
import java.util.Locale;

public record ServerRecord(String id, String name, List<String> domains, String publicKeyBase64, boolean active, String registeredAt) {
   public boolean covers(String typedHost) {
      if (typedHost != null && !typedHost.isEmpty()) {
         String h = typedHost.toLowerCase(Locale.ROOT);

         for (String d : this.domains) {
            String dom = d.toLowerCase(Locale.ROOT);
            if (h.equals(dom) || h.endsWith("." + dom)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }
}
