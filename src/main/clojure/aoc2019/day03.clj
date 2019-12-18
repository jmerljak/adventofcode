(ns aoc2019.day03
  (:require [utils]
            [clojure.set :as set]
            [clojure.string :as str]))

; data
(def data (utils/get-lines "input03"))
(def steps1 (vec (str/split (data 0) #",")))
(def steps2 (vec (str/split (data 1) #",")))

; Manhattan distance
(defn dist
  ; from central point
  ([xy]
   (+
     (Math/abs (xy 0))
     (Math/abs (xy 1))
     ))
  ; between two points
  ([xy1 xy2]
   (+
     (Math/abs (- (xy1 0) (xy2 0)))
     (Math/abs (- (xy1 1) (xy2 1)))
     ))
  )

; paths
(defn h-path [x-range y]
  (set (for [x x-range] [x y])))

(defn v-path [x y-range]
  (set (for [y y-range] [x y])))

(defn get-path [xy direction length]
  (case direction
    "L" (h-path (range (- (xy 0) length) (xy 0)) (xy 1))
    "R" (h-path (range (inc (xy 0)) (+ (inc (xy 0)) length)) (xy 1)) ; note exclusive range
    "U" (v-path (xy 0) (range (inc (xy 1)) (+ (inc (xy 1)) length))) ; note exclusive range
    "D" (v-path (xy 0) (range (- (xy 1) length) (xy 1)))
    (throw (Exception. (str "Illegal direction: " direction))))
  )

; move
(defn move [xy direction length]
  (case direction
    "L" [(- (xy 0) length) (xy 1)]
    "R" [(+ (xy 0) length) (xy 1)]
    "U" [(xy 0) (+ (xy 1) length)]
    "D" [(xy 0) (- (xy 1) length)]
    (throw (Exception. (str "Illegal direction: " direction))))
  )

; follow steps
(defn follow-steps [steps]
  (def xy [0 0])
  (map
    #(let [direction (subs % 0 1)
           length (read-string (subs % 1))]
       (def path (get-path xy direction length))
       (def xy (move xy direction length))
       path
       )
    steps
    )
  )

(def area1 (reduce set/union (follow-steps steps1)))
(def area2 (reduce set/union (follow-steps steps2)))
(def intersections (set/intersection area1 area2))

; part 1
(def min-dist (apply min (map dist intersections)))
(println "min distance =" min-dist)                         ; 386

; part 2
(defn required-steps [steps intersection]
  (def xy [0 0])
  (def n 0)
  (loop [i 0]
    (let [direction (subs (steps i) 0 1)
          length (read-string (subs (steps i) 1))]
      (def path (get-path xy direction length))
      (if (contains? path intersection)
        (+ n (dist xy intersection))
        (do
          (def n (+ n length))
          (def xy (move xy direction length))
          (recur (inc i))
          )
        )
      )
    )
  )

(def combined-steps
  (map
    #(+ (required-steps steps1 %) (required-steps steps2 %))
    intersections))

(def min-steps (apply min combined-steps))
(println "combined steps =" min-steps)                      ;6484
