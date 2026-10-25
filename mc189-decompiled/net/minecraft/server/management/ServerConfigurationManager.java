package net.minecraft.server.management;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.network.play.server.S41PacketServerDifficulty;
import net.minecraft.network.play.server.S44PacketWorldBorder;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.border.IBorderListener;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.demo.DemoWorldManager;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ServerConfigurationManager {
   public static final File a = new File("banned-players.json");
   public static final File b = new File("banned-ips.json");
   public static final File c = new File("ops.json");
   public static final File d = new File("whitelist.json");
   private static final Logger f = LogManager.getLogger();
   private static final SimpleDateFormat g = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
   private final MinecraftServer h;
   private final List<EntityPlayerMP> i = Lists.newArrayList();
   private final Map<UUID, EntityPlayerMP> j = Maps.newHashMap();
   private final UserListBans k = new UserListBans(a);
   private final BanList l = new BanList(b);
   private final UserListOps m = new UserListOps(c);
   private final UserListWhitelist n = new UserListWhitelist(d);
   private final Map<UUID, StatisticsFile> o = Maps.newHashMap();
   private IPlayerFileData p;
   private boolean q;
   protected int e;
   private int r;
   private WorldSettings.GameType s;
   private boolean t;
   private int u;

   public ServerConfigurationManager(MinecraftServer var1) {
      this.h = var1;
      this.k.a(false);
      this.l.a(false);
      this.e = 8;
   }

   public void a(NetworkManager var1, EntityPlayerMP var2) {
      GameProfile var3 = var2.cd();
      PlayerProfileCache var4 = this.h.aF();
      GameProfile var5 = var4.a(var3.getId());
      String var6 = var5 == null ? var3.getName() : var5.getName();
      var4.a(var3);
      NBTTagCompound var7 = this.a(var2);
      var2.a(this.h.a(var2.am));
      var2.c.a((WorldServer)var2.o);
      String var8 = "local";
      if (var1.b() != null) {
         var8 = var1.b().toString();
      }

      f.info(var2.e_() + "[" + var8 + "] logged in with entity id " + var2.F() + " at (" + var2.s + ", " + var2.t + ", " + var2.u + ")");
      WorldServer var9 = this.h.a(var2.am);
      WorldInfo var10 = var9.P();
      BlockPos var11 = var9.M();
      this.a(var2, null, var9);
      NetHandlerPlayServer var12 = new NetHandlerPlayServer(this.h, var1, var2);
      var12.a(new S01PacketJoinGame(var2.F(), var2.c.b(), var10.t(), var9.t.q(), var9.aa(), this.p(), var10.u(), var9.Q().b("reducedDebugInfo")));
      var12.a(new S3FPacketCustomPayload("MC|Brand", new PacketBuffer(Unpooled.buffer()).a(this.c().getServerModName())));
      var12.a(new S41PacketServerDifficulty(var10.y(), var10.z()));
      var12.a(new S05PacketSpawnPosition(var11));
      var12.a(new S39PacketPlayerAbilities(var2.bA));
      var12.a(new S09PacketHeldItemChange(var2.bi.c));
      var2.A().d();
      var2.A().b(var2);
      this.a((ServerScoreboard)var9.Z(), var2);
      this.h.aH();
      ChatComponentTranslation var13;
      if (!var2.e_().equalsIgnoreCase(var6)) {
         var13 = new ChatComponentTranslation("multiplayer.player.joined.renamed", var2.f_(), var6);
      } else {
         var13 = new ChatComponentTranslation("multiplayer.player.joined", var2.f_());
      }

      var13.b().a(EnumChatFormatting.o);
      this.a(var13);
      this.c(var2);
      var12.a(var2.s, var2.t, var2.u, var2.y, var2.z);
      this.b(var2, var9);
      if (this.h.ab().length() > 0) {
         var2.a(this.h.ab(), this.h.ac());
      }

      for (PotionEffect var15 : var2.bl()) {
         var12.a(new S1DPacketEntityEffect(var2.F(), var15));
      }

      var2.g_();
      if (var7 != null && var7.b("Riding", 10)) {
         Entity var16 = EntityList.a(var7.m("Riding"), var9);
         if (var16 != null) {
            var16.n = true;
            var9.d(var16);
            var2.a(var16);
            var16.n = false;
         }
      }
   }

   protected void a(ServerScoreboard var1, EntityPlayerMP var2) {
      HashSet var3 = Sets.newHashSet();

      for (ScorePlayerTeam var5 : var1.g()) {
         var2.a.a(new S3EPacketTeams(var5, 0));
      }

      for (int var9 = 0; var9 < 19; var9++) {
         ScoreObjective var10 = var1.a(var9);
         if (var10 != null && !var3.contains(var10)) {
            for (Packet var8 : var1.d(var10)) {
               var2.a.a(var8);
            }

            var3.add(var10);
         }
      }
   }

   public void a(WorldServer[] var1) {
      this.p = var1[0].O().e();
      var1[0].af().a(new IBorderListener() {
         @Override
         public void a(WorldBorder var1, double var2) {
            ServerConfigurationManager.this.a(new S44PacketWorldBorder(var1, S44PacketWorldBorder.Action.a));
         }

         @Override
         public void a(WorldBorder var1, double var2, double var4, long var6) {
            ServerConfigurationManager.this.a(new S44PacketWorldBorder(var1, S44PacketWorldBorder.Action.b));
         }

         @Override
         public void a(WorldBorder var1, double var2, double var4) {
            ServerConfigurationManager.this.a(new S44PacketWorldBorder(var1, S44PacketWorldBorder.Action.c));
         }

         @Override
         public void a(WorldBorder var1, int var2) {
            ServerConfigurationManager.this.a(new S44PacketWorldBorder(var1, S44PacketWorldBorder.Action.e));
         }

         @Override
         public void b(WorldBorder var1, int var2) {
            ServerConfigurationManager.this.a(new S44PacketWorldBorder(var1, S44PacketWorldBorder.Action.f));
         }

         @Override
         public void b(WorldBorder var1, double var2) {
         }

         @Override
         public void c(WorldBorder var1, double var2) {
         }
      });
   }

   public void a(EntityPlayerMP var1, WorldServer var2) {
      WorldServer var3 = var1.u();
      if (var2 != null) {
         var2.t().c(var1);
      }

      var3.t().a(var1);
      var3.b.c((int)var1.s >> 4, (int)var1.u >> 4);
   }

   public int d() {
      return PlayerManager.b(this.s());
   }

   public NBTTagCompound a(EntityPlayerMP var1) {
      NBTTagCompound var2 = this.h.d[0].P().i();
      NBTTagCompound var3;
      if (var1.e_().equals(this.h.S()) && var2 != null) {
         var1.f(var2);
         var3 = var2;
         f.debug("loading single player");
      } else {
         var3 = this.p.b(var1);
      }

      return var3;
   }

   protected void b(EntityPlayerMP var1) {
      this.p.a(var1);
      StatisticsFile var2 = this.o.get(var1.aK());
      if (var2 != null) {
         var2.b();
      }
   }

   public void c(EntityPlayerMP var1) {
      this.i.add(var1);
      this.j.put(var1.aK(), var1);
      this.a(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.a, var1));
      WorldServer var2 = this.h.a(var1.am);
      var2.d(var1);
      this.a(var1, null);

      for (int var3 = 0; var3 < this.i.size(); var3++) {
         EntityPlayerMP var4 = this.i.get(var3);
         var1.a.a(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.a, var4));
      }
   }

   public void d(EntityPlayerMP var1) {
      var1.u().t().d(var1);
   }

   public void e(EntityPlayerMP var1) {
      var1.b(StatList.f);
      this.b(var1);
      WorldServer var2 = var1.u();
      if (var1.m != null) {
         var2.f(var1.m);
         f.debug("removing player mount");
      }

      var2.e(var1);
      var2.t().c(var1);
      this.i.remove(var1);
      UUID var3 = var1.aK();
      EntityPlayerMP var4 = this.j.get(var3);
      if (var4 == var1) {
         this.j.remove(var3);
         this.o.remove(var3);
      }

      this.a(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.e, var1));
   }

   public String a(SocketAddress var1, GameProfile var2) {
      if (this.k.a(var2)) {
         UserListBansEntry var5 = this.k.b(var2);
         String var6 = "You are banned from this server!\nReason: " + var5.d();
         if (var5.c() != null) {
            var6 = var6 + "\nYour ban will be removed on " + g.format(var5.c());
         }

         return var6;
      } else if (!this.e(var2)) {
         return "You are not white-listed on this server!";
      } else if (this.l.a(var1)) {
         IPBanEntry var3 = this.l.b(var1);
         String var4 = "Your IP address is banned from this server!\nReason: " + var3.d();
         if (var3.c() != null) {
            var4 = var4 + "\nYour ban will be removed on " + g.format(var3.c());
         }

         return var4;
      } else {
         return this.i.size() >= this.e && !this.f(var2) ? "The server is full!" : null;
      }
   }

   public EntityPlayerMP g(GameProfile var1) {
      UUID var2 = EntityPlayer.a(var1);
      ArrayList var3 = Lists.newArrayList();

      for (int var4 = 0; var4 < this.i.size(); var4++) {
         EntityPlayerMP var5 = this.i.get(var4);
         if (var5.aK().equals(var2)) {
            var3.add(var5);
         }
      }

      EntityPlayerMP var7 = this.j.get(var1.getId());
      if (var7 != null && !var3.contains(var7)) {
         var3.add(var7);
      }

      for (EntityPlayerMP var6 : var3) {
         var6.a.c("You logged in from another location");
      }

      Object var9;
      if (this.h.X()) {
         var9 = new DemoWorldManager(this.h.a(0));
      } else {
         var9 = new ItemInWorldManager(this.h.a(0));
      }

      return new EntityPlayerMP(this.h, this.h.a(0), var1, (ItemInWorldManager)var9);
   }

   public EntityPlayerMP a(EntityPlayerMP var1, int var2, boolean var3) {
      var1.u().s().b(var1);
      var1.u().s().b((Entity)var1);
      var1.u().t().c(var1);
      this.i.remove(var1);
      this.h.a(var1.am).f(var1);
      BlockPos var4 = var1.ch();
      boolean var5 = var1.ci();
      var1.am = var2;
      Object var6;
      if (this.h.X()) {
         var6 = new DemoWorldManager(this.h.a(var1.am));
      } else {
         var6 = new ItemInWorldManager(this.h.a(var1.am));
      }

      EntityPlayerMP var7 = new EntityPlayerMP(this.h, this.h.a(var1.am), var1.cd(), (ItemInWorldManager)var6);
      var7.a = var1.a;
      var7.a(var1, var3);
      var7.d(var1.F());
      var7.o(var1);
      WorldServer var8 = this.h.a(var1.am);
      this.a(var7, var1, var8);
      if (var4 != null) {
         BlockPos var9 = EntityPlayer.a(this.h.a(var1.am), var4, var5);
         if (var9 != null) {
            var7.b(var9.n() + 0.5F, var9.o() + 0.1F, var9.p() + 0.5F, 0.0F, 0.0F);
            var7.a(var4, var5);
         } else {
            var7.a.a(new S2BPacketChangeGameState(0, 0.0F));
         }
      }

      var8.b.c((int)var7.s >> 4, (int)var7.u >> 4);

      while (!var8.a(var7, var7.aR()).isEmpty() && var7.t < 256.0) {
         var7.b(var7.s, var7.t + 1.0, var7.u);
      }

      var7.a.a(new S07PacketRespawn(var7.am, var7.o.aa(), var7.o.P().u(), var7.c.b()));
      BlockPos var10 = var8.M();
      var7.a.a(var7.s, var7.t, var7.u, var7.y, var7.z);
      var7.a.a(new S05PacketSpawnPosition(var10));
      var7.a.a(new S1FPacketSetExperience(var7.bD, var7.bC, var7.bB));
      this.b(var7, var8);
      var8.t().a(var7);
      var8.d(var7);
      this.i.add(var7);
      this.j.put(var7.aK(), var7);
      var7.g_();
      var7.i(var7.bn());
      return var7;
   }

   public void a(EntityPlayerMP var1, int var2) {
      int var3 = var1.am;
      WorldServer var4 = this.h.a(var1.am);
      var1.am = var2;
      WorldServer var5 = this.h.a(var1.am);
      var1.a.a(new S07PacketRespawn(var1.am, var1.o.aa(), var1.o.P().u(), var1.c.b()));
      var4.f(var1);
      var1.I = false;
      this.a(var1, var3, var4, var5);
      this.a(var1, var4);
      var1.a.a(var1.s, var1.t, var1.u, var1.y, var1.z);
      var1.c.a(var5);
      this.b(var1, var5);
      this.f(var1);

      for (PotionEffect var7 : var1.bl()) {
         var1.a.a(new S1DPacketEntityEffect(var1.F(), var7));
      }
   }

   public void a(Entity var1, int var2, WorldServer var3, WorldServer var4) {
      double var5 = var1.s;
      double var7 = var1.u;
      double var9 = 8.0;
      float var11 = var1.y;
      var3.B.a("moving");
      if (var1.am == -1) {
         var5 = MathHelper.a(var5 / var9, var4.af().b() + 16.0, var4.af().d() - 16.0);
         var7 = MathHelper.a(var7 / var9, var4.af().c() + 16.0, var4.af().e() - 16.0);
         var1.b(var5, var1.t, var7, var1.y, var1.z);
         if (var1.ai()) {
            var3.a(var1, false);
         }
      } else if (var1.am == 0) {
         var5 = MathHelper.a(var5 * var9, var4.af().b() + 16.0, var4.af().d() - 16.0);
         var7 = MathHelper.a(var7 * var9, var4.af().c() + 16.0, var4.af().e() - 16.0);
         var1.b(var5, var1.t, var7, var1.y, var1.z);
         if (var1.ai()) {
            var3.a(var1, false);
         }
      } else {
         BlockPos var12;
         if (var2 == 1) {
            var12 = var4.M();
         } else {
            var12 = var4.m();
         }

         var5 = var12.n();
         var1.t = var12.o();
         var7 = var12.p();
         var1.b(var5, var1.t, var7, 90.0F, 0.0F);
         if (var1.ai()) {
            var3.a(var1, false);
         }
      }

      var3.B.b();
      if (var2 != 1) {
         var3.B.a("placing");
         var5 = MathHelper.a((int)var5, -29999872, 29999872);
         var7 = MathHelper.a((int)var7, -29999872, 29999872);
         if (var1.ai()) {
            var1.b(var5, var1.t, var7, var1.y, var1.z);
            var4.u().a(var1, var11);
            var4.d(var1);
            var4.a(var1, false);
         }

         var3.B.b();
      }

      var1.a(var4);
   }

   public void e() {
      if (++this.u > 600) {
         this.a(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.c, this.i));
         this.u = 0;
      }
   }

   public void a(Packet var1) {
      for (int var2 = 0; var2 < this.i.size(); var2++) {
         this.i.get(var2).a.a(var1);
      }
   }

   public void a(Packet var1, int var2) {
      for (int var3 = 0; var3 < this.i.size(); var3++) {
         EntityPlayerMP var4 = this.i.get(var3);
         if (var4.am == var2) {
            var4.a.a(var1);
         }
      }
   }

   public void a(EntityPlayer var1, IChatComponent var2) {
      Team var3 = var1.bO();
      if (var3 != null) {
         for (String var6 : var3.d()) {
            EntityPlayerMP var7 = this.a(var6);
            if (var7 != null && var7 != var1) {
               var7.a(var2);
            }
         }
      }
   }

   public void b(EntityPlayer var1, IChatComponent var2) {
      Team var3 = var1.bO();
      if (var3 == null) {
         this.a(var2);
      } else {
         for (int var4 = 0; var4 < this.i.size(); var4++) {
            EntityPlayerMP var5 = this.i.get(var4);
            if (var5.bO() != var3) {
               var5.a(var2);
            }
         }
      }
   }

   public String b(boolean var1) {
      String var2 = "";
      ArrayList var3 = Lists.newArrayList(this.i);

      for (int var4 = 0; var4 < var3.size(); var4++) {
         if (var4 > 0) {
            var2 = var2 + ", ";
         }

         var2 = var2 + ((EntityPlayerMP)var3.get(var4)).e_();
         if (var1) {
            var2 = var2 + " (" + ((EntityPlayerMP)var3.get(var4)).aK().toString() + ")";
         }
      }

      return var2;
   }

   public String[] f() {
      String[] var1 = new String[this.i.size()];

      for (int var2 = 0; var2 < this.i.size(); var2++) {
         var1[var2] = this.i.get(var2).e_();
      }

      return var1;
   }

   public GameProfile[] g() {
      GameProfile[] var1 = new GameProfile[this.i.size()];

      for (int var2 = 0; var2 < this.i.size(); var2++) {
         var1[var2] = this.i.get(var2).cd();
      }

      return var1;
   }

   public UserListBans h() {
      return this.k;
   }

   public BanList i() {
      return this.l;
   }

   public void a(GameProfile var1) {
      this.m.a(new UserListOpsEntry(var1, this.h.p(), this.m.b(var1)));
   }

   public void b(GameProfile var1) {
      this.m.c(var1);
   }

   public boolean e(GameProfile var1) {
      return !this.q || this.m.d(var1) || this.n.d(var1);
   }

   public boolean h(GameProfile var1) {
      return this.m.d(var1) || this.h.T() && this.h.d[0].P().v() && this.h.S().equalsIgnoreCase(var1.getName()) || this.t;
   }

   public EntityPlayerMP a(String var1) {
      for (EntityPlayerMP var3 : this.i) {
         if (var3.e_().equalsIgnoreCase(var1)) {
            return var3;
         }
      }

      return null;
   }

   public void a(double var1, double var3, double var5, double var7, int var9, Packet var10) {
      this.a(null, var1, var3, var5, var7, var9, var10);
   }

   public void a(EntityPlayer var1, double var2, double var4, double var6, double var8, int var10, Packet var11) {
      for (int var12 = 0; var12 < this.i.size(); var12++) {
         EntityPlayerMP var13 = this.i.get(var12);
         if (var13 != var1 && var13.am == var10) {
            double var14 = var2 - var13.s;
            double var16 = var4 - var13.t;
            double var18 = var6 - var13.u;
            if (var14 * var14 + var16 * var16 + var18 * var18 < var8 * var8) {
               var13.a.a(var11);
            }
         }
      }
   }

   public void j() {
      for (int var1 = 0; var1 < this.i.size(); var1++) {
         this.b(this.i.get(var1));
      }
   }

   public void d(GameProfile var1) {
      this.n.a(new UserListWhitelistEntry(var1));
   }

   public void c(GameProfile var1) {
      this.n.c(var1);
   }

   public UserListWhitelist k() {
      return this.n;
   }

   public String[] l() {
      return this.n.a();
   }

   public UserListOps m() {
      return this.m;
   }

   public String[] n() {
      return this.m.a();
   }

   public void a() {
   }

   public void b(EntityPlayerMP var1, WorldServer var2) {
      WorldBorder var3 = this.h.d[0].af();
      var1.a.a(new S44PacketWorldBorder(var3, S44PacketWorldBorder.Action.d));
      var1.a.a(new S03PacketTimeUpdate(var2.K(), var2.L(), var2.Q().b("doDaylightCycle")));
      if (var2.S()) {
         var1.a.a(new S2BPacketChangeGameState(1, 0.0F));
         var1.a.a(new S2BPacketChangeGameState(7, var2.j(1.0F)));
         var1.a.a(new S2BPacketChangeGameState(8, var2.h(1.0F)));
      }
   }

   public void f(EntityPlayerMP var1) {
      var1.a(var1.bj);
      var1.r();
      var1.a.a(new S09PacketHeldItemChange(var1.bi.c));
   }

   public int o() {
      return this.i.size();
   }

   public int p() {
      return this.e;
   }

   public String[] q() {
      return this.h.d[0].O().e().f();
   }

   public void a(boolean var1) {
      this.q = var1;
   }

   public List<EntityPlayerMP> b(String var1) {
      ArrayList var2 = Lists.newArrayList();

      for (EntityPlayerMP var4 : this.i) {
         if (var4.w().equals(var1)) {
            var2.add(var4);
         }
      }

      return var2;
   }

   public int s() {
      return this.r;
   }

   public MinecraftServer c() {
      return this.h;
   }

   public NBTTagCompound t() {
      return null;
   }

   public void a(WorldSettings.GameType var1) {
      this.s = var1;
   }

   private void a(EntityPlayerMP var1, EntityPlayerMP var2, World var3) {
      if (var2 != null) {
         var1.c.a(var2.c.b());
      } else if (this.s != null) {
         var1.c.a(this.s);
      }

      var1.c.b(var3.P().r());
   }

   public void c(boolean var1) {
      this.t = var1;
   }

   public void u() {
      for (int var1 = 0; var1 < this.i.size(); var1++) {
         this.i.get(var1).a.c("Server closed");
      }
   }

   public void a(IChatComponent var1, boolean var2) {
      this.h.a(var1);
      int var3 = var2 ? 1 : 0;
      this.a(new S02PacketChat(var1, (byte)var3));
   }

   public void a(IChatComponent var1) {
      this.a(var1, true);
   }

   public StatisticsFile a(EntityPlayer var1) {
      UUID var2 = var1.aK();
      StatisticsFile var3 = var2 == null ? null : this.o.get(var2);
      if (var3 == null) {
         File var4 = new File(this.h.a(0).O().b(), "stats");
         File var5 = new File(var4, var2.toString() + ".json");
         if (!var5.exists()) {
            File var6 = new File(var4, var1.e_() + ".json");
            if (var6.exists() && var6.isFile()) {
               var6.renameTo(var5);
            }
         }

         var3 = new StatisticsFile(this.h, var5);
         var3.a();
         this.o.put(var2, var3);
      }

      return var3;
   }

   public void a(int var1) {
      this.r = var1;
      if (this.h.d != null) {
         for (WorldServer var5 : this.h.d) {
            if (var5 != null) {
               var5.t().a(var1);
            }
         }
      }
   }

   public List<EntityPlayerMP> v() {
      return this.i;
   }

   public EntityPlayerMP a(UUID var1) {
      return this.j.get(var1);
   }

   public boolean f(GameProfile var1) {
      return false;
   }
}
