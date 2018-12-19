(ns recipe.database
  (:gen-class)
  (:require [recipe.db :refer [db]]
            [recipe.sql :as sql]))

; (def pg-db {:dbtype "postgresql"
;             :dbname "test_db"
;             :host "localhost"
;             :user "hewarren"
;             :password (slurp "debug/db_password")
;             :ssl false})

; (def pg-db {:classname "org.postgresql.Driver"
;             :subprotocol "postgresql"
;             :subname "//localhost:5432"
;             ;; Not needed for a non-secure local database...
;             ;; :user "bilbo"
;             ;; :password "secret"
;             })

; (def pg-db
;   {:classname "org.postgresql.Driver"
;    :subprotocol "postgresql"
;    :subname "//localhost:5432/test_db"
;    :user "hewarren"
;    :password (slurp "debug/db_password")
;    :sslmode "none"
;    })


(defn user-login [id ref-tok]
  (println "inserting refresh token into DB by lookup User ID")
  (println (str "id: " id "\nref-tok: " ref-tok))
  (sql/user-login db {:id      id 
                      :refresh ref-tok}))
  
  ; (jdbc/insert! pg-db :users
  ;   {:id      id 
  ;    :refresh ref-tok}))