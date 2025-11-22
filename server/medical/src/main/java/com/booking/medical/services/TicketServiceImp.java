package com.booking.medical.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.booking.medical.exception.CustomException;
import com.booking.medical.models.dtos.CreateTicketInputDTO;
import com.booking.medical.models.dtos.ScheduleDTO;
import com.booking.medical.models.entities.Schedule;
import com.booking.medical.models.entities.Ticket;
import com.booking.medical.models.entities.User;
import com.booking.medical.repositories.TicketRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TicketServiceImp implements TicketService {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private UserService userService;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void Booking(Long doctorID, Long recordID,
            Long scheduleID,
            CreateTicketInputDTO createTicketInputDTO) {
        // check thông tin bệnh nhân
        User user = userService.GetUserWithAuthentication();
        user.getRecords().stream().filter(item -> item.getId() == scheduleID).findFirst().orElseThrow(() -> {
            throw new CustomException("Hệ thống không tìm thấy thông tin hồ sơ bạn chọn. Vui lòng thử lại sau!!",
                    HttpStatus.BAD_REQUEST);
        });
        // check lịch khám
        List<ScheduleDTO> schedulesDTO = doctorService.GetSchedule(doctorID);

        ScheduleDTO scheduleDTO = schedulesDTO.stream().filter(item -> item.getId() == scheduleID).findFirst()
                .orElseThrow(() -> {
                    throw new CustomException("Không tìm thấy thông tin lịch khám đã chọn", HttpStatus.BAD_REQUEST);
                });
        Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);
        // đặt lịch khám
        Integer STT = null;
        if (createTicketInputDTO.getIsMorning()) {
            if (schedule.getCountAm() == 0) {
                throw new CustomException("Lịch khám đã hết vui lòng chọn lịch khám khác", HttpStatus.BAD_REQUEST);
            }
            STT = schedule.getCountAm() - (schedule.getCountAm() - 1);
            schedule.setCountAm(schedule.getCountAm() - 1);
            // cập nhật schedule
            scheduleService.Save(schedule);
            // tạo phiếu khám
            Ticket ticket = new Ticket();
            ticket.setSerial(String.valueOf(STT));
            ticket.setIsHealthInsurance(createTicketInputDTO.getIsHealthInsurance());
            ticket.setIsMorning(true);
            ticket.setSchedule(modelMapper.map(scheduleDTO, Schedule.class));
            //
            if (user.getTickets() == null) {
                user.setTickets(new ArrayList<>());
            }
            ticket = ticketRepository.save(ticket);
            user.getTickets().add(ticket);
            userService.Save(user);
            // Gửi phiếu khám
            log.info(ticket.toString());
        }
    }

}
