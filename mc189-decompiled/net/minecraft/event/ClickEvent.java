package net.minecraft.event;

import com.google.common.collect.Maps;
import java.util.Map;

public class ClickEvent {
   private final ClickEvent.Action a;
   private final String b;

   public ClickEvent(ClickEvent.Action var1, String var2) {
      this.a = var1;
      this.b = var2;
   }

   public ClickEvent.Action a() {
      return this.a;
   }

   public String b() {
      return this.b;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         ClickEvent var2 = (ClickEvent)var1;
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
      return "ClickEvent{action=" + this.a + ", value='" + this.b + '\'' + '}';
   }

   @Override
   public int hashCode() {
      int var1 = this.a.hashCode();
      return 31 * var1 + (this.b != null ? this.b.hashCode() : 0);
   }

   public static enum Action {
      a("open_url", true),
      b("open_file", false),
      c("run_command", true),
      d("twitch_user_info", false),
      e("suggest_command", true),
      f("change_page", true);

      private static final Map<String, ClickEvent.Action> g = Maps.newHashMap();
      private final boolean h;
      private final String i;

      private Action(String var3, boolean var4) {
         this.i = var3;
         this.h = var4;
      }

      public boolean a() {
         return this.h;
      }

      public String b() {
         return this.i;
      }

      public static ClickEvent.Action a(String var0) {
         return g.get(var0);
      }

      static {
         for (ClickEvent.Action var3 : values()) {
            g.put(var3.b(), var3);
         }
      }
   }
}
