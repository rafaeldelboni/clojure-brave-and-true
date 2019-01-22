(ns clojure-brave-and-true.chapter9-test
  (:require [clojure.test :refer :all :as t]
            [clojure-brave-and-true.chapter9 :refer :all :as ch9]
            [mock-clj.core :as mock]))

(t/deftest build-search-url-test
  (testing "should build url based on term and engine"
      (t/is (= (ch9/build-search-url :duck "corgi")
             "https://duckduckgo.com/html?q=corgi"))
      (t/is (= (ch9/build-search-url :bing "short dog")
             "https://www.bing.com/search?q=short dog"))
      (t/is (= (ch9/build-search-url :google "wondrous dog breed")
             "https://www.google.com/search?q=wondrous dog breed"))
      ))

(t/deftest url-special-chars-test
  (testing "should convert and replace html special chars on url"
      (t/is (= (ch9/url-special-chars "aa<%2f%3A%2D>bbb")
             "aa</:->bbb"))
      (t/is (= (ch9/url-special-chars "https%3A%2f%2fwww.corgi%2ddog.com")
             "https://www.corgi-dog.com"))

      ))

(t/deftest parse-urls-test
  (testing "should convert and replace html special chars on url"
      (t/is (= (ch9/parse-urls
                 "<\"https%3A%2f%2fcorgi.cc\"><\"https%3A%2f%2fwww.dog.com\">")
             '("https://corgi.cc" "https://www.dog.com")))
      ))

(t/deftest multi-web-search-test
  (testing "should convert and replace html special chars on url"
    (mock/with-mock
      [ch9/web-search
       {:body "<\"https%3A%2f%2fcorgi.cc\"><\"https%3A%2f%2fwww.dog.com\">"}]
      (t/is (= (ch9/multi-web-search [:duck] "doggos")
                 '("https://corgi.cc" "https://www.dog.com"))))
      ))
