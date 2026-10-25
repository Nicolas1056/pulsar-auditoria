package net.minecraft.client.gui.inventory;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;

public class GuiEditSign extends GuiScreen {
   private TileEntitySign a;
   private int f;
   private int g;
   private GuiButton h;

   public GuiEditSign(TileEntitySign var1) {
      this.a = var1;
   }

   @Override
   public void b() {
      this.n.clear();
      Keyboard.enableRepeatEvents(true);
      this.n.add(this.h = new GuiButton(0, this.l / 2 - 100, this.m / 4 + 120, I18n.a("gui.done")));
      this.a.a(false);
   }

   @Override
   public void m() {
      Keyboard.enableRepeatEvents(false);
      NetHandlerPlayClient var1 = this.j.u();
      if (var1 != null) {
         var1.a(new C12PacketUpdateSign(this.a.v(), this.a.a));
      }

      this.a.a(true);
   }

   @Override
   public void e() {
      this.f++;
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.l) {
         if (var1.k == 0) {
            this.a.p_();
            this.j.a(null);
         }
      }
   }

   @Override
   protected void a(char var1, int var2) {
      if (var2 == 200) {
         this.g = this.g - 1 & 3;
      }

      if (var2 == 208 || var2 == 28 || var2 == 156) {
         this.g = this.g + 1 & 3;
      }

      String var3 = this.a.a[this.g].c();
      if (var2 == 14 && var3.length() > 0) {
         var3 = var3.substring(0, var3.length() - 1);
      }

      if (ChatAllowedCharacters.a(var1) && this.q.a(var3 + var1) <= 90) {
         var3 = var3 + var1;
      }

      this.a.a[this.g] = new ChatComponentText(var3);
      if (var2 == 1) {
         this.a(this.h);
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      this.a(this.q, I18n.a("sign.edit"), this.l / 2, 40, 16777215);
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.E();
      GlStateManager.b((float)(this.l / 2), 0.0F, 50.0F);
      float var4 = 93.75F;
      GlStateManager.a(-var4, -var4, -var4);
      GlStateManager.b(180.0F, 0.0F, 1.0F, 0.0F);
      Block var5 = this.a.w();
      if (var5 == Blocks.an) {
         float var6 = this.a.u() * 360 / 16.0F;
         GlStateManager.b(var6, 0.0F, 1.0F, 0.0F);
         GlStateManager.b(0.0F, -1.0625F, 0.0F);
      } else {
         int var8 = this.a.u();
         float var7 = 0.0F;
         if (var8 == 2) {
            var7 = 180.0F;
         }

         if (var8 == 4) {
            var7 = 90.0F;
         }

         if (var8 == 5) {
            var7 = -90.0F;
         }

         GlStateManager.b(var7, 0.0F, 1.0F, 0.0F);
         GlStateManager.b(0.0F, -1.0625F, 0.0F);
      }

      if (this.f / 6 % 2 == 0) {
         this.a.f = this.g;
      }

      TileEntityRendererDispatcher.a.a(this.a, -0.5, -0.75, -0.5, 0.0F);
      this.a.f = -1;
      GlStateManager.F();
      super.a(var1, var2, var3);
   }
}
