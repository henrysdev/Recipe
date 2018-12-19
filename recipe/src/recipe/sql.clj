(ns recipe.sql
  (:gen-class)
  (:require [hugsql.core :as hugsql]))

(hugsql/def-db-fns "recipe/sql/users.sql")