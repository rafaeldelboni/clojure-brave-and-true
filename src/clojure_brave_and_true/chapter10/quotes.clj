(ns clojure-brave-and-true.chapter10.quotes)

(def random-quotes (atom []))

(defn remove-author-name
  [quote]
  (first (clojure.string/split quote #" -- ")))

(defn cleanup-words
  [quote]
  ( ->> quote
        (re-seq #"[a-zA-Z\ ]+")
        clojure.string/join
        clojure.string/lower-case))

(defn list-words
  [quote]
  (re-seq #"\w+" quote))

(def convert-quote-words
  (comp list-words cleanup-words remove-author-name))

(defn increment-word-count
  [current-word-count next-word]
  (if (current-word-count next-word)
    (update-in current-word-count [next-word] inc)
    (conj current-word-count {next-word 1})))

(defn words-to-count-map
  [word-list]
  (reduce increment-word-count {} word-list))

(defn generate-future-tasks
  [task-number]
  (take task-number
        (repeat
          '(swap!
              random-quotes
              into (convert-quote-words
                     (slurp "https://www.braveclojure.com/random-quote")))
          )))

(defn async-many-futures
  [tasks]
  (do
    (let [futures
          (doall
            (for [task tasks]
              (future
                (eval task))))]
      (doseq [completion futures]
        @completion))))

(defn quote-word-count
  [quote-number]
  (do 
    (reset! random-quotes [])
    (async-many-futures (generate-future-tasks quote-number))
    (words-to-count-map @random-quotes)))
