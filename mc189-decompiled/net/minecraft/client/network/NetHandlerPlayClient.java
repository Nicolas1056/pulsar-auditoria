package net.minecraft.client.network;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.GuardianSound;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.GuiScreenDemo;
import net.minecraft.client.gui.GuiScreenRealmsProxy;
import net.minecraft.client.gui.GuiWinGame;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EntityPickupFX;
import net.minecraft.client.player.inventory.ContainerLocalMenu;
import net.minecraft.client.player.inventory.LocalBlockIntercommunication;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.stream.MetadataAchievement;
import net.minecraft.client.stream.MetadataCombat;
import net.minecraft.client.stream.MetadataPlayerDeath;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.NpcMerchant;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Items;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.network.play.server.S0APacketUseBed;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.network.play.server.S0EPacketSpawnObject;
import net.minecraft.network.play.server.S0FPacketSpawnMob;
import net.minecraft.network.play.server.S10PacketSpawnPainting;
import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S19PacketEntityHeadLook;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.network.play.server.S1CPacketEntityMetadata;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.network.play.server.S20PacketEntityProperties;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S24PacketBlockAction;
import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
import net.minecraft.network.play.server.S26PacketMapChunkBulk;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S28PacketEffect;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.network.play.server.S31PacketWindowProperty;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S33PacketUpdateSign;
import net.minecraft.network.play.server.S34PacketMaps;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.play.server.S36PacketSignEditorOpen;
import net.minecraft.network.play.server.S37PacketStatistics;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S3APacketTabComplete;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.network.play.server.S3CPacketUpdateScore;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.network.play.server.S41PacketServerDifficulty;
import net.minecraft.network.play.server.S42PacketCombatEvent;
import net.minecraft.network.play.server.S43PacketCamera;
import net.minecraft.network.play.server.S44PacketWorldBorder;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.network.play.server.S46PacketSetCompressionLevel;
import net.minecraft.network.play.server.S47PacketPlayerListHeaderFooter;
import net.minecraft.network.play.server.S48PacketResourcePackSend;
import net.minecraft.network.play.server.S49PacketUpdateEntityNBT;
import net.minecraft.potion.PotionEffect;
import net.minecraft.realms.DisconnectedRealmsScreen;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.Explosion;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerPlayClient implements INetHandlerPlayClient {
   private static final Logger b = LogManager.getLogger();
   private final NetworkManager c;
   private final GameProfile d;
   private final GuiScreen e;
   private Minecraft f;
   private WorldClient g;
   private boolean h;
   private final Map<UUID, NetworkPlayerInfo> i = Maps.newHashMap();
   public int a = 20;
   private boolean j = false;
   private final Random k = new Random();

   public NetHandlerPlayClient(Minecraft var1, GuiScreen var2, NetworkManager var3, GameProfile var4) {
      this.f = var1;
      this.e = var2;
      this.c = var3;
      this.d = var4;
   }

   public void b() {
      this.g = null;
   }

   @Override
   public void a(S01PacketJoinGame var1) {
      PacketThreadUtil.a(var1, this, this.f);
      this.f.c = new PlayerControllerMP(this.f, this);
      this.g = new WorldClient(this, new WorldSettings(0L, var1.c(), false, var1.b(), var1.g()), var1.d(), var1.e(), this.f.A);
      this.f.t.az = var1.e();
      this.f.a(this.g);
      this.f.h.am = var1.d();
      this.f.a(new GuiDownloadTerrain(this));
      this.f.h.d(var1.a());
      this.a = var1.f();
      this.f.h.k(var1.h());
      this.f.c.a(var1.c());
      this.f.t.c();
      this.c.a(new C17PacketCustomPayload("MC|Brand", new PacketBuffer(Unpooled.buffer()).a(ClientBrandRetriever.getClientModName())));
   }

   @Override
   public void a(S0EPacketSpawnObject var1) {
      PacketThreadUtil.a(var1, this, this.f);
      double var2 = var1.b() / 32.0;
      double var4 = var1.c() / 32.0;
      double var6 = var1.d() / 32.0;
      Object var8 = null;
      if (var1.j() == 10) {
         var8 = EntityMinecart.a(this.g, var2, var4, var6, EntityMinecart.EnumMinecartType.a(var1.k()));
      } else if (var1.j() == 90) {
         Entity var9 = this.g.a(var1.k());
         if (var9 instanceof EntityPlayer) {
            var8 = new EntityFishHook(this.g, var2, var4, var6, (EntityPlayer)var9);
         }

         var1.g(0);
      } else if (var1.j() == 60) {
         var8 = new EntityArrow(this.g, var2, var4, var6);
      } else if (var1.j() == 61) {
         var8 = new EntitySnowball(this.g, var2, var4, var6);
      } else if (var1.j() == 71) {
         var8 = new EntityItemFrame(this.g, new BlockPos(MathHelper.c(var2), MathHelper.c(var4), MathHelper.c(var6)), EnumFacing.b(var1.k()));
         var1.g(0);
      } else if (var1.j() == 77) {
         var8 = new EntityLeashKnot(this.g, new BlockPos(MathHelper.c(var2), MathHelper.c(var4), MathHelper.c(var6)));
         var1.g(0);
      } else if (var1.j() == 65) {
         var8 = new EntityEnderPearl(this.g, var2, var4, var6);
      } else if (var1.j() == 72) {
         var8 = new EntityEnderEye(this.g, var2, var4, var6);
      } else if (var1.j() == 76) {
         var8 = new EntityFireworkRocket(this.g, var2, var4, var6, null);
      } else if (var1.j() == 63) {
         var8 = new EntityLargeFireball(this.g, var2, var4, var6, var1.e() / 8000.0, var1.f() / 8000.0, var1.g() / 8000.0);
         var1.g(0);
      } else if (var1.j() == 64) {
         var8 = new EntitySmallFireball(this.g, var2, var4, var6, var1.e() / 8000.0, var1.f() / 8000.0, var1.g() / 8000.0);
         var1.g(0);
      } else if (var1.j() == 66) {
         var8 = new EntityWitherSkull(this.g, var2, var4, var6, var1.e() / 8000.0, var1.f() / 8000.0, var1.g() / 8000.0);
         var1.g(0);
      } else if (var1.j() == 62) {
         var8 = new EntityEgg(this.g, var2, var4, var6);
      } else if (var1.j() == 73) {
         var8 = new EntityPotion(this.g, var2, var4, var6, var1.k());
         var1.g(0);
      } else if (var1.j() == 75) {
         var8 = new EntityExpBottle(this.g, var2, var4, var6);
         var1.g(0);
      } else if (var1.j() == 1) {
         var8 = new EntityBoat(this.g, var2, var4, var6);
      } else if (var1.j() == 50) {
         var8 = new EntityTNTPrimed(this.g, var2, var4, var6, null);
      } else if (var1.j() == 78) {
         var8 = new EntityArmorStand(this.g, var2, var4, var6);
      } else if (var1.j() == 51) {
         var8 = new EntityEnderCrystal(this.g, var2, var4, var6);
      } else if (var1.j() == 2) {
         var8 = new EntityItem(this.g, var2, var4, var6);
      } else if (var1.j() == 70) {
         var8 = new EntityFallingBlock(this.g, var2, var4, var6, Block.d(var1.k() & 65535));
         var1.g(0);
      }

      if (var8 != null) {
         ((Entity)var8).bW = var1.b();
         ((Entity)var8).bX = var1.c();
         ((Entity)var8).bY = var1.d();
         ((Entity)var8).z = var1.h() * 360 / 256.0F;
         ((Entity)var8).y = var1.i() * 360 / 256.0F;
         Entity[] var12 = ((Entity)var8).aB();
         if (var12 != null) {
            int var10 = var1.a() - ((Entity)var8).F();

            for (int var11 = 0; var11 < var12.length; var11++) {
               var12[var11].d(var12[var11].F() + var10);
            }
         }

         ((Entity)var8).d(var1.a());
         this.g.a(var1.a(), (Entity)var8);
         if (var1.k() > 0) {
            if (var1.j() == 60) {
               Entity var13 = this.g.a(var1.k());
               if (var13 instanceof EntityLivingBase && var8 instanceof EntityArrow) {
                  ((EntityArrow)var8).c = var13;
               }
            }

            ((Entity)var8).i(var1.e() / 8000.0, var1.f() / 8000.0, var1.g() / 8000.0);
         }
      }
   }

   @Override
   public void a(S11PacketSpawnExperienceOrb var1) {
      PacketThreadUtil.a(var1, this, this.f);
      EntityXPOrb var2 = new EntityXPOrb(this.g, var1.b() / 32.0, var1.c() / 32.0, var1.d() / 32.0, var1.e());
      var2.bW = var1.b();
      var2.bX = var1.c();
      var2.bY = var1.d();
      var2.y = 0.0F;
      var2.z = 0.0F;
      var2.d(var1.a());
      this.g.a(var1.a(), var2);
   }

   @Override
   public void a(S2CPacketSpawnGlobalEntity var1) {
      PacketThreadUtil.a(var1, this, this.f);
      double var2 = var1.b() / 32.0;
      double var4 = var1.c() / 32.0;
      double var6 = var1.d() / 32.0;
      EntityLightningBolt var8 = null;
      if (var1.e() == 1) {
         var8 = new EntityLightningBolt(this.g, var2, var4, var6);
      }

      if (var8 != null) {
         var8.bW = var1.b();
         var8.bX = var1.c();
         var8.bY = var1.d();
         var8.y = 0.0F;
         var8.z = 0.0F;
         var8.d(var1.a());
         this.g.c(var8);
      }
   }

   @Override
   public void a(S10PacketSpawnPainting var1) {
      PacketThreadUtil.a(var1, this, this.f);
      EntityPainting var2 = new EntityPainting(this.g, var1.b(), var1.c(), var1.d());
      this.g.a(var1.a(), var2);
   }

   @Override
   public void a(S12PacketEntityVelocity var1) {
      PacketThreadUtil.a(var1, this, this.f);
      Entity var2 = this.g.a(var1.a());
      if (var2 != null) {
         var2.i(var1.b() / 8000.0, var1.c() / 8000.0, var1.d() / 8000.0);
      }
   }

   @Override
   public void a(S1CPacketEntityMetadata var1) {
      PacketThreadUtil.a(var1, this, this.f);
      Entity var2 = this.g.a(var1.b());
      if (var2 != null && var1.a() != null) {
         var2.H().a(var1.a());
      }
   }

   @Override
   public void a(S0CPacketSpawnPlayer var1) {
      PacketThreadUtil.a(var1, this, this.f);
      double var2 = var1.d() / 32.0;
      double var4 = var1.e() / 32.0;
      double var6 = var1.f() / 32.0;
      float var8 = var1.g() * 360 / 256.0F;
      float var9 = var1.h() * 360 / 256.0F;
      EntityOtherPlayerMP var10 = new EntityOtherPlayerMP(this.f.f, this.a(var1.c()).a());
      var10.p = var10.P = var10.bW = var1.d();
      var10.q = var10.Q = var10.bX = var1.e();
      var10.r = var10.R = var10.bY = var1.f();
      int var11 = var1.i();
      if (var11 == 0) {
         var10.bi.a[var10.bi.c] = null;
      } else {
         var10.bi.a[var10.bi.c] = new ItemStack(Item.b(var11), 1, 0);
      }

      var10.a(var2, var4, var6, var8, var9);
      this.g.a(var1.b(), var10);
      List var12 = var1.a();
      if (var12 != null) {
         var10.H().a(var12);
      }
   }

   @Override
   public void a(S18PacketEntityTeleport var1) {
      PacketThreadUtil.a(var1, this, this.f);
      Entity var2 = this.g.a(var1.a());
      if (var2 != null) {
         var2.bW = var1.b();
         var2.bX = var1.c();
         var2.bY = var1.d();
         double var3 = var2.bW / 32.0;
         double var5 = var2.bX / 32.0;
         double var7 = var2.bY / 32.0;
         float var9 = var1.e() * 360 / 256.0F;
         float var10 = var1.f() * 360 / 256.0F;
         if (!(Math.abs(var2.s - var3) >= 0.03125) && !(Math.abs(var2.t - var5) >= 0.015625) && !(Math.abs(var2.u - var7) >= 0.03125)) {
            var2.a(var2.s, var2.t, var2.u, var9, var10, 3, true);
         } else {
            var2.a(var3, var5, var7, var9, var10, 3, true);
         }

         var2.C = var1.g();
      }
   }

   @Override
   public void a(S09PacketHeldItemChange var1) {
      PacketThreadUtil.a(var1, this, this.f);
      if (var1.a() >= 0 && var1.a() < InventoryPlayer.i()) {
         this.f.h.bi.c = var1.a();
      }
   }

   @Override
   public void a(S14PacketEntity var1) {
      PacketThreadUtil.a(var1, this, this.f);
      Entity var2 = var1.a(this.g);
      if (var2 != null) {
         var2.bW = var2.bW + var1.a();
         var2.bX = var2.bX + var1.b();
         var2.bY = var2.bY + var1.c();
         double var3 = var2.bW / 32.0;
         double var5 = var2.bX / 32.0;
         double var7 = var2.bY / 32.0;
         float var9 = var1.f() ? var1.d() * 360 / 256.0F : var2.y;
         float var10 = var1.f() ? var1.e() * 360 / 256.0F : var2.z;
         var2.a(var3, var5, var7, var9, var10, 3, false);
         var2.C = var1.g();
      }
   }

   @Override
   public void a(S19PacketEntityHeadLook var1) {
      PacketThreadUtil.a(var1, this, this.f);
      Entity var2 = var1.a(this.g);
      if (var2 != null) {
         float var3 = var1.a() * 360 / 256.0F;
         var2.f(var3);
      }
   }

   @Override
   public void a(S13PacketDestroyEntities var1) {
      PacketThreadUtil.a(var1, this, this.f);

      for (int var2 = 0; var2 < var1.a().length; var2++) {
         this.g.e(var1.a()[var2]);
      }
   }

   @Override
   public void a(S08PacketPlayerPosLook var1) {
      PacketThreadUtil.a(var1, this, this.f);
      EntityPlayerSP var2 = this.f.h;
      double var3 = var1.a();
      double var5 = var1.b();
      double var7 = var1.c();
      float var9 = var1.d();
      float var10 = var1.e();
      if (var1.f().contains(S08PacketPlayerPosLook.EnumFlags.a)) {
         var3 += var2.s;
      } else {
         var2.v = 0.0;
      }

      if (var1.f().contains(S08PacketPlayerPosLook.EnumFlags.b)) {
         var5 += var2.t;
      } else {
         var2.w = 0.0;
      }

      if (var1.f().contains(S08PacketPlayerPosLook.EnumFlags.c)) {
         var7 += var2.u;
      } else {
         var2.x = 0.0;
      }

      if (var1.f().contains(S08PacketPlayerPosLook.EnumFlags.e)) {
         var10 += var2.z;
      }

      if (var1.f().contains(S08PacketPlayerPosLook.EnumFlags.d)) {
         var9 += var2.y;
      }

      var2.a(var3, var5, var7, var9, var10);
      this.c.a(new C03PacketPlayer.C06PacketPlayerPosLook(var2.s, var2.aR().b, var2.u, var2.y, var2.z, false));
      if (!this.h) {
         this.f.h.p = this.f.h.s;
         this.f.h.q = this.f.h.t;
         this.f.h.r = this.f.h.u;
         this.h = true;
         this.f.a(null);
      }
   }

   @Override
   public void a(S22PacketMultiBlockChange var1) {
      PacketThreadUtil.a(var1, this, this.f);

      for (S22PacketMultiBlockChange.BlockUpdateData var5 : var1.a()) {
         this.g.b(var5.a(), var5.c());
      }
   }

   @Override
   public void a(S21PacketChunkData var1) {
      PacketThreadUtil.a(var1, this, this.f);
      if (var1.e()) {
         if (var1.d() == 0) {
            this.g.b(var1.b(), var1.c(), false);
            return;
         }

         this.g.b(var1.b(), var1.c(), true);
      }

      this.g.a(var1.b() << 4, 0, var1.c() << 4, (var1.b() << 4) + 15, 256, (var1.c() << 4) + 15);
      Chunk var2 = this.g.a(var1.b(), var1.c());
      var2.a(var1.a(), var1.d(), var1.e());
      this.g.b(var1.b() << 4, 0, var1.c() << 4, (var1.b() << 4) + 15, 256, (var1.c() << 4) + 15);
      if (!var1.e() || !(this.g.t instanceof WorldProviderSurface)) {
         var2.l();
      }
   }

   @Override
   public void a(S23PacketBlockChange var1) {
      PacketThreadUtil.a(var1, this, this.f);
      this.g.b(var1.b(), var1.a());
   }

   @Override
   public void a(S40PacketDisconnect var1) {
      this.c.a(var1.a());
   }

   @Override
   public void a(IChatComponent var1) {
      this.f.a(null);
      if (this.e != null) {
         if (this.e instanceof GuiScreenRealmsProxy) {
            this.f.a(new DisconnectedRealmsScreen(((GuiScreenRealmsProxy)this.e).a(), "disconnect.lost", var1).getProxy());
         } else {
            this.f.a(new GuiDisconnected(this.e, "disconnect.lost", var1));
         }
      } else {
         this.f.a(new GuiDisconnected(new GuiMultiplayer(new GuiMainMenu()), "disconnect.lost", var1));
      }
   }

   public void a(Packet var1) {
      this.c.a(var1);
   }

   @Override
   public void a(S0DPacketCollectItem var1) {
      PacketThreadUtil.a(var1, this, this.f);
      Entity var2 = this.g.a(var1.a());
      Object var3 = (EntityLivingBase)this.g.a(var1.b());
      if (var3 == null) {
         var3 = this.f.h;
      }

      if (var2 != null) {
         if (var2 instanceof EntityXPOrb) {
            this.g.a(var2, "random.orb", 0.2F, ((this.k.nextFloat() - this.k.nextFloat()) * 0.7F + 1.0F) * 2.0F);
         } else {
            this.g.a(var2, "random.pop", 0.2F, ((this.k.nextFloat() - this.k.nextFloat()) * 0.7F + 1.0F) * 2.0F);
         }

         this.f.j.a(new EntityPickupFX(this.g, var2, (Entity)var3, 0.5F));
         this.g.e(var1.a());
      }
   }

   @Override
   public void a(S02PacketChat var1) {
      PacketThreadUtil.a(var1, this, this.f);
      if (var1.c() == 2) {
         this.f.q.a(var1.a(), false);
      } else {
         this.f.q.d().a(var1.a());
      }
   }

   @Override
   public void a(S0BPacketAnimation var1) {
      PacketThreadUtil.a(var1, this, this.f);
      Entity var2 = this.g.a(var1.a());
      if (var2 != null) {
         if (var1.b() == 0) {
            EntityLivingBase var3 = (EntityLivingBase)var2;
            var3.bw();
         } else if (var1.b() == 1) {
            var2.ar();
         } else if (var1.b() == 2) {
            EntityPlayer var4 = (EntityPlayer)var2;
            var4.a(false, false, false);
         } else if (var1.b() == 4) {
            this.f.j.a(var2, EnumParticleTypes.j);
         } else if (var1.b() == 5) {
            this.f.j.a(var2, EnumParticleTypes.k);
         }
      }
   }

   @Override
   public void a(S0APacketUseBed var1) {
      PacketThreadUtil.a(var1, this, this.f);
      var1.a(this.g).a(var1.a());
   }

   @Override
   public void a(S0FPacketSpawnMob var1) {
      PacketThreadUtil.a(var1, this, this.f);
      double var2 = var1.d() / 32.0;
      double var4 = var1.e() / 32.0;
      double var6 = var1.f() / 32.0;
      float var8 = var1.j() * 360 / 256.0F;
      float var9 = var1.k() * 360 / 256.0F;
      EntityLivingBase var10 = (EntityLivingBase)EntityList.a(var1.c(), this.f.f);
      var10.bW = var1.d();
      var10.bX = var1.e();
      var10.bY = var1.f();
      var10.aI = var10.aK = var1.l() * 360 / 256.0F;
      Entity[] var11 = var10.aB();
      if (var11 != null) {
         int var12 = var1.b() - var10.F();

         for (int var13 = 0; var13 < var11.length; var13++) {
            var11[var13].d(var11[var13].F() + var12);
         }
      }

      var10.d(var1.b());
      var10.a(var2, var4, var6, var8, var9);
      var10.v = var1.g() / 8000.0F;
      var10.w = var1.h() / 8000.0F;
      var10.x = var1.i() / 8000.0F;
      this.g.a(var1.b(), var10);
      List var14 = var1.a();
      if (var14 != null) {
         var10.H().a(var14);
      }
   }

   @Override
   public void a(S03PacketTimeUpdate var1) {
      PacketThreadUtil.a(var1, this, this.f);
      this.f.f.a(var1.a());
      this.f.f.b(var1.b());
   }

   @Override
   public void a(S05PacketSpawnPosition var1) {
      PacketThreadUtil.a(var1, this, this.f);
      this.f.h.a(var1.a(), true);
      this.f.f.P().a(var1.a());
   }

   @Override
   public void a(S1BPacketEntityAttach var1) {
      PacketThreadUtil.a(var1, this, this.f);
      Object var2 = this.g.a(var1.b());
      Entity var3 = this.g.a(var1.c());
      if (var1.a() == 0) {
         boolean var4 = false;
         if (var1.b() == this.f.h.F()) {
            var2 = this.f.h;
            if (var3 instanceof EntityBoat) {
               ((EntityBoat)var3).a(false);
            }

            var4 = ((Entity)var2).m == null && var3 != null;
         } else if (var3 instanceof EntityBoat) {
            ((EntityBoat)var3).a(true);
         }

         if (var2 == null) {
            return;
         }

         ((Entity)var2).a(var3);
         if (var4) {
            GameSettings var5 = this.f.t;
            this.f.q.a(I18n.a("mount.onboard", GameSettings.c(var5.ad.i())), false);
         }
      } else if (var1.a() == 1 && var2 instanceof EntityLiving) {
         if (var3 != null) {
            ((EntityLiving)var2).a(var3, false);
         } else {
            ((EntityLiving)var2).a(false, false);
         }
      }
   }

   @Override
   public void a(S19PacketEntityStatus var1) {
      PacketThreadUtil.a(var1, this, this.f);
      Entity var2 = var1.a(this.g);
      if (var2 != null) {
         if (var1.a() == 21) {
            this.f.W().a(new GuardianSound((EntityGuardian)var2));
         } else {
            var2.a(var1.a());
         }
      }
   }

   @Override
   public void a(S06PacketUpdateHealth var1) {
      PacketThreadUtil.a(var1, this, this.f);
      this.f.h.n(var1.a());
      this.f.h.cl().a(var1.b());
      this.f.h.cl().b(var1.c());
   }

   @Override
   public void a(S1FPacketSetExperience var1) {
      PacketThreadUtil.a(var1, this, this.f);
      this.f.h.a(var1.a(), var1.b(), var1.c());
   }

   @Override
   public void a(S07PacketRespawn var1) {
      PacketThreadUtil.a(var1, this, this.f);
      if (var1.a() != this.f.h.am) {
         this.h = false;
         Scoreboard var2 = this.g.Z();
         this.g = new WorldClient(this, new WorldSettings(0L, var1.c(), false, this.f.f.P().t(), var1.d()), var1.a(), var1.b(), this.f.A);
         this.g.a(var2);
         this.f.a(this.g);
         this.f.h.am = var1.a();
         this.f.a(new GuiDownloadTerrain(this));
      }

      this.f.a(var1.a());
      this.f.c.a(var1.c());
   }

   @Override
   public void a(S27PacketExplosion var1) {
      PacketThreadUtil.a(var1, this, this.f);
      Explosion var2 = new Explosion(this.f.f, null, var1.d(), var1.e(), var1.f(), var1.g(), var1.h());
      var2.a(true);
      this.f.h.v = this.f.h.v + var1.a();
      this.f.h.w = this.f.h.w + var1.b();
      this.f.h.x = this.f.h.x + var1.c();
   }

   @Override
   public void a(S2DPacketOpenWindow var1) {
      PacketThreadUtil.a(var1, this, this.f);
      EntityPlayerSP var2 = this.f.h;
      if ("minecraft:container".equals(var1.b())) {
         var2.a(new InventoryBasic(var1.c(), var1.d()));
         var2.bk.d = var1.a();
      } else if ("minecraft:villager".equals(var1.b())) {
         var2.a(new NpcMerchant(var2, var1.c()));
         var2.bk.d = var1.a();
      } else if ("EntityHorse".equals(var1.b())) {
         Entity var3 = this.g.a(var1.e());
         if (var3 instanceof EntityHorse) {
            var2.a((EntityHorse)var3, new AnimalChest(var1.c(), var1.d()));
            var2.bk.d = var1.a();
         }
      } else if (!var1.f()) {
         var2.a(new LocalBlockIntercommunication(var1.b(), var1.c()));
         var2.bk.d = var1.a();
      } else {
         ContainerLocalMenu var4 = new ContainerLocalMenu(var1.b(), var1.c(), var1.d());
         var2.a((IInventory)var4);
         var2.bk.d = var1.a();
      }
   }

   @Override
   public void a(S2FPacketSetSlot var1) {
      PacketThreadUtil.a(var1, this, this.f);
      EntityPlayerSP var2 = this.f.h;
      if (var1.a() == -1) {
         var2.bi.b(var1.c());
      } else {
         boolean var3 = false;
         if (this.f.m instanceof GuiContainerCreative) {
            GuiContainerCreative var4 = (GuiContainerCreative)this.f.m;
            var3 = var4.f() != CreativeTabs.m.a();
         }

         if (var1.a() == 0 && var1.b() >= 36 && var1.b() < 45) {
            ItemStack var5 = var2.bj.a(var1.b()).d();
            if (var1.c() != null && (var5 == null || var5.b < var1.c().b)) {
               var1.c().c = 5;
            }

            var2.bj.a(var1.b(), var1.c());
         } else if (var1.a() == var2.bk.d && (var1.a() != 0 || !var3)) {
            var2.bk.a(var1.b(), var1.c());
         }
      }
   }

   @Override
   public void a(S32PacketConfirmTransaction var1) {
      PacketThreadUtil.a(var1, this, this.f);
      Container var2 = null;
      EntityPlayerSP var3 = this.f.h;
      if (var1.a() == 0) {
         var2 = var3.bj;
      } else if (var1.a() == var3.bk.d) {
         var2 = var3.bk;
      }

      if (var2 != null && !var1.c()) {
         this.a(new C0FPacketConfirmTransaction(var1.a(), var1.b(), true));
      }
   }

   @Override
   public void a(S30PacketWindowItems var1) {
      PacketThreadUtil.a(var1, this, this.f);
      EntityPlayerSP var2 = this.f.h;
      if (var1.a() == 0) {
         var2.bj.a(var1.b());
      } else if (var1.a() == var2.bk.d) {
         var2.bk.a(var1.b());
      }
   }

   @Override
   public void a(S36PacketSignEditorOpen var1) {
      PacketThreadUtil.a(var1, this, this.f);
      Object var2 = this.g.s(var1.a());
      if (!(var2 instanceof TileEntitySign)) {
         var2 = new TileEntitySign();
         ((TileEntity)var2).a(this.g);
         ((TileEntity)var2).a(var1.a());
      }

      this.f.h.a((TileEntitySign)var2);
   }

   @Override
   public void a(S33PacketUpdateSign var1) {
      PacketThreadUtil.a(var1, this, this.f);
      boolean var2 = false;
      if (this.f.f.e(var1.a())) {
         TileEntity var3 = this.f.f.s(var1.a());
         if (var3 instanceof TileEntitySign) {
            TileEntitySign var4 = (TileEntitySign)var3;
            if (var4.b()) {
               System.arraycopy(var1.b(), 0, var4.a, 0, 4);
               var4.p_();
            }

            var2 = true;
         }
      }

      if (!var2 && this.f.h != null) {
         this.f.h.a(new ChatComponentText("Unable to locate sign at " + var1.a().n() + ", " + var1.a().o() + ", " + var1.a().p()));
      }
   }

   @Override
   public void a(S35PacketUpdateTileEntity var1) {
      PacketThreadUtil.a(var1, this, this.f);
      if (this.f.f.e(var1.a())) {
         TileEntity var2 = this.f.f.s(var1.a());
         int var3 = var1.b();
         if (var3 == 1 && var2 instanceof TileEntityMobSpawner
            || var3 == 2 && var2 instanceof TileEntityCommandBlock
            || var3 == 3 && var2 instanceof TileEntityBeacon
            || var3 == 4 && var2 instanceof TileEntitySkull
            || var3 == 5 && var2 instanceof TileEntityFlowerPot
            || var3 == 6 && var2 instanceof TileEntityBanner) {
            var2.a(var1.c());
         }
      }
   }

   @Override
   public void a(S31PacketWindowProperty var1) {
      PacketThreadUtil.a(var1, this, this.f);
      EntityPlayerSP var2 = this.f.h;
      if (var2.bk != null && var2.bk.d == var1.a()) {
         var2.bk.b(var1.b(), var1.c());
      }
   }

   @Override
   public void a(S04PacketEntityEquipment var1) {
      PacketThreadUtil.a(var1, this, this.f);
      Entity var2 = this.g.a(var1.b());
      if (var2 != null) {
         var2.c(var1.c(), var1.a());
      }
   }

   @Override
   public void a(S2EPacketCloseWindow var1) {
      PacketThreadUtil.a(var1, this, this.f);
      this.f.h.q();
   }

   @Override
   public void a(S24PacketBlockAction var1) {
      PacketThreadUtil.a(var1, this, this.f);
      this.f.f.c(var1.a(), var1.d(), var1.b(), var1.c());
   }

   @Override
   public void a(S25PacketBlockBreakAnim var1) {
      PacketThreadUtil.a(var1, this, this.f);
      this.f.f.c(var1.a(), var1.b(), var1.c());
   }

   @Override
   public void a(S26PacketMapChunkBulk var1) {
      PacketThreadUtil.a(var1, this, this.f);

      for (int var2 = 0; var2 < var1.a(); var2++) {
         int var3 = var1.a(var2);
         int var4 = var1.b(var2);
         this.g.b(var3, var4, true);
         this.g.a(var3 << 4, 0, var4 << 4, (var3 << 4) + 15, 256, (var4 << 4) + 15);
         Chunk var5 = this.g.a(var3, var4);
         var5.a(var1.c(var2), var1.d(var2), true);
         this.g.b(var3 << 4, 0, var4 << 4, (var3 << 4) + 15, 256, (var4 << 4) + 15);
         if (!(this.g.t instanceof WorldProviderSurface)) {
            var5.l();
         }
      }
   }

   @Override
   public void a(S2BPacketChangeGameState var1) {
      PacketThreadUtil.a(var1, this, this.f);
      EntityPlayerSP var2 = this.f.h;
      int var3 = var1.a();
      float var4 = var1.b();
      int var5 = MathHelper.d(var4 + 0.5F);
      if (var3 >= 0 && var3 < S2BPacketChangeGameState.a.length && S2BPacketChangeGameState.a[var3] != null) {
         var2.b(new ChatComponentTranslation(S2BPacketChangeGameState.a[var3]));
      }

      if (var3 == 1) {
         this.g.P().b(true);
         this.g.k(0.0F);
      } else if (var3 == 2) {
         this.g.P().b(false);
         this.g.k(1.0F);
      } else if (var3 == 3) {
         this.f.c.a(WorldSettings.GameType.a(var5));
      } else if (var3 == 4) {
         this.f.a(new GuiWinGame());
      } else if (var3 == 5) {
         GameSettings var6 = this.f.t;
         if (var4 == 0.0F) {
            this.f.a(new GuiScreenDemo());
         } else if (var4 == 101.0F) {
            this.f
               .q
               .d()
               .a(
                  new ChatComponentTranslation(
                     "demo.help.movement", GameSettings.c(var6.Y.i()), GameSettings.c(var6.Z.i()), GameSettings.c(var6.aa.i()), GameSettings.c(var6.ab.i())
                  )
               );
         } else if (var4 == 102.0F) {
            this.f.q.d().a(new ChatComponentTranslation("demo.help.jump", GameSettings.c(var6.ac.i())));
         } else if (var4 == 103.0F) {
            this.f.q.d().a(new ChatComponentTranslation("demo.help.inventory", GameSettings.c(var6.af.i())));
         }
      } else if (var3 == 6) {
         this.g.a(var2.s, var2.t + var2.aS(), var2.u, "random.successful_hit", 0.18F, 0.45F, false);
      } else if (var3 == 7) {
         this.g.k(var4);
      } else if (var3 == 8) {
         this.g.i(var4);
      } else if (var3 == 10) {
         this.g.a(EnumParticleTypes.P, var2.s, var2.t, var2.u, 0.0, 0.0, 0.0, new int[0]);
         this.g.a(var2.s, var2.t, var2.u, "mob.guardian.curse", 1.0F, 1.0F, false);
      }
   }

   @Override
   public void a(S34PacketMaps var1) {
      PacketThreadUtil.a(var1, this, this.f);
      MapData var2 = ItemMap.a(var1.a(), this.f.f);
      var1.a(var2);
      this.f.o.k().a(var2);
   }

   @Override
   public void a(S28PacketEffect var1) {
      PacketThreadUtil.a(var1, this, this.f);
      if (var1.a()) {
         this.f.f.a(var1.b(), var1.d(), var1.c());
      } else {
         this.f.f.b(var1.b(), var1.d(), var1.c());
      }
   }

   @Override
   public void a(S37PacketStatistics var1) {
      PacketThreadUtil.a(var1, this, this.f);
      boolean var2 = false;

      for (Entry var4 : var1.a().entrySet()) {
         StatBase var5 = (StatBase)var4.getKey();
         int var6 = (Integer)var4.getValue();
         if (var5.d() && var6 > 0) {
            if (this.j && this.f.h.x().a(var5) == 0) {
               Achievement var7 = (Achievement)var5;
               this.f.p.a(var7);
               this.f.Y().a(new MetadataAchievement(var7), 0L);
               if (var5 == AchievementList.f) {
                  this.f.t.I = false;
                  this.f.t.b();
               }
            }

            var2 = true;
         }

         this.f.h.x().a(this.f.h, var5, var6);
      }

      if (!this.j && !var2 && this.f.t.I) {
         this.f.p.b(AchievementList.f);
      }

      this.j = true;
      if (this.f.m instanceof IProgressMeter) {
         ((IProgressMeter)this.f.m).a();
      }
   }

   @Override
   public void a(S1DPacketEntityEffect var1) {
      PacketThreadUtil.a(var1, this, this.f);
      Entity var2 = this.g.a(var1.b());
      if (var2 instanceof EntityLivingBase) {
         PotionEffect var3 = new PotionEffect(var1.c(), var1.e(), var1.d(), false, var1.f());
         var3.b(var1.a());
         ((EntityLivingBase)var2).c(var3);
      }
   }

   @Override
   public void a(S42PacketCombatEvent var1) {
      PacketThreadUtil.a(var1, this, this.f);
      Entity var2 = this.g.a(var1.c);
      EntityLivingBase var3 = var2 instanceof EntityLivingBase ? (EntityLivingBase)var2 : null;
      if (var1.a == S42PacketCombatEvent.Event.b) {
         long var4 = 1000 * var1.d / 20;
         MetadataCombat var6 = new MetadataCombat(this.f.h, var3);
         this.f.Y().a(var6, 0L - var4, 0L);
      } else if (var1.a == S42PacketCombatEvent.Event.c) {
         Entity var7 = this.g.a(var1.b);
         if (var7 instanceof EntityPlayer) {
            MetadataPlayerDeath var5 = new MetadataPlayerDeath((EntityPlayer)var7, var3);
            var5.a(var1.e);
            this.f.Y().a(var5, 0L);
         }
      }
   }

   @Override
   public void a(S41PacketServerDifficulty var1) {
      PacketThreadUtil.a(var1, this, this.f);
      this.f.f.P().a(var1.b());
      this.f.f.P().e(var1.a());
   }

   @Override
   public void a(S43PacketCamera var1) {
      PacketThreadUtil.a(var1, this, this.f);
      Entity var2 = var1.a(this.g);
      if (var2 != null) {
         this.f.a(var2);
      }
   }

   @Override
   public void a(S44PacketWorldBorder var1) {
      PacketThreadUtil.a(var1, this, this.f);
      var1.a(this.g.af());
   }

   @Override
   public void a(S45PacketTitle var1) {
      PacketThreadUtil.a(var1, this, this.f);
      S45PacketTitle.Type var2 = var1.a();
      String var3 = null;
      String var4 = null;
      String var5 = var1.b() != null ? var1.b().d() : "";
      switch (var2) {
         case a:
            var3 = var5;
            break;
         case b:
            var4 = var5;
            break;
         case e:
            this.f.q.a("", "", -1, -1, -1);
            this.f.q.a();
            return;
      }

      this.f.q.a(var3, var4, var1.c(), var1.d(), var1.e());
   }

   @Override
   public void a(S46PacketSetCompressionLevel var1) {
      if (!this.c.c()) {
         this.c.a(var1.a());
      }
   }

   @Override
   public void a(S47PacketPlayerListHeaderFooter var1) {
      this.f.q.h().b(var1.a().d().length() == 0 ? null : var1.a());
      this.f.q.h().a(var1.b().d().length() == 0 ? null : var1.b());
   }

   @Override
   public void a(S1EPacketRemoveEntityEffect var1) {
      PacketThreadUtil.a(var1, this, this.f);
      Entity var2 = this.g.a(var1.a());
      if (var2 instanceof EntityLivingBase) {
         ((EntityLivingBase)var2).l(var1.b());
      }
   }

   @Override
   public void a(S38PacketPlayerListItem var1) {
      PacketThreadUtil.a(var1, this, this.f);

      for (S38PacketPlayerListItem.AddPlayerData var3 : var1.a()) {
         if (var1.b() == S38PacketPlayerListItem.Action.e) {
            this.i.remove(var3.a().getId());
         } else {
            NetworkPlayerInfo var4 = this.i.get(var3.a().getId());
            if (var1.b() == S38PacketPlayerListItem.Action.a) {
               var4 = new NetworkPlayerInfo(var3);
               this.i.put(var4.a().getId(), var4);
            }

            if (var4 != null) {
               switch (var1.b()) {
                  case a:
                     var4.a(var3.c());
                     var4.a(var3.b());
                     break;
                  case b:
                     var4.a(var3.c());
                     break;
                  case c:
                     var4.a(var3.b());
                     break;
                  case d:
                     var4.a(var3.d());
               }
            }
         }
      }
   }

   @Override
   public void a(S00PacketKeepAlive var1) {
      this.a(new C00PacketKeepAlive(var1.a()));
   }

   @Override
   public void a(S39PacketPlayerAbilities var1) {
      PacketThreadUtil.a(var1, this, this.f);
      EntityPlayerSP var2 = this.f.h;
      var2.bA.b = var1.b();
      var2.bA.d = var1.d();
      var2.bA.a = var1.a();
      var2.bA.c = var1.c();
      var2.bA.a(var1.e());
      var2.bA.b(var1.f());
   }

   @Override
   public void a(S3APacketTabComplete var1) {
      PacketThreadUtil.a(var1, this, this.f);
      String[] var2 = var1.a();
      if (this.f.m instanceof GuiChat) {
         GuiChat var3 = (GuiChat)this.f.m;
         var3.a(var2);
      }
   }

   @Override
   public void a(S29PacketSoundEffect var1) {
      PacketThreadUtil.a(var1, this, this.f);
      this.f.f.a(var1.b(), var1.c(), var1.d(), var1.a(), var1.e(), var1.f(), false);
   }

   @Override
   public void a(S48PacketResourcePackSend var1) {
      final String var2 = var1.a();
      final String var3 = var1.b();
      if (var2.startsWith("level://")) {
         String var4 = var2.substring("level://".length());
         File var5 = new File(this.f.v, "saves");
         File var6 = new File(var5, var4);
         if (var6.isFile()) {
            this.c.a(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.Action.d));
            Futures.addCallback(this.f.R().a(var6), new FutureCallback<Object>() {
               public void onSuccess(Object var1) {
                  NetHandlerPlayClient.this.c.a(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.Action.a));
               }

               public void onFailure(Throwable var1) {
                  NetHandlerPlayClient.this.c.a(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.Action.c));
               }
            });
         } else {
            this.c.a(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.Action.c));
         }
      } else {
         if (this.f.D() != null && this.f.D().b() == ServerData.ServerResourceMode.a) {
            this.c.a(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.Action.d));
            Futures.addCallback(this.f.R().a(var2, var3), new FutureCallback<Object>() {
               public void onSuccess(Object var1) {
                  NetHandlerPlayClient.this.c.a(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.Action.a));
               }

               public void onFailure(Throwable var1) {
                  NetHandlerPlayClient.this.c.a(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.Action.c));
               }
            });
         } else if (this.f.D() != null && this.f.D().b() != ServerData.ServerResourceMode.c) {
            this.c.a(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.Action.b));
         } else {
            this.f.a(new Runnable() {
               @Override
               public void run() {
                  NetHandlerPlayClient.this.f.a(new GuiYesNo(new GuiYesNoCallback() {
                     @Override
                     public void a(boolean var1, int var2x) {
                        NetHandlerPlayClient.this.f = Minecraft.A();
                        if (var1) {
                           if (NetHandlerPlayClient.this.f.D() != null) {
                              NetHandlerPlayClient.this.f.D().a(ServerData.ServerResourceMode.a);
                           }

                           NetHandlerPlayClient.this.c.a(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.Action.d));
                           Futures.addCallback(NetHandlerPlayClient.this.f.R().a(var2, var3), new FutureCallback<Object>() {
                              public void onSuccess(Object var1) {
                                 NetHandlerPlayClient.this.c.a(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.Action.a));
                              }

                              public void onFailure(Throwable var1) {
                                 NetHandlerPlayClient.this.c.a(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.Action.c));
                              }
                           });
                        } else {
                           if (NetHandlerPlayClient.this.f.D() != null) {
                              NetHandlerPlayClient.this.f.D().a(ServerData.ServerResourceMode.b);
                           }

                           NetHandlerPlayClient.this.c.a(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.Action.b));
                        }

                        ServerList.b(NetHandlerPlayClient.this.f.D());
                        NetHandlerPlayClient.this.f.a(null);
                     }
                  }, I18n.a("multiplayer.texturePrompt.line1"), I18n.a("multiplayer.texturePrompt.line2"), 0));
               }
            });
         }
      }
   }

   @Override
   public void a(S49PacketUpdateEntityNBT var1) {
      PacketThreadUtil.a(var1, this, this.f);
      Entity var2 = var1.a(this.g);
      if (var2 != null) {
         var2.g(var1.a());
      }
   }

   @Override
   public void a(S3FPacketCustomPayload var1) {
      PacketThreadUtil.a(var1, this, this.f);
      if ("MC|TrList".equals(var1.a())) {
         PacketBuffer var2 = var1.b();

         try {
            int var3 = var2.readInt();
            GuiScreen var4 = this.f.m;
            if (var4 != null && var4 instanceof GuiMerchant && var3 == this.f.h.bk.d) {
               IMerchant var5 = ((GuiMerchant)var4).a();
               MerchantRecipeList var6 = MerchantRecipeList.b(var2);
               var5.a(var6);
            }
         } catch (IOException var10) {
            b.error("Couldn't load trade info", var10);
         } finally {
            var2.release();
         }
      } else if ("MC|Brand".equals(var1.a())) {
         this.f.h.f(var1.b().c(32767));
      } else if ("MC|BOpen".equals(var1.a())) {
         ItemStack var12 = this.f.h.bZ();
         if (var12 != null && var12.b() == Items.bN) {
            this.f.a(new GuiScreenBook(this.f.h, var12, false));
         }
      }
   }

   @Override
   public void a(S3BPacketScoreboardObjective var1) {
      PacketThreadUtil.a(var1, this, this.f);
      Scoreboard var2 = this.g.Z();
      if (var1.c() == 0) {
         ScoreObjective var3 = var2.a(var1.a(), IScoreObjectiveCriteria.b);
         var3.a(var1.b());
         var3.a(var1.d());
      } else {
         ScoreObjective var4 = var2.b(var1.a());
         if (var1.c() == 1) {
            var2.k(var4);
         } else if (var1.c() == 2) {
            var4.a(var1.b());
            var4.a(var1.d());
         }
      }
   }

   @Override
   public void a(S3CPacketUpdateScore var1) {
      PacketThreadUtil.a(var1, this, this.f);
      Scoreboard var2 = this.g.Z();
      ScoreObjective var3 = var2.b(var1.b());
      if (var1.d() == S3CPacketUpdateScore.Action.a) {
         Score var4 = var2.c(var1.a(), var3);
         var4.c(var1.c());
      } else if (var1.d() == S3CPacketUpdateScore.Action.b) {
         if (StringUtils.b(var1.b())) {
            var2.d(var1.a(), null);
         } else if (var3 != null) {
            var2.d(var1.a(), var3);
         }
      }
   }

   @Override
   public void a(S3DPacketDisplayScoreboard var1) {
      PacketThreadUtil.a(var1, this, this.f);
      Scoreboard var2 = this.g.Z();
      if (var1.b().length() == 0) {
         var2.a(var1.a(), null);
      } else {
         ScoreObjective var3 = var2.b(var1.b());
         var2.a(var1.a(), var3);
      }
   }

   @Override
   public void a(S3EPacketTeams var1) {
      PacketThreadUtil.a(var1, this, this.f);
      Scoreboard var2 = this.g.Z();
      ScorePlayerTeam var3;
      if (var1.f() == 0) {
         var3 = var2.e(var1.a());
      } else {
         var3 = var2.d(var1.a());
      }

      if (var1.f() == 0 || var1.f() == 2) {
         var3.a(var1.b());
         var3.b(var1.c());
         var3.c(var1.d());
         var3.a(EnumChatFormatting.a(var1.h()));
         var3.a(var1.g());
         Team.EnumVisible var4 = Team.EnumVisible.a(var1.i());
         if (var4 != null) {
            var3.a(var4);
         }
      }

      if (var1.f() == 0 || var1.f() == 3) {
         for (String var5 : var1.e()) {
            var2.a(var5, var1.a());
         }
      }

      if (var1.f() == 4) {
         for (String var8 : var1.e()) {
            var2.a(var8, var3);
         }
      }

      if (var1.f() == 1) {
         var2.d(var3);
      }
   }

   @Override
   public void a(S2APacketParticles var1) {
      PacketThreadUtil.a(var1, this, this.f);
      if (var1.j() == 0) {
         double var2 = var1.i() * var1.f();
         double var4 = var1.i() * var1.g();
         double var6 = var1.i() * var1.h();

         try {
            this.g.a(var1.a(), var1.b(), var1.c(), var1.d(), var1.e(), var2, var4, var6, var1.k());
         } catch (Throwable var17) {
            b.warn("Could not spawn particle effect " + var1.a());
         }
      } else {
         for (int var18 = 0; var18 < var1.j(); var18++) {
            double var3 = this.k.nextGaussian() * var1.f();
            double var5 = this.k.nextGaussian() * var1.g();
            double var7 = this.k.nextGaussian() * var1.h();
            double var9 = this.k.nextGaussian() * var1.i();
            double var11 = this.k.nextGaussian() * var1.i();
            double var13 = this.k.nextGaussian() * var1.i();

            try {
               this.g.a(var1.a(), var1.b(), var1.c() + var3, var1.d() + var5, var1.e() + var7, var9, var11, var13, var1.k());
            } catch (Throwable var16) {
               b.warn("Could not spawn particle effect " + var1.a());
               return;
            }
         }
      }
   }

   @Override
   public void a(S20PacketEntityProperties var1) {
      PacketThreadUtil.a(var1, this, this.f);
      Entity var2 = this.g.a(var1.a());
      if (var2 != null) {
         if (!(var2 instanceof EntityLivingBase)) {
            throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + var2 + ")");
         } else {
            BaseAttributeMap var3 = ((EntityLivingBase)var2).by();

            for (S20PacketEntityProperties.Snapshot var5 : var1.b()) {
               IAttributeInstance var6 = var3.a(var5.a());
               if (var6 == null) {
                  var6 = var3.b(new RangedAttribute(null, var5.a(), 0.0, Double.MIN_NORMAL, Double.MAX_VALUE));
               }

               var6.a(var5.b());
               var6.d();

               for (AttributeModifier var8 : var5.c()) {
                  var6.b(var8);
               }
            }
         }
      }
   }

   public NetworkManager a() {
      return this.c;
   }

   public Collection<NetworkPlayerInfo> d() {
      return this.i.values();
   }

   public NetworkPlayerInfo a(UUID var1) {
      return this.i.get(var1);
   }

   public NetworkPlayerInfo a(String var1) {
      for (NetworkPlayerInfo var3 : this.i.values()) {
         if (var3.a().getName().equals(var1)) {
            return var3;
         }
      }

      return null;
   }

   public GameProfile e() {
      return this.d;
   }
}
