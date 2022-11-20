(ns bookvert.parse
  (:require [clojure.string :as string]))


(defn gap-or-line [cleaned-line]
  (if (= (set cleaned-line) #{\-})
      [:gap ""]
      [:p cleaned-line]))


(defn mark-line [line]
  (let
      [cleaned-line (string/trim line)
       first-char (get cleaned-line 0)]
    (case first-char
      nil [:empty ""]
      \- (gap-or-line cleaned-line)
      [:p cleaned-line])))


(defn split-by-empty [lines]
  (filter (fn [grouped-lines] (not= (ffirst grouped-lines)
                                    :empty))
   (partition-by first lines)))


(defn lines-to-blocks [lines]
  (map (fn [grouped-lines] (list (ffirst grouped-lines)
                                 (map last grouped-lines)))
       (split-by-empty (map mark-line lines))))
