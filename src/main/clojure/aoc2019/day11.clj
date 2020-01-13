(ns aoc2019.day11
  (:require [aoc2019.intCodeComp :as comp]))

; data
(def program (comp/load-program "input11"))

(def orientations [:up :right :down :left])

(defn move [position orientation-id]
  (case (orientations orientation-id)
    :up [(position 0) (dec (position 1))]
    :right [(inc (position 0)) (position 1)]
    :down [(position 0) (inc (position 1))]
    :left [(dec (position 0)) (position 1)]
    (throw (Exception. (str "Illegal orientation-id: " orientation-id)))
    )
  )

(defn paint [initial-color]
  (def state (apply conj program (repeat 500 0)))
  (def address-offset 0)
  (def position [0 0])
  (def panels {position initial-color})
  (def stage :paint)
  (def orientation-id 0)

  (loop [pointer 0]
    (def color (get panels position 0))
    (def response (comp/run state [color] pointer true address-offset))

    (def state (response 0))
    (def output (last (response 1)))
    (def address-offset (response 3))

    (case stage
      :paint (do
               (def panels (assoc panels position output))
               (def stage :rotate))

      :rotate (do
                (def orientation-id
                  (case output
                    0 (mod (dec orientation-id) 4)          ; turn left
                    1 (mod (inc orientation-id) 4)          ; turn right
                    (throw (Exception. (str "Illegal turn: " output)))
                    ))
                (def position (move position orientation-id))
                (def stage :paint))

      (throw (Exception. (str "Illegal stage: " stage))))

    (def pntr (response 2))
    (if
      (nil? pntr)
      panels
      (recur pntr))
    )
  )

; part 1
(defn part1 []
  (count (keys (paint 0))))

(println "painted panels =" (part1))                        ; 2293

; part 2
(def registration (paint 1))
(def width (inc (apply max (map #(first (first %)) registration))))
(def height (inc (apply max (map #(last (first %)) registration))))

(println "registration =")                                  ; AHLCPRAL
(dotimes [h height]
  (dotimes [w width]
    (if (= (registration [w h]) 1)
      (print "#")
      (print " ")
      )
    )
  (println)
  )
