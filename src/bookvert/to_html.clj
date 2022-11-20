(ns bookvert.to-html
  (:require [clojure.string :as string]))


(defn wrap-in-p [parts]
  (str "<p>" (string/join " " parts) "</p>"))


(def TAG-MAP {:p wrap-in-p
              :gap (fn [_parts] "<hr class='gap'/>")})


(defn wrap-block [block]
  (let [[tag parts] block]
    (case tag
      :p (wrap-in-p parts)
      :gap "<hr class='gap'/>"
      (wrap-in-p parts))))


(defn convert-chapter-body [blocks]
  (string/join "\n" (map wrap-block blocks)))
