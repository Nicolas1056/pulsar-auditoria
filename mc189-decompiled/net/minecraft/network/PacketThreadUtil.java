package net.minecraft.network;

import net.minecraft.util.IThreadListener;

public class PacketThreadUtil {
   public static <T extends INetHandler> void a(final Packet<T> var0, final T var1, IThreadListener var2) throws ThreadQuickExitException {
      if (!var2.aJ()) {
         var2.a(new Runnable() {
            @Override
            public void run() {
               var0.a(var1);
            }
         });
         throw ThreadQuickExitException.a;
      }
   }
}
