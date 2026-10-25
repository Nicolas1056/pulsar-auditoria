package com.metrofico.pulsar.api.input;

import com.metrofico.pulsar.api.gfx.Canvas;

public final class MouseLockView {
   private static final int BUTTON_W = 140;
   private static final int BUTTON_H = 20;
   private final MouseLock.Block block;
   private final Runnable quit;
   private int buttonX;
   private int buttonY;

   public MouseLockView(MouseLock.Block block, Runnable quit) {
      this.block = block;
      this.quit = quit;
   }

   String title() {
      return switch (this.block.reason()) {
         case OTHER_DEVICE -> "Se ha usado otro ratón";
         case DETECTOR_BLIND -> "Pulsar no puede leer un ratón conectado";
         case DETECTOR_GONE, DETECTOR_SILENT -> "Pulsar ha dejado de comprobar tu ratón";
      };
   }

   String detail() {
      String device = this.block.device() == null ? "" : this.block.device().displayName();

      return switch (this.block.reason()) {
         case OTHER_DEVICE -> "Esta partida va con un solo ratón y se ha hecho clic con " + device + ".";
         case DETECTOR_BLIND -> device + " está conectado y Pulsar no tiene permiso para leerlo.";
         case DETECTOR_GONE, DETECTOR_SILENT -> "El detector de ratones de Pulsar se ha cerrado.";
      };
   }

   public void render(Canvas g, int mouseX, int mouseY) {
      int width = g.width();
      int height = g.height();
      g.fill(0, 0, width, height, -300871407);
      int cx = width / 2;
      int y = height / 2 - 40;
      center(g, this.title(), cx, y, -855310);
      center(g, this.detail(), cx, y + 18, -5000269);
      center(g, "Para cambiar de ratón, cierra el juego y vuelve a abrirlo desde Pulsar.", cx, y + 30, -5000269);
      this.buttonX = cx - 70;
      this.buttonY = y + 54;
      boolean hover = mouseX >= this.buttonX && mouseX < this.buttonX + 140 && mouseY >= this.buttonY && mouseY < this.buttonY + 20;
      g.fill(this.buttonX, this.buttonY, this.buttonX + 140, this.buttonY + 20, hover ? -868464036 : -870441442);
      g.outline(this.buttonX, this.buttonY, 140, 20, hover ? -11930 : -7697782);
      String label = "Cerrar el juego";
      g.vtext(label, cx - g.vwidth(label) / 2, this.buttonY + 6, hover ? -96 : -2039584, true);
   }

   private static void center(Canvas g, String text, int cx, int y, int argb) {
      g.vtext(text, cx - g.vwidth(text) / 2, y, argb, true);
   }

   public boolean mouseClicked(double x, double y, int button) {
      if (button == 0 && x >= this.buttonX && x < this.buttonX + 140 && y >= this.buttonY && y < this.buttonY + 20) {
         this.quit.run();
         return true;
      } else {
         return true;
      }
   }
}
