package com.metrofico.pulsar.api.engine;

import com.metrofico.pulsar.api.event.EventBus;
import com.metrofico.pulsar.api.model.EngineInfo;

public interface Engine extends AutoCloseable {
   EngineInfo info();

   EventBus events();

   @Override
   void close();
}
