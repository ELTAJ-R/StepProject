create table messages
(
    id serial not null,
    sender varchar not null,
    receiver varchar not null,
    message text not null,
    date varchar not null
);


create unique index messages_id_uindex
    on messages (id);

