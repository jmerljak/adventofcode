def find_loop_size(subject_number, key):
    value = 1
    for loop_size in range(1, 1000000):
        value = (value * subject_number) % 20201227
        if value == key:
            return loop_size


def transform(subject_number, loop_size):
    value = 1
    for _ in range(0, loop_size):
        value = (value * subject_number) % 20201227
    return value


def main():
    subject_number = 7
    card_public_key = 13316116
    door_public_key = 13651422

    # part 1
    card_loop_size = find_loop_size(subject_number, card_public_key)
    encryption_key = transform(door_public_key, card_loop_size)
    print("result1 =", encryption_key)  # 12929

    # part 2
    # a gold coin from the pouch :)


if __name__ == '__main__':
    main()
