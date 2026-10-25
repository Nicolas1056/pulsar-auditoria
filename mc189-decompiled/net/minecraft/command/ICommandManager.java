package net.minecraft.command;

import java.util.List;
import java.util.Map;
import net.minecraft.util.BlockPos;

public interface ICommandManager {
   int a(ICommandSender var1, String var2);

   List<String> a(ICommandSender var1, String var2, BlockPos var3);

   List<ICommand> a(ICommandSender var1);

   Map<String, ICommand> a();
}
