package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public class CommandShowSeed extends CommandBase {
   @Override
   public boolean a(ICommandSender var1) {
      return MinecraftServer.N().T() || super.a(var1);
   }

   @Override
   public String c() {
      return "seed";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.seed.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      Object var3 = var1 instanceof EntityPlayer ? ((EntityPlayer)var1).o : MinecraftServer.N().a(0);
      var1.a(new ChatComponentTranslation("commands.seed.success", ((World)var3).J()));
   }
}
