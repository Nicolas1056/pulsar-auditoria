package com.metrofico.pulsar.launcher.auth;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public final class MicrosoftAuth {
   private final HttpClient http;
   private final AuthEndpoints endpoints;

   public MicrosoftAuth(HttpClient http, AuthEndpoints endpoints) {
      this.http = http;
      this.endpoints = endpoints;
   }

   public AuthEndpoints endpoints() {
      return this.endpoints;
   }

   public AuthorizationRequest beginAuthorization() {
      String state = UUID.randomUUID().toString();
      Map<String, String> query = new LinkedHashMap<>();
      query.put("client_id", this.endpoints.clientId());
      query.put("response_type", "code");
      query.put("scope", this.endpoints.scope());
      query.put("redirect_uri", this.endpoints.redirectUri());
      query.put("prompt", "select_account");
      query.put("state", state);
      return new AuthorizationRequest(this.endpoints.authorizeUrl() + "?" + encode(query), this.endpoints.redirectUri(), state);
   }

   public MsaToken exchangeRedirect(AuthorizationRequest request, String redirectUrl) throws AuthException {
      if (redirectUrl != null && redirectUrl.startsWith(request.redirectUri())) {
         Map<String, String> params = queryParams(redirectUrl);
         String error = params.get("error");
         if (error == null) {
            String returnedState = params.get("state");
            if (returnedState != null && !returnedState.equals(request.state())) {
               throw new AuthException(
                  "msa_error", "No se pudo iniciar sesión", "La respuesta de Microsoft no corresponde a este inicio de sesión. Vuelve a intentarlo."
               );
            } else {
               String code = params.get("code");
               if (code != null && !code.isBlank()) {
                  Map<String, String> form = new LinkedHashMap<>();
                  form.put("client_id", this.endpoints.clientId());
                  form.put("code", code);
                  form.put("grant_type", "authorization_code");
                  form.put("redirect_uri", request.redirectUri());
                  form.put("scope", this.endpoints.scope());
                  JsonObject o = this.postForm(this.endpoints.tokenUrl(), form, "No se pudo iniciar sesión");
                  if (!o.has("access_token")) {
                     throw new AuthException("msa_error", "No se pudo iniciar sesión", "Microsoft no devolvió el token de acceso.");
                  } else {
                     return msaToken(o);
                  }
               } else {
                  throw new AuthException("msa_error", "No se pudo iniciar sesión", "Microsoft no devolvió el código de acceso.");
               }
            }
         } else if ("access_denied".equals(error)) {
            throw new AuthException("declined", "Inicio de sesión cancelado", "No se concedió acceso a la cuenta. Puedes intentarlo de nuevo.");
         } else {
            throw new AuthException("msa_error", "No se pudo iniciar sesión", "Microsoft respondió: " + error);
         }
      } else {
         throw new AuthException("msa_error", "No se pudo iniciar sesión", "La ventana de Microsoft terminó en una dirección inesperada.");
      }
   }

   static Map<String, String> queryParams(String url) {
      Map<String, String> params = new LinkedHashMap<>();
      int start = url.indexOf(63);
      if (start < 0) {
         start = url.indexOf(35);
      }

      if (start < 0) {
         return params;
      } else {
         for (String pair : url.substring(start + 1).split("[&#]")) {
            int eq = pair.indexOf(61);
            if (eq > 0) {
               params.putIfAbsent(
                  URLDecoder.decode(pair.substring(0, eq), StandardCharsets.UTF_8), URLDecoder.decode(pair.substring(eq + 1), StandardCharsets.UTF_8)
               );
            }
         }

         return params;
      }
   }

   public DeviceCode beginDeviceCode() throws AuthException {
      Map<String, String> form = this.endpoints.azure()
         ? Map.of("client_id", this.endpoints.clientId(), "scope", this.endpoints.scope())
         : Map.of("client_id", this.endpoints.clientId(), "scope", this.endpoints.scope(), "response_type", "device_code");
      JsonObject o = this.postForm(this.endpoints.deviceCodeUrl(), form, "No se pudo iniciar sesión");
      return new DeviceCode(
         str(o, "user_code"),
         str(o, "verification_uri"),
         str(o, "device_code"),
         Math.max(1, intOr(o, "interval", 5)),
         System.currentTimeMillis() + intOr(o, "expires_in", 900) * 1000L
      );
   }

   public Optional<MsaToken> pollDeviceCode(DeviceCode code) throws AuthException {
      Map<String, String> form = Map.of(
         "client_id", this.endpoints.clientId(), "device_code", code.deviceCode(), "grant_type", "urn:ietf:params:oauth:grant-type:device_code"
      );
      MicrosoftAuth.Response r = this.send(
         HttpRequest.newBuilder(URI.create(this.endpoints.tokenUrl()))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .POST(BodyPublishers.ofString(encode(form)))
            .build()
      );
      JsonObject o = r.json();
      if (r.status == 200 && o.has("access_token")) {
         return Optional.of(msaToken(o));
      } else {
         String error = o.has("error") ? o.get("error").getAsString() : "http_" + r.status;
         switch (error) {
            case "authorization_pending":
            case "slow_down":
               return Optional.empty();
            case "expired_token":
               throw new AuthException("code_expired", "El código caducó", "Vuelve a pulsar Iniciar sesión para obtener uno nuevo.");
            case "authorization_declined":
            case "access_denied":
               throw new AuthException("declined", "Inicio de sesión cancelado", "No se concedió acceso a la cuenta. Puedes intentarlo de nuevo.");
            default:
               throw new AuthException("msa_error", "No se pudo iniciar sesión", "Microsoft respondió: " + error);
         }
      }
   }

   public MsaToken refresh(String refreshToken) throws AuthException {
      Map<String, String> form = Map.of(
         "client_id", this.endpoints.clientId(), "refresh_token", refreshToken, "grant_type", "refresh_token", "scope", this.endpoints.scope()
      );
      MicrosoftAuth.Response r = this.send(
         HttpRequest.newBuilder(URI.create(this.endpoints.tokenUrl()))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .POST(BodyPublishers.ofString(encode(form)))
            .build()
      );
      JsonObject o = r.json();
      if (r.status >= 500) {
         throw new AuthException("msa_unavailable", "Microsoft no responde", "HTTP " + r.status + "; se reintentará.");
      } else if (r.status == 200 && o.has("access_token")) {
         return msaToken(o);
      } else {
         String error = o.has("error") ? o.get("error").getAsString() : "HTTP " + r.status;
         if (r.status == 400 && !"invalid_grant".equals(error)) {
            throw new AuthException("msa_unavailable", "Microsoft no respondió bien", error + "; se reintentará.");
         } else {
            throw new AuthException("session_expired", "Tu sesión caducó", "Vuelve a iniciar sesión con Microsoft.");
         }
      }
   }

   public MicrosoftAuth.XboxToken xboxLive(MsaToken msa) throws AuthException {
      JsonObject props = new JsonObject();
      props.addProperty("AuthMethod", "RPS");
      props.addProperty("SiteName", "user.auth.xboxlive.com");
      props.addProperty("RpsTicket", (this.endpoints.azure() ? "d=" : "t=") + msa.accessToken());
      JsonObject body = new JsonObject();
      body.add("Properties", props);
      body.addProperty("RelyingParty", "http://auth.xboxlive.com");
      body.addProperty("TokenType", "JWT");
      MicrosoftAuth.Response r = this.postJson(this.endpoints.xblUrl(), body);
      if (r.status != 200) {
         throw new AuthException("xbox_error", "Xbox Live no respondió", "No se pudo validar la cuenta con Xbox Live (HTTP " + r.status + ").");
      } else {
         return xboxToken(r.json());
      }
   }

   public MicrosoftAuth.XboxToken xsts(MicrosoftAuth.XboxToken xbl, String relyingParty) throws AuthException {
      JsonObject props = new JsonObject();
      props.addProperty("SandboxId", "RETAIL");
      JsonArray tokens = new JsonArray();
      tokens.add(xbl.token());
      props.add("UserTokens", tokens);
      JsonObject body = new JsonObject();
      body.add("Properties", props);
      body.addProperty("RelyingParty", relyingParty);
      body.addProperty("TokenType", "JWT");
      MicrosoftAuth.Response r = this.postJson(this.endpoints.xstsUrl(), body);
      if (r.status == 401) {
         long xerr = r.json().has("XErr") ? r.json().get("XErr").getAsLong() : 0L;
         if (xerr == 2148916233L) {
            throw new AuthException(
               "no_xbox_account", "Esta cuenta no tiene perfil de Xbox", "Crea uno en xbox.com con esta misma cuenta y vuelve a intentarlo."
            );
         } else if (xerr == 2148916235L) {
            throw new AuthException("region_blocked", "Xbox Live no está disponible en tu región", "No es posible iniciar sesión desde esta cuenta.");
         } else if (xerr == 2148916238L) {
            throw new AuthException(
               "child_account", "Cuenta infantil sin familia", "Un adulto debe añadir esta cuenta a una familia de Microsoft antes de poder jugar."
            );
         } else {
            throw new AuthException("xsts_error", "Xbox Live rechazó la sesión", "Código " + xerr + ".");
         }
      } else if (r.status != 200) {
         throw new AuthException("xsts_error", "Xbox Live no respondió", "HTTP " + r.status);
      } else {
         return xboxToken(r.json());
      }
   }

   public MinecraftSession minecraft(MicrosoftAuth.XboxToken xsts, String xuid) throws AuthException {
      JsonObject body = new JsonObject();
      body.addProperty("identityToken", "XBL3.0 x=" + xsts.userHash() + ";" + xsts.token());
      MicrosoftAuth.Response r = this.postJson(this.endpoints.minecraftLoginUrl(), body);
      if (r.status != 200) {
         throw new AuthException("minecraft_error", "Los servicios de Minecraft no respondieron", "HTTP " + r.status);
      } else {
         JsonObject o = r.json();
         String token = str(o, "access_token");
         long expiresAt = System.currentTimeMillis() + intOr(o, "expires_in", 86400) * 1000L;
         MicrosoftAuth.Response p = this.send(
            HttpRequest.newBuilder(URI.create(this.endpoints.minecraftProfileUrl())).header("Authorization", "Bearer " + token).GET().build()
         );
         if (p.status == 404) {
            throw new AuthException(
               "no_minecraft", "Esta cuenta no tiene Minecraft: Java Edition", "Inicia sesión con la cuenta que compró el juego, o cómpralo en minecraft.net."
            );
         } else if (p.status != 200) {
            throw new AuthException("minecraft_error", "No se pudo leer tu perfil de Minecraft", "HTTP " + p.status);
         } else {
            JsonObject profile = p.json();
            String skin = null;
            if (profile.has("skins") && profile.get("skins").isJsonArray()) {
               for (JsonElement e : profile.getAsJsonArray("skins")) {
                  JsonObject s = e.getAsJsonObject();
                  if ("ACTIVE".equalsIgnoreCase(s.has("state") ? s.get("state").getAsString() : "")) {
                     skin = str(s, "url");
                  }
               }
            }

            return new MinecraftSession(str(profile, "name"), uuid(str(profile, "id")), token, expiresAt, xuid, skin);
         }
      }
   }

   public MinecraftSession complete(MsaToken msa) throws AuthException {
      MicrosoftAuth.XboxToken xbl = this.xboxLive(msa);
      MicrosoftAuth.XboxToken mcXsts = this.xsts(xbl, "rp://api.minecraftservices.com/");
      String xuid = mcXsts.xuid();
      if (xuid == null) {
         try {
            xuid = this.xsts(xbl, "http://xboxlive.com").xuid();
         } catch (AuthException var6) {
            xuid = null;
         }
      }

      return this.minecraft(mcXsts, xuid == null ? "" : xuid);
   }

   private MicrosoftAuth.Response send(HttpRequest request) throws AuthException {
      try {
         HttpResponse<String> r = this.http.send(request, BodyHandlers.ofString(StandardCharsets.UTF_8));
         return new MicrosoftAuth.Response(r.statusCode(), r.body());
      } catch (ConnectException var3) {
         throw new AuthException("offline", "Sin conexión", "No se pudo contactar con Microsoft. Revisa tu conexión y vuelve a intentarlo.", var3);
      } catch (IOException var4) {
         throw new AuthException("network", "Problema de red", var4.getMessage(), var4);
      } catch (InterruptedException var5) {
         Thread.currentThread().interrupt();
         throw new AuthException("cancelled", "Inicio de sesión cancelado", "");
      }
   }

   private JsonObject postForm(String url, Map<String, String> form, String title) throws AuthException {
      MicrosoftAuth.Response r = this.send(
         HttpRequest.newBuilder(URI.create(url))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .POST(BodyPublishers.ofString(encode(form)))
            .build()
      );
      if (r.status != 200) {
         throw new AuthException("msa_error", title, "Microsoft respondió HTTP " + r.status + ".");
      } else {
         return r.json();
      }
   }

   private MicrosoftAuth.Response postJson(String url, JsonObject body) throws AuthException {
      return this.send(
         HttpRequest.newBuilder(URI.create(url))
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .POST(BodyPublishers.ofString(body.toString()))
            .build()
      );
   }

   private static MsaToken msaToken(JsonObject o) {
      return new MsaToken(
         str(o, "access_token"),
         o.has("refresh_token") ? o.get("refresh_token").getAsString() : null,
         System.currentTimeMillis() + intOr(o, "expires_in", 3600) * 1000L
      );
   }

   private static MicrosoftAuth.XboxToken xboxToken(JsonObject o) throws AuthException {
      try {
         JsonObject xui = o.getAsJsonObject("DisplayClaims").getAsJsonArray("xui").get(0).getAsJsonObject();
         return new MicrosoftAuth.XboxToken(str(o, "Token"), str(xui, "uhs"), xui.has("xid") ? xui.get("xid").getAsString() : null);
      } catch (RuntimeException var2) {
         throw new AuthException("xbox_error", "Respuesta inesperada de Xbox Live", var2.toString());
      }
   }

   private static String encode(Map<String, String> form) {
      StringBuilder sb = new StringBuilder();
      form.forEach(
         (k, v) -> sb.append(sb.isEmpty() ? "" : "&")
            .append(URLEncoder.encode(k, StandardCharsets.UTF_8))
            .append('=')
            .append(URLEncoder.encode(v, StandardCharsets.UTF_8))
      );
      return sb.toString();
   }

   private static String str(JsonObject o, String key) {
      return o.has(key) && !o.get(key).isJsonNull() ? o.get(key).getAsString() : "";
   }

   private static int intOr(JsonObject o, String key, int fallback) {
      try {
         return o.has(key) ? o.get(key).getAsInt() : fallback;
      } catch (RuntimeException var4) {
         return fallback;
      }
   }

   static UUID uuid(String undashed) {
      String s = undashed.replace("-", "");
      return s.length() != 32
         ? UUID.nameUUIDFromBytes(undashed.getBytes(StandardCharsets.UTF_8))
         : UUID.fromString(s.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
   }

   private record Response(int status, String body) {
      JsonObject json() {
         try {
            JsonElement e = JsonParser.parseString(this.body != null && !this.body.isBlank() ? this.body : "{}");
            return e.isJsonObject() ? e.getAsJsonObject() : new JsonObject();
         } catch (RuntimeException var2) {
            return new JsonObject();
         }
      }
   }

   public record XboxToken(String token, String userHash, String xuid) {
   }
}
