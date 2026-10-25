package net.minecraft.network.play;

import net.minecraft.network.INetHandler;
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

public interface INetHandlerPlayClient extends INetHandler {
   void a(S0EPacketSpawnObject var1);

   void a(S11PacketSpawnExperienceOrb var1);

   void a(S2CPacketSpawnGlobalEntity var1);

   void a(S0FPacketSpawnMob var1);

   void a(S3BPacketScoreboardObjective var1);

   void a(S10PacketSpawnPainting var1);

   void a(S0CPacketSpawnPlayer var1);

   void a(S0BPacketAnimation var1);

   void a(S37PacketStatistics var1);

   void a(S25PacketBlockBreakAnim var1);

   void a(S36PacketSignEditorOpen var1);

   void a(S35PacketUpdateTileEntity var1);

   void a(S24PacketBlockAction var1);

   void a(S23PacketBlockChange var1);

   void a(S02PacketChat var1);

   void a(S3APacketTabComplete var1);

   void a(S22PacketMultiBlockChange var1);

   void a(S34PacketMaps var1);

   void a(S32PacketConfirmTransaction var1);

   void a(S2EPacketCloseWindow var1);

   void a(S30PacketWindowItems var1);

   void a(S2DPacketOpenWindow var1);

   void a(S31PacketWindowProperty var1);

   void a(S2FPacketSetSlot var1);

   void a(S3FPacketCustomPayload var1);

   void a(S40PacketDisconnect var1);

   void a(S0APacketUseBed var1);

   void a(S19PacketEntityStatus var1);

   void a(S1BPacketEntityAttach var1);

   void a(S27PacketExplosion var1);

   void a(S2BPacketChangeGameState var1);

   void a(S00PacketKeepAlive var1);

   void a(S21PacketChunkData var1);

   void a(S26PacketMapChunkBulk var1);

   void a(S28PacketEffect var1);

   void a(S01PacketJoinGame var1);

   void a(S14PacketEntity var1);

   void a(S08PacketPlayerPosLook var1);

   void a(S2APacketParticles var1);

   void a(S39PacketPlayerAbilities var1);

   void a(S38PacketPlayerListItem var1);

   void a(S13PacketDestroyEntities var1);

   void a(S1EPacketRemoveEntityEffect var1);

   void a(S07PacketRespawn var1);

   void a(S19PacketEntityHeadLook var1);

   void a(S09PacketHeldItemChange var1);

   void a(S3DPacketDisplayScoreboard var1);

   void a(S1CPacketEntityMetadata var1);

   void a(S12PacketEntityVelocity var1);

   void a(S04PacketEntityEquipment var1);

   void a(S1FPacketSetExperience var1);

   void a(S06PacketUpdateHealth var1);

   void a(S3EPacketTeams var1);

   void a(S3CPacketUpdateScore var1);

   void a(S05PacketSpawnPosition var1);

   void a(S03PacketTimeUpdate var1);

   void a(S33PacketUpdateSign var1);

   void a(S29PacketSoundEffect var1);

   void a(S0DPacketCollectItem var1);

   void a(S18PacketEntityTeleport var1);

   void a(S20PacketEntityProperties var1);

   void a(S1DPacketEntityEffect var1);

   void a(S42PacketCombatEvent var1);

   void a(S41PacketServerDifficulty var1);

   void a(S43PacketCamera var1);

   void a(S44PacketWorldBorder var1);

   void a(S45PacketTitle var1);

   void a(S46PacketSetCompressionLevel var1);

   void a(S47PacketPlayerListHeaderFooter var1);

   void a(S48PacketResourcePackSend var1);

   void a(S49PacketUpdateEntityNBT var1);
}
