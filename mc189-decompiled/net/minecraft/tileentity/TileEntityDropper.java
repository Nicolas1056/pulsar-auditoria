package net.minecraft.tileentity;

public class TileEntityDropper extends TileEntityDispenser {
   @Override
   public String e_() {
      return this.l_() ? this.a : "container.dropper";
   }

   @Override
   public String k() {
      return "minecraft:dropper";
   }
}
