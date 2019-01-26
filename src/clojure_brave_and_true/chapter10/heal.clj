(ns clojure-brave-and-true.chapter10.heal)

(def player-1
  (ref {:name "Wolfpuddle"
        :max-health 40
        :cur-health 15
        :potions 0}))

(def player-2
  (ref {:name "Gingerback"
        :max-health 40
        :cur-health 40
        :potions 1}))

(defn give-potion
  [receiver giver]
  (dosync
   (when (>= (:potions @giver) 1)
     (alter receiver update-in [:potions] inc)
     (alter giver update-in [:potions] dec))))

(defn use-potion
  [player]
  (dosync
   (when (>= (:potions @player) 1)
     (alter player update-in [:potions] dec)
     (alter player assoc-in [:cur-health] (@player :max-health)))))
