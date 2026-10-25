package com.metrofico.pulsar.launcher.auth;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.types.Variant;

final class LinuxKeyrings {
   static final long PROBE_SECONDS = 5L;
   static final long PROMPT_SECONDS = 60L;
   static final String APP = "Pulsar Launcher";

   private LinuxKeyrings() {
   }

   static final class KWalletNative implements SecretStores.Native {
      static final String FOLDER = "Pulsar Launcher";
      private static final List<String[]> DAEMONS = List.of(
         new String[]{"org.kde.kwalletd6", "/modules/kwalletd6"}, new String[]{"org.kde.kwalletd5", "/modules/kwalletd5"}
      );

      @Override
      public String describe() {
         return "KWallet";
      }

      @Override
      public boolean persistent() {
         return true;
      }

      @Override
      public boolean available() {
         return Dbus.<Boolean>withSession(5L, conn -> wallet(conn) != null).orElse(false);
      }

      private static Dbus.KWallet wallet(DBusConnection conn) throws Exception {
         for (String[] d : DAEMONS) {
            if (Dbus.present(conn, d[0])) {
               Dbus.KWallet w = (Dbus.KWallet)conn.getRemoteObject(d[0], d[1], Dbus.KWallet.class);
               return w.isEnabled() ? w : null;
            }
         }

         return null;
      }

      private static <T> Optional<T> withWallet(LinuxKeyrings.KWalletNative.WalletOp<T> op) {
         return Dbus.withSession(65L, conn -> {
            Dbus.KWallet w = wallet(conn);
            if (w == null) {
               return null;
            } else {
               int handle = w.open(w.networkWallet(), 0L, "Pulsar Launcher");
               if (handle < 0) {
                  return null;
               } else {
                  Object var4;
                  try {
                     if (!w.hasFolder(handle, "Pulsar Launcher", "Pulsar Launcher")) {
                        w.createFolder(handle, "Pulsar Launcher", "Pulsar Launcher");
                     }

                     var4 = op.run(w, handle);
                  } finally {
                     w.close(handle, false, "Pulsar Launcher");
                  }

                  return (T)var4;
               }
            }
         });
      }

      @Override
      public Optional<String> get(String key) {
         return withWallet(
               (w, h) -> w.hasEntry(h, "Pulsar Launcher", key, "Pulsar Launcher") ? w.readPassword(h, "Pulsar Launcher", key, "Pulsar Launcher") : null
            )
            .filter(s -> !s.isEmpty());
      }

      @Override
      public void put(String key, String value) {
         withWallet((w, h) -> w.writePassword(h, "Pulsar Launcher", key, value, "Pulsar Launcher"));
      }

      @Override
      public void delete(String key) {
         withWallet((w, h) -> w.removeEntry(h, "Pulsar Launcher", key, "Pulsar Launcher"));
      }

      @FunctionalInterface
      private interface WalletOp<T> {
         T run(Dbus.KWallet var1, int var2) throws Exception;
      }
   }

   static final class SecretService implements SecretStores.Native {
      static final String BUS = "org.freedesktop.secrets";
      static final String SERVICE_PATH = "/org/freedesktop/secrets";
      static final String DEFAULT_COLLECTION = "/org/freedesktop/secrets/aliases/default";

      @Override
      public String describe() {
         return "llavero del sistema (Secret Service)";
      }

      @Override
      public boolean persistent() {
         return true;
      }

      @Override
      public boolean available() {
         return Dbus.<Boolean>withSession(5L, conn -> {
            if (!Dbus.present(conn, "org.freedesktop.secrets")) {
               return false;
            } else {
               DBusPath session = openSession(conn);
               closeSession(conn, session);
               return true;
            }
         }).orElse(false);
      }

      private static Map<String, String> attributes(String key) {
         return Map.of("service", "com.metrofico.pulsar", "account", key);
      }

      @Override
      public Optional<String> get(String key) {
         return Dbus.<String>withSession(65L, conn -> {
            Dbus.Service service = service(conn);
            Dbus.Pair<List<DBusPath>, List<DBusPath>> found = service.SearchItems(attributes(key));
            DBusPath item;
            if (!found.a.isEmpty()) {
               item = found.a.get(0);
            } else {
               if (found.b.isEmpty()) {
                  return null;
               }

               item = found.b.get(0);
               if (!unlock(conn, service, List.of(item))) {
                  return null;
               }
            }

            DBusPath session = openSession(conn);

            String var7;
            try {
               Dbus.Secret secret = ((Dbus.Item)conn.getRemoteObject("org.freedesktop.secrets", item.getPath(), Dbus.Item.class)).GetSecret(session);
               var7 = new String(secret.value, StandardCharsets.UTF_8);
            } finally {
               closeSession(conn, session);
            }

            return var7;
         }).filter(s -> !s.isEmpty());
      }

      @Override
      public void put(String key, String value) {
         Dbus.withSession(
            65L,
            conn -> {
               Dbus.Service service = service(conn);
               DBusPath collection = defaultCollection(conn, service);
               if (collection != null && unlock(conn, service, List.of(collection))) {
                  DBusPath session = openSession(conn);

                  Boolean var9;
                  try {
                     Map<String, Variant<?>> props = new HashMap<>();
                     props.put("org.freedesktop.Secret.Item.Label", new Variant("Pulsar Launcher (" + key + ")"));
                     props.put("org.freedesktop.Secret.Item.Attributes", new Variant(new HashMap<>(attributes(key)), "a{ss}"));
                     Dbus.Secret secret = new Dbus.Secret(session, new byte[0], value.getBytes(StandardCharsets.UTF_8), "text/plain; charset=utf8");
                     Dbus.Pair<DBusPath, DBusPath> r = ((Dbus.Collection)conn.getRemoteObject(
                           "org.freedesktop.secrets", collection.getPath(), Dbus.Collection.class
                        ))
                        .CreateItem(props, secret, true);
                     var9 = Dbus.isRoot(r.b) || prompt(conn, r.b);
                  } finally {
                     closeSession(conn, session);
                  }

                  return var9;
               } else {
                  return false;
               }
            }
         );
      }

      @Override
      public void delete(String key) {
         Dbus.withSession(65L, conn -> {
            Dbus.Pair<List<DBusPath>, List<DBusPath>> found = service(conn).SearchItems(attributes(key));

            for (List<DBusPath> list : List.of(found.a, found.b)) {
               for (DBusPath item : list) {
                  DBusPath p = ((Dbus.Item)conn.getRemoteObject("org.freedesktop.secrets", item.getPath(), Dbus.Item.class)).Delete();
                  if (!Dbus.isRoot(p)) {
                     prompt(conn, p);
                  }
               }
            }

            return true;
         });
      }

      private static Dbus.Service service(DBusConnection conn) throws Exception {
         return (Dbus.Service)conn.getRemoteObject("org.freedesktop.secrets", "/org/freedesktop/secrets", Dbus.Service.class);
      }

      private static DBusPath openSession(DBusConnection conn) throws Exception {
         return service(conn).OpenSession("plain", new Variant("")).b;
      }

      private static void closeSession(DBusConnection conn, DBusPath session) {
         try {
            ((Dbus.Session)conn.getRemoteObject("org.freedesktop.secrets", session.getPath(), Dbus.Session.class)).Close();
         } catch (Exception var3) {
         }
      }

      private static DBusPath defaultCollection(DBusConnection conn, Dbus.Service service) throws Exception {
         DBusPath alias = service.ReadAlias("default");
         if (!Dbus.isRoot(alias)) {
            return alias;
         } else {
            Map<String, Variant<?>> props = Map.of("org.freedesktop.Secret.Collection.Label", new Variant("Default keyring"));
            Dbus.Pair<DBusPath, DBusPath> r = service.CreateCollection(props, "default");
            if (!Dbus.isRoot(r.a)) {
               return r.a;
            } else {
               return prompt(conn, r.b) ? new DBusPath("/org/freedesktop/secrets/aliases/default") : null;
            }
         }
      }

      private static boolean unlock(DBusConnection conn, Dbus.Service service, List<DBusPath> objects) throws Exception {
         Dbus.Pair<List<DBusPath>, DBusPath> r = service.Unlock(objects);
         return Dbus.isRoot(r.b) ? !r.a.isEmpty() : prompt(conn, r.b);
      }

      private static boolean prompt(DBusConnection conn, DBusPath prompt) throws Exception {
         CompletableFuture<Boolean> done = new CompletableFuture<>();

         boolean var4;
         try (AutoCloseable ignored = conn.addSigHandler(Dbus.Prompt.Completed.class, s -> {
               if (prompt.getPath().equals(s.getPath())) {
                  done.complete(!s.dismissed);
               }
            })) {
            ((Dbus.Prompt)conn.getRemoteObject("org.freedesktop.secrets", prompt.getPath(), Dbus.Prompt.class)).Prompt("");
            var4 = done.get(60L, TimeUnit.SECONDS);
         }

         return var4;
      }
   }
}
