package net.minecraft.client.main;

import com.mojang.authlib.properties.PropertyMap;
import java.io.File;
import java.net.Proxy;
import net.minecraft.util.Session;

public class GameConfiguration {
   public final GameConfiguration.UserInformation a;
   public final GameConfiguration.DisplayInformation b;
   public final GameConfiguration.FolderInformation c;
   public final GameConfiguration.GameInformation d;
   public final GameConfiguration.ServerInformation e;

   public GameConfiguration(
      GameConfiguration.UserInformation var1,
      GameConfiguration.DisplayInformation var2,
      GameConfiguration.FolderInformation var3,
      GameConfiguration.GameInformation var4,
      GameConfiguration.ServerInformation var5
   ) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
      this.e = var5;
   }

   public static class DisplayInformation {
      public final int a;
      public final int b;
      public final boolean c;
      public final boolean d;

      public DisplayInformation(int var1, int var2, boolean var3, boolean var4) {
         this.a = var1;
         this.b = var2;
         this.c = var3;
         this.d = var4;
      }
   }

   public static class FolderInformation {
      public final File a;
      public final File b;
      public final File c;
      public final String d;

      public FolderInformation(File var1, File var2, File var3, String var4) {
         this.a = var1;
         this.b = var2;
         this.c = var3;
         this.d = var4;
      }
   }

   public static class GameInformation {
      public final boolean a;
      public final String b;

      public GameInformation(boolean var1, String var2) {
         this.a = var1;
         this.b = var2;
      }
   }

   public static class ServerInformation {
      public final String a;
      public final int b;

      public ServerInformation(String var1, int var2) {
         this.a = var1;
         this.b = var2;
      }
   }

   public static class UserInformation {
      public final Session a;
      public final PropertyMap b;
      public final PropertyMap c;
      public final Proxy d;

      public UserInformation(Session var1, PropertyMap var2, PropertyMap var3, Proxy var4) {
         this.a = var1;
         this.b = var2;
         this.c = var3;
         this.d = var4;
      }
   }
}
