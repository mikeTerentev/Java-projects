(defn elem_func [op a sSubOp b]
  (loop [cnt 0 res []] (if (== cnt (count a))
                         res
                         (recur (inc cnt) (conj res (op (a cnt) (sSubOp b cnt)))))))

(defn scalar [a b] (loop [cnt 0 res 0] (if (== cnt (count a))
                                         res
                                         (recur (inc cnt) (+ res (* (nth a cnt) (nth b cnt)))))))

(defn operate [op] (fn [a b] (mapv op a b)))

(defn m*v [m v] (mapv (fn [vertical] (scalar vertical v)) m))

(def v+ (operate +))
(def v- (operate -))
(def v* (operate *))
(def m+ (operate v+))
(def m- (operate v-))
(def m* (operate v*))

(defn v*s [a s] (elem_func * a (fn [x cnt] x) s))

(defn m*s [a s] (elem_func v*s a (fn [x cnt] x) s))

(defn vect [a b]
  [(- (* (nth a 1) (nth b 2)) (* (nth a 2) (nth b 1)))
   (- (* (nth a 2) (nth b 0)) (* (nth a 0) (nth b 2)))
   (- (* (nth a 0) (nth b 1)) (* (nth a 1) (nth b 0)))
   ])

(defn transpose [m]
  (apply mapv vector m))

(defn m*m [a b]
  (transpose (mapv (fn [x] (m*v a x)) (transpose b))))

(defn abstract [op]
  (fn calc [a b] (if (vector? a) (mapv calc a b) (op a b))))

(def s+ (abstract +))
(def s* (abstract *))
(def s- (abstract -))
