(ns recipe.user
  (:gen-class)
  (:require [clj-spotify.core :refer :all]
            [clj-spotify.util :refer :all]
            [clj-http.client :as http]))

(defn paginate [accum page-obj acc-tok]
  (let [accum     (concat accum (:items page-obj))
        next-page (:next page-obj)]
    (if (nil? next-page)
      accum
      (let [resp (http/get next-page {:oauth-token acc-tok
                                      :as :json})]
        (paginate accum (:body resp) acc-tok)))))

(defn get-library [acc-tok]
  (let [page-obj    (get-users-saved-tracks {:limit 50} acc-tok)
        track-objs  (map #(:track %1) (paginate [] page-obj acc-tok))
        pop-sorted  (sort-by #(:popularity %1) > track-objs)
        track-names (map #(:name %1) pop-sorted)]
    track-names))

(defn process [user]
  (def acc-tok (:access user))
  (def ref-tok (:refresh user))
  (get-library acc-tok))

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