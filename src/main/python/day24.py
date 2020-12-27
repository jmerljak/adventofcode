from util import read_lines


def adjacent(tile):
    x, y = tile
    return [(x - 2, y), (x + 2, y), (x - 1, y - 1), (x - 1, y + 1), (x + 1, y - 1), (x + 1, y + 1)]


def count_adjacent(tile, blacks):
    return len([t for t in adjacent(tile) if t in blacks])


def flip_tiles(blacks):
    updated = set()
    for black in blacks:
        if 0 < count_adjacent(black, blacks) < 3:
            updated.add(black)
        for tile in adjacent(black):
            if tile not in blacks and count_adjacent(tile, blacks) == 2:
                updated.add(tile)

    return updated


def main():
    data = read_lines("../resources/aoc2020/input24")

    # part 1
    blacks = set()
    for line in data:
        x, y, i = 0, 0, 0
        while i < len(line):
            if line[i] == 'e':
                x = x + 2
                i = i + 1
            elif line[i] == 'w':
                x = x - 2
                i = i + 1
            elif line[i] == 's':
                if line[i + 1] == 'e':
                    x = x + 1
                else:
                    x = x - 1
                y = y + 1
                i = i + 2
            elif line[i] == 'n':
                if line[i + 1] == 'e':
                    x = x + 1
                else:
                    x = x - 1
                y = y - 1
                i = i + 2
        if (x, y) in blacks:
            blacks.remove((x, y))
        else:
            blacks.add((x, y))

    result1 = len(blacks)
    print("result1 =", result1)  # 382

    # part 2
    for _ in range(0, 100):
        blacks = flip_tiles(blacks)

    result2 = len(blacks)
    print("result2 =", result2)  # 3964


if __name__ == '__main__':
    main()
