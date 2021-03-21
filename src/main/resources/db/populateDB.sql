DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM restaurants;
DELETE FROM menus;
DELETE FROM dishes;
DELETE FROM votes;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('user', 'user@user.ru', '{noop}password'), --100_000
       ('admin', 'admin@admin.com', '{noop}admin'); --100_001

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001),
       ('ROLE_USER', 100001);

INSERT INTO restaurants (name, description, address)
VALUES ('Restaurant name 1', 'Restaurant description 1', 'Restaurant address 1'), --100_002
       ('Restaurant name 2', 'Restaurant description 2', 'Restaurant address 2'), --100_003
       ('Restaurant name 3', 'Restaurant description 3', 'Restaurant address 3'); --100_004

INSERT INTO menus (date, restaurant_id)
VALUES ('2000-01-01', 100002), --100_005
       ('2000-01-01', 100003), --100_006
       ('2000-01-01', 100004); --100_007

INSERT INTO dishes (name, price, menu_id)
VALUES ('Dish name 1', 10000, 100005),
       ('Dish name 2', 20000, 100005),
       ('Dish name 3', 30000, 100005),
       ('Dish name 4', 60000, 100006),
       ('Dish name 5', 70000, 100006),
       ('Dish name 6', 80000, 100006),
       ('Dish name 7', 100000, 100007),
       ('Dish name 8', 110000, 100007),
       ('Dish name 9', 120000, 100007);

INSERT INTO votes (date_time, user_id, restaurant_id)
VALUES ('2000-01-01 10:00:00', 100000, 100002),
       ('2000-01-01 9:00:00', 100001, 100003);

