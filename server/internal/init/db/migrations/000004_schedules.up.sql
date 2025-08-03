create table if not exists schedules(
    schedule_id serial primary key,
    doctor_id int not null,
    appointment_date date not null,
    appointment_time time not null,
    status varchar(20) default 'pending' check (status in ('pending', 'confirmed', 'cancelled')),
    created_at timestamp with time zone default current_timestamp,
    updated_at timestamp with time zone default current_timestamp,

    constraint fk_doctor foreign key (doctor_id) references doctors(doctor_id) on delete cascade
)