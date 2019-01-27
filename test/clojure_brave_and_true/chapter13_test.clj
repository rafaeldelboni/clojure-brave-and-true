(ns clojure-brave-and-true.chapter13-test
  (:require [clojure.test :refer :all :as t]
            [clojure-brave-and-true.chapter13 :refer :all :as ch13]))

(t/deftest full-moon-behavior-test
  (testing "should return a full moon behavior"
    (is (= (ch13/full-moon-behavior {:were-type :wolf
                                     :name "Rachel from next door"})
           "Rachel from next door will howl and murder"))
    (is (= (ch13/full-moon-behavior {:were-type :unicorn
                                     :name "John the Otaku"})
           "John the Otaku will evoke cute reactions and make people puke"))
    ))

(t/deftest pro-full-moon-behavior-test
  (testing "should return a full moon behavior for protocols"
    (is (= (ch13/pro-full-moon-behavior
             (ch13/map->WereWolf {:name "Lucian" :title "CEO of Melodrama"}))
           "Lucian will howl and murder"))
    (is (= (ch13/pro-full-moon-behavior
             (ch13/map->WereSimmons {:name "Rafael" :title "Newbie of Everything"}))
           "Rafael will encourage people and sweat to the oldies"))
    ))

(t/deftest psychodynamics-test
  (testing "should return a incremented state"
    (is (= (ch13/thoughts
             (ch13/map->WereWolf {:name "Lucian" :title "CEO of Melodrama"}))
           "Brawwwwm, shuonm! Graaaarrr?"))
    (is (= (ch13/thoughts
             (ch13/map->WereSimmons {:name "Rafael" :title "Newbie of Everything"}))
           "I really believe on you, mah."))
    ))

(t/deftest cast-spell-test
  (testing "should return a full moon behavior"
    (is (= (ch13/cast-spell {:school :fire
                             :name "Ogien"})
           "Ogien casts a star fire, awaken and deliver your judgment!"))
    (is (= (ch13/cast-spell {:school :ice
                             :name "Zadymka"})
           "Zadymka casts a freezing wind, speak of forgotten truths!"))
    ))
