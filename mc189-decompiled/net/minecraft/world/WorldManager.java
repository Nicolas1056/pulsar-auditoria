package net.minecraft.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
import net.minecraft.network.play.server.S28PacketEffect;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class WorldManager implements IWorldAccess {
   private MinecraftServer a;
   private WorldServer b;

   public WorldManager(MinecraftServer var1, WorldServer var2) {
      this.a = var1;
      this.b = var2;
   }

   @Override
   public void a(int var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
   }

   @Override
   public void a(Entity var1) {
      this.b.s().a(var1);
   }

   @Override
   public void b(Entity var1) {
      this.b.s().b(var1);
      this.b.Z().a(var1);
   }

   @Override
   public void a(String var1, double var2, double var4, double var6, float var8, float var9) {
      this.a.ap().a(var2, var4, var6, var8 > 1.0F ? 16.0F * var8 : 16.0, this.b.t.q(), new S29PacketSoundEffect(var1, var2, var4, var6, var8, var9));
   }

   @Override
   public void a(EntityPlayer var1, String var2, double var3, double var5, double var7, float var9, float var10) {
      this.a.ap().a(var1, var3, var5, var7, var9 > 1.0F ? 16.0F * var9 : 16.0, this.b.t.q(), new S29PacketSoundEffect(var2, var3, var5, var7, var9, var10));
   }

   @Override
   public void a(int var1, int var2, int var3, int var4, int var5, int var6) {
   }

   @Override
   public void a(BlockPos var1) {
      this.b.t().a(var1);
   }

   @Override
   public void b(BlockPos var1) {
   }

   @Override
   public void a(String var1, BlockPos var2) {
   }

   @Override
   public void a(EntityPlayer var1, int var2, BlockPos var3, int var4) {
      this.a.ap().a(var1, var3.n(), var3.o(), var3.p(), 64.0, this.b.t.q(), new S28PacketEffect(var2, var3, var4, false));
   }

   @Override
   public void a(int var1, BlockPos var2, int var3) {
      this.a.ap().a(new S28PacketEffect(var1, var2, var3, true));
   }

   @Override
   public void b(int var1, BlockPos var2, int var3) {
      for (EntityPlayerMP var5 : this.a.ap().v()) {
         if (var5 != null && var5.o == this.b && var5.F() != var1) {
            double var6 = var2.n() - var5.s;
            double var8 = var2.o() - var5.t;
            double var10 = var2.p() - var5.u;
            if (var6 * var6 + var8 * var8 + var10 * var10 < 1024.0) {
               var5.a.a(new S25PacketBlockBreakAnim(var1, var2, var3));
            }
         }
      }
   }
}
