package com.booking.medical.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.booking.medical.models.dtos.DoctorDTO;
import com.booking.medical.models.dtos.ScheduleDTO;

public interface DoctorService {
    Page<DoctorDTO> GetAllByKeyword(String keyword, Pageable pageable);

    List<ScheduleDTO> GetSchedule(Long idDoctor);

}
