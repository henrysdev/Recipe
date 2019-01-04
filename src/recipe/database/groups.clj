(ns recipe.database.groups
  (:gen-class)
  (:require [hugsql.core :as hugsql]))

(hugsql/def-db-fns "recipe/database/sql/groups.sql")