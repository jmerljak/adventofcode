import re

from util import read_lines


def is_valid(passport: str) -> bool:
    return (passport.find("byr:") != -1) & \
           (passport.find("iyr:") != -1) & \
           (passport.find("eyr:") != -1) & \
           (passport.find("hgt:") != -1) & \
           (passport.find("hcl:") != -1) & \
           (passport.find("ecl:") != -1) & \
           (passport.find("pid:") != -1)


def number_between(passport: str, prefix: str, digits: int, low: int, high: int, suffix: str = "") -> bool:
    pattern = prefix + "(\\d{" + str(digits) + "})" + suffix
    match = re.search(pattern, passport)
    if match is not None:
        return low <= int(match[1]) <= high
    return False


def is_valid_strict(passport: str) -> bool:
    return (number_between(passport, "byr:", 4, 1920, 2002)) & \
           (number_between(passport, "iyr:", 4, 2010, 2020)) & \
           (number_between(passport, "eyr:", 4, 2020, 2030)) & \
           (number_between(passport, "hgt:", 3, 150, 193, "cm") | number_between(passport, "hgt:", 2, 59, 76, "in")) & \
           (re.search("hcl:#[0-9a-f]{6}", passport) is not None) & \
           (re.search("ecl:(amb|blu|brn|gry|grn|hzl|oth)", passport) is not None) & \
           (re.search("pid:[0-9]{9}\\s", passport + " ") is not None)


def main():
    data = read_lines("../resources/aoc2020/input04")
    passports = " ".join(data).split("  ")

    # part 1
    result1 = len(list(filter(is_valid, passports)))
    print("result1 =", result1)  # 233

    # part 2
    filtered = list(filter(is_valid_strict, passports))
    result2 = len(filtered)
    print("result2 =", result2)  # 111


if __name__ == '__main__':
    main()
