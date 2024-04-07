package dev.medicalhub.api.controller;

import dev.medicalhub.api.entity.DoctorEntity;
import dev.medicalhub.api.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
@RequiredArgsConstructor
public class TestController {

    private final DoctorService doctorService;

    @PostMapping("doctor")
    public ResponseEntity<?> createDoctor(){
        DoctorEntity doctor = new DoctorEntity();
        doctor.setFullName("1234");
        doctor.setDiplomaNumber(1234);
        doctor.setPassword("1234");
        return ResponseEntity.ok(doctorService.create(doctor));
    }
}
