package com.metrofico.pulsar.launcher.daemon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public final class Protocol {
   public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

   public static String ok(long id, Object result) {
      JsonObject o = new JsonObject();
      o.addProperty("id", id);
      o.addProperty("ok", true);
      o.add("result", GSON.toJsonTree(result));
      return GSON.toJson(o);
   }

   public static String error(long id, DaemonException e) {
      JsonObject o = new JsonObject();
      o.addProperty("id", id);
      o.addProperty("ok", false);
      JsonObject err = new JsonObject();
      err.addProperty("code", e.code());
      err.addProperty("title", e.title());
      err.addProperty("message", e.getMessage());
      if (e.detail() != null) {
         err.addProperty("detail", e.detail());
      }

      o.add("error", err);
      return GSON.toJson(o);
   }

   public static String event(String name, Object payload) {
      JsonObject o = GSON.toJsonTree(payload).getAsJsonObject();
      o.addProperty("event", name);
      return GSON.toJson(o);
   }

   private Protocol() {
   }

   public record Request(long id, String cmd, JsonObject args) {
      public static Protocol.Request parse(String line) {
         JsonObject o;
         try {
            o = (JsonObject)Protocol.GSON.fromJson(line, JsonObject.class);
         } catch (JsonParseException var5) {
            throw new DaemonException("bad_request", "Petición no válida", "La línea no es JSON: " + line);
         }

         if (o != null && o.has("cmd")) {
            long id = o.has("id") ? o.get("id").getAsLong() : -1L;
            JsonObject args = o.has("args") && o.get("args").isJsonObject() ? o.getAsJsonObject("args") : new JsonObject();
            return new Protocol.Request(id, o.get("cmd").getAsString(), args);
         } else {
            throw new DaemonException("bad_request", "Petición no válida", "Falta \"cmd\"");
         }
      }

      public String string(String key) {
         JsonElement e = this.args.get(key);
         if (e != null && !e.isJsonNull()) {
            return e.getAsString();
         } else {
            throw new DaemonException("bad_request", "Petición no válida", "Falta el argumento \"" + key + "\" en " + this.cmd);
         }
      }

      public String stringOr(String key, String fallback) {
         JsonElement e = this.args.get(key);
         return e != null && !e.isJsonNull() ? e.getAsString() : fallback;
      }
   }
}
