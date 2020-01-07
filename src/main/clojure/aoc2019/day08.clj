(ns aoc2019.day08
  (:require [utils]))

; data
(def data (utils/get-line "input08"))

(def width 25)
(def height 6)
(def layer-size (* width height))
(def layers (re-seq (re-pattern (str ".{1," layer-size "}")) data))

(defn count-chars [char-seq char]
  (count (re-seq (re-pattern char) char-seq)))

; part 1
(def sorted-layers
  (sort-by #(count-chars % "0") layers))

(def checksum-layer (first sorted-layers))
(def checksum (* (count-chars checksum-layer "1") (count-chars checksum-layer "2")))
(println "checksum =" checksum)                             ; 1920

; part 2
(defn resolve-color [pos]
  (loop [layer-num 0]
    (case (nth (nth layers layer-num) pos)
      \0 " "
      \1 "#"
      \2 (recur (inc layer-num)))
    )
  )

(def rendered "")
(loop [pos 0]
  (if (< pos layer-size)
    (do
      (def rendered (str rendered (resolve-color pos)))
      (recur (inc pos)))))

(println "message =")
(dotimes [h height]
  (println (subs rendered (* h width) (* (inc h) width))))  ; PCULA