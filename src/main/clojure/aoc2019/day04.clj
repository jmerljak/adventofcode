(ns aoc2019.day04)

(def candidates (range 307237 (inc 769058)))

(def regex-2-digits #"([0-9])\1")
(def regex-3-digits #"([0-9])\1\1")

; https://stackoverflow.com/a/29942388
(defn to-digits [n]
  (if (pos? n)
    (conj (to-digits (quot n 10)) (mod n 10))
    []))

(defn never-decreases [digits]
  (loop [i 1]
    (if
      (= i (count digits))
      true
      (if
        (< (digits i) (digits (- i 1)))
        false
        (recur (inc i))
        )
      )
    )
  )

(defn matching-digits [regex n]
  (map #(get % 1) (re-seq regex (str n))))

; part 1
(def passwords1
  (filter
    #(and
       (never-decreases (to-digits %))
       (not-empty (matching-digits regex-2-digits %))
       )
    candidates
    )
  )

(println "passwords1 =" (count passwords1))                 ; 889

; part 2
(def passwords2
  (filter
    #(and
       (never-decreases (to-digits %))
       (not-empty
         (apply disj
                (set (matching-digits regex-2-digits %))
                (set (matching-digits regex-3-digits %))
                )
         )
       )
    candidates
    )
  )

(println "passwords2 =" (count passwords2))                 ; 589