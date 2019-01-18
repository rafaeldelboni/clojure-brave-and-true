(ns clojure-brave-and-true.chapter8-test
  (:require [clojure.test :refer :all :as t]
            [clojure-brave-and-true.chapter8 :refer :all :as ch8]
            [clojure-brave-and-true.chapter5 :refer [character]]))

(t/deftest when-valid-test
  (testing "should behave as when with the validations"
    (let [valid-order {:name "Rafoleu Dolbone" :email "rafo@dol.cc"}
          invalid-order {:name "Rafoleu Dolbone" :email "rafodol.cc"}]
      (t/is (= (ch8/when-valid valid-order ch8/order-details-validations (inc 1))
             2))
      (t/is (= (ch8/when-valid invalid-order ch8/order-details-validations (inc 1))
             nil))
      )))

(t/deftest defattrs-test
  (testing "should behave as when with the validations"
    (do (ch8/defattrs test-c-int :intelligence test-c-str :strength)
        (t/is (= (test-c-int character)
                 10))
        (t/is (= (test-c-str character)
                 4))
        )))
