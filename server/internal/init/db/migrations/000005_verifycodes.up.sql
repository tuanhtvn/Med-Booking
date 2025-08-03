create table if not exists verifycodes (
    id serial primary key,
    user_id int unique not null,
    code varchar(6) not null,
    expires_at timestamp with time zone not null,
    created_at timestamp with time zone default current_timestamp,

    constraint fk_user foreign key (user_id) references users(user_id) on delete cascade
);