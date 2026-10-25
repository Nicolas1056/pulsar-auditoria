package com.metrofico.pulsar.api.render;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TerrainShaderPatch {
   private static final Pattern POSITION = Pattern.compile("(?<![A-Za-z0-9_])Position(?![A-Za-z0-9_])");
   private static final Pattern UV2 = Pattern.compile("(?<![A-Za-z0-9_])UV2(?![A-Za-z0-9_])");
   private static final Pattern VERSION = Pattern.compile("#version\\s+(\\d+)");

   private TerrainShaderPatch() {
   }

   public static String patch(String vanilla, boolean vertex, boolean compact) {
      return patch(vanilla, vertex, compact, 0);
   }

   public static String patch(String vanilla, boolean vertex, boolean compact, int minVersion) {
      if (minVersion > 0) {
         Matcher m = VERSION.matcher(vanilla);
         if (m.find() && Integer.parseInt(m.group(1)) < minVersion) {
            vanilla = vanilla.substring(0, m.start()) + "#version " + minVersion + vanilla.substring(m.end());
         }
      }

      int v = vanilla.indexOf("#version");
      int nl = v < 0 ? -1 : vanilla.indexOf(10, v);
      String head = nl < 0 ? "" : vanilla.substring(0, nl + 1);
      String rest = nl < 0 ? vanilla : vanilla.substring(nl + 1);
      int main = rest.indexOf("void main()");
      String decl = rest.substring(0, main < 0 ? rest.length() : main);
      String body = main < 0 ? "" : rest.substring(main);
      if (!vertex) {
         body = body.replace("ChunkVisibility", "pulsarVisibility");
         return head + "flat in float pulsarVisibility;\n" + decl + body;
      } else {
         body = body.replace("ChunkPosition", "pulsarSection.xyz");
         String prologue = "    ivec4 pulsarSection = texelFetch(PulsarSections, gl_DrawIDARB);\n    pulsarVisibility = intBitsToFloat(pulsarSection.w);";
         if (compact) {
            decl = decl.replace("in vec3 Position;", "in uvec4 Position;").replace("in ivec2 UV2;", "");
            body = POSITION.matcher(body).replaceAll("pulsarPos");
            body = UV2.matcher(body).replaceAll("pulsarUV2");
            prologue = prologue
               + "\n    vec3 pulsarPos = vec3(Position.xyz) * (1.0 / 2048.0) + (-8.0);\n    int pulsarLight = int(Position.w);\n    ivec2 pulsarUV2 = ivec2(pulsarLight & 255, pulsarLight >> 8);";
         }

         body = body.replaceFirst("void main\\(\\)\\s*\\{", "void main() {\n" + prologue.replace("\\", "\\\\").replace("$", "\\$"));
         return head
            + "#extension GL_ARB_shader_draw_parameters : require\nuniform isamplerBuffer PulsarSections;\nflat out float pulsarVisibility;\n"
            + decl
            + body;
      }
   }
}
