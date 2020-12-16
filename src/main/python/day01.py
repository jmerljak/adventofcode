from util import read_lines


def part1(numbers):
    for i in range(0, len(numbers)):
        for j in range(i, len(numbers)):
            x = int(numbers[i])
            y = int(numbers[j])
            if x + y == 2020:
                return x * y


def part2(numbers):
    for i in range(0, len(numbers)):
        for j in range(i, len(numbers)):
            for k in range(j, len(numbers)):
                x = int(numbers[i])
                y = int(numbers[j])
                z = int(numbers[k])
                if x + y + z == 2020:
                    return x * y * z


def main():
    numbers = read_lines("../resources/aoc2020/input01")

    # part 1
    print("result1 =", part1(numbers))  # 877971

    # part 2
    print("result2 =", part2(numbers))  # 203481432


if __name__ == '__main__':
    main()
