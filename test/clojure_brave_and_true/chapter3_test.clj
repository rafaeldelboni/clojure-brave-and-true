(ns clojure-brave-and-true.chapter3-test
  (:require [clojure.test :refer :all :as t]
            [clojure-brave-and-true.chapter3 :refer :all :as ch3]))

(t/deftest middle-int-test
  (testing "should return middle integer"
    (is (= (ch3/middle-point 5)
           3))
    (is (= (ch3/middle-point 10)
           5))
    (is (= (ch3/middle-point 9)
           5))
    (is (= (ch3/middle-point 2)
           1)))
  )

(t/deftest match-body-side-test
  (testing "should return left"
    (is (= (ch3/match-body-side 1 3)
           "left-"))
    (is (= (ch3/match-body-side 1 2)
           "left-"))
    (is (= (ch3/match-body-side 1 4)
           "left-"))
    (is (= (ch3/match-body-side 2 4)
           "left-")))
  (testing "should return right"
    (is (= (ch3/match-body-side 3 3)
           "right-"))
    (is (= (ch3/match-body-side 2 2)
           "right-"))
    (is (= (ch3/match-body-side 3 4)
           "right-"))
    (is (= (ch3/match-body-side 4 4)
           "right-")))
  (testing "should return central"
    (is (= (ch3/match-body-side 2 3)
           "central-"))
    (is (= (ch3/match-body-side 3 5)
           "central-"))
    (is (= (ch3/match-body-side 7 13)
           "central-")))
  )

(t/deftest create-body-part-sides-test
  (testing "should return right left"
    (is (= (ch3/create-body-part-sides 2)
           ["right-" "left-"])))
  (testing "should return right center left"
    (is (= (ch3/create-body-part-sides 3)
           ["right-" "central-" "left-"])))
  (testing "should return right right center left left"
    (is (= (ch3/create-body-part-sides 5)
           ["right-" "right-" "central-" "left-" "left-"])))
  )

(t/deftest humanoid-expand-parts-test
  (testing "should return right left body part"
    (is (= (ch3/humanoid-expand-parts {:name "symmetric-foot" :size 5})
           [{:name "right-foot" :size 5} {:name "left-foot" :size 5}])))
  )

(t/deftest alien-expand-parts-test
  (testing "should return right right central left left body part"
    (is (= (ch3/alien-expand-parts {:name "symmetric-foot" :size 5})
           [{:name "right-foot" :size 5}
            {:name "right-foot" :size 5}
            {:name "central-foot" :size 5}
            {:name "left-foot" :size 5}
            {:name "left-foot" :size 5}])))
  )

(t/deftest expand-body-parts-test
  (testing "should return right left for each part"
    (is (= (ch3/expand-body-parts
             [{:name "head" :size 6}
              {:name "symmetric-hand" :size 3}
              {:name "symmetric-foot" :size 5}]
             ch3/humanoid-expand-parts)
           [{:name "head" :size 6}
            {:name "right-hand" :size 3}
            {:name "left-hand" :size 3}
            {:name "right-foot" :size 5}
            {:name "left-foot" :size 5}])))
  )
