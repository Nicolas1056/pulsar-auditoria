package net.minecraft.network;

import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.google.common.util.concurrent.Futures;
import io.netty.buffer.Unpooled;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.Callable;
import net.minecraft.block.material.Material;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.network.play.client.C11PacketEnchantItem;
import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S3APacketTabComplete;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListBansEntry;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldServer;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerPlayServer implements INetHandlerPlayServer, ITickable {
   private static final Logger c = LogManager.getLogger();
   public final NetworkManager a;
   private final MinecraftServer d;
   public EntityPlayerMP b;
   private int e;
   private int f;
   private int g;
   private boolean h;
   private int i;
   private long j;
   private long k;
   private int l;
   private int m;
   private IntHashMap<Short> n = new IntHashMap<>();
   private double o;
   private double p;
   private double q;
   private boolean r = true;

   public NetHandlerPlayServer(MinecraftServer var1, NetworkManager var2, EntityPlayerMP var3) {
      this.d = var1;
      this.a = var2;
      var2.a(this);
      this.b = var3;
      var3.a = this;
   }

   @Override
   public void c() {
      this.h = false;
      this.e++;
      this.d.c.a("keepAlive");
      if (this.e - this.k > 40L) {
         this.k = this.e;
         this.j = this.d();
         this.i = (int)this.j;
         this.a(new S00PacketKeepAlive(this.i));
      }

      this.d.c.b();
      if (this.l > 0) {
         this.l--;
      }

      if (this.m > 0) {
         this.m--;
      }

      if (this.b.D() > 0L && this.d.aA() > 0 && MinecraftServer.az() - this.b.D() > this.d.aA() * 1000 * 60) {
         this.c("You have been idle for too long!");
      }
   }

   public NetworkManager a() {
      return this.a;
   }

   public void c(String var1) {
      final ChatComponentText var2 = new ChatComponentText(var1);
      this.a.a(new S40PacketDisconnect(var2), new GenericFutureListener<Future<? super Void>>() {
         public void operationComplete(Future<? super Void> var1) throws Exception {
            NetHandlerPlayServer.this.a.a(var2);
         }
      });
      this.a.k();
      Futures.getUnchecked(this.d.a(new Runnable() {
         @Override
         public void run() {
            NetHandlerPlayServer.this.a.l();
         }
      }));
   }

   @Override
   public void a(C0CPacketInput var1) {
      PacketThreadUtil.a(var1, this, this.b.u());
      this.b.a(var1.a(), var1.b(), var1.c(), var1.d());
   }

   private boolean b(C03PacketPlayer var1) {
      return !Doubles.isFinite(var1.a())
         || !Doubles.isFinite(var1.b())
         || !Doubles.isFinite(var1.c())
         || !Floats.isFinite(var1.e())
         || !Floats.isFinite(var1.d());
   }

   @Override
   public void a(C03PacketPlayer var1) {
      PacketThreadUtil.a(var1, this, this.b.u());
      if (this.b(var1)) {
         this.c("Invalid move packet received");
      } else {
         WorldServer var2 = this.d.a(this.b.am);
         this.h = true;
         if (!this.b.i) {
            double var3 = this.b.s;
            double var5 = this.b.t;
            double var7 = this.b.u;
            double var9 = 0.0;
            double var11 = var1.a() - this.o;
            double var13 = var1.b() - this.p;
            double var15 = var1.c() - this.q;
            if (var1.g()) {
               var9 = var11 * var11 + var13 * var13 + var15 * var15;
               if (!this.r && var9 < 0.25) {
                  this.r = true;
               }
            }

            if (this.r) {
               this.f = this.e;
               if (this.b.m != null) {
                  float var43 = this.b.y;
                  float var18 = this.b.z;
                  this.b.m.al();
                  double var44 = this.b.s;
                  double var45 = this.b.t;
                  double var46 = this.b.u;
                  if (var1.h()) {
                     var43 = var1.d();
                     var18 = var1.e();
                  }

                  this.b.C = var1.f();
                  this.b.l();
                  this.b.a(var44, var45, var46, var43, var18);
                  if (this.b.m != null) {
                     this.b.m.al();
                  }

                  this.d.ap().d(this.b);
                  if (this.b.m != null) {
                     if (var9 > 4.0) {
                        Entity var47 = this.b.m;
                        this.b.a.a(new S18PacketEntityTeleport(var47));
                        this.a(this.b.s, this.b.t, this.b.u, this.b.y, this.b.z);
                     }

                     this.b.m.ai = true;
                  }

                  if (this.r) {
                     this.o = this.b.s;
                     this.p = this.b.t;
                     this.q = this.b.u;
                  }

                  var2.g(this.b);
                  return;
               }

               if (this.b.bJ()) {
                  this.b.l();
                  this.b.a(this.o, this.p, this.q, this.b.y, this.b.z);
                  var2.g(this.b);
                  return;
               }

               double var17 = this.b.t;
               this.o = this.b.s;
               this.p = this.b.t;
               this.q = this.b.u;
               double var19 = this.b.s;
               double var21 = this.b.t;
               double var23 = this.b.u;
               float var25 = this.b.y;
               float var26 = this.b.z;
               if (var1.g() && var1.b() == -999.0) {
                  var1.a(false);
               }

               if (var1.g()) {
                  var19 = var1.a();
                  var21 = var1.b();
                  var23 = var1.c();
                  if (Math.abs(var1.a()) > 3.0E7 || Math.abs(var1.c()) > 3.0E7) {
                     this.c("Illegal position");
                     return;
                  }
               }

               if (var1.h()) {
                  var25 = var1.d();
                  var26 = var1.e();
               }

               this.b.l();
               this.b.a(this.o, this.p, this.q, var25, var26);
               if (!this.r) {
                  return;
               }

               double var27 = var19 - this.b.s;
               double var29 = var21 - this.b.t;
               double var31 = var23 - this.b.u;
               double var33 = this.b.v * this.b.v + this.b.w * this.b.w + this.b.x * this.b.x;
               double var35 = var27 * var27 + var29 * var29 + var31 * var31;
               if (var35 - var33 > 100.0 && (!this.d.T() || !this.d.S().equals(this.b.e_()))) {
                  c.warn(this.b.e_() + " moved too quickly! " + var27 + "," + var29 + "," + var31 + " (" + var27 + ", " + var29 + ", " + var31 + ")");
                  this.a(this.o, this.p, this.q, this.b.y, this.b.z);
                  return;
               }

               float var37 = 0.0625F;
               boolean var38 = var2.a(this.b, this.b.aR().d(var37, var37, var37)).isEmpty();
               if (this.b.C && !var1.f() && var29 > 0.0) {
                  this.b.bF();
               }

               this.b.d(var27, var29, var31);
               this.b.C = var1.f();
               var27 = var19 - this.b.s;
               var29 = var21 - this.b.t;
               if (var29 > -0.5 || var29 < 0.5) {
                  var29 = 0.0;
               }

               var31 = var23 - this.b.u;
               var35 = var27 * var27 + var29 * var29 + var31 * var31;
               boolean var41 = false;
               if (var35 > 0.0625 && !this.b.bJ() && !this.b.c.d()) {
                  var41 = true;
                  c.warn(this.b.e_() + " moved wrongly!");
               }

               this.b.a(var19, var21, var23, var25, var26);
               this.b.k(this.b.s - var3, this.b.t - var5, this.b.u - var7);
               if (!this.b.T) {
                  boolean var42 = var2.a(this.b, this.b.aR().d(var37, var37, var37)).isEmpty();
                  if (var38 && (var41 || !var42) && !this.b.bJ()) {
                     this.a(this.o, this.p, this.q, var25, var26);
                     return;
                  }
               }

               AxisAlignedBB var52 = this.b.aR().b(var37, var37, var37).a(0.0, -0.55, 0.0);
               if (this.d.ak() || this.b.bA.c || var2.c(var52)) {
                  this.g = 0;
               } else if (var29 >= -0.03125) {
                  this.g++;
                  if (this.g > 80) {
                     c.warn(this.b.e_() + " was kicked for floating too long!");
                     this.c("Flying is not enabled on this server");
                     return;
                  }
               }

               this.b.C = var1.f();
               this.d.ap().d(this.b);
               this.b.a(this.b.t - var17, var1.f());
            } else if (this.e - this.f > 20) {
               this.a(this.o, this.p, this.q, this.b.y, this.b.z);
            }
         }
      }
   }

   public void a(double var1, double var3, double var5, float var7, float var8) {
      this.a(var1, var3, var5, var7, var8, Collections.emptySet());
   }

   public void a(double var1, double var3, double var5, float var7, float var8, Set<S08PacketPlayerPosLook.EnumFlags> var9) {
      this.r = false;
      this.o = var1;
      this.p = var3;
      this.q = var5;
      if (var9.contains(S08PacketPlayerPosLook.EnumFlags.a)) {
         this.o = this.o + this.b.s;
      }

      if (var9.contains(S08PacketPlayerPosLook.EnumFlags.b)) {
         this.p = this.p + this.b.t;
      }

      if (var9.contains(S08PacketPlayerPosLook.EnumFlags.c)) {
         this.q = this.q + this.b.u;
      }

      float var10 = var7;
      float var11 = var8;
      if (var9.contains(S08PacketPlayerPosLook.EnumFlags.d)) {
         var10 = var7 + this.b.y;
      }

      if (var9.contains(S08PacketPlayerPosLook.EnumFlags.e)) {
         var11 = var8 + this.b.z;
      }

      this.b.a(this.o, this.p, this.q, var10, var11);
      this.b.a.a(new S08PacketPlayerPosLook(var1, var3, var5, var7, var8, var9));
   }

   @Override
   public void a(C07PacketPlayerDigging var1) {
      PacketThreadUtil.a(var1, this, this.b.u());
      WorldServer var2 = this.d.a(this.b.am);
      BlockPos var3 = var1.a();
      this.b.z();
      switch (var1.c()) {
         case e:
            if (!this.b.v()) {
               this.b.a(false);
            }

            return;
         case d:
            if (!this.b.v()) {
               this.b.a(true);
            }

            return;
         case f:
            this.b.bU();
            return;
         case a:
         case b:
         case c:
            double var4 = this.b.s - (var3.n() + 0.5);
            double var6 = this.b.t - (var3.o() + 0.5) + 1.5;
            double var8 = this.b.u - (var3.p() + 0.5);
            double var10 = var4 * var4 + var6 * var6 + var8 * var8;
            if (var10 > 36.0) {
               return;
            } else if (var3.o() >= this.d.an()) {
               return;
            } else {
               if (var1.c() == C07PacketPlayerDigging.Action.a) {
                  if (!this.d.a(var2, var3, this.b) && var2.af().a(var3)) {
                     this.b.c.a(var3, var1.b());
                  } else {
                     this.b.a.a(new S23PacketBlockChange(var2, var3));
                  }
               } else {
                  if (var1.c() == C07PacketPlayerDigging.Action.c) {
                     this.b.c.a(var3);
                  } else if (var1.c() == C07PacketPlayerDigging.Action.b) {
                     this.b.c.e();
                  }

                  if (var2.p(var3).c().t() != Material.a) {
                     this.b.a.a(new S23PacketBlockChange(var2, var3));
                  }
               }

               return;
            }
         default:
            throw new IllegalArgumentException("Invalid player action");
      }
   }

   @Override
   public void a(C08PacketPlayerBlockPlacement var1) {
      PacketThreadUtil.a(var1, this, this.b.u());
      WorldServer var2 = this.d.a(this.b.am);
      ItemStack var3 = this.b.bi.h();
      boolean var4 = false;
      BlockPos var5 = var1.a();
      EnumFacing var6 = EnumFacing.a(var1.b());
      this.b.z();
      if (var1.b() == 255) {
         if (var3 == null) {
            return;
         }

         this.b.c.a(this.b, var2, var3);
      } else if (var5.o() < this.d.an() - 1 || var6 != EnumFacing.b && var5.o() < this.d.an()) {
         if (this.r && this.b.e(var5.n() + 0.5, var5.o() + 0.5, var5.p() + 0.5) < 64.0 && !this.d.a(var2, var5, this.b) && var2.af().a(var5)) {
            this.b.c.a(this.b, var2, var3, var5, var6, var1.d(), var1.e(), var1.f());
         }

         var4 = true;
      } else {
         ChatComponentTranslation var7 = new ChatComponentTranslation("build.tooHigh", this.d.an());
         var7.b().a(EnumChatFormatting.m);
         this.b.a.a(new S02PacketChat(var7));
         var4 = true;
      }

      if (var4) {
         this.b.a.a(new S23PacketBlockChange(var2, var5));
         this.b.a.a(new S23PacketBlockChange(var2, var5.a(var6)));
      }

      var3 = this.b.bi.h();
      if (var3 != null && var3.b == 0) {
         this.b.bi.a[this.b.bi.c] = null;
         var3 = null;
      }

      if (var3 == null || var3.l() == 0) {
         this.b.g = true;
         this.b.bi.a[this.b.bi.c] = ItemStack.b(this.b.bi.a[this.b.bi.c]);
         Slot var9 = this.b.bk.a(this.b.bi, this.b.bi.c);
         this.b.bk.b();
         this.b.g = false;
         if (!ItemStack.b(this.b.bi.h(), var1.c())) {
            this.a(new S2FPacketSetSlot(this.b.bk.d, var9.e, this.b.bi.h()));
         }
      }
   }

   @Override
   public void a(C18PacketSpectate var1) {
      PacketThreadUtil.a(var1, this, this.b.u());
      if (this.b.v()) {
         Entity var2 = null;

         for (WorldServer var6 : this.d.d) {
            if (var6 != null) {
               var2 = var1.a(var6);
               if (var2 != null) {
                  break;
               }
            }
         }

         if (var2 != null) {
            this.b.e(this.b);
            this.b.a(null);
            if (var2.o != this.b.o) {
               WorldServer var7 = this.b.u();
               WorldServer var8 = (WorldServer)var2.o;
               this.b.am = var2.am;
               this.a(new S07PacketRespawn(this.b.am, var7.aa(), var7.P().u(), this.b.c.b()));
               var7.f(this.b);
               this.b.I = false;
               this.b.b(var2.s, var2.t, var2.u, var2.y, var2.z);
               if (this.b.ai()) {
                  var7.a(this.b, false);
                  var8.d(this.b);
                  var8.a(this.b, false);
               }

               this.b.a(var8);
               this.d.ap().a(this.b, var7);
               this.b.a(var2.s, var2.t, var2.u);
               this.b.c.a(var8);
               this.d.ap().b(this.b, var8);
               this.d.ap().f(this.b);
            } else {
               this.b.a(var2.s, var2.t, var2.u);
            }
         }
      }
   }

   @Override
   public void a(C19PacketResourcePackStatus var1) {
   }

   @Override
   public void a(IChatComponent var1) {
      c.info(this.b.e_() + " lost connection: " + var1);
      this.d.aH();
      ChatComponentTranslation var2 = new ChatComponentTranslation("multiplayer.player.left", this.b.f_());
      var2.b().a(EnumChatFormatting.o);
      this.d.ap().a(var2);
      this.b.q();
      this.d.ap().e(this.b);
      if (this.d.T() && this.b.e_().equals(this.d.S())) {
         c.info("Stopping singleplayer server as player logged out");
         this.d.w();
      }
   }

   public void a(final Packet var1) {
      if (var1 instanceof S02PacketChat) {
         S02PacketChat var2 = (S02PacketChat)var1;
         EntityPlayer.EnumChatVisibility var3 = this.b.y();
         if (var3 == EntityPlayer.EnumChatVisibility.c) {
            return;
         }

         if (var3 == EntityPlayer.EnumChatVisibility.b && !var2.b()) {
            return;
         }
      }

      try {
         this.a.a(var1);
      } catch (Throwable var5) {
         CrashReport var6 = CrashReport.a(var5, "Sending packet");
         CrashReportCategory var4 = var6.a("Packet being sent");
         var4.a("Packet class", new Callable<String>() {
            public String a() throws Exception {
               return var1.getClass().getCanonicalName();
            }
         });
         throw new ReportedException(var6);
      }
   }

   @Override
   public void a(C09PacketHeldItemChange var1) {
      PacketThreadUtil.a(var1, this, this.b.u());
      if (var1.a() >= 0 && var1.a() < InventoryPlayer.i()) {
         this.b.bi.c = var1.a();
         this.b.z();
      } else {
         c.warn(this.b.e_() + " tried to set an invalid carried item");
      }
   }

   @Override
   public void a(C01PacketChatMessage var1) {
      PacketThreadUtil.a(var1, this, this.b.u());
      if (this.b.y() == EntityPlayer.EnumChatVisibility.c) {
         ChatComponentTranslation var5 = new ChatComponentTranslation("chat.cannotSend");
         var5.b().a(EnumChatFormatting.m);
         this.a(new S02PacketChat(var5));
      } else {
         this.b.z();
         String var2 = var1.a();
         var2 = StringUtils.normalizeSpace(var2);

         for (int var3 = 0; var3 < var2.length(); var3++) {
            if (!ChatAllowedCharacters.a(var2.charAt(var3))) {
               this.c("Illegal characters in chat");
               return;
            }
         }

         if (var2.startsWith("/")) {
            this.d(var2);
         } else {
            ChatComponentTranslation var6 = new ChatComponentTranslation("chat.type.text", this.b.f_(), var2);
            this.d.ap().a(var6, false);
         }

         this.l += 20;
         if (this.l > 200 && !this.d.ap().h(this.b.cd())) {
            this.c("disconnect.spam");
         }
      }
   }

   private void d(String var1) {
      this.d.P().a(this.b, var1);
   }

   @Override
   public void a(C0APacketAnimation var1) {
      PacketThreadUtil.a(var1, this, this.b.u());
      this.b.z();
      this.b.bw();
   }

   @Override
   public void a(C0BPacketEntityAction var1) {
      PacketThreadUtil.a(var1, this, this.b.u());
      this.b.z();
      switch (var1.b()) {
         case a:
            this.b.c(true);
            break;
         case b:
            this.b.c(false);
            break;
         case d:
            this.b.d(true);
            break;
         case e:
            this.b.d(false);
            break;
         case c:
            this.b.a(false, true, true);
            this.r = false;
            break;
         case f:
            if (this.b.m instanceof EntityHorse) {
               ((EntityHorse)this.b.m).v(var1.c());
            }
            break;
         case g:
            if (this.b.m instanceof EntityHorse) {
               ((EntityHorse)this.b.m).g(this.b);
            }
            break;
         default:
            throw new IllegalArgumentException("Invalid client command!");
      }
   }

   @Override
   public void a(C02PacketUseEntity var1) {
      PacketThreadUtil.a(var1, this, this.b.u());
      WorldServer var2 = this.d.a(this.b.am);
      Entity var3 = var1.a(var2);
      this.b.z();
      if (var3 != null) {
         boolean var4 = this.b.t(var3);
         double var5 = 36.0;
         if (!var4) {
            var5 = 9.0;
         }

         if (this.b.h(var3) < var5) {
            if (var1.a() == C02PacketUseEntity.Action.a) {
               this.b.u(var3);
            } else if (var1.a() == C02PacketUseEntity.Action.c) {
               var3.a(this.b, var1.b());
            } else if (var1.a() == C02PacketUseEntity.Action.b) {
               if (var3 instanceof EntityItem || var3 instanceof EntityXPOrb || var3 instanceof EntityArrow || var3 == this.b) {
                  this.c("Attempting to attack an invalid entity");
                  this.d.f("Player " + this.b.e_() + " tried to attack an invalid entity");
                  return;
               }

               this.b.f(var3);
            }
         }
      }
   }

   @Override
   public void a(C16PacketClientStatus var1) {
      PacketThreadUtil.a(var1, this, this.b.u());
      this.b.z();
      C16PacketClientStatus.EnumState var2 = var1.a();
      switch (var2) {
         case a:
            if (this.b.i) {
               this.b = this.d.ap().a(this.b, 0, true);
            } else if (this.b.u().P().t()) {
               if (this.d.T() && this.b.e_().equals(this.d.S())) {
                  this.b.a.c("You have died. Game over, man, it's game over!");
                  this.d.aa();
               } else {
                  UserListBansEntry var3 = new UserListBansEntry(this.b.cd(), null, "(You just lost the game)", null, "Death in Hardcore");
                  this.d.ap().h().a(var3);
                  this.b.a.c("You have died. Game over, man, it's game over!");
               }
            } else {
               if (this.b.bn() > 0.0F) {
                  return;
               }

               this.b = this.d.ap().a(this.b, 0, false);
            }
            break;
         case b:
            this.b.A().a(this.b);
            break;
         case c:
            this.b.b(AchievementList.f);
      }
   }

   @Override
   public void a(C0DPacketCloseWindow var1) {
      PacketThreadUtil.a(var1, this, this.b.u());
      this.b.p();
   }

   @Override
   public void a(C0EPacketClickWindow var1) {
      PacketThreadUtil.a(var1, this, this.b.u());
      this.b.z();
      if (this.b.bk.d == var1.a() && this.b.bk.c(this.b)) {
         if (this.b.v()) {
            ArrayList var2 = Lists.newArrayList();

            for (int var3 = 0; var3 < this.b.bk.c.size(); var3++) {
               var2.add(this.b.bk.c.get(var3).d());
            }

            this.b.a(this.b.bk, var2);
         } else {
            ItemStack var5 = this.b.bk.a(var1.b(), var1.c(), var1.f(), this.b);
            if (ItemStack.b(var1.e(), var5)) {
               this.b.a.a(new S32PacketConfirmTransaction(var1.a(), var1.d(), true));
               this.b.g = true;
               this.b.bk.b();
               this.b.o();
               this.b.g = false;
            } else {
               this.n.a(this.b.bk.d, var1.d());
               this.b.a.a(new S32PacketConfirmTransaction(var1.a(), var1.d(), false));
               this.b.bk.a(this.b, false);
               ArrayList var6 = Lists.newArrayList();

               for (int var4 = 0; var4 < this.b.bk.c.size(); var4++) {
                  var6.add(this.b.bk.c.get(var4).d());
               }

               this.b.a(this.b.bk, var6);
            }
         }
      }
   }

   @Override
   public void a(C11PacketEnchantItem var1) {
      PacketThreadUtil.a(var1, this, this.b.u());
      this.b.z();
      if (this.b.bk.d == var1.a() && this.b.bk.c(this.b) && !this.b.v()) {
         this.b.bk.a(this.b, var1.b());
         this.b.bk.b();
      }
   }

   @Override
   public void a(C10PacketCreativeInventoryAction var1) {
      PacketThreadUtil.a(var1, this, this.b.u());
      if (this.b.c.d()) {
         boolean var2 = var1.a() < 0;
         ItemStack var3 = var1.b();
         if (var3 != null && var3.n() && var3.o().b("BlockEntityTag", 10)) {
            NBTTagCompound var4 = var3.o().m("BlockEntityTag");
            if (var4.c("x") && var4.c("y") && var4.c("z")) {
               BlockPos var5 = new BlockPos(var4.f("x"), var4.f("y"), var4.f("z"));
               TileEntity var6 = this.b.o.s(var5);
               if (var6 != null) {
                  NBTTagCompound var7 = new NBTTagCompound();
                  var6.b(var7);
                  var7.o("x");
                  var7.o("y");
                  var7.o("z");
                  var3.a("BlockEntityTag", var7);
               }
            }
         }

         boolean var8 = var1.a() >= 1 && var1.a() < 36 + InventoryPlayer.i();
         boolean var9 = var3 == null || var3.b() != null;
         boolean var10 = var3 == null || var3.i() >= 0 && var3.b <= 64 && var3.b > 0;
         if (var8 && var9 && var10) {
            if (var3 == null) {
               this.b.bj.a(var1.a(), null);
            } else {
               this.b.bj.a(var1.a(), var3);
            }

            this.b.bj.a(this.b, true);
         } else if (var2 && var9 && var10 && this.m < 200) {
            this.m += 20;
            EntityItem var11 = this.b.a(var3, true);
            if (var11 != null) {
               var11.j();
            }
         }
      }
   }

   @Override
   public void a(C0FPacketConfirmTransaction var1) {
      PacketThreadUtil.a(var1, this, this.b.u());
      Short var2 = this.n.a(this.b.bk.d);
      if (var2 != null && var1.b() == var2 && this.b.bk.d == var1.a() && !this.b.bk.c(this.b) && !this.b.v()) {
         this.b.bk.a(this.b, true);
      }
   }

   @Override
   public void a(C12PacketUpdateSign var1) {
      PacketThreadUtil.a(var1, this, this.b.u());
      this.b.z();
      WorldServer var2 = this.d.a(this.b.am);
      BlockPos var3 = var1.a();
      if (var2.e(var3)) {
         TileEntity var4 = var2.s(var3);
         if (!(var4 instanceof TileEntitySign)) {
            return;
         }

         TileEntitySign var5 = (TileEntitySign)var4;
         if (!var5.b() || var5.c() != this.b) {
            this.d.f("Player " + this.b.e_() + " just tried to change non-editable sign");
            return;
         }

         IChatComponent[] var6 = var1.b();

         for (int var7 = 0; var7 < var6.length; var7++) {
            var5.a[var7] = new ChatComponentText(EnumChatFormatting.a(var6[var7].c()));
         }

         var5.p_();
         var2.h(var3);
      }
   }

   @Override
   public void a(C00PacketKeepAlive var1) {
      if (var1.a() == this.i) {
         int var2 = (int)(this.d() - this.j);
         this.b.h = (this.b.h * 3 + var2) / 4;
      }
   }

   private long d() {
      return System.nanoTime() / 1000000L;
   }

   @Override
   public void a(C13PacketPlayerAbilities var1) {
      PacketThreadUtil.a(var1, this, this.b.u());
      this.b.bA.b = var1.b() && this.b.bA.c;
   }

   @Override
   public void a(C14PacketTabComplete var1) {
      PacketThreadUtil.a(var1, this, this.b.u());
      ArrayList var2 = Lists.newArrayList();

      for (String var4 : this.d.a(this.b, var1.a(), var1.b())) {
         var2.add(var4);
      }

      this.b.a.a(new S3APacketTabComplete(var2.toArray(new String[var2.size()])));
   }

   @Override
   public void a(C15PacketClientSettings var1) {
      PacketThreadUtil.a(var1, this, this.b.u());
      this.b.a(var1);
   }

   @Override
   public void a(C17PacketCustomPayload var1) {
      PacketThreadUtil.a(var1, this, this.b.u());
      if ("MC|BEdit".equals(var1.a())) {
         PacketBuffer var44 = new PacketBuffer(Unpooled.wrappedBuffer(var1.b()));

         try {
            ItemStack var49 = var44.i();
            if (var49 == null) {
               return;
            }

            if (!ItemWritableBook.b(var49.o())) {
               throw new IOException("Invalid book tag!");
            }

            ItemStack var52 = this.b.bi.h();
            if (var52 != null) {
               if (var49.b() == Items.bM && var49.b() == var52.b()) {
                  var52.a("pages", var49.o().c("pages", 8));
               }

               return;
            }
         } catch (Exception var36) {
            c.error("Couldn't handle book info", var36);
            return;
         } finally {
            var44.release();
         }

         return;
      } else if ("MC|BSign".equals(var1.a())) {
         PacketBuffer var43 = new PacketBuffer(Unpooled.wrappedBuffer(var1.b()));

         try {
            ItemStack var48 = var43.i();
            if (var48 == null) {
               return;
            }

            if (!ItemEditableBook.b(var48.o())) {
               throw new IOException("Invalid book tag!");
            }

            ItemStack var51 = this.b.bi.h();
            if (var51 != null) {
               if (var48.b() == Items.bN && var51.b() == Items.bM) {
                  var51.a("author", new NBTTagString(this.b.e_()));
                  var51.a("title", new NBTTagString(var48.o().j("title")));
                  var51.a("pages", var48.o().c("pages", 8));
                  var51.a(Items.bN);
               }

               return;
            }
         } catch (Exception var38) {
            c.error("Couldn't sign book", var38);
            return;
         } finally {
            var43.release();
         }

         return;
      } else if ("MC|TrSel".equals(var1.a())) {
         try {
            int var2 = var1.b().readInt();
            Container var3 = this.b.bk;
            if (var3 instanceof ContainerMerchant) {
               ((ContainerMerchant)var3).d(var2);
            }
         } catch (Exception var35) {
            c.error("Couldn't select trade", var35);
         }
      } else if ("MC|AdvCdm".equals(var1.a())) {
         if (!this.d.al()) {
            this.b.a(new ChatComponentTranslation("advMode.notEnabled"));
         } else if (this.b.a(2, "") && this.b.bA.d) {
            PacketBuffer var40 = var1.b();

            try {
               byte var45 = var40.readByte();
               CommandBlockLogic var4 = null;
               if (var45 == 0) {
                  TileEntity var5 = this.b.o.s(new BlockPos(var40.readInt(), var40.readInt(), var40.readInt()));
                  if (var5 instanceof TileEntityCommandBlock) {
                     var4 = ((TileEntityCommandBlock)var5).b();
                  }
               } else if (var45 == 1) {
                  Entity var53 = this.b.o.a(var40.readInt());
                  if (var53 instanceof EntityMinecartCommandBlock) {
                     var4 = ((EntityMinecartCommandBlock)var53).j();
                  }
               }

               String var54 = var40.c(var40.readableBytes());
               boolean var6 = var40.readBoolean();
               if (var4 != null) {
                  var4.a(var54);
                  var4.a(var6);
                  if (!var6) {
                     var4.b(null);
                  }

                  var4.h();
                  this.b.a(new ChatComponentTranslation("advMode.setCommand.success", var54));
               }
            } catch (Exception var33) {
               c.error("Couldn't set command block", var33);
            } finally {
               var40.release();
            }
         } else {
            this.b.a(new ChatComponentTranslation("advMode.notAllowed"));
         }
      } else if ("MC|Beacon".equals(var1.a())) {
         if (this.b.bk instanceof ContainerBeacon) {
            try {
               PacketBuffer var41 = var1.b();
               int var46 = var41.readInt();
               int var50 = var41.readInt();
               ContainerBeacon var55 = (ContainerBeacon)this.b.bk;
               Slot var56 = var55.a(0);
               if (var56.e()) {
                  var56.a(1);
                  IInventory var7 = var55.e();
                  var7.b(1, var46);
                  var7.b(2, var50);
                  var7.p_();
               }
            } catch (Exception var32) {
               c.error("Couldn't set beacon", var32);
            }
         }
      } else if ("MC|ItemName".equals(var1.a()) && this.b.bk instanceof ContainerRepair) {
         ContainerRepair var42 = (ContainerRepair)this.b.bk;
         if (var1.b() != null && var1.b().readableBytes() >= 1) {
            String var47 = ChatAllowedCharacters.a(var1.b().c(32767));
            if (var47.length() <= 30) {
               var42.a(var47);
            }
         } else {
            var42.a("");
         }
      }
   }
}
