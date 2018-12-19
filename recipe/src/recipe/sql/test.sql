-- :name user-login :! :n
-- :doc insert new user
INSERT INTO users (user_id, 
                   email, 
                   display_name, 
                   href, 
                   refresh_token)
VALUES (:user_id, 
        :email, 
        :display_name, 
        :href, 
        :refresh_token)