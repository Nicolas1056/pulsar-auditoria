#!/usr/bin/env python3
"""
verify_hooks.py — resuelve cada hook de los 100 mixins de Pulsar contra el
bytecode real de Minecraft 1.8.9 (client-1.8.9.jar). Sin ejecutar nada.

Para cada @Inject/@Redirect/ModifyArg/ModifyVariable comprueba:
  * que la clase @Mixin existe en el jar
  * que el método objetivo (nombre[+descriptor]) existe en esa clase
  * a qué método nombre-Searge corresponde (joined.srg)
  * que la clase del `target` de @At existe

Salida: tabla, resumen y lista de hooks NO resueltos.
"""
import json, os, re, struct, glob, zipfile, csv, collections

HERE = os.path.dirname(os.path.abspath(__file__))
JAR = os.path.join(HERE, 'client-1.8.9.jar')
MAPPING = os.path.join(HERE, 'classmapping.json')
SRG = os.path.join(HERE, 'mcp-srg', 'joined.srg')
MIXINS = os.path.join(HERE, '..', 'jadx-out', 'src-v189', 'com', 'metrofico',
                      'pulsar', 'engine', 'v189', 'mixin')


def parse_class(data):
    p = 10
    cp = {}
    n = struct.unpack_from('>H', data, 8)[0]
    i = 1
    while i < n:
        tag = data[p]; p += 1
        if tag == 1:
            ln = struct.unpack_from('>H', data, p)[0]; p += 2
            cp[i] = data[p:p + ln].decode('utf-8', 'replace'); p += ln
        elif tag in (7, 8, 16): p += 2
        elif tag == 15: p += 3
        elif tag in (3, 4, 9, 10, 11, 12, 17, 18): p += 4
        elif tag in (5, 6): p += 8; i += 1
        else: raise ValueError(tag)
        i += 1
    p += 6
    ic = struct.unpack_from('>H', data, p)[0]; p += 2 + 2 * ic
    fc = struct.unpack_from('>H', data, p)[0]; p += 2
    for _ in range(fc):
        p += 6
        ac = struct.unpack_from('>H', data, p)[0]; p += 2
        for _ in range(ac):
            p += 2
            ln = struct.unpack_from('>I', data, p)[0]; p += 4 + ln
    mc = struct.unpack_from('>H', data, p)[0]; p += 2
    methods = {}
    for _ in range(mc):
        _acc, ni, di = struct.unpack_from('>HHH', data, p); p += 6
        methods.setdefault(cp[ni], set()).add(cp[di])
        ac = struct.unpack_from('>H', data, p)[0]; p += 2
        for _ in range(ac):
            p += 2
            ln = struct.unpack_from('>I', data, p)[0]; p += 4 + ln
    return methods


def annotations(src, kinds):
    """Devuelve [(kind, cuerpo_entre_paréntesis)] balanceando paréntesis."""
    out = []
    for m in re.finditer(r'@(' + kinds + r')\s*\(', src):
        i = m.end() - 1
        depth = 0
        for j in range(i, len(src)):
            if src[j] == '(':
                depth += 1
            elif src[j] == ')':
                depth -= 1
                if depth == 0:
                    out.append((m.group(1), src[i + 1:j]))
                    break
    return out


def load_srg_names():
    """obf/owner/name -> nombre SRG (func_/field_)."""
    md, fd = {}, {}
    for line in open(SRG, encoding='utf-8', errors='replace'):
        if line.startswith('MD: '):
            pr = line.rstrip().split(' ')
            if len(pr) >= 4:
                own, meth = pr[1].split('/', 1)
                md[(own, meth, pr[2])] = pr[3].split('/')[-1]
        elif line.startswith('FD: '):
            pr = line.rstrip().split(' ')
            if len(pr) == 3 and '/' in pr[1]:
                own, fld = pr[1].split('/', 1)
                fd[(own, fld)] = pr[2].split('/')[-1]
    return md, fd


def main():
    cm = json.load(open(MAPPING))
    methods_obf = {}
    with zipfile.ZipFile(JAR) as z:
        for nm in z.namelist():
            if nm.endswith('.class'):
                try:
                    methods_obf[nm[:-6]] = parse_class(z.read(nm))
                except Exception:
                    pass

    md, fd = load_srg_names()
    srg_to_mcp = {}
    for row in csv.DictReader(open(os.path.join(HERE, 'mcp', 'methods.csv'), encoding='utf-8', errors='replace')):
        srg_to_mcp[row['searge']] = row['name']

    rows, unresolved = [], []
    stats = collections.Counter()
    per_class = collections.Counter()

    for path in sorted(glob.glob(os.path.join(MIXINS, '*.java'))):
        name = os.path.basename(path)[:-5]
        src = open(path, encoding='utf-8', errors='replace').read()
        mm = re.search(r'@Mixin\(\{([^}]*)\}\)', src)
        if not mm:
            continue
        targets = [x.strip().split('.')[0] for x in mm.group(1).split(',') if x.strip()]
        present = [t for t in targets if t in methods_obf]
        stats['mixins'] += 1
        stats['mixin_class_ok'] += bool(present)
        per_class[cm.get(targets[0], targets[0]).split('/')[-1]] += 1

        hooks = annotations(src, 'Inject|Redirect|ModifyArg|ModifyVariable|Overwrite')
        accs = annotations(src, 'Accessor|Invoker')
        stats['hooks'] += len(hooks)
        stats['accessors'] += len(accs)

        for kind, body in hooks:
            mmeth = re.search(r'method\s*=\s*\{(.*?)\}', body, re.S) or \
                    re.search(r'method\s*=\s*"([^"]+)"', body)
            names = re.findall(r'"([^"]+)"', mmeth.group(1)) if mmeth else []
            resolved, srgname = False, ''
            for cand in names:
                mname = cand.split('(')[0]
                desc = cand[len(mname):]
                for t in present:
                    cands = methods_obf[t]
                    if desc and desc in cands.get(mname, set()):
                        resolved = True
                        srgname = md.get((t, mname, desc), '')
                        break
                    if not desc and mname in cands:
                        resolved = True
                        srgname = md.get((t, mname, next(iter(cands[mname]))), '')
                        break
                if resolved:
                    break
            if names:
                stats['hook_resolved'] += resolved
            at = re.search(r'target\s*=\s*"L([^;]+);', body)
            tgt_ok = bool(at and at.group(1) in methods_obf)
            if at:
                stats['at_target'] += 1
                stats['at_target_ok'] += tgt_ok
            label = 'SÍ' if resolved else ('?' if not names else 'NO')
            rows.append((name, targets[0], names[0] if names else '',
                         srg_to_mcp.get(srgname, srgname), label))
            if label == 'NO':
                unresolved.append((name, targets[0], names, kind))

    print(f"{'Mixin':34} {'obf':9} {'objetivo':22} {'MCP':26} ok")
    for r in rows:
        print(f"{r[0]:34} {r[1]:9} {r[2]:22} {r[3]:26} {r[4]}")

    print('\n--- resumen ---')
    for k in ('mixins', 'mixin_class_ok', 'hooks', 'hook_resolved', 'accessors',
              'at_target', 'at_target_ok'):
        print(f'{k:16}: {stats[k]}')
    print(f'clases MC destino distintas: {len(per_class)}')
    print(f'\ntop clases instrumentadas: {per_class.most_common(10)}')
    if unresolved:
        print('\nNO resueltos:')
        for u in unresolved:
            print('  ', u)


if __name__ == '__main__':
    main()
