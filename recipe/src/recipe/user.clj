(ns recipe.user
  (:gen-class)
  (:require [clj-spotify.core :as spotify]
            [clj-spotify.util :as spotify-utils]
            [clj-http.client :as http]
            [recipe.authorization :as auth]
            [recipe.persistence :as persist]
            [recipe.utils :as utils]))

(defn login [tokens]
  (def access-token (:access tokens))
  (let [prof-obj (spotify/get-current-users-profile {} access-token)
        user     {:user_id       (:id prof-obj)
                  :email         (:email prof-obj)
                  :display_name  (:display_name prof-obj)
                  :href          (:href prof-obj)
                  :refresh_token (:refresh tokens)}]
        (persist/user-login user))
  (utils/fetch-all spotify/get-users-saved-tracks {:limit 50} access-token)
  (slurp "public/menu.html"))
