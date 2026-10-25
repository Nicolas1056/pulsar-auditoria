package net.minecraft.client.network;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OldServerPinger {
   private static final Splitter a = Splitter.on('\u0000').limit(6);
   private static final Logger b = LogManager.getLogger();
   private final List<NetworkManager> c = Collections.synchronizedList(Lists.newArrayList());

   public void a(final ServerData var1) throws UnknownHostException {
      ServerAddress var2 = ServerAddress.a(var1.b);
      final NetworkManager var3 = NetworkManager.a(InetAddress.getByName(var2.a()), var2.b(), false);
      this.c.add(var3);
      var1.d = "Pinging...";
      var1.e = -1L;
      var1.i = null;
      var3.a(new INetHandlerStatusClient() {
         private boolean d = false;
         private boolean e = false;
         private long f = 0L;

         @Override
         public void a(S00PacketServerInfo var1x) {
            if (this.e) {
               var3.a(new ChatComponentText("Received unrequested status"));
            } else {
               this.e = true;
               ServerStatusResponse var2x = var1x.a();
               if (var2x.a() != null) {
                  var1.d = var2x.a().d();
               } else {
                  var1.d = "";
               }

               if (var2x.c() != null) {
                  var1.g = var2x.c().a();
                  var1.f = var2x.c().b();
               } else {
                  var1.g = "Old";
                  var1.f = 0;
               }

               if (var2x.b() != null) {
                  var1.c = EnumChatFormatting.h + "" + var2x.b().b() + "" + EnumChatFormatting.i + "/" + EnumChatFormatting.h + var2x.b().a();
                  if (ArrayUtils.isNotEmpty(var2x.b().c())) {
                     StringBuilder var3x = new StringBuilder();

                     for (GameProfile var7 : var2x.b().c()) {
                        if (var3x.length() > 0) {
                           var3x.append("\n");
                        }

                        var3x.append(var7.getName());
                     }

                     if (var2x.b().c().length < var2x.b().b()) {
                        if (var3x.length() > 0) {
                           var3x.append("\n");
                        }

                        var3x.append("... and ").append(var2x.b().b() - var2x.b().c().length).append(" more ...");
                     }

                     var1.i = var3x.toString();
                  }
               } else {
                  var1.c = EnumChatFormatting.i + "???";
               }

               if (var2x.d() != null) {
                  String var8 = var2x.d();
                  if (var8.startsWith("data:image/png;base64,")) {
                     var1.a(var8.substring("data:image/png;base64,".length()));
                  } else {
                     OldServerPinger.b.error("Invalid server icon (unknown format)");
                  }
               } else {
                  var1.a(null);
               }

               this.f = Minecraft.J();
               var3.a(new C01PacketPing(this.f));
               this.d = true;
            }
         }

         @Override
         public void a(S01PacketPong var1x) {
            long var2x = this.f;
            long var4 = Minecraft.J();
            var1.e = var4 - var2x;
            var3.a(new ChatComponentText("Finished"));
         }

         @Override
         public void a(IChatComponent var1x) {
            if (!this.d) {
               OldServerPinger.b.error("Can't ping " + var1.b + ": " + var1x.c());
               var1.d = EnumChatFormatting.e + "Can't connect to server.";
               var1.c = "";
               OldServerPinger.this.b(var1);
            }
         }
      });

      try {
         var3.a(new C00Handshake(47, var2.a(), var2.b(), EnumConnectionState.c));
         var3.a(new C00PacketServerQuery());
      } catch (Throwable var5) {
         b.error(var5);
      }
   }

   private void b(final ServerData var1) {
      final ServerAddress var2 = ServerAddress.a(var1.b);
      ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)NetworkManager.d.c())).handler(new ChannelInitializer<Channel>() {
         protected void initChannel(Channel var1x) throws Exception {
            try {
               var1x.config().setOption(ChannelOption.TCP_NODELAY, true);
            } catch (ChannelException var3) {
            }

            var1x.pipeline().addLast(new ChannelHandler[]{new SimpleChannelInboundHandler<ByteBuf>() {
               public void channelActive(ChannelHandlerContext var1x) throws Exception {
                  super.channelActive(var1x);
                  ByteBuf var2x = Unpooled.buffer();

                  try {
                     var2x.writeByte(254);
                     var2x.writeByte(1);
                     var2x.writeByte(250);
                     char[] var3 = "MC|PingHost".toCharArray();
                     var2x.writeShort(var3.length);

                     for (char var7 : var3) {
                        var2x.writeChar(var7);
                     }

                     var2x.writeShort(7 + 2 * var2.a().length());
                     var2x.writeByte(127);
                     var3 = var2.a().toCharArray();
                     var2x.writeShort(var3.length);

                     for (char var15 : var3) {
                        var2x.writeChar(var15);
                     }

                     var2x.writeInt(var2.b());
                     var1x.channel().writeAndFlush(var2x).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                  } finally {
                     var2x.release();
                  }
               }

               protected void a(ChannelHandlerContext var1x, ByteBuf var2x) throws Exception {
                  short var3 = var2x.readUnsignedByte();
                  if (var3 == 255) {
                     String var4 = new String(var2x.readBytes(var2x.readShort() * 2).array(), Charsets.UTF_16BE);
                     String[] var5 = (String[])Iterables.toArray(OldServerPinger.a.split(var4), String.class);
                     if ("§1".equals(var5[0])) {
                        int var6 = MathHelper.a(var5[1], 0);
                        String var7 = var5[2];
                        String var8 = var5[3];
                        int var9 = MathHelper.a(var5[4], -1);
                        int var10 = MathHelper.a(var5[5], -1);
                        var1.f = -1;
                        var1.g = var7;
                        var1.d = var8;
                        var1.c = EnumChatFormatting.h + "" + var9 + "" + EnumChatFormatting.i + "/" + EnumChatFormatting.h + var10;
                     }
                  }

                  var1x.close();
               }

               public void exceptionCaught(ChannelHandlerContext var1x, Throwable var2x) throws Exception {
                  var1x.close();
               }
            }});
         }
      })).channel(NioSocketChannel.class)).connect(var2.a(), var2.b());
   }

   public void a() {
      synchronized (this.c) {
         Iterator var2 = this.c.iterator();

         while (var2.hasNext()) {
            NetworkManager var3 = (NetworkManager)var2.next();
            if (var3.g()) {
               var3.a();
            } else {
               var2.remove();
               var3.l();
            }
         }
      }
   }

   public void b() {
      synchronized (this.c) {
         Iterator var2 = this.c.iterator();

         while (var2.hasNext()) {
            NetworkManager var3 = (NetworkManager)var2.next();
            if (var3.g()) {
               var2.remove();
               var3.a(new ChatComponentText("Cancelled"));
            }
         }
      }
   }
}
