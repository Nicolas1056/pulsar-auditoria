package com.metrofico.pulsar.api.hub;

import com.metrofico.pulsar.api.hud.Zone;
import java.util.Locale;

public sealed interface Param permits Param.Toggle, Param.Choice, Param.Range, Param.ZonePick, Param.KeyBind, Param.Color {
   String id();

   String label();

   public record Choice(String id, String label, String[] ids, String[] labels, String def) implements Param {
   }

   public record Color(String id, String label, int def) implements Param {
   }

   public record KeyBind(String id, String label, String action) implements Param {
   }

   public record Range(String id, String label, double min, double max, double step, double def, String suffix, String maxLabel) implements Param {
      public Range(String id, String label, double min, double max, double step, double def) {
         this(id, label, min, max, step, def, "", null);
      }

      public boolean integer() {
         return this.step == Math.floor(this.step);
      }

      public String format(double v) {
         return this.maxLabel != null && v >= this.max
            ? this.maxLabel
            : (this.integer() ? Integer.toString((int)Math.round(v)) : String.format(Locale.ROOT, "%.2f", v).replaceAll("0+$", "").replaceAll("\\.$", ""))
               + this.suffix;
      }

      public double clamp(double v) {
         double snapped = this.min + Math.round((v - this.min) / this.step) * this.step;
         return Math.max(this.min, Math.min(this.max, snapped));
      }
   }

   public record Toggle(String id, String label, boolean def) implements Param {
   }

   public record ZonePick(String id, String label, Zone def) implements Param {
   }
}
