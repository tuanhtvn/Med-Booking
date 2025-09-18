package com.booking.medical.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.booking.medical.exception.CustomException;
import com.booking.medical.models.dtos.DoctorDTO;
import com.booking.medical.models.dtos.ScheduleDTO;
import com.booking.medical.models.entities.Doctor;
import com.booking.medical.repositories.DoctorRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DoctorServiceImp implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<DoctorDTO> GetAllByKeyword(String keyword, Pageable pageable) {
        Doctor probe = new Doctor();
        probe.setFullName(keyword);

        ExampleMatcher matcher = ExampleMatcher.matching();

        matcher = matcher
                .withIgnorePaths("id", "specialty", "schedule", "price", "gender", "title")
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Doctor> example = Example.of(probe, matcher);

        Page<Doctor> doctors = doctorRepository.findAll(example, pageable);
        return doctors.map(doctor -> {
            return modelMapper.map(doctor, DoctorDTO.class);
        });
    }

    @Override
    public List<ScheduleDTO> GetSchedule(String idDoctor) {
        log.info("Fin by doctor id: {}", idDoctor);
        Doctor doctor = doctorRepository.findById(idDoctor).orElseThrow(() -> {
            throw new CustomException("Hệ thống không tìm thấy thông tin bác sĩ này", HttpStatus.NOT_FOUND);
        });
        Calendar now = Calendar.getInstance();
        int currentDay = now.get(Calendar.DAY_OF_MONTH);
        int currentMonth = now.get(Calendar.MONTH) + 1;
        int currentYear = now.get(Calendar.YEAR);

        int nextMonth = currentMonth + 1 > 12 ? 1 : currentMonth + 1;
        int nextMonthYear = nextMonth == 1 ? currentYear + 1 : currentYear;

        Calendar itemCal = Calendar.getInstance();
        List<ScheduleDTO> schedulesDTO = doctor.getSchedule().stream().filter(item -> {
            itemCal.setTime(Date.from(item.getDate()));

            int itemDay = itemCal.get(Calendar.DAY_OF_MONTH);
            int itemMonth = itemCal.get(Calendar.MONTH) + 1;
            int itemYear = itemCal.get(Calendar.YEAR);

            boolean isMatch = (itemDay > currentDay && itemMonth == currentMonth && itemYear == currentYear) ||
                    (itemMonth == nextMonth && itemYear == nextMonthYear);
            return isMatch;
        }).map(item -> {
            return modelMapper.map(item, ScheduleDTO.class);
        }).toList();
        return schedulesDTO;
    }
}
