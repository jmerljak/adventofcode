import re

from util import read_lines


def main():
    pattern = "([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)"
    lines = read_lines("../resources/aoc2020/input02")
    data = [re.match(pattern, line) for line in lines]

    # part 1
    result1 = len(list(filter(lambda d: int(d[1]) <= d[4].count(d[3]) <= int(d[2]), data)))
    print("result1 =", result1)  # 636

    # part 2
    result2 = len(list(filter(lambda d: (d[4][int(d[1]) - 1] == d[3]) ^ (d[4][int(d[2]) - 1] == d[3]), data)))
    print("result2 =", result2)  # 588


if __name__ == '__main__':
    main()
