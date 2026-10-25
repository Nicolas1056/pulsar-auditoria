package net.minecraft.tileentity;

import net.minecraft.block.BlockDaylightDetector;
import net.minecraft.util.ITickable;

public class TileEntityDaylightDetector extends TileEntity implements ITickable {
   @Override
   public void c() {
      if (this.b != null && !this.b.D && this.b.K() % 20L == 0L) {
         this.e = this.w();
         if (this.e instanceof BlockDaylightDetector) {
            ((BlockDaylightDetector)this.e).f(this.b, this.c);
         }
      }
   }
}
