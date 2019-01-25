(ns clojure-brave-and-true.chapter10.atomic)

(def atomic-number (atom 0))

(defn atomic-incrementer
  [atomic-state increase-by]
  (swap! atomic-state + increase-by))
