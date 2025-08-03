create table if not exists tickets (
    ticket_id serial primary key,
    serial_number varchar(50) not null unique,
    category varchar(50) default 'vip' check (category in ('general', 'vip')),
    area varchar(50) not null,
    clinic varchar(100) not null,
    specialist varchar(100) not null,
    schedule_id int not null,
    date_clinic date not null,
    time_clinic varchar(50) not null,
    price numeric(10, 2) not null default 150.0,
    status varchar(20) default 'waiting' check (status in ('waiting', 'completed', 'cancelled')),
    fullname varchar(50) not null,
    record_id int not null,
    created_at timestamp with time zone default current_timestamp,

    constraint fk_record foreign key (record_id) references records(record_id) on delete cascade,
    constraint fk_schedule foreign key (schedule_id) references schedules(schedule_id) on delete cascade
);