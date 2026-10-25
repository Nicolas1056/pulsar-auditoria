package net.minecraft.client.stream;

import tv.twitch.ErrorCode;
import tv.twitch.broadcast.IngestServer;
import tv.twitch.chat.ChatUserInfo;

public interface IStream {
   void f();

   void g();

   void h();

   boolean i();

   boolean j();

   boolean k();

   void a(Metadata var1, long var2);

   void a(Metadata var1, long var2, long var4);

   boolean l();

   void m();

   void n();

   void o();

   void p();

   void q();

   void r();

   IngestServer[] s();

   void u();

   IngestServerTester v();

   boolean w();

   int x();

   boolean y();

   String z();

   ChatUserInfo e(String var1);

   void f(String var1);

   boolean A();

   ErrorCode B();

   boolean C();

   void a(boolean var1);

   boolean D();

   IStream.AuthFailureReason E();

   public static enum AuthFailureReason {
      a,
      b;
   }
}
