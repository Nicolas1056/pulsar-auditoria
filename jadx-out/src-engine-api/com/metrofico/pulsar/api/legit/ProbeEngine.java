package com.metrofico.pulsar.api.legit;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public final class ProbeEngine {
   public static final int ANSWER_BYTES = 16;
   private static final HexFormat HEX = HexFormat.of();
   private static volatile ProbeEngine current;
   private final ProbeEngine.Source source;

   public ProbeEngine(ProbeEngine.Source source) {
      this.source = source;
   }

   public static void install(ProbeEngine.Source source) {
      current = new ProbeEngine(source);
   }

   public static ProbeEngine current() {
      return current;
   }

   public List<String> classBytes(byte[] salt, List<String> classes) throws Exception {
      List<String> out = new ArrayList<>(classes.size());

      for (String name : classes) {
         out.add(HEX.formatHex(answer(salt, normalize(this.source.classBytes(name)))));
      }

      return out;
   }

   public List<String> jarRanges(byte[] salt, List<ProbeEngine.Range> ranges) throws IOException {
      List<String> out = new ArrayList<>(ranges.size());

      for (ProbeEngine.Range r : ranges) {
         Path jar = this.source.jar(r.jar());
         if (jar == null) {
            throw new IOException("unknown jar " + r.jar());
         }

         out.add(HEX.formatHex(answer(salt, readRange(jar, r.offset(), r.length()))));
      }

      return out;
   }

   public static byte[] answer(byte[] salt, byte[] data) {
      MessageDigest sha = sha256();
      sha.update(salt);
      sha.update(data);
      byte[] full = sha.digest();
      byte[] out = new byte[16];
      System.arraycopy(full, 0, out, 0, 16);
      return out;
   }

   public static byte[] normalize(byte[] bytes) {
      byte[] out = null;

      for (int i = 0; i + 36 <= bytes.length; i++) {
         if (isUuidAt(bytes, i)) {
            if (out == null) {
               out = (byte[])bytes.clone();
            }

            byte[] suffix = new byte[6];
            System.arraycopy(bytes, i + 30, suffix, 0, 6);
            zeroRange(out, i, 36);
            replaceAllOccurrences(out, suffix);
            i += 35;
         }
      }

      return out == null ? bytes : out;
   }

   public static String bootHash(List<Path> jars) throws IOException {
      Map<String, String> sorted = new TreeMap<>();

      for (Path jar : jars) {
         sorted.put(jar.getFileName().toString(), fileSha256(jar));
      }

      MessageDigest sha = sha256();

      for (Entry<String, String> e : sorted.entrySet()) {
         sha.update((e.getKey() + "\t" + e.getValue() + "\n").getBytes(StandardCharsets.UTF_8));
      }

      return HEX.formatHex(sha.digest());
   }

   public static String fileSha256(Path file) throws IOException {
      MessageDigest sha = sha256();
      sha.update(Files.readAllBytes(file));
      return HEX.formatHex(sha.digest());
   }

   static byte[] readRange(Path jar, long offset, int length) throws IOException {
      byte[] var6;
      try (RandomAccessFile f = new RandomAccessFile(jar.toFile(), "r")) {
         if (offset < 0L || length < 0 || offset + length > f.length()) {
            throw new IOException("range out of " + jar.getFileName() + ": " + offset + "+" + length);
         }

         byte[] out = new byte[length];
         f.seek(offset);
         f.readFully(out);
         var6 = out;
      }

      return var6;
   }

   private static boolean isUuidAt(byte[] b, int i) {
      for (int k = 0; k < 36; k++) {
         byte c = b[i + k];
         boolean dash = k == 8 || k == 13 || k == 18 || k == 23;
         if (dash ? c != 45 : (c < 48 || c > 57) && (c < 97 || c > 102)) {
            return false;
         }
      }

      return true;
   }

   private static void zeroRange(byte[] out, int at, int length) {
      for (int k = 0; k < length; k++) {
         out[at + k] = 48;
      }
   }

   private static void replaceAllOccurrences(byte[] out, byte[] needle) {
      label35:
      for (int i = 0; i + needle.length <= out.length; i++) {
         for (int k = 0; k < needle.length; k++) {
            if (out[i + k] != needle[k]) {
               continue label35;
            }
         }

         for (int kx = 0; kx < needle.length; kx++) {
            out[i + kx] = 48;
         }

         i += needle.length - 1;
      }
   }

   private static MessageDigest sha256() {
      try {
         return MessageDigest.getInstance("SHA-256");
      } catch (NoSuchAlgorithmException var1) {
         throw new IllegalStateException(var1);
      }
   }

   public record Range(String jar, long offset, int length) {
   }

   public interface Source {
      byte[] classBytes(String var1) throws Exception;

      Path jar(String var1);
   }
}
