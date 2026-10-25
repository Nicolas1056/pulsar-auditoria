package net.minecraft.world.storage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;

public class DerivedWorldInfo extends WorldInfo {
   private final WorldInfo b;

   public DerivedWorldInfo(WorldInfo var1) {
      this.b = var1;
   }

   @Override
   public NBTTagCompound a() {
      return this.b.a();
   }

   @Override
   public NBTTagCompound a(NBTTagCompound var1) {
      return this.b.a(var1);
   }

   @Override
   public long b() {
      return this.b.b();
   }

   @Override
   public int c() {
      return this.b.c();
   }

   @Override
   public int d() {
      return this.b.d();
   }

   @Override
   public int e() {
      return this.b.e();
   }

   @Override
   public long f() {
      return this.b.f();
   }

   @Override
   public long g() {
      return this.b.g();
   }

   @Override
   public long h() {
      return this.b.h();
   }

   @Override
   public NBTTagCompound i() {
      return this.b.i();
   }

   @Override
   public String k() {
      return this.b.k();
   }

   @Override
   public int l() {
      return this.b.l();
   }

   @Override
   public long m() {
      return this.b.m();
   }

   @Override
   public boolean n() {
      return this.b.n();
   }

   @Override
   public int o() {
      return this.b.o();
   }

   @Override
   public boolean p() {
      return this.b.p();
   }

   @Override
   public int q() {
      return this.b.q();
   }

   @Override
   public WorldSettings.GameType r() {
      return this.b.r();
   }

   @Override
   public void a(int var1) {
   }

   @Override
   public void b(int var1) {
   }

   @Override
   public void c(int var1) {
   }

   @Override
   public void b(long var1) {
   }

   @Override
   public void c(long var1) {
   }

   @Override
   public void a(BlockPos var1) {
   }

   @Override
   public void a(String var1) {
   }

   @Override
   public void e(int var1) {
   }

   @Override
   public void a(boolean var1) {
   }

   @Override
   public void f(int var1) {
   }

   @Override
   public void b(boolean var1) {
   }

   @Override
   public void g(int var1) {
   }

   @Override
   public boolean s() {
      return this.b.s();
   }

   @Override
   public boolean t() {
      return this.b.t();
   }

   @Override
   public WorldType u() {
      return this.b.u();
   }

   @Override
   public void a(WorldType var1) {
   }

   @Override
   public boolean v() {
      return this.b.v();
   }

   @Override
   public void c(boolean var1) {
   }

   @Override
   public boolean w() {
      return this.b.w();
   }

   @Override
   public void d(boolean var1) {
   }

   @Override
   public GameRules x() {
      return this.b.x();
   }

   @Override
   public EnumDifficulty y() {
      return this.b.y();
   }

   @Override
   public void a(EnumDifficulty var1) {
   }

   @Override
   public boolean z() {
      return this.b.z();
   }

   @Override
   public void e(boolean var1) {
   }
}
