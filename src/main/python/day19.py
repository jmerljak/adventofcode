import re

from util import read_lines


def parse_rules(data):
    rules = {}
    for line in data:
        if line == '':
            break
        parts = line.split(': ')
        rules.update({parts[0]: parts[1]})
    return rules


def resolve(rules, rule_id, rng = 0):
    rule = rules.get(rule_id)
    if rule.startswith('"'):
        return rule.replace('"', '')

    match = re.match('([0-9]+) \\| \\1 ' + rule_id, rule)
    if match is not None:
        return '(' + resolve(rules, match[1], rng) + ')+'

    match = re.match('([0-9]+) ([0-9]+) \\| \\1 ' + rule_id + ' \\2', rule)
    if match is not None:
        resolved1 = resolve(rules, match[1], rng)
        resolved2 = resolve(rules, match[2], rng)
        resolved = '|'.join(
            [''.join([resolved1] * i + [resolved2] * i) for i in range(1, rng)])
        return '(' + resolved + ')'

    if re.fullmatch('[0-9]+', rule):
        return resolve(rules, rule, rng)
    if ' ' not in rule:
        return rule
    if '|' in rule:
        resolved = '(' + '|'.join(
            [''.join([resolve(rules, r, rng) for r in r1.split(' ')]) for r1 in rule.split(' | ')]) + ")"
        rules.update({id: resolved})
        return resolved
    else:
        resolved = ''.join([resolve(rules, r, rng) for r in rule.split(' ')])
        rules.update({id: resolved})
        return resolved


def main():
    data = read_lines("../resources/aoc2020/input19")
    rules = parse_rules(data)
    messages = data[len(rules) + 1:]

    # part 1
    rule0 = resolve(rules, '0')
    result1 = len([m for m in messages if re.fullmatch(rule0, m) is not None])
    print("result1 =", result1)  # 213

    # part 2
    # re-parse and update rules
    rules = parse_rules(data)
    rules.update({'8': '42 | 42 8'})
    rules.update({'11': '42 31 | 42 11 31'})

    result2 = 0
    for i in range(2, 10):
        prev = result2
        rule0 = resolve(rules, '0', i)
        result2 = len([m for m in messages if re.fullmatch(rule0, m) is not None])
        if result2 == prev:
            break

    print("result2 =", result2)  # 325


if __name__ == '__main__':
    main()
