(ns clojure-brave-and-true.core
  (:require [clojure.string :as string]
            [clojure.tools.cli :refer [parse-opts]]
            [clojure-brave-and-true.chapter3 :as chapter3])
  (:gen-class))

(def chapters {:chapter3 chapter3/main})

(defn match-chapter
  "Match which chapter based on argument"
  [chapter]
  (cond
    (= chapter 3) :chapter3
    :else nil))

(defn exec-chapter
  "Execute function of matched chapter"
  [chapter]
  (def matched (match-chapter chapter))
  (if-some [key matched] ((chapters key)) nil))

(def cli-options
  ;; An option with a required argument
  [["-c" "--chapter NUMBER"
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 2 % 4) "Chapter must be a number be 3"]]
   ;; A boolean option defaulting to nil
   ["-h" "--help"]])

(defn usage [options-summary]
  (->> ["Usage: program-name [options]"
        ""
        "Options:"
        options-summary]
       (string/join \newline)))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (string/join \newline errors)))

(defn validate-args
  "Validate command line arguments. Either return a map indicating the program
  should exit (with a error message, and optional ok status), or a map
  indicating the action the program should take and the options provided."
  [args]
  (let [{:keys [options errors summary]} (parse-opts args cli-options)]
    (cond
      (:help options)
      {:message (usage summary)}
      (:chapter options)
      {:message (exec-chapter (:chapter options))}
      errors
      {:message (error-msg errors)}
      :else
      {:message (usage summary)})))

(defn show [msg]
  (println msg))

(defn -main [& args]
  (let [{:keys [message]} (validate-args args)]
    (if message
      (show message))))
