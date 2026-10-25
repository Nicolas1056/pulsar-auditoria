import org.objectweb.asm.*;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.zip.*;

/**
 * RemapMinecraft — remapea client-1.8.9.jar (nombres Searge ofuscados) a
 * nombres MCP usando el classmapping.json generado por srg2classmapping.py.
 *
 * Uso: java -cp "<asm jars>" RemapMinecraft.java in.jar out.jar mapping.json
 */
public final class RemapMinecraft {

    /** Remapper jerárquico: primero busca el descriptor exacto, luego solo el nombre. */
    static final class McpRemapper extends Remapper {
        private final Map<String, String> map;
        private long hits, misses;
        private final Map<String, Integer> missedNames = new LinkedHashMap<>();

        McpRemapper(Map<String, String> map) { this.map = map; }

        @Override
        public String map(String internalName) {
            String v = map.get(internalName);
            if (v != null) { hits++; return v; }
            // clases internas: obf 'aad$1' ya está en el mapping como tal
            return internalName;
        }

        @Override
        public String mapMethodName(String owner, String name, String descriptor) {
            String exact = map.get(owner + "/" + name + descriptor);
            if (exact != null) { hits++; return exact; }
            String v = map.get(owner + "/" + name);
            if (v != null) { hits++; return v; }
            misses++;
            missedNames.merge(name, 1, Integer::sum);
            return name;
        }

        @Override
        public String mapFieldName(String owner, String name, String descriptor) {
            String v = map.get(owner + "/" + name);
            if (v != null) { hits++; return v; }
            misses++;
            missedNames.merge(name, 1, Integer::sum);
            return name;
        }
    }

    @SuppressWarnings("unchecked")
    private static Map<String, String> loadMapping(Path p) throws IOException {
        String json = Files.readString(p, StandardCharsets.UTF_8).trim();
        Map<String, String> out = new LinkedHashMap<>();
        // parser JSON mínimo (objeto plano string->string)
        json = json.substring(1, json.length() - 1);
        int i = 0;
        while (i < json.length()) {
            int k0 = json.indexOf('"', i);
            if (k0 < 0) break;
            int k1 = k0 + 1;
            StringBuilder kb = new StringBuilder();
            while (json.charAt(k1) != '"') {
                char c = json.charAt(k1);
                if (c == '\\') { kb.append(json.charAt(++k1)); } else { kb.append(c); }
                k1++;
            }
            int v0 = json.indexOf('"', json.indexOf(':', k1)) + 1;
            int v1 = v0;
            StringBuilder vb = new StringBuilder();
            while (json.charAt(v1) != '"') {
                char c = json.charAt(v1);
                if (c == '\\') { vb.append(json.charAt(++v1)); } else { vb.append(c); }
                v1++;
            }
            out.put(kb.toString(), vb.toString());
            i = v1 + 1;
        }
        return out;
    }

    public static void main(String[] args) throws Exception {
        Path in = Path.of(args[0]), out = Path.of(args[1]), mapFile = Path.of(args[2]);
        Map<String, String> mapping = loadMapping(mapFile);
        McpRemapper remapper = new McpRemapper(mapping);
        System.err.println("mapping entries: " + mapping.size());

        int classes = 0, resources = 0, failures = 0;
        try (ZipInputStream zin = new ZipInputStream(Files.newInputStream(in));
             ZipOutputStream zout = new ZipOutputStream(Files.newOutputStream(out))) {
            ZipEntry e;
            while ((e = zin.getNextEntry()) != null) {
                byte[] data = zin.readAllBytes();
                String name = e.getName();
                if (name.endsWith(".class")) {
                    try {
                        ClassReader cr = new ClassReader(data);
                        ClassWriter cw = new ClassWriter(0);
                        cr.accept(new ClassRemapper(cw, remapper), 0);
                        byte[] remapped = cw.toByteArray();
                        String newName = remapper.map(cr.getClassName()) + ".class";
                        zout.putNextEntry(new ZipEntry(newName));
                        zout.write(remapped);
                        zout.closeEntry();
                        classes++;
                    } catch (Throwable t) {
                        failures++;
                        System.err.println("FAIL " + name + ": " + t);
                        zout.putNextEntry(new ZipEntry(name));
                        zout.write(data);
                        zout.closeEntry();
                    }
                } else {
                    zout.putNextEntry(new ZipEntry(name));
                    zout.write(data);
                    zout.closeEntry();
                    resources++;
                }
            }
        }
        System.err.println("classes remapped: " + classes + " | resources: " + resources
                + " | failures: " + failures + " | member hits: " + remapper.hits
                + " | member misses: " + remapper.misses);
        System.err.println("top unmapped member names: " + remapper.missedNames.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue()).limit(12).toList());
    }
}
