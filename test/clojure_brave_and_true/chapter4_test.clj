(ns clojure-brave-and-true.chapter4-test
  (:require [clojure.test :refer :all :as t]
            [clojure-brave-and-true.chapter4 :refer :all :as ch4]))

(t/deftest convert-test
  (testing "should return a converted value"
    (is (= (ch4/convert :name "rafael")
           "rafael"))
    (is (= (ch4/convert :glitter-index "42")
           42))
    ))

(t/deftest parse-test
  (testing "should return a seq of parsed arrays"
    (is (= (ch4/parse "")
           ()))
    (is (= (ch4/parse "a,b")
           ()))
    (is (= (ch4/parse "a,b\r")
           '(["a" "b"])))
    (is (= (ch4/parse "a,b\n")
           '(["a" "b"])))
    (is (= (ch4/parse "a,b\r\n")
           '(["a" "b"])))
    (is (= (ch4/parse "a,b\nc,d\ne,f\n")
           '(["a" "b"] ["c" "d"] ["e" "f"])))
    ))

(t/deftest mapify-test
  (testing "should return a mapped value from csv"
    (is (= (ch4/mapify '(["a" 1] ["b" 2] ["c" 3]))
           '({:name "a" :glitter-index 1}
             {:name "b" :glitter-index 2}
             {:name "c" :glitter-index 3})))
    ))

(t/deftest glitter-filter-test
  (testing "should return a map regarding the minimun index"
    (is (= (ch4/glitter-filter
             3
             '({:name "a" :glitter-index 1}
               {:name "b" :glitter-index 2}
               {:name "c" :glitter-index 3}))
           '({:name "c" :glitter-index 3})))
    ))

(t/deftest key-list-test
  (testing "should return a list of the names"
    (is (= (ch4/key-list :name
             '({:name "a" :glitter-index 1}
               {:name "b" :glitter-index 2}
               {:name "c" :glitter-index 3}))
           '("a" "b" "c")))
    ))

(t/deftest validate-vamp-test
  (testing "should return true for valid vampire maps"
    (is (= (ch4/validate-vamp ch4/validations {:name "a" :glitter-index 1})
               true))
    (is (= (ch4/validate-vamp ch4/validations {:name "a"})
               false))
    (is (= (ch4/validate-vamp ch4/validations {:glitter-index 1})
               false))
    (is (= (ch4/validate-vamp ch4/validations {:name "a" :glitter-index "1"})
               false))
    (is (= (ch4/validate-vamp ch4/validations {:name 1 :glitter-index 1})
               false))
    ))

(t/deftest append-vamp-test
  (testing "should return a list with the appended vamp"
    (is (= (ch4/append-vamp
             '({:name "a" :glitter-index 1})
             {:name "b" :glitter-index 2})
           '({:name "b" :glitter-index 2} {:name "a" :glitter-index 1})))
    (is (= (ch4/append-vamp
             '({:name "a" :glitter-index 1})
             {:name 888 :glitter-index "invalid"})
           '({:name "a" :glitter-index 1})))
    ))

(t/deftest to-csv-test
  (testing "should return a csv of the map list"
    (is (= (ch4/to-csv
             '({:name "a" :glitter-index 1}
               {:name "b" :glitter-index 2}
               {:name "c" :glitter-index 3}))
           "a, 1\nb, 2\nc, 3\n"))
    ))
