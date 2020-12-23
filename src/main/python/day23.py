def main():
    cups = [6, 8, 5, 9, 7, 4, 2, 1, 3]

    # part 1
    for _ in range(0, 100):
        curr = cups[0]
        pick = cups[1:4]
        rest = cups[:1] + cups[4:]

        dest_value = curr - 1
        while dest_value in pick:
            dest_value = dest_value - 1
        if dest_value < 1:
            dest_value = max(rest)

        i = rest.index(dest_value)
        cups = rest[:i + 1] + pick + rest[i + 1:]

        i = cups.index(curr) + 1
        cups = cups[i:] + cups[:i]

    i = cups.index(1)
    cups = cups[i + 1:] + cups[:i]
    result1 = "".join([str(c) for c in cups])
    print("result1 =", result1)  # 82635947

    # part 2
    # TODO


if __name__ == '__main__':
    main()
