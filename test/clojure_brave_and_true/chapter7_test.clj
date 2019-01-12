(ns clojure-brave-and-true.chapter7-test
  (:require [clojure.test :refer :all :as t]
            [clojure-brave-and-true.chapter7 :refer :all :as ch7]))

(t/deftest infix-all-test
  (testing "should calculate the respecting the infix order"
    (is (= (ch7/infix-all [1 + 2])
           3))
    (is (= (ch7/infix-all [1 + 1 / 3 - 3])
           -5/3))
    (is (= (ch7/infix-all [1 + 3 * 4 - 5])
           8))
    ))
