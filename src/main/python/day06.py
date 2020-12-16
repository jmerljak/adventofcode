from functools import reduce

from util import read_lines


def main():
    data = read_lines("../resources/aoc2020/input06")
    groups = " ".join(data).split("  ")

    # part 1
    result1 = sum([len(set(g.replace(" ", ""))) for g in groups])
    print("result1 =", result1)  # 6585

    # part 2
    result2 = sum([len(reduce(set.intersection, [set(p) for p in g.split(" ")])) for g in groups])
    print("result2 =", result2)  # 3276


if __name__ == '__main__':
    main()
