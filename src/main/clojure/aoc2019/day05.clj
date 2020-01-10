(ns aoc2019.day05
  (:require [utils]
            [aoc2019.intCodeComp :as comp]))

; data
(def program (comp/load-program "input05"))

; part 1
(defn part1 []
  (comp/run-get-output program [1]))

(println "diagnostic code 1 =" (part1))                     ; 5182797

; part 2
(defn part2 []
  (comp/run-get-output program [5]))

(println "diagnostic code 2 =" (part2))                     ; 12077198