(ns clojure-recursive-reduce.core-test
  (:require [clojure.test :refer :all]
            [clojure-recursive-reduce.core :refer :all]))

(def items
  [{:id 1, :good true}
   {:id 2,
    :children
    [{:id 3, :good true}
     {:id 4, :good true}
     {:id 5,
      :children
      [{:id 6, :good true} {:id 7, :good true} {:id 8, :good true}]}
     {:id 9,
      :children
      [{:id 10, :children [{:id 11, :good true} {:id 12, :good true}]}]}
     {:id 13, :good true}
     {:id 17, :children []}
     {:id 14, :children [{:id 15, :good true}]}]}
   {:id 16, :good true}])

(def known-good-items
  [{:id 1, :good true}
   {:id 3, :good true}
   {:id 4, :good true}
   {:id 6, :good true}
   {:id 7, :good true}
   {:id 8, :good true}
   {:id 11, :good true}
   {:id 12, :good true}
   {:id 13, :good true}
   {:id 15, :good true}
   {:id 16, :good true}])

(def good-ids
  (map #(:id %1) known-good-items))

(defn good-one [acc item]
  (if (item :good)
    (conj acc (item :id))
    (if (seq (item :children))
      (reduce good-one acc (item :children))
      acc)))

(defn good-ones [collection]
  (reduce good-one [] collection))

(deftest good-ids-test
  (testing "good-ids should collect the good ids"
    (is (= good-ids [1 3 4 6 7 8 11 12 13 15 16]))))

(deftest good-items-test
  (testing "good-items should collect the good ids"
    (is (= (good-ones items) good-ids))))
