delete
from user_role;
delete
from usr;

insert into usr (id, active, password, username)
values (1, true, '$2a$08$dGN86CU0HWDzXeyRoGMQHeNlW11719NRuaruOXx0Y5WS9o4m5jQ5m', 'user'),
       (2, true, '$2a$08$dGN86CU0HWDzXeyRoGMQHeNlW11719NRuaruOXx0Y5WS9o4m5jQ5m', 'mike');

insert into user_role(user_id, roles)
values (1, 'USER'),
       (1, 'ADMIN'),
       (2, 'USER');