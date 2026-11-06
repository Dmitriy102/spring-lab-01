create table app_user (
    id            bigserial primary key,
    username      varchar(60)  not null unique,
    password_hash varchar(100) not null,
    enabled       boolean      not null default true
);
 
create table user_role (
    user_id bigint      not null references app_user (id),
    role    varchar(20) not null,
    primary key (user_id, role)
);
 
insert into app_user (username, password_hash) values
    ('user',  '$2b$12$6P7NemuXGYSr6h6DFWcaiucPNInmL9QzqrX6FtgrIbZXfxFY.s8KG'),
    ('admin', '$2b$12$7LRoevLvkqUz/JVKS7S9rOmuhkB0uroitwSCCxBWLjncBt1wnukp2');
 
insert into user_role (user_id, role)
    select id, 'USER'  from app_user;

insert into user_role (user_id, role)
    select id, 'ADMIN' from app_user where username = 'admin';