from util import read_lines


def bisect(code, i, high):
    low = 0
    while (high - low) != 1:
        if (code[i] == 'F') | (code[i] == 'L'):
            high = high - round((high - low) / 2)
        else:
            low = low + round((high - low) / 2)
        i = i + 1

    if (code[i] == 'F') | (code[i] == 'L'):
        return low
    return high


def seat_id(code):
    return bisect(code, 0, 127) * 8 + bisect(code, 7, 7)


def main():
    data = read_lines("../resources/aoc2020/input05")
    seats = sorted([seat_id(d) for d in data])

    # part 1
    print("result1 =", seats[-1])  # 894

    # part 2
    i = 1
    while seats[i] - seats[i - 1] == 1:
        i = i + 1
    print("result2 =", seats[i] - 1)  # 579


if __name__ == '__main__':
    main()
