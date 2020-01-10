(ns aoc2019.day07
  (:require [utils]
            [aoc2019.intCodeComp :as comp]))

; data
(def program (comp/load-program "input07"))

; https://stackoverflow.com/a/26076537
(defn permutations [colls]
  (if (= 1 (count colls))
    (list colls)
    (for [head colls
          tail (permutations (disj (set colls) head))]
      (cons head tail))))

; part 1
(defn amplify [phases]
  (def output 0)
  (loop [i 0]
    (if
      (= i 5)
      output
      (do
        (def output (comp/run-get-output program [(nth phases i) output]))
        (recur (inc i))
        )
      )
    )
  )

(defn part1 []
  (apply max (map amplify (permutations (range 0 5)))))

(println "highest output 1 =" (part1))                      ; 45730

; part 2
(defn amplify-loop [phases]
  (def states [[program 0] [program 0] [program 0] [program 0] [program 0]]) ; [state pointer]
  (def first-pass true)
  (def output 0)

  (loop [i 0]
    (if
      first-pass
      (def inputVals [(nth phases i) output])
      (def inputVals [output])
      )

    (def response (comp/run ((states i) 0) inputVals ((states i) 1) true))
    (def pointer (response 2))

    (if
      (nil? pointer)
      output
      (do
        (def state (response 0))
        (def states (apply conj (conj (subvec states 0 i) [state pointer]) (subvec states (inc i))))
        (def output (last (response 1)))

        (if
          (< i 4)
          (recur (inc i))
          (do
            (def first-pass false)
            (recur 0)
            )
          )
        )
      )
    )
  )

(defn part2 []
  (apply max (map amplify-loop (permutations (range 5 10)))))

(println "highest output 2 =" (part2))                      ; 5406484