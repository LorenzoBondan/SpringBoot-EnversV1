INSERT INTO tb_productor (name) VALUES ('Jonas');
INSERT INTO tb_productor (name) VALUES ('Marcos');

INSERT INTO tb_album (title, author, dt_create, productor_id) VALUES ('Nothing but the beat', 'David Guetta', '2012', 1);
INSERT INTO tb_album (title, author, dt_create, productor_id) VALUES ('Wild Ones', 'Flo Rida', '2012', 2);

INSERT INTO tb_music (title, time_duration, album_id) VALUES ('I can only imagine', 3.5, 1);
INSERT INTO tb_music (title, time_duration, album_id) VALUES ('Whistle', 4.2, 2);

INSERT INTO tb_user (name, email, password) VALUES ('Alex', 'alex@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (name, email, password) VALUES ('Maria', 'maria@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');

INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);