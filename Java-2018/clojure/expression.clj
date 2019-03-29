(defn constant [value] (fn [map] value))
(defn variable [name] (fn [map] (get map name)))

(defn binary [op] (fn [a b]
                    (fn [val]
                      (op (eval (a val)) (eval (b val))))))

(defn unary [op] (fn [a]
                   (fn [val]
                     (op (eval (a val))))))

(def add (binary +))
(def subtract (binary -))
(def multiply (binary *))
(defn divide [a b] (fn [map] (/ (double (a map)) (double (b map)))))
(def negate (unary -))
(def sinh (unary (fn [a] (Math/sinh a))))
(def cosh (unary (fn [a] (Math/cosh a))))
(def exp-map
  {'sinh   sinh
   'cosh   cosh
   '+      add
   '-      subtract
   '*      multiply
   '/      divide
   'negate negate})
(defn parseList [expression]
  (cond
    (number? expression) (constant expression)
    (symbol? expression) (variable (str expression))
    (list? expression) (apply (exp-map (first expression)) (map parseList (rest expression)))
    )
  )

(defn parseFunction
  ([expression] (parseList (read-string expression))))

