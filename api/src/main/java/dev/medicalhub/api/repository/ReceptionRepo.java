package dev.medicalhub.api.repository;

import dev.medicalhub.api.entity.ReceptionEntity;
import dev.medicalhub.api.entity.StatusReception;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReceptionRepo extends CrudRepository<ReceptionEntity,Long> {
    public Iterable<ReceptionEntity> findByDoctorDiplomaNumberAndStatus(long doctorDiplomaNumber, StatusReception status);
    public Iterable<ReceptionEntity> findByDoctorDiplomaNumberAndStatusNot(long doctorDiplomaNumber, StatusReception status);

    List<ReceptionEntity> findByDoctor_DiplomaNumber(long diplomaNumber);
    List<ReceptionEntity> findByDoctor_DiplomaNumberOrderByPatient(long diplomaNumber);

    long countByDoctor_DiplomaNumberAndPatient_Id(long diplomaNumber, Long id);

    List<ReceptionEntity> findByPatientId(long id);
}
