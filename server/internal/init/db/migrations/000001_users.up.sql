create table if not exists users (
    user_id serial primary key,
    fullname varchar(50) not null,
    email varchar(100) not null unique,
    password varchar(255) not null,
    role varchar(20) default 'user',
    gender varchar(10) not null check (gender in ('Nam', 'Nữ', 'Khác')),
    birthday date not null,
    created_at timestamp with time zone default current_timestamp,
    updated_at timestamp with time zone default current_timestamp
);