(ns clojure-brave-and-true.chapter10-test
  (:require [clojure.test :refer :all :as t]
            [clojure-brave-and-true.chapter10.atomic :refer :all :as atomic]
            [clojure-brave-and-true.chapter10.quotes :refer :all :as quotes]
            [clojure-brave-and-true.chapter10.heal :refer :all :as heal]
            [mock-clj.core :as mock]))

(t/deftest atomic-incrementer-test
  (testing "should return a incremented state"
    (is (= (do
             (reset! atomic/atomic-number 0)
             (atomic/atomic-incrementer atomic/atomic-number 3)
             (atomic/atomic-incrementer atomic/atomic-number 5)
             @atomic/atomic-number)
           8))
    (is (= (do
             (reset! atomic/atomic-number 0)
             (atomic/atomic-incrementer atomic/atomic-number 12)
             (atomic/atomic-incrementer atomic/atomic-number 6)
             @atomic/atomic-number)
           18))
    ))

(t/deftest remove-author-name-test
  (testing "should return a quote without the author"
    (is (= (quotes/remove-author-name "Duper random shobe, drama. -- Rofa Doll")
           "Duper random shobe, drama."))
    (is (= (quotes/remove-author-name "Duper random shobe, drama.")
           "Duper random shobe, drama."))
    ))

(t/deftest cleanup-words-test
  (testing "should return quote without special or upper case chars"
    (is (= (quotes/cleanup-words "Duper; random! shobe, drama.?")
           "duper random shobe drama"))
    (is (= (quotes/cleanup-words "duper random shobe drama")
           "duper random shobe drama"))
    ))

(t/deftest list-words-test
  (testing "should word list"
    (is (= (quotes/list-words "duper random shobe drama")
           '("duper" "random" "shobe" "drama")))
    ))

(t/deftest convert-quote-words-test
  (testing "should return a clean word list"
    (is (= (quotes/convert-quote-words "Duper; random! shobe, drama.? -- Rofa Doll")
           '("duper" "random" "shobe" "drama")))
    ))

(t/deftest increment-word-count-test
  (testing "should return a task list"
    (let [current-state {"book" 41 "monster" 3}]
      (is (= (quotes/increment-word-count current-state "book")
             {"book" 42 "monster" 3}))
      (is (= (quotes/increment-word-count current-state "magic")
             {"book" 41 "monster" 3 "magic" 1}))
    )))

(t/deftest generate-future-tasks-test
  (testing "should return a task list"
    (is (= (count (quotes/generate-future-tasks 2))
           2))
    ))

(t/deftest async-many-futures-test
  (testing "should populate quote-word-count-testords with a word list"
    (is (= (do
             (reset! quotes/state-quote-words  [])
             (quotes/async-many-futures
               [(fn [atom-quotes] (swap! quotes/state-quote-words
                                         into (quotes/convert-quote-words
                                                (str "de bo la"))))
                (fn [atom-quotes] (swap! quotes/state-quote-words
                                         into (quotes/convert-quote-words 
                                                (str "ar af le"))))]
               quotes/state-quote-words )
             (sort @quotes/state-quote-words ))
           ["af" "ar" "bo" "de" "la" "le"]))
    ))

(t/deftest words-to-count-map-test
  (testing "should return a map with counted words"
    (is (= (quotes/words-to-count-map ["dog" "doggo" "corgo" "corgo" "dog" "catto"])
           {"dog" 2, "doggo" 1, "corgo" 2, "catto" 1}))
    ))

(t/deftest quote-word-count-test
  (testing "should return a map with counted words"
    (mock/with-mock
      [slurp "Duper; random! shobe, drama.? -- Rofa Doll"]
      (t/is (= (quotes/quote-word-count 4)
               {"duper" 4, "random" 4, "shobe" 4, "drama" 4})))
      ))

(t/deftest give-potion-test
  (testing "should giver give a potion to receiver"
    (do (def player-uno (ref {:potions 1}))
        (def player-duo (ref {:potions 9}))
        (heal/give-potion player-uno player-duo)
        (is (= @player-uno
               {:potions 2}))
        (is (= @player-duo
               {:potions 8}))
    )))

(t/deftest use-potion-test
  (testing "should giver give a potion to receiver"
    (do (def player-tre (ref {:max-health 40 :cur-health 15 :potions 1}))
        (def player-quattro (ref {:max-health 40 :cur-health 40 :potions 9}))
        (def player-cinque (ref {:max-health 40 :cur-health 1 :potions 0}))
        (heal/use-potion player-tre)
        (heal/use-potion player-quattro)
        (heal/use-potion player-cinque)
        (is (= @player-tre
               {:max-health 40, :cur-health 40, :potions 0}))
        (is (= @player-quattro
               {:max-health 40, :cur-health 40, :potions 8}))
        (is (= @player-cinque
               {:max-health 40, :cur-health 1, :potions 0}))
    )))
