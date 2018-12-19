-- :name users-all
-- :command :query
-- :result n
-- :doc select all users with all attributes
SELECT * FROM users

-- :name user-login :! :n
-- :doc insert new user
INSERT INTO users (id, refresh)
VALUES (:id, :refresh)