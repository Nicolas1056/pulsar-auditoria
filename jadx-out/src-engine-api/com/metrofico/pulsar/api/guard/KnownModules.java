package com.metrofico.pulsar.api.guard;

import java.util.List;
import java.util.Locale;
import java.util.Set;

public final class KnownModules {
   static final List<String> NAMES = List.of(
      "ntdll.dll",
      "kernel32.dll",
      "kernelbase.dll",
      "user32.dll",
      "gdi32.dll",
      "advapi32.dll",
      "ws2_32.dll",
      "msvcrt.dll",
      "msvcp140.dll",
      "vcruntime140.dll",
      "ole32.dll",
      "oleaut32.dll",
      "shell32.dll",
      "d3d9.dll",
      "d3d11.dll",
      "d3d12.dll",
      "dxgi.dll",
      "opengl32.dll",
      "glu32.dll",
      "winmm.dll",
      "dinput8.dll",
      "xinput1_4.dll",
      "dsound.dll",
      "mmdevapi.dll",
      "audioses.dll",
      "wintrust.dll",
      "nvoglv64.dll",
      "atio6axx.dll",
      "ig9icd64.dll",
      "igd10iumd64.dll",
      "amdxc64.dll",
      "vulkan-1.dll",
      "java.dll",
      "net.dll",
      "nio.dll",
      "zip.dll",
      "awt.dll",
      "fontmanager.dll",
      "jvm.dll",
      "jli.dll",
      "lwjgl.dll",
      "lwjgl_opengl.dll",
      "lwjgl_glfw.dll",
      "lwjgl_stb.dll",
      "openal.dll",
      "openal32.dll",
      "discord_hook.dll",
      "discord-rpc.dll",
      "gameoverlayrenderer64.dll",
      "gameoverlayrenderer.dll",
      "nvspcap64.dll",
      "rtsshooks64.dll",
      "rtsshooks.dll",
      "msiafterburner.dll",
      "obs-vulkan.dll",
      "libc.so",
      "libm.so",
      "libpthread.so",
      "libdl.so",
      "librt.so",
      "libstdc++.so",
      "libgcc_s.so",
      "libgl.so",
      "libgl.so.1",
      "libgles.so",
      "libegl.so",
      "libvulkan.so",
      "libvulkan.so.1",
      "libasound.so",
      "libpulse.so",
      "libjack.so",
      "libopenal.so",
      "liblwjgl.so",
      "liblwjgl_opengl.so",
      "liblwjgl_glfw.so",
      "liblwjgl_stb.so",
      "libjvm.so",
      "libjli.so",
      "libnet.so",
      "libnio.so",
      "libsystem.dylib",
      "libobjc.a.dylib",
      "libdyld.dylib",
      "libc++.1.dylib",
      "libz.1.dylib",
      "liblwjgl.dylib",
      "liblwjgl_opengl.dylib",
      "liblwjgl_glfw.dylib",
      "liblwjgl_stb.dylib"
   );
   private static final Set<String> KNOWN = Set.copyOf(NAMES);

   private KnownModules() {
   }

   public static boolean isKnown(String name) {
      String n = name.toLowerCase(Locale.ROOT);
      if (KNOWN.contains(n)) {
         return true;
      } else {
         int so = n.indexOf(".so.");
         return so > 0 && KNOWN.contains(n.substring(0, so + 3));
      }
   }
}
