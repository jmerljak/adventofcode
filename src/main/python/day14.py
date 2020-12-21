from util import read_lines


def mask_value(value, mask):
    b_val = '{:0>36b}'.format(int(bin(value)[2:]))
    new_val = ""
    for i in range(0, 36):
        if mask[i] == 'X':
            new_val = new_val + b_val[i]
        else:
            new_val = new_val + mask[i]
    return int(new_val, 2)


def mask_address(address, mask):
    b_val = '{:0>36b}'.format(address)
    new_vals = [""]
    for i in range(0, 36):
        if mask[i] == '0':
            for n in range(0, len(new_vals)):
                new_vals[n] = new_vals[n] + b_val[i]
        elif mask[i] == '1':
            for n in range(0, len(new_vals)):
                new_vals[n] = new_vals[n] + '1'
        else:
            for n in range(0, len(new_vals)):
                new_vals.append(new_vals[n] + '1')
                new_vals[n] = new_vals[n] + '0'
    return [int(new_val, 2) for new_val in new_vals]


def main():
    instructions = read_lines("../resources/aoc2020/input14")

    # part 1
    memory, mask = {}, None
    for inst in instructions:
        if inst.startswith("mask"):
            mask = inst[7:]
        else:
            address = int(inst[inst.index('[') + 1:inst.index(']')])
            value = mask_value(int(inst[inst.index('=') + 2:]), mask)
            memory.update({address: value})

    result1 = sum(memory.values())
    print("result1 =", result1)  # 9967721333886

    # part 2
    memory, mask = {}, None
    for inst in instructions:
        if inst.startswith("mask"):
            mask = inst[7:]
        else:
            address = int(inst[inst.index('[') + 1:inst.index(']')])
            value = int(inst[inst.index('=') + 2:])
            for a in mask_address(address, mask):
                memory.update({a: value})

    result2 = sum(memory.values())
    print("result2 =", result2)  # 4355897790573


if __name__ == '__main__':
    main()
