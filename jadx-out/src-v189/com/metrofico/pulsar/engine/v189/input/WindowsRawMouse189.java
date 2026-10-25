package com.metrofico.pulsar.engine.v189.input;

import com.metrofico.pulsar.api.input.RawMouse;
import com.metrofico.pulsar.bootstrap.BootstrapLog;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;

final class WindowsRawMouse189 {
   private static final int WM_INPUT = 255;
   private static final int RID_INPUT = 268435459;
   private static final int RIM_TYPEMOUSE = 0;
   private static final int RIDEV_INPUTSINK = 256;
   private static final int MOUSE_MOVE_ABSOLUTE = 1;
   private static final int HEADER = 24;
   private static final int RAW_SIZE = 48;
   private static final int MSG_SIZE = 48;

   private WindowsRawMouse189() {
   }

   static void start() {
      Thread t = new Thread(WindowsRawMouse189::run, "pulsar-rawmouse");
      t.setDaemon(true);
      t.start();
   }

   private static void run() {
      try {
         WindowsRawMouse189.User32 user32 = (WindowsRawMouse189.User32)Native.loadLibrary("user32", WindowsRawMouse189.User32.class);
         Pointer hwnd = user32.CreateWindowExW(0, new WString("Message"), new WString("pulsar-rawmouse"), 0, 0, 0, 0, 0, new Pointer(-3L), null, null, null);
         if (hwnd == null) {
            BootstrapLog.info("raw input: no message window, using the cursor");
            return;
         }

         Memory device = new Memory(16L);
         device.setShort(0L, (short)1);
         device.setShort(2L, (short)2);
         device.setInt(4L, 256);
         device.setPointer(8L, hwnd);
         if (!user32.RegisterRawInputDevices(device, 1, 16)) {
            BootstrapLog.info("raw input: RegisterRawInputDevices failed, using the cursor");
            return;
         }

         RawMouse.available(true);
         BootstrapLog.info("raw input: Windows Raw Input active");
         Memory msg = new Memory(48L);
         Memory raw = new Memory(48L);
         IntByReference size = new IntByReference();

         while (user32.GetMessageW(msg, null, 0, 0) > 0) {
            int message = msg.getInt(8L);
            Pointer wParam = msg.getPointer(16L);
            Pointer lParam = msg.getPointer(24L);
            if (message == 255) {
               size.setValue(48);
               if (user32.GetRawInputData(lParam, 268435459, raw, size, 24) > 0 && raw.getInt(0L) == 0 && (raw.getShort(24L) & 1) == 0) {
                  RawMouse.add(raw.getInt(36L), raw.getInt(40L), System.nanoTime());
               }
            }

            user32.DefWindowProcW(msg.getPointer(0L), message, wParam, lParam);
         }
      } catch (Throwable var9) {
         RawMouse.available(false);
         BootstrapLog.info("raw input unavailable: " + var9);
      }
   }

   interface User32 extends StdCallLibrary {
      Pointer CreateWindowExW(
         int var1, WString var2, WString var3, int var4, int var5, int var6, int var7, int var8, Pointer var9, Pointer var10, Pointer var11, Pointer var12
      );

      boolean RegisterRawInputDevices(Pointer var1, int var2, int var3);

      int GetMessageW(Pointer var1, Pointer var2, int var3, int var4);

      Pointer DefWindowProcW(Pointer var1, int var2, Pointer var3, Pointer var4);

      int GetRawInputData(Pointer var1, int var2, Pointer var3, IntByReference var4, int var5);
   }
}
