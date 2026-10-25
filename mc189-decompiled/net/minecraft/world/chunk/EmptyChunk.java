package net.minecraft.world.chunk;

import com.google.common.base.Predicate;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class EmptyChunk extends Chunk {
   public EmptyChunk(World var1, int var2, int var3) {
      super(var1, var2, var3);
   }

   @Override
   public boolean a(int var1, int var2) {
      return var1 == this.a && var2 == this.b;
   }

   @Override
   public int b(int var1, int var2) {
      return 0;
   }

   @Override
   public void a() {
   }

   @Override
   public void b() {
   }

   @Override
   public Block a(BlockPos var1) {
      return Blocks.a;
   }

   @Override
   public int b(BlockPos var1) {
      return 255;
   }

   @Override
   public int c(BlockPos var1) {
      return 0;
   }

   @Override
   public int a(EnumSkyBlock var1, BlockPos var2) {
      return var1.c;
   }

   @Override
   public void a(EnumSkyBlock var1, BlockPos var2, int var3) {
   }

   @Override
   public int a(BlockPos var1, int var2) {
      return 0;
   }

   @Override
   public void a(Entity var1) {
   }

   @Override
   public void b(Entity var1) {
   }

   @Override
   public void a(Entity var1, int var2) {
   }

   @Override
   public boolean d(BlockPos var1) {
      return false;
   }

   @Override
   public TileEntity a(BlockPos var1, Chunk.EnumCreateEntityType var2) {
      return null;
   }

   @Override
   public void a(TileEntity var1) {
   }

   @Override
   public void a(BlockPos var1, TileEntity var2) {
   }

   @Override
   public void e(BlockPos var1) {
   }

   @Override
   public void c() {
   }

   @Override
   public void d() {
   }

   @Override
   public void e() {
   }

   @Override
   public void a(Entity var1, AxisAlignedBB var2, List<Entity> var3, Predicate<? super Entity> var4) {
   }

   @Override
   public <T extends Entity> void a(Class<? extends T> var1, AxisAlignedBB var2, List<T> var3, Predicate<? super T> var4) {
   }

   @Override
   public boolean a(boolean var1) {
      return false;
   }

   @Override
   public Random a(long var1) {
      return new Random(this.p().J() + this.a * this.a * 4987142 + this.a * 5947611 + this.b * this.b * 4392871L + this.b * 389711 ^ var1);
   }

   @Override
   public boolean f() {
      return true;
   }

   @Override
   public boolean c(int var1, int var2) {
      return true;
   }
}
