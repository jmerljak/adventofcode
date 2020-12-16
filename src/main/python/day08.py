from util import read_lines


def execute(program, index, acc):
    instr = program[index]
    op = instr[0:3]
    # print("executing", instr)

    if op == 'nop':
        return index + 1, acc

    arg = int(instr[4:])
    if op == 'acc':
        return index + 1, acc + arg
    if op == 'jmp':
        return index + arg, acc

    raise AssertionError("unknown operation: " + op)


def execute_program(program):
    acc = 0
    index = 0
    visited = set()
    while index not in visited:
        visited.add(index)
        (index, acc) = execute(program, index, acc)
        if index == len(program):
            return True, acc

    return False, acc


def main():
    program = read_lines("../resources/aoc2020/input08")

    # part 1
    result1 = execute_program(program)[1]
    print("result1 =", result1)  # 1614

    # part 2
    result2 = -1
    for i in range(0, len(program)):
        if program[i].startswith("acc"):
            continue
        elif program[i].startswith('jmp'):
            p2 = program.copy()
            p2[i] = program[i].replace('jmp', 'nop')
            r = execute_program(p2)
            if r[0]:
                result2 = r[1]
                break
        else:
            p2 = program.copy()
            p2[i] = program[i].replace('nop', 'jmp')
            r = execute_program(p2)
            if r[0]:
                result2 = r[1]
                break

    print("result2 =", result2)  # 1260


if __name__ == '__main__':
    main()
