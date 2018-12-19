(ns recipe.db)

(def db
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :subname "//localhost:5432/test_db"
   :user "hewarren"
   :password (slurp "debug/db_password")
   ;:sslmode "prefer"
   })