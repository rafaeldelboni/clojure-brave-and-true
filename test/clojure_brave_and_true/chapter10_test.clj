(ns clojure-brave-and-true.chapter10-test
  (:require [clojure.test :refer :all :as t]
            [clojure-brave-and-true.chapter10.atomic :refer :all :as atomic]
            [clojure-brave-and-true.chapter10.quotes :refer :all :as quotes]
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
    (is (= (quotes/generate-future-tasks 2)
           '((swap! random-quotes into (convert-quote-words
                    (slurp "https://www.braveclojure.com/random-quote")))
             (swap! random-quotes into (convert-quote-words
                    (slurp "https://www.braveclojure.com/random-quote"))))))
    ))

(t/deftest async-many-futures-test
  (testing "should populate random-quotes with a word list"
    (is (= (do
             (reset! quotes/random-quotes [])
             (quotes/async-many-futures
                 ['(swap! random-quotes into (quotes/convert-quote-words (str "de bo la")))
                  '(swap! random-quotes into (quotes/convert-quote-words (str "ar af le")))])
             (sort @quotes/random-quotes))
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
