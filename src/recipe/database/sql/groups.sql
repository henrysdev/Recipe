-- :name create-group :! :n
-- :doc insert a new group
INSERT INTO groups (group_id, 
                    owner_uid, 
                    group_name)
       SELECT :group_id, 
              :owner_uid,
              :group_name
       WHERE NOT EXISTS (SELECT 1 FROM groups WHERE group_id = :group_id)


-- :name update-group
-- :doc update existing group if they exist.
UPDATE groups SET group_id = :group_id, 
                  owner_uid = :owner_uid, 
                  group_name = :group_name WHERE group_id = :group_id


-- :name group-exists?
-- :doc check if a group already exists
SELECT 1 FROM users WHERE group_id = :group_id
