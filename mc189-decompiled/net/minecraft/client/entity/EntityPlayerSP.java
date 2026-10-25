package net.minecraft.client.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSoundMinecartRiding;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiCommandBlock;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.gui.inventory.GuiBrewingStand;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.potion.Potion;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class EntityPlayerSP extends AbstractClientPlayer {
   public final NetHandlerPlayClient a;
   private final StatFileWriter bJ;
   private double bK;
   private double bL;
   private double bM;
   private float bN;
   private float bO;
   private boolean bP;
   private boolean bQ;
   private int bR;
   private boolean bS;
   private String bT;
   public MovementInput b;
   protected Minecraft c;
   protected int d;
   public int e;
   public float f;
   public float g;
   public float h;
   public float i;
   private int bU;
   private float bV;
   public float bH;
   public float bI;

   public EntityPlayerSP(Minecraft var1, World var2, NetHandlerPlayClient var3, StatFileWriter var4) {
      super(var2, var3.e());
      this.a = var3;
      this.bJ = var4;
      this.c = var1;
      this.am = 0;
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      return false;
   }

   @Override
   public void h(float var1) {
   }

   @Override
   public void a(Entity var1) {
      super.a(var1);
      if (var1 instanceof EntityMinecart) {
         this.c.W().a(new MovingSoundMinecartRiding(this, (EntityMinecart)var1));
      }
   }

   @Override
   public void t_() {
      if (this.o.e(new BlockPos(this.s, 0.0, this.u))) {
         super.t_();
         if (this.au()) {
            this.a.a(new C03PacketPlayer.C05PacketPlayerLook(this.y, this.z, this.C));
            this.a.a(new C0CPacketInput(this.aZ, this.ba, this.b.c, this.b.d));
         } else {
            this.p();
         }
      }
   }

   public void p() {
      boolean var1 = this.aw();
      if (var1 != this.bQ) {
         if (var1) {
            this.a.a(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.d));
         } else {
            this.a.a(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.e));
         }

         this.bQ = var1;
      }

      boolean var2 = this.av();
      if (var2 != this.bP) {
         if (var2) {
            this.a.a(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.a));
         } else {
            this.a.a(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.b));
         }

         this.bP = var2;
      }

      if (this.A()) {
         double var3 = this.s - this.bK;
         double var5 = this.aR().b - this.bL;
         double var7 = this.u - this.bM;
         double var9 = this.y - this.bN;
         double var11 = this.z - this.bO;
         boolean var13 = var3 * var3 + var5 * var5 + var7 * var7 > 9.0E-4 || this.bR >= 20;
         boolean var14 = var9 != 0.0 || var11 != 0.0;
         if (this.m == null) {
            if (var13 && var14) {
               this.a.a(new C03PacketPlayer.C06PacketPlayerPosLook(this.s, this.aR().b, this.u, this.y, this.z, this.C));
            } else if (var13) {
               this.a.a(new C03PacketPlayer.C04PacketPlayerPosition(this.s, this.aR().b, this.u, this.C));
            } else if (var14) {
               this.a.a(new C03PacketPlayer.C05PacketPlayerLook(this.y, this.z, this.C));
            } else {
               this.a.a(new C03PacketPlayer(this.C));
            }
         } else {
            this.a.a(new C03PacketPlayer.C06PacketPlayerPosLook(this.v, -999.0, this.x, this.y, this.z, this.C));
            var13 = false;
         }

         this.bR++;
         if (var13) {
            this.bK = this.s;
            this.bL = this.aR().b;
            this.bM = this.u;
            this.bR = 0;
         }

         if (var14) {
            this.bN = this.y;
            this.bO = this.z;
         }
      }
   }

   @Override
   public EntityItem a(boolean var1) {
      C07PacketPlayerDigging.Action var2 = var1 ? C07PacketPlayerDigging.Action.d : C07PacketPlayerDigging.Action.e;
      this.a.a(new C07PacketPlayerDigging(var2, BlockPos.a, EnumFacing.a));
      return null;
   }

   @Override
   protected void a(EntityItem var1) {
   }

   public void e(String var1) {
      this.a.a(new C01PacketChatMessage(var1));
   }

   @Override
   public void bw() {
      super.bw();
      this.a.a(new C0APacketAnimation());
   }

   @Override
   public void cb() {
      this.a.a(new C16PacketClientStatus(C16PacketClientStatus.EnumState.a));
   }

   @Override
   protected void d(DamageSource var1, float var2) {
      if (!this.b(var1)) {
         this.i(this.bn() - var2);
      }
   }

   @Override
   public void n() {
      this.a.a(new C0DPacketCloseWindow(this.bk.d));
      this.q();
   }

   public void q() {
      this.bi.b(null);
      super.n();
      this.c.a(null);
   }

   public void n(float var1) {
      if (this.bS) {
         float var2 = this.bn() - var1;
         if (var2 <= 0.0F) {
            this.i(var1);
            if (var2 < 0.0F) {
               this.Z = this.aD / 2;
            }
         } else {
            this.aX = var2;
            this.i(this.bn());
            this.Z = this.aD;
            this.d(DamageSource.k, var2);
            this.au = this.av = 10;
         }
      } else {
         this.i(var1);
         this.bS = true;
      }
   }

   @Override
   public void a(StatBase var1, int var2) {
      if (var1 != null) {
         if (var1.f) {
            super.a(var1, var2);
         }
      }
   }

   @Override
   public void t() {
      this.a.a(new C13PacketPlayerAbilities(this.bA));
   }

   @Override
   public boolean cc() {
      return true;
   }

   protected void r() {
      this.a.a(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.f, (int)(this.z() * 100.0F)));
   }

   public void u() {
      this.a.a(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.g));
   }

   public void f(String var1) {
      this.bT = var1;
   }

   public String w() {
      return this.bT;
   }

   public StatFileWriter x() {
      return this.bJ;
   }

   @Override
   public void b(IChatComponent var1) {
      this.c.q.d().a(var1);
   }

   @Override
   protected boolean j(double var1, double var3, double var5) {
      if (this.T) {
         return false;
      } else {
         BlockPos var7 = new BlockPos(var1, var3, var5);
         double var8 = var1 - var7.n();
         double var10 = var5 - var7.p();
         if (!this.e(var7)) {
            byte var12 = -1;
            double var13 = 9999.0;
            if (this.e(var7.e()) && var8 < var13) {
               var13 = var8;
               var12 = 0;
            }

            if (this.e(var7.f()) && 1.0 - var8 < var13) {
               var13 = 1.0 - var8;
               var12 = 1;
            }

            if (this.e(var7.c()) && var10 < var13) {
               var13 = var10;
               var12 = 4;
            }

            if (this.e(var7.d()) && 1.0 - var10 < var13) {
               var13 = 1.0 - var10;
               var12 = 5;
            }

            float var15 = 0.1F;
            if (var12 == 0) {
               this.v = -var15;
            }

            if (var12 == 1) {
               this.v = var15;
            }

            if (var12 == 4) {
               this.x = -var15;
            }

            if (var12 == 5) {
               this.x = var15;
            }
         }

         return false;
      }
   }

   private boolean e(BlockPos var1) {
      return !this.o.p(var1).c().v() && !this.o.p(var1.a()).c().v();
   }

   @Override
   public void d(boolean var1) {
      super.d(var1);
      this.e = var1 ? 600 : 0;
   }

   public void a(float var1, int var2, int var3) {
      this.bD = var1;
      this.bC = var2;
      this.bB = var3;
   }

   @Override
   public void a(IChatComponent var1) {
      this.c.q.d().a(var1);
   }

   @Override
   public boolean a(int var1, String var2) {
      return var1 <= 0;
   }

   @Override
   public BlockPos c() {
      return new BlockPos(this.s + 0.5, this.t + 0.5, this.u + 0.5);
   }

   @Override
   public void a(String var1, float var2, float var3) {
      this.o.a(this.s, this.t, this.u, var1, var2, var3, false);
   }

   @Override
   public boolean bM() {
      return true;
   }

   public boolean y() {
      return this.m != null && this.m instanceof EntityHorse && ((EntityHorse)this.m).cG();
   }

   public float z() {
      return this.bV;
   }

   @Override
   public void a(TileEntitySign var1) {
      this.c.a(new GuiEditSign(var1));
   }

   @Override
   public void a(CommandBlockLogic var1) {
      this.c.a(new GuiCommandBlock(var1));
   }

   @Override
   public void a(ItemStack var1) {
      Item var2 = var1.b();
      if (var2 == Items.bM) {
         this.c.a(new GuiScreenBook(this, var1, true));
      }
   }

   @Override
   public void a(IInventory var1) {
      String var2 = var1 instanceof IInteractionObject ? ((IInteractionObject)var1).k() : "minecraft:container";
      if ("minecraft:chest".equals(var2)) {
         this.c.a(new GuiChest(this.bi, var1));
      } else if ("minecraft:hopper".equals(var2)) {
         this.c.a(new GuiHopper(this.bi, var1));
      } else if ("minecraft:furnace".equals(var2)) {
         this.c.a(new GuiFurnace(this.bi, var1));
      } else if ("minecraft:brewing_stand".equals(var2)) {
         this.c.a(new GuiBrewingStand(this.bi, var1));
      } else if ("minecraft:beacon".equals(var2)) {
         this.c.a(new GuiBeacon(this.bi, var1));
      } else if (!"minecraft:dispenser".equals(var2) && !"minecraft:dropper".equals(var2)) {
         this.c.a(new GuiChest(this.bi, var1));
      } else {
         this.c.a(new GuiDispenser(this.bi, var1));
      }
   }

   @Override
   public void a(EntityHorse var1, IInventory var2) {
      this.c.a(new GuiScreenHorseInventory(this.bi, var2, var1));
   }

   @Override
   public void a(IInteractionObject var1) {
      String var2 = var1.k();
      if ("minecraft:crafting_table".equals(var2)) {
         this.c.a(new GuiCrafting(this.bi, this.o));
      } else if ("minecraft:enchanting_table".equals(var2)) {
         this.c.a(new GuiEnchantment(this.bi, this.o, var1));
      } else if ("minecraft:anvil".equals(var2)) {
         this.c.a(new GuiRepair(this.bi, this.o));
      }
   }

   @Override
   public void a(IMerchant var1) {
      this.c.a(new GuiMerchant(this.bi, var1, this.o));
   }

   @Override
   public void b(Entity var1) {
      this.c.j.a(var1, EnumParticleTypes.j);
   }

   @Override
   public void c(Entity var1) {
      this.c.j.a(var1, EnumParticleTypes.k);
   }

   @Override
   public boolean av() {
      boolean var1 = this.b != null ? this.b.d : false;
      return var1 && !this.bw;
   }

   @Override
   public void bK() {
      super.bK();
      if (this.A()) {
         this.aZ = this.b.a;
         this.ba = this.b.b;
         this.aY = this.b.c;
         this.h = this.f;
         this.i = this.g;
         this.g = (float)(this.g + (this.z - this.g) * 0.5);
         this.f = (float)(this.f + (this.y - this.f) * 0.5);
      }
   }

   protected boolean A() {
      return this.c.ac() == this;
   }

   @Override
   public void m() {
      if (this.e > 0) {
         this.e--;
         if (this.e == 0) {
            this.d(false);
         }
      }

      if (this.d > 0) {
         this.d--;
      }

      this.bI = this.bH;
      if (this.ak) {
         if (this.c.m != null && !this.c.m.d()) {
            this.c.a(null);
         }

         if (this.bH == 0.0F) {
            this.c.W().a(PositionedSoundRecord.a(new ResourceLocation("portal.trigger"), this.V.nextFloat() * 0.4F + 0.8F));
         }

         this.bH += 0.0125F;
         if (this.bH >= 1.0F) {
            this.bH = 1.0F;
         }

         this.ak = false;
      } else if (this.a(Potion.k) && this.b(Potion.k).b() > 60) {
         this.bH += 0.006666667F;
         if (this.bH > 1.0F) {
            this.bH = 1.0F;
         }
      } else {
         if (this.bH > 0.0F) {
            this.bH -= 0.05F;
         }

         if (this.bH < 0.0F) {
            this.bH = 0.0F;
         }
      }

      if (this.aj > 0) {
         this.aj--;
      }

      boolean var1 = this.b.c;
      boolean var2 = this.b.d;
      float var3 = 0.8F;
      boolean var4 = this.b.b >= var3;
      this.b.a();
      if (this.bS() && !this.au()) {
         this.b.a *= 0.2F;
         this.b.b *= 0.2F;
         this.d = 0;
      }

      this.j(this.s - this.J * 0.35, this.aR().b + 0.5, this.u + this.J * 0.35);
      this.j(this.s - this.J * 0.35, this.aR().b + 0.5, this.u - this.J * 0.35);
      this.j(this.s + this.J * 0.35, this.aR().b + 0.5, this.u - this.J * 0.35);
      this.j(this.s + this.J * 0.35, this.aR().b + 0.5, this.u + this.J * 0.35);
      boolean var5 = this.cl().a() > 6.0F || this.bA.c;
      if (this.C && !var2 && !var4 && this.b.b >= var3 && !this.aw() && var5 && !this.bS() && !this.a(Potion.q)) {
         if (this.d <= 0 && !this.c.t.ae.d()) {
            this.d = 7;
         } else {
            this.d(true);
         }
      }

      if (!this.aw() && this.b.b >= var3 && var5 && !this.bS() && !this.a(Potion.q) && this.c.t.ae.d()) {
         this.d(true);
      }

      if (this.aw() && (this.b.b < var3 || this.D || !var5)) {
         this.d(false);
      }

      if (this.bA.c) {
         if (this.c.c.k()) {
            if (!this.bA.b) {
               this.bA.b = true;
               this.t();
            }
         } else if (!var1 && this.b.c) {
            if (this.bm == 0) {
               this.bm = 7;
            } else {
               this.bA.b = !this.bA.b;
               this.t();
               this.bm = 0;
            }
         }
      }

      if (this.bA.b && this.A()) {
         if (this.b.d) {
            this.w = this.w - this.bA.a() * 3.0F;
         }

         if (this.b.c) {
            this.w = this.w + this.bA.a() * 3.0F;
         }
      }

      if (this.y()) {
         if (this.bU < 0) {
            this.bU++;
            if (this.bU == 0) {
               this.bV = 0.0F;
            }
         }

         if (var1 && !this.b.c) {
            this.bU = -10;
            this.r();
         } else if (!var1 && this.b.c) {
            this.bU = 0;
            this.bV = 0.0F;
         } else if (var1) {
            this.bU++;
            if (this.bU < 10) {
               this.bV = this.bU * 0.1F;
            } else {
               this.bV = 0.8F + 2.0F / (this.bU - 9) * 0.1F;
            }
         }
      } else {
         this.bV = 0.0F;
      }

      super.m();
      if (this.C && this.bA.b && !this.c.c.k()) {
         this.bA.b = false;
         this.t();
      }
   }
}
