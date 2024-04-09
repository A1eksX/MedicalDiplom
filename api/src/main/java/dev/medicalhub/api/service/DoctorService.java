package dev.medicalhub.api.service;

import dev.medicalhub.api.entity.DoctorEntity;
import dev.medicalhub.api.entity.ReceptionEntity;
import dev.medicalhub.api.exception.ConflictException;
import dev.medicalhub.api.exception.NotFoundException;
import dev.medicalhub.api.model.DoctorModel;
import dev.medicalhub.api.model.Patient;
import dev.medicalhub.api.model.PatientCountProcedureModel;
import dev.medicalhub.api.model.ReceptionWithPatient;
import dev.medicalhub.api.repository.DoctorRepo;
import dev.medicalhub.api.repository.ReceptionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepo doctorRepo;
    private final ReceptionRepo receptionRepo;


    public DoctorModel getByDiplomaNumber(long diplomaNumber){
        return doctorRepo.findByDiplomaNumber(diplomaNumber)
                .orElseThrow(()-> new NotFoundException("Доктор не найден")).toDTOModel();
    }

    public DoctorEntity create(DoctorEntity doctor){
        if (doctorRepo.existsByDiplomaNumber(doctor.getDiplomaNumber()))
            throw new ConflictException("Доктор уже зарегистрирован");
        return doctorRepo.save(doctor);
    }

    public List<DoctorModel> getAllFromMedicalInstitution( long medicalInstitutionId) {
        var doctors = doctorRepo.findByMedicalInstitutionId(medicalInstitutionId);
        List<DoctorModel> doctorModels = new ArrayList<>();
        for (DoctorEntity doctor:doctors)
        {
            if(doctor.getSpecialization().getName().equals("Регистратор"))
                continue;
            doctorModels.add(doctor.toDTOModel());
        }

        return doctorModels;
    }

    public List<ReceptionWithPatient> getReceptionWithPatient(long diplomaNumber){
        return receptionRepo.findByDoctor_DiplomaNumber(diplomaNumber)
                .stream().map(ReceptionEntity::toDTOWithPatient).toList();

    }
    public List<PatientCountProcedureModel> getPatient(long diplomaNumber){


        var patientCountProcedureModels = new ArrayList<>(receptionRepo.findByDoctor_DiplomaNumberOrderByPatient(diplomaNumber)
                .stream().map(o -> o.getPatient().
                        toDTOCountProcedureModel(receptionRepo.countByDoctor_DiplomaNumberAndPatient_Id(diplomaNumber, o.getPatient().getId()))).toList());
        // Add the elements to set
        Set<PatientCountProcedureModel> set = new LinkedHashSet<>(patientCountProcedureModels);

        // Clear the list
        patientCountProcedureModels.clear();

        // add the elements of set
        // with no duplicates to the list
        patientCountProcedureModels.addAll(set);

        // return the list
        return patientCountProcedureModels;
    }
}
