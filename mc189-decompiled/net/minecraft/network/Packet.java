package net.minecraft.network;

import java.io.IOException;

public interface Packet<T extends INetHandler> {
   void a(PacketBuffer var1) throws IOException;

   void b(PacketBuffer var1) throws IOException;

   void a(T var1);
}
