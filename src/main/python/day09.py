from util import read_lines


def is_valid(preamble, value):
    for i in range(0, len(preamble) - 1):
        for j in range(i + 1, len(preamble)):
            if preamble[i] + preamble[j] == value:
                return True

    return False


def find_range(data, value):
    for i in range(0, len(data) - 1):
        s = data[i]
        for j in range(i + 1, len(data)):
            s = s + data[j]
            if s == value:
                return data[i:j + 1]
            elif s > value:
                break


def main():
    data = [int(line) for line in read_lines("../resources/aoc2020/input09")]
    p_len = 25

    # part 1
    i = p_len
    while is_valid(data[i - p_len:i], data[i]):
        i = i + 1
    result1 = data[i]
    print("result1 =", result1)  # 15353384

    # part 2
    numbers = find_range(data, result1)
    result2 = min(numbers) + max(numbers)
    print("result2 =", result2)  # 2466556


if __name__ == '__main__':
    main()
