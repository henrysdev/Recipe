(ns recipe.core
  (:gen-class)
  (:require [clj-spotify.core :refer :all]
            [clj-spotify.util :refer :all]))

(defn get-track-label [track-id auth-tok]
  (let [track (get-a-track  {:id track-id} auth-tok)
        album (get-an-album {:id (:id (:album track))} auth-tok)]
        (:label album)))

(defn -main [& args]
  (def auth-tok
    (let [client-id     (slurp "debug/client_id")
          client-secret (slurp "debug/secret")]
      (get-access-token client-id client-secret)))
  (with-open [rdr (clojure.java.io/reader "debug/fav_songs")]
    (->> (line-seq rdr)
      (map (fn [track-id] (get-track-label track-id auth-tok)))
      (sort)
      (println))))

;; OAUTH2 => http://leonid.shevtsov.me/post/oauth2-is-easy/