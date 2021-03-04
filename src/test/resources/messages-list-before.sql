delete
from message;

alter sequence message_id_seq restart;

insert into message(text, tag, user_id)
values ('first', 'my-tag', 1),
       ('second', 'new', 1),
       ('third', 'my-tag', 1),
       ('fourth', 'hi', 1);

alter sequence message_id_seq restart with 10;