; tests assuring changes to intCodeComp are backward compatible
(ns aoc2019.intCodeCompTest
  (:require [clojure.test :refer :all]
            [aoc2019.day02 :as day02]
            [aoc2019.day05 :as day05]
            [aoc2019.day07 :as day07]
            [aoc2019.day09 :as day09]))

(assert (= (day02/part1) 6327510))
(assert (= (day02/part2) 4112))

(assert (= (day05/part1) 5182797))
(assert (= (day05/part2) 12077198))

(assert (= (day07/part1) 45730))
(assert (= (day07/part2) 5406484))

(assert (= (day09/part1) 2427443564))
(assert (= (day09/part2) 87221))