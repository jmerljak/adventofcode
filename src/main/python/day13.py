from util import read_lines


def main():
    data = read_lines("../resources/aoc2020/input13")
    timestamp = int(data[0])
    busses = data[1].split(",")

    # part 1
    relevant = [int(b) for b in filter(lambda b: b != 'x', busses)]
    t1, found, bus = 0, False, None
    while not found:
        t1 = t1 + 1
        for r in relevant:
            if (timestamp + t1) % r == 0:
                found = True
                bus = r
                break

    result1 = bus * t1
    print("result1 =", result1)  # 246

    # part 2
    indexed = []
    for i in range(0, len(busses)):
        if busses[i] != 'x':
            indexed.append((i, int(busses[i])))

    t2, step = 0, indexed[0][1]
    for i in range(1, len(indexed)):
        index = indexed[i][0]
        number = indexed[i][1]
        while (t2 + index) % number != 0:
            t2 = t2 + step
        step = step * number

    result2 = t2
    print("result2 =", result2)  # 939490236001473


if __name__ == '__main__':
    main()
