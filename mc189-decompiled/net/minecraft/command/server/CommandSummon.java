package net.minecraft.command.server;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class CommandSummon extends CommandBase {
   @Override
   public String c() {
      return "summon";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.summon.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 1) {
         throw new WrongUsageException("commands.summon.usage");
      } else {
         String var3 = var2[0];
         BlockPos var4 = var1.c();
         Vec3 var5 = var1.d();
         double var6 = var5.a;
         double var8 = var5.b;
         double var10 = var5.c;
         if (var2.length >= 4) {
            var6 = b(var6, var2[1], true);
            var8 = b(var8, var2[2], false);
            var10 = b(var10, var2[3], true);
            var4 = new BlockPos(var6, var8, var10);
         }

         World var12 = var1.e();
         if (!var12.e(var4)) {
            throw new CommandException("commands.summon.outOfWorld");
         } else if ("LightningBolt".equals(var3)) {
            var12.c(new EntityLightningBolt(var12, var6, var8, var10));
            a(var1, this, "commands.summon.success", new Object[0]);
         } else {
            NBTTagCompound var13 = new NBTTagCompound();
            boolean var14 = false;
            if (var2.length >= 5) {
               IChatComponent var15 = a(var1, var2, 4);

               try {
                  var13 = JsonToNBT.a(var15.c());
                  var14 = true;
               } catch (NBTException var20) {
                  throw new CommandException("commands.summon.tagError", var20.getMessage());
               }
            }

            var13.a("id", var3);

            Entity var21;
            try {
               var21 = EntityList.a(var13, var12);
            } catch (RuntimeException var19) {
               throw new CommandException("commands.summon.failed");
            }

            if (var21 == null) {
               throw new CommandException("commands.summon.failed");
            } else {
               var21.b(var6, var8, var10, var21.y, var21.z);
               if (!var14 && var21 instanceof EntityLiving) {
                  ((EntityLiving)var21).a(var12.E(new BlockPos(var21)), null);
               }

               var12.d(var21);
               Entity var16 = var21;

               for (NBTTagCompound var17 = var13; var16 != null && var17.b("Riding", 10); var17 = var17.m("Riding")) {
                  Entity var18 = EntityList.a(var17.m("Riding"), var12);
                  if (var18 != null) {
                     var18.b(var6, var8, var10, var18.y, var18.z);
                     var12.d(var18);
                     var16.a(var18);
                  }

                  var16 = var18;
               }

               a(var1, this, "commands.summon.success", new Object[0]);
            }
         }
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      if (var2.length == 1) {
         return a(var2, EntityList.b());
      } else {
         return var2.length > 1 && var2.length <= 4 ? a(var2, 1, var3) : null;
      }
   }
}
