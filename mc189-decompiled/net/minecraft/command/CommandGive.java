package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandGive extends CommandBase {
   @Override
   public String c() {
      return "give";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.give.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 2) {
         throw new WrongUsageException("commands.give.usage");
      } else {
         EntityPlayerMP var3 = a(var1, var2[0]);
         Item var4 = f(var1, var2[1]);
         int var5 = var2.length >= 3 ? a(var2[2], 1, 64) : 1;
         int var6 = var2.length >= 4 ? a(var2[3]) : 0;
         ItemStack var7 = new ItemStack(var4, var5, var6);
         if (var2.length >= 5) {
            String var8 = a(var1, var2, 4).c();

            try {
               var7.d(JsonToNBT.a(var8));
            } catch (NBTException var10) {
               throw new CommandException("commands.give.tagError", var10.getMessage());
            }
         }

         boolean var11 = var3.bi.a(var7);
         if (var11) {
            var3.o.a((Entity)var3, "random.pop", 0.2F, ((var3.bc().nextFloat() - var3.bc().nextFloat()) * 0.7F + 1.0F) * 2.0F);
            var3.bj.b();
         }

         if (var11 && var7.b <= 0) {
            var7.b = 1;
            var1.a(CommandResultStats.Type.d, var5);
            EntityItem var12 = var3.a(var7, false);
            if (var12 != null) {
               var12.v();
            }
         } else {
            var1.a(CommandResultStats.Type.d, var5 - var7.b);
            EntityItem var9 = var3.a(var7, false);
            if (var9 != null) {
               var9.q();
               var9.b(var3.e_());
            }
         }

         a(var1, this, "commands.give.success", new Object[]{var7.C(), var5, var3.e_()});
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      if (var2.length == 1) {
         return a(var2, this.d());
      } else {
         return var2.length == 2 ? a(var2, Item.e.c()) : null;
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
