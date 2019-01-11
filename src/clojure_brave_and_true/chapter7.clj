(ns clojure-brave-and-true.chapter7)

(defn printme []
  (eval (list (read-string "println") "Rafael" " - " "Blade Runner")))

(defmacro infix-div
  [list]
    (reduce
      (fn [acum next]
        (if (= '/ (last acum))
            (conj (pop (pop acum)) (/ (last (pop acum)) next))
            (conj acum next)))
      []
      list))

(defmacro infix-mult
  [list]
    (reduce
      (fn [acum next]
        (if (= '* (last acum))
            (conj (pop (pop acum)) (* (last (pop acum)) next))
            (conj acum next)))
      []
      list))
