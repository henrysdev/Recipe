-- :name add-group-member
-- :doc insert new group member
INSERT INTO group_members (group_id, 
                           user_id)


-- :name remove-all-group-members
-- :doc delete all members in a group
DELETE * FROM group_members WHERE group_id = :group_id


-- :name remove-group-member
-- :doc delete a member from a group
DELETE 1 FROM group_members WHERE group_id = :group_id,
                                  user_id  = :user_id


-- :name group-member-exists?
-- :doc check if a group member already exists
SELECT 1 FROM group_members WHERE group_id = :group_id,
                                  user_id  = :user_id
