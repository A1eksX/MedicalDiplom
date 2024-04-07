package dev.medicalhub.api.service;

import dev.medicalhub.api.entity.DiseaseEntity;
import dev.medicalhub.api.entity.ProcedureEntity;
import dev.medicalhub.api.exception.BadRequestException;
import dev.medicalhub.api.exception.NotFoundException;
import dev.medicalhub.api.model.ProcedureModel;
import dev.medicalhub.api.repository.DiseaseRepo;
import dev.medicalhub.api.repository.ProcedureRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProcedureService {
    private final ProcedureRepo procedureRepo;
    private final DiseaseRepo diseaseRepo;
    public ProcedureModel create(ProcedureModel procedureModel) {
        var procedureEntity = new ProcedureEntity ();
        procedureEntity.setName(procedureModel.getName());
        procedureEntity.setCount(procedureModel.getCount());
        procedureEntity.setDrugs(procedureModel.getDrugs());

        var disease = diseaseRepo.findById(procedureModel.getDisease_id())
                .orElseThrow(()->new BadRequestException("Некоректный id заболевания"));
        procedureEntity.setDisease(disease);
        disease.addProcedure(procedureEntity);

        return procedureRepo.save(procedureEntity).toDTOModel();
    }

    public void delete(long id) {
        if (!procedureRepo.existsById(id))
            throw new NotFoundException("Процедура не найдена");
        procedureRepo.deleteById(id);
    }

    public void patchCount(long id, int count) {
        if (!procedureRepo.existsById(id))
            throw new NotFoundException("Процедура не найдена");
        procedureRepo.updateCountById(count,id);
    }
}
