(ns utils)

; reads file line by line
(defn get-lines [name]
  (with-open [rdr (clojure.java.io/reader (str "../../resources/aoc2019/" name))]
    (reduce conj [] (line-seq rdr))))

; reads first line of file
(defn get-line [name]
  ((get-lines name) 0))