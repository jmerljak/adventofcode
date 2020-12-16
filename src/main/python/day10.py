from util import read_lines


def paths(jolts, i, calculated):
    if i in calculated:
        return calculated.get(i)

    n = paths(jolts, i - 1, calculated)
    if (i > 1) & (0 < jolts[i] - jolts[i - 2] < 4):
        n = n + paths(jolts, i - 2, calculated)
    if (i > 2) & (0 < jolts[i] - jolts[i - 3] < 4):
        n = n + paths(jolts, i - 3, calculated)

    calculated.update({i: n})
    return n


def main():
    data = [int(line) for line in read_lines("../resources/aoc2020/input10")]
    data.append(0)
    data.append(max(data) + 3)
    jolts = sorted(data)

    # part 1
    ones, threes = 0, 0
    for i in range(0, len(jolts) - 1):
        diff = jolts[i + 1] - jolts[i]
        if diff == 1:
            ones = ones + 1
        elif diff == 3:
            threes = threes + 1

    result1 = ones * threes
    print("result1 =", result1)  # 2201

    # part 2
    result2 = paths(jolts, len(jolts) - 1, {0: 1, 1: 1})
    print("result2 =", result2)  # 169255295254528


if __name__ == '__main__':
    main()
