package net.minecraft.network;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.local.LocalEventLoopGroup;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import net.minecraft.client.network.NetHandlerHandshakeMemory;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.NetHandlerHandshakeTCP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.LazyLoadBase;
import net.minecraft.util.MessageDeserializer;
import net.minecraft.util.MessageDeserializer2;
import net.minecraft.util.MessageSerializer;
import net.minecraft.util.MessageSerializer2;
import net.minecraft.util.ReportedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetworkSystem {
   private static final Logger e = LogManager.getLogger();
   public static final LazyLoadBase<NioEventLoopGroup> a = new LazyLoadBase<NioEventLoopGroup>() {
      protected NioEventLoopGroup a() {
         return new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Server IO #%d").setDaemon(true).build());
      }
   };
   public static final LazyLoadBase<EpollEventLoopGroup> b = new LazyLoadBase<EpollEventLoopGroup>() {
      protected EpollEventLoopGroup a() {
         return new EpollEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Epoll Server IO #%d").setDaemon(true).build());
      }
   };
   public static final LazyLoadBase<LocalEventLoopGroup> c = new LazyLoadBase<LocalEventLoopGroup>() {
      protected LocalEventLoopGroup a() {
         return new LocalEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Local Server IO #%d").setDaemon(true).build());
      }
   };
   private final MinecraftServer f;
   public volatile boolean d;
   private final List<ChannelFuture> g = Collections.synchronizedList(Lists.newArrayList());
   private final List<NetworkManager> h = Collections.synchronizedList(Lists.newArrayList());

   public NetworkSystem(MinecraftServer var1) {
      this.f = var1;
      this.d = true;
   }

   public void a(InetAddress var1, int var2) throws IOException {
      synchronized (this.g) {
         Class<EpollServerSocketChannel> var4;
         LazyLoadBase var5;
         if (Epoll.isAvailable() && this.f.ai()) {
            var4 = EpollServerSocketChannel.class;
            var5 = b;
            e.info("Using epoll channel type");
         } else {
            var4 = NioServerSocketChannel.class;
            var5 = a;
            e.info("Using default channel type");
         }

         this.g
            .add(
               ((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel(var4))
                     .childHandler(
                        new ChannelInitializer<Channel>() {
                           protected void initChannel(Channel var1) throws Exception {
                              try {
                                 var1.config().setOption(ChannelOption.TCP_NODELAY, true);
                              } catch (ChannelException var3) {
                              }

                              var1.pipeline()
                                 .addLast("timeout", new ReadTimeoutHandler(30))
                                 .addLast("legacy_query", new PingResponseHandler(NetworkSystem.this))
                                 .addLast("splitter", new MessageDeserializer2())
                                 .addLast("decoder", new MessageDeserializer(EnumPacketDirection.a))
                                 .addLast("prepender", new MessageSerializer2())
                                 .addLast("encoder", new MessageSerializer(EnumPacketDirection.b));
                              NetworkManager var2x = new NetworkManager(EnumPacketDirection.a);
                              NetworkSystem.this.h.add(var2x);
                              var1.pipeline().addLast("packet_handler", var2x);
                              var2x.a(new NetHandlerHandshakeTCP(NetworkSystem.this.f, var2x));
                           }
                        }
                     )
                     .group((EventLoopGroup)var5.c())
                     .localAddress(var1, var2))
                  .bind()
                  .syncUninterruptibly()
            );
      }
   }

   public SocketAddress a() {
      ChannelFuture var1;
      synchronized (this.g) {
         var1 = ((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel(LocalServerChannel.class)).childHandler(new ChannelInitializer<Channel>() {
            protected void initChannel(Channel var1) throws Exception {
               NetworkManager var2 = new NetworkManager(EnumPacketDirection.a);
               var2.a(new NetHandlerHandshakeMemory(NetworkSystem.this.f, var2));
               NetworkSystem.this.h.add(var2);
               var1.pipeline().addLast("packet_handler", var2);
            }
         }).group((EventLoopGroup)a.c()).localAddress(LocalAddress.ANY)).bind().syncUninterruptibly();
         this.g.add(var1);
      }

      return var1.channel().localAddress();
   }

   public void b() {
      this.d = false;

      for (ChannelFuture var2 : this.g) {
         try {
            var2.channel().close().sync();
         } catch (InterruptedException var4) {
            e.error("Interrupted whilst closing channel");
         }
      }
   }

   public void c() {
      synchronized (this.h) {
         Iterator var2 = this.h.iterator();

         while (var2.hasNext()) {
            final NetworkManager var3 = (NetworkManager)var2.next();
            if (!var3.h()) {
               if (!var3.g()) {
                  var2.remove();
                  var3.l();
               } else {
                  try {
                     var3.a();
                  } catch (Exception var8) {
                     if (var3.c()) {
                        CrashReport var10 = CrashReport.a(var8, "Ticking memory connection");
                        CrashReportCategory var6 = var10.a("Ticking connection");
                        var6.a("Connection", new Callable<String>() {
                           public String a() throws Exception {
                              return var3.toString();
                           }
                        });
                        throw new ReportedException(var10);
                     }

                     e.warn("Failed to handle packet for " + var3.b(), var8);
                     final ChatComponentText var5 = new ChatComponentText("Internal server error");
                     var3.a(new S40PacketDisconnect(var5), new GenericFutureListener<Future<? super Void>>() {
                        public void operationComplete(Future<? super Void> var1) throws Exception {
                           var3.a(var5);
                        }
                     });
                     var3.k();
                  }
               }
            }
         }
      }
   }

   public MinecraftServer d() {
      return this.f;
   }
}
