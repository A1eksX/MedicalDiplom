package dev.medicalhub.api.repository;

import dev.medicalhub.api.entity.PatientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepo extends CrudRepository<PatientEntity,Long> {
    boolean existsBySnils(String snils);

    boolean existsByPassportData(String passportData);
    Iterable<PatientEntity> findByPassportDataLikeOrSnilsLike(String passportData,String snils);

    Optional<PatientEntity> findByPassportDataAndSnils(String passportData, String snils);

    List<PatientEntity> findByPassportDataContainsAndSnilsContains(String passportData, String snils);
}
