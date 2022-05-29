INSERT INTO `users` (username, password, is_active, email) VALUES ('user1','12345', 1,'user1@personal.com');
INSERT INTO `users` (username, password, is_active, email) VALUES ('admin1','12345', 1, 'admin1@personal.com');

INSERT INTO `roles` (name) VALUES ('ROLE_USER');
INSERT INTO `roles` (name) VALUES ('ROLE_ADMIN');

INSERT INTO `users_roles` (user_id, role_id) VALUES (1, 1);
INSERT INTO `users_roles` (user_id, role_id) VALUES (2, 2);
INSERT INTO `users_roles` (user_id, role_id) VALUES (2, 1);
