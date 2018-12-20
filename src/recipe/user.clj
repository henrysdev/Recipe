(ns recipe.user
  (:gen-class)
  (:require [clj-spotify.core :as spotify]
            [clj-spotify.util :as spotify-utils]
            [recipe.database :as database]
            [recipe.utils :as utils]))

(defn login [tokens]
  (def access-token (:access tokens))
  (let [prof-obj (spotify/get-current-users-profile {} access-token)
        user     {:user_id       (:id prof-obj)
                  :email         (:email prof-obj)
                  :display_name  (:display_name prof-obj)
                  :href          (:href prof-obj)
                  :refresh_token (:refresh tokens)}]
        (database/user-login user))
  ;(utils/paginate spotify/get-users-saved-tracks {:limit 50} access-token)
  (slurp "public/menu.html"))
