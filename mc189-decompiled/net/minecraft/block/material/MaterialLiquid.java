package net.minecraft.block.material;

public class MaterialLiquid extends Material {
   public MaterialLiquid(MapColor var1) {
      super(var1);
      this.i();
      this.n();
   }

   @Override
   public boolean d() {
      return true;
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public boolean a() {
      return false;
   }
}
