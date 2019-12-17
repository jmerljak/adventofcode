(ns aoc2019.day02
  (:require [utils]
            [clojure.string :as str]))

; data
(def line (utils/get-line "input02"))
(def input (vec (map read-string (str/split line #","))))
;(println "input =" input)

; restore state
(defn restore-state [v1 v2]
  (assoc (assoc input 1 v1) 2 v2))

; process
(defn process [in]
  (def state in)
  (loop [i 0]
    (let [opcode (state i)]
      (case opcode
        1 (let [in1 (state (+ i 1))
                in2 (state (+ i 2))
                out (state (+ i 3))]

            (def state (assoc state out (+ (state in1) (state in2))))
            (recur (+ i 4)))

        2 (let [in1 (state (+ i 1))
                in2 (state (+ i 2))
                out (state (+ i 3))]

            (def state (assoc state out (* (state in1) (state in2))))
            (recur (+ i 4)))

        99 (state 0)

        (throw (Exception. (str "Illegal opcode: " opcode))))
      )
    )
  )

; part 1
(def state1 (restore-state 12 2))
(println "result 1 =" (process state1))                     ; 6327510

; part 2
(def result2
  (some
    #(if
       (= (process (restore-state (% 0) (% 1))) 19690720)
       (+ (* 100 (% 0)) (% 1)))
    (for [v1 (range 100)
          v2 (range 100)]
      [v1 v2]))
  )

(println "result 2 =" result2)                              ; 4112

