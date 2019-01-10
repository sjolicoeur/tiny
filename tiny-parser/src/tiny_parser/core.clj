(ns tiny-parser.core
  (:gen-class)
  (:require [instaparse.core :as insta]
            [clojure.string :as s]))


(defn process-file
  [t file]
  (with-open [rdr (clojure.java.io/reader file)]
    (into []
          t
          (line-seq rdr))))

(def import-re #"@import \"(.*)\"")

(defn parse-import [text]
  (let [matches (re-find import-re text)]
    (if-not matches
      text
      (let [filename (second matches)
            ;; it has to be relative to the path
            imported-content (slurp (str "test/tiny/" filename))]
        (s/replace text import-re imported-content)))))

(defn ensure-linebreak [text]
  (if-not (re-find #"\n$" text)
    (str text "\n")
    text))

(defn tiny-parse [filename]
  (ensure-linebreak (s/join "\n" (process-file (map parse-import) filename))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
