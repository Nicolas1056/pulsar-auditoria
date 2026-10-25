package com.metrofico.pulsar.api.hud.modules;

import java.util.ArrayDeque;

public final class ClickCounter {
   private static final ArrayDeque<Long> LEFT = new ArrayDeque<>();
   private static final ArrayDeque<Long> RIGHT = new ArrayDeque<>();

   private ClickCounter() {
   }

   public static synchronized void press(int button) {
      long now = System.nanoTime();
      if (button == 0) {
         LEFT.addLast(now);
      } else if (button == 1) {
         RIGHT.addLast(now);
      }
   }

   private static int count(ArrayDeque<Long> q) {
      long cutoff = System.nanoTime() - 1000000000L;

      while (!q.isEmpty() && q.peekFirst() < cutoff) {
         q.pollFirst();
      }

      return q.size();
   }

   public static synchronized int left() {
      return count(LEFT);
   }

   public static synchronized int right() {
      return count(RIGHT);
   }
}
