import org.objectweb.asm.*;
import java.nio.file.*; import java.util.zip.*;

public final class StripDebug {
    public static void main(String[] a) throws Exception {
        Path in = Path.of(a[0]), out = Path.of(a[1]);
        int n = 0;
        try (ZipInputStream zin = new ZipInputStream(Files.newInputStream(in));
             ZipOutputStream zout = new ZipOutputStream(Files.newOutputStream(out))) {
            ZipEntry e;
            while ((e = zin.getNextEntry()) != null) {
                byte[] d = zin.readAllBytes();
                if (e.getName().endsWith(".class")) {
                    ClassReader cr = new ClassReader(d);
                    ClassWriter cw = new ClassWriter(0);
                    // SKIP_DEBUG: sin LocalVariableTable, SourceFile ni LineNumberTable
                    cr.accept(cw, ClassReader.SKIP_DEBUG);
                    d = cw.toByteArray();
                    n++;
                }
                zout.putNextEntry(new ZipEntry(e.getName()));
                zout.write(d);
                zout.closeEntry();
            }
        }
        System.err.println("stripped classes: " + n);
    }
}
