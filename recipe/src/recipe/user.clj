(ns recipe.user
  (:gen-class)
  (:require [clj-spotify.core :refer :all]
            [clj-spotify.util :refer :all]))



(defn process [user]
  (clojure.pprint/pprint user))
;   (println (:access_token  user))
;   (println (:refresh_token user)))

; (defn get-track-label [track-id auth-tok]
;   (let [track (get-a-track  {:id track-id} auth-tok)
;         album (get-an-album {:id (:id (:album track))} auth-tok)]
;         (:label album)))


; (defn -main [& args]
;   (def auth-tok      (slurp "debug/temp_auth_tok"))
;   (def client-id     (slurp "debug/client_id"))
;   (def client-secret (slurp "debug/secret"))
;   (println (get-users-saved-tracks {:limit 50} auth-tok)))


; (defn -main [& args]
;   (def auth-tok
;     (let [client-id     (slurp "debug/client_id")
;           client-secret (slurp "debug/secret")]
;       (get-access-token client-id client-secret)))
;   (with-open [rdr (clojure.java.io/reader "debug/fav_songs")]
;     (->> (line-seq rdr)
;       (map (fn [track-id] (get-track-label track-id auth-tok)))
;       (sort)
;       (println))))