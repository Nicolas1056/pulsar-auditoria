package com.metrofico.pulsar.api.model;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record GameVersion(String id, List<Integer> numbers, GameVersion.Kind kind, int stage, String raw) implements Comparable<GameVersion> {
   private static final Pattern RELEASE = Pattern.compile("^(\\d+(?:\\.\\d+)*)$");
   private static final Pattern PRE = Pattern.compile("^(\\d+(?:\\.\\d+)*)-(pre|rc)(\\d+)$", 2);
   private static final Pattern SNAPSHOT = Pattern.compile("^(\\d+(?:\\.\\d+)*)-snapshot-(\\d+)$", 2);
   private static final Pattern WEEKLY = Pattern.compile("^(\\d{2})w(\\d{2})([a-z])$");

   public GameVersion(String id, List<Integer> numbers, GameVersion.Kind kind, int stage, String raw) {
      Objects.requireNonNull(id, "id");
      numbers = List.copyOf(numbers);
      Objects.requireNonNull(kind, "kind");
      Objects.requireNonNull(raw, "raw");
      this.id = id;
      this.numbers = numbers;
      this.kind = kind;
      this.stage = stage;
      this.raw = raw;
   }

   public static GameVersion parse(String id) {
      String trimmed = id.trim();
      Matcher m;
      if ((m = RELEASE.matcher(trimmed)).matches()) {
         return new GameVersion(trimmed, numbers(m.group(1)), GameVersion.Kind.RELEASE, 0, trimmed);
      } else if ((m = PRE.matcher(trimmed)).matches()) {
         GameVersion.Kind kind = m.group(2).equalsIgnoreCase("rc") ? GameVersion.Kind.RELEASE_CANDIDATE : GameVersion.Kind.PRE_RELEASE;
         return new GameVersion(trimmed, numbers(m.group(1)), kind, Integer.parseInt(m.group(3)), trimmed);
      } else if ((m = SNAPSHOT.matcher(trimmed)).matches()) {
         return new GameVersion(trimmed, numbers(m.group(1)), GameVersion.Kind.SNAPSHOT, Integer.parseInt(m.group(2)), trimmed);
      } else if ((m = WEEKLY.matcher(trimmed)).matches()) {
         int year = Integer.parseInt(m.group(1));
         int week = Integer.parseInt(m.group(2));
         int letter = m.group(3).charAt(0) - 'a';
         return new GameVersion(trimmed, List.of(0, year, week), GameVersion.Kind.SNAPSHOT, letter, trimmed);
      } else {
         return new GameVersion(trimmed, List.of(), GameVersion.Kind.SNAPSHOT, 0, trimmed);
      }
   }

   private static List<Integer> numbers(String dotted) {
      String[] parts = dotted.split("\\.");
      Integer[] out = new Integer[parts.length];

      for (int i = 0; i < parts.length; i++) {
         out[i] = Integer.parseInt(parts[i]);
      }

      return List.of(out);
   }

   public boolean isRelease() {
      return this.kind == GameVersion.Kind.RELEASE;
   }

   public String base() {
      StringBuilder sb = new StringBuilder();

      for (int i = 0; i < this.numbers.size(); i++) {
         if (i > 0) {
            sb.append('.');
         }

         sb.append(this.numbers.get(i));
      }

      return sb.toString();
   }

   public int compareTo(GameVersion other) {
      int max = Math.max(this.numbers.size(), other.numbers.size());

      for (int i = 0; i < max; i++) {
         int a = i < this.numbers.size() ? this.numbers.get(i) : 0;
         int b = i < other.numbers.size() ? other.numbers.get(i) : 0;
         if (a != b) {
            return Integer.compare(a, b);
         }
      }

      if (this.kind != other.kind) {
         return this.kind.compareTo(other.kind);
      } else {
         return this.stage != other.stage ? Integer.compare(this.stage, other.stage) : Comparator.<String>naturalOrder().compare(this.raw, other.raw);
      }
   }

   @Override
   public String toString() {
      return this.id;
   }

   public static enum Kind {
      SNAPSHOT,
      PRE_RELEASE,
      RELEASE_CANDIDATE,
      RELEASE;
   }
}
