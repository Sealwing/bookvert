(ns bookvert.parse-test
  (:require [clojure.test :refer :all]
            [bookvert.parse :refer :all]))

(deftest test-gap-or-line
  (testing "1 '-' should be gap"
    (is (= (gap-or-line "-")
           [:gap ""])))
  (testing "3 '-' should be gap"
    (is (= (gap-or-line "---")
           [:gap ""])))
  (testing "another symbol should be line"
    (is (= (gap-or-line "--- f")
           [:p "--- f"]))))

(deftest test-mark-line
  (testing "new line"
    (is (= (mark-line "\n")
           [:empty ""])))
  (testing "line with spaces"
    (is (= (mark-line " What's that \n")
           [:p "What's that"]))))

(deftest test-split-by-empty
  (testing "basic case no gaps"
    (let [input '((:p "1") (:p "2") (:empty "") (:p "3"))
          expected '(((:p "1") (:p "2")) ((:p "3")))]
      (is (= (split-by-empty input) expected)))))


(deftest test-lines-to-blocks
  (testing "3 blocks with gap"
    (let [input '("First line\n"
                  "second line \n"
                   "\n"
                   " Another block \n"
                   "\n"
                   "---\n"
                   "\n"
                   "Third paragraph\n")
          expected '((:p ("First line" "second line"))
                     (:p ("Another block"))
                     (:gap (""))
                     (:p ("Third paragraph")))]
    (is (= (lines-to-blocks input) expected)))))
