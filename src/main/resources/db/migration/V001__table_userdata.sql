create table userdata
(
    id serial not null
        constraint users_data_pk
            primary key,
    username varchar not null,
    password varchar not null,
    email varchar not null,
    name varchar not null,
    surname varchar not null,
    photo varchar
);



