package dev.medicalhub.api.repository;

import dev.medicalhub.api.entity.DiseaseEntity;
import dev.medicalhub.api.entity.ProcedureEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DiseaseRepo extends CrudRepository<DiseaseEntity,Long> {
}
