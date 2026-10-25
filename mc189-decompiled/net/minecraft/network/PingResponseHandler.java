package net.minecraft.network;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.net.InetSocketAddress;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PingResponseHandler extends ChannelInboundHandlerAdapter {
   private static final Logger a = LogManager.getLogger();
   private NetworkSystem b;

   public PingResponseHandler(NetworkSystem var1) {
      this.b = var1;
   }

   public void channelRead(ChannelHandlerContext var1, Object var2) throws Exception {
      ByteBuf var3 = (ByteBuf)var2;
      var3.markReaderIndex();
      boolean var4 = true;

      try {
         try {
            if (var3.readUnsignedByte() != 254) {
               return;
            }

            InetSocketAddress var5 = (InetSocketAddress)var1.channel().remoteAddress();
            MinecraftServer var6 = this.b.d();
            int var7 = var3.readableBytes();
            switch (var7) {
               case 0:
                  a.debug("Ping: (<1.3.x) from {}:{}", new Object[]{var5.getAddress(), var5.getPort()});
                  String var30 = String.format("%s§%d§%d", var6.am(), var6.I(), var6.J());
                  this.a(var1, this.a(var30));
                  break;
               case 1:
                  if (var3.readUnsignedByte() != 1) {
                     return;
                  }

                  a.debug("Ping: (1.4-1.5.x) from {}:{}", new Object[]{var5.getAddress(), var5.getPort()});
                  String var29 = String.format("§1\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", 127, var6.H(), var6.am(), var6.I(), var6.J());
                  this.a(var1, this.a(var29));
                  break;
               default:
                  boolean var8 = var3.readUnsignedByte() == 1;
                  var8 &= var3.readUnsignedByte() == 250;
                  var8 &= "MC|PingHost".equals(new String(var3.readBytes(var3.readShort() * 2).array(), Charsets.UTF_16BE));
                  int var9 = var3.readUnsignedShort();
                  var8 &= var3.readUnsignedByte() >= 73;
                  var8 &= 3 + var3.readBytes(var3.readShort() * 2).array().length + 4 == var9;
                  var8 &= var3.readInt() <= 65535;
                  var8 &= var3.readableBytes() == 0;
                  if (!var8) {
                     return;
                  }

                  a.debug("Ping: (1.6) from {}:{}", new Object[]{var5.getAddress(), var5.getPort()});
                  String var10 = String.format("§1\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", 127, var6.H(), var6.am(), var6.I(), var6.J());
                  ByteBuf var11 = this.a(var10);

                  try {
                     this.a(var1, var11);
                  } finally {
                     var11.release();
                  }
            }

            var3.release();
            var4 = false;
         } catch (RuntimeException var21) {
         }
      } finally {
         if (var4) {
            var3.resetReaderIndex();
            var1.channel().pipeline().remove("legacy_query");
            var1.fireChannelRead(var2);
         }
      }
   }

   private void a(ChannelHandlerContext var1, ByteBuf var2) {
      var1.pipeline().firstContext().writeAndFlush(var2).addListener(ChannelFutureListener.CLOSE);
   }

   private ByteBuf a(String var1) {
      ByteBuf var2 = Unpooled.buffer();
      var2.writeByte(255);
      char[] var3 = var1.toCharArray();
      var2.writeShort(var3.length);

      for (char var7 : var3) {
         var2.writeChar(var7);
      }

      return var2;
   }
}
