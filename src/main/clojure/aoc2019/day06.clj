(ns aoc2019.day06
  (:require [utils]
            [clojure.set :as set]
            [clojure.string :as str]))

; data
(def data (map #(str/split % #"\)") (utils/get-lines "input06")))
(def direct-orbits (into (hash-map) (map #(vec (reverse %)) data)))

; count direct and indirect orbits
(defn count-orbits [current]
  (let [next (direct-orbits current)]
    (if
      (= next "COM")
      1
      (inc (count-orbits next))
      )
    )
  )

; part 1
(def total-orbits (reduce + (map count-orbits (keys direct-orbits))))
(println "total orbits =" total-orbits)                     ; 402879

; part 2
(defn path-to-com [current]
  (let [next (direct-orbits current)]
    (if
      (= next "COM")
      #{next}
      (conj (path-to-com next) next)
      )
    )
  )

(def path1 (path-to-com "SAN"))
(def path2 (path-to-com "YOU"))
(def transfers (+
                 (count (set/difference path1 path2))
                 (count (set/difference path2 path1))
                 ))
(println "required transfers =" transfers)                  ; 484
