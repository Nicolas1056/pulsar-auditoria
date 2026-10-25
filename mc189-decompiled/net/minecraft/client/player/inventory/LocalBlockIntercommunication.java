package net.minecraft.client.player.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IInteractionObject;

public class LocalBlockIntercommunication implements IInteractionObject {
   private String a;
   private IChatComponent b;

   public LocalBlockIntercommunication(String var1, IChatComponent var2) {
      this.a = var1;
      this.b = var2;
   }

   @Override
   public Container a(InventoryPlayer var1, EntityPlayer var2) {
      throw new UnsupportedOperationException();
   }

   @Override
   public String e_() {
      return this.b.c();
   }

   @Override
   public boolean l_() {
      return true;
   }

   @Override
   public String k() {
      return this.a;
   }

   @Override
   public IChatComponent f_() {
      return this.b;
   }
}
