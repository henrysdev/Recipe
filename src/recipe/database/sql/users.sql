-- :name create-user :! :n
-- :doc insert new user.
INSERT INTO users (user_id, 
                   email,
                   display_name,
                   href,
                   refresh_token)
       SELECT :user_id, 
              :email, 
              :display_name, 
              :href, 
              :refresh_token
       WHERE NOT EXISTS (SELECT 1 FROM users WHERE user_id = :user_id)


-- :name update-user :! :n
-- :doc update existing user if they exist.
UPDATE users SET user_id = :user_id, 
                 email = :email, 
                 display_name = :display_name, 
                 href = :href, 
                 refresh_token = :refresh_token WHERE user_id = :user_id


-- :name user-exists?
-- :doc check if a user already exists
SELECT 1 FROM users WHERE user_id = :user_id