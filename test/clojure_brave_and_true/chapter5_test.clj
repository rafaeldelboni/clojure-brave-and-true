(ns clojure-brave-and-true.chapter5-test
  (:require [clojure.test :refer :all :as t]
            [clojure-brave-and-true.chapter5 :refer :all :as ch5]))

(t/deftest c-int-test
  (testing "should return character int"
    (is (= (ch5/c-int ch5/character)
           10))
    ))

(t/deftest c-dex-test
  (testing "should return character dex"
    (is (= (ch5/c-dex ch5/character)
           5))
    ))

(t/deftest c-str-test
  (testing "should return character str"
    (is (= (ch5/c-str ch5/character)
           4))
    ))

(t/deftest n-int-test
  (testing "should return character next int"
    (is (= (ch5/c-int (ch5/n-int ch5/character 1))
           11))
    ))

(t/deftest n-dex-test
  (testing "should return character next dex"
    (is (= (ch5/c-dex (ch5/n-dex ch5/character 1))
           6))
    ))

(t/deftest n-str-test
  (testing "should return character next str"
    (is (= (ch5/c-str (ch5/n-str ch5/character 1))
           5))
    ))


(t/deftest n-str-test
  (testing "should return character next str"
    (is (= (ch5/c-str (ch5/n-str ch5/character 1))
           5))
    ))

(t/deftest my-comp-test
  (testing "my-comp should behave as comp"
    (is (= ((ch5/my-comp str +) 8 8 8)
           "24"))
    (is (= (filter (ch5/my-comp not zero?) [0 1 0 2 0 3 0 4])
           [1 2 3 4]))
    ))

(t/deftest my-assoc-in-test
  (testing "my-assoc-in should behave as assoc-in"
    (let [users [{:name "James" :age 26}  {:name "John" :age 43}]]
      (is (= (ch5/my-assoc-in users [1 :age] 44)
             [{:name "James", :age 26} {:name "John", :age 44}]))
      (is (= (ch5/my-assoc-in users [1 :password] "nhoJ")
             [{:name "James", :age 26} {:password "nhoJ", :name "John", :age 43}]))
      )))

(t/deftest my-update-in-test
  (testing "my-update-in should behave as update-in"
    (let [p {:name "James" :age 26}]
      (is (= (ch5/my-update-in p [:age] inc)
             {:name "James", :age 27}))
      (is (= (ch5/my-update-in p [:age] + 10)
             {:name "James", :age 36}))
      )))
