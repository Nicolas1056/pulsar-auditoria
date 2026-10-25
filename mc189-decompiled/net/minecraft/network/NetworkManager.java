package net.minecraft.network;

import com.google.common.collect.Queues;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.local.LocalEventLoopGroup;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.TimeoutException;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.crypto.SecretKey;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.CryptManager;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import net.minecraft.util.LazyLoadBase;
import net.minecraft.util.MessageDeserializer;
import net.minecraft.util.MessageDeserializer2;
import net.minecraft.util.MessageSerializer;
import net.minecraft.util.MessageSerializer2;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class NetworkManager extends SimpleChannelInboundHandler<Packet> {
   private static final Logger g = LogManager.getLogger();
   public static final Marker a = MarkerManager.getMarker("NETWORK");
   public static final Marker b = MarkerManager.getMarker("NETWORK_PACKETS", a);
   public static final AttributeKey<EnumConnectionState> c = AttributeKey.valueOf("protocol");
   public static final LazyLoadBase<NioEventLoopGroup> d = new LazyLoadBase<NioEventLoopGroup>() {
      protected NioEventLoopGroup a() {
         return new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Client IO #%d").setDaemon(true).build());
      }
   };
   public static final LazyLoadBase<EpollEventLoopGroup> e = new LazyLoadBase<EpollEventLoopGroup>() {
      protected EpollEventLoopGroup a() {
         return new EpollEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Epoll Client IO #%d").setDaemon(true).build());
      }
   };
   public static final LazyLoadBase<LocalEventLoopGroup> f = new LazyLoadBase<LocalEventLoopGroup>() {
      protected LocalEventLoopGroup a() {
         return new LocalEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Local Client IO #%d").setDaemon(true).build());
      }
   };
   private final EnumPacketDirection h;
   private final Queue<NetworkManager.InboundHandlerTuplePacketListener> i = Queues.newConcurrentLinkedQueue();
   private final ReentrantReadWriteLock j = new ReentrantReadWriteLock();
   private Channel k;
   private SocketAddress l;
   private INetHandler m;
   private IChatComponent n;
   private boolean o;
   private boolean p;

   public NetworkManager(EnumPacketDirection var1) {
      this.h = var1;
   }

   public void channelActive(ChannelHandlerContext var1) throws Exception {
      super.channelActive(var1);
      this.k = var1.channel();
      this.l = this.k.remoteAddress();

      try {
         this.a(EnumConnectionState.a);
      } catch (Throwable var3) {
         g.fatal(var3);
      }
   }

   public void a(EnumConnectionState var1) {
      this.k.attr(c).set(var1);
      this.k.config().setAutoRead(true);
      g.debug("Enabled auto read");
   }

   public void channelInactive(ChannelHandlerContext var1) throws Exception {
      this.a(new ChatComponentTranslation("disconnect.endOfStream"));
   }

   public void exceptionCaught(ChannelHandlerContext var1, Throwable var2) throws Exception {
      ChatComponentTranslation var3;
      if (var2 instanceof TimeoutException) {
         var3 = new ChatComponentTranslation("disconnect.timeout");
      } else {
         var3 = new ChatComponentTranslation("disconnect.genericReason", "Internal Exception: " + var2);
      }

      this.a(var3);
   }

   protected void a(ChannelHandlerContext var1, Packet var2) throws Exception {
      if (this.k.isOpen()) {
         try {
            var2.a(this.m);
         } catch (ThreadQuickExitException var4) {
         }
      }
   }

   public void a(INetHandler var1) {
      Validate.notNull(var1, "packetListener", new Object[0]);
      g.debug("Set listener of {} to {}", new Object[]{this, var1});
      this.m = var1;
   }

   public void a(Packet var1) {
      if (this.g()) {
         this.m();
         this.a(var1, null);
      } else {
         this.j.writeLock().lock();

         try {
            this.i.add(new NetworkManager.InboundHandlerTuplePacketListener(var1, null));
         } finally {
            this.j.writeLock().unlock();
         }
      }
   }

   public void a(Packet var1, GenericFutureListener<? extends Future<? super Void>> var2, GenericFutureListener<? extends Future<? super Void>>... var3) {
      if (this.g()) {
         this.m();
         this.a(var1, (GenericFutureListener<? extends Future<? super Void>>[])ArrayUtils.add(var3, 0, var2));
      } else {
         this.j.writeLock().lock();

         try {
            this.i
               .add(
                  new NetworkManager.InboundHandlerTuplePacketListener(
                     var1, (GenericFutureListener<? extends Future<? super Void>>[])ArrayUtils.add(var3, 0, var2)
                  )
               );
         } finally {
            this.j.writeLock().unlock();
         }
      }
   }

   private void a(final Packet var1, final GenericFutureListener<? extends Future<? super Void>>[] var2) {
      final EnumConnectionState var3 = EnumConnectionState.a(var1);
      final EnumConnectionState var4 = (EnumConnectionState)this.k.attr(c).get();
      if (var4 != var3) {
         g.debug("Disabled auto read");
         this.k.config().setAutoRead(false);
      }

      if (this.k.eventLoop().inEventLoop()) {
         if (var3 != var4) {
            this.a(var3);
         }

         ChannelFuture var5 = this.k.writeAndFlush(var1);
         if (var2 != null) {
            var5.addListeners(var2);
         }

         var5.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
      } else {
         this.k.eventLoop().execute(new Runnable() {
            @Override
            public void run() {
               if (var3 != var4) {
                  NetworkManager.this.a(var3);
               }

               ChannelFuture var1x = NetworkManager.this.k.writeAndFlush(var1);
               if (var2 != null) {
                  var1x.addListeners(var2);
               }

               var1x.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
            }
         });
      }
   }

   private void m() {
      if (this.k != null && this.k.isOpen()) {
         this.j.readLock().lock();

         try {
            while (!this.i.isEmpty()) {
               NetworkManager.InboundHandlerTuplePacketListener var1 = this.i.poll();
               this.a(var1.a, var1.b);
            }
         } finally {
            this.j.readLock().unlock();
         }
      }
   }

   public void a() {
      this.m();
      if (this.m instanceof ITickable) {
         ((ITickable)this.m).c();
      }

      this.k.flush();
   }

   public SocketAddress b() {
      return this.l;
   }

   public void a(IChatComponent var1) {
      if (this.k.isOpen()) {
         this.k.close().awaitUninterruptibly();
         this.n = var1;
      }
   }

   public boolean c() {
      return this.k instanceof LocalChannel || this.k instanceof LocalServerChannel;
   }

   public static NetworkManager a(InetAddress var0, int var1, boolean var2) {
      final NetworkManager var3 = new NetworkManager(EnumPacketDirection.b);
      Class<EpollSocketChannel> var4;
      LazyLoadBase var5;
      if (Epoll.isAvailable() && var2) {
         var4 = EpollSocketChannel.class;
         var5 = e;
      } else {
         var4 = NioSocketChannel.class;
         var5 = d;
      }

      ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)var5.c()))
               .handler(
                  new ChannelInitializer<Channel>() {
                     protected void initChannel(Channel var1) throws Exception {
                        try {
                           var1.config().setOption(ChannelOption.TCP_NODELAY, true);
                        } catch (ChannelException var3x) {
                        }

                        var1.pipeline()
                           .addLast("timeout", new ReadTimeoutHandler(30))
                           .addLast("splitter", new MessageDeserializer2())
                           .addLast("decoder", new MessageDeserializer(EnumPacketDirection.b))
                           .addLast("prepender", new MessageSerializer2())
                           .addLast("encoder", new MessageSerializer(EnumPacketDirection.a))
                           .addLast("packet_handler", var3);
                     }
                  }
               ))
            .channel(var4))
         .connect(var0, var1)
         .syncUninterruptibly();
      return var3;
   }

   public static NetworkManager a(SocketAddress var0) {
      final NetworkManager var1 = new NetworkManager(EnumPacketDirection.b);
      ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)f.c())).handler(new ChannelInitializer<Channel>() {
         protected void initChannel(Channel var1x) throws Exception {
            var1x.pipeline().addLast("packet_handler", var1);
         }
      })).channel(LocalChannel.class)).connect(var0).syncUninterruptibly();
      return var1;
   }

   public void a(SecretKey var1) {
      this.o = true;
      this.k.pipeline().addBefore("splitter", "decrypt", new NettyEncryptingDecoder(CryptManager.a(2, var1)));
      this.k.pipeline().addBefore("prepender", "encrypt", new NettyEncryptingEncoder(CryptManager.a(1, var1)));
   }

   public boolean f() {
      return this.o;
   }

   public boolean g() {
      return this.k != null && this.k.isOpen();
   }

   public boolean h() {
      return this.k == null;
   }

   public INetHandler i() {
      return this.m;
   }

   public IChatComponent j() {
      return this.n;
   }

   public void k() {
      this.k.config().setAutoRead(false);
   }

   public void a(int var1) {
      if (var1 >= 0) {
         if (this.k.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
            ((NettyCompressionDecoder)this.k.pipeline().get("decompress")).a(var1);
         } else {
            this.k.pipeline().addBefore("decoder", "decompress", new NettyCompressionDecoder(var1));
         }

         if (this.k.pipeline().get("compress") instanceof NettyCompressionEncoder) {
            ((NettyCompressionEncoder)this.k.pipeline().get("decompress")).a(var1);
         } else {
            this.k.pipeline().addBefore("encoder", "compress", new NettyCompressionEncoder(var1));
         }
      } else {
         if (this.k.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
            this.k.pipeline().remove("decompress");
         }

         if (this.k.pipeline().get("compress") instanceof NettyCompressionEncoder) {
            this.k.pipeline().remove("compress");
         }
      }
   }

   public void l() {
      if (this.k != null && !this.k.isOpen()) {
         if (!this.p) {
            this.p = true;
            if (this.j() != null) {
               this.i().a(this.j());
            } else if (this.i() != null) {
               this.i().a(new ChatComponentText("Disconnected"));
            }
         } else {
            g.warn("handleDisconnection() called twice");
         }
      }
   }

   static class InboundHandlerTuplePacketListener {
      private final Packet a;
      private final GenericFutureListener<? extends Future<? super Void>>[] b;

      public InboundHandlerTuplePacketListener(Packet var1, GenericFutureListener<? extends Future<? super Void>>... var2) {
         this.a = var1;
         this.b = var2;
      }
   }
}
