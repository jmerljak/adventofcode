from util import read_lines


def match(tile1, tile2):
    borders1 = ["".join([d[0] for d in tile1]),
                "".join([d[9] for d in tile1]),
                tile1[0],
                tile1[9]]
    borders2 = ["".join([d[0] for d in tile2]),
                "".join([d[9] for d in tile2]),
                tile2[0],
                tile2[9]]

    for b1 in borders1:
        for b2 in borders2:
            if (b1 == b2) | (b1 == b2[::-1]):
                return True

    return False


def main():
    data = read_lines("../resources/aoc2020/input20")

    tiles = {}
    arr = {}
    for i in range(0, len(data), 12):
        tile_id = int(data[i][5:9])
        tiles.update({tile_id: data[i + 1:i + 11]})
        arr.update({tile_id: []})

    for i in tiles:
        for j in tiles:
            if i == j:
                continue
            tile1 = tiles.get(i)
            tile2 = tiles.get(j)
            if match(tile1, tile2):
                arr.update({i: arr.get(i) + [j]})

    # part 1
    result1 = 1
    for n in [k for k in arr.keys() if len(arr.get(k)) == 2]:
        result1 = result1 * n
    print("result1 =", result1)  # 23497974998093

    # part 2
    # TODO


if __name__ == '__main__':
    main()
