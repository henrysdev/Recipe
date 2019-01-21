(ns recipe.user
  (:gen-class)
  (:require [clj-spotify.core :as spotify]
            [clj-spotify.util :as spotify-utils]
            [recipe.database :as database]
            [recipe.stats :as stats]
            [recipe.utils :as utils]))

(defn login [tokens]
  (let [access-token (:access tokens)
        prof-obj (spotify/get-current-users-profile {} access-token)
        user     {:user_id       (:id prof-obj)
                  :email         (:email prof-obj)
                  :display_name  (:display_name prof-obj)
                  :href          (:href prof-obj)
                  :refresh_token (:refresh tokens)}
        cookies  {:session_id (utils/rand-str 64)}]
        (if (database/user-login user) cookies nil)))
  ;(stats/generate (:refresh tokens)))
  ;(utils/paginate spotify/get-users-saved-tracks {:limit 50} access-token)
  ;(slurp "public/menu.html"))
