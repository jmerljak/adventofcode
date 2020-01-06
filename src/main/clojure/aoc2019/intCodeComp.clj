(ns aoc2019.intCodeComp)

; resolve value based on parameter mode
(defn resolve-value [state mode param]
  (case mode
    0 (state param)
    1 param
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
   (run program []))
  ([program, input]
   (def state program)
   (def inputVals input)
   (def output nil)
   (loop [i 0]
     (let [instruction (state i)]

       (def opcode (mod instruction 100))
       (def mode-p1 (mod (quot instruction 100) 10))
       (def mode-p2 (mod (quot instruction 1000) 10))
       ;(def mode-p3 (mod (quot instruction 10000) 10))

       (case opcode
         1 (let [in1 (resolve-value state mode-p1 (state (+ i 1)))
                 in2 (resolve-value state mode-p2 (state (+ i 2)))
                 out (state (+ i 3))]

             (def state (assoc state out (+ in1 in2)))
             (recur (resolve-pointer i out 4)))

         2 (let [in1 (resolve-value state mode-p1 (state (+ i 1)))
                 in2 (resolve-value state mode-p2 (state (+ i 2)))
                 out (state (+ i 3))]

             (def state (assoc state out (* in1 in2)))
             (recur (resolve-pointer i out 4)))

         3 (let [out (state (+ i 1))
                 value (first inputVals)]                   ; read value from input

             (def inputVals (rest inputVals))
             (def state (assoc state out value))
             (recur (resolve-pointer i out 2)))

         4 (let [in1 (resolve-value state mode-p1 (state (+ i 1)))]

             (def output in1)                               ; write to output
             (recur (+ i 2)))

         5 (let [in1 (resolve-value state mode-p1 (state (+ i 1)))
                 in2 (resolve-value state mode-p2 (state (+ i 2)))]

             (if
               (zero? in1)
               (recur (+ i 3))
               (recur in2)))

         6 (let [in1 (resolve-value state mode-p1 (state (+ i 1)))
                 in2 (resolve-value state mode-p2 (state (+ i 2)))]

             (if
               (zero? in1)
               (recur in2)
               (recur (+ i 3))))

         7 (let [in1 (resolve-value state mode-p1 (state (+ i 1)))
                 in2 (resolve-value state mode-p2 (state (+ i 2)))
                 out (state (+ i 3))]

             (def state (assoc state out (if (< in1 in2) 1 0)))
             (recur (resolve-pointer i out 4)))

         8 (let [in1 (resolve-value state mode-p1 (state (+ i 1)))
                 in2 (resolve-value state mode-p2 (state (+ i 2)))
                 out (state (+ i 3))]

             (def state (assoc state out (if (= in1 in2) 1 0)))
             (recur (resolve-pointer i out 4)))

         99 [state output]

         (throw (Exception. (str "Illegal opcode: " opcode))))
       )
     ))
  )

(defn run-get-state [program]
  ((run program) 0))

(defn run-get-output [program input]
  ((run program input) 1))