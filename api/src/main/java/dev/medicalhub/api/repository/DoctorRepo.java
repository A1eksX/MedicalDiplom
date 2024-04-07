package dev.medicalhub.api.repository;

import dev.medicalhub.api.entity.DoctorEntity;
import dev.medicalhub.api.entity.ReceptionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepo extends CrudRepository<DoctorEntity,Long> {
    Optional<DoctorEntity> findByDiplomaNumber(long diplomaNumber);
    List<DoctorEntity> findByMedicalInstitutionId(long id);
    boolean existsByDiplomaNumber(long diplomaNumber);
}
