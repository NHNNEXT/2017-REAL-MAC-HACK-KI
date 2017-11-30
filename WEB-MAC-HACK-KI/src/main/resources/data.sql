INSERT INTO party_guest (name, email, age, gender, language, theme, attraction) VALUES ('Adam', 'email', 22, 'male', 'English', 'theme', 'attraction');

INSERT INTO role (role_id, role) VALUES (1, 'admin');
INSERT INTO role (role_id, role) VALUES (2, 'user');
INSERT INTO role (role_id, role) VALUES (3, 'unconfirmed_user');

INSERT INTO user (user_id, name, email, password, age, credit_point) VALUES (1, 'bbq923', 'bbq9234@gmail.com', '$2a$10$kt5F/aobdTF4Ldz83oARneiXUGU.vYxZsT6iQhqd7oy1mPaPi/.Iy', 24, 0);
INSERT INTO user (user_id, name, email, password, age, credit_point) VALUES (2, 'wkddngus5', 'wkddngus5@naver.com', '$2a$10$kt5F/aobdTF4Ldz83oARneiXUGU.vYxZsT6iQhqd7oy1mPaPi/.Iy', 23, 0);

INSERT INTO user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO user_role (user_id, role_id) VALUES (2, 2);