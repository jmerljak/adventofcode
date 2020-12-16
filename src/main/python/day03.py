from util import read_lines


def check_slope(terrain, x_step, y_step) -> int:
    x = 0
    width = len(terrain[0])
    count = 0
    for y in range(0, len(terrain), y_step):
        item = terrain[y][x % width]
        x = x + x_step
        if item == '#':
            count += 1
    return count


def main():
    terrain = read_lines("../resources/aoc2020/input03")

    # part 1
    result1 = check_slope(terrain, 3, 1)
    print("result1 =", result1)  # 171

    # part 2
    result2 = check_slope(terrain, 1, 1) * \
              check_slope(terrain, 3, 1) * \
              check_slope(terrain, 5, 1) * \
              check_slope(terrain, 7, 1) * \
              check_slope(terrain, 1, 2)
    print("result2 =", result2)  # 1206576000


if __name__ == '__main__':
    main()
