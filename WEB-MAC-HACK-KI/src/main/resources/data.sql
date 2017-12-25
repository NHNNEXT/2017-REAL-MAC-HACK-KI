INSERT INTO role (role_id, role) VALUES (1, 'admin');
INSERT INTO role (role_id, role) VALUES (2, 'user');
INSERT INTO role (role_id, role) VALUES (3, 'unconfirmed_user');

INSERT INTO user (email, name, password, nationality) VALUES ('email@email.com', 'name', '$2a$10$jZrabsOosN30mbzdFF7zIOX6UzqGmNL3uTbe0pmJvB6ZA44Bp6LS.', 'KR'); -- pw: aaaaa

INSERT INTO user_role (user_id, role_id) VALUES (1, 2);

INSERT INTO locals_article (locals_article_id, title, contents, location, user_id) VALUES (1, 'TITLE', 'HI IM CONTENTS', 'Seoul, South Korea', 1);