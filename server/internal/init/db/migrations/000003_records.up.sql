create table if not exists records (
    record_id serial primary key,
    user_id int not null,
    fullname varchar(50) not null,
    birthday date not null,
    gender varchar(10) not null check (gender in ('Nam', 'Nữ', 'Khác')),
    identificationcard varchar(12) not null unique check (length(identificationcard) = 12),
    healthinsurance varchar(15) unique check (length(healthinsurance) = 15 or healthinsurance is null),
    phone varchar(15) not null unique check (length(phone) = 10),
    address varchar(255) not null,

    created_at timestamp with time zone default current_timestamp,
    updated_at timestamp with time zone default current_timestamp,

    constraint fk_user foreign key (user_id) references users(user_id) on delete cascade
);