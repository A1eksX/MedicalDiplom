package dev.medicalhub.api.service;

import dev.medicalhub.api.entity.MedicalBookEntity;
import dev.medicalhub.api.entity.PatientEntity;
import dev.medicalhub.api.entity.ReceptionEntity;
import dev.medicalhub.api.exception.BadRequestException;
import dev.medicalhub.api.exception.ConflictException;
import dev.medicalhub.api.exception.NotFoundException;
import dev.medicalhub.api.model.*;
import dev.medicalhub.api.repository.PatientRepo;
import dev.medicalhub.api.repository.ReceptionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepo patientRepo;
    private final ReceptionRepo receptionRepo;

    public PatientModel registration(RegistrationPatientModel registrationPatientModel){
        if (patientRepo.existsByPassportData(String.valueOf(registrationPatientModel.getPassportData())))
            throw new ConflictException("Пациент уже зарегистрирован");
        PatientEntity patientEntity =new PatientEntity();
        MedicalBookEntity medicalBookEntity = getMedicalBook(registrationPatientModel, patientEntity);

        patientEntity.setId(registrationPatientModel.getPatientId());
        patientEntity.setMedicalBook(medicalBookEntity);
        patientEntity.setSnils(String.valueOf(registrationPatientModel.getSnils()));
        patientEntity.setFullName(registrationPatientModel.getFullName());
        patientEntity.setPassportData(String.valueOf(registrationPatientModel.getPassportData()));

        return patientRepo.save(patientEntity).toDTOModel();
    }

    private static MedicalBookEntity getMedicalBook(RegistrationPatientModel registrationPatientModel, PatientEntity patientEntity) {
        MedicalBookEntity medicalBookEntity = new MedicalBookEntity();
        medicalBookEntity.setId(registrationPatientModel.getMedicalBookId());
        medicalBookEntity.setPatient(patientEntity);
        medicalBookEntity.setAllergy(registrationPatientModel.getAllergy());
        medicalBookEntity.setGroupBlood(registrationPatientModel.getGroupBlood());
        medicalBookEntity.setGraftCertificate(registrationPatientModel.getGraftCertificate());
        medicalBookEntity.setRhFactor(registrationPatientModel.isRhFactor());
        return medicalBookEntity;
    }

    public List<PatientInfoModel> search(int passportData, int snils) {
        var patientEntities = patientRepo.findByPassportDataContainsAndSnilsContains(String.valueOf(passportData),String.valueOf(snils));
            if (patientEntities.isEmpty())
                throw new BadRequestException("Пациент не найден");
        return patientEntities.stream().map(PatientEntity::toDTOInfoModel).toList();
    }

    public PatientModel getById(long id){
       return patientRepo.findById(id)
                .orElseThrow(()->new NotFoundException("Пациент не найден")).toDTOModel();
    }

    public PatientModel getPassportDataAndSnils(int passportData, int snils) {
        return patientRepo.findByPassportDataAndSnils(String.valueOf(passportData),String.valueOf(snils))
                .orElseThrow(()->new NotFoundException("Пациент не найден")).toDTOModel();
    }

    public List<ReceptionWithDoctor> getByIdReceptions(long id) {
        return receptionRepo.findByPatientId(id).stream().map(ReceptionEntity::toReceptionWithDoctor).toList();

    }
}
