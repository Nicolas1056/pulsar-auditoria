package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.hub.ClientOptions;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public final class TerrainShader189 {
   public static final int A_POS = 0;
   public static final int A_LIGHT = 1;
   public static final int A_COLOR = 2;
   public static final int A_UV = 3;
   public static final int A_SECTION = 4;
   private static final String VERTEX = "#version 120\nattribute vec3 a_pos;\nattribute vec2 a_light;\nattribute vec4 a_color;\nattribute vec2 a_uv;\nattribute vec3 a_section;\nvarying vec4 v_color;\nvarying vec2 v_uv;\nvarying vec2 v_light;\nvoid main() {\n    vec3 p = a_pos / 2048.0 - 8.0 + a_section;   // a_pos llega como entero 0..65535: división por potencia de dos, exacta\n    vec4 eye = gl_ModelViewMatrix * vec4(p, 1.0);\n    gl_Position = gl_ProjectionMatrix * eye;\n    v_color = a_color;\n    v_uv = a_uv;\n    v_light = (a_light * 255.0 + 8.0) / 256.0;\n    gl_FogFragCoord = length(eye.xyz);\n}\n";
   private static final String FRAGMENT = "#version 120\nuniform sampler2D u_tex;\nuniform sampler2D u_light;\nuniform int u_fog;\nvarying vec4 v_color;\nvarying vec2 v_uv;\nvarying vec2 v_light;\nvoid main() {\n    vec4 c = v_color * texture2D(u_tex, v_uv) * texture2D(u_light, v_light);\n    if (u_fog == 1) {\n        float f = clamp((gl_Fog.end - gl_FogFragCoord) * gl_Fog.scale, 0.0, 1.0);\n        c.rgb = mix(gl_Fog.color.rgb, c.rgb, f);\n    } else if (u_fog == 2) {\n        float f = clamp(exp(-gl_Fog.density * gl_FogFragCoord), 0.0, 1.0);\n        c.rgb = mix(gl_Fog.color.rgb, c.rgb, f);\n    }\n    gl_FragColor = c;\n}\n";
   private static int program;
   private static int fogUniform;
   private static Boolean ok;

   private TerrainShader189() {
   }

   public static boolean ready() {
      if (ok != null) {
         return ok;
      } else {
         try {
            int vs = compile(
               35633,
               "#version 120\nattribute vec3 a_pos;\nattribute vec2 a_light;\nattribute vec4 a_color;\nattribute vec2 a_uv;\nattribute vec3 a_section;\nvarying vec4 v_color;\nvarying vec2 v_uv;\nvarying vec2 v_light;\nvoid main() {\n    vec3 p = a_pos / 2048.0 - 8.0 + a_section;   // a_pos llega como entero 0..65535: división por potencia de dos, exacta\n    vec4 eye = gl_ModelViewMatrix * vec4(p, 1.0);\n    gl_Position = gl_ProjectionMatrix * eye;\n    v_color = a_color;\n    v_uv = a_uv;\n    v_light = (a_light * 255.0 + 8.0) / 256.0;\n    gl_FogFragCoord = length(eye.xyz);\n}\n"
            );
            int fs = compile(
               35632,
               "#version 120\nuniform sampler2D u_tex;\nuniform sampler2D u_light;\nuniform int u_fog;\nvarying vec4 v_color;\nvarying vec2 v_uv;\nvarying vec2 v_light;\nvoid main() {\n    vec4 c = v_color * texture2D(u_tex, v_uv) * texture2D(u_light, v_light);\n    if (u_fog == 1) {\n        float f = clamp((gl_Fog.end - gl_FogFragCoord) * gl_Fog.scale, 0.0, 1.0);\n        c.rgb = mix(gl_Fog.color.rgb, c.rgb, f);\n    } else if (u_fog == 2) {\n        float f = clamp(exp(-gl_Fog.density * gl_FogFragCoord), 0.0, 1.0);\n        c.rgb = mix(gl_Fog.color.rgb, c.rgb, f);\n    }\n    gl_FragColor = c;\n}\n"
            );
            program = GL20.glCreateProgram();
            GL20.glAttachShader(program, vs);
            GL20.glAttachShader(program, fs);
            GL20.glBindAttribLocation(program, 0, "a_pos");
            GL20.glBindAttribLocation(program, 1, "a_light");
            GL20.glBindAttribLocation(program, 2, "a_color");
            GL20.glBindAttribLocation(program, 3, "a_uv");
            GL20.glBindAttribLocation(program, 4, "a_section");
            GL20.glLinkProgram(program);
            if (GL20.glGetProgrami(program, 35714) == 0) {
               throw new IllegalStateException("link: " + GL20.glGetProgramInfoLog(program, 4096));
            }

            GL20.glDeleteShader(vs);
            GL20.glDeleteShader(fs);
            GL20.glUseProgram(program);
            GL20.glUniform1i(GL20.glGetUniformLocation(program, "u_tex"), 0);
            GL20.glUniform1i(GL20.glGetUniformLocation(program, "u_light"), 1);
            fogUniform = GL20.glGetUniformLocation(program, "u_fog");
            GL20.glUseProgram(0);
            ok = true;
            ClientOptions.LOG.accept("perf: terrain shader ready (compact vertices)");
         } catch (Throwable var2) {
            ok = false;
            ClientOptions.LOG.accept("perf: terrain shader failed, compact vertices off: " + var2.getMessage());
         }

         return ok;
      }
   }

   private static int compile(int type, String src) {
      int id = GL20.glCreateShader(type);
      GL20.glShaderSource(id, src);
      GL20.glCompileShader(id);
      if (GL20.glGetShaderi(id, 35713) == 0) {
         throw new IllegalStateException("compile: " + GL20.glGetShaderInfoLog(id, 4096));
      } else {
         return id;
      }
   }

   public static void use() {
      GL20.glUseProgram(program);
      int mode = 0;
      if (GL11.glIsEnabled(2912)) {
         int m = GL11.glGetInteger(2917);
         mode = m == 9729 ? 1 : 2;
      }

      GL20.glUniform1i(fogUniform, mode);
   }

   public static void stop() {
      GL20.glUseProgram(0);
   }
}
