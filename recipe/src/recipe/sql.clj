(ns recipe.sql
  (:gen-class)
  (:require [hugsql.core :as hugsql]))

; CREATE DATABASE FROM SQL FILE
(hugsql/def-db-fns "recipe/sql/test.sql")