package dev.medicalhub.api.service;

import dev.medicalhub.api.entity.*;
import dev.medicalhub.api.exception.BadRequestException;
import dev.medicalhub.api.exception.NotFoundException;
import dev.medicalhub.api.model.ReceptionHistoryModel;
import dev.medicalhub.api.model.ReceptionModel;
import dev.medicalhub.api.model.ReceptionWithPatientModel;
import dev.medicalhub.api.repository.DoctorRepo;
import dev.medicalhub.api.repository.PatientRepo;
import dev.medicalhub.api.repository.ReceptionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReceptionService {

    private final ReceptionRepo receptionRepo;
    private final DoctorRepo doctorRepo;
    private final PatientRepo patientRepo;

    public ReceptionModel create(ReceptionModel receptionModel) {
        var receptionEntity = new ReceptionEntity();
        receptionEntity.setStatus(StatusReception.InProgress);
        receptionEntity.setData(receptionModel.getData());
        receptionEntity.setDateTime(receptionModel.getDateTime());

        if(!doctorRepo.existsById(receptionModel.getDoctorDiplomaNumber()))
            throw new NotFoundException("Доктор не найден");
        DoctorEntity doctor = new DoctorEntity();
        doctor.setDiplomaNumber(receptionModel.getDoctorDiplomaNumber());
        doctor.addReception(receptionEntity);

        receptionEntity.setDoctor(doctor);
        if(!patientRepo.existsById(receptionModel.getPatientId()))
            throw new NotFoundException("Пациент не найден");
        PatientEntity patient = new PatientEntity();
        patient.setId(receptionModel.getPatientId());
        receptionEntity.setPatient(patient);

        return receptionRepo.save(receptionEntity).toDTOModel();

    }

    public List<ReceptionWithPatientModel> getIncomplete (Long diplomaNumber){
        List<ReceptionEntity> responseEntities = (List<ReceptionEntity>) receptionRepo.findByDoctorDiplomaNumberAndStatus(diplomaNumber,StatusReception.InProgress);
        return responseEntities.stream().map(ReceptionEntity::toDTOWithPatientModel).toList();
    }

    public List<ReceptionHistoryModel> getHistory(Long id){
        List<ReceptionEntity> responseEntities = (List<ReceptionEntity>) receptionRepo.findByDoctorDiplomaNumberAndStatusNot(id,StatusReception.InProgress);
        return responseEntities.stream().map(ReceptionEntity::toDTOHistoryModel).toList();
    }

    public void deleteReception(long id) {
        if(!receptionRepo.existsById(id))
            throw new NotFoundException("Запись не найдена");
        receptionRepo.deleteById(id);
    }

    public ReceptionModel getById(long id) {
       return receptionRepo.findById(id)
               .orElseThrow(()->new NotFoundException("Запись не найдена")).toDTOModel();
    }

    public void end(long id, long doctor, boolean isCancelled,String data) {
        var reception =receptionRepo.findById(id)
                .orElseThrow(()-> new NotFoundException("Рецепт не найден"));
        if(reception.getDoctor().getDiplomaNumber() != doctor)
            throw new BadRequestException("Вы не можете завершить чужую смену");
        if (!reception.getStatus().equals(StatusReception.InProgress))
            throw new BadRequestException("Рецепт завершён был ранее");


        if (isCancelled){
            reception.setStatus(StatusReception.Cancelled);
        }
        else{
            reception.setData(data);
            reception.setStatus(StatusReception.Completed);
        }

        receptionRepo.save(reception);
    }
}
