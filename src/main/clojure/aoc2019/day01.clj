(ns aoc2019.day01
  (:require utils))

; data
(def masses (map read-string (utils/get-lines "input01")))

; fuel estimation
(defn fuel-estimation [mass]
  (- (quot mass 3) 2))

; part 1
(def fuel-per-module (map fuel-estimation masses))
(def requirement1 (reduce + fuel-per-module))

(println "requirement1 =" requirement1)                     ;3331523

; part 2
(defn additional-fuel-required [fuel-mass]
  (let [additional-fuel (fuel-estimation fuel-mass)]
    (if (> additional-fuel 0)
      (+ additional-fuel (additional-fuel-required additional-fuel))
      0)
    ))

(def additional-fuel-per-module (map additional-fuel-required fuel-per-module))
(def requirement2 (reduce + additional-fuel-per-module))
(def total (+ requirement1 requirement2))

(println "total =" total)                                   ; 4994396