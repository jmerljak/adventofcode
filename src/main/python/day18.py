import re

from util import read_lines

pattern = '.*(\\(.+\\)).*'


def evaluate_ltr(expr):
    matches = re.findall(pattern, expr)
    while len(matches) > 0:
        for m in matches:
            stop = m.index(')')
            replacement = evaluate_ltr(m[1:stop])
            expr = expr.replace(m[0:stop + 1], str(replacement))
        matches = re.findall(pattern, expr)

    parts = expr.split(' ')
    result = int(parts[0])
    for i in range(1, len(parts) - 1, 2):
        if parts[i] == '+':
            result = result + int(parts[i + 1])
        else:
            result = result * int(parts[i + 1])
    return result


def evaluate_adv(expr):
    matches = re.findall(pattern, expr)
    while len(matches) > 0:
        for m in matches:
            stop = m.index(')')
            replacement = evaluate_adv(m[1:stop])
            expr = expr.replace(m[0:stop + 1], str(replacement))
        matches = re.findall(pattern, expr)

    parts = expr.split(' * ')
    parts = [str(evaluate_ltr(p)) for p in parts]
    return evaluate_ltr(' * '.join(parts))


def main():
    data = read_lines("../resources/aoc2020/input18")

    # part 1
    result1 = sum([evaluate_ltr(expr) for expr in data])
    print("result1 =", result1)  # 36382392389406

    # part 2
    result2 = sum([evaluate_adv(expr) for expr in data])
    print("result2 =", result2)  # 381107029777968


if __name__ == '__main__':
    main()
