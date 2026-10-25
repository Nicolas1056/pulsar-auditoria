package net.minecraft.network.rcon;

import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class RConConsoleSource implements ICommandSender {
   private static final RConConsoleSource a = new RConConsoleSource();
   private StringBuffer b = new StringBuffer();

   @Override
   public String e_() {
      return "Rcon";
   }

   @Override
   public IChatComponent f_() {
      return new ChatComponentText(this.e_());
   }

   @Override
   public void a(IChatComponent var1) {
      this.b.append(var1.c());
   }

   @Override
   public boolean a(int var1, String var2) {
      return true;
   }

   @Override
   public BlockPos c() {
      return new BlockPos(0, 0, 0);
   }

   @Override
   public Vec3 d() {
      return new Vec3(0.0, 0.0, 0.0);
   }

   @Override
   public World e() {
      return MinecraftServer.N().e();
   }

   @Override
   public Entity f() {
      return null;
   }

   @Override
   public boolean u_() {
      return true;
   }

   @Override
   public void a(CommandResultStats.Type var1, int var2) {
   }
}
