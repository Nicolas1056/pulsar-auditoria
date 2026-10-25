package net.minecraft.command;

import java.util.List;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandEnchant extends CommandBase {
   @Override
   public String c() {
      return "enchant";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.enchant.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 2) {
         throw new WrongUsageException("commands.enchant.usage");
      } else {
         EntityPlayerMP var3 = a(var1, var2[0]);
         var1.a(CommandResultStats.Type.d, 0);

         int var4;
         try {
            var4 = a(var2[1], 0);
         } catch (NumberInvalidException var12) {
            Enchantment var6 = Enchantment.b(var2[1]);
            if (var6 == null) {
               throw var12;
            }

            var4 = var6.B;
         }

         int var5 = 1;
         ItemStack var13 = var3.bZ();
         if (var13 == null) {
            throw new CommandException("commands.enchant.noItem");
         } else {
            Enchantment var7 = Enchantment.c(var4);
            if (var7 == null) {
               throw new NumberInvalidException("commands.enchant.notFound", var4);
            } else if (!var7.a(var13)) {
               throw new CommandException("commands.enchant.cantEnchant");
            } else {
               if (var2.length >= 3) {
                  var5 = a(var2[2], var7.e(), var7.b());
               }

               if (var13.n()) {
                  NBTTagList var8 = var13.p();
                  if (var8 != null) {
                     for (int var9 = 0; var9 < var8.c(); var9++) {
                        short var10 = var8.b(var9).e("id");
                        if (Enchantment.c(var10) != null) {
                           Enchantment var11 = Enchantment.c(var10);
                           if (!var11.a(var7)) {
                              throw new CommandException("commands.enchant.cantCombine", var7.d(var5), var11.d(var8.b(var9).e("lvl")));
                           }
                        }
                     }
                  }
               }

               var13.a(var7, var5);
               a(var1, this, "commands.enchant.success", new Object[0]);
               var1.a(CommandResultStats.Type.d, 1);
            }
         }
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      if (var2.length == 1) {
         return a(var2, this.d());
      } else {
         return var2.length == 2 ? a(var2, Enchantment.c()) : null;
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
