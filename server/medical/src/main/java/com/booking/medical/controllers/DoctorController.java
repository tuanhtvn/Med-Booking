package com.booking.medical.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.booking.medical.common.Response;
import com.booking.medical.models.dtos.DoctorDTO;
import com.booking.medical.models.dtos.ScheduleDTO;
import com.booking.medical.services.DoctorService;

@RestController
@RequestMapping("/api")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
    private Response resp = new Response();

    @GetMapping("/doctors")
    public ResponseEntity<Response> GetAll(
            @RequestParam(name = "keyword", defaultValue = "", required = false) String keyword,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(page, size, sort);

        Page<DoctorDTO> doctors = doctorService.GetAllByKeyword(keyword, pageable);

        resp.setMessage("Tải dữ liệu thành công");
        resp.setStatus(HttpStatus.OK);
        resp.put("doctors", doctors.getContent());
        resp.put("totalPages", doctors.getTotalPages());
        resp.put("resPerPage", doctors.getSize());
        return ResponseEntity.status(resp.getStatus()).body(resp);
    }

    @GetMapping("/doctors/scheduled/{id}")
    public ResponseEntity<Response> GetScheduledDoctor(@PathVariable("id") String doctorID) {
        List<ScheduleDTO> schedules = doctorService.GetSchedule(doctorID);

        resp.setMessage("Tải dữ liệu thành công");
        resp.setStatus(HttpStatus.OK);

        resp.put("doctorId", doctorID);
        resp.put("scheduled", schedules);

        return ResponseEntity.status(resp.getStatus()).body(resp);
    }
}