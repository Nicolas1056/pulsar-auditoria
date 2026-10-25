package net.minecraft.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.io.IOException;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class MessageSerializer extends MessageToByteEncoder<Packet> {
   private static final Logger a = LogManager.getLogger();
   private static final Marker b = MarkerManager.getMarker("PACKET_SENT", NetworkManager.b);
   private final EnumPacketDirection c;

   public MessageSerializer(EnumPacketDirection var1) {
      this.c = var1;
   }

   protected void a(ChannelHandlerContext var1, Packet var2, ByteBuf var3) throws Exception {
      Integer var4 = ((EnumConnectionState)var1.channel().attr(NetworkManager.c).get()).a(this.c, var2);
      if (a.isDebugEnabled()) {
         a.debug(b, "OUT: [{}:{}] {}", new Object[]{var1.channel().attr(NetworkManager.c).get(), var4, var2.getClass().getName()});
      }

      if (var4 == null) {
         throw new IOException("Can't serialize unregistered packet");
      } else {
         PacketBuffer var5 = new PacketBuffer(var3);
         var5.b(var4);

         try {
            if (var2 instanceof S0CPacketSpawnPlayer) {
               var2 = var2;
            }

            var2.b(var5);
         } catch (Throwable var7) {
            a.error(var7);
         }
      }
   }
}
