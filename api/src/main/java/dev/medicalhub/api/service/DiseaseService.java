package dev.medicalhub.api.service;

import dev.medicalhub.api.entity.DiseaseEntity;
import dev.medicalhub.api.entity.MedicalBookEntity;
import dev.medicalhub.api.entity.ProcedureEntity;
import dev.medicalhub.api.exception.BadRequestException;
import dev.medicalhub.api.exception.NotFoundException;
import dev.medicalhub.api.model.DiseaseModel;
import dev.medicalhub.api.model.Procedure;
import dev.medicalhub.api.repository.DiseaseRepo;
import dev.medicalhub.api.repository.MedicalBookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiseaseService {
    private final DiseaseRepo diseaseRepo;
    private final MedicalBookRepo medicalBookRepo;

    public List<Procedure> getProcedures(long id) {
        return diseaseRepo.findById(id)
                .orElseThrow(()-> new  NotFoundException("Заболивание не найдена"))
                .getProcedures().stream().map(ProcedureEntity::toDTO).toList();
    }

    public long create(DiseaseModel diseaseModel) {
        var diseaseEntity = new DiseaseEntity();
        diseaseEntity.setDiagnosis(diseaseModel.getDiagnosis());
        diseaseEntity.setRezeptura(diseaseModel.getRezeptura());
        diseaseEntity.setTreatmentStart(diseaseModel.getTreatmentStart());
        diseaseEntity.setTreatmentEnd(diseaseModel.getTreatmentEnd());

       var medicalBook = medicalBookRepo.findById(diseaseModel.getMedicalBookId())
                .orElseThrow(()->new BadRequestException("Некоректный id мед книшки"));

        medicalBook.addHistoryDisease(diseaseEntity);
        diseaseEntity.setMedicalBook(medicalBook);

        return diseaseRepo.save(diseaseEntity).toDTOModel().getId();
    }

    public void delete(long id) {
        diseaseRepo.deleteById(id);
    }
}
