package dev.medicalhub.api.controller;

import dev.medicalhub.api.model.MedicalBookWithDiseaseModel;
import dev.medicalhub.api.service.MedicalBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/medicalbook")
@RequiredArgsConstructor
public class MedicalBookController {
    private final MedicalBookService medicalBookService;
    @GetMapping("/{id}")
    public MedicalBookWithDiseaseModel get(@PathVariable long id){
        return medicalBookService.findByIdWithProcedures(id);
    }
}
