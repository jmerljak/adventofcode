(ns aoc2019.day05
  (:require [utils]
            [aoc2019.intCodeComp :as comp]))

; data
(def program (comp/load-program "input05"))

; part 1
(def code1 (comp/run-get-output program [1]))
(println "diagnostic code 1 =" code1)                       ; 5182797

; part 2
(def code2 (comp/run-get-output program [5]))
(println "diagnostic code 2 =" code2)                       ; 12077198