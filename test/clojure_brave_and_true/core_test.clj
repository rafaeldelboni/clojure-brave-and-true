(ns clojure-brave-and-true.core-test
  (:require [clojure.test :refer :all :as t]
            [clojure-brave-and-true.core :refer :all :as core]))

(t/deftest match-chapter-test
  (testing "should return :chapter3"
    (is (= (core/match-chapter 3)
           :chapter3)))
  (testing "should return nil"
    (is (= (core/match-chapter 0)
           nil))))

(t/deftest exec-chapter-test
  (testing "should return nil"
    (is (= (core/exec-chapter 0)
           nil))))

(t/deftest validate-args-test
  (testing "should return help"
    (is (= (core/validate-args (list "-h"))
           {:message "Usage: program-name [options]\n\nOptions:\n  -c, --chapter NUMBER\n  -h, --help"})))
  (testing "should return generic error"
    (is (= (core/validate-args (list "chops"))
           {:message "Usage: program-name [options]\n\nOptions:\n  -c, --chapter NUMBER\n  -h, --help"})))
  (testing "should return error invalid chapter"
    (is (= (core/validate-args (list "-c" "1"))
           {:message "The following errors occurred while parsing your command:\n\nFailed to validate \"-c 1\": Chapter must be a number be 3"}))))
