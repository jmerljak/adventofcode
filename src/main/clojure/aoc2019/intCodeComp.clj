(ns aoc2019.intCodeComp
  (:require [utils]
            [clojure.string :as str]))

; loads program
(defn load-program [file-name]
  (vec (map read-string (str/split (utils/get-line file-name) #","))))

; resolve value based on parameter mode
(defn resolve-value [state mode param relative-base]
  (case mode
    0 (state param)                                         ; position
    1 param                                                 ; immediate
    2 (state (+ relative-base param))                       ; relative                                                ; relative
    (throw (Exception. (str "Illegal mode: " mode))))
  )

; resolve write address based on parameter mode
(defn resolve-write-address [mode param relative-base]
  (case mode
    0 param                                                 ; position
    2 (+ relative-base param)                               ; relative                                                 ; relative
    (throw (Exception. (str "Illegal mode: " mode))))
  )

; determine whether instruction pointer should increase
(defn resolve-pointer [current next param-count]
  (if
    (= current next)
    current
    (+ current param-count))
  )

(defn run
  ([program]
   (run program [] 0 false 0))
  ([program input]
   (run program input 0 false 0))
  ([program input pointer pause-on-output]
   (run program input pointer pause-on-output 0))
  ([program input pointer pause-on-output address-offset]
   (def state program)
   (def inputVals input)
   (def output [])
   (def relative-base address-offset)
   (loop [i pointer]
     (let [instruction (state i)]

       (def opcode (mod instruction 100))
       (def mode-p1 (mod (quot instruction 100) 10))
       (def mode-p2 (mod (quot instruction 1000) 10))
       (def mode-p3 (mod (quot instruction 10000) 10))

       (case opcode
         1 (let [in1 (resolve-value state mode-p1 (state (+ i 1)) relative-base)
                 in2 (resolve-value state mode-p2 (state (+ i 2)) relative-base)
                 out (resolve-write-address mode-p3 (state (+ i 3)) relative-base)]

             (def state (assoc state out (+ in1 in2)))
             (recur (resolve-pointer i out 4)))

         2 (let [in1 (resolve-value state mode-p1 (state (+ i 1)) relative-base)
                 in2 (resolve-value state mode-p2 (state (+ i 2)) relative-base)
                 out (resolve-write-address mode-p3 (state (+ i 3)) relative-base)]

             (def state (assoc state out (* in1 in2)))
             (recur (resolve-pointer i out 4)))

         3 (let [out (resolve-write-address mode-p1 (state (+ i 1)) relative-base)
                 value (first inputVals)]                   ; read value from input

             (def inputVals (rest inputVals))
             (def state (assoc state out value))
             (recur (resolve-pointer i out 2)))

         4 (let [in1 (resolve-value state mode-p1 (state (+ i 1)) relative-base)]

             (def output (conj output in1))                 ; write to output
             (if pause-on-output
               [state output (+ i 2) relative-base]         ; return
               (recur (+ i 2))                              ; continue
               )
             )

         5 (let [in1 (resolve-value state mode-p1 (state (+ i 1)) relative-base)
                 in2 (resolve-value state mode-p2 (state (+ i 2)) relative-base)]

             (if
               (zero? in1)
               (recur (+ i 3))
               (recur in2)))

         6 (let [in1 (resolve-value state mode-p1 (state (+ i 1)) relative-base)
                 in2 (resolve-value state mode-p2 (state (+ i 2)) relative-base)]

             (if
               (zero? in1)
               (recur in2)
               (recur (+ i 3))))

         7 (let [in1 (resolve-value state mode-p1 (state (+ i 1)) relative-base)
                 in2 (resolve-value state mode-p2 (state (+ i 2)) relative-base)
                 out (resolve-write-address mode-p3 (state (+ i 3)) relative-base)]

             (def state (assoc state out (if (< in1 in2) 1 0)))
             (recur (resolve-pointer i out 4)))

         8 (let [in1 (resolve-value state mode-p1 (state (+ i 1)) relative-base)
                 in2 (resolve-value state mode-p2 (state (+ i 2)) relative-base)
                 out (resolve-write-address mode-p3 (state (+ i 3)) relative-base)]

             (def state (assoc state out (if (= in1 in2) 1 0)))
             (recur (resolve-pointer i out 4)))

         9 (let [in1 (resolve-value state mode-p1 (state (+ i 1)) relative-base)]

             (def relative-base (+ relative-base in1))
             (recur (+ i 2)))

         99 [state output nil nil]

         (throw (Exception. (str "Illegal opcode: " opcode))))
       )
     ))
  )

(defn run-get-state [program]
  ((run program) 0))

(defn run-get-output [program input]
  (last ((run program input) 1)))