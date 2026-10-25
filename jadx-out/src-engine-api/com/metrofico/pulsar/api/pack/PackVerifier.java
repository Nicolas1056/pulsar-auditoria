package com.metrofico.pulsar.api.pack;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class PackVerifier {
   public static final long MAX_TOTAL_BYTES = 268435456L;
   public static final int MAX_RESOURCES = 2000;
   public static final int MAX_CAPABILITIES = 64;
   private final PublicKey pulsarKey;

   static boolean allowedUrl(String url) {
      return url.startsWith("https://") || url.startsWith("http://127.0.0.1") || url.startsWith("http://localhost");
   }

   public PackVerifier(PublicKey pulsarKey) {
      this.pulsarKey = pulsarKey;
   }

   public static PublicKey publicKeyFromBase64(String spkiBase64) throws GeneralSecurityException {
      return KeyFactory.getInstance("Ed25519").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(spkiBase64)));
   }

   public static PublicKey publicKeyFromPem(String pem) throws GeneralSecurityException {
      return publicKeyFromBase64(pem.replaceAll("-----[A-Z ]+-----", "").replaceAll("\\s", ""));
   }

   public ServerRecord verifyServer(String registryJson) throws PackVerifier.RejectedException {
      Map<String, Object> root;
      try {
         root = Json.object(Json.parse(registryJson), "server registry");
      } catch (RuntimeException var11) {
         throw new PackVerifier.RejectedException("server_bad_json", "El registro del servidor no se pudo leer.");
      }

      if (!Long.valueOf(1L).equals(root.get("version"))) {
         throw new PackVerifier.RejectedException("server_version", "Versión del registro no soportada.");
      } else {
         Object server = root.get("server");
         String signature = root.get("signature") instanceof String s ? s : null;
         if (server != null && signature != null && verify(Json.canonical(server), signature, this.pulsarKey)) {
            Map<String, Object> sx = Json.object(server, "server");
            String status = Json.string(sx, "status", "server");
            List<String> domains = new ArrayList<>();
            if (sx.get("domains") instanceof List<?> list) {
               list.forEach(dx -> domains.add(String.valueOf(dx)));
            } else if (sx.get("domain") instanceof String d && !d.isEmpty()) {
               domains.add(d);
            }

            ServerRecord record = new ServerRecord(
               Json.string(sx, "id", "server"),
               Json.string(sx, "name", "server"),
               List.copyOf(domains),
               Json.string(sx, "publicKey", "server"),
               "active".equals(status),
               Json.string(sx, "registeredAt", "server")
            );
            if (!record.active()) {
               throw new PackVerifier.RejectedException("server_revoked", "Pulsar ha revocado a este servidor.");
            } else {
               return record;
            }
         } else {
            throw new PackVerifier.RejectedException("server_unsigned", "El registro del servidor no está firmado por Pulsar.");
         }
      }
   }

   public static void verifyDestination(ServerRecord record, String typedHost) throws PackVerifier.RejectedException {
      if (!record.covers(typedHost)) {
         throw new PackVerifier.RejectedException("other_network", "Este pack es de " + record.name() + ", no del servidor al que entraste.");
      }
   }

   public PackManifest verifyPack(String manifestJson, ServerRecord server) throws PackVerifier.RejectedException {
      Map<String, Object> root;
      try {
         root = Json.object(Json.parse(manifestJson), "pack");
      } catch (RuntimeException var29) {
         throw new PackVerifier.RejectedException("pack_bad_json", "El pack del servidor no se pudo leer.");
      }

      if (!Long.valueOf(1L).equals(root.get("version"))) {
         throw new PackVerifier.RejectedException("pack_version", "Versión de pack no soportada.");
      } else {
         Object pack = root.get("pack");
         String signature = root.get("signature") instanceof String s ? s : null;

         PublicKey serverKey;
         try {
            serverKey = publicKeyFromBase64(server.publicKeyBase64());
         } catch (IllegalArgumentException | GeneralSecurityException var28) {
            throw new PackVerifier.RejectedException("server_key", "La clave del servidor no es válida.");
         }

         if (pack != null && signature != null && verify(Json.canonical(pack), signature, serverKey)) {
            String id;
            String serverId;
            Map<String, Object> p;
            try {
               p = Json.object(pack, "pack");
               id = Json.string(p, "id", "pack");
               serverId = Json.string(p, "serverId", "pack");
            } catch (RuntimeException var27) {
               throw new PackVerifier.RejectedException("pack_fields", "Al pack le faltan datos: " + var27.getMessage());
            }

            if (!serverId.equals(server.id())) {
               throw new PackVerifier.RejectedException("pack_server_mismatch", "El pack dice ser de otro servidor.");
            } else if (!id.matches("[a-z0-9-]{3,64}")) {
               throw new PackVerifier.RejectedException("pack_id", "Identificador de pack no válido.");
            } else {
               Set<Capability> caps = EnumSet.noneOf(Capability.class);
               List<Object> capIds = Json.array(p.getOrDefault("capabilities", List.of()), "pack.capabilities");
               if (capIds.size() > 64) {
                  throw new PackVerifier.RejectedException("pack_capabilities", "Demasiadas capacidades.");
               } else {
                  for (Object c : capIds) {
                     Capability cap = Capability.byId(String.valueOf(c))
                        .orElseThrow(
                           () -> new PackVerifier.RejectedException("pack_capability_unknown", "El pack pide una capacidad que Pulsar no ofrece: " + c)
                        );
                     caps.add(cap);
                  }

                  List<PackManifest.Resource> resources = new ArrayList<>();
                  List<Object> res = Json.array(p.getOrDefault("resources", List.of()), "pack.resources");
                  if (res.size() > 2000) {
                     throw new PackVerifier.RejectedException("pack_resources", "El pack tiene demasiados archivos.");
                  } else {
                     String baseUrl = p.get("baseUrl") instanceof String b ? b : null;
                     if (baseUrl != null && !allowedUrl(baseUrl)) {
                        throw new PackVerifier.RejectedException("pack_url", "Las descargas del pack deben ser https.");
                     } else {
                        long total = 0L;

                        for (Object o : res) {
                           Map<String, Object> r = Json.object(o, "resource");
                           String path = Json.string(r, "path", "resource");
                           String sha = Json.string(r, "sha256", "resource");
                           long size = Json.integer(r, "size", "resource");
                           if (path.matches("[A-Za-z0-9_./-]{1,200}") && !path.contains("..") && !path.startsWith("/")) {
                              if (sha.matches("[0-9a-f]{64}") && size >= 0L) {
                                 String url = r.get("url") instanceof String u ? u : null;
                                 if (url != null && !allowedUrl(url)) {
                                    throw new PackVerifier.RejectedException("pack_url", "Las descargas del pack deben ser https.");
                                 }

                                 if (url == null && baseUrl == null) {
                                    throw new PackVerifier.RejectedException("pack_url", "El pack no dice de dónde descargar " + path);
                                 }

                                 total += size;
                                 resources.add(new PackManifest.Resource(path, sha, size, url != null ? url : baseUrl.replaceAll("/+$", "") + "/" + sha));
                                 continue;
                              }

                              throw new PackVerifier.RejectedException("pack_hash", "Archivo sin hash válido: " + path);
                           }

                           throw new PackVerifier.RejectedException("pack_path", "Ruta de archivo no permitida: " + path);
                        }

                        if (total > 268435456L) {
                           throw new PackVerifier.RejectedException("pack_too_big", "El pack supera el tamaño máximo (256 MB).");
                        } else {
                           List<String> locales = new ArrayList<>();

                           for (Object l : Json.array(p.getOrDefault("locales", List.of()), "pack.locales")) {
                              locales.add(String.valueOf(l));
                           }

                           return new PackManifest(
                              id,
                              Json.integer(p, "packVersion", "pack"),
                              serverId,
                              Json.string(p, "name", "pack"),
                              p.get("minClient") instanceof String m ? m : null,
                              caps,
                              List.copyOf(resources),
                              baseUrl,
                              List.copyOf(locales)
                           );
                        }
                     }
                  }
               }
            }
         } else {
            throw new PackVerifier.RejectedException("pack_unsigned", "El pack no está firmado por este servidor.");
         }
      }
   }

   public static boolean verify(String canonical, String signatureB64, PublicKey key) {
      try {
         Signature s = Signature.getInstance("Ed25519");
         s.initVerify(key);
         s.update(canonical.getBytes(StandardCharsets.UTF_8));
         return s.verify(Base64.getDecoder().decode(signatureB64));
      } catch (IllegalArgumentException | GeneralSecurityException var4) {
         return false;
      }
   }

   public static final class RejectedException extends Exception {
      private final String code;

      public RejectedException(String code, String message) {
         super(message);
         this.code = code;
      }

      public String code() {
         return this.code;
      }
   }
}
