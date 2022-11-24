(ns bookvert.run
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as string])
  (:require [bookvert.parse :as parse])
  (:require [bookvert.to-html :as to-html])
  (:gen-class))


(def cli-options
  [["-o" "--out-dir DIRECTORY" "Directory to put parsed results into"
    :validate-fn (fn [arg] (.isDirectory (io/file arg)))]])


(defn exit-with-error [messages]
  (println  (string/join \newline messages))
  (System/exit 1))


(defn validate-args [args]
  (let [[from to] args]
    (cond
      (or (not from) (not to)) (exit-with-error ["You should provider source and destination directories"])
      (not (.isDirectory (io/file from))) (exit-with-error ["Incorect 'source' directory" from])
      (not (.isDirectory (io/file to))) (exit-with-error ["Incorrect 'destination' directory" to])
      :else (list (io/file from) (io/file to)))))


(defn -main [& args]
  (let [[from to] (validate-args args)]
    (->> (parse/parse-md-files from)
         (to-html/write-html-files to))))
