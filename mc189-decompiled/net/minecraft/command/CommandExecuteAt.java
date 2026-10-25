package net.minecraft.command;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class CommandExecuteAt extends CommandBase {
   @Override
   public String c() {
      return "execute";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.execute.usage";
   }

   @Override
   public void a(final ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 5) {
         throw new WrongUsageException("commands.execute.usage");
      } else {
         final Entity var3 = a(var1, var2[0], Entity.class);
         final double var4 = b(var3.s, var2[1], false);
         final double var6 = b(var3.t, var2[2], false);
         final double var8 = b(var3.u, var2[3], false);
         final BlockPos var10 = new BlockPos(var4, var6, var8);
         byte var11 = 4;
         if ("detect".equals(var2[4]) && var2.length > 10) {
            World var12 = var3.e();
            double var13 = b(var4, var2[5], false);
            double var15 = b(var6, var2[6], false);
            double var17 = b(var8, var2[7], false);
            Block var19 = g(var1, var2[8]);
            int var20 = a(var2[9], -1, 15);
            BlockPos var21 = new BlockPos(var13, var15, var17);
            IBlockState var22 = var12.p(var21);
            if (var22.c() != var19 || var20 >= 0 && var22.c().c(var22) != var20) {
               throw new CommandException("commands.execute.failed", "detect", var3.e_());
            }

            var11 = 10;
         }

         String var24 = a(var2, var11);
         ICommandSender var14 = new ICommandSender() {
            @Override
            public String e_() {
               return var3.e_();
            }

            @Override
            public IChatComponent f_() {
               return var3.f_();
            }

            @Override
            public void a(IChatComponent var1x) {
               var1.a(var1x);
            }

            @Override
            public boolean a(int var1x, String var2x) {
               return var1.a(var1x, var2x);
            }

            @Override
            public BlockPos c() {
               return var10;
            }

            @Override
            public Vec3 d() {
               return new Vec3(var4, var6, var8);
            }

            @Override
            public World e() {
               return var3.o;
            }

            @Override
            public Entity f() {
               return var3;
            }

            @Override
            public boolean u_() {
               MinecraftServer var1x = MinecraftServer.N();
               return var1x == null || var1x.d[0].Q().b("commandBlockOutput");
            }

            @Override
            public void a(CommandResultStats.Type var1x, int var2x) {
               var3.a(var1x, var2x);
            }
         };
         ICommandManager var25 = MinecraftServer.N().P();

         try {
            int var16 = var25.a(var14, var24);
            if (var16 < 1) {
               throw new CommandException("commands.execute.allInvocationsFailed", var24);
            }
         } catch (Throwable var23) {
            throw new CommandException("commands.execute.failed", var24, var3.e_());
         }
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      if (var2.length == 1) {
         return a(var2, MinecraftServer.N().K());
      } else if (var2.length > 1 && var2.length <= 4) {
         return a(var2, 1, var3);
      } else if (var2.length > 5 && var2.length <= 8 && "detect".equals(var2[4])) {
         return a(var2, 5, var3);
      } else {
         return var2.length == 9 && "detect".equals(var2[4]) ? a(var2, Block.c.c()) : null;
      }
   }

   @Override
   public boolean b(String[] var1, int var2) {
      return var2 == 0;
   }
}
