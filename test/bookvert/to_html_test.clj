(ns bookvert.to-html-test
  (:require [clojure.test :refer :all]
            [bookvert.to-html :refer :all]))

(deftest test-convert-chapter-body
  (testing "paragraphs and gaps to html string"
    (let [input '((:p ("some" "sort" "of" "string"))
                  (:p ("Second paragraph"))
                  (:gap (""))
                  (:p ("last" "paragraph")))
          expected "<p>some sort of string</p>\n<p>Second paragraph</p>\n<hr class='gap'/>\n<p>last paragraph</p>"]
      (is (= (convert-chapter-body input)
             expected))))
  (testing "fallback to <p>"
    (is (= (convert-chapter-body '((:laskj ("hoho" "haha"))))
           "<p>hoho haha</p>"))))
