package net.minecraft.client.gui.achievement;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatCrafting;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.stats.StatList;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class GuiStats extends GuiScreen implements IProgressMeter {
   protected GuiScreen a;
   protected String f = "Select world";
   private GuiStats.StatsGeneral g;
   private GuiStats.StatsItem h;
   private GuiStats.StatsBlock i;
   private GuiStats.StatsMobsList r;
   private StatFileWriter s;
   private GuiSlot t;
   private boolean u = true;

   public GuiStats(GuiScreen var1, StatFileWriter var2) {
      this.a = var1;
      this.s = var2;
   }

   @Override
   public void b() {
      this.f = I18n.a("gui.stats");
      this.u = true;
      this.j.u().a(new C16PacketClientStatus(C16PacketClientStatus.EnumState.b));
   }

   @Override
   public void k() {
      super.k();
      if (this.t != null) {
         this.t.p();
      }
   }

   public void f() {
      this.g = new GuiStats.StatsGeneral(this.j);
      this.g.d(1, 1);
      this.h = new GuiStats.StatsItem(this.j);
      this.h.d(1, 1);
      this.i = new GuiStats.StatsBlock(this.j);
      this.i.d(1, 1);
      this.r = new GuiStats.StatsMobsList(this.j);
      this.r.d(1, 1);
   }

   public void g() {
      this.n.add(new GuiButton(0, this.l / 2 + 4, this.m - 28, 150, 20, I18n.a("gui.done")));
      this.n.add(new GuiButton(1, this.l / 2 - 160, this.m - 52, 80, 20, I18n.a("stat.generalButton")));
      GuiButton var1;
      this.n.add(var1 = new GuiButton(2, this.l / 2 - 80, this.m - 52, 80, 20, I18n.a("stat.blocksButton")));
      GuiButton var2;
      this.n.add(var2 = new GuiButton(3, this.l / 2, this.m - 52, 80, 20, I18n.a("stat.itemsButton")));
      GuiButton var3;
      this.n.add(var3 = new GuiButton(4, this.l / 2 + 80, this.m - 52, 80, 20, I18n.a("stat.mobsButton")));
      if (this.i.b() == 0) {
         var1.l = false;
      }

      if (this.h.b() == 0) {
         var2.l = false;
      }

      if (this.r.b() == 0) {
         var3.l = false;
      }
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.l) {
         if (var1.k == 0) {
            this.j.a(this.a);
         } else if (var1.k == 1) {
            this.t = this.g;
         } else if (var1.k == 3) {
            this.t = this.h;
         } else if (var1.k == 2) {
            this.t = this.i;
         } else if (var1.k == 4) {
            this.t = this.r;
         } else {
            this.t.a(var1);
         }
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      if (this.u) {
         this.c();
         this.a(this.q, I18n.a("multiplayer.downloadingStats"), this.l / 2, this.m / 2, 16777215);
         this.a(this.q, c_[(int)(Minecraft.J() / 150L % c_.length)], this.l / 2, this.m / 2 + this.q.a * 2, 16777215);
      } else {
         this.t.a(var1, var2, var3);
         this.a(this.q, this.f, this.l / 2, 20, 16777215);
         super.a(var1, var2, var3);
      }
   }

   @Override
   public void a() {
      if (this.u) {
         this.f();
         this.g();
         this.t = this.g;
         this.u = false;
      }
   }

   @Override
   public boolean d() {
      return !this.u;
   }

   private void a(int var1, int var2, Item var3) {
      this.b(var1 + 1, var2 + 1);
      GlStateManager.B();
      RenderHelper.c();
      this.k.a(new ItemStack(var3, 1, 0), var1 + 2, var2 + 2);
      RenderHelper.a();
      GlStateManager.C();
   }

   private void b(int var1, int var2) {
      this.c(var1, var2, 0, 0);
   }

   private void c(int var1, int var2, int var3, int var4) {
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      this.j.P().a(c);
      float var5 = 0.0078125F;
      float var6 = 0.0078125F;
      byte var7 = 18;
      byte var8 = 18;
      Tessellator var9 = Tessellator.a();
      WorldRenderer var10 = var9.c();
      var10.a(7, DefaultVertexFormats.g);
      var10.b((double)(var1 + 0), (double)(var2 + 18), (double)this.e).a((double)((var3 + 0) * 0.0078125F), (double)((var4 + 18) * 0.0078125F)).d();
      var10.b((double)(var1 + 18), (double)(var2 + 18), (double)this.e).a((double)((var3 + 18) * 0.0078125F), (double)((var4 + 18) * 0.0078125F)).d();
      var10.b((double)(var1 + 18), (double)(var2 + 0), (double)this.e).a((double)((var3 + 18) * 0.0078125F), (double)((var4 + 0) * 0.0078125F)).d();
      var10.b((double)(var1 + 0), (double)(var2 + 0), (double)this.e).a((double)((var3 + 0) * 0.0078125F), (double)((var4 + 0) * 0.0078125F)).d();
      var9.b();
   }

   abstract class Stats extends GuiSlot {
      protected int v = -1;
      protected List<StatCrafting> w;
      protected Comparator<StatCrafting> x;
      protected int y = -1;
      protected int z;

      protected Stats(Minecraft var2) {
         super(var2, GuiStats.this.l, GuiStats.this.m, 32, GuiStats.this.m - 64, 20);
         this.b(false);
         this.a(true, 20);
      }

      @Override
      protected void a(int var1, boolean var2, int var3, int var4) {
      }

      @Override
      protected boolean a(int var1) {
         return false;
      }

      @Override
      protected void a() {
         GuiStats.this.c();
      }

      @Override
      protected void a(int var1, int var2, Tessellator var3) {
         if (!Mouse.isButtonDown(0)) {
            this.v = -1;
         }

         if (this.v == 0) {
            GuiStats.this.c(var1 + 115 - 18, var2 + 1, 0, 0);
         } else {
            GuiStats.this.c(var1 + 115 - 18, var2 + 1, 0, 18);
         }

         if (this.v == 1) {
            GuiStats.this.c(var1 + 165 - 18, var2 + 1, 0, 0);
         } else {
            GuiStats.this.c(var1 + 165 - 18, var2 + 1, 0, 18);
         }

         if (this.v == 2) {
            GuiStats.this.c(var1 + 215 - 18, var2 + 1, 0, 0);
         } else {
            GuiStats.this.c(var1 + 215 - 18, var2 + 1, 0, 18);
         }

         if (this.y != -1) {
            short var4 = 79;
            byte var5 = 18;
            if (this.y == 1) {
               var4 = 129;
            } else if (this.y == 2) {
               var4 = 179;
            }

            if (this.z == 1) {
               var5 = 36;
            }

            GuiStats.this.c(var1 + var4, var2 + 1, var5, 0);
         }
      }

      @Override
      protected void a(int var1, int var2) {
         this.v = -1;
         if (var1 >= 79 && var1 < 115) {
            this.v = 0;
         } else if (var1 >= 129 && var1 < 165) {
            this.v = 1;
         } else if (var1 >= 179 && var1 < 215) {
            this.v = 2;
         }

         if (this.v >= 0) {
            this.d(this.v);
            this.a.W().a(PositionedSoundRecord.a(new ResourceLocation("gui.button.press"), 1.0F));
         }
      }

      @Override
      protected final int b() {
         return this.w.size();
      }

      protected final StatCrafting c(int var1) {
         return this.w.get(var1);
      }

      protected abstract String b(int var1);

      protected void a(StatBase var1, int var2, int var3, boolean var4) {
         if (var1 != null) {
            String var5 = var1.a(GuiStats.this.s.a(var1));
            GuiStats.this.c(GuiStats.this.q, var5, var2 - GuiStats.this.q.a(var5), var3 + 5, var4 ? 16777215 : 9474192);
         } else {
            String var6 = "-";
            GuiStats.this.c(GuiStats.this.q, var6, var2 - GuiStats.this.q.a(var6), var3 + 5, var4 ? 16777215 : 9474192);
         }
      }

      @Override
      protected void b(int var1, int var2) {
         if (var2 >= this.d && var2 <= this.e) {
            int var3 = this.c(var1, var2);
            int var4 = this.b / 2 - 92 - 16;
            if (var3 >= 0) {
               if (var1 < var4 + 40 || var1 > var4 + 40 + 20) {
                  return;
               }

               StatCrafting var11 = this.c(var3);
               this.a(var11, var1, var2);
            } else {
               String var5 = "";
               if (var1 >= var4 + 115 - 18 && var1 <= var4 + 115) {
                  var5 = this.b(0);
               } else if (var1 >= var4 + 165 - 18 && var1 <= var4 + 165) {
                  var5 = this.b(1);
               } else {
                  if (var1 < var4 + 215 - 18 || var1 > var4 + 215) {
                     return;
                  }

                  var5 = this.b(2);
               }

               var5 = ("" + I18n.a(var5)).trim();
               if (var5.length() > 0) {
                  int var6 = var1 + 12;
                  int var7 = var2 - 12;
                  int var8 = GuiStats.this.q.a(var5);
                  GuiStats.this.a(var6 - 3, var7 - 3, var6 + var8 + 3, var7 + 8 + 3, -1073741824, -1073741824);
                  GuiStats.this.q.a(var5, (float)var6, (float)var7, -1);
               }
            }
         }
      }

      protected void a(StatCrafting var1, int var2, int var3) {
         if (var1 != null) {
            Item var4 = var1.a();
            ItemStack var5 = new ItemStack(var4);
            String var6 = var5.a();
            String var7 = ("" + I18n.a(var6 + ".name")).trim();
            if (var7.length() > 0) {
               int var8 = var2 + 12;
               int var9 = var3 - 12;
               int var10 = GuiStats.this.q.a(var7);
               GuiStats.this.a(var8 - 3, var9 - 3, var8 + var10 + 3, var9 + 8 + 3, -1073741824, -1073741824);
               GuiStats.this.q.a(var7, (float)var8, (float)var9, -1);
            }
         }
      }

      protected void d(int var1) {
         if (var1 != this.y) {
            this.y = var1;
            this.z = -1;
         } else if (this.z == -1) {
            this.z = 1;
         } else {
            this.y = -1;
            this.z = 0;
         }

         Collections.sort(this.w, this.x);
      }
   }

   class StatsBlock extends GuiStats.Stats {
      public StatsBlock(Minecraft var2) {
         super(var2);
         this.w = Lists.newArrayList();

         for (StatCrafting var4 : StatList.e) {
            boolean var5 = false;
            int var6 = Item.b(var4.a());
            if (GuiStats.this.s.a(var4) > 0) {
               var5 = true;
            } else if (StatList.ad[var6] != null && GuiStats.this.s.a(StatList.ad[var6]) > 0) {
               var5 = true;
            } else if (StatList.ac[var6] != null && GuiStats.this.s.a(StatList.ac[var6]) > 0) {
               var5 = true;
            }

            if (var5) {
               this.w.add(var4);
            }
         }

         this.x = new Comparator<StatCrafting>() {
            public int a(StatCrafting var1, StatCrafting var2x) {
               int var3 = Item.b(var1.a());
               int var4 = Item.b(var2x.a());
               StatBase var5x = null;
               StatBase var6x = null;
               if (StatsBlock.this.y == 2) {
                  var5x = StatList.ab[var3];
                  var6x = StatList.ab[var4];
               } else if (StatsBlock.this.y == 0) {
                  var5x = StatList.ac[var3];
                  var6x = StatList.ac[var4];
               } else if (StatsBlock.this.y == 1) {
                  var5x = StatList.ad[var3];
                  var6x = StatList.ad[var4];
               }

               if (var5x != null || var6x != null) {
                  if (var5x == null) {
                     return 1;
                  }

                  if (var6x == null) {
                     return -1;
                  }

                  int var7 = GuiStats.this.s.a(var5x);
                  int var8 = GuiStats.this.s.a(var6x);
                  if (var7 != var8) {
                     return (var7 - var8) * StatsBlock.this.z;
                  }
               }

               return var3 - var4;
            }
         };
      }

      @Override
      protected void a(int var1, int var2, Tessellator var3) {
         super.a(var1, var2, var3);
         if (this.v == 0) {
            GuiStats.this.c(var1 + 115 - 18 + 1, var2 + 1 + 1, 18, 18);
         } else {
            GuiStats.this.c(var1 + 115 - 18, var2 + 1, 18, 18);
         }

         if (this.v == 1) {
            GuiStats.this.c(var1 + 165 - 18 + 1, var2 + 1 + 1, 36, 18);
         } else {
            GuiStats.this.c(var1 + 165 - 18, var2 + 1, 36, 18);
         }

         if (this.v == 2) {
            GuiStats.this.c(var1 + 215 - 18 + 1, var2 + 1 + 1, 54, 18);
         } else {
            GuiStats.this.c(var1 + 215 - 18, var2 + 1, 54, 18);
         }
      }

      @Override
      protected void a(int var1, int var2, int var3, int var4, int var5, int var6) {
         StatCrafting var7 = this.c(var1);
         Item var8 = var7.a();
         GuiStats.this.a(var2 + 40, var3, var8);
         int var9 = Item.b(var8);
         this.a(StatList.ac[var9], var2 + 115, var3, var1 % 2 == 0);
         this.a(StatList.ad[var9], var2 + 165, var3, var1 % 2 == 0);
         this.a(var7, var2 + 215, var3, var1 % 2 == 0);
      }

      @Override
      protected String b(int var1) {
         if (var1 == 0) {
            return "stat.crafted";
         } else {
            return var1 == 1 ? "stat.used" : "stat.mined";
         }
      }
   }

   class StatsGeneral extends GuiSlot {
      public StatsGeneral(Minecraft var2) {
         super(var2, GuiStats.this.l, GuiStats.this.m, 32, GuiStats.this.m - 64, 10);
         this.b(false);
      }

      @Override
      protected int b() {
         return StatList.c.size();
      }

      @Override
      protected void a(int var1, boolean var2, int var3, int var4) {
      }

      @Override
      protected boolean a(int var1) {
         return false;
      }

      @Override
      protected int k() {
         return this.b() * 10;
      }

      @Override
      protected void a() {
         GuiStats.this.c();
      }

      @Override
      protected void a(int var1, int var2, int var3, int var4, int var5, int var6) {
         StatBase var7 = StatList.c.get(var1);
         GuiStats.this.c(GuiStats.this.q, var7.e().c(), var2 + 2, var3 + 1, var1 % 2 == 0 ? 16777215 : 9474192);
         String var8 = var7.a(GuiStats.this.s.a(var7));
         GuiStats.this.c(GuiStats.this.q, var8, var2 + 2 + 213 - GuiStats.this.q.a(var8), var3 + 1, var1 % 2 == 0 ? 16777215 : 9474192);
      }
   }

   class StatsItem extends GuiStats.Stats {
      public StatsItem(Minecraft var2) {
         super(var2);
         this.w = Lists.newArrayList();

         for (StatCrafting var4 : StatList.d) {
            boolean var5 = false;
            int var6 = Item.b(var4.a());
            if (GuiStats.this.s.a(var4) > 0) {
               var5 = true;
            } else if (StatList.ae[var6] != null && GuiStats.this.s.a(StatList.ae[var6]) > 0) {
               var5 = true;
            } else if (StatList.ac[var6] != null && GuiStats.this.s.a(StatList.ac[var6]) > 0) {
               var5 = true;
            }

            if (var5) {
               this.w.add(var4);
            }
         }

         this.x = new Comparator<StatCrafting>() {
            public int a(StatCrafting var1, StatCrafting var2x) {
               int var3 = Item.b(var1.a());
               int var4 = Item.b(var2x.a());
               StatBase var5x = null;
               StatBase var6x = null;
               if (StatsItem.this.y == 0) {
                  var5x = StatList.ae[var3];
                  var6x = StatList.ae[var4];
               } else if (StatsItem.this.y == 1) {
                  var5x = StatList.ac[var3];
                  var6x = StatList.ac[var4];
               } else if (StatsItem.this.y == 2) {
                  var5x = StatList.ad[var3];
                  var6x = StatList.ad[var4];
               }

               if (var5x != null || var6x != null) {
                  if (var5x == null) {
                     return 1;
                  }

                  if (var6x == null) {
                     return -1;
                  }

                  int var7 = GuiStats.this.s.a(var5x);
                  int var8 = GuiStats.this.s.a(var6x);
                  if (var7 != var8) {
                     return (var7 - var8) * StatsItem.this.z;
                  }
               }

               return var3 - var4;
            }
         };
      }

      @Override
      protected void a(int var1, int var2, Tessellator var3) {
         super.a(var1, var2, var3);
         if (this.v == 0) {
            GuiStats.this.c(var1 + 115 - 18 + 1, var2 + 1 + 1, 72, 18);
         } else {
            GuiStats.this.c(var1 + 115 - 18, var2 + 1, 72, 18);
         }

         if (this.v == 1) {
            GuiStats.this.c(var1 + 165 - 18 + 1, var2 + 1 + 1, 18, 18);
         } else {
            GuiStats.this.c(var1 + 165 - 18, var2 + 1, 18, 18);
         }

         if (this.v == 2) {
            GuiStats.this.c(var1 + 215 - 18 + 1, var2 + 1 + 1, 36, 18);
         } else {
            GuiStats.this.c(var1 + 215 - 18, var2 + 1, 36, 18);
         }
      }

      @Override
      protected void a(int var1, int var2, int var3, int var4, int var5, int var6) {
         StatCrafting var7 = this.c(var1);
         Item var8 = var7.a();
         GuiStats.this.a(var2 + 40, var3, var8);
         int var9 = Item.b(var8);
         this.a(StatList.ae[var9], var2 + 115, var3, var1 % 2 == 0);
         this.a(StatList.ac[var9], var2 + 165, var3, var1 % 2 == 0);
         this.a(var7, var2 + 215, var3, var1 % 2 == 0);
      }

      @Override
      protected String b(int var1) {
         if (var1 == 1) {
            return "stat.crafted";
         } else {
            return var1 == 2 ? "stat.used" : "stat.depleted";
         }
      }
   }

   class StatsMobsList extends GuiSlot {
      private final List<EntityList.EntityEggInfo> v = Lists.newArrayList();

      public StatsMobsList(Minecraft var2) {
         super(var2, GuiStats.this.l, GuiStats.this.m, 32, GuiStats.this.m - 64, GuiStats.this.q.a * 4);
         this.b(false);

         for (EntityList.EntityEggInfo var4 : EntityList.a.values()) {
            if (GuiStats.this.s.a(var4.d) > 0 || GuiStats.this.s.a(var4.e) > 0) {
               this.v.add(var4);
            }
         }
      }

      @Override
      protected int b() {
         return this.v.size();
      }

      @Override
      protected void a(int var1, boolean var2, int var3, int var4) {
      }

      @Override
      protected boolean a(int var1) {
         return false;
      }

      @Override
      protected int k() {
         return this.b() * GuiStats.this.q.a * 4;
      }

      @Override
      protected void a() {
         GuiStats.this.c();
      }

      @Override
      protected void a(int var1, int var2, int var3, int var4, int var5, int var6) {
         EntityList.EntityEggInfo var7 = this.v.get(var1);
         String var8 = I18n.a("entity." + EntityList.b(var7.a) + ".name");
         int var9 = GuiStats.this.s.a(var7.d);
         int var10 = GuiStats.this.s.a(var7.e);
         String var11 = I18n.a("stat.entityKills", var9, var8);
         String var12 = I18n.a("stat.entityKilledBy", var8, var10);
         if (var9 == 0) {
            var11 = I18n.a("stat.entityKills.none", var8);
         }

         if (var10 == 0) {
            var12 = I18n.a("stat.entityKilledBy.none", var8);
         }

         GuiStats.this.c(GuiStats.this.q, var8, var2 + 2 - 10, var3 + 1, 16777215);
         GuiStats.this.c(GuiStats.this.q, var11, var2 + 2, var3 + 1 + GuiStats.this.q.a, var9 == 0 ? 6316128 : 9474192);
         GuiStats.this.c(GuiStats.this.q, var12, var2 + 2, var3 + 1 + GuiStats.this.q.a * 2, var10 == 0 ? 6316128 : 9474192);
      }
   }
}
