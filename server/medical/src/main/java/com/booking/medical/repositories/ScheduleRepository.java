package com.booking.medical.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.booking.medical.models.entities.Schedule;

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, String> {

}
