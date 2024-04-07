package dev.medicalhub.api.controller;

import dev.medicalhub.api.model.ProcedureModel;
import dev.medicalhub.api.service.ProcedureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/procedure")
@RequiredArgsConstructor
public class ProcedureController {
    private final ProcedureService procedureService;

    @PostMapping
    public long create(@RequestBody ProcedureModel procedureModel){
        return procedureService.create(procedureModel).getId();
    }

    @PatchMapping("/{id}")
    public void patchCount(@PathVariable long id,int count){
        procedureService.patchCount(id,count);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        procedureService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
