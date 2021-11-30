-- insert roles
insert into roles(id, name) values(1,'ROLE_USER');
insert into roles(id, name) values(2,'ROLE_MODERATOR');
insert into roles(id, name) values(3,'ROLE_ADMIN');

-- insert mock users password (123456)
insert into users(id, email, password, permissions, username) values (1, 'hossain@gmail.com',
'$2a$10$dIoKICLPNQEAgMAfB/ULuev4IjHZ8Zp5cGd1v6uG4Ftfuw3Qnj2u2', 'ACCESS_TEST1,ACCESS_TEST3', 'hossain');

-- set roles for user
insert into role_user(user_id, role_id) values (1, 1);
insert into role_user(user_id, role_id) values (1, 2);
insert into role_user(user_id, role_id) values (1, 3);