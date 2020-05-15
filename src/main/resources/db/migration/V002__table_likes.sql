create table likes
(
    current varchar not null,
    liked varchar not null,
    constraint userinfo_pk
        primary key (current, liked)
);


