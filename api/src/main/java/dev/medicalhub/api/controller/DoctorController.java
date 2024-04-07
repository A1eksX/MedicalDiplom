package dev.medicalhub.api.controller;

import dev.medicalhub.api.model.DoctorModel;
import dev.medicalhub.api.model.Patient;
import dev.medicalhub.api.model.PatientCountProcedureModel;
import dev.medicalhub.api.model.ReceptionWithPatient;
import dev.medicalhub.api.service.AuthService;
import dev.medicalhub.api.service.DoctorService;
import dev.medicalhub.api.service.ReceptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final AuthService authService;
    @GetMapping
    public List<DoctorModel> getAll(){
        return doctorService.getAllFromMedicalInstitution(authService.getDoctor().getMedicalInstitution().getId());
    }

    @GetMapping("/{diplomaNumber}/reception")
    public List<ReceptionWithPatient> getReceptionWithPatient(@PathVariable long diplomaNumber){
        return doctorService.getReceptionWithPatient(diplomaNumber);
    }

    @GetMapping("/my")
    public DoctorModel getMy(){
        return doctorService.getByDiplomaNumber(Long.parseLong(authService.getAuthInfo().getUsername()));
    }
    @GetMapping("/my/patient")
    public List<PatientCountProcedureModel> getPatient(){
        return doctorService.getPatient(Long.parseLong(authService.getAuthInfo().getUsername()));
    }

//    @GetMapping("/{diplomaNumber}/reception")
//    public List<ReceptionModel> getIncompleteDoctor(@PathVariable long diplomaNumber) {
//        return receptionService.getIncomplete(diplomaNumber);
//    }
}
