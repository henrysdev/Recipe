(ns recipe.user
  (:gen-class)
  (:require [clj-spotify.core :as spotify]
            [clj-spotify.util :as spotify-utils]
            [clj-http.client :as http]
            [recipe.authorization :as auth]
            [recipe.database :as db]))

(defn paginate [accum page-obj acc-tok]
  (let [accum     (concat accum (:items page-obj))
        next-page (:next page-obj)]
    (if (nil? next-page)
      accum
      (let [resp (http/get next-page {:oauth-token acc-tok
                                      :as          :json})]
        (paginate accum (:body resp) acc-tok)))))

(defn get-library [acc-tok]
  (let [page-obj    (spotify/get-users-saved-tracks {:limit 50} acc-tok)
        track-objs  (map #(:track %1) (paginate [] page-obj acc-tok))
        pop-sorted  (sort-by #(:popularity %1) > track-objs)
        track-names (map #(:name %1) pop-sorted)]
    track-names))

(defn- get-user-id [acc-tok]
  (let [prof-obj (spotify/get-current-users-profile {} acc-tok)
        user-id  (:id prof-obj)]
        user-id))

(defn process [user]
  (def acc-tok (:access user))
  (def ref-tok (:refresh user))
  (db/user-login (get-user-id acc-tok) ref-tok)
  ;(println (auth/refresh-tokens ref-tok))
  (get-library acc-tok))