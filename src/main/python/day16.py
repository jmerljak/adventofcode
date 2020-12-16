import re

from util import read_lines


def is_valid(value, rule):
    return (rule[1] <= value <= rule[2]) | (rule[3] <= value <= rule[4])


def any_valid(value, rules):
    for rule in rules:
        if is_valid(value, rule):
            return True
    return False


def sum_error_values(ticket, rules):
    s = 0
    for value in ticket:
        if not any_valid(value, rules):
            s = s + value
    return s


def find_possible_indexes(tickets, rule):
    a = []
    for i in range(0, len(tickets[0])):
        valid = True
        for ticket in tickets:
            # print(ticket[i])
            if not is_valid(ticket[i], rule):
                valid = False
                break
        if valid:
            a.append(i)

    return {rule[0]: set(a)}


def main():
    rule_pattern = '(.+): ([0-9]+)\\-([0-9]+) or ([0-9]+)\\-([0-9]+)'
    data = read_lines("../resources/aoc2020/input16")

    rules = []
    for line in data:
        if line == '':
            break
        match = re.match(rule_pattern, line)
        rules.append((match[1], int(match[2]), int(match[3]), int(match[4]), int(match[5])))

    my_ticket = [int(v) for v in data[len(rules) + 2].split(',')]
    tickets = [[int(v) for v in line.split(',')] for line in data[len(rules) + 5:]]

    # part 1
    result1 = sum(map(lambda t: sum_error_values(t, rules), tickets))
    print("result1 =", result1)  # 32835

    # part 2
    valid_tickets = list(filter(lambda t: sum_error_values(t, rules) == 0, tickets))

    positions = {}
    for rule in rules:
        indexes = find_possible_indexes([my_ticket] + valid_tickets, rule)
        print(indexes)
        positions.update(indexes)

    # by manually analysing possible indexes we determine
    # departure values are at positions 1, 2, 6, 13, 14 and 15
    # TODO solve with code

    result2 = my_ticket[1] * \
              my_ticket[2] * \
              my_ticket[6] * \
              my_ticket[13] * \
              my_ticket[14] * \
              my_ticket[15]
    print("result2 =", result2)  # 514662805187


if __name__ == '__main__':
    main()
