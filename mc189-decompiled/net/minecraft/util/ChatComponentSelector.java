package net.minecraft.util;

public class ChatComponentSelector extends ChatComponentStyle {
   private final String b;

   public ChatComponentSelector(String var1) {
      this.b = var1;
   }

   public String g() {
      return this.b;
   }

   @Override
   public String e() {
      return this.b;
   }

   public ChatComponentSelector h() {
      ChatComponentSelector var1 = new ChatComponentSelector(this.b);
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
      } else if (!(var1 instanceof ChatComponentSelector)) {
         return false;
      } else {
         ChatComponentSelector var2 = (ChatComponentSelector)var1;
         return this.b.equals(var2.b) && super.equals(var1);
      }
   }

   @Override
   public String toString() {
      return "SelectorComponent{pattern='" + this.b + '\'' + ", siblings=" + this.a + ", style=" + this.b() + '}';
   }
}
