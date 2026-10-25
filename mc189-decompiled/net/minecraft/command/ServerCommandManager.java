package net.minecraft.command;

import net.minecraft.command.server.CommandAchievement;
import net.minecraft.command.server.CommandBanIp;
import net.minecraft.command.server.CommandBanPlayer;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.command.server.CommandBroadcast;
import net.minecraft.command.server.CommandDeOp;
import net.minecraft.command.server.CommandEmote;
import net.minecraft.command.server.CommandListBans;
import net.minecraft.command.server.CommandListPlayers;
import net.minecraft.command.server.CommandMessage;
import net.minecraft.command.server.CommandMessageRaw;
import net.minecraft.command.server.CommandOp;
import net.minecraft.command.server.CommandPardonIp;
import net.minecraft.command.server.CommandPardonPlayer;
import net.minecraft.command.server.CommandPublishLocalServer;
import net.minecraft.command.server.CommandSaveAll;
import net.minecraft.command.server.CommandSaveOff;
import net.minecraft.command.server.CommandSaveOn;
import net.minecraft.command.server.CommandScoreboard;
import net.minecraft.command.server.CommandSetBlock;
import net.minecraft.command.server.CommandSetDefaultSpawnpoint;
import net.minecraft.command.server.CommandStop;
import net.minecraft.command.server.CommandSummon;
import net.minecraft.command.server.CommandTeleport;
import net.minecraft.command.server.CommandTestFor;
import net.minecraft.command.server.CommandTestForBlock;
import net.minecraft.command.server.CommandWhitelist;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.rcon.RConConsoleSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class ServerCommandManager extends CommandHandler implements IAdminCommand {
   public ServerCommandManager() {
      this.a(new CommandTime());
      this.a(new CommandGameMode());
      this.a(new CommandDifficulty());
      this.a(new CommandDefaultGameMode());
      this.a(new CommandKill());
      this.a(new CommandToggleDownfall());
      this.a(new CommandWeather());
      this.a(new CommandXP());
      this.a(new CommandTeleport());
      this.a(new CommandGive());
      this.a(new CommandReplaceItem());
      this.a(new CommandStats());
      this.a(new CommandEffect());
      this.a(new CommandEnchant());
      this.a(new CommandParticle());
      this.a(new CommandEmote());
      this.a(new CommandShowSeed());
      this.a(new CommandHelp());
      this.a(new CommandDebug());
      this.a(new CommandMessage());
      this.a(new CommandBroadcast());
      this.a(new CommandSetSpawnpoint());
      this.a(new CommandSetDefaultSpawnpoint());
      this.a(new CommandGameRule());
      this.a(new CommandClearInventory());
      this.a(new CommandTestFor());
      this.a(new CommandSpreadPlayers());
      this.a(new CommandPlaySound());
      this.a(new CommandScoreboard());
      this.a(new CommandExecuteAt());
      this.a(new CommandTrigger());
      this.a(new CommandAchievement());
      this.a(new CommandSummon());
      this.a(new CommandSetBlock());
      this.a(new CommandFill());
      this.a(new CommandClone());
      this.a(new CommandCompare());
      this.a(new CommandBlockData());
      this.a(new CommandTestForBlock());
      this.a(new CommandMessageRaw());
      this.a(new CommandWorldBorder());
      this.a(new CommandTitle());
      this.a(new CommandEntityData());
      if (MinecraftServer.N().ae()) {
         this.a(new CommandOp());
         this.a(new CommandDeOp());
         this.a(new CommandStop());
         this.a(new CommandSaveAll());
         this.a(new CommandSaveOff());
         this.a(new CommandSaveOn());
         this.a(new CommandBanIp());
         this.a(new CommandPardonIp());
         this.a(new CommandBanPlayer());
         this.a(new CommandListBans());
         this.a(new CommandPardonPlayer());
         this.a(new CommandServerKick());
         this.a(new CommandListPlayers());
         this.a(new CommandWhitelist());
         this.a(new CommandSetPlayerTimeout());
      } else {
         this.a(new CommandPublishLocalServer());
      }

      CommandBase.a(this);
   }

   @Override
   public void a(ICommandSender var1, ICommand var2, int var3, String var4, Object... var5) {
      boolean var6 = true;
      MinecraftServer var7 = MinecraftServer.N();
      if (!var1.u_()) {
         var6 = false;
      }

      ChatComponentTranslation var8 = new ChatComponentTranslation("chat.type.admin", var1.e_(), new ChatComponentTranslation(var4, var5));
      var8.b().a(EnumChatFormatting.h);
      var8.b().b(true);
      if (var6) {
         for (EntityPlayer var10 : var7.ap().v()) {
            if (var10 != var1 && var7.ap().h(var10.cd()) && var2.a(var1)) {
               boolean var11 = var1 instanceof MinecraftServer && MinecraftServer.N().r();
               boolean var12 = var1 instanceof RConConsoleSource && MinecraftServer.N().q();
               if (var11 || var12 || !(var1 instanceof RConConsoleSource) && !(var1 instanceof MinecraftServer)) {
                  var10.a(var8);
               }
            }
         }
      }

      if (var1 != var7 && var7.d[0].Q().b("logAdminCommands")) {
         var7.a(var8);
      }

      boolean var13 = var7.d[0].Q().b("sendCommandFeedback");
      if (var1 instanceof CommandBlockLogic) {
         var13 = ((CommandBlockLogic)var1).m();
      }

      if ((var3 & 1) != 1 && var13 || var1 instanceof MinecraftServer) {
         var1.a(new ChatComponentTranslation(var4, var5));
      }
   }
}
