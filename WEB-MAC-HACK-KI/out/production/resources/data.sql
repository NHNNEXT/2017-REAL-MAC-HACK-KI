INSERT INTO role (role_id, role) VALUES (1, 'admin');
INSERT INTO role (role_id, role) VALUES (2, 'user');
INSERT INTO role (role_id, role) VALUES (3, 'unconfirmed_user');

INSERT INTO chatting_room (chattingroom_id) VALUES (1);
INSERT INTO chatting_room (chattingroom_id) VALUES (2);
INSERT INTO chatting_room (chattingroom_id) VALUES (3);

INSERT INTO user (user_type, email, name, password, nationality, city, birthday, contents)
  VALUES ('Email', 'wkddngus5@naver.com', 'Jang Woohyeon', '$2a$10$dxou/N0NFgOFaOwiQXtytOvrF8ptKQsuxXnqVrIN2t2OvVY/qlMWO',
          'KR', 'Incheon, South Korea', '1995-05-20',
          'Hi there! I\’m a student studying computer science and developing this service.<br>
          I don\’t speak foreign language well, but I want to meet and experience various people.<br>
          If you want to experience real Korean\’s life, not the information in guide, please follow me:)<br><br>
          p.s)儉而不陋 華而不侈. it means \’frugal but not poor, fancy but not extravagant\’.<br>
          it\’s korean beauty style. Let me show you beauty the beauty of Korea.'); -- pw: 123456

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (1, 2);

INSERT INTO user_chatting_room (user_id, chattingroom_id) VALUES (1, 1);
INSERT INTO user_chatting_room (user_id, chattingroom_id) VALUES (1, 2);
INSERT INTO user_chatting_room (user_id, chattingroom_id) VALUES (1, 3);


INSERT INTO user (user_type, email, name, password, nationality, city, birthday, contents)
  VALUES ('Email', 'bbq9234@naver.com', 'bbq923', '$2a$10$dxou/N0NFgOFaOwiQXtytOvrF8ptKQsuxXnqVrIN2t2OvVY/qlMWO',
          'KR', 'Yongin, South Korea', '1994-09-23',
          'Howdy! Here comes your best trip mate to discover around Korea with. Mostly spent my time guiding those coming<br>
           to Korea for the first time, I mainly enjoy introducing Korean traditional food and sports. As being one of the developers<br>
           for this service, feel free to point out any inconvenience you faced while using this service.'); -- pw: 123456

INSERT INTO user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 2);

INSERT INTO user_chatting_room (user_id, chattingroom_id) VALUES (2, 1);

INSERT INTO user (user_type, email, name, password, nationality, city, birthday, contents)
  VALUES ('Email', 'dev.yongjai@gmail.com', 'Yongjae Kwon', '$2a$10$dxou/N0NFgOFaOwiQXtytOvrF8ptKQsuxXnqVrIN2t2OvVY/qlMWO',
          'KR', 'Seoul, South Korea', '1994-05-23',
          'Hi, I\’m yongjae. Like other introductions, I\’m also studying computer science and developing iOS clients.<br>
           Actually I\’m not good at communicate in English. But we are living in the 21st century, and there are technologies<br>
           that will fill the shortage of my language skills. So, I do not worry.'); -- pw: 123456

INSERT INTO user_role (user_id, role_id) VALUES (3, 1);
INSERT INTO user_role (user_id, role_id) VALUES (3, 2);

INSERT INTO user_chatting_room (user_id, chattingroom_id) VALUES (3, 2);

INSERT INTO user (user_type, email, name, password, nationality, city, birthday, contents)
  VALUES ('Email', 'LeoFab@gmail.com', 'Leonardo Fabbrica', '$2a$10$dxou/N0NFgOFaOwiQXtytOvrF8ptKQsuxXnqVrIN2t2OvVY/qlMWO',
          'IT', 'Pomezia, Lazio, Italy', '1986-11-23',
          'I\’m an italian boy, i like movies, TV series, manga, i like the sea, I\’m a sportive guy, i like to go to the gym or swim<br>
           in the pool, ride a bike. Also like animals, I always had cats, so now I have a cat which is half persian.'); -- pw: 123456

INSERT INTO user_role (user_id, role_id) VALUES (4, 1);
INSERT INTO user_role (user_id, role_id) VALUES (4, 2);

INSERT INTO user_chatting_room (user_id, chattingroom_id) VALUES (4, 3);

INSERT INTO user (user_type, email, name, password, nationality, city, birthday, contents)
  VALUES ('Email', 'JZhang@gmail.com', 'Julie Zhang', '$2a$10$dxou/N0NFgOFaOwiQXtytOvrF8ptKQsuxXnqVrIN2t2OvVY/qlMWO',
          'CN', 'Guangzhou, Guangdong, China', '1994-03-27',
          'I am a 24-year-old girl from China. I just finished my study of college in China and I would like to travel to Europe. I am kenn on <br>
           making friends and traveling. I think Couchsurfing is an excellent way to make friends and enjoy life. By learning different cultures<br>
           and custom, I hope I can get experience from it. I really enjoy meeting new friends, and value it more than visiting the places of interest<br>
           sometimes.'); -- pw: 123456

INSERT INTO user_role (user_id, role_id) VALUES (5, 1);
INSERT INTO user_role (user_id, role_id) VALUES (5, 2);

INSERT INTO locals_article (locals_article_id, title, contents, location, user_id)
  VALUES (1, 'Seoul palace tour', 'There are many palaces in Joseon dinesty', 'Seoul, South Korea', 1);

INSERT INTO locals_article (locals_article_id, title, contents, location, user_id)
  VALUES (2, 'Incheon tour', 'find out what you can do in Incheon.', 'Seoul, South Korea', 1);

INSERT INTO locals_article (locals_article_id, title, contents, location, user_id)
  VALUES (3, 'trip to Everland', 'Visit the best amusement park in Korea', 'Yongin-si, Gyeonggi-do, South Korea', 2);

INSERT INTO locals_article (locals_article_id, title, contents, location, user_id)
  VALUES (4, 'Busan Film Festival', 'Join in one of the biggest international film festival. Meet celebrities, feel the mood, dive deep into the screens', 'Busan, South Korea', 4);

INSERT INTO locals_article (locals_article_id, title, contents, location, user_id)
  VALUES (5, 'DMZ Tour', 'Visit the symbol of last remaining divided nation in world.', 'Paju-si, Gyeonggi-do, South Korea', 5);

INSERT INTO star (from_id, to_id) VALUES (2, 1);
INSERT INTO star (from_id, to_id) VALUES (2, 3);
INSERT INTO star (from_id, to_id) VALUES (4, 5);
INSERT INTO star (from_id, to_id) VALUES (1, 4);
INSERT INTO star (from_id, to_id) VALUES (2, 5);

INSERT INTO avatar (avatar_id, user_id) VALUES (1, 1);
INSERT INTO avatar (avatar_id, user_id) VALUES (2, 2);
INSERT INTO avatar (avatar_id, user_id) VALUES (3, 3);
INSERT INTO avatar (avatar_id, user_id) VALUES (4, 4);
INSERT INTO avatar (avatar_id, user_id) VALUES (5, 5);

INSERT INTO photo (photo_id, article_id) VALUES (1, 1);
INSERT INTO photo (photo_id, article_id) VALUES (2, 1);
INSERT INTO photo (photo_id, article_id) VALUES (3, 2);
INSERT INTO photo (photo_id, article_id) VALUES (4, 3);
INSERT INTO photo (photo_id, article_id) VALUES (5, 4);
INSERT INTO photo (photo_id, article_id) VALUES (6, 4);
INSERT INTO photo (photo_id, article_id) VALUES (7, 5);
INSERT INTO photo (photo_id, article_id) VALUES (8, 5);

INSERT INTO language (user_id, level, name) VALUES (1, 2, 1);
INSERT INTO language (user_id, level, name) VALUES (1, 1, 2);
INSERT INTO language (user_id, level, name) VALUES (1, 0, 3);

INSERT INTO interest (interest_name) VALUES (1);
INSERT INTO interest (interest_name) VALUES (2);
INSERT INTO interest (interest_name) VALUES (3);

INSERT INTO user_interest (user_id, interest_id) VALUES (1, 1);
INSERT INTO user_interest (user_id, interest_id) VALUES (1, 2);

INSERT INTO theme (theme_name) VALUES (0);
INSERT INTO theme (theme_name) VALUES (1);
INSERT INTO theme (theme_name) VALUES (2);
INSERT INTO theme (theme_name) VALUES (3);
INSERT INTO theme (theme_name) VALUES (4);
INSERT INTO theme (theme_name) VALUES (5);
INSERT INTO theme (theme_name) VALUES (6);
INSERT INTO theme (theme_name) VALUES (7);

INSERT INTO user_theme (user_id, theme_id) VALUES (1, 1);
INSERT INTO user_theme (user_id, theme_id) VALUES (1, 3);
INSERT INTO user_theme (user_id, theme_id) VALUES (2, 2);
INSERT INTO user_theme (user_id, theme_id) VALUES (3, 2);
INSERT INTO user_theme (user_id, theme_id) VALUES (3, 4);
INSERT INTO user_theme (user_id, theme_id) VALUES (4, 1);
INSERT INTO user_theme (user_id, theme_id) VALUES (4, 5);
INSERT INTO user_theme (user_id, theme_id) VALUES (5, 1);
INSERT INTO user_theme (user_id, theme_id) VALUES (5, 6);
