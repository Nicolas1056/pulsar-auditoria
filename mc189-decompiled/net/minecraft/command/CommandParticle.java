package net.minecraft.command;

import java.util.List;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class CommandParticle extends CommandBase {
   @Override
   public String c() {
      return "particle";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.particle.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 8) {
         throw new WrongUsageException("commands.particle.usage");
      } else {
         boolean var3 = false;
         EnumParticleTypes var4 = null;

         for (EnumParticleTypes var8 : EnumParticleTypes.values()) {
            if (var8.f()) {
               if (var2[0].startsWith(var8.b())) {
                  var3 = true;
                  var4 = var8;
                  break;
               }
            } else if (var2[0].equals(var8.b())) {
               var3 = true;
               var4 = var8;
               break;
            }
         }

         if (!var3) {
            throw new CommandException("commands.particle.notFound", var2[0]);
         } else {
            String var30 = var2[0];
            Vec3 var31 = var1.d();
            double var32 = (float)b(var31.a, var2[1], true);
            double var9 = (float)b(var31.b, var2[2], true);
            double var11 = (float)b(var31.c, var2[3], true);
            double var13 = (float)c(var2[4]);
            double var15 = (float)c(var2[5]);
            double var17 = (float)c(var2[6]);
            double var19 = (float)c(var2[7]);
            int var21 = 0;
            if (var2.length > 8) {
               var21 = a(var2[8], 0);
            }

            boolean var22 = false;
            if (var2.length > 9 && "force".equals(var2[9])) {
               var22 = true;
            }

            World var23 = var1.e();
            if (var23 instanceof WorldServer) {
               WorldServer var24 = (WorldServer)var23;
               int[] var25 = new int[var4.d()];
               if (var4.f()) {
                  String[] var26 = var2[0].split("_", 3);

                  for (int var27 = 1; var27 < var26.length; var27++) {
                     try {
                        var25[var27 - 1] = Integer.parseInt(var26[var27]);
                     } catch (NumberFormatException var29) {
                        throw new CommandException("commands.particle.notFound", var2[0]);
                     }
                  }
               }

               var24.a(var4, var22, var32, var9, var11, var21, var13, var15, var17, var19, var25);
               a(var1, this, "commands.particle.success", new Object[]{var30, Math.max(var21, 1)});
            }
         }
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      if (var2.length == 1) {
         return a(var2, EnumParticleTypes.a());
      } else if (var2.length > 1 && var2.length <= 4) {
         return a(var2, 1, var3);
      } else {
         return var2.length == 10 ? a(var2, new String[]{"normal", "force"}) : null;
      }
   }
}
