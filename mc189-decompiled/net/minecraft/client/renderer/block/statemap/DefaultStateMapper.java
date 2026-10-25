package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class DefaultStateMapper extends StateMapperBase {
   @Override
   protected ModelResourceLocation a(IBlockState var1) {
      return new ModelResourceLocation(Block.c.c(var1.c()), this.a(var1.b()));
   }
}
