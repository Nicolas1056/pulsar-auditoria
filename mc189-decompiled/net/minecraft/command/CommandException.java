package net.minecraft.command;

public class CommandException extends Exception {
   private final Object[] a;

   public CommandException(String var1, Object... var2) {
      super(var1);
      this.a = var2;
   }

   public Object[] a() {
      return this.a;
   }
}
