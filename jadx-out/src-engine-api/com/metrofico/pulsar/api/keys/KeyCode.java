package com.metrofico.pulsar.api.keys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class KeyCode {
   private static final List<KeyCode.Key> ALL = new ArrayList<>();
   private static final Map<String, KeyCode.Key> BY_NAME = new HashMap<>();
   private static final String[] BY_LWJGL2 = new String[256];
   private static final String[] BY_GLFW = new String[349];
   private static final String[] MOUSE = new String[]{null, null, "MOUSE3", "MOUSE4", "MOUSE5"};

   private KeyCode() {
   }

   private static void add(String name, String label, int lwjgl2, int glfw) {
      KeyCode.Key k = new KeyCode.Key(name, label, lwjgl2, glfw);
      ALL.add(k);
      BY_NAME.put(name, k);
      if (lwjgl2 >= 0) {
         BY_LWJGL2[lwjgl2] = name;
      }

      if (glfw >= 0) {
         BY_GLFW[glfw] = name;
      }
   }

   public static String fromLwjgl2(int code) {
      return code >= 0 && code < BY_LWJGL2.length ? BY_LWJGL2[code] : null;
   }

   public static String fromGlfw(int key) {
      return key >= 0 && key < BY_GLFW.length ? BY_GLFW[key] : null;
   }

   public static String fromMouse(int button) {
      return button >= 0 && button < MOUSE.length ? MOUSE[button] : null;
   }

   public static boolean mouse(String name) {
      return name != null && name.startsWith("MOUSE");
   }

   public static KeyCode.Key byName(String name) {
      return name == null ? null : BY_NAME.get(name);
   }

   public static String label(String name) {
      KeyCode.Key k = byName(name);
      return k == null ? "Sin tecla" : k.label();
   }

   public static List<KeyCode.Key> all() {
      return Collections.unmodifiableList(ALL);
   }

   static {
      String letters = "QWERTYUIOPASDFGHJKLZXCVBNM";
      int[] letterCodes = new int[]{16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 30, 31, 32, 33, 34, 35, 36, 37, 38, 44, 45, 46, 47, 48, 49, 50};

      for (int i = 0; i < letters.length(); i++) {
         String l = String.valueOf(letters.charAt(i));
         add(l, l, letterCodes[i], letters.charAt(i));
      }

      add("1", "1", 2, 49);
      add("2", "2", 3, 50);
      add("3", "3", 4, 51);
      add("4", "4", 5, 52);
      add("5", "5", 6, 53);
      add("6", "6", 7, 54);
      add("7", "7", 8, 55);
      add("8", "8", 9, 56);
      add("9", "9", 10, 57);
      add("0", "0", 11, 48);
      int[] fLwjgl = new int[]{59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 87, 88, 100, 101, 102, 103, 104, 105, 113};

      for (int i = 0; i < fLwjgl.length; i++) {
         add("F" + (i + 1), "F" + (i + 1), fLwjgl[i], 290 + i);
      }

      add("LALT", "Alt izq.", 56, 342);
      add("RALT", "Alt Gr", 184, 346);
      add("LSHIFT", "Shift izq.", 42, 340);
      add("RSHIFT", "Shift der.", 54, 344);
      add("LCTRL", "Ctrl izq.", 29, 341);
      add("RCTRL", "Ctrl der.", 157, 345);
      add("LSUPER", "Win izq.", 219, 343);
      add("RSUPER", "Win der.", 220, 347);
      add("MENU", "Menú", 221, 348);
      add("SPACE", "Espacio", 57, 32);
      add("ENTER", "Enter", 28, 257);
      add("TAB", "Tab", 15, 258);
      add("BACKSPACE", "Retroceso", 14, 259);
      add("CAPSLOCK", "Bloq Mayús", 58, 280);
      add("INSERT", "Insert", 210, 260);
      add("DELETE", "Supr", 211, 261);
      add("HOME", "Inicio", 199, 268);
      add("END", "Fin", 207, 269);
      add("PAGEUP", "Re Pág", 201, 266);
      add("PAGEDOWN", "Av Pág", 209, 267);
      add("UP", "Flecha arriba", 200, 265);
      add("DOWN", "Flecha abajo", 208, 264);
      add("LEFT", "Flecha izq.", 203, 263);
      add("RIGHT", "Flecha der.", 205, 262);
      add("SCROLLLOCK", "Bloq Despl", 70, 281);
      add("NUMLOCK", "Bloq Num", 69, 282);
      add("PAUSE", "Pausa", 197, 284);
      int[] numLwjgl = new int[]{82, 79, 80, 81, 75, 76, 77, 71, 72, 73};

      for (int i = 0; i < 10; i++) {
         add("NUM" + i, "Num " + i, numLwjgl[i], 320 + i);
      }

      add("NUMDECIMAL", "Num .", 83, 330);
      add("NUMDIVIDE", "Num /", 181, 331);
      add("NUMMULTIPLY", "Num *", 55, 332);
      add("NUMSUBTRACT", "Num -", 74, 333);
      add("NUMADD", "Num +", 78, 334);
      add("NUMENTER", "Num Enter", 156, 335);
      add("NUMEQUALS", "Num =", 141, 336);
      add("MINUS", "-", 12, 45);
      add("EQUALS", "=", 13, 61);
      add("LBRACKET", "[", 26, 91);
      add("RBRACKET", "]", 27, 93);
      add("SEMICOLON", ";", 39, 59);
      add("APOSTROPHE", "'", 40, 39);
      add("GRAVE", "`", 41, 96);
      add("BACKSLASH", "\\", 43, 92);
      add("COMMA", ",", 51, 44);
      add("PERIOD", ".", 52, 46);
      add("SLASH", "/", 53, 47);
      add("MOUSE3", "Rueda (clic)", -1, -1);
      add("MOUSE4", "Ratón 4", -1, -1);
      add("MOUSE5", "Ratón 5", -1, -1);
   }

   public record Key(String name, String label, int lwjgl2, int glfw) {
   }
}
