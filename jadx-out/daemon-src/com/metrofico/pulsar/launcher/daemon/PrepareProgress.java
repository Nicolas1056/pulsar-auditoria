package com.metrofico.pulsar.launcher.daemon;

public record PrepareProgress(PrepareProgress.Stage stage, int done, int total) {
   public float fraction() {
      float before = 0.0F;

      for (PrepareProgress.Stage s : PrepareProgress.Stage.values()) {
         if (s == this.stage) {
            break;
         }

         before += s.weight;
      }

      float within = this.total <= 0 ? 1.0F : Math.min(1.0F, Math.max(0.0F, (float)this.done / this.total));
      return Math.min(1.0F, before + this.stage.weight * within);
   }

   public int percent() {
      return (int)(this.fraction() * 100.0F);
   }

   public String statusText() {
      return this.total > 0 ? this.stage.label + " — " + this.percent() + " %" : this.stage.label;
   }

   public PrepareProgress.Event toEvent(long requestId) {
      return new PrepareProgress.Event(requestId, this.stage.name().toLowerCase(), this.done, this.total, this.fraction(), this.percent(), this.statusText());
   }

   public record Event(long requestId, String stage, int done, int total, float fraction, int percent, String text) {
   }

   public static enum Stage {
      MANIFEST(0.02F, "Consultando versiones"),
      RUNTIME(0.1F, "Preparando Java"),
      CLIENT(0.08F, "Descargando el cliente"),
      LIBRARIES(0.15F, "Verificando bibliotecas"),
      ASSETS(0.65F, "Verificando recursos");

      final float weight;
      final String label;

      private Stage(float weight, String label) {
         this.weight = weight;
         this.label = label;
      }
   }
}
