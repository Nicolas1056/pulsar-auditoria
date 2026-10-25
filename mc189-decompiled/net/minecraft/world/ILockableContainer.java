package net.minecraft.world;

import net.minecraft.inventory.IInventory;

public interface ILockableContainer extends IInventory, IInteractionObject {
   boolean r_();

   void a(LockCode var1);

   LockCode i();
}
