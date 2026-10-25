package net.minecraft.client.gui.inventory;

import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiBeacon extends GuiContainer {
   private static final Logger u = LogManager.getLogger();
   private static final ResourceLocation v = new ResourceLocation("textures/gui/container/beacon.png");
   private IInventory w;
   private GuiBeacon.ConfirmButton x;
   private boolean y;

   public GuiBeacon(InventoryPlayer var1, IInventory var2) {
      super(new ContainerBeacon(var1, var2));
      this.w = var2;
      this.f = 230;
      this.g = 219;
   }

   @Override
   public void b() {
      super.b();
      this.n.add(this.x = new GuiBeacon.ConfirmButton(-1, this.i + 164, this.r + 107));
      this.n.add(new GuiBeacon.CancelButton(-2, this.i + 190, this.r + 107));
      this.y = true;
      this.x.l = false;
   }

   @Override
   public void e() {
      super.e();
      int var1 = this.w.a_(0);
      int var2 = this.w.a_(1);
      int var3 = this.w.a_(2);
      if (this.y && var1 >= 0) {
         this.y = false;

         for (int var4 = 0; var4 <= 2; var4++) {
            int var5 = TileEntityBeacon.a[var4].length;
            int var6 = var5 * 22 + (var5 - 1) * 2;

            for (int var7 = 0; var7 < var5; var7++) {
               int var8 = TileEntityBeacon.a[var4][var7].H;
               GuiBeacon.PowerButton var9 = new GuiBeacon.PowerButton(var4 << 8 | var8, this.i + 76 + var7 * 24 - var6 / 2, this.r + 22 + var4 * 25, var8, var4);
               this.n.add(var9);
               if (var4 >= var1) {
                  var9.l = false;
               } else if (var8 == var2) {
                  var9.b(true);
               }
            }
         }

         byte var10 = 3;
         int var11 = TileEntityBeacon.a[var10].length + 1;
         int var12 = var11 * 22 + (var11 - 1) * 2;

         for (int var13 = 0; var13 < var11 - 1; var13++) {
            int var15 = TileEntityBeacon.a[var10][var13].H;
            GuiBeacon.PowerButton var16 = new GuiBeacon.PowerButton(var10 << 8 | var15, this.i + 167 + var13 * 24 - var12 / 2, this.r + 47, var15, var10);
            this.n.add(var16);
            if (var10 >= var1) {
               var16.l = false;
            } else if (var15 == var3) {
               var16.b(true);
            }
         }

         if (var2 > 0) {
            GuiBeacon.PowerButton var14 = new GuiBeacon.PowerButton(var10 << 8 | var2, this.i + 167 + (var11 - 1) * 24 - var12 / 2, this.r + 47, var2, var10);
            this.n.add(var14);
            if (var10 >= var1) {
               var14.l = false;
            } else if (var2 == var3) {
               var14.b(true);
            }
         }
      }

      this.x.l = this.w.a(0) != null && var2 > 0;
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.k == -2) {
         this.j.a(null);
      } else if (var1.k == -1) {
         String var2 = "MC|Beacon";
         PacketBuffer var3 = new PacketBuffer(Unpooled.buffer());
         var3.writeInt(this.w.a_(1));
         var3.writeInt(this.w.a_(2));
         this.j.u().a(new C17PacketCustomPayload(var2, var3));
         this.j.a(null);
      } else if (var1 instanceof GuiBeacon.PowerButton) {
         if (((GuiBeacon.PowerButton)var1).c()) {
            return;
         }

         int var5 = var1.k;
         int var6 = var5 & 0xFF;
         int var4 = var5 >> 8;
         if (var4 < 3) {
            this.w.b(1, var6);
         } else {
            this.w.b(2, var6);
         }

         this.n.clear();
         this.b();
         this.e();
      }
   }

   @Override
   protected void b(int var1, int var2) {
      RenderHelper.a();
      this.a(this.q, I18n.a("tile.beacon.primary"), 62, 10, 14737632);
      this.a(this.q, I18n.a("tile.beacon.secondary"), 169, 10, 14737632);

      for (GuiButton var4 : this.n) {
         if (var4.a()) {
            var4.b(var1 - this.i, var2 - this.r);
            break;
         }
      }

      RenderHelper.c();
   }

   @Override
   protected void a(float var1, int var2, int var3) {
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      this.j.P().a(v);
      int var4 = (this.l - this.f) / 2;
      int var5 = (this.m - this.g) / 2;
      this.b(var4, var5, 0, 0, this.f, this.g);
      this.k.a = 100.0F;
      this.k.b(new ItemStack(Items.bO), var4 + 42, var5 + 109);
      this.k.b(new ItemStack(Items.i), var4 + 42 + 22, var5 + 109);
      this.k.b(new ItemStack(Items.k), var4 + 42 + 44, var5 + 109);
      this.k.b(new ItemStack(Items.j), var4 + 42 + 66, var5 + 109);
      this.k.a = 0.0F;
   }

   static class Button extends GuiButton {
      private final ResourceLocation o;
      private final int p;
      private final int q;
      private boolean r;

      protected Button(int var1, int var2, int var3, ResourceLocation var4, int var5, int var6) {
         super(var1, var2, var3, 22, 22, "");
         this.o = var4;
         this.p = var5;
         this.q = var6;
      }

      @Override
      public void a(Minecraft var1, int var2, int var3) {
         if (this.m) {
            var1.P().a(GuiBeacon.v);
            GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
            this.n = var2 >= this.h && var3 >= this.i && var2 < this.h + this.f && var3 < this.i + this.g;
            short var4 = 219;
            int var5 = 0;
            if (!this.l) {
               var5 += this.f * 2;
            } else if (this.r) {
               var5 += this.f * 1;
            } else if (this.n) {
               var5 += this.f * 3;
            }

            this.b(this.h, this.i, var5, var4, this.f, this.g);
            if (!GuiBeacon.v.equals(this.o)) {
               var1.P().a(this.o);
            }

            this.b(this.h + 2, this.i + 2, this.p, this.q, 18, 18);
         }
      }

      public boolean c() {
         return this.r;
      }

      public void b(boolean var1) {
         this.r = var1;
      }
   }

   class CancelButton extends GuiBeacon.Button {
      public CancelButton(int var2, int var3, int var4) {
         super(var2, var3, var4, GuiBeacon.v, 112, 220);
      }

      @Override
      public void b(int var1, int var2) {
         GuiBeacon.this.a(I18n.a("gui.cancel"), var1, var2);
      }
   }

   class ConfirmButton extends GuiBeacon.Button {
      public ConfirmButton(int var2, int var3, int var4) {
         super(var2, var3, var4, GuiBeacon.v, 90, 220);
      }

      @Override
      public void b(int var1, int var2) {
         GuiBeacon.this.a(I18n.a("gui.done"), var1, var2);
      }
   }

   class PowerButton extends GuiBeacon.Button {
      private final int p;
      private final int q;

      public PowerButton(int var2, int var3, int var4, int var5, int var6) {
         super(var2, var3, var4, GuiContainer.a, 0 + Potion.a[var5].f() % 8 * 18, 198 + Potion.a[var5].f() / 8 * 18);
         this.p = var5;
         this.q = var6;
      }

      @Override
      public void b(int var1, int var2) {
         String var3 = I18n.a(Potion.a[this.p].a());
         if (this.q >= 3 && this.p != Potion.l.H) {
            var3 = var3 + " II";
         }

         GuiBeacon.this.a(var3, var1, var2);
      }
   }
}
