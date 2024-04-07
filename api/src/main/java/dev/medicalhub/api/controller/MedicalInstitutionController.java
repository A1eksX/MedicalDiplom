package dev.medicalhub.api.controller;

import dev.medicalhub.api.entity.MedicalInstitutionEntity;
import dev.medicalhub.api.model.MedicalInstitutionModel;
import dev.medicalhub.api.service.MedicalInstitutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("api/medicalinstitution")
@RequiredArgsConstructor
public class MedicalInstitutionController {

    private final MedicalInstitutionService medicalInstitutionService;
    @GetMapping
    public ResponseEntity<List<MedicalInstitutionModel>> get(String search){
        List<MedicalInstitutionEntity> medicalInstitutionEntities = medicalInstitutionService.search(search);
        List<MedicalInstitutionModel> medicalInstitutionModels = new ArrayList<>();
        for (MedicalInstitutionEntity medicalInstitutionEntity : medicalInstitutionEntities) {
            medicalInstitutionModels.add(medicalInstitutionEntity.toDTO());
        }
        return ResponseEntity.ok(medicalInstitutionModels);
    }
}
