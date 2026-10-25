package net.minecraft.network;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.network.login.server.S02PacketLoginSuccess;
import net.minecraft.network.login.server.S03PacketEnableCompression;
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
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;
import org.apache.logging.log4j.LogManager;

public enum EnumConnectionState {
   a(-1) {
      {
         this.a(EnumPacketDirection.a, C00Handshake.class);
      }
   },
   b(0) {
      {
         this.a(EnumPacketDirection.b, S00PacketKeepAlive.class);
         this.a(EnumPacketDirection.b, S01PacketJoinGame.class);
         this.a(EnumPacketDirection.b, S02PacketChat.class);
         this.a(EnumPacketDirection.b, S03PacketTimeUpdate.class);
         this.a(EnumPacketDirection.b, S04PacketEntityEquipment.class);
         this.a(EnumPacketDirection.b, S05PacketSpawnPosition.class);
         this.a(EnumPacketDirection.b, S06PacketUpdateHealth.class);
         this.a(EnumPacketDirection.b, S07PacketRespawn.class);
         this.a(EnumPacketDirection.b, S08PacketPlayerPosLook.class);
         this.a(EnumPacketDirection.b, S09PacketHeldItemChange.class);
         this.a(EnumPacketDirection.b, S0APacketUseBed.class);
         this.a(EnumPacketDirection.b, S0BPacketAnimation.class);
         this.a(EnumPacketDirection.b, S0CPacketSpawnPlayer.class);
         this.a(EnumPacketDirection.b, S0DPacketCollectItem.class);
         this.a(EnumPacketDirection.b, S0EPacketSpawnObject.class);
         this.a(EnumPacketDirection.b, S0FPacketSpawnMob.class);
         this.a(EnumPacketDirection.b, S10PacketSpawnPainting.class);
         this.a(EnumPacketDirection.b, S11PacketSpawnExperienceOrb.class);
         this.a(EnumPacketDirection.b, S12PacketEntityVelocity.class);
         this.a(EnumPacketDirection.b, S13PacketDestroyEntities.class);
         this.a(EnumPacketDirection.b, S14PacketEntity.class);
         this.a(EnumPacketDirection.b, S14PacketEntity.S15PacketEntityRelMove.class);
         this.a(EnumPacketDirection.b, S14PacketEntity.S16PacketEntityLook.class);
         this.a(EnumPacketDirection.b, S14PacketEntity.S17PacketEntityLookMove.class);
         this.a(EnumPacketDirection.b, S18PacketEntityTeleport.class);
         this.a(EnumPacketDirection.b, S19PacketEntityHeadLook.class);
         this.a(EnumPacketDirection.b, S19PacketEntityStatus.class);
         this.a(EnumPacketDirection.b, S1BPacketEntityAttach.class);
         this.a(EnumPacketDirection.b, S1CPacketEntityMetadata.class);
         this.a(EnumPacketDirection.b, S1DPacketEntityEffect.class);
         this.a(EnumPacketDirection.b, S1EPacketRemoveEntityEffect.class);
         this.a(EnumPacketDirection.b, S1FPacketSetExperience.class);
         this.a(EnumPacketDirection.b, S20PacketEntityProperties.class);
         this.a(EnumPacketDirection.b, S21PacketChunkData.class);
         this.a(EnumPacketDirection.b, S22PacketMultiBlockChange.class);
         this.a(EnumPacketDirection.b, S23PacketBlockChange.class);
         this.a(EnumPacketDirection.b, S24PacketBlockAction.class);
         this.a(EnumPacketDirection.b, S25PacketBlockBreakAnim.class);
         this.a(EnumPacketDirection.b, S26PacketMapChunkBulk.class);
         this.a(EnumPacketDirection.b, S27PacketExplosion.class);
         this.a(EnumPacketDirection.b, S28PacketEffect.class);
         this.a(EnumPacketDirection.b, S29PacketSoundEffect.class);
         this.a(EnumPacketDirection.b, S2APacketParticles.class);
         this.a(EnumPacketDirection.b, S2BPacketChangeGameState.class);
         this.a(EnumPacketDirection.b, S2CPacketSpawnGlobalEntity.class);
         this.a(EnumPacketDirection.b, S2DPacketOpenWindow.class);
         this.a(EnumPacketDirection.b, S2EPacketCloseWindow.class);
         this.a(EnumPacketDirection.b, S2FPacketSetSlot.class);
         this.a(EnumPacketDirection.b, S30PacketWindowItems.class);
         this.a(EnumPacketDirection.b, S31PacketWindowProperty.class);
         this.a(EnumPacketDirection.b, S32PacketConfirmTransaction.class);
         this.a(EnumPacketDirection.b, S33PacketUpdateSign.class);
         this.a(EnumPacketDirection.b, S34PacketMaps.class);
         this.a(EnumPacketDirection.b, S35PacketUpdateTileEntity.class);
         this.a(EnumPacketDirection.b, S36PacketSignEditorOpen.class);
         this.a(EnumPacketDirection.b, S37PacketStatistics.class);
         this.a(EnumPacketDirection.b, S38PacketPlayerListItem.class);
         this.a(EnumPacketDirection.b, S39PacketPlayerAbilities.class);
         this.a(EnumPacketDirection.b, S3APacketTabComplete.class);
         this.a(EnumPacketDirection.b, S3BPacketScoreboardObjective.class);
         this.a(EnumPacketDirection.b, S3CPacketUpdateScore.class);
         this.a(EnumPacketDirection.b, S3DPacketDisplayScoreboard.class);
         this.a(EnumPacketDirection.b, S3EPacketTeams.class);
         this.a(EnumPacketDirection.b, S3FPacketCustomPayload.class);
         this.a(EnumPacketDirection.b, S40PacketDisconnect.class);
         this.a(EnumPacketDirection.b, S41PacketServerDifficulty.class);
         this.a(EnumPacketDirection.b, S42PacketCombatEvent.class);
         this.a(EnumPacketDirection.b, S43PacketCamera.class);
         this.a(EnumPacketDirection.b, S44PacketWorldBorder.class);
         this.a(EnumPacketDirection.b, S45PacketTitle.class);
         this.a(EnumPacketDirection.b, S46PacketSetCompressionLevel.class);
         this.a(EnumPacketDirection.b, S47PacketPlayerListHeaderFooter.class);
         this.a(EnumPacketDirection.b, S48PacketResourcePackSend.class);
         this.a(EnumPacketDirection.b, S49PacketUpdateEntityNBT.class);
         this.a(EnumPacketDirection.a, C00PacketKeepAlive.class);
         this.a(EnumPacketDirection.a, C01PacketChatMessage.class);
         this.a(EnumPacketDirection.a, C02PacketUseEntity.class);
         this.a(EnumPacketDirection.a, C03PacketPlayer.class);
         this.a(EnumPacketDirection.a, C03PacketPlayer.C04PacketPlayerPosition.class);
         this.a(EnumPacketDirection.a, C03PacketPlayer.C05PacketPlayerLook.class);
         this.a(EnumPacketDirection.a, C03PacketPlayer.C06PacketPlayerPosLook.class);
         this.a(EnumPacketDirection.a, C07PacketPlayerDigging.class);
         this.a(EnumPacketDirection.a, C08PacketPlayerBlockPlacement.class);
         this.a(EnumPacketDirection.a, C09PacketHeldItemChange.class);
         this.a(EnumPacketDirection.a, C0APacketAnimation.class);
         this.a(EnumPacketDirection.a, C0BPacketEntityAction.class);
         this.a(EnumPacketDirection.a, C0CPacketInput.class);
         this.a(EnumPacketDirection.a, C0DPacketCloseWindow.class);
         this.a(EnumPacketDirection.a, C0EPacketClickWindow.class);
         this.a(EnumPacketDirection.a, C0FPacketConfirmTransaction.class);
         this.a(EnumPacketDirection.a, C10PacketCreativeInventoryAction.class);
         this.a(EnumPacketDirection.a, C11PacketEnchantItem.class);
         this.a(EnumPacketDirection.a, C12PacketUpdateSign.class);
         this.a(EnumPacketDirection.a, C13PacketPlayerAbilities.class);
         this.a(EnumPacketDirection.a, C14PacketTabComplete.class);
         this.a(EnumPacketDirection.a, C15PacketClientSettings.class);
         this.a(EnumPacketDirection.a, C16PacketClientStatus.class);
         this.a(EnumPacketDirection.a, C17PacketCustomPayload.class);
         this.a(EnumPacketDirection.a, C18PacketSpectate.class);
         this.a(EnumPacketDirection.a, C19PacketResourcePackStatus.class);
      }
   },
   c(1) {
      {
         this.a(EnumPacketDirection.a, C00PacketServerQuery.class);
         this.a(EnumPacketDirection.b, S00PacketServerInfo.class);
         this.a(EnumPacketDirection.a, C01PacketPing.class);
         this.a(EnumPacketDirection.b, S01PacketPong.class);
      }
   },
   d(2) {
      {
         this.a(EnumPacketDirection.b, S00PacketDisconnect.class);
         this.a(EnumPacketDirection.b, S01PacketEncryptionRequest.class);
         this.a(EnumPacketDirection.b, S02PacketLoginSuccess.class);
         this.a(EnumPacketDirection.b, S03PacketEnableCompression.class);
         this.a(EnumPacketDirection.a, C00PacketLoginStart.class);
         this.a(EnumPacketDirection.a, C01PacketEncryptionResponse.class);
      }
   };

   private static int e = -1;
   private static int f = 2;
   private static final EnumConnectionState[] g = new EnumConnectionState[f - e + 1];
   private static final Map<Class<? extends Packet>, EnumConnectionState> h = Maps.newHashMap();
   private final int i;
   private final Map<EnumPacketDirection, BiMap<Integer, Class<? extends Packet>>> j = Maps.newEnumMap(EnumPacketDirection.class);

   private EnumConnectionState(int var3) {
      this.i = var3;
   }

   protected EnumConnectionState a(EnumPacketDirection var1, Class<? extends Packet> var2) {
      Object var3 = this.j.get(var1);
      if (var3 == null) {
         var3 = HashBiMap.create();
         this.j.put(var1, (BiMap<Integer, Class<? extends Packet>>)var3);
      }

      if (var3.containsValue(var2)) {
         String var4 = var1 + " packet " + var2 + " is already known to ID " + var3.inverse().get(var2);
         LogManager.getLogger().fatal(var4);
         throw new IllegalArgumentException(var4);
      } else {
         var3.put(var3.size(), var2);
         return this;
      }
   }

   public Integer a(EnumPacketDirection var1, Packet var2) {
      return (Integer)this.j.get(var1).inverse().get(var2.getClass());
   }

   public Packet a(EnumPacketDirection var1, int var2) throws IllegalAccessException, InstantiationException {
      Class var3 = (Class)this.j.get(var1).get(var2);
      return var3 == null ? null : (Packet)var3.newInstance();
   }

   public int a() {
      return this.i;
   }

   public static EnumConnectionState a(int var0) {
      return var0 >= e && var0 <= f ? g[var0 - e] : null;
   }

   public static EnumConnectionState a(Packet var0) {
      return h.get(var0.getClass());
   }

   static {
      for (EnumConnectionState var3 : values()) {
         int var4 = var3.a();
         if (var4 < e || var4 > f) {
            throw new Error("Invalid protocol ID " + Integer.toString(var4));
         }

         g[var4 - e] = var3;

         for (EnumPacketDirection var6 : var3.j.keySet()) {
            for (Class var8 : var3.j.get(var6).values()) {
               if (h.containsKey(var8) && h.get(var8) != var3) {
                  throw new Error("Packet " + var8 + " is already assigned to protocol " + h.get(var8) + " - can't reassign to " + var3);
               }

               try {
                  var8.newInstance();
               } catch (Throwable var10) {
                  throw new Error("Packet " + var8 + " fails instantiation checks! " + var8);
               }

               h.put(var8, var3);
            }
         }
      }
   }
}
