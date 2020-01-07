(ns aoc2019.day09
  (:require [utils]
            [aoc2019.intCodeComp :as comp]
            [clojure.string :as str]))

; data
(def line (utils/get-line "input09"))
(def program (vec (map read-string (str/split line #","))))

(defn extend-memory [prog n]
  (apply conj prog (repeat n 0)))

; part 1
(def keycode (comp/run-get-output (extend-memory program 100) [1]))
(println "BOOST keycode =" keycode)                         ; 2427443564

; part 1
(def coordinates (comp/run-get-output (extend-memory program 110) [2]))
(println "coordinates =" coordinates)                       ; 87221