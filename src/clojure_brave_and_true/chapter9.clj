(ns clojure-brave-and-true.chapter9
  (:require [clj-http.client :as http]))

(def search-engines 
  {:duck "https://duckduckgo.com/html?q="
   :google "https://www.google.com/search?q="
   :bing "https://www.bing.com/search?q="
   })

(defn build-search-url
  "Build a web search url based on parameter engine and search term"
  [engine term]
  (str (engine search-engines) term))

(def web-search (comp http/get build-search-url))

(defn url-special-chars
  "Convert url special chars to normal chars"
  [html-body]
  (clojure.string/replace
    html-body
    #"%3[aA]|%2[fF]|%2[dD]"
    {"%3A" ":" "%2F" "/" "%2D" "-" "%3a" ":" "%2f" "/" "%2d" "-"}))

(defn get-urls
  "Get all urls from the html body and return as a vector"
  [html-body]
  (re-seq #"https?://[^\"]*" html-body))

(def parse-urls (comp get-urls url-special-chars))

(defn multi-web-search
  "Returns the first result page from an list search engines and search term"
  [search-engines term]
  (let [http-search-promise (promise)]
    (doseq [engine search-engines]
      (future (if-let [web-result (web-search engine term)]
                (deliver http-search-promise (parse-urls (:body web-result))))))
    @http-search-promise))

(defn main []
  (multi-web-search [:google :bing :duck] "corgi"))
