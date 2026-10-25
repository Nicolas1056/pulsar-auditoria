package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.gson.JsonParseException;
import io.netty.buffer.Unpooled;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiScreenBook extends GuiScreen {
   private static final Logger a = LogManager.getLogger();
   private static final ResourceLocation f = new ResourceLocation("textures/gui/book.png");
   private final EntityPlayer g;
   private final ItemStack h;
   private final boolean i;
   private boolean r;
   private boolean s;
   private int t;
   private int u = 192;
   private int v = 192;
   private int w = 1;
   private int x;
   private NBTTagList y;
   private String z = "";
   private List<IChatComponent> A;
   private int B = -1;
   private GuiScreenBook.NextPageButton C;
   private GuiScreenBook.NextPageButton D;
   private GuiButton E;
   private GuiButton F;
   private GuiButton G;
   private GuiButton H;

   public GuiScreenBook(EntityPlayer var1, ItemStack var2, boolean var3) {
      this.g = var1;
      this.h = var2;
      this.i = var3;
      if (var2.n()) {
         NBTTagCompound var4 = var2.o();
         this.y = var4.c("pages", 8);
         if (this.y != null) {
            this.y = (NBTTagList)this.y.b();
            this.w = this.y.c();
            if (this.w < 1) {
               this.w = 1;
            }
         }
      }

      if (this.y == null && var3) {
         this.y = new NBTTagList();
         this.y.a(new NBTTagString(""));
         this.w = 1;
      }
   }

   @Override
   public void e() {
      super.e();
      this.t++;
   }

   @Override
   public void b() {
      this.n.clear();
      Keyboard.enableRepeatEvents(true);
      if (this.i) {
         this.n.add(this.F = new GuiButton(3, this.l / 2 - 100, 4 + this.v, 98, 20, I18n.a("book.signButton")));
         this.n.add(this.E = new GuiButton(0, this.l / 2 + 2, 4 + this.v, 98, 20, I18n.a("gui.done")));
         this.n.add(this.G = new GuiButton(5, this.l / 2 - 100, 4 + this.v, 98, 20, I18n.a("book.finalizeButton")));
         this.n.add(this.H = new GuiButton(4, this.l / 2 + 2, 4 + this.v, 98, 20, I18n.a("gui.cancel")));
      } else {
         this.n.add(this.E = new GuiButton(0, this.l / 2 - 100, 4 + this.v, 200, 20, I18n.a("gui.done")));
      }

      int var1 = (this.l - this.u) / 2;
      byte var2 = 2;
      this.n.add(this.C = new GuiScreenBook.NextPageButton(1, var1 + 120, var2 + 154, true));
      this.n.add(this.D = new GuiScreenBook.NextPageButton(2, var1 + 38, var2 + 154, false));
      this.f();
   }

   @Override
   public void m() {
      Keyboard.enableRepeatEvents(false);
   }

   private void f() {
      this.C.m = !this.s && (this.x < this.w - 1 || this.i);
      this.D.m = !this.s && this.x > 0;
      this.E.m = !this.i || !this.s;
      if (this.i) {
         this.F.m = !this.s;
         this.H.m = this.s;
         this.G.m = this.s;
         this.G.l = this.z.trim().length() > 0;
      }
   }

   private void a(boolean var1) {
      if (this.i && this.r) {
         if (this.y != null) {
            while (this.y.c() > 1) {
               String var2 = this.y.f(this.y.c() - 1);
               if (var2.length() != 0) {
                  break;
               }

               this.y.a(this.y.c() - 1);
            }

            if (this.h.n()) {
               NBTTagCompound var6 = this.h.o();
               var6.a("pages", this.y);
            } else {
               this.h.a("pages", this.y);
            }

            String var7 = "MC|BEdit";
            if (var1) {
               var7 = "MC|BSign";
               this.h.a("author", new NBTTagString(this.g.e_()));
               this.h.a("title", new NBTTagString(this.z.trim()));

               for (int var3 = 0; var3 < this.y.c(); var3++) {
                  String var4 = this.y.f(var3);
                  ChatComponentText var5 = new ChatComponentText(var4);
                  var4 = IChatComponent.Serializer.a(var5);
                  this.y.a(var3, new NBTTagString(var4));
               }

               this.h.a(Items.bN);
            }

            PacketBuffer var8 = new PacketBuffer(Unpooled.buffer());
            var8.a(this.h);
            this.j.u().a(new C17PacketCustomPayload(var7, var8));
         }
      }
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.l) {
         if (var1.k == 0) {
            this.j.a(null);
            this.a(false);
         } else if (var1.k == 3 && this.i) {
            this.s = true;
         } else if (var1.k == 1) {
            if (this.x < this.w - 1) {
               this.x++;
            } else if (this.i) {
               this.g();
               if (this.x < this.w - 1) {
                  this.x++;
               }
            }
         } else if (var1.k == 2) {
            if (this.x > 0) {
               this.x--;
            }
         } else if (var1.k == 5 && this.s) {
            this.a(true);
            this.j.a(null);
         } else if (var1.k == 4 && this.s) {
            this.s = false;
         }

         this.f();
      }
   }

   private void g() {
      if (this.y != null && this.y.c() < 50) {
         this.y.a(new NBTTagString(""));
         this.w++;
         this.r = true;
      }
   }

   @Override
   protected void a(char var1, int var2) {
      super.a(var1, var2);
      if (this.i) {
         if (this.s) {
            this.c(var1, var2);
         } else {
            this.b(var1, var2);
         }
      }
   }

   private void b(char var1, int var2) {
      if (GuiScreen.e(var2)) {
         this.b(GuiScreen.o());
      } else {
         switch (var2) {
            case 14:
               String var3 = this.h();
               if (var3.length() > 0) {
                  this.a(var3.substring(0, var3.length() - 1));
               }

               return;
            case 28:
            case 156:
               this.b("\n");
               return;
            default:
               if (ChatAllowedCharacters.a(var1)) {
                  this.b(Character.toString(var1));
               }
         }
      }
   }

   private void c(char var1, int var2) {
      switch (var2) {
         case 14:
            if (!this.z.isEmpty()) {
               this.z = this.z.substring(0, this.z.length() - 1);
               this.f();
            }

            return;
         case 28:
         case 156:
            if (!this.z.isEmpty()) {
               this.a(true);
               this.j.a(null);
            }

            return;
         default:
            if (this.z.length() < 16 && ChatAllowedCharacters.a(var1)) {
               this.z = this.z + Character.toString(var1);
               this.f();
               this.r = true;
            }
      }
   }

   private String h() {
      return this.y != null && this.x >= 0 && this.x < this.y.c() ? this.y.f(this.x) : "";
   }

   private void a(String var1) {
      if (this.y != null && this.x >= 0 && this.x < this.y.c()) {
         this.y.a(this.x, new NBTTagString(var1));
         this.r = true;
      }
   }

   private void b(String var1) {
      String var2 = this.h();
      String var3 = var2 + var1;
      int var4 = this.q.b(var3 + "" + EnumChatFormatting.a + "_", 118);
      if (var4 <= 128 && var3.length() < 256) {
         this.a(var3);
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      this.j.P().a(f);
      int var4 = (this.l - this.u) / 2;
      byte var5 = 2;
      this.b(var4, var5, 0, 0, this.u, this.v);
      if (this.s) {
         String var6 = this.z;
         if (this.i) {
            if (this.t / 6 % 2 == 0) {
               var6 = var6 + "" + EnumChatFormatting.a + "_";
            } else {
               var6 = var6 + "" + EnumChatFormatting.h + "_";
            }
         }

         String var7 = I18n.a("book.editTitle");
         int var8 = this.q.a(var7);
         this.q.a(var7, var4 + 36 + (116 - var8) / 2, var5 + 16 + 16, 0);
         int var9 = this.q.a(var6);
         this.q.a(var6, var4 + 36 + (116 - var9) / 2, var5 + 48, 0);
         Object var10 = I18n.a("book.byAuthor", this.g.e_());
         int var11 = this.q.a((String)var10);
         this.q.a(EnumChatFormatting.i + var10, var4 + 36 + (116 - var11) / 2, var5 + 48 + 10, 0);
         String var12 = I18n.a("book.finalizeWarning");
         this.q.a(var12, var4 + 36, var5 + 80, 116, 0);
      } else {
         String var14 = I18n.a("book.pageIndicator", this.x + 1, this.w);
         String var15 = "";
         if (this.y != null && this.x >= 0 && this.x < this.y.c()) {
            var15 = this.y.f(this.x);
         }

         if (this.i) {
            if (this.q.b()) {
               var15 = var15 + "_";
            } else if (this.t / 6 % 2 == 0) {
               var15 = var15 + "" + EnumChatFormatting.a + "_";
            } else {
               var15 = var15 + "" + EnumChatFormatting.h + "_";
            }
         } else if (this.B != this.x) {
            if (ItemEditableBook.b(this.h.o())) {
               try {
                  IChatComponent var16 = IChatComponent.Serializer.a(var15);
                  this.A = var16 != null ? GuiUtilRenderComponents.a(var16, 116, this.q, true, true) : null;
               } catch (JsonParseException var13) {
                  this.A = null;
               }
            } else {
               ChatComponentText var17 = new ChatComponentText(EnumChatFormatting.e.toString() + "* Invalid book tag *");
               this.A = Lists.newArrayList(var17);
            }

            this.B = this.x;
         }

         int var18 = this.q.a(var14);
         this.q.a(var14, var4 - var18 + this.u - 44, var5 + 16, 0);
         if (this.A == null) {
            this.q.a(var15, var4 + 36, var5 + 16 + 16, 116, 0);
         } else {
            int var19 = Math.min(128 / this.q.a, this.A.size());

            for (int var20 = 0; var20 < var19; var20++) {
               IChatComponent var22 = this.A.get(var20);
               this.q.a(var22.c(), var4 + 36, var5 + 16 + 16 + var20 * this.q.a, 0);
            }

            IChatComponent var21 = this.b(var1, var2);
            if (var21 != null) {
               this.a(var21, var1, var2);
            }
         }
      }

      super.a(var1, var2, var3);
   }

   @Override
   protected void a(int var1, int var2, int var3) {
      if (var3 == 0) {
         IChatComponent var4 = this.b(var1, var2);
         if (this.a(var4)) {
            return;
         }
      }

      super.a(var1, var2, var3);
   }

   @Override
   protected boolean a(IChatComponent var1) {
      ClickEvent var2 = var1 == null ? null : var1.b().h();
      if (var2 == null) {
         return false;
      } else if (var2.a() == ClickEvent.Action.f) {
         String var6 = var2.b();

         try {
            int var4 = Integer.parseInt(var6) - 1;
            if (var4 >= 0 && var4 < this.w && var4 != this.x) {
               this.x = var4;
               this.f();
               return true;
            }
         } catch (Throwable var5) {
         }

         return false;
      } else {
         boolean var3 = super.a(var1);
         if (var3 && var2.a() == ClickEvent.Action.c) {
            this.j.a(null);
         }

         return var3;
      }
   }

   public IChatComponent b(int var1, int var2) {
      if (this.A == null) {
         return null;
      } else {
         int var3 = var1 - (this.l - this.u) / 2 - 36;
         int var4 = var2 - 2 - 16 - 16;
         if (var3 >= 0 && var4 >= 0) {
            int var5 = Math.min(128 / this.q.a, this.A.size());
            if (var3 <= 116 && var4 < this.j.k.a * var5 + var5) {
               int var6 = var4 / this.j.k.a;
               if (var6 >= 0 && var6 < this.A.size()) {
                  IChatComponent var7 = this.A.get(var6);
                  int var8 = 0;

                  for (IChatComponent var10 : var7) {
                     if (var10 instanceof ChatComponentText) {
                        var8 += this.j.k.a(((ChatComponentText)var10).g());
                        if (var8 > var3) {
                           return var10;
                        }
                     }
                  }
               }

               return null;
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   static class NextPageButton extends GuiButton {
      private final boolean o;

      public NextPageButton(int var1, int var2, int var3, boolean var4) {
         super(var1, var2, var3, 23, 13, "");
         this.o = var4;
      }

      @Override
      public void a(Minecraft var1, int var2, int var3) {
         if (this.m) {
            boolean var4 = var2 >= this.h && var3 >= this.i && var2 < this.h + this.f && var3 < this.i + this.g;
            GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
            var1.P().a(GuiScreenBook.f);
            byte var5 = 0;
            short var6 = 192;
            if (var4) {
               var5 += 23;
            }

            if (!this.o) {
               var6 += 13;
            }

            this.b(this.h, this.i, var5, var6, 23, 13);
         }
      }
   }
}
