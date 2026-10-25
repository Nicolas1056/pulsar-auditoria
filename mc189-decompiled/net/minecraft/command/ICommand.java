package net.minecraft.command;

import java.util.List;
import net.minecraft.util.BlockPos;

public interface ICommand extends Comparable<ICommand> {
   String c();

   String c(ICommandSender var1);

   List<String> b();

   void a(ICommandSender var1, String[] var2) throws CommandException;

   boolean a(ICommandSender var1);

   List<String> a(ICommandSender var1, String[] var2, BlockPos var3);

   boolean b(String[] var1, int var2);
}
