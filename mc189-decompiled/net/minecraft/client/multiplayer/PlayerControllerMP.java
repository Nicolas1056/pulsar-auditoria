package net.minecraft.client.multiplayer;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.network.play.client.C11PacketEnchantItem;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;

public class PlayerControllerMP {
   private final Minecraft a;
   private final NetHandlerPlayClient b;
   private BlockPos c = new BlockPos(-1, -1, -1);
   private ItemStack d;
   private float e;
   private float f;
   private int g;
   private boolean h;
   private WorldSettings.GameType i = WorldSettings.GameType.b;
   private int j;

   public PlayerControllerMP(Minecraft var1, NetHandlerPlayClient var2) {
      this.a = var1;
      this.b = var2;
   }

   public static void a(Minecraft var0, PlayerControllerMP var1, BlockPos var2, EnumFacing var3) {
      if (!var0.f.a(var0.h, var2, var3)) {
         var1.a(var2, var3);
      }
   }

   public void a(EntityPlayer var1) {
      this.i.a(var1.bA);
   }

   public boolean a() {
      return this.i == WorldSettings.GameType.e;
   }

   public void a(WorldSettings.GameType var1) {
      this.i = var1;
      this.i.a(this.a.h.bA);
   }

   public void b(EntityPlayer var1) {
      var1.y = -180.0F;
   }

   public boolean b() {
      return this.i.e();
   }

   public boolean a(BlockPos var1, EnumFacing var2) {
      if (this.i.c()) {
         if (this.i == WorldSettings.GameType.e) {
            return false;
         }

         if (!this.a.h.cn()) {
            Block var3 = this.a.f.p(var1).c();
            ItemStack var4 = this.a.h.bZ();
            if (var4 == null) {
               return false;
            }

            if (!var4.c(var3)) {
               return false;
            }
         }
      }

      if (this.i.d() && this.a.h.bA() != null && this.a.h.bA().b() instanceof ItemSword) {
         return false;
      } else {
         WorldClient var8 = this.a.f;
         IBlockState var9 = var8.p(var1);
         Block var5 = var9.c();
         if (var5.t() == Material.a) {
            return false;
         } else {
            var8.b(2001, var1, Block.f(var9));
            boolean var6 = var8.g(var1);
            if (var6) {
               var5.d(var8, var1, var9);
            }

            this.c = new BlockPos(this.c.n(), -1, this.c.p());
            if (!this.i.d()) {
               ItemStack var7 = this.a.h.bZ();
               if (var7 != null) {
                  var7.a(var8, var5, var1, this.a.h);
                  if (var7.b == 0) {
                     this.a.h.ca();
                  }
               }
            }

            return var6;
         }
      }
   }

   public boolean b(BlockPos var1, EnumFacing var2) {
      if (this.i.c()) {
         if (this.i == WorldSettings.GameType.e) {
            return false;
         }

         if (!this.a.h.cn()) {
            Block var3 = this.a.f.p(var1).c();
            ItemStack var4 = this.a.h.bZ();
            if (var4 == null) {
               return false;
            }

            if (!var4.c(var3)) {
               return false;
            }
         }
      }

      if (!this.a.f.af().a(var1)) {
         return false;
      } else {
         if (this.i.d()) {
            this.b.a(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.a, var1, var2));
            a(this.a, this, var1, var2);
            this.g = 5;
         } else if (!this.h || !this.a(var1)) {
            if (this.h) {
               this.b.a(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.b, this.c, var2));
            }

            this.b.a(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.a, var1, var2));
            Block var5 = this.a.f.p(var1).c();
            boolean var6 = var5.t() != Material.a;
            if (var6 && this.e == 0.0F) {
               var5.a(this.a.f, var1, (EntityPlayer)this.a.h);
            }

            if (var6 && var5.a(this.a.h, this.a.h.o, var1) >= 1.0F) {
               this.a(var1, var2);
            } else {
               this.h = true;
               this.c = var1;
               this.d = this.a.h.bA();
               this.e = 0.0F;
               this.f = 0.0F;
               this.a.f.c(this.a.h.F(), this.c, (int)(this.e * 10.0F) - 1);
            }
         }

         return true;
      }
   }

   public void c() {
      if (this.h) {
         this.b.a(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.b, this.c, EnumFacing.a));
         this.h = false;
         this.e = 0.0F;
         this.a.f.c(this.a.h.F(), this.c, -1);
      }
   }

   public boolean c(BlockPos var1, EnumFacing var2) {
      this.n();
      if (this.g > 0) {
         this.g--;
         return true;
      } else if (this.i.d() && this.a.f.af().a(var1)) {
         this.g = 5;
         this.b.a(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.a, var1, var2));
         a(this.a, this, var1, var2);
         return true;
      } else if (this.a(var1)) {
         Block var3 = this.a.f.p(var1).c();
         if (var3.t() == Material.a) {
            this.h = false;
            return false;
         } else {
            this.e = this.e + var3.a(this.a.h, this.a.h.o, var1);
            if (this.f % 4.0F == 0.0F) {
               this.a
                  .W()
                  .a(
                     new PositionedSoundRecord(
                        new ResourceLocation(var3.H.c()), (var3.H.d() + 1.0F) / 8.0F, var3.H.e() * 0.5F, var1.n() + 0.5F, var1.o() + 0.5F, var1.p() + 0.5F
                     )
                  );
            }

            this.f++;
            if (this.e >= 1.0F) {
               this.h = false;
               this.b.a(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.c, var1, var2));
               this.a(var1, var2);
               this.e = 0.0F;
               this.f = 0.0F;
               this.g = 5;
            }

            this.a.f.c(this.a.h.F(), this.c, (int)(this.e * 10.0F) - 1);
            return true;
         }
      } else {
         return this.b(var1, var2);
      }
   }

   public float d() {
      return this.i.d() ? 5.0F : 4.5F;
   }

   public void e() {
      this.n();
      if (this.b.a().g()) {
         this.b.a().a();
      } else {
         this.b.a().l();
      }
   }

   private boolean a(BlockPos var1) {
      ItemStack var2 = this.a.h.bA();
      boolean var3 = this.d == null && var2 == null;
      if (this.d != null && var2 != null) {
         var3 = var2.b() == this.d.b() && ItemStack.a(var2, this.d) && (var2.e() || var2.i() == this.d.i());
      }

      return var1.equals(this.c) && var3;
   }

   private void n() {
      int var1 = this.a.h.bi.c;
      if (var1 != this.j) {
         this.j = var1;
         this.b.a(new C09PacketHeldItemChange(this.j));
      }
   }

   public boolean a(EntityPlayerSP var1, WorldClient var2, ItemStack var3, BlockPos var4, EnumFacing var5, Vec3 var6) {
      this.n();
      float var7 = (float)(var6.a - var4.n());
      float var8 = (float)(var6.b - var4.o());
      float var9 = (float)(var6.c - var4.p());
      boolean var10 = false;
      if (!this.a.f.af().a(var4)) {
         return false;
      } else {
         if (this.i != WorldSettings.GameType.e) {
            IBlockState var11 = var2.p(var4);
            if ((!var1.av() || var1.bA() == null) && var11.c().a(var2, var4, var11, var1, var5, var7, var8, var9)) {
               var10 = true;
            }

            if (!var10 && var3 != null && var3.b() instanceof ItemBlock) {
               ItemBlock var12 = (ItemBlock)var3.b();
               if (!var12.a(var2, var4, var5, var1, var3)) {
                  return false;
               }
            }
         }

         this.b.a(new C08PacketPlayerBlockPlacement(var4, var5.a(), var1.bi.h(), var7, var8, var9));
         if (var10 || this.i == WorldSettings.GameType.e) {
            return true;
         } else if (var3 == null) {
            return false;
         } else if (this.i.d()) {
            int var14 = var3.i();
            int var15 = var3.b;
            boolean var13 = var3.a(var1, var2, var4, var5, var7, var8, var9);
            var3.b(var14);
            var3.b = var15;
            return var13;
         } else {
            return var3.a(var1, var2, var4, var5, var7, var8, var9);
         }
      }
   }

   public boolean a(EntityPlayer var1, World var2, ItemStack var3) {
      if (this.i == WorldSettings.GameType.e) {
         return false;
      } else {
         this.n();
         this.b.a(new C08PacketPlayerBlockPlacement(var1.bi.h()));
         int var4 = var3.b;
         ItemStack var5 = var3.a(var2, var1);
         if (var5 != var3 || var5 != null && var5.b != var4) {
            var1.bi.a[var1.bi.c] = var5;
            if (var5.b == 0) {
               var1.bi.a[var1.bi.c] = null;
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public EntityPlayerSP a(World var1, StatFileWriter var2) {
      return new EntityPlayerSP(this.a, var1, this.b, var2);
   }

   public void a(EntityPlayer var1, Entity var2) {
      this.n();
      this.b.a(new C02PacketUseEntity(var2, C02PacketUseEntity.Action.b));
      if (this.i != WorldSettings.GameType.e) {
         var1.f(var2);
      }
   }

   public boolean b(EntityPlayer var1, Entity var2) {
      this.n();
      this.b.a(new C02PacketUseEntity(var2, C02PacketUseEntity.Action.a));
      return this.i != WorldSettings.GameType.e && var1.u(var2);
   }

   public boolean a(EntityPlayer var1, Entity var2, MovingObjectPosition var3) {
      this.n();
      Vec3 var4 = new Vec3(var3.c.a - var2.s, var3.c.b - var2.t, var3.c.c - var2.u);
      this.b.a(new C02PacketUseEntity(var2, var4));
      return this.i != WorldSettings.GameType.e && var2.a(var1, var4);
   }

   public ItemStack a(int var1, int var2, int var3, int var4, EntityPlayer var5) {
      short var6 = var5.bk.a(var5.bi);
      ItemStack var7 = var5.bk.a(var2, var3, var4, var5);
      this.b.a(new C0EPacketClickWindow(var1, var2, var3, var4, var7, var6));
      return var7;
   }

   public void a(int var1, int var2) {
      this.b.a(new C11PacketEnchantItem(var1, var2));
   }

   public void a(ItemStack var1, int var2) {
      if (this.i.d()) {
         this.b.a(new C10PacketCreativeInventoryAction(var2, var1));
      }
   }

   public void a(ItemStack var1) {
      if (this.i.d() && var1 != null) {
         this.b.a(new C10PacketCreativeInventoryAction(-1, var1));
      }
   }

   public void c(EntityPlayer var1) {
      this.n();
      this.b.a(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.f, BlockPos.a, EnumFacing.a));
      var1.bU();
   }

   public boolean f() {
      return this.i.e();
   }

   public boolean g() {
      return !this.i.d();
   }

   public boolean h() {
      return this.i.d();
   }

   public boolean i() {
      return this.i.d();
   }

   public boolean j() {
      return this.a.h.au() && this.a.h.m instanceof EntityHorse;
   }

   public boolean k() {
      return this.i == WorldSettings.GameType.e;
   }

   public WorldSettings.GameType l() {
      return this.i;
   }

   public boolean m() {
      return this.h;
   }
}
