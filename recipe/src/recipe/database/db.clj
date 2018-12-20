(ns recipe.database.db)

(def db {:classname "org.postgresql.Driver"
         :subprotocol "postgresql"
         :subname "//localhost:5432/recipe_db"
         :user "hewarren"
         :password (slurp "debug/db_password")})
