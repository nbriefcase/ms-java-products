INSERT INTO `users` (username, password, is_active, email) VALUES ('user1','$2a$10$81Szl60E17xzyQ16yseQT.Urm4EgKvO9CCH3inHYx/hN/3N.d1OpW', 1,'user1@personal.com');
INSERT INTO `users` (username, password, is_active, email) VALUES ('admin1','$2a$10$dQj15j7dxfj5KEZ1Vhbm1eqxkto3R.84LrcfP/qwysvGXDaSWakQe', 1, 'admin1@personal.com');

INSERT INTO `roles` (name) VALUES ('ROLE_USER');
INSERT INTO `roles` (name) VALUES ('ROLE_ADMIN');

INSERT INTO `users_roles` (user_id, role_id) VALUES (1, 1);
INSERT INTO `users_roles` (user_id, role_id) VALUES (2, 2);
INSERT INTO `users_roles` (user_id, role_id) VALUES (2, 1);
