from util import read_lines


def count_adjacent_3d(cubes, x, y, z):
    s = 0
    for dx in (-1, 0, 1):
        for dy in (-1, 0, 1):
            for dz in (-1, 0, 1):
                if (dx == 0) & (dy == 0) & (dz == 0):
                    continue
                elif (x + dx, y + dy, z + dz) in cubes:
                    s = s + 1
    return s


def proceed_3d(cubes):
    updated = cubes.copy()
    x_min = min([c[0] for c in cubes]) - 1
    x_max = max([c[0] for c in cubes]) + 2
    y_min = min([c[1] for c in cubes]) - 1
    y_max = max([c[1] for c in cubes]) + 2
    z_min = min([c[2] for c in cubes]) - 1
    z_max = max([c[2] for c in cubes]) + 2

    for x in range(x_min, x_max):
        for y in range(y_min, y_max):
            for z in range(z_min, z_max):
                n = count_adjacent_3d(cubes, x, y, z)
                if (x, y, z) in cubes:
                    if (n < 2) | (n > 3):
                        updated.remove((x, y, z))
                elif n == 3:
                    updated.add((x, y, z))

    return updated


def count_adjacent_4d(cubes, x, y, z, w):
    s = 0
    for dx in (-1, 0, 1):
        for dy in (-1, 0, 1):
            for dz in (-1, 0, 1):
                for dw in (-1, 0, 1):
                    if (dx == 0) & (dy == 0) & (dz == 0) & (dw == 0):
                        continue
                    elif (x + dx, y + dy, z + dz, w + dw) in cubes:
                        s = s + 1
    return s


def proceed_4d(hcubes):
    updated = hcubes.copy()
    x_min = min([c[0] for c in hcubes]) - 1
    x_max = max([c[0] for c in hcubes]) + 2
    y_min = min([c[1] for c in hcubes]) - 1
    y_max = max([c[1] for c in hcubes]) + 2
    z_min = min([c[2] for c in hcubes]) - 1
    z_max = max([c[2] for c in hcubes]) + 2
    w_min = min([c[3] for c in hcubes]) - 1
    w_max = max([c[3] for c in hcubes]) + 2

    for x in range(x_min, x_max):
        for y in range(y_min, y_max):
            for z in range(z_min, z_max):
                for w in range(w_min, w_max):
                    n = count_adjacent_4d(hcubes, x, y, z, w)
                    if (x, y, z, w) in hcubes:
                        if (n < 2) | (n > 3):
                            updated.remove((x, y, z, w))
                    elif n == 3:
                        updated.add((x, y, z, w))

    return updated


def main():
    data = read_lines("../resources/aoc2020/input17")

    # part 1
    cubes = set()
    for y in range(0, len(data)):
        for x in range(0, len(data[0])):
            if data[y][x] == '#':
                cubes.add((x, y, 0))

    for _ in range(0, 6):
        cubes = proceed_3d(cubes)

    result1 = len(cubes)
    print("result1 =", result1)  # 273

    # part 2
    hcubes = set()
    for y in range(0, len(data)):
        for x in range(0, len(data[0])):
            if data[y][x] == '#':
                hcubes.add((x, y, 0, 0))

    for _ in range(0, 6):
        hcubes = proceed_4d(hcubes)

    result2 = len(hcubes)
    print("result1 =", result2)  # 1504


if __name__ == '__main__':
    main()
