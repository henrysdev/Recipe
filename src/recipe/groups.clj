(ns recipe.groups
  (:gen-class)
  (:require [clj-spotify.core :as spotify]
            [clj-spotify.util :as spotify-utils]
            [recipe.database :as database]
            [recipe.utils :as utils]))

(defn create-group [user-id group-name]
    (let group {:owner_uid  user-id
                :group_name group-name}
        (database/new-group group)))