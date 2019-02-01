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

(def import-re #"@import \"(?<filename>.*)\"")
(def map-re #"@map\(\[(?<array>.*)\], \((?<variable>.*)\)\s+=>\s+\{\s*(?<template>.+) \}\)")

(def rules [parse-import
            parse-map])

(defn parse-import [text]
  (let [matches (re-find import-re text)]
    (if-not matches
      text
      (let [filename (second matches)
            ;; it has to be relative to the path
            imported-content (slurp (str "test/tiny/" filename))]
        (s/replace text import-re imported-content)))))

(defn parse-map [text]
  (println "my text is" text)
  (let [matcher (re-matcher map-re text)]
    (if-not (.matches matcher)
      text
      (let [array (.group matcher "array")
            variable (.group matcher "variable")
            template (.group matcher "template")]
        (println "hi" matcher)
        (println array)
        (println variable)
        (println template)
        "terrible"
        ))))

(defn ensure-linebreak [text]
  (if-not (re-find #"\n$" text)
    (str text "\n")
    text))

(defn apply-rules [text]
  (reduce (fn [acc rule]
            (rule text))
          text rules))

(defn tiny-parse [filename]
  (ensure-linebreak (s/join "\n" (process-file (map apply-rules) filename))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
