(ns aoc2019.day02
  (:require [utils]
            [aoc2019.intCodeComp :as comp]
            [clojure.string :as str]))

; data
(def line (utils/get-line "input02"))
(def input (vec (map read-string (str/split line #","))))
;(println "input =" input)

; restore state
(defn restore-state [v1 v2]
  (assoc (assoc input 1 v1) 2 v2))

; part 1
(def state1 (restore-state 12 2))
(println "result 1 =" ((comp/run-get-state state1) 0))      ; 6327510

; part 2
(def result2
  (some
    #(if
       (= ((comp/run-get-state (restore-state (% 0) (% 1))) 0) 19690720)
       (+ (* 100 (% 0)) (% 1)))
    (for [v1 (range 100)
          v2 (range 100)]
      [v1 v2]))
  )

(println "result 2 =" result2)                              ; 4112

