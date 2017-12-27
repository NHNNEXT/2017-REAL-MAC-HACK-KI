INSERT INTO role (role_id, role) VALUES (1, 'admin');
INSERT INTO role (role_id, role) VALUES (2, 'user');
INSERT INTO role (role_id, role) VALUES (3, 'unconfirmed_user');

INSERT INTO user (email, name, password, nationality, city, birthday, contents)
  VALUES ('wkddngus5@naver.com', 'Jang Woohyeon', '$2a$10$SRQgq4tJTr2IW5DoLT2AB.lsj0jACBX59xZsY/3yL7inpqjfFbhgS',
          'KR', 'Incheon, South Korea', '1995-05-20',
          'Hi there! I\’m a student studying computer science and developing this service.<br>
          I don\’t speak foreign language well, but I want to meet and experience various people.<br>
          If you want to experience real Korean\’s life, not the information in guide, please follow me:)<br><br>
          p.s)儉而不陋 華而不侈. it means \’frugal but not poor, fancy but not extravagant\’.<br>
          it\’s korean beauty style. Let me show you beauty the beauty of Korea.'); -- pw: Q

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (1, 2);

INSERT INTO locals_article (locals_article_id, title, contents, location, user_id)
  VALUES (1, 'Seoul palace tour', 'There are many palaces in Joseon dinesty', 'Seoul, South Korea', 1);
