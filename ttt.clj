(ns ttt)

(def winning-moves [[1 2 3]
                    [4 5 6]
                    [7 8 9]
                    [1 4 7]
                    [2 5 8]
                    [3 6 9]
                    [1 5 9]
                    [3 5 7]])

(defn won?
  [player]
  (some #(every? (player :moves) %) winning-moves))

(defn add-move
  [game move]
  (update-in game [:current-player :moves] conj move))

(defn swap-player
  [{:keys [current-player next-player] :as game}]
  (assoc game
         :current-player next-player
         :next-player current-player))

(defn replace-with [symbol moves board]
  (map #(if (contains? moves %) symbol %) board))

(defn render-board
  [{:keys [current-player next-player]}]
  (->> (range 1 10)
       (replace-with (current-player :symbol) (current-player :moves))
       (replace-with (next-player :symbol) (next-player :moves))
       (map str)
       (partition 3)
       (interpose "\n")
       (flatten)
       (java.lang.String/join " ")
       (println "")))

;; doseq we can use here

(def read-move (comp read-string read-line))

(defn play [initial-game]
  (loop [game initial-game]
    (render-board game)
    (let [updated-game (->> (read-move)
                            (add-move game))]
      (if (won? (:current-player updated-game))
        (println "Winner is " ((updated-game :current-player) :name))
        (recur (swap-player updated-game))))))

(def game {:current-player {:name "Prajakta"
                            :moves #{}
                            :symbol "X"}
           :next-player {:name "Subhash"
                         :moves #{}
                         :symbol "O"}})
(play game)