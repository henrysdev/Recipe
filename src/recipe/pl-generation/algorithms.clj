(ns recipe.pl-generation.algorithms
  (:gen-class)
  (:require [clj-spotify.core :as spotify]
            [clj-spotify.util :as spotify-utils]
            [recipe.database :as database]
            [recipe.utils :as utils]))

(defn shared-tracks [leader-id group-id]
  (let member-ids (get-group group-id)
       save-lists (map get-saved-tracks member-ids)
       save-sets  (map set save-lists)
       ; workaround for being unable to figure out passing
       ; a collection of sets to a function that expects a 
       ; variable number of arguments... how to unpack?
       (reduce #(clojure.set/intersection save-sets))))