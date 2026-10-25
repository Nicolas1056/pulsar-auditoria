package net.minecraft.util;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatComponentTranslation extends ChatComponentStyle {
   private final String d;
   private final Object[] e;
   private final Object f = new Object();
   private long g = -1L;
   List<IChatComponent> b = Lists.newArrayList();
   public static final Pattern c = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");

   public ChatComponentTranslation(String var1, Object... var2) {
      this.d = var1;
      this.e = var2;

      for (Object var6 : var2) {
         if (var6 instanceof IChatComponent) {
            ((IChatComponent)var6).b().a(this.b());
         }
      }
   }

   synchronized void g() {
      synchronized (this.f) {
         long var2 = StatCollector.a();
         if (var2 == this.g) {
            return;
         }

         this.g = var2;
         this.b.clear();
      }

      try {
         this.b(StatCollector.a(this.d));
      } catch (ChatComponentTranslationFormatException var6) {
         this.b.clear();

         try {
            this.b(StatCollector.b(this.d));
         } catch (ChatComponentTranslationFormatException var5) {
            throw var6;
         }
      }
   }

   protected void b(String var1) {
      boolean var2 = false;
      Matcher var3 = c.matcher(var1);
      int var4 = 0;
      int var5 = 0;

      try {
         while (var3.find(var5)) {
            int var6 = var3.start();
            int var7 = var3.end();
            if (var6 > var5) {
               ChatComponentText var8 = new ChatComponentText(String.format(var1.substring(var5, var6)));
               var8.b().a(this.b());
               this.b.add(var8);
            }

            String var14 = var3.group(2);
            String var9 = var1.substring(var6, var7);
            if ("%".equals(var14) && "%%".equals(var9)) {
               ChatComponentText var15 = new ChatComponentText("%");
               var15.b().a(this.b());
               this.b.add(var15);
            } else {
               if (!"s".equals(var14)) {
                  throw new ChatComponentTranslationFormatException(this, "Unsupported format: '" + var9 + "'");
               }

               String var10 = var3.group(1);
               int var11 = var10 != null ? Integer.parseInt(var10) - 1 : var4++;
               if (var11 < this.e.length) {
                  this.b.add(this.a(var11));
               }
            }

            var5 = var7;
         }

         if (var5 < var1.length()) {
            ChatComponentText var13 = new ChatComponentText(String.format(var1.substring(var5)));
            var13.b().a(this.b());
            this.b.add(var13);
         }
      } catch (IllegalFormatException var12) {
         throw new ChatComponentTranslationFormatException(this, var12);
      }
   }

   private IChatComponent a(int var1) {
      if (var1 >= this.e.length) {
         throw new ChatComponentTranslationFormatException(this, var1);
      } else {
         Object var2 = this.e[var1];
         Object var3;
         if (var2 instanceof IChatComponent) {
            var3 = (IChatComponent)var2;
         } else {
            var3 = new ChatComponentText(var2 == null ? "null" : var2.toString());
            ((IChatComponent)var3).b().a(this.b());
         }

         return (IChatComponent)var3;
      }
   }

   @Override
   public IChatComponent a(ChatStyle var1) {
      super.a(var1);

      for (Object var5 : this.e) {
         if (var5 instanceof IChatComponent) {
            ((IChatComponent)var5).b().a(this.b());
         }
      }

      if (this.g > -1L) {
         for (IChatComponent var7 : this.b) {
            var7.b().a(var1);
         }
      }

      return this;
   }

   @Override
   public Iterator<IChatComponent> iterator() {
      this.g();
      return Iterators.concat(a(this.b), a(this.a));
   }

   @Override
   public String e() {
      this.g();
      StringBuilder var1 = new StringBuilder();

      for (IChatComponent var3 : this.b) {
         var1.append(var3.e());
      }

      return var1.toString();
   }

   public ChatComponentTranslation h() {
      Object[] var1 = new Object[this.e.length];

      for (int var2 = 0; var2 < this.e.length; var2++) {
         if (this.e[var2] instanceof IChatComponent) {
            var1[var2] = ((IChatComponent)this.e[var2]).f();
         } else {
            var1[var2] = this.e[var2];
         }
      }

      ChatComponentTranslation var5 = new ChatComponentTranslation(this.d, var1);
      var5.a(this.b().m());

      for (IChatComponent var4 : this.a()) {
         var5.a(var4.f());
      }

      return var5;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ChatComponentTranslation)) {
         return false;
      } else {
         ChatComponentTranslation var2 = (ChatComponentTranslation)var1;
         return Arrays.equals(this.e, var2.e) && this.d.equals(var2.d) && super.equals(var1);
      }
   }

   @Override
   public int hashCode() {
      int var1 = super.hashCode();
      var1 = 31 * var1 + this.d.hashCode();
      return 31 * var1 + Arrays.hashCode(this.e);
   }

   @Override
   public String toString() {
      return "TranslatableComponent{key='" + this.d + '\'' + ", args=" + Arrays.toString(this.e) + ", siblings=" + this.a + ", style=" + this.b() + '}';
   }

   public String i() {
      return this.d;
   }

   public Object[] j() {
      return this.e;
   }
}
