(ns clojure-brave-and-true.chapter10.core
  (:require [clojure-brave-and-true.chapter10.quotes :as quotes]
            [clojure-brave-and-true.chapter10.heal :as heal]))

(defn main []
  (do 
    (heal/give-potion heal/player-1 heal/player-2)
    (heal/use-potion heal/player-1)
    {:word-from-quotes (quotes/quote-word-count 5)
     :heal-friend {:char1 @heal/player-1 :char2 @heal/player-2}}))
