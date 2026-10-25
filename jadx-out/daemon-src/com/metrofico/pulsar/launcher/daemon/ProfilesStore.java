package com.metrofico.pulsar.launcher.daemon;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

public final class ProfilesStore {
   private final JsonStore<ProfilesStore.File> store;
   private ProfilesStore.File state;

   public ProfilesStore(Path dataDir) {
      this.store = new JsonStore<>(dataDir.resolve("profiles.json"), ProfilesStore.File.class, () -> new ProfilesStore.File(List.of(), null));
      this.state = this.store.load();
   }

   public synchronized List<Profile> all() {
      return List.copyOf(this.state.profiles());
   }

   public synchronized Optional<Profile> selected() {
      return this.state
         .profiles()
         .stream()
         .filter(p -> p.id().equals(this.state.selectedId()))
         .findFirst()
         .or(() -> this.state.profiles().stream().findFirst());
   }

   public synchronized Profile save(Profile profile) {
      List<Profile> next = new ArrayList<>(this.state.profiles().stream().filter(p -> !p.id().equals(profile.id())).toList());
      int index = this.indexOf(profile.id());
      if (index >= 0) {
         next.add(index, profile);
      } else {
         next.add(profile);
      }

      this.state = new ProfilesStore.File(next, this.state.selectedId() != null ? this.state.selectedId() : profile.id());
      this.store.save(this.state);
      return profile;
   }

   public synchronized void retarget(UnaryOperator<String> target) {
      List<Profile> next = this.state.profiles().stream().map(p -> {
         String v = target.apply(p.version());
         return (Profile)(v.equals(p.version()) ? p : new Profile(p.id(), p.name(), v, p.memoryMb(), p.javaPath(), p.lastPlayedEpochMs(), p.gameDir()));
      }).toList();
      if (!next.equals(this.state.profiles())) {
         this.state = new ProfilesStore.File(next, this.state.selectedId());
         this.store.save(this.state);
      }
   }

   public synchronized void delete(String id) {
      List<Profile> remaining = this.state.profiles().stream().filter(p -> !p.id().equals(id)).toList();
      String selected = id.equals(this.state.selectedId()) ? remaining.stream().map(Profile::id).findFirst().orElse(null) : this.state.selectedId();
      this.state = new ProfilesStore.File(remaining, selected);
      this.store.save(this.state);
   }

   public synchronized void select(String id) {
      if (this.indexOf(id) < 0) {
         throw new DaemonException("bad_request", "Perfil desconocido", "No existe un perfil con id " + id);
      } else {
         this.state = new ProfilesStore.File(this.state.profiles(), id);
         this.store.save(this.state);
      }
   }

   private int indexOf(String id) {
      for (int i = 0; i < this.state.profiles().size(); i++) {
         if (this.state.profiles().get(i).id().equals(id)) {
            return i;
         }
      }

      return -1;
   }

   record File(List<Profile> profiles, String selectedId) {
   }
}
