(ns recipe.database
  (:gen-class)
  (:require [recipe.database.db :refer [db]]
            [recipe.database.users :as users]
            [recipe.database.groups :as groups]))

(defn user-login [user]
  (println "inserting refresh token into DB by lookup User ID")
  (if (= () (users/user-exists? db {:user_id (:user_id user)}))
    (users/create-user db user)
    (users/update-user db user)))

(defn new-group [group]
  (println "inserting new group into DB by lookup group ID")
  (groups/create-group db group))