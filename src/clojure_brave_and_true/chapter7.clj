(ns clojure-brave-and-true.chapter7)

(defn printme []
  (eval (list (read-string "println") "Rafael" " - " "Blade Runner")))

(defn do-math
  [operator acum next]
  (if (= operator (last acum))
     (conj (pop (pop acum)) ((eval operator) (last (pop acum)) next))
     (conj acum next)))

(def do-divs (partial do-math '/))
(def do-muls (partial do-math '*))
(def do-adds (partial do-math '+))
(def do-subs (partial do-math '-))

(defmacro infix-all
  [list]
  (->> list
    (reduce do-divs [])
    (reduce do-muls [])
    (reduce do-adds [])
    (reduce do-subs [])
    (last)))

(defn main []
  ['(1 + 1 / 3 - 3) "=" (infix-all [1 + 1 / 3 - 3])])
