package com.metrofico.pulsar.launcher.runtime;

import com.metrofico.pulsar.launcher.rules.Platform;
import java.io.IOException;

public interface RuntimeSource {
   String vendor();

   RuntimeRelease latest(int var1, Platform var2) throws IOException, InterruptedException;
}
