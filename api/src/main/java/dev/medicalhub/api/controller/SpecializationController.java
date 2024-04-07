package dev.medicalhub.api.controller;

import dev.medicalhub.api.entity.SpecializationEntity;
import dev.medicalhub.api.model.SpecializationModel;
import dev.medicalhub.api.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/specialization")
@RequiredArgsConstructor
public class SpecializationController {

    private final SpecializationService specializationService;
    @GetMapping
    public List<SpecializationModel> get(String search){
        var specializationEntities = specializationService.search(search);
        var specializationModels = new ArrayList<SpecializationModel>();
        for (SpecializationEntity specializationEntity : specializationEntities) {
            specializationModels.add(specializationEntity.toDTOModel());
        }
        return specializationModels;
    }
}
