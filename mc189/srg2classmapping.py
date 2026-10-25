#!/usr/bin/env python3
"""
srg2classmapping.py — convierte joined.srg (obf -> SRG) +
methods.csv/fields.csv (SRG -> nombre) en un mapping JSON de ClassRemapper:
  { "obf/a": "net/minecraft/Foo", "obf/a/b()V": "realName" }

Uso: python3 srg2classmapping.py
"""
import csv, json, os, re

HERE = os.path.dirname(os.path.abspath(__file__))

def load():
    classes = {}
    srg = os.path.join(HERE, 'mcp-srg', 'joined.srg')
    with open(srg, encoding='utf-8', errors='replace') as f:
        for line in f:
            line = line.rstrip('\r\n')
            if line.startswith('CL: '):
                _, obf, real = line.split(' ', 2)
                classes[obf] = real
    return classes

def load_members():
    methods, fields = {}, {}
    with open(os.path.join(HERE, 'mcp', 'methods.csv'), encoding='utf-8', errors='replace') as f:
        for row in csv.DictReader(f):
            methods[row['searge']] = row['name'].strip() or row['searge']
    with open(os.path.join(HERE, 'mcp', 'fields.csv'), encoding='utf-8', errors='replace') as f:
        for row in csv.DictReader(f):
            fields[row['searge']] = row['name'].strip() or row['searge']
    return methods, fields

def main():
    classes = load()
    methods, fields = load_members()

    # descriptor ofuscado -> descriptor remapeado
    def remap_desc(desc):
        if not desc:
            return desc
        out, i = [], 0
        while i < len(desc):
            ch = desc[i]
            if ch == 'L':
                end = desc.index(';', i)
                name = desc[i + 1:end]
                arr = ''
                while name.endswith('[]'):
                    name, arr = name[:-2], arr + '[]'
                out.append('L' + classes.get(name, name) + ';' + arr)
                i = end + 1
            else:
                out.append(ch)
                i += 1
        return ''.join(out)

    mapping = {}
    for obf, real in classes.items():
        mapping[obf] = real

    n_md = n_fd = n_hit = 0
    with open(os.path.join(HERE, 'mcp-srg', 'joined.srg'), encoding='utf-8', errors='replace') as f:
        for line in f:
            line = line.rstrip('\r\n')
            if line.startswith('MD: '):
                parts = line.split(' ')
                if len(parts) < 4:
                    continue
                obf_owner_meth, obf_desc, srg_owner_meth = parts[1], parts[2], parts[3]
                owner, meth = obf_owner_meth.split('/', 1)
                srg_name = srg_owner_meth.split('/')[-1]
                real = methods.get(srg_name)
                if real and owner in classes:
                    key = '%s/%s%s' % (classes[owner], meth, remap_desc(obf_desc))
                    mapping[key] = real
                    n_md += 1
                    n_hit += 1
            elif line.startswith('FD: '):
                parts = line.split(' ')
                if len(parts) != 3:
                    continue
                obf_owner_field, srg_owner_field = parts[1:]
                if '/' not in obf_owner_field:
                    continue
                owner, fld = obf_owner_field.split('/', 1)
                srg_name = srg_owner_field.split('/')[-1]
                real = fields.get(srg_name)
                if real and owner in classes:
                    key = '%s/%s' % (classes[owner], fld)
                    mapping[key] = real
                    n_fd += 1
                    n_hit += 1

    # las clases anidadas deben ir primero en ClassRemapper? (no) - pero el
    # nombre de la clase interna no lleva el prefijo externo en el .class obf
    out = os.path.join(HERE, 'classmapping.json')
    with open(out, 'w', encoding='utf-8') as f:
        json.dump(mapping, f)
    print('clases            :', len(classes))
    print('métodos mapeados  :', n_md)
    print('campos mapeados   :', n_fd)
    print('entradas totales  :', len(mapping))
    print('escrito           :', out)

if __name__ == '__main__':
    main()
