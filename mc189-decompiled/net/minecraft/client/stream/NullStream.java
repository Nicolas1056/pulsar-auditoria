package net.minecraft.client.stream;

import tv.twitch.ErrorCode;
import tv.twitch.broadcast.IngestServer;
import tv.twitch.chat.ChatUserInfo;

public class NullStream implements IStream {
   private final Throwable a;

   public NullStream(Throwable var1) {
      this.a = var1;
   }

   @Override
   public void f() {
   }

   @Override
   public void g() {
   }

   @Override
   public void h() {
   }

   @Override
   public boolean i() {
      return false;
   }

   @Override
   public boolean j() {
      return false;
   }

   @Override
   public boolean k() {
      return false;
   }

   @Override
   public void a(Metadata var1, long var2) {
   }

   @Override
   public void a(Metadata var1, long var2, long var4) {
   }

   @Override
   public boolean l() {
      return false;
   }

   @Override
   public void m() {
   }

   @Override
   public void n() {
   }

   @Override
   public void o() {
   }

   @Override
   public void p() {
   }

   @Override
   public void q() {
   }

   @Override
   public void r() {
   }

   @Override
   public IngestServer[] s() {
      return new IngestServer[0];
   }

   @Override
   public void u() {
   }

   @Override
   public IngestServerTester v() {
      return null;
   }

   @Override
   public boolean w() {
      return false;
   }

   @Override
   public int x() {
      return 0;
   }

   @Override
   public boolean y() {
      return false;
   }

   @Override
   public String z() {
      return null;
   }

   @Override
   public ChatUserInfo e(String var1) {
      return null;
   }

   @Override
   public void f(String var1) {
   }

   @Override
   public boolean A() {
      return false;
   }

   @Override
   public ErrorCode B() {
      return null;
   }

   @Override
   public boolean C() {
      return false;
   }

   @Override
   public void a(boolean var1) {
   }

   @Override
   public boolean D() {
      return false;
   }

   @Override
   public IStream.AuthFailureReason E() {
      return IStream.AuthFailureReason.a;
   }

   public Throwable a() {
      return this.a;
   }
}
