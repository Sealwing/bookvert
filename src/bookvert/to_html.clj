(ns bookvert.to-html
  (:require [clojure.string :as string]))


;; CONVERTING

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


(defn wrap-in-body [body]
  (string/join "\n" ["<body>" body "</body>"]))


(defn add-head [title body]
  (string/join "\n" ["<head>" "<title>" title "</title>" "</head>" body]))


;; FILE WRITER

(defn to-title-and-html [named-block]
  (let [[title blocks] named-block
        html-chapter (convert-chapter-body blocks)
        html (add-head title (wrap-in-body html-chapter))]
    (list title html)))


(defn write-html-files [directory named-blocks]
  (doseq [title-and-content (map to-title-and-html named-blocks)]
    (let [[title content] title-and-content]
      (spit (string/join "/" [directory (str title ".html")])
            content))))
