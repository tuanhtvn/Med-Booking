package com.booking.medical.services;

import org.springframework.data.domain.Page;

import com.booking.medical.models.dtos.CreateRecordInputDTO;
import com.booking.medical.models.dtos.RecordDTO;
import com.booking.medical.models.dtos.UpdateRecordInputDTO;

public interface RecordService {

    Page<RecordDTO> RecordGetAll(String keyword, int page, int size);

    void RecordCreate(CreateRecordInputDTO createRecordInputDTO);

    void RecordUpdate(UpdateRecordInputDTO UpdateRecordInputDTO);

    void RecordDelete(String recordID);
}
