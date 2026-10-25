package net.minecraft.util;

import net.minecraft.crash.CrashReport;

public class ReportedException extends RuntimeException {
   private final CrashReport a;

   public ReportedException(CrashReport var1) {
      this.a = var1;
   }

   public CrashReport a() {
      return this.a;
   }

   @Override
   public Throwable getCause() {
      return this.a.b();
   }

   @Override
   public String getMessage() {
      return this.a.a();
   }
}
