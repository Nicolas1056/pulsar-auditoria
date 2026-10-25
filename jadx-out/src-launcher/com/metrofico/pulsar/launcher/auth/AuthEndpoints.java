package com.metrofico.pulsar.launcher.auth;

public record AuthEndpoints(
   String clientId,
   String scope,
   boolean azure,
   String deviceCodeUrl,
   String tokenUrl,
   String xblUrl,
   String xstsUrl,
   String minecraftLoginUrl,
   String minecraftProfileUrl,
   String authorizeUrl,
   String redirectUri
) {
   public static final String OFFICIAL_LAUNCHER_CLIENT_ID = "00000000402b5328";
   public static final String PULSAR_CLIENT_ID = "d35d8c14-9bcf-473d-ac3d-f7f99a82737a";
   public static final String DEFAULT_CLIENT_ID = "00000000402b5328";

   public static AuthEndpoints liveConnect() {
      return new AuthEndpoints(
         "00000000402b5328",
         "service::user.auth.xboxlive.com::MBI_SSL",
         false,
         "https://login.live.com/oauth20_connect.srf",
         "https://login.live.com/oauth20_token.srf",
         "https://user.auth.xboxlive.com/user/authenticate",
         "https://xsts.auth.xboxlive.com/xsts/authorize",
         "https://api.minecraftservices.com/authentication/login_with_xbox",
         "https://api.minecraftservices.com/minecraft/profile",
         "https://login.live.com/oauth20_authorize.srf",
         "https://login.live.com/oauth20_desktop.srf"
      );
   }

   public static AuthEndpoints azure(String clientId) {
      return new AuthEndpoints(
         clientId,
         "XboxLive.signin offline_access",
         true,
         "https://login.microsoftonline.com/consumers/oauth2/v2.0/devicecode",
         "https://login.microsoftonline.com/consumers/oauth2/v2.0/token",
         "https://user.auth.xboxlive.com/user/authenticate",
         "https://xsts.auth.xboxlive.com/xsts/authorize",
         "https://api.minecraftservices.com/authentication/login_with_xbox",
         "https://api.minecraftservices.com/minecraft/profile",
         "https://login.microsoftonline.com/consumers/oauth2/v2.0/authorize",
         "https://login.microsoftonline.com/common/oauth2/nativeclient"
      );
   }

   public static AuthEndpoints fromEnvironment() {
      String own = System.getenv("PULSAR_MSA_CLIENT_ID");
      return forClient(own != null && !own.isBlank() ? own.trim() : "00000000402b5328");
   }

   public static AuthEndpoints forClient(String clientId) {
      return "00000000402b5328".equals(clientId) ? liveConnect() : azure(clientId);
   }

   public AuthEndpoints withBase(String base) {
      return new AuthEndpoints(
         this.clientId,
         this.scope,
         this.azure,
         base + "/devicecode",
         base + "/token",
         base + "/xbl",
         base + "/xsts",
         base + "/mc/login",
         base + "/mc/profile",
         base + "/authorize",
         base + "/desktop"
      );
   }
}
