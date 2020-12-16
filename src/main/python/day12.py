import re
from math import cos, radians, sin

from util import read_lines


def main():
    pattern = "(N|S|E|W|L|R|F)([0-9]+)$"
    data = read_lines("../resources/aoc2020/input12")
    instructions = [re.match(pattern, line) for line in data]

    # part 1
    x, y, i = 0, 0, 1
    directions = ['N', 'E', 'S', 'W']
    for instruction in instructions:
        action = instruction[1]
        value = int(instruction[2])
        if (action == 'N') | ((action == 'F') & (directions[i] == 'N')):
            y = y - value
        if (action == 'S') | ((action == 'F') & (directions[i] == 'S')):
            y = y + value
        if (action == 'E') | ((action == 'F') & (directions[i] == 'E')):
            x = x - value
        if (action == 'W') | ((action == 'F') & (directions[i] == 'W')):
            x = x + value
        if action == 'L':
            i = (i - int(value / 90)) % 4
        if action == 'R':
            i = (i + int(value / 90)) % 4

    result1 = abs(x) + abs(y)
    print("result1 =", result1)  # 562

    # part 2
    x, y = 0, 0
    waypoint = (-10, -1)
    for instruction in instructions:
        action = instruction[1]
        value = int(instruction[2])
        if action == 'N':
            waypoint = (waypoint[0], waypoint[1] - value)
        if action == 'S':
            waypoint = (waypoint[0], waypoint[1] + value)
        if action == 'E':
            waypoint = (waypoint[0] - value, waypoint[1])
        if action == 'W':
            waypoint = (waypoint[0] + value, waypoint[1])
        if (action == 'L') | (action == 'R'):
            angle = radians(value)
            if action == 'R': angle = - angle
            w1 = (int(waypoint[0] * cos(angle)) - int(waypoint[1] * sin(angle)))
            w2 = (int(waypoint[0] * sin(angle)) + int(waypoint[1] * cos(angle)))
            waypoint = (w1, w2)
        if action == 'F':
            x = x + (value * waypoint[0])
            y = y + (value * waypoint[1])

    result2 = abs(x) + abs(y)
    print("result2 =", result2)  # 101860


if __name__ == '__main__':
    main()
