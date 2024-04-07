package dev.medicalhub.api.controller;

import dev.medicalhub.api.model.*;
import dev.medicalhub.api.service.MedicalBookService;
import dev.medicalhub.api.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final MedicalBookService medicalBookService;

    @PostMapping
    public PatientModel registration(@RequestBody RegistrationPatientModel registrationPatientModel) {
            return patientService.registration(registrationPatientModel);
    }
    @GetMapping("search")
    public List<PatientInfoModel> search(int passportData, int snils){
        return patientService.search(passportData, snils);
    }
    @GetMapping()
    public PatientModel getPassportDataAndSnils(int passportData, int snils){
        return patientService.getPassportDataAndSnils(passportData, snils);
    }

    @GetMapping("/{id}")
    public PatientModel getById(@PathVariable long id){
        return patientService.getById(id);
    }

    @GetMapping("/{id}/medicalbook")
    public MedicalBookWithDiseaseModel findByPatientId(@PathVariable long id){
        return medicalBookService.findByPatientIdWithProcedures(id);
    }

}
