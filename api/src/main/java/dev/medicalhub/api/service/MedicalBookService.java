package dev.medicalhub.api.service;

import dev.medicalhub.api.entity.MedicalBookEntity;
import dev.medicalhub.api.exception.ConflictException;
import dev.medicalhub.api.exception.NotFoundException;
import dev.medicalhub.api.model.MedicalBookModel;
import dev.medicalhub.api.model.MedicalBookWithDiseaseModel;
import dev.medicalhub.api.repository.MedicalBookRepo;
import dev.medicalhub.api.repository.PatientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicalBookService {
    private final MedicalBookRepo medicalBookRepo;
    private final PatientRepo patientRepo;

    public MedicalBookModel create(MedicalBookModel medicalBookModel){
        if (!patientRepo.findById(medicalBookModel.getPatientId())
                .orElseThrow(()-> new NotFoundException("Пациента нет"))
                .getMedicalBook().getId().equals(medicalBookModel.getPatientId()))
            throw new ConflictException("У клиента уже есть мед. книжка.");
        return medicalBookRepo.save( new MedicalBookEntity(medicalBookModel)).toDTOModel();
    }


    public MedicalBookWithDiseaseModel findByIdWithProcedures(long id){
        return medicalBookRepo.findById(id)
                .orElseThrow(()-> new NotFoundException("Медицинская книжка не найдена")).toDTOWithDiseaseModel();
    }
    public MedicalBookModel findById(long id){
        return medicalBookRepo.findById(id)
                .orElseThrow(()-> new NotFoundException("Медицинская книжка не найдена")).toDTOModel();
    }

    public MedicalBookWithDiseaseModel findByPatientIdWithProcedures(long id) {
        return medicalBookRepo.findByPatient_Id(id)
                .orElseThrow(()-> new NotFoundException("Медицинская книжка не найдена")).toDTOWithDiseaseModel();
    }
}
