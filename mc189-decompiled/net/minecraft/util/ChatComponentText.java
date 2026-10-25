package net.minecraft.util;

public class ChatComponentText extends ChatComponentStyle {
   private final String b;

   public ChatComponentText(String var1) {
      this.b = var1;
   }

   public String g() {
      return this.b;
   }

   @Override
   public String e() {
      return this.b;
   }

   public ChatComponentText h() {
      ChatComponentText var1 = new ChatComponentText(this.b);
      var1.a(this.b().m());

      for (IChatComponent var3 : this.a()) {
         var1.a(var3.f());
      }

      return var1;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ChatComponentText)) {
         return false;
      } else {
         ChatComponentText var2 = (ChatComponentText)var1;
         return this.b.equals(var2.g()) && super.equals(var1);
      }
   }

   @Override
   public String toString() {
      return "TextComponent{text='" + this.b + '\'' + ", siblings=" + this.a + ", style=" + this.b() + '}';
   }
}
