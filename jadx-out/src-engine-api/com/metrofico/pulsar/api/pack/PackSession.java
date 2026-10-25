package com.metrofico.pulsar.api.pack;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

public final class PackSession {
   private PackSession.State state = PackSession.State.NONE;
   private PackManifest manifest;
   private ServerRecord server;
   private Set<Capability> granted = EnumSet.noneOf(Capability.class);
   private String reason;

   public PackSession.State state() {
      return this.state;
   }

   public PackManifest manifest() {
      return this.manifest;
   }

   public ServerRecord server() {
      return this.server;
   }

   public Set<Capability> granted() {
      return EnumSet.copyOf((Collection<Capability>)(this.granted.isEmpty() ? EnumSet.noneOf(Capability.class) : this.granted));
   }

   public String reason() {
      return this.reason;
   }

   public void offered(ServerRecord server, PackManifest manifest) {
      this.server = server;
      this.manifest = manifest;
      this.state = PackSession.State.OFFERED;
   }

   public void rejected(String reason) {
      this.reason = reason;
      this.state = PackSession.State.REJECTED;
   }

   public void consenting() {
      this.require(PackSession.State.OFFERED);
      this.state = PackSession.State.CONSENTING;
   }

   public void decide(PackSession.Consent consent) {
      this.require(PackSession.State.OFFERED, PackSession.State.CONSENTING);
      switch (consent) {
         case FULL:
            this.granted = EnumSet.copyOf(this.manifest.capabilities());
            break;
         case INFORMATIVE:
            this.granted = EnumSet.copyOf(this.manifest.capabilities());
            this.granted.retainAll(Capability.INFORMATIVE);
            break;
         case NONE:
            this.granted = EnumSet.noneOf(Capability.class);
      }

      this.state = consent == PackSession.Consent.NONE ? PackSession.State.ENDED : PackSession.State.DOWNLOADING;
   }

   public void downloaded() {
      this.require(PackSession.State.DOWNLOADING);
      this.state = PackSession.State.ACTIVE;
   }

   public void ended() {
      this.state = PackSession.State.ENDED;
      this.granted = EnumSet.noneOf(Capability.class);
   }

   public boolean allows(Capability c) {
      return this.state == PackSession.State.ACTIVE && this.granted.contains(c);
   }

   private void require(PackSession.State... allowed) {
      for (PackSession.State s : allowed) {
         if (s == this.state) {
            return;
         }
      }

      throw new IllegalStateException("pack session is " + this.state);
   }

   public static enum Consent {
      FULL,
      INFORMATIVE,
      NONE;
   }

   public static enum State {
      NONE,
      OFFERED,
      CONSENTING,
      DOWNLOADING,
      ACTIVE,
      ENDED,
      REJECTED;
   }
}
