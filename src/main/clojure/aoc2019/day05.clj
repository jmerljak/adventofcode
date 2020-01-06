(ns aoc2019.day05
  (:require [utils]
            [aoc2019.intCodeComp :as comp]
            [clojure.string :as str]))

; data
(def line (utils/get-line "input05"))
(def program (vec (map read-string (str/split line #","))))

; part 1
(def code1 (comp/run-get-output program [1]))
(println "diagnostic code 1 =" code1)                       ; 5182797

; part 2
(def code2 (comp/run-get-output program [5]))
(println "diagnostic code 2 =" code2)                       ; 12077198