package net.minecraft.entity.player;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryMerchant;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMapBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.network.play.server.S0APacketUseBed;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S26PacketMapChunkBulk;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.network.play.server.S31PacketWindowProperty;
import net.minecraft.network.play.server.S36PacketSignEditorOpen;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.network.play.server.S42PacketCombatEvent;
import net.minecraft.network.play.server.S43PacketCamera;
import net.minecraft.network.play.server.S48PacketResourcePackSend;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.JsonSerializableSet;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityPlayerMP extends EntityPlayer implements ICrafting {
   private static final Logger bH = LogManager.getLogger();
   private String bI = "en_US";
   public NetHandlerPlayServer a;
   public final MinecraftServer b;
   public final ItemInWorldManager c;
   public double d;
   public double e;
   public final List<ChunkCoordIntPair> f = Lists.newLinkedList();
   private final List<Integer> bJ = Lists.newLinkedList();
   private final StatisticsFile bK;
   private float bL = Float.MIN_VALUE;
   private float bM = -1.0E8F;
   private int bN = -99999999;
   private boolean bO = true;
   private int bP = -99999999;
   private int bQ = 60;
   private EntityPlayer.EnumChatVisibility bR;
   private boolean bS = true;
   private long bT = System.currentTimeMillis();
   private Entity bU = null;
   private int bV;
   public boolean g;
   public int h;
   public boolean i;

   public EntityPlayerMP(MinecraftServer var1, WorldServer var2, GameProfile var3, ItemInWorldManager var4) {
      super(var2, var3);
      var4.b = this;
      this.c = var4;
      BlockPos var5 = var2.M();
      if (!var2.t.o() && var2.P().r() != WorldSettings.GameType.d) {
         int var6 = Math.max(5, var1.aw() - 6);
         int var7 = MathHelper.c(var2.af().b(var5.n(), var5.p()));
         if (var7 < var6) {
            var6 = var7;
         }

         if (var7 <= 1) {
            var6 = 1;
         }

         var5 = var2.r(var5.a(this.V.nextInt(var6 * 2) - var6, 0, this.V.nextInt(var6 * 2) - var6));
      }

      this.b = var1;
      this.bK = var1.ap().a((EntityPlayer)this);
      this.S = 0.0F;
      this.a(var5, 0.0F, 0.0F);

      while (!var2.a(this, this.aR()).isEmpty() && this.t < 255.0) {
         this.b(this.s, this.t + 1.0, this.u);
      }
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      if (var1.b("playerGameType", 99)) {
         if (MinecraftServer.N().ax()) {
            this.c.a(MinecraftServer.N().m());
         } else {
            this.c.a(WorldSettings.GameType.a(var1.f("playerGameType")));
         }
      }
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("playerGameType", this.c.b().a());
   }

   @Override
   public void a(int var1) {
      super.a(var1);
      this.bP = -1;
   }

   @Override
   public void b(int var1) {
      super.b(var1);
      this.bP = -1;
   }

   public void g_() {
      this.bk.a((ICrafting)this);
   }

   @Override
   public void h_() {
      super.h_();
      this.a.a(new S42PacketCombatEvent(this.bs(), S42PacketCombatEvent.Event.a));
   }

   @Override
   public void j() {
      super.j();
      this.a.a(new S42PacketCombatEvent(this.bs(), S42PacketCombatEvent.Event.b));
   }

   @Override
   public void t_() {
      this.c.a();
      this.bQ--;
      if (this.Z > 0) {
         this.Z--;
      }

      this.bk.b();
      if (!this.o.D && !this.bk.a((EntityPlayer)this)) {
         this.n();
         this.bk = this.bj;
      }

      while (!this.bJ.isEmpty()) {
         int var1 = Math.min(this.bJ.size(), Integer.MAX_VALUE);
         int[] var2 = new int[var1];
         Iterator var3 = this.bJ.iterator();
         int var4 = 0;

         while (var3.hasNext() && var4 < var1) {
            var2[var4++] = (Integer)var3.next();
            var3.remove();
         }

         this.a.a(new S13PacketDestroyEntities(var2));
      }

      if (!this.f.isEmpty()) {
         ArrayList var6 = Lists.newArrayList();
         Iterator var8 = this.f.iterator();
         ArrayList var9 = Lists.newArrayList();

         while (var8.hasNext() && var6.size() < 10) {
            ChunkCoordIntPair var10 = (ChunkCoordIntPair)var8.next();
            if (var10 != null) {
               if (this.o.e(new BlockPos(var10.a << 4, 0, var10.b << 4))) {
                  Chunk var5 = this.o.a(var10.a, var10.b);
                  if (var5.i()) {
                     var6.add(var5);
                     var9.addAll(((WorldServer)this.o).a(var10.a * 16, 0, var10.b * 16, var10.a * 16 + 16, 256, var10.b * 16 + 16));
                     var8.remove();
                  }
               }
            } else {
               var8.remove();
            }
         }

         if (!var6.isEmpty()) {
            if (var6.size() == 1) {
               this.a.a(new S21PacketChunkData((Chunk)var6.get(0), true, 65535));
            } else {
               this.a.a(new S26PacketMapChunkBulk(var6));
            }

            for (TileEntity var13 : var9) {
               this.a(var13);
            }

            for (Chunk var14 : var6) {
               this.u().s().a(this, var14);
            }
         }
      }

      Entity var7 = this.C();
      if (var7 != this) {
         if (!var7.ai()) {
            this.e(this);
         } else {
            this.a(var7.s, var7.t, var7.u, var7.y, var7.z);
            this.b.ap().d(this);
            if (this.av()) {
               this.e(this);
            }
         }
      }
   }

   public void l() {
      try {
         super.t_();

         for (int var1 = 0; var1 < this.bi.o_(); var1++) {
            ItemStack var6 = this.bi.a(var1);
            if (var6 != null && var6.b().f()) {
               Packet var8 = ((ItemMapBase)var6.b()).c(var6, this.o, this);
               if (var8 != null) {
                  this.a.a(var8);
               }
            }
         }

         if (this.bn() != this.bM || this.bN != this.bl.a() || this.bl.e() == 0.0F != this.bO) {
            this.a.a(new S06PacketUpdateHealth(this.bn(), this.bl.a(), this.bl.e()));
            this.bM = this.bn();
            this.bN = this.bl.a();
            this.bO = this.bl.e() == 0.0F;
         }

         if (this.bn() + this.bN() != this.bL) {
            this.bL = this.bn() + this.bN();

            for (ScoreObjective var9 : this.cp().a(IScoreObjectiveCriteria.g)) {
               this.cp().c(this.e_(), var9).a(Arrays.asList(this));
            }
         }

         if (this.bC != this.bP) {
            this.bP = this.bC;
            this.a.a(new S1FPacketSetExperience(this.bD, this.bC, this.bB));
         }

         if (this.W % 20 * 5 == 0 && !this.A().a(AchievementList.L)) {
            this.i_();
         }
      } catch (Throwable var4) {
         CrashReport var2 = CrashReport.a(var4, "Ticking player");
         CrashReportCategory var3 = var2.a("Player being ticked");
         this.a(var3);
         throw new ReportedException(var2);
      }
   }

   protected void i_() {
      BiomeGenBase var1 = this.o.b(new BlockPos(MathHelper.c(this.s), 0, MathHelper.c(this.u)));
      String var2 = var1.ah;
      JsonSerializableSet var3 = this.A().b(AchievementList.L);
      if (var3 == null) {
         var3 = this.A().a(AchievementList.L, new JsonSerializableSet());
      }

      var3.add(var2);
      if (this.A().b(AchievementList.L) && var3.size() >= BiomeGenBase.n.size()) {
         HashSet var4 = Sets.newHashSet(BiomeGenBase.n);

         for (String var6 : var3) {
            Iterator var7 = var4.iterator();

            while (var7.hasNext()) {
               BiomeGenBase var8 = (BiomeGenBase)var7.next();
               if (var8.ah.equals(var6)) {
                  var7.remove();
               }
            }

            if (var4.isEmpty()) {
               break;
            }
         }

         if (var4.isEmpty()) {
            this.b(AchievementList.L);
         }
      }
   }

   @Override
   public void a(DamageSource var1) {
      if (this.o.Q().b("showDeathMessages")) {
         Team var2 = this.bO();
         if (var2 == null || var2.j() == Team.EnumVisible.a) {
            this.b.ap().a(this.bs().b());
         } else if (var2.j() == Team.EnumVisible.c) {
            this.b.ap().a(this, this.bs().b());
         } else if (var2.j() == Team.EnumVisible.d) {
            this.b.ap().b(this, this.bs().b());
         }
      }

      if (!this.o.Q().b("keepInventory")) {
         this.bi.n();
      }

      for (ScoreObjective var4 : this.o.Z().a(IScoreObjectiveCriteria.d)) {
         Score var5 = this.cp().c(this.e_(), var4);
         var5.a();
      }

      EntityLivingBase var7 = this.bt();
      if (var7 != null) {
         EntityList.EntityEggInfo var8 = EntityList.a.get(EntityList.a(var7));
         if (var8 != null) {
            this.b(var8.e);
         }

         var7.b(this, this.aW);
      }

      this.b(StatList.y);
      this.a(StatList.h);
      this.bs().g();
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      if (this.b(var1)) {
         return false;
      } else {
         boolean var3 = this.b.ae() && this.cr() && "fall".equals(var1.p);
         if (!var3 && this.bQ > 0 && var1 != DamageSource.j) {
            return false;
         } else {
            if (var1 instanceof EntityDamageSource) {
               Entity var4 = var1.j();
               if (var4 instanceof EntityPlayer && !this.a((EntityPlayer)var4)) {
                  return false;
               }

               if (var4 instanceof EntityArrow) {
                  EntityArrow var5 = (EntityArrow)var4;
                  if (var5.c instanceof EntityPlayer && !this.a((EntityPlayer)var5.c)) {
                     return false;
                  }
               }
            }

            return super.a(var1, var2);
         }
      }
   }

   @Override
   public boolean a(EntityPlayer var1) {
      return !this.cr() ? false : super.a(var1);
   }

   private boolean cr() {
      return this.b.aj();
   }

   @Override
   public void c(int var1) {
      if (this.am == 1 && var1 == 1) {
         this.b(AchievementList.D);
         this.o.e(this);
         this.i = true;
         this.a.a(new S2BPacketChangeGameState(4, 0.0F));
      } else {
         if (this.am == 0 && var1 == 1) {
            this.b(AchievementList.C);
            BlockPos var2 = this.b.a(var1).m();
            if (var2 != null) {
               this.a.a(var2.n(), var2.o(), var2.p(), 0.0F, 0.0F);
            }

            var1 = 1;
         } else {
            this.b(AchievementList.y);
         }

         this.b.ap().a(this, var1);
         this.bP = -1;
         this.bM = -1.0F;
         this.bN = -1;
      }
   }

   @Override
   public boolean a(EntityPlayerMP var1) {
      if (var1.v()) {
         return this.C() == this;
      } else {
         return this.v() ? false : super.a(var1);
      }
   }

   private void a(TileEntity var1) {
      if (var1 != null) {
         Packet var2 = var1.y_();
         if (var2 != null) {
            this.a.a(var2);
         }
      }
   }

   @Override
   public void a(Entity var1, int var2) {
      super.a(var1, var2);
      this.bk.b();
   }

   @Override
   public EntityPlayer.EnumStatus a(BlockPos var1) {
      EntityPlayer.EnumStatus var2 = super.a(var1);
      if (var2 == EntityPlayer.EnumStatus.a) {
         S0APacketUseBed var3 = new S0APacketUseBed(this, var1);
         this.u().s().a(this, var3);
         this.a.a(this.s, this.t, this.u, this.y, this.z);
         this.a.a(var3);
      }

      return var2;
   }

   @Override
   public void a(boolean var1, boolean var2, boolean var3) {
      if (this.bJ()) {
         this.u().s().b(this, new S0BPacketAnimation(this, 2));
      }

      super.a(var1, var2, var3);
      if (this.a != null) {
         this.a.a(this.s, this.t, this.u, this.y, this.z);
      }
   }

   @Override
   public void a(Entity var1) {
      Entity var2 = this.m;
      super.a(var1);
      if (var1 != var2) {
         this.a.a(new S1BPacketEntityAttach(0, this, this.m));
         this.a.a(this.s, this.t, this.u, this.y, this.z);
      }
   }

   @Override
   protected void a(double var1, boolean var3, Block var4, BlockPos var5) {
   }

   public void a(double var1, boolean var3) {
      int var4 = MathHelper.c(this.s);
      int var5 = MathHelper.c(this.t - 0.2F);
      int var6 = MathHelper.c(this.u);
      BlockPos var7 = new BlockPos(var4, var5, var6);
      Block var8 = this.o.p(var7).c();
      if (var8.t() == Material.a) {
         Block var9 = this.o.p(var7.b()).c();
         if (var9 instanceof BlockFence || var9 instanceof BlockWall || var9 instanceof BlockFenceGate) {
            var7 = var7.b();
            var8 = this.o.p(var7).c();
         }
      }

      super.a(var1, var3, var8, var7);
   }

   @Override
   public void a(TileEntitySign var1) {
      var1.a(this);
      this.a.a(new S36PacketSignEditorOpen(var1.v()));
   }

   private void cs() {
      this.bV = this.bV % 100 + 1;
   }

   @Override
   public void a(IInteractionObject var1) {
      this.cs();
      this.a.a(new S2DPacketOpenWindow(this.bV, var1.k(), var1.f_()));
      this.bk = var1.a(this.bi, this);
      this.bk.d = this.bV;
      this.bk.a((ICrafting)this);
   }

   @Override
   public void a(IInventory var1) {
      if (this.bk != this.bj) {
         this.n();
      }

      if (var1 instanceof ILockableContainer) {
         ILockableContainer var2 = (ILockableContainer)var1;
         if (var2.r_() && !this.a(var2.i()) && !this.v()) {
            this.a.a(new S02PacketChat(new ChatComponentTranslation("container.isLocked", var1.f_()), (byte)2));
            this.a.a(new S29PacketSoundEffect("random.door_close", this.s, this.t, this.u, 1.0F, 1.0F));
            return;
         }
      }

      this.cs();
      if (var1 instanceof IInteractionObject) {
         this.a.a(new S2DPacketOpenWindow(this.bV, ((IInteractionObject)var1).k(), var1.f_(), var1.o_()));
         this.bk = ((IInteractionObject)var1).a(this.bi, this);
      } else {
         this.a.a(new S2DPacketOpenWindow(this.bV, "minecraft:container", var1.f_(), var1.o_()));
         this.bk = new ContainerChest(this.bi, var1, this);
      }

      this.bk.d = this.bV;
      this.bk.a((ICrafting)this);
   }

   @Override
   public void a(IMerchant var1) {
      this.cs();
      this.bk = new ContainerMerchant(this.bi, var1, this.o);
      this.bk.d = this.bV;
      this.bk.a((ICrafting)this);
      InventoryMerchant var2 = ((ContainerMerchant)this.bk).e();
      IChatComponent var3 = var1.f_();
      this.a.a(new S2DPacketOpenWindow(this.bV, "minecraft:villager", var3, var2.o_()));
      MerchantRecipeList var4 = var1.b_(this);
      if (var4 != null) {
         PacketBuffer var5 = new PacketBuffer(Unpooled.buffer());
         var5.writeInt(this.bV);
         var4.a(var5);
         this.a.a(new S3FPacketCustomPayload("MC|TrList", var5));
      }
   }

   @Override
   public void a(EntityHorse var1, IInventory var2) {
      if (this.bk != this.bj) {
         this.n();
      }

      this.cs();
      this.a.a(new S2DPacketOpenWindow(this.bV, "EntityHorse", var2.f_(), var2.o_(), var1.F()));
      this.bk = new ContainerHorseInventory(this.bi, var2, var1, this);
      this.bk.d = this.bV;
      this.bk.a((ICrafting)this);
   }

   @Override
   public void a(ItemStack var1) {
      Item var2 = var1.b();
      if (var2 == Items.bN) {
         this.a.a(new S3FPacketCustomPayload("MC|BOpen", new PacketBuffer(Unpooled.buffer())));
      }
   }

   @Override
   public void a(Container var1, int var2, ItemStack var3) {
      if (!(var1.a(var2) instanceof SlotCrafting)) {
         if (!this.g) {
            this.a.a(new S2FPacketSetSlot(var1.d, var2, var3));
         }
      }
   }

   public void a(Container var1) {
      this.a(var1, var1.a());
   }

   @Override
   public void a(Container var1, List<ItemStack> var2) {
      this.a.a(new S30PacketWindowItems(var1.d, var2));
      this.a.a(new S2FPacketSetSlot(-1, -1, this.bi.p()));
   }

   @Override
   public void a(Container var1, int var2, int var3) {
      this.a.a(new S31PacketWindowProperty(var1.d, var2, var3));
   }

   @Override
   public void a(Container var1, IInventory var2) {
      for (int var3 = 0; var3 < var2.g(); var3++) {
         this.a.a(new S31PacketWindowProperty(var1.d, var3, var2.a_(var3)));
      }
   }

   @Override
   public void n() {
      this.a.a(new S2EPacketCloseWindow(this.bk.d));
      this.p();
   }

   public void o() {
      if (!this.g) {
         this.a.a(new S2FPacketSetSlot(-1, -1, this.bi.p()));
      }
   }

   public void p() {
      this.bk.b((EntityPlayer)this);
      this.bk = this.bj;
   }

   public void a(float var1, float var2, boolean var3, boolean var4) {
      if (this.m != null) {
         if (var1 >= -1.0F && var1 <= 1.0F) {
            this.aZ = var1;
         }

         if (var2 >= -1.0F && var2 <= 1.0F) {
            this.ba = var2;
         }

         this.aY = var3;
         this.c(var4);
      }
   }

   @Override
   public void a(StatBase var1, int var2) {
      if (var1 != null) {
         this.bK.b(this, var1, var2);

         for (ScoreObjective var4 : this.cp().a(var1.k())) {
            this.cp().c(this.e_(), var4).a(var2);
         }

         if (this.bK.e()) {
            this.bK.a(this);
         }
      }
   }

   @Override
   public void a(StatBase var1) {
      if (var1 != null) {
         this.bK.a(this, var1, 0);

         for (ScoreObjective var3 : this.cp().a(var1.k())) {
            this.cp().c(this.e_(), var3).c(0);
         }

         if (this.bK.e()) {
            this.bK.a(this);
         }
      }
   }

   public void q() {
      if (this.l != null) {
         this.l.a((Entity)this);
      }

      if (this.bw) {
         this.a(true, false, false);
      }
   }

   public void r() {
      this.bM = -1.0E8F;
   }

   @Override
   public void b(IChatComponent var1) {
      this.a.a(new S02PacketChat(var1));
   }

   @Override
   protected void s() {
      this.a.a(new S19PacketEntityStatus(this, (byte)9));
      super.s();
   }

   @Override
   public void a(ItemStack var1, int var2) {
      super.a(var1, var2);
      if (var1 != null && var1.b() != null && var1.b().e(var1) == EnumAction.b) {
         this.u().s().b(this, new S0BPacketAnimation(this, 3));
      }
   }

   @Override
   public void a(EntityPlayer var1, boolean var2) {
      super.a(var1, var2);
      this.bP = -1;
      this.bM = -1.0F;
      this.bN = -1;
      this.bJ.addAll(((EntityPlayerMP)var1).bJ);
   }

   @Override
   protected void a(PotionEffect var1) {
      super.a(var1);
      this.a.a(new S1DPacketEntityEffect(this.F(), var1));
   }

   @Override
   protected void a(PotionEffect var1, boolean var2) {
      super.a(var1, var2);
      this.a.a(new S1DPacketEntityEffect(this.F(), var1));
   }

   @Override
   protected void b(PotionEffect var1) {
      super.b(var1);
      this.a.a(new S1EPacketRemoveEntityEffect(this.F(), var1));
   }

   @Override
   public void a(double var1, double var3, double var5) {
      this.a.a(var1, var3, var5, this.y, this.z);
   }

   @Override
   public void b(Entity var1) {
      this.u().s().b(this, new S0BPacketAnimation(var1, 4));
   }

   @Override
   public void c(Entity var1) {
      this.u().s().b(this, new S0BPacketAnimation(var1, 5));
   }

   @Override
   public void t() {
      if (this.a != null) {
         this.a.a(new S39PacketPlayerAbilities(this.bA));
         this.B();
      }
   }

   public WorldServer u() {
      return (WorldServer)this.o;
   }

   @Override
   public void a(WorldSettings.GameType var1) {
      this.c.a(var1);
      this.a.a(new S2BPacketChangeGameState(3, var1.a()));
      if (var1 == WorldSettings.GameType.e) {
         this.a(null);
      } else {
         this.e(this);
      }

      this.t();
      this.bP();
   }

   @Override
   public boolean v() {
      return this.c.b() == WorldSettings.GameType.e;
   }

   @Override
   public void a(IChatComponent var1) {
      this.a.a(new S02PacketChat(var1));
   }

   @Override
   public boolean a(int var1, String var2) {
      if ("seed".equals(var2) && !this.b.ae()) {
         return true;
      } else if (!"tell".equals(var2) && !"help".equals(var2) && !"me".equals(var2) && !"trigger".equals(var2)) {
         if (this.b.ap().h(this.cd())) {
            UserListOpsEntry var3 = this.b.ap().m().b(this.cd());
            return var3 != null ? var3.a() >= var1 : this.b.p() >= var1;
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   public String w() {
      String var1 = this.a.a.b().toString();
      var1 = var1.substring(var1.indexOf("/") + 1);
      return var1.substring(0, var1.indexOf(":"));
   }

   public void a(C15PacketClientSettings var1) {
      this.bI = var1.a();
      this.bR = var1.c();
      this.bS = var1.d();
      this.H().b(10, (byte)var1.e());
   }

   public EntityPlayer.EnumChatVisibility y() {
      return this.bR;
   }

   public void a(String var1, String var2) {
      this.a.a(new S48PacketResourcePackSend(var1, var2));
   }

   @Override
   public BlockPos c() {
      return new BlockPos(this.s, this.t + 0.5, this.u);
   }

   public void z() {
      this.bT = MinecraftServer.az();
   }

   public StatisticsFile A() {
      return this.bK;
   }

   @Override
   public void d(Entity var1) {
      if (var1 instanceof EntityPlayer) {
         this.a.a(new S13PacketDestroyEntities(var1.F()));
      } else {
         this.bJ.add(var1.F());
      }
   }

   @Override
   protected void B() {
      if (this.v()) {
         this.bj();
         this.e(true);
      } else {
         super.B();
      }

      this.u().s().a(this);
   }

   public Entity C() {
      return (Entity)(this.bU == null ? this : this.bU);
   }

   public void e(Entity var1) {
      Entity var2 = this.C();
      this.bU = (Entity)(var1 == null ? this : var1);
      if (var2 != this.bU) {
         this.a.a(new S43PacketCamera(this.bU));
         this.a(this.bU.s, this.bU.t, this.bU.u);
      }
   }

   @Override
   public void f(Entity var1) {
      if (this.c.b() == WorldSettings.GameType.e) {
         this.e(var1);
      } else {
         super.f(var1);
      }
   }

   public long D() {
      return this.bT;
   }

   public IChatComponent E() {
      return null;
   }
}
