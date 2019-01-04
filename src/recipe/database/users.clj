(ns recipe.database.users
  (:gen-class)
  (:require [hugsql.core :as hugsql]))

(hugsql/def-db-fns "recipe/database/sql/users.sql")