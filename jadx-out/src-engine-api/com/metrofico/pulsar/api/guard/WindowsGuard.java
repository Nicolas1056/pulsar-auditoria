package com.metrofico.pulsar.api.guard;

import java.io.InputStream;
import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class WindowsGuard implements Guard {
   private static final int LIST_MODULES_ALL = 3;
   private static final int TH32CS_SNAPTHREAD = 4;
   private static final int THREAD_QUERY_INFORMATION = 64;
   private static final int THREAD_QUERY_SET_WIN32_START_ADDRESS = 9;
   private static final int THREADENTRY32_SIZE = 28;
   private static final int MODULEINFO_SIZE = 24;
   private static final int MAX_PATH_CHARS = 32768;
   private static final MemorySegment CURRENT_PROCESS = MemorySegment.ofAddress(-1L);
   private static final MemorySegment INVALID_HANDLE = MemorySegment.ofAddress(-1L);
   private static final int GENERIC_READ = Integer.MIN_VALUE;
   private static final int FILE_SHARE_ALL = 7;
   private static final int OPEN_EXISTING = 3;
   private static final int FILE_ATTRIBUTE_NORMAL = 128;
   private static final int CATALOG_INFO_SIZE = 524;
   private static final int MAX_HASH_BYTES = 64;
   private static final int WTD_CHOICE_FILE = 1;
   private static final int WTD_CHOICE_CATALOG = 2;
   private static final int WTD_REVOCATION_CHECK_NONE = 16;
   private static final int WTD_USE_DEFAULT_OSVER_CHECK = 1024;
   private static final int WTD_CACHE_ONLY_URL_RETRIEVAL = 4096;
   static final long[] WINTRUST_ACTION_GENERIC_VERIFY_V2 = new long[]{11191659L, 52548L, 4560L, -8304073936945900050L};
   private static final long[] DRIVER_ACTION_VERIFY = new long[]{4149274307L, 14574L, 4561L, -8798625471026522642L};
   private static final WindowsGuard.Api API = WindowsGuard.Api.load();
   private final Map<String, GuardReport.Module> measured = new ConcurrentHashMap<>();
   private final Map<String, Boolean> systemCatalog = new ConcurrentHashMap<>();

   @Override
   public boolean supported() {
      return Guard.osName().equals("windows") && API != null;
   }

   @Override
   public GuardReport scan() {
      if (API == null) {
         return GuardReport.EMPTY;
      } else {
         boolean debugger = false;

         try {
            debugger = (int)API.isDebuggerPresent.invoke() != 0 || remoteDebugger();
         } catch (Throwable var14) {
         }

         List<GuardReport.Module> modules = new ArrayList<>();
         int foreignThreads = 0;

         try (Arena arena = Arena.ofConfined()) {
            List<long[]> ranges = new ArrayList<>();

            for (MemorySegment module : modules(arena)) {
               String path = fileName(arena, module);
               long[] range = range(arena, module);
               if (range != null) {
                  ranges.add(range);
               }

               if (path != null) {
                  String key = path.toLowerCase(Locale.ROOT);
                  if (!this.systemCatalog.computeIfAbsent(key, k -> catalogSigned(path))) {
                     modules.add(this.measured.computeIfAbsent(key, k -> measure(path)));
                  }
               }
            }

            foreignThreads = foreignThreads(arena, ranges);
         } catch (Throwable var13) {
         }

         return new GuardReport(debugger, List.copyOf(modules), foreignThreads, 0);
      }
   }

   private static List<MemorySegment> modules(Arena arena) throws Throwable {
      int capacity = 512;

      for (int attempt = 0; attempt < 3; attempt++) {
         MemorySegment handles = arena.allocate(ValueLayout.ADDRESS, capacity);
         MemorySegment needed = arena.allocate(ValueLayout.JAVA_INT);
         int ok = (int)API.enumProcessModulesEx
            .invoke((MemorySegment)CURRENT_PROCESS, (MemorySegment)handles, (int)((int)handles.byteSize()), (MemorySegment)needed, (int)3);
         if (ok == 0) {
            return List.of();
         }

         int count = needed.get(ValueLayout.JAVA_INT, 0L) / (int)ValueLayout.ADDRESS.byteSize();
         if (count <= capacity) {
            List<MemorySegment> out = new ArrayList<>(count);

            for (int i = 0; i < count; i++) {
               out.add(handles.getAtIndex(ValueLayout.ADDRESS, (long)i));
            }

            return out;
         }

         capacity = count + 64;
      }

      return List.of();
   }

   private static String fileName(Arena arena, MemorySegment module) throws Throwable {
      MemorySegment buffer = arena.allocate(ValueLayout.JAVA_CHAR, 32768L);
      int chars = (int)API.getModuleFileName.invoke((MemorySegment)module, (MemorySegment)buffer, (int)32768);
      return chars <= 0 ? null : buffer.getString(0L, StandardCharsets.UTF_16LE);
   }

   private static long[] range(Arena arena, MemorySegment module) throws Throwable {
      MemorySegment info = arena.allocate(24L, 8L);
      int ok = (int)API.getModuleInformation.invoke((MemorySegment)CURRENT_PROCESS, (MemorySegment)module, (MemorySegment)info, (int)24);
      if (ok == 0) {
         return null;
      } else {
         long base = info.get(ValueLayout.ADDRESS, 0L).address();
         long size = Integer.toUnsignedLong(info.get(ValueLayout.JAVA_INT, 8L));
         return new long[]{base, base + size};
      }
   }

   private static int foreignThreads(Arena arena, List<long[]> ranges) throws Throwable {
      if (ranges.isEmpty()) {
         return 0;
      } else {
         MemorySegment snapshot = (MemorySegment)API.createSnapshot.invoke((int)4, (int)0);
         if (snapshot.address() == INVALID_HANDLE.address()) {
            return 0;
         } else {
            int pid = (int)ProcessHandle.current().pid();
            int foreign = 0;

            try {
               MemorySegment entry = arena.allocate(28L, 4L);
               MemorySegment start = arena.allocate(ValueLayout.ADDRESS);
               entry.set(ValueLayout.JAVA_INT, 0L, 28);

               for (int more = (int)API.thread32First.invoke((MemorySegment)snapshot, (MemorySegment)entry);
                  more != 0;
                  more = (int)API.thread32Next.invoke((MemorySegment)snapshot, (MemorySegment)entry)
               ) {
                  if (entry.get(ValueLayout.JAVA_INT, 12L) == pid) {
                     int tid = entry.get(ValueLayout.JAVA_INT, 8L);
                     MemorySegment thread = (MemorySegment)API.openThread.invoke((int)64, (int)0, (int)tid);
                     if (thread.address() != 0L) {
                        try {
                           start.set(ValueLayout.ADDRESS, 0L, MemorySegment.NULL);
                           int status = (int)API.queryThread
                              .invoke(
                                 (MemorySegment)thread,
                                 (int)9,
                                 (MemorySegment)start,
                                 (int)((int)ValueLayout.ADDRESS.byteSize()),
                                 (MemorySegment)MemorySegment.NULL
                              );
                           long address = start.get(ValueLayout.ADDRESS, 0L).address();
                           if (status == 0 && address != 0L && !inside(ranges, address)) {
                              foreign++;
                           }
                        } finally {
                           API.closeHandle.invoke((MemorySegment)thread);
                        }
                     }
                  }
               }
            } finally {
               API.closeHandle.invoke((MemorySegment)snapshot);
            }

            return foreign;
         }
      }
   }

   private static boolean inside(List<long[]> ranges, long address) {
      for (long[] r : ranges) {
         if (Long.compareUnsigned(address, r[0]) >= 0 && Long.compareUnsigned(address, r[1]) < 0) {
            return true;
         }
      }

      return false;
   }

   static boolean catalogSigned(String path) {
      return API.catalog == null ? false : inCatalog(path, null) || inCatalog(path, "SHA256");
   }

   private static boolean inCatalog(String path, String algorithm) {
      WindowsGuard.Catalog cat = API.catalog;

      try {
         boolean var14;
         try (Arena arena = Arena.ofConfined()) {
            MemorySegment name = arena.allocateFrom(path, StandardCharsets.UTF_16LE);
            MemorySegment file = (MemorySegment)API.createFile
               .invoke(
                  (MemorySegment)name, (int)Integer.MIN_VALUE, (int)7, (MemorySegment)MemorySegment.NULL, (int)3, (int)128, (MemorySegment)MemorySegment.NULL
               );
            if (file.address() == INVALID_HANDLE.address()) {
               return false;
            }

            try {
               MemorySegment adminOut = arena.allocate(ValueLayout.ADDRESS);
               MemorySegment hashAlgorithm = algorithm == null ? MemorySegment.NULL : arena.allocateFrom(algorithm, StandardCharsets.UTF_16LE);
               if ((int)cat.acquireContext2
                     .invoke(
                        (MemorySegment)adminOut,
                        (MemorySegment)guid(arena, DRIVER_ACTION_VERIFY),
                        (MemorySegment)hashAlgorithm,
                        (MemorySegment)MemorySegment.NULL,
                        (int)0
                     )
                  == 0) {
                  return false;
               }

               MemorySegment admin = adminOut.get(ValueLayout.ADDRESS, 0L);

               try {
                  MemorySegment hashSize = arena.allocate(ValueLayout.JAVA_INT);
                  hashSize.set(ValueLayout.JAVA_INT, 0L, 64);
                  MemorySegment hash = arena.allocate(64L);
                  if ((int)cat.calcHash2.invoke((MemorySegment)admin, (MemorySegment)file, (MemorySegment)hashSize, (MemorySegment)hash, (int)0) == 0) {
                     return false;
                  }

                  int size = hashSize.get(ValueLayout.JAVA_INT, 0L);
                  MemorySegment info = (MemorySegment)cat.enumFromHash
                     .invoke((MemorySegment)admin, (MemorySegment)hash, (int)size, (int)0, (MemorySegment)MemorySegment.NULL);
                  if (info.address() == 0L) {
                     return false;
                  }

                  try {
                     MemorySegment catalogInfo = arena.allocate(524L, 4L);
                     catalogInfo.set(ValueLayout.JAVA_INT, 0L, 524);
                     if ((int)cat.infoFromContext.invoke((MemorySegment)info, (MemorySegment)catalogInfo, (int)0) == 0) {
                        return false;
                     }

                     var14 = verifyCatalogMember(arena, catalogInfo.asSlice(4L), name, file, hash, size, admin);
                  } finally {
                     cat.releaseCatalogContext.invoke((MemorySegment)admin, (MemorySegment)info, (int)0);
                  }
               } finally {
                  cat.releaseContext.invoke((MemorySegment)admin, (int)0);
               }
            } finally {
               API.closeHandle.invoke((MemorySegment)file);
            }
         }

         return var14;
      } catch (Throwable var41) {
         return false;
      }
   }

   private static boolean verifyCatalogMember(
      Arena arena, MemorySegment catalogFile, MemorySegment memberPath, MemorySegment file, MemorySegment hash, int size, MemorySegment admin
   ) throws Throwable {
      String tag = HexFormat.of().withUpperCase().formatHex(hash.asSlice(0L, size).toArray(ValueLayout.JAVA_BYTE));
      MemorySegment catalogInfo = arena.allocate(72L, 8L);
      catalogInfo.set(ValueLayout.JAVA_INT, 0L, 72);
      catalogInfo.set(ValueLayout.ADDRESS, 8L, catalogFile);
      catalogInfo.set(ValueLayout.ADDRESS, 16L, arena.allocateFrom(tag, StandardCharsets.UTF_16LE));
      catalogInfo.set(ValueLayout.ADDRESS, 24L, memberPath);
      catalogInfo.set(ValueLayout.ADDRESS, 32L, file);
      catalogInfo.set(ValueLayout.ADDRESS, 40L, hash);
      catalogInfo.set(ValueLayout.JAVA_INT, 48L, size);
      catalogInfo.set(ValueLayout.ADDRESS, 64L, admin);
      return verifyTrust(arena, 2, catalogInfo, 1024);
   }

   private static GuardReport.Module measure(String path) {
      String name = path.substring(Math.max(path.lastIndexOf(92), path.lastIndexOf(47)) + 1).toLowerCase(Locale.ROOT);
      return new GuardReport.Module(name, sha256(Path.of(path)), signed(path));
   }

   private static String sha256(Path file) {
      try {
         String var8;
         try (InputStream in = Files.newInputStream(file)) {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] buf = new byte[65536];

            while ((n = in.read(buf)) > 0) {
               sha.update(buf, 0, n);
            }

            var8 = HexFormat.of().formatHex(sha.digest());
         }

         return var8;
      } catch (Exception var7) {
         return "";
      }
   }

   static boolean signed(String path) {
      try {
         boolean var3;
         try (Arena arena = Arena.ofConfined()) {
            MemorySegment fileInfo = arena.allocate(32L, 8L);
            fileInfo.set(ValueLayout.JAVA_INT, 0L, 32);
            fileInfo.set(ValueLayout.ADDRESS, 8L, arena.allocateFrom(path, StandardCharsets.UTF_16LE));
            var3 = verifyTrust(arena, 1, fileInfo, 0);
         }

         return var3;
      } catch (Throwable var6) {
         return false;
      }
   }

   private static boolean verifyTrust(Arena arena, int unionChoice, MemorySegment subject, int extraProvFlags) throws Throwable {
      if (API.winVerifyTrust == null) {
         return false;
      } else {
         MemorySegment action = guid(arena, WINTRUST_ACTION_GENERIC_VERIFY_V2);
         MemorySegment data = arena.allocate(88L, 8L);
         data.set(ValueLayout.JAVA_INT, 0L, 88);
         data.set(ValueLayout.JAVA_INT, 24L, 2);
         data.set(ValueLayout.JAVA_INT, 28L, 0);
         data.set(ValueLayout.JAVA_INT, 32L, unionChoice);
         data.set(ValueLayout.ADDRESS, 40L, subject);
         data.set(ValueLayout.JAVA_INT, 48L, 1);
         data.set(ValueLayout.JAVA_INT, 72L, 4112 | extraProvFlags);
         int result = (int)API.winVerifyTrust.invoke((MemorySegment)INVALID_HANDLE, (MemorySegment)action, (MemorySegment)data);
         data.set(ValueLayout.JAVA_INT, 48L, 2);
         API.winVerifyTrust.invoke((MemorySegment)INVALID_HANDLE, (MemorySegment)action, (MemorySegment)data);
         return result == 0;
      }
   }

   static MemorySegment guid(Arena arena, long[] g) {
      MemorySegment out = arena.allocate(16L, 4L);
      out.set(ValueLayout.JAVA_INT, 0L, (int)g[0]);
      out.set(ValueLayout.JAVA_SHORT, 4L, (short)g[1]);
      out.set(ValueLayout.JAVA_SHORT, 6L, (short)g[2]);

      for (int i = 0; i < 8; i++) {
         out.set(ValueLayout.JAVA_BYTE, (long)(8 + i), (byte)(g[3] >>> 56 - 8 * i));
      }

      return out;
   }

   static boolean remoteDebugger() {
      if (API == null) {
         return false;
      } else {
         try {
            boolean var2;
            try (Arena arena = Arena.ofConfined()) {
               MemorySegment present = arena.allocate(ValueLayout.JAVA_INT);
               var2 = (int)API.checkRemoteDebuggerPresent.invoke((MemorySegment)CURRENT_PROCESS, (MemorySegment)present) != 0
                  && present.get(ValueLayout.JAVA_INT, 0L) != 0;
            }

            return var2;
         } catch (Throwable var5) {
            return false;
         }
      }
   }

   private record Api(
      MethodHandle isDebuggerPresent,
      MethodHandle checkRemoteDebuggerPresent,
      MethodHandle enumProcessModulesEx,
      MethodHandle getModuleFileName,
      MethodHandle getModuleInformation,
      MethodHandle createSnapshot,
      MethodHandle thread32First,
      MethodHandle thread32Next,
      MethodHandle openThread,
      MethodHandle closeHandle,
      MethodHandle queryThread,
      MethodHandle createFile,
      MethodHandle winVerifyTrust,
      WindowsGuard.Catalog catalog
   ) {
      static WindowsGuard.Api load() {
         if (!System.getProperty("os.name", "").toLowerCase(Locale.ROOT).contains("win")) {
            return null;
         } else {
            try {
               Linker linker = Linker.nativeLinker();
               SymbolLookup kernel32 = SymbolLookup.libraryLookup("kernel32", Arena.global());
               SymbolLookup ntdll = SymbolLookup.libraryLookup("ntdll", Arena.global());
               ValueLayout i = ValueLayout.JAVA_INT;
               ValueLayout a = ValueLayout.ADDRESS;
               return new WindowsGuard.Api(
                  fn(linker, kernel32, "IsDebuggerPresent", FunctionDescriptor.of(i)),
                  fn(linker, kernel32, "CheckRemoteDebuggerPresent", FunctionDescriptor.of(i, a, a)),
                  fn(linker, kernel32, "K32EnumProcessModulesEx", FunctionDescriptor.of(i, a, a, i, a, i)),
                  fn(linker, kernel32, "GetModuleFileNameW", FunctionDescriptor.of(i, a, a, i)),
                  fn(linker, kernel32, "K32GetModuleInformation", FunctionDescriptor.of(i, a, a, a, i)),
                  fn(linker, kernel32, "CreateToolhelp32Snapshot", FunctionDescriptor.of(a, i, i)),
                  fn(linker, kernel32, "Thread32First", FunctionDescriptor.of(i, a, a)),
                  fn(linker, kernel32, "Thread32Next", FunctionDescriptor.of(i, a, a)),
                  fn(linker, kernel32, "OpenThread", FunctionDescriptor.of(a, i, i, i)),
                  fn(linker, kernel32, "CloseHandle", FunctionDescriptor.of(i, a)),
                  fn(linker, ntdll, "NtQueryInformationThread", FunctionDescriptor.of(i, a, i, a, i, a)),
                  fn(linker, kernel32, "CreateFileW", FunctionDescriptor.of(a, a, i, i, a, i, i, a)),
                  winVerifyTrust(linker),
                  catalog(linker)
               );
            } catch (Throwable var5) {
               return null;
            }
         }
      }

      private static WindowsGuard.Catalog catalog(Linker linker) {
         try {
            SymbolLookup wintrust = SymbolLookup.libraryLookup("wintrust", Arena.global());
            ValueLayout i = ValueLayout.JAVA_INT;
            ValueLayout a = ValueLayout.ADDRESS;
            return new WindowsGuard.Catalog(
               fn(linker, wintrust, "CryptCATAdminAcquireContext2", FunctionDescriptor.of(i, a, a, a, a, i)),
               fn(linker, wintrust, "CryptCATAdminCalcHashFromFileHandle2", FunctionDescriptor.of(i, a, a, a, a, i)),
               fn(linker, wintrust, "CryptCATAdminEnumCatalogFromHash", FunctionDescriptor.of(a, a, a, i, i, a)),
               fn(linker, wintrust, "CryptCATCatalogInfoFromContext", FunctionDescriptor.of(i, a, a, i)),
               fn(linker, wintrust, "CryptCATAdminReleaseCatalogContext", FunctionDescriptor.of(i, a, a, i)),
               fn(linker, wintrust, "CryptCATAdminReleaseContext", FunctionDescriptor.of(i, a, i))
            );
         } catch (Throwable var4) {
            return null;
         }
      }

      private static MethodHandle fn(Linker linker, SymbolLookup lib, String name, FunctionDescriptor descriptor) {
         return linker.downcallHandle(lib.find(name).orElseThrow(), descriptor);
      }

      private static MethodHandle winVerifyTrust(Linker linker) {
         try {
            SymbolLookup wintrust = SymbolLookup.libraryLookup("wintrust", Arena.global());
            return fn(
               linker, wintrust, "WinVerifyTrust", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)
            );
         } catch (Throwable var2) {
            return null;
         }
      }
   }

   private record Catalog(
      MethodHandle acquireContext2,
      MethodHandle calcHash2,
      MethodHandle enumFromHash,
      MethodHandle infoFromContext,
      MethodHandle releaseCatalogContext,
      MethodHandle releaseContext
   ) {
   }
}
