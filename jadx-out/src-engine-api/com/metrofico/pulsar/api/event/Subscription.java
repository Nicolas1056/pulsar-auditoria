package com.metrofico.pulsar.api.event;

public interface Subscription extends AutoCloseable {
   @Override
   void close();
}
