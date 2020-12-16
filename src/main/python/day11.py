from util import read_lines


def find_first(seats, xy, x_step, y_step, radius):
    (x, y) = xy
    for _ in range(1, radius + 1):
        x = x + x_step
        y = y + y_step
        if (x < 0) | (y < 0):
            return None
        if (x, y) in seats:
            return seats.get((x, y))

    return None


def count_adjacent(seats, xy, c, radius):
    count = 0
    if find_first(seats, xy, - 1, - 1, radius) is c:
        count = count + 1
    if find_first(seats, xy, - 1, 0, radius) is c:
        count = count + 1
    if find_first(seats, xy, - 1, 1, radius) is c:
        count = count + 1
    if find_first(seats, xy, 0, -1, radius) is c:
        count = count + 1
    if find_first(seats, xy, 0, 1, radius) is c:
        count = count + 1
    if find_first(seats, xy, 1, -1, radius) is c:
        count = count + 1
    if find_first(seats, xy, 1, 0, radius) is c:
        count = count + 1
    if find_first(seats, xy, 1, 1, radius) is c:
        count = count + 1
    return count


def proceed(seats, radius=1, tolerance=3):
    changed = seats.copy()
    is_changed = False
    for seat in seats:
        if (seats.get(seat) == 'L') & (count_adjacent(seats, seat, "#", radius) == 0):
            changed.update({seat: '#'})
            is_changed = True
        if (seats.get(seat) == '#') & (count_adjacent(seats, seat, "#", radius) > tolerance):
            changed.update({seat: 'L'})
            is_changed = True

    return is_changed, changed


def main():
    data = read_lines("../resources/aoc2020/input11")
    seats = {}
    for y in range(0, len(data)):
        for x in range(0, len(data[0])):
            if data[y][x] != '.':
                seats.update({(x, y): data[y][x]})

    # part 1
    change, state = True, seats
    while change:
        change, state = proceed(state)

    result1 = len(list(filter(lambda seat: seat == '#', state.values())))
    print("result1 =", result1)  # 2251

    # part 2
    change, state = True, seats
    while change:
        change, state = proceed(state, len(data), 4)

    result2 = len(list(filter(lambda seat: seat == '#', state.values())))
    print("result2 =", result2)  # 2019


if __name__ == '__main__':
    main()
