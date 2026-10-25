package com.metrofico.pulsar.launcher.auth;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.Struct;
import org.freedesktop.dbus.Tuple;
import org.freedesktop.dbus.annotations.DBusInterfaceName;
import org.freedesktop.dbus.annotations.Position;
import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.connections.impl.DBusConnectionBuilder;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.interfaces.DBus;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.messages.DBusSignal;
import org.freedesktop.dbus.types.Variant;

final class Dbus {
   private Dbus() {
   }

   static <T> Optional<T> withSession(long timeoutSeconds, Dbus.SessionOp<T> op) {
      if (System.getenv("DBUS_SESSION_BUS_ADDRESS") == null && System.getenv("XDG_RUNTIME_DIR") == null) {
         return Optional.empty();
      } else {
         Callable<T> task = () -> {
            DBusConnection conn = DBusConnectionBuilder.forSessionBus().withShared(false).build();

            Object t$;
            try {
               t$ = op.run(conn);
            } catch (Throwable var5) {
               if (conn != null) {
                  try {
                     conn.close();
                  } catch (Throwable var4x) {
                     var5.addSuppressed(var4x);
                  }
               }

               throw var5;
            }

            if (conn != null) {
               conn.close();
            }

            return (T)t$;
         };
         CompletableFuture<T> f = new CompletableFuture<>();
         Thread.startVirtualThread(() -> {
            try {
               f.complete(task.call());
            } catch (Throwable var3x) {
               f.completeExceptionally(var3x);
            }
         });

         try {
            return Optional.ofNullable(f.get(timeoutSeconds, TimeUnit.SECONDS));
         } catch (InterruptedException var6) {
            Thread.currentThread().interrupt();
            return Optional.empty();
         } catch (Exception var7) {
            return Optional.empty();
         }
      }
   }

   static boolean present(DBusConnection conn, String name) throws DBusException {
      DBus bus = (DBus)conn.getRemoteObject("org.freedesktop.DBus", "/org/freedesktop/DBus", DBus.class);
      return bus.NameHasOwner(name) || List.of(bus.ListActivatableNames()).contains(name);
   }

   static boolean isRoot(DBusPath p) {
      return p == null || "/".equals(p.getPath());
   }

   @DBusInterfaceName("org.freedesktop.Secret.Collection")
   public interface Collection extends DBusInterface {
      Dbus.Pair<DBusPath, DBusPath> CreateItem(Map<String, Variant<?>> var1, Dbus.Secret var2, boolean var3);
   }

   @DBusInterfaceName("org.freedesktop.Secret.Item")
   public interface Item extends DBusInterface {
      Dbus.Secret GetSecret(DBusPath var1);

      DBusPath Delete();
   }

   @DBusInterfaceName("org.kde.KWallet")
   public interface KWallet extends DBusInterface {
      boolean isEnabled();

      String networkWallet();

      int open(String var1, long var2, String var4);

      int close(int var1, boolean var2, String var3);

      boolean hasFolder(int var1, String var2, String var3);

      boolean createFolder(int var1, String var2, String var3);

      boolean hasEntry(int var1, String var2, String var3, String var4);

      String readPassword(int var1, String var2, String var3, String var4);

      int writePassword(int var1, String var2, String var3, String var4, String var5);

      int removeEntry(int var1, String var2, String var3, String var4);
   }

   public static final class Pair<A, B> extends Tuple {
      @Position(0)
      public final A a;
      @Position(1)
      public final B b;

      public Pair(A a, B b) {
         this.a = a;
         this.b = b;
      }
   }

   @DBusInterfaceName("org.freedesktop.Secret.Prompt")
   public interface Prompt extends DBusInterface {
      void Prompt(String var1);

      void Dismiss();

      public static class Completed extends DBusSignal {
         public final boolean dismissed;

         public Completed(String path, boolean dismissed, Variant<?> result) throws DBusException {
            super(path, new Object[]{dismissed, result});
            this.dismissed = dismissed;
         }
      }
   }

   public static final class Secret extends Struct {
      @Position(0)
      public final DBusPath session;
      @Position(1)
      public final byte[] parameters;
      @Position(2)
      public final byte[] value;
      @Position(3)
      public final String contentType;

      public Secret(DBusPath session, byte[] parameters, byte[] value, String contentType) {
         this.session = session;
         this.parameters = parameters;
         this.value = value;
         this.contentType = contentType;
      }
   }

   @DBusInterfaceName("org.freedesktop.Secret.Service")
   public interface Service extends DBusInterface {
      Dbus.Pair<Variant<?>, DBusPath> OpenSession(String var1, Variant<?> var2);

      Dbus.Pair<List<DBusPath>, List<DBusPath>> SearchItems(Map<String, String> var1);

      Dbus.Pair<List<DBusPath>, DBusPath> Unlock(List<DBusPath> var1);

      DBusPath ReadAlias(String var1);

      Dbus.Pair<DBusPath, DBusPath> CreateCollection(Map<String, Variant<?>> var1, String var2);
   }

   @DBusInterfaceName("org.freedesktop.Secret.Session")
   public interface Session extends DBusInterface {
      void Close();
   }

   @FunctionalInterface
   interface SessionOp<T> {
      T run(DBusConnection var1) throws Exception;
   }
}
