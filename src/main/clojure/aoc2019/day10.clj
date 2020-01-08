(ns aoc2019.day10
  (:require [utils]))

; data
(def data (utils/get-lines "input10"))
(def map-width (count (data 0)))
(def map-height (count data))
(def asteroids
  (set (filter
         #(= (nth (data (last %)) (first %)) \#)
         (for [x (range map-width)
               y (range map-height)]
           [x y]))))

; https://stackoverflow.com/a/17693189
(defn on-line-between [a b c]
  (let [x1 (first a)
        y1 (last a)
        x2 (first b)
        y2 (last b)
        x (first c)
        y (last c)]
    (and
      ; on line
      (=
        (* (- x2 x1) (- y y1))
        (* (- x x1) (- y2 y1))
        )
      ; between points
      (and
        (or (<= x1 x x2) (>= x1 x x2))
        (or (<= y1 y y2) (>= y1 y y2))
        )
      )
    )
  )

(defn visible [a b]
  (not
    (some
      #(on-line-between a b %)
      (disj asteroids a b))))

; part 1
(def detected-from-position
  (sort-by
    #(count (last %))
    (for [a asteroids]
      [a
       (filter
         #(visible a %)
         (disj asteroids a))
       ]
      )
    )
  )

(def best-match (last detected-from-position))
(def station (first best-match))
(def detected-asteroids (last best-match))
(println "max detected asteroids =" (count detected-asteroids)) ; 280

; part 2
(defn zenith-angle
  [a b]
  (let [dx (- (first b) (first a))
        dy (- (last a) (last b))]                           ; note: y axis is inverted
    (cond
      (zero? dx) (if (pos? dy) 0.0 180.0)
      (zero? dy) (if (pos? dx) 90.0 270.0)
      :else (do
              (def angle (Math/toDegrees (Math/atan2 dy dx))) ; calculate angle
              (- 360 (mod (- angle 90) 360)))               ; rotate and invert to clockwise
      )
    )
  )

(def asteroids-by-angle
  (sort-by
    #(zenith-angle station %)
    detected-asteroids
    ))

(def asteroid-200 (nth asteroids-by-angle 199))
(println "result =" (+ (* (first asteroid-200) 100) (last asteroid-200))) ; 706