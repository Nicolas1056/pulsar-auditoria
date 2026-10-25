package net.minecraft.util;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;

public abstract class ChatComponentStyle implements IChatComponent {
   protected List<IChatComponent> a = Lists.newArrayList();
   private ChatStyle b;

   @Override
   public IChatComponent a(IChatComponent var1) {
      var1.b().a(this.b());
      this.a.add(var1);
      return this;
   }

   @Override
   public List<IChatComponent> a() {
      return this.a;
   }

   @Override
   public IChatComponent a(String var1) {
      return this.a(new ChatComponentText(var1));
   }

   @Override
   public IChatComponent a(ChatStyle var1) {
      this.b = var1;

      for (IChatComponent var3 : this.a) {
         var3.b().a(this.b());
      }

      return this;
   }

   @Override
   public ChatStyle b() {
      if (this.b == null) {
         this.b = new ChatStyle();

         for (IChatComponent var2 : this.a) {
            var2.b().a(this.b);
         }
      }

      return this.b;
   }

   @Override
   public Iterator<IChatComponent> iterator() {
      return Iterators.concat(Iterators.forArray(new ChatComponentStyle[]{this}), a(this.a));
   }

   @Override
   public final String c() {
      StringBuilder var1 = new StringBuilder();

      for (IChatComponent var3 : this) {
         var1.append(var3.e());
      }

      return var1.toString();
   }

   @Override
   public final String d() {
      StringBuilder var1 = new StringBuilder();

      for (IChatComponent var3 : this) {
         var1.append(var3.b().k());
         var1.append(var3.e());
         var1.append(EnumChatFormatting.v);
      }

      return var1.toString();
   }

   public static Iterator<IChatComponent> a(Iterable<IChatComponent> var0) {
      Iterator var1 = Iterators.concat(Iterators.transform(var0.iterator(), new Function<IChatComponent, Iterator<IChatComponent>>() {
         public Iterator<IChatComponent> a(IChatComponent var1) {
            return var1.iterator();
         }
      }));
      return Iterators.transform(var1, new Function<IChatComponent, IChatComponent>() {
         public IChatComponent a(IChatComponent var1) {
            IChatComponent var2 = var1.f();
            var2.a(var2.b().n());
            return var2;
         }
      });
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ChatComponentStyle)) {
         return false;
      } else {
         ChatComponentStyle var2 = (ChatComponentStyle)var1;
         return this.a.equals(var2.a) && this.b().equals(var2.b());
      }
   }

   @Override
   public int hashCode() {
      return 31 * this.b.hashCode() + this.a.hashCode();
   }

   @Override
   public String toString() {
      return "BaseComponent{style=" + this.b + ", siblings=" + this.a + '}';
   }
}
