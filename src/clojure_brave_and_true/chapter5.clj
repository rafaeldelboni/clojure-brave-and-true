(ns clojure-brave-and-true.chapter5)

(def character
  {:name "Smooches McCutes"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})

(def c-int (comp :intelligence :attributes))

(def c-str (comp :strength :attributes))

(def c-dex (comp :dexterity :attributes))

(def attr (comp (character :attributes)))

(defn n-int [char lvl] (update-in char [:attributes :intelligence] + lvl))

(defn n-str [char lvl] (update-in char [:attributes :strength] + lvl))

(defn n-dex [char lvl] (update-in char [:attributes :dexterity] + lvl))

(defn my-comp [& all-fns]
  (reduce
    (fn [acc-fn curr-fn]
      (fn [& args]
        (acc-fn (apply curr-fn args))))
    identity
    all-fns))

(defn my-assoc-in
  [map [key & keys] values]
  (if keys
    (assoc map key (my-assoc-in (get map key) keys values))
    (assoc map key values)))

(defn my-update-in
  ([map keys func & args]
   (let [up (fn up [map keys func args]
              (let [[key & keys] keys]
                (if keys
                  (assoc map key (up (get map key) keys func args))
                  (assoc map key (apply func (get map key) args)))))]
     (up map keys func args))))

(defn main []
  {:current
   {:int (c-int character) :dex (c-dex character) :str (c-str character)}
   :next
   {
    :int (c-int (n-int character 3))
    :dex (c-dex (n-dex character 1))
    :str (c-str (n-str character 2))}})
