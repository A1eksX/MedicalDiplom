package dev.medicalhub.api.repository;

import dev.medicalhub.api.entity.MedicalBookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalBookRepo extends CrudRepository<MedicalBookEntity,Long> {
    Optional<MedicalBookEntity> findByPatient_Id(Long id);
}
