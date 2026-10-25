package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import org.lwjgl.util.glu.Project;

public class GuiEnchantment extends GuiContainer {
   private static final ResourceLocation C = new ResourceLocation("textures/gui/container/enchanting_table.png");
   private static final ResourceLocation D = new ResourceLocation("textures/entity/enchanting_table_book.png");
   private static final ModelBook E = new ModelBook();
   private final InventoryPlayer F;
   private Random G = new Random();
   private ContainerEnchantment H;
   public int u;
   public float v;
   public float w;
   public float x;
   public float y;
   public float z;
   public float A;
   ItemStack B;
   private final IWorldNameable I;

   public GuiEnchantment(InventoryPlayer var1, World var2, IWorldNameable var3) {
      super(new ContainerEnchantment(var1, var2));
      this.F = var1;
      this.H = (ContainerEnchantment)this.h;
      this.I = var3;
   }

   @Override
   protected void b(int var1, int var2) {
      this.q.a(this.I.f_().c(), 12, 5, 4210752);
      this.q.a(this.F.f_().c(), 8, this.g - 96 + 2, 4210752);
   }

   @Override
   public void e() {
      super.e();
      this.a();
   }

   @Override
   protected void a(int var1, int var2, int var3) {
      super.a(var1, var2, var3);
      int var4 = (this.l - this.f) / 2;
      int var5 = (this.m - this.g) / 2;

      for (int var6 = 0; var6 < 3; var6++) {
         int var7 = var1 - (var4 + 60);
         int var8 = var2 - (var5 + 14 + 19 * var6);
         if (var7 >= 0 && var8 >= 0 && var7 < 108 && var8 < 19 && this.H.a(this.j.h, var6)) {
            this.j.c.a(this.H.d, var6);
         }
      }
   }

   @Override
   protected void a(float var1, int var2, int var3) {
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      this.j.P().a(C);
      int var4 = (this.l - this.f) / 2;
      int var5 = (this.m - this.g) / 2;
      this.b(var4, var5, 0, 0, this.f, this.g);
      GlStateManager.E();
      GlStateManager.n(5889);
      GlStateManager.E();
      GlStateManager.D();
      ScaledResolution var6 = new ScaledResolution(this.j);
      GlStateManager.b((var6.a() - 320) / 2 * var6.e(), (var6.b() - 240) / 2 * var6.e(), 320 * var6.e(), 240 * var6.e());
      GlStateManager.b(-0.34F, 0.23F, 0.0F);
      Project.gluPerspective(90.0F, 1.3333334F, 9.0F, 80.0F);
      float var7 = 1.0F;
      GlStateManager.n(5888);
      GlStateManager.D();
      RenderHelper.b();
      GlStateManager.b(0.0F, 3.3F, -16.0F);
      GlStateManager.a(var7, var7, var7);
      float var8 = 5.0F;
      GlStateManager.a(var8, var8, var8);
      GlStateManager.b(180.0F, 0.0F, 0.0F, 1.0F);
      this.j.P().a(D);
      GlStateManager.b(20.0F, 1.0F, 0.0F, 0.0F);
      float var9 = this.A + (this.z - this.A) * var1;
      GlStateManager.b((1.0F - var9) * 0.2F, (1.0F - var9) * 0.1F, (1.0F - var9) * 0.25F);
      GlStateManager.b(-(1.0F - var9) * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(180.0F, 1.0F, 0.0F, 0.0F);
      float var10 = this.w + (this.v - this.w) * var1 + 0.25F;
      float var11 = this.w + (this.v - this.w) * var1 + 0.75F;
      var10 = (var10 - MathHelper.b((double)var10)) * 1.6F - 0.3F;
      var11 = (var11 - MathHelper.b((double)var11)) * 1.6F - 0.3F;
      if (var10 < 0.0F) {
         var10 = 0.0F;
      }

      if (var11 < 0.0F) {
         var11 = 0.0F;
      }

      if (var10 > 1.0F) {
         var10 = 1.0F;
      }

      if (var11 > 1.0F) {
         var11 = 1.0F;
      }

      GlStateManager.B();
      E.a(null, 0.0F, var10, var11, var9, 0.0F, 0.0625F);
      GlStateManager.C();
      RenderHelper.a();
      GlStateManager.n(5889);
      GlStateManager.b(0, 0, this.j.d, this.j.e);
      GlStateManager.F();
      GlStateManager.n(5888);
      GlStateManager.F();
      RenderHelper.a();
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      EnchantmentNameParts.a().a(this.H.f);
      int var12 = this.H.e();

      for (int var13 = 0; var13 < 3; var13++) {
         int var14 = var4 + 60;
         int var15 = var14 + 20;
         byte var16 = 86;
         String var17 = EnchantmentNameParts.a().b();
         this.e = 0.0F;
         this.j.P().a(C);
         int var18 = this.H.g[var13];
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         if (var18 == 0) {
            this.b(var14, var5 + 14 + 19 * var13, 0, 185, 108, 19);
         } else {
            String var19 = "" + var18;
            FontRenderer var20 = this.j.l;
            int var21 = 6839882;
            if ((var12 < var13 + 1 || this.j.h.bB < var18) && !this.j.h.bA.d) {
               this.b(var14, var5 + 14 + 19 * var13, 0, 185, 108, 19);
               this.b(var14 + 1, var5 + 15 + 19 * var13, 16 * var13, 239, 16, 16);
               var20.a(var17, var15, var5 + 16 + 19 * var13, var16, (var21 & 16711422) >> 1);
               var21 = 4226832;
            } else {
               int var22 = var2 - (var4 + 60);
               int var23 = var3 - (var5 + 14 + 19 * var13);
               if (var22 >= 0 && var23 >= 0 && var22 < 108 && var23 < 19) {
                  this.b(var14, var5 + 14 + 19 * var13, 0, 204, 108, 19);
                  var21 = 16777088;
               } else {
                  this.b(var14, var5 + 14 + 19 * var13, 0, 166, 108, 19);
               }

               this.b(var14 + 1, var5 + 15 + 19 * var13, 16 * var13, 223, 16, 16);
               var20.a(var17, var15, var5 + 16 + 19 * var13, var16, var21);
               var21 = 8453920;
            }

            var20 = this.j.k;
            var20.a(var19, (float)(var15 + 86 - var20.a(var19)), (float)(var5 + 16 + 19 * var13 + 7), var21);
         }
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      super.a(var1, var2, var3);
      boolean var4 = this.j.h.bA.d;
      int var5 = this.H.e();

      for (int var6 = 0; var6 < 3; var6++) {
         int var7 = this.H.g[var6];
         int var8 = this.H.h[var6];
         int var9 = var6 + 1;
         if (this.c(60, 14 + 19 * var6, 108, 17, var1, var2) && var7 > 0 && var8 >= 0) {
            ArrayList var10 = Lists.newArrayList();
            if (var8 >= 0 && Enchantment.c(var8 & 0xFF) != null) {
               String var11 = Enchantment.c(var8 & 0xFF).d((var8 & 0xFF00) >> 8);
               var10.add(EnumChatFormatting.p.toString() + EnumChatFormatting.u.toString() + I18n.a("container.enchant.clue", var11));
            }

            if (!var4) {
               if (var8 >= 0) {
                  var10.add("");
               }

               if (this.j.h.bB < var7) {
                  var10.add(EnumChatFormatting.m.toString() + "Level Requirement: " + this.H.g[var6]);
               } else {
                  String var12 = "";
                  if (var9 == 1) {
                     var12 = I18n.a("container.enchant.lapis.one");
                  } else {
                     var12 = I18n.a("container.enchant.lapis.many", var9);
                  }

                  if (var5 >= var9) {
                     var10.add(EnumChatFormatting.h.toString() + "" + var12);
                  } else {
                     var10.add(EnumChatFormatting.m.toString() + "" + var12);
                  }

                  if (var9 == 1) {
                     var12 = I18n.a("container.enchant.level.one");
                  } else {
                     var12 = I18n.a("container.enchant.level.many", var9);
                  }

                  var10.add(EnumChatFormatting.h.toString() + "" + var12);
               }
            }

            this.a(var10, var1, var2);
            break;
         }
      }
   }

   @Override
   public void a() {
      ItemStack var1 = this.h.a(0).d();
      if (!ItemStack.b(var1, this.B)) {
         this.B = var1;

         do {
            this.x = this.x + (this.G.nextInt(4) - this.G.nextInt(4));
         } while (this.v <= this.x + 1.0F && this.v >= this.x - 1.0F);
      }

      this.u++;
      this.w = this.v;
      this.A = this.z;
      boolean var2 = false;

      for (int var3 = 0; var3 < 3; var3++) {
         if (this.H.g[var3] != 0) {
            var2 = true;
         }
      }

      if (var2) {
         this.z += 0.2F;
      } else {
         this.z -= 0.2F;
      }

      this.z = MathHelper.a(this.z, 0.0F, 1.0F);
      float var5 = (this.x - this.v) * 0.4F;
      float var4 = 0.2F;
      var5 = MathHelper.a(var5, -var4, var4);
      this.y = this.y + (var5 - this.y) * 0.9F;
      this.v = this.v + this.y;
   }
}
