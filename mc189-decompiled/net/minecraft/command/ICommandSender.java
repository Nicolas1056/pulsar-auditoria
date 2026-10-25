package net.minecraft.command;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public interface ICommandSender {
   String e_();

   IChatComponent f_();

   void a(IChatComponent var1);

   boolean a(int var1, String var2);

   BlockPos c();

   Vec3 d();

   World e();

   Entity f();

   boolean u_();

   void a(CommandResultStats.Type var1, int var2);
}
