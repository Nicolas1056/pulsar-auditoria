package net.minecraft.world.storage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface IPlayerFileData {
   void a(EntityPlayer var1);

   NBTTagCompound b(EntityPlayer var1);

   String[] f();
}
