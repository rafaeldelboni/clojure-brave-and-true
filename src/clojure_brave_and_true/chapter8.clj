(ns clojure-brave-and-true.chapter8)

(def order-details-validations
  {:name
   ["Please enter a name" not-empty]
   :email
   ["Please enter an email address" not-empty
    "Your email address doesn't look like an email address"
    #(or (empty? %) (re-seq #"@" %))]})

(defn error-messages-for
  "Return a seq of error messages"
  [to-validate message-validator-pairs]
  (map first (filter #(not ((second %) to-validate))
                     (partition 2 message-validator-pairs))))

(defn validate
  "Returns a map with a vector of errors for each key"
  [to-validate validations]
  (reduce (fn [errors validation]
            (let [[fieldname validation-check-groups] validation
                  value (get to-validate fieldname)
                  error-messages (error-messages-for value validation-check-groups)]
              (if (empty? error-messages)
                errors
                (assoc errors fieldname error-messages))))
          {}
          validations))

(defmacro if-valid
  "Handle validation more concisely"
  [to-validate validations errors-name & then-else]
  `(let [~errors-name (validate ~to-validate ~validations)]
     (if (empty? ~errors-name)
       ~@then-else)))

(defmacro when-valid
  "Handle validation more concisely using when"
  [to-validate validations & body]
  `(let [errors-name# (validate ~to-validate ~validations)]
     (if (empty? errors-name#)
       (do ~@body))))

(defmacro defattrs
  "create attr getter fn based on a list of pairs [fn-name :key]"
  ([] nil)
  ([fn-name attr-name]
  `(def ~fn-name (comp ~attr-name :attributes)))
  ([fn-name attr-name & fn-attr-pairs]
   `(do
      (defattrs ~fn-name ~attr-name)
      (defattrs ~@fn-attr-pairs))))

(defn main []
  (let [valid-order {:name "Rafoleu Dolbone" :email "rafo@dol.cc"}]
    (when-valid valid-order order-details-validations "order is valid!")))
