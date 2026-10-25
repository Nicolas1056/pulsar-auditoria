package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public abstract class MovingSound extends PositionedSound implements ITickableSound {
   protected boolean j = false;

   protected MovingSound(ResourceLocation var1) {
      super(var1);
   }

   @Override
   public boolean k() {
      return this.j;
   }
}
