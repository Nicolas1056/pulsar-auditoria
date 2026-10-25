#!/usr/bin/env python3
"""
verify_targets.py — comprueba los `@At(target = "Lclase;miembro")` de los mixins
de Pulsar contra el bytecode de 1.8.9, resolviendo la herencia (la JVM resuelve
invokevirtual/invokestatic subiendo por la jerarquía, así que `Lpr;f_()Leu;`
es válido aunque `f_` se declare en `pk`).
"""
import glob, json, os, re, struct, zipfile, importlib.util

HERE = os.path.dirname(os.path.abspath(__file__))
spec = importlib.util.spec_from_file_location('vh', os.path.join(HERE, 'verify_hooks.py'))
vh = importlib.util.module_from_spec(spec); spec.loader.exec_module(vh)


def parse_full(data):
    """-> (super, metodos{name->{desc}}, campos{name: desc})"""
    p = 10
    cp = {}
    n = struct.unpack_from('>H', data, 8)[0]
    i = 1
    while i < n:
        tag = data[p]; p += 1
        if tag == 1:
            ln = struct.unpack_from('>H', data, p)[0]; p += 2
            cp[i] = data[p:p + ln].decode('utf-8', 'replace'); p += ln
        elif tag == 7:
            cp[i] = struct.unpack_from('>H', data, p)[0]   # Class -> indice de nombre
            p += 2
        elif tag in (8, 16): p += 2
        elif tag == 15: p += 3
        elif tag in (3, 4, 9, 10, 11, 12, 17, 18): p += 4
        elif tag in (5, 6): p += 8; i += 1
        i += 1
    _acc, this_i, super_i = struct.unpack_from('>HHH', data, p); p += 6
    def uname(idx):
        v = cp.get(idx)
        return cp.get(v) if isinstance(v, int) else v
    supername = uname(super_i)
    ic = struct.unpack_from('>H', data, p)[0]; p += 2 + 2 * ic
    fc = struct.unpack_from('>H', data, p)[0]; p += 2
    fields = {}
    for _ in range(fc):
        _a, ni, di = struct.unpack_from('>HHH', data, p); p += 6
        fields[cp[ni]] = cp[di]
        ac = struct.unpack_from('>H', data, p)[0]; p += 2
        for _ in range(ac):
            p += 2; ln = struct.unpack_from('>I', data, p)[0]; p += 4 + ln
    mc = struct.unpack_from('>H', data, p)[0]; p += 2
    methods = {}
    for _ in range(mc):
        _a, ni, di = struct.unpack_from('>HHH', data, p); p += 6
        methods.setdefault(cp[ni], set()).add(cp[di])
        ac = struct.unpack_from('>H', data, p)[0]; p += 2
        for _ in range(ac):
            p += 2; ln = struct.unpack_from('>I', data, p)[0]; p += 4 + ln
    return uname(this_i), supername, methods, fields


def main():
    jar = os.path.join(HERE, 'client-1.8.9.jar')
    classes = {}
    with zipfile.ZipFile(jar) as z:
        for nm in z.namelist():
            if nm.endswith('.class'):
                try:
                    classes[nm[:-6]] = parse_full(z.read(nm))
                except Exception:
                    pass

    def resolve_method(cls, name, desc=None):
        seen = set()
        while cls and cls not in seen:
            seen.add(cls)
            rec = classes.get(cls)
            if not rec:
                return None
            if name in rec[2] and (desc is None or desc in rec[2][name]):
                return cls
            cls = rec[1]
        return None

    def resolve_field(cls, name, desc=None):
        seen = set()
        while cls and cls not in seen:
            seen.add(cls)
            rec = classes.get(cls)
            if not rec:
                return None
            if name in rec[3] and (desc is None or desc == rec[3][name]):
                return cls
            cls = rec[1]
        return None

    total = inside = outside = ok = inherited = bad = 0
    problems = []
    for path in sorted(glob.glob(os.path.join(HERE, '..', 'jadx-out', 'src-v189',
                                              'com', 'metrofico', 'pulsar', 'engine',
                                              'v189', 'mixin', '*.java'))):
        src = open(path, encoding='utf-8', errors='replace').read()
        for kind, body in vh.annotations(src, 'Inject|Redirect|ModifyArg|ModifyVariable'):
            for at in re.finditer(r'target\s*=\s*"L([^;]+);([^"]*)"', body):
                cls, member = at.group(1), at.group(2)
                total += 1
                if cls not in classes:
                    outside += 1        # LWJGL / JDK / Guava: fuera del jar de MC
                    continue
                inside += 1
                if ':' in member:
                    f, desc = member.split(':', 1)
                    owner = resolve_field(cls, f, desc)
                else:
                    mn = member.split('(')[0]
                    desc = member[len(mn):] or None
                    owner = resolve_method(cls, mn, desc)
                if owner is None:
                    bad += 1
                    problems.append((os.path.basename(path), cls, member))
                else:
                    ok += 1
                    if owner != cls:
                        inherited += 1

    print(f'@At targets totales          : {total}')
    print(f'  fuera del jar (LWJGL/JDK)  : {outside}')
    print(f'  dentro del jar de MC       : {inside}')
    print(f'    resueltos                : {ok}  (por herencia: {inherited})')
    print(f'    NO resueltos             : {bad}')
    for p in problems:
        print('   ', p)


if __name__ == '__main__':
    main()
