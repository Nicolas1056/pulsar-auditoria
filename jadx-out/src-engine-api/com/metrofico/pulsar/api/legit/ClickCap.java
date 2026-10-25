package com.metrofico.pulsar.api.legit;

import java.util.concurrent.atomic.AtomicIntegerArray;

public final class ClickCap {
   static final int MAX_CAP = 255;
   private static final long SECOND_NS = 1000000000L;
   private static volatile int capLeft;
   private static volatile int capRight;
   private static volatile boolean stale;
   private static final long[][] PRESSES = new long[][]{new long[255], new long[255]};
   private static final int[] HEAD = new int[2];
   private static final int[] FILLED = new int[2];
   private static final boolean[] DOWN_DROPPED = new boolean[2];
   private static final AtomicIntegerArray PRESSED = new AtomicIntegerArray(2);
   private static final AtomicIntegerArray CAPPED = new AtomicIntegerArray(2);

   private ClickCap() {
   }

   public static synchronized void apply(Object left, Object right) {
      capLeft = stricter(capLeft, parse(left));
      capRight = stricter(capRight, parse(right));
   }

   public static synchronized void clear() {
      capLeft = 0;
      capRight = 0;
      stale = true;
   }

   public static int cap(int button) {
      return button == 0 ? capLeft : (button == 1 ? capRight : 0);
   }

   public static boolean drop(int button, boolean pressed, long nanos) {
      if (button != 0 && button != 1) {
         return false;
      } else {
         if (stale) {
            stale = false;
            FILLED[0] = FILLED[1] = 0;
            DOWN_DROPPED[0] = DOWN_DROPPED[1] = false;
         }

         if (!pressed) {
            boolean dropped = DOWN_DROPPED[button];
            DOWN_DROPPED[button] = false;
            return dropped;
         } else {
            PRESSED.incrementAndGet(button);
            int cap = cap(button);
            if (cap == 0) {
               return false;
            } else {
               long[] presses = PRESSES[button];
               int filled = FILLED[button];
               int head = HEAD[button];
               if (filled >= cap) {
                  long nth = presses[Math.floorMod(head - cap, 255)];
                  if (nanos - nth < 1000000000L) {
                     DOWN_DROPPED[button] = true;
                     CAPPED.incrementAndGet(button);
                     return true;
                  }
               }

               presses[head] = nanos;
               HEAD[button] = (head + 1) % 255;
               if (filled < 255) {
                  FILLED[button] = filled + 1;
               }

               DOWN_DROPPED[button] = false;
               return false;
            }
         }
      }
   }

   public static int drainCapped(int button) {
      return button != 0 && button != 1 ? 0 : CAPPED.getAndSet(button, 0);
   }

   static int drainPressed(int button) {
      return button != 0 && button != 1 ? 0 : PRESSED.getAndSet(button, 0);
   }

   private static int stricter(int current, int next) {
      if (next == 0) {
         return current;
      } else {
         return current == 0 ? next : Math.min(current, next);
      }
   }

   private static int parse(Object value) {
      int v;
      if (value instanceof Number n) {
         v = n.intValue();
      } else {
         if (!(value instanceof String s)) {
            return 0;
         }

         try {
            v = Integer.parseInt(s.trim());
         } catch (NumberFormatException var5) {
            return 0;
         }
      }

      return v > 0 && v < 255 ? v : 0;
   }
}
