package net.minecraft.network.play;

import net.minecraft.network.INetHandler;
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

public interface INetHandlerPlayServer extends INetHandler {
   void a(C0APacketAnimation var1);

   void a(C01PacketChatMessage var1);

   void a(C14PacketTabComplete var1);

   void a(C16PacketClientStatus var1);

   void a(C15PacketClientSettings var1);

   void a(C0FPacketConfirmTransaction var1);

   void a(C11PacketEnchantItem var1);

   void a(C0EPacketClickWindow var1);

   void a(C0DPacketCloseWindow var1);

   void a(C17PacketCustomPayload var1);

   void a(C02PacketUseEntity var1);

   void a(C00PacketKeepAlive var1);

   void a(C03PacketPlayer var1);

   void a(C13PacketPlayerAbilities var1);

   void a(C07PacketPlayerDigging var1);

   void a(C0BPacketEntityAction var1);

   void a(C0CPacketInput var1);

   void a(C09PacketHeldItemChange var1);

   void a(C10PacketCreativeInventoryAction var1);

   void a(C12PacketUpdateSign var1);

   void a(C08PacketPlayerBlockPlacement var1);

   void a(C18PacketSpectate var1);

   void a(C19PacketResourcePackStatus var1);
}
