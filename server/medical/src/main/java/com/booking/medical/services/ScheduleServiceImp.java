package com.booking.medical.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.medical.models.entities.Schedule;
import com.booking.medical.repositories.ScheduleRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScheduleServiceImp implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public void Save(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

}
