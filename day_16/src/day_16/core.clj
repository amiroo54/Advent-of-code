(ns day-16.core
  (:gen-class))

(require '[clojure.java.io :as io])

(def grid (atom []))
(def enegizedGrid (atom []))
(def highest (atom 0))
(defn getNextDirection [pos direction]
  (case direction
    "u" [(-> pos first (- 1)) (-> pos last)]
    "r" [(-> pos first) (-> pos last (+ 1))]
    "d" [(-> pos first (+ 1)) (-> pos last)]
    "l" [(-> pos first) (-> pos last (- 1))]))

(defn getBackSlashMirrorDir [direction]
  (case direction
    "u" "l"
    "l" "u"
    "r" "d"
    "d" "r"))

(defn getSlashMirrorDir [direction]
  (case direction
    "u" "r"
    "r" "u"
    "l" "d"
    "d" "l"))

(defn goNext [pos direction] 
  (let [currentCell (nth @(nth @enegizedGrid (first pos)) (second pos))]
    (case currentCell
      \v (println "hit a wall")
      (reset! (nth @enegizedGrid (first pos)) (assoc @(nth @enegizedGrid (first pos)) (second pos) (+ currentCell 1)))))

  (case (nth @(nth @grid (first pos)) (second pos))
    \/ (goNext (getNextDirection pos (getSlashMirrorDir direction)) (getSlashMirrorDir direction))
    \\ (goNext (getNextDirection pos (getBackSlashMirrorDir direction)) (getBackSlashMirrorDir direction))
    \- (case direction
         ("r" "l") (goNext (getNextDirection pos direction) direction)
         ("d" "u") (if (= (nth @(nth @enegizedGrid (first pos)) (second pos)) 1) 
                     (do (goNext (getNextDirection pos "r") "r") 
                         (goNext (getNextDirection pos "l") "l")) ()))
    \| (case direction
         ("d" "u") (goNext (getNextDirection pos direction) direction)
         ("r" "l") (if (= (nth @(nth @enegizedGrid (first pos)) (second pos)) 1) 
                     (do (goNext (getNextDirection pos "u") "u") 
                         (goNext (getNextDirection pos "d") "d")) ()))
    \. (goNext (getNextDirection pos direction) direction)
    \v ()))

(defn -main [& args]
  (let [path (first args)]
    (with-open [rdr (io/reader path)]
      (doseq [line (line-seq rdr)]

        (if (= (first @grid) nil)
          (let [firstline (atom [])]
            (swap! firstline conj \.)
            (doseq [char (seq line)]
              (swap! firstline conj \v))
            (swap! grid conj firstline)
            (swap! enegizedGrid conj firstline)))

        (let [row (atom [])] ;;main grid.
          (swap! row conj \v)
          (doseq [char (seq line)]
            (swap! row conj char))
          (swap! row conj \v)

          (swap! grid conj row))

        (let [row (atom [])] ;;enegized grid.
          (swap! row conj \v)
          (doseq [char (seq line)]
            (swap! row conj 0))
          (swap! row conj \v)
          (swap! enegizedGrid conj row))))

    (swap! grid conj (first @grid))
    (swap! enegizedGrid conj (first @enegizedGrid)))
  ;;(try (goNext [1 1] "r") (catch Throwable t (println "failed miserably.")))

  (doseq [row @grid] )

  (let [total (atom 0)]
    (doseq [row @enegizedGrid]
      (doseq [col @row]
        (if (or (= col \v) (= col \.)) ()
            (if (> col 0) (swap! total inc) ()))))
    (println @total)))
  