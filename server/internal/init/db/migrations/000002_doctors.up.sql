create table if not exists doctors(
    doctor_id serial primary key,
    fullname varchar(50) not null,
    title varchar(50) not null,
    gender varchar(10) not null check (gender in ('Nam', 'Nữ', 'Khác')),
    specialization varchar(100) not null,
    price numeric(10, 2) not null default 150.0,
    isDeleted boolean default false,
    created_at timestamp with time zone default current_timestamp,
    updated_at timestamp with time zone default current_timestamp
);