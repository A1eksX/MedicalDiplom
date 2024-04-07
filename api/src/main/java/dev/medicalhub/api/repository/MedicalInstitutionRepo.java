package dev.medicalhub.api.repository;

import dev.medicalhub.api.entity.MedicalInstitutionEntity;
import dev.medicalhub.api.entity.SpecializationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalInstitutionRepo extends CrudRepository<MedicalInstitutionEntity,Long> {
     boolean existsByName(String name);
    Optional<MedicalInstitutionEntity> findByName(String name);

    Optional<List<MedicalInstitutionEntity>> findByNameLike(String s);
    List<MedicalInstitutionEntity> findByNameLikeOrAddressLike(String name, String address);
}
