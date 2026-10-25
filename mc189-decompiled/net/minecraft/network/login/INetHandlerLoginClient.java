package net.minecraft.network.login;

import net.minecraft.network.INetHandler;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.network.login.server.S02PacketLoginSuccess;
import net.minecraft.network.login.server.S03PacketEnableCompression;

public interface INetHandlerLoginClient extends INetHandler {
   void a(S01PacketEncryptionRequest var1);

   void a(S02PacketLoginSuccess var1);

   void a(S00PacketDisconnect var1);

   void a(S03PacketEnableCompression var1);
}
