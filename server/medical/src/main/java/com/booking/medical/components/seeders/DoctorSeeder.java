package com.booking.medical.components.seeders;

import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.booking.medical.common.Gender;
import com.booking.medical.models.entities.Doctor;
import com.booking.medical.models.entities.Schedule;
import com.booking.medical.repositories.DoctorRepository;
import com.booking.medical.repositories.ScheduleRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DoctorSeeder implements Seeder {
    private final String localtion = "data/Doctor.json";
    private DoctorRepository doctorRepository;
    private ScheduleRepository scheduleRepository;

    public DoctorSeeder(DoctorRepository doctorRepository, ScheduleRepository scheduleRepository) {
        this.doctorRepository = doctorRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public void seed() {

        scheduleRepository.deleteAll();
        log.info("Clear all schedule completed");
        doctorRepository.deleteAll();
        log.info("Clear all doctor info completed");

        JSONParser parser = new JSONParser();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            File file = new ClassPathResource(localtion).getFile();
            Object obj = parser.parse(new FileReader(file));
            JSONArray jsonArray = (JSONArray) obj;
            for (Object item : jsonArray) {
                JSONObject doctorData = (JSONObject) item;
                String fullname = (String) doctorData.get("fullname");
                String title = (String) doctorData.get("title");
                String gender = (String) doctorData.get("gender");
                String specialist = (String) doctorData.get("specialist");

                Doctor doctor = new Doctor();
                doctor.setFullName(fullname);
                doctor.setTitle(title);
                doctor.setGender(this.VNToEN(gender));
                doctor.setSpecialist(specialist);

                JSONArray schedules = (JSONArray) doctorData.get("schedule");
                for (Object value : schedules) {
                    JSONObject scheduleData = (JSONObject) value;
                    String date = (String) scheduleData.get("date");
                    String clinic = (String) scheduleData.get("clinic");

                    LocalDate localDate = LocalDate.parse(date, formatter);
                    Schedule schedule = new Schedule();
                    schedule.setDate(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    schedule.setClinic(clinic);
                    schedule = scheduleRepository.save(schedule);

                    doctor.getSchedules().add(schedule);
                }
                doctorRepository.save(doctor);
                log.info("Create doctor: {} - Success", doctor.getFullName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Gender VNToEN(String Vietnames) {
        switch (Vietnames) {
            case "Nam":
                return Gender.MALE;
            case "Nữ":
                return Gender.FEMALE;
            default:
                return Gender.OTHER;
        }
    }
}
