create table user
(
    id   integer      not null auto_increment,
    name varchar(255) not null,

    constraint user_pk primary key (id)
);


create table product
(
    id   integer      not null auto_increment,
    name varchar(255) not null,

    constraint product_pk primary key (id)
);

