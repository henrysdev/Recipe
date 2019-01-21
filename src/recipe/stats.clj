(ns recipe.stats
  (:gen-class)
  (:require [clj-spotify.core :as spotify]
            [clj-spotify.util :as spotify-utils]
            [recipe.database :as database]
            [recipe.authorization :as authorization]
            [recipe.utils :as utils]))

(defn generate [refresh-token]
  (let [tokens (authorization/refresh-tokens refresh-token)
        access-token (first tokens)]))

