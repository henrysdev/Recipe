(ns recipe.database.group-members
  (:gen-class)
  (:require [hugsql.core :as hugsql]))

(hugsql/def-db-fns "recipe/database/sql/group_members.sql")