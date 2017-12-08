INSERT INTO party_guest (name, email, age, gender, language, theme, attraction) VALUES ('Adam', 'email', 22, 'male', 'English', 'theme', 'attraction');

INSERT INTO role (role_id, role) VALUES (1, 'admin');
INSERT INTO role (role_id, role) VALUES (2, 'user');
INSERT INTO role (role_id, role) VALUES (3, 'unconfirmed_user');

INSERT INTO user (email, name, password) VALUES ('email@email.com', 'name', '$2a$10$jZrabsOosN30mbzdFF7zIOX6UzqGmNL3uTbe0pmJvB6ZA44Bp6LS.'); -- pw: aaaaa

INSERT INTO user_role (user_id, role_id) VALUES (1, 2);