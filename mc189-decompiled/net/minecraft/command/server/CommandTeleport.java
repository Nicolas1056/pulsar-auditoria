package net.minecraft.command.server;

import java.util.EnumSet;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class CommandTeleport extends CommandBase {
   @Override
   public String c() {
      return "tp";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.tp.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 1) {
         throw new WrongUsageException("commands.tp.usage");
      } else {
         byte var3 = 0;
         Object var4;
         if (var2.length != 2 && var2.length != 4 && var2.length != 6) {
            var4 = b(var1);
         } else {
            var4 = b(var1, var2[0]);
            var3 = 1;
         }

         if (var2.length == 1 || var2.length == 2) {
            Entity var16 = b(var1, var2[var2.length - 1]);
            if (var16.o != ((Entity)var4).o) {
               throw new CommandException("commands.tp.notSameDimension");
            } else {
               ((Entity)var4).a(null);
               if (var4 instanceof EntityPlayerMP) {
                  ((EntityPlayerMP)var4).a.a(var16.s, var16.t, var16.u, var16.y, var16.z);
               } else {
                  ((Entity)var4).b(var16.s, var16.t, var16.u, var16.y, var16.z);
               }

               a(var1, this, "commands.tp.success", new Object[]{((Entity)var4).e_(), var16.e_()});
            }
         } else if (var2.length < var3 + 3) {
            throw new WrongUsageException("commands.tp.usage");
         } else if (((Entity)var4).o != null) {
            int var5 = var3 + 1;
            CommandBase.CoordinateArg var6 = a(((Entity)var4).s, var2[var3], true);
            CommandBase.CoordinateArg var7 = a(((Entity)var4).t, var2[var5++], 0, 0, false);
            CommandBase.CoordinateArg var8 = a(((Entity)var4).u, var2[var5++], true);
            CommandBase.CoordinateArg var9 = a(((Entity)var4).y, var2.length > var5 ? var2[var5++] : "~", false);
            CommandBase.CoordinateArg var10 = a(((Entity)var4).z, var2.length > var5 ? var2[var5] : "~", false);
            if (var4 instanceof EntityPlayerMP) {
               EnumSet var11 = EnumSet.noneOf(S08PacketPlayerPosLook.EnumFlags.class);
               if (var6.c()) {
                  var11.add(S08PacketPlayerPosLook.EnumFlags.a);
               }

               if (var7.c()) {
                  var11.add(S08PacketPlayerPosLook.EnumFlags.b);
               }

               if (var8.c()) {
                  var11.add(S08PacketPlayerPosLook.EnumFlags.c);
               }

               if (var10.c()) {
                  var11.add(S08PacketPlayerPosLook.EnumFlags.e);
               }

               if (var9.c()) {
                  var11.add(S08PacketPlayerPosLook.EnumFlags.d);
               }

               float var12 = (float)var9.b();
               if (!var9.c()) {
                  var12 = MathHelper.g(var12);
               }

               float var13 = (float)var10.b();
               if (!var10.c()) {
                  var13 = MathHelper.g(var13);
               }

               if (var13 > 90.0F || var13 < -90.0F) {
                  var13 = MathHelper.g(180.0F - var13);
                  var12 = MathHelper.g(var12 + 180.0F);
               }

               ((Entity)var4).a(null);
               ((EntityPlayerMP)var4).a.a(var6.b(), var7.b(), var8.b(), var12, var13, var11);
               ((Entity)var4).f(var12);
            } else {
               float var17 = (float)MathHelper.g(var9.a());
               float var18 = (float)MathHelper.g(var10.a());
               if (var18 > 90.0F || var18 < -90.0F) {
                  var18 = MathHelper.g(180.0F - var18);
                  var17 = MathHelper.g(var17 + 180.0F);
               }

               ((Entity)var4).b(var6.a(), var7.a(), var8.a(), var17, var18);
               ((Entity)var4).f(var17);
            }

            a(var1, this, "commands.tp.success.coordinates", new Object[]{((Entity)var4).e_(), var6.a(), var7.a(), var8.a()});
         }
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length != 1 && var2.length != 2 ? null : a(var2, MinecraftServer.N().K());
   }

   @Override
   public boolean b(String[] var1, int var2) {
      return var2 == 0;
   }
}
