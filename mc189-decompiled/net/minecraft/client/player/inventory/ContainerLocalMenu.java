package net.minecraft.client.player.inventory;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;

public class ContainerLocalMenu extends InventoryBasic implements ILockableContainer {
   private String a;
   private Map<Integer, Integer> b = Maps.newHashMap();

   public ContainerLocalMenu(String var1, IChatComponent var2, int var3) {
      super(var2, var3);
      this.a = var1;
   }

   @Override
   public int a_(int var1) {
      return this.b.containsKey(var1) ? this.b.get(var1) : 0;
   }

   @Override
   public void b(int var1, int var2) {
      this.b.put(var1, var2);
   }

   @Override
   public int g() {
      return this.b.size();
   }

   @Override
   public boolean r_() {
      return false;
   }

   @Override
   public void a(LockCode var1) {
   }

   @Override
   public LockCode i() {
      return LockCode.a;
   }

   @Override
   public String k() {
      return this.a;
   }

   @Override
   public Container a(InventoryPlayer var1, EntityPlayer var2) {
      throw new UnsupportedOperationException();
   }
}
