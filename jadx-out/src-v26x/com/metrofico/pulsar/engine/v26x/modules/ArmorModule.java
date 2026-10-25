package com.metrofico.pulsar.engine.v26x.modules;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.hud.HudBackground;
import com.metrofico.pulsar.api.hud.HudDefaults;
import com.metrofico.pulsar.api.hud.HudModule;
import com.metrofico.pulsar.api.hud.HudModules;
import com.metrofico.pulsar.api.hud.HudText;
import com.metrofico.pulsar.api.hud.Zone;
import com.metrofico.pulsar.engine.v26x.hub.Canvas26;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class ArmorModule implements HudModule {
   private static final EquipmentSlot[] ARMOR = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
   private static final int ROW = 18;
   private static final int GAP = 2;
   static final int TEXT_SIZE_PERCENT = 75;
   private static List<ItemStack> sample;
   private static List<ItemStack> memo = List.of();
   private static int memoTick = -1;
   private static boolean memoEditing;

   public String id() {
      return "armor";
   }

   public Zone zone() {
      return ClientOptions.get().zone("armor.zone", HudDefaults.zone("armor", Zone.LEFT));
   }

   private static List<ItemStack> sample() {
      if (sample == null) {
         sample = List.of(
            new ItemStack(Items.DIAMOND_HELMET),
            new ItemStack(Items.DIAMOND_CHESTPLATE),
            new ItemStack(Items.DIAMOND_LEGGINGS),
            new ItemStack(Items.DIAMOND_BOOTS)
         );
      }

      return sample;
   }

   private static List<ItemStack> items() {
      int tick = HudModules.TICK;
      if (tick != memoTick || memoEditing != HudModules.editing) {
         memo = computeItems();
         memoTick = tick;
         memoEditing = HudModules.editing;
      }

      return memo;
   }

   private static List<ItemStack> computeItems() {
      Minecraft mc = Minecraft.getInstance();
      if (mc.player == null) {
         return HudModules.editing ? sample() : List.of();
      } else {
         ClientOptions o = ClientOptions.get();
         boolean hideEmpty = o.bool("armor.hideEmpty", true);
         List<ItemStack> out = new ArrayList<>();

         for (EquipmentSlot s : ARMOR) {
            ItemStack st = mc.player.getItemBySlot(s);
            if (!st.isEmpty() || !hideEmpty) {
               out.add(st);
            }
         }

         if (o.bool("armor.hands", false)) {
            for (EquipmentSlot sx : new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}) {
               ItemStack st = mc.player.getItemBySlot(sx);
               if (!st.isEmpty() || !hideEmpty) {
                  out.add(st);
               }
            }
         }

         boolean any = false;

         for (ItemStack st : out) {
            any |= !st.isEmpty();
         }

         return !any && HudModules.editing ? sample() : out;
      }
   }

   public boolean present() {
      for (ItemStack s : items()) {
         if (!s.isEmpty()) {
            return true;
         }
      }

      return false;
   }

   private boolean vertical() {
      return !"horizontal".equals(ClientOptions.get().string("armor.layout", "vertical"));
   }

   private static String label(ItemStack s) {
      if (!s.isEmpty() && s.isDamageableItem()) {
         int left = s.getMaxDamage() - s.getDamageValue();
         String var2 = ClientOptions.get().string("armor.durability", "number");

         return switch (var2) {
            case "percent" -> Math.round(100.0F * left / s.getMaxDamage()) + "%";
            case "bar" -> "";
            default -> Integer.toString(left);
         };
      } else {
         return s.isEmpty() ? "" : (s.getCount() > 1 ? Integer.toString(s.getCount()) : "");
      }
   }

   private float textRatio(Canvas c) {
      return HudText.crispRatio(c, this.scale(), ClientOptions.get().integer("armor.textSize", 75) / 100.0);
   }

   private int entryWidth(Canvas c, ItemStack s) {
      String l = label(s);
      return 16 + (l.isEmpty() ? 0 : 3 + (int)Math.ceil(c.vwidth(l) * this.textRatio(c))) + 2;
   }

   private int innerWidth(Canvas c) {
      int w = 0;
      List<ItemStack> list = items();

      for (ItemStack s : list) {
         w = this.vertical() ? Math.max(w, this.entryWidth(c, s)) : w + this.entryWidth(c, s) + 2;
      }

      if (!this.vertical() && !list.isEmpty()) {
         w -= 2;
      }

      return w;
   }

   private int innerHeight() {
      int n = items().size();
      return this.vertical() ? n * 18 + Math.max(0, n - 1) * 2 : 18;
   }

   public int width(Canvas c) {
      return (int)Math.ceil(this.innerWidth(c) * this.scale());
   }

   public int height(Canvas c) {
      return (int)Math.ceil(this.innerHeight() * this.scale());
   }

   private static int wearColor(ItemStack s) {
      if (ClientOptions.get().bool("armor.colorize", true) && s.isDamageableItem()) {
         float f = 1.0F - (float)s.getDamageValue() / s.getMaxDamage();
         int r = f < 0.5F ? 255 : Math.round(255.0F * (1.0F - f) * 2.0F);
         int gr = f > 0.5F ? 255 : Math.round(255.0F * f * 2.0F);
         return 0xFF000000 | r << 16 | gr << 8 | 64;
      } else {
         return -1512206;
      }
   }

   public void draw(Canvas c, int x, int y) {
      boolean bg = ClientOptions.get().bool("armor.background", true);
      int fill = HudBackground.color("armor.opacity");
      GuiGraphicsExtractor g = ((Canvas26)c).gfx();
      float s = (float)this.scale();
      boolean bar = "bar".equals(ClientOptions.get().string("armor.durability", "number"));
      g.pose().pushMatrix();
      g.pose().translate(x, y);
      g.pose().scale(s, s);
      if (bg) {
         c.fill(0, 0, this.innerWidth(c), this.innerHeight(), fill);
      }

      int ex = 0;
      int ey = 0;

      for (ItemStack st : items()) {
         int w = this.entryWidth(c, st);
         if (!st.isEmpty()) {
            g.item(st, ex, ey + 1);
            if (bar && st.isDamageableItem()) {
               float f = 1.0F - (float)st.getDamageValue() / st.getMaxDamage();
               g.fill(ex + 1, ey + 15, ex + 15, ey + 17, -1073741824);
               g.fill(ex + 1, ey + 15, ex + 1 + Math.round(14.0F * f), ey + 17, wearColor(st));
            }

            String l = label(st);
            if (!l.isEmpty()) {
               float r = this.textRatio(c);
               c.push();
               c.translate(ex + 19, ey + 1 + (16.0F - 7.0F * r) / 2.0F);
               c.scale(r, r);
               c.vtext(l, 0, 0, wearColor(st), !bg);
               c.pop();
            }
         } else {
            g.fill(ex + 2, ey + 3, ex + 14, ey + 15, 1090519039);
         }

         if (this.vertical()) {
            ey += 20;
         } else {
            ex += w + 2;
         }
      }

      g.pose().popMatrix();
   }
}
