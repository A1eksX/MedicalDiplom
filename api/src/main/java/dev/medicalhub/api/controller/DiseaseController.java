package dev.medicalhub.api.controller;

import dev.medicalhub.api.model.DiseaseModel;
import dev.medicalhub.api.model.Procedure;
import dev.medicalhub.api.service.DiseaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/disease")
@RequiredArgsConstructor
public class DiseaseController {
    private final DiseaseService diseaseService;

    @GetMapping("/{id}/procedures")
    public List<Procedure> getProcedures(@PathVariable long id){
       return diseaseService.getProcedures(id);
    }
    @PostMapping
    public long create (@RequestBody DiseaseModel diseaseModel){
        return diseaseService.create(diseaseModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable long id){
        diseaseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
