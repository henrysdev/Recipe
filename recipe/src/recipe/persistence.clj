(ns recipe.persistence
  (:gen-class)
  (:require [recipe.db :refer [db]]
            [recipe.sql :as sql]))

(defn user-login [user]
  (println "inserting refresh token into DB by lookup User ID")
  (sql/user-login db user))