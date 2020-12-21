from functools import reduce

from util import read_lines


def main():
    data = read_lines("../resources/aoc2020/input21")
    food = [(
        line[:line.index(' (')].split(' '),
        line[line.index('(') + 10: -1].split(', ')
    ) for line in data]

    # part 1
    ing_map = dict()
    for f in food:
        for a in f[1]:
            if a in ing_map:
                ing_map.update({a: set(f[0]).intersection(ing_map.get(a))})
            else:
                ing_map.update({a: set(f[0])})

    allergens = reduce(set.union, ing_map.values())
    result1 = sum([len([ing for ing in f[0] if ing not in allergens]) for f in food])
    print("result1 =", result1)  # 2170

    # part 2
    for i1 in ing_map:
        for i2 in ing_map:
            if i1 == i2:
                continue
            in1 = ing_map.get(i1)
            in2 = ing_map.get(i2)
            if len(in1) >= len(in2):
                ing_map.update({i1: in1.difference(in2)})
            else:
                ing_map.update({i2: in2.difference(in1)})

    result2 = ','.join([ing_map.get(ing).pop() for ing in sorted(list(ing_map.keys()))])
    print("result2 =", result2)  # nfnfk,nbgklf,clvr,fttbhdr,qjxxpr,hdsm,sjhds,xchzh


if __name__ == '__main__':
    main()
