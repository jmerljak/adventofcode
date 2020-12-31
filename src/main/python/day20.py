import re

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


def rotate90(tile):
    # https://stackoverflow.com/a/57958721
    return list(''.join(list(x)[::-1]) for x in zip(*tile))


def flip_v(tile):
    return tile[:: -1]


def flip_h(tile):
    return [line[::-1] for line in tile]


def align(tile1, tile2):
    if tile1[0] == tile2[9]:
        return 1
    if "".join([d[9] for d in tile1]) == "".join([d[0] for d in tile2]):
        return 2
    if tile1[9] == tile2[0]:
        return 3
    if "".join([d[0] for d in tile1]) == "".join([d[9] for d in tile2]):
        return 4
    return None


def transform_and_match(tile1, tile2):
    pos = align(tile1, tile2)
    if pos is not None:
        return pos, tile2

    r0v = flip_v(tile2)
    pos = align(tile1, r0v)
    if pos is not None:
        return pos, r0v

    r0h = flip_h(tile2)
    pos = align(tile1, r0h)
    if pos is not None:
        return pos, r0h

    r90 = rotate90(tile2)
    pos = align(tile1, r90)
    if pos is not None:
        return pos, r90

    r90v = flip_v(r90)
    pos = align(tile1, r90v)
    if pos is not None:
        return pos, r90v

    r90h = flip_h(r90)
    pos = align(tile1, r90h)
    if pos is not None:
        return pos, r90h

    r180 = rotate90(r90)
    pos = align(tile1, r180)
    if pos is not None:
        return pos, r180

    r180v = flip_v(r180)
    pos = align(tile1, r180v)
    if pos is not None:
        return pos, r180v

    r180h = flip_h(r180)
    pos = align(tile1, r180h)
    if pos is not None:
        return pos, r180h

    r270 = rotate90(r180)
    pos = align(tile1, r270)
    if pos is not None:
        return pos, r270


def find_right(tiles, neighbours, skip, id1):
    tile1 = tiles.get(id1)
    for id2 in neighbours.get(id1):
        if id2 in skip:
            continue
        p, t2 = transform_and_match(tile1, tiles.get(id2))
        if p == 2:
            return id2, t2
    return None, None


def find_below(tiles, neighbours, skip, id1):
    tile1 = tiles.get(id1)
    for id2 in neighbours.get(id1):
        if id2 in skip:
            continue
        p, t2 = transform_and_match(tile1, tiles.get(id2))
        if p == 3:
            return id2, t2
    return None, None


def join_tiles(tile1, tile2):
    for i in range(0, len(tile1)):
        tile1[i] = tile1[i] + tile2[i + 1][1:9]
    return tile1


def main():
    data = read_lines("../resources/aoc2020/input20")

    tiles = {}
    neighbours = {}
    for i in range(0, len(data), 12):
        tiles.update({int(data[i][5:9]): data[i + 1:i + 11]})

    for id1 in tiles:
        for id2 in tiles:
            if id1 == id2:
                continue
            tile1 = tiles.get(id1)
            tile2 = tiles.get(id2)
            if match(tile1, tile2):
                if id1 in neighbours:
                    neighbours.update({id1: neighbours.get(id1) + [id2]})
                else:
                    neighbours.update({id1: [id2]})

    # part 1
    result1 = 1
    corners = [k for k in neighbours.keys() if len(neighbours.get(k)) == 2]
    for tile_id in corners:
        result1 = result1 * tile_id
    print("result1 =", result1)  # 23497974998093

    # part 2
    skip = set()
    head = corners[0]
    tile = tiles.get(head)
    image = []
    while head is not None:
        skip.add(head)
        tiles.update({head: tile})
        joined = [line[1:9] for line in tile.copy()[1:9]]
        right, tile = find_right(tiles, neighbours, skip, head)
        while right is not None:
            skip.add(right)
            tiles.update({right: tile})
            joined = join_tiles(joined, tile)
            right, tile = find_right(tiles, neighbours, skip, right)

        image = image + joined
        head, tile = find_below(tiles, neighbours, skip, head)

    # rotate image (a bit of guessing here)
    image = rotate90(image)

    monster_count = 0
    for i in range(1, len(image) - 1):
        for match2 in re.finditer('#.{4}##.{4}##.{4}###', image[i]):
            start = match2.span()[0]
            match1 = re.fullmatch('.{18}#.', image[i - 1][start:start + 20])
            if match1 is not None:
                match3 = re.fullmatch('.#.{2}#.{2}#.{2}#.{2}#.{2}#.{3}', image[i + 1][start:start + 20])
                if match3 is not None:
                    monster_count = monster_count + 1

            if re.match('#.{4}##.{4}##.{4}###', image[i][start + 1:]) is not None:
                # overlapping monsters, increase counter
                # TODO check lines above and below
                print("another monster!")
                monster_count = monster_count + 1

    result2 = sum([len([x for x in line if x == '#']) for line in image]) - (monster_count * 15)
    print("result2 =", result2)  # 2256


if __name__ == '__main__':
    main()
