(ns clojure-brave-and-true.chapter3)

(def asym-body-parts [{:name "head" :size 3}
                             {:name "symmetric-eye" :size 1}
                             {:name "symmetric-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "symmetric-shoulder" :size 3}
                             {:name "symmetric-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "symmetric-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "symmetric-kidney" :size 1}
                             {:name "symmetric-hand" :size 2}
                             {:name "symmetric-knee" :size 2}
                             {:name "symmetric-thigh" :size 4}
                             {:name "symmetric-lower-leg" :size 3}
                             {:name "symmetric-achilles" :size 1}
                             {:name "symmetric-foot" :size 2}])

(defn middle-point
  "Returns the middle integer point for odd and the quotient for even"
  [num]
  (let [quotient (quot num 2)]
    (if (odd? num)
      (inc quotient)
      quotient)))

(defn match-body-side
  "Defines if body part is on left, right or center"
  [part symmetric-size]
  (let [middle (middle-point symmetric-size)]
    (if (even? symmetric-size)
      (cond
        (<= part middle)"left-"
        :else "right-")
      (cond
        (< part middle) "left-"
        (> part middle) "right-"
        :else "central-"))))

(defn create-body-part-sides
  "Create body part sides"
  [num-parts]
  (loop [remaining-body-parts num-parts
         final-body-parts []]
    (if (zero? remaining-body-parts)
      final-body-parts
      (recur (dec remaining-body-parts) 
             (into final-body-parts 
                   [(match-body-side remaining-body-parts num-parts)])))))

(defn expand-parts
  "Create humanoid body symmetrical parts"
  [part num-parts]
  (let [expand-tag #"^symmetric-"
        part-name (:name part)
        part-size (:size part)]
    (if (re-find expand-tag part-name)
      (map (fn [num-parts]
             {:name (clojure.string/replace part-name expand-tag num-parts)
              :size part-size})
           (create-body-part-sides num-parts))
      [{:name part-name :size part-size}])))

(defn humanoid-expand-parts
  "Create humanoid body symmetrical parts"
  [part]
  (expand-parts part 2))

(defn alien-expand-parts
  "Create alien body radial symmetry parts"
  [part]
  (expand-parts part 5))

(defn expand-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts expander]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (expander part)))
          []
          asym-body-parts))

(defn main 
  []
  [{:type "hobbit" :body (expand-body-parts asym-body-parts humanoid-expand-parts)}
  {:type "alien" :body (expand-body-parts asym-body-parts alien-expand-parts)}])
