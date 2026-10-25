package net.minecraft.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;

public class ChatStyle {
   private ChatStyle a;
   private EnumChatFormatting b;
   private Boolean c;
   private Boolean d;
   private Boolean e;
   private Boolean f;
   private Boolean g;
   private ClickEvent h;
   private HoverEvent i;
   private String j;
   private static final ChatStyle k = new ChatStyle() {
      @Override
      public EnumChatFormatting a() {
         return null;
      }

      @Override
      public boolean b() {
         return false;
      }

      @Override
      public boolean c() {
         return false;
      }

      @Override
      public boolean d() {
         return false;
      }

      @Override
      public boolean e() {
         return false;
      }

      @Override
      public boolean f() {
         return false;
      }

      @Override
      public ClickEvent h() {
         return null;
      }

      @Override
      public HoverEvent i() {
         return null;
      }

      @Override
      public String j() {
         return null;
      }

      @Override
      public ChatStyle a(EnumChatFormatting var1) {
         throw new UnsupportedOperationException();
      }

      @Override
      public ChatStyle a(Boolean var1) {
         throw new UnsupportedOperationException();
      }

      @Override
      public ChatStyle b(Boolean var1) {
         throw new UnsupportedOperationException();
      }

      @Override
      public ChatStyle c(Boolean var1) {
         throw new UnsupportedOperationException();
      }

      @Override
      public ChatStyle d(Boolean var1) {
         throw new UnsupportedOperationException();
      }

      @Override
      public ChatStyle e(Boolean var1) {
         throw new UnsupportedOperationException();
      }

      @Override
      public ChatStyle a(ClickEvent var1) {
         throw new UnsupportedOperationException();
      }

      @Override
      public ChatStyle a(HoverEvent var1) {
         throw new UnsupportedOperationException();
      }

      @Override
      public ChatStyle a(ChatStyle var1) {
         throw new UnsupportedOperationException();
      }

      @Override
      public String toString() {
         return "Style.ROOT";
      }

      @Override
      public ChatStyle m() {
         return this;
      }

      @Override
      public ChatStyle n() {
         return this;
      }

      @Override
      public String k() {
         return "";
      }
   };

   public EnumChatFormatting a() {
      return this.b == null ? this.o().a() : this.b;
   }

   public boolean b() {
      return this.c == null ? this.o().b() : this.c;
   }

   public boolean c() {
      return this.d == null ? this.o().c() : this.d;
   }

   public boolean d() {
      return this.f == null ? this.o().d() : this.f;
   }

   public boolean e() {
      return this.e == null ? this.o().e() : this.e;
   }

   public boolean f() {
      return this.g == null ? this.o().f() : this.g;
   }

   public boolean g() {
      return this.c == null && this.d == null && this.f == null && this.e == null && this.g == null && this.b == null && this.h == null && this.i == null;
   }

   public ClickEvent h() {
      return this.h == null ? this.o().h() : this.h;
   }

   public HoverEvent i() {
      return this.i == null ? this.o().i() : this.i;
   }

   public String j() {
      return this.j == null ? this.o().j() : this.j;
   }

   public ChatStyle a(EnumChatFormatting var1) {
      this.b = var1;
      return this;
   }

   public ChatStyle a(Boolean var1) {
      this.c = var1;
      return this;
   }

   public ChatStyle b(Boolean var1) {
      this.d = var1;
      return this;
   }

   public ChatStyle c(Boolean var1) {
      this.f = var1;
      return this;
   }

   public ChatStyle d(Boolean var1) {
      this.e = var1;
      return this;
   }

   public ChatStyle e(Boolean var1) {
      this.g = var1;
      return this;
   }

   public ChatStyle a(ClickEvent var1) {
      this.h = var1;
      return this;
   }

   public ChatStyle a(HoverEvent var1) {
      this.i = var1;
      return this;
   }

   public ChatStyle a(String var1) {
      this.j = var1;
      return this;
   }

   public ChatStyle a(ChatStyle var1) {
      this.a = var1;
      return this;
   }

   public String k() {
      if (this.g()) {
         return this.a != null ? this.a.k() : "";
      } else {
         StringBuilder var1 = new StringBuilder();
         if (this.a() != null) {
            var1.append(this.a());
         }

         if (this.b()) {
            var1.append(EnumChatFormatting.r);
         }

         if (this.c()) {
            var1.append(EnumChatFormatting.u);
         }

         if (this.e()) {
            var1.append(EnumChatFormatting.t);
         }

         if (this.f()) {
            var1.append(EnumChatFormatting.q);
         }

         if (this.d()) {
            var1.append(EnumChatFormatting.s);
         }

         return var1.toString();
      }
   }

   private ChatStyle o() {
      return this.a == null ? k : this.a;
   }

   @Override
   public String toString() {
      return "Style{hasParent="
         + (this.a != null)
         + ", color="
         + this.b
         + ", bold="
         + this.c
         + ", italic="
         + this.d
         + ", underlined="
         + this.e
         + ", obfuscated="
         + this.g
         + ", clickEvent="
         + this.h()
         + ", hoverEvent="
         + this.i()
         + ", insertion="
         + this.j()
         + '}';
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ChatStyle)) {
         return false;
      } else {
         ChatStyle var2 = (ChatStyle)var1;
         return this.b() == var2.b()
            && this.a() == var2.a()
            && this.c() == var2.c()
            && this.f() == var2.f()
            && this.d() == var2.d()
            && this.e() == var2.e()
            && (this.h() != null ? this.h().equals(var2.h()) : var2.h() == null)
            && (this.i() != null ? this.i().equals(var2.i()) : var2.i() == null)
            && (this.j() != null ? this.j().equals(var2.j()) : var2.j() == null);
      }
   }

   @Override
   public int hashCode() {
      int var1 = this.b.hashCode();
      var1 = 31 * var1 + this.c.hashCode();
      var1 = 31 * var1 + this.d.hashCode();
      var1 = 31 * var1 + this.e.hashCode();
      var1 = 31 * var1 + this.f.hashCode();
      var1 = 31 * var1 + this.g.hashCode();
      var1 = 31 * var1 + this.h.hashCode();
      var1 = 31 * var1 + this.i.hashCode();
      return 31 * var1 + this.j.hashCode();
   }

   public ChatStyle m() {
      ChatStyle var1 = new ChatStyle();
      var1.c = this.c;
      var1.d = this.d;
      var1.f = this.f;
      var1.e = this.e;
      var1.g = this.g;
      var1.b = this.b;
      var1.h = this.h;
      var1.i = this.i;
      var1.a = this.a;
      var1.j = this.j;
      return var1;
   }

   public ChatStyle n() {
      ChatStyle var1 = new ChatStyle();
      var1.a(this.b());
      var1.b(this.c());
      var1.c(this.d());
      var1.d(this.e());
      var1.e(this.f());
      var1.a(this.a());
      var1.a(this.h());
      var1.a(this.i());
      var1.a(this.j());
      return var1;
   }

   public static class Serializer implements JsonDeserializer<ChatStyle>, JsonSerializer<ChatStyle> {
      public ChatStyle a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         if (var1.isJsonObject()) {
            ChatStyle var4 = new ChatStyle();
            JsonObject var5 = var1.getAsJsonObject();
            if (var5 == null) {
               return null;
            } else {
               if (var5.has("bold")) {
                  var4.c = var5.get("bold").getAsBoolean();
               }

               if (var5.has("italic")) {
                  var4.d = var5.get("italic").getAsBoolean();
               }

               if (var5.has("underlined")) {
                  var4.e = var5.get("underlined").getAsBoolean();
               }

               if (var5.has("strikethrough")) {
                  var4.f = var5.get("strikethrough").getAsBoolean();
               }

               if (var5.has("obfuscated")) {
                  var4.g = var5.get("obfuscated").getAsBoolean();
               }

               if (var5.has("color")) {
                  var4.b = (EnumChatFormatting)var3.deserialize(var5.get("color"), EnumChatFormatting.class);
               }

               if (var5.has("insertion")) {
                  var4.j = var5.get("insertion").getAsString();
               }

               if (var5.has("clickEvent")) {
                  JsonObject var6 = var5.getAsJsonObject("clickEvent");
                  if (var6 != null) {
                     JsonPrimitive var7 = var6.getAsJsonPrimitive("action");
                     ClickEvent.Action var8 = var7 == null ? null : ClickEvent.Action.a(var7.getAsString());
                     JsonPrimitive var9 = var6.getAsJsonPrimitive("value");
                     String var10 = var9 == null ? null : var9.getAsString();
                     if (var8 != null && var10 != null && var8.a()) {
                        var4.h = new ClickEvent(var8, var10);
                     }
                  }
               }

               if (var5.has("hoverEvent")) {
                  JsonObject var11 = var5.getAsJsonObject("hoverEvent");
                  if (var11 != null) {
                     JsonPrimitive var12 = var11.getAsJsonPrimitive("action");
                     HoverEvent.Action var13 = var12 == null ? null : HoverEvent.Action.a(var12.getAsString());
                     IChatComponent var14 = (IChatComponent)var3.deserialize(var11.get("value"), IChatComponent.class);
                     if (var13 != null && var14 != null && var13.a()) {
                        var4.i = new HoverEvent(var13, var14);
                     }
                  }
               }

               return var4;
            }
         } else {
            return null;
         }
      }

      public JsonElement a(ChatStyle var1, Type var2, JsonSerializationContext var3) {
         if (var1.g()) {
            return null;
         } else {
            JsonObject var4 = new JsonObject();
            if (var1.c != null) {
               var4.addProperty("bold", var1.c);
            }

            if (var1.d != null) {
               var4.addProperty("italic", var1.d);
            }

            if (var1.e != null) {
               var4.addProperty("underlined", var1.e);
            }

            if (var1.f != null) {
               var4.addProperty("strikethrough", var1.f);
            }

            if (var1.g != null) {
               var4.addProperty("obfuscated", var1.g);
            }

            if (var1.b != null) {
               var4.add("color", var3.serialize(var1.b));
            }

            if (var1.j != null) {
               var4.add("insertion", var3.serialize(var1.j));
            }

            if (var1.h != null) {
               JsonObject var5 = new JsonObject();
               var5.addProperty("action", var1.h.a().b());
               var5.addProperty("value", var1.h.b());
               var4.add("clickEvent", var5);
            }

            if (var1.i != null) {
               JsonObject var6 = new JsonObject();
               var6.addProperty("action", var1.i.a().b());
               var6.add("value", var3.serialize(var1.i.b()));
               var4.add("hoverEvent", var6);
            }

            return var4;
         }
      }
   }
}
