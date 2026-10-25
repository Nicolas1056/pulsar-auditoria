package com.metrofico.pulsar.api.emote;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HexFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public final class EmoteDev {
   private static List<EmoteDev.Entry> entries = List.of();

   private EmoteDev() {
   }

   public static Path dir() {
      return Emotes.pack().dataDir().resolve("emotes").resolve("dev");
   }

   public static synchronized List<Emote> list() {
      Path dir = dir();
      List<EmoteDev.Entry> fresh = new ArrayList<>();
      if (Files.isDirectory(dir)) {
         try (Stream<Path> oggs = Files.list(dir)) {
            oggs.filter(fx -> fx.getFileName().toString().endsWith(".ogg")).forEach(EmoteDev::cacheSound);
         } catch (IOException var20) {
         }

         try (Stream<Path> s = Files.list(dir)) {
            for (Path f : s.filter(EmoteDev::isEmoteFile).sorted(Comparator.comparing(p -> p.getFileName().toString())).toList()) {
               long mtime = Files.getLastModifiedTime(f).toMillis();
               EmoteDev.Entry cached = entries.stream().filter(exx -> exx.file().equals(f) && exx.mtime() == mtime).findFirst().orElse(null);
               if (cached != null) {
                  fresh.add(cached);
               } else {
                  try {
                     String name = f.getFileName().toString();
                     boolean pemote = name.endsWith(".pemote") || name.endsWith(".pemote.json");
                     EmoteImport.SoundResolver resolver = effect -> cacheSound(dir.resolve(effect + ".ogg"));
                     EmoteImport.Result r = EmoteImport.importBytes(
                        Files.readAllBytes(f), pemote ? EmoteImport.Options.NONE : EmoteImport.Options.dev(idFromFile(name), resolver)
                     );
                     Emote e = r.emote();
                     fresh.add(new EmoteDev.Entry(f, mtime, e));
                     Emotes.log(
                        "emote dev: loaded "
                           + name
                           + " ("
                           + e.id()
                           + ", "
                           + e.duration()
                           + " s)"
                           + (r.warnings().isEmpty() ? "" : " · " + String.join(" · ", r.warnings()))
                     );
                  } catch (IOException | RuntimeException var16) {
                     Emotes.log("emote dev: skipped " + f.getFileName() + ": " + var16.getMessage());
                  }
               }
            }
         } catch (IOException var18) {
            Emotes.log("emote dev: cannot list " + dir + ": " + var18.getMessage());
         }
      } else {
         try {
            Files.createDirectories(dir);
         } catch (IOException var14) {
         }
      }

      entries = fresh;
      return fresh.stream().map(EmoteDev.Entry::emote).toList();
   }

   private static String cacheSound(Path ogg) {
      try {
         if (Files.isRegularFile(ogg) && Files.size(ogg) <= 256000L) {
            byte[] bytes = Files.readAllBytes(ogg);
            String sha = HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));
            Path target = Emotes.pack().cache().pathFor(sha);
            if (!Files.isRegularFile(target)) {
               Files.createDirectories(target.getParent());
               Files.write(target, bytes);
            }

            return sha;
         } else {
            return null;
         }
      } catch (Exception var4) {
         Emotes.log("emote dev: sonido " + ogg.getFileName() + " ilegible: " + var4.getMessage());
         return null;
      }
   }

   private static boolean isEmoteFile(Path p) {
      String n = p.getFileName().toString();
      return n.endsWith(".pemote") || n.endsWith(".json") || n.endsWith(".bbmodel") || n.endsWith(".glb");
   }

   static String idFromFile(String name) {
      String base = name.replaceAll("\\.(animation\\.json|pemote\\.json|pemote|json|bbmodel|glb)$", "");
      String slug = base.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "-").replaceAll("^-+|-+$", "");
      if (!slug.matches("^[a-z0-9].*")) {
         slug = "e-" + slug;
      }

      if (slug.length() > 64) {
         slug = slug.substring(0, 64);
      }

      while (slug.length() < 2) {
         slug = slug + "0";
      }

      return slug;
   }

   private record Entry(Path file, long mtime, Emote emote) {
   }
}
