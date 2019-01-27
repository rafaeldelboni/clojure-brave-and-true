(ns clojure-brave-and-true.chapter13)

(defmulti full-moon-behavior (fn [were-creature] (:were-type were-creature)))

(defmethod full-moon-behavior :wolf
  [were-creature]
  (str (:name were-creature) " will howl and murder"))

(defmethod full-moon-behavior :unicorn
  [were-creature]
  (str (:name were-creature) " will evoke cute reactions and make people puke"))

(defprotocol WereCreature
  (pro-full-moon-behavior [x]))

(defrecord WereWolf [name title]
  WereCreature
  (pro-full-moon-behavior [x]
    (str name " will howl and murder")))

(defrecord WereSimmons [name title]
  WereCreature
  (pro-full-moon-behavior [x]
    (str name " will encourage people and sweat to the oldies")))

(defprotocol Psychodynamics
  "Plumb the inner depths of your data types"
  (thoughts [x] "The data type's innermost thoughts"))

(extend-protocol Psychodynamics
  WereWolf
  (thoughts [x] "Brawwwwm, shuonm! Graaaarrr?")

  WereSimmons
  (thoughts [x] "I really believe on you, mah."))

(defmulti cast-spell (fn [magician] (:school magician)))

(defmethod cast-spell :fire
  [magician]
  (str (:name magician) " casts a star fire, awaken and deliver your judgment!"))

(defmethod cast-spell :ice
  [magician]
  (str (:name magician) " casts a freezing wind, speak of forgotten truths!"))

(defn main []
  {:full-moon-behavior
   (full-moon-behavior {:were-type :wolf :name "Rachel from next door"})
   :pro-full-moon-behavior
   (pro-full-moon-behavior
     (map->WereSimmons {:name "Rafael" :title "Newbie of Everything"}))
   :psychodynamics
   (thoughts
     (map->WereWolf {:name "Lucian" :title "CEO of Melodrama"}))
   :mages
   (cast-spell {:school :fire
                :name "Ogien"})})

