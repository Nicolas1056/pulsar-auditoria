package com.metrofico.pulsar.launcher.auth;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermissions;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

final class MachineBoundFile implements SecretStores.Native {
   private static final byte VERSION = 1;
   private static final int IV_BYTES = 12;
   private static final List<Path> MACHINE_ID = List.of(Path.of("/etc/machine-id"), Path.of("/var/lib/dbus/machine-id"));
   private final Path dir;
   private final SecureRandom random = new SecureRandom();

   MachineBoundFile(Path dataDir) {
      this.dir = dataDir.resolve("secrets");
   }

   @Override
   public String describe() {
      return "archivo cifrado en este equipo";
   }

   @Override
   public boolean persistent() {
      return true;
   }

   @Override
   public boolean available() {
      try {
         this.ensureDir();
         return Files.isWritable(this.dir);
      } catch (IOException var2) {
         return false;
      }
   }

   private Path file(String key) {
      return this.dir.resolve(key.replaceAll("[^A-Za-z0-9._-]", "_") + ".sec");
   }

   @Override
   public Optional<String> get(String key) {
      Path f = this.file(key);

      try {
         if (!Files.isRegularFile(f)) {
            return Optional.empty();
         } else {
            byte[] blob = Files.readAllBytes(f);
            if (blob.length > 13 && blob[0] == 1) {
               Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
               c.init(2, this.key(), new GCMParameterSpec(128, blob, 1, 12));
               c.updateAAD(key.getBytes(StandardCharsets.UTF_8));
               byte[] plain = c.doFinal(blob, 13, blob.length - 1 - 12);
               return Optional.of(new String(plain, StandardCharsets.UTF_8)).filter(s -> !s.isEmpty());
            } else {
               return Optional.empty();
            }
         }
      } catch (GeneralSecurityException | IOException var6) {
         return Optional.empty();
      }
   }

   @Override
   public void put(String key, String value) {
      try {
         this.ensureDir();
         byte[] iv = new byte[12];
         this.random.nextBytes(iv);
         Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
         c.init(1, this.key(), new GCMParameterSpec(128, iv));
         c.updateAAD(key.getBytes(StandardCharsets.UTF_8));
         byte[] ct = c.doFinal(value.getBytes(StandardCharsets.UTF_8));
         byte[] blob = new byte[13 + ct.length];
         blob[0] = 1;
         System.arraycopy(iv, 0, blob, 1, 12);
         System.arraycopy(ct, 0, blob, 13, ct.length);
         this.writePrivate(this.file(key), blob);
      } catch (GeneralSecurityException | IOException var7) {
      }
   }

   @Override
   public void delete(String key) {
      try {
         Files.deleteIfExists(this.file(key));
      } catch (IOException var3) {
      }
   }

   private SecretKeySpec key() throws IOException, GeneralSecurityException {
      Path keyFile = this.dir.resolve("machine.key");
      byte[] local;
      if (Files.isRegularFile(keyFile) && Files.size(keyFile) == 32L) {
         local = Files.readAllBytes(keyFile);
      } else {
         local = new byte[32];
         this.random.nextBytes(local);
         this.writePrivate(keyFile, local);
      }

      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(new SecretKeySpec(local, "HmacSHA256"));
      byte[] k = mac.doFinal(machineId());
      Arrays.fill(local, (byte)0);
      return new SecretKeySpec(k, "AES");
   }

   private static byte[] machineId() {
      for (Path p : MACHINE_ID) {
         try {
            String id = Files.readString(p).trim();
            if (!id.isEmpty()) {
               return id.getBytes(StandardCharsets.UTF_8);
            }
         } catch (IOException var3) {
         }
      }

      return new byte[0];
   }

   private void ensureDir() throws IOException {
      if (!Files.isDirectory(this.dir)) {
         if (posix()) {
            Files.createDirectories(this.dir, PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwx------")));
         } else {
            Files.createDirectories(this.dir);
         }
      }
   }

   private void writePrivate(Path target, byte[] data) throws IOException {
      Path tmp = target.resolveSibling(target.getFileName() + ".tmp");
      Files.deleteIfExists(tmp);
      if (posix()) {
         Files.createFile(tmp, PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rw-------")));
      }

      Files.write(tmp, data);

      try {
         Files.move(tmp, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
      } catch (AtomicMoveNotSupportedException var5) {
         Files.move(tmp, target, StandardCopyOption.REPLACE_EXISTING);
      }
   }

   private static boolean posix() {
      return FileSystems.getDefault().supportedFileAttributeViews().contains("posix");
   }
}
