(ns aoc2019.day09
  (:require [utils]
            [aoc2019.intCodeComp :as comp]))

; data
(def program (comp/load-program "input09"))

(defn extend-memory [prog n]
  (apply conj prog (repeat n 0)))

; part 1
(defn part1 []
  (comp/run-get-output (extend-memory program 100) [1]))

(println "BOOST keycode =" (part1))                         ; 2427443564

; part 1
(defn part2 []
  (comp/run-get-output (extend-memory program 110) [2]))

(println "coordinates =" (part2))                           ; 87221