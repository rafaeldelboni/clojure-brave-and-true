(ns clojure-brave-and-true.chapter4)

(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(def validations {:name string?
                  :glitter-index integer?})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (cond
         (re-find #"\r\n" string) (clojure.string/split string #"\r\n")
         (re-find #"\r" string) (clojure.string/split string #"\r")
         (re-find #"\n" string) (clojure.string/split string #"\n")
         :else nil)))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(defn key-list
  [key records]
  (map (fn [row] (key row)) records))

(defn validate-vamp
  [vamp-validade-keys value]
  (reduce (fn [current [vamp-key vamp-validate]]
            (and
              current
              (vamp-validate (vamp-key value))))
          true
          (seq vamp-validade-keys)))

(defn append-vamp
  [records vamp]
  (if (validate-vamp validations vamp) (merge records vamp) records))

(defn to-csv
  [records]
  (reduce (fn [current next]
            (str
              current
              (clojure.string/join
                ", "
                (map (fn [current-key] (current-key next)) vamp-keys))
              "\n"))
            ""
            records))

(defn main
  []
  (to-csv
    (append-vamp
      (mapify (parse (slurp filename))) {:name "Blade" :glitter-index 0})))
