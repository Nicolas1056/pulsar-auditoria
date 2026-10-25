package net.minecraft.world.storage;

import java.util.List;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.util.IProgressUpdate;

public interface ISaveFormat {
   String a();

   ISaveHandler a(String var1, boolean var2);

   List<SaveFormatComparator> b() throws AnvilConverterException;

   void d();

   WorldInfo c(String var1);

   boolean d(String var1);

   boolean e(String var1);

   void a(String var1, String var2);

   boolean a(String var1);

   boolean b(String var1);

   boolean a(String var1, IProgressUpdate var2);

   boolean f(String var1);
}
