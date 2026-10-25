package net.minecraft.event;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.IChatComponent;

public class HoverEvent {
   private final HoverEvent.Action a;
   private final IChatComponent b;

   public HoverEvent(HoverEvent.Action var1, IChatComponent var2) {
      this.a = var1;
      this.b = var2;
   }

   public HoverEvent.Action a() {
      return this.a;
   }

   public IChatComponent b() {
      return this.b;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         HoverEvent var2 = (HoverEvent)var1;
         if (this.a != var2.a) {
            return false;
         } else {
            return this.b != null ? this.b.equals(var2.b) : var2.b == null;
         }
      } else {
         return false;
      }
   }

   @Override
   public String toString() {
      return "HoverEvent{action=" + this.a + ", value='" + this.b + '\'' + '}';
   }

   @Override
   public int hashCode() {
      int var1 = this.a.hashCode();
      return 31 * var1 + (this.b != null ? this.b.hashCode() : 0);
   }

   public static enum Action {
      a("show_text", true),
      b("show_achievement", true),
      c("show_item", true),
      d("show_entity", true);

      private static final Map<String, HoverEvent.Action> e = Maps.newHashMap();
      private final boolean f;
      private final String g;

      private Action(String var3, boolean var4) {
         this.g = var3;
         this.f = var4;
      }

      public boolean a() {
         return this.f;
      }

      public String b() {
         return this.g;
      }

      public static HoverEvent.Action a(String var0) {
         return e.get(var0);
      }

      static {
         for (HoverEvent.Action var3 : values()) {
            e.put(var3.b(), var3);
         }
      }
   }
}
