package com.metrofico.pulsar.api.net;

import java.util.Deque;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.LongSupplier;

public final class TaskDrain<T> {
   private Object[] batch;
   private int deferred;

   public TaskDrain(int initialCapacity) {
      this.batch = new Object[Math.max(1, initialCapacity)];
   }

   public int run(Deque<T> queue, Object lock, IntConsumer onDrained, Consumer<? super T> runner) {
      return this.run(queue, lock, onDrained, runner, Long.MAX_VALUE, System::nanoTime);
   }

   public int run(Deque<T> queue, Object lock, IntConsumer onDrained, Consumer<? super T> runner, long budgetNanos, LongSupplier clock) {
      this.deferred = 0;
      long start = budgetNanos == Long.MAX_VALUE ? 0L : clock.getAsLong();
      int n;
      synchronized (lock) {
         n = queue.size();
         if (n > this.batch.length) {
            this.batch = new Object[Math.max(n, this.batch.length * 2)];
         }

         for (int i = 0; i < n; i++) {
            this.batch[i] = queue.pollFirst();
         }

         onDrained.accept(n);
      }

      int done = 0;
      boolean overBudget = false;

      try {
         while (done < n) {
            T task = (T)this.batch[done];
            this.batch[done] = null;
            done++;
            runner.accept(task);
            if (budgetNanos != Long.MAX_VALUE && done < n && clock.getAsLong() - start >= budgetNanos) {
               overBudget = true;
               break;
            }
         }
      } finally {
         if (done < n) {
            this.deferred = n - done;
            this.requeue(queue, lock, done, n);
         }
      }

      return overBudget ? done : n;
   }

   public int deferred() {
      return this.deferred;
   }

   private void requeue(Deque<T> queue, Object lock, int from, int to) {
      synchronized (lock) {
         for (int i = to - 1; i >= from; i--) {
            T task = (T)this.batch[i];
            this.batch[i] = null;
            queue.addFirst(task);
         }
      }
   }

   int capacity() {
      return this.batch.length;
   }

   int retained() {
      int n = 0;

      for (Object o : this.batch) {
         n += o == null ? 0 : 1;
      }

      return n;
   }
}
