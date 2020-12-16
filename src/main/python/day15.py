def play(numbers, stop):
    indexed = {}
    for i in range(0, len(numbers) - 1):
        indexed.update({numbers[i]: i})

    last = numbers[-1]
    for i in range(len(indexed), stop - 1):
        if last in indexed:
            next_val = i - indexed.get(last)
        else:
            next_val = 0
        indexed.update({last: i})
        last = next_val

    return last


def main():
    numbers = [6, 19, 0, 5, 7, 13, 1]

    # part 1
    result1 = play(numbers, 2020)
    print("result1 =", result1)  # 468

    # part 2
    result2 = play(numbers, 30000000)
    print("result2 =", result2)  # 1801753


if __name__ == '__main__':
    main()
