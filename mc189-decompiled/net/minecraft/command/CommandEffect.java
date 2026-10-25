package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;

public class CommandEffect extends CommandBase {
   @Override
   public String c() {
      return "effect";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.effect.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 2) {
         throw new WrongUsageException("commands.effect.usage");
      } else {
         EntityLivingBase var3 = a(var1, var2[0], EntityLivingBase.class);
         if (var2[1].equals("clear")) {
            if (var3.bl().isEmpty()) {
               throw new CommandException("commands.effect.failure.notActive.all", var3.e_());
            } else {
               var3.bk();
               a(var1, this, "commands.effect.success.removed.all", new Object[]{var3.e_()});
            }
         } else {
            int var4;
            try {
               var4 = a(var2[1], 1);
            } catch (NumberInvalidException var11) {
               Potion var6 = Potion.b(var2[1]);
               if (var6 == null) {
                  throw var11;
               }

               var4 = var6.H;
            }

            int var5 = 600;
            int var12 = 30;
            int var7 = 0;
            if (var4 >= 0 && var4 < Potion.a.length && Potion.a[var4] != null) {
               Potion var8 = Potion.a[var4];
               if (var2.length >= 3) {
                  var12 = a(var2[2], 0, 1000000);
                  if (var8.b()) {
                     var5 = var12;
                  } else {
                     var5 = var12 * 20;
                  }
               } else if (var8.b()) {
                  var5 = 1;
               }

               if (var2.length >= 4) {
                  var7 = a(var2[3], 0, 255);
               }

               boolean var9 = true;
               if (var2.length >= 5 && "true".equalsIgnoreCase(var2[4])) {
                  var9 = false;
               }

               if (var12 > 0) {
                  PotionEffect var10 = new PotionEffect(var4, var5, var7, false, var9);
                  var3.c(var10);
                  a(var1, this, "commands.effect.success", new Object[]{new ChatComponentTranslation(var10.g()), var4, var7, var3.e_(), var12});
               } else if (var3.k(var4)) {
                  var3.m(var4);
                  a(var1, this, "commands.effect.success.removed", new Object[]{new ChatComponentTranslation(var8.a()), var3.e_()});
               } else {
                  throw new CommandException("commands.effect.failure.notActive", new ChatComponentTranslation(var8.a()), var3.e_());
               }
            } else {
               throw new NumberInvalidException("commands.effect.notFound", var4);
            }
         }
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      if (var2.length == 1) {
         return a(var2, this.d());
      } else if (var2.length == 2) {
         return a(var2, Potion.c());
      } else {
         return var2.length == 5 ? a(var2, new String[]{"true", "false"}) : null;
      }
   }

   protected String[] d() {
      return MinecraftServer.N().K();
   }

   @Override
   public boolean b(String[] var1, int var2) {
      return var2 == 0;
   }
}
