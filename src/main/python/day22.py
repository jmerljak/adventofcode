import sys

from util import read_lines


def play(cards1, cards2):
    if len(cards1) == 0:
        # print("player 2 wins!")
        return cards2
    if len(cards2) == 0:
        # print("player 1 wins!")
        return cards1

    c1 = cards1.pop()
    c2 = cards2.pop()
    if c1 > c2:
        return play([c2, c1] + cards1, cards2)
    else:
        return play(cards1, [c1, c2] + cards2)


def play_recur(cards1, cards2, prev1, prev2):
    if len(cards1) == 0:
        # print("player 2 wins!")
        return 2, cards2
    if (len(cards2) == 0) | (cards1 in prev1) | (cards2 in prev2):
        # print("player 1 wins!")
        return 1, cards1

    prev1.insert(0, cards1[:])
    prev2.insert(0, cards2[:])

    c1 = cards1.pop()
    c2 = cards2.pop()

    if (len(cards1) >= c1) & (len(cards2) >= c2):
        # print("sub-game!")
        if play_recur(cards1[-c1:], cards2[-c2:], [], [])[0] == 1:
            return play_recur([c2, c1] + cards1, cards2, prev1, prev2)
        else:
            return play_recur(cards1, [c1, c2] + cards2, prev1, prev2)

    if c1 > c2:
        return play_recur([c2, c1] + cards1, cards2, prev1, prev2)
    else:
        return play_recur(cards1, [c1, c2] + cards2, prev1, prev2)


def main():
    data = read_lines("../resources/aoc2020/input22")
    i = 0
    while data[i] != '':
        i = i + 1
    cards1 = [int(d) for d in data[i - 1:0:-1]]
    cards2 = [int(d) for d in data[:i + 1:-1]]

    # part 1
    win = play(cards1[:], cards2[:])
    result1 = sum([win[i] * (i + 1) for i in range(0, len(win))])
    print("result1 =", result1)  # 33694

    # part 2
    sys.setrecursionlimit(3000)
    win = play_recur(cards1[:], cards2[:], [], [])[1]
    result2 = sum([win[i] * (i + 1) for i in range(0, len(win))])
    print("result2 =", result2)  # 31835


if __name__ == '__main__':
    main()
