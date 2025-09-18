package com.booking.medical.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.booking.medical.common.Response;
import com.booking.medical.models.dtos.CreateRecordInputDTO;
import com.booking.medical.models.dtos.RecordDTO;
import com.booking.medical.services.RecordService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class RecordController {
    @Autowired
    private RecordService recordService;
    private Response resp = new Response();

    @GetMapping("/records")
    public ResponseEntity<Response> RecordGetAll(
            @RequestParam(name = "keyword", defaultValue = "", required = false) String keyword,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Page<RecordDTO> records = recordService.RecordGetAll(keyword, page, size);

        resp.setMessage("Tải dữ liệu thành công");
        resp.setStatus(HttpStatus.OK);
        resp.put("records", records.getContent());
        resp.put("count", records.getTotalElements());
        resp.put("resPerPage", records.getSize());
        return ResponseEntity.status(resp.getStatus()).body(resp);
    }

    @PostMapping("/records")
    public ResponseEntity<Response> Create(@RequestBody @Valid CreateRecordInputDTO createRecordInputDTO) {
        recordService.RecordCreate(createRecordInputDTO);
        resp.setMessage("Tạo hồ sơ khám bệnh thành công");
        resp.setStatus(HttpStatus.CREATED);
        return ResponseEntity.status(resp.getStatus()).body(resp);
    }
}
