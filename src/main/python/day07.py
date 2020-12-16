from util import read_lines


def parse_rule(rule: str):
    i = rule.find(" bags contain ")
    item = rule[0:i]
    if rule.endswith(" no other bags."):
        return item, {}

    content = rule[i + 14:-1].replace(" bags", "").replace(" bag", "").split(", ")
    return item, {c[2:]: int(c[0]) for c in content}


def may_contain(rules, key, color):
    content: dict = rules.get(key)
    if len(content) == 0:
        return False

    for c in content.keys():
        if (c == color) | may_contain(rules, c, color):
            return True

    return False


def count_content(rules: dict, key):
    content: dict = rules.get(key)
    if len(content) == 0:
        return 0

    return sum([(content[k] + content[k] * count_content(rules, k)) for k in content])


def main():
    data = read_lines("../resources/aoc2020/input07")
    rules = dict([parse_rule(r) for r in data])

    # part 1
    result1 = len(list(filter(lambda k: may_contain(rules, k, "shiny gold"), rules.keys())))
    print("result1 =", result1)  # 272

    # part 2
    result2 = count_content(rules, "shiny gold")
    print("result2 =", result2)  # 172246


if __name__ == '__main__':
    main()
