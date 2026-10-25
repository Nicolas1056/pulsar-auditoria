package net.minecraft.util;

import com.google.common.util.concurrent.ListenableFuture;

public interface IThreadListener {
   ListenableFuture<Object> a(Runnable var1);

   boolean aJ();
}
