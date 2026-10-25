package net.minecraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;

public abstract class TileEntityLockable extends TileEntity implements IInteractionObject, ILockableContainer {
   private LockCode a;

   public TileEntityLockable() {
      this.a = LockCode.a;
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.a = LockCode.b(var1);
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      if (this.a != null) {
         this.a.a(var1);
      }
   }

   @Override
   public boolean r_() {
      return this.a != null && !this.a.a();
   }

   @Override
   public LockCode i() {
      return this.a;
   }

   @Override
   public void a(LockCode var1) {
      this.a = var1;
   }

   @Override
   public IChatComponent f_() {
      return (IChatComponent)(this.l_() ? new ChatComponentText(this.e_()) : new ChatComponentTranslation(this.e_()));
   }
}
