package dev.medicalhub.api.repository;

import dev.medicalhub.api.entity.ProcedureEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProcedureRepo extends CrudRepository<ProcedureEntity,Long> {
    @Transactional
    @Modifying
    @Query("update ProcedureEntity p set p.count = ?1 where p.id = ?2")
    void updateCountById(int count, Long id);
}
