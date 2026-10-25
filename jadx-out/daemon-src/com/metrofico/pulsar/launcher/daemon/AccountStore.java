package com.metrofico.pulsar.launcher.daemon;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class AccountStore {
   private final JsonStore<AccountStore.File> store;
   private List<Account> accounts;
   private String selectedUuid;

   public AccountStore(Path dataDir) {
      this.store = new JsonStore<>(dataDir.resolve("account.json"), AccountStore.File.class, () -> new AccountStore.File(null, null, null));
      AccountStore.File f = this.store.load();
      this.accounts = f.accounts() == null ? new ArrayList<>() : new ArrayList<>(f.accounts());
      this.selectedUuid = f.selectedUuid();
      if (this.accounts.isEmpty() && f.account() != null) {
         this.accounts.add(f.account());
         this.selectedUuid = f.account().uuid();
      }
   }

   public synchronized List<Account> all() {
      return List.copyOf(this.accounts);
   }

   public synchronized Optional<Account> current() {
      return this.accounts.stream().filter(a -> a.uuid().equals(this.selectedUuid)).findFirst().or(() -> this.accounts.stream().findFirst());
   }

   public synchronized void save(Account account) {
      this.accounts.removeIf(a -> a.uuid().equals(account.uuid()));
      this.accounts.add(0, account);
      this.selectedUuid = account.uuid();
      this.persist();
   }

   public synchronized boolean select(String uuid) {
      if (this.accounts.stream().noneMatch(a -> a.uuid().equals(uuid))) {
         return false;
      } else {
         this.selectedUuid = uuid;
         this.persist();
         return true;
      }
   }

   public synchronized Optional<Account> remove(String uuid) {
      Optional<Account> found = this.accounts.stream().filter(a -> a.uuid().equals(uuid)).findFirst();
      this.accounts.removeIf(a -> a.uuid().equals(uuid));
      if (uuid.equals(this.selectedUuid)) {
         this.selectedUuid = this.accounts.isEmpty() ? null : this.accounts.get(0).uuid();
      }

      this.persist();
      return found;
   }

   public synchronized void clear() {
      this.accounts = new ArrayList<>();
      this.selectedUuid = null;
      this.persist();
   }

   private void persist() {
      this.store.save(new AccountStore.File(this.current().orElse(null), List.copyOf(this.accounts), this.selectedUuid));
   }

   record File(Account account, List<Account> accounts, String selectedUuid) {
   }
}
