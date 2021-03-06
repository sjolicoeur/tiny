(ns tiny-parser.core-test
  (:use midje.sweet)
  (:require [tiny-parser.core :refer :all]))

(defn load-tiny-file [filename]
  (str "test/tiny/" filename))

(facts "tiny-parse"
       (fact "passes through simple YAML"
             (tiny-parse (load-tiny-file "simple.yml")) => "name: Bob\nage: 123\n")
       (fact "imports from another file"
             (tiny-parse (load-tiny-file "simple.tiny")) => "name: Bob\nage: 123\nextra_stuff: true\n")
       (fact "parses maps"
             (tiny-parse (load-tiny-file "function.tiny")) => "users:\n- name: bob\n- name: cat"))

(facts "regexes"
       (fact "import regex"
             (re-find import-re "@import \"extra.yml\"") => ["@import \"extra.yml\"" "extra.yml"])
       (fact "map regex"
             (re-find map-re "user: @map([\"bob\", \"cat\"], ($name) => { - name: $name })") => ["@map([\"bob\", \"cat\"], ($name) => { - name: $name })"
 "\"bob\", \"cat\""
 "$name"
 "- name: $name"]))
